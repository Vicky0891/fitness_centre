package controller.command.impl.order;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.OrderService;
import service.dto.OrderDto;

public class EditOrderFormCommand implements Command {
    private OrderService orderService;

    public EditOrderFormCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long orderId = Long.parseLong(req.getParameter("orderId"));
        OrderDto orderDto = orderService.getById(orderId);
        req.setAttribute("order", orderDto);
        return "jsp/order/editorderform.jsp";
    }

}
