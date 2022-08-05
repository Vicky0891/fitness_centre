package service.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String phoneNumber;
    private String additionalInfo;
    private TypeDto typeDto;
    private TrainerDto trainerDto;
    private List<OrderDto> listOfOrdersDto;

    public enum TypeDto {
        NEW, REGULAR, CORPORATE
    }

}
