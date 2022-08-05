package service;

import java.util.List;

import service.dto.UserDto;

public interface UserService extends AbstractService<Long, UserDto> {

    @Override
    UserDto getById(Long id);

    @Override
    List<UserDto> getAll();

    @Override
    UserDto create(UserDto userDto);

    @Override
    UserDto update(UserDto userDto);

    @Override
    void delete(Long id);

}
