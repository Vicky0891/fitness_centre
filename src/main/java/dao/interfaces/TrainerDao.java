package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.Client;
import dao.entity.Trainer;

public interface TrainerDao {
    
    Trainer get(Long id) throws DaoException;

    List<Trainer> getAll() throws DaoException;

    List<Client> getAllClientsByTrainer(Long trainerId) throws DaoException;

    Trainer create(Trainer trainer) throws DaoException;

    Trainer update(Trainer trainer) throws DaoException;

    boolean delete(Long id) throws DaoException;

}
