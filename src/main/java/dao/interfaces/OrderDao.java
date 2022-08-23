package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.Order;

public interface OrderDao {

    Order get(Long id) throws DaoException;

    int getDiscount(String name) throws DaoException;

    List<Order> getAll() throws DaoException;

    List<Order> getAll(int limit, Long offset) throws DaoException;

    Order create(Order order) throws DaoException;

    Order update(Order order) throws DaoException;

    Order addFeedback(Order order) throws DaoException;

    List<Order> getAllOrdersByClient(Long id) throws DaoException;

    List<Order> getAllByStatus(String name) throws DaoException;

    boolean delete(Long id) throws DaoException;

    long count() throws DaoException;

}
