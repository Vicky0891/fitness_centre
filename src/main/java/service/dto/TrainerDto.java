package service.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TrainerDto extends UserDto{
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String category;
    private List<ClientDto> clients;
    
    public TrainerDto () {
        super();
    }

}
