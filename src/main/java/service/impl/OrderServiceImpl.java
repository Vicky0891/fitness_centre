package service.impl;

import java.util.List;

import dao.entity.Customer;
import dao.entity.Order;
import dao.entity.Trainer;
import dao.entity.Customer.Type;
import dao.entity.Order.Status;
import dao.interfaces.OrderDao;
import lombok.extern.log4j.Log4j2;
import service.OrderService;
import service.dto.CustomerDto;
import service.dto.OrderDto;
import service.dto.TrainerDto;
import service.dto.CustomerDto.TypeDto;
import service.dto.OrderDto.StatusDto;

@Log4j2
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public OrderDto getById(Long id) {
        Order order = orderDao.get(id);
        if(order == null) {
            throw new RuntimeException("No order with id " + id);
        }
        OrderDto orderDto = toDto(order);
        return orderDto;
    }

    @Override
    public List<OrderDto> getAll() {
        return orderDao.getAll().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        Order existing = orderDao.get(orderDto.getId());
        if (existing != null) {
            log.error("Prescription with id " + orderDto.getId() + " already exists");
            throw new RuntimeException("Prescription with id " + orderDto.getId() + " already exists");
        }
        Order order = toOrder(orderDto);
        Order createdOrder = orderDao.create(order);
        return toDto(createdOrder);
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        Order existing = orderDao.get(orderDto.getId());
        if (existing != null && existing.getId() != orderDto.getId()) {
            log.error("Order with id " + orderDto.getId() + " already exists");
            throw new RuntimeException("Order with id " + orderDto.getId() + " already exists");
        }
        Order order = toOrder(orderDto);
        Order createdOrder = orderDao.update(order);
        return toDto(createdOrder);
    }

    @Override
    public OrderDto addFeedback(OrderDto orderDto) {
        Order existing = orderDao.get(orderDto.getId());
        if (existing != null && existing.getId() != orderDto.getId()) {
            log.error("Order with id " + orderDto.getId() + " already exists");
            throw new RuntimeException("Order with id " + orderDto.getId() + " already exists");
        }
        Order order = toOrder(orderDto);
        Order createdOrder = orderDao.addFeedback(order);
        return toDto(createdOrder);
    }

    @Override
    public List<OrderDto> getAllOrdersDtoByCustomer(Long id) {
        return orderDao.getAllOrdersByCustomer(id).stream().map(e -> toDto(e)).toList();
    }

    @Override
    public List<OrderDto> getAllByStatus(String statusName) {
        return orderDao.getAllByStatus(statusName).stream().map(e -> toDto(e)).toList();
    }

    @Override
    public void delete(Long id) {
        if(!orderDao.delete(id)) {
            throw new RuntimeException("Couldn't delete user with id " + id);
        };
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

    private OrderDto toDto(Order order) {
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

}
