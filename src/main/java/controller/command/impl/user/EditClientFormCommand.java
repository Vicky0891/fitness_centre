package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.ClientService;
import service.TrainerService;
import service.dto.ClientDto;
import service.dto.TrainerDto;

public class EditClientFormCommand implements Command {
    private ClientService clientService;
    private TrainerService trainerService;

    public EditClientFormCommand(ClientService clientService, TrainerService trainerService) {
        this.clientService = clientService;
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long clientId = Long.parseLong(req.getParameter("clientId"));
        ClientDto clientDto = clientService.getById(clientId);
        List<TrainerDto> trainers = trainerService.getAll();
        req.setAttribute("trainers", trainers);
        req.setAttribute("client", clientDto);
        return "jsp/user/editclientform.jsp";
    }

}
