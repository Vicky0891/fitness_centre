package dao.factory;

import java.util.HashMap;
import java.util.Map;

import dao.connection.DataSource;
import dao.impl.ClientDaoImpl;
import dao.impl.GymMembershipDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.OrderInfoDaoImpl;
import dao.impl.PrescriptionDaoImpl;
import dao.impl.TrainerDaoImpl;
import dao.impl.UserDaoImpl;
import dao.interfaces.ClientDao;
import dao.interfaces.GymMembershipDao;
import dao.interfaces.OrderDao;
import dao.interfaces.OrderInfoDao;
import dao.interfaces.PrescriptionDao;
import dao.interfaces.TrainerDao;
import dao.interfaces.UserDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DaoFactory {
    private Map<Class<?>, Object> map;
    public static final DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory() {
        map = new HashMap<>();
        map.put(UserDao.class, new UserDaoImpl(DataSource.INSTANCE));
        map.put(ClientDao.class, new ClientDaoImpl(DataSource.INSTANCE));
        map.put(TrainerDao.class, new TrainerDaoImpl(DataSource.INSTANCE, getDao(ClientDao.class)));
        map.put(GymMembershipDao.class, new GymMembershipDaoImpl(DataSource.INSTANCE));
        map.put(PrescriptionDao.class, new PrescriptionDaoImpl(DataSource.INSTANCE));
        map.put(OrderInfoDao.class, new OrderInfoDaoImpl(DataSource.INSTANCE, getDao(GymMembershipDao.class)));
        map.put(OrderDao.class, new OrderDaoImpl(DataSource.INSTANCE, getDao(OrderInfoDao.class)));

    }

    @SuppressWarnings("unchecked")
    public <T> T getDao(Class<T> clazz) {
        T dao = (T) map.get(clazz);
        if (dao == null) {
            log.error("Class {} is not constructed", clazz);
            throw new RuntimeException("Class " + clazz + " is not constructed");
        }
        return (T) map.get(clazz);
    }

}
