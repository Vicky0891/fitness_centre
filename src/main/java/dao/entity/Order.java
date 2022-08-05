package dao.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Order {
    
    private Long id;
    private LocalDate dateOfOrder;
    private Customer customer;
    private BigDecimal totalCost;
    private Status status;
    private String feedback;
    private List<OrderInfo> details;
//    private Prescription prescription;
    
    public enum Status {
        PENDING, CONFIRMED
    }


}
