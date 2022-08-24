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
<title><fmt:message key="msg.cart.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<header>
<jsp:include page="../navbar.jsp"/>
</header>
<h1><fmt:message key="msg.cart.title"/></h1>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>
<c:if test="${requestScope.cart == null}">
<p><fmt:message key="msg.cart.emptycart"/></p>

<h3><a href="controller?command=gymmemberships"><fmt:message key="msg.gymmembership.ourgym"/></a></h3>
</c:if>
<c:if test="${requestScope.cart != null}">

<table>
<tr><th><fmt:message key="msg.gymmembership.title"/></th><th><fmt:message key="msg.cart.quantity"/></th><th><fmt:message key="msg.cart.price"/>, USD</th><th></th><th></th></tr>
<c:forEach items="${requestScope.cart.details}" var="item">
<tr>
<td><a href="controller?command=gymmembership&id=${item.gymMembershipDto.id}"><c:out value="${item.gymMembershipDto.typeOfTraining}"/></a></td>
<td><c:out value="${item.gymMembershipQuantity}"/></td>
<td><c:out value="${item.gymMembershipPrice}"/></td>
<td></td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="remove_from_cart"/>
<input type="hidden" name="gymmembershipId" value="${item.gymMembershipDto.id}"/>
<input type="hidden" name="redirect" value="cart"/>
<input type="submit" value="<fmt:message key="msg.cart.remove"/>"/>
</form>
</td>


</tr>
</c:forEach>
<tr>
<td colspan="3"><fmt:message key="msg.cart.totalcost"/>: <c:out value="${requestScope.cart.totalCost}"/> USD</td>
<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="create_order"/>
<input type="submit" value="<fmt:message key="msg.cart.buynow"/>"/>
</form>
</td>

<td>
<form method="post" action="controller">
<input type="hidden" name="command" value="remove_order"/>
<input type="submit" value="<fmt:message key="msg.cart.removeall"/>"/>
</form>
</td>
</tr>
</table>
</c:if>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>