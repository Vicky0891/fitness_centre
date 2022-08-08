package service;

import java.util.List;

import service.dto.UserDto;

public interface UserService extends AbstractService<Long, UserDto> {

    @Override
    UserDto getById(Long id);
    
    UserDto getUserDtoByEmail(String email);
    
    UserDto login(String email, String password);

    @Override
    List<UserDto> getAll();
    
    List<UserDto> getAllClients();
    
    List<UserDto> getAllTrainers();
    
    List<UserDto> getAllClientsDtoByTrainer(Long trainerId);
    
    List<UserDto> getAllClientsByType(String typeOfClient);

    @Override
    UserDto create(UserDto userDto);

    @Override
    UserDto update(UserDto userDto);

    @Override
    void delete(Long id);

}
