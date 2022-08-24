package service.impl;

import java.util.List;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.DaoException;
import controller.util.exception.impl.InternalErrorException;
import controller.util.exception.impl.NotFoundException;
import dao.entity.Client;
import dao.entity.Client.Type;
import dao.entity.User.Role;
import dao.interfaces.ClientDao;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.dto.ClientDto;
import service.dto.ClientDto.TypeDto;
import service.dto.UserDto.RoleDto;

@Log4j2
public class ClientServiceImpl implements ClientService {

    private ClientDao clientDao;

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public ClientDto getById(Long id) throws Exception {
        Client client = clientDao.get(id);
        if (client == null) {
            log.error("Trying to get not existing client, client id={}", id);
            throw new NotFoundException("client with id " + id + " not found");
        }
        return toDto(client);
    }

    @Override
    public List<ClientDto> getAll() throws DaoException {
        return clientDao.getAll().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public List<ClientDto> getAll(Paging paging) throws DaoException {
        return clientDao.getAll(paging.getLimit(), paging.getOffset()).stream().map(e -> toDto(e)).toList();
    }

    @Override
    public ClientDto create(ClientDto clientDto) throws Exception {
        Client existing = clientDao.get(clientDto.getId());
        if (existing != null) {
            return toDto(existing);
        }
        Client client = toClient(clientDto);
        Client createdClient = clientDao.create(client);
        return toDto(createdClient);
    }

    @Override
    public ClientDto update(ClientDto clientDto) throws Exception {
        Client client = toClient(clientDto);
        Client updatedClient = clientDao.update(client);
        return toDto(updatedClient);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (!clientDao.delete(id)) {
            log.error("Client wasn't delete, client id={}", id);
            throw new InternalErrorException("Internal Server Error. Client wasn't delete.");
        }
    }

    @Override
    public List<ClientDto> getAllClientsByType(String typeOfClient) throws DaoException {
        return clientDao.getAllClientsByType(typeOfClient).stream().map(e -> toDto(e)).toList();
    }

    /**
     * Method transforming Client to ClientDto
     * 
     * @param client Object for transforming
     * @return transformed Object
     */
    private ClientDto toDto(Client client) {
        ClientDto clientDto = new ClientDto();
        try {
            clientDto.setId(client.getId());
            clientDto.setEmail(client.getEmail());
            clientDto.setFirstName(client.getFirstName());
            clientDto.setLastName(client.getLastName());
            clientDto.setBirthDate(client.getBirthDate());
            clientDto.setPhoneNumber(client.getPhoneNumber());
            clientDto.setType(TypeDto.valueOf(client.getType().toString()));
            clientDto.setRoleDto(RoleDto.valueOf(client.getRole().toString()));
            clientDto.setTrainerId(client.getTrainerId());
            clientDto.setAdditionalInfo(client.getAdditionalInfo());
            clientDto.setPathAvatar(client.getPathAvatar());
        } catch (NullPointerException e) {
            log.error("Client wasn't create, client={} ", client);
        }
        return clientDto;
    }

    /**
     * Method transforming ClientDto to Client
     * 
     * @param clientDto Object for transforming
     * @return transformed Object
     */
    private Client toClient(ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setBirthDate(clientDto.getBirthDate());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setType(Type.valueOf(clientDto.getType().toString()));
        client.setRole(Role.valueOf(clientDto.getRoleDto().toString()));
        client.setTrainerId(clientDto.getTrainerId());
        client.setAdditionalInfo(clientDto.getAdditionalInfo());
        client.setPathAvatar(clientDto.getPathAvatar());
        return client;
    }

    @Override
    public long count() throws DaoException {
        return clientDao.count();
    }

}
