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
<h1>My prescription</h1>

<c:if test="${requestScope.prescription == null}">
<h2>No prescription at this moment. Please wait some time</h2>
</c:if>


<c:if test="${prescription.id != null}">

<h3>Type of training: <c:out value="${prescription.typeOfTraining}"/></h3>
<h3>Equipment: <c:out value="${prescription.equipment}"/></h3>
<h3>Diet: <c:out value="${prescription.diet}"/></h3>
<h3>Status: <c:out value="${prescription.statusDto.toString().toLowerCase()}"/></h3>
<br/>
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_prescription_form"/>
<input type="hidden" name="userId" value="${requestScope.prescription.userId}"/>
<input type="submit" value="Edit prescription"/>
</form>
</c:if>

</body>
</html>