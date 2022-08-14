package dao.interfaces;

import java.util.List;

import dao.entity.Client;

public interface ClientDao {
    
    Client get(Long id);

    List<Client> getAll();

    List<Client> getAllClientsByType(String typeOfClient);

    Client create(Client client);

    Client update(Client client);

    boolean delete(Long id);

}
