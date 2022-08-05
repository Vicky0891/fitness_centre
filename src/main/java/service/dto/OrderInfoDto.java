package service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderInfoDto {

    private Long id;
    private GymMembershipDto gymMembershipDto;
    private Integer gymMembershipQuantity;
    private BigDecimal gymMembershipPrice;
    private Long orderId;

}
