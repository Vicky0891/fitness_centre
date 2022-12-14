package controller;

import java.io.IOException;

import controller.command.Command;
import controller.command.CommandFactory;
import controller.util.exception.ExceptionHandler;
import dao.connection.DataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@MultipartConfig(maxFileSize = Controller.MB * 10, maxRequestSize = Controller.MB * 100)
@WebServlet("/controller")
public class Controller extends HttpServlet {

    public static final String REDIRECT = "redirect:";
    private ExceptionHandler exceptionHandler = ExceptionHandler.INSTANCE;
    public static final int MB = 1024 * 1024;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        String page;
        try {
            Command commandInstance = CommandFactory.getInstance().getCommand(command);
            page = commandInstance.execute(req);
            checkRedirect(page, req, resp);
        } catch (Exception e) {
            log.error("Request isn't correct " + e);
            page = exceptionHandler.handleException(e, req);
            checkRedirect(page, req, resp);
        }
    }

    @Override
    public void destroy() {
        DataSource.INSTANCE.close();
    }

    /**
     * Method checks if the page contains a "redirect" and sends according to the
     * request
     * 
     * @param page Address of page for check
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    private void checkRedirect(String page, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        if (page.startsWith(REDIRECT)) {
            resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
            return;
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }
}
