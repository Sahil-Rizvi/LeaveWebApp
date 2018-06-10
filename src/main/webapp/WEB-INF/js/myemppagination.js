$(document).ready(function(){

	
	if (firstUrl) {
		$('button[id="first"]').click(function(){
			$.ajax({
				type: "GET",
				url: firstUrl, 
				data: { 
			        id: $( "#department" ).val(), 
			       	dept: $("#department option:selected" ).text()
			  },
			  success: function(response){
					$('#info').html(response);
				},
				error: function(e){
					$('#info').html("Error");
				}
				}); 
			}); 
	}
	
	if(baseUrl){
		$('button[name="getContent"]').on('click',function(){
		var pageUrl = baseUrl+this.id;
		$.ajax({
			type: "GET",
			url: pageUrl,
			data: { 
		        id: $( "#department" ).val(), 
		       	dept: $("#department option:selected" ).text()
		  },
		  success: function(response){
				$('#info').html(response);
			},
			error: function(e){
				$('#info').html("Error");
			}
			}); 
		}); 
	}
	
		
	if (prevUrl) {
		$('button[id="prev"]').click(function(){
		$.ajax({
			type: "GET",
			url: prevUrl, 
			data: { 
		        id: $( "#department" ).val(), 
		       	dept: $("#department option:selected" ).text()
		  },
		  success: function(response){
				$('#info').html(response);
			},
			error: function(e){
				$('#info').html("Error");
			}
			});
		}); 
	}
		
	
	if (nextUrl) {
		$('button[id="next"]').click(function(){
			$.ajax({
				type: "GET",
				url: nextUrl, 
				data: { 
			        id: $( "#department" ).val(), 
			       	dept: $("#department option:selected" ).text()
			  },
			  success: function(response){
					$('#info').html(response);
				},
				error: function(e){
					$('#info').html("Error");
				}
				});
			}); 
	}
	
	
	if (lastUrl) {
		$('button[id="last"]').on('click',function(){
			$.ajax({
				type: "GET",
				url: lastUrl, 
				data: { 
			        id: $( "#department" ).val(), 
			       	dept: $("#department option:selected" ).text()
			  },
			  success: function(response){
					$('#info').html(response);
				},
				error: function(e){
					$('#info').html("Error");
				}
				}); 
			}); 
	}
});
