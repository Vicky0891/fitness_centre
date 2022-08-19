package controller.command.impl.prescription;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.PrescriptionService;
import service.dto.PrescriptionDto;
import service.dto.UserDto;

public class PrescriptionCommand implements Command {
    private final PrescriptionService prescriptionService;

    public PrescriptionCommand(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        if (userDto.getRoleDto().name().equals("TRAINER")) {
            Long clientId = Long.parseLong(req.getParameter("clientId"));
            prescriptionDto = prescriptionService.getById(clientId);
        }
        if (userDto.getRoleDto().name().equals("CLIENT")) {
            prescriptionDto = prescriptionService.getById(userDto.getId());
        }
        req.setAttribute("prescription", prescriptionDto);
        return "jsp/prescription/prescription.jsp";

    }
}
