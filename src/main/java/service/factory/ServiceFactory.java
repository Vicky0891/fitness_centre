package service.factory;

import java.util.HashMap;
import java.util.Map;

import dao.factory.DaoFactory;
import dao.interfaces.ClientDao;
import dao.interfaces.GymMembershipDao;
import dao.interfaces.OrderDao;
import dao.interfaces.PrescriptionDao;
import dao.interfaces.TrainerDao;
import dao.interfaces.UserDao;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.GymMembershipService;
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

@Log4j2
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
        map.put(OrderService.class, new OrderServiceImpl(DaoFactory.INSTANCE.getDao(OrderDao.class),
                (DaoFactory.INSTANCE.getDao(GymMembershipDao.class)), (DaoFactory.INSTANCE.getDao(ClientDao.class))));
        map.put(PrescriptionService.class,
                new PrescriptionServiceImpl(DaoFactory.INSTANCE.getDao(PrescriptionDao.class)));
    }

    /**
     * Method to get service objects by class
     * 
     * @param <T>   Object of requested class
     * @param clazz Name of class to get
     * @return Object of requested class
     */
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> clazz) {
        T service = (T) map.get(clazz);
        if (service == null) {
            log.error("Class {} is not constructed", clazz);
            throw new RuntimeException("Class " + clazz + " is not constructed");
        }
        return (T) map.get(clazz);
    }

}
