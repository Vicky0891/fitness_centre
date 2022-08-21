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
<title>Login</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="msg.login.title"/></h1>
<c:if test="${requestScope != null}">
<h3>${requestScope.message}</h3>
</c:if>

<h2><fmt:message key="msg.login.pleaselogin"/></h2>
<br/>
<form method="post" action="controller">
<input name="command" type="hidden" value="login"/>
<label for="email-input"><fmt:message key="msg.login.email"/>: </label>
<input id="email-input" name="email" type="email"/>
<br/>
<label for="password-input"><fmt:message key="msg.login.password"/>: </label>
<input id="password-input" name="password" type="password" min="4"/>
<br/>
<br/>
<input type="submit" value="<fmt:message key="msg.login.login"/>"/>
</form>
<h2><fmt:message key="msg.login.newuser"/></h2>
<a href="controller?command=create_user_form"><fmt:message key="msg.login.register"/></a>
</body>
</html>