package controller.command.impl.user;

import java.time.LocalDate;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.TrainerService;
import service.dto.TrainerDto;

public class EditCabinetCommand implements Command {

    private final TrainerService trainerService;

    public EditCabinetCommand(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        TrainerDto currentTrainerDto = (TrainerDto) session.getAttribute("user");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String birthDate = req.getParameter("birthDate");
        String category = req.getParameter("category");

        currentTrainerDto.setFirstName(firstName);
        currentTrainerDto.setLastName(lastName);
        if (("").equals(birthDate)) {
            currentTrainerDto.setBirthDate(LocalDate.parse("0001-01-01"));
        } else {
            currentTrainerDto.setBirthDate(LocalDate.parse(birthDate));
        }
        currentTrainerDto.setCategory(category);

        TrainerDto updated = trainerService.update(currentTrainerDto);
        session.setAttribute("user", updated);
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.update.feedback"));
        return "jsp/user/user.jsp";
    }

}
