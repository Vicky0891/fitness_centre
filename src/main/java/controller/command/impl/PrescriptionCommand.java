package controller.command.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.PrescriptionService;
import service.dto.PrescriptionDto;

@Log4j2
public class PrescriptionCommand implements Command{
    private PrescriptionService prescriptionService;

    public PrescriptionCommand(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            PrescriptionDto prescriptionDto = prescriptionService.getById(id);
            req.setAttribute("prescription", prescriptionDto);
            return "jsp/prescription/prescription.jsp";
        } catch (NumberFormatException e) {
            log.error("Request isn't correct" + e);
            return "jsp/error.jsp";
        }
    }

}
