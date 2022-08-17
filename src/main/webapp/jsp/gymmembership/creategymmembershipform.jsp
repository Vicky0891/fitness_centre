<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Create gymmembership</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Create gymmembership</h1>
<form method="post" action="controller">
<input name="command" type="hidden" value="create_gymmembership"/>

<br/>
<label for="numberOfVisits-input">Number of visits: </label>
<input id="numberOfVisits-input" name="numberOfVisits" type="number" min="1"/>
<br/>
<label for="typeOfTraining-input">Type of training: </label>
<input id="typeOfTraining-input" name="typeOfTraining" type="text"/>
<br/>
<label for="cost-input">Cost USD: </label>
<input id="cost-input" name="cost" type="text"/>
<br/>

<input type="submit" value="CREATE"/>
</form>
</body>
</html>