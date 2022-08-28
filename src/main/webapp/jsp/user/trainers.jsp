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
<title><fmt:message key="msg.trainers.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
<div class="content">
<header>
<jsp:include page="../navbar.jsp"/>
</header>
<h1><fmt:message key="msg.trainers.maintitle"/></h1>
<h3><fmt:message key="msg.trainers.maintext"/></h3>
<table>
<c:if test="${trainers.size() == 0}">
<h1><fmt:message key="msg.trainers.notrainers"/></h1>
</c:if>
<c:if test="${trainers.size() > 0}">
<th></th><th>#</th><th><fmt:message key="msg.user.firstname"/></th><th><fmt:message key="msg.user.lastname"/></th><th><fmt:message key="msg.user.category"/></th>  
<c:forEach items="${trainers}" var="trainer">
<tr>
<td>
<img src="images/trainersavatars/${trainer.pathAvatar}" alt="photo" class="trainerphoto">
</td>
<td><a href="controller?command=trainer&id=${trainer.id}"><c:out value="${trainer.id}"/></a></td>
<td><a href="controller?command=trainer&id=${trainer.id}"><c:out value="${trainer.firstName}"/></a></td>
<td><c:out value="${trainer.lastName}"/></td>
<td><c:out value="${trainer.category}"/></td>
</tr>
</c:forEach>
</c:if>
</table> 
</div>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</div>          
</body>
</html>