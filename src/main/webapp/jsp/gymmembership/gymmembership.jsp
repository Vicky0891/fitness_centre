<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${sessionScope.language != null}">
<fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<head>
<title><fmt:message key="msg.gymmembership.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<c:if test="${gymmembership.id != null}">
<h1><fmt:message key="msg.gymmembership.info"/></h1>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>

<h3>#: <c:out value="${gymmembership.id}"/></h3>
<h3><fmt:message key="msg.gymmembership.typeoftraining"/>: <c:out value="${gymmembership.typeOfTraining}"/></h3>
<h3><fmt:message key="msg.gymmembership.numberofvisits"/>: <c:out value="${gymmembership.numberOfVisits}"/></h3>
<h3><fmt:message key="msg.gymmembership.cost"/>: <c:out value="${gymmembership.cost}"/> USD</h3>
<br/>
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'ADMIN'}">
<td><form method="post" action="controller">
<input type="hidden" name="command" value="edit_gymmembership_form"/>
<input type="hidden" name="gymmembershipId" value="${gymmembership.id}"/>
<input type="submit" value="<fmt:message key="msg.gymmembership.edit"/>"/>
</form><td>
</c:if>
<c:if test="${gymmembership.id == null}">
<jsp:include page="../error/error.jsp"/>
</c:if>
</body>
</html>