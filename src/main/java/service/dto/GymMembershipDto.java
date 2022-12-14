package service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GymMembershipDto {

    private Long id;
    private Integer numberOfVisits;
    private String typeOfTraining;
    private BigDecimal cost;

}
