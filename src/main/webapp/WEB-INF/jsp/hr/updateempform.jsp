<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update emp form for hr</title>
</head>
<body>

<div id="editFormDiv">
    <form name="editForm" id="editForm">
		<p><label for="employeeCode">Emp Code:</label>
  		<input type="text" name="employeeCode" required='required' id="employeeCode" value="${emp.employeeCode}" readonly="readonly"/><br></p>
  		<p><label for="name">Name:</label>
  		<input type="text" name="name" required='required' id="name" value="${emp.name}"/><br></p>
  		<p><label for="designation">Designation:</label>
  		<select id="designation" required='required' name="designation">
        <c:forEach items="${designationTypes}" var="type">
        <option value="${type}" ${type == emp.designation ? 'selected="selected"' : ''}>${type}</option>
        </c:forEach>
         </select>
  		</p>
  		<p>
  		<p><label for="department">Department:</label>
  		<select id="department" required='required' name="department">
        <c:forEach items="${departmentTypes}" var="type">
         <option value="${type}" ${type == emp.department ? 'selected="selected"' : ''}>${type}</option>
       </c:forEach>
         </select>
  		</p>
  		<p>
        <label for="dateofBirth">Date of Birth</label>
        <input id="dateofBirth" required='required' name="dateofBirth" type="text" id="datepicker" value="${emp.dateofBirth}" placeholder="dd/MM/yyyy"/>
        </p>
        <p>
        <label for="emailId">Email</label>
        <input id="emailId" required='required' name="emailId" type="email" value="${emp.emailId}"/>
        </p>
        <p>
        <label for="phoneNo">Phone No.</label>
        <input id="phoneNo" required='required' name="phoneNo" type="text" value="${emp.phoneNo}"/>
        </p>
  		<p>
        <label for="managerCode">Manager Code</label>
        <input id="managerCode" required='required' name="managerCode" type="text" value="${emp.managerCode}"/>
        </p>
        <p>
  		<label for="isManager">Manager Status</label>
  		<input type="radio" name="isManager" value="ASSIGN" ${"ASSIGN" == emp.isManager ? 'checked="checked"' : ''}/> ASSIGN
		<input type="radio" name="isManager" value="REMOVE" ${"REMOVE" == emp.isManager ? 'checked="checked"' : ''}/> REMOVE
  		</p>
  		
  		<input type="submit" id="editEmpSubmit" value="Submit"/>
  		</form>
        <div id="editResp"></div>
</div>


<script type="text/javascript">


$("#datepicker").datepicker({
	changeMonth: true,//this option for allowing user to select month
    changeYear: true, //this option for allowing user to select from year range
    todayBtn: "linked",
    language: "it",
    autoclose: true,
    todayHighlight: true,
    dateFormat: 'dd/mm/yy' 
});






$("#editEmpSubmit").on('click', function(e) {
    var f = $(this);
    e.preventDefault();
    var data = $('form[name=editForm]').serialize();
    alert(data);
    	$.ajax({
   	       type: 'POST', // method attribute of form
   	       url: '/LeaveWebApp/hr/updateEmployee',  // action attribute of form
   	   // convert form data to json format
   	       data : $('form[name=editForm]').serialize(),
   	       success: function(response){
   	    	//alert(response);
   	    	$('#editResp').html(response);
			modal.style.display = "block";
   	   		},
   	   		error: function(e){
   	   			alert(e);
   	   		}
   	    });
    
});
</script>

</body>
</html>