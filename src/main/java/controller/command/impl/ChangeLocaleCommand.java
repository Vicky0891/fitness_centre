package controller.command.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLocaleCommand implements Command {
    
    String REFERER = "http://localhost:8080/fitness_centre/";

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        String language = req.getParameter("language");
        session.setAttribute("language", language);
        String url = req.getHeader("referer");
        String path = url.substring(REFERER.length());
        return "redirect:" + path;
    }

}
