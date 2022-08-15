package controller.command.impl.order;

import java.util.Map;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RemoveFromCartCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        Long gymmembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart != null) {
            Integer quantity = cart.get(gymmembershipId);

            if (quantity == null) {
                if (cart.isEmpty()) {
                    session.removeAttribute("cart");
                }
                return "redirect:controller?command=gymmemberships";
            }
            if (quantity.intValue() == 1) {
                cart.remove(gymmembershipId, quantity);
                session.setAttribute("cart", cart);
            }
            if (quantity.intValue() > 1) {
                cart.put(gymmembershipId, quantity - 1);
                session.setAttribute("cart", cart);
            }
            if (cart.isEmpty()) {
                session.removeAttribute("cart");
            }
        }
        return "redirect:controller?command=gymmemberships";
    }
}
