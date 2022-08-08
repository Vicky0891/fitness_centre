package service.dto;

import lombok.Data;

@Data
public class UserDto {
    
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private TypeDto typeDto;
    private RoleDto roleDto;
    private Long trainerId;

    public enum RoleDto {
        ADMIN, CLIENT, TRAINER
    }
        
    public enum TypeDto {
        NEW, REGULAR, CORPORATE, OTHER
    }
    
}
