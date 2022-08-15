package controller.command.impl.order;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AddFeedbackFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        Long orderId = Long.parseLong(req.getParameter("orderId"));
        HttpSession session = req.getSession(false);
        session.setAttribute("orderId", orderId);
        return "jsp/order/addfeedbackform.jsp";
    }

}
