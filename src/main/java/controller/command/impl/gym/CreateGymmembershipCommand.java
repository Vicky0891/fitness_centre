package controller.command.impl.gym;

import java.math.BigDecimal;

import controller.command.Command;
import controller.util.MessageManager;
import controller.util.exception.impl.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

@Log4j2
public class CreateGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;

    public CreateGymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        String numberOfVisits = req.getParameter("numberOfVisits");
        String typeOfTraining = req.getParameter("typeOfTraining");
        String cost = req.getParameter("cost");
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        GymMembershipDto gymMembershipDto = new GymMembershipDto();
        try {
            gymMembershipDto.setNumberOfVisits(Integer.valueOf(numberOfVisits));
            gymMembershipDto.setTypeOfTraining(typeOfTraining);
            gymMembershipDto.setCost(new BigDecimal(cost));
        } catch (Exception e) {
            log.error("Gymmembership wasn't update. Exception: " + e);
            throw new BadRequestException("The entered data is incorrect. Try again.");
        }
        GymMembershipDto created = gymMembershipService.create(gymMembershipDto);
        req.setAttribute("gymmembership", created);
        req.setAttribute("message", messageManager.getMessage("msg.create.gym"));
        return "jsp/gymmembership/gymmembership.jsp";
    }

}
