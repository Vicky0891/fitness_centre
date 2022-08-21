package controller.command.impl.user;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.ClientService;
import service.dto.ClientDto;
import service.dto.ClientDto.TypeDto;

public class EditClientCommand implements Command {
    private final ClientService clientService;

    public EditClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long clientId = Long.parseLong(req.getParameter("id"));
        ClientDto currentClientDto = clientService.getById(clientId);
        String type = req.getParameter("type");
        String trainerId = req.getParameter("trainerId");
        currentClientDto.setType(TypeDto.valueOf(type));
        currentClientDto.setTrainerId(Long.valueOf(trainerId));

        ClientDto updated = clientService.update(currentClientDto);
        req.setAttribute("client", updated);
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.update.client"));
        return "redirect:controller?command=all_clients";
    }

}
