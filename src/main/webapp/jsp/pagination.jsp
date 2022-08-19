<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>


<a href="controller?command=gymmemberships&page=1">First</a>   |   
<c:if test="${requestScope.currentPage > 1}">
<a href="controller?command=gymmemberships&page=${requestScope.currentPage - 1}">Prev</a>   |   
</c:if>
Page ${requestScope.currentPage} out of ${requestScope.totalPages}   |   
<c:if test="{requestScope.currentPage < requestScope.totalPages}">
<a href="controller?command=gymmemberships&page=${requestScope.currentPage + 1}">Next</a>   |   
</c:if>
<a href="controller?command=gymmemberships&page=${requestScope.totalPages}">Last</a>