package service;

import service.dto.UserDto;

public interface UserService extends AbstractService<Long, UserDto> {

    UserDto getUserDtoByEmail(String email);
    
    UserDto login(String email, String password);
    
    String getTypeOfUser(Long id);
    
}
