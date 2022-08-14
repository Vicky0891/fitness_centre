package dao.interfaces;

import java.sql.Connection;
import java.util.List;

import dao.entity.OrderInfo;

public interface OrderInfoDao {

    OrderInfo get(Long id);

    List<OrderInfo> getAll();
    
    List<OrderInfo> getAllByOrderId(Long id);

    OrderInfo create(OrderInfo orderInfo, Connection connection);

    OrderInfo update(OrderInfo orderInfo);

    boolean delete(Long id);

}
