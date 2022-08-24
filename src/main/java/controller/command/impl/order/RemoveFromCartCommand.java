package controller.command.impl.order;

import java.util.Map;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RemoveFromCartCommand implements Command {

    private final String REDIRECT_CART = "redirect:controller?command=cart";
    private final String REDIRECT_GYMMEMBERSHIPS = "redirect:controller?command=gymmemberships&page=";

    @Override
    public String execute(HttpServletRequest req) {
        Long gymmembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
        String page = req.getParameter("currentPage");
        String redirect = req.getParameter("redirect");
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart != null) {
            Integer quantity = cart.get(gymmembershipId);
            if (quantity == null) {
                if (redirect.equals("cart")) {
                    return REDIRECT_CART;
                } else
                    return REDIRECT_GYMMEMBERSHIPS + page;
            }

            if (quantity.intValue() == 1) {
                cart.remove(gymmembershipId, quantity);
            } else {
                cart.put(gymmembershipId, quantity - 1);
            }
            session.setAttribute("cart", cart);

            if (cart.isEmpty()) {
                session.removeAttribute("cart");
            }
        }
        if (redirect.equals("cart")) {
            return REDIRECT_CART;
        } else
            return REDIRECT_GYMMEMBERSHIPS + page;
    }
}
