package controller.command;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {

    /**
     * Method to execute command based on request
     * 
     * @param req Request for getting data
     * @return Address of pages to go
     * @throws Exception
     */
    public String execute(HttpServletRequest req) throws Exception;

}
