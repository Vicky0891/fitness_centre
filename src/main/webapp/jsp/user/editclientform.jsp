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
<title><fmt:message key="msg.editclient.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
<div class="content">
<header>
<jsp:include page="../navbar.jsp"/>
</header>
<h1><fmt:message key="msg.editclient.title"/></h1>
<c:if test="${requestScope.message != null}">
<h2>${requestScope.message}</h2>
</c:if>
<h3><fmt:message key="msg.editclient.selecttrainer"/></h3>
<form method="post" action="controller">
<input name="command" type="hidden" value="edit_client"/>
<input name="id" type="hidden" value="${requestScope.client.id}"/>
<select id="trainerId-select" name="trainerId">
<c:forEach items="${trainers}" var="trainer">
<option value="${trainer.id}">${trainer.id} (${trainer.firstName} ${trainer.lastName})</option>
</c:forEach>
</select>
<br/>
<h3><fmt:message key="msg.editclient.selecttype"/></h3>
<input id="type-input-regular" name="type" type="radio"
value="REGULAR" ${requestScope.client.type=='REGULAR' ? 'checked' : ''}>
<label for="type-input-regular"><fmt:message key="msg.editclient.regular"/></label>
<input id="type-input-new" name="type" type="radio" value="NEW"
value="NEW" ${requestScope.client.type=='NEW' ? 'checked' : ''}>
<label for="type-input-new"><fmt:message key="msg.editclient.new"/></label>
<input id="type-input-corporate" name="type" type="radio"
value="CORPORATE" ${requestScope.client.type=='CORPORATE' ? 'checked' : ''}>
<label for="type-input-corporate"><fmt:message key="msg.editclient.corporate"/></label>
<br/>
<input type="submit" value="<fmt:message key="msg.order.save"/>"/>
</form>
</div>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</div>
</body>
</html>
