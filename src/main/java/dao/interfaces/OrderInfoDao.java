package dao.interfaces;

import java.sql.Connection;
import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.OrderInfo;

public interface OrderInfoDao extends AbstractDao<Long, OrderInfo> {

    /**
     * Method finds all Entity objects by order in the data source
     * 
     * @param id Order id to get OrderInfo objects
     * @return List of Objects
     * @throws DaoException
     */
    List<OrderInfo> getAllByOrderId(Long id);

    /**
     * Method finds all Entity objects by order in the data source
     * 
     * @param id         Order id to get OrderInfo objects
     * @param connection Connection to be used
     * @return List of Objects
     * @throws DaoException
     */
    List<OrderInfo> getAllByOrderId(Long id, Connection connection);

    /**
     * Method is used to create Object in the data source
     * 
     * @param orderInfo  entity to be saved
     * @param connection Connection to be used
     * @return created Object
     * @throws DaoException
     */
    OrderInfo create(OrderInfo orderInfo, Connection connection);

}
