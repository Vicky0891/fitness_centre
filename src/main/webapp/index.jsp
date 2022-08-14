<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Fitness_centre</title>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1>Welcome to our fitness centre, Dear ${sessionScope.user != null ? sessionScope.user.email : 'Guest'}!</h1>

<jsp:include page="jsp/navbar.jsp"/>

</body>
</html>