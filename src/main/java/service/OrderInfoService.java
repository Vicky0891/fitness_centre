package service;

import java.util.List;

import service.dto.OrderInfoDto;

public interface OrderInfoService extends AbstractService<Long, OrderInfoDto>{

    @Override
    OrderInfoDto getById(Long id);

    @Override
    List<OrderInfoDto> getAll();

    @Override
    OrderInfoDto create(OrderInfoDto orderInfoDto);

    @Override
    OrderInfoDto update(OrderInfoDto orderInfoDto);

    @Override
    void delete(Long id);

}
