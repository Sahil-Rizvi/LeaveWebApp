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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<h2>Emp Code : ${employee.employeeCode}</h2>
	<button id="editEmp">Edit Employee</button>
	<button id="deleteEmp">Delete Employee</button>
<div class="container1">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#empdetails">Employee Details</a></li>
    	<li class="dropdown">
      		<a class="dropdown-toggle" data-toggle="dropdown" href="#leaves">Leaves<span class="caret"></span></a>
      		<ul class="dropdown-menu">
        	<li><a data-toggle="tab" href="#pendingleaves">Pending Leaves</a></li>
        	<li><a data-toggle="tab" href="#rejectedleaves">Rejected Leaves</a></li>
        	<li><a data-toggle="tab" href="#approvedleaves">Approved Leaves</a></li>                        
      		</ul>
    	</li>
    	<li class="dropdown">
      		<a class="dropdown-toggle" data-toggle="dropdown" href="#compoffs">Comp Offs<span class="caret"></span></a>
      		<ul class="dropdown-menu">
        	<li><a data-toggle="tab" href="#pendingcompoffs">Pending Comp-Offs</a></li>
        	<li><a data-toggle="tab" href="#rejectedcompoffs">Rejected Comp-Offs</a></li>
        	<li><a data-toggle="tab" href="#approvedcompoffs">Approved Comp-Offs</a></li>                        
      		</ul>
    	</li>
	</ul>
	<div class="tab-content">
		<div id="empdetails" class="tab-pane fade in active">
		<c:if test="${not empty message}">
		  ${message}
		</c:if>
		<c:if test="${empty message}">
		
		  <div class="table-responsive">
		  			<h3>EMPLOYEE DETAILS</h3>          
  <table class="table table-hover">
    <tbody>
      <tr>
        <td>Code</td>
        <td>${employee.employeeCode}</td>
      </tr>
      <tr>
        <td>Name</td>
        <td>${employee.employeeName}</td>
      </tr>
      <tr>
        <td>Designation</td>
        <td>${employee.designation}</td>
      </tr>
      <tr>
        <td>Department</td>
        <td>${employee.department}</td>
      </tr>
      <tr>
        <td>D.O.B.</td>
        <td>${employee.dateofBirth}</td>
      </tr>
      <tr>
        <td>Joining Date</td>
        <td>${employee.dateofJoining}</td>
      </tr>
      <tr>
        <td>Email</td>
        <td>${employee.contact.emailId}</td>
      </tr>
      <tr>
        <td>Phone No.</td>
        <td>${employee.contact.phoneNo}</td>
      </tr>
      <tr>
        <td>Manager's code</td>
        <td>${employee.managerCode}</td>
      </tr>
      <tr>
        <td>Manager's name</td>
        <td>${employee.managerName}</td>
      </tr>
    </tbody>
  </table>
  </div>
<div class="table-responsive">        
			<h3>LEAVE COUNT</h3>  
<table class="table table-hover">
<thead>
<tr>
<th>SICK</th>
<th>CASUAL</th>
<th>PRIVILEGED</th>
<th>B'DAY</th>
<th>COMP-OFF</th>
</tr>
</thead>
<tbody>
<tr>
<td>${employee.leaveCount.sickCount}</td>
<td>${employee.leaveCount.casualCount}</td>
<td>${employee.leaveCount.privilegedCount}</td>
<td>${employee.leaveCount.bdayCount}</td>
<td>${employee.leaveCount.compOffCount}</td>
</tr>   
</tbody>
</table>
</div>
		
		
		</c:if>
		</div>
		<div id="pendingleaves" class="tab-pane fade">
			<h3>PENDING LEAVES</h3>
			<c:if test="${not empty employee.pendingLeaves}">
				<div class="table-responsive">
				<table class="table table-hover">
					<tr>
						<th>LEAVEID</th>
						<th>FROM</th>
						<th>TO</th>
						<th>APPLIED ON</th>
						<th>PENDING WITH (EMPCODE)</th>
						<th>PENDING WITH (NAME)</th>
						<th>PENDING WITH (DEPT.)</th>
						<th>LEAVE TYPE</th>
						<th>NO. OF DAYS</th>
					</tr>
					<c:forEach items="${employee.pendingLeaves}" var="leave">
						<tr>
							<td>${leave.leaveId}</td>
							<td>${leave.fromDate}</td>
							<td>${leave.toDate}</td>
							<td>${leave.appliedOn}</td>
							<td>${leave.pendingWith.empCode}</td>
							<td>${leave.pendingWith.empName}</td>
							<td>${leave.pendingWith.empDepartment}</td>
							<td>${leave.leaveType}</td>
							<td>${leave.noOfDays}
						</tr>
					</c:forEach>
				</table>
				</div>
			</c:if>
			<c:if test="${empty employee.pendingLeaves}">
 				No Pending Leave Is Available
			</c:if>
		</div>
		<div id="rejectedleaves" class="tab-pane fade">
			<h3>REJECTED LEAVES</h3>
			<c:if test="${not empty employee.rejectedLeaves}">
				<div class="table-responsive">
				<table class="table table-hover">
					<tr>
						<th>LEAVEID</th>
						<th>FROM</th>
						<th>TO</th>
						<th>APPLIED ON</th>
						<th>REJECTED ON</th>
						<th>REJECTED BY (EMPCODE)</th>
						<th>REJECTED BY (NAME)</th>
						<th>REJECTED BY (DEPT.)</th>
						<th>LEAVE TYPE</th>
						<th>NO. OF DAYS</th>
					</tr>
					<c:forEach items="${employee.rejectedLeaves}" var="leave">
						<tr>
							<td>${leave.leaveId}</td>
							<td>${leave.fromDate}</td>
							<td>${leave.toDate}</td>
							<td>${leave.appliedOn}</td>
							<td>${leave.rejectedOn}</td>
							<td>${leave.rejectedBy.empCode}</td>
							<td>${leave.rejectedBy.empName}</td>
							<td>${leave.rejectedBy.empDepartment}</td>
							<td>${leave.leaveType}</td>
							<td>${leave.noOfDays}
						</tr>
					</c:forEach>
				</table>
				</div>
			</c:if>
			<c:if test="${empty employee.rejectedLeaves}">
 				No Rejected Leave Is Available
			</c:if>
		</div>
		<div id="approvedleaves" class="tab-pane fade">
			<h3>APPROVED LEAVES</h3>
			<c:if test="${not empty employee.approvedLeaves}">
				<div class="table-responsive">
				<table class="table table-hover">
					<tr>
						<th>LEAVEID</th>
						<th>FROM</th>
						<th>TO</th>
						<th>APPLIED ON</th>
						<th>APPROVED ON</th>
						<th>APPROVED BY (EMPCODE)</th>
						<th>APPROVED BY (NAME)</th>
						<th>APPROVED BY (DEPT.)</th>
						<th>LEAVE TYPE</th>
						<th>NO. OF DAYS</th>
					</tr>
					<c:forEach items="${employee.approvedLeaves}" var="leave">
						<tr>
							<td>${leave.leaveId}</td>
							<td>${leave.fromDate}</td>
							<td>${leave.toDate}</td>
							<td>${leave.appliedOn}</td>
							<td>${leave.approvedOn}</td>
							<td>${leave.approvedBy.empCode}</td>
							<td>${leave.approvedBy.empName}</td>
							<td>${leave.approvedBy.empDepartment}</td>
							<td>${leave.leaveType}</td>
							<td>${leave.noOfDays}
						</tr>
					</c:forEach>
				</table>
				</div>
			</c:if>
			<c:if test="${empty employee.approvedLeaves}">
 				No Approved Leave Is Available
			</c:if>
		</div>
		<div id="pendingcompoffs" class="tab-pane fade">
			<h3>PENDING COMP-OFFS</h3>
			<c:if test="${not empty employee.pendingCompOffs}">
				<div class="table-responsive">
				<table class="table table-hover">
					<tr>
						<th>COMP-OFF ID</th>
						<th>APPLIED ON</th>
						<th>FOR DATE</th>
						<th>PENDING WITH (EMPCODE)</th>
						<th>PENDING WITH (NAME)</th>
						<th>PENDING WITH (DEPT.)</th>
					</tr>
					<c:forEach items="${employee.pendingCompOffs}" var="compOff">
						<tr>
							<td>${compOff.compOffId}</td>
							<td>${compOff.appliedOn}</td>
							<td>${compOff.forDate}</td>
							<td>${compOff.pendingWith.empCode}</td>
							<td>${compOff.pendingWith.empName}</td>
							<td>${compOff.pendingWith.empDepartment}</td>
						</tr>
					</c:forEach>
				</table>
				</div>
			</c:if>
			<c:if test="${empty employee.pendingCompOffs}">
 				No Pending CompOff Is Available
			</c:if>		
			</div>
			<div id="rejectedcompoffs" class="tab-pane fade">
			<h3>REJECTED COMP-OFFS</h3>
			<c:if test="${not empty employee.rejectedCompOffs}">
				<div class="table-responsive">
				<table class="table table-hover">
					<tr>
						<th>COMP-OFF ID</th>
						<th>APPLIED ON</th>
						<th>FOR DATE</th>
						<th>REJECTED ON</th>
						<th>REJECTED BY (EMPCODE)</th>
						<th>REJECTED BY (NAME)</th>
						<th>REJECTED BY (DEPT.)</th>
					</tr>
					<c:forEach items="${employee.rejectedCompOffs}" var="compOff">
						<tr>
							<td>${compOff.compOffId}</td>
							<td>${compOff.appliedOn}</td>
							<td>${compOff.forDate}</td>
							<td>${compOff.rejectedOn}</td>
							<td>${compOff.rejectedBy.empCode}</td>
							<td>${compOff.rejectedBy.empName}</td>
							<td>${compOff.rejectedBy.empDepartment}</td>
						</tr>
					</c:forEach>
				</table>
				</div>
			</c:if>
			<c:if test="${empty employee.rejectedCompOffs}">
 				No Rejected CompOff Is Available
			</c:if>		
		</div>
		<div id="approvedcompoffs" class="tab-pane fade">
			<h3>APPROVED COMP-OFFS</h3>
			<c:if test="${not empty employee.approvedCompOffs}">
				<div class="table-responsive">
				<table class="table table-hover">
					<tr>
						<th>COMP-OFF ID</th>
						<th>APPLIED ON</th>
						<th>FOR DATE</th>
						<th>APPROVED ON</th>
						<th>APPROVED BY (EMPCODE)</th>
						<th>APPROVED BY (NAME)</th>
						<th>APPROVED BY (DEPT.)</th>
					</tr>
					<c:forEach items="${employee.approvedCompOffs}" var="compOff">
						<tr>
							<td>${compOff.compOffId}</td>
							<td>${compOff.appliedOn}</td>
							<td>${compOff.forDate}</td>
							<td>${compOff.approvedOn}</td>
							<td>${compOff.approvedBy.empCode}</td>
							<td>${compOff.approvedBy.empName}</td>
							<td>${compOff.approvedBy.empDepartment}</td>
						</tr>
					</c:forEach>
				</table>
				</div>
			</c:if>
			<c:if test="${empty employee.approvedCompOffs}">
 				No Approved CompOff Is Available
			</c:if>		
		</div>
	</div>
	
	
<!-- The Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close">&times;</span>
      <h2>Edit Employee</h2>
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

<!-- The Modal -->
<div id="myModal1" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close1">&times;</span>
      <h2>Delete Employee</h2>
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





	
	
	<script>
	
	
	var modal = document.getElementById('myModal');

	var modal1 = document.getElementById('myModal1');
	
	//Get the button that opens the modal
	var btn = document.getElementById('editEmp');

	//Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	var span1 = document.getElementsByClassName("close1")[0];
	

	// When the user clicks on <span> (x), close the modal
span.onclick = function() {
 		modal.style.display = "none";
}


span1.onclick = function() {
 modal1.style.display = "none";
}

//When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
	if (event.target == modal) {
        modal.style.display = "none";
    } if (event.target == modal1) {
     modal1.style.display = "none";
 }
}

	
	$("#editEmp").on('click', function() {
        var id = "${employee.employeeCode}";
       	if(id){
        	
        	$.ajax({
       	       type: 'GET', // method attribute of form
       	       url: '/LeaveWebApp/hr/updateEmployee',  // action attribute of form
       	   // convert form data to json format
       	       data : {"id":id},
       	       success: function(response){
       	    	$('#new1').html(response);
    			modal.style.display = "block";
       	   		},
       	   		error: function(e){
       	   		$('#new1').html(e);
    			modal.style.display = "block";
       	   		}
       	    });
        	
        }
        else{
         alert("Invalid Employee Code");
        }
	});
	
	
	
	$("#deleteEmp").on('click', function() {
		ele1= $(this).closest('tr').find('.dept-name');    
		var alertForm =document.getElementById("deleteForm");
		alertForm.style.display="block";
	       	modal1.style.display = "block";
	});




	$("button[name='deleteYes']").on('click', function() {
		var id = "${employee.employeeCode}";
		
		if(id){
	    	
	    	$.ajax({
	   	       type: 'POST', // method attribute of form
	   	       url: '/LeaveWebApp/hr/deleteEmp',  // action attribute of form
	   	   // convert form data to json format
	   	       data : {"id":id},
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
