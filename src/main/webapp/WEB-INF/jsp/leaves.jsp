<div id="whole1">
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
<c:url var="firstUrl1" value="/leave/getAllLeavesOfSpecificEmployeeTeamMates/1" />
<c:url var="lastUrl1" value="/leave/getAllLeavesOfSpecificEmployeeTeamMates/${pages1.totalPages}" />
<c:url var="prevUrl1" value="/leave/getAllLeavesOfSpecificEmployeeTeamMates/${currentIndex1 - 1}" />
<c:url var="nextUrl1" value="/leave/getAllLeavesOfSpecificEmployeeTeamMates/${currentIndex1 + 1}" />
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

<c:forEach items="${pages1.content}" var="leave">
<tr>
<td>${leave.leaveId}</td>
<td>${leave.fromDate}</td>
<td>${leave.toDate}</td>
<td>${leave.appliedOn}</td>
<td>${leave.leaveType}</td>
</tr>   
</c:forEach>
</table>

<c:if test="${pages1.totalPages}>0">
<div class="center"> 
<ul class="pagination">

        <c:choose>
            <c:when test="${currentIndex1 == 1}">
                <li class="disabled"><button>&lt;&lt;</button></li>
                <li class="disabled"><button>&lt;</button></li>
            </c:when>
            <c:otherwise>
                <li><button id="first1">&lt;&lt;</button></li>
                <li><button id="prev1">&lt;</button></li>
            </c:otherwise>
        </c:choose>

        <c:forEach var="i" begin="${beginIndex1}" end="${endIndex1}">
            <c:choose>
                <c:when test="${i == currentIndex1}">
                    <li class="active"><button><c:out value="${i}" /></button></li>
                </c:when>
                <c:otherwise>
                    <li><button name="getContent1" id="${i}"><c:out value="${i}" /></button></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        
        <c:choose>
            <c:when test="${currentIndex1 == pages1.totalPages}">
                <li class="disabled"><button>&gt;</button>
                <li class="disabled"><button>&gt;&gt;</button>
            </c:when>
            <c:otherwise>
                <li><button id="next1">&gt;</button></li>
                <li><button id="last1">&gt;&gt;</button></li>
            </c:otherwise>
        </c:choose>
        
</ul>
</div>
</c:if>





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
	
	
	
	firstUrl = "${firstUrl}";
    lastUrl = "${lastUrl}";
	prevUrl = "${prevUrl}";
    nextUrl = "${nextUrl}";
	
/*	
	
	var modal = document.getElementById('myModal');

	// Get the button that opens the modal
//	var btn = document.getElementsByName("hisTeam")[0];

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	*/

	if ("${pages.totalPages}">0){
		
	$('button[id="first"]').click(function(){
		alert("other first");
		$.ajax({
			type: "GET",
			url: firstUrl, 
			data:{"emp":emp},
			success: function(response){
				alert(response);
				$('#whole1').html(response);
			},
			error: function(e){
				alert(e);
			}
			}); 
		}); 
	
	$('button[id="prev"]').click(function(){
		alert("other prev");
		$.ajax({
			type: "GET",
			url: prevUrl, 
			data:{"emp":emp},
			success: function(response){
				alert(response);
				$('#whole1').html(response);
			},
			error: function(e){
				alert(e);
			}
			});
		}); 
	
	
		
		$('button[id="next"]').click(function(){
			alert("other next");
			$.ajax({
				type: "GET",
				url: nextUrl, 
				data:{"emp":emp},
				success: function(response){
					alert(response);
					$('#whole1').html(response);
				},
				error: function(e){
					alert(e);
				}
				});
			}); 
		
		$('button[id="last"]').on('click',function(){
			alert("other last");
			$.ajax({
				type: "GET",
				url: lastUrl, 
				data:{"emp":emp},
				success: function(response){
					alert(response);
					$('#whole1').html(response);
				},
				error: function(e){
					alert(e);
				}
				}); 
			}); 
	
	}

});

</script>
</div>