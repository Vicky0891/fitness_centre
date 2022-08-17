package controller.command.impl;

import controller.command.Command;
import controller.util.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.ClientService;
import service.TrainerService;
import service.UserService;
import service.dto.UserDto;

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
    public String execute(HttpServletRequest req) {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            UserDto userDto = userService.login(email, password);
            UserRole userRole = new UserRole(clientService, trainerService);
            Object user = userRole.getUserRole(userDto);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            return "index.jsp";
        } catch (Exception e) {
            req.setAttribute("message", "No user with this email. Please register");

            return "jsp/user/createuserform.jsp";
        }
    }
}
