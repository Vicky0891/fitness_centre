package controller.command.impl.gym;

import java.math.BigDecimal;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

@Log4j2
public class EditGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;

    public EditGymmembershipCommand(GymMembershipService gymMembershipService) {
        this.gymMembershipService = gymMembershipService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long gymMembershipId = Long.parseLong(req.getParameter("id"));
        GymMembershipDto currentGymMembershipDto = gymMembershipService.getById(gymMembershipId);
        String numberOfVisits = req.getParameter("numberOfVisits");
        String typeOfTraining = req.getParameter("typeOfTraining");
        String cost = req.getParameter("cost");
        currentGymMembershipDto.setNumberOfVisits(Integer.valueOf(numberOfVisits));
        currentGymMembershipDto.setTypeOfTraining(typeOfTraining);
        currentGymMembershipDto.setCost(new BigDecimal(cost));
        GymMembershipDto updated = gymMembershipService.update(currentGymMembershipDto);
        log.info("Gymmembership was update, gymmembership={}", updated);
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("gymmembership", updated);
        req.setAttribute("message", messageManager.getMessage("msg.update.gym"));
        return "redirect:controller?command=gymmemberships";
    }
}
