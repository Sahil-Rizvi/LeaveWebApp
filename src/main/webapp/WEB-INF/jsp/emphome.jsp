<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
  <title>:: EmployeeHome ::</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

<%--   <spring:url value="/resources/css/bootstrap/bootstrap.min.css" var="bootstrapmincss" /> --%>
<%--   <link href="${bootstrapmincss}" rel="stylesheet" /> --%>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<%--   <spring:url value="/resources/js/jquery/jquery.min.js" var="jqueryminjs"/> --%>
<%--   <script src="${jqueryminjs}"></script> --%>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<%--   <spring:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapminjs"/> --%>
<%--   <script src="${bootstrapminjs}"></script> --%>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
<%--   <spring:url value="/resources/css/jquery/jquery-ui.css" var="jqueryuicss" /> --%>
<%--   <link href="${jqueryuicss}" rel="stylesheet" />   --%>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">

<%--    <spring:url value="/resources/css/jquery/jquery1.11.3.min.js" var="jquery1113minjs" /> --%>
<%--    <script src="${jquery1113minjs}"></script>     --%>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>


<%--   <spring:url value="/resources/js/jquery/jquery-ui.min.js" var="jqueryuiminjs"/> --%>
<%--   <script src="${jqueryuiminjs}"></script> --%>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>


 <style> 
 
input[type=text], input[type=password],select, textarea {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    resize: vertical;
}

label {
    padding: 12px 12px 12px 0;
    display: inline-block;
}

input[type=submit] {
    background-color: #999;
    color: white;
    padding: 8px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    float: right;
}

input[type=submit]:hover {
    background-color: #999;
}
</style>
  
  <style>

   .panel-heading:hover {
  background-color: #dfdfdf ;
}
    /* Remove the navbar's default margin-bottom and rounded borders */ 
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
    }
     
   a {
    text-decoration: none !important;
} 
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 540px}
    
    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 100%;
      text-align:left;
    }
    
    a{
      text-decoration:none;
    }
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;} 
    }
  </style>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">Logo</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a id="about" href="#about">About</a></li>
        <li><a id="files" href="#files">Files</a></li>
        <li><a id="holidayfile" href="#holidayfile">Holidays</a></li>
        <li><a id="contact" href="#contact">Contact</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/LeaveWebApp/j_spring_security_logout"><span class="glyphicon glyphicon-log-in"></span> Log Out</a></li>
      </ul>
    </div>
  </div>
</nav>
<div class="container-fluid text-center">    
  <div class="row content">
    <div class="col-sm-2 sidenav">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a id="balance" href="#leaveBalance"><span class="glyphicon glyphicon-hourglass"></span>Leave Balance</a>
                        </h4>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a id="apply" href="#applyLeave"><span class="glyphicon glyphicon-edit"></span>Apply Leave</a>
                        </h4>
                    </div>
                </div>                
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#leaveRequests"><span class="glyphicon glyphicon-folder-open">
                            </span> Leave Requests</a>
                        </h4>
                    </div>
                    <div id="leaveRequests" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ban-circle"></span><a id="pendingLeaves" href="#pendingleaves"> Pending Leaves</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ok-circle"></span><a id="approvedLeaves" href="#approvedleaves"> Approved Leaves</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-remove-circle"></span><a id="rejectedLeaves" href="#rejectedleaves"> Rejected Leaves</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a id="team" href="#team"><span class="glyphicon glyphicon-link"></span> Leaves Of Team-mates</a>
                        </h4>
                    </div>
                </div>                
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a id="registerCompOff" href="#applyCompOff"><span class="glyphicon glyphicon-edit"></span>Apply Comp-Off</a>
                        </h4>
                    </div>
                </div>        
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#compOffRequests"><span class="glyphicon glyphicon-folder-open">
                            </span> Comp Off Requests</a>
                        </h4>
                    </div>
                    <div id="compOffRequests" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ban-circle"></span><a id="pendingCompOffs" href="#pendingCompOffs"> Pending Comp Offs</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ok-circle"></span><a id="approvedCompOffs" href="#approvedCompOffs"> Approved Comp Offs</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-remove-circle"></span><a id="rejectedCompOffs" href="#rejectedCompOffs"> Rejected Comp Offs</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
	        <c:if test="${not empty managerLink}">        
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a id="managerHome" href="#managerHome"><span class="glyphicon glyphicon-dashboard"></span> Manager Home</a>
                        </h4>
                    </div>
                </div>                
	        </c:if>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#account"><span class="glyphicon glyphicon-user">
                            </span> Account</a>
                        </h4>
                    </div>
                    <div id="account" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-lock"></span><a id="updatePassword" href="#updatePassword"> Change Password</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-cog"></span><a id="updateEmp" href="#updateEmp"> Update Details</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>    
    <div id="new" class="col-sm-8 text-left"> 
      <div id="whole1"></div>
    </div>
    <div class="col-sm-2 sidenav">
      <div class="">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-eye-open"></span> Employee Id : ${emp.employeeId} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-star-empty"></span> Name : ${emp.employeeName} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-pencil"></span> Designation : ${emp.designation} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-home"></span> Department : ${emp.department} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-briefcase"></span> ManagerId : ${emp.managerId} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-envelope"></span> Email : ${emp.email} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-phone"></span> Contact : ${emp.phoneNo} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-calendar"></span> D.O.B. : <fmt:formatDate type = "date" value = "${emp.dateOfBirth}" /> 
                                    </td>
                                </tr>
                            </table>
            </div>
    </div>
  </div>
</div>




<footer class="container-fluid text-center">
  <p>Footer Text</p>
</footer>

<c:if test="${not empty managerLink}">
<script type="text/javascript">
$('#managerHome').on('click',function(){
		location.href = "managerHome";		
	});
</script>
</c:if>

<script>

emp="${empId}"; 

	$('#balance').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leave/emp/viewLeaveBalance",  
		success: function(response){
		
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
		
	$('#updateEmp').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/emp/updateEmployee",  
		success: function(response){	
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#updatePassword').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/emp/updatePassword",  
		success: function(response){	
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#apply').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leave/emp/leaveHome",  
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#approvedLeaves').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leave/emp/status/approved",  
		data: {"emp":emp},
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#pendingLeaves').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leave/emp/status/pending",  
		data: {"emp":emp},
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});

	$('#rejectedLeaves').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leave/emp/status/rejected",  
		data: {"emp":emp},
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#team').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leave/emp/status/teammates", 
		data:{"emp":emp},
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#registerCompOff').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/compOff/emp/compOffHome",  
		success: function(response){
		
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#approvedCompOffs').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/compOff/emp/status/approved",  
		data: {"emp":emp},
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#pendingCompOffs').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/compOff/emp/status/pending",  
		data: {"emp":emp},
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});

	$('#rejectedCompOffs').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/compOff/emp/status/rejected",  
		data: {"emp":emp},
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	
	
	
	$('#about').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/tab/about",  
		success: function(response){	
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	
	$('#files').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/tab/files",  
		success: function(response){	
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	
	$('#holidayfile').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/tab/holiday",  
		success: function(response){	
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	
	$('#contact').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/tab/contact",  
		success: function(response){	
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	


</script>



</body>
</html>
