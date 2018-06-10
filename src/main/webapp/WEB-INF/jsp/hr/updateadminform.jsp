<div class="col-sm-8 text-left">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="/LeaveWebApp/hr/updateAdmin" method="post" id="updateAdminForm">     
	    <p>
	    <label>AdminId</label>
        <input  name="adminId"   required='required' type="text" value="${adm.adminId}" readonly="readonly"/>
        </p>
        <p>
        <label>Name</label>
        <input  name="name"   required='required' type="text" value="${adm.name}">
        </p>
        <p>
        <label>Email Id</label>
        <input  name="emailId" required='required' type="email" value="${adm.emailId}">
        </p>
        <input type="submit" value="Submit Form">
</form>

<div id="info"></div>

<script>

$(document).ready(function() {
	

$('#updateAdminForm').submit(function(e) {
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
   		$("input[name=adminId]").val('');
   		$("input[name=name]").val('');
   		$("input[name=emailId]").val('');
   		},
   		error: function(e){
   			$('#info').html('EXCEPTION OCCURED');
   		}
    });
});

});


</script>
</div>