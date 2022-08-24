package controller.command.impl.order;

import java.time.LocalDate;
import java.util.Map;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.OrderService;
import service.dto.ClientDto;
import service.dto.ClientDto.TypeDto;
import service.dto.OrderDto;
import service.dto.UserDto;
import service.dto.UserDto.RoleDto;

@Log4j2
public class CreateOrderCommand implements Command {
    private final OrderService orderService;
    private final ClientService clientService;
    private final String DEFAULT_PHOTO = "default_photo.jpg";

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
        log.info("Order was create, order={}", created);
        req.setAttribute("order", created);
        req.setAttribute("message", messageManager.getMessage("msg.create.order"));
        session.removeAttribute("cart");
        ClientDto createdClient = createClientDto(userDto);
        session.setAttribute("user", createdClient);
        return "jsp/order/order.jsp";
    }

    /**
     * Method create ClientDto and added it in data source
     * 
     * @param userDto User in system
     * @return ClientDto created in data source table
     * @throws Exception
     */
    private ClientDto createClientDto(UserDto userDto) throws Exception {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(userDto.getId());
        clientDto.setType(TypeDto.NEW);
        clientDto.setRoleDto(RoleDto.CLIENT);
        clientDto.setBirthDate(LocalDate.parse("0001-01-01"));
        clientDto.setPathAvatar(DEFAULT_PHOTO);
        return clientService.create(clientDto);
    }
}
