package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.UserService;
import service.dto.UserDto;

@Log4j2
public class EditUserFormCommand implements Command {
    private UserService userService;

    public EditUserFormCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto userDto = userService.getById(id);
        req.setAttribute("user", userDto);
        List<UserDto> trainers = userService.getAllTrainers();
        req.setAttribute("trainers", trainers);
        return "jsp/user/edituserform.jsp";
    }

}
