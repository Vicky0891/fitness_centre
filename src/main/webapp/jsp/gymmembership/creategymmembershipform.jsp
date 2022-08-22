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
<title><fmt:message key="msg.gymmembership.createtitle"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.gymmembership.createtitle"/></h1>
<form method="post" action="controller">
<input name="command" type="hidden" value="create_gymmembership"/>

<br/>
<label for="numberOfVisits-input"><fmt:message key="msg.gymmembership.numberofvisits"/>: </label>
<input id="numberOfVisits-input" name="numberOfVisits" type="number" min="1"/>
<br/>
<label for="typeOfTraining-input"><fmt:message key="msg.gymmembership.typeoftraining"/>: </label>
<input id="typeOfTraining-input" name="typeOfTraining" type="text"/>
<br/>
<label for="cost-input"><fmt:message key="msg.gymmembership.cost"/> USD: </label>
<input id="cost-input" name="cost" type="text"/>
<br/>

<input type="submit" value="<fmt:message key="msg.gymmembership.create"/>"/>
</form>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>