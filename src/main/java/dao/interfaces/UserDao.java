package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.User;

public interface UserDao extends AbstractDao<Long, User> {

    User getByEmail(String email) throws DaoException;

    List<User> getAll(int limit, Long offset) throws DaoException;

    long count() throws DaoException;

}
