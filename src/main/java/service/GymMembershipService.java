package service;

import java.util.List;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.DaoException;
import service.dto.GymMembershipDto;

public interface GymMembershipService extends AbstractService<Long, GymMembershipDto> {

    /**
     * Method get list of GymMembershipDto for pagination
     * 
     * @param paging Object pass parameters for pagination
     * @return list of GymMembershipDto
     * @throws DaoException
     */
    List<GymMembershipDto> getAll(Paging paging) throws Exception;

    /**
     * Method get quantity of gymmemberships from Dao
     * 
     * @return quantity of gymmemberships
     * @throws DaoException
     */
    long count() throws DaoException;

}
