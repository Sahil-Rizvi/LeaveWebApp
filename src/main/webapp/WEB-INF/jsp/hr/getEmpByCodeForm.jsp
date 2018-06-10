<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form method="GET" action="/LeaveWebApp/hr/findByEmpCode" id="getempForm">     
	<label>Emp Code</label>
    <input type="text" required='required' name="id" class="form-control" placeholder="Search">
    <div class="input-group-btn">
      <button class="btn btn-default" type="submit">
        <i class="glyphicon glyphicon-search"></i>
      </button>
    </div>
</form>

<div id="info"></div>


<script>

$(document).ready(function() {
	


$('#getempForm').submit(function(e) {
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