package controller.command.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class ChangePasswordFormCommand implements Command{
    
    @Override
    public String execute(HttpServletRequest req) {
    return "jsp/changepasswordform.jsp";
    }

}
