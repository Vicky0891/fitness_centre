package service;

import java.util.List;

import service.dto.PrescriptionDto;

public interface PrescriptionService extends AbstractService<Long, PrescriptionDto> {

    @Override
    PrescriptionDto getById(Long id);
    
    @Override
    List<PrescriptionDto> getAll();

    @Override
    PrescriptionDto create(PrescriptionDto prescriptionDto);

    @Override
    PrescriptionDto update(PrescriptionDto prescriptionDto);

    @Override
    void delete(Long id);

}
