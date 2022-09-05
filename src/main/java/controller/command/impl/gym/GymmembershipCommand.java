package controller.command.impl.gym;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

public class GymmembershipCommand implements Command {

    private GymMembershipService gymMembershipService;

    public GymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        GymMembershipDto gymMembershipDto = gymMembershipService.getById(id);
        HttpSession session = req.getSession();
        session.setAttribute("gymmembership", gymMembershipDto);
        return "jsp/gymmembership/gymmembership.jsp";
    }

}
