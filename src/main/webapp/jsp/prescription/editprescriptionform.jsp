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
<h1>Change what you want</h1>
<h3>Notice, you can change prescription just one time!</h3>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_prescription"/>
<input name="userId" type="hidden" value="${sessionScope.prescription.userId}"/>


<label for="typeOfTraining-textarea">Type of training: </label>
<textarea id="typeOfTraining-textarea" name="typeOfTraining" placeholder="${sessionScope.prescription.typeOfTraining}" rows="5" cols="20"></textarea>
<br/>
<label for="equipment-textarea">Equipment: </label>
<textarea id="equipment-textarea" name="equipment" placeholder="${sessionScope.prescription.equipment}" rows="5" cols="20"></textarea>
<br/>
<label for="diet-textarea">Diet: </label>
<textarea id="diet-textarea" name="diet" placeholder="${sessionScope.prescription.diet}" rows="5" cols="20"></textarea>
<br/>

<input type="submit" value="SAVE"/>
</form>
</body>
</html>