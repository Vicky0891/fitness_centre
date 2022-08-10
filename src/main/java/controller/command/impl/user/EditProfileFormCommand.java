package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.UserService;
import service.dto.UserDto;

public class EditProfileFormCommand implements Command{
    private UserService userService;

    public EditProfileFormCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto userDto = userService.getById(id);
        req.setAttribute("user", userDto);
        return "jsp/user/editprofileform.jsp";
    }

}
