package service.dto;

import java.time.LocalDate;

import lombok.Data;

    @Data
    public class ClientDto extends UserDto{
        
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private String phoneNumber;
        private TypeDto type;
        private Long trainerId;
        private String additionalInfo;
        
        public enum TypeDto {
            NEW, REGULAR, CORPORATE
        }
        
        public ClientDto () {
            super();
        }

}
