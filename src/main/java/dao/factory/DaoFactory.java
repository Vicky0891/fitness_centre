package dao.factory;

import java.util.HashMap;
import java.util.Map;

import dao.connection.DataSource;
import dao.entity.OrderInfo;
import dao.impl.CustomerDaoImpl;
import dao.impl.GymMembershipDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.OrderInfoDaoImpl;
import dao.impl.PrescriptionDaoImpl;
import dao.impl.TrainerDaoImpl;
import dao.impl.UserDaoImpl;
import dao.interfaces.CustomerDao;
import dao.interfaces.GymMembershipDao;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderInfoDao;
import dao.interfaces.PrescriptionDao;
import dao.interfaces.TrainerDao;
import dao.interfaces.UserDao;

public class DaoFactory {
    private Map<Class<?>, Object> map;
    public static final DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory() {
        map = new HashMap<>();
        map.put(UserDao.class, new UserDaoImpl(DataSource.INSTANCE));
        map.put(CustomerDao.class, new CustomerDaoImpl(DataSource.INSTANCE, getDao(TrainerDao.class), getDao(OrderDao.class)));
        map.put(TrainerDao.class, new TrainerDaoImpl(DataSource.INSTANCE, getDao(CustomerDao.class)));
        map.put(GymMembershipDao.class, new GymMembershipDaoImpl(DataSource.INSTANCE, getDao(TrainerDao.class)));
        map.put(OrderInfo.class, new OrderInfoDaoImpl(DataSource.INSTANCE, getDao(GymMembershipDao.class)));
        map.put(PrescriptionDao.class, new PrescriptionDaoImpl(DataSource.INSTANCE, getDao(CustomerDao.class), getDao(TrainerDao.class)));
        map.put(OrderDao.class, new OrderDaoImpl(DataSource.INSTANCE, getDao(CustomerDao.class), getDao(OrderInfoDao.class)));
        
    }

    @SuppressWarnings("unchecked")
    public <T> T getDao(Class<T> clazz) {
        return (T) map.get(clazz);
    }

}
