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
<c:if test="${requestScope != null}">
<h3>${requestScope.message}</h3>
</c:if>
<c:if test="${requestScope.cart == null}">
<p>Your cart is empty</p>
<h3>Choose gymmembership for you</h3>
<h3><a href="controller?command=gymmemberships">Our gymmemberships</a></h3>
</c:if>
<c:if test="${requestScope.cart != null}">

<table>
<tr><th>Gymmembership</th><th>Quantity</th><th>Price, USD</th><th></th><th></th></tr>
<c:forEach items="${requestScope.cart.details}" var="item">
<tr>
<td><a href="controller?command=gymmembership&id=${item.gymMembershipDto.id}"><c:out value="${item.gymMembershipDto.typeOfTraining}"/></a></td>
<td><c:out value="${item.gymMembershipQuantity}"/></td>
<td><c:out value="${item.gymMembershipPrice}"/></td>
<td></td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="remove_from_cart"/>
<input type="hidden" name="gymmembershipId" value="${item.gymMembershipDto.id}"/>
<input type="hidden" name="redirect" value="cart"/>
<input type="submit" value="Remove"/>
</form>
</td>


</tr>
</c:forEach>
<tr>
<td colspan="3">TOTAL COST: <c:out value="${requestScope.cart.totalCost}"/> USD</td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="create_order"/>
<input type="submit" value="Buy now"/>
</form>
</td>

<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="remove_order"/>
<input type="submit" value="Remove all"/>
</form>
</td>


</tr>
</table>
<h3>${sessionScope.message}</h3>  
</c:if>
</body>
</html>