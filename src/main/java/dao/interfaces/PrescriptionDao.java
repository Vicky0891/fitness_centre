package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.InternalErrorException;
import dao.entity.Prescription;

public interface PrescriptionDao {
    
    Prescription get(Long id);
    
    List<Prescription> getAll();

    Prescription create(Prescription prescription) throws InternalErrorException;

    Prescription update(Prescription prescription) throws InternalErrorException;
    
    void delete(Prescription prescription);

}
