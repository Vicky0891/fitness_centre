package dao.interfaces;

import java.util.List;

import dao.entity.Prescription;

public interface PrescriptionDao {
    
    Prescription get(Long id);
    
    List<Prescription> getAll();

    Prescription create(Prescription prescription);

    Prescription update(Prescription prescription);
    
    void delete(Prescription prescription);

}
