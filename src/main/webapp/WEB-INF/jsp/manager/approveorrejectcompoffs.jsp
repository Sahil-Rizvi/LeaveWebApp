<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>   
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CompOff Requests</title>

</head>
<body>
<c:if test="${not empty compOffResponse.compOffs}">
<form:form modelAttribute="compOffResponse"  method="POST"   id="responseForm">
<table border=1 width="50%">
<tr>
<th>COMPOFF IDs</th>
<th>EMPLOYEE CODE</th>
<th>EMPLOYEE NAME</th>
<th>FOR DATE</th>
<th>APPLIED ON</th>
<th>RESPONSE</th>
<th>REJECTION REASON</th>
</tr>
<c:forEach items="${compOffResponse.compOffs}"  var="c" varStatus="i"> 
<tr>
<form:hidden name="leaveId" path="compOffs[${i.index}].compOffId"/>
<td>${c.compOffId}</td>
<td>${c.employeeCode}</td>
<td>${c.employeeName}</td>
<td>${c.forDate}</td>
<td>${c.appliedOn}</td>
<td>
<form:radiobuttons name="responseType" path="compOffs[${i.index}].responseType" items="${enums}"/> 
</td>
<td>
<form:textarea name="rejectionReason" path="compOffs[${i.index}].rejectionReason"/>
</td>
</tr>
</c:forEach>
</table> 
<input type="submit" value="Submit"/>
</form:form>
<div id="info"></div>
</c:if>
<c:if test="${empty compOffResponse.compOffs}">
No CompOff Request is Pending
</c:if>
<script type="text/javascript">

$('#responseForm').submit(function(e) {
    // reference to form object
var form = $(this);
    // for stopping the default action of element
    e.preventDefault();
    // mapthat will hold form data
    alert(form.serialize());
   $.ajax({
       type: form.attr('method'), // method attribute of form
       url: "/LeaveWebApp/compOff/manager/approveOrReject",  // action attribute of form
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
