package controller.command.impl.order;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RemoveOrderCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("cart");
        req.setAttribute("message", "Cart cleared successfully");
        return "jsp/order/cart.jsp";
    }

}
