package dao.interfaces;

import java.sql.Connection;
import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.Order;

public interface OrderDao extends AbstractDao<Long, Order> {

    /**
     * Method to get discount for client by particular type
     * 
     * @param name name of type of client
     * @return int amount of discount
     * @throws DaoException
     */
    int getDiscount(String name);

    /**
     * Method get list of objects starting from begin position in the table
     * 
     * @param limit  number of records in the table
     * @param offset starting position for search in the table
     * @return list of Orders
     * @throws DaoException
     */
    List<Order> getAll(int limit, Long offset);

    /**
     * Method to update feedback in the order
     * 
     * @param order Order to update
     * @return updated order
     * @throws DaoException
     */
    Order addFeedback(Order order);

    /**
     * Method to get orders by client
     * 
     * @param id Client id to get orders
     * @return list of orders
     * @throws DaoException
     */
    List<Order> getAllOrdersByClient(Long id);

    /**
     * Method to get all orders particular status
     * 
     * @param name name of status
     * @return list of orders
     * @throws DaoException
     */
    List<Order> getAllByStatus(String name);

    /**
     * Method count all orders
     * 
     * @return quantity of orders
     * @throws DaoException
     */
    long count();

    /**
     * Method find Order in the data source by id
     * 
     * @param id         Order id to be get
     * @param connection Connection to be used
     * @return Order
     * @throws DaoException
     */
    Order get(Long id, Connection connection);

}
