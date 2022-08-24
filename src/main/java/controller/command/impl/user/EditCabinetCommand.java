package controller.command.impl.user;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;
import java.util.UUID;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import lombok.extern.log4j.Log4j2;
import service.TrainerService;
import service.dto.TrainerDto;

@Log4j2
public class EditCabinetCommand implements Command {

    private final TrainerService trainerService;
    public static final String CONFIG_FILE = "/application.properties";

    public EditCabinetCommand(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        TrainerDto currentTrainerDto = (TrainerDto) session.getAttribute("user");
        Part part = req.getPart("avatar");
        if (part.getSize() != 0) {
            String fileName = UUID.randomUUID() + "_" + part.getSubmittedFileName();
            try (InputStream is = getClass().getResourceAsStream(CONFIG_FILE)) {
                Properties props = new Properties();
                props.load(is);
                String path = props.getProperty("path.trainersavatars");
                part.write(path + fileName);
                currentTrainerDto.setPathAvatar(fileName);
            }
        }
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String birthDate = req.getParameter("birthDate");
        String category = req.getParameter("category");

        currentTrainerDto.setFirstName(firstName);
        currentTrainerDto.setLastName(lastName);
        if (("").equals(birthDate)) {
            currentTrainerDto.setBirthDate(LocalDate.parse("0001-01-01"));
        } else {
            currentTrainerDto.setBirthDate(LocalDate.parse(birthDate));
        }
        currentTrainerDto.setCategory(category);

        TrainerDto updated = trainerService.update(currentTrainerDto);
        log.info("Trainer was update, trainer={}", updated);
        session.setAttribute("user", updated);
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.update.feedback"));
        return "jsp/user/user.jsp";
    }

}
