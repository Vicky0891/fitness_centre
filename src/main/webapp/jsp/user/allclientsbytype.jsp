<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${sessionScope.language != null}">
<fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<head>
<title><fmt:message key="msg.allclientsbytype.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="../navbar.jsp"/>

<h1><fmt:message key="msg.allclientsbytype.all"/> <c:out value="${requestScope.type.toString().toLowerCase()}"/> <fmt:message key="msg.allclientsbytype.clients"/></h1>

<table>
<c:if test="${clients.size() == 0}">
<h1><fmt:message key="msg.allclientsbytype.noclients"/></h1>
</c:if>
<c:if test="${clients.size() > 0}">


<tr><th>ID</th><th><fmt:message key="msg.user.firstname"/></th><th><fmt:message key="msg.user.lastname"/></th><th><fmt:message key="msg.login.email"/></th><th><fmt:message key="msg.user.birthdate"/></th><th><fmt:message key="msg.user.phonenumber"/></th><th><fmt:message key="msg.editclient.trainerid"/></th><th><fmt:message key="msg.user.addinfo"/></th></tr>     
<c:forEach items="${clients}" var="client">
<tr>
<td><c:out value="${client.id}"/></td>
<td><c:out value="${client.firstName}"/></td>
<td><c:out value="${client.lastName}"/></td>
<td><c:out value="${client.email}"/></td>
<td><c:out value="${client.birthDate}"/></td>
<td><c:out value="${client.phoneNumber}"/></td>
<td><c:out value="${client.trainerId}"/></td>
<td><c:out value="${client.additionalInfo}"/></td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_client_form"/>
<input type="hidden" name="clientId" value="${client.id}"/>
<input type="submit" value="<fmt:message key="msg.gymmembership.edit"/>"/>
</form>
</td>
</tr>
</c:forEach>
</c:if>
</table>           
</body>
</html>