package controller.command.impl.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.UserService;
import service.dto.UserDto;
import controller.command.Command;
@Log4j2
public class TrainerCommand implements Command{
 
    private UserService userService;

    public TrainerCommand(UserService userService) {
        this.userService = userService;
    }

        @Override
        public String execute(HttpServletRequest req) {
            try {
                Long id = Long.parseLong(req.getParameter("id"));
                UserDto trainer = userService.getById(id);
                req.setAttribute("trainer", trainer);
                return "jsp/user/trainer.jsp";
            } catch (NumberFormatException e) {
                log.error("Request isn't correct" + e);
                return "jsp/error.jsp";
            }
        }

}
