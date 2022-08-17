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
<h1>Order details</h1>



<h3>№ of order: <c:out value="${requestScope.order.id}"/></h3>
<h3>Date of order: <c:out value="${requestScope.order.dateOfOrder}"/></h3>
<h3>Total cost: <c:out value="${requestScope.order.totalCost}"/> USD</h3>

<table>
<tr><th>Type of training</th><th>Number of visits</th><th>Cost</th></tr>
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