package service;

import java.util.List;
import java.util.Map;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.InternalErrorException;
import service.dto.OrderDto;
import service.dto.UserDto;

public interface OrderService extends AbstractService<Long, OrderDto> {

    OrderDto processCart(Map<Long, Integer> cart, UserDto userDto) throws Exception;

    OrderDto addFeedback(OrderDto orderDto);

    List<OrderDto> getAllOrdersDtoByClient(Long id);

    List<OrderDto> getAllByStatus(String statusName);

    List<OrderDto> getAll(Paging paging);
    
    long count() throws InternalErrorException;

}
