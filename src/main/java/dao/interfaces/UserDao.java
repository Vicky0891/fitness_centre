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
    User getByEmail(String email) throws DaoException;

    /**
     * Method get list of objects starting from begin position in the table
     * 
     * @param limit  number of records in the table
     * @param offset starting position for search in the table
     * @return list of Users
     * @throws DaoException
     */
    List<User> getAll(int limit, Long offset) throws DaoException;

    /**
     * Method count all users
     * 
     * @return quantity of users
     * @throws DaoException
     */
    long count() throws DaoException;
    
    /**
     * Method find Entity object in the data source by id
     * 
     * @param id Object id to be get
     * @return Object
     * @throws DaoException
     */
    User get(Long id, Connection connection) throws DaoException;

}
