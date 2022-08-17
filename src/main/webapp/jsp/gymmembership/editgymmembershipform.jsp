<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Edit gymmembership</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Edit gymmembership</h1>
<form method="post" action="controller">
<input name="id" type="hidden" value="${requestScope.gymmembership.id}"/>
<input name="command" type="hidden" value="edit_gymmembership"/>

<br/>
<label for="numberOfVisits-input">Number of visits: </label>
<input id="numberOfVisits-input" name="numberOfVisits" type="number" min="1" value="${requestScope.gymmembership.numberOfVisits}"/>
<br/>
<label for="typeOfTraining-input">Type of training: </label>
<input id="typeOfTraining-input" name="typeOfTraining" type="text" value="${requestScope.gymmembership.typeOfTraining}"/>
<br/>
<label for="cost-input">Cost USD: </label>
<input id="cost-input" name="cost" type="text" value="${requestScope.gymmembership.cost}"/>
<br/>

<input type="submit" value="EDIT"/>
</form>
</body>
</html>