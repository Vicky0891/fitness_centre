package controller.impl;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.CustomerService;
import service.dto.CustomerDto;

@Log4j2
public class CustomerCommand implements Command {
    private CustomerService customerService;

    public CustomerCommand(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            CustomerDto customerDto = customerService.getById(id);
            req.setAttribute("customer", customerDto);
            return "jsp/customer.jsp";
        } catch (NumberFormatException e) {
            log.error("Request isn't correct" + e);
            return "jsp/error.jsp";
        }
    }

}
