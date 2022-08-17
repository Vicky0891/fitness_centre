package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.entity.Client;
import dao.entity.Trainer;
import dao.entity.Client.Type;
import dao.entity.User.Role;
import dao.interfaces.TrainerDao;
import lombok.extern.log4j.Log4j2;
import service.TrainerService;
import service.dto.ClientDto;
import service.dto.TrainerDto;
import service.dto.ClientDto.TypeDto;
import service.dto.UserDto.RoleDto;
@Log4j2
public class TrainerServiceImpl implements TrainerService{
    
    private TrainerDao trainerDao;

    public TrainerServiceImpl(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Override
    public TrainerDto getById(Long id) {
        Trainer trainer = trainerDao.get(id);
        if(trainer == null) {
            throw new RuntimeException("No trainer with id " + id);
        }
        TrainerDto trainerDto = toDto(trainer);
        return trainerDto;
    }

    @Override
    public List<TrainerDto> getAll() {
        return trainerDao.getAll().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public TrainerDto create(TrainerDto trainerDto) {
        Trainer existing = trainerDao.get(trainerDto.getId());
        if (existing != null) {
            log.error("Trainer with this id=" + trainerDto.getId() + " already exists");
            throw new RuntimeException("Trainer with this id=" + trainerDto.getId() + " already exists");
        }
        Trainer trainer = toTrainerForCreate(trainerDto);
        Trainer createdTrainer = trainerDao.create(trainer);
        return toDtoForCreate(createdTrainer);
    }

    @Override
    public TrainerDto update(TrainerDto trainerDto) {
        Trainer trainer = toTrainer(trainerDto);
        Trainer createdTrainer = trainerDao.update(trainer);
        return toDto(createdTrainer);
    }

    @Override
    public void delete(Long id) {
        if(!trainerDao.delete(id)) {
            throw new RuntimeException("Couldn't delete client with id " + id);
        };
        
    }

    @Override
    public List<ClientDto> getAllClientsByTrainer(Long trainerId) {
        return trainerDao.getAllClientsByTrainer(trainerId).stream().map(e -> clientToDto(e)).toList();
    }
    
    private TrainerDto toDto(Trainer trainer) {
        TrainerDto trainerDto = new TrainerDto();
        try {
            trainerDto.setId(trainer.getId());
            trainerDto.setEmail(trainer.getEmail());
            trainerDto.setFirstName(trainer.getFirstName());
            trainerDto.setLastName(trainer.getLastName());
            trainerDto.setBirthDate(trainer.getBirthDate());
            trainerDto.setRoleDto(RoleDto.valueOf(trainer.getRole().name()));
            List<Client> clients = trainer.getClients();
            List<ClientDto> clientsDto = new ArrayList<>();
            for(Client client: clients) {
                ClientDto clientDto = clientToDto(client);
                clientsDto.add(clientDto);
            }
            trainerDto.setClients(clientsDto);
            trainerDto.setCategory(trainer.getCategory());
        } catch (NullPointerException e) {
            log.error("UserDto wasn't create " + e);
        }
        return trainerDto;
    }
    
    private TrainerDto toDtoForCreate(Trainer trainer) {
        TrainerDto trainerDto = new TrainerDto();
        try {
            trainerDto.setId(trainer.getId());
            trainerDto.setEmail(trainer.getEmail());
            trainerDto.setRoleDto(RoleDto.valueOf(trainer.getRole().name()));
        } catch (NullPointerException e) {
            log.error("UserDto wasn't create " + e);
        }
        return trainerDto;
    }
    
    private Trainer toTrainerForCreate(TrainerDto trainerDto) {
        Trainer trainer = new Trainer();
        trainer.setId(trainerDto.getId());
        trainer.setEmail(trainerDto.getEmail());
        trainer.setRole(Role.valueOf(trainerDto.getRoleDto().name()));
        return trainer;
    }
    
    private Trainer toTrainer(TrainerDto trainerDto) {
        Trainer trainer = new Trainer();
        trainer.setId(trainerDto.getId());
        trainer.setFirstName(trainerDto.getFirstName());
        trainer.setLastName(trainerDto.getLastName());
        trainer.setBirthDate(trainerDto.getBirthDate());
        trainer.setRole(Role.valueOf(trainerDto.getRoleDto().name()));
        List<ClientDto> clientsDto = trainerDto.getClients();
        List<Client> clients = new ArrayList<>();
        for(ClientDto clientDto: clientsDto) {
            Client client = toClient(clientDto);
            clients.add(client);
        }
        trainer.setClients(clients);
        trainer.setCategory(trainerDto.getCategory());
        return trainer;
    }

    
    private ClientDto clientToDto(Client client) {
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
            clientDto.setRoleDto(RoleDto.CLIENT);
            clientDto.setTrainerId(client.getTrainerId());
            clientDto.setAdditionalInfo(client.getAdditionalInfo());
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

}
