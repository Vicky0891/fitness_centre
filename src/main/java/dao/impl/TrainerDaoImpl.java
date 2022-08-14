package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.connection.DataSource;
import dao.entity.Client;
import dao.entity.Trainer;
import dao.entity.User.Role;
import dao.interfaces.ClientDao;
import dao.interfaces.TrainerDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TrainerDaoImpl implements TrainerDao{
    private static final String DELETE = "UPDATE users SET deleted = true WHERE id = ?";
    private static final String UPDATE = "UPDATE trainers SET first_name = ?, last_name = ?, birth_date = ?, "
            + "category = ? WHERE user_id = ?";
    private static final String INSERT = "INSERT INTO trainers (user_id, first_name, last_name, birth_date, "
            + "category) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT tr.user_id, tr.first_name, tr.last_name, u.email, u.password, r.name AS role, "
            + "tr.birth_date, tr.category FROM trainers tr JOIN users u ON u.id = tr.user_id JOIN roles r ON r.id = u.role_id WHERE u.deleted = false";
    private static final String SELECT_BY_ID = "SELECT tr.user_id, tr.first_name, tr.last_name, u.email, u.password, r.name AS role, "
            + "tr.birth_date, tr.category FROM trainers tr JOIN users u ON tr.user_id = u.id JOIN roles r ON r.id = u.role_id WHERE tr.user_id = ? AND u.deleted = false";
    private static final String SELECT_CLIENTS_FOR_TRAINER = "SELECT c.user_id, c.first_name, c.last_name, u.email, u.password, r.name AS role, c.birth_date, c.phone_number, "
            + "c.trainer_id, t.name AS type, c.additional_info FROM clients c JOIN types t ON t.id = c.type_id "
            + "JOIN trainers tr ON c.trainer_id = tr.user_id JOIN users u ON u.id = c.user_id JOIN roles r ON r.id = u.role_id WHERE tr.user_id = ? AND u.deleted = false";

    private DataSource dataSource;
    private ClientDao clientDao;

    public TrainerDaoImpl(DataSource dataSource, ClientDao clientDao) {
        this.dataSource = dataSource;
        this.clientDao = clientDao;
    }

    @Override
    public Trainer get(Long id) {
        Connection connection = dataSource.getConnection();
        try {
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                connection.commit();
                return processTrainer(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public List<Trainer> getAll() {
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
        } finally {
            close(connection);
        }
        return trainers;
    }

    @Override
    public Trainer create(Trainer trainer) {
        Connection connection = dataSource.getConnection();
        try {
                PreparedStatement statement = connection.prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, trainer.getId());
            statement.setString(2, trainer.getFirstName());
            statement.setString(3, trainer.getLastName());
            statement.setDate(4, Date.valueOf(trainer.getBirthDate()));
            statement.setString(5, trainer.getCategory());
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
    public Trainer update(Trainer trainer) {
        Connection connection = dataSource.getConnection();
        try {
                PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, trainer.getFirstName());
            statement.setString(2, trainer.getLastName());
            statement.setDate(3, Date.valueOf(trainer.getBirthDate()));
            statement.setString(4, trainer.getCategory());
            statement.setLong(5, trainer.getId());

            statement.executeUpdate();
            return get(trainer.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
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

    @Override
    public List<Client> getAllClientsByTrainer(Long id) {
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
        } finally {
            close(connection);
        }
        return clients;
    }

    private Trainer processTrainer(ResultSet result) throws SQLException {
        Trainer trainer = new Trainer();
        trainer.setId(result.getLong("user_id"));
        trainer.setEmail(result.getString("email"));
        trainer.setPassword(result.getString("password"));
        
        trainer.setRole(Role.valueOf(result.getString("role")));
        
        
        trainer.setFirstName(result.getString("first_name"));
        trainer.setLastName(result.getString("last_name"));
        trainer.setBirthDate(result.getDate("birth_date").toLocalDate());
        trainer.setCategory(result.getString("category"));
        List<Client> clients = getAllClientsByTrainer(result.getLong("user_id"));
        trainer.setClients(clients);
        return trainer;
    }

    private void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
        }
    }


}
