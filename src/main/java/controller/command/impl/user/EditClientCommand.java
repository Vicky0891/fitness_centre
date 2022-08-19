package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
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
        req.setAttribute("message", "Client updated");
        return "redirect:controller?command=all_clients";
    }

}
