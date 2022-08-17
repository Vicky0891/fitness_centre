package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.TrainerService;
import service.UserService;
import service.dto.UserDto;

@Log4j2
public class EditUserFormCommand implements Command {
    private UserService userService;

    public EditUserFormCommand(UserService userService, TrainerService trainerService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long userId = Long.parseLong(req.getParameter("userId"));
        UserDto userDto = userService.getById(userId);
        req.setAttribute("user", userDto);
        return "jsp/user/edituserform.jsp";
    }
}
