package controller.command.impl.prescription;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.PrescriptionService;
import service.dto.PrescriptionDto;
import service.dto.UserDto;

@Log4j2
public class PrescriptionCommand implements Command {
    private final PrescriptionService prescriptionService;

    public PrescriptionCommand(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession();
            UserDto userDto = (UserDto) session.getAttribute("user");
            PrescriptionDto prescriptionDto = prescriptionService.getById(userDto.getId());
            req.setAttribute("prescription", prescriptionDto);
            return "jsp/prescription/prescription.jsp";
        } catch (NumberFormatException e) {
            log.error("Request isn't correct" + e);
            return "jsp/error.jsp";
        }
    }

}
