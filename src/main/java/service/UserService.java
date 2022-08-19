package service;

import service.dto.UserDto;

public interface UserService extends AbstractService<Long, UserDto> {

    UserDto getUserDtoByEmail(String email) throws Exception;
    
    UserDto login(String email, String password) throws Exception;
    
    String getTypeOfUser(Long id);
    
}
