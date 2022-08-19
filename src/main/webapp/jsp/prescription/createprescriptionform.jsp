<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Create prescription</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Create prescription</h1>
<form method="post" action="controller">
<input type="hidden" name="clientId" value="${requestScope.clientId}"/>
<input name="command" type="hidden" value="create_prescription"/>

<br/>
<label for="typeOfTraining-textarea">Type of training: </label>
<textarea id="typeOfTraining-textarea" name="typeOfTraining" rows="5" cols="20"></textarea>
<br/>
<label for="equipment-textarea">Equipment: </label>
<textarea id="equipment-textarea" name="equipment" rows="5" cols="20"></textarea>
<br/>
<label for="diet-textarea">Diet: </label>
<textarea id="diet-textarea" name="diet" rows="5" cols="20"></textarea>
<br/>

<input type="submit" value="SAVE"/>
</form>
</body>
</html>