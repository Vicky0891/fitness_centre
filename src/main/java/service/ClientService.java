package service;

import java.util.List;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.DaoException;
import service.dto.ClientDto;

public interface ClientService extends AbstractService<Long, ClientDto> {

    List<ClientDto> getAllClientsByType(String typeOfClient) throws DaoException;

    List<ClientDto> getAll(Paging paging) throws DaoException;

    long count() throws DaoException;

}
