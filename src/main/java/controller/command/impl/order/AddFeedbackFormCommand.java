package controller.command.impl.order;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class AddFeedbackFormCommand implements Command{
    
    @Override
    public String execute(HttpServletRequest req) {
        
    return "jsp/order/addfeedbackform.jsp";
    }

}
