<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <title>Employee Leave Management System :: Admin Sign In</title>
  <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    
<%--    <spring:url value="/resources/css/jquery/jquery-ui.css" var="jqueryuicss" /> --%>
<%--    <link href="${jqueryuicss}" rel="stylesheet" /> --%>
    
    <spring:url value="/resources/css/style.css" var="mainCss" />
    <link href="${mainCss}" rel="stylesheet" />
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  
<%--   <spring:url value="/resources/js/jquery/jquery.min.js" var="jqueryminjs" /> --%>
 
<%--   <spring:url value="/resources/js/jquery/jquery-ui.min.js" var="jqueryuiminjs" /> --%>
  
  <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
  
    
    
</head>
<body>

<div class="login-card">
    <h3 style="text-align:center;">Employee Leave Management System</h3>
    <h1>Admin Sign in</h1><br>
     <c:if test="${not empty param.message}">
     <div class="error">${param.message}</div>
     </c:if>
     <c:if test="${not empty param.v}">
      <font color="green">
        Account verified .
      </font>
     </c:if>
     <c:if test="${not empty param.ivt}">
      <font color="red">
        Invalid token .
      </font>
     </c:if>
     <c:if test="${not empty param.te}">
      <font color="red">
        Token expired .
      </font>
     </c:if>

    <form name='loginForm' action="/LeaveWebApp/customAdmin/process_admlogin" method='POST'>
       <input type="email" name="username" required="required" placeholder="admin@address.com">
       <input type="password" name="password" required="required" placeholder="Password">
      <input name="submit" type="submit" class="login login-submit" value="submit" />
     </form>
    <div class="login-help">  
    <a href="/LeaveWebApp/admin/register">Sign Up</a><br>
    <a href="/LeaveWebApp/admin/adm/forgetPassword">Forgot Password</a><br>
    <a href="/LeaveWebApp/">Sign In As Employee</a>
    </div>
    <div id="info"></div>
</div>
    
</body>
</html>
