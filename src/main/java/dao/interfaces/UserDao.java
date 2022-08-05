package dao.interfaces;

import java.util.List;

import dao.entity.User;

public interface UserDao {
    
    User get(Long id);

    List<User> getAll();
    
    User create(User user);

    User update(User user);
    
    User getUserByEmail(String email);

    boolean delete(Long id);

}
