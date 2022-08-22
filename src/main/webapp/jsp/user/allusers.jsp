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
<title><fmt:message key="msg.allusers.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="../navbar.jsp"/>

<h1><fmt:message key="msg.allusers.title"/></h1>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>
<table>
<c:if test="${users.size() == 0}">
<h1><fmt:message key="msg.allusers.nousers"/></h1>
</c:if>
<c:if test="${users.size() > 0}">
<br/>
<jsp:include page="../pagination/userspagination.jsp"/>
<tr><th>ID</th><th><fmt:message key="msg.login.email"/></th><th><fmt:message key="msg.allusers.role"/></th></tr>  
<c:forEach items="${users}" var="user">
<tr>
<td><a href="controller?command=user&id=${user.id}"><c:out value="${user.id}"/></a></td>
<td><a href="controller?command=user&id=${user.id}"><c:out value="${user.email}"/></a></td>
<td><c:out value="${user.roleDto.toString().toLowerCase()}"/></td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_user_form"/>
<input type="hidden" name="userId" value="${user.id}"/>
<input type="submit" value="<fmt:message key="msg.gymmembership.edit"/>"/>
</form>
</td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="delete_user"/>
<input type="hidden" name="userId" value="${user.id}"/>
<input type="submit" value="<fmt:message key="msg.gymmembership.delete"/>"/>
</form>
</td>
</tr>
</c:forEach>
</c:if>
</table> 
<footer>
<jsp:include page="../footer.jsp"/>
</footer>          
</body>
</html>