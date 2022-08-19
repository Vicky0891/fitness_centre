package controller.command.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
        return "index.jsp";
    }

}
