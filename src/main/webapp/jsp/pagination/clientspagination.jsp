<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${sessionScope.language != null}">
<fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${sessionScope.language}">


<a href="controller?command=all_clients&page=1"><fmt:message key="msg.pagination.first"/></a>   |   
<c:if test="${requestScope.currentPage > 1}">
<a href="controller?command=all_clients&page=${requestScope.currentPage - 1}"><fmt:message key="msg.pagination.prev"/></a>   |   
</c:if>
<fmt:message key="msg.pagination.page"/> ${requestScope.currentPage} <fmt:message key="msg.pagination.outof"/> ${requestScope.totalPages}   |   
<c:if test="${requestScope.currentPage < requestScope.totalPages}">
<a href="controller?command=all_clients&page=${requestScope.currentPage + 1}"><fmt:message key="msg.pagination.next"/></a>   |   
</c:if>
<a href="controller?command=all_clients&page=${requestScope.totalPages}"><fmt:message key="msg.pagination.last"/></a>