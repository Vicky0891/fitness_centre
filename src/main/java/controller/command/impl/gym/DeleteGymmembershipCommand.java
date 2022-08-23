package controller.command.impl.gym;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.GymMembershipService;

@Log4j2
public class DeleteGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;

    public DeleteGymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long gymMembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
        gymMembershipService.delete(gymMembershipId);
        log.info("Gymmembership was delete, gymmembership id={}", gymMembershipId);
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.delete.gym"));
        return "redirect:controller?command=gymmemberships";
    }

}
