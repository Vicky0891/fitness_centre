package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.UserService;
import service.dto.UserDto;
import service.dto.UserDto.RoleDto;

public class EditUserCommand implements Command{
    private final UserService userService;

    public EditUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserDto currentUserDto = (UserDto)session.getAttribute("user");
//            String firstName = req.getParameter("firstName");
//            String lastName = req.getParameter("lastName");
            
            
            
            
            String role = req.getParameter("role");
            String type = req.getParameter("type");
            String trainerId = req.getParameter("trainerId");
//            currentUserDto.setFirstName(firstName);
//            currentUserDto.setLastName(lastName);
            currentUserDto.setRoleDto(RoleDto.valueOf(role));
                UserDto updated = userService.update(currentUserDto);
            req.setAttribute("user", updated);
            req.setAttribute("message", "User updated");
            return "jsp/user/user.jsp";
    }

}
