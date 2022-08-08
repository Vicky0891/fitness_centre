<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Order</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="../navbar.jsp"/>

<h1>My cart</h1>
<c:if test="${requestScope.cart == null}">
<p>Your cart is empty</p>
<h3>Choose gymmembership for you</h3>
<h3><a href="controller?command=gymmemberships">Our gymmemberships</a></h3>
</c:if>
<c:if test="${requestScope.cart != null}">
<table>
<th>Gymmembership</th><th>Quantity</th><th>Price</th>  
<c:forEach items="${requestScope.cart.details}" var="item">
<tr>
<td><a href="controller?command=gymmembership&id=${item.gymMembershipDto.id}">${item.gymMembershipDto.typeOfTraining}</a></td>
<td>${item.gymMembershipQuantity}</td>
<td>${item.gymMembershipPrice}</td>
</tr>
</c:forEach>
<tr>
<td colspan="3">TOTAL COST: ${requestScope.cart.totalCost}</td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="create_order"/>
<input type="submit" value="Buy now"/>
</form>
</td>
</tr>
</table>
<h3>${sessionScope.message}</h3>  
</c:if>
</body>
</html>