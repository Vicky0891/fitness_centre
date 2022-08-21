package controller.command.impl.prescription;

import controller.command.Command;
import controller.util.MessageManager;
import controller.util.exception.impl.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.PrescriptionService;
import service.dto.PrescriptionDto;
import service.dto.TrainerDto;
import service.dto.OrderDto.StatusDto;

@Log4j2
public class CreatePrescriptionCommand implements Command {

    private final PrescriptionService prescriptionService;

    public CreatePrescriptionCommand(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        TrainerDto trainerDto = (TrainerDto) session.getAttribute("user");
        Long trainerId = trainerDto.getId();
        String userId = req.getParameter("clientId");
        String typeOfTraining = req.getParameter("typeOfTraining");
        String equipment = req.getParameter("equipment");
        String diet = req.getParameter("diet");

        PrescriptionDto prescriptionDto = new PrescriptionDto();
        try {
            prescriptionDto.setTrainerId(trainerId);
            prescriptionDto.setUserId(Long.valueOf(userId));
            prescriptionDto.setTypeOfTraining(typeOfTraining);
            prescriptionDto.setEquipment(equipment);
            prescriptionDto.setDiet(diet);
            prescriptionDto.setStatusDto(StatusDto.PENDING);
        } catch (Exception e) {
            log.error("prescription wasn't create. Exception: " + e);
            throw new BadRequestException("The entered data is incorrect. Try again.");
        }
        PrescriptionDto created = prescriptionService.create(prescriptionDto);
        req.setAttribute("prescription", created);
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.create.prescription"));
        return "jsp/prescription/prescription.jsp";
    }

}
