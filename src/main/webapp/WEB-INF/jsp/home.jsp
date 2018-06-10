<div class="col-sm-8 text-left">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="/LeaveWebApp/leave/emp/addLeave" method="post" id="leaveForm">     
	    <p>
	    <label>From</label>
        <input  name="fromDate" id="datepicker1"  type="text" required="required" placeholder="dd/MM/yyyy"/>
        </p>
        <p>
        <label>To</label>
        <input  name="toDate"  id="datepicker2" type="text" required="required" placeholder="dd/MM/yyyy">
        </p>
        <p>
        <label>LeaveType</label>
        <select id="leaveType" required="required"  name="leaveType">
        <c:forEach items="${leaveTypes}" var="type">
         <option value="${type}">${type}</option>
        </c:forEach>
         </select>
        </p>        
	    <input type="submit" value="Submit Form">
</form>

<div id="info"></div>


<script>

$(document).ready(function() {
	

    $("#datepicker1").datepicker({
    	changeMonth: true,//this option for allowing user to select month
        changeYear: true, //this option for allowing user to select from year range
        todayBtn: "linked",
        language: "it",
        autoclose: true,
        todayHighlight: true,
        dateFormat: 'dd/mm/yy' 
    });
    
    
    $("#datepicker2").datepicker({
    	changeMonth: true,//this option for allowing user to select month
        changeYear: true, //this option for allowing user to select from year range
        todayBtn: "linked",
        language: "it",
        autoclose: true,
        todayHighlight: true,
        dateFormat: 'dd/mm/yy' 
    });
        

$('#leaveForm').submit(function(e) {
    // reference to form object
var form = $(this);
    // for stopping the default action of element
    e.preventDefault();
    // mapthat will hold form data
    
    
    
    
    
   $.ajax({
       type: form.attr('method'), // method attribute of form
       url: form.attr('action'),  // action attribute of form
   // convert form data to json format
       data : form.serialize(),
       success: function(response){
   		// we have the response
   		//alert(response)
   		$('#info').html(response);
   		$("input[name=fromDate]").val('');
   		$("input[name=toDate]").val('');
   		},
   		error: function(e){
   			$('#info').html('EXCEPTION OCCURED');
   		}
    });
});

});


</script>
</div>