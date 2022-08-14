package service;

import java.util.List;

import service.dto.ClientDto;

public interface ClientService extends AbstractService<Long, ClientDto> {

    List<ClientDto> getAllClientsByType(String typeOfClient);

}
