package controller.command.impl.order;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.OrderService;
import service.dto.OrderDto;

public class OrderCommand implements Command {
    private OrderService orderService;

    public OrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long id = Long.parseLong(req.getParameter("id"));
        OrderDto orderDto = orderService.getById(id);
        req.setAttribute("order", orderDto);
        return "jsp/order/order.jsp";
    }
}