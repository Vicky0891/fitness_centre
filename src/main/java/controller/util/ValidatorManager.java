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

    /**
     * Method get input phone number, validate it to pattern and return it or make
     * exception
     * 
     * @param req Request with input phone number
     * @return String with validated phone number
     * @throws ValidationException
     */
    public String getPhoneNumber(HttpServletRequest req) throws ValidationException {
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
            String page = req.getParameter("page");
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            log.info("Trying to input incorrect phone number: " + phoneNumber);
            throw new ValidateException(messageManager.getMessage("msg.incorrect.phone"), page);
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
    public BigDecimal getCost(HttpServletRequest req) throws ValidationException {
        String cost = req.getParameter("cost");
        Pattern pattern = Pattern.compile("^([0-9]+(\\.[0-9]{0,2})?)$");
        Matcher matcher = pattern.matcher(cost);
        if (matcher.find()) {
            return new BigDecimal(cost);
        } else {
            HttpSession session = req.getSession();
            String page = req.getParameter("page");
            MessageManager messageManager = (MessageManager) session.getAttribute("manager");
            log.info("Trying to input incorrect cost: " + cost);
            throw new ValidateException(messageManager.getMessage("msg.incorrect.cost"), page);
        }
    }

}
