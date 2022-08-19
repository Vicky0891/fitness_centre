package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.InternalErrorException;
import dao.entity.Order;

public interface OrderDao {
    
    Order get(Long id);
    
    int getDiscount(String name) throws InternalErrorException;

    List<Order> getAll();
    
    List<Order> getAll(int limit, Long offset);
    
    Order create(Order order) throws InternalErrorException;

    Order update(Order order) throws InternalErrorException;
    
    Order addFeedback(Order order);
    
    List<Order> getAllOrdersByClient(Long id);
    
    List<Order> getAllByStatus(String name);

    boolean delete(Long id);
    
    long count() throws InternalErrorException;

}
