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
    private static final String SELECT_BY_ID = "SELECT oi.id, oi.order_id, oi.gymmembership_id, "
            + "oi.gymmembership_quantity, oi.gymmembership_price FROM orderinfo oi " + " WHERE oi.id = ?";
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
    public OrderInfo get(Long id) throws DaoException {
        log.debug("Accessing to database using \"get\" method, order id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processOrderInfo(result);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Failed to get orderdetail order id=" + id
                    + ". Contact your system administrator.");
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public List<OrderInfo> getAllByOrderId(Long id) throws DaoException {
        log.debug("Accessing to database using \"getAllByOrderId\" method, order id={}", id);
        List<OrderInfo> orderInfo = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ORDER_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                orderInfo.add(processOrderInfo(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return orderInfo;
    }

    @Override
    public List<OrderInfo> getAll() throws DaoException {
        log.debug("Accessing to database using \"getAll\" method");
        List<OrderInfo> orderInfos = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL);
            while (result.next()) {
                orderInfos.add(processOrderInfo(result));
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
        } finally {
            close(connection);
        }
        return orderInfos;
    }

    @Override
    public OrderInfo create(OrderInfo orderInfo, Connection connection) throws DaoException {
        log.debug("Accessing to database using \"create\" method, orderInfo={}", orderInfo);
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, orderInfo.getOrderId());
            statement.setLong(2, orderInfo.getGymMembership().getId());
            statement.setInt(3, orderInfo.getGymMembershipQuantity());
            statement.setBigDecimal(4, orderInfo.getGymMembershipPrice());
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                Long id = result.getLong("id");
                return get(id);
            }
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Order wasn't create. Contact your system administrator.");
        }
        return null;
    }

    @Override
    public OrderInfo update(OrderInfo orderInfo) throws DaoException {
        log.debug("Accessing to database using \"update\" method, orderInfo={}", orderInfo);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, orderInfo.getGymMembershipQuantity());
            statement.setBigDecimal(2, orderInfo.getGymMembershipPrice());
            statement.setLong(2, orderInfo.getId());

            statement.executeUpdate();
            return get(orderInfo.getId());
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Order wasn't update. Contact your system administrator.");
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        log.debug("Accessing to database using \"delete\" method, id={}", id);
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Order wasn't delete. Contact your system administrator.");
        } finally {
            close(connection);
        }
    }

    private OrderInfo processOrderInfo(ResultSet result) throws DaoException {
        try {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setId(result.getLong("id"));
            orderInfo.setOrderId(result.getLong("order_id"));
            Long gymMembershipId = result.getLong("gymmembership_id");
            orderInfo.setGymMembership(gymMembershipDao.get(gymMembershipId));
            orderInfo.setGymMembershipQuantity(result.getInt("gymmembership_quantity"));
            orderInfo.setGymMembershipPrice(result.getBigDecimal("gymmembership_price"));
            return orderInfo;
        } catch (SQLException e) {
            log.error("SQL Exception: " + e);
            throw new DaoException("Something went wrong. Contact your system administrator.");
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
