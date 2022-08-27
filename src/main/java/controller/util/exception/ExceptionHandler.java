package controller.util.exception;

import controller.util.exception.impl.BadRequestException;
import controller.util.exception.impl.DaoException;
import controller.util.exception.impl.InternalErrorException;
import controller.util.exception.impl.LoginException;
import controller.util.exception.impl.NotFoundException;
import controller.util.exception.impl.RegistrationException;
import controller.util.exception.impl.SamePasswordException;
import controller.util.exception.impl.ValidateException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@Data
public class ExceptionHandler {
    public static final ExceptionHandler INSTANCE = new ExceptionHandler();
    private String message;
    private int errorStatus;
    private String page;
    private final static String DEFAULTPAGE = "jsp/error/error.jsp";

    /**
     * Method get all exceptions and redirect to error page depending on type of
     * exception
     * 
     * @param e   Exception
     * @param req HttpServletRequest
     * @return Redirect address
     */
    public String handleException(Exception e, HttpServletRequest req) {
        if (e instanceof NotFoundException) {
            message = ((NotFoundException) e).errorMessage;
            errorStatus = 404;
            page = "jsp/error/error404.jsp";
        } else if (e instanceof BadRequestException) {
            message = ((BadRequestException) e).errorMessage;
            errorStatus = 400;
            page = "jsp/error/error400.jsp";
        } else if (e instanceof SamePasswordException) {
            message = ((SamePasswordException) e).errorMessage;
            errorStatus = 400;
            page = "jsp/changepasswordform.jsp";
        } else if (e instanceof RegistrationException) {
            message = ((RegistrationException) e).errorMessage;
            errorStatus = 400;
            page = "jsp/user/createuserform.jsp";
        } else if (e instanceof LoginException) {
            message = ((LoginException) e).errorMessage;
            errorStatus = 400;
            page = "jsp/loginform.jsp";
        } else if (e instanceof ValidateException) {
            message = ((ValidateException) e).errorMessage;
            errorStatus = 406;
            page = ((ValidateException) e).page;
        } else if (e instanceof InternalErrorException) {
            message = ((InternalErrorException) e).errorMessage;
            errorStatus = 500;
            page = "jsp/error/error500.jsp";
        } else if (e instanceof DaoException) {
            message = ((DaoException) e).errorMessage;
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
