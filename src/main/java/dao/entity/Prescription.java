package dao.entity;

import dao.entity.Order.Status;
import lombok.Data;

@Data
public class Prescription {
    
    private Long id;
    private Customer customer;
    private Trainer trainer;
    private String typeOfTraining;
    private String equipment;
    private String diet;
    private Status status;
    private Order order;
    
}
