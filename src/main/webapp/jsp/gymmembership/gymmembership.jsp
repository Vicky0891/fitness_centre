<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Gymmembership</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<c:if test="${gymmembership.id != null}">
<h1>Info about gymmembership</h1>
<h3>Id: <c:out value="${gymmembership.id}"/></h3>
<h3>Type of training: <c:out value="${gymmembership.typeOfTraining}"/></h3>
<h3>Number of visits: <c:out value="${gymmembership.numberOfVisits}"/></h3>
<h3>Cost: <c:out value="${gymmembership.cost}"/></h3>
<br/>
</c:if>
<c:if test="${gymmembership.id == null}">
<jsp:include page="../error.jsp"/>
</c:if>
</body>
</html>