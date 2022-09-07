package controller.command.impl.gym;

import java.math.BigDecimal;

import controller.command.Command;
import controller.util.ValidatorManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

@Log4j2
public class EditGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;
    private final ValidatorManager validator;

    public EditGymmembershipCommand(GymMembershipService gymMembershipService, ValidatorManager validator) {
        this.gymMembershipService = gymMembershipService;
        this.validator = validator;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        GymMembershipDto currentGymMembershipDto = (GymMembershipDto) session.getAttribute("gymmembership");
        String numberOfVisits = req.getParameter("numberOfVisits");
        String typeOfTraining = req.getParameter("typeOfTraining");
        BigDecimal cost = validator.getCorrectCost(req);
        currentGymMembershipDto.setNumberOfVisits(Integer.valueOf(numberOfVisits));
        currentGymMembershipDto.setTypeOfTraining(typeOfTraining);
        currentGymMembershipDto.setCost(cost);
        GymMembershipDto updated = gymMembershipService.update(currentGymMembershipDto);
        log.info("Gymmembership was update, gymmembership={}", updated);
        return "redirect:controller?command=gymmembership&id=" + updated.getId() + "&edit= ";
    }
}
