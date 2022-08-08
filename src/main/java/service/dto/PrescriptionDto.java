package service.dto;

import lombok.Data;
import service.dto.OrderDto.StatusDto;

@Data
public class PrescriptionDto {

    private Long id;
    private Long userId;
    private Long trainerId;
    private String typeOfTraining;
    private String equipment;
    private String diet;
    private StatusDto statusDto;

}
