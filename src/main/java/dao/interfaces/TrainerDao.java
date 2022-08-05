package dao.interfaces;

import java.util.List;

import dao.entity.Customer;
import dao.entity.Trainer;

public interface TrainerDao {
    
    Trainer get(Long id);

    List<Trainer> getAll();
    
    Trainer create(Trainer trainer);

    Trainer update(Trainer trainer);
    
    List<Customer> getCustomersForTrainer(Long id);
    
    Trainer getTrainerByEmail(String email);

    boolean delete(Long id);

}
