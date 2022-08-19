package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.util.exception.impl.InternalErrorException;
import controller.util.exception.impl.NotFoundException;
import dao.connection.DataSource;
import dao.entity.Prescription;
import dao.entity.Order.Status;
import dao.interfaces.PrescriptionDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PrescriptionDaoImpl implements PrescriptionDao {

    private static final String DELETE = "UPDATE prescriptions SET deleted = true WHERE client_id = ?";
    private static final String UPDATE = "UPDATE prescriptions SET type_of_training = ?, equipment = ?, diet = ?, "
            + "status_id = ? WHERE client_id = ?";
    private static final String INSERT = "INSERT INTO prescriptions (trainer_id, client_id, type_of_training, equipment, diet, status_id)"
            + " VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT p.id, p.trainer_id, p.client_id, p.type_of_training, p.equipment, p.diet, s.name AS status FROM prescriptions p "
            + "JOIN status s ON s.id = p.status_id JOIN users u ON p.client_id = u.id WHERE p.client_id = ? AND u.deleted = false";
    private static final String SELECT_STATUS_PENDING = "SELECT s.id FROM status s WHERE name = 'PENDING'";
    private static final String SELECT_STATUS_CONFIRM = "SELECT s.id FROM status s WHERE name = 'CONFIRM'";
    private static final String SELECT_ALL = "SELECT p.id, p.trainer_id, p.client_id, p.type_of_training, "
            + "p.equipment, p.diet, s.name AS status FROM prescriptions p JOIN status s ON s.id = p.status_id WHERE p.deleted = false";

    private DataSource dataSource;

    public PrescriptionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Prescription get(Long clientId) {
        log.debug("Accessing to database using \"get\" method, client id={}", clientId);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, clientId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processPrescription(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public Prescription create(Prescription prescription) throws InternalErrorException {
        log.debug("Accessing to database using \"create\" method, prescription={}", prescription);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, prescription.getTrainerId());
            statement.setLong(2, prescription.getUserId());
            statement.setString(3, prescription.getTypeOfTraining());
            statement.setString(4, prescription.getEquipment());
            statement.setString(5, prescription.getDiet());
            statement.setInt(6, getStatusPending());
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                Long id = result.getLong("client_id");
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public Prescription update(Prescription prescription) throws InternalErrorException {
        log.debug("Accessing to database using \"update\" method, prescription={}", prescription);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, prescription.getTypeOfTraining());
            statement.setString(2, prescription.getEquipment());
            statement.setString(3, prescription.getDiet());
            statement.setInt(4, getStatusConfirm());
            statement.setLong(5, prescription.getUserId());
            statement.executeUpdate();
            return get(prescription.getUserId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public void delete(Prescription prescription) {
        log.debug("Accessing to database using \"delete\" method, prescription={}", prescription);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, prescription.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
    }

    private Prescription processPrescription(ResultSet result) throws SQLException {
        Prescription prescription = new Prescription();
        prescription.setId(result.getLong("id"));
        prescription.setUserId(result.getLong("client_id"));
        prescription.setTrainerId(result.getLong("trainer_id"));
        prescription.setTypeOfTraining(result.getString("type_of_training"));
        prescription.setEquipment(result.getString("equipment"));
        prescription.setDiet(result.getString("diet"));
        prescription.setStatus(Status.valueOf(result.getString("status")));
        return prescription;
    }

    private int getStatusPending() throws InternalErrorException {
        log.debug("Accessing to database using \"getStatusPending\"");
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_STATUS_PENDING);
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        log.error("Id status \"pending\" didn't find");
        throw new InternalErrorException("Internal Server Error");
    }

    private int getStatusConfirm() throws InternalErrorException {
        log.debug("Accessing to database using \"getStatusConfirm\"");
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_STATUS_CONFIRM);
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        log.error("Id status \"confirm\" didn't find");
        throw new InternalErrorException("Internal Server Error");
    }

    @Override
    public List<Prescription> getAll() {
        log.debug("Accessing to database using \"getAll\" method");
        List<Prescription> prescriptions = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                prescriptions.add(processPrescription(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return prescriptions;
    }

    private void close(Connection connection) {
        try {
            connection.close();
            log.debug("Connection closed");
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
        }
    }
}
