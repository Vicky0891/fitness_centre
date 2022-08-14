package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.TrainerService;
import service.UserService;
import service.dto.TrainerDto;
import service.dto.UserDto;

@Log4j2
public class EditUserFormCommand implements Command {
    private UserService userService;
    private TrainerService trainerService;

    public EditUserFormCommand(UserService userService, TrainerService trainerService) {
        this.userService = userService;
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto userDto = userService.getById(id);
        req.setAttribute("user", userDto);
        List<TrainerDto> trainers = trainerService.getAll();
        req.setAttribute("trainers", trainers);
        return "jsp/user/edituserform.jsp";
    }

}
