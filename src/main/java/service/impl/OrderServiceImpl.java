package service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.DaoException;
import controller.util.exception.impl.NotFoundException;
import dao.entity.Order;
import dao.entity.OrderInfo;
import dao.entity.Client;
import dao.entity.GymMembership;
import dao.entity.Order.Status;
import dao.interfaces.ClientDao;
import dao.interfaces.GymMembershipDao;
import dao.interfaces.OrderDao;
import lombok.extern.log4j.Log4j2;
import service.OrderService;
import service.dto.OrderDto;
import service.dto.UserDto;
import service.dto.GymMembershipDto;
import service.dto.OrderDto.StatusDto;
import service.dto.OrderInfoDto;

@Log4j2
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private GymMembershipDao gymMembershipDao;
    private ClientDao clientDao;

    public OrderServiceImpl(OrderDao orderDao, GymMembershipDao gymMembershipDao, ClientDao clientDao) {
        this.orderDao = orderDao;
        this.gymMembershipDao = gymMembershipDao;
        this.clientDao = clientDao;
    }

    @Override
    public OrderDto getById(Long id) {
        Order order = orderDao.get(id);
        if (order == null) {
            log.error("Trying to get not existing order, order id={}", id);
            throw new NotFoundException("Order with id " + id + " not found");
        }
        return toDto(order);
    }

    @Override
    public List<OrderDto> getAll() {
        return orderDao.getAll().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public List<OrderDto> getAll(Paging paging) {
        return orderDao.getAll(paging.getLimit(), paging.getOffset()).stream().map(e -> toDto(e)).toList();
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
        return toDto(createdOrder);
    }

    @Override
    public OrderDto processCart(Map<Long, Integer> cart, UserDto userDto) {
        OrderDto orderDto = createDto(cart, userDto);
        return orderDto;
    }

    /**
     * Method for creating an order based on the items in the cart
     * 
     * @param cart    Cart with order items
     * @param userDto Client who make order
     * @return OrderDto Order ready to pay
     * @throws Exception
     */
    public OrderDto createDto(Map<Long, Integer> cart, UserDto userDto) {
        OrderDto orderDto = new OrderDto();
        orderDto.setStatusDto(StatusDto.PENDING);
        orderDto.setDateOfOrder(LocalDate.now());
        List<OrderInfoDto> details = new ArrayList<>();
        cart.forEach((gymmembershipId, quantity) -> {
            OrderInfoDto orderInfoDto = new OrderInfoDto();
            GymMembership gymMembership;
            try {
                gymMembership = gymMembershipDao.get(gymmembershipId);
                GymMembershipDto gymmembershipDto = toGymMembershipDto(gymMembership);
                orderInfoDto.setGymMembershipDto(gymmembershipDto);
                orderInfoDto.setGymMembershipPrice(gymmembershipDto.getCost());
                orderInfoDto.setGymMembershipQuantity(quantity);
            } catch (DaoException e) {
                log.error("SQL Exception: " + e);
            }
            details.add(orderInfoDto);
        });
        orderDto.setDetails(details);
        BigDecimal totalCost = calculatePrice(details);
        orderDto.setFeedback("");
        if (userDto != null) {
            totalCost = calculateDiscount(userDto, totalCost);
            orderDto.setUserId(userDto.getId());
        }
        orderDto.setTotalCost(totalCost);
        return orderDto;
    }

    /**
     * Method calculate discount for Client depending on type
     * 
     * @param userDto   Client to get discount
     * @param totalCost Total cost of order before get discount
     * @return total cost with discount
     * @throws Exception
     */
    private BigDecimal calculateDiscount(UserDto userDto, BigDecimal totalCost) {
        Client existingClient = clientDao.get(userDto.getId());
        if (existingClient == null) {
            return totalCost;
        }
        String typeOfClient = existingClient.getType().toString();
        int discount = orderDao.getDiscount(typeOfClient);
        double totalCostInDouble = totalCost.doubleValue();
        totalCostInDouble = (totalCost.doubleValue() - ((totalCost.doubleValue() / (double) 100) * (double) discount));
        return BigDecimal.valueOf(totalCostInDouble);
    }

    /**
     * Method calculate total price for all items of order
     * 
     * @param details List items of order
     * @return total cost
     */
    private BigDecimal calculatePrice(List<OrderInfoDto> details) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderInfoDto detail : details) {
            BigDecimal gymmembershipPrice = detail.getGymMembershipPrice();
            BigDecimal itemPrice = gymmembershipPrice.multiply(BigDecimal.valueOf(detail.getGymMembershipQuantity()));
            totalCost = totalCost.add(itemPrice);
        }
        return totalCost;
    }

    /**
     * Method transforming gymMembership to GymMembershipDto
     * 
     * @param gymMembership Object for transforming
     * @return transformed Object
     */
    private GymMembershipDto toGymMembershipDto(GymMembership gymMembership) {
        GymMembershipDto gymMembershipDto = new GymMembershipDto();
        try {
            gymMembershipDto.setId(gymMembership.getId());
            gymMembershipDto.setNumberOfVisits(gymMembership.getNumberOfVisits());
            gymMembershipDto.setTypeOfTraining(gymMembership.getTypeOfTraining());
            gymMembershipDto.setCost(gymMembership.getCost());
        } catch (NullPointerException e) {
            log.error("GymMembershipDto wasn't create, gymmembership={} ", gymMembership);
        }
        return gymMembershipDto;
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        Order existing = orderDao.get(orderDto.getId());
        if (existing != null && existing.getId() != orderDto.getId()) {
            log.error("Trying to update not existing or incorrect order, order={}", orderDto);
            throw new RuntimeException("Trying to update not existing or incorrect order id=" + orderDto.getId());
        }
        Order order = toOrderForUpdate(orderDto);
        Order createdOrder = orderDao.update(order);
        return toDto(createdOrder);
    }

    @Override
    public OrderDto addFeedback(OrderDto orderDto) {
        Order existing = orderDao.get(orderDto.getId());
        if (existing != null && existing.getId() != orderDto.getId()) {
            log.error("Trying to add feedback to not existing or incorrect order, order={}", orderDto);
            throw new RuntimeException(
                    "Trying to add feedback to not existing or incorrect order, order={}\", orderDto");
        }
        Order order = toOrderForUpdate(orderDto);
        Order createdOrder = orderDao.addFeedback(order);
        return toDto(createdOrder);
    }

    @Override
    public void delete(Long id) {
        if (!orderDao.delete(id)) {
            log.error("Order wasn't delete, order id={}", id);
            throw new DaoException(
                    "Something went wrong. Order id=" + id + "wasn't delete. Contact your system administrator.");
        }
    }

    /**
     * Method transforming OrderDto to Order for use to update
     * 
     * @param orderDto Object for transforming
     * @return transformed Object
     */
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
        for (OrderInfoDto orderInfoDto : detailsDto) {
            OrderInfo orderInfo = toOrderInfo(orderInfoDto);
            details.add(orderInfo);
        }
        order.setDetails(details);
        return order;
    }

    /**
     * Method transforming OrderDto to Order
     * 
     * @param orderDto Object for transforming
     * @return transformed Object
     */
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
        for (OrderInfoDto orderInfoDto : detailsDto) {
            OrderInfo orderInfo = toOrderInfo(orderInfoDto);
            details.add(orderInfo);
        }
        order.setDetails(details);
        return order;
    }

    /**
     * Method transforming Order to OrderDto
     * 
     * @param order Object for transforming
     * @return transformed Object
     */
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
            for (OrderInfo orderInfo : details) {
                OrderInfoDto orderInfoDto = toOrderInfoDto(orderInfo);
                detailsDto.add(orderInfoDto);
            }
            orderDto.setDetails(detailsDto);
        } catch (NullPointerException e) {
            log.error("OrderDto wasn't create, order={} ", order);
        }
        return orderDto;
    }

    /**
     * Method transforming OrderInfoDto to OrderInfo
     * 
     * @param orderInfoDto Object for transforming
     * @return transformed Object
     */
    private OrderInfo toOrderInfo(OrderInfoDto orderInfoDto) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGymMembership(toGymMembership(orderInfoDto.getGymMembershipDto()));
        orderInfo.setGymMembershipQuantity(orderInfoDto.getGymMembershipQuantity());
        orderInfo.setGymMembershipPrice(orderInfoDto.getGymMembershipPrice());
        return orderInfo;
    }

    /**
     * Method transforming OrderInfo to OrderInfoDto
     * 
     * @param orderInfo Object for transforming
     * @return transformed Object
     */
    private OrderInfoDto toOrderInfoDto(OrderInfo orderInfo) {
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        try {
            orderInfoDto.setGymMembershipDto(toGymMembershipDto(orderInfo.getGymMembership()));
            orderInfoDto.setOrderId(orderInfo.getOrderId());
            orderInfoDto.setGymMembershipQuantity(orderInfo.getGymMembershipQuantity());
            orderInfoDto.setGymMembershipPrice(orderInfo.getGymMembershipPrice());

        } catch (NullPointerException e) {
            log.error("OrderInfoDto wasn't create, order={}", orderInfo);
        }
        return orderInfoDto;
    }

    /**
     * Method transforming gymMembershipDto to GymMembership
     * 
     * @param gymMembershipDto Object for transforming
     * @return transformed Object
     */
    private GymMembership toGymMembership(GymMembershipDto gymMembershipDto) {
        GymMembership gymMembership = new GymMembership();
        gymMembership.setId(gymMembershipDto.getId());
        gymMembership.setNumberOfVisits(gymMembershipDto.getNumberOfVisits());
        gymMembership.setTypeOfTraining(gymMembershipDto.getTypeOfTraining());
        gymMembership.setCost(gymMembershipDto.getCost());
        return gymMembership;
    }

    @Override
    public long count() {
        return orderDao.count();
    }

}
