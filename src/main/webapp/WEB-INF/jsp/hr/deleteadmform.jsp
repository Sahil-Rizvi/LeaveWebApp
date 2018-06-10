<div class="col-sm-8 text-left">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="/LeaveWebApp/hr/deleteAdmin" method="post" id="deleteAdminForm">     
	    <p>
	    <label>EmailId</label>
        <input  name="emailId"   required='required' type="email" />
        </p>
        <p>
        <label>Password</label>
        <input  name="password"   required='required' type="password" />
        </p>
        <p>
        <input type="submit" value="Submit Form">
</form>

<div id="info"></div>


<script>

$(document).ready(function() {
	

$('#deleteAdminForm').submit(function(e) {
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
   		$("input[name=emailId]").val('');
   		$("input[name=password]").val('');
   		},
   		error: function(e){
   			$('#info').html('EXCEPTION OCCURED');
   		}
    });
});

});


</script>
</div>