package controller.command.impl.user;

import java.time.LocalDate;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.dto.ClientDto;

@Log4j2
public class EditProfileCommand implements Command {
    private final ClientService clientService;

    public EditProfileCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        ClientDto currentClientDto = (ClientDto) session.getAttribute("user");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String birthDate = req.getParameter("birthDate");
        String phoneNumber = req.getParameter("phoneNumber");
        String additionalInfo = req.getParameter("additionalInfo");
        currentClientDto.setFirstName(firstName);
        currentClientDto.setLastName(lastName);
        if(("").equals(birthDate)) {
            currentClientDto.setBirthDate(LocalDate.parse("0001-01-01"));
        } else {
            currentClientDto.setBirthDate(LocalDate.parse(birthDate));
        }
        currentClientDto.setPhoneNumber(phoneNumber);
        currentClientDto.setAdditionalInfo(additionalInfo);
        ClientDto updated = clientService.update(currentClientDto);
        session.setAttribute("user", updated);
        req.setAttribute("message", "Information  updated successfully");
        return "jsp/user/user.jsp";
    }

}
