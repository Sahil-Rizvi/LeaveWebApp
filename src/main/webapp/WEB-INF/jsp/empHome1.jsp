<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
</head>
<style type="text/css">
.error {
	color: red;
}
</style>
<body>



<h3>Registration Form</h3>
<form:form action="/LeaveWebApp/addEmployee" method="post" commandName="employee" id="empForm">     
	    <p>
	    <label>Employee Id</label>
        <form:input required='required' path="employeeId"  type="text"/>
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
        <form:input required='required' path="designation" type="text"/>
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
	    <input type="submit" value="Submit Form">
</form:form>
<br><br>
<a href="logInEmployee">Log In (If registered)</a>


<div id="info"></div>

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

<!--  
<script>
$('#empForm').submit(function(e){
var form = $(this);
e.preventDefault();
var formData = {}
$.each(this,function(i,v){
 var input = $(v);
 formData[input.attr('name')] = input.val();
});
$.ajax({
	type : form.attr('method'),
	url : form.attr('action'),
	data : form.serialize(),
});
});
</script>
-->
</body>
</html>
