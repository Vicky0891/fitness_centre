package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.ClientService;
import service.dto.ClientDto;

public class AllClientsCommand implements Command {

    private ClientService clientService;

    public AllClientsCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<ClientDto> clients = clientService.getAll();
        req.setAttribute("clients", clients);
        return "jsp/user/allclients.jsp";
    }

}
