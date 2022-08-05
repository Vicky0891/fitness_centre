package service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.entity.Customer;
import dao.entity.Customer.Type;
import dao.interfaces.CustomerDao;
import dao.interfaces.TrainerDao;
import lombok.extern.log4j.Log4j2;
import dao.entity.Trainer;
import service.CustomerService;
import service.dto.CustomerDto;
import service.dto.CustomerDto.TypeDto;
import service.dto.TrainerDto;
@Log4j2
public class CustomerServiceImpl implements CustomerService{
    
    private CustomerDao customerDao;
//    private TrainerDao trainerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
//        this.trainerDao = trainerDao;
    }

    @Override
    public CustomerDto getById(Long id) {
        Customer customer = customerDao.get(id);
        if(customer == null) {
            throw new RuntimeException("No customer with id " + id);
        }
        CustomerDto customerDto = toDto(customer);
        return customerDto;
    }

    @Override
    public List<CustomerDto> getAll() {
        return customerDao.getAll().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public CustomerDto create(CustomerDto customerDto) {
        Customer existing = customerDao.getCustomerByEmail(customerDto.getEmail());
        if (existing != null) {
            log.error("Customer with email " + customerDto.getEmail() + " already exists");
            throw new RuntimeException("Customer with email " + customerDto.getEmail() + " already exists");
        }
        Customer customer = toCustomer(customerDto);
        Customer createdCustomer = customerDao.create(customer);
        return toDto(createdCustomer);
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        Customer existing = customerDao.getCustomerByEmail(customerDto.getEmail());
        if (existing != null && existing.getEmail() != customerDto.getEmail()) {
            log.error("Customer with email " + customerDto.getEmail() + " already exists");
            throw new RuntimeException("Customer with email " + customerDto.getEmail() + " already exists");
        }
        Customer customer = toCustomer(customerDto);
        Customer createdCustomer = customerDao.update(customer);
        return toDto(createdCustomer);
    }

    @Override
    public CustomerDto updateByAdmin(CustomerDto customerDto) {
        Customer existing = customerDao.getCustomerByEmail(customerDto.getEmail());
        if (existing != null && existing.getEmail() != customerDto.getEmail()) {
            log.error("Customer with email " + customerDto.getEmail() + " already exists");
            throw new RuntimeException("Customer with email " + customerDto.getEmail() + " already exists");
        }
        Customer customer = toCustomer(customerDto);
        Customer createdCustomer = customerDao.updateByAdmin(customer);
        return toDto(createdCustomer);
    }

    @Override
    public CustomerDto getCustomerDtoByEmail(String email) {
        Customer customer = customerDao.getCustomerByEmail(email);
        if(customer == null) {
            throw new RuntimeException("No customer with email " + email);
        }
        CustomerDto customerDto = toDto(customer);
        return customerDto;
    }

    @Override
    public List<CustomerDto> getAllByType(String typeName) {
        return customerDao.getAllByType(typeName).stream().map(e -> toDto(e)).toList();
    }

    @Override
    public void delete(Long id) {
        if(!customerDao.delete(id)) {
            throw new RuntimeException("Couldn't delete customer with id " + id);
        };
    }
    
    private Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(customerDto.getPassword());
        customer.setBirthDate(customerDto.getBirthDate());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        Type type = Type.valueOf(customerDto.getTypeDto().toString());
        customer.setType(type);
        
//        Long trainerId = customerDto.getTrainerDto().getId();
//        Trainer trainer = trainerDao.get(trainerId);
        
        Trainer trainer = toTrainer(customerDto.getTrainerDto());
        customer.setTrainer(trainer);
        return customer;
    }

    private CustomerDto toDto(Customer customer) {
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
            TrainerDto trainerDto = toTrainerDto(customer.getTrainer());
            customerDto.setTrainerDto(trainerDto);
        } catch (NullPointerException e) {
            log.error("CustomerDto wasn't create " + e);
        }
        return customerDto;
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
    
    private TrainerDto toTrainerDto(Trainer trainer) {
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

}
