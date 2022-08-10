<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Prescription</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Your prescription</h1>
<h3>Type of training: <c:out value="${prescription.typeOfTraining}"/></h3>
<h3>Equipment: <c:out value="${prescription.equipment}"/></h3>
<h3>Diet: <c:out value="${prescription.diet}"/></h3>
<h3>Status: <c:out value="${prescription.statusDto.toString().toLowerCase()}"/></h3>
<br/>
<h3 class="alltrainers"><a href="/fitness_centre">Main page</a></h3>
</body>
</html>