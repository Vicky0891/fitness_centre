package controller.command.impl.gym;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.GymMembershipService;

public class DeleteGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;

    public DeleteGymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long gymMembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
        gymMembershipService.delete(gymMembershipId);
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.delete.gym"));
        return "redirect:controller?command=gymmemberships";
    }

}
