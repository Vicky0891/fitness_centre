package service.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TrainerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String phoneNumber;
    private List<CustomerDto> listOfCustomersDto;
    private String additionalInfo;

}
