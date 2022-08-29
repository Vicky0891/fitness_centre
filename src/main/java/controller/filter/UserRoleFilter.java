package controller.filter;

import java.io.IOException;

import controller.util.MessageManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import service.dto.UserDto;

@Log4j2
@WebFilter(urlPatterns = "/controller/*")
public class UserRoleFilter extends HttpFilter {
    private static final String DEFAULT_ROLE = "CLIENT";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String command = req.getParameter("command");
        HttpSession session = req.getSession();
        String role = DEFAULT_ROLE;

        if (session.getAttribute("user") != null) {
            UserDto userDto = (UserDto) session.getAttribute("user");
            role = userDto.getRoleDto().toString();
        }
        if (permissionRequired(role, command)) {
            log.info("Trying to access a restricted page. Command: " + command + ". Role: " + role);
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            req.setAttribute("message", messageManager.getMessage("msg.error.userrole"));
            req.getRequestDispatcher("index.jsp").forward(req, res);
            return;
        }
        chain.doFilter(req, res);
    }

    /**
     * Method to check command for access required for users depending on it role
     * and command
     * 
     * @param role    Role of user
     * @param command Commend for check
     * @return Boolean about permission required
     */
    private boolean permissionRequired(String role, String command) {
        switch (role) {
        case "CLIENT":
            return clientAccess(command);
        case "TRAINER":
            return trainersAccess(command);
        case "ADMIN":
            return false;
        }
        return true;
    }

    /**
     * Method to check command for access required for clients. If "false" - access
     * permitted
     * 
     * @param command Name of command to check
     * @return Boolean about the need of authorization
     */
    private boolean clientAccess(String command) {
        return switch (command) {
        case "gymmemberships", "gymmembership", "trainers", "trainer", "create_user_form", "create_user", "add_to_cart",
                "cart", "login", "login_form", "logout", "create_order", "user", "edit_profile_form", "edit_profile",
                "add_feedback_form", "add_feedback", "orders", "order", "remove_order", "prescription",
                "edit_prescription", "edit_prescription_form", "remove_from_cart", "change_locale", "contacts",
                "change_password_form", "change_password", "mailto" ->
            false;
        default -> true;
        };
    }

    /**
     * Method to check command for access required for trainers. If "false" - access
     * 
     * @param command Name of command to check
     * @return Boolean about the need of authorization
     */
    private boolean trainersAccess(String command) {
        return switch (command) {
        case "gymmemberships", "gymmembership", "trainers", "trainer", "create_user_form", "create_user", "add_to_cart",
                "cart", "login", "login_form", "logout", "create_order", "user", "edit_profile_form", "edit_profile",
                "prescription", "edit_cabinet_form", "edit_cabinet", "clients", "create_prescription_form",
                "create_prescription", "change_locale", "contacts", "change_password_form", "change_password",  "mailto" ->
            false;
        default -> true;
        };
    }

}
