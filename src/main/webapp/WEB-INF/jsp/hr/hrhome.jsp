<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
  <title>:: HR Home ::</title>
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
 
 <spring:url value="/resources/js/mypagination.js" var="paginationJS" />
<link href="${paginationJS}" lang="javascript"/>
 
  <style> 
 
input[type=text],input[type=email], input[type=password],select, textarea {
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

input[type=submit] , button , .adddesigbutton , .adddeptbutton, .add , .remove {
    background-color: #999;
    color: white;
    padding: 8px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    float: right;
}

input[type=submit]:hover , button:hover , .adddesigbutton:hover , .adddeptbutton:hover, .add:hover , .remove:hover{
    background-color: #999;
}



</style>
 
  
  <style>


.dropbtn {
    background-color: #999;
    color: white;
    padding: 16px;
    font-size: 16px;
    border: none;
}

.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f1f1f1;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}

.dropdown-content a:hover {background-color: #ddd}

.dropdown:hover .dropdown-content {
    display: block;
}

.dropdown:hover .dropbtn {
    background-color: #3e8e41;
}
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
      <div class="dropdown">
    <button class="dropbtn">Notifications 
      <i class="fa fa-caret-down"></i>
    </button>
    <div id="notifications" class="dropdown-content">      
    </div>
  </div> 
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/LeaveWebApp/customAdmin/logout"><span class="glyphicon glyphicon-log-in"></span> Log Out</a></li>
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
                            <a data-toggle="collapse" data-parent="#accordion" href="#employees"><span class="glyphicon glyphicon-folder-open">
                            </span> Employee</a>
                        </h4>
                    </div>
                    <div id="employees" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ban-circle"></span><a id="searchEmp" href="#searchEmp"> Search</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ok-circle"></span><a id="listEmpInDept" href="#listEmpInDept"> View</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-remove-circle"></span><a id="updateManagerStatus" href="#updateManagerStatus"> Update Manager Status</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#departments"><span class="glyphicon glyphicon-folder-open">
                            </span> Departments</a>
                        </h4>
                    </div>
                    <div id="departments" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ban-circle"></span><a id="addDept" href="#addDept"> Add</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ok-circle"></span><a id="viewDept" href="#viewDept"> View</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#designations"><span class="glyphicon glyphicon-folder-open">
                            </span> Designations</a>
                        </h4>
                    </div>
                    <div id="designations" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ban-circle"></span><a id="addDesig" href="#addDesig"> Add</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ok-circle"></span><a id="viewDesig" href="#viewDesig"> View</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#leaveCount"><span class="glyphicon glyphicon-folder-open">
                            </span> Leave Count</a>
                        </h4>
                    </div>
                    <div id="leaveCount" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ban-circle"></span><a id="addLeaveCount" href="#addLeaveCount"> Add</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ok-circle"></span><a id="viewLeaveCount" href="#viewLeaveCount"> View</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-ok-circle"></span><a id="viewAllLeaveCount" href="#viewAllLeaveCount"> View All</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
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
                                        <span class="glyphicon glyphicon-lock"></span><a id="updateAdminDetails" href="#updateAdminDetails"> Update Details</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-cog"></span><a id="updateAdminPassword" href="#updateAdminPassword"> Update Password</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-cog"></span><a id="deleteAdminAccount" href="#deleteAdminAccount"> Delete </a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#fileSystem"><span class="glyphicon glyphicon-user">
                            </span> File System</a>
                        </h4>
                    </div>
                    <div id="fileSystem" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-lock"></span><a id="holiday" href="#holiday"> Holiday</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-cog"></span><a id="leavepolicy" href="#leavepolicy"> Leave Policy</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-cog"></span><a id="notice" href="#notice"> Notice</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-cog"></span><a id="other" href="#other"> Others</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
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
                                        <span class="glyphicon glyphicon-star-empty"></span> Name : ${admin.name} 
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="glyphicon glyphicon-envelope"></span> Email : ${admin.email} 
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

$(document).ready(function() {

	var holidayMessage = "${holidayMessage}";
	var departmentMessage = "${departmentMessage}";
	var designationMesssage = "${designationMessage}";
	var leaveCountMessage = "${leaveCountMessage}";
	
	if(holidayMessage){
		var mydiv = document.getElementById("notifications");
		var aTag = document.createElement('a');
		aTag.setAttribute('href',"#");
		aTag.innerHTML = holidayMessage;
		mydiv.appendChild(aTag);
	}
	
	if(departmentMessage){
		var mydiv = document.getElementById("notifications");
		var aTag = document.createElement('a');
		aTag.setAttribute('href',"#");
		aTag.innerHTML = departmentMessage;
		mydiv.appendChild(aTag);
	}
	
	if(designationMesssage){
		var mydiv = document.getElementById("notifications");
		var aTag = document.createElement('a');
		aTag.setAttribute('href',"#");
		aTag.innerHTML = designationMesssage;
		mydiv.appendChild(aTag);
	}
	
	if(leaveCountMessage){
		var mydiv = document.getElementById("notifications");
		var aTag = document.createElement('a');
		aTag.setAttribute('href',"#");
		aTag.innerHTML = leaveCountMessage;
		mydiv.appendChild(aTag);
	}
	


	$('#upload').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/hr/uploadForm",  
		success: function(response){
		
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	

	$('#download').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/hr/download",  
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	
	$('#updateManagerStatus').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/hr/empToUpdateManagerStatus",  
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});

	
	$('#searchEmp').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/hr/form/empByCode",  
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#listEmpInDept').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/hr/form/empByDept",  
		success: function(response){
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});

	
	

	
	$('#addDept').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/customAdmin/dept/add",  
		success: function(response){
		
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#viewDept').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/customAdmin/dept/view",  
		success: function(response){
		
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	
	
	
	

	$('#addDesig').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/customAdmin/desig/add",  
		success: function(response){
		
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	
	
	

	$('#viewDesig').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/customAdmin/desig/view",  
		success: function(response){
		
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});

	
	$('#updateAdminDetails').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/hr/updateAdmin",  
		success: function(response){	
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	$('#updateAdminPassword').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/hr/updatePassword",  
		success: function(response){	
		$('#new').html(response);
		},
		error: function(e){
			$('#new').html(e);
		}
		});
	});
	
	
	$('#deleteAdminAccount').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/hr/deleteAdmin",  
		success: function(response){	
		$('#new').html(response);
		},
		
		error:function (xhr, ajaxOptions, thrownError){
		    if(xhr.status==404) {
		        $('#new').html("Permitted only after log in");
		    }
		}
		
		});
	});

	
	$('#addLeaveCount').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leavecount/add",  
		success: function(response){	
		$('#new').html(response);
		},
		
		error:function (xhr, ajaxOptions, thrownError){
		    if(xhr.status==404) {
		        $('#new').html("Permitted only after log in");
		    }
		}
		
		});
	});

	$('#viewLeaveCount').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leavecount/get",  
		success: function(response){	
		$('#new').html(response);
		},
		
		error:function (xhr, ajaxOptions, thrownError){
		    if(xhr.status==404) {
		        $('#new').html("Permitted only after log in");
		    }
		}
		
		});
	});
	
	
	$('#viewAllLeaveCount').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leavecount/getAll",  
		success: function(response){	
		$('#new').html(response);
		},
		error:function (xhr, ajaxOptions, thrownError){
		    if(xhr.status==404) {
		        $('#new').html("Permitted only after log in");
		    }
		}
		});
	});
		

	$('#holiday').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/uploader/api/list/holiday",  
		success: function(response){	
		$('#new').html(response);
		},
		
		error:function (xhr, ajaxOptions, thrownError){
		    if(xhr.status==404) {
		        $('#new').html("Permitted only after log in");
		    }
		}
		
		});
	});

	
	$('#leavepolicy').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/uploader/api/list/leavepolicy",  
		success: function(response){	
		$('#new').html(response);
		},
		
		error:function (xhr, ajaxOptions, thrownError){
		    if(xhr.status==404) {
		        $('#new').html("Permitted only after log in");
		    }
		}
		
		});
	});

	
	$('#notice').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/uploader/api/list/notice",  
		success: function(response){	
		$('#new').html(response);
		},
		
		error:function (xhr, ajaxOptions, thrownError){
		    if(xhr.status==404) {
		        $('#new').html("Permitted only after log in");
		    }
		}
		
		});
	});

	
	$('#other').on('click', function() {
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/uploader/api/list/other",  
		success: function(response){	
		$('#new').html(response);
		},
		
		error:function (xhr, ajaxOptions, thrownError){
		    if(xhr.status==404) {
		        $('#new').html("Permitted only after log in");
		    }
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
	
	

	
});
	
</script>


</body>

</html>
