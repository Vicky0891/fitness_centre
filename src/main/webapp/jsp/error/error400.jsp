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
<title><fmt:message key="msg.main.error"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<header>
<jsp:include page="../navbar.jsp"/>
</header>
<h1 class="error"><fmt:message key="msg.main.error"/> 400</h1>
<h2><fmt:message key="msg.error.bad"/></h2>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>