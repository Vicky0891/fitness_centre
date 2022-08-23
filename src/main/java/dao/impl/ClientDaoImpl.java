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
import dao.entity.Client.Type;
import dao.entity.User.Role;
import dao.interfaces.ClientDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientDaoImpl implements ClientDao {

    private static final String DELETE = "UPDATE users SET deleted = true WHERE id = ?";
    private static final String UPDATE = "UPDATE clients SET first_name = ?, last_name = ?, birth_date = ?, "
            + "phone_number = ?, trainer_id = ?, type_id = ?, additional_info = ?, path_avatar = ? WHERE user_id = ?";
    private static final String INSERT = "INSERT INTO clients (user_id, birth_date, type_id) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT c.user_id, c.first_name, c.last_name, u.email, u.password, "
            + "r.name AS role, c.birth_date, c.phone_number, c.trainer_id, t.name AS type, c.additional_info, "
            + "c.path_avatar FROM clients c JOIN types t ON c.type_id = t.id JOIN users u ON c.user_id = u.id "
            + "JOIN roles r ON r.id = u.role_id WHERE u.deleted = false ORDER BY c.user_id";
    private static final String SELECT_ALL_BY_PAGED = "SELECT c.user_id, c.first_name, c.last_name, u.email, u.password, "
            + "r.name AS role, c.birth_date, c.phone_number, c.trainer_id, t.name AS type, c.additional_info, "
            + "c.path_avatar FROM clients c JOIN types t ON c.type_id = t.id JOIN users u ON c.user_id = u.id "
            + "JOIN roles r ON r.id = u.role_id WHERE u.deleted = false ORDER BY c.user_id LIMIT ? OFFSET ?";
    private static final String SELECT_BY_ID = "SELECT c.user_id, c.first_name, c.last_name, u.email, "
            + "u.password, r.name AS role, c.birth_date, c.phone_number, c.trainer_id, t.name AS type, "
            + "c.additional_info, c.path_avatar FROM clients c JOIN types t ON c.type_id = t.id JOIN users u "
            + "ON c.user_id = u.id JOIN roles r ON r.id = u.role_id WHERE c.user_id = ? AND u.deleted = false";
    private static final String SELECT_TYPE_ID = "SELECT t.id FROM types t WHERE name = ?";
    private static final String SELECT_ALL_BY_TYPES = "SELECT c.user_id, c.first_name, c.last_name, u.email, "
            + "u.password, r.name AS role, c.birth_date, c.phone_number, c.trainer_id, t.name AS type, "
            + "c.additional_info, c.path_avatar FROM clients c JOIN types t ON c.type_id = t.id JOIN users u "
            + "ON c.user_id = u.id JOIN roles r ON r.id = u.role_id WHERE t.name = ? AND u.deleted = false "
            + "ORDER BY c.user_id";
    private static final String COUNT_ALL = "SELECT count(*) AS total FROM clients c JOIN users u ON c.user_id = u.id"
            + " WHERE u.deleted = false";

    private DataSource dataSource;

    public ClientDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Client get(Long id) throws DaoException {
        log.debug("Accessing to database using \"get\" method, client id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processClient(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException(
                    "Something went wrong. Failed to get client id=" + id + ". Contact your system administrator.");
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public List<Client> getAll() throws DaoException {
        log.debug("Accessing to database using \"getAll\" method");
        List<Client> clients = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                clients.add(processClient(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return clients;
    }

    @Override
    public List<Client> getAll(int limit, Long offset) throws DaoException {
        log.debug("Accessing to database using \"getAll\" method");
        List<Client> clients = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                clients.add(processClient(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return clients;
    }

    @Override
    public List<Client> getAllClientsByType(String typeOfClient) throws DaoException {
        log.debug("Accessing to database using \"getAllClientsByType\" method");
        List<Client> clients = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_TYPES);
            statement.setString(1, typeOfClient);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                clients.add(processClient(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return clients;
    }

    @Override
    public Client create(Client client) throws DaoException {
        log.debug("Accessing to database using \"create\" method, client={}", client);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, client.getId());
            statement.setDate(2, Date.valueOf(client.getBirthDate()));
            statement.setInt(3, getTypeId(client.getType().name()));
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                Long id = result.getLong("user_id");
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Client wasn't create. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public Client update(Client client) throws DaoException {
        log.debug("Accessing to database using \"update\" method, client=", client);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setDate(3, Date.valueOf(client.getBirthDate()));
            statement.setString(4, client.getPhoneNumber());
            statement.setLong(5, client.getTrainerId());
            statement.setInt(6, getTypeId(client.getType().name()));
            statement.setString(7, client.getAdditionalInfo());
            statement.setString(8, client.getPathAvatar());
            statement.setLong(9, client.getId());
            statement.executeUpdate();

            return get(client.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Client with id=" + client.getId()
                    + "wasn't update. Contact your system administrator.");
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        log.debug("Accessing to database using \"delete\" method, client id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException(
                    "Something went wrong. Client with id=" + id + "wasn't delete. Contact your system administrator.");
        } finally {
            close(connection);
        }
    }

    private Client processClient(ResultSet result) throws DaoException {
        try {
            Client client = new Client();
            client.setId(result.getLong("user_id"));
            client.setEmail(result.getString("email"));
            client.setPassword(result.getString("password"));
            client.setRole(Role.valueOf(result.getString("role")));
            client.setFirstName(result.getString("first_name"));
            client.setLastName(result.getString("last_name"));
            client.setBirthDate(result.getDate("birth_date").toLocalDate());
            client.setPhoneNumber(result.getString("phone_number"));
            client.setType(Type.valueOf(result.getString("type")));
            client.setTrainerId(result.getLong("trainer_id"));
            client.setAdditionalInfo(result.getString("additional_info"));
            client.setPathAvatar(result.getString("path_avatar"));
            return client;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        }
    }

    private int getTypeId(String name) throws DaoException {
        log.debug("Accessing to database using \"getTypeId\" method, name={}", name);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_TYPE_ID);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            if (result.next()) {

                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        log.error("Type of client with name={} didn't find", name);
        throw new DaoException("Something went wrong. Contact your system administrator.");
    }

    @Override
    public long count() throws DaoException {
        log.debug("Accessing to database using \"count\" method");
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(COUNT_ALL);
            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        log.error("Couldn't count clients");
        throw new DaoException("Something went wrong. Contact your system administrator.");
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
