package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.UserService;
import service.dto.UserDto;
@Log4j2
public class UserCommand implements Command{
    private UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            UserDto userDto = userService.getById(id);
            req.setAttribute("user", userDto);
            return "jsp/user/user.jsp";
        } catch (NumberFormatException e) {
            log.error("Request isn't correct" + e);
            return "jsp/error.jsp";
        }
    }

}
