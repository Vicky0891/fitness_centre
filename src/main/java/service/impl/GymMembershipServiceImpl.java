package service.impl;

import java.util.List;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.InternalErrorException;
import controller.util.exception.impl.NotFoundException;
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
    public GymMembershipDto getById(Long id) throws Exception {
        GymMembership gymMembership = gymMembershipDao.get(id);
        if (gymMembership == null) {
            log.error("Trying to get not existing gymmembership, gymmembership id={}", id);
            throw new NotFoundException("Gymmembership with id " + id + " not found");
        }
        return toDto(gymMembership);
    }

    @Override
    public List<GymMembershipDto> getAll() {
        return gymMembershipDao.getAll().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public GymMembershipDto create(GymMembershipDto gymMembershipDto) {
        GymMembership gymMembership = toGymMembership(gymMembershipDto);
        GymMembership createdGymMembership = gymMembershipDao.create(gymMembership);
        log.info("Gymmembership was create, gymmembership={}", gymMembershipDto);
        return toDto(createdGymMembership);
    }

    @Override
    public GymMembershipDto update(GymMembershipDto gymMembershipDto) {
        GymMembership existing = gymMembershipDao.get(gymMembershipDto.getId());
        if (existing != null && existing.getId() != gymMembershipDto.getId()) {
            log.error("Trying to update not existing or incorrect gymmembership, gymmembership={}", gymMembershipDto);
            throw new RuntimeException("Trying to update not existing or incorrect gymmembership");
        }
        GymMembership gymMembership = toGymMembership(gymMembershipDto);
        GymMembership createdGymMembership = gymMembershipDao.update(gymMembership);
        log.info("Gymmembership was update, gymmembership={}", gymMembershipDto);
        return toDto(createdGymMembership);
    }

    @Override
    public void delete(Long id) {
        if (!gymMembershipDao.delete(id)) {
            log.error("Gymmembership wasn't delete, gymmembership id={}", id);
            throw new RuntimeException("Couldn't delete user with id " + id);
        }
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
            log.error("GymMembershipDto wasn't create, gymmembership={} ", gymMembership);
        }
        return gymMembershipDto;
    }

    @Override
    public List<GymMembershipDto> getAll(Paging paging) {
        return gymMembershipDao.getAll(paging.getLimit(), paging.getOffset()).stream().map(e -> toDto(e)).toList();
    }

    @Override
    public long count() throws InternalErrorException {
        return gymMembershipDao.count();
    }
}
