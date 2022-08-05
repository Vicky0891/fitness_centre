package service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.entity.Customer;
import dao.entity.Trainer;
import dao.interfaces.TrainerDao;
import lombok.extern.log4j.Log4j2;
import service.TrainerService;
import service.dto.CustomerDto;
import service.dto.TrainerDto;
import service.dto.CustomerDto.TypeDto;
@Log4j2
public class TrainerServiceImpl implements TrainerService {

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
        Trainer existing = trainerDao.getTrainerByEmail(trainerDto.getEmail());
        if (existing != null) {
            log.error("User with email " + trainerDto.getEmail() + " already exists");
            throw new RuntimeException("Trainer with email " + trainerDto.getEmail() + " already exists");
        }
        Trainer trainer = toTrainer(trainerDto);
        Trainer createdTrainer = trainerDao.create(trainer);
        return toDto(createdTrainer);
    }

    @Override
    public TrainerDto update(TrainerDto trainerDto) {
        Trainer existing = trainerDao.getTrainerByEmail(trainerDto.getEmail());
        if (existing != null && existing.getEmail() != trainerDto.getEmail()) {
            log.error("Trainer with email " + trainerDto.getEmail() + " already exists");
            throw new RuntimeException("Trainer with email " + trainerDto.getEmail() + " already exists");
        }
        Trainer trainer = toTrainer(trainerDto);
        Trainer changedTrainer = trainerDao.update(trainer);
        return toDto(changedTrainer);
    }

    @Override
    public List<CustomerDto> getCustomersDtoForTrainer(Long id) {
        return trainerDao.getCustomersForTrainer(id).stream().map(e -> toCustomerDto(e)).toList();
    }

    @Override
    public void delete(Long id) {
        if(!trainerDao.delete(id)) {
            throw new RuntimeException("Couldn't delete user with id " + id);
        };
    }

    private Trainer toTrainer(TrainerDto trainerDto) {
        Trainer trainer = new Trainer();
        trainer.setId(trainerDto.getId());
        trainer.setFirstName(trainerDto.getFirstName());
        trainer.setLastName(trainerDto.getLastName());
        trainer.setEmail(trainerDto.getEmail());
        trainer.setPassword(trainerDto.getPassword());
        trainer.setBirthDate(trainerDto.getBirthDate());
        trainer.setPhoneNumber(trainerDto.getPhoneNumber());
        trainer.setAdditionalInfo(trainerDto.getAdditionalInfo());
        return trainer;
    }

    private TrainerDto toDto(Trainer trainer) {
        TrainerDto trainerDto = new TrainerDto();
        try {
            trainerDto.setId(trainer.getId());
            trainerDto.setFirstName(trainer.getFirstName());
            trainerDto.setLastName(trainer.getLastName());
            trainerDto.setEmail(trainer.getEmail());
            trainerDto.setPassword(trainer.getPassword());
            trainerDto.setBirthDate(trainer.getBirthDate());
            trainerDto.setPhoneNumber(trainer.getPhoneNumber());
            trainerDto.setAdditionalInfo(trainer.getAdditionalInfo());
        } catch (NullPointerException e) {
            log.error("TrainerDto wasn't create " + e);
        }
        return trainerDto;
    }

    private CustomerDto toCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        try {
            customerDto.setId(customer.getId());
            customerDto.setFirstName(customer.getFirstName());
            customerDto.setLastName(customer.getLastName());
            customerDto.setEmail(customer.getEmail());
            customerDto.setPassword(customer.getPassword());
            customerDto.setBirthDate(customer.getBirthDate());
            customerDto.setPhoneNumber(customer.getPhoneNumber());
            TypeDto typeDto = TypeDto.valueOf(customer.getType().toString());
            customerDto.setTypeDto(typeDto);
            TrainerDto trainerDto = toDto(customer.getTrainer());
            customerDto.setTrainerDto(trainerDto);
        } catch (NullPointerException e) {
            log.error("CustomerDto wasn't create " + e);
        }
        return customerDto;
    }

}
