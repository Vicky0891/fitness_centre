package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.dto.ClientDto;

@Log4j2
public class AllClientsCommand implements Command {

    private ClientService clientService;

    public AllClientsCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            List<ClientDto> clients = clientService.getAll();
            req.setAttribute("clients", clients);
            return "jsp/user/allclients.jsp";
        } catch (RuntimeException e) {
            log.error("Couldn't got clients. Exception: " + e);
            req.setAttribute("message", "Something went wrong. Try again later");
            return "jsp/error/error.jsp";
        }
    }

}
