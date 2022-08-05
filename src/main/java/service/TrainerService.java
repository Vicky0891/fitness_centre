package service;

import java.util.List;

import service.dto.CustomerDto;
import service.dto.TrainerDto;

public interface TrainerService extends AbstractService<Long, TrainerDto> {

    @Override
    TrainerDto getById(Long id);

    @Override
    List<TrainerDto> getAll();

    @Override
    TrainerDto create(TrainerDto trainerDto);

    @Override
    TrainerDto update(TrainerDto trainerDto);

    List<CustomerDto> getCustomersDtoForTrainer(Long id);

    @Override
    void delete(Long id);

}
