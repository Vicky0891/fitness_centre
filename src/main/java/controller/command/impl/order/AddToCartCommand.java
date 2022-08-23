package controller.command.impl.order;

import java.util.HashMap;
import java.util.Map;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AddToCartCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        try {
            Long gymmembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
            String page = req.getParameter("currentPage");
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
            return "redirect:controller?command=gymmemberships&page=" + page;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            req.setAttribute("message", messageManager.getMessage("msg.error.errormessage"));
            return "jsp/error/error.jsp";
        }
    }
}
