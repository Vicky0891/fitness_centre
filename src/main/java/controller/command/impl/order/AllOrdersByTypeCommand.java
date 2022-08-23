package controller.command.impl.order;

import java.util.List;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.OrderService;
import service.dto.OrderDto;

@Log4j2
public class AllOrdersByTypeCommand implements Command {

    private final OrderService orderService;

    public AllOrdersByTypeCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        try {
            String status = req.getParameter("status");
            List<OrderDto> orders = orderService.getAllByStatus(status);
            req.setAttribute("orders", orders);
            req.setAttribute("status", status);
            return "jsp/order/allordersbytype.jsp";
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            HttpSession session = req.getSession();
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            req.setAttribute("message", messageManager.getMessage("msg.error.errormessage"));
            return "jsp/error/error.jsp";
        }
    }

}
