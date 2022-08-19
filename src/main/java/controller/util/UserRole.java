package controller.util;

import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.TrainerService;
import service.dto.ClientDto;
import service.dto.TrainerDto;
import service.dto.UserDto;

@Log4j2
public class UserRole {
    private ClientService clientService;
    private TrainerService trainerService;

    public UserRole(ClientService clientService, TrainerService trainerService) {
        this.clientService = clientService;
        this.trainerService = trainerService;
    }

    public Object getUserRole(UserDto userDto) throws Exception {

        switch (userDto.getRoleDto().toString()) {
        case "CLIENT":
            try {
                ClientDto clientDto = clientService.getById(userDto.getId());
                if (clientDto != null) {
                    return clientDto;
                }
            } catch (RuntimeException e) {
                log.error("Client not exists or something went wrong. Exception: " + e);
            }
        case "TRAINER":
            try {
                TrainerDto trainerDto = trainerService.getById(userDto.getId());
                if (trainerDto != null) {
                    return trainerDto;
                }
            } catch (RuntimeException e) {
                log.error("Trainer not exists or something went wrong. Exception: " + e);
            }
        case "ADMIN":
            return userDto;
        }
        return userDto;
    }
}
