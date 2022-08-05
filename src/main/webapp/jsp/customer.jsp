<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Customer</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<c:if test="${customer.id != null}">
<h1>Your profile</h1>
<h3>Id: ${customer.id}</h3>
<h3>First name: ${customer.firstName}</h3>
<h3>Last name: ${customer.lastName}</h3>
<h3>Email: ${customer.email}</h3>
<h3>Birth date: ${customer.birthDate}</h3>
<h3>Phone number: ${customer.phoneNumber}</h3>
<h3>Trainer: ${customer.trainerDto.lastName}</h3>
<br/>
<h3 class="alltrainers"><a href="controller?command=trainers">All trainers</a></h3>
<h3 class="alltrainers"><a href="controller?command=gymmemberships">Our gymmemberships</a></h3>
</c:if>
<c:if test="${customer.id == null}">
<h1 class="error">Something went wrong</h1>
</c:if>
</body>
</html>