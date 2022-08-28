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
<title><fmt:message key="msg.navbar.contacts"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
<div class="content">
<header>
<jsp:include page="navbar.jsp"/>
</header>
<h1><fmt:message key="msg.navbar.contacts"/></h1>
<h3><fmt:message key="msg.contacts.tel"/></h3>
<h3><fmt:message key="msg.contacts.email"/></h3>
<h3><fmt:message key="msg.contacts.address"/></h3>
<img src="images/map.jpg" alt="map" class="mapimg"\>
</div>
<footer>
<jsp:include page="footer.jsp"/>
</footer>
</div>
</body>
</html>