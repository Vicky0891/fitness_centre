package controller.command.impl;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class MailToCommand implements Command{
    private final String REFERER = "http://localhost:8080/fitness_centre/";

    @Override
    public String execute(HttpServletRequest req) throws IOException {
        String url = req.getHeader("referer");
        String path = url.substring(REFERER.length());
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        Desktop desktop = Desktop.getDesktop();
        String message = "mailto:" + messageManager.getMessage("msg.mailto");
        URI uri = URI.create(message);
        desktop.mail(uri);
        return "redirect:" + path;
    }

}
