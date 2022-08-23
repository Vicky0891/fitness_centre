package service;

import java.util.List;

import controller.util.exception.impl.DaoException;
import service.dto.ClientDto;
import service.dto.TrainerDto;

public interface TrainerService extends AbstractService<Long, TrainerDto>{
    
    List<ClientDto> getAllClientsByTrainer(Long trainerId) throws DaoException;

}
