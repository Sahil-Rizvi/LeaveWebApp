<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
  <title>:: Manager Home ::</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
<%--     <spring:url value="/resources/css/bootstrap/bootstrap.min.css" var="bootstrapmincss" /> --%>
<%--   <link href="${bootstrapmincss}" rel="stylesheet" /> --%>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<%--   <spring:url value="/resources/js/jquery/jquery.min.js" var="jqueryminjs"/> --%>
<%--   <script src="${jqueryminjs}"></script> --%>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<%--   <spring:url value="/resources/js/bootstrap/bootstrap.min.js" var="bootstrapminjs"/> --%>
<%--   <script src="${bootstrapminjs}"></script> --%>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
	
  
<%--    <spring:url value="/resources/css/jquery/jquery-ui.css" var="jqueryuicss" /> --%>
<%--   <link href="${jqueryuicss}" rel="stylesheet" />   --%>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
 

<%--    <spring:url value="/resources/css/jquery/jquery1.11.3.min.js" var="jquery1113minjs" /> --%>
<%--    <script src="${jquery1113minjs}"></script>     --%>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
 
<%--    <spring:url value="/resources/css/jquery/jquery-ui.min.js" var="jqueryuiminjs" /> --%>
<%--    <script src="${jqueryuiminjs}"></script>     --%>
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
                            <a data-toggle="collapse" data-parent="#accordion" href="#requests"><span class="glyphicon glyphicon-bell">
                            </span> Requests</a>
                        </h4>
                    </div>
                    <div id="requests" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-send"></span><a id="leaveRequests" href="#leaveRequests"> Leave Requests</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-send"></span><a id="compOffRequests" href="#compOffRequests"> Comp-Off Requests</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#leaves"><span class="glyphicon glyphicon-folder-open">
                            </span> Leaves</a>
                        </h4>
                    </div>
                    <div id="leaves" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ok-circle"></span><a id="approvedLeaves" href="#approvedLeaves"> Approved Leaves</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-remove-circle"></span><a id="rejectedLeaves" href="#rejectedLeaves"> Rejected Leaves</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#compOffs"><span class="glyphicon glyphicon-folder-open">
                            </span> Comp Offs</a>
                        </h4>
                    </div>
                    <div id="compOffs" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
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
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a id="empHome" href="#empHome"><span class="glyphicon glyphicon-dashboard"></span> Employee Home</a>
                        </h4>
                    </div>
                </div>
            </div>
    </div>
    <div id="new" class="col-sm-8 text-left">   		
    </div>
    <div class="col-sm-2 sidenav">
      <div >
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-eye-open"></span> Employee Id : ${mg.employeeId} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-star-empty"></span> Name : ${mg.employeeName} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-pencil"></span> Designation : ${mg.designation} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-home"></span> Department : ${mg.department} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-briefcase"></span> ManagerId : ${mg.managerId} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-envelope"></span> Email : ${mg.email} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-phone"></span> Contact : ${mg.phoneNo} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-calendar"></span> D.O.B. : <fmt:formatDate type = "date" value = "${mg.dateOfBirth}" /> 
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


<script>

	emp="${empId}"; 

	$('#empHome').on('click',function(){
		location.href="empHome";
	});
	
	$('#leaveRequests').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leave/manager/getLeavesForUpdationForManager",  
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#compOffRequests').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/compOff/manager/getCompOffsForUpdationForManager",  
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
		url: "/LeaveWebApp/leave/manager/status/approved",  
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
		url: "/LeaveWebApp/leave/manager/status/rejected",  
		data: {"emp":emp},
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
		url: "/LeaveWebApp/compOff/manager/status/approved",  
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
		url: "/LeaveWebApp/compOff/manager/status/rejected",  
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
