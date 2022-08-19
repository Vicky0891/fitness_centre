package controller.command.impl.order;

import java.util.List;

import controller.command.Command;
import controller.util.PagingUtil;
import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.OrderService;
import service.dto.OrderDto;

@Log4j2
public class AllOrdersCommand implements Command {
    private OrderService orderService;
    private PagingUtil pagingUtil;

    public AllOrdersCommand(OrderService orderService, PagingUtil pagingUtil) {
        this.orderService = orderService;
        this.pagingUtil = pagingUtil;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Paging paging = pagingUtil.getPaging(req);
        List<OrderDto> orders = orderService.getAll(paging);
        long totalEntities = orderService.count();
        long totalPage = pagingUtil.getTotalPages(totalEntities, paging.getLimit());

        if (paging.getPage() > 0 && paging.getPage() <= totalPage) {
            req.setAttribute("orders", orders);
            req.setAttribute("currentPage", paging.getPage());
            req.setAttribute("totalPages", totalPage);
            return "jsp/order/allorders.jsp";
        } else {
            log.error("Trying to get not existing page");
            throw new BadRequestException("Request is not correct. Page " + paging.getPage() + " is not exist.");
        }
    }
}
