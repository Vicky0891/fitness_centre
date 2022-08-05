package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.connection.DataSource;
import dao.entity.User;
import dao.interfaces.UserDao;
import lombok.extern.log4j.Log4j2;
import service.impl.UserServiceImpl;
@Log4j2
public class UserDaoImpl implements UserDao {

    private static final String DELETE = "UPDATE users u SET deleted = true WHERE u.id = ?";
    private static final String UPDATE = "UPDATE users SET email = ?, password = ? "
            + "WHERE id = ? AND deleted = false";
    private static final String INSERT = "INSERT INTO users (email, password) VALUES (?, ?)";
    private static final String SELECT_ALL = "SELECT u.id, u.email, u.password FROM users u WHERE u.deleted = false";
    private static final String SELECT_BY_ID = "SELECT u.id, u.email, u.password FROM users u WHERE u.id = ? "
            + "AND u.deleted = false";
    private static final String SELECT_BY_EMAIL = "SELECT u.id, u.email, u.password FROM users u WHERE u.email = ? "
            + "AND u.deleted = false";

    private DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User get(Long id) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processUser(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                users.add(processUser(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return users;
    }

    @Override
    public User create(User user) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                Long id = result.getLong("id");
                System.out.println(id);
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public User update(User user) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getId());

            statement.executeUpdate();
            return get(user.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return false;
    }
    
    @Override
    public User getUserByEmail(String email) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processUser(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    private User processUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getLong("id"));
        user.setEmail(result.getString("email"));
        user.setPassword(result.getString("password"));
        return user;
    }
}
