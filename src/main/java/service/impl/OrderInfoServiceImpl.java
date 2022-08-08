package service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.entity.GymMembership;
import dao.entity.OrderInfo;
import dao.interfaces.OrderInfoDao;
import lombok.extern.log4j.Log4j2;
import service.OrderInfoService;
import service.dto.GymMembershipDto;
import service.dto.OrderInfoDto;
@Log4j2
public class OrderInfoServiceImpl implements OrderInfoService {

    private OrderInfoDao orderInfoDao;
    private static final Logger logger = LogManager.getLogger(OrderInfoServiceImpl.class);

    public OrderInfoServiceImpl(OrderInfoDao orderInfoDao) {
        this.orderInfoDao = orderInfoDao;
    }

    @Override
    public OrderInfoDto getById(Long id) {
        OrderInfo orderInfo = orderInfoDao.get(id);
        OrderInfoDto orderInfoDto = toDto(orderInfo);
        return orderInfoDto;
    }

    @Override
    public List<OrderInfoDto> getAll() {
        return orderInfoDao.getAll().stream().map(e -> toDto(e)).toList();
    }
    
    

    @Override
    public OrderInfoDto create(OrderInfoDto orderInfoDto) {
        OrderInfo orderInfo = toOrderInfo(orderInfoDto);
        OrderInfo createdOrderInfo = orderInfoDao.create(orderInfo);
        return toDto(createdOrderInfo);
    }

    @Override
    public OrderInfoDto update(OrderInfoDto orderInfoDto) {
        OrderInfo existing = orderInfoDao.get(orderInfoDto.getId());
        if (existing != null && existing.getId() != orderInfoDto.getId()) {
            logger.error("Order with id " + orderInfoDto.getId() + " already exists");
            throw new RuntimeException("Order with id \" + orderInfoDto.getId() + \" already exists");
        }
        OrderInfo orderInfo = toOrderInfo(orderInfoDto);
        OrderInfo createdOrderInfo = orderInfoDao.update(orderInfo);
        return toDto(createdOrderInfo);
    }

    @Override
    public void delete(Long id) {
        orderInfoDao.delete(id);
    }

    private OrderInfo toOrderInfo(OrderInfoDto orderInfoDto) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderInfoDto.getId());
        orderInfo.setGymMembership(toGymMembership(orderInfoDto.getGymMembershipDto()));
        orderInfo.setOrderId(orderInfoDto.getOrderId());
        orderInfo.setGymMembershipQuantity(orderInfoDto.getGymMembershipQuantity());
        orderInfo.setGymMembershipPrice(orderInfoDto.getGymMembershipPrice());
        return orderInfo;
    }

    private OrderInfoDto toDto(OrderInfo orderInfo) {
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        try {
            orderInfoDto.setId(orderInfo.getId());
            orderInfoDto.setGymMembershipDto(toGymMembershipDto(orderInfo.getGymMembership()));
            orderInfoDto.setOrderId(orderInfo.getOrderId());
            orderInfoDto.setGymMembershipQuantity(orderInfo.getGymMembershipQuantity());
            orderInfoDto.setGymMembershipPrice(orderInfo.getGymMembershipPrice());

        } catch (NullPointerException e) {
            logger.error("OrderInfoDto wasn't create " + e);
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

    private GymMembershipDto toGymMembershipDto(GymMembership gymMembership) {
        GymMembershipDto gymMembershipDto = new GymMembershipDto();
        try {
            gymMembershipDto.setId(gymMembership.getId());
            gymMembershipDto.setNumberOfVisits(gymMembership.getNumberOfVisits());
            gymMembershipDto.setTypeOfTraining(gymMembership.getTypeOfTraining());
            gymMembershipDto.setCost(gymMembership.getCost());
        } catch (NullPointerException e) {
            logger.error("GymMembershipDto wasn't create " + e);
        }
        return gymMembershipDto;
    }

    @Override
    public List<OrderInfoDto> getAllByOrderId(Long orderId) {
        return orderInfoDao.getAllByOrderId(orderId).stream().map(e -> toDto(e)).toList();
    }

    }
