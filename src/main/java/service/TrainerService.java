package service;

import java.util.List;

import controller.util.exception.impl.DaoException;
import service.dto.ClientDto;
import service.dto.TrainerDto;

public interface TrainerService extends AbstractService<Long, TrainerDto> {

    /**
     * Method get list of clientsDto for particular trainer
     * 
     * @param trainerId id of trainer to get clients
     * @return list of ClientsDto
     * @throws DaoException
     */
    List<ClientDto> getAllClientsByTrainer(Long trainerId) throws DaoException;

}
