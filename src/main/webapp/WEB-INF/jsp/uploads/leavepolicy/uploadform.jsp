<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload LeavePolicy File</title>
</head>
<body>

<div id="uploadLeavePolicyForm">
    <form method="POST" enctype="multipart/form-data" id="leavePolicyFileUploadForm">
    Name : <input type="text" name="name"/><br/><br/>
    File :<input type="file" name="multipartFile"/><br/><br/>
    Description :<input type="text" name="description"/><br/><br/>
    <input type="submit" value="Submit" id="leavePolicyBtnSubmit"/>
	</form>    
    <div id="uploadResp"></div>
</div>


<script type="text/javascript">

$("#leavePolicyBtnSubmit").click(function (event) {

    //stop submit the form, we will post it manually.
    event.preventDefault();

    leavePolicy_fire_ajax_submit();

});



function leavePolicy_fire_ajax_submit() {

    // Get form
    var form = $('#leavePolicyFileUploadForm')[0];

    var data = new FormData(form);

    $("#leavePolicyBtnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/LeaveWebApp/uploader/api/upload/leavepolicy",
        data: data,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

            $("#uploadResp").text(data);
            $("#leavePolicyBtnSubmit").prop("disabled", false);

        },
        
        error:function (xhr, ajaxOptions, thrownError){
        	//$("#holidayResult").text("File not uploaded due to some exception");
		    if(xhr.status==404) {
		        $('#uploadResp').html("Permitted only after log in");
		    }
		    if(xhr.status=500){
		    	$('#uploadResp').html("File Size exceeded max limit.");
		    }
		    $("#leavePolicyBtnSubmit").prop("disabled", false);

		}
        
    });
}


</script>

</body>
</html>