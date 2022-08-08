package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class CreateUserFormCommand implements Command{

    @Override
    public String execute(HttpServletRequest req) {
                        return "jsp/user/createuserform.jsp";
    }

}
