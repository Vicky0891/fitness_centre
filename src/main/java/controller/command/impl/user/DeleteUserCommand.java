package controller.command.impl.user;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.UserService;

@Log4j2
public class DeleteUserCommand implements Command {
    private final UserService userService;

    public DeleteUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long userId = Long.parseLong(req.getParameter("userId"));
        userService.delete(userId);
        log.info("User was delete, user id={}", userId);
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.delete.user"));
        return "redirect:controller?command=all_users";
    }

}
