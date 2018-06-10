<div class="col-sm-8 text-left"> 
      
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
.close2,
.close3 {
    color: white;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close2:hover,
.close2:focus
.close3:hover,
.close3:focus{
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




<c:if test="${not empty lc}">
<div>
<table class="table table-hover">
<thead>
<tr>
<th>YEAR</th>
<th>SICK COUNT</th>
<th>CASUAL COUNT</th>
<th>PRIVILEGED COUNT</th>
<th>B'DAY COUNT</th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<tr>
<td id="${lc.year}" class="lc-year">${lc.year}</td>
<td>${lc.sickCount}</td>
<td>${lc.casualCount}</td>
<td>${lc.privilegedCount}</td>
<td>${lc.bdayCount}</td>
<td>
<button id="editButton" name="editButton">Edit</button> 
</td>
<td>
<button id="deleteButton" name="deleteButton">Delete</button> 
</td>
</tr>
</tbody>
</table> 
</div>
</c:if>
<c:if test="${not empty message}">
 ${message}
</c:if>




<!-- The Modal -->
<div id="myModal2" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close2">&times;</span>
      <h2>Edit LeaveCount</h2>
    </div>
    <div class="modal-body" id="modal-body2">
      <div id="new2"></div>
     <!-- <p>Some other text...</p> -->
    </div>
    <div class="modal-footer">
      <h3>Modal Footer</h3>
    </div>
  </div>

</div>


<!-- The Modal -->
<div id="myModal3" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close3">&times;</span>
      <h2>Delete LeaveCount</h2>
    </div>
    <div class="modal-body" id="modal-body3">
<div id="deleteForm" >
                <p> Do you really want to delete it ?</p>
  		<p><button name="deleteYes">Yes</button>
		<button name="deleteNo">No</button></p>
                <div id="deleteResp"></div>
</div>

    </div>
    <div class="modal-footer">
      <h3>Modal Footer</h3>
    </div>
  </div>

</div>




<script type="text/javascript">



var modal2 = document.getElementById('myModal2');

var modal3 = document.getElementById('myModal3');


//Get the button that opens the modal
var editBtn = document.getElementsByName("editButton")[0];

var deleteBtn = document.getElementsByName("deleteButton")[0];


//Get the <span> element that closes the modal

var span2 = document.getElementsByClassName("close2")[0];

var span3 = document.getElementsByClassName("close3")[0];


// When the user clicks on <span> (x), close the modal

span2.onclick = function() {
modal2.style.display = "none";
}

span3.onclick = function() {
	modal3.style.display = "none";
	$("#deleteResp").text("");
}


//When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
if (event.target == modal2) {
 modal2.style.display = "none";
}
if (event.target == modal3) {
	 modal3.style.display = "none";
	 $("#deleteResp").text("");
}
}


$("#editButton").on('click', function() {
	var ele= $(this).closest('tr').find('.lc-year');
	var year = ele.attr('id');
   	if(year){
    	$.ajax({
   	       type: 'GET', // method attribute of form
   	       url: '/LeaveWebApp/leavecount/update',  // action attribute of form
   	   // convert form data to json format
   	       data : {"year":year},
   	       success: function(response){
   	    	$('#new2').html(response);
			modal2.style.display = "block";
   	   		},
   	   		error: function(e){
   	   		$('#new2').html(e);
			modal2.style.display = "block";
   	   		}
   	    });
    	
    }
    else{
     alert("Invalid Year");
    }
});


var ele1="";
$("#deleteButton").on('click', function() {
	ele1= $(this).closest('tr').find('.lc-year');    
	var alertForm =document.getElementById("deleteForm");
	alertForm.style.display="block";
    modal3.style.display = "block";
});




$("button[name='deleteYes']").on('click', function() {
	
	if(ele1 && ele1.attr('id')){
    	
    	$.ajax({
   	       type: 'POST', // method attribute of form
   	       url: '/LeaveWebApp/leavecount/delete',  // action attribute of form
   	   // convert form data to json format
   	       data : {"year":ele1.attr('id')},
   	       success: function(response){
   	    	$("#deleteResp").text(response);
   	   		},
   	   		error: function(e){
   	   			$('#deleteResp').html("Error");
   	   		}
   	    });
    	
    }
    else{
     $("#deleteResp").text("Invalid values");
    }
    
});

$("button[name='deleteNo']").on('click', function() {
   modal3.style.display = "none";
 $("#deleteResp").text("");     
});


    </script>
     
      
</div>








