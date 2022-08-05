<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Trainers</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1>Our gymmemberships</h1>
<table>
<c:if test="${gymmemberships.size() == 0}">
<h1>No available gymmemberships</h1>
</c:if>
<c:if test="${gymmemberships.size() > 0}">
<th>Id</th><th>Trainer</th><th>Number of visits</th><th>Type of training</th><th>Cost</th>    
<c:forEach items="${gymmemberships}" var="gymmembership">
<tr>
<td>${gymmembership.id}</td>
<td><a href="controller?command=trainer&id=${gymmembership.trainerDto.id}">${gymmembership.trainerDto.lastName}</a></td>
<td>${gymmembership.numberOfVisits}</td>
<td>${gymmembership.typeOfTraining}</td>
<td>${gymmembership.cost}</td>
</tr>
</c:forEach>
</c:if>
</table>           
<h3><a href="/fitness_centre">Main page</a></h3>
</body>
</html>