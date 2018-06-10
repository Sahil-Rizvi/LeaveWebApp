<div class="col-sm-8 text-left">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="/LeaveWebApp/hr/updatePassword" method="post" id="updateAdminPassForm">     
	    <p>
	    <label>Old Password</label>
        <input  required='required' name="oldPassword"   type="password" />
        </p>
        <p>
        <label>New Password</label>
        <input  required='required' name="newPassword"   type="password" />
        </p>
        <p>
        <label>Confirm Password</label>
        <input  required='required' name="confirmPassword"   type="password" />
        </p>
        <input type="submit" value="Submit Form">
</form>

<div id="info"></div>


<script>

$(document).ready(function() {
	

$('#updateAdminPassForm').submit(function(e) {
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
   		$("input[name=oldPassword]").val('');
   		$("input[name=newPassword]").val('');
   		$("input[name=confirmPassword]").val('');
   		
   		},
   		error: function(e){
   			$('#info').html('EXCEPTION OCCURED');
   		}
    });
});

});


</script>
</div>