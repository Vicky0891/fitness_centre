package controller.command.impl.gym;

import java.math.BigDecimal;

import controller.command.Command;
import controller.util.MessageManager;
import controller.util.ValidatorManager;
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
        ValidatorManager validator = new ValidatorManager();
        BigDecimal cost = validator.getCost(req);
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        GymMembershipDto gymMembershipDto = new GymMembershipDto();
        gymMembershipDto.setNumberOfVisits(Integer.valueOf(numberOfVisits));
        gymMembershipDto.setTypeOfTraining(typeOfTraining);
        gymMembershipDto.setCost(cost);
        GymMembershipDto created = gymMembershipService.create(gymMembershipDto);
        log.info("Gymmembership was create, gymmembership={}", created);
        req.setAttribute("gymmembership", created);
        req.setAttribute("message", messageManager.getMessage("msg.create.gym"));
        return "jsp/gymmembership/gymmembership.jsp";
    }

}
