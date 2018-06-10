<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form method="GET" action="/LeaveWebApp/leave/manager/getLeavesApprovedByManager/1" id="getleaveForm">     
	    <p>
	    <label>From When</label>
        <input required='required' name="fromWhen" id="datepicker1"  type="text" placeholder="dd/MM/yyyy"/>
        </p>
        <p>
        <label>To When</label>
        <input required='required' name="toWhen"  id="datepicker2" type="text" placeholder="dd/MM/yyyy">
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
        

$('#getleaveForm').submit(function(e) {
    // reference to form object
var form = $(this);
    // for stopping the default action of element
    e.preventDefault();
    
    
   $.ajax({
       type: form.attr('method'), // method attribute of form
       url: form.attr('action'),  // action attribute of form
   // convert form data to json format
       data : form.serialize(),
       success: function(response){
   		// we have the response
   		//alert(response)
   		$('#info').html(response);
   		},
   		error: function(e){
   			$('#info').html('EXCEPTION OCCURED');
   		}
    });
});

});


</script>