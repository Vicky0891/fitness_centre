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
<h1>Edit profile</h1>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_profile"/>
<input name="id" type="hidden" value="${sessionScope.user.id}"/>


<label for="firstName-input">First name: </label>
<input id="firstName-input" name="firstName" type="text" value="${sessionScope.user.firstName}"/>
<br/>
<label for="lastName-input">Last name: </label>
<input id="lastName-input" name="lastName" type="text" value="${sessionScope.user.lastName}"/>
<br/>
<label for="birthDate-input">Date of birth: </label>
<input id="birthDate-input" name="birthDate" type="date"/>
<br/>
<label for="phoneNumber-input">Phone number (9-13 digits): </label>
<input id="phoneNumber-input" name="phoneNumber" type="tel" value="${sessionScope.user.phoneNumber}" min="9" max="13"/>
<br/>
<label for="additionalInfo-textarea">Additional information: </label>
<textarea id="additionalInfo-textarea" name="additionalInfo" placeholder="${sessionScope.user.additionalInfo}" rows="5" cols="20"></textarea>
<br/>
<input type="submit" value="SAVE"/>
</form>
</body>
</html>