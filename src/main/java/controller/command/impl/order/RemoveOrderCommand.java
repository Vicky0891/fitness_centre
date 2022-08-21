package controller.command.impl.order;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RemoveOrderCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        session.removeAttribute("cart");
        req.setAttribute("message", messageManager.getMessage("msg.delete.order"));
        return "jsp/order/cart.jsp";
    }

}
