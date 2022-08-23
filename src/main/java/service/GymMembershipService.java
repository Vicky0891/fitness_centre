package service;

import java.util.List;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.DaoException;
import service.dto.GymMembershipDto;

public interface GymMembershipService extends AbstractService<Long, GymMembershipDto>{

    List<GymMembershipDto> getAll(Paging paging) throws Exception;
    
    long count() throws DaoException;

}
