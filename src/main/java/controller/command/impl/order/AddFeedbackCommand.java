package controller.command.impl.order;

import controller.command.Command;
import controller.util.exception.impl.BadRequestException;
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
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        Long orderId = (Long) session.getAttribute("orderId");
        OrderDto currentOrderDto = orderService.getById(orderId);
        String feedback = req.getParameter("feedback");
        try {
            currentOrderDto.setFeedback(feedback);
        } catch (Exception e) {
            log.error("Feedback wasn't added. Exception: " + e);
            throw new BadRequestException("The entered data is incorrect. Try again.");
        }
        OrderDto updated = orderService.addFeedback(currentOrderDto);
        req.setAttribute("order", updated);
        req.setAttribute("message", "Information  updated successfully");
        return "redirect:controller?command=orders";
    }
}
