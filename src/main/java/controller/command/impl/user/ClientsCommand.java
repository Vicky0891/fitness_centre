package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.TrainerService;
import service.dto.ClientDto;
import service.dto.TrainerDto;

@Log4j2
public class ClientsCommand implements Command {

    private TrainerService trainerService;

    public ClientsCommand(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        try {
            TrainerDto trainerDto = (TrainerDto) session.getAttribute("user");
            List<ClientDto> clients = trainerService.getAllClientsByTrainer(trainerDto.getId());
            req.setAttribute("clients", clients);
            return "jsp/user/clients.jsp";
        } catch (RuntimeException e) {
            log.error("Couldn't got clients. Exception: " + e);
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            req.setAttribute("message", messageManager.getMessage("msg.error.errormessage"));
            return "jsp/error/error.jsp";
        }
    }

}
