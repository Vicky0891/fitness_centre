package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.connection.DataSource;
import dao.entity.Client;
import dao.entity.Trainer;
import dao.entity.User.Role;
import dao.interfaces.ClientDao;
import dao.interfaces.TrainerDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TrainerDaoImpl implements TrainerDao {
    private static final String DELETE = "UPDATE users SET deleted = true WHERE id = ?";
    private static final String UPDATE = "UPDATE trainers SET first_name = ?, last_name = ?, birth_date = ?, "
            + "category = ?, path_avatar = ? WHERE user_id = ?";
    private static final String INSERT = "INSERT INTO trainers (user_id, birth_date) VALUES (?, ?)";
    private static final String SELECT_ALL = "SELECT tr.user_id, tr.first_name, tr.last_name, u.email, u.password,"
            + " r.name AS role, tr.birth_date, tr.category, tr.path_avatar FROM trainers tr JOIN users u "
            + "ON u.id = tr.user_id JOIN roles r ON r.id = u.role_id WHERE u.deleted = false ORDER BY tr.user_id";
    private static final String SELECT_BY_ID = "SELECT tr.user_id, tr.first_name, tr.last_name, u.email, u.password,"
            + " r.name AS role, tr.birth_date, tr.category, tr.path_avatar FROM trainers tr JOIN users u "
            + "ON tr.user_id = u.id JOIN roles r ON r.id = u.role_id WHERE tr.user_id = ? AND u.deleted = false";
    private static final String SELECT_CLIENTS_FOR_TRAINER = "SELECT c.user_id, c.first_name, c.last_name, u.email, "
            + "u.password, r.name AS role, c.birth_date, c.phone_number, c.trainer_id, t.name AS type, "
            + "c.additional_info FROM clients c JOIN types t ON t.id = c.type_id JOIN trainers tr "
            + "ON c.trainer_id = tr.user_id JOIN users u ON u.id = c.user_id JOIN roles r ON r.id = u.role_id "
            + "WHERE tr.user_id = ? AND u.deleted = false";

    private DataSource dataSource;
    private ClientDao clientDao;

    public TrainerDaoImpl(DataSource dataSource, ClientDao clientDao) {
        this.dataSource = dataSource;
        this.clientDao = clientDao;
    }

    @Override
    public Trainer get(Long id) throws DaoException {
        log.debug("Accessing to database using \"get\" method, trainer id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processTrainer(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException(
                    "Something went wrong. Failed to get trainer id=" + id + ". Contact your system administrator.");
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public List<Trainer> getAll() throws DaoException {
        log.debug("Accessing to database using \"getAll\" method");
        List<Trainer> trainers = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                trainers.add(processTrainer(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return trainers;
    }

    @Override
    public Trainer create(Trainer trainer) throws DaoException {
        log.debug("Accessing to database using \"create\" method, trainer={}", trainer);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, trainer.getId());
            statement.setDate(2, Date.valueOf(trainer.getBirthDate()));
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                Long id = result.getLong("user_id");
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Trainer wasn't create. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public Trainer update(Trainer trainer) throws DaoException {
        log.debug("Accessing to database using \"update\" method, trainer={}", trainer);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, trainer.getFirstName());
            statement.setString(2, trainer.getLastName());
            statement.setDate(3, Date.valueOf(trainer.getBirthDate()));
            statement.setString(4, trainer.getCategory());
            statement.setString(5, trainer.getPathAvatar());
            statement.setLong(6, trainer.getId());

            statement.executeUpdate();
            return get(trainer.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Trainer witn id=" + trainer.getId()
                    + " wasn't create. Contact your system administrator.");
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        log.debug("Accessing to database using \"delete\" method, trainer id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Trainer witn id=" + id
                    + " wasn't delete. Contact your system administrator.");
        } finally {
            close(connection);
        }
    }

    @Override
    public List<Client> getAllClientsByTrainer(Long id) throws DaoException {
        log.debug("Accessing to database using \"getAllClientsByTrainer\" method, trainer id={}", id);
        List<Client> clients = new ArrayList<>();

        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CLIENTS_FOR_TRAINER);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                clients.add(clientDao.get(result.getLong("user_id")));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return clients;
    }

    /**
     * Method to process trainer by resultSet from data source
     * 
     * @param result ResultSet from data source
     * @return Trainer
     * @throws DaoException
     */
    private Trainer processTrainer(ResultSet result) throws DaoException {
        try {
            Trainer trainer = new Trainer();
            trainer.setId(result.getLong("user_id"));
            trainer.setEmail(result.getString("email"));
            trainer.setPassword(result.getString("password"));
            trainer.setRole(Role.valueOf(result.getString("role")));
            trainer.setFirstName(result.getString("first_name"));
            trainer.setLastName(result.getString("last_name"));
            trainer.setBirthDate(result.getDate("birth_date").toLocalDate());
            trainer.setCategory(result.getString("category"));
            trainer.setPathAvatar(result.getString("path_avatar"));
            List<Client> clients = getAllClientsByTrainer(result.getLong("user_id"));
            trainer.setClients(clients);
            return trainer;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        }
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
