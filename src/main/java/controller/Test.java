package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.connection.DataSource;
import dao.factory.DaoFactory;
import dao.interfaces.GymMembershipDao;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderInfoDao;
import dao.interfaces.PrescriptionDao;
import dao.interfaces.UserDao;
import service.GymMembershipService;
import service.OrderInfoService;
import service.OrderService;
import service.PrescriptionService;
import service.UserService;
import service.dto.OrderDto;
import service.dto.OrderInfoDto;
import service.dto.OrderDto.StatusDto;
import service.impl.GymMembershipServiceImpl;
import service.impl.OrderInfoServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.PrescriptionServiceImpl;
import service.impl.UserServiceImpl;

public class Test {
//    public static void main(String[] args) {
//        DataSource ds = DataSource.INSTANCE;
//        UserServiceImpl usi = new UserServiceImpl(DaoFactory.INSTANCE.getDao(UserDao.class));
//        GymMembershipServiceImpl gi = new GymMembershipServiceImpl(DaoFactory.INSTANCE.getDao(GymMembershipDao.class));
//        OrderInfoServiceImpl oisi = new OrderInfoServiceImpl(DaoFactory.INSTANCE.getDao(OrderInfoDao.class));
//        PrescriptionServiceImpl psi = new PrescriptionServiceImpl(DaoFactory.INSTANCE.getDao(PrescriptionDao.class));
//        OrderServiceImpl osi = new OrderServiceImpl(DaoFactory.INSTANCE.getDao(OrderDao.class), (DaoFactory.INSTANCE.getDao(GymMembershipDao.class)), (DaoFactory.INSTANCE.getDao(OrderInfoDao.class)));
//        
//        OrderDto orderDto = new OrderDto();
//        orderDto.setUserId((long)2);
//        orderDto.setDateOfOrder(LocalDate.now());
//        orderDto.setFeedback("");
//        orderDto.setStatusDto(StatusDto.CONFIRMED);
//        orderDto.setTotalCost(BigDecimal.valueOf(2));
//        OrderInfoDto oid = new OrderInfoDto();
//        oid.setOrderId((long)12);
//        
//        OrderInfoDto oid2 = new OrderInfoDto();
//        oid2.setOrderId((long)12);
//        List<OrderInfoDto> list = new ArrayList<>();
//        list.add(oid);
//        list.add(oid2);
//        orderDto.setDetails(list);
//        
//        osi.create(orderDto);
//        
//        System.out.println(orderDto.toString());
//    }

}
