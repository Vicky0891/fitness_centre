package controller.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.dto.UserDto;

@WebFilter(urlPatterns = "/controller/*")
public class UserRoleFilter extends HttpFilter {
    private static final String DEFAULT_ROLE = "CLIENT";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String command = req.getParameter("command");
        HttpSession session = req.getSession(false);
        String role = DEFAULT_ROLE;

        if (session.getAttribute("user") != null) {
            UserDto userDto = (UserDto) session.getAttribute("user");
            role = userDto.getRoleDto().toString();
        }
        if (permissionRequired(role, command)) {
            req.setAttribute("message", "Don't have sufficient rights to complete the request");
            req.getRequestDispatcher("index.jsp").forward(req, res);
            return;
        }
        chain.doFilter(req, res);
    }

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

    private boolean clientAccess(String command) {
        return switch (command) {
        case "gymmemberships", "gymmembership", "trainers", "trainer", "create_user_form", "create_user", "add_to_cart",
                "cart", "login", "login_form", "logout", "create_order", "user", "edit_profile_form", "edit_profile",
                "add_feedback_form", "add_feedback", "orders", "order", "remove_order", "prescription",
                "edit_prescription", "edit_prescription_form", "remove_from_cart" ->
            false;
        default -> true;
        };
    }

    private boolean trainersAccess(String command) {
        return switch (command) {
        case "gymmemberships", "gymmembership", "trainers", "trainer", "create_user_form", "create_user", "add_to_cart",
                "cart", "login", "login_form", "logout", "create_order", "user", "edit_profile_form", "edit_profile",
                "prescription" ->
            false;
        default -> true;
        };
    }

}
