package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.GymMembership;

public interface GymMembershipDao {

    GymMembership get(Long id) throws DaoException;

    List<GymMembership> getAll() throws DaoException;

    List<GymMembership> getAll(int limit, Long offset) throws DaoException;
    
    GymMembership create(GymMembership gymMembership) throws DaoException;

    GymMembership update(GymMembership gymMembership) throws DaoException;

    boolean delete(Long id) throws DaoException;
    
    long count() throws DaoException;

}
