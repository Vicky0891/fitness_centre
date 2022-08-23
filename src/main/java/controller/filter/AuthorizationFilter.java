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

@Log4j2
@WebFilter(urlPatterns = "/controller/*")
public class AuthorizationFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String command = req.getParameter("command");
        if (requiresAutorization(command)) {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                log.info("Trying to access a restricted page. Command: " + command);
                MessageManager messageManager = (MessageManager) session.getAttribute("manager");
                req.setAttribute("message", messageManager.getMessage("msg.error.autorization"));
                req.getRequestDispatcher("jsp/loginform.jsp").forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private boolean requiresAutorization(String command) {

        return switch (command) {
        case "gymmemberships", "gymmembership", "trainers", "trainer", "create_user_form", "create_user", "add_to_cart",
                "remove_from_cart", "cart", "login", "login_form", "create_order", "remove_order", "change_locale",
                "contacts" ->
            false;
        default -> true;
        };
    }

}
