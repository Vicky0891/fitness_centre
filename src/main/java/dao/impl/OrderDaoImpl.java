package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            + "s.name AS status FROM orders o JOIN status s ON s.id = o.status_id WHERE o.deleted = false";
    private static final String SELECT_BY_ID = "SELECT o.id, o.date_of_order, o.user_id, u.first_name, u.last_name, o.total_cost, o.feedback, "
            + "s.name AS status FROM orders o JOIN status s ON s.id = o.status_id JOIN users u "
            + "ON u.id = o.user_id WHERE o.id = ? AND o.deleted = false";
    private static final String SELECT_ALL_BY_CLIENT = "SELECT o.id, o.date_of_order, o.user_id, o.feedback, o.total_cost, s.name AS status "
            + "FROM orders o JOIN status s ON s.id = o.status_id JOIN users u "
            + "ON u.id = o.user_id WHERE o.user_id = ? AND o.deleted = false";
    private static final String SELECT_STATUS_ID = "SELECT s.id FROM status s WHERE name = ?";
    private static final String UPDATE_FEEDBACK = "UPDATE orders SET feedback = ? WHERE id = ? AND deleted = false";
    private static final String SELECT_ALL_BY_STATUS = "SELECT o.id, o.date_of_order, o.user_id, o.feedback, "
            + "o.total_cost, s.name AS status FROM orders o JOIN status s ON s.id = o.status_id "
            + "WHERE s.name = ? AND o.deleted = false";

    private DataSource dataSource;
    private OrderInfoDao orderInfoDao;

    public OrderDaoImpl(DataSource dataSource, OrderInfoDao orderInfoDao) {
        this.dataSource = dataSource;
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
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(order.getDateOfOrder()));
            statement.setLong(2, order.getUserId());
            statement.setBigDecimal(3, order.getTotalCost());
            statement.setInt(4, getStatusId(order.getStatus().name()));
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            
            if (result.next()) {
                Long id = result.getLong("id");
//                List<OrderInfo> orderDetails = new ArrayList<>();
//                
//                
//                orderDetails = order.getDetails();
//                for(OrderInfo detail: orderDetails){
//                    System.out.println(detail);
//                }
//                
//           
//                List<OrderInfo> detailsNew = new ArrayList<>();
//                for(OrderInfo detail: orderDetails) {
//                    detail.setOrderId(id);
//                    
//                    System.out.println(detail.toString());
//                    
//                    OrderInfo newOI = orderInfoDao.create(detail);
//                    
//                    
//                    detailsNew.add(newOI);
//                }
                
//                for(OrderInfo detail: details) {
//                    detail.setOrderId(id);
//                    details.add(orderInfoDao.create(detail));
//                }
//                Order orderFinish = get(id);
                
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }

    @Override
    public Order update(Order order) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE)) {
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
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE_FEEDBACK)) {
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
    public List<Order> getAllOrdersByClient(Long id) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_ALL_BY_CLIENT)) {
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
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_ALL_BY_STATUS)) {
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
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(DELETE)) {
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
        order.setUserId(result.getLong("user_id"));
        order.setTotalCost(result.getBigDecimal("total_cost"));
        order.setStatus(Status.valueOf(result.getString("status")));
        order.setFeedback(result.getString("feedback"));
        List<OrderInfo> details = orderInfoDao.getAllByOrderId(order.getId());
        order.setDetails(details);

        return order;
    }

    private int getStatusId(String name) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_STATUS_ID)) {
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
