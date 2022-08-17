package controller.command.impl.order;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.OrderService;
import service.dto.OrderDto;

public class AllOrdersCommand implements Command{
    private OrderService orderService;

    public AllOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<OrderDto> orders = orderService.getAll();
        req.setAttribute("orders", orders);
        return "jsp/order/allorders.jsp";
    }

}
