package controller.command.impl.gym;

import java.math.BigDecimal;

import controller.command.Command;
import controller.util.ValidatorManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

@Log4j2
public class CreateGymmembershipCommand implements Command {
    private final GymMembershipService gymMembershipService;
    private final ValidatorManager validator;

    public CreateGymmembershipCommand(GymMembershipService gymMembershipService, ValidatorManager validator) {
        this.gymMembershipService = gymMembershipService;
        this.validator = validator;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String numberOfVisits = req.getParameter("numberOfVisits");
        String typeOfTraining = req.getParameter("typeOfTraining");
        BigDecimal cost = validator.getCorrectCost(req);
        GymMembershipDto gymMembershipDto = new GymMembershipDto();
        gymMembershipDto.setNumberOfVisits(Integer.valueOf(numberOfVisits));
        gymMembershipDto.setTypeOfTraining(typeOfTraining);
        gymMembershipDto.setCost(cost);
        GymMembershipDto created = gymMembershipService.create(gymMembershipDto);
        log.info("Gymmembership was create, gymmembership={}", created);
        return "redirect:controller?command=gymmembership&id=" + created.getId() + "&create= ";
    }

}
