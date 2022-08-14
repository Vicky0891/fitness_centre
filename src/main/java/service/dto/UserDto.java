package service.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private RoleDto roleDto;

    public enum RoleDto {
        ADMIN, CLIENT, TRAINER
    }

}
