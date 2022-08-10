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

<h1>Our trainers</h1>
<table>
<c:if test="${trainers.size() == 0}">
<h1>This catalog is empty</h1>
</c:if>
<c:if test="${trainers.size() > 0}">
<th>Id</th><th>First Name</th><th>Last Name</th><th>Email</th>  
<c:forEach items="${trainers}" var="trainer">
<tr>
<td><a href="controller?command=trainer&id=${trainer.id}"><c:out value="${trainer.id}"/></a></td>
<td><a href="controller?command=trainer&id=${trainer.id}"><c:out value="${trainer.firstName}"/></a></td>
<td><c:out value="${trainer.lastName}"/></td>
<td><c:out value="${trainer.email}"/></td>
</tr>
</c:forEach>
</c:if>
</table>           
</body>
</html>