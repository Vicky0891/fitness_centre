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
<title><fmt:message key="msg.gymmembership.edittitle"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<header>
<jsp:include page="../navbar.jsp"/>
</header>
<h1><fmt:message key="msg.gymmembership.edittitle"/></h1>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>
<form method="post" action="controller">
<input name="gymmembershipId" type="hidden" value="${sessionScope.gymmembership.id}"/>
<input name="command" type="hidden" value="edit_gymmembership"/>
<input type="hidden" name="redirect" value="edit_gymmembership_form"/>

<br/>
<label for="numberOfVisits-input"><fmt:message key="msg.gymmembership.numberofvisits"/>: </label>
<input id="numberOfVisits-input" name="numberOfVisits" type="number" min="1" value="${sessionScope.gymmembership.numberOfVisits}"/>
<br/>
<label for="typeOfTraining-input"><fmt:message key="msg.gymmembership.typeoftraining"/>: </label>
<input id="typeOfTraining-input" name="typeOfTraining" type="text" value="${sessionScope.gymmembership.typeOfTraining}"/>
<br/>
<label for="cost-input"><fmt:message key="msg.gymmembership.costformat"/> USD: </label>
<input id="cost-input" name="cost" type="text" pattern="\d+(\.\d{0,})?" value="${sessionScope.gymmembership.cost}"/>
<br/>

<input type="submit" value="<fmt:message key="msg.order.save"/>"/>
</form>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>