<div class="col-sm-8 text-left">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty available}">
<form action="/LeaveWebApp/leavecount/add" method="post" id="countForm">     
	    <p>
	    <label>Sick Count</label>
        <input  required='required' name="sickCount"  type="text" />
        </p>
        <p>
        <label>Casual Count</label>
        <input  required='required' name="casualCount" type="text" />
        </p>
        <p>
        <label>Privileged Count</label>
        <input  required='required' name="privilegedCount" type="text" />
        </p>        
	    <p>
        <label>B'day Count</label>
        <input  required='required' name="bdayCount" type="text" />
        </p>        
	    <input type="submit" value="Submit Form">
</form>
<div id="info"></div>
<script>

$(document).ready(function() {
	

$('#countForm').submit(function(e) {
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
   		$("input[name=sickCount]").val('');
   		$("input[name=casualCount]").val('');
   		$("input[name=privilegedCount]").val('');
   		$("input[name=bdayCount]").val('');
   		
       },
   		error: function(e){
   			$('#info').html('EXCEPTION OCCURED');
   		}
    });
});

});


</script>
</c:if>
<c:if test="${not empty available}">
 Leave Count already added for current year.
</c:if>

</div>