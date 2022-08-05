package service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.entity.Customer;
import dao.entity.Order;
import dao.entity.Prescription;
import dao.entity.Trainer;
import dao.entity.Customer.Type;
import dao.entity.Order.Status;
import dao.interfaces.PrescriptionDao;
import lombok.extern.log4j.Log4j2;
import service.PrescriptionService;
import service.dto.CustomerDto;
import service.dto.OrderDto;
import service.dto.OrderDto.StatusDto;
import service.dto.PrescriptionDto;
import service.dto.TrainerDto;
import service.dto.CustomerDto.TypeDto;
@Log4j2
public class PrescriptionServiceImpl implements PrescriptionService {

    private PrescriptionDao prescriptionDao;

    public PrescriptionServiceImpl(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    @Override
    public PrescriptionDto getById(Long id) {
        Prescription prescription = prescriptionDao.get(id);
        if(prescription == null) {
            throw new RuntimeException("No order with prescription with id " + id);
        }
        PrescriptionDto prescriptionDto = toDto(prescription);
        return prescriptionDto;
    }

    @Override
    public PrescriptionDto create(PrescriptionDto prescriptionDto) {
        Prescription existing = prescriptionDao.get(prescriptionDto.getId());
        if (existing != null) {
            log.error("Prescription with id " + prescriptionDto.getId() + " already exists");
            throw new RuntimeException("Prescription with id " + prescriptionDto.getId() + " already exists");
        }
        Prescription prescription = toPrescription(prescriptionDto);
        Prescription createdPrescription = prescriptionDao.create(prescription);
        return toDto(createdPrescription);
    }

    @Override
    public PrescriptionDto update(PrescriptionDto prescriptionDto) {
        Prescription existing = prescriptionDao.get(prescriptionDto.getId());
        if (existing != null && existing.getId() != prescriptionDto.getId()) {
            log.error("Prescription with id \" + prescriptionDto.getId() + \" already exists");
            throw new RuntimeException("Prescription with id \" + prescriptionDto.getId() + \" already exists");
        }
        Prescription prescription = toPrescription(prescriptionDto);
        Prescription createdPrescription = prescriptionDao.update(prescription);
        return toDto(createdPrescription);
    }

    @Override
    public void delete(Long id) {
        prescriptionDao.delete(prescriptionDao.get(id));
    }

    private Prescription toPrescription(PrescriptionDto prescriptionDto) {
        Prescription prescription = new Prescription();
        prescription.setId(prescriptionDto.getId());
        prescription.setTypeOfTraining(prescriptionDto.getTypeOfTraining());
        prescription.setEquipment(prescriptionDto.getEquipment());
        prescription.setDiet(prescriptionDto.getDiet());
        Status status = Status.valueOf(prescriptionDto.getStatusDto().toString());
        prescription.setStatus(status);
        prescription.setCustomer(toCustomer(prescriptionDto.getCustomerDto()));
        prescription.setTrainer(toTrainer(prescriptionDto.getTrainerDto()));
        prescription.setOrder(toOrder(prescriptionDto.getOrderDto()));
        return prescription;
    }

    private PrescriptionDto toDto(Prescription prescription) {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        try {
            prescriptionDto.setId(prescription.getId());
            prescriptionDto.setTypeOfTraining(prescription.getTypeOfTraining());
            prescriptionDto.setEquipment(prescription.getEquipment());
            prescriptionDto.setDiet(prescription.getDiet());
            StatusDto statusDto = StatusDto.valueOf(prescription.getStatus().toString());
            prescriptionDto.setStatusDto(statusDto);
            prescriptionDto.setCustomerDto(toCustomerDto(prescription.getCustomer()));
            prescriptionDto.setTrainerDto(toTrainerDto(prescription.getTrainer()));
            prescriptionDto.setOrderDto(toOrderDto(prescription.getOrder()));
            
        } catch (NullPointerException e) {
            log.error("PrescriptionDto wasn't create " + e);
        }
        return prescriptionDto;
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
        Trainer trainer = toTrainer(customerDto.getTrainerDto());
        customer.setTrainer(trainer);
        return customer;
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
    
    private Order toOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setDateOfOrder(orderDto.getDateOfOrder());
        order.setCustomer(toCustomer(orderDto.getCustomerDto()));
        order.setTotalCost(orderDto.getTotalCost());
        Status status = Status.valueOf(orderDto.getStatusDto().toString());
        order.setStatus(status);
        order.setFeedback(orderDto.getFeedback());
        return order;
    }
    
    private OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        try {
            orderDto.setId(order.getId());
            orderDto.setDateOfOrder(order.getDateOfOrder());
            orderDto.setCustomerDto(toCustomerDto(order.getCustomer()));
            orderDto.setTotalCost(order.getTotalCost());
            StatusDto statusDto = StatusDto.valueOf(order.getStatus().toString());
            orderDto.setStatusDto(statusDto);
            orderDto.setFeedback(order.getFeedback());
        } catch (NullPointerException e) {
            log.error("OrderDto wasn't create " + e);
        }
        return orderDto;
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
            TrainerDto trainerDto = toTrainerDto(customer.getTrainer());
            customerDto.setTrainerDto(trainerDto);
        } catch (NullPointerException e) {
            log.error("CustomerDto wasn't create " + e);
        }
        return customerDto;
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

    @Override
    public List<PrescriptionDto> getAll() {
        return prescriptionDao.getAll().stream().map(e -> toDto(e)).toList();
    }

}
