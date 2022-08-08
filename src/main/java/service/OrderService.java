package service;

import java.util.List;
import java.util.Map;

import service.dto.OrderDto;
import service.dto.UserDto;

public interface OrderService extends AbstractService<Long, OrderDto>{

    @Override
    OrderDto getById(Long id);

    @Override
    List<OrderDto> getAll();

    @Override
    OrderDto create(OrderDto orderDto);
    
    OrderDto processCart(Map<Long, Integer> cart, UserDto userDto);

    @Override
    OrderDto update(OrderDto orderDto);

    OrderDto addFeedback(OrderDto orderDto);

    List<OrderDto> getAllOrdersDtoByClient(Long id);

    List<OrderDto> getAllByStatus(String statusName);

    @Override
    void delete(Long id);


}
