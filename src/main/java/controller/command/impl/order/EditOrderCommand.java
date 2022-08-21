package controller.command.impl.order;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.OrderService;
import service.dto.OrderDto;
import service.dto.OrderDto.StatusDto;

public class EditOrderCommand implements Command {
    private OrderService orderService;

    public EditOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long orderId = Long.parseLong(req.getParameter("id"));
        OrderDto currentOrderDto = orderService.getById(orderId);
        String status = req.getParameter("status");
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        currentOrderDto.setStatusDto(StatusDto.valueOf(status));
        OrderDto updated = orderService.update(currentOrderDto);
        req.setAttribute("order", updated);
        req.setAttribute("message", messageManager.getMessage("msg.update.order"));
        return "redirect:controller?command=all_orders";
    }

}
