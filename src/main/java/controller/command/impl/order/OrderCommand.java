package controller.command.impl.order;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.OrderService;
import service.dto.OrderDto;
@Log4j2
public class OrderCommand implements Command{
    private OrderService orderService;

    public OrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            OrderDto orderDto = orderService.getById(id);
            req.setAttribute("order", orderDto);
            return "jsp/order/order.jsp";
        } catch (NumberFormatException e) {
            log.error("Request isn't correct" + e);
            return "jsp/error.jsp";
        }
    }

}
