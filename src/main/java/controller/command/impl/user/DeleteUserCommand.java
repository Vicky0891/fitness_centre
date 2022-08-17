package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import service.UserService;

public class DeleteUserCommand implements Command {
    private final UserService userService;

    public DeleteUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long userId = Long.parseLong(req.getParameter("userId"));
        userService.delete(userId);
        req.setAttribute("message", "User deleted successfully");
        return "redirect:controller?command=all_users";
    }

}
