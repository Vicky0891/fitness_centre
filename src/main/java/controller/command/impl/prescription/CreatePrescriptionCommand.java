package controller.command.impl.prescription;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.PrescriptionService;
import service.dto.PrescriptionDto;
import service.dto.UserDto;
import service.dto.OrderDto.StatusDto;

public class CreatePrescriptionCommand implements Command {

    private final PrescriptionService prescriptionService;

    public CreatePrescriptionCommand(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        Long trainerId = userDto.getId();
        String userId = req.getParameter("userId");
        String typeOfTraining = req.getParameter("typeOfTraining");
        String equipment = req.getParameter("equipment");
        String diet = req.getParameter("diet");
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        prescriptionDto.setTrainerId(trainerId);
        prescriptionDto.setUserId(Long.valueOf(userId));
        prescriptionDto.setTypeOfTraining(typeOfTraining);
        prescriptionDto.setEquipment(equipment);
        prescriptionDto.setDiet(diet);
        prescriptionDto.setStatusDto(StatusDto.PENDING);
        PrescriptionDto created = prescriptionService.create(prescriptionDto);
        req.setAttribute("prescription", created);
        req.setAttribute("message", "Prescription was created");
        return "jsp/prescription/prescription.jsp";
    }

}
