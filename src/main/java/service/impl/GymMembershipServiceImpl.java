package service.impl;

import java.util.List;

import controller.util.PagingUtil.Paging;
import dao.entity.GymMembership;
import dao.interfaces.GymMembershipDao;
import lombok.extern.log4j.Log4j2;
import service.GymMembershipService;
import service.dto.GymMembershipDto;
@Log4j2
public class GymMembershipServiceImpl implements GymMembershipService {

    private GymMembershipDao gymMembershipDao;

    public GymMembershipServiceImpl(GymMembershipDao gymMembershipDao) {
        this.gymMembershipDao = gymMembershipDao;
    }

    @Override
    public GymMembershipDto getById(Long id) {
        GymMembership gymMembership = gymMembershipDao.get(id);
        if(gymMembership == null) {
            throw new RuntimeException("No gymMembership with id " + id);
        }
        GymMembershipDto gymMembershipDto = toDto(gymMembership);
        return gymMembershipDto;
    }

    @Override
    public List<GymMembershipDto> getAll() {
        return gymMembershipDao.getAll().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public GymMembershipDto create(GymMembershipDto gymMembershipDto) {
        GymMembership existing = gymMembershipDao.get(gymMembershipDto.getId());
        if (existing != null) {
            log.error("GymMembership with id " + gymMembershipDto.getId() + " already exists");
            throw new RuntimeException("GymMembership with id " + gymMembershipDto.getId() + " already exists");
        }
        GymMembership gymMembership = toGymMembership(gymMembershipDto);
        GymMembership createdGymMembership = gymMembershipDao.create(gymMembership);
        return toDto(createdGymMembership);
    }

    @Override
    public GymMembershipDto update(GymMembershipDto gymMembershipDto) {
        GymMembership existing = gymMembershipDao.get(gymMembershipDto.getId());
        if (existing != null && existing.getId() != gymMembershipDto.getId()) {
            log.error("GymMembership with id " + gymMembershipDto.getId() + " already exists");
            throw new RuntimeException("GymMembership with id " + gymMembershipDto.getId() + " already exists");
        }
        GymMembership gymMembership = toGymMembership(gymMembershipDto);
        GymMembership createdGymMembership = gymMembershipDao.update(gymMembership);
        return toDto(createdGymMembership);
    }

    @Override
    public void delete(Long id) {
        if(!gymMembershipDao.delete(id)) {
            throw new RuntimeException("Couldn't delete user with id " + id);
        };
    }

    private GymMembership toGymMembership(GymMembershipDto gymMembershipDto) {
        GymMembership gymMembership = new GymMembership();
        gymMembership.setId(gymMembershipDto.getId());
        gymMembership.setNumberOfVisits(gymMembershipDto.getNumberOfVisits());
        gymMembership.setTypeOfTraining(gymMembershipDto.getTypeOfTraining());
        gymMembership.setCost(gymMembershipDto.getCost());
        return gymMembership;
    }

    private GymMembershipDto toDto(GymMembership gymMembership) {
        GymMembershipDto gymMembershipDto = new GymMembershipDto();
        try {
            gymMembershipDto.setId(gymMembership.getId());
            gymMembershipDto.setNumberOfVisits(gymMembership.getNumberOfVisits());
            gymMembershipDto.setTypeOfTraining(gymMembership.getTypeOfTraining());
            gymMembershipDto.setCost(gymMembership.getCost());
        } catch (NullPointerException e) {
            log.error("GymMembershipDto wasn't create " + e);
        }
        return gymMembershipDto;
    }

    @Override
    public List<GymMembershipDto> getAll(Paging paging) {
        return gymMembershipDao.getAll(paging.getLimit(), paging.getOffset()).stream().map(e -> toDto(e)).toList();
    }

    @Override
    public long count() {
        return gymMembershipDao.count();
    }


}
