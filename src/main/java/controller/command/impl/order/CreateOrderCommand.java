package controller.command.impl.order;

import java.util.Map;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.OrderService;
import service.dto.OrderDto;
import service.dto.UserDto;

public class CreateOrderCommand implements Command{
    private final OrderService orderService;

    public CreateOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
       UserDto userDto = (UserDto)session.getAttribute("user");
       if(userDto == null) {
           req.setAttribute("message", "Please login");
           return "jsp/loginform.jsp";
       }
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>)session.getAttribute("cart");
        OrderDto processed = orderService.processCart(cart, userDto);
        OrderDto created = orderService.create(processed);
        session.removeAttribute("cart");
        req.setAttribute("order", created);
        req.setAttribute("message", "Order created successfully");
        return "jsp/order/order.jsp";
    }
    

}
