package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import controller.util.PagingUtil;
import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.UserService;
import service.dto.UserDto;

@Log4j2
public class AllUsersCommand implements Command {

    private UserService userService;
    private PagingUtil pagingUtil;

    public AllUsersCommand(UserService userService, PagingUtil pagingUtil) {
        this.userService = userService;
        this.pagingUtil = pagingUtil;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Paging paging = pagingUtil.getPaging(req);
        List<UserDto> users = userService.getAll(paging);
        long totalEntities = userService.count();
        long totalPage = pagingUtil.getTotalPages(totalEntities, paging.getLimit());

        if (paging.getPage() > 0 && paging.getPage() <= totalPage) {
            req.setAttribute("users", users);
            req.setAttribute("currentPage", paging.getPage());
            req.setAttribute("totalPages", totalPage);
            return "jsp/user/allusers.jsp";
        } else {
            log.error("Trying to get not existing page");
            throw new BadRequestException("Request is not correct. Page " + paging.getPage() + " is not exist.");
        }
    }

}
