package dao.interfaces;

import java.util.List;

import dao.entity.Client;
import dao.entity.Trainer;

public interface TrainerDao {
    
    Trainer get(Long id);

    List<Trainer> getAll();

    List<Client> getAllClientsByTrainer(Long trainerId);

    Trainer create(Trainer trainer);

    Trainer update(Trainer trainer);

    boolean delete(Long id);

}
