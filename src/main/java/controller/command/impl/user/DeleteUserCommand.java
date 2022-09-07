package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.UserService;

@Log4j2
public class DeleteUserCommand implements Command {
    private final UserService userService;

    public DeleteUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long userId = Long.parseLong(req.getParameter("userId"));
        userService.delete(userId);
        log.info("User was delete, user id={}", userId);
        return "redirect:controller?command=all_users&delete= ";
    }

}
