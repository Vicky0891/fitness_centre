package controller.command.impl.order;

import java.util.Map;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.OrderService;
import service.dto.OrderDto;
import service.dto.UserDto;

public class CartCommand implements Command {
    private OrderService orderService;
    private static final String PAGE = "jsp/order/cart.jsp";

    public CartCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            req.setAttribute("message", "Cart is empty");
            return PAGE;
        }
        UserDto userDto = (UserDto) session.getAttribute("user");
        OrderDto processed = orderService.processCart(cart, userDto);
        req.setAttribute("cart", processed);
        return PAGE;
    }

}
