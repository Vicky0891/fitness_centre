package controller.command.impl.prescription;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.PrescriptionService;
import service.dto.PrescriptionDto;

public class EditPrescriptionFormCommand implements Command {
    private final PrescriptionService prescriptionService;

    public EditPrescriptionFormCommand(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long userId = Long.parseLong(req.getParameter("userId"));
        PrescriptionDto prescriptionDto = prescriptionService.getById(userId);
        HttpSession session = req.getSession();
        session.setAttribute("prescription", prescriptionDto);
        return "jsp/prescription/editprescriptionform.jsp";
    }
}
