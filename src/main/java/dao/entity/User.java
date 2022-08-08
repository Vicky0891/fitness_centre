package dao.entity;

import lombok.Data;

@Data
public class User {
    
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Type type;
    private Role role;
    private Long trainerId;
    
    public enum Role {
        ADMIN, CLIENT, TRAINER
    }
        
    public enum Type {
        NEW, REGULAR, CORPORATE, OTHER
    }
    
}
