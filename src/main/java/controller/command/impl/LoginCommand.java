package controller.command.impl;

import controller.command.Command;
import controller.util.UserRoleManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.TrainerService;
import service.UserService;
import service.dto.UserDto;

@Log4j2
public class LoginCommand implements Command {
    private final UserService userService;
    private final ClientService clientService;
    private final TrainerService trainerService;

    public LoginCommand(UserService userService, TrainerService trainerService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDto userDto = userService.login(email, password);
        UserRoleManager userRole = new UserRoleManager(clientService, trainerService);
        Object user = userRole.getUserRole(userDto);
        log.info("Login user={}", user);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        return "index.jsp";
    }
}
