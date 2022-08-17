package controller.command.impl.gym;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.GymMembershipService;

public class DeleteGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;

    public DeleteGymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long gymMembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
        gymMembershipService.delete(gymMembershipId);
        req.setAttribute("message", "Gymmembership deleted successfully");
        return "redirect:controller?command=gymmemberships";
    }

}
