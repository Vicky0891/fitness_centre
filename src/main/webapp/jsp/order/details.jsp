<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Details</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Details of order</h1>
<table>
<jsp:include page="../pagination/gympagination.jsp"/>

<c:if test="${gymmemberships.size() == 0}">
<h1>No available gymmemberships</h1>
</c:if>
<c:if test="${gymmemberships.size() > 0}">
<th>Id</th><th>Number of visits</th><th>Type of training</th><th>Cost</th><th></th>     
<c:forEach items="${requestScope.gymmemberships}" var="gymmembership">
<tr>
<td><c:out value="${gymmembership.id}"/></td>
<td><c:out value="${gymmembership.numberOfVisits}"/></td>
<td><a href="controller?command=gymmembership&id=${gymmembership.id}"><c:out value="${gymmembership.typeOfTraining}"/></a></td>
<td><c:out value="${gymmembership.cost}"/> USD</td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="add_to_cart"/>
<input type="hidden" name="gymmembershipId" value="${gymmembership.id}"/>
<input type="submit" value="Add to cart"/>
</form>
</tr>
</c:forEach>
</c:if>
</table>           
</body>
</html>