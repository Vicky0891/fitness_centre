package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.Client;
import dao.entity.Trainer;

public interface TrainerDao extends AbstractDao<Long, Trainer> {

    List<Client> getAllClientsByTrainer(Long trainerId) throws DaoException;

}
