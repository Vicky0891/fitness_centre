package service;

import java.util.List;

import controller.util.PagingUtil.Paging;
import service.dto.GymMembershipDto;

public interface GymMembershipService extends AbstractService<Long, GymMembershipDto>{

    @Override
    GymMembershipDto getById(Long id);

    @Override
    List<GymMembershipDto> getAll();
    
    List<GymMembershipDto> getAll(Paging paging);

    @Override
    GymMembershipDto create(GymMembershipDto gymMembershipDto);

    @Override
    GymMembershipDto update(GymMembershipDto gymMembershipDto);

    @Override
    void delete(Long id);
    
    long count();

}
