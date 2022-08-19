package controller.command.impl.order;

import java.util.HashMap;
import java.util.Map;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AddToCartCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        try {
            Long gymmembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
            HttpSession session = req.getSession();
            @SuppressWarnings("unchecked")
            Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
            if (cart == null) {
                cart = new HashMap<Long, Integer>();
            }

            Integer quantity = cart.get(gymmembershipId);
            if (quantity == null) {
                cart.put(gymmembershipId, 1);
            } else {
                cart.put(gymmembershipId, quantity + 1);
            }
            session.setAttribute("cart", cart);
            return "redirect:controller?command=gymmemberships";
        } catch (RuntimeException e) {
            log.error("Couldn't parse gymmembership id or got \"cart\". Exception: " + e);
            req.setAttribute("message",
                    "The requested gymmembership doesn't exist or has been deleted. Try to select another");
            return "jsp/error/error.jsp";
        }
    }
}
