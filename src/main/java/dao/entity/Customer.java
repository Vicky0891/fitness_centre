package dao.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Customer extends User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String phoneNumber;
    private String additionalInfo;
    private Type type;
    private Trainer trainer;
    private List<Order> listOfOrders;
            
    public enum Type {
        NEW, REGULAR, CORPORATE
    }
    
}
