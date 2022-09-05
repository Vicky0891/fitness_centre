package dao.interfaces;

import java.sql.Connection;
import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.GymMembership;

public interface GymMembershipDao extends AbstractDao<Long, GymMembership> {

    /**
     * Method get list of objects starting from begin position in the table
     * 
     * @param limit  number of records in the table
     * @param offset starting position for search in the table
     * @return list of gymmemberships
     * @throws DaoException
     */
    List<GymMembership> getAll(int limit, Long offset);

    /**
     * Method count all gymmemberships
     * 
     * @return quantity of gymmemberships
     * @throws DaoException
     */
    long count();

    /**
     * Method find Gymmembership in the data source by id
     * 
     * @param id         Gymmembership id to be get
     * @param connection Connection to be used
     * @return Gymmembership
     * @throws DaoException
     */
    GymMembership get(Long id, Connection connection);

}
