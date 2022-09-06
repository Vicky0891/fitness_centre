package fitness_centre.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dao.connection.DataSource;
import dao.entity.User;
import dao.impl.UserDaoImpl;
import dao.interfaces.UserDao;

public class TestUserDaoImpl {

    private final UserDao userDao = new UserDaoImpl(DataSource.INSTANCE);
    private User user;

    @Before
    public void setUserInfo() {
        user = new User();
        user.setId(1L);
        user.setEmail("admin@gmail.com");
        user.setPassword("D033E22AE348AEB5660FC2140AEC35850C4DA997");
        user.setRole(User.Role.ADMIN);
    }

    @Test
    public void get() {
        User actual = userDao.get(1L);
        assertEquals(user, actual);
    }

    @Test
    public void getByEmail() {
        User actual = userDao.getByEmail("admin@gmail.com");
        assertEquals(user, actual);
    }

    @Test
    public void count() {
        Long actual = userDao.count();
        Long expected = 6L;
        assertEquals(expected, actual);
    }

}
