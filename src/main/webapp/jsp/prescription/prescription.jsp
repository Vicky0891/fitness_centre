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
<title><fmt:message key="msg.prescription.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.prescription.title"/></h1>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>

<c:if test="${sessionScope.user['class'].simpleName == 'ClientDto'}">
<c:if test="${requestScope.prescription.id == null}">
<h2><fmt:message key="msg.prescription.noprescription"/></h2>
</c:if>


<c:if test="${prescription.id != null}">
<h3><fmt:message key="msg.gymmembership.typeoftraining"/>: <c:out value="${prescription.typeOfTraining}"/></h3>
<h3><fmt:message key="msg.prescription.equipment"/>: <c:out value="${prescription.equipment}"/></h3>
<h3><fmt:message key="msg.prescription.diet"/>: <c:out value="${prescription.diet}"/></h3>
<h3><fmt:message key="msg.allorders.status"/>: <c:out value="${prescription.statusDto.toString().toLowerCase()}"/></h3>
<br/>

<c:if test="${prescription.statusDto.toString() == 'PENDING'}">
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_prescription_form"/>
<input type="hidden" name="userId" value="${requestScope.prescription.userId}"/>
<input type="submit" value="<fmt:message key="msg.prescription.edittitle"/>"/>
</form>
</c:if>
</c:if>
</c:if>

<c:if test="${sessionScope.user['class'].simpleName == 'TrainerDto'}">

<c:if test="${requestScope.prescription.id == null}">
<form method="post" action="controller">
<input type="hidden" name="command" value="create_prescription_form"/>
<input type="hidden" name="clientId" value="${requestScope.clientId}"/>
<input type="submit" value="<fmt:message key="msg.prescription.createtitle"/>"/>
</form>
</c:if>


<c:if test="${prescription.id != null}">
<h3><fmt:message key="msg.prescription.existprescription"/></h3>
<h3><fmt:message key="msg.gymmembership.typeoftraining"/>: <c:out value="${prescription.typeOfTraining}"/></h3>
<h3><fmt:message key="msg.prescription.equipment"/>: <c:out value="${prescription.equipment}"/></h3>
<h3><fmt:message key="msg.prescription.diet"/>: <c:out value="${prescription.diet}"/></h3>
<h3><fmt:message key="msg.allorders.status"/>: <c:out value="${prescription.statusDto.toString().toLowerCase()}"/></h3>
<br/>

</c:if>
</c:if>

</body>
</html>