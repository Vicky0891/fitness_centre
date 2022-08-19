package controller.command.impl.user;

import java.util.List;

import controller.command.Command;
import controller.util.PagingUtil;
import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.dto.ClientDto;

@Log4j2
public class AllClientsCommand implements Command {

    private ClientService clientService;
    private PagingUtil pagingUtil;

    public AllClientsCommand(ClientService clientService, PagingUtil pagingUtil) {
        this.clientService = clientService;
        this.pagingUtil = pagingUtil;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Paging paging = pagingUtil.getPaging(req);
        List<ClientDto> clients = clientService.getAll(paging);
        long totalEntities = clientService.count();
        long totalPage = pagingUtil.getTotalPages(totalEntities, paging.getLimit());

        if (paging.getPage() > 0 && paging.getPage() <= totalPage) {
            req.setAttribute("clients", clients);
            req.setAttribute("currentPage", paging.getPage());
            req.setAttribute("totalPages", totalPage);
            return "jsp/user/allclients.jsp";
        } else {
            log.error("Trying to get not existing page");
            throw new BadRequestException("Request is not correct. Page " + paging.getPage() + " is not exist.");
        }
    }

}
