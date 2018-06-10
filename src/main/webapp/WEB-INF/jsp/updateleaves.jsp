<div class="col-sm-8 text-left">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<c:forEach items="${leaves}" var="leave">
<tr>
<td>${leave.leaveId}</td>
<td>${leave.employeeEntity}</td>
<td>${leave.managerEntity}</td>
<td>${leave.currentlyManager}</td>
<td>${leave.fromDate}</td>
<td>${leave.toDate}</td>
<td>${leave.leaveType}</td>
<td>${leave.leaveStatus}</td>
<td>
<input type="radio" name="${leave.leaveId}" value="APPROVED"/>Approve
</td>
<td>
<input type="radio" name="${leave.leaveId}" value="REJECTED"/>Reject
</td>
</tr>   
</c:forEach>
</table>

 <button id="leavecheck">Submit</button>
 
<div id="info"></div>


<script src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
<script>
$(document).ready(function () {
	 
	$('#leavecheck').on('click', function() {

		var checkboxValueArray=[];
		var checkboxNameArray=[];
		//var myMap = new Map();
		
		$( "input[type=radio]:checked" ).each(function(){
			//var1 = $(this).attr("name");
			checkboxNameArray.push($(this).attr("name"));
			//var2 = $(this).val();
			checkboxValueArray.push($(this).val());
			
			//myMap.set(var1,var2);
			//alert(var1);
		    //alert(var2);
		});
		
		
		//myMap.forEach(function(value, key) {
			//  console.log(key + ' = ' + value);
			//});
		//alert("displaying again");
		//alert(checkboxValueArray);
		//alert(checkboxNameArray);
        alert(checkboxNameArray);
        alert(checkboxValueArray);
		$.ajax({
		type: "POST",
		url: "/LeaveWebApp/leave/approveReject",
		data: {
			 
		           "firstArray": checkboxNameArray,
		           "secondArray": checkboxValueArray,
		          
		},  
		success: function(response){
		// we have the response
		//alert(response);
		$('#info').html(response);
		},
		error: function(e){
			$('#info').html(response);
		}
		});
	});

});

</script>
</div>