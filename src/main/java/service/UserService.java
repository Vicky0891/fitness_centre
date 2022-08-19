package service;

import java.util.List;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.InternalErrorException;
import service.dto.UserDto;

public interface UserService extends AbstractService<Long, UserDto> {

    UserDto getUserDtoByEmail(String email) throws Exception;
    
    UserDto login(String email, String password) throws Exception;
    
    List<UserDto> getAll(Paging paging);
    
    String getTypeOfUser(Long id);
    
    long count() throws InternalErrorException;
    
}
