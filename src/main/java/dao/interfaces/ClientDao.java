package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.Client;

public interface ClientDao {

    Client get(Long id) throws DaoException;

    List<Client> getAll() throws DaoException;

    List<Client> getAll(int limit, Long offset) throws DaoException;

    List<Client> getAllClientsByType(String typeOfClient) throws DaoException;

    Client create(Client client) throws DaoException;

    Client update(Client client) throws DaoException;

    boolean delete(Long id) throws DaoException;

    long count() throws DaoException;

}
