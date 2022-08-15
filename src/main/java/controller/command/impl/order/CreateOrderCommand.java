package controller.command.impl.order;

import java.util.Map;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.ClientService;
import service.OrderService;
import service.dto.ClientDto;
import service.dto.ClientDto.TypeDto;
import service.dto.OrderDto;
import service.dto.UserDto;

public class CreateOrderCommand implements Command {
    private final OrderService orderService;
    private final ClientService clientService;

    public CreateOrderCommand(OrderService orderService, ClientService clientService) {
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        if (userDto == null) {
            req.setAttribute("message", "Please login");
            return "jsp/loginform.jsp";
        }
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        OrderDto processed = orderService.processCart(cart, userDto);
        OrderDto created = orderService.create(processed);
        req.setAttribute("order", created);
        req.setAttribute("message", "Order created successfully");

        ClientDto ClientDto = new ClientDto();
        ClientDto.setId(userDto.getId());
        ClientDto.setType(TypeDto.NEW);
        
        try {
        ClientDto createdClient = clientService.create(ClientDto);
        session.setAttribute("user", createdClient);
        } catch (RuntimeException e) {
            return "jsp/order/order.jsp";
        }
        session.removeAttribute("cart");

        return "jsp/order/order.jsp";
    }

}
