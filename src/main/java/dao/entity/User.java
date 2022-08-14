package dao.entity;

import lombok.Data;

@Data
public class User {
    
    private Long id;
    private String email;
    private String password;
    private Role role;
    
    public enum Role {
        ADMIN, CLIENT, TRAINER
    }
        
}
