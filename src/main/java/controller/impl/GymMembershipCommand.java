package controller.impl;

import java.util.List;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

public class GymMembershipCommand implements Command {
    private GymMembershipService gymMembershipService;

    public GymMembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<GymMembershipDto> gymMemberships = gymMembershipService.getAll();
        req.setAttribute("gymmemberships", gymMemberships);
        return "jsp/gymmemberships.jsp";
    }

}
