<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Error</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1 class="error">Error 500</h1>
<h2>Internal server error</h2>
<c:if test="${requestScope.message != null}">
<h3>${requestScope.message}</h3>
</c:if>

</body>
</html>