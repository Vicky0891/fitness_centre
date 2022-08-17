<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Gymmembership</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<c:if test="${gymmembership.id != null}">
<h1>Info about gymmembership</h1>
<c:if test="${requestScope.message != null}">
<h3>${requestScope.message}</h3>
</c:if>

<h3>Id: <c:out value="${gymmembership.id}"/></h3>
<h3>Type of training: <c:out value="${gymmembership.typeOfTraining}"/></h3>
<h3>Number of visits: <c:out value="${gymmembership.numberOfVisits}"/></h3>
<h3>Cost: <c:out value="${gymmembership.cost}"/> USD</h3>
<br/>
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'ADMIN'}">
<td><form method="post" action="controller">
<input type="hidden" name="command" value="edit_gymmembership_form"/>
<input type="hidden" name="gymmembershipId" value="${gymmembership.id}"/>
<input type="submit" value="Edit gymmembership"/>
</form><td>
</c:if>
<c:if test="${gymmembership.id == null}">
<jsp:include page="../error.jsp"/>
</c:if>
</body>
</html>