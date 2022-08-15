package service.impl;

import java.util.List;

import dao.entity.Order.Status;
import dao.entity.Prescription;
import dao.interfaces.PrescriptionDao;
import lombok.extern.log4j.Log4j2;
import service.PrescriptionService;
import service.dto.OrderDto.StatusDto;
import service.dto.PrescriptionDto;
@Log4j2
public class PrescriptionServiceImpl implements PrescriptionService {

    private PrescriptionDao prescriptionDao;

    public PrescriptionServiceImpl(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    @Override
    public PrescriptionDto getById(Long id) {
        Prescription prescription = prescriptionDao.get(id);
        if(prescription == null) {
            throw new RuntimeException("No prescription for client with id=" + id);
        }
        PrescriptionDto prescriptionDto = toDto(prescription);
        return prescriptionDto;
    }

    @Override
    public List<PrescriptionDto> getAll() {
        return prescriptionDao.getAll().stream().map(e -> toDto(e)).toList();
    }
    
    @Override
    public PrescriptionDto create(PrescriptionDto prescriptionDto) {
        Prescription prescription = toPrescription(prescriptionDto);
        Prescription createdPrescription = prescriptionDao.create(prescription);
        return toDto(createdPrescription);
    }

    @Override
    public PrescriptionDto update(PrescriptionDto prescriptionDto) {
        Prescription existing = prescriptionDao.get(prescriptionDto.getId());
        if (existing != null && existing.getId() != prescriptionDto.getId()) {
            log.error("Prescription with id \" + prescriptionDto.getId() + \" already exists");
            throw new RuntimeException("Prescription with id \" + prescriptionDto.getId() + \" already exists");
        }
        Prescription prescription = toPrescription(prescriptionDto);
        Prescription createdPrescription = prescriptionDao.update(prescription);
        return toDto(createdPrescription);
    }

    @Override
    public void delete(Long id) {
        prescriptionDao.delete(prescriptionDao.get(id));
    }

    private Prescription toPrescription(PrescriptionDto prescriptionDto) {
        Prescription prescription = new Prescription();
        prescription.setId(prescriptionDto.getId());
        prescription.setUserId(prescriptionDto.getUserId());
        prescription.setTrainerId(prescriptionDto.getTrainerId());
        prescription.setTypeOfTraining(prescriptionDto.getTypeOfTraining());
        prescription.setEquipment(prescriptionDto.getEquipment());
        prescription.setDiet(prescriptionDto.getDiet());
        Status status = Status.valueOf(prescriptionDto.getStatusDto().toString());
        prescription.setStatus(status);
        return prescription;
    }

    private PrescriptionDto toDto(Prescription prescription) {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        try {
            prescriptionDto.setId(prescription.getId());
            prescriptionDto.setUserId(prescription.getUserId());
            prescriptionDto.setTrainerId(prescription.getTrainerId());
            prescriptionDto.setTypeOfTraining(prescription.getTypeOfTraining());
            prescriptionDto.setEquipment(prescription.getEquipment());
            prescriptionDto.setDiet(prescription.getDiet());
            StatusDto statusDto = StatusDto.valueOf(prescription.getStatus().toString());
            prescriptionDto.setStatusDto(statusDto);
        } catch (NullPointerException e) {
            log.error("PrescriptionDto wasn't create " + e);
        }
        return prescriptionDto;
    }

}
