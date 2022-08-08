package dao.entity;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class GymMembership {
    
    private Long id;
    private Integer numberOfVisits;
    private String typeOfTraining;
    private BigDecimal cost;

}
