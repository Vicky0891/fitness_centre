package controller.command.impl.user;

import java.time.LocalDate;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.UserService;
import service.dto.UserDto;

public class EditProfileCommand implements Command {
    private final UserService userService;

    public EditProfileCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserDto currentUserDto = (UserDto) session.getAttribute("user");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String birthDate = req.getParameter("birthDate");
        String phoneNumber = req.getParameter("phoneNumber");
        String additionalInfo = req.getParameter("additionalInfo");
        currentUserDto.setFirstName(firstName);
        currentUserDto.setLastName(lastName);
        currentUserDto.setBirthDate(LocalDate.parse(birthDate));
        currentUserDto.setPhoneNumber(phoneNumber);
        currentUserDto.setAdditionalInfo(additionalInfo);
        UserDto updated = userService.update(currentUserDto);
        req.setAttribute("user", updated);
        req.setAttribute("message", "Information  updated successfully");
        return "jsp/user/user.jsp";
    }

}
