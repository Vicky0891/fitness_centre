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
<h1>Edit cabinet</h1>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_cabinet"/>
<input name="id" type="hidden" value="${sessionScope.user.id}"/>


<label for="firstName-input">First name: </label>
<input id="firstName-input" name="firstName" type="text" value="${requestScope.user.firstName}"/>
<br/>
<label for="lastName-input">Last name: </label>
<input id="lastName-input" name="lastName" type="text" value="${requestScope.user.lastName}"/>
<br/>
<label for="birthDate-input">Date of birth: </label>
<input id="birthDate-input" name="birthDate" type="date"/>
<br/>
<label for="category-option">Category: </label>
<select id="category-option">
<option value="first">First</option>
<option value="second">Second</option>
<option value="high">High</option>
</select>
<br/>
<input type="submit" value="SAVE"/>
</form>
</body>
</html>