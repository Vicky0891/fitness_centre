package controller.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.util.exception.impl.ValidateException;
import controller.util.exception.impl.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ValidatorManager {
    public static final ValidatorManager INSTANCE = new ValidatorManager();

    /**
     * Method get input phone number, validate it to pattern and return it or make
     * exception
     * 
     * @param req Request with input phone number
     * @return String with validated phone number
     * @throws ValidationException
     */
    public String getCorrectPhoneNumber(HttpServletRequest req) {
        String phoneNumber = req.getParameter("phoneNumber");
        Pattern pattern = Pattern.compile("[0-9]{12}");
        Matcher matcher = pattern.matcher(phoneNumber);
        Pattern pattern2 = Pattern.compile("\\d");
        Matcher matcher2 = pattern2.matcher(phoneNumber);
        int counter = 0;
        while (matcher2.find()) {
            counter++;
        }
        if (matcher.find() && counter == 12) {
            return phoneNumber;
        } else {
            HttpSession session = req.getSession();
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            log.info("Trying to input incorrect phone number: " + phoneNumber);
            throw new ValidateException(messageManager.getMessage("msg.incorrect.phone"), getRedirect(req));
        }
    }

    /**
     * Method get input cost, validate it to pattern and return it by BigDecimal
     * format or make exception
     * 
     * @param req Request with input cost
     * @return Validated cost or exception
     * @throws ValidationException
     */
    public BigDecimal getCorrectCost(HttpServletRequest req) {
        String cost = req.getParameter("cost");
        Pattern pattern = Pattern.compile("^([0-9]+(\\.[0-9]{0,2})?)$");
        Matcher matcher = pattern.matcher(cost);
        if (matcher.find()) {
            return new BigDecimal(cost);
        } else {
            HttpSession session = req.getSession();
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            log.info("Trying to input incorrect cost: " + cost);
            throw new ValidateException(messageManager.getMessage("msg.incorrect.cost"), getRedirect(req));
        }
    }

    /**
     * Method gets two string with new password and a repeated new password, compare
     * them and return new password or exception
     * 
     * @param req Request with input cost
     * @return Validated cost or exception
     * @throws ValidationException
     */
    public String getNewPassword(HttpServletRequest req) {
        String newPassword = req.getParameter("newpassword");
        String repeatPassword = req.getParameter("repeatpassword");
        if (newPassword.equals(repeatPassword)) {
            return newPassword;
        } else {
            HttpSession session = req.getSession();
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            log.debug("Entered passwords do not match: " + newPassword + " and " + repeatPassword);
            throw new ValidateException(messageManager.getMessage("msg.changepassword.incorrect"), getRedirect(req));
        }
    }

    /**
     * Method to get address of redirect based on request
     * 
     * @param req HttpServletRequest
     * @return Name of redirect
     */
    private String getRedirect(HttpServletRequest req) {
        String redirect = req.getParameter("redirect");
        return "redirect:controller?command=" + redirect;
    }
}
