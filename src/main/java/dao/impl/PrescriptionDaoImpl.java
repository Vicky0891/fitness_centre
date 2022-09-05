package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.util.exception.impl.DaoException;
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
    private static final String INSERT = "INSERT INTO prescriptions (trainer_id, client_id, type_of_training, equipment,"
            + " diet, status_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT p.id, p.trainer_id, p.client_id, p.type_of_training, p.equipment, "
            + "p.diet, s.name AS status FROM prescriptions p JOIN status s ON s.id = p.status_id JOIN users u "
            + "ON p.client_id = u.id WHERE p.client_id = ? AND u.deleted = false";
    private static final String SELECT_STATUS_PENDING = "SELECT s.id FROM status s WHERE name = 'PENDING'";
    private static final String SELECT_STATUS_CONFIRM = "SELECT s.id FROM status s WHERE name = 'CONFIRM'";
    private static final String SELECT_ALL = "SELECT p.id, p.trainer_id, p.client_id, p.type_of_training, "
            + "p.equipment, p.diet, s.name AS status FROM prescriptions p JOIN status s ON s.id = p.status_id "
            + "WHERE p.deleted = false";

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
            throw new DaoException("Something went wrong. Failed to get prescription with client id=" + clientId
                    + ". Contact your system administrator.");
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public Prescription create(Prescription prescription) {
        log.debug("Accessing to database using \"create\" method, prescription={}", prescription);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, prescription.getTrainerId());
            statement.setLong(2, prescription.getUserId());
            statement.setString(3, prescription.getTypeOfTraining());
            statement.setString(4, prescription.getEquipment());
            statement.setString(5, prescription.getDiet());
            statement.setInt(6, getStatusPending(connection));
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                Long id = result.getLong("client_id");
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException(
                    "Something went wrong. Prescription wasn't create. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public Prescription update(Prescription prescription) {
        log.debug("Accessing to database using \"update\" method, prescription={}", prescription);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, prescription.getTypeOfTraining());
            statement.setString(2, prescription.getEquipment());
            statement.setString(3, prescription.getDiet());
            statement.setInt(4, getStatusConfirm(connection));
            statement.setLong(5, prescription.getUserId());
            statement.executeUpdate();
            return get(prescription.getUserId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Prescription with client id=" + prescription.getUserId()
                    + "wasn't update. Contact your system administrator.");
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing to database using \"delete\" method, prescription id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Prescription with client id=" + id
                    + "wasn't delete. Contact your system administrator.");
        } finally {
            close(connection);
        }
    }

    /**
     * Method to process prescription by resultSet from data source
     * 
     * @param result ResultSet from data source
     * @return Prescription
     * @throws DaoException
     */
    private Prescription processPrescription(ResultSet result) {
        try {
            Prescription prescription = new Prescription();
            prescription.setId(result.getLong("id"));
            prescription.setUserId(result.getLong("client_id"));
            prescription.setTrainerId(result.getLong("trainer_id"));
            prescription.setTypeOfTraining(result.getString("type_of_training"));
            prescription.setEquipment(result.getString("equipment"));
            prescription.setDiet(result.getString("diet"));
            prescription.setStatus(Status.valueOf(result.getString("status")));
            return prescription;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        }
    }

    /**
     * Method to get id of status "pending" in data source table
     * 
     * @return id of status "pending"
     * @throws DaoException
     */
    private int getStatusPending(Connection connection) {
        log.debug("Accessing to database using \"getStatusPending\"");
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_STATUS_PENDING);
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        log.error("Id status \"pending\" didn't find");
        throw new DaoException("Something went wrong. Contact your system administrator.");
    }

    /**
     * Method to get id of status "confirm" in data source table
     * 
     * @return id of status "confirm"
     * @throws DaoException
     */
    private int getStatusConfirm(Connection connection) {
        log.debug("Accessing to database using \"getStatusConfirm\"");
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_STATUS_CONFIRM);
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        log.error("Id status \"confirm\" didn't find");
        throw new DaoException("Something went wrong. Contact your system administrator.");
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
            throw new DaoException("Something went wrong. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return prescriptions;
    }

    /**
     * Method to close connection
     * 
     * @param connection Connection to close
     */
    private void close(Connection connection) {
        try {
            connection.close();
            log.debug("Connection closed");
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
        }
    }
}
