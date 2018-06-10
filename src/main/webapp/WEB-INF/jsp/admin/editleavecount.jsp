<div>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="/LeaveWebApp/leavecount/update" method="post" id="editCountForm">     
	    <p>
	    <label>Year</label>
        <input  required='required' name="year"  type="text" readonly="readonly" value="${lc.year}"/>
        </p>
	    <p>
	    <label>Sick Count</label>
        <input  required='required' name="sickCount"  type="text" value="${lc.sickCount}"/>
        </p>
        <p>
        <label>Casual Count</label>
        <input  required='required' name="casualCount" type="text" value="${lc.casualCount}"/>
        </p>
        <p>
        <label>Privileged Count</label>
        <input  required='required' name="privilegedCount" type="text" value="${lc.privilegedCount}"/>
        </p>        
	    <p>
        <label>B'day Count</label>
        <input  required='required' name="bdayCount" type="text" value="${lc.bdayCount}" />
        </p>        
	    <input type="submit" value="Submit Form">
</form>
<div id="info"></div>


<script>
$(document).ready(function() {
	

$('#editCountForm').submit(function(e) {
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
   		$("input[name=year]").val('');
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
</div>