package controller.command.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class LoginFormCommand implements Command{
    
    @Override
    public String execute(HttpServletRequest req) {
        
    return "jsp/loginform.jsp";
    }

}
