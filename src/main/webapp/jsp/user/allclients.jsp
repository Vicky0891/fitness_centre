<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>All clients</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="../navbar.jsp"/>

<h1>All clients</h1>
<c:if test="${requestScope.message != null}">
<h2>${requestScope.message}</h2>
</c:if>
<table>
<c:if test="${clients.size() == 0}">
<h1>No clients yet</h1>
</c:if>
<c:if test="${clients.size() > 0}">

<h2>Select to view one type of client</h2>
<br/>
<form method="post" action="controller">
<input name="command" type="hidden" value="all_clients_by_type"/>
<input id="type-input-regular" name="type" type="radio" value="REGULAR">
<label for="type-input-regular">REGULAR</label>
<input id="type-input-new" name="type" type="radio" value="NEW">
<label for="type-input-new">NEW</label>
<input id="type-input-corporate" name="type" type="radio" value="CORPORATE">
<label for="type-input-corporate">CORPORATE</label>

<br/>
<input type="submit" value="SELECT"/>
</form>
<br/>
<jsp:include page="../pagination/clientspagination.jsp"/>

<tr><th>Id</th><th>First Name</th><th>Last Name</th><th>Email</th><th>Birth date</th><th>Phone number</th><th>Type</th><th>Trainer id</th><th>Additional info</th></tr>     
<c:forEach items="${clients}" var="client">
<tr>
<td><c:out value="${client.id}"/></td>
<td><c:out value="${client.firstName}"/></td>
<td><c:out value="${client.lastName}"/></td>
<td><c:out value="${client.email}"/></td>
<td><c:out value="${client.birthDate}"/></td>
<td><c:out value="${client.phoneNumber}"/></td>
<td><c:out value="${client.type.toString().toLowerCase()}"/></td>
<td><c:out value="${client.trainerId}"/></td>
<td><c:out value="${client.additionalInfo}"/></td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_client_form"/>
<input type="hidden" name="clientId" value="${client.id}"/>
<input type="submit" value="Edit"/>
</form>
</td>
</tr>
</c:forEach>
</c:if>
</table>           
</body>
</html>