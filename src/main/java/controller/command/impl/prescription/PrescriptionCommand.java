package controller.command.impl.prescription;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.PrescriptionService;
import service.dto.ClientDto;
import service.dto.PrescriptionDto;

@Log4j2
public class PrescriptionCommand implements Command {
    private final PrescriptionService prescriptionService;

    public PrescriptionCommand(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession(false);

            ClientDto clientDto = (ClientDto) session.getAttribute("user");

            PrescriptionDto prescriptionDto = prescriptionService.getById(clientDto.getId());
            req.setAttribute("prescription", prescriptionDto);
            return "jsp/prescription/prescription.jsp";
        } catch (NumberFormatException e) {
            log.error("Request isn't correct" + e);
            return "jsp/error.jsp";
        }
    }

}
