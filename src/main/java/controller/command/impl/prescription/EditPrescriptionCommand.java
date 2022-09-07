package controller.command.impl.prescription;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.PrescriptionService;
import service.dto.OrderDto.StatusDto;
import service.dto.PrescriptionDto;

@Log4j2
public class EditPrescriptionCommand implements Command {

    private final PrescriptionService prescriptionService;

    public EditPrescriptionCommand(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        PrescriptionDto currentPrescriptionDto = (PrescriptionDto) session.getAttribute("prescription");
        String typeOfTraining = req.getParameter("typeOfTraining");
        String equipment = req.getParameter("equipment");
        String diet = req.getParameter("diet");

        currentPrescriptionDto.setTypeOfTraining(typeOfTraining);
        currentPrescriptionDto.setEquipment(equipment);
        currentPrescriptionDto.setDiet(diet);
        currentPrescriptionDto.setStatusDto(StatusDto.CONFIRM);
        PrescriptionDto updated = prescriptionService.update(currentPrescriptionDto);
        log.info("Prescription was update, prescription={}", updated);
        return "redirect:controller?command=prescription&id=" + updated.getId() + "&update= ";
    }
}
