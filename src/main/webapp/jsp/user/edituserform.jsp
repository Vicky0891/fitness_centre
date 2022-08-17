<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Edit user</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Edit user</h1>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_user"/>
<input name="id" type="hidden" value="${requestScope.user.id}"/>

<input id="role-input-admin" name="role" type="radio"
value="ADMIN" ${requestScope.user.roleDto=='ADMIN' ? 'checked' : ''}>
<label for="role-input-admin">ADMIN</label>
<input id="role-input-client" name="role" type="radio" value="CLIENT"
<c:if test="${requestScope.user.roleDto=='CLIENT'}">checked</c:if>>
<label for="role-input-client">CLIENT</label>
<input id="role-input-trainer" name="role" type="radio"
value="TRAINER" ${requestScope.user.roleDto=='TRAINER' ? 'checked' : ''}>
<label for="role-input-trainer">TRAINER</label>
<br/>
<input type="submit" value="SAVE"/>
<br/>
</form>
</body>
</html>