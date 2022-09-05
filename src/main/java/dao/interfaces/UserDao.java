package dao.interfaces;

import java.sql.Connection;
import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.User;

public interface UserDao extends AbstractDao<Long, User> {

    /**
     * Method find User object in the data source by email
     * 
     * @param email User email to be get
     * @return User
     * @throws DaoException
     */
    User getByEmail(String email);

    /**
     * Method get list of objects starting from begin position in the table
     * 
     * @param limit  number of records in the table
     * @param offset starting position for search in the table
     * @return list of Users
     * @throws DaoException
     */
    List<User> getAll(int limit, Long offset);

    /**
     * Method count all users
     * 
     * @return quantity of users
     * @throws DaoException
     */
    long count();

    /**
     * Method find Entity object in the data source by id
     * 
     * @param id         Object id to be get
     * @param connection Connection to be used
     * @return Object
     * @throws DaoException
     */
    User get(Long id, Connection connection);

    /**
     * Method is used to update user password in the data source
     * 
     * @param entity to be updated
     * @return updated Object
     * @throws DaoException
     */
    User updatePassword(User user);

}
