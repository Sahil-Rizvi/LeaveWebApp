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

  <title>Employee Leave Management System :: Employee Sign Up</title>
  <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    
<%--     <spring:url value="/resources/css/jquery/jquery-ui.css" var="jqueryuicss"/> --%>
<%--     <link href="${jqueryuicss}" rel="stylesheet"/> --%>
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
    <h1>Sign Up</h1><br>
    <c:if test="${empty  departmentTypes}">
 	<br> Please contact Admin <br>
 	as Department names have not been uploaded.<br>
    </c:if>
    <c:if test="${empty  designationTypes}">
 	<br> Please contact Admin <br>
 	as Designation names have not been uploaded.<br>
    </c:if>
    <c:if test="${not empty lcmessage}">
 	<br> Please contact Admin <br>
 	as Leave Counts have not been uploaded for current year.<br>
    </c:if>
    <c:if test="${not empty message}">
    <div class="errorbox">${message}</div>
    </c:if>
    <c:if test="${not empty param.successmessage}">
     <div class="successbox">${param.successmessage}</div>
    </c:if>
<c:if test="${not empty  departmentTypes &&  not empty designationTypes && empty lcmessage}">
<form:form action="/LeaveWebApp/r/register" method="post" commandName="employee" id="empForm">     
	    <form:errors path="*" cssClass="errorbox" element="div" />
	    <p>
        <label>Employee Id</label>
        <form:input required='required' path="employeeId" type="text"/>
        <form:errors path="employeeId" cssClass="error"/>
        </p>
        <p>
        <label>Name</label>
        <form:input required='required' path="employeeName" type="text"/>
        <form:errors path="employeeName" cssClass="error"/>
        </p>
        <p>
        <label>Manager Id</label>
        <form:input path="managerId" type="text"/>
        <form:errors path="managerId" cssClass="error"/>
        </p>
        <p>
        <label>Department</label>
        <form:select required='required' id="department" path="department">
        <c:forEach items="${departmentTypes}" var="type">
         <option value="${type}">${type}</option>
        </c:forEach>
         </form:select>
         <form:errors path="department" cssClass="error"/>
        </p>
        <p>
        <label>Designation</label>
        <form:select required='required' id="designation" path="designation">
        <c:forEach items="${designationTypes}" var="type">
         <option value="${type}">${type}</option>
        </c:forEach>
         </form:select>
        <form:errors path="designation" cssClass="error"/>
        </p>
		<p>
        <label>Date of Birth</label>
        <form:input required='required' path="dateOfBirth" type="text" id="datepicker" placeholder="dd/MM/yyyy"/>
        <form:errors path="dateOfBirth" cssClass="error"/>
        </p>
        <p>
        <label>Email</label>
        <form:input required='required' path="email" type="email"/>
        <form:errors path="email" cssClass="error"/>
        </p>
        <p>
        <label>Phone No.</label>
        <form:input required='required' path="phoneNo" type="text"/>
        <form:errors path="phoneNo" cssClass="error"/>
        </p>
        <p>
        <label>Password</label>
        <form:input required='required' path="password" type="password"/>
        <form:errors path="password" cssClass="error"/>
        </p>
        <p>
        <label>Confirm Password</label>
        <form:input required='required' path="matchingPassword" type="password"/>
        <form:errors path="matchingPassword" cssClass="error"/>
        </p>
	    <input type="submit" class="login login-submit" value="Submit Form">
</form:form>
    
  <div class="login-help">
    <a href="/LeaveWebApp/">Sign In</a><br>
  </div>
</c:if>
  <div class="login-help">
    <br>
    <a href="/LeaveWebApp/admin/register">Sign Up As Admin</a>
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