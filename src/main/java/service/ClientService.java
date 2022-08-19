package service;

import java.util.List;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.InternalErrorException;
import service.dto.ClientDto;

public interface ClientService extends AbstractService<Long, ClientDto> {

    List<ClientDto> getAllClientsByType(String typeOfClient);

    List<ClientDto> getAll(Paging paging);

    long count() throws InternalErrorException;

}
