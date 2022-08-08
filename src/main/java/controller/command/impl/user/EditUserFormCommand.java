package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.UserService;
import service.dto.UserDto;
@Log4j2
public class EditUserFormCommand implements Command{
    private UserService userService;

    public EditUserFormCommand(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public String execute(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession();
            UserDto userDto = (UserDto)session.getAttribute("user");
            return "jsp/user/edituserform.jsp";
        } catch (NumberFormatException e) {
            log.error("Request isn't correct" + e);
            return "jsp/error.jsp";
        }
    }

}
