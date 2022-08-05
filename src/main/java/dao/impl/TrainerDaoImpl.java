package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.connection.DataSource;
import dao.entity.Customer;
import dao.entity.OrderInfo;
import dao.entity.Trainer;
import dao.interfaces.CustomerDao;
import dao.interfaces.TrainerDao;
import lombok.extern.log4j.Log4j2;
import service.impl.UserServiceImpl;
@Log4j2
public class TrainerDaoImpl implements TrainerDao {

    private static final String DELETE = "UPDATE trainers SET deleted = true WHERE id = ?";
    private static final String UPDATE = "UPDATE trainers SET first_name = ?, last_name = ?, password = ?, "
            + "phone_number = ? WHERE id = ? AND deleted = false";
    private static final String INSERT = "INSERT INTO trainers (first_name, last_name, email, password, birth_date, "
            + "phone_number) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT tr.id, tr.first_name, tr.last_name, tr.email, tr.password, "
            + "tr.birth_date, tr.phone_number FROM trainers tr WHERE tr.deleted = false";
    private static final String SELECT_BY_ID = "SELECT tr.id, tr.first_name, tr.last_name, tr.email, tr.password, "
            + "tr.birth_date, tr.phone_number FROM trainers tr WHERE tr.id = ? AND tr.deleted = false";
    private static final String SELECT_ALL_FOR_TRAINER = "SELECT c.id, c.first_name, c.last_name, c.email, c.password,"
            + " c.birth_date, c.phone_number FROM customers c JOIN trainers tr ON c.trainer_id = tr.id WHERE tr.id = ? "
            + "AND c.deleted = false";
    private static final String SELECT_BY_EMAIL = "SELECT tr.id, tr.first_name, tr.last_name, tr.email, tr.password, "
            + "tr.birth_date, tr.phone_number FROM trainers tr WHERE tr.email = ? AND tr.deleted = false";

    private DataSource dataSource;
    private CustomerDao customerDao;

    public TrainerDaoImpl(DataSource dataSource, CustomerDao customerDao) {
        this.dataSource = dataSource;
        this.customerDao = customerDao;
    }

    @Override
    public Trainer get(Long id) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processTrainer(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public List<Trainer> getAll() {
        List<Trainer> trainers = new ArrayList<>();
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                trainers.add(processTrainer(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return trainers;
    }

    @Override
    public Trainer create(Trainer trainer) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, trainer.getFirstName());
            statement.setString(2, trainer.getLastName());
            statement.setString(3, trainer.getEmail());
            statement.setString(4, trainer.getPassword());
            statement.setDate(5, Date.valueOf(trainer.getBirthDate()));
            statement.setString(6, trainer.getPhoneNumber());
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
    public Trainer update(Trainer trainer) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE);
            statement.setString(1, trainer.getFirstName());
            statement.setString(2, trainer.getLastName());
            statement.setString(3, trainer.getPassword());
            statement.setString(4, trainer.getPhoneNumber());
            statement.setLong(5, trainer.getId());

            statement.executeUpdate();
            return get(trainer.getId());
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
    public List<Customer> getCustomersForTrainer(Long id) {
        List<Customer> customers = new ArrayList<>();

        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_ALL_FOR_TRAINER);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                customers.add(customerDao.get(result.getLong("id")));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return customers;
    }
    @Override
    public Trainer getTrainerByEmail(String email) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processTrainer(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    private Trainer processTrainer(ResultSet result) throws SQLException {
        Trainer trainer = new Trainer();
        trainer.setId(result.getLong("id"));
        trainer.setFirstName(result.getString("first_name"));
        trainer.setLastName(result.getString("last_name"));
        trainer.setEmail(result.getString("email"));
        trainer.setPassword(result.getString("password"));
        trainer.setBirthDate(result.getDate("birth_date").toLocalDate());
        trainer.setPhoneNumber(result.getString("phone_number"));
//        List<Customer> listOFCustomers = getCustomersForTrainer(result.getLong("id"));
//        trainer.setListOfCustomers(listOFCustomers);
        return trainer;
    }


}
