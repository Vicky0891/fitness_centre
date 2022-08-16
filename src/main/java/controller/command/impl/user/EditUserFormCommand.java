package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.TrainerService;
import service.UserService;
import service.dto.TrainerDto;
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
        
//        
//        
//        Long id = Long.parseLong(req.getParameter("id"));
//        UserDto userDto = userService.getById(id);
//        req.setAttribute("user", userDto);
//        req.setAttribute("trainers", trainers);
//        return "jsp/user/edituserform.jsp";
//    }

}
