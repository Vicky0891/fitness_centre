package controller.command.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    
    String REFERER = "http://localhost:8080/fitness_centre/";

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
        return "index.jsp";
    }

}
