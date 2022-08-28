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
<title><fmt:message key="msg.trainer.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
<div class="content">
<header>
<jsp:include page="../navbar.jsp"/>
</header>
<c:if test="${trainer.id != null}">
<h1><fmt:message key="msg.trainer.info"/></h1>
<h3>#: <c:out value="${trainer.id}"/></h3>
<h3><fmt:message key="msg.user.firstname"/>: <c:out value="${trainer.firstName}"/></h3>
<h3><fmt:message key="msg.user.lastname"/>: <c:out value="${trainer.lastName}"/></h3>
<h3><fmt:message key="msg.user.category"/>: <c:out value="${trainer.category}"/></h3>
<br/>
</c:if>
<c:if test="${trainer.id == null}">
<h1 class="error"><fmt:message key="msg.user.noexist"/></h1>
</c:if>
</div>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</div>
</body>
</html>