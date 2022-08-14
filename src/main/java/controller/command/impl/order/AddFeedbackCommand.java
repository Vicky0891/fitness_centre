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
        HttpSession session = req.getSession(false);
        Long orderId = (Long) session.getAttribute("orderId");
        OrderDto currentOrderDto = orderService.getById(orderId);
        String feedback = req.getParameter("feedback");
        currentOrderDto.setFeedback(feedback);
        OrderDto updated = orderService.addFeedback(currentOrderDto);
        req.setAttribute("order", updated);
        req.setAttribute("message", "Information  updated successfully");
        return "redirect:controller?command=orders";
    }

}
