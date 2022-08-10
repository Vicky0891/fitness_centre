package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.UserService;
import service.dto.UserDto.RoleDto;
import service.dto.UserDto.TypeDto;
import service.dto.UserDto;

public class CreateUserCommand implements Command {
    private final UserService userService;

    public CreateUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setTypeDto(TypeDto.NEW);
        userDto.setRoleDto(RoleDto.CLIENT);
        UserDto created = userService.create(userDto);
        req.setAttribute("user", created);
        req.setAttribute("message", "New user was created");
        return "jsp/user/user.jsp";
    }
}
