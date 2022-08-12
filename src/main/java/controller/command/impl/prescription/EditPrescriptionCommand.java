package controller.command.impl.prescription;

import java.time.LocalDate;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.PrescriptionService;
import service.UserService;
import service.dto.PrescriptionDto;
import service.dto.UserDto;

public class EditPrescriptionCommand implements Command{

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
            PrescriptionDto updated = prescriptionService.update(currentPrescriptionDto);
            req.setAttribute("prescription", updated);
            req.setAttribute("message", "Information  updated successfully");
            return "jsp/prescription/prescription.jsp";
        }
        

}
