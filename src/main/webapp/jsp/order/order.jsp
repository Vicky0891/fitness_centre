<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Orders</title>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Order details</h1>



<h3>Id: ${order.id}</h3>
<h3>Date of order: ${order.dateOfOrder}</h3>
<h3>Total cost: ${order.totalCost}</h3>



          
</body>
</html>