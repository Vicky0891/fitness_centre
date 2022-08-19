package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.InternalErrorException;
import dao.entity.User;

public interface UserDao {

    User get(Long id);

    User getByEmail(String email);
    
    List<User> getAll();

    User create(User user) throws InternalErrorException;

    User update(User user) throws InternalErrorException;

    boolean delete(Long id);

}
