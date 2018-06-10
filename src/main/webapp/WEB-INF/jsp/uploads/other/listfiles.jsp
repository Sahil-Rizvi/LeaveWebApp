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

<div id="other" class="">
<c:if test="${not empty otherfiles}">
<table class="table table-hover">
<thead>
<tr>
<th>Id</th>
<th>Name</th>
<th>Type</th>
<th>Created On</th>
<th>Description</th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<c:forEach items="${otherfiles}" var="file">
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
</c:if>
<c:if test="${empty otherfiles}">
 No file found
</c:if>
<button id="uploadButton">Upload</button> 
<!-- The Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close">&times;</span>
      <h2>Upload Other File</h2>
    </div>
    <div class="modal-body">
      <div id="new1"></div>
     <!-- <p>Some other text...</p> -->
    </div>
    <div class="modal-footer">
      <h3>Modal Footer</h3>
    </div>
  </div>

</div>


<span id="otherResult"></span>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">

$(document).ready(function () {

	

	var modal = document.getElementById('myModal');
	

	//Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	// When the user clicks on <span> (x), close the modal
span.onclick = function() {
 		modal.style.display = "none";
}


//When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
	if (event.target == modal) {
        modal.style.display = "none";
    }
}

	
    
    $("#uploadButton").on('click', function() {	
        	$.ajax({
       	       type: 'GET', // method attribute of form
       	       url: '/LeaveWebApp/uploader/api/uploadform/other',  // action attribute of form
       	       success: function(response){
       	    	$('#new1').html(response);
    			modal.style.display = "block";
       	   		},
       	   		error: function(e){
       	   		$('#new1').html(e);
    			modal.style.display = "block";
       	   		}
       	    });
	});
	


    $("button[name='deleteFile']").on('click', function() {
    	var ele= $(this).closest('tr').find('.file-id');
    	var id = ele.text();
	     // make ajax call
        if(id){
        	
        	$.ajax({
       	       type: 'GET', // method attribute of form
       	       url: '/LeaveWebApp/uploader/api/delete',  // action attribute of form
       	   // convert form data to json format
       	       data : {"id":id},
       	       success: function(response){
       	    	$("#otherResult").text(response);
       	   		},
       	   		error: function(e){
       	   			$('#otherResult').html("Error");
       	   		}
       	    });
        	
        }
        else{
         $("#otherResult").text("Invalid values");
        }
        
});

    
});




</script>

</div>