<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Employee Leave Management System :: Admin Reset Password Form</title>
    <spring:url value="/resources/css/style.css" var="stylecss" />
    <link href="${stylecss}" rel="stylesheet" />
<%--     <spring:url value="/resources/js/jquery/jquery1.11.2.min.js" var="jqueryminjs" /> --%>
 
        
</head>
<body>
<div sec:authorize="hasAuthority('CHANGE_PASSWORD_PRIVILEGE')">

<div class="login-card">
    <h3 style="text-align:center;">Employee Leave Management System</h3>
    <h3  style="text-align:center;">Admin Reset Password Form</h3>
    <form name='resetPasswordForm' action="#" method='POST'>
      <input required='required' type='password' id='oldPassword' name='oldPassword' placeholder="password">
      <input required='required' type='password' id='newPassword' name='newPassword' placeholder="confirm password">
      <input name="submit" type="submit" class="login login-submit" value="submit" />
     </form>
    <h4 id="info" class="successbox"></h4>
</div>   


</div>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<%-- <script src="${jqueryminjs}"></script> --%>

<script>
$(document).ready(function () {
	$('form').submit(function(event) {
		resetPass(event);
    });
});

function resetPass(event){
    event.preventDefault();	
    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#newPassword").val();
	$.ajax({
		type: "POST",
		url: "/LeaveWebApp/admin/adm/savePassword",
		data: {
		           "oldPassword":oldPassword,
		           "newPassword":newPassword
		},  
		success: function(response){
			$('#info').html(response);
		},
		error: function(e){
			$('#info').html(e);
		}
		});

    
}
</script>
</body>

</html>
