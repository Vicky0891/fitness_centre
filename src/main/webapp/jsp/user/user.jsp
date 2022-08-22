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
<title>User</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>

<h3><fmt:message key="msg.user.account"/>: <c:out value="${user.id}"/></h3>
<h3><fmt:message key="msg.login.email"/>: <c:out value="${user.email}"/></h3>


<c:if test="${sessionScope.user['class'].simpleName == 'ClientDto'}">
<img src="images/usersavatars/${user.pathAvatar}" alt="photo" class="clientphoto">


<h3><fmt:message key="msg.user.firstname"/>: <c:out value="${user.firstName}"/></h3>
<h3><fmt:message key="msg.user.lastname"/>: <c:out value="${user.lastName}"/></h3>
<c:if test="${user.birthDate != '0001-01-01'}">
<h3><fmt:message key="msg.user.birthdate"/>: <c:out value="${user.birthDate}"/></h3>
</c:if>
<h3><fmt:message key="msg.user.phonenumber"/>: <c:out value="${user.phoneNumber}"/></h3>
<h3><fmt:message key="msg.user.addinfo"/>: <c:out value="${user.additionalInfo}"/></h3>
<br/>
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_profile_form"/>
<input type="hidden" name="id" value="${user.id}"/>
<input type="submit" value="<fmt:message key="msg.user.editinfo"/>"/>
</form>
<h3><a href="controller?command=orders"><fmt:message key="msg.user.myorders"/></a></h3>
<h3><a href="controller?command=prescription&id=${user.id}"><fmt:message key="msg.user.persprescription"/></a></h3>
</c:if>



<c:if test="${sessionScope.user['class'].simpleName == 'TrainerDto'}">
<br/>
<h3><fmt:message key="msg.user.firstname"/>: <c:out value="${user.firstName}"/></h3>
<h3><fmt:message key="msg.user.lastname"/>: <c:out value="${user.lastName}"/></h3>
<c:if test="${user.birthDate != '0001-01-01'}">
<h3><fmt:message key="msg.user.birthdate"/>: <c:out value="${user.birthDate}"/></h3>
</c:if>
<h3><fmt:message key="msg.user.category"/>: <c:out value="${user.category}"/></h3>
<br/>
<form method="post" action="controller">
<input type="hidden" name="command" value="edit_cabinet_form"/>
<input type="hidden" name="id" value="${user.id}"/>
<input type="submit" value="<fmt:message key="msg.user.editinfo"/>"/>
</form>
<br/>
<h3><a href="controller?command=clients"><fmt:message key="msg.user.myclients"/></a></h3>
</c:if>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>