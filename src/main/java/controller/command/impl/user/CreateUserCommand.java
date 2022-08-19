package controller.command.impl.user;

import controller.command.Command;
import controller.util.exception.impl.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.UserService;
import service.dto.UserDto.RoleDto;
import service.dto.UserDto;

@Log4j2
public class CreateUserCommand implements Command {
    private final UserService userService;

    public CreateUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDto userDto = new UserDto();
        try {
            userDto.setEmail(email);
            userDto.setPassword(password);
            userDto.setRoleDto(RoleDto.CLIENT);
        } catch (Exception e) {
            log.error("User wasn't create. Exception: " + e);
            throw new BadRequestException("The entered data is incorrect. Try again.");
        }
        UserDto created = userService.create(userDto);
        req.setAttribute("user", created);
        req.setAttribute("message", "New user was created");
        return "jsp/user/user.jsp";
    }
}
