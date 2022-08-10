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

<label for="trainerId-input">Trainer id: </label>
<input id="trainerId-input" name="trainerId" type="text" value="${requestScope.user.trainerId}"/>
<br/>

<input id="role-input-admin" name="role" type="radio"
value="ADMIN" ${requestScope.user.roleDto=='ADMIN' ? 'checked' : ''}>
<label for="role-input-admin">ADMIN</label>
<input id="role-input-client" name="role" type="radio" value="CLIENT"
<c:if test="${requestScope.user.roleDto=='CLIENT'}">checked</c:if>>
<label for="role-input-client">CLIENT</label>
<input id="role-input-trainer" name="role" type="radio"
value="TRAINER" ${requestScope.user.roleDto=='TRAINER' ? 'checked' : ''}>
<label for="role-input-admin">TRAINER</label>
<br/>
<br/>
<input id="type-input-regular" name="type" type="radio"
value="REGULAR" ${requestScope.user.typeDto=='REGULAR' ? 'checked' : ''}>
<label for="type-input-regular">REGULAR</label>
<input id="type-input-new" name="type" type="radio" value="NEW"
<c:if test="${requestScope.user.typeDto=='NEW'}">checked</c:if>>
<label for="type-input-new">NEW</label>
<input id="type-input-corporate" name="type" type="radio"
value="CORPORATE" ${requestScope.user.typeDto=='CORPORATE' ? 'checked' : ''}>
<label for="type-input-corporate">CORPORATE</label>

<br/>
<input type="submit" value="SAVE"/>
<br/>

<h2>Trainers</h2>
<table>
<th>Id</th><th>First Name</th><th>Last Name</th><th>Email</th>  
<c:forEach items="${trainers}" var="trainer">
<tr>
<td><a href="controller?command=trainer&id=${trainer.id}"><c:out value="${trainer.id}"/></a></td>
<td><a href="controller?command=trainer&id=${trainer.id}"><c:out value="${trainer.firstName}"/></a></td>
<td><c:out value="${trainer.lastName}"/></td>
<td><c:out value="${trainer.email}"/></td>
</tr>
</c:forEach>
</table>  

</form>
</body>
</html>