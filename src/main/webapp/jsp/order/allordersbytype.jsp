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
<title><fmt:message key="msg.allorders.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<header>
<jsp:include page="../navbar.jsp"/>
</header>
<h1><fmt:message key="msg.allorders.alloderstatus"/> "<c:out value="${requestScope.status.toString().toLowerCase()}"/>"</h1>
<table>
<c:if test="${orders.size() == 0}">
<h1><fmt:message key="msg.allorders.noorders"/></h1>
</c:if>
<c:if test="${orders.size() > 0}">
<tr><th>#</th><th><fmt:message key="msg.allorders.dateoforder"/></th><th><fmt:message key="msg.allorders.orderdetails"/></th><th><fmt:message key="msg.allorders.totalcost"/>, USD</th><th><fmt:message key="msg.allorders.clientid"/></th><th><fmt:message key="msg.allorders.feedback"/></th></tr>
<c:forEach items="${requestScope.orders}" var="order">
<tr>
<td><a href="controller?command=order&id=${order.id}"><c:out value="${order.id}"/></a></td>
<td><c:out value="${order.dateOfOrder}"/></td>
<td>
<ul>
<c:forEach items="${order.details}" var="detail">
<li><c:out value="${detail.gymMembershipDto.typeOfTraining}"/> <fmt:message key="msg.allorders.for"/> <c:out value="${detail.gymMembershipDto.numberOfVisits}"/> <fmt:message key="msg.allorders.times"/> (<c:out value="${detail.gymMembershipDto.cost}"/> USD)</li>
</c:forEach>
</ul>
</td>
<td><c:out value="${order.totalCost}"/> USD</td>
<td><c:out value="${order.userId}"/></td>
<td><c:out value="${order.feedback}"/></td>
<td><form method="post" action="controller">
<input type="hidden" name="command" value="edit_order_form"/>
<input type="hidden" name="orderId" value="${order.id}"/>
<input type="submit" value="<fmt:message key="msg.allorders.editorderstatus"/>"/>
</form></td>
</tr>          
</c:forEach>


</table>
</c:if>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>