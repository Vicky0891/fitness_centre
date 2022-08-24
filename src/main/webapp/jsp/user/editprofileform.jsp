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
<title><fmt:message key="msg.editprofile.title"/></title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><fmt:message key="msg.editprofile.title"/></h1>
<c:if test="${requestScope.message != null}">
<h5>${requestScope.message}</h5>
</c:if>
<form method="post" action="controller" enctype="multipart/form-data">
<input name="command" type="hidden" value="edit_profile"/>
<input type="hidden" name="page" value="jsp/user/editprofileform.jsp"/>
<input name="id" type="hidden" value="${sessionScope.user.id}"/>

<label for="avatar"><fmt:message key="msg.editprofile.photo"/>: </label>
<input type="file" name="avatar" accept="image/*"/>
<br/>
<label for="firstName-input"><fmt:message key="msg.user.firstname"/>: </label>
<input id="firstName-input" name="firstName" type="text" value="${sessionScope.user.firstName}"/>
<br/>
<label for="lastName-input"><fmt:message key="msg.user.lastname"/>: </label>
<input id="lastName-input" name="lastName" type="text" value="${sessionScope.user.lastName}"/>
<br/>
<label for="birthDate-input"><fmt:message key="msg.user.birthdate"/>: </label>
<input id="birthDate-input" name="birthDate" type="date"/>
<br/>
<label for="phoneNumber-input"><fmt:message key="msg.editprofile.phonenumber"/>: </label>
<input id="phoneNumber-input" name="phoneNumber" type="tel" value="${sessionScope.user.phoneNumber}" pattern="\d{12}"/>
<br/>
<label for="additionalInfo-textarea"><fmt:message key="msg.user.addinfo"/>: </label>
<textarea id="additionalInfo-textarea" name="additionalInfo" placeholder="${sessionScope.user.additionalInfo}" rows="5" cols="20"></textarea>
<br/>
<input type="submit" value="<fmt:message key="msg.order.save"/>"/>
</form>
<footer>
<jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>