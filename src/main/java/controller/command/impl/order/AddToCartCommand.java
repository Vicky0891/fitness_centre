package controller.command.impl.order;

import java.util.HashMap;
import java.util.Map;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AddToCartCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        Long gymmembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>)session.getAttribute("cart");
        if(cart == null) {
            cart = new HashMap();
        }
        Integer quantity = cart.get(gymmembershipId);
        if(quantity == null) {
            cart.put(gymmembershipId, 1);
        } else {
            cart.put(gymmembershipId, quantity+1);
        }
        session.setAttribute("cart", cart);
        return "redirect:controller?command=gymmemberships";
    }
}
