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
<title><fmt:message key="msg.user.changepassword"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
<div class="content">
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="msg.login.title"/></h1>
<c:if test="${requestScope.message != null}">
<h3>${requestScope.message}</h3>
</c:if>

<h4><fmt:message key="msg.user.mainstring"/></h4>
<form method="post" action="controller">
<input name="command" type="hidden" value="change_password"/>
<input type="hidden" name="redirect" value="change_password_form"/>
<label for="newpassword-input"><fmt:message key="msg.user.newpassword"/>: </label>
<input id="newpassword-input" name="newpassword" type="password" minlength="6"/>
<br/>
<label for="repeatpassword-input"><fmt:message key="msg.user.repeatpassword"/>: </label>
<input id="repeatpassword-input" name="repeatpassword" type="password" minlength="6"/>
<br/>
<br/>
<input type="submit" value="<fmt:message key="msg.user.changepassword"/>"/>
</form>
</div>
<footer>
<jsp:include page="footer.jsp"/>
</footer>
</div>
</body>
</html>