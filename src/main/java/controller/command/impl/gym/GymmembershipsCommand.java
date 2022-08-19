package controller.command.impl.gym;

import java.util.List;

import controller.command.Command;
import controller.util.PagingUtil;
import controller.util.PagingUtil.Paging;
import jakarta.servlet.http.HttpServletRequest;
import service.GymMembershipService;
import service.dto.GymMembershipDto;

public class GymmembershipsCommand implements Command {
    private GymMembershipService gymMembershipService;
    private PagingUtil pagingUtil;

    public GymmembershipsCommand(GymMembershipService gymMembershipService, PagingUtil pagingUtil) {
        this.gymMembershipService = gymMembershipService;
        this.pagingUtil = pagingUtil;
    }

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        Paging paging = pagingUtil.getPaging(req);
        List<GymMembershipDto> gymMemberships = gymMembershipService.getAll(paging);
        long totalEntities = gymMembershipService.count();
        long totalPage = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
        
        if(paging.getPage() > 0 && paging.getPage() <= totalPage) {
            req.setAttribute("currentPage", paging.getPage());
        } else {
            req.setAttribute("currentPage", 1);
        }
        req.setAttribute("gymmemberships", gymMemberships);
        req.setAttribute("totalPages", totalPage);
        return "jsp/gymmembership/gymmemberships.jsp";
        
        
        
    }
}
