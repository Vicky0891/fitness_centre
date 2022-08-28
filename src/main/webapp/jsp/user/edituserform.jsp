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
<title><fmt:message key="msg.edituser.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
<div class="content">
<header>
<jsp:include page="../navbar.jsp"/>
</header>
<h1><fmt:message key="msg.edituser.title"/></h1>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_user"/>
<input name="id" type="hidden" value="${requestScope.user.id}"/>

<input id="role-input-admin" name="role" type="radio"
value="ADMIN" ${requestScope.user.roleDto=='ADMIN' ? 'checked' : ''}>
<label for="role-input-admin"><fmt:message key="msg.edituser.admin"/></label>
<input id="role-input-client" name="role" type="radio" value="CLIENT"
<c:if test="${requestScope.user.roleDto=='CLIENT'}">checked</c:if>>
<label for="role-input-client"><fmt:message key="msg.edituser.client"/></label>
<input id="role-input-trainer" name="role" type="radio"
value="TRAINER" ${requestScope.user.roleDto=='TRAINER' ? 'checked' : ''}>
<label for="role-input-trainer"><fmt:message key="msg.edituser.trainer"/></label>
<br/>
<input type="submit" value="<fmt:message key="msg.order.save"/>"/>
<br/>
</form>
</div>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</div>
</body>
</html>