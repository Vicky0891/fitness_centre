<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Trainer</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<c:if test="${trainer.id != null}">
<h1>Info about trainer</h1>
<h3>Id: <c:out value="${trainer.id}"/></h3>
<h3>First name: <c:out value="${trainer.firstName}"/></h3>
<h3>Last name: <c:out value="${trainer.lastName}"/></h3>
<h3>Email: <c:out value="${trainer.email}"/></h3>
<br/>
<h3 class="alltrainers"><a href="controller?command=trainers">All trainers</a></h3>
</c:if>
<c:if test="${trainer.id == null}">
<h1 class="error">Trainer with this ID does not exist</h1>
</c:if>
</body>
</html>