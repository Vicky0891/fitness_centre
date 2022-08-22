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
<title><fmt:message key="msg.prescription.createtitle"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.prescription.createtitle"/></h1>
<form method="post" action="controller">
<input type="hidden" name="clientId" value="${requestScope.clientId}"/>
<input name="command" type="hidden" value="create_prescription"/>

<br/>
<label for="typeOfTraining-textarea"><fmt:message key="msg.gymmembership.typeoftraining"/>: </label>
<textarea id="typeOfTraining-textarea" name="typeOfTraining" rows="5" cols="20"></textarea>
<br/>
<label for="equipment-textarea"><fmt:message key="msg.prescription.equipment"/>: </label>
<textarea id="equipment-textarea" name="equipment" rows="5" cols="20"></textarea>
<br/>
<label for="diet-textarea"><fmt:message key="msg.prescription.diet"/>: </label>
<textarea id="diet-textarea" name="diet" rows="5" cols="20"></textarea>
<br/>

<input type="submit" value="<fmt:message key="msg.order.save"/>"/>
</form>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>