package controller.command.impl.order;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.OrderService;
import service.dto.OrderDto;

@Log4j2
public class AddFeedbackCommand implements Command {
    private final OrderService orderService;

    public AddFeedbackCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long orderId = (Long) session.getAttribute("orderId");
        OrderDto currentOrderDto = orderService.getById(orderId);
        String feedback = req.getParameter("feedback");
        currentOrderDto.setFeedback(feedback);
        OrderDto updated = orderService.addFeedback(currentOrderDto);
        log.info("Feedback was added, order={}", updated);
        return "redirect:controller?command=orders" + "&feedback= ";
    }
}
