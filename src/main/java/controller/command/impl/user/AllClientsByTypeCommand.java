package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
        HttpSession session = req.getSession();
        try {
            String type = req.getParameter("type");
            List<ClientDto> clients = clientService.getAllClientsByType(type);
            req.setAttribute("clients", clients);
            req.setAttribute("type", type);
            return "jsp/user/allclientsbytype.jsp";
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            req.setAttribute("message", messageManager.getMessage("msg.error.errormessage"));
            return "jsp/error/error.jsp";
        }
    }

}
