package controller.command.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.UserService;
import service.dto.UserDto;

public class LoginCommand implements Command {
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            UserDto user = userService.login(email, password);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            return "index.jsp";
        } catch (Exception e) {
            req.setAttribute("message", "No user with this email. Please register");
            System.out.println(e);
            
            return "jsp/user/createuserform.jsp";
        }
    }
}
