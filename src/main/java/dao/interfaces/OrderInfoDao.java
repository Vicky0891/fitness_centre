package dao.interfaces;

import java.sql.Connection;
import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.OrderInfo;

public interface OrderInfoDao extends AbstractDao<Long, OrderInfo> {

    List<OrderInfo> getAllByOrderId(Long id) throws DaoException;

    OrderInfo create(OrderInfo orderInfo, Connection connection) throws DaoException;

}
