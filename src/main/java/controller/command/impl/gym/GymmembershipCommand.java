package controller.command.impl.gym;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

@Log4j2
public class GymmembershipCommand implements Command {

    private GymMembershipService gymMembershipService;

    public GymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            GymMembershipDto gymMembershipDto = gymMembershipService.getById(id);
            req.setAttribute("gymmembership", gymMembershipDto);
            return "jsp/gymmembership/gymmembership.jsp";
        } catch (NumberFormatException e) {
            log.error("Request isn't correct" + e);
            return "jsp/error.jsp";
        }
    }

}
