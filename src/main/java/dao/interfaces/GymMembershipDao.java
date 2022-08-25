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
    List<GymMembership> getAll(int limit, Long offset) throws DaoException;

    /**
     * Method count all gymmemberships
     * 
     * @return quantity of gymmemberships
     * @throws DaoException
     */
    long count() throws DaoException;

    GymMembership get(Long gymMembershipId, Connection connection) throws DaoException;

}
