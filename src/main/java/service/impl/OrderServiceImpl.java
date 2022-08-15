package service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.entity.Order;
import dao.entity.OrderInfo;
import dao.entity.Client;
import dao.entity.Client.Type;
import dao.entity.GymMembership;
import dao.entity.Order.Status;
import dao.interfaces.ClientDao;
import dao.interfaces.GymMembershipDao;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderInfoDao;
import lombok.extern.log4j.Log4j2;
import service.OrderService;
import service.dto.OrderDto;
import service.dto.UserDto;
import service.dto.ClientDto;
import service.dto.GymMembershipDto;
import service.dto.OrderDto.StatusDto;
import service.dto.OrderInfoDto;

@Log4j2
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private GymMembershipDao gymMembershipDao;
    private OrderInfoDao orderInfoDao;
    private ClientDao clientDao;

    public OrderServiceImpl(OrderDao orderDao, GymMembershipDao gymMembershipDao, OrderInfoDao orderInfoDao, ClientDao clientDao) {
        this.orderDao = orderDao;
        this.gymMembershipDao = gymMembershipDao;
        this.orderInfoDao = orderInfoDao;
        this.clientDao = clientDao;
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
    public List<OrderDto> getAllOrdersDtoByClient(Long id) {
        return orderDao.getAllOrdersByClient(id).stream().map(e -> toDto(e)).toList();
        
    }
    
    @Override
    public List<OrderDto> getAllByStatus(String statusName) {
        return orderDao.getAllByStatus(statusName).stream().map(e -> toDto(e)).toList();
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        Order order = toOrder(orderDto);
        Order createdOrder = orderDao.create(order);
//        
//        
//        Long userId = orderDto.getUserId();
//        Client existingClient = clientDao.get(userId);
//        if(existingClient == null) {
//            Client client = new Client();
//            client.setId(userId);
//            client.setType(Type.NEW);
//            clientDao.create(client);
//        }
        return toDto(createdOrder);
    }
    
    @Override
    public OrderDto processCart(Map<Long, Integer> cart, UserDto userDto) {
        OrderDto orderDto = createDto(cart, userDto);
        return orderDto;
    }
   
    public OrderDto createDto(Map<Long, Integer> cart, UserDto userDto) {
        OrderDto orderDto = new OrderDto();
        orderDto.setStatusDto(StatusDto.PENDING);
        orderDto.setDateOfOrder(LocalDate.now());
        List<OrderInfoDto> details = new ArrayList<>();
        cart.forEach((gymmembershipId, quantity) -> {
            OrderInfoDto orderInfoDto = new OrderInfoDto();
            GymMembership gymMembership = gymMembershipDao.get(gymmembershipId);
            GymMembershipDto gymmembershipDto = toGymMembershipDto(gymMembership);
            orderInfoDto.setGymMembershipDto(gymmembershipDto);
            orderInfoDto.setGymMembershipPrice(gymmembershipDto.getCost());
            orderInfoDto.setGymMembershipQuantity(quantity);
            details.add(orderInfoDto);
        });
        orderDto.setDetails(details);
        BigDecimal totalCost = calculatePrice(details);
        if(userDto != null) {
            totalCost = calculateDiscount(userDto, totalCost);
        }
        orderDto.setTotalCost(totalCost);
        orderDto.setFeedback("");
        if(userDto == null) {
        return orderDto;
        }
        orderDto.setUserId(userDto.getId());
        return orderDto;
    }

    private BigDecimal calculateDiscount(UserDto userDto, BigDecimal totalCost) {
        Client existingClient = clientDao.get(userDto.getId());
        if(existingClient == null) {
            return totalCost;
        }
        String typeOfClient = existingClient.getType().toString();
        int discount = orderDao.getDiscount(typeOfClient);
        double totalCostInDouble = totalCost.doubleValue();
        totalCostInDouble = (totalCost.doubleValue() - ((totalCost.doubleValue() / (double)100) * (double)discount));
        return BigDecimal.valueOf(totalCostInDouble);
    }
    
    private BigDecimal calculatePrice(List<OrderInfoDto> details) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderInfoDto detail: details) {
            BigDecimal gymmembershipPrice = detail.getGymMembershipPrice();
            BigDecimal itemPrice = gymmembershipPrice.multiply(BigDecimal.valueOf(detail.getGymMembershipQuantity()));
            totalCost = totalCost.add(itemPrice);
        }
        return totalCost;
    }
    
    private GymMembershipDto toGymMembershipDto(GymMembership gymMembership) {
        GymMembershipDto gymMembershipDto = new GymMembershipDto();
        try {
            gymMembershipDto.setId(gymMembership.getId());
            gymMembershipDto.setNumberOfVisits(gymMembership.getNumberOfVisits());
            gymMembershipDto.setTypeOfTraining(gymMembership.getTypeOfTraining());
            gymMembershipDto.setCost(gymMembership.getCost());
        } catch (NullPointerException e) {
            log.error("GymMembershipDto wasn't create " + e);
        }
        return gymMembershipDto;
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        Order existing = orderDao.get(orderDto.getId());
        if (existing != null && existing.getId() != orderDto.getId()) {
            log.error("Order with id " + orderDto.getId() + " already exists");
            throw new RuntimeException("Order with id " + orderDto.getId() + " already exists");
        }
        Order order = toOrderForUpdate(orderDto);
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
        Order order = toOrderForUpdate(orderDto);
        Order createdOrder = orderDao.addFeedback(order);
        return toDto(createdOrder);
    }


    @Override
    public void delete(Long id) {
        if(!orderDao.delete(id)) {
            throw new RuntimeException("Couldn't delete user with id " + id);
        };
    }

    private Order toOrderForUpdate(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setDateOfOrder(orderDto.getDateOfOrder());
        order.setUserId(orderDto.getUserId());
        order.setTotalCost(orderDto.getTotalCost());
        Status status = Status.valueOf(orderDto.getStatusDto().toString());
        order.setStatus(status);
        order.setFeedback(orderDto.getFeedback());
        List<OrderInfoDto> detailsDto = orderDto.getDetails();
        List<OrderInfo> details = new ArrayList<>();
        for(OrderInfoDto orderInfoDto: detailsDto) {
            OrderInfo orderInfo = toOrderInfo(orderInfoDto);
            details.add(orderInfo);
        }
        order.setDetails(details);
        return order;
    }
    
    private Order toOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setDateOfOrder(orderDto.getDateOfOrder());
        order.setUserId(orderDto.getUserId());
        order.setTotalCost(orderDto.getTotalCost());
        Status status = Status.valueOf(orderDto.getStatusDto().toString());
        order.setStatus(status);
        order.setFeedback(orderDto.getFeedback());
        List<OrderInfoDto> detailsDto = orderDto.getDetails();
        List<OrderInfo> details = new ArrayList<>();
        for(OrderInfoDto orderInfoDto: detailsDto) {
            OrderInfo orderInfo = toOrderInfo(orderInfoDto);
            details.add(orderInfo);
        }
        order.setDetails(details);
        return order;
    }

    private OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        try {
            orderDto.setId(order.getId());
            orderDto.setDateOfOrder(order.getDateOfOrder());
            orderDto.setUserId(order.getUserId());
            orderDto.setTotalCost(order.getTotalCost());
            StatusDto statusDto = StatusDto.valueOf(order.getStatus().toString());
            orderDto.setStatusDto(statusDto);
            orderDto.setFeedback(order.getFeedback());
            List<OrderInfo> details = order.getDetails();
            List<OrderInfoDto> detailsDto = new ArrayList<>();
            for(OrderInfo orderInfo: details) {
                OrderInfoDto orderInfoDto = toOrderInfoDto(orderInfo);
                detailsDto.add(orderInfoDto);
            }
            orderDto.setDetails(detailsDto);
        } catch (NullPointerException e) {
            log.error("OrderDto wasn't create " + e);
        }
        return orderDto;
    }

    private OrderInfo toOrderInfo(OrderInfoDto orderInfoDto) {
        OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setId(orderInfoDto.getId());
//        orderInfo.setOrderId(orderInfoDto.getOrderId());
        orderInfo.setGymMembership(toGymMembership(orderInfoDto.getGymMembershipDto()));
        orderInfo.setGymMembershipQuantity(orderInfoDto.getGymMembershipQuantity());
        orderInfo.setGymMembershipPrice(orderInfoDto.getGymMembershipPrice());
        return orderInfo;
    }
    
    private OrderInfoDto toOrderInfoDto(OrderInfo orderInfo) {
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        try {
//            orderInfoDto.setId(orderInfo.getId());
            orderInfoDto.setGymMembershipDto(toGymMembershipDto(orderInfo.getGymMembership()));
            orderInfoDto.setOrderId(orderInfo.getOrderId());
            orderInfoDto.setGymMembershipQuantity(orderInfo.getGymMembershipQuantity());
            orderInfoDto.setGymMembershipPrice(orderInfo.getGymMembershipPrice());

        } catch (NullPointerException e) {
            log.error("OrderInfoDto wasn't create " + e);
        }
        return orderInfoDto;
    }


    private GymMembership toGymMembership(GymMembershipDto gymMembershipDto) {
        GymMembership gymMembership = new GymMembership();
        gymMembership.setId(gymMembershipDto.getId());
        gymMembership.setNumberOfVisits(gymMembershipDto.getNumberOfVisits());
        gymMembership.setTypeOfTraining(gymMembershipDto.getTypeOfTraining());
        gymMembership.setCost(gymMembershipDto.getCost());
        return gymMembership;
    }

}
