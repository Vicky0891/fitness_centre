package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.connection.DataSource;
import dao.entity.Customer;
import dao.entity.OrderInfo;
import dao.entity.Customer.Type;
import dao.entity.Order;
import dao.interfaces.CustomerDao;
import dao.interfaces.OrderDao;
import dao.interfaces.TrainerDao;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class CustomerDaoImpl implements CustomerDao {
    
    private static final String DELETE = "UPDATE customers c SET deleted = true WHERE c.id = ?";
    private static final String UPDATE = "UPDATE customers SET first_name = ?, last_name = ?, password = ?, "
            + "phone_number = ? WHERE id = ? AND deleted = false";
    private static final String UPDATE_BY_ADMIN = "UPDATE customers SET first_name = ?, last_name = ?, password = ?, "
            + "phone_number = ?, trainer_id = ?, type_id = ? WHERE id = ? AND deleted = false";
    private static final String INSERT = "INSERT INTO customers (first_name, last_name, email, password, birth_date, "
            + "phone_number, trainer_id, type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT c.id, c.first_name, c.last_name, c.email, c.password, "
            + "c.birth_date, c.phone_number, c.trainer_id, t.name AS type, tr.first_name, tr.last_name FROM customers c"
            + " JOIN types t ON c.type_id = t.id JOIN trainers tr ON c.trainer_id = tr.id WHERE c.deleted = false";
    private static final String SELECT_BY_ID = "SELECT c.id, c.first_name, c.last_name, c.email, c.password, "
            + "c.birth_date, c.phone_number, c.trainer_id, t.name AS type, tr.first_name, tr.last_name FROM customers c"
            + " JOIN types t ON c.type_id = t.id JOIN trainers tr ON c.trainer_id = tr.id WHERE c.id = ? AND "
            + "c.deleted = false";
    private static final String SELECT_BY_EMAIL = "SELECT c.id, c.first_name, c.last_name, c.email, c.password, "
            + "c.birth_date, c.phone_number, c.trainer_id, t.name AS type, tr.first_name, tr.last_name FROM customers c"
            + " JOIN types t ON c.type_id = t.id JOIN trainers tr ON c.trainer_id = tr.id WHERE c.email = ? "
            + "AND c.deleted = false";
    private static final String SELECT_TYPE_ID = "SELECT t.id FROM types t WHERE name = ?";
    private static final String SELECT_TRAINER_ID = "SELECT tr.id FROM trainers tr WHERE last_name = ?";
    private static final String SELECT_ALL_BY_TYPES = "SELECT c.id, c.first_name, c.last_name, c.email, c.password,"
            + "c.birth_date, c.phone_number, c.trainer_id, t.name AS type FROM customers c JOIN types t "
            + "ON c.type_id = t.id WHERE t.name = ? AND c.deleted = false";

    private DataSource dataSource;
    private TrainerDao trainerDao;
    private OrderDao orderDao;

    public CustomerDaoImpl(DataSource dataSource, TrainerDao trainerDao, OrderDao orderDao) {
        this.dataSource = dataSource;
        this.trainerDao = trainerDao;
        this.orderDao = orderDao;
    }

    @Override
    public Customer get(Long id) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processCustomer(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        try (
            Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                customers.add(processCustomer(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return customers;
    }

    @Override
    public Customer create(Customer customer) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.setDate(5, Date.valueOf(customer.getBirthDate()));            
            statement.setString(6, customer.getPhoneNumber());
            statement.setInt(7, getTrainerId(customer.getTrainer().getLastName()));
            statement.setInt(8, getTypeId(customer.getType().name()));
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
    public Customer update(Customer customer) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPassword());
            statement.setString(4,customer.getPhoneNumber());
            statement.setLong(5, customer.getId());

            statement.executeUpdate();
            return get(customer.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }
    
    @Override
    public Customer updateByAdmin(Customer customer) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE_BY_ADMIN);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPassword());
            statement.setString(4,customer.getPhoneNumber());
            statement.setInt(5, getTrainerId(customer.getTrainer().getLastName()));
            statement.setInt(6, getTypeId(customer.getType().name()));
            statement.setLong(7, customer.getId());

            statement.executeUpdate();
            return get(customer.getId());
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
    public Customer getCustomerByEmail(String email) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processCustomer(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }
    


    @Override
    public List<Customer> getAllByType(String name) {
        List<Customer> customers = new ArrayList<>();
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_ALL_BY_TYPES);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                customers.add(processCustomer(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return customers;
    }

    private Customer processCustomer(ResultSet result) throws SQLException {
        Customer customer = new Customer();
        customer.setId(result.getLong("id"));
        customer.setFirstName(result.getString("first_name"));
        customer.setLastName(result.getString("last_name"));
        customer.setEmail(result.getString("email"));
        customer.setPassword(result.getString("password"));
        customer.setBirthDate(result.getDate("birth_date").toLocalDate());
        customer.setPhoneNumber(result.getString("phone_number"));
//        Long trainerId = result.getLong("trainer_id");
//        customer.setTrainer(trainerDao.get(trainerId));
        customer.setType(Type.valueOf(result.getString("type")));
//        List<Order> orders = orderDao.getAllOrdersByCustomer(result.getLong("id"));
//        customer.setListOfOrders(orders);
        return customer;
    }
    
    private int getTrainerId(String lastName) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_TRAINER_ID);
            statement.setString(1, lastName);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        log.info("Customer don't have trainer. Default value -1");
        return -1;
    }
    
    private int getTypeId(String name) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_TYPE_ID);
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
