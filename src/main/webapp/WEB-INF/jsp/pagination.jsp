<div id="whole">
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
<div id = "table">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="firstUrl" value="/leave/getAllPaginatedLeavesForUpdationForManager/1" />
<c:url var="lastUrl" value="/leave/getAllPaginatedLeavesForUpdationForManager/${pages.totalPages}" />
<c:url var="prevUrl" value="/leave/getAllPaginatedLeavesForUpdationForManager/${currentIndex - 1}" />
<c:url var="nextUrl" value="/leave/getAllPaginatedLeavesForUpdationForManager/${currentIndex + 1}" />
<table border=1 width="50%">
<tr>
<th>LEAVEID</th>
<th>EMPLOYEEID</th>
<th>MANAGERID</th>
<th>CURRENTLY WITH</th>
<th>FROM</th>
<th>TO</th>
<th>TYPE</th>
<th>STATUS</th>
</tr>

<c:forEach items="${pages.content}" var="leave">
<tr>
<td>${leave.leaveId}</td>
<td>${leave.employeeEntity}</td>
<td>${leave.managerEntity}</td>
<td>${leave.currentlyManager}</td>
<td>${leave.fromDate}</td>
<td>${leave.toDate}</td>
<td>${leave.leaveType}</td>
<td>${leave.leaveStatus}</td>
</tr>   
</c:forEach>
</table>

<div class="center"> 
<ul class="pagination">

        <c:choose>
            <c:when test="${currentIndex == 1}">
                <li class="disabled"><button>&lt;&lt;</button></li>
                <li class="disabled"><button>&lt;</button></li>
            </c:when>
            <c:otherwise>
                <li><button id="first">&lt;&lt;</button></li>
                <li><button id="prev">&lt;</button></li>
            </c:otherwise>
        </c:choose>

        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="active"><button><c:out value="${i}" /></button></li>
                </c:when>
                <c:otherwise>
                    <li><button name="getContent" id="${i}"><c:out value="${i}" /></button></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        
        <c:choose>
            <c:when test="${currentIndex == pages.totalPages}">
                <li class="disabled"><button>&gt;</button>
                <li class="disabled"><button>&gt;&gt;</button>
            </c:when>
            <c:otherwise>
                <li><button id="next">&gt;</button></li>
                <li><button id="last">&gt;&gt;</button></li>
            </c:otherwise>
        </c:choose>
        
</ul>
</div>
 





<!-- The Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close">&times;</span>
      <h2>Modal Header</h2>
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


</div> 


<script src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script>



$(document).ready(function () {
	
	
	
	var firstUrl = "${firstUrl}";
	var lastUrl = "${lastUrl}";
	var prevUrl = "${prevUrl}";
	var nextUrl = "${nextUrl}";
	var baseUrl = "/LeaveWebApp/leave/getAllPaginatedLeavesForUpdationForManager/";
	
/*	
	
	var modal = document.getElementById('myModal');

	// Get the button that opens the modal
//	var btn = document.getElementsByName("hisTeam")[0];

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	*/

	$('button[id="first"]').click(function(){
		$.ajax({
			type: "GET",
			url: firstUrl, 
			success: function(response){
				$('#whole').html(response);
			},
			error: function(e){
				alert(e);
			}
			}); 
		}); 
	
	$('button[id="prev"]').click(function(){
		$.ajax({
			type: "GET",
			url: prevUrl, 
			success: function(response){
				$('#whole').html(response);
			},
			error: function(e){
				alert(e);
			}
			});
		}); 
	
	$('button[id="next"]').click(function(){
		$.ajax({
			type: "GET",
			url: nextUrl, 
			success: function(response){
				$('#whole').html(response);
			},
			error: function(e){
				alert(e);
			}
			});
		}); 
	
	$('button[id="last"]').on('click',function(){
		$.ajax({
			type: "GET",
			url: lastUrl, 
			success: function(response){
				$('#whole').html(response);
			},
			error: function(e){
				alert(e);
			}
			}); 
		}); 
	
	$('button[name="getContent"]').on('click',function(){
		var pageUrl = baseUrl+this.id;
		$.ajax({
			type: "GET",
			url: pageUrl,
			success: function(response){
				$('#whole').html(response);
			},
			error: function(e){
				alert(e);
			}
			}); 
		}); 
	
	/*
	
	$('#leavecheck').on('click', function() {

		var checkboxValueArray=[];
		var checkboxNameArray=[];
		
		$( "input[type=radio]:checked" ).each(function(){
			checkboxNameArray.push($(this).attr("name"));
			checkboxValueArray.push($(this).val());
		});
		
		
		$.ajax({
		type: "POST",
		url: "/LeaveWebApp/leave/approveOrReject",
		data: {
		           "firstArray": checkboxNameArray,
		           "secondArray": checkboxValueArray,       
		},  
		success: function(response){
			$('#info').html(response.message);
		},
		error: function(e){
			$('#info').html(e);
		}
		});
	});
	

	

	// When the user clicks the button, open the modal 
	$('[name="hisTeam"]').on('click', function() {
		var emp = this.id;
		$.ajax({
		type: "GET",
		url: "/LeaveWebApp/leave/getAllLeavesOfSpecificEmployeeTeamMates", 
		data:{"emp":emp},
		success: function(response){
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
</div>