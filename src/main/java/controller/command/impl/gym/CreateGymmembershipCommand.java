package controller.command.impl.gym;

import java.math.BigDecimal;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

public class CreateGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;

    public CreateGymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String numberOfVisits = req.getParameter("numberOfVisits");
        String typeOfTraining = req.getParameter("typeOfTraining");
        String cost = req.getParameter("cost");
        GymMembershipDto gymMembershipDto = new GymMembershipDto();
        gymMembershipDto.setNumberOfVisits(Integer.valueOf(numberOfVisits));
        gymMembershipDto.setTypeOfTraining(typeOfTraining);
        gymMembershipDto.setCost(new BigDecimal(cost));
        GymMembershipDto created = gymMembershipService.create(gymMembershipDto);
        req.setAttribute("gymmembership", created);
        req.setAttribute("message", "New gymmembership was created");
        return "jsp/gymmembership/gymmembership.jsp";
    }

}
