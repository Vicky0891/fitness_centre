package controller.util.exception;

import controller.util.exception.impl.BadRequestException;
import controller.util.exception.impl.InternalErrorException;
import controller.util.exception.impl.LoginException;
import controller.util.exception.impl.NotFoundException;
import controller.util.exception.impl.RegistrationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@Data
public class ExceptionHandler {
    public static final ExceptionHandler INSTANCE = new ExceptionHandler();
    private String message;
    private int errorStatus;
    private String page;
    private final static String DEFAULTPAGE = "jsp/error/error.jsp";
    
    public String handleException (Exception e, HttpServletRequest req) {
        if (e instanceof NotFoundException) {
            message = ((NotFoundException) e).errorMessage;
            errorStatus = 404;
            page = "index.jsp";
        } else if (e instanceof BadRequestException) {
            message = ((BadRequestException) e).errorMessage;
            errorStatus = 400;
            page = "jsp/error/error400.jsp";
        } else if (e instanceof RegistrationException) {
            message = ((RegistrationException) e).errorMessage;
            errorStatus = 400;
            page = "jsp/user/createuserform.jsp";
        } else if (e instanceof LoginException) {
            message = ((LoginException) e).errorMessage;
            errorStatus = 400;
            page = "jsp/loginform.jsp";
        } else if (e instanceof InternalErrorException) {
            message = ((InternalErrorException) e).errorMessage;
            errorStatus = 500;
            page = "jsp/error/error500.jsp";
        } else {
            message = "Internal server error";
            errorStatus = 500;
            page = DEFAULTPAGE;
        }
        
        req.setAttribute("message", message);
        req.setAttribute("errorStatus", errorStatus);
        return page;
    }

}
