package controller.command.impl.gym;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

public class EditGymmembershipFormCommand implements Command {
    private final GymMembershipService gymMembershipService;

    public EditGymmembershipFormCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long gymmembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
        GymMembershipDto gymMembershipDto = gymMembershipService.getById(gymmembershipId);
        req.setAttribute("gymmembership", gymMembershipDto);
        return "jsp/gymmembership/editgymmembershipform.jsp";
    }
}
