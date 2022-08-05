package service;

import java.util.List;

import service.dto.OrderDto;

public interface OrderService extends AbstractService<Long, OrderDto>{

    @Override
    OrderDto getById(Long id);

    @Override
    List<OrderDto> getAll();

    @Override
    OrderDto create(OrderDto orderDto);

    @Override
    OrderDto update(OrderDto orderDto);

    OrderDto addFeedback(OrderDto orderDto);

    List<OrderDto> getAllOrdersDtoByCustomer(Long id);

    List<OrderDto> getAllByStatus(String statusName);

    @Override
    void delete(Long id);

}
