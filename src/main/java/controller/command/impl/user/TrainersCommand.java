package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.TrainerService;
import service.dto.TrainerDto;

@Log4j2
public class TrainersCommand implements Command {

    private TrainerService trainerService;

    public TrainersCommand(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        try {
            List<TrainerDto> trainers = trainerService.getAll();
            req.setAttribute("trainers", trainers);
            return "jsp/user/trainers.jsp";
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            req.setAttribute("message", messageManager.getMessage("msg.error.errormessage"));
            return "jsp/error/error.jsp";
        }
    }

}
