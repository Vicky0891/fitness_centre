package controller.command.impl.user;

import controller.command.Command;
import controller.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.UserService;

public class DeleteUserCommand implements Command {
    private final UserService userService;

    public DeleteUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Long userId = Long.parseLong(req.getParameter("userId"));
        userService.delete(userId);
        HttpSession session = req.getSession();
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.delete.user"));
        return "redirect:controller?command=all_users";
    }

}
