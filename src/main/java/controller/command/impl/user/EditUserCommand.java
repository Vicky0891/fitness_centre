package controller.command.impl.user;

import java.time.LocalDate;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public String execute(HttpServletRequest req) throws Exception {
        Long userId = Long.parseLong(req.getParameter("id"));
        UserDto currentUserDto = userService.getById(userId);
        String role = req.getParameter("role");
        currentUserDto.setRoleDto(RoleDto.valueOf(role));
        UserDto updated = userService.update(currentUserDto);
        if (role.equals("TRAINER")) {
            TrainerDto trainerDto = new TrainerDto();
            trainerDto.setId(updated.getId());
            trainerDto.setRoleDto(updated.getRoleDto());
            trainerDto.setBirthDate(LocalDate.parse("0001-01-01"));
            trainerService.create(trainerDto);
        }
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.update.user"));
        return "redirect:controller?command=all_users";
    }

}
