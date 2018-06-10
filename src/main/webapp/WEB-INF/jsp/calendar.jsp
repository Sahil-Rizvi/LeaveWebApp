<head>
<link rel='stylesheet' href='https://fullcalendar.io/js/fullcalendar-2.2.5/fullcalendar.css' />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src='https://fullcalendar.io/js/fullcalendar-2.2.5/lib/moment.min.js'></script>
<script src='https://fullcalendar.io/js/fullcalendar-2.2.5/fullcalendar.min.js'></script>
<script>
    $(document).ready(function() {
    	
    	$('#calendar1').fullCalendar({
    	    events: '/LeaveWebApp/leave/getData',
    	   
    	
    });
    	
    });
    	</script>



<!--  

<script>
var date = new Date();
var d = date.getDate(),
    m = date.getMonth(),
    y = date.getFullYear();
$('#calendar').fullCalendar({
  header: {
    left: 'prev,next today',
    center: 'title',
    right: 'month,agendaWeek,agendaDay'
  },
  
  events: function (start, end, callback) {
      $.ajax({
          type: "POST",    //WebMethods will not allow GET
          url: '/LeaveWebApp/leave/getData',   //url of a webmethod - example below
          success: function (doc) {
              var events = [];   //javascript event object created here
              var obj = $.parseJSON(doc);  //.net returns json wrapped in "d"
              for(var i=0;i<obj.length;i++){
                  console.log(obj[i]['date']);
                  console.log(obj[i]['0']);
                  addCalanderEvent(obj[i]['date'],obj[i]['start'],obj[i]['end'],obj[i]['date']);
              }
          }
      });
  })
  
  
  
  function addCalanderEvent(id, start, end, title)
  {
  console.log(id + start + end + title);
  var eventObject = {
  title: title,
  start: start,
  end: end,
  id: id,
  allDay: true
  };

  $('#calendar').fullCalendar('renderEvent', eventObject, true);
  return eventObject;
  }


});




/* ADDING EVENTS */
var currColor = "#3c8dbc"; //Red by default
//Color chooser button
var colorChooser = $("#color-chooser-btn");
$("#color-chooser > li > a").click(function (e) {
  e.preventDefault();
  //Save color
  currColor = $(this).css("color");
  //Add color effect to button
  $('#add-new-event').css({"background-color": currColor, "border-color": currColor});
});
$("#add-new-event").click(function (e) {
  e.preventDefault();
  //Get value and make sure it is not null
  var val = $("#new-event").val();
  if (val.length == 0) {
    return;
  }

  //Create events
  var event = $("<div />");
  event.css({"background-color": currColor, "border-color": currColor, "color": "#fff"}).addClass("external-event");
  event.html(val);
  $('#external-events').prepend(event);

  //Add draggable funtionality
  ini_events(event);

  //Remove event from text input
  $("#new-event").val("");
});
});



function saveEvent(){
    console.clear();
    console.log(startDateFinal +" - "+ endDateFinal+" - "+eventNameFinal);

    $.ajax({
         type: "POST",
         data: "{'startYear':'" + startDateFinal + "', 'endYear':'" + endDateFinal + "', 'eventName':'" + eventNameFinal + "'}",
         contentType: "application/json; charset=utf-8",
         dataType: 'json',
         success: function (response) {
              if (response.d) {
                 debugger;
         }
         else {
                  debugger;
         }
         },
         failure: function (response) {
                  debugger;
         }
    });
}

function getData() {

var data3;
$.ajax({
    type: "POST",    
    url: '/LeaveWebApp/leave/getData',
    //contentType: "application/json; charset=utf-8",  
    dataType: "json",
    async:false,
     success: function (doc) {

              data3 = $.parseJSON(doc.d);
              data3 = JSON.stringify(data3)

          },
          error: function(xhr, status, error) {
              alert(xhr.responseText);
          }
});

return data3;

}


function addCalanderEvent(id, start, end, title)
{
console.log(id + start + end + title);
var eventObject = {
title: title,
start: start,
end: end,
id: id,
allDay: true
};

$('#calendar').fullCalendar('renderEvent', eventObject, true);
return eventObject;
}








</script>

-->


</head>

<div id="calendar1"></div>