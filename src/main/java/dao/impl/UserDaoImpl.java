package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.connection.DataSource;
import dao.entity.User;
import dao.entity.User.Role;
import dao.entity.User.Type;
import dao.interfaces.UserDao;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class UserDaoImpl implements UserDao {

    private static final String DELETE = "UPDATE users u SET deleted = true WHERE u.id = ?";
    private static final String UPDATE = "UPDATE users SET email = ?, password = ?, first_name = ?, last_name = ?, type_id = ?, role_id = ?, trainer_id = ?"
            + "WHERE id = ? AND deleted = false";
    private static final String INSERT = "INSERT INTO users (email, password, first_name, last_name, type_id, role_id, trainer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT u.id, u.email, u.password, u.first_name, u.last_name, t.name "
            + "AS type, r.name AS role, u.trainer_id FROM users u JOIN types t ON u.type_id = t.id JOIN roles r "
            + "ON u.role_id = r.id WHERE u.deleted = false";
    private static final String SELECT_ALL_CLIENTS = "SELECT u.id, u.email, u.password, u.first_name, u.last_name, t.name "
            + "AS type, r.name AS role, u.trainer_id FROM users u JOIN types t ON u.type_id = t.id "
            + "JOIN roles r ON u.role_id = r.id WHERE r.name = 'CLIENT' AND u.deleted = false";
    private static final String SELECT_CLIENTS_BY_TYPE = "SELECT u.id, u.email, u.password, u.first_name, u.last_name, t.name "
            + "AS type, r.name AS role, u.trainer_id FROM users u JOIN types t ON u.type_id = t.id "
            + "JOIN roles r ON u.role_id = r.id WHERE t.name = ? AND u.deleted = false";
    private static final String SELECT_CLIENTS_BY_TRAINER = "SELECT u.id, u.email, u.password, u.first_name, u.last_name, t.name "
            + "AS type, r.name AS role, u.trainer_id FROM users u JOIN types t ON u.type_id = t.id "
            + "JOIN roles r ON u.role_id = r.id WHERE u.trainer_id = ? AND u.deleted = false";
    private static final String SELECT_ALL_TRAINERS = "SELECT u.id, u.email, u.password, u.first_name, u.last_name, t.name "
            + "AS type, r.name AS role, u.trainer_id FROM users u JOIN types t ON u.type_id = t.id "
            + "JOIN roles r ON u.role_id = r.id WHERE r.name = 'TRAINER' AND u.deleted = false";
    private static final String SELECT_BY_ID = "SELECT u.id, u.email, u.password, u.first_name, u.last_name, t.name "
            + "AS type, r.name AS role, u.trainer_id FROM users u JOIN types t ON u.type_id = t.id JOIN roles r "
            + "ON u.role_id = r.id WHERE u.id = ? AND u.deleted = false";
    private static final String SELECT_BY_EMAIL = "SELECT u.id, u.email, u.password, u.first_name, u.last_name, t.name AS type, r.name AS role, u.trainer_id FROM users u JOIN types t ON u.type_id = t.id JOIN roles r ON u.role_id = r.id WHERE u.email = ? "
            + "AND u.deleted = false";
    private static final String SELECT_TYPE_ID = "SELECT t.id FROM types t WHERE name = ?";
    private static final String SELECT_ROLE_ID = "SELECT r.id FROM roles r WHERE name = ?";
    private static final Long DEFAULT = (long) -1;

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
    public User getByEmail(String email) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_EMAIL)) {
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
    public List<User> getAllClients() {
        List<User> clients = new ArrayList<>();
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL_CLIENTS);
            while (result.next()) {
                clients.add(processUser(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return clients;
    }

    @Override
    public List<User> getAllTrainers() {
        List<User> trainers = new ArrayList<>();
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL_TRAINERS);
            while (result.next()) {
                trainers.add(processUser(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return trainers;
    }

    @Override
    public List<User> getAllClientsByTrainer(Long trainerId) {
        List<User> trainers = new ArrayList<>();
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_CLIENTS_BY_TRAINER)) {
            statement.setLong(1, trainerId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                trainers.add(processUser(result));
        }
            } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return trainers;
    }

    @Override
    public List<User> getAllClientsByType(String typeOfClient) {
        List<User> clients = new ArrayList<>();
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_CLIENTS_BY_TYPE)) {
            statement.setString(1, typeOfClient);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                clients.add(processUser(result));
        }
            } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return clients;
    }

    @Override
    public User create(User user) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, getTypeId(user.getType().name()));
            statement.setInt(6, getRoleId(user.getRole().name()));
            statement.setLong(7, DEFAULT);
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
    public User update(User user) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, getTypeId(user.getType().name()));
            statement.setInt(6, getRoleId(user.getRole().name()));
            statement.setLong(7, user.getTrainerId());
            statement.setLong(8, user.getId());

            statement.executeUpdate();
            return get(user.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(DELETE)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return false;
    }
    

    private User processUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getLong("id"));
        user.setEmail(result.getString("email"));
        user.setPassword(result.getString("password"));
        user.setFirstName(result.getString("first_name"));
        user.setLastName(result.getString("last_name"));
        user.setType(Type.valueOf(result.getString("type")));
        user.setRole(Role.valueOf(result.getString("role")));
        user.setTrainerId(result.getLong("trainer_id"));
        return user;
    }

    private int getTypeId(String name) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_TYPE_ID)) {
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        log.error("Unable to establish connection or error in id");
        throw new RuntimeException();
    }
    
    private int getRoleId(String name) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_ROLE_ID)) {
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        log.error("Unable to establish connection or error in id");
        throw new RuntimeException();
    }

}
