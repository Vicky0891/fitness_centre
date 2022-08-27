package controller.command.impl;

import controller.command.Command;
import controller.util.MessageManager;
import controller.util.ValidatorManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.UserService;
import service.dto.UserDto;

@Log4j2
public class ChangePasswordCommand implements Command {
    private final UserService userService;

    public ChangePasswordCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        ValidatorManager validator = new ValidatorManager();
        String newpassword = validator.getNewPassword(req);
        userDto.setPassword(newpassword);
        UserDto updated = userService.changePassword(userDto);
        log.info("User password was update, user={}", updated);
        MessageManager messageManager = (MessageManager) session.getAttribute("manager");
        req.setAttribute("message", messageManager.getMessage("msg.update.feedback"));
        return "jsp/user/user.jsp";
    }

}
