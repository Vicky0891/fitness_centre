package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.TrainerService;
import service.dto.ClientDto;
import service.dto.TrainerDto;

public class ClientsCommand implements Command {

    private TrainerService trainerService;

    public ClientsCommand(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        TrainerDto trainerDto = (TrainerDto) session.getAttribute("user");
        List<ClientDto> clients = trainerService.getAllClientsByTrainer(trainerDto.getId());
        req.setAttribute("clients", clients);
        return "jsp/user/clients.jsp";
    }

}
