<div class="col-sm-8 text-left">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="/LeaveWebApp/compOff/emp/addCompOff" method="post" id="compOffForm">     
	    <p>
	    <label>For Date</label>
        <input  id="datepicker" required='required' name="forDate" type="text" placeholder="dd/MM/yyyy"/>
        </p>
        <input type="submit" value="Submit Form">
</form>

<div id="info"></div>


<script>
$(document).ready(function () {
$("#datepicker").datepicker({
	changeMonth: true,//this option for allowing user to select month
    changeYear: true, //this option for allowing user to select from year range
    todayBtn: "linked",
    language: "it",
    autoclose: true,
    todayHighlight: true,
    dateFormat: 'dd/mm/yy' 
});





$('#compOffForm').submit(function(e) {
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
   		$("input[name='forDate']").val('');
   		},
   		error: function(e){
   			$('#info').html(e);
   		}
    });
});

});

</script>
</div>