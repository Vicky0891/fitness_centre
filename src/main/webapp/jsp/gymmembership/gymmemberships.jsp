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
<title><fmt:message key="msg.gymmembership.titles"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wrapper">
<div class="content">
<header>
<jsp:include page="../navbar.jsp"/>
</header>
<h1><fmt:message key="msg.gymmembership.ourgym"/></h1>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>
<c:if test="${param.delete != null}">
<h4><fmt:message key="msg.delete.gym"/></h4>
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'ADMIN'}">
<form method="post" action="controller">
<input type="hidden" name="command" value="create_gymmembership_form"/>
<input type="submit" value="<fmt:message key="msg.gymmembership.createnewgym"/>"/>
</form>
</c:if>
<table>
<jsp:include page="../pagination/gympagination.jsp"/>

<c:if test="${gymmemberships.size() == 0}">
<h1><fmt:message key="msg.gymmembership.noavailable"/></h1>
</c:if>
<c:if test="${gymmemberships.size() > 0}">
<tr><th>#</th><th><fmt:message key="msg.gymmembership.numberofvisits"/></th><th><fmt:message key="msg.gymmembership.typeoftraining"/></th><th><fmt:message key="msg.gymmembership.cost"/></th></tr>    
<c:forEach items="${requestScope.gymmemberships}" var="gymmembership">
<tr>
<td><c:out value="${gymmembership.id}"/></td>
<td><c:out value="${gymmembership.numberOfVisits}"/></td>
<td><a href="controller?command=gymmembership&id=${gymmembership.id}"><c:out value="${gymmembership.typeOfTraining}"/></a></td>
<td><c:out value="${gymmembership.cost}"/> USD</td>
<c:if test="${sessionScope.user.roleDto.toString() != 'ADMIN' && sessionScope.user.roleDto.toString() != 'TRAINER'}">
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="add_to_cart"/>
<input type="hidden" name="gymmembershipId" value="${gymmembership.id}"/>
<input type="submit" value="<fmt:message key="msg.gymmembership.addtocart"/>"/>
</form>
</td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="remove_from_cart"/>
<input type="hidden" name="gymmembershipId" value="${gymmembership.id}"/>
<input type="submit" value="<fmt:message key="msg.gymmembership.removefromcart"/>"/>
</form>
</td>
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'ADMIN'}">
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="delete_gymmembership"/>
<input type="hidden" name="gymmembershipId" value="${gymmembership.id}"/>
<input type="submit" value="<fmt:message key="msg.gymmembership.delete"/>"/>
</form>
</td>
</c:if>
</tr>

</c:forEach>
</c:if>
<tr>
<td align="right" colspan="6"><a href="controller?command=cart"><fmt:message key="msg.gymmembership.gotocart"/></a></td>
</tr>
</table>
</div> 
<footer>
<jsp:include page="../footer.jsp"/>
</footer> 
</div>         
</body>
</html>