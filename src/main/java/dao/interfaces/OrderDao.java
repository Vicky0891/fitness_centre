package dao.interfaces;

import java.util.List;

import dao.entity.Order;

public interface OrderDao {
    
    Order get(Long id);

    List<Order> getAll();
    
    Order create(Order order);

    Order update(Order order);
    
    Order addFeedback(Order order);
    
    List<Order> getAllOrdersByCustomer(Long id);
    
    List<Order> getAllByStatus(String name);

    boolean delete(Long id);

}
