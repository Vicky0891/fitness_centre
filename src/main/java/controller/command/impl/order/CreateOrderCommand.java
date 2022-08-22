package controller.command.impl.order;

import java.time.LocalDate;
import java.util.Map;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.ClientService;
import service.OrderService;
import service.dto.ClientDto;
import service.dto.ClientDto.TypeDto;
import service.dto.OrderDto;
import service.dto.UserDto;
import service.dto.UserDto.RoleDto;

public class CreateOrderCommand implements Command {
    private final OrderService orderService;
    private final ClientService clientService;

    public CreateOrderCommand(OrderService orderService, ClientService clientService) {
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        if (userDto == null) {
            req.setAttribute("message", messageManager.getMessage("msg.login"));
            return "jsp/loginform.jsp";
        }
        @SuppressWarnings("unchecked")
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        OrderDto processed = orderService.processCart(cart, userDto);
        OrderDto created = orderService.create(processed);
        req.setAttribute("order", created);
        req.setAttribute("message", messageManager.getMessage("msg.create.order"));
        session.removeAttribute("cart");
        ClientDto createdClient = createClientDto(userDto);
        session.setAttribute("user", createdClient);
        return "jsp/order/order.jsp";
    }

    private ClientDto createClientDto(UserDto userDto) throws Exception {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(userDto.getId());
        clientDto.setType(TypeDto.NEW);
        clientDto.setRoleDto(RoleDto.CLIENT);
        clientDto.setBirthDate(LocalDate.parse("0001-01-01"));
        return clientService.create(clientDto);
    }
}
