package controller.util;

import controller.util.exception.impl.NotFoundException;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.TrainerService;
import service.dto.ClientDto;
import service.dto.TrainerDto;
import service.dto.UserDto;

@Log4j2
public class UserRoleManager {
    private ClientService clientService;
    private TrainerService trainerService;

    public UserRoleManager(ClientService clientService, TrainerService trainerService) {
        this.clientService = clientService;
        this.trainerService = trainerService;
    }

    /**
     * Method to get object particular class depending on it role
     * 
     * @param userDto Any user in system
     * @return Class Object according to the role
     * @throws Exception
     */
    public Object getUserRole(UserDto userDto) {
        switch (userDto.getRoleDto().toString()) {
        case "CLIENT":
            try {
                ClientDto clientDto = clientService.getById(userDto.getId());
                if (clientDto != null) {
                    return clientDto;
                }
            } catch (NotFoundException e) {
                log.error("Client not exists or something went wrong. Exception: " + e);
            }
        case "TRAINER":
            try {
                TrainerDto trainerDto = trainerService.getById(userDto.getId());
                if (trainerDto != null) {
                    return trainerDto;
                }
            } catch (NotFoundException e) {
                log.error("Client not exists or something went wrong. Exception: " + e);
            }
        case "ADMIN":
            return userDto;
        }
        return userDto;
    }
}