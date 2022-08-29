package controller.command.impl;

import java.awt.Desktop;
import java.net.URI;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class MailToCommand implements Command{

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        Desktop desktop = Desktop.getDesktop();
        String message = "mailto:" + messageManager.getMessage("msg.mailto");
        URI uri = URI.create(message);
        desktop.mail(uri);
        return "redirect:controller?command=contacts";
    }

}
