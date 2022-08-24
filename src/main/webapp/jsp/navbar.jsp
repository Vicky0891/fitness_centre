<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${sessionScope.language != null}">
<fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<h3 class="alltrainers"><a href="/fitness_centre/"><fmt:message key="msg.navbar.main"/></a>   |   
<a href="controller?command=contacts"><fmt:message key="msg.navbar.contacts"/></a>   |   
<a href="controller?command=trainers"><fmt:message key="msg.navbar.ourtrainers"/></a>   | 
<a href="controller?command=gymmemberships"><fmt:message key="msg.navbar.gymmemberships"/></a>   |  

<c:if test="${sessionScope.user == null}">
<a href="controller?command=cart"><fmt:message key="msg.navbar.cart"/></a>   |   
<a href="controller?command=create_user_form"><fmt:message key="msg.navbar.signup"/></a>   |   
<a href="controller?command=login_form"><fmt:message key="msg.navbar.signin"/></a>   |   
</c:if>
 
<c:if test="${sessionScope.user != null}">
<c:if test="${sessionScope.user.roleDto.toString() == 'CLIENT'}">
<a href="controller?command=cart"><fmt:message key="msg.navbar.cart"/></a>   |   
<a href="controller?command=user&id=${sessionScope.user.id}"><fmt:message key="msg.navbar.myprofile"/></a>   |   
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'TRAINER'}">
<a href="controller?command=user&id=${sessionScope.user.id}"><fmt:message key="msg.navbar.mycabinet"/></a>   |   
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'ADMIN'}">
<a href="controller?command=all_users"><fmt:message key="msg.navbar.allusers"/></a>   |   
<a href="controller?command=all_clients"><fmt:message key="msg.navbar.allclients"/></a>   |   
<a href="controller?command=all_orders"><fmt:message key="msg.navbar.allorders"/></a>   |   


</c:if>
<a href="controller?command=logout"><fmt:message key="msg.navbar.logout"/></a>   |   
</c:if>

<form method="post" action="controller">
<input name="command" type="hidden" value="change_locale"/>
<select name="language" onchange="submit()">
<option disabled>Language</option>
<option value="ru" ${sessionScope.language == 'ru' ? 'selected' : ''}>RU</option>
<option value="en" ${sessionScope.language == 'en' ? 'selected' : ''}>EN</option>
</select>
</form>
</h3>