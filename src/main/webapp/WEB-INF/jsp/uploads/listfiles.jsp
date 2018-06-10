<div class="col-sm-8 text-left"> 

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    


<style>
body {font-family: Arial, Helvetica, sans-serif;}

/* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    position: relative;
    background-color: #fefefe;
    margin: auto;
    padding: 0;
    border: 1px solid #888;
    width: 40%;
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
    -webkit-animation-name: animatetop;
    -webkit-animation-duration: 0.4s;
    animation-name: animatetop;
    animation-duration: 0.4s
}

/* Add Animation */
@-webkit-keyframes animatetop {
    from {top:-300px; opacity:0} 
    to {top:0; opacity:1}
}

@keyframes animatetop {
    from {top:-300px; opacity:0}
    to {top:0; opacity:1}
}

/* The Close Button */
.close,
.close1 {
    color: white;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus,
.close1:hover,
.close1:focus{
    color: #000;
    text-decoration: none;
    cursor: pointer;
}

.modal-header {
    padding: 2px 16px;
    background-color: #999;
    color: white;
}

.modal-body {padding: 2px 16px;}

.modal-footer {
    padding: 2px 16px;
    background-color: #999;
    color: white;
}


</style>



<div id="holiday">
<table border=1 width="50%">
<c:forEach items="${holidayfiles}" var="file">
<tr>
<td class="file-id">${file.id}</td>
<td>${file.name}</td>
<td>${file.fileType}</td>
<td>${file.createdOn}</td>
<td>${file.description}</td>
<td>
<a href="/LeaveWebApp/uploader/api/download?id=${file.id}">Download</a>
</td>
<td>
<button name="deleteFile">Delete</button> 
</td>
</tr>   
</c:forEach>
</table>



<h3>Upload Holiday Calendar (in .csv format)</h3>

<form method="POST" enctype="multipart/form-data" id="holidayFileUploadForm">
    Name : <input type="text" name="name"/><br/><br/>
    File :<input type="file" name="multipartFile"/><br/><br/>
    Description :<input type="text" name="description"/><br/><br/>
    <input type="submit" value="Submit" id="holidayBtnSubmit"/>
</form>


<span id="holidayResult"></span>
</div>


<div id="leavepolicy">
<table border=1 width="50%">
<c:forEach items="${leavepolicyfiles}" var="file">
<tr>
<td>${file.id}</td>
<td>${file.name}</td>
<td>${file.fileType}</td>
<td>${file.createdOn}</td>
<td>${file.description}</td>
<td>
<button name="editButton">Edit</button> 
</td>
<td>
<button name="deleteButton">Delete</button> 
</td>
</tr>   
</c:forEach>
</table>

<h3>Upload Leave Policy</h3>

<form method="POST" enctype="multipart/form-data" id="leavepolicyFileUploadForm">
    Name : <input type="text" name="name"/><br/><br/>
    File :<input type="file" name="multipartFile"/><br/><br/>
    Description :<input type="text" name="description"/><br/><br/>
    <input type="submit" value="Submit" id="leavepolicyBtnSubmit"/>
</form>


<span id="leavepolicyResult"></span>
</div>


<div id="notice">
<table border=1 width="50%">
<c:forEach items="${noticefiles}" var="file">
<tr>
<td>${file.id}</td>
<td>${file.name}</td>
<td>${file.fileType}</td>
<td>${file.createdOn}</td>
<td>${file.description}</td>
<td>
<button name="editButton">Edit</button> 
</td>
<td>
<button name="deleteButton">Delete</button> 
</td>
</tr>   
</c:forEach>
</table>



<h3>Upload Notice</h3>

<form method="POST" enctype="multipart/form-data" id="noticeFileUploadForm">
    Name : <input type="text" name="name"/><br/><br/>
    File :<input type="file" name="multipartFile"/><br/><br/>
    Description :<input type="text" name="description"/><br/><br/>
    <input type="submit" value="Submit" id="noticeBtnSubmit"/>
</form>


<span id="noticeResult"></span>
</div>


<div id="other">
<table border=1 width="50%">
<c:forEach items="${otherfiles}" var="file">
<tr>
<td>${file.id}</td>
<td>${file.name}</td>
<td>${file.fileType}</td>
<td>${file.createdOn}</td>
<td>${file.description}</td>
<td>
<button name="editButton">Edit</button> 
</td>
<td>
<button name="deleteButton">Delete</button> 
</td>
</tr>   
</c:forEach>
</table>


<h3>Upload Notice</h3>

<form method="POST" enctype="multipart/form-data" id="otherFileUploadForm">
    Name : <input type="text" name="name"/><br/><br/>
    File :<input type="file" name="multipartFile"/><br/><br/>
    Description :<input type="text" name="description"/><br/><br/>
    <input type="submit" value="Submit" id="otherBtnSubmit"/>
</form>


<span id="otherResult"></span>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">

$(document).ready(function () {

    $("#holidayBtnSubmit").click(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        holiday_fire_ajax_submit();

    });
    
    $("#leavepolicyBtnSubmit").click(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        leavepolicy_fire_ajax_submit();

    });
    
    $("#noticeBtnSubmit").click(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        notice_fire_ajax_submit();

    });
    
    $("#otherBtnSubmit").click(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        other_fire_ajax_submit();

    });
    
    $("button[name='downloadFile']").on('click', function() {
    	var ele= $(this).closest('tr').find('.file-id');
    	var id = ele.text();
	alert(id);
	     // make ajax call
        if(id){
        	
        	$.ajax({
        		headers: { 
        	        Accept : "text/csv; charset=utf-8",
        	        "Content-Type": "text/csv; charset=utf-8"
        	    },
       	       type: 'GET', // method attribute of form
       	       url: '/LeaveWebApp/uploader/api/download',  // action attribute of form
       	   // convert form data to json format
       	       data : {"id":id},
       	       //success: function(response){
       	    	//$("#holidayResult").text(response);
       	   		// },
       	   		error: function(e){
       	   			$('#holidayResult').html("Error");
       	   		}
       	    });
        	
        }
        else{
         $("#holidayResult").text("Invalid values");
        }
        
});


    $("button[name='deleteFile']").on('click', function() {
    	var ele= $(this).closest('tr').find('.file-id');
    	var id = ele.text();
	alert(id);
	     // make ajax call
        if(id){
        	
        	$.ajax({
       	       type: 'GET', // method attribute of form
       	       url: '/LeaveWebApp/uploader/api/delete',  // action attribute of form
       	   // convert form data to json format
       	       data : {"id":id},
       	       success: function(response){
       	    	$("#holidayResult").text(response);
       	   		},
       	   		error: function(e){
       	   			$('#holidayResult').html("Error");
       	   		}
       	    });
        	
        }
        else{
         $("#holidayResult").text("Invalid values");
        }
        
});

    
    
    
});

function holiday_fire_ajax_submit() {

    // Get form
    var form = $('#holidayFileUploadForm')[0];

    var data = new FormData(form);

    $("#holidayBtnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/LeaveWebApp/uploader/api/upload/holiday",
        data: data,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

            $("#holidayResult").text(data);
            console.log("SUCCESS : ", data);
            $("#holidayBtnSubmit").prop("disabled", false);

        },
        
        error:function (xhr, ajaxOptions, thrownError){
        	//$("#holidayResult").text("File not uploaded due to some exception");
		    if(xhr.status==404) {
		        $('#holidayResult').html("Permitted only after log in");
		    }
		    if(xhr.status=500){
		    	$('#holidayResult').html("File Size exceeded max limit.");
		    }
		    $("#holidayBtnSubmit").prop("disabled", false);

		}
        
    });
}

function leavepolicy_fire_ajax_submit() {

    // Get form
    var form = $('#leavepolicyFileUploadForm')[0];

    var data = new FormData(form);

    $("#leavepolicyBtnSubmit").prop("disabled", true);

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

            $("#leavepolicyResult").text(data);
            console.log("SUCCESS : ", data);
            $("#leavepolicyBtnSubmit").prop("disabled", false);

        },
        error: function (e) {

            $("#leavepolicyResult").text("File not uploaded due to some exception");
            console.log("ERROR : ", e);
            $("#leavepolicyBtnSubmit").prop("disabled", false);

        }
    });
}



function notice_fire_ajax_submit() {

    // Get form
    var form = $('#noticeFileUploadForm')[0];

    var data = new FormData(form);

    $("#noticeBtnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/LeaveWebApp/uploader/api/upload/notice",
        data: data,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

            $("#noticeResult").text(data);
            console.log("SUCCESS : ", data);
            $("#noticeBtnSubmit").prop("disabled", false);

        },
        error: function (e) {

            $("#noticeResult").text("File not uploaded due to some exception");
            console.log("ERROR : ", e);
            $("#noticeBtnSubmit").prop("disabled", false);

        }
    });
}




function other_fire_ajax_submit() {

    // Get form
    var form = $('#otherFileUploadForm')[0];

    var data = new FormData(form);

    $("#otherBtnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/LeaveWebApp/uploader/api/upload/other",
        data: data,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

            $("#otherResult").text(data);
            console.log("SUCCESS : ", data);
            $("#otherBtnSubmit").prop("disabled", false);

        },
        error: function (e) {

            $("#otherResult").text("File not uploaded due to some exception");
            console.log("ERROR : ", e);
            $("#otherBtnSubmit").prop("disabled", false);

        }
    });
}



</script>

</div>