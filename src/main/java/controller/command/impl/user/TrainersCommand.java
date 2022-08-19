package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
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
        try {
            List<TrainerDto> trainers = trainerService.getAll();
            req.setAttribute("trainers", trainers);
            return "jsp/user/trainers.jsp";
        } catch (RuntimeException e) {
            log.error("Couldn't got trainers. Exception: " + e);
            req.setAttribute("message", "Something went wrong. Try again later");
            return "jsp/error/error.jsp";
        }
    }

}
