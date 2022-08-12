package controller.command.impl.prescription;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.PrescriptionService;
import service.dto.PrescriptionDto;

public class EditPrescriptionFormCommand implements Command{
    private final PrescriptionService prescriptionService;

    public EditPrescriptionFormCommand(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        PrescriptionDto prescriptionDto = prescriptionService.getById(id);
        req.setAttribute("prescription", prescriptionDto);
        return "jsp/prescription/editprescriptionform.jsp";
    }
}
