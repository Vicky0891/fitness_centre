package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.connection.DataSource;
import dao.entity.Prescription;
import dao.entity.Order.Status;
import dao.interfaces.CustomerDao;
import dao.interfaces.PrescriptionDao;
import dao.interfaces.TrainerDao;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class PrescriptionDaoImpl implements PrescriptionDao {

    private static final String DELETE = "DELETE FROM prescriptions WHERE order_id = ?";
    private static final String UPDATE = "UPDATE prescriptions SET type_of_training = ?, equipment = ?, diet = ?, "
            + "status_id = ? WHERE order_id = ?";
    private static final String INSERT = "INSERT INTO prescriptions (order_id, trainer_id, customer_id, type_of_training, equipment, diet, status_id"
            + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT p.id, p.order_id, p.trainer_id, p.customer_id, p.type_of_training, p.equipment, p.diet, p.status_id FROM prescriptions p "
            + "JOIN status s ON s.id = p.status_id JOIN orders o ON p.order_id = o.id WHERE o.id = ? AND o.deleted = false";
    private static final String SELECT_STATUS_PENDING = "SELECT s.id FROM status s WHERE name = 'PENDING'";
    private static final String SELECT_STATUS_CONFIRM = "SELECT s.id FROM status s WHERE name = 'CONFIRM'";
    private static final String SELECT_ALL = "SELECT p.id, p.order_id, p.trainer_id, p.customer_id, p.type_of_training, "
            + "p.equipment, p.diet FROM prescriptions p";

    private DataSource dataSource;
    private CustomerDao customerDao;
    private TrainerDao trainerDao;

    public PrescriptionDaoImpl(DataSource dataSource, CustomerDao customerDao, TrainerDao trainerDao) {
        this.dataSource = dataSource;
        this.customerDao = customerDao;
        this.trainerDao = trainerDao;
    }

    @Override
    public Prescription get(Long orderId) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, orderId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processPrescription(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public Prescription create(Prescription prescription) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, prescription.getOrder().getId());
            statement.setLong(2, prescription.getTrainer().getId());
            statement.setLong(3, prescription.getCustomer().getId());
            statement.setString(4, prescription.getTypeOfTraining());
            statement.setString(5, prescription.getEquipment());
            statement.setString(6, prescription.getDiet());
            statement.setInt(7, getStatusPending());            
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                Long id = result.getLong("id");
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }
        

    @Override
    public Prescription update(Prescription prescription) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE);
            statement.setString(1, prescription.getTypeOfTraining());
            statement.setString(2, prescription.getEquipment());
            statement.setString(3, prescription.getDiet());
            statement.setInt(4, getStatusConfirm());
            statement.setLong(5, prescription.getOrder().getId());
            statement.executeUpdate();

            return get(prescription.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public void delete(Prescription prescription) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(DELETE);
            statement.setLong(1, prescription.getOrder().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
    }

    private Prescription processPrescription(ResultSet result) throws SQLException {
        Prescription prescription = new Prescription();
        prescription.setId(result.getLong("id"));
        prescription.setTypeOfTraining(result.getString("type_of_training"));
        prescription.setEquipment(result.getString("equipment"));
        prescription.setDiet(result.getString("diet"));
        Long customerId = result.getLong("customer_id");
        prescription.setCustomer(customerDao.get(customerId));
        Long trainerId = result.getLong("trainer_id");
        prescription.setTrainer(trainerDao.get(trainerId));
        prescription.setStatus(Status.valueOf(result.getString("status")));
        return prescription;
    }

    private int getStatusPending() {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_STATUS_PENDING);
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        log.error("Unable to establish connection or error in id");
        throw new RuntimeException();
    }

    private int getStatusConfirm() {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_STATUS_CONFIRM);
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        log.error("Unable to establish connection or error in id");
        throw new RuntimeException();
    }

    @Override
    public List<Prescription> getAll() {
        List<Prescription> prescriptions = new ArrayList<>();
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                prescriptions.add(processPrescription(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return prescriptions;
    }

}
