package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public String execute(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setRoleDto(RoleDto.CLIENT);
        UserDto created = userService.create(userDto);
        log.info("User was create, user={}", created);

        userService.login(email, password);
        HttpSession session = req.getSession();
        session.setAttribute("user", created);

        return "redirect:controller?command=user&id=" + created.getId() + "&create= ";
    }
}
