<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${sessionScope.language != null}">
<fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<html>
<head>
<title><fmt:message key="msg.main.title"/></title>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1><fmt:message key="msg.main.welcome"/> ${sessionScope.user != null ? sessionScope.user.email : ''}!</h1>
<jsp:include page="jsp/navbar.jsp"/>

<c:if test="${requestScope.errorStatus != null}">
<h3><fmt:message key="msg.main.error"/> ${requestScope.errorStatus}</h3>
</c:if>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>
<footer>
<jsp:include page="jsp/footer.jsp"/>
</footer>
</body>
</html>