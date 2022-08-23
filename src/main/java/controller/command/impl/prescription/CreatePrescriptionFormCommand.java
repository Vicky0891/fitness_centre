package controller.command.impl.prescription;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CreatePrescriptionFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        try {
            Long clientId = Long.parseLong(req.getParameter("clientId"));
            req.setAttribute("clientId", clientId);
            return "jsp/prescription/createprescriptionform.jsp";
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            req.setAttribute("message", messageManager.getMessage("msg.error.errormessage"));
            return "jsp/error/error.jsp";
        }
    }
}
