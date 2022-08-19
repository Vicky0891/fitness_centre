package controller.command.impl.gym;

import java.math.BigDecimal;

import controller.command.Command;
import controller.util.exception.impl.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

@Log4j2
public class EditGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;

    public EditGymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long gymMembershipId = Long.parseLong(req.getParameter("id"));
        GymMembershipDto currentGymMembershipDto = gymMembershipService.getById(gymMembershipId);
        String numberOfVisits = req.getParameter("numberOfVisits");
        String typeOfTraining = req.getParameter("typeOfTraining");
        String cost = req.getParameter("cost");
        try {
            currentGymMembershipDto.setNumberOfVisits(Integer.valueOf(numberOfVisits));
            currentGymMembershipDto.setTypeOfTraining(typeOfTraining);
            currentGymMembershipDto.setCost(new BigDecimal(cost));
        } catch (Exception e) {
            log.error("Gymmembership wasn't update. Exception: " + e);
            throw new BadRequestException("The entered data is incorrect. Try again.");
        }
        GymMembershipDto updated = gymMembershipService.update(currentGymMembershipDto);
        req.setAttribute("gymmembership", updated);
        req.setAttribute("message", "Gymmembership updated");
        return "redirect:controller?command=gymmemberships";
    }
}
