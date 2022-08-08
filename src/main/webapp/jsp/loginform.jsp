<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Login</h1>
<form method="post" action="controller">
<input name="command" type="hidden" value="login"/>
<label for="email-input">Email: </label>
<input id="email-input" name="email" type="email"/>
<br/>
<label for="password-input">Password: </label>
<input id="password-input" name="password" type="text" min="4"/>
<br/>
<input type="submit" value="LOGIN"/>
</form>
</body>
</html>