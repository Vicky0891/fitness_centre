package controller.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

public enum PagingUtil {
    INSTANCE;

    /**
     * Method to get parameters for pagination based on request. If parameters is
     * not available default value is set
     * 
     * @param req Request with parameters
     * @return Paging object with parameters for pagination
     */
    public Paging getPaging(HttpServletRequest req) {
        String limitStr = req.getParameter("limit");
        int limit;
        if (limitStr == null) {
            limit = 5;
        } else {
            limit = Integer.valueOf(limitStr);
        }
        String offsetStr = req.getParameter("page");
        Long page;
        if (offsetStr == null) {
            page = (long) 1;
        } else {
            page = Long.valueOf(offsetStr);
        }
        long offset = (page - 1) * limit;
        return new Paging(limit, offset, page);
    }

    /**
     * Method to get total pages for pagination
     * 
     * @param totalEntities Quantity of objects
     * @param limit         Number of entities to be show on page
     * @return quantity of total pages
     */
    public long getTotalPages(long totalEntities, int limit) {
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
