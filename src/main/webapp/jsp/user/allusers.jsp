<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>All users</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="../navbar.jsp"/>

<h1>All users</h1>
<table>
<c:if test="${users.size() == 0}">
<h1>No users</h1>
</c:if>
<c:if test="${users.size() > 0}">
<br/>
<jsp:include page="../pagination/userspagination.jsp"/>
<tr><th>Id</th><th>Email</th><th>Role</th></tr>  
<c:forEach items="${users}" var="user">
<tr>
<td><a href="controller?command=user&id=${user.id}"><c:out value="${user.id}"/></a></td>
<td><a href="controller?command=user&id=${user.id}"><c:out value="${user.email}"/></a></td>
<td><c:out value="${user.roleDto.toString().toLowerCase()}"/></td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_user_form"/>
<input type="hidden" name="userId" value="${user.id}"/>
<input type="submit" value="Edit"/>
</form>
</td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="delete_user"/>
<input type="hidden" name="userId" value="${user.id}"/>
<input type="submit" value="Delete"/>
</form>
</td>
</tr>
</c:forEach>
</c:if>
</table>           
</body>
</html>