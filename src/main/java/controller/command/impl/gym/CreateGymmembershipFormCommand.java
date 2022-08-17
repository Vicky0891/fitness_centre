package controller.command.impl.gym;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class CreateGymmembershipFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/gymmembership/creategymmembershipform.jsp";
    }

}
