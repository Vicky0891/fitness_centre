package controller.command.impl.gym;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.GymMembershipService;

@Log4j2
public class DeleteGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;
    String REFERER = "http://localhost:8080/fitness_centre/";

    public DeleteGymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long gymMembershipId = Long.parseLong(req.getParameter("gymmembershipId"));
        gymMembershipService.delete(gymMembershipId);
        String url = req.getHeader("referer");
        String path = url.substring(REFERER.length());
        log.info("Gymmembership was delete, gymmembership id={}", gymMembershipId);
        return "redirect:" + path + "&delete= ";
    }

}
