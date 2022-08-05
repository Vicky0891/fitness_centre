package dao.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderInfo {
    
    private Long id;
    private GymMembership gymMembership;
    private Integer gymMembershipQuantity;
    private BigDecimal gymMembershipPrice;
    private Long orderId;

}
