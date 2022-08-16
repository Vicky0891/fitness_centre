<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<h3 class="alltrainers"><a href="/fitness_centre/">Main page</a>   |   
<a href="controller?command=trainers">Our trainers</a>   | 
<a href="controller?command=gymmemberships">Our gymmemberships</a>   |  

<c:if test="${sessionScope.user == null}">
<a href="controller?command=cart">Cart</a>   |   
<a href="controller?command=create_user_form">Sign up</a>   |   
<a href="controller?command=login_form">Sign in</a>   |   
</c:if>
 
<c:if test="${sessionScope.user != null}">
<c:if test="${sessionScope.user.roleDto.toString() == 'CLIENT'}">
<a href="controller?command=cart">Cart</a>   |   
<a href="controller?command=user&id=${sessionScope.user.id}">My profile</a>   |   
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'TRAINER'}">
<a href="controller?command=user&id=${sessionScope.user.id}">My cabinet</a>   |   
</c:if>
<c:if test="${sessionScope.user.roleDto.toString() == 'ADMIN'}">
<a href="controller?command=users">All users</a>   |   
<a href="controller?command=all_clients">All clients</a>   |   
<a href="controller?command=all_orders">All orders</a>   |   


</c:if>
<a href="controller?command=logout">Log out</a>   |   
</c:if>


</h3>