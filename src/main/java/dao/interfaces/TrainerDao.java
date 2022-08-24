package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.Client;
import dao.entity.Trainer;

public interface TrainerDao extends AbstractDao<Long, Trainer> {

    /**
     * Method get list of clients for particular trainer
     * 
     * @param trainerId id of trainer to get clients
     * @return list of Clients
     * @throws DaoException
     */
    List<Client> getAllClientsByTrainer(Long trainerId) throws DaoException;

}
