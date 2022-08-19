<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Trainers</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="../navbar.jsp"/>

<h1>My clients</h1>
<table>
<c:if test="${clients.size() == 0}">
<h1>You don't have any clients yet</h1>
</c:if>
<c:if test="${clients.size() > 0}">
<tr><th>Account number</th><th>First Name</th><th>Last Name</th><th>Email</th><th>Birth date</th><th>Type</th><th>Additional info</th></tr>     
<c:forEach items="${clients}" var="client">
<tr>
<td><c:out value="${client.id}"/></td>
<td><c:out value="${client.firstName}"/></td>
<td><c:out value="${client.lastName}"/></td>
<td><c:out value="${client.email}"/></td>
<td><c:out value="${client.birthDate}"/></td>
<td><c:out value="${client.type.toString().toLowerCase()}"/></td>
<td><c:out value="${client.additionalInfo}"/></td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="prescription"/>
<input type="hidden" name="clientId" value="${client.id}"/>
<input type="submit" value="Check prescription"/>
</form>
</td>
</tr>
</c:forEach>
</c:if>
</table>           
</body>
</html>