package dao.entity;

import dao.entity.Order.Status;
import lombok.Data;

@Data
public class Prescription {
    
    private Long id;
    private Long userId;
    private Long trainerId;
    private String typeOfTraining;
    private String equipment;
    private String diet;
    private Status status;
    
}
