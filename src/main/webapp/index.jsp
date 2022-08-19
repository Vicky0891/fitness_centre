<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.language != null}">
<fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
<title>Fitness_centre</title>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1>Welcome to our fitness centre, Dear ${sessionScope.user != null ? sessionScope.user.email : 'Guest'}!</h1>



<jsp:include page="jsp/navbar.jsp"/>

<c:if test="${requestScope.errorStatus != null}">
<h3>Error ${requestScope.errorStatus}</h3>
</c:if>
<c:if test="${requestScope.message != null}">
<h3>${requestScope.message}</h3>
</c:if>

<h3><fmt:message key="msg.main.welcome"/></h3>
</body>
</html>