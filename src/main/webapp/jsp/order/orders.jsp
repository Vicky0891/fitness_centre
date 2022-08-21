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
<title><fmt:message key="msg.orders.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.orders.title"/></h1>
<table>
<c:if test="${orders.size() == 0}">
<h1><fmt:message key="msg.orders.noorders"/></h1>
</c:if>
<c:if test="${orders.size() > 0}">
<th>#</th><th><fmt:message key="msg.allorders.dateoforder"/></th><th><fmt:message key="msg.allorders.totalcost"/>, USD</th><th><fmt:message key="msg.allorders.status"/></th><th><fmt:message key="msg.allorders.feedback"/></th><th></th><th><fmt:message key="msg.allorders.orderdetails"/></th>
<c:forEach items="${requestScope.orders}" var="order">
<tr>
<td><a href="controller?command=order&id=${order.id}"><c:out value="${order.id}"/></a></td>
<td><c:out value="${order.dateOfOrder}"/></td>
<td><c:out value="${order.totalCost}"/> USD</td>
<td><c:out value="${order.statusDto.toString()}"/></td>
<td><c:out value="${order.feedback}"/></td>
<td><form method="post" action="controller">
<input type="hidden" name="command" value="add_feedback_form"/>
<input type="hidden" name="orderId" value="${order.id}"/>
<input type="submit" value="<fmt:message key="msg.order.titleaddfeedback"/>"/>
</form></td>
<td>
<ul>
<c:forEach items="${order.details}" var="detail">
<li><c:out value="${detail.gymMembershipDto.typeOfTraining}"/> <fmt:message key="msg.allorders.for"/> <c:out value="${detail.gymMembershipDto.numberOfVisits}"/> <fmt:message key="msg.allorders.times"/> (<c:out value="${detail.gymMembershipDto.cost}"/> USD)</li>
</c:forEach>
</ul>
</td>
</tr>          
</c:forEach>
</table>
</c:if>
</body>
</html>