package dao.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Trainer extends User{
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String phoneNumber;
    private List<Customer> listOfCustomers;
    private String additionalInfo;
        
}
