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
<title><fmt:message key="msg.order.titleaddfeedback"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
<div class="content">
<header>
<jsp:include page="../navbar.jsp"/>
</header>
<c:if test="${requestScope != null}">
<h5>${requestScope.message}</h5>
</c:if>

<h2><fmt:message key="msg.order.addfeedback"/></h2>
<br/>
<form method="post" action="controller">
<input name="command" type="hidden" value="add_feedback"/>
<input type="hidden" name="orderId" value="${sessionScope.orderId}"/>
<label for="feedback-textarea"><fmt:message key="msg.order.write"/>: </label>
<textarea id="feedback-textarea" name="feedback" rows="5" cols="20"></textarea>

<br/>
<input type="submit" value="<fmt:message key="msg.order.save"/>"/>
</form>
</div>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</div>
</body>
</html>