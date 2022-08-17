<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Orders</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>All orders</h1>
<table>
<c:if test="${orders.size() == 0}">
<h1>No orders</h1>
</c:if>
<c:if test="${orders.size() > 0}">
<tr><th>#</th><th>Date of order</th><th>Order details</th><th>Total cost</th><th>Client id</th><th>Status</th><th>Feedback</th><th></th></tr>
<c:forEach items="${requestScope.orders}" var="order">
<tr>
<td><a href="controller?command=order&id=${order.id}"><c:out value="${order.id}"/></a></td>
<td><c:out value="${order.dateOfOrder}"/></td>
<td>
<ul>
<c:forEach items="${order.details}" var="detail">
<li><c:out value="${detail.gymMembershipDto.typeOfTraining}"/> for <c:out value="${detail.gymMembershipDto.numberOfVisits}"/> times (<c:out value="${detail.gymMembershipDto.cost}"/> USD)</li>
</c:forEach>
</ul>
</td>
<td><c:out value="${order.totalCost}"/> USD</td>
<td><c:out value="${order.userId}"/></td>
<td><c:out value="${order.statusDto.toString()}"/></td>
<td><c:out value="${order.feedback}"/></td>

<td><form method="post" action="controller">
<input type="hidden" name="command" value="edit_order_form"/>
<input type="hidden" name="orderId" value="${order.id}"/>
<input type="submit" value="Edit order status"/>
</form><td>


</tr>          
</c:forEach>


</table>
</c:if>
</body>
</html>