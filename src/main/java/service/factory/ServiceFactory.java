package service.factory;

import java.util.HashMap;
import java.util.Map;

import dao.factory.DaoFactory;
import dao.interfaces.ClientDao;
import dao.interfaces.GymMembershipDao;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderInfoDao;
import dao.interfaces.PrescriptionDao;
import dao.interfaces.TrainerDao;
import dao.interfaces.UserDao;
import service.ClientService;
import service.GymMembershipService;
import service.OrderInfoService;
import service.OrderService;
import service.PrescriptionService;
import service.TrainerService;
import service.UserService;
import service.impl.ClientServiceImpl;
import service.impl.GymMembershipServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.PrescriptionServiceImpl;
import service.impl.TrainerServiceImpl;
import service.impl.UserServiceImpl;

public class ServiceFactory {
    private Map<Class<?>, Object> map;
    public static final ServiceFactory INSTANCE = new ServiceFactory();

    private ServiceFactory() {
        map = new HashMap<>();
        map.put(UserService.class, new UserServiceImpl(DaoFactory.INSTANCE.getDao(UserDao.class)));
        map.put(TrainerService.class, new TrainerServiceImpl(DaoFactory.INSTANCE.getDao(TrainerDao.class)));
        map.put(ClientService.class, new ClientServiceImpl(DaoFactory.INSTANCE.getDao(ClientDao.class)));
        map.put(GymMembershipService.class,
                new GymMembershipServiceImpl(DaoFactory.INSTANCE.getDao(GymMembershipDao.class)));
        map.put(OrderInfoService.class, new PrescriptionServiceImpl(DaoFactory.INSTANCE.getDao(PrescriptionDao.class)));
        map.put(OrderService.class,
                new OrderServiceImpl(DaoFactory.INSTANCE.getDao(OrderDao.class),
                        (DaoFactory.INSTANCE.getDao(GymMembershipDao.class)),
                        (DaoFactory.INSTANCE.getDao(OrderInfoDao.class)), (DaoFactory.INSTANCE.getDao(ClientDao.class))));
        map.put(PrescriptionService.class, new PrescriptionServiceImpl(DaoFactory.INSTANCE.getDao(PrescriptionDao.class)));
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> clazz) {
        return (T) map.get(clazz);
    }

}
