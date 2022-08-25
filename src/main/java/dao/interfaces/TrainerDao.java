package dao.interfaces;

import java.sql.Connection;
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
    
    /**
     * Method get list of clients for particular trainer
     * 
     * @param trainerId id of trainer to get clients
     * @return list of Clients
     * @throws DaoException
     */
    List<Client> getAllClientsByTrainer(Long id, Connection connection) throws DaoException;

    /**
     * Method find Trainer in the data source by id
     * 
     * @param id Trainer id to be get
     * @return Trainer
     * @throws DaoException
     */
    Trainer get(Long id, Connection connection) throws DaoException;

}
