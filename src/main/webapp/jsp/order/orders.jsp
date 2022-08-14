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
<h1>My orders</h1>
<table>
<c:if test="${orders.size() == 0}">
<h1>You don't have any orders</h1>
</c:if>
<c:if test="${orders.size() > 0}">
<th>#</th><th>Date of order</th><th>Items</th><th>Total cost</th><th>Status</th><th>Feedback</th><th>Order details</th>
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
<input type="submit" value="Add feedback"/>
</form><td>


<td><a href="controller?command=add_feedback_form">Add feedback</a></td>
<td>
<ul>
<c:forEach items="${order.details}" var="detail">
<li><c:out value="${detail.gymMembershipDto.typeOfTraining}"/> for <c:out value="${detail.gymMembershipDto.numberOfVisits}"/> times (<c:out value="${detail.gymMembershipDto.cost}"/> USD)</li>
</c:forEach>
</ul>
</td>
</tr>          
</c:forEach>


</table>
</c:if>
</body>
</html>