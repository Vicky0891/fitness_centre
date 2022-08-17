package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.ClientService;
import service.dto.ClientDto;

public class AllClientsByTypeCommand implements Command{
    private final ClientService clientService;

    public AllClientsByTypeCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String type = req.getParameter("type");
        List<ClientDto> clients = clientService.getAllClientsByType(type);
        req.setAttribute("clients", clients);
        req.setAttribute("type", type);
        return "jsp/user/allclientsbytype.jsp";
    }

}
