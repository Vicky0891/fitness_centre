package controller.command.impl.gym;

import java.math.BigDecimal;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

public class EditGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;

    public EditGymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long gymMembershipId = Long.parseLong(req.getParameter("id"));
        GymMembershipDto currentGymMembershipDto = gymMembershipService.getById(gymMembershipId);
        String numberOfVisits = req.getParameter("numberOfVisits");
        String typeOfTraining = req.getParameter("typeOfTraining");
        String cost = req.getParameter("cost");
        currentGymMembershipDto.setNumberOfVisits(Integer.valueOf(numberOfVisits));
        currentGymMembershipDto.setTypeOfTraining(typeOfTraining);
        currentGymMembershipDto.setCost(new BigDecimal(cost));
        GymMembershipDto updated = gymMembershipService.update(currentGymMembershipDto);
        req.setAttribute("gymmembership", updated);
        req.setAttribute("message", "Gymmembership updated");
        return "redirect:controller?command=gymmemberships";
    }

}
