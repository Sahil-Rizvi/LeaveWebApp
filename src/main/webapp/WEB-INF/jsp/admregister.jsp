<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html lang="en" >

<head>
  <meta charset="UTF-8">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <title>Employee Leave Management System :: Admin Sign Up</title>
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
    <h1>Admin Sign Up</h1><br>
    <c:if test="${not empty message}">
    <div class="errorbox">${message}</div>
    </c:if>
    <c:if test="${not empty param.emailerror}">
     <div class="successbox">${param.emailerror}</div>
    </c:if>	    
    <c:if test="${not empty param.successmessage}">
     <div class="successbox">Successfully Registered. A verification link is sent to your email Id.</div>
    </c:if>
    
<c:if test="${empty param.emailerror && empty param.successmessage &&  not empty block }">
 <div class="errorbox">${block}</div>
</c:if>    
<c:if test="${empty block}">
<form:form action="/LeaveWebApp/admin/register" method="post" commandName="admin" id="adminForm">     
	    <form:errors path="*" cssClass="errorbox" element="div" />
	    
	    <label>Name</label>
        <form:input required='required' path="name" type="text"/>
        <form:errors path="name" cssClass="error"/>
        
        <label>Email</label>
        <form:input required='required' path="email" type="email"/>
        <form:errors path="email" cssClass="error"/>
        
         <label>Password</label>
        <form:input required='required' path="password" type="password"/>
        <form:errors path="password" cssClass="error"/>
       
        <label>Confirm Password</label>
        <form:input required='required' path="matchingPassword" type="password"/>
        <form:errors path="matchingPassword" cssClass="error"/>

        <input type="submit" class="login login-submit" value="Submit Form">
</form:form>
</c:if>    
  <div class="login-help">
    <a href="/LeaveWebApp/customAdmin/login">Sign In</a><br>
    <a href="/LeaveWebApp/r/register">Sign Up As Employee</a><br>
  </div>
<div id="info"></div>
</div>

<%-- <script src="${jqueryminjs}"></script> --%>
<%-- <script src="${jqueryuiminjs}"></script> --%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>


<script>
  $(document).ready(function() {
    $("#datepicker").datepicker({
    	changeMonth: true,//this option for allowing user to select month
        changeYear: true, //this option for allowing user to select from year range
        todayBtn: "linked",
        language: "it",
        autoclose: true,
        todayHighlight: true,
        dateFormat: 'dd/mm/yy' 
    });
        
  });
  
  
</script>  
</body>
</html>