<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${sessionScope.language != null}">
<fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<table class="navbar">
<tr>
<td>
<h3><a href="/fitness_centre/"><fmt:message key="msg.navbar.main"/></a></h3></td>
<td><h3><a href="controller?command=contacts"><fmt:message key="msg.navbar.contacts"/></a></h3></td>
<td><h3><a href="controller?command=trainers"><fmt:message key="msg.navbar.ourtrainers"/></a></h3></td>
<td><h3><a href="controller?command=gymmemberships"><fmt:message key="msg.navbar.gymmemberships"/></a></h3></td>

<c:if test="${sessionScope.user == null}">
<td><h3><a href="controller?command=cart"><fmt:message key="msg.navbar.cart"/></a></h3></td>
<td><h3><a href="controller?command=create_user_form"><fmt:message key="msg.navbar.signup"/></a></h3></td>
<td><h3><a href="controller?command=login_form"><fmt:message key="msg.navbar.signin"/></a></h3></td>
</c:if>
 
<c:if test="${sessionScope.user != null}">
<c:if test="${sessionScope.user.roleDto.toString() == 'CLIENT'}">
<td><h3><a href="controller?command=cart"><fmt:message key="msg.navbar.cart"/></a></h3></td>
<td><h3><a href="controller?command=user&id=${sessionScope.user.id}"><fmt:message key="msg.navbar.myprofile"/></a></h3></td>
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'TRAINER'}">
<td><h3><a href="controller?command=user&id=${sessionScope.user.id}"><fmt:message key="msg.navbar.mycabinet"/></a></h3></td>
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'ADMIN'}">
<td><h3><a href="controller?command=all_users"><fmt:message key="msg.navbar.allusers"/></a></h3></td>
<td><h3><a href="controller?command=all_clients"><fmt:message key="msg.navbar.allclients"/></a></h3></td>
<td><h3><a href="controller?command=all_orders"><fmt:message key="msg.navbar.allorders"/></a></h3></td>
</c:if>
<td><h3><a href="controller?command=logout"><fmt:message key="msg.navbar.logout"/></a></h3></td>
</c:if>
<td>
<form method="post" action="controller">
<input name="command" type="hidden" value="change_locale"/>
<select name="language" onchange="submit()">
<option disabled>Language</option>
<option value="ru" ${sessionScope.language == 'ru' ? 'selected' : ''}>RU</option>
<option value="en" ${sessionScope.language == 'en' ? 'selected' : ''}>EN</option>
</select>
</form>
</td>
</tr>
</table>