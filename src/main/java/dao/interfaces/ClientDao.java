package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.Client;

public interface ClientDao extends AbstractDao<Long, Client>{

    List<Client> getAll(int limit, Long offset) throws DaoException;

    List<Client> getAllClientsByType(String typeOfClient) throws DaoException;

    long count() throws DaoException;

}
