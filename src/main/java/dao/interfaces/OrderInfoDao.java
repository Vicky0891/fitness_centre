package dao.interfaces;

import java.sql.Connection;
import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.OrderInfo;

public interface OrderInfoDao {

    OrderInfo get(Long id) throws DaoException;

    List<OrderInfo> getAll() throws DaoException;

    List<OrderInfo> getAllByOrderId(Long id) throws DaoException;

    OrderInfo create(OrderInfo orderInfo, Connection connection) throws DaoException;

    OrderInfo update(OrderInfo orderInfo) throws DaoException;

    boolean delete(Long id) throws DaoException;

}
