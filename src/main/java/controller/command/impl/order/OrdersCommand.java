package controller.command.impl.order;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.OrderService;
import service.dto.OrderDto;
import service.dto.UserDto;

@Log4j2
public class OrdersCommand implements Command {
    private OrderService orderService;

    public OrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession();
            UserDto userDto = (UserDto) session.getAttribute("user");
            List<OrderDto> orders = orderService.getAllOrdersDtoByClient(userDto.getId());
            req.setAttribute("orders", orders);
            return "jsp/order/orders.jsp";
        } catch (RuntimeException e) {
            log.error("Couldn't get orders. Exception: " + e);
            req.setAttribute("message", "Something went wrong. Try again later");
            return "jsp/error.jsp";
        }
    }

}
