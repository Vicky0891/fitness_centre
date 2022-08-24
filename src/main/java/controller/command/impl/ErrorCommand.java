package controller.command.impl;

import controller.command.Command;
import controller.util.MessageManager;
import controller.util.exception.impl.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ErrorCommand implements Command{
    
    @Override
    public String execute(HttpServletRequest req) throws BadRequestException {
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        log.error("Request isn't correct");
        throw new BadRequestException(messageManager.getMessage("msg.error.bad"));
    }

}
