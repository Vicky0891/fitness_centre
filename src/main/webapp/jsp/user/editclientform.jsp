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
<title><fmt:message key="msg.editclient.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.editclient.title"/></h1>
<c:if test="${requestScope.message != null}">
<h2>${requestScope.message}</h2>
</c:if>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_client"/>
<input name="id" type="hidden" value="${requestScope.client.id}"/>
<br/>
<label for="trainerId-select"><fmt:message key="msg.editclient.trainerid"/>: </label>
<select id="trainerId-select" name="trainerId">
<c:forEach items="${trainers}" var="trainer">
<option value="${trainer.id}">${trainer.id}</option>
</c:forEach>
</select>



<br/>
<h3><fmt:message key="msg.editclient.selecttype"/></h3>
<input id="type-input-regular" name="type" type="radio"
value="REGULAR" ${requestScope.user.typeDto=='REGULAR' ? 'checked' : ''}>
<label for="type-input-regular"><fmt:message key="msg.editclient.regular"/></label>
<input id="type-input-new" name="type" type="radio" value="NEW"
<c:if test="${requestScope.user.typeDto=='NEW'}">checked</c:if>>
<label for="type-input-new"><fmt:message key="msg.editclient.new"/></label>
<input id="type-input-corporate" name="type" type="radio"
value="CORPORATE" ${requestScope.user.typeDto=='CORPORATE' ? 'checked' : ''}>
<label for="type-input-corporate"><fmt:message key="msg.editclient.corporate"/></label>

<br/>
<input type="submit" value="<fmt:message key="msg.order.save"/>"/>
</form>
<br/>

<h3><fmt:message key="msg.trainers.title"/></h3>
<table>
<tr><th>ID</th><th><fmt:message key="msg.user.firstname"/></th><th><fmt:message key="msg.user.lastname"/></th></tr>  
<c:forEach items="${trainers}" var="trainer">
<tr>
<td><a href="controller?command=trainer&id=${trainer.id}"><c:out value="${trainer.id}"/></a></td>
<td><c:out value="${trainer.firstName}"/></td>
<td><a href="controller?command=trainer&id=${trainer.id}"><c:out value="${trainer.lastName}"/></a></td>
</tr>
</c:forEach>
</table>  
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>
