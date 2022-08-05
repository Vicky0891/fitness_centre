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
import dao.entity.Order;
import dao.entity.Order.Status;
import dao.entity.OrderInfo;
import dao.interfaces.CustomerDao;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderInfoDao;
import lombok.extern.log4j.Log4j2;
import service.impl.UserServiceImpl;
@Log4j2
public class OrderDaoImpl implements OrderDao {

    private static final String DELETE = "UPDATE orders SET deleted = true WHERE id = ?";
    private static final String UPDATE = "UPDATE orders SET status_order_id = ? WHERE id = ? AND deleted = false";
    private static final String INSERT = "INSERT INTO orders (date_of_order, customer_id, total_cost, status_order_id) "
            + "VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT o.id, o.date_of_order, o.customer_id, c.first_name, c.last_name, o.total_cost, o.feedback,  "
            + "s.name AS status FROM orders o JOIN status s ON s.id = o.status_order_id JOIN customers c "
            + "ON c.id = o.customer_id WHERE o.deleted = false";
    private static final String SELECT_BY_ID = "SELECT o.id, o.date_of_order, o.customer_id, c.first_name, c.last_name, o.total_cost, o.feedback, "
            + "s.name AS status FROM orders o JOIN status s ON s.id = o.status_order_id JOIN customers c "
            + "ON c.id = o.customer_id WHERE o.id = ? AND o.deleted = false";
    private static final String SELECT_ALL_BY_CUSTOMER = "SELECT o.id, o.date_of_order, o.customer_id, c.first_name, c.last_name, o.feedback, "
            + "o.total_cost, s.name AS status FROM orders o JOIN status s ON s.id = o.status_order_id JOIN customers c "
            + "ON c.id = o.customer_id WHERE o.customer_id = ? AND o.deleted = false";
    private static final String SELECT_STATUS_ID = "SELECT s.id FROM status s WHERE name = ?";
    private static final String UPDATE_FEEDBACK = "UPDATE orders SET feedback = ? WHERE id = ? AND deleted = false";
    private static final String SELECT_ALL_BY_STATUS = "SELECT o.id, o.date_of_order, o.customer_id, c.first_name, c.last_name, o.feedback, "
            + "o.total_cost, s.name AS status FROM orders o JOIN status s ON s.id = o.status_order_id JOIN customers c "
            + "ON c.id = o.customer_id WHERE s.name = ? AND o.deleted = false";

    private DataSource dataSource;
    private CustomerDao customerDao;
    private OrderInfoDao orderInfoDao;

    public OrderDaoImpl(DataSource dataSource, CustomerDao customerDao, OrderInfoDao orderInfoDao) {
        this.dataSource = dataSource;
        this.customerDao = customerDao;
        this.orderInfoDao = orderInfoDao;
    }

    @Override
    public Order get(Long id) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processOrder(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                orders.add(processOrder(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return orders;
    }

    @Override
    public Order create(Order order) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(order.getDateOfOrder()));
            statement.setLong(2, order.getCustomer().getId());
            statement.setBigDecimal(3, order.getTotalCost());
            statement.setInt(4, getStatusId(order.getStatus().name()));
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
    public Order update(Order order) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE);
            statement.setInt(1, getStatusId(order.getStatus().name()));
            statement.setLong(2, order.getId());

            statement.executeUpdate();
            return get(order.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public Order addFeedback(Order order) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE_FEEDBACK);
            statement.setString(1, order.getFeedback());
            statement.setLong(2, order.getId());

            statement.executeUpdate();
            return get(order.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public List<Order> getAllOrdersByCustomer(Long id) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_ALL_BY_CUSTOMER);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                orders.add(processOrder(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return orders;

    }

    @Override
    public List<Order> getAllByStatus(String statusName) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_ALL_BY_STATUS);
            statement.setString(1, statusName);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                orders.add(processOrder(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return orders;
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

    private Order processOrder(ResultSet result) throws SQLException {
        Order order = new Order();
        order.setId(result.getLong("id"));
        order.setDateOfOrder(result.getDate("date_of_order").toLocalDate());
        Long customerId = result.getLong("customer_id");
        order.setCustomer(customerDao.get(customerId));
        order.setStatus(Status.valueOf(result.getString("status")));
        order.setTotalCost(result.getBigDecimal("total_cost"));
        order.setFeedback(result.getString("feedback"));
        List<OrderInfo> details = orderInfoDao.getAllByOrderId(order.getId());
        order.setDetails(details);

        return order;
    }

    private int getStatusId(String name) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_STATUS_ID);
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
