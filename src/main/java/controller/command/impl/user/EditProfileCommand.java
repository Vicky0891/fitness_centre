package controller.command.impl.user;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;
import java.util.UUID;

import controller.command.Command;
import controller.util.MessageManager;
import controller.util.ValidatorManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.dto.ClientDto;

@Log4j2
public class EditProfileCommand implements Command {
    private final ClientService clientService;
    public static final String CONFIG_FILE = "/application.properties";

    public EditProfileCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        ClientDto currentClientDto = (ClientDto) session.getAttribute("user");
        Part part = req.getPart("avatar");
        if (part.getSize() != 0) {
            String fileName = UUID.randomUUID() + "_" + part.getSubmittedFileName();
            try (InputStream is = getClass().getResourceAsStream(CONFIG_FILE)) {
                Properties props = new Properties();
                props.load(is);
                String path = props.getProperty("path.clientavatars");
                part.write(path + fileName);
                currentClientDto.setPathAvatar(fileName);
            }
        }
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String birthDate = req.getParameter("birthDate");
        ValidatorManager validator = new ValidatorManager();
        String phoneNumber = validator.getCorrectPhoneNumber(req);
        String additionalInfo = req.getParameter("additionalInfo");
        currentClientDto.setFirstName(firstName);
        currentClientDto.setLastName(lastName);
        if (("").equals(birthDate)) {
            currentClientDto.setBirthDate(LocalDate.parse("0001-01-01"));
        } else {
            currentClientDto.setBirthDate(LocalDate.parse(birthDate));
        }
        currentClientDto.setPhoneNumber(phoneNumber);
        currentClientDto.setAdditionalInfo(additionalInfo);
        ClientDto updated = clientService.update(currentClientDto);
        log.info("Client was update, client={}", updated);
        session.setAttribute("user", updated);
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.update.feedback"));
        return "jsp/user/user.jsp";
    }
}
