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

<label for="firstName-input">First name: </label>
<input id="firstName-input" name="firstName" type="text" value="${requestScope.user.firstName}"/>
<br/>
<label for="lastName-input">Last name: </label>
<input id="lastName-input" name="lastName" type="text" value="${requestScope.user.lasttName}"/>
<br/>
<label for="trainerId-input">Trainer id: </label>
<input id="trainerId-input" name="trainerId" type="text" value="${requestScope.user.trainerId}"/>
<br/>

<input id="role-input-admin" name="role" type="radio"
value="ADMIN" ${requestScope.user.role=='ADMIN' ? 'checked' : ''}>
<label for="role-input-admin">ADMIN</label>
<input id="role-input-client" name="role" type="radio" value="CLIENT"
<c:if test="${requestScope.user.role=='CLIENT'}">checked</c:if>>
<label for="role-input-client">CLIENT</label>
<input id="role-input-trainer" name="role" type="radio"
value="TRAINER" ${requestScope.user.role=='TRAINER' ? 'checked' : ''}>
<label for="role-input-admin">TRAINER</label>
<br/>
<br/>
<input id="type-input-regular" name="type" type="radio"
value="REGULAR" ${requestScope.user.type=='REGULAR' ? 'checked' : ''}>
<label for="type-input-regular">REGULAR</label>
<input id="type-input-new" name="type" type="radio" value="NEW"
<c:if test="${requestScope.user.type=='NEW'}">checked</c:if>>
<label for="type-input-new">NEW</label>
<input id="type-input-corporate" name="type" type="radio"
value="CORPORATE" ${requestScope.user.type=='CORPORATE' ? 'checked' : ''}>
<label for="type-input-corporate">CORPORATE</label>
<input id="type-input-other" name="type" type="radio"
value="OTHER" ${requestScope.user.type=='OTHER' ? 'checked' : ''}>
<label for="type-input-other">OTHER</label>
<br/>
<input type="submit" value="SAVE"/>
<br/>

</form>
</body>
</html>