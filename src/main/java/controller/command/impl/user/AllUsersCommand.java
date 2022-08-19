package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.UserService;
import service.dto.UserDto;

@Log4j2
public class AllUsersCommand implements Command {

    private UserService userService;

    public AllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            List<UserDto> users = userService.getAll();
            req.setAttribute("users", users);
            return "jsp/user/allusers.jsp";
        } catch (RuntimeException e) {
            log.error("Couldn't got users. Exception: " + e);
            req.setAttribute("message", "Something went wrong. Try again later");
            return "jsp/error/error.jsp";
        }
    }

}
