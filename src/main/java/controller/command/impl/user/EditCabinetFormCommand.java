package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.TrainerService;
import service.dto.TrainerDto;

public class EditCabinetFormCommand implements Command {
    private TrainerService trainerService;

    public EditCabinetFormCommand(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        TrainerDto trainerDto = trainerService.getById(id);
        req.setAttribute("user", trainerDto);
        return "jsp/user/editcabinetform.jsp";
    }

}
