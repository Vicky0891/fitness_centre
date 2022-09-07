package controller.command.impl.user;

import java.time.LocalDate;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.TrainerService;
import service.UserService;
import service.dto.UserDto;
import service.dto.TrainerDto;
import service.dto.UserDto.RoleDto;

@Log4j2
public class EditUserCommand implements Command {
    private final UserService userService;
    private final TrainerService trainerService;
    private final String DEFAULT_PHOTO = "default_photo.jpg";

    public EditUserCommand(UserService userService, TrainerService trainerService) {
        this.userService = userService;
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long userId = Long.parseLong(req.getParameter("id"));
        UserDto currentUserDto = userService.getById(userId);
        String role = req.getParameter("role");
        currentUserDto.setRoleDto(RoleDto.valueOf(role));
        UserDto updated = userService.update(currentUserDto);
        log.info("User was update, user={}", updated);
        if (role.equals("TRAINER")) {
            createTrainerDto(updated);
            log.info("Trainer was create, trainer id={}", updated.getId());
        }
        return "redirect:controller?command=all_users&update= ";
    }

    /**
     * Method create TrainerDto and added it in data source
     * 
     * @param userDto User in system
     * @return TrainerDto created in data source table
     * @throws Exception
     */
    private void createTrainerDto(UserDto userDto) {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setId(userDto.getId());
        trainerDto.setRoleDto(userDto.getRoleDto());
        trainerDto.setBirthDate(LocalDate.parse("0001-01-01"));
        trainerDto.setPathAvatar(DEFAULT_PHOTO);
        trainerService.create(trainerDto);
    }
}
