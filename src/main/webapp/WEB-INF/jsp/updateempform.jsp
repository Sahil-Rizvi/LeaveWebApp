<div class="col-sm-8 text-left">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="/LeaveWebApp/emp/updateEmployee" method="post" id="updateEmpForm">     
	    <p>
	    <label>Name</label>
        <input  name="employeeName" required='required'  type="text" value="${emp1.employeeName}" />
        </p>
        <p>
        <label>Phone No.</label>
        <input  name="phoneNo"  required='required' type="text" value="${emp1.phoneNo}">
        </p>
        <input type="submit" value="Submit Form">
</form>

<div id="info"></div>


<script>

$(document).ready(function() {
	

$('#updateEmpForm').submit(function(e) {
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
   		$("input[name=employeeName]").val('');
   		$("input[name=phoneNo]").val('');
   		},
   		error: function(e){
   			$('#info').html('EXCEPTION OCCURED');
   		}
    });
});

});


</script>
</div>