package controller.command.impl.order;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.dto.OrderDto;

public class AddFeedbackFormCommand implements Command{
    
    @Override
    public String execute(HttpServletRequest req) {
        Long orderId = Long.parseLong(req.getParameter("orderId"));
//        HttpSession session = req.getSession();
//        session.setAttribute("orderId", orderId);
        req.setAttribute("orderId", orderId);
    return "jsp/order/addfeedbackform.jsp";
    }

}
