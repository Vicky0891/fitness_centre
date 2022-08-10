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
<c:if test="${requestScope != null}">
<h3>${requestScope.message}</h3>
</c:if>

<h2>Please enter your login and password</h2>
<br/>
<form method="post" action="controller">
<input name="command" type="hidden" value="login"/>
<label for="email-input">Email: </label>
<input id="email-input" name="email" type="email"/>
<br/>
<label for="password-input">Password: </label>
<input id="password-input" name="password" type="password" min="4"/>
<br/>
<br/>
<input type="submit" value="LOGIN"/>
</form>
<h2>New user? Please register</h2>
<a href="controller?command=create_user_form">Register</a>
</body>
</html>