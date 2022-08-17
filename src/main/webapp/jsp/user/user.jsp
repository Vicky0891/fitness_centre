<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>User</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<h1>My profile</h1>
<c:if test="${requestScope.message != null}">
<h3>${requestScope.message}</h3>
</c:if>

<h3>Account number: <c:out value="${user.id}"/></h3>
<h3>Email: <c:out value="${user.email}"/></h3>


<c:if test="${sessionScope.user['class'].simpleName == 'ClientDto'}">
<br/>
<h3>First name: <c:out value="${user.firstName}"/></h3>
<h3>Last name: <c:out value="${user.lastName}"/></h3>
<c:if test="${user.birthDate != '0001-01-01'}">
<h3>Date of birth: <c:out value="${user.birthDate}"/></h3>
</c:if>
<h3>Phone number: <c:out value="${user.phoneNumber}"/></h3>
<h3>Account number: <c:out value="${user.id}"/></h3>
<h3>Additional info: <c:out value="${user.additionalInfo}"/></h3>
<br/>
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_profile_form"/>
<input type="hidden" name="id" value="${user.id}"/>
<input type="submit" value="Edit personal information"/>
</form>
<h3><a href="controller?command=orders">My orders</a></h3>


<br/>

<h3><a href="controller?command=prescription&id=${user.id}">Personal prescription</a></h3>
</c:if>



<c:if test="${sessionScope.user['class'].simpleName == 'TrainerDto'}">
<br/>
<h3>First name: <c:out value="${user.firstName}"/></h3>
<h3>Last name: <c:out value="${user.lastName}"/></h3>
<c:if test="${user.birthDate != '0001-01-01'}">
<h3>Date of birth: <c:out value="${user.birthDate}"/></h3>
</c:if>
<h3>Category: <c:out value="${user.category}"/></h3>
<br/>
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_cabinet_form"/>
<input type="hidden" name="id" value="${user.id}"/>
<input type="submit" value="Edit personal information"/>
</form>
<br/>
<h3><a href="controller?command=clients">My clients</a></h3>
</c:if>





</body>
</html>