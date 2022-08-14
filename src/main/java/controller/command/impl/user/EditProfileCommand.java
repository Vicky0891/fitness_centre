package controller.command.impl.user;

import java.time.LocalDate;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.ClientService;
import service.UserService;
import service.dto.ClientDto;
import service.dto.UserDto;

public class EditProfileCommand implements Command {
    private final UserService userService;
    private final ClientService clientService;

    public EditProfileCommand(UserService userService, ClientService clientService) {
        this.userService = userService;
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
        currentClientDto.setBirthDate(LocalDate.parse(birthDate));
        currentClientDto.setPhoneNumber(phoneNumber);
        currentClientDto.setAdditionalInfo(additionalInfo);
        ClientDto updated = clientService.update(currentClientDto);
        req.setAttribute("user", updated);
        req.setAttribute("message", "Information  updated successfully");
        return "jsp/user/user.jsp";
    }

}
