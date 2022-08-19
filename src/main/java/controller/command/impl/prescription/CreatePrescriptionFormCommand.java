package controller.command.impl.prescription;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CreatePrescriptionFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        try {
            Long clientId = Long.parseLong(req.getParameter("clientId"));
            req.setAttribute("clientId", clientId);
            return "jsp/prescription/createprescriptionform.jsp";
        } catch (NumberFormatException e) {
            log.error("Couldn't parse client id. Exception: " + e);
            req.setAttribute("message", "Something went wrong. Try again later");
            return "jsp/error/error.jsp";
        }
    }
}
