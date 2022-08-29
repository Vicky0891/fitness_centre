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
<h3><fmt:message key="msg.contacts.email"/><a href="controller?command=mailto">fitness@gmail.com</a></h3>
<h3><fmt:message key="msg.contacts.address"/></h3>
<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2351.3519837535573!2d27.55764175169886!3d53.88994754145684!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x46dbcfda52d22955%3A0x2842bea429615f47!2z0YPQu9C40YbQsCDQodCy0LXRgNC00LvQvtCy0LAgNjAsINCc0LjQvdGB0Lo!5e0!3m2!1sru!2sby!4v1661721574038!5m2!1sru!2sby" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
</div>
<footer>
<jsp:include page="footer.jsp"/>
</footer>
</div>
</body>
</html>