package controller.command.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ErrorCommand implements Command{
    
    @Override
    public String execute(HttpServletRequest req) {
        log.error("Request isn't correct");
        return "jsp/error/error.jsp";
    }

}
