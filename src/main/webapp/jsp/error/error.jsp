<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Error</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<c:if test="${requestScope.message != null}">
<h3>${requestScope.message}</h3>
</c:if>
<c:if test="${requestScope.errorStatus != null}">
<h3>Error ${requestScope.errorStatus}</h3>
</c:if>
<h1 class="error">Something went wrong</h1>

</body>
</html>