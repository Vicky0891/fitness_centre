<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Add feedback</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Add feedback</h1>
<c:if test="${requestScope != null}">
<h3>${requestScope.message}</h3>
</c:if>

<h2>Please add feedback to your order</h2>
<br/>
<form method="post" action="controller">
<input name="command" type="hidden" value="add_feedback"/>
<input type="hidden" name="orderId" value="${sessionScope.orderId}"/>
<label for="feedback-textarea">Write something : </label>
<textarea id="feedback-textarea" name="feedback" rows="5" cols="20"></textarea>

<br/>
<input type="submit" value="SAVE"/>
</form>
</body>
</html>