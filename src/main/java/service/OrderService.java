package service;

import java.util.List;
import java.util.Map;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.DaoException;
import service.dto.OrderDto;
import service.dto.UserDto;

public interface OrderService extends AbstractService<Long, OrderDto> {

    /**
     * Method to create OrderDto based on cart in session for user
     * 
     * @param cart    Cart with items
     * @param userDto User who makes order
     * @return OrderDto object
     * @throws Exception
     */
    OrderDto processCart(Map<Long, Integer> cart, UserDto userDto) throws Exception;

    /**
     * Method to update feedback in the order
     * 
     * @param orderDto OrderDto for update
     * @return OrderDto with updated feedback
     * @throws DaoException
     */
    OrderDto addFeedback(OrderDto orderDto) throws DaoException;

    /**
     * Method get list of all orders for particular client
     * 
     * @param id Client id to get order
     * @return list of OrderDto
     * @throws DaoException
     */
    List<OrderDto> getAllOrdersDtoByClient(Long id) throws DaoException;

    /**
     * Method get list of orderDto particular status
     * 
     * @param statusName Name of status to get
     * @return list of OrderDto
     * @throws DaoException
     */
    List<OrderDto> getAllByStatus(String statusName) throws DaoException;

    /**
     * Method get list of OrderDto for pagination
     * 
     * @param paging Object pass parameters for pagination
     * @return list of OrderDto
     * @throws DaoException
     */
    List<OrderDto> getAll(Paging paging) throws DaoException;

    /**
     * Method get quantity of orders from Dao
     * 
     * @return quantity of orders
     * @throws DaoException
     */
    long count() throws DaoException;

}
