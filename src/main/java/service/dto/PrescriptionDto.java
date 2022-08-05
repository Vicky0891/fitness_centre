package service.dto;

import lombok.Data;
import service.dto.OrderDto.StatusDto;

@Data
public class PrescriptionDto {

    private Long id;
    private CustomerDto customerDto;
    private TrainerDto trainerDto;
    private String typeOfTraining;
    private String equipment;
    private String diet;
    private StatusDto statusDto;
    private OrderDto orderDto;

}
