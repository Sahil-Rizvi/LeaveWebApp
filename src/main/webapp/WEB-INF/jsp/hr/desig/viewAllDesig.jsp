
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


<c:if test="${not empty desig}">
<table class="table table-hover">
<tr>
<th>DESIGNATION NAME</th>
<th></th>
<th></th>
</tr>
<c:forEach items="${desig}"  var="d" > 
<tr>
<td id="${d.id}" class="desig-name">${d.name}</td>
<td>
<button name="editButton">Edit</button> 
</td>
<td>
<button name="deleteButton">Delete</button> 
</td>
</tr>
</c:forEach>
</table> 
<div id="info"></div>
</c:if>

<c:if test="${not empty message}">
 ${message}
</c:if>



<!-- The Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close">&times;</span>
      <h2>Edit Designation</h2>
    </div>
    <div class="modal-body" id="modal-body1">
     
<div id="editForm" >
		<p><label for="oldDesig">Old Name:</label>
  		<input type="text" id="oldDesig" value="" readonly="readonly"/><br></p>
  		<p><label for="newDesig">New Name:</label>
  		<input type="text" id="newDesig" value=""/><br></p>
  		<button name="editSubmit">Submit</button>
                <div id="editResp"></div>
</div>


    </div>
    <div class="modal-footer">
      <h3></h3>
    </div>
  </div>

</div>



<!-- The Modal -->
<div id="myModal1" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close1">&times;</span>
      <h2>Delete Designation</h2>
    </div>
    <div class="modal-body" id="modal-body2">

<div id="deleteForm" >
                <p> Do you really want to delete it ?</p>
  		<p><button name="deleteYes">Yes</button>
		<button name="deleteNo">No</button></p>
                <div id="deleteResp"></div>
</div>

    </div>
    <div class="modal-footer">
      <h3></h3>
    </div>
  </div>

</div>


<script type="text/javascript">

var modal = document.getElementById('myModal');

var modal1 = document.getElementById('myModal1');

//Get the button that opens the modal
var btn = document.getElementsByName("editButton")[0];

//Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

var span1 = document.getElementsByClassName("close1")[0];

//When the user clicks on <span> (x), close the modal
span.onclick = function() {
 modal.style.display = "none";
 if(document.getElementById("newDesig")){
 	document.getElementById("newDesig").value = "";
 }
 if(document.getElementById("oldDesig")){
 	document.getElementById("oldDesig").value = "";
 }
 $("#editResp").text("");
}


span1.onclick = function() {
 modal1.style.display = "none";
 $("#deleteResp").text("");
}




//When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
 if (event.target == modal) {
     modal.style.display = "none";
  if(document.getElementById("newDesig")){
 	document.getElementById("newDesig").value = "";
  }
 if(document.getElementById("oldDesig")){
 	document.getElementById("oldDesig").value = "";
  }
   $("#editResp").text("");
 }
 if (event.target == modal1) {
     modal1.style.display = "none";
     $("#deleteResp").text("");
 }
}


var ele = "";


$("button[name='editButton']").on('click', function() {
	ele= $(this).closest('tr').find('.desig-name');
	var id = ele.attr('id');
	var name = ele.text();
	var form = document.getElementById("editForm");
         form.style.display="block";	
        if(document.getElementById("oldDesig")){
 	document.getElementById("oldDesig").value = name;
	}
	modal.style.display = "block";
});



$("button[name='editSubmit']").on('click', function() {
        var id = ele.attr('id');
	var oldDesignation = ele.text();
        var newDesignation = "";
        if(document.getElementById("newDesig")){
 	newDesignation=document.getElementById("newDesig").value;
 	}
        // make ajax call
 if(oldDesignation && newDesignation){
        	
        	$.ajax({
       	       type: 'POST', // method attribute of form
       	       url: '/LeaveWebApp/customAdmin/desig/edit',  // action attribute of form
       	   // convert form data to json format
       	       data : {"id":id,"oldDesignation":oldDesignation,"newDesignation":newDesignation},
       	       success: function(response){
       	    	$("#editResp").text(response);
       	   		},
       	   		error: function(e){
       	   			$('#editResp').html("Error");
       	   		}
       	    });
        	
        }
        else{
         $("#editResp").text("Invalid values");
        }
        document.getElementById("newDesig").value = "";

});

var ele1 ="";
$("button[name='deleteButton']").on('click', function() {
	ele1= $(this).closest('tr').find('.desig-name');
	var alertForm =document.getElementById("deleteForm");
	alertForm.style.display="block";
       	modal1.style.display = "block";
});




$("button[name='deleteYes']").on('click', function() {
	var id = ele1.attr('id');
	var name = ele1.text();
	if(id && name){
    	
    	$.ajax({
   	       type: 'POST', // method attribute of form
   	       url: '/LeaveWebApp/customAdmin/desig/delete',  // action attribute of form
   	   // convert form data to json format
   	       data : {"id":id,"designation":name},
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
   modal1.style.display = "none";
 $("#deleteResp").text("");     
});

</script>
     
      
</div>


