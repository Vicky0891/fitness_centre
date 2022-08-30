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
<title><fmt:message key="msg.login.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
<div class="content">
<header>
<jsp:include page="navbar.jsp"/>
</header>
<h1><fmt:message key="msg.login.title"/></h1>
<c:if test="${requestScope.message != null}">
<h3>${requestScope.message}</h3>
</c:if>

<h4><fmt:message key="msg.login.pleaselogin"/></h4>
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
<h4><fmt:message key="msg.login.newuser"/></h4>
<h3><a href="controller?command=create_user_form"><fmt:message key="msg.login.register"/></a></h3>
</div>
<footer>
<jsp:include page="footer.jsp"/>
</footer>
</div>
</body>
</html>