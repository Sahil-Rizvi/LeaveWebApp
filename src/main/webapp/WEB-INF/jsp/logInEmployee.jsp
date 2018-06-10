<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Employee Log In</title>
</head>
<body>
<h3>Log In Form</h3>
<form id="logInForm" action="submitLogInEmpDetails" method="post">
<p>
<label>Emp Id</label>
<input name="empId" type="text"/>
</p>
<p>
<label>Password</label>
<input name="empPass" type="password"/>
</p>
<input type="submit" value="SubmitForm"/>
</form>
<br><br>
<a href="/LeaveWebApp">Register (in case not registered)</a>



<script>
$("#logInForm").submit(function(e){
	var form = this;
	e.preventDefault();
	var formData={};
	$.each(this,function(i,v){
		var input = $(v);
		formData[input.attr('name')]=input.val();
	});
	$.ajax({
		type:this.attr('method'),
		url:this.attr('action'),
		dataType:'json',
		data:JSON.stringify(formData),
	});
});
</script>

</body>
</html>