package service;

import service.dto.PrescriptionDto;

public interface PrescriptionService extends AbstractService<Long, PrescriptionDto> {

    @Override
    PrescriptionDto getById(Long id);

    @Override
    PrescriptionDto create(PrescriptionDto prescriptionDto);

    @Override
    PrescriptionDto update(PrescriptionDto prescriptionDto);

    @Override
    void delete(Long id);

}
