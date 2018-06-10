<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>   
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EMPLOYEES</title>

</head>
<body>
<c:if test="${not empty employees.employees}">
<form:form modelAttribute="employees"  method="POST"    id="responseForm">
<div class="table-responsive">          
<table class="table table-hover">
<thead>
<tr>
<th>EMPLOYEE CODE</th>
<th>EMPLOYEE NAME</th>
<th>DESIGNATION</th>
<th>DEPARTMENT</th>
<th>DATE OF JOINING</th>
<th>MANAGER STATUS</th>
</tr>
</thead>
<tbody>
<c:forEach items="${employees.employees}"  var="e" varStatus="i"> 
<tr>
<form:hidden name="empId" path="employees[${i.index}].empCode"/>
<td>${e.empCode}</td>
<td>${e.name}</td>
<td>${e.designation}</td>
<td>${e.department}</td>
<td>${e.dateOfJoining}</td>
<td>
<form:radiobutton name="managerStatus" path="employees[${i.index}].managerStatus" value="true"/> Assign
<form:radiobutton name="managerStatus" path="employees[${i.index}].managerStatus" value="false"/> Not Now
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div> 
<input type="submit" value="Submit"/>
</form:form>
<div id="info"></div>
</c:if>
<c:if test="${empty employees.employees}">
No Employee With Manager Status False Exists
</c:if>
<script type="text/javascript">

$('#responseForm').submit(function(e) {
    // reference to form object
var form = $(this);
    // for stopping the default action of element
    e.preventDefault();
    // mapthat will hold form data
   $.ajax({
       type: form.attr('method'), // method attribute of form
       url: "/LeaveWebApp/hr/updateManagerStatus",  // action attribute of form
   // convert form data to json format
       data : form.serialize(),
       success: function(response){
   		// we have the response
   		//alert(response)
   		$('#info').html(response);
   		},
   		error: function(e){
   			$('#info').html('EXCEPTION OCCURED');
   		}
    });
});

</script>
</body>
</html>
