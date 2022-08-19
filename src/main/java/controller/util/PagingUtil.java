package controller.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

public enum PagingUtil {
    INSTANCE;
    
    public Paging getPaging(HttpServletRequest req) {
        String limitStr = req.getParameter("limit");
        int limit;
        if(limitStr == null) {
            limit = 7;
        } else {
            limit = Integer.valueOf(limitStr);
        }
        String offsetStr = req.getParameter("page");
        Long page;
        if(offsetStr == null) {
            page = (long)1;
        } else {
            page = Long.valueOf(offsetStr);
        }
        long offset = (page - 1) * limit;
        return new Paging(limit, offset, page);
    }
    
    public long getTotalPages (long totalEntities, int limit) {
        long totalPages = totalEntities / limit;
        int addPage = (totalEntities - (totalPages * limit) > 0) ? 1 : 0;
        return totalPages + addPage;
            
    }
    
    
    @Data
    public static class Paging {
        private final int limit;
        private final long offset;
        private final long page;
    }

}
