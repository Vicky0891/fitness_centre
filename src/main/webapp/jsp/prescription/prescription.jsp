<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Prescription</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<c:if test="${sessionScope.user['class'].simpleName == 'ClientDto'}">
<h1>My prescription</h1>
<c:if test="${requestScope.message != null}">
<h2>${requestScope.message}</h2>
</c:if>

<c:if test="${requestScope.prescription.id == null}">
<h2>No prescription at this moment. Please wait some time</h2>
</c:if>


<c:if test="${prescription.id != null}">
<h3>Type of training: <c:out value="${prescription.typeOfTraining}"/></h3>
<h3>Equipment: <c:out value="${prescription.equipment}"/></h3>
<h3>Diet: <c:out value="${prescription.diet}"/></h3>
<h3>Status: <c:out value="${prescription.statusDto.toString().toLowerCase()}"/></h3>
<br/>

<c:if test="${prescription.statusDto.toString() == 'PENDING'}">
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_prescription_form"/>
<input type="hidden" name="userId" value="${requestScope.prescription.userId}"/>
<input type="submit" value="Edit prescription"/>
</form>
</c:if>
</c:if>
</c:if>

<c:if test="${sessionScope.user['class'].simpleName == 'TrainerDto'}">
<h1>Prescription</h1>
<c:if test="${requestScope.message != null}">
<h2>${requestScope.message}</h2>
</c:if>

<c:if test="${requestScope.prescription.id == null}">
<form method="post" action="controller">
<input type="hidden" name="command" value="create_prescription_form"/>
<input type="hidden" name="clientId" value="${requestScope.clientId}"/>
<input type="submit" value="Create prescription"/>
</form>
</c:if>


<c:if test="${prescription.id != null}">
<h3>Prescription for this client already exist</h3>
<h3>Type of training: <c:out value="${prescription.typeOfTraining}"/></h3>
<h3>Equipment: <c:out value="${prescription.equipment}"/></h3>
<h3>Diet: <c:out value="${prescription.diet}"/></h3>
<h3>Status: <c:out value="${prescription.statusDto.toString().toLowerCase()}"/></h3>
<br/>

</c:if>
</c:if>

</body>
</html>