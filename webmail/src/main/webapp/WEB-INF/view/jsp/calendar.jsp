<%@page import="java.util.GregorianCalendar"%>
<%@page import="javax.xml.datatype.XMLGregorianCalendar"%>
<%@page import="javax.xml.datatype.DatatypeConfigurationException"%>
<%@page import="javax.xml.datatype.DatatypeFactory"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.util.Calendar"%>
<%@page import="webmail.wsdl.CreateCalendarRequest"%>
<%@page import="java.text.ParsePosition"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="webmail.wsdl.VCFLdapDirAtt"%>
<%@page import="webmail.webservice.client.WebmailClient"%>
<%@page import="webmail.wsdl.GetVCFLdapDirResponse"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="webmail.wsdl.File"%>
<%@page import="java.util.ArrayList"%>
<%@page import="webmail.wsdl.EventBean"%>
<%@page import="java.util.List"%>
<%@page import="webmail.wsdl.EventArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
$(document).ready(function(){	
 	
 	function generate_cal(type) {
         var n = noty({
             text        : type,
             type        : type,
             dismissQueue: false,
             layout      : 'topCenter',
             theme       : 'defaultTheme'
         });
       
         return n;
     }
});
</script>

 

</head>
<body>
<%
HttpSession hsbd=request.getSession();
String mailid_bd=hsbd.getAttribute("id").toString();
String fname_bd=hsbd.getAttribute("fname").toString();
byte[] jpegBytes2=(byte[])hsbd.getAttribute("img_myurl");
String chat_img_bd="";
if(jpegBytes2!=null)
{
	chat_img_bd= new sun.misc.BASE64Encoder().encode(jpegBytes2);
}
%>
<div class="right-pane"> 
    
    <!-----------------------------/// Main Cointer Stared here --------------->
    <div id="widget" > 
      
      <!-------/// CALENDER TOP THREE OPTION ----------->
 <div class="top_three_calender"> 
        
        <!---/// FIRST CALENDER ------>
        <div class="first_cal_option new_con_option" title="Create Calendar"><div class="create_cal_icon"></div> Create Calendar </div>
        <!------/// FIRST CALENDER END HERE --------> 
        <!---/// SECOND CALENDER ------>
        <!-- <div class="first_cal_option_1 hide_this new_con_option"><div class="create_cal_icon_1"></div>Create Task</div> -->
        <!------/// SECOND CALENDER END HERE --------> 
        <!---/// THIRD CALENDER ------>
        <div class="first_cal_option_2 create_event new_con_option" title="Create Event"><div class="create_cal_icon_2"></div>Create Event</div>
        <!------/// THIRD CALENDER END HERE --------> 
        
      </div>
      <!---------/// CALENDER TOP THREE OPTION END HERE -------->
    <!--   <div class="agenda">Agenda</div> -->
      <div class="right_tool_part for_calender">
        <div class="right_tool" title="Refresh"> <a  href="#" onclick="refreshCalendar()"> <img src="images/reload.png"> </a> </div>
       <!--  <div class="right_tool_1">
          <ul id="menu" >
            <li> <img src="images/setting_toll.png" class="four_margin" ></li>
            <li class="right_menu_1" > <img src="images/open_sub_menu.png" style="margin-left: 8px !important;"> 
              <ul class="for_setting">
                                            <li> <a href="#">Settings</a></li>
                                            <li><a href="#">Themes</a></li>
                                            <li> <a href="#">Help</a></li>
                                        </ul> 
            </li>
          </ul>
        </div> -->
      </div>
      
      <!---------------/// Tab Content Stared Here Off VIEW ---------------------------->
      <div id='calendar'></div>
      <!-----------------/// Tab Content End Here Off VIEW----------------------------> 
      
      <!-------------------/// Right View and Bottom View Tab Stared Here --------------->
      
     
      
      <!-------------------/// Right View and Bottom View Tab End Here ---------------> 
      
    </div>
    
    <!--------------------/// Mail Cointer End Here ----------------->
    <div class="clear"></div>
  </div>
</div>
<div class="for_setting_1">
  <ul id="menu" class="extra_menu">
    <li> <a href="#">&nbsp; </a>
      <ul class="for_setting new_submenu">
        <li> <a href="#">Settings</a></li>
        <li><a href="#">Themes</a></li>
        <li> <a href="#">Help</a></li>
      </ul>
    </li>
  </ul>
</div>






<script src="js/event.js" type="text/javascript" language="javascript" ></script> 
<script src="js/tab_event.js" type="text/javascript" language="javascript" ></script> 
<script type='text/javascript' src='js/jquery.dcjqaccordion.2.7.min.js'></script> 
<script type='text/javascript' src='js/form2object.js'></script> 
<script type="text/javascript">
                                                $(document).ready(function($) {
                                                    $('#accordion-3').dcAccordion({
                                                        eventType: 'click',
                                                        autoClose: false,
                                                        saveState: false,
                                                        disableLink: false,
                                                        showCount: false,
                                                        speed: 'slow'
                                                    });
                                                });
            </script>
<link rel="stylesheet" type="text/css" href="css/jquery.jscrollpane.css" />
<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script> 
<script type="text/javascript">
                                                $(document).ready(function() {
                                                    if (!$.browser.webkit) {
                                                        $('.container').jScrollPane();
                                                    }
                                                });
            </script> 

<!---------------------/// JS CALENDER STARED HERE ------------> 

<script>
            $(document).ready(function() {
            	var tdate = new Date();
            	var month="";
            	var day="";
            	   var dd = tdate.getDate(); //yields day
            	   var MM = tdate.getMonth(); //yields month
            	   MM=MM+1
            	   month=MM < 10 ? '0' + MM : '' + MM;
            	   day=dd < 10 ? '0' + dd : '' + dd;
            	   var yyyy = tdate.getFullYear(); //yields year
            	   var xxx =yyyy + "-" +month + "-" + day;
            $('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			  nextDayThreshold: '00:00:00',
// 			defaultView: 'agendaWeek',
			defaultDate: xxx,
			selectable: true,
			selectHelper: true,
			select: function(start, end) {
				
				
				$('.pop_tab ul li.remind_active').removeClass('remind_active');
				$('.pop_tab ul li.work_active').removeClass('work_active')
				$('.pop_tab ul li.repe_active').removeClass('repe_active')
				$('.gen_opt').addClass('gen_active');
				$('.workgroup_cal').hide();
				$('.reminder_cal').hide();
				$('.repeat_cal').hide();
				$('.gen_content').show();
				
				  // START DATE 
			    var allDay = !start.hasTime();
// 				var allDay = !end.hasTime();
				
				
				 // STARED DATE END 
				//alert("date : " + end.date);
				/// TEST ADD HERE 
				
				$('.until_input').val(getEndedDate);
				$('.count_input').val("10");
				var view = $('#calendar').fullCalendar('getView');
// 				alert("The view's title is " + view.name);
				
				if($('.calender_pop').css('display')=='none')
				{
					$("#allday").prop("checked", false);
					$('.date_stared').removeClass('hide_time');
					
					if(view.name == "month")
					{
						var temp = new Date(end);
						temp.setDate(temp.getDate()-1);
						var getStartedDate = [moment(start).format('YYYY-MM-DD')];
						var getEndedDate = [moment(temp).format('YYYY-MM-DD')];
						//var getStartedtime = [moment(start).format('hh:mmA')];
						//var getEndedtime = [moment(end).format('hh:mmA')];
						
						$('.start_date').val(getStartedDate);
						//$('#starttime').val(getStartedtime);
 						$('.end_date').val(getEndedDate);
						
 						$('#starttime').addClass("hide_time");
 						$('#endtime').addClass("hide_time");
						
 						$('#allday').attr('checked','checked');

					}
					else
					{
						var getStartedDate = [moment(start).format('YYYY-MM-DD')];
						var getEndedDate = [moment(end).format('YYYY-MM-DD')];
						var getStartedtime = [moment(start).format('hh:mmA')];
						var getEndedtime = [moment(end).format('hh:mmA')];
						$('.start_date').val(getStartedDate);
						$('#starttime').val(getStartedtime);
 						$('.end_date').val(getEndedDate);
 						$('#endtime').val(getEndedtime);
						
						$('#allday').removeAttr('checked');
						$('.hide_time').removeClass("hide_time");
					}
					
					$('.calender_pop').css('display','block');
					$('.web_dialog_overlay').show();
					$('.end_recur').hide();
				 	$('.repeat_every_day').hide();
					
				 	$('.guest_content_11 >table.append_guest >tbody').html("");
					$('.clear_hiiden').val("");
					
			}
				
				 $('.save_cal').click(function(){
					
				     var input_value = $('.summary').val();
					//alert(input_value);
					
					});
				 
				/// ********************** ///
				///     NOTE  stred here  ///
				/// ******************** ///
				
				
				////  i am not change the value when or append the value in each box beause it affect in development
				  //   if  you want to new  state only remove the comment of //var title = prompt('Event Title:'); only 
				  /// and hide the new test event
				
				
				
				
				/// ********************** ///
				///     NOTE  end         ///
				/// ******************** ///
				
				
				/// TEST ADD END HERE 
				
				
				//var title = prompt('Event Title:');
				//var title = prompt('Event Title:');
				
				
				var eventData;
				if (title) {
					eventData = {
						title: title,
						start: start,
						end: end
					};
					$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
				}
				$('#calendar').fullCalendar('unselect');


				


			},
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			
			events: [
			         <% 
			         List <EventBean> eventarray=(List <EventBean>)request.getAttribute("eventlist");
			         try
			         {
			         for(EventBean e:eventarray)
			         {  			       		   
			        	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+e.getSummary());
			        	 DateFormat dateformat=null;
			        	 DateFormat dateformat2=null;
			        	 String startdt="";
			        	 String enddt="";
			        	 e.getEndeventdate().setDay(e.getEndeventdate().getDay());
			        	 if(e.getAllday().equals("on"))
			        		 {
			        		 	dateformat=new SimpleDateFormat("yyyy-MM-dd");
			        		 	dateformat2=new SimpleDateFormat("yyyy-MM-dd");
			        		 	startdt=dateformat.format(e.getStarteventdate().toGregorianCalendar().getTime());
			        		 	XMLGregorianCalendar date2= e.getEndeventdate();
			        		 	GregorianCalendar calendar = date2.toGregorianCalendar();
			    				calendar.add(Calendar.DAY_OF_MONTH, -1);
			    				try {
			    					date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
			    				} catch (DatatypeConfigurationException e11) {
			    					// TODO Auto-generated catch block
			    					e11.printStackTrace();
			    				}
			    				 enddt=dateformat2.format(date2.toGregorianCalendar().getTime());
			    				
			        		 }
			        	 else
			        	 {
				        	 dateformat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				        	 dateformat2=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
				        	 startdt=dateformat2.format(e.getStarteventdate().toGregorianCalendar().getTime());
				        	 enddt=dateformat2.format(e.getEndeventdate().toGregorianCalendar().getTime());
			        	 }
			        	 String startdate=dateformat.format(e.getStarteventdate().toGregorianCalendar().getTime());
			        	 String enddate=dateformat.format(e.getEndeventdate().toGregorianCalendar().getTime());
			        	  String sum=e.getSummary();
	        	    //	 System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+sum);
	        	    	//    sum=HtmlUtils.htmlEscape(sum);
	        	    	//    System.out.println("........................"+sum); 
	        	    	sum=sum.replaceAll("'", "");
	        	    	if(e.getRecurrenceEventID()==null)
	        	    		e.setRecurrenceEventID("");
	        	    	if(e.getRepeatStatus()==null)
	        	    		e.setRepeatStatus("");
	        	if(e.getRepeatdatetimelist()!=null)
	        	{
	        		
	        	      for(String str:e.getRepeatdatetimelist().getDateTime())
	        	      {
	        	    	    String []dates=str.split("`");
	        	    	      	    	   DateFormat dateformat1=new SimpleDateFormat("yyyyMMdd'T'hhmmss");
	        	    	    Date datestart=dateformat1.parse(dates[0]);
	        	    	    Date dateend=dateformat1.parse(dates[1]);
	        	    	    dateend.setDate(dateend.getDate());
	        	    	  Date dt11=null;
	        	    	  if(e.getAllday().equals("on"))
			        		 {
	        	    		 	 //System.out.println("dt="+dateend);
	        	    		  	 Calendar calendar = Calendar.getInstance();
	        	    		 	 calendar.setTime(dateend);
			    				 calendar.add(Calendar.DAY_OF_MONTH, -1);
			    				 dt11= calendar.getTime();
			        		 }
	        	    	  else
	        	    	  {
	        	    		  dt11=dateend;
	        	    	  }
	        	    	    %>
	        	    	  {
	      					id: '<%=e.getUid()%>',
	      					c:'<%=e.getUid()%>',
	      					name:'<%=e.getCalendar()%>',
	      					title: '<%=sum %>',
	      					start: '<%= dateformat.format(datestart)%>',
	      					end: '<%= dateformat.format(dateend)%>',
	      					color: '<%=e.getColor()%>', 
	      					recurrenceID: '<%=e.getRecurrenceEventID()%>', 
	      					repeatStatus: '<%=e.getRepeatStatus()%>', 
	      					startDT: '<%= dateformat2.format(datestart)%>',
	      					endDT: '<%= dateformat2.format(dt11)%>',
	      					timezone: 'Asia/Kolkata',
	      					 /*textColor: 'black' */
	      				},
	        	    	  <%
	        	      }
	 		    }	
	        	else
	        	{
		        	
	         %>
		{
			id: '<%=e.getUid()%>',
			c:'<%=e.getUid()%>',
			name:'<%=e.getCalendar()%>',
			title: '<%=sum %>',
			start: '<%= startdate%>',
			end:'<%=enddate %>',
			 color: '<%=e.getColor()%>', 
			 recurrenceID: '<%=e.getRecurrenceEventID()%>', 
				repeatStatus: '<%=e.getRepeatStatus()%>', 
				startDT: '<%= startdt%>',
				endDT:'<%=enddt %>',
			 timezone: 'Asia/Kolkata',
			 /*textColor: 'black' */
		},
		<% 			
	        	}
			         }
			         }
			         catch(Exception ee)
			         {
			        	 ee.printStackTrace();
			         }
			         %>
			         
			         
				
				{
					title: 'Click for Google',
					url: 'http://google.com/',
					start: '2014-11-28'
				}
			]
		});
		
	});

         /*    document.getElementById('div_progress').style.display= 'none'; */
</script>
<link rel='stylesheet' href='css/create_event.css' />
<link rel='stylesheet' href='css/jquery-ui.min.css' />
<link href='css/fullcalendar.css' rel='stylesheet' />
<link src='css/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.js'></script>
<script src='js/moment.min.js'></script> 
<script src='js/create_event.js'></script> 
<!--<script src='../lib/jquery.min.js'></script>---> 
<script src='js/fullcalendar.js'></script>
<!-----------/// COLOR PICKER STRED HERE ----------->
    <link rel="stylesheet" type="text/css" href="color_picker/spectrum.css">
    <script type="text/javascript" src="color_picker/spectrum.js"></script>
    <script type='text/javascript' src='color_picker/docs.js'></script>
<!-------------/// COLOR PICKER END HERE --------------->
<style>
	#calendar {
		/*max-width: 900px;*/
		margin: 0 auto;
	}
</style>
<!--------------------/// JS CALENDER END HERE ------------------> 

<!------------------/// NEW SPILITTER STARED HERE --------------> 
<script>
   /*     jQuery(function($) {
    $(window).on('resize', function() {
        var height = $(window).height()-135;
		//alert(height);
        console.log(height);
        $('#foo').height(height).split({ orientation:'horizontal', limit:50 });
		$('#foo').css('height',height)
        $('#b').height(height / 2)-30;
		//$('.chat_inner_content').height(height / 2);
		
		
    }).trigger('resize');
});

   */
    </script> 
<!-------------------//// NEW SPILITTER END HERE ---------------> 

<!------------/// CREATE CALENDER POP STARED HERE ----------->

<div class="calender_pop"> 
<form id="eventform" commandName="eventform">
  <!--------/// HEADER STARED HERE ----------->
   <input type="hidden" id="hid_cal_id" value="" class="clear_data"/>
   <input type="hidden" id="hid_organizer" value="" class="clear_data"/>
   <input type="hidden" id="hid_startdate" value="" class="clear_data"/>
   <input type="hidden" id="hid_enddate" value="" class="clear_data"/>
   <input type="hidden" id="hid_starttime" value="" class="clear_data"/>
   <input type="hidden" id="hid_endtime" value="" class="clear_data"/>
   <input type="hidden" id="hid_repeatstatus" value="" class="clear_data"/>
   <input type="hidden" id="hid_recureventid" value="" class="clear_data"/>
   <input type="hidden" id="hid_repStartDate" value="" class="clear_data"/>
   <input type="hidden" id="exdate" name="exdate" value="" class="clear_data"/> 
   <input type="hidden" id="hid_cal_path" name="hid_cal_path" value="" class="clear_data"/> 
  <div class="pop_header">
  <div id="editeventheader">Create event</div>
  
      <span>X</span>
  </div>
  <!----------/// HEADER END HERE -------> 
  <!----------// POP TAB STRED HERE -------->
  <div class="pop_tab">
         <ul>
             <li class="gen_opt gen_active"><div class="gen_icon"></div>General options</li>
             <li class="repe"><div class="repe_icon"></div>Repeat</li>
             <li class="remind"><div class="reminder_icon"></div>Reminders</li>
           <li class="work_g"><div class="working_icon"></div>Invite Guest</li>
         </ul>
  
  
  
  <!---  <div class="gen_opt">General options</div>
    <div class="repe">Repeat</div>
    <div class="remind">Reminders</div>
    <div class="work_g">Workgroup</div> --->
  </div>
  <!---------/// POP TAB END HERE -------->
  <div class="clear"></div>
  <!----------/// POPUP CREATE CONTENT BOX ---------->

  <div class="pop_content"> 
    <!------------//// POP CONTENT GENERAL OPTION --------->
    <div class="gen_content">
      <table class="table">
        <tr>
          <td>Event Title</td>
          <td><input type="text" class="summary_1 clear_data" id="summary" name="summary" required="required"/><span>*</span></td>
        </tr>
        <tr>
          <td>Where</td>
          <td><input type="text" id="location" name="location" class="clear_data"/><span>*</span></td>
        </tr>
        <tr>
          <td>Calendar</td>
          <td>
          <input type="text" id="calendar_select_np" name="calendar_select_np" class="clear_data" style="display: none;"/>
          <select id="calendar_select" name="calendar" class="clear_data">
<!--           <option value="Select Calendar">Select Calendar</option> -->
          <%   
          List <CreateCalendarRequest> calendarlist=(List <CreateCalendarRequest>)request.getAttribute("calendarfilelist");
          List<String> filenames=(List<String>)request.getAttribute("filenames");
       	  int i=0;       	
          for(CreateCalendarRequest cl: calendarlist)
          {
       	  	if(filenames.get(i).equals("default.ics"))
          	{
           		%>
             		<option value="<%=filenames.get(i) %>"><%=cl.getCalID() %></option>
             	<%
       	  	}
             i++;
       	  	
          } 
          i=0;       	
          for(CreateCalendarRequest cl: calendarlist)
          {
       	 	if(!filenames.get(i).equals("default.ics"))
           	{
           		%>
             		<option value="<%=filenames.get(i) %>"><%=cl.getCalID() %></option>
             	<%
       	 		
           	}
             i++;
          } 
          
	      List <CreateCalendarRequest> sharedcalendarlist=(List<CreateCalendarRequest>)request.getAttribute("sharedcalendarfilelist");
	      List<String> sharedfilenames=(List<String>)request.getAttribute("sharedfilenames");
    	  i=0;
          for(CreateCalendarRequest cl: sharedcalendarlist)
          {
        	  System.out.println("Permission : " + cl.getPermission());
        	  if(cl.getPermission()!=null && cl.getPermission().equals("manage"))
        	  {
             	%>
	           		<option value="<%=sharedfilenames.get(i) %>"><%=cl.getCalID() %> ( <%= cl.getUserid() %> )</option>
	            <%
        	  }
	            i++;
           }
			        	 System.out.println("E>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>333333388888888");
            
           %>
              </select></td>
        </tr>
       
        <tr>
          <td>Start date</td>
          <td><input type="text" id="startdate" name="startdate" class="clear_data popupDatepicker start_date" required="required"/>
            <input type="text" class="date_stared clear_data" placeholder="Show Time Here" id="starttime" name="starttime"/><span>*</span></td>
        </tr>
        <tr>
          <td>End date</td>
          <td><input type="text" id="enddate" name="enddate" class="clear_data popupDatepicker end_date" required="required"/>
            <input type="text" class="date_stared clear_data" placeholder="Show Time Here" id="endtime" name="endtime" /><span>*</span></td>
        </tr>
    <tr>
          <td>All day</td>
          <td><input type="checkbox" id="allday" name="allday" class="clear_data all_day" /></td>
        </tr> 
        <tr >
                   <td>Privacy</td>
                   <td>
                              <select id="clazz" name="clazz" class="clear_data">
                              		<option value="PUBLIC">Public</option>
                              		<option value="PRIVATE">Private</option>
                                    <option value="CONFIDENTIAL">Confidential</option>
                              </select><span>*</span>
                   </td>
                </tr>
                <tr>
                   <td>Show Me as</td>
                   <td>
                            <select id="freebusy" class="clear_data" name="freebusy">
                                   <option>Free</option>
                                  <option>Busy</option>
                            </select>
                   <span>*</span>
                   </td>
        </tr>
        <tr>
          <td>Description</td>
          <td><textarea id="description" name="description" class="clear_data"></textarea></td>
        </tr>
      </table>
      
         <div class="remi_date_opt">
      
<div class="dymaic_am">
  <div class="12a amvalue twitle_num">12:00AM</div>
  <div class="12.30a amvalue">12:30AM</div>
  <div class="1a amvalue" >1:00AM</div>
  <div class="1.30a amvalue" >1:30AM</div>
  <div class="2a amvalue" >2:00AM</div>
  <div class="2.3a amvalue" >2:30AM</div>
  <div class="3a amvalue" >3:00AM</div>
  <div class="3.3a amvalue" >3:30AM</div>
  <div class="4a amvalue" >4:00AM</div>
  <div class="4.3a amvalue" >4:30AM</div>
  <div class="5a amvalue" >5:00AM</div>
  <div class="5.30a amvalue" >5:30AM</div>
  <div class="6a amvalue" >6:00AM</div>
  <div class="6.30a amvalue" >6:30AM</div>
  <div class="7a amvalue" >7:00AM</div>
  <div class="7.30a amvalue" >7:30AM</div>
  <div class="8a amvalue" >8:00AM</div>
  <div class="8.3a amvalue" >8:30AM</div>
  <div class="9a amvalue" >9:00AM</div>
  <div class="9.3a amvalue" >9:30AM</div>
  <div class="10a amvalue" >10:00AM</div>
  <div class="10.3a amvalue" >10:30AM</div>
  <div class="11a amvalue" >11:00AM</div>
  <div class="11.3a amvalue" >11:30AM</div>
   <div class="12p amvalue" >12:00PM</div>
  <div class="12.3p amvalue" >12:30PM</div>
  <div class="1p amvalue" >1:00PM</div>
  <div class="1.3p amvalue" >1:30PM</div>
  <div class="2p amvalue" >2:00PM</div>
  <div class="2.3p amvalue" >2:30PM</div>
  <div class="3p amvalue" >3:00PM</div>
  <div class="3.30p amvalue" >3:30PM</div>
  <div class="4p amvalue" >4:00PM</div>
  <div class="4.30p amvalue" >4:30PM</div>
  <div class="5p amvalue" >5:00PM</div>
  <div class="5.3p amvalue" >5:30PM</div>
  <div class="6p amvalue" >6:00PM</div>
  <div class="6.3p amvalue" >6:30PM</div>
  <div class="7p amvalue" >7:00PM</div>
  <div class="730p amvalue" >7:30PM</div>
  <div class="8p amvalue" >8:00PM</div>
  <div class="8.30p amvalue" >8:30PM</div>
  <div class="9p amvalue" >9:00PM</div>
  <div class="9.30p amvalue" >9:30PM</div>
  <div class="10p amvalue" >10:00PM</div>
  <div class="10.30p amvalue" >10:30PM</div>
  <div class="11.p amvalue" >11:00PM</div>
  <div class="11.30p amvalue" >11:30PM</div>
</div>
 
     
                     </div>
      <div class="clear"></div>
    </div>
    <!------------//// POP CONTENT GENERAL OPTION END ---------> 
    <!--------/// REPEAT CONTENT ------>
    <div class="repeat_cal">
      <table class="table">
        <tr>
          <td class="repeat_heading" >Repeat</td>
          <td><select class="select_repeat" id="frequency" name="frequency">
              <option value="no">No repetitions</option>
              <option value="DAILY">Daily</option>
              <option value="WEEKLY">Weekly</option>
              <option value="MONTHLY">Monthly</option>
              <option value="YEARLY">Yearly</option>
            </select></td>
        </tr>
        <tr class="repeat_every_day">
          <td class="repeat_heading">Repeat every:</td>
          <td>
          	  <select  title="Repeat every 1 days" class="select_every_day" name="interval">
              <option value="1" class="year_default" >1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
              <option value="8">8</option>
              <option value="9">9</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
              <option value="13">13</option>
              <option value="14">14</option>
              <option value="15">15</option>
              <option value="16">16</option>
              <option value="17">17</option>
              <option value="18">18</option>
              <option value="19">19</option>
              <option value="20">20</option>
              <option value="21">21</option>
              <option value="22">22</option>
              <option value="23">23</option>
              <option value="24">24</option>
              <option value="25">25</option>
              <option value="26">26</option>
              <option value="27">27</option>
              <option value="28">28</option>
              <option value="29">29</option>
              <option value="30">30</option>
            </select>
            <label id="inter_lebel">days </label></td>
        </tr>
        
        
        <tr class="repeat_on_week">
          <td class="repeat_heading"> Repeat on:* </td>
          <td>
                  <span class="repeat_week">
                        <input   type="checkbox"  value="SU" class="sunday" name="daysofweek">
                        <label  title="Sunday">S</label>
                  </span> 
                  <span class="repeat_week">
                        <input  type="checkbox"  value="MO" class="monday" name="daysofweek">
                        <label  title="Monday">M</label>
                  </span>
                  <span class="repeat_week">
                        <input   type="checkbox"  value="TU" class="tuesday" name="daysofweek">
                        <label  title="Tuesday">T</label>
                  </span>
                  <span class="repeat_week">
                        <input   type="checkbox"   value="WE" class="wednesday" name="daysofweek">
                        <label  title="Wednesday">W</label>
                  </span> 
                  <span class="repeat_week">
                        <input   type="checkbox"  value="TH" class="thursday" name="daysofweek">
                        <label  title="Thursday">T</label>
                  </span> 
                  <span class="repeat_week">
                        <input   type="checkbox" value="FR" class="friday" name="daysofweek">
                        <label  title="Friday">F</label>
                  </span> 
                  <span class="repeat_week">
                        <input   type="checkbox"  value="SA" class="saturday" name="daysofweek">
                        <label  title="Saturday">S</label>
                  </span>
            </td>
        </tr>
        <tr class="repeat_day_month">
            <td class="repeat_heading">Repeat by:</td>
            <td><input type="radio" name="month"  checked="checked" style="margin-left: 0;"/><label>day of the month</label><input type="radio" name="month" /><label>day of the week</label></td>
        </tr>
        <tr><td>&nbsp;</td></tr>
        <tr class="end_recur" style="margin-top: 5px;display: none;">
          <td style="vertical-align: top;">
          		<label>End Recurrence:</label>
          </td>
          <td >
          		<input type="radio" name="count" class="count_radio" value="never" />
            	<label>Never</label><br><br>
          		<input type="radio" name="count" class="count_radio" value="count" checked="checked" />
            	<label>After</label>
            	<input type="number"  class="positiveNumber count_input clear_data" name="count_input" id="count_input" value="10" />
            	<label>occurrence(s)</label>
            	<br><br>
          		<input type="radio"  name="count" class="count_radio " value="until"   />
            	<label>On</label>
            	<input type="text"    class="until_input popupDatepicker clear_data" name="until_input" id="until_input"/>
            	<br>
          </td>
        </tr>
<!--         <tr class="disable_row_count"> -->
<!--           <td class="repeat_heading">Count</td> -->
<!--           <td><input type="number"  class="count_input clear_data" name="count" id="count" /></td> -->
<!--         </tr> -->
<!--         <tr class="disable_row_until"> -->
<!--           <td class="repeat_heading">Until</td> -->
<!--           <td><input type="text" disabled="disabled" class="until_input popupDatepicker clear_data" name="until" id="until"/></td> -->
<!--         </tr> -->
        <tr style="display: none;">
           <td valign="top" class="summary repeat_heading">Summary</td>
           <td>
                <div class="weekly_summary">
                    <span class="weekly_text" style="display:none;">Weekly on</span>
                    <span class="weekly_on_text">
                         <span class="weekend_end"></span>
                         <span class="weekly_on"></span>
                         <span class="weekend_name"></span>
                         <span class="month_days_date"></span>
                         <span class="month_message"></span>
                         <span class="year_message">
                             <span class="year_date"></span>
                             <span class="year_month_name"></span>
                         </span>
                         <span class="every_select_day_date_1"></span>
                         
                         <span class="every_select_day_date"></span>
                         <span class="every_select_week_date"></span>
                         
                    </span>
                    <div class="clear"></div>
                 </div></td>
        </tr>
      </table>
    </div>
    <!-----------/// REPEAT CONTENT END HERE ------> 
    <!-----------// REMINDER CAL STRED --------->
    <div class="reminder_cal">
                       <table id="defaultremindertable" >
        <tr>
          <td colspan="2" class="new_line">This event has no configured reminders</td>
        </tr>
        <tr>
          <td colspan="2" class="new_line_1">New reminder:</td>
        </tr>
       <!--  <tr>
          <td><img src="images/new_reminder.png" /></td>
          <td >
            <select id="remindertype1"><option>Email</option><option>Pop-up</option></select>
            <input type="text" class="reminder_time" id="remindertime1"/>
            <select class="sel_remin_opt" id="reminderduration1"><option class="minutes">minutes</option><option class="hours">hours</option>
            <option class="day">days</option><option class="week">weeks</option></select>
            </td>
            <td class="delete_reminder"><img src="images/tool.png" /></td>
        </tr> -->
        <tr><td colspan="3"><span>Add New</span></td></tr> 
      </table>
      <input type="hidden" id="counter" value="0" class="clear_data"/>
      <input type="hidden" id="reminderdata" name="reminderdata" class="clear_data" />
      <input type="hidden" id="uid" name="uid" class="clear_data"/>
             <div class="append_reminder">
                    <table class="new_reminder" >
           
                   </table>
             </div>

      
      <div class="clear"></div>
    </div>
    <!-----------/// REMINDER CAL END HERE ------->
    <!-----------//// WORKGROUP CAL ------------>
     <div class="workgroup_cal">
     <input type="hidden" id="hid_org_Status" class="clear_data" />
    <table>  <tr>
                   <td >Status</td>
                   <td width="33px"></td>
                     <td>         <select id="organizerStatus" name="organizerStatus" class="clear_data" style="width:286px">
                      				<option value="NEEDS-ACTION">Need Action</option>
                                    <option value="ACCEPTED">Confirmed</option>
                                    <option value="TENTATIVE">Tentative</option>
                                    <option value="DECLINED">Declined</option>
                              </select>
                   </td>
                </tr> </table>
             <table>
                <tr >
                   <td>Add Guest </td>
                   <td colspan="2">
                            <input type="text" class="add_guest_name" class="clear_data" />
                   </td>
                    <td colspan="3" >
                           <div class="add_guest" >Add</div>
                   
                   </td>
                </tr>
                
                </table>
           <div class="add_guest_content">
                <table class="heading_guest">
                <tr class="add_name">
                   <td >
                   		<input type="checkbox" class="select_guest"  /> 
                   		<div class="attendee_status "></div>
                   		<input type="hidden" class="old_guest clear_hiiden" name="oldguest" />
                   		<input type="hidden" class="new_guest clear_hiiden" name="newguest" />
                   		<input type="hidden" class="deleted_guest clear_hiiden" name="deletedguest" />
                   		<span>Guests</span></td>
                   <td><div class="close_guest_1"><img src="images/tool.png" /></div></td>
                </tr>
                </table>
                     <div class="guest_content_11">
                            <table class="append_guest">
                              	<tbody>
<!--                             		<tr class="add_name row_guest"> -->
<!--                             			<td><input type="checkbox"><div class="attendee_status "><div class=""></div></div> <span>amar@silvereye.in</span></td> -->
<!--                             			<td><div class="close_guest" id="n`amar@silvereye.in"><img src="images/tool.png"></div></td> -->
<!--                            			</tr> -->
                                </tbody>
                        	</table>
                     </div>
             </div>
    
    
    </div>
    <!------------/// WORKGROUP CAL END HERE ------->
    <div class="clear"></div>
    <div class="delete_event" style="float: left;color: #000;width: 20px !important;display: none;"><img src="images/tool.png"></div>
    <div id="div_send_invite_mail" style="padding: 8px; float: left;font-size: 13px">
    	<input type="checkbox" value="true" id="send_invite_mail" name="send_invite_mail" style="float:left;margin-top: 2px" checked/><span>Send Invite & Updates</span>
    </div>
    <div class="cancel_cal">Cancel</div>
    <div class="save_cal saveEvent" >Save</div>
    <div class="clear"></div>
  </div>
  <!-------------/// POPUP CREATE CONTENT BOX END HERE ------------> 
 </form> 
</div>






<!-- ---   End here  -->

<script type="text/javascript">
//$(document).ready(function(){
//function saveEvent()
//	$(document.body).on('click', '.saveEvent' ,function(){ 
$( ".saveEvent" ).click(function(){
//{
	var hid_repeatstatus=$("#hid_repeatstatus").val();
	var hid_recureventid=$("#hid_recureventid").val();
	if(!hid_repeatstatus )
		{
	//$("#eventform").validate();    
	$( "#eventform" ).validate();
	 var isvalidate =  !($('#calendar_select').val() == "Select Calendar") && $("#eventform").valid();
	 //isvalidate=$("#eventform").valid();
     if(!isvalidate)
     {
         return false;
         //alert(getvalues("myform"));
     }
     document.getElementById('action_gif').style.display= 'block';
	setreminder();
	//var x=$('daysofweek').val;
	
	var values ="";
	
	$.each($("input[name='daysofweek']:checked"), function() {
// 	  values.push($(this).val());
	  if(values!="")
	  values=values+"`"+$(this).val() ;
	  else
	  values=$(this).val() ;
	});
		
	var eventdetails= form2object('eventform');
  	// alert(eventdetails);
  	eventdetails['daysofweek'] = values;
  	
   var value = JSON.stringify(eventdetails, null, '\t');
// 	alert(value.);
	var send_invite_mail = $('input[name=send_invite_mail]:checked').length;// $("#send_invite_mail").attr('checked') ;
	var deletedguest = $('.deleted_guest').val();
	var hid_uid=$('#hid_cal_id').val();
	var hid_organizer=$('#hid_organizer').val();
	var hid_org_Status=$('#hid_org_Status').val();
	var organizerSt=$('#organizerStatus').val();
	if($('.delete_event').css('display')=='none')
	{
		hid_uid="";
	}
	if(hid_uid==null)
		{
		hid_uid="";
		}
// 	alert("deleted_guest :  "+deletedguest);
var justedited="No";
if(!hid_recureventid)
	{
	justedited="No";
	hid_recureventid="";
	}
else
	{
	justedited="Yes";
	}
	var hid_cal_path=$("#hid_cal_path").val();
	$.ajax({
        type:"get",
        data:{
        	'eventdetails':value,'send_invite_mail':send_invite_mail,'deletedguest':deletedguest, 'hid_uid':hid_uid, 'hid_organizer': hid_organizer ,'organizerSt': organizerSt, "hid_org_Status": hid_org_Status,"justedited":justedited, "hid_recureventid": hid_recureventid, "hid_cal_path": hid_cal_path 
        },
        url:"createEvent",
        async:true,
        dataType: "json",
        success: function(data){
        	
        	if(data.error =="true")
       		{
        		if(data.endDateError == "true")
       			{
       				$('#enddate').val("");
       				$('#enddate').addClass("error");
       				$('#endtime').val("");
       				$('#endtime').addClass("error");
       				showmsg("alert","Event end  can not be samll than start !");
       			}
        		if(data.untilDateError == "true")
    			{
        			$('.until_input').val("");
    				$('.until_input').addClass("error");
    				showmsg("alert","Repeat until can not be small than event end !");
    			}
        		if(data.orgError == "true")
        			{
        			showmsg("alert","You do not have permission to update this event!");
        			}
       		}
        	else if(data.id=="-1")
        		{

       			
   			 $('#hid_cal_id').val("");
   	        	$('#hid_organizer').val("");
       		 showmsg("alert","Bad request occurred!");
       		// $('.calender_pop').hide(50);
	    		  $('.web_dialog_overlay').hide();
	    		  $('.clear_data').val("");
		    		 $('#defaultremindertable').html('<table ><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
		    		 $('.new_reminder').html("");
		    		 $('.calender_pop').hide(500);
		    		  $('.web_dialog_overlay').hide();
		    		  $('#frequency').val("no");
		    			 ///////////
		//     			 $('.repeat_every_day').hide();
		    			 $('.repeat_every_week').hide();
		    			 $('.repeat_every_month').hide();
		    			 $('.repeat_every_year').hide();
		    			 $('.repeat_on_week').hide();
		    			 $('.repeat_day_month').hide();
		    			 ////////////
		    			/*  $('input[name=daysofweek]').each(function() {
		    								 $(this).removeAttr('checked');
		    							 	}
		    							 ); */
		    			 $('#allday').removeAttr('checked');
       		 refreshCalendar();
//        		 location.href="/webmail/calendar";
        		}
       		else if(data.c!="")
    		 {
       			
    			 $('#hid_cal_id').val("");
    	        	$('#hid_organizer').val("");
        		 showmsg("alert","Event updated successfully !");
        		// $('.calender_pop').hide(50);
	    		  $('.web_dialog_overlay').hide();
	    		  $('.clear_data').val("");
		    		 $('#defaultremindertable').html('<table ><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
		    		 $('.new_reminder').html("");
		    		 $('.calender_pop').hide(500);
		    		  $('.web_dialog_overlay').hide();
		    		  $('#frequency').val("no");
		    			 ///////////
		//     			 $('.repeat_every_day').hide();
		    			 $('.repeat_every_week').hide();
		    			 $('.repeat_every_month').hide();
		    			 $('.repeat_every_year').hide();
		    			 $('.repeat_on_week').hide();
		    			 $('.repeat_day_month').hide();
		    			 ////////////
		    			/*  $('input[name=daysofweek]').each(function() {
		    								 $(this).removeAttr('checked');
		    							 	}
		    							 ); */
		    			 $('#allday').removeAttr('checked');
        		 refreshCalendar();
//         		 location.href="/webmail/calendar";
         	
    		 }
        	 else
        		 {

    			 $('#hid_cal_id').val("");
    	        	$('#hid_organizer').val("");
        		
        		// $('.calender_pop').hide(50);
	    		  $('.web_dialog_overlay').hide();
	    		  $('.clear_data').val("");
		    		 $('#defaultremindertable').html('<table ><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
		    		 $('.new_reminder').html("");
		    		 $('.calender_pop').hide(500);
		    		  $('.web_dialog_overlay').hide();
		    		  $('#frequency').val("no");
		    			 ///////////
		//     			 $('.repeat_every_day').hide();
		    			 $('.repeat_every_week').hide();
		    			 $('.repeat_every_month').hide();
		    			 $('.repeat_every_year').hide();
		    			 $('.repeat_on_week').hide();
		    			 $('.repeat_day_month').hide();
		    			 ////////////
		    			/*  $('input[name=daysofweek]').each(function() {
		    								 $(this).removeAttr('checked');
		    							 	}
		    							 ); */
		    			 $('#allday').removeAttr('checked');
		    	 showmsg("alert","Event created successfully !");
        		 refreshCalendar();
        		 
        		 /* 
        	
        		 $('#hid_cal_id').val("");
             	$('#hid_organizer').val("");
            	 var filename=data.name.split("`");
            	 
            		 $('#calendar').fullCalendar('removeEvents', data.id);
            		 
            	 	 if(Object.keys(data.repeatdates).length!=0)
            		 {
            		 	for(j=0;j<Object.keys(data.repeatdates).length;j++)
            			{
            			 var eventData=null;   			 
            			 eventData = {
                			     id:data.id ,
                			     c:data.id,
                				 name:data.name ,
        						title: data.summary,
        						start:data.repeatdates[Object.keys(data.repeatdates)[j]],
        						end:data.erepeatdates[Object.keys(data.erepeatdates)[j]],
        						color:data.color,
        						timezone:'UTC'
        					    };
            			 $('#calendar').fullCalendar('renderEvent', eventData, true);
            			 }
            		 }
            	 	else
            		 {
            		 var eventData=null;
            	  	 eventData = {
            			     id:data.id ,
            			     c:data.id,
            				 name:data.name ,
    						title: data.summary,
    						start:  data.start,
    						end :data.end,
    						color:data.color,
    						timezone:'UTC'
    					    };
            	  
            	  showmsg("alert","Event created successfully !");
            	 // To display Loading on top of page
            	  $('#calendar').fullCalendar('renderEvent', eventData, true);
            	 // To display Pop-up that the evnt has been crreated
            		 }
	        	 $('.clear_data').val("");
	    		 $('#defaultremindertable').html('<table ><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
	    		 $('.new_reminder').html("");
	    		 $('.calender_pop').hide(500);
	    		  $('.web_dialog_overlay').hide();
	    		  $('#frequency').val("no");
	    			 ///////////
	//     			 $('.repeat_every_day').hide();
	    			 $('.repeat_every_week').hide();
	    			 $('.repeat_every_month').hide();
	    			 $('.repeat_every_year').hide();
	    			 $('.repeat_on_week').hide();
	    			 $('.repeat_day_month').hide();
	    			 ////////////
	    		
	    			 $('#allday').removeAttr('checked'); */
            	
        		 }
        	 document.getElementById('action_gif').style.display= 'none';
                      
        },
        error: function (xhr, ajaxOptions, thrownError) {
            
        //	alert(xhr.status);
         //   alert(thrownError);
            location.reload();
          }
    });
    
		}
	else
		{
		
		if(hid_repeatstatus=="Yes")
			{
			saveRepeatEvent();
			}
	
		}
});

function saveRepeatEvent()
{

	$('.web_dialog_overlay').show(); 
	repevent_save_conf("confirm","This is a repeating event.");
}

function repevent_save_conf(type,msg) {
   	//alert(id);
         var n = noty({
           text        : msg,
           type        : type,
           theme       : 'relax',
           dismissQueue: false,
           layout      : 'center',
           theme       : 'defaultTheme',
           buttons     : (type != 'confirm') ? false : [
               {addClass: 'btn btn-primary', text: 'Just This', onClick: function ($noty) {
   				
                 $noty.close();
               editRepEvent("one");
               
                 $('.web_dialog_overlay').hide(); 
               }
               },
               {addClass: 'btn btn-primary', text: 'Entire Series', onClick: function ($noty) {
      				
                   $noty.close();
                 
                   editRepEvent("all");
                   $('.web_dialog_overlay').hide(); 
                 }
                 },
               {addClass: 'btn btn-danger', text: 'Cancel', onClick: function ($noty) {
                   $noty.close();
                   $('.web_dialog_overlay').hide(); 
               
               }
               }
           ]
       });
      
           
           //console.log(type + ' - ' + n.options.id);
           return n; 
            
       }

function editRepEvent(repaction)
{
	//alert(repaction);
	var hid_startdate=$('#hid_startdate').val();
	var hid_enddate=$('#hid_enddate').val();
	var hid_starttime=$('#hid_starttime').val();
	var hid_endtime=$('#hid_endtime').val();

	//$("#eventform").validate();    
	$( "#eventform" ).validate();
	 var isvalidate =  !($('#calendar_select').val() == "Select Calendar") && $("#eventform").valid();
	 //isvalidate=$("#eventform").valid();
     if(!isvalidate)
     {
         return false;
         //alert(getvalues("myform"));
     }
     document.getElementById('action_gif').style.display= 'block';
	setreminder();
	//var x=$('daysofweek').val;
	
	var values ="";
	
	$.each($("input[name='daysofweek']:checked"), function() {
// 	  values.push($(this).val());
	  if(values!="")
	  values=values+"`"+$(this).val() ;
	  else
	  values=$(this).val() ;
	});
		
	var eventdetails= form2object('eventform');
  	// alert(eventdetails);
  	eventdetails['daysofweek'] = values;
  	
   var value = JSON.stringify(eventdetails, null, '\t');
// 	alert(value.);
	var send_invite_mail = $('input[name=send_invite_mail]:checked').length;// $("#send_invite_mail").attr('checked') ;
	var deletedguest = $('.deleted_guest').val();
	var hid_uid=$('#hid_cal_id').val();
	var hid_organizer=$('#hid_organizer').val();
	var hid_org_Status=$('#hid_org_Status').val();
	var organizerSt=$('#organizerStatus').val();
	if($('.delete_event').css('display')=='none')
	{
		hid_uid="";
	}
	if(hid_uid==null)
		{
		hid_uid="";
		}
// 	alert("deleted_guest :  "+deletedguest);
var hid_cal_path=$("#hid_cal_path").val();
	$.ajax({
        type:"get",
        data:{
        	'eventdetails':value,'send_invite_mail':send_invite_mail,'deletedguest':deletedguest, 'hid_uid':hid_uid, 'hid_organizer': hid_organizer ,'organizerSt': organizerSt, "hid_org_Status": hid_org_Status, "repaction": repaction, "hid_startdate": hid_startdate, "hid_enddate": hid_enddate, "hid_starttime": hid_starttime, "hid_endtime": hid_endtime, "hid_cal_path": hid_cal_path
        },
        url:"editRepeatJustOrAll",
        async:true,
        dataType: "json",
        success: function(data){
        	
        	if(data.error =="true")
       		{
        		if(data.endDateError == "true")
       			{
       				$('#enddate').val("");
       				$('#enddate').addClass("error");
       				$('#endtime').val("");
       				$('#endtime').addClass("error");
       				showmsg("alert","Event end  can not be samll than start !");
       			}
        		if(data.untilDateError == "true")
    			{
        			$('.until_input').val("");
    				$('.until_input').addClass("error");
    				showmsg("alert","Repeat until can not be small than event end !");
    			}
        		if(data.orgError == "true")
        			{
        			showmsg("alert","You do not have permission to update this event!");
        			}
       		}
       		else if(data.c!="")
    		 {
       			
    			 $('#hid_cal_id').val("");
    	        	$('#hid_organizer').val("");
        		 showmsg("alert","Event updated successfully !");
        		// $('.calender_pop').hide(50);
	    		  $('.web_dialog_overlay').hide();
	    		  $('.clear_data').val("");
		    		 $('#defaultremindertable').html('<table ><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
		    		 $('.new_reminder').html("");
		    		 $('.calender_pop').hide(500);
		    		  $('.web_dialog_overlay').hide();
		    		  $('#frequency').val("no");
		    			 ///////////
		//     			 $('.repeat_every_day').hide();
		    			 $('.repeat_every_week').hide();
		    			 $('.repeat_every_month').hide();
		    			 $('.repeat_every_year').hide();
		    			 $('.repeat_on_week').hide();
		    			 $('.repeat_day_month').hide();
		    			 ////////////
		    			/*  $('input[name=daysofweek]').each(function() {
		    								 $(this).removeAttr('checked');
		    							 	}
		    							 ); */
		    			 $('#allday').removeAttr('checked');
        		 refreshCalendar();
//         		 location.href="/webmail/calendar";
         	
    		 }
        	 else
        		 {
        		 $('#hid_cal_id').val("");
 	        	$('#hid_organizer').val("");
     		
     		// $('.calender_pop').hide(50);
	    		  $('.web_dialog_overlay').hide();
	    		  $('.clear_data').val("");
		    		 $('#defaultremindertable').html('<table ><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
		    		 $('.new_reminder').html("");
		    		 $('.calender_pop').hide(500);
		    		  $('.web_dialog_overlay').hide();
		    		  $('#frequency').val("no");
		    			 ///////////
		//     			 $('.repeat_every_day').hide();
		    			 $('.repeat_every_week').hide();
		    			 $('.repeat_every_month').hide();
		    			 $('.repeat_every_year').hide();
		    			 $('.repeat_on_week').hide();
		    			 $('.repeat_day_month').hide();
		    			 ////////////
		    			/*  $('input[name=daysofweek]').each(function() {
		    								 $(this).removeAttr('checked');
		    							 	}
		    							 ); */
		    			 $('#allday').removeAttr('checked');
		     showmsg("alert","Event created successfully!");
     		 refreshCalendar();
        		 
        		 /* 
        	
        		 $('#hid_cal_id').val("");
             	$('#hid_organizer').val("");
            	 var filename=data.name.split("`");
            	 
            		 $('#calendar').fullCalendar('removeEvents', data.id);
            		 
            	 	 if(Object.keys(data.repeatdates).length!=0)
            		 {
            		 	for(j=0;j<Object.keys(data.repeatdates).length;j++)
            			{
            			 var eventData=null;   			 
            			 eventData = {
                			     id:data.id ,
                			     c:data.id,
                				 name:data.name ,
        						title: data.summary,
        						start:data.repeatdates[Object.keys(data.repeatdates)[j]],
        						end:data.erepeatdates[Object.keys(data.erepeatdates)[j]],
        						color:data.color,
        						timezone:'UTC'
        					    };
            			 $('#calendar').fullCalendar('renderEvent', eventData, true);
            			 }
            		 }
            	 	else
            		 {
            		 var eventData=null;
            	  	 eventData = {
            			     id:data.id ,
            			     c:data.id,
            				 name:data.name ,
    						title: data.summary,
    						start:  data.start,
    						end :data.end,
    						color:data.color,
    						timezone:'UTC'
    					    };
            	  
            	  showmsg("alert","Event created successfully !");
            	 // To display Loading on top of page
            	  $('#calendar').fullCalendar('renderEvent', eventData, true);
            	 // To display Pop-up that the evnt has been crreated
            		 }
	        	 $('.clear_data').val("");
	    		 $('#defaultremindertable').html('<table ><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
	    		 $('.new_reminder').html("");
	    		 $('.calender_pop').hide(500);
	    		  $('.web_dialog_overlay').hide();
	    		  $('#frequency').val("no");
	    			 ///////////
	//     			 $('.repeat_every_day').hide();
	    			 $('.repeat_every_week').hide();
	    			 $('.repeat_every_month').hide();
	    			 $('.repeat_every_year').hide();
	    			 $('.repeat_on_week').hide();
	    			 $('.repeat_day_month').hide();
	    			 ////////////
	    		
	    			 $('#allday').removeAttr('checked');
            	 */
        		 }
        	 document.getElementById('action_gif').style.display= 'none';
                      
        },
        error: function (xhr, ajaxOptions, thrownError) {
            
        //	alert(xhr.status);
         //   alert(thrownError);
            location.reload();
          }
    });
    
}

function setreminder()
{
	reminderdata="";
	var loopterminator=$('#counter').val().split(",");


	 for(j=0;j<loopterminator.length;j++)
		{
		   if($('#remindertime'+loopterminator[j]).val()!=undefined)
			reminderdata=reminderdata+$('#remindertype'+loopterminator[j]).val()+"`"+$('#remindertime'+loopterminator[j]).val()+"`"+$('#reminderduration'+loopterminator[j]).val()+";";

		} 


	$('#reminderdata').val(reminderdata);

	
	}
//});



</script>
<script type="text/javascript">
$(document.body).on('click','.mange_sharing',function(){
	$("#action_gif").css("display","block");
	var cal_file_name = $('#hid_cal_file_name').val();
	var cal_disp_name = $('#hid_disp_calnm').val();
	$(".initive_people").val("");
	$(".initive_people").removeClass("error")
 //	alert("cal_file_name : " + cal_file_name);
// 	document.getElementById('action_gif').style.display= 'block';
	var requestPage="${pageContext.request.contextPath}/sharingPopup";
	jQuery.get(requestPage,
            {
                    'cal_file_name' : cal_file_name
            },
            function( data ) 
            {
        	$("#action_gif").css("display","none");
        
        	
        	$( "#srd_cnt" ).html( data );
        	if(!(!cal_disp_name))
    		{
    		$("#spopuphead").html("("+cal_disp_name+")");
    		}
        	$(".send_share, .cancel_share").css("background-color",$("#hid_mail_bgColor").val());
			 if($('.sharing_mange').css('display')=='none')
			 {
				 $('.sharing_mange').show();
				 $('.web_dialog_overlay').show();
				 
		     }
             
            });
});



    function assignSinglePermissions() {
//        alert("hiiiiiiiiii");
        var count=0;
       
       
        
        var userss="";
        var valuess="";
       
        $(".userforshare").each(function() {
            count++;
            var counter=0;
            var userforshare=$(this).val();
            
//             alert("userforshare :"+userforshare);
            $(".permissionsforshare").each(function() {
                counter++;
                
                if(count==counter){
                var permissionforshare=$(this).val();
//                 alert("permissionforshare :"+permissionforshare);
                var values=userforshare+","+permissionforshare;
                var vale = values;
                var valu = vale.split(",");
                var user = "";
                vale = valu[1];
                user = valu[0];
               
                var sourcePaths=$('#hid_cal_file_name').val();
                docPath1=sourcePaths;
               
                //alert(docPath1);
                value = valu[1];
            //    alert("user = "+user+"  value = "+value);
            if(user!=""){
                if(userss=="" && valuess=="")
                	{
            		userss=user;
                	valuess=value;
                	}
                else
                	{
                	{
                		userss+=","+user;
                    	valuess+=","+value;
                    }
                	}
                }
            }
            });
        });
         
        $.ajax({
            type : "GET",
            url : "${pageContext.request.contextPath}/assignSinglePermissionCal",
            data : {
                'user' : userss,
                'value' : valuess,
                'multipath':docPath1,
            },
            contentType : "application/json",
            async : true,
            success : function(data) {
                if(data=="true"){location.href="ajaxTrue";}else{
            	  
                    $('.sharing_mange').hide();
					 $('.web_dialog_overlay').hide();
				 showmsg("alert","Calendar Shared Successfully !");
                   
                }
            }
        });

       
    }
   



    function shareDelete(remid,sno){
       
        var sourcePaths=$('#hid_cal_file_name').val();
       //alert(sourcePaths);
        $.ajax({
           type : "GET",
           url : "${pageContext.request.contextPath}/removeAssignedPermissionCal",
           data : {
               'folderPath' : sourcePaths,
               'remid' : remid,
               'sno' : sno
           },
           contentType : "application/json",
           async : true,
           success : function(data) {
               if(data=="true"){location.href="ajaxTrue";}else{
            showmsg("alert","Calendar Sharing Removed Successfully !");
               }
           }
       });
        
        
        
    }
                
</script>
<!---------//// CREATE THE CALENDER STRED HERE --------------->
<div class="create_cal">
<!---/// HEADER ----->
<div class="pop_header">New Calendar <span>X</span> </div>
<!-----/// HEADER END HERE ------->
<!----/// CONTENT STRED HERE ------>
<div class="inn_con_cal">

        <table>
     <tbody><tr>
          <td>Display Name</td>
           <td><input type="text" class="dis_name clear_data" id="calendarname" ></td>
     </tr>
     <tr>
          <td>Display Color</td>
           <td class="new_cal_color clear_data" ><div class="color_1 color_find" ><span> &#x2713 </span></div>
                         <div class="color_2 color_find" > <span> &#x2713 </span></div>
                         <div class="color_3 color_find" ><span> &#x2713 </span></div>
                         <div class="color_4 color_find" > <span> &#x2713 </span></div>
                         <div class="color_5 color_find" > <span> &#x2713 </span></div>
                         <div class="color_6 color_find" > <span> &#x2713 </span></div>
                         <div class="clear"></div>
                         <div class="color_7 color_find" > <span> &#x2713 </span></div>
                         <div class="color_8 color_find"> <span> &#x2713 </span></div>
                         <div class="color_9 color_find" > <span> &#x2713 </span></div>
                         <div class="color_10 color_find" > <span> &#x2713 </span></div>
                         <div class="color_11 color_find"> <span> &#x2713 </span></div>
                         <div class="color_12 color_find" > <span> &#x2713 </span></div></td>
     </tr>
  </tbody></table>
   <input type="hidden" id="calendarcolor" class="clear_data"/>      


</div>
  <div class="clear"></div>
   
    <div class="cancel_cal_1">Cancel</div>
     <div class="save_cal_1" onclick="saveCalendar()">Add</div>
    <div class="clear"></div>

<!------/// CONTENT END HERE ---------->


</div>

<script type="text/javascript">
function saveCalendar()
{
	
	var calid=$('#calendarname').val();
	var color=$('#calendarcolor').val();
	if(calid==null || calid=="")
		{
		 showmsg("alert","Please enter Calendar name.");
		}
	else if(color==null || color=="")
		{
		 showmsg("alert","Please select Calendar color.");
		}
	else
		{
	 document.getElementById('action_gif').style.display= 'block';
	 jQuery.get("createCalendar",
			{
		     "calendarid":calid,
		      "calcolor":color
			},
			function(data)
			{
				$('.web_dialog_overlay').hide();
			 $('.create_cal').hide();
			 $('.clear_data').val("");
			
			  $('span.select_color').removeClass('select_color');
			  showmsg("alert","Calendar created");
			  document.getElementById('action_gif').style.display= 'none';
				if(data!=null && data!="")
				{	
				   $('.my_calender_content >ul').append('<li id="'+data+'"><div class="color_calender" style="background-color:'+ color +';"></div><span>'+ calid +'</span style="color: black;"></span><div class="cal_option"><img src="images/cal-open.png"></div><div class="clear"></div></li>');
				   $("#calendar_select").append('<option value="'+data+'">'+calid+'</option>');
					 
				}		
				}
			); 	 
	// $('.clear_data').val("");
	

	}
}
	/* function getcalendarcolor()
	{alert("hello");
	alert($(this).css("background-color"));
		 var calendarcolor=this.css("background");
		 alert(calendarcolor);
	} */
</script>

<!-----------/// CREATE THE CALENDER END HERE ---------------->

<!------------/// CREATE CALENDER POP END HERE -------------->


   <!---/// Upload photo ---------> 
        <div class="pop_for_image_upload" >
                 <h1>Change Your Profile Picture <span>X</span></h1>
                      
                      
                       <form id="uploadImage" >
			         
			       
                      <table>
                      
                           <tbody><tr>
                              <td colspan="2"> <input type="file" id="upl1" name="upl1"  /></td>
                           </tr>
                          <tr>
 							<td colspan="2" style="  font-size: 11px; ">
 							Accepted image size is up to 100KB and File type must be png or jpg only. </td>
                           </tr>
                           <tr>
                              <td></td>
                              <td><input type="button" onclick="changeLDAPImg()" value="Upload" class="file_upload search_button"></td>
                           
                           </tr>
                      </tbody></table>
                  </form>
                 </div>
         
         
         
         
         
         <!----------/// CALENDER SHARE POP STARED HERE ----------->
<div class="sharing_mange">
         <h1>Sharing <span id="spopuphead"></span>
<div class="mange_can_top">X</div>
</h1>

<div class="table_append">
<table class="append_tr">
<tr class="share_bottom">
  <td colspan="2">Who has access</td>
</tr>
<tr>
   
<td colspan="2" class="own_row">
<span class="you_share"><%=mailid_bd %> </span>
<%
String arrr[]=mailid_bd.split("@");

%>
<input type="hidden" id="hid_dom" value="<%=arrr[1] %>">
<input type="hidden" id="hid_dom_id" value="<%=mailid_bd %>">
<span class="text_right">Is Owner </span>
</td> 
                       </tr>
                       <tr>
                         <td colspan="2" class="invite_people_list">
                            <div id="srd_cnt">
                             
                             
                             
                                
                                      </div>
                            
                         </td>
                       </tr>
                       
                       <tr class="share_bottom">
                           <td colspan="2">Invite people:</td>
                       </tr>
                       <tr>
                           <td><input type="text"  class="initive_people " id="share_input"/></td>
                           <td>
                              <div class="can_edit">
                                     
                                      <div class="can_edite">
                                            <select  class="permissionsforshare npselect">
                                               <option value="ur">Read </option>
                                                <option value="uw">Read/Write </option>
                                                <option value="us">All</option> 
                                           </select>
                                      </div>
                                       <div class="share_more">Add</div>
                                     
                              </div>
                          </td>
                       </tr>
                   </table>
				
                   <div class="clear"></div>
                     <div class="share_save_close">
               <!--   <div class="your_self"><input type="checkbox"/><a href="#">Notify via Email </a></div> -->
                    <div class="share_save_box">
                              <div class="cancel_share mange_can">Cancel</div>
                               <div class="send_share cancel_share" id="shareButtonId" onclick="assignSinglePermissions()" >Save</div>
                               <div class="clear"></div>
                    </div>
                 <div class="clear"></div>
                 </div>
                 <div class="clear"></div>
                 </div>
                 
                 
</div>
<!------------/// CALENDER SHARE POP END HERE ------------->
         
         
         <!-------/// CALENDAR SETTING PAGE STARED HERE ----------->
<div class="cal_setting">
   <h1>Calendar Settings<div class="mange_can_top cal_set_top">X</div></h1>

        <!--------// CALENDAR TAB 1----->
<div class="cal_setting_1">
   <table width="100%">
      <tr> 
         <td class="cal_heding">Calendar Name:</td>
          <td><input type="text" class="cal_setting_input" id= "cal_name" /></td>
      </tr>
       <tr> 
         <td class="cal_heding">Calendar Owner:</td>
          <td id="owner_id"></td>
      </tr>
       <tr> 
         <td class="cal_heding">Description:</td>
          <td><textarea id="cal_desc"></textarea></td>
      </tr>
       <tr> 
         <td class="cal_heding">Calendar Address:</td>
          <td>
          <div id="cal_link"></div>
          <div class="clear"></div>
         <!--  <span>This is the address for your calendar</span> -->
          </td>
      </tr>
       <!-- <tr> 
         <td class="cal_heding">Calendar Address:</td>
          <td class="cal_setting_img"><img src="images/xml.gif" /><img src="images/ical.gif" /><img src="images/html.gif" />
          <div class="clear"></div>
          <span>This is the address for your calendar. No one can use this link unless you have made your calendar public.</span></td>
      </tr>
       <tr> 
         <td class="cal_heding">Event notifications:</td>
          <td>
                <span class="cal_default"> By default, notify me via </span>
                    <div class="cal_notification">
                        <div class="cal_row">
                                 <select class="cal_setting_select">
                                 <option value="1">Email</option>
                                 <option value="3">Pop-up</option>
                                 </select>
                                 <input  class="text_cal_notifi" type="text"/>
                                 <select  class="cal_setting_select" >
                                 <option >minutes</option>
                                 <option >hours</option>
                                 <option >days</option>
                                 <option >weeks</option>
                                 </select>
                                  <span> before each event </span> 
                                  <div class="clear"></div>
                         </div>
                   </div>
                 
                  <div class="clear"></div>
                  <span class="add_notification">Add a notification</span>
           </td>
      </tr> -->
      
   </table>
</div>
<!--------// CALENDER TAB 1 END ---->

                 <div class="share_save_close">
                    <div class="share_save_box">
<!--                               <div class="delete_event" style="float: left;">Delete</div> -->
                              <div class="cancel_share mange_can">Cancel</div>
                               <div class="send_share cancel_share" id="update_calendar">Save</div> 
                               <div class="clear"></div>
                    </div>
                 <div class="clear"></div>
                 </div>
                 <div class="clear"></div>
                 
                 
</div>
<!-------/// CALENDAR SETTING END HERE ------------>
         

<%-- <div class="sharing_mange">
   <h1>Share Contacts <div class="mange_can_top">X</div></h1>
   <div class="table_append">
                   <table class="append_tr">
                       
                       <tr>
                           <td>Calendars</td>
                           <td >
                           <%
                          %>
                           <select id="confldr" >
                           <%
                           for(File cl: calendarlist)
                           {  	            	
                           	String []filenamelist=cl.getFileName().split("`");
                           	String calendarfilename=filenamelist[0]; 
           					%>
           					<option value="/calendar/<%=cl.getFileName()%>"><%=calendarfilename %></option>
           					<%
           	        	 
           				}
                           %>
                           </select>
                          </td>
                       </tr>
                      
                      <tr>
                           <td>Share Email Id</td>
                           <td >
                           <select id="shareid">
                           <%
                           WebmailClient webmailClient=(WebmailClient)request.getAttribute("webmailClient");
                           HttpSession hs_c=request.getSession();
                           String id=(String)hs_c.getAttribute("id");
                           String pass=(String)hs_c.getAttribute("pass");
           				String ldapurl=(String)hs_c.getAttribute("ldapurl");
           				String ldpabase=(String)hs_c.getAttribute("ldapbase"); 
           				//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
           				GetVCFLdapDirResponse getdirres=webmailClient.getLdapDirectory(ldapurl, id, pass, ldpabase);
           		 		List<VCFLdapDirAtt> ldapDirList=getdirres.getGetVCFLdapDirByParentFile().getVCFLdapDirListResult().getVCFLdapDirList();
           				for(VCFLdapDirAtt ulst : ldapDirList)
           				{
           					if(!ulst.getContactEmail().equalsIgnoreCase(id))
           					{
           					%>
           					<option value="<%=ulst.getContactEmail() %>"><%=ulst.getContactEmail() %></option>
           					<%
           					}
           				}
                           %>
                           </select>
                           </td>
                       </tr>
                       
                   </table>
                   <div class="clear"></div>
   
        </div>
          
                 <div>
                       
                       <div class="cancel_share mange_can">Cancel</div>
                       <div onclick="sharecalendar()" class="send_share">Share</div>
            
  </div>
                 
                 
</div> --%>
<script type="text/javascript">

/* function sharecalendar() {
	document.getElementById('action_gif').style.display= 'block';
	var e = document.getElementById("shareid"); 
	   	var shareid = e.options[e.selectedIndex].value;
	   //	alert(shareid);
	   	var e1 = document.getElementById("confldr"); 
	   	var confldr = e1.options[e1.selectedIndex].value;
	  	//alert(confldr);
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/sharecalendar",
		data : {
			'shareid' : shareid,
			'confldr': confldr
			
		},
		contentType : "application/json",
		success : function(data) {
			
			document.getElementById('action_gif').style.display= 'none';
			//alert("Contact has been shared.");
			  $('.sharing_mange').hide();
              $('.web_dialog_overlay').hide();
        
		}
	});

} */
</script>
<div class="import_con">
<h1>Import contacts
     <div class="import_can_top">x</div>
</h1>
<div class="import_details">

<p>Please select a iCalendar(.ics) file to upload:</p>
<input type="hidden" id="hid_cal_name" />
<p><input type="file" id="upl_con" name="upl_con" /></p>
<div class="submit_right">
   
    <div class="import search_button" >Import</div>
    <div class="cancel_import search_button">Cancel</div>
</div>
<div class="clear"></div>
</div>
<div class="clear"></div>
</div>



<div class="cal_showdetail" >
   <h1>Calendar Details<div class="mange_can_top cal_set_top">X</div></h1>

        <!--------// CALENDAR TAB 1----->
<div class="cal_setting_1">
   <table width="100%">
      <tbody><tr> 
         <td class="cal_heding">Calendar Name:</td>
          <td id="srd_cal_nm"></td>
      </tr>
       <tr> 
         <td class="cal_heding">Calendar Owner:</td>
          <td id="srd_owner_id"></td>
      </tr>
       <tr> 
         <td class="cal_heding">Permission:</td>
          <td id="srd_cal_per"></td>
      </tr>
     <tr> 
         <td class="cal_heding">Description:</td>
          <td id="share_cal_desc"></td>
      </tr>
       <tr> 
         <td class="cal_heding">Calendar Address:</td>
          <td>
          <div id="share_cal_link"></div>
          <div class="clear"></div>
         <!--  <span>This is the address for your calendar</span> -->
          </td>
      </tr>
       
      
   </tbody></table>
</div>
<!--------// CALENDER TAB 1 END ---->

                 <div class="share_save_close">
                    <div class="share_save_box">
<!--                               <div class="delete_event" style="float: left;">Delete</div> -->
                              <div class="cancel_share mange_can cal_detail_stop" >Cancel</div>
                              
                               <div class="clear"></div>
                    </div>
                 <div class="clear"></div>
                 </div>
                 <div class="clear"></div>
                 
                 
</div>


<div class="cal_showdetail_sub" >
   <h1>Manage Subscription<div class="mange_can_top cal_set_top">X</div></h1>

        <!--------// CALENDAR TAB 1----->
<div class="cal_setting_1" id="cal_sub_data">
  
</div>
<!--------// CALENDER TAB 1 END ---->

                 <div class="share_save_close">
                    <div class="share_save_box">
<!--                               <div class="delete_event" style="float: left;">Delete</div> -->
                              <div class="cancel_share mange_can cal_detail_stop" >Cancel</div>
                              
                               <div class="clear"></div>
                    </div>
                 <div class="clear"></div>
                 </div>
                 <div class="clear"></div>
                 
                 
</div>
<!--------// CALENDER TAB 1 END ---->

    



<script type="text/javascript">
function takeCalSubsAction(id,subs) {
	 document.getElementById('action_gif').style.display= 'block';
	$.ajax({
		    		type : "GET",
		    		url : "${pageContext.request.contextPath}/takeActionCalSubs",
		    		data : {
		    			  'calendar' : id,
		    			  'subs': subs
		    			   },
		    		/* contentType : "application/json", */
		    		success : function(data) {
		    			 if(subs=="unsubs")
		    				 {
		    				try
		    				{
		    				 var child = document.getElementById(id);
							child.parentNode.removeChild(child);
		    				}
		    				catch (e) {
		    					try
		    					{
		    					id=id.replace("@","%40");
		    					var child = document.getElementById(id);
								child.parentNode.removeChild(child);
		    					}
		    					catch (ee) {}
							}
		    				 }
		    			 else if (subs=="subs")
	    				 {
		    				if(!data)
		    					{
		    					
		    					}
		    				else if(data!="true" && data!="false")
		    					 {
		    					  $(".other_calender_content").html(data)
		    					  try
		    					  {
		    						  $('.left_tab_content ul li').hover(
		    									function() {
		    										$(this).css('border-left',
		    												"3px solid " + $("#hid_mail_bgColor").val())
		    									}, function() {
		    										$(this).css('border-left', "3px solid #fff")
		    										/* $(".my_con_active").css("border-left","3px solid "+$("#hid_mail_bgColor").val())	 */
		    									});
		    					  }
		    					  catch(e){}
		    					 }
	    				 }
		    		
		    			document.getElementById('action_gif').style.display= 'none';
		    			refreshCalendar();
		    		}
	});
}

$(document.body).on('click','.import',function(){
	 $('.import_con').hide();
	 $('.web_dialog_overlay').hide();
	/* var success = generate('alert');
 	$.noty.setText(success.options.id, 'not implemented for Demo.'); // same as alert.setText('Text Override')
 	setTimeout(function () {    $.noty.close(success.options.id);  }, 5000);    */
 	var calnm=$("#hid_cal_name").val();
	 var oMyForm = new FormData();
	  
		
	 		 var upl_con = document.getElementById("upl_con")   ;
			 var nm= "";
			 if(upl_con.files.length>=1)
				 {
			     nm= upl_con.files[0].name;
		  		oMyForm.append("file0", upl_con.files[0]);
				 }
		  		
		  	
		  		
		  	var arr = nm.split(".");
		  
		  	 
		  if((arr[arr.length-1]!="ics" &&  arr[arr.length-1]!="ICS"))
			  {
			  var msg="File type must be ics only.";
			  var success = generate('alert');
		      	$.noty.setText(success.options.id, msg); // same as alert.setText('Text Override')
		      	setTimeout(function () {    $.noty.close(success.options.id);  }, 4000); 
			  }
		    else
			  {
		  		
			  document.getElementById('action_gif').style.display= 'block';
			 
			  
				$.ajax({
		    		type : "GET",
		    		url : "${pageContext.request.contextPath}/setActiveCal",
		    		data : {
		    			  'calnm' : calnm
		    			   },
		    		contentType : "application/json",
		    		success : function(data) {
		    			
		             
		    		
			  
		  $.ajax({
		    url: '${pageContext.request.contextPath}/importCalendar',
		    data: oMyForm,
		    dataType: 'text',
		    processData: false,
		    contentType: false,
		    type: 'POST',
		    success: function(data){
		    //	alert(data)
		   
		    	document.getElementById('action_gif').style.display= 'none';
		    if(data!="true")
		    	{
		    	 var success = generate('alert');
		      	$.noty.setText(success.options.id, data); // same as alert.setText('Text Override')
		      	setTimeout(function () {    $.noty.close(success.options.id);  }, 4000); 
		    	}
		    else
		    	{
		    	refreshCalendar();
		    	}
		    }
		  }); 
		  
		  
			
		    		}
		    	});
				  
			  }
		  
});

$(document.body).on('click', '.manage_unsubscribe' ,function(event){
	
	var calr=$("#hid_cal_file_name").val();
	document.getElementById('action_gif').style.display= 'block';
	$.ajax({
			type : "GET",
			url : "setCalUnsubscribe",
			data : {'calendar':calr},
			/* contentType : "application/json", */
			success : function(data) {
				document.getElementById('action_gif').style.display= 'none';
				if(data=="true")
					{
					var child = document.getElementById(calr);
					child.parentNode.removeChild(child);
					refreshCalendar();
					showmsg("alert","Unsubscribed successfully!");
					}
			}
});
});
function calImport()
{
	  var calr=$("#hid_cal_file_name").val();
	  $("#hid_cal_name").val(calr);
	 // alert(calr);
	  $(".import_con").show();
	  $('.web_dialog_overlay').show(); 
}

function calExport()
{
	  var calr=$("#hid_cal_file_name").val();
	//alert(calr);
	window.open( '${pageContext.request.contextPath}/exportICSDownload?filenm='+calr, '_blank' );
	
}

  function deletecalendar()
  {
	  var calr=$("#hid_cal_file_name").val();
	  $('.web_dialog_overlay').show(); 
	  
	  task_del_conf("confirm","Are you sure to delete this calendar?",calr);
  }
  function task_del_conf(type,msg,uid) {
	   	//alert(id);
	         var n = noty({
	           text        : msg,
	           type        : type,
	           theme       : 'relax',
	           dismissQueue: false,
	           layout      : 'center',
	           theme       : 'defaultTheme',
	           buttons     : (type != 'confirm') ? false : [
	               {addClass: 'btn btn-primary', text: 'Ok', onClick: function ($noty) {
	   				
	                 $noty.close();
	                 $("#action_gif").css("display","block");
	                 $('.web_dialog_overlay').hide(); 
	                 
	                 document.getElementById('action_gif').style.display= 'block';
	                 $.ajax({
	     				type : "GET",
	     				url : "${pageContext.request.contextPath}/deleteCalendar",
	     				data : {'calendar':uid},
	     				contentType : "application/json",
	     				success : function(data)
	     				{
	     					refreshCalendar();
	     					document.getElementById(uid).remove();
	     						
	     				},
	     				error: function (xhr, ajaxOptions, thrownError) {
	     			     //   alert(xhr.status);
	     			      }
	     		    }) ;
	                
	               }
	               },
	               {addClass: 'btn btn-danger', text: 'Cancel', onClick: function ($noty) {
	                   $noty.close();
	                   $('.web_dialog_overlay').hide(); 
	               
	               }
	               }
	           ]
	       });
	      
	           
	           //console.log(type + ' - ' + n.options.id);
	           return n; 
	            
	       }
  </script>




     <!------/// CALENDER MORE OPTION ------->
                <div class="calender_option" >
                   <ul>
                  <li class="cal_setting_opt">Calendar Settings</li>
                   <li class="mange_sharing">Manage Sharing</li>
                    <li class="create_event">Create Event</li>
                     <li class="calImport" onclick="calImport()">Import</li>
                      <li class="calExport" onclick="calExport()">Export</li>
<!--                     <li>Display only this Calendar</li> -->
                    <li class="del_cal" onclick="deletecalendar()">Delete Calendar</li>
                        <li class="calender_color">
                         <div class="color_1 color_find"><span> &#x2713 </span></div>
                         <div class="color_2 color_find"> <span> &#x2713 </span></div>
                         <div class="color_3 color_find"> <span> &#x2713 </span></div>
                         <div class="color_4 color_find"> <span> &#x2713 </span></div>
                         <div class="color_5 color_find"> <span> &#x2713 </span></div>
                         <div class="color_6 color_find"> <span> &#x2713 </span></div>
                         <div class="clear"></div>
                         <div class="color_7 color_find"> <span> &#x2713 </span></div>
                         <div class="color_8 color_find"> <span> &#x2713 </span></div>
                         <div class="color_9 color_find"> <span> &#x2713 </span></div>
                         <div class="color_10 color_find"> <span> &#x2713 </span></div>
                         <div class="color_11 color_find"> <span> &#x2713 </span></div>
                         <div class="color_12 color_find"> <span> &#x2713 </span></div>
                    
                    
                    </li>
                     <li class="custom_color">
                       <table>
                          <tr>
                              <td><input type="text" class="custom" /></td>
                          </tr>
                       </table>
                    
                    </li>
<!--                     <li class="choose_custom">Choose custom color <span class="custom_check"> &#x2713 </span></li> -->
                  </ul>
                  <div class="clear"></div>
                </div>
                
                
 <div class="calender_option_shared" >
                   <ul>
                  <li class="show_cal_detail">Show Details</li>
                   <li class="manage_unsubscribe">Unsubscribe</li>
                    <li class="create_event shared_create_event">Create Event</li>
                    <li class="calImport sharedcalimp" onclick="calImport()">Import</li>
                    <li class="calExport" onclick="calExport()">Export</li>

                    <li class="del_cal_shared" onclick="deletecalendar()">Delete Calendar</li>
                    

                  </ul>
                  <div class="clear"></div>
                </div> 
                <!----------/// CALENDER MORE OPTION END HERE ---------->
  <div class="web_dialog_overlay"></div>
 <div class="web_dialog_overlay_on_popup"></div>
</body>
</html>