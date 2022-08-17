package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.TrainerService;
import service.UserService;
import service.dto.UserDto;
import service.dto.TrainerDto;
import service.dto.UserDto.RoleDto;

public class EditUserCommand implements Command {
    private final UserService userService;
    private final TrainerService trainerService;

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
        if (role.equals("TRAINER")) {
            TrainerDto trainerDto = new TrainerDto();
            trainerDto.setId(updated.getId());
            trainerDto.setRoleDto(updated.getRoleDto());
            try {
                trainerService.create(trainerDto);
            } catch (RuntimeException e) {
                System.out.println(e);
                return "redirect:controller?command=all_users";
            }
        }
        req.setAttribute("message", "User updated");
        return "redirect:controller?command=all_users";
    }

}
