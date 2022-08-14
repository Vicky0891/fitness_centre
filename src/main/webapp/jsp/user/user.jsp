<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>User</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<c:if test="${user.id != null}">
<h1>My profile</h1>
<h3>Account number: <c:out value="${user.id}"/></h3>
<h3>Email: <c:out value="${user.email}"/></h3>





<br/>
<h3><a href="controller?command=orders">My orders</a></h3>
<h3><a href="controller?command=prescription&id=${user.id}">Personal prescription</a></h3>
</c:if>



<form method="post" action="controller">
<input type="hidden" name="command" value="edit_profile_form"/>
<input type="hidden" name="id" value="${user.id}"/>
<input type="submit" value="Edit personal information"/>
</form>


<c:if test="${user.roleDto.toString() == 'ADMIN'}">
<h2>ADMIN</h2>
</c:if>

<c:if test="${user.id == null}">
<h1 class="error">Something went wrong</h1>
</c:if>
</body>
</html>