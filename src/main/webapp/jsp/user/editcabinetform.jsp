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
<title><fmt:message key="msg.editcabinet.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.editcabinet.title"/></h1>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_cabinet"/>
<input name="id" type="hidden" value="${sessionScope.user.id}"/>


<label for="firstName-input"><fmt:message key="msg.user.firstname"/>: </label>
<input id="firstName-input" name="firstName" type="text" value="${requestScope.user.firstName}"/>
<br/>
<label for="lastName-input"><fmt:message key="msg.user.lastname"/>: </label>
<input id="lastName-input" name="lastName" type="text" value="${requestScope.user.lastName}"/>
<br/>
<label for="birthDate-input"><fmt:message key="msg.user.birthdate"/>: </label>
<input id="birthDate-input" name="birthDate" type="date"/>
<br/>
<label for="category-option"><fmt:message key="msg.user.category"/>: </label>
<select id="category-option" name="category">
<option value="first"><fmt:message key="msg.editcabinet.first"/></option>
<option value="second"><fmt:message key="msg.editcabinet.second"/></option>
<option value="high"><fmt:message key="msg.editcabinet.high"/></option>
</select>
<br/>
<input type="submit" value="<fmt:message key="msg.order.save"/>"/>
</form>
</body>
</html>