<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<form method="GET" action="/LeaveWebApp/hr/findByEmpDepartment/1" id="getempByDeptForm">     
	    
	    <c:if test="${empty message}">
	    	<label>Select Department</label>
        	<select id="department" required='required'>
        		<c:forEach items="${dept}" var="dept">
         		<option value="${dept.id}">${dept.name}</option>
        		</c:forEach>
        	</select>
        </c:if>
        <c:if test="${not empty message}">
         <h3>${message}</h3>
        </c:if>
    <c:if test="${not empty dept }">
    	<div class="input-group-btn">
      		<button class="btn btn-default" type="submit">
        	<i class="glyphicon glyphicon-search"></i>
     	 	</button>
    	</div>
    </c:if>
</form>

<div id="info"></div>

<script>

$(document).ready(function() {
	


$('#getempByDeptForm').submit(function(e) {
    // reference to form object
var form = $(this);
    // for stopping the default action of element
    e.preventDefault();
    
    
   $.ajax({
       type: form.attr('method'), // method attribute of form
       url: form.attr('action'),  // action attribute of form
   // convert form data to json format
        data: { 
        id: $( "#department" ).val(), 
       	dept: $("#department option:selected" ).text()
  },
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