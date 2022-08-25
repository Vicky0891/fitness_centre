package dao.interfaces;

import java.sql.Connection;
import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.Client;

public interface ClientDao extends AbstractDao<Long, Client> {

    /**
     * Method get list of objects starting from begin position in the table
     * 
     * @param limit  number of records in the table
     * @param offset starting position for search in the table
     * @return list of Clients
     * @throws DaoException
     */
    List<Client> getAll(int limit, Long offset) throws DaoException;

    /**
     * Method get list of objects particular type
     * 
     * @param typeOfClient type of client to get
     * @return list of Clients
     * @throws DaoException
     */
    List<Client> getAllClientsByType(String typeOfClient) throws DaoException;

    /**
     * Method count all clients
     * 
     * @return quantity of clients
     * @throws DaoException
     */
    long count() throws DaoException;

    /**
     * Method find Client in the data source by id
     * 
     * @param id Client id to be get
     * @return Client
     * @throws DaoException
     */
    Client get(Long id, Connection connection) throws DaoException;

}
