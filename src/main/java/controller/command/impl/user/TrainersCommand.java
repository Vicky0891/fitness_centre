package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.UserService;
import service.dto.UserDto;

public class TrainersCommand implements Command{
    
    private UserService userService;

    public TrainersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<UserDto> trainers = userService.getAllTrainers();
        req.setAttribute("trainers", trainers);
        return "jsp/user/trainers.jsp";
    }


}
