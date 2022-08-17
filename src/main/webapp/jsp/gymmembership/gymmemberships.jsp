<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Gymmemberships</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Our gymmemberships</h1>

<c:if test="${sessionScope.user.roleDto.toString() == 'ADMIN'}">
<form method="post" action="controller">
<input type="hidden" name="command" value="create_gymmembership_form"/>
<input type="submit" value="Create new gymmembership"/>
</form>
</c:if>
<table>
<jsp:include page="../pagination.jsp"/>

<c:if test="${gymmemberships.size() == 0}">
<h1>No available gymmemberships</h1>
</c:if>
<c:if test="${gymmemberships.size() > 0}">
<tr><th>Id</th><th>Number of visits</th><th>Type of training</th><th>Cost</th><th></th><th></th></tr>    
<c:forEach items="${requestScope.gymmemberships}" var="gymmembership">
<tr>
<td><c:out value="${gymmembership.id}"/></td>
<td><c:out value="${gymmembership.numberOfVisits}"/></td>
<td><a href="controller?command=gymmembership&id=${gymmembership.id}"><c:out value="${gymmembership.typeOfTraining}"/></a></td>
<td><c:out value="${gymmembership.cost}"/> USD</td>
<c:if test="${sessionScope.user.roleDto.toString() != 'ADMIN' && sessionScope.user.roleDto.toString() != 'TRAINER'}">
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="add_to_cart"/>
<input type="hidden" name="gymmembershipId" value="${gymmembership.id}"/>
<input type="submit" value="Add to cart"/>
</form>
</td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="remove_from_cart"/>
<input type="hidden" name="gymmembershipId" value="${gymmembership.id}"/>
<input type="submit" value="Remove from cart"/>
</form>
</td>
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'ADMIN'}">
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="delete_gymmembership"/>
<input type="hidden" name="gymmembershipId" value="${gymmembership.id}"/>
<input type="submit" value="Delete"/>
</form>
</td>
</c:if>
</tr>
</c:forEach>
</c:if>
</table>           
</body>
</html>