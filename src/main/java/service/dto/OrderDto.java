package service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class OrderDto {

    private Long id;
    private LocalDate dateOfOrder;
    private CustomerDto customerDto;
    private BigDecimal totalCost;
    private StatusDto statusDto;
    private String feedback;
    private List<OrderInfoDto> orderInfoDto;

    public enum StatusDto {
        PENDING, CONFIRMED
    }

}
