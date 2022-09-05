package service;

import java.util.List;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.DaoException;
import service.dto.ClientDto;

public interface ClientService extends AbstractService<Long, ClientDto> {

    /**
     * Method get list of clientsDto particular type
     * 
     * @param typeOfClient type of clientDto to get
     * @return list of ClientsDto
     * @throws DaoException
     */
    List<ClientDto> getAllClientsByType(String typeOfClient);

    /**
     * Method get list of ClientsDto for pagination
     * 
     * @param paging Object pass parameters for pagination
     * @return list of ClientsDto
     * @throws DaoException
     */
    List<ClientDto> getAll(Paging paging);

    /**
     * Method get quantity of clients from Dao
     * 
     * @return quantity of clients
     * @throws DaoException
     */
    long count();

}
