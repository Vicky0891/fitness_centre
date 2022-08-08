package dao.interfaces;

import java.util.List;

import dao.entity.User;

public interface UserDao {

    User get(Long id);

    User getByEmail(String email);
    
    List<User> getAll();

    List<User> getAllClients();

    List<User> getAllTrainers();

    List<User> getAllClientsByTrainer(Long trainerId);

    List<User> getAllClientsByType(String typeOfClient);

    User create(User user);

    User update(User user);

    boolean delete(Long id);

}
