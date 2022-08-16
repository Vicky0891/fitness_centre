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
<h1>Edit client</h1>
<c:if test="${requestScope.message != null}">
<h2>${requestScope.message}</h2>
</c:if>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_client"/>
<input name="id" type="hidden" value="${requestScope.client.id}"/>

<label for="trainerId-input">Trainer id: </label>
<input id="trainerId-input" name="trainerId" type="number" min="1" value="${requestScope.client.trainerId}"/>
<br/>
<h2>Choose type</h2>
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
</form>
<br/>

<h2>Trainers</h2>
<table>
<tr><th>Id</th><th>First Name</th><th>Last Name</th></tr>  
<c:forEach items="${trainers}" var="trainer">
<tr>
<td><a href="controller?command=trainer&id=${trainer.id}"><c:out value="${trainer.id}"/></a></td>
<td><c:out value="${trainer.firstName}"/></td>
<td><a href="controller?command=trainer&id=${trainer.id}"><c:out value="${trainer.lastName}"/></a></td>
</tr>
</c:forEach>
</table>  

</body>
</html>
