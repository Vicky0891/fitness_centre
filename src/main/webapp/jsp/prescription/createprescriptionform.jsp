<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Edit user</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Create prescription</h1>
<form method="post" action="controller">
<input type="hidden" name="clientId" value="${requestScope.clientId}"/>
<input name="command" type="hidden" value="create_prescription"/>

<br/>
<label for="typeOfTraining-input">Type of training: </label>
<input id="typeOfTraining-input" name="typeOfTraining" type="text"/>
<br/>
<label for="equipment-input">Equipment: </label>
<input id="equipment-input" name="equipment" type="text"/>
<br/>
<label for="diet-input">Diet: </label>
<input id="diet-input" name="diet" type="text"/>
<br/>

<input type="submit" value="SAVE"/>
</form>
</body>
</html>