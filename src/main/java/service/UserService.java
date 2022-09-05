package service;

import java.util.List;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.DaoException;
import service.dto.UserDto;

public interface UserService extends AbstractService<Long, UserDto> {

    /**
     * Method find User from Dao by email
     * 
     * @param email User email to be get
     * @return UserDto
     * @throws DaoException
     */
    UserDto getUserDtoByEmail(String email);

    /**
     * Method check user by email and password and login it if user exists and
     * parameters correct
     * 
     * @param email    Email of user
     * @param password Password of user
     * @return UserDto
     * @throws Exception
     */
    UserDto login(String email, String password);

    /**
     * Method get list of UserDto for pagination
     * 
     * @param paging Object pass parameters for pagination
     * @return list of UserDto
     * @throws DaoException
     */
    List<UserDto> getAll(Paging paging);

    /**
     * Method to get name of type of user (role) by user id
     * 
     * @param id User id
     * @return name (role) of user
     * @throws DaoException
     */
    String getTypeOfUser(Long id);

    /**
     * Method get quantity of users from Dao
     * 
     * @return quantity of users
     * @throws DaoException
     */
    long count();

    /**
     * Method to change password for user
     * 
     * @param userDto User who changes password
     * @return User with updated password
     * @throws Exception
     */
    UserDto changePassword(UserDto userDto);

}
