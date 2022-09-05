package controller.command.impl.order;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.OrderService;
import service.dto.OrderInfoDto;

public class OrderDetailsCommand implements Command {
    private OrderService orderService;

    public OrderDetailsCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        List<OrderInfoDto> details = orderService.getById(id).getDetails();
        req.setAttribute("details", details);
        return "jsp/order/details.jsp";
    }

}
