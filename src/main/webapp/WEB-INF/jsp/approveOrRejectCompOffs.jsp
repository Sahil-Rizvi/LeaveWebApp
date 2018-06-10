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
    width: 80%;
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
.close {
    color: white;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}

.modal-header {
    padding: 2px 16px;
    background-color: #5cb85c;
    color: white;
}

.modal-body {padding: 2px 16px;}

.modal-footer {
    padding: 2px 16px;
    background-color: #5cb85c;
    color: white;
}
</style>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table border=1 width="50%">
<tr>
<th>COMPOFFID</th>
<th>EMPLOYEEID</th>
<th>MANAGERID</th>
<th>CURRENTLY WITH</th>
<th>APPLIED ON</th>
<th>FOR DATE</th>
<th>STATUS</th>
</tr>

<c:forEach items="${compoffs}" var="compoff">
<tr>
<td>${compoff.compOffId}</td>
<td id="emp">${compoff.employeeEntity}</td>
<td>${compoff.managerEntity}</td>
<td>${compoff.currentlyManager}</td>
<td>${compoff.appliedOn}</td>
<td>${compoff.forDate}</td>
<td>${compoff.compOffStatus}</td>
<td>
<input type="radio" name="${compoff.compOffId}" value="APPROVED"/>Approve
</td>
<td>
<input type="radio" name="${compoff.compOffId}" value="REJECTED"/>Reject
</td>
<td>
<textarea name="${compoff.compOffId}" ></textarea>
</td>
</tr>   
</c:forEach>
</table>

 <button id="compOffCheck">Submit</button>
 
<div id="info">${message}</div>





<!-- The Modal -->
 
<!--  <div id="myModal" class="modal">-->

  <!-- Modal content -->
  <!--<div class="modal-content"> -->
     <!--<div class="modal-header"> -->
      <!--<span class="close">&times;</span> -->
      <!--<h2>Modal Header</h2> -->
    <!--</div> -->
    <!--<div class="modal-body"> -->
      <!--<div id="new1"></div> -->
      <!--<p>Some other text...</p> -->
    <!--</div> -->
    <!--<div class="modal-footer"> -->
      <!--<h3>Modal Footer</h3> -->
    <!--</div> -->
  <!--</div> -->

<!--</div> -->



<script src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script>



$(document).ready(function () {

	//var modal = document.getElementById('myModal');

	// Get the button that opens the modal
//	var btn = document.getElementsByName("hisTeam")[0];

	// Get the <span> element that closes the modal
	//var span = document.getElementsByClassName("close")[0];

	
	$('#compOffCheck').on('click', function() {

		var checkboxValueArray=[];
		var checkboxNameArray=[];
		var textareaReasonArray=[];
		$( "input[type=radio]:checked" ).each(function(){
			checkboxNameArray.push($(this).attr("name"));
			checkboxValueArray.push($(this).val());
			var name=$(this).attr("name");
		    if(!$.trim($("textarea[name='"+name+"'").val()))
			 textareaReasonArray.push(" ");
		    else
		     textareaReasonArray.push($.trim($("textarea[name='"+name+"'").val()));

		});
		
		
		$.ajax({
		type: "POST",
		url: "/LeaveWebApp/compOff/approveOrReject",
		data: {
		           "firstArray": checkboxNameArray,
		           "secondArray": checkboxValueArray,
		           "reasonArray":textareaReasonArray,
		},  
		success: function(response){
			$('#info').html(response.message);
		},
		error: function(e){
			$('#info').html(e);
		}
		});
	});
	
	/*

	// When the user clicks the button, open the modal 
	$('[name="hisTeam"]').on('click', function() {
		alert('clicked');
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leave/getAllLeavesOfSpecificEmployeeTeamMates", 
		data:{"emp":$("#emp").text()},
		success: function(response){
			alert(response);
			$('#new1').html(response);
			modal.style.display = "block";
		},
		error: function(e){
			$('#new1').html(e);
		}
		});
	});

	

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
	    modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}
*/

});

</script>