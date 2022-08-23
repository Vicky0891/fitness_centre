package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.User;

public interface UserDao {

    User get(Long id) throws DaoException;

    User getByEmail(String email) throws DaoException;

    List<User> getAll() throws DaoException;

    List<User> getAll(int limit, Long offset) throws DaoException;

    User create(User user) throws DaoException;

    User update(User user) throws DaoException;

    boolean delete(Long id) throws DaoException;

    long count() throws DaoException;

}
