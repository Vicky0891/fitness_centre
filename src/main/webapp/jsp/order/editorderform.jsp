<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Edit order</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Edit order</h1>
<c:if test="${requestScope != null}">
<h3>${requestScope.message}</h3>
</c:if>

<h2>Choose order status</h2>
<br/>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_order"/>
<input name="id" type="hidden" value="${requestScope.order.id}"/>

<input id="status-input-pending" name="status" type="radio"
value="PENDING" ${requestScope.order.statusDto=='PENDING' ? 'checked' : ''}>
<label for="status-input-pending">PENDING</label>
<input id="status-input-confirm" name="status" type="radio" value="CONFIRM"
<c:if test="${requestScope.order.statusDto=='CONFIRM'}">checked</c:if>>
<label for="status-input-confirm">CONFIRM</label>

<br/>
<input type="submit" value="SAVE"/>
<br/>
</form>
</body>
</html>