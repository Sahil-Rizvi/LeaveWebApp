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
.close1,
.close2,
.close3 {
    color: white;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close1:hover,
.close1:focus,
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
<div class="table-responsive">
<table class="table table-hover">
<tr>
<th>YEAR</th>
<th>SICK COUNT</th>
<th>CASUAL COUNT</th>
<th>PRIVILEGED COUNT</th>
<th>B'DAY COUNT</th>
<th></th>
<th></th>
</tr>
<c:forEach items="${lc}" var="lc" > 
<tr>
<td id="${lc.year}" class="lc-year">${lc.year}</td>
<td>${lc.sickCount}</td>
<td>${lc.casualCount}</td>
<td>${lc.privilegedCount}</td>
<td>${lc.bdayCount}</td>
<td>
<button name="deleteButton">Delete</button> 
</td>
</tr>
</c:forEach>
</table> 
</div>
<div id="info"></div>
</c:if>
<c:if test="${not empty message}">
 ${message}
</c:if>
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


var modal3 = document.getElementById('myModal3');


var span3 = document.getElementsByClassName("close3")[0];


span3.onclick = function() {
	modal3.style.display = "none";
}


//When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
if (event.target == modal3) {
	 modal3.style.display = "none";
}
}

var ele1="";
$("button[name='deleteButton']").on('click', function() {
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