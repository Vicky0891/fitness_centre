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
<h3>Id: ${user.id}</h3>
<h3>First name: ${user.firstName}</h3>
<h3>Last name: ${user.lastName}</h3>
<h3>Email: ${user.email}</h3>
</c:if>


<form method="post" action="controller">
<input type="hidden" name="command" value="edit_user_form"/>
<input type="hidden" name="id" value="${user.id}"/>
<input type="submit" value="Edit personal information"/>

<c:if test="${user.id == null}">
<h1 class="error">Something went wrong</h1>
</c:if>
</body>
</html>