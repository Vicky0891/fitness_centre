package controller.command.impl.order;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.OrderService;
import service.dto.OrderDto;

@Log4j2
public class AllOrdersCommand implements Command{
    private OrderService orderService;

    public AllOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
        List<OrderDto> orders = orderService.getAll();
        req.setAttribute("orders", orders);
        return "jsp/order/allorders.jsp";
        } catch (RuntimeException e) {
            log.error("Couldn't get orders. Exception: " + e);
            req.setAttribute("message", "Couldn't get orders.");
            return "jsp/error/error.jsp";
        }
    }

}
