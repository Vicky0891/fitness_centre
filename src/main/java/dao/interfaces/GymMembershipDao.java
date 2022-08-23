package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.GymMembership;

public interface GymMembershipDao extends AbstractDao<Long, GymMembership> {

    List<GymMembership> getAll(int limit, Long offset) throws DaoException;

    long count() throws DaoException;

}
