package service;

import java.util.List;

import service.dto.UserDto;

public interface UserService extends AbstractService<Long, UserDto> {

    UserDto getUserDtoByEmail(String email);
    
    UserDto login(String email, String password);

    List<UserDto> getAllClients();
    
    List<UserDto> getAllTrainers();
    
    List<UserDto> getAllClientsDtoByTrainer(Long trainerId);
    
    List<UserDto> getAllClientsByType(String typeOfClient);

}
