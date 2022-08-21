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
<title><fmt:message key="msg.order.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.allorders.orderdetails"/></h1>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>
<h3>#: <c:out value="${requestScope.order.id}"/></h3>
<h3><fmt:message key="msg.allorders.dateoforder"/>: <c:out value="${requestScope.order.dateOfOrder}"/></h3>
<h3><fmt:message key="msg.allorders.totalcost"/>, USD: <c:out value="${requestScope.order.totalCost}"/> USD</h3>
<table>
<tr><th><fmt:message key="msg.gymmembership.typeoftraining"/></th><th><fmt:message key="msg.msg.gymmembership.numberofvisits"/></th><th><fmt:message key="msg.gymmembership.cost"/>, USD</th></tr>
<c:forEach items="${order.details}" var="detail">
<tr>
<td><c:out value="${detail.gymMembershipDto.typeOfTraining}"/></td>
<td><c:out value="${detail.gymMembershipDto.numberOfVisits}"/></td>
<td><c:out value="${detail.gymMembershipDto.cost}"/> USD</td>
</tr>          
</c:forEach>
</table>
</body>
</html>