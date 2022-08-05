package controller.impl;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.TrainerService;
import service.dto.TrainerDto;

public class TrainersCommand implements Command{
    
    private TrainerService trainerService;

    public TrainersCommand(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<TrainerDto> trainers = trainerService.getAll();
        req.setAttribute("trainers", trainers);
        return "jsp/trainers.jsp";
    }


}
