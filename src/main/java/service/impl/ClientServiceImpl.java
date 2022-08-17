package service.impl;

import java.util.List;

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
public class ClientServiceImpl implements ClientService{
    
    private ClientDao clientDao;

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public ClientDto getById(Long id) {
        Client client = clientDao.get(id);
        if(client == null) {
            throw new RuntimeException("No client with id " + id);
        }
        ClientDto clientDto = toDto(client);
        return clientDto;
    }

    @Override
    public List<ClientDto> getAll() {
        return clientDao.getAll().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        Client existing = clientDao.get(clientDto.getId());
        if (existing != null) {
            log.error("Client with this id=" + clientDto.getId() + " already exists");
            throw new RuntimeException("Client with this id=" + clientDto.getId() + " already exists");
        }
        Client client = toClientForCreate(clientDto);
        Client createdClient = clientDao.create(client);
        return toDtoForCreate(createdClient);
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        Client client = toClient(clientDto);
        Client updatedClient = clientDao.update(client);
        return toDto(updatedClient);
    }

    @Override
    public void delete(Long id) {
        if(!clientDao.delete(id)) {
            throw new RuntimeException("Couldn't delete client with id " + id);
        };
    }

    @Override
    public List<ClientDto> getAllClientsByType(String typeOfClient) {
        return clientDao.getAllClientsByType(typeOfClient).stream().map(e -> toDto(e)).toList();
    }
    
    private ClientDto toDto(Client client) {
        ClientDto clientDto = new ClientDto();
        try {
            clientDto.setId(client.getId());
            clientDto.setEmail(client.getEmail());
            clientDto.setFirstName(client.getFirstName());
            clientDto.setLastName(client.getLastName());
            clientDto.setBirthDate(client.getBirthDate());
            clientDto.setPhoneNumber(client.getPhoneNumber());
            TypeDto typeDto = TypeDto.valueOf(client.getType().toString());
            clientDto.setType(typeDto);
            RoleDto roleDto = RoleDto.valueOf(client.getRole().toString());
            clientDto.setRoleDto(roleDto);
            clientDto.setTrainerId(client.getTrainerId());
            clientDto.setAdditionalInfo(client.getAdditionalInfo());
        } catch (NullPointerException e) {
            log.error("UserDto wasn't create " + e);
        }
        return clientDto;
    }
    
    private ClientDto toDtoForCreate(Client client) {
        ClientDto clientDto = new ClientDto();
        try {
            clientDto.setId(client.getId());
            clientDto.setEmail(client.getEmail());
            TypeDto typeDto = TypeDto.valueOf(client.getType().toString());
            clientDto.setType(typeDto);
            RoleDto roleDto = RoleDto.valueOf(client.getRole().toString());
            clientDto.setRoleDto(roleDto);
        } catch (NullPointerException e) {
            log.error("UserDto wasn't create " + e);
        }
        return clientDto;
    }
    
    private Client toClient(ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setBirthDate(clientDto.getBirthDate());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        Type type = Type.valueOf(clientDto.getType().toString());
        client.setType(type);
        client.setRole(Role.CLIENT);
        client.setTrainerId(clientDto.getTrainerId());
        client.setAdditionalInfo(clientDto.getAdditionalInfo());
        return client;
    }
    
    private Client toClientForCreate(ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        Type type = Type.valueOf(clientDto.getType().toString());
        client.setType(type);
        client.setRole(Role.valueOf(clientDto.getRoleDto().toString()));
        return client;
    }

}
