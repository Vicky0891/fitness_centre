package controller.command.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        try {
            req.getSession().removeAttribute("user");
            return "index.jsp";
        } catch (Exception e) {
            return "jsp/error.jsp";
        }
    }

}
