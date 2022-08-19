package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.dto.ClientDto;

@Log4j2
public class AllClientsByTypeCommand implements Command {
    private final ClientService clientService;

    public AllClientsByTypeCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            String type = req.getParameter("type");
            List<ClientDto> clients = clientService.getAllClientsByType(type);
            req.setAttribute("clients", clients);
            req.setAttribute("type", type);
            return "jsp/user/allclientsbytype.jsp";
        } catch (RuntimeException e) {
            log.error("Couldn't got clients. Exception: " + e);
            req.setAttribute("message", "Something went wrong. Try again later");
            return "jsp/error.jsp";
        }
    }

}
