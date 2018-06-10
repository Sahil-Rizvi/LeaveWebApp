<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <title>Employee Leave Management System :: Employee Sign In</title>
   <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
  
<%--   <spring:url value="/resources/css/jquery/jquery-ui.css" var="jqueryuicss" /> --%>
<%--   <link href="${jqueryuicss}" rel="stylesheet" /> --%>
  
  <spring:url value="/resources/css/style.css" var="stylecss" />
  <link href="${stylecss}" rel="stylesheet" />
  
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  
<%--   <spring:url value="/resources/js/jquery/jquery.min.js" var="jqueryminjs" /> --%>
 
<%--   <spring:url value="/resources/js/jquery/jquery-ui.min.js" var="jqueryuiminjs" /> --%>
  
  <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
  
    
</head>
<body>

  
  <div class="login-card">
    <h3 style="text-align:center;">Employee Leave Management System</h3>
    <h1>Sign in</h1><br>
    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
      <font color="red">
        Your login attempt was not successful due to <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
      </font>
      <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
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
		

  <form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST'>
    <input type="text" name="username" required="required" placeholder="Employee Code">
    <input type="password" name="password" required="required" placeholder="Password">
    <input type="submit" name="login" class="login login-submit" value="Sign In">
    <input type="hidden" name="${_csrf.parameterName}"
                 value="${_csrf.token}" />
  </form>
    
  <div class="login-help">
    <a href="/LeaveWebApp/r/register">Sign Up</a><br>
    <a href="/LeaveWebApp/r/emp/forgetPassword">Forgot Password</a><br>
    <a href="/LeaveWebApp/customAdmin/login">Sign In As Admin</a>
  </div>
  <div id="info"></div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

<%-- <script src="${jqueryminjs}"></script> --%>
<%-- <script src="${jqueryuiminjs}"></script> --%>


</body>
</html>