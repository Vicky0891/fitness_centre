package controller.command.impl.user;

import jakarta.servlet.http.HttpServletRequest;
import service.TrainerService;
import service.dto.TrainerDto;
import controller.command.Command;

public class TrainerCommand implements Command {

    private TrainerService trainerService;

    public TrainerCommand(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        TrainerDto trainer = trainerService.getById(id);
        req.setAttribute("trainer", trainer);
        return "jsp/user/trainer.jsp";
    }

}
