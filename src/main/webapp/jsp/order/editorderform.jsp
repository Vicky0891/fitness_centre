<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${sessionScope.language != null}">
<fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<head>
<title><fmt:message key="msg.editorder.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.editorder.title"/></h1>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>

<h2><fmt:message key="msg.editorder.selectorderstatus"/></h2>
<br/>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_order"/>
<input name="id" type="hidden" value="${requestScope.order.id}"/>

<input id="status-input-pending" name="status" type="radio"
value="PENDING" ${requestScope.order.statusDto=='PENDING' ? 'checked' : ''}>
<label for="status-input-pending"><fmt:message key="msg.allorders.pending"/></label>
<input id="status-input-confirm" name="status" type="radio" value="CONFIRM"
<c:if test="${requestScope.order.statusDto=='CONFIRM'}">checked</c:if>>
<label for="status-input-confirm"><fmt:message key="msg.allorders.confirm"/></label>

<br/>
<input type="submit" value="<fmt:message key="msg.order.save"/>"/>
<br/>
</form>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>