package controller.command.impl.order;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.OrderService;
import service.dto.OrderDto;
import service.dto.OrderDto.StatusDto;

public class EditOrderCommand implements Command {
    private OrderService orderService;

    public EditOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long orderId = Long.parseLong(req.getParameter("id"));
        OrderDto currentOrderDto = orderService.getById(orderId);
        String status = req.getParameter("status");
        currentOrderDto.setStatusDto(StatusDto.valueOf(status));
        OrderDto updated = orderService.update(currentOrderDto);
        req.setAttribute("order", updated);
        req.setAttribute("message", "Order updated");
        return "redirect:controller?command=all_orders";
    }

}
