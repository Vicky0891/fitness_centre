package controller;

import java.io.IOException;

import controller.command.Command;
import controller.command.CommandFactory;
import dao.connection.DataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/controller")
public class Controller extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String command = req.getParameter("command");
            Command commandInstance = CommandFactory.getInstance().getCommand(command);
            String page = commandInstance.execute(req);
            req.getRequestDispatcher(page).forward(req, resp);
        }

        @Override
        public void destroy() {
            DataSource.INSTANCE.close();
            System.out.println("connection close");
        }

}
