package dao.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Client extends User{
    
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private Type type;
    private Long trainerId;
    private String additionalInfo;
    
    public enum Type {
        NEW, REGULAR, CORPORATE
    }
    
    public Client () {
        super();
    }

}
