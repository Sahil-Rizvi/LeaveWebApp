<div class="col-sm-8 text-left"> 
<div class="container1" >
 <div class='element' id='div_1'>
  <input type='text'  required="required" name="dept[]" placeholder='Department' id='txt_1' >&nbsp;<button id='remove_1' class='remove'>Remove</button>
 </div>
<button class='add'>Add Departments</button>

<button id='adddeptbutton'>Submit</button>

<div id="info"></div>

</div>


<script type="text/javascript">

    $(document).ready(function() {

    	 // Add new element
    	 $(".add").click(function(){

    	  // Finding total number of elements added
    	  var total_element = $(".element").length;
    	  
    	 
    	  var nextindex = 0;
    	  var max = 25;
    	  if(total_element==0){
    		  nextindex = 1;
        	  
        	  $(".container1").prepend("<div class='element' id='div_1'><input type='text'  required='required' name='dept[]' placeholder='Department' id='txt_1' >&nbsp;<button id='remove_1' class='remove'>Remove</button></div>");

    	  }
    	  else{
    		  var lastid = $(".element:last").attr("id");
        	  var split_id = lastid.split("_");  
        	  nextindex = Number(split_id[1]) + 1;
        	  if(total_element < max ){
           	   // Adding new div container after last occurance of element class
           	   $(".element:last").after("<div class='element' id='div_"+ nextindex +"'></div>");
           	 
           	   // Adding element to <div>
           	   $("#div_" + nextindex).append("<input type='text' required='required' name='dept[]' placeholder='Department' id='txt_"+ nextindex +"'>&nbsp;<button id='remove_" + nextindex + "' class='remove'>Remove</button>");
           	 
           	  }
    	  }
    	 
    	  
 
    	 
    	 
    	 });

    	 // Remove element
    	 $('.container1').on('click','.remove',function(){
    	 
    	  var id = this.id;
    	  var split_id = id.split("_");
    	  var deleteindex = split_id[1];

    	  // Remove <div> with id
    	  $("#div_" + deleteindex).remove();

    	 });
    	 
    	 
      $('#adddeptbutton').on('click',function() {
  	    
  	  var values = [];
	  $("div  input[name='dept[]']").each(function() {
    		values.push($(this).val());
	  });
  	  
  	  if(values.length>0){
	  $.ajax({
  	       type: 'POST', // method attribute of form
  	       url: '/LeaveWebApp/customAdmin/dept/add',  // action attribute of form
  	   // convert form data to json format
  	       data : {"dept":values},
  	       success: function(response){
  	   		// we have the response
  	   		//alert(response)
  	   		$('#info').html(response);
  	   		$("input[name='dept[]']").val('');
  	   		},
  	   		error: function(e){
  	   			$('#info').html("error");
  	   		}
  	    });
  	  }
  	  else{
  		$('#info').html("Please enter some value");
  	  }
  	});



    });

</script>


</div>