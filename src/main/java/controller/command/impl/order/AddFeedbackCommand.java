package controller.command.impl.order;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.OrderService;
import service.dto.OrderDto;

public class AddFeedbackCommand implements Command {
    private final OrderService orderService;

    public AddFeedbackCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long orderId = Long.parseLong(req.getParameter("orderId"));
        HttpSession session = req.getSession();
        OrderDto currentOrderDto = orderService.getById(orderId);
//        OrderDto currentOrderDto = (OrderDto) session.getAttribute("orders");
        String feedback = req.getParameter("feedback");
        currentOrderDto.setFeedback(feedback);
        OrderDto updated = orderService.addFeedback(currentOrderDto);
        req.setAttribute("order", updated);
        req.setAttribute("message", "Information  updated successfully");
        return "jsp/order/orders.jsp";
    }

}
