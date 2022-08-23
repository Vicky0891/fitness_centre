package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;
import dao.entity.Prescription;

public interface PrescriptionDao {

    Prescription get(Long id) throws DaoException;

    List<Prescription> getAll() throws DaoException;

    Prescription create(Prescription prescription) throws DaoException;

    Prescription update(Prescription prescription) throws DaoException;

    void delete(Prescription prescription) throws DaoException;

}
