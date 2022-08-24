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
import dao.entity.User;
import dao.entity.User.Role;
import dao.interfaces.UserDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserDaoImpl implements UserDao {

    private static final String DELETE = "UPDATE users u SET deleted = true WHERE u.id = ?";
    private static final String UPDATE = "UPDATE users SET role_id = ? WHERE id = ? AND deleted = false";
    private static final String INSERT = "INSERT INTO users (email, password, role_id) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT u.id, u.email, u.password, r.name AS role FROM users u "
            + "JOIN roles r ON u.role_id = r.id WHERE u.deleted = false ORDER BY u.id";
    private static final String SELECT_ALL_BY_PAGED = "SELECT u.id, u.email, u.password, r.name AS role FROM users u "
            + "JOIN roles r ON u.role_id = r.id WHERE u.deleted = false ORDER BY u.id LIMIT ? OFFSET ?";
    private static final String SELECT_BY_ID = "SELECT u.id, u.email, u.password, r.name AS role FROM users u "
            + "JOIN roles r ON u.role_id = r.id WHERE u.id = ? AND u.deleted = false";
    private static final String SELECT_BY_EMAIL = "SELECT u.id, u.email, u.password, r.name AS role FROM users u "
            + "JOIN roles r ON u.role_id = r.id WHERE u.email = ? AND u.deleted = false";
    private static final String SELECT_ROLE_ID = "SELECT r.id FROM roles r WHERE name = ?";
    private static final String COUNT_ALL = "SELECT count(*) AS total FROM users u WHERE u.deleted = false";

    private DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User get(Long id) throws DaoException {
        log.debug("Accessing to database using \"get\" method, user id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processUser(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException(
                    "Something went wrong. Failed to get user id=" + id + ". Contact your system administrator.");
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public User getByEmail(String email) throws DaoException {
        log.debug("Accessing to database using \"getByEmail\" method, user email={}", email);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processUser(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException(
                    "Something went wrong. Failed to get user email=" + email + ". Contact your system administrator.");
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public List<User> getAll() throws DaoException {
        log.debug("Accessing to database using \"getAll\" method");
        List<User> users = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                users.add(processUser(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return users;
    }

    @Override
    public List<User> getAll(int limit, Long offset) throws DaoException {
        log.debug("Accessing to database using \"getAll\" method");
        List<User> users = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                users.add(processUser(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return users;
    }

    @Override
    public User create(User user) throws DaoException {
        log.debug("Accessing to database using \"create\" method, user={}", user);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setInt(3, getRoleId(user.getRole().name()));
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                Long id = result.getLong("id");
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. User wasn't create. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public User update(User user) throws DaoException {
        log.debug("Accessing to database using \"update\" method, user={}", user);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, getRoleId(user.getRole().name()));
            statement.setLong(2, user.getId());

            statement.executeUpdate();
            return get(user.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. User id=" + user.getId()
                    + " wasn't update. Contact your system administrator.");
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        log.debug("Accessing to database using \"delete\" method, user id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException(
                    "Something went wrong. User id=" + id + " wasn't delete. Contact your system administrator.");
        } finally {
            close(connection);
        }
    }

    /**
     * Method to process user by resultSet from data source
     * 
     * @param result ResultSet from data source
     * @return User
     * @throws DaoException
     */
    private User processUser(ResultSet result) throws DaoException {
        try {
            User user = new User();
            user.setId(result.getLong("id"));
            user.setEmail(result.getString("email"));
            user.setPassword(result.getString("password"));
            user.setRole(Role.valueOf(result.getString("role")));
            return user;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        }
    }

    /**
     * Method to get id of user role in data source table
     * 
     * @param name Name of role
     * @return id of role
     * @throws DaoException
     */
    private int getRoleId(String name) throws DaoException {
        log.debug("Accessing to database using \"getRoleId\" method, role name={}", name);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_ID);
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
        log.error("Role of user with name={} didn't find", name);
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
        log.error("Couldn't count users");
        throw new DaoException("Something went wrong. Contact your system administrator.");
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
