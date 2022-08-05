package service;

import java.util.List;

import service.dto.CustomerDto;

public interface CustomerService extends AbstractService<Long, CustomerDto>{

    @Override
    CustomerDto getById(Long id);

    @Override
    List<CustomerDto> getAll();

    @Override
    CustomerDto create(CustomerDto customerDto);

    @Override
    CustomerDto update(CustomerDto customerDto);

    CustomerDto updateByAdmin(CustomerDto customerDto);

    CustomerDto getCustomerDtoByEmail(String email);

    List<CustomerDto> getAllByType(String typeName);

    @Override
    void delete(Long id);

}
