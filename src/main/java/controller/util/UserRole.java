package controller.util;

import service.ClientService;
import service.TrainerService;
import service.dto.ClientDto;
import service.dto.TrainerDto;
import service.dto.UserDto;

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
            ClientDto clientDto = clientService.getById(userDto.getId());
            if (clientDto != null) {
                return clientDto;
            }
        case "TRAINER":
            TrainerDto trainerDto = trainerService.getById(userDto.getId());
            if (trainerDto != null) {
                return trainerDto;
            }
        }
        return userDto;
    }
}