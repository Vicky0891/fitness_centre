package dao.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;


@Data
public class Trainer extends User{
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String category;
    private List<Client> clients;
    
    public Trainer () {
        super();
    }

}
