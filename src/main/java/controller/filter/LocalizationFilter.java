package controller.filter;

import java.io.IOException;
import java.util.Locale;

import controller.util.MessageManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = "/controller/*")
public class LocalizationFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = req.getSession();
        String language = (String) session.getAttribute("language");
        MessageManager messageManager;
        Locale locale = req.getLocale();
        if (language == null && locale.getLanguage().equals("ru") || language.equals("ru")) {
            messageManager = new MessageManager(new Locale("ru"));
        } else {
            messageManager = new MessageManager(Locale.UK);
        }
        session.setAttribute("manager", messageManager);
        chain.doFilter(req, res);
    }

}
