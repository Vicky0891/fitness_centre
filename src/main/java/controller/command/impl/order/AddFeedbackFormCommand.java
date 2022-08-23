package controller.command.impl.order;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AddFeedbackFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        try {
            Long orderId = Long.parseLong(req.getParameter("orderId"));
            session.setAttribute("orderId", orderId);
            return "jsp/order/addfeedbackform.jsp";
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            req.setAttribute("message", messageManager.getMessage("msg.error.errormessage"));
            return "jsp/error/error.jsp";
        }
    }

}
