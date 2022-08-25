package controller.command.impl.gym;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class EditGymmembershipFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        return "jsp/gymmembership/editgymmembershipform.jsp";
    }
}
