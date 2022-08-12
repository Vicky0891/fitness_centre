package service;

import java.util.List;

import service.dto.OrderInfoDto;

public interface OrderInfoService extends AbstractService<Long, OrderInfoDto>{

    List<OrderInfoDto> getAllByOrderId(Long orderId);

}
