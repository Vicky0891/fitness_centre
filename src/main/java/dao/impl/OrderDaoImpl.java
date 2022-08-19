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
import dao.entity.Order;
import dao.entity.Order.Status;
import dao.entity.OrderInfo;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderInfoDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrderDaoImpl implements OrderDao {

    private static final String DELETE = "UPDATE orders SET deleted = true WHERE id = ?";
    private static final String UPDATE = "UPDATE orders SET status_id = ? WHERE id = ? AND deleted = false";
    private static final String INSERT = "INSERT INTO orders (date_of_order, user_id, total_cost, status_id) "
            + "VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT o.id, o.date_of_order, o.user_id, o.total_cost, o.feedback, "
            + "s.name AS status FROM orders o JOIN status s ON s.id = o.status_id WHERE o.deleted = false "
            + "ORDER BY o.id";
    private static final String SELECT_BY_ID = "SELECT o.id, o.date_of_order, o.user_id, o.total_cost, o.feedback, "
            + "s.name AS status FROM orders o JOIN status s ON s.id = o.status_id JOIN users u "
            + "ON u.id = o.user_id WHERE o.id = ? AND o.deleted = false";
    private static final String SELECT_ALL_BY_CLIENT = "SELECT o.id, o.date_of_order, o.user_id, o.feedback, "
            + "o.total_cost, s.name AS status FROM orders o JOIN status s ON s.id = o.status_id JOIN users u "
            + "ON u.id = o.user_id WHERE o.user_id = ? AND o.deleted = false ORDER BY o.id";
    private static final String SELECT_STATUS_ID = "SELECT s.id FROM status s WHERE name = ?";
    private static final String UPDATE_FEEDBACK = "UPDATE orders SET feedback = ? WHERE id = ? AND deleted = false";
    private static final String SELECT_ALL_BY_STATUS = "SELECT o.id, o.date_of_order, o.user_id, o.feedback, "
            + "o.total_cost, s.name AS status FROM orders o JOIN status s ON s.id = o.status_id "
            + "WHERE s.name = ? AND o.deleted = false ORDER BY o.id";
    private static final String SELECT_DISCOUNT = "SELECT t.discount FROM types t WHERE name = ?";

    private DataSource dataSource;
    private OrderInfoDao orderInfoDao;

    public OrderDaoImpl(DataSource dataSource, OrderInfoDao orderInfoDao) {
        this.dataSource = dataSource;
        this.orderInfoDao = orderInfoDao;
    }

    @Override
    public Order get(Long id) {
        log.debug("Accessing to database using \"get\" method, order id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {

                return processOrder(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        log.debug("Accessing to database using \"getAll\" method");
        List<Order> orders = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                orders.add(processOrder(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return orders;
    }

    @Override
    public Order create(Order order) throws InternalErrorException {
        log.debug("Accessing to database using \"create\" method, order={}", order);
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(order.getDateOfOrder()));
            statement.setLong(2, order.getUserId());
            statement.setBigDecimal(3, order.getTotalCost());
            statement.setInt(4, getStatusId(order.getStatus().name()));
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();

            if (result.next()) {
                Long id = result.getLong("id");
                for (OrderInfo detail : order.getDetails()) {
                    detail.setOrderId(id);
                    orderInfoDao.create(detail, connection);
                }
                connection.commit();
                return get(id);
            }
        } catch (SQLException e) {
            rollback(connection);
            log.error("SQL Exception: " + e + ". Rollback made");
        } finally {
            restore(connection);
        }
        return null;
    }

    @Override
    public Order update(Order order) throws InternalErrorException {
        log.debug("Accessing to database using \"update\" method, order={}", order);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, getStatusId(order.getStatus().name()));
            statement.setLong(2, order.getId());

            statement.executeUpdate();
            return get(order.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public Order addFeedback(Order order) {
        log.debug("Accessing to database using \"addFeedback\" method, order={}", order);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_FEEDBACK);
            statement.setString(1, order.getFeedback());
            statement.setLong(2, order.getId());

            statement.executeUpdate();
            return get(order.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public List<Order> getAllOrdersByClient(Long id) {
        log.debug("Accessing to database using \"getAllOrdersByClient\" method, id={}", id);
        List<Order> orders = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_CLIENT);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                orders.add(processOrder(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return orders;

    }

    @Override
    public List<Order> getAllByStatus(String statusName) {
        log.debug("Accessing to database using \"getAllByStatus\" method, Status name={}", statusName);
        List<Order> orders = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_STATUS);
            statement.setString(1, statusName);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                orders.add(processOrder(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        return orders;
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Accessing to database using \"delete\" method, id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            return false;
        } finally {
            close(connection);
        }
    }

    private Order processOrder(ResultSet result) throws SQLException {
        Order order = new Order();
        order.setId(result.getLong("id"));
        order.setDateOfOrder(result.getDate("date_of_order").toLocalDate());
        order.setUserId(result.getLong("user_id"));
        order.setTotalCost(result.getBigDecimal("total_cost"));
        order.setStatus(Status.valueOf(result.getString("status")));
        order.setFeedback(result.getString("feedback"));
        List<OrderInfo> details = orderInfoDao.getAllByOrderId(result.getLong("id"));
        order.setDetails(details);
        return order;
    }

    public int getStatusId(String name) throws InternalErrorException {
        log.debug("Accessing to database using \"getStatusId\" method, name={}", name);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_STATUS_ID);
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
        log.error("Status id for order with name={} didn't find", name);
        throw new InternalErrorException("Internal Server Error");
    }

    @Override
    public int getDiscount(String name) throws InternalErrorException {
        log.debug("Accessing to database using \"getDiscount\" method, name={}", name);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_DISCOUNT);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("discount");
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        } finally {
            close(connection);
        }
        log.error("Discount id with name={} didn't find", name);
        throw new InternalErrorException("Internal Server Error");
    }

    private void restore(Connection connection) {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            log.error(e.getMessage() + e);
        }
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
