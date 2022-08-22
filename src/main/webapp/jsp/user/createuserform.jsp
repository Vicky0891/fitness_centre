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
<title><fmt:message key="msg.createuser.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.createuser.title"/></h1>

<c:if test="${requestScope.message != null}">
<h3>${requestScope.message}</h3>
</c:if>
<c:if test="${requestScope.errorStatus != null}">
<h3><fmt:message key="msg.main.error"/> ${requestScope.errorStatus}</h3>
</c:if>

<form method="post" action="controller">
<input name="command" type="hidden" value="create_user"/>
<label for="email-input"><fmt:message key="msg.login.email"/>: </label>
<input id="email-input" name="email" type="email"/>
<br/>
<label for="password-input"><fmt:message key="msg.login.password"/>: </label>
<input id="password-input" name="password" type="password" minlength="4"/>
<br/>


<input type="submit" value="<fmt:message key="msg.createuser.register"/>"/>
</form>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>