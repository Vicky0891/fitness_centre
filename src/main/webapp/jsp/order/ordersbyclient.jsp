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
<h1>Your orders</h1>
<table>
<c:if test="${orders.size() == 0}">
<h1>You don't have any orders</h1>
</c:if>
<c:if test="${orders.size() > 0}">
<th>Id</th><th>Date of order</th><th>Items</th><th>Total cost</th><th>Status</th><th>Feedback</th>    
<c:forEach items="${orders}" var="order">
<tr>
<td><a href="controller?command=order&id=${order.id}">${order.id}</a></td>
<td>${order.dateOfOrder}</td>
<td>${order.totalCost} USD</td>
<td>${order.statusDto.toString()}</td>
<td>${order.feedback}</td>
</tr>
</c:forEach>
</c:if>
</table>           
</body>
</html>