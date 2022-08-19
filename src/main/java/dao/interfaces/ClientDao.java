package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.InternalErrorException;
import dao.entity.Client;

public interface ClientDao {
    
    Client get(Long id);

    List<Client> getAll();
    
    List<Client> getAll(int limit, Long offset);

    List<Client> getAllClientsByType(String typeOfClient);

    Client create(Client client) throws InternalErrorException;

    Client update(Client client) throws InternalErrorException;

    boolean delete(Long id);
    
    long count() throws InternalErrorException;

}
