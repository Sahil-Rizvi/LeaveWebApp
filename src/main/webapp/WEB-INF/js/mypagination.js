$(document).ready(function(){

	if (firstUrl) {
		$('button[id="first"]').click(function(){
			$.ajax({
				type: "GET",
				url: firstUrl, 
				data:$("#getleaveForm").serialize(),
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
			data:$("#getleaveForm").serialize(),
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
			data:$("#getleaveForm").serialize(),
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
				data:$("#getleaveForm").serialize(),
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
				data:$("#getleaveForm").serialize(),
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
