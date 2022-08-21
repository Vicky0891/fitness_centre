package controller.command.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLocaleCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        String language = req.getParameter("language");
        session.setAttribute("language", language);
        return "index.jsp";
    }

}
