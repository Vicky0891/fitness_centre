<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Register new user</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Register new user</h1>
<h3>${requestScope.message}</h3>

<form method="post" action="controller">
<input name="command" type="hidden" value="create_user"/>
<label for="email-input">Email: </label>
<input id="email-input" name="email" type="email"/>
<br/>
<label for="password-input">Password: </label>
<input id="password-input" name="password" type="password" minlength="4"/>
<br/>


<input type="submit" value="REGISTER"/>
</form>
</body>
</html>