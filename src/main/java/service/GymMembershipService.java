package service;

import java.util.List;

import controller.util.PagingUtil.Paging;
import service.dto.GymMembershipDto;

public interface GymMembershipService extends AbstractService<Long, GymMembershipDto>{

    List<GymMembershipDto> getAll(Paging paging);

    long count();

}
