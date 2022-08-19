package service.impl;

import java.util.ArrayList;
import java.util.List;

import controller.util.exception.impl.BadRequestException;
import controller.util.exception.impl.InternalErrorException;
import controller.util.exception.impl.NotFoundException;
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
public class TrainerServiceImpl implements TrainerService {

    private TrainerDao trainerDao;

    public TrainerServiceImpl(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Override
    public TrainerDto getById(Long id) throws Exception {
        Trainer trainer = trainerDao.get(id);
        if (trainer == null) {
            log.error("Trying to get not existing trainer, trainer id={}", id);
            throw new NotFoundException("Trainer with id " + id + " not found");
        }
        return toDto(trainer);
    }

    @Override
    public List<TrainerDto> getAll() {
        return trainerDao.getAll().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public TrainerDto create(TrainerDto trainerDto) throws Exception {
        Trainer existing = trainerDao.get(trainerDto.getId());
        if (existing != null) {
            log.error("Trying to create an existing trainer, trainer={}", trainerDto);
            throw new BadRequestException("Such trainer already exists");
        }
        Trainer trainer = toTrainer(trainerDto);
        Trainer createdTrainer = trainerDao.create(trainer);
        log.info("Trainer was create, trainer={}", trainerDto);
        return toDto(createdTrainer);
    }

    @Override
    public TrainerDto update(TrainerDto trainerDto) {
        Trainer trainer = toTrainer(trainerDto);
        Trainer createdTrainer = trainerDao.update(trainer);
        log.info("Trainer was update, trainer={}", trainerDto);
        return toDto(createdTrainer);
    }

    @Override
    public void delete(Long id) throws InternalErrorException {
        if (!trainerDao.delete(id)) {
            log.error("Trainer wasn't delete, trainer id={}", id);
            throw new InternalErrorException("Internal Server Error. Trainer wasn't delete.");
        }

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
            for (Client client : clients) {
                ClientDto clientDto = clientToDto(client);
                clientsDto.add(clientDto);
            }
            trainerDto.setClients(clientsDto);
            trainerDto.setCategory(trainer.getCategory());
        } catch (NullPointerException e) {
            log.error("Trainer wasn't create, trainer={} ", trainer);
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
            log.error("Trainer wasn't create, trainer={} ", trainer);
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
        for (ClientDto clientDto : clientsDto) {
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
            clientDto.setType(TypeDto.valueOf(client.getType().toString()));
            clientDto.setRoleDto(RoleDto.valueOf(client.getRole().toString()));
            clientDto.setTrainerId(client.getTrainerId());
            clientDto.setAdditionalInfo(client.getAdditionalInfo());
        } catch (NullPointerException e) {
            log.error("Client wasn't create, client={} ", client);
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
        client.setType(Type.valueOf(clientDto.getType().toString()));
        client.setRole(Role.valueOf(clientDto.getRoleDto().toString()));
        client.setTrainerId(clientDto.getTrainerId());
        client.setAdditionalInfo(clientDto.getAdditionalInfo());
        return client;
    }

}
