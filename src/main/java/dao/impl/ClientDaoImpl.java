package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.util.exception.impl.InternalErrorException;
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
            + "phone_number = ?, trainer_id = ?, type_id = ?, additional_info = ? WHERE user_id = ?";
    private static final String INSERT = "INSERT INTO clients (user_id, birth_date, type_id) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT c.user_id, c.first_name, c.last_name, u.email, u.password, "
            + "r.name AS role, c.birth_date, c.phone_number, c.trainer_id, t.name AS type, c.additional_info "
            + "FROM clients c JOIN types t ON c.type_id = t.id JOIN users u ON c.user_id = u.id JOIN roles r "
            + "ON r.id = u.role_id WHERE u.deleted = false ORDER BY c.user_id";
    private static final String SELECT_BY_ID = "SELECT c.user_id, c.first_name, c.last_name, u.email, "
            + "u.password, r.name AS role, c.birth_date, c.phone_number, c.trainer_id, t.name AS type, "
            + "c.additional_info FROM clients c JOIN types t ON c.type_id = t.id JOIN users u ON c.user_id = u.id "
            + "JOIN roles r ON r.id = u.role_id WHERE c.user_id = ? AND u.deleted = false";
    private static final String SELECT_TYPE_ID = "SELECT t.id FROM types t WHERE name = ?";
    private static final String SELECT_ALL_BY_TYPES = "SELECT c.user_id, c.first_name, c.last_name, u.email, "
            + "u.password, r.name AS role, c.birth_date, c.phone_number, c.trainer_id, t.name AS type, "
            + "c.additional_info FROM clients c JOIN types t ON c.type_id = t.id JOIN users u ON c.user_id = u.id "
            + "JOIN roles r ON r.id = u.role_id WHERE t.name = ? AND u.deleted = false ORDER BY c.user_id";

    private DataSource dataSource;

    public ClientDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Client get(Long id) {
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
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
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
        } finally {
            close(connection);
        }
        return clients;
    }

    @Override
    public List<Client> getAllClientsByType(String typeOfClient) {
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
        } finally {
            close(connection);
        }
        return clients;
    }

    @Override
    public Client create(Client client) throws InternalErrorException {
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
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public Client update(Client client) throws InternalErrorException {
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
            statement.setLong(8, client.getId());
            statement.executeUpdate();

            return get(client.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing to database using \"delete\" method, client id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return false;
    }

    private Client processClient(ResultSet result) throws SQLException {
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
        return client;
    }

    private int getTypeId(String name) throws InternalErrorException {
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
        throw new InternalErrorException("Internal Server Error");
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
