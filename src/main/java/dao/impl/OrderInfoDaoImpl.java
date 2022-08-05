package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.connection.DataSource;
import dao.entity.OrderInfo;
import dao.interfaces.GymMembershipDao;
import dao.interfaces.OrderInfoDao;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class OrderInfoDaoImpl implements OrderInfoDao {

    private static final String DELETE = "DELETE FROM orderinfo WHERE id = ?";
    private static final String UPDATE = "UPDATE orderinfo SET gymmembership_quantity = ?, gymmembership_price = ? WHERE id = ? AND deleted = false";
    private static final String INSERT = "INSERT INTO orderinfo (order_id, gymmembership_id, gymmembership_quantity,"
            + " gymmembership_price) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT oi.id, oi.order_id, oi.gymmembership_id, "
            + "oi.gymmembership_quantity, oi.gymmembership_price FROM orderinfo oi JOIN orders o ON o.id = oi.order_id"
            + " WHERE o.deleted = false";
    private static final String SELECT_BY_ORDER_ID = "SELECT oi.id, oi.order_id, oi.gymmembership_id, "
            + "oi.gymmembership_quantity, oi.gymmembership_price FROM orderinfo oi JOIN orders o ON o.id = oi.order_id"
            + " WHERE o.id = ? AND o.deleted = false";

    private DataSource dataSource;
    private GymMembershipDao gymMembershipDao;

    public OrderInfoDaoImpl(DataSource dataSource, GymMembershipDao gymMembershipDao) {
        this.dataSource = dataSource;
        this.gymMembershipDao = gymMembershipDao;
    }

    @Override
    public OrderInfo get(Long id) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_ORDER_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processOrderInfo(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return null;
    }
    
    @Override
    public List<OrderInfo> getAllByOrderId(Long id) {
        List<OrderInfo> orderInfos = new ArrayList<>();
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_BY_ORDER_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                orderInfos.add(processOrderInfo(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return orderInfos;
    }

    @Override
    public List<OrderInfo> getAll() {
        List<OrderInfo> orderInfos = new ArrayList<>();
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                orderInfos.add(processOrderInfo(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
        }
        return orderInfos;
    }

    @Override
    public OrderInfo create(OrderInfo orderInfo) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, orderInfo.getOrderId());
            statement.setLong(2, orderInfo.getGymMembership().getId());
            statement.setInt(4, orderInfo.getGymMembershipQuantity());
            statement.setBigDecimal(3, orderInfo.getGymMembershipPrice());
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
    public OrderInfo update(OrderInfo orderInfo) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE);
            statement.setInt(1, orderInfo.getGymMembershipQuantity());
            statement.setBigDecimal(2, orderInfo.getGymMembershipPrice());
            statement.setLong(2, orderInfo.getId());

            statement.executeUpdate();
            return get(orderInfo.getId());
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

    private OrderInfo processOrderInfo(ResultSet result) throws SQLException {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(result.getLong("id"));
        orderInfo.setOrderId(result.getLong("order_id"));
        Long gymMembershipId = result.getLong("gymmembership_id");
        orderInfo.setGymMembership(gymMembershipDao.get(gymMembershipId));
        orderInfo.setGymMembershipQuantity(result.getInt("gymmembership_quantity"));
        orderInfo.setGymMembershipPrice(result.getBigDecimal("gymmembership_price"));
        return orderInfo;
    }

}
