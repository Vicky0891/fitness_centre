package controller.command.impl.prescription;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class CreatePrescriptionFormCommand implements Command{

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/prescription/createprescriptionform.jsp";
    }
}
