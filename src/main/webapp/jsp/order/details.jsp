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
<title><fmt:message key="msg.details.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.details.detailsoforder"/></h1>
<table>
<jsp:include page="../pagination/gympagination.jsp"/>

<c:if test="${gymmemberships.size() == 0}">
<h1><fmt:message key="msg.gymmembership.noavailable"/></h1>
</c:if>
<c:if test="${gymmemberships.size() > 0}">
<th>#</th><th><fmt:message key="msg.gymmembership.numberofvisits"/></th><th><fmt:message key="msg.gymmembership.typeoftraining"/></th><th><fmt:message key="msg.gymmembership.cost"/>, USD</th><th></th>     
<c:forEach items="${requestScope.gymmemberships}" var="gymmembership">
<tr>
<td><c:out value="${gymmembership.id}"/></td>
<td><c:out value="${gymmembership.numberOfVisits}"/></td>
<td><a href="controller?command=gymmembership&id=${gymmembership.id}"><c:out value="${gymmembership.typeOfTraining}"/></a></td>
<td><c:out value="${gymmembership.cost}"/> USD</td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="add_to_cart"/>
<input type="hidden" name="gymmembershipId" value="${gymmembership.id}"/>
<input type="submit" value="<fmt:message key="msg.gymmembership.addtocart"/>"/>
</form>
</td>
</tr>
</c:forEach>
</c:if>
</table>           
</body>
</html>