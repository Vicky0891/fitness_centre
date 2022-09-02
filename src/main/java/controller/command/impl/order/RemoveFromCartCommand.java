package controller.command.impl.order;

import java.util.Map;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RemoveFromCartCommand implements Command {

    private final String REFERER = "http://localhost:8080/fitness_centre/";

    @Override
    public String execute(HttpServletRequest req) {
        Long gymmembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
        String url = req.getHeader("referer");
        String path = url.substring(REFERER.length());
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart != null) {
            Integer quantity = cart.get(gymmembershipId);
            if (quantity == null) {
                return "redirect:" + path;
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
        return "redirect:" + path;
    }
}
