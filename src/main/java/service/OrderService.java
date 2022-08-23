package service;

import java.util.List;
import java.util.Map;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.DaoException;
import service.dto.OrderDto;
import service.dto.UserDto;

public interface OrderService extends AbstractService<Long, OrderDto> {

    OrderDto processCart(Map<Long, Integer> cart, UserDto userDto) throws Exception;

    OrderDto addFeedback(OrderDto orderDto) throws DaoException;

    List<OrderDto> getAllOrdersDtoByClient(Long id) throws DaoException;

    List<OrderDto> getAllByStatus(String statusName) throws DaoException;

    List<OrderDto> getAll(Paging paging) throws DaoException;

    long count() throws DaoException;

}
