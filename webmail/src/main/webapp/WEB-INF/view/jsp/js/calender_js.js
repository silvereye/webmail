
// JavaScript Document
	

jQuery(document).ready(function() {
	
	  /// NEW CODE 
	$('.all_day').click(function(){
		
//		  $('.sel_val').val('');
//		  
		     if($('.all_day').prop('checked')) {
				// something when checked
		    	 $('.date_stared').val('');
		    	 				
				//  alert('This is Checked !');
				  $('.date_stared').addClass('hide_time');
				
			     } else {
				// something else when not
				//alert('This is Not Checked !');
				$('.date_stared').removeClass('hide_time');
			}
		
		
		
		}); 

	 
	 
	 /// NEW CODE END HERE 
	
	
	function isEmail(email) 
	{
		  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		  return regex.test(email);
	}
	
	
		
	
	// FOR HTML
	$('html').click(function(){

		  $('.active_cal').removeClass('active_cal');
		  $('.dymaic_am').hide();



		});
		 
	//For HTML
	
	
	
	
	
	
	
	 // 	My Calendars Started
	$(document.body).on('click', '.cal_option' ,function(event){
		//$('.cal_option').click(function(){
			//alert('Hi');
			
			$('li.select_arrow_li').removeClass('select_arrow_li');
			/// NEW CODE 
			
			//alert('Hi');
			var cal_top = $(this).offset().top;
			var cal_left = $(this).offset().left;
			//alert(cal_top);
			//alert(cal_left);
			$('.active_cal').removeClass('active_cal');
			$('.calender_option').addClass('active_cal');
			$('.active_cal').css('left',cal_left+1);
			$('.active_cal').css('top',cal_top+11);
			
			
			/// NEW CODE END HERE 
			//alert($(this).parent().attr('id'));
			
			       $('div.hi').removeClass('hi');
				   $('div.select_arrow').removeClass('select_arrow');
				  $("#hid_cal_file_name").val($(this).parent().attr('id'));
				  $("#hid_disp_calnm").val($(this).parent().children('span').text());
			       var cho_arrow =  $(this).parent().parent().parent().children('.calender_option').addClass('show_option');
				   $(this).parent().children('.color_calender').addClass('select_arrow');
				   $(this).parent().addClass('select_arrow_li');
				   //  var cho_arrow =  $(this)
				   var cho_box_left = $(this).offset().left ;
				   var cho_box_top = $(this).offset().top ;
				    $('.show_option').css('top',cho_box_top - 85);
			        $('.show_option').css('left',cho_box_left - 64); 
					 $('.show_option').slideToggle("slow");
					  $('.other_calender_option').hide("slow");
					  
					  var cal_file = $("#hid_cal_file_name").val();
					 // alert(cal_file)
					  $(".del_cal").show();
					  
					  var cond1=cal_file.indexOf("/calendar/", cal_file.length - "/calendar/".length) !== -1;
					  var cond2=cal_file.indexOf("/calendar", cal_file.length - "/calendar".length) !== -1;
					 
					  if(cond1 || cond2)
					  {
						  $(".del_cal").hide();
					  }
					  // TEST 
					 // var new_color_find = $(this).parent().children().find('.select_arrow').background;
					//  alert(new_color_find);
					  // TEST
					//  alert(select_arrow_color);
					//  $('div.color_calender').find('.select_arrow').alert('hi');
					  
					//var find_color = $('.select_arrow').attr('style');
					//alert(find_color);
					
					/// TEST STTARED HERE 
					
				    var find_select_color = $('.select_arrow').css('background-color');
					var find_other_1 = $('.color_1').css('background-color');
					var find_other_2 = $('.color_2').css('background-color');
					var find_other_3 = $('.color_3').css('background-color');
					var find_other_4 = $('.color_4').css('background-color');
					var find_other_5 = $('.color_5').css('background-color');
					var find_other_6 = $('.color_6').css('background-color');
					var find_other_7 = $('.color_7').css('background-color');
					var find_other_8 = $('.color_8').css('background-color');
					var find_other_9 = $('.color_9').css('background-color');
					var find_other_10 = $('.color_10').css('background-color');
					var find_other_11 = $('.color_11').css('background-color');
					var find_other_12 = $('.color_12').css('background-color');
					if(find_select_color == find_other_1)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_1>span').addClass('select_color');
						
				     }else if(find_select_color == find_other_2)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_2>span').addClass('select_color');
						
				     }else if(find_select_color == find_other_3)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_3>span').addClass('select_color');
						
				     }else if(find_select_color == find_other_4)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_4>span').addClass('select_color');
						
				     } else if(find_select_color == find_other_5)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_5>span').addClass('select_color');
						
				     }else if(find_select_color == find_other_6)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_6>span').addClass('select_color');
						
				     } else if(find_select_color == find_other_7)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_7>span').addClass('select_color');
						
				     } else if(find_select_color == find_other_8)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_8>span').addClass('select_color');
						
				     }else if(find_select_color == find_other_9)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_9>span').addClass('select_color');
						
				     }else if(find_select_color == find_other_10)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_10>span').addClass('select_color');
						
				     }else if(find_select_color == find_other_11)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_11>span').addClass('select_color');
						
				     }else if(find_select_color == find_other_12)
					{
						$('span.select_color').removeClass('select_color');
						$('.color_12>span').addClass('select_color');
						
				     }
				     else 
						{
							$('span.select_color').removeClass('select_color');
							
							
					     }
					
					/// TEST END HERE 
					
					
					
					/// TEST 
					
					$('.sp-choose').addClass('custom_color_find');
					event.stopPropagation();
					
					// TEST 
					
					     
					
					 
			
			});
	//  My Calendars End 
	
	$(document.body).on('click', '.cal_option_share' ,function(event){
		//$('.cal_option').click(function(){
			//alert('Hi');
			
			$('li.select_arrow_li').removeClass('select_arrow_li');
			/// NEW CODE 
			
			//alert('Hi');
			var cal_top = $(this).offset().top;
			var cal_left = $(this).offset().left;
			
			var woner=$(this).parent().children('#hid_srd_cal_won').val();
			var srdcalnm=$(this).parent().children('#hid_srd_cal_nm').val();
			var calper=$(this).parent().children('#hid_srd_cal_per').val();
			$("#srd_cal_nm").html(srdcalnm);
			$("#srd_owner_id").html(woner);
			$("#srd_cal_per").html(calper);
			if(calper!="All")
				{
				$(".del_cal_shared").hide();
				}
			else
				{
				$(".del_cal_shared").show();
				$("#srd_cal_per").html("Full Access");
				}
			if(calper=="Read")
				{
				$(".shared_create_event").hide();
				$(".sharedcalimp").hide();
				}
			else
				{
				$(".shared_create_event").show();
				$(".sharedcalimp").show();
				}
	
			
			$('.active_cal').removeClass('active_cal');
			$('.calender_option_shared').addClass('active_cal');
			$('.active_cal').css('left',cal_left+1);
			$('.active_cal').css('top',cal_top+11);
			
			
			
			       $('div.hi').removeClass('hi');
				   $('div.select_arrow').removeClass('select_arrow');
				  $("#hid_cal_file_name").val($(this).parent().attr('id'));
				  $("#hid_disp_calnm").val($(this).parent().children('span').text());
			       var cho_arrow =  $(this).parent().parent().parent().children('.calender_option_shared').addClass('show_option');
				   $(this).parent().children('.color_calender').addClass('select_arrow');
				   $(this).parent().addClass('select_arrow_li');
				   //  var cho_arrow =  $(this)
				   var cho_box_left = $(this).offset().left ;
				   var cho_box_top = $(this).offset().top ;
				    $('.show_option').css('top',cho_box_top - 85);
			        $('.show_option').css('left',cho_box_left - 64); 
					 $('.show_option').slideToggle("slow");
					  $('.other_calender_option').hide("slow");
					  
					  
					
					
				   
					event.stopPropagation();
					
					// TEST 
					
					     
					
					 
			
			});
	
	//3333333333333333333333333333
	
	
	$(document.body).on('click', '.delete_event' ,function(){
//		alert("Delete event");
		var uid = $(this).attr('id');
		var calname = $(this).attr('name');
		var hid_repeatstatus=$("#hid_repeatstatus").val();
		var hid_recureventid=$("#hid_recureventid").val();
		if(!hid_repeatstatus && !hid_recureventid)
			{
			
			$('.web_dialog_overlay').show(); 
			event_del_conf("confirm","Do you want to delete this event ?",uid,calname);
			}
		else
			{
			
			if(hid_repeatstatus=="Yes")
				{
				
				$('.web_dialog_overlay').show(); 
				event_del_repeating_conf("confirm","Do you want to delete this repeating event ?",uid,calname);
				
				}
			else if(hid_recureventid!=null && hid_recureventid!="")
				{
				$('.web_dialog_overlay').show(); 
				event_del_editedrep_conf("confirm","Do you want to delete this event ?",uid,calname,hid_recureventid);
				}
				
			
			}
	});
  
	function event_del_repeating_conf(type,msg,uid,calname) {
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
	                 repEventDeleteEditedOrJust(uid,calname,"just","");
	                 
	                 $('.web_dialog_overlay').hide(); 
	               }
	               },
	               {addClass: 'btn btn-primary', text: 'Entire Series', onClick: function ($noty) {
		   				
		                 $noty.close();
		                 basicEventDelete(uid,calname)
		                 
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
	
	function event_del_editedrep_conf(type,msg,uid,calname,hid_recureventid) {
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
	                 repEventDeleteEditedOrJust(uid,calname,"edited",hid_recureventid);
	                 
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
	
	function  repEventDeleteEditedOrJust(uid,calname,flg,hid_recureventid)
	{

		var hid_repStartDate=$("#hid_repStartDate").val();
		$("#action_gif").css("display","block");
	        $.ajax({
				type : "GET",
				url : "repEventDeleteEditedOrJust",
				data : {'uid':uid,'calendarname':calname,'flg': flg, 'hid_recureventid': hid_recureventid, 'hid_repStartDate': hid_repStartDate},
				contentType : "application/json",
				success : function(data) {
					$("#action_gif").css("display","none");
					var obj = jQuery.parseJSON(data);
					if(obj.success == "true")
					{
						
						showmsg("alert","Event deleted successfully !")
						try{
							$('.calender_pop').hide();
							  $('.web_dialog_overlay').hide();
							  $('.clear_data').val("");
								 $('#defaultremindertable').html('<table><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
					           $('.new_reminder').html("");
					           $('#frequency').val("no");
								 ///////////
//								 $('.repeat_every_day').hide();
								 $('.repeat_every_week').hide();
								 $('.repeat_every_month').hide();
								 $('.repeat_every_year').hide();
								 $('.repeat_on_week').hide();
								 $('.repeat_day_month').hide();
								 ////////////
								 $('#allday').removeAttr('checked');
								 $('#editeventheader').html("Create Event");
								 $('.delete_event').hide();
								// $('.delete_event').show();
								 $('.saveEvent').show();
								$(".gen_content >table >tbody>tr>td>input[type='text'], select, textarea").prop("disabled", false);
								$('#div_send_invite_mail').show();
								 $('#calendar_select').show();
									$('#calendar_select_np').val("");
									$('#calendar_select_np').hide();
						}
						catch (e) {
							// TODO: handle exception
						}
						refreshCalendar();
						$('.clear_data').val("");
					}
					else
					{
						showmsg("alert","You don't have permission to delete event!")
					}
					$('.calender_pop').hide(500);
	    		    $('.web_dialog_overlay').hide();
					
				},
				error: function (xhr, ajaxOptions, thrownError) {
			        alert(xhr.status);
			      }
		    }) ;
	}
	
  function event_del_conf(type,msg,uid,calname) {
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
	                 basicEventDelete(uid,calname)
	                 
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
	
	function basicEventDelete(uid,calname)
	{
	$("#action_gif").css("display","block");
        $.ajax({
			type : "GET",
			url : "deleteCalendearEvent",
			data : {'uid':uid,'calendarname':calname},
			contentType : "application/json",
			success : function(data) {
				$("#action_gif").css("display","none");
				var obj = jQuery.parseJSON(data);
				if(obj.success == "true")
				{
					
					showmsg("alert","Event deleted successfully !")
					try{
						$('.calender_pop').hide();
						  $('.web_dialog_overlay').hide();
						  $('.clear_data').val("");
							 $('#defaultremindertable').html('<table><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
				           $('.new_reminder').html("");
				           $('#frequency').val("no");
							 ///////////
//							 $('.repeat_every_day').hide();
							 $('.repeat_every_week').hide();
							 $('.repeat_every_month').hide();
							 $('.repeat_every_year').hide();
							 $('.repeat_on_week').hide();
							 $('.repeat_day_month').hide();
							 ////////////
							 $('#allday').removeAttr('checked');
							 $('#editeventheader').html("Create Event");
							 $('.delete_event').hide();
							// $('.delete_event').show();
							 $('.saveEvent').show();
							$(".gen_content >table >tbody>tr>td>input[type='text'], select, textarea").prop("disabled", false);
							$('#div_send_invite_mail').show();
							 $('#calendar_select').show();
								$('#calendar_select_np').val("");
								$('#calendar_select_np').hide();
					}
					catch (e) {
						// TODO: handle exception
					}
					refreshCalendar();
					$('.clear_data').val("");
				}
				else
				{
					showmsg("alert","You don't have permission to delete event !")
				}
				$('.calender_pop').hide(500);
    		    $('.web_dialog_overlay').hide();
				
			},
			error: function (xhr, ajaxOptions, thrownError) {
		        alert(xhr.status);
		      }
	    }) ;
	}
	
  $(document.body).on('change','.positiveNumber',function(){
	  var num = $(this).val();
	  if(!isNaN(num))
	  {
		 if(parseInt(num) < 1)
			 {
			 	$(this).val("1");
			 }
		 
	  }
	  else
	  {
		  $(this).val("1");
	  }
	  
	  
  });
	
	
	
	
	
	
	
	
	//TEST STARTED
	 $(document.body).on('click','.fc-content',function(){
		 
		 var uid=($(this).attr('id'));
		 $('.work_active').removeClass('work_active');
		 $('.repe').removeClass('repe_active');
		 $('.remind').removeClass('remind_active');
		 $('.gen_opt').addClass('work_active');
		 $('.gen_content').show();
		 $('.repeat_cal').hide();
		 $('.reminder_cal').hide();
		 $('.workgroup_cal').hide();
		 document.getElementById('action_gif').style.display= 'block';
		 var calendarname=($(this).attr('name'));
		 $('.delete_event').show();
		 $('.delete_event').attr('id',uid);
		 $('.delete_event').attr('name',calendarname);
		 $("#hid_cal_id").val(uid);
		 var recurrenceID=($(this).attr('recurrenceID'));
		 var repeatStatus=($(this).attr('repeatStatus'));
		 var enddt=($(this).attr('enddt'));
		 var startdt=($(this).attr('startdt'));
		// alert("recurrenceID---"+recurrenceID);
		 //alert("repeatStatus---"+repeatStatus);
		 /*alert($(this).children(event.start).html());*/
		 jQuery.get("geteventdetails",
					{
				     "uid":uid,
				      "calendarname":calendarname, "recurrenceID": recurrenceID, "repeatStatus": repeatStatus
					},
					function(data)
					{
						$('#editeventheader').html("Edit Event");
						$('#summary').val(data.summary);
						$('#hid_organizer').val(data.organizer);
						$('#location').val(data.location);
						try
						{
							$('#organizerStatus').val(data.yourStatus);
							$('#hid_org_Status').val(data.yourStatus);
						}
						catch (e) {
							
							// TODO: handle exception
						}
						try
						{
							$('#hid_cal_path').val(data.calendar);
							$('#hid_repeatstatus').val(data.repeatstatus);
							$('#hid_startdate').val(data.start);
							$('#hid_enddate').val(data.end);
							$('#hid_starttime').val(data.starttime);
							$('#hid_endtime').val(data.endtime);
							$('#hid_recureventid').val(data.recureventid);
							$('#hid_repStartDate').val(startdt);
						}
						catch (e) {
							
							// TODO: handle exception
						}
						$('#calendar_select').val(data.calendar);
						
						$('#description').val(data.description);
						$('#startdate').val(data.start);
						$('#enddate').val(data.end);
						$('#exdate').val(data.exdatelst);
						if(data.permission  == "read")
						{
							$('.delete_event').hide();
							$('.saveEvent').hide();
							$(".gen_content >table >tbody>tr>td>input[type='text'], select, textarea").prop("disabled", true);
							$('#div_send_invite_mail').hide();
							//$('#calendar_select').hide();
							//$('#calendar_select_np').val(data.calendar);
							//$('#calendar_select_np').show();
						}
						if(data.allday=="off")
						{
							$('#starttime').val(data.starttime);
							$('#endtime').val(data.endtime);
							$('.date_stared').removeClass('hide_time');
						}
						else
						{
							$("#allday").prop("checked", true);
							$('.date_stared').addClass('hide_time');
						}
						$('#clazz').val(data.clazz);
						
						if(repeatStatus=="Yes")
							{
							
							if(startdt.indexOf("T")>0)
							{
							var earr=startdt.split("T");
							$('#startdate').val(earr[0]);
							$('#starttime').val(earr[1].replace(" ",""));
							}
						else
							{
							$('#startdate').val(startdt);
							}
							
							if(enddt.indexOf("T")>0)
							{
							var sarr=enddt.split("T");
							$('#enddate').val(sarr[0]);
							$('#endtime').val(sarr[1].replace(" ",""));
							}
						else
							{
							$('#enddate').val(enddt);
							}
							
							}
						
						$('#frequency').val(data.frequency);
						if(!recurrenceID)
							{
							$('#frequency').removeAttr("disabled");
							}
						else
							{
							$('#frequency').attr("disabled","true");
							}
						if(data.count > 0)
						{
							$('.count_input').val(data.count);
							$('.count_input').removeAttr('disabled');
							$('#until_input').attr('disabled','disabled');
							$('input[name="count"][value="count"]').prop('checked', true);
						}
						else if(data.until !=  "")
						{
							$('.until_input').val(data.until);
							$('#until_input').removeAttr('disabled');
							$('#count_input').attr('disabled','disabled');
							$('input[name="count"][value="until"]').prop('checked', true);
						}
						else
						{
							$('#count_input').attr('disabled','disabled');
							$('#until_input').attr('disabled','disabled');
							$('input[name="count"][value="never"]').prop('checked', true);
						}
						$('.select_every_day').val(data.interval);
						//$('#allday').val(data.allday);
						
						/*if(data.interval>0)
							{*/
							if(data.frequency=="DAILY")
								
								$('#inter_lebel').html('day(s)');
							
							if(data.frequency=="WEEKLY")
								$('#inter_lebel').html('week(s)');
							if(data.frequency=="MONTHLY")
								$('#inter_lebel').html('month(s)');
							if(data.frequency=="YEARLY")
								$('#inter_lebel').html('yaer(s)');
							$('#interval').val(data.interval);
							/*}*/
						/////////////////////////////////weekdays
//						alert(data.frequency);
						if(data.frequency != null)
						{
							$('.end_recur').show();
						    $('.repeat_every_day').show();
						}
						else
						{
							$('.end_recur').hide();
						    $('.repeat_every_day').hide();
						}
						if(data.frequency=="WEEKLY")
						{
							$('.repeat_on_week').show();
							//$('.repeat_every_week').show();
							/*data.daylist.replace("/[/g","")
							data.daylist.replace("/]/g","");
							data.daylist.replace("/ /g","");*/
							var dayarray=data.daylist.split(",");
						 $('input[name=daysofweek]').each(function() {
								//	alert(this.value);
										   for(var i=0;i<dayarray.length;i++){
										 if( $(this).val()==dayarray[i]){
											 $(this).prop("checked", "checked");
										 	}
										 } 
										 });
						}
						 /////////////////////
						
						var reminderdata=data.reminderdata.split(";");
					//	alert(reminderdata.length);
						$('.new_reminder').html("");
						$('.new_reminder').html("").append('<tbody>');
						for(j=0;j<reminderdata.length;j++)
						{
							count++;
						   var reminders=reminderdata[j].split("`");
						   if(reminders[1]!= undefined)
							{
							   $('.new_reminder').append('<tr id="'+count+'"><td><img src="images/new_reminder.png" /></td><td><select id="remindertype'+count+'" class="reminder_type"><option>Email</option><option>Pop-up</option></select><input type="number" value="1" class=" positiveNumber reminder_time" id="remindertime'+count+'"  /><select id="reminderduration'+count+'" class="reminder_dur"><option value="M">minutes</option><option value="H">hours</option><option value="D">days</option><option value="W">weeks</option></select></td><td class="delete_reminder del_rem"><img src="images/tool.png" /></td></tr>');
							   $('#remindertype'+count).val(reminders[0]) ; 
							   $('#remindertime'+count).val(reminders[1]) ; 
							   $('#reminderduration'+count).val(reminders[2]) ; 
							   remindercounter=remindercounter+count+",";
							}
						} 
						$('.new_reminder').append('</tbody>');
						$('#counter').val(remindercounter);
						$('#uid').val(data.eventuid);
						
						$('.guest_content_11 >table.append_guest >tbody').html("");
						$('.clear_hiiden').val("");
						if(data.oldguest != "")
						{
							var guest = data.oldguest.split(",");
							
							$('.old_guest').val(guest);
							for(g = 0; g<guest.length;g++)
							{
								var name = guest[g];
								if(name != "")
								{
									var st_name = name.split("`");
									
									$('.guest_content_11 >table.append_guest >tbody').append('<tr class="add_name row_guest"><td ><input type="checkbox" /><div class="attendee_status "><div class=" status '+st_name[0]+'"></div></div>  <span>'+st_name[1]+'</span></td><td><div class="close_guest" id="'+name+'"><img src="images/tool.png" /></div></td></tr>')
								}
								
							}
						}
						
						document.getElementById('action_gif').style.display= 'none';
						if($('.calender_pop').css('display')=='none')
						{
					
							$('.calender_pop').css('display','block');
							 $('.web_dialog_overlay').show();
							 $('.calender_option').hide();
											
				
						}
					});

	        		             
			/*var cal_val = $(this).children('.fc-title').html();
			var cal_time = $(this).children('.fc-time').html();*/
			
			//$('.location').val("Hello");
			//alert(cal_val);
			 
			
			/*var showVar = '<c:out value="${test}"/>';
			   alert(showVar);*/
	 });	
		
	//TEST END
	
	   
	        
		///  TEST STRED HERE
		
			///  GENERAL OPTION STRED HERE 
			
			 $(document.body).on('focus','.date_stared',function(event){
				  
				   
					
					     $('.dymaic_am').show();
						 var leftPos = $(this).position().left;
						 var topPos = $(this).position().top;
						$('input.sel_val').removeClass('sel_val');
						 $('.remi_date_opt').css('left',leftPos);
						 $('.remi_date_opt').css('top',topPos);
						 $(this).addClass('sel_val');
						 if (event.stopPropagation) 
						 {
							 event.stopPropagation();
					     }
//						 alert(event.isPropagationStopped());
//						 	event.stopPropagation();
//						alert(event.isPropagationStopped());
				  });
		/// GENERAL OPTION END HERE 
		
		
		/// TEST FOR ON HIDE DIV ON CLICK BODY 
		
	
 
		
		
		//// TEST FOR ON HIDE DIV ON CLICK BODY
		
		
		
		
		
		
		
		
		
		
		
		
		///  TEST END HERE 
			/*
			  $(document.body).on('focus','.reminder_time',function(){
				  
				     // alert('Hi');
					
					     $('.dymaic_am').show();
						 var leftPos = $(this).offset().left;
						 var topPos = $(this).offset().top;
						// alert(leftPos);
						// alert(topPos);
						$('input.sel_val').removeClass('sel_val');
						 $('.remi_date_opt').css('left',leftPos-604);
						 $('.remi_date_opt').css('top',topPos-190);
						 $(this).addClass('sel_val');
						 
				  
				  
				  
				  });*/
			
			
	
				//	$(document.body).on('focusout','.reminder_time',function(){
			  /// $('.reminder_time').focusout(function() {
				   
				  // alert('By');
					
					     //$('.dymaic_am').hide();
				//	 setTimeout(function() { 
                    //    $('.dymaic_am').hide();}, 100); 
					
				//	});
			
	
	
	
	
	/// FIND THE VALUE 
	$(document.body).on('click','.amvalue',function(){
	
	      window.findhtm = $(this).html();
		  $('.sel_val').val(findhtm);
		  $('.dymaic_am').hide();
		  //$(this).parent().parent().parent().find('table.new_reminder').children().children().children().addClass('Hi');
	
	});
	
	
	/// 
	$(document.body).on('mouseover','.dymaic_am',function(){
		
		$('.dymaic_am').show();
		
		
		});
	
	
	

	
	     /// ++++++++++++++++++++++++++++ REMINDER ALERT STRED HERE 
		 
		 $('.save_cal').click(function(){
		 
		 var find_time = (new Date).getTime();
		 
		 
		    var d = new Date();    /// DATE 
			
		var weekday = new Array(7);
        weekday[0] = "Sunday";
        weekday[1] = "Monday";
		weekday[2] = "Tuesday";
		weekday[3] = "Wednesday";
		weekday[4] = "Thursday";
		weekday[5] = "Friday";
		weekday[6] = "Saturday";

         var x = weekday[d.getDay()]; /// SYSTEM WEEK
			
			
			var e =d.getDay();   /// SYSTEM DAY
            var y =d.getHours();   /// SYSTEM HOURS
            var z = d.getMinutes() /// SYSTEM MINITUE
		// alert(x);
		 // GET TIME FOR GIVEN MINITUE ,HOUR,DAY,WEEKAND
		 var find_re_time = $('.reminder_time').val();
		// alert(find_re_time);
		 /// FIND OPTION VALUE STRED
		 var sel_rem_opt = $('.sel_remin_opt').val();
		 var minitue = $('.minutes').val();
		 var hours = $('.hours').val();
		 var day = $('.day').val();
		 var week = $('.week').val();
		 /// FIND OPTION VALUE END HERE 
		// alert(sel_rem_opt);
		 
		 // SET REMINDER OPTION 
		 if(sel_rem_opt == minitue)
		 { 
		// alert('You Select MINITE ');
		 
		  var total_minute = parseInt(z) + parseInt(find_re_time);
		//  alert(z);
		//  alert(total_minute);
		      if( z == total_minute )
			  {
				 //  alert('Please Check The Mail Time !-say Reminder');
				  
				  }
		 
		 
		 }
		 else if(sel_rem_opt == hours)
		 {//alert('You Select Hours ')
		 ;}
		 else if(sel_rem_opt == day)
		 {//alert('You Select Day ')
		 ;}
		 else if(sel_rem_opt == week)
		 {//alert('You Select Weeks ')
		 ;}
		 
		 
		 
		 
		 // SET REMINDER END HERE 
		 
		 
		 
		 
		 
		 
		 });
		 /// +++++++++++++++++++++ REMINDER END HERE 
	
	    
	
	
	
	      // DELETE REMINDER STRED HERE 
		  $(document.body).on('click','.del_rem',function(){
			  
			 //    alert('Hi');
//				   var ask_delet = confirm('Are you sure you want to delete this reminder?');
//				   if(ask_delet==true)
//				   {
//					   var rid=($(this).parent().attr('id'));
//					   remindercounter=remindercounter.replace(rid+",","");
				//	   alert(remindercounter);
				//	   alert(($(this).parent().attr('id')));
//				   }
				      var row = $(this).parent();
			  $('.web_dialog_overlay_on_popup').show(); 
			  rem_del_conf("confirm","Do you want to delete this reminder ?",row);
		  });
			  function rem_del_conf(type,msg,row) {
				   var n = noty({
			           text        : msg,
			           type        : type,
			           theme       : 'relax',
			           dismissQueue: false,
			           layout      : 'center',
			           theme       : 'defaultTheme',
			           buttons     : (type != 'confirm') ? false : [
			               {addClass: 'btn btn-primary', text: 'Ok', onClick: function ($noty) 
			            	   {
				            	   row.remove();
				            	   $('.web_dialog_overlay_on_popup').hide(); 
				            	   $noty.close();
				               }
			               },
			               {addClass: 'btn btn-danger', text: 'Cancel', onClick: function ($noty) {
			                   $noty.close();
			                   $('.web_dialog_overlay_on_popup').hide(); 
			               
			               }
			               }
			           ]
			       });
			      
			  }
			  

		  
		  /// DELETE REMINDER END HERE 
		  
		  /// ADDD NEW REMINDER STRED HERE 
		  var count=0;
		  var remindercounter="";
		  /*$('.reminder_cal >table >tbody >tr >td >span').click(function(){*/
			  $(document.body).on('click','.reminder_cal >table >tbody >tr >td >span',function(){	
			//	alert(remindercounter);  /*remindercounter=$('#reminderdata').val();*/
			  count++;
			  remindercounter=remindercounter+count+",";
			//  alert(remindercounter);
			       
				   $('table.new_reminder').append('<tr id="'+count+'"><td><img src="images/new_reminder.png" /></td><td><select id="remindertype'+count+'" class="reminder_type"><option>Email</option><option>Pop-up</option></select><input type="number" value="1" class="positiveNumber reminder_time" id="remindertime'+count+'" /><select id="reminderduration'+count+'" class="reminder_dur"><option value="M">minutes</option><option value="H">hours</option><option value="D">days</option><option value="W">weeks</option></select></td><td class="delete_reminder del_rem"><img src="images/tool.png" /></td></tr>');
			    
			  $('#counter').val(remindercounter);
			  });
		  
		  /// ADDD NEW REMINDER END HERE 
		  
	
	
	
	    /// SELECT ALL GUEST 
		
		
		  $(".select_guest").click(function () {
								// alert('By');
                                  $('.guest_content_11 >table >tbody >tr >td >input:checkbox').prop('checked', this.checked);   
                             });
							 
							 
		  $('.close_guest_1').click(function(){
			  
			      // alert('Hi');
				  // $('.guest_content_11 >table >tbody >tr >td >input:checkbox').prop('checked', this.checked).hide();
				   var count_guest = $(".guest_content_11 >table >tbody >tr >td >[type='checkbox']:checked").length;
				  // alert(count_guest);
				   if(count_guest)
				   {
					    // alert('Hello This Test');
						 $('.row_guest').hide();
					   
					   }
			  
			  });
		
		
		/// SELECT ALL GUEST END HERE 
	
	
	
	
	
	
	   /// ADDD GUEST STRED HERE 
	   
	   $(document).on("click",".add_guest",function()
	   {
		   
		     var guestname = $('.add_guest_name').val();
			 
			 if( guestname !=''  && isEmail(guestname) )
			 {
				 $('.guest_content_11 >table.append_guest >tbody').append('<tr class="add_name row_guest"><td><input type="checkbox" /> <div class="attendee_status "><div class=""></div></div><span>'+guestname+'</span></td><td><div class="close_guest" id="'+guestname+'"><img src="images/tool.png" /></div></td></tr>')
				 var new_guests = $('.new_guest').val();
				 $('.new_guest').val(new_guests+","+guestname);
				 
				 $('.add_guest_name').val('');
			 }
			 else 
			 {
				 alert('Please enter valid email !');
			 }
		});
		   
	   /// DELETE GUEST HERE 
	   
	     $(document.body).on('click', '.close_guest >img' ,function()
		 {
				
				$('.web_dialog_overlay_on_popup').show(); 
				var guest_row = $(this).parent().parent().parent(); //.hide();
				var guest = $(this).parent().attr("id");
				guest_del_conf("confirm","Are you sure you want to delete this guest?",guest_row, guest);
			    
		 });
	     
	     function guest_del_conf(type,msg,guest_row, guest) {
			   var n = noty({
		           text        : msg,
		           type        : type,
		           theme       : 'relax',
		           dismissQueue: false,
		           layout      : 'center',
		           theme       : 'defaultTheme',
		           buttons     : (type != 'confirm') ? false : [
		               {addClass: 'btn btn-primary', text: 'Ok', onClick: function ($noty) 
		            	   {
			            	   var new_guests = $('.new_guest').val();
			            	   var deleted_guest = $('.deleted_guest').val();
			            	   if(new_guests.indexOf(guest) >= 0)
		            		   {
			            		   new_guests= new_guests.replace(','+guest,'');
			            		   $('.new_guest').val(new_guests);
		            		   }
			            	   var old_guests = $('.old_guest').val();
			            	   if(old_guests.indexOf(guest) >= 0)
		            		   {
			            		   old_guests= old_guests.replace(','+guest,'');
			            		   $('.old_guest').val(old_guests);
			            		   
			            		   deleted_guest = deleted_guest + ","+guest;
			            		   $('.deleted_guest').val(deleted_guest);
			            		   
		            		   }
			  				   
		            	   	   guest_row.remove();
			            	   $('.web_dialog_overlay_on_popup').hide(); 
			            	   $noty.close();
			               }
		               },
		               {addClass: 'btn btn-danger', text: 'Cancel', onClick: function ($noty) {
		                   $noty.close();
		                   $('.web_dialog_overlay_on_popup').hide(); 
		               
		               }
		               }
		           ]
		       });
		      
		  }
		  
			 
			 
			 
			 /// TEST STRAED HERE 
			 
			 
			 var confirmed = false;
function confirmDialog(obj)
{
    
}
			 
			 
			 /// TEST END HERE 
	   
	   
	   
	   /// ADD GUEST END HERE
	
	
	
	
	
	 
         /// CHOOSE CUSTOM COLOR 
		 
		 $('.choose_custom').click(function(event){
			 
			   
				if($('.custom_color').css('display')=='none')
				{

					$('.choose_custom >span').addClass('custom_check');
					$('.custom_color').css('display','block');
					$('.choose_custom').addClass('active_custom');
				//	alert('Hi');
			    }
				else {
					$('.choose_custom >span').removeClass('custom_check');
					 $('.custom_color').css('display','none');
					 $('.choose_custom').removeClass('active_custom');
					// $('.choose_custom').css('background','#fff;');
				//	 alert('Hihi');
					}
				event.stopPropagation();
			 });
		 
		 
		 
		 
	
	/// CALENDER TOP OPTION STRAED HERE
	
	
	// OPTION 1 STARTD
	$('.first_cal_option').click(function(){
		
		         if($('.create_cal').css('display')=='none')
				 {
					 
					 $('.web_dialog_overlay').show();
					 $('.create_cal').css('display','block');
					 
				  }
		
		});
		
		$('.create_cal >.pop_header >span').click(function(){
			
			     
			
			        $('.web_dialog_overlay').hide();
					 $('.create_cal').hide();
					 $('.clear_data').val("");
					  $('span.select_color').removeClass('select_color');
					 
					
					
			});
			
	  $('.cancel_cal_1').click(function(){
			
			     
			        $('.web_dialog_overlay').hide();
					 $('.create_cal').hide();
					 $('.clear_data').val("");
					  $('span.select_color').removeClass('select_color');
			
	  });
	  
	  
	 /* 
	  $('.save_cal_1').click(function(){
			 
	      // alert(displayColor);
		 //  alert();
		  // alert(applyOptions);
		  // alert(colorInput.value);
		  //[r,g,b] = displayColor;
		  var cre_cal_nam = $('.dis_name').val();
		 // alert(cre_cal_nam);
		   
		 //var cal_col_di = $('.color_calender').css('background',displayColor);
		   //find('.sp-preview-inner').addClass('hi');
		   $('.my_calender_content >ul').append('<li><div class="color_calender" style="background:'+ background +';"></div><span>'+ cre_cal_nam +'</span><div class="cal_option"><img src="images/cal-open.png"></div><div class="clear"></div></li>');
		    $('.create_cal').hide();
			$('.dis_name').val('');
			  $('.web_dialog_overlay').hide();
			  $('span.select_color').removeClass('select_color');
		  // alert(rgb);
	 
	 });*/

	
	
	// OPTION 2 STARTD
	
	
	
	
	// OPTION 3 STARTD 
	$('.create_event').click(function(){	
		// NEW CODE 
		
		var tdate = new Date();
		var getStartDate = [moment(tdate).format('YYYY-MM-DD')];
		var getStartedtime = [moment(tdate).format('hh:mmA')];
		$('.start_date').val(getStartDate);
		$('#starttime').val(getStartedtime);
		$('.end_date').val(getStartDate);
		$('#endtime').val(getStartedtime);
		$('.pop_tab ul li.remind_active').removeClass('remind_active');
		$('.pop_tab ul li.work_active').removeClass('work_active')
		$('.pop_tab ul li.repe_active').removeClass('repe_active')
		$('.gen_opt').addClass('gen_active');
		$('.workgroup_cal').hide();
		$('.reminder_cal').hide();
		$('.repeat_cal').hide();
		$('.gen_content').show();
		$('#calendar_select').val($("#hid_cal_file_name").val());
		tdate.setMonth(tdate.getMonth() + 1)
		getStartDate = [moment(tdate).format('YYYY-MM-DD')];
		$('.until_input').val(getStartDate);
		
		$('.guest_content_11 >table.append_guest >tbody').html("");
		$('.clear_hiiden').val("");

	if($('.calender_pop').css('display')=='none')
			{
				$("#allday").prop("checked", false);
				$('.date_stared').removeClass('hide_time');
				$('#editeventheader').html("Create Event");
				$('.calender_pop').css('display','block');
				$('.web_dialog_overlay').show();
				$('.calender_option').hide();
					
					
			}
			});
			
			
	
	/// CALENDER TOP OPTION END HERE
	
	/// TEST CANCEL CALENDER 
	
		$('.pop_header >span').click(function(){
		
		 $('.calender_pop').hide();
		 $('.web_dialog_overlay').hide();
		 $('.clear_data').val("");
		 $('#defaultremindertable').html('<table><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
		 $('.new_reminder').html("");
		 
		 $('#frequency').val("no");
		 ///////////
//		 $('.repeat_every_day').hide();
		 $('.repeat_every_week').hide();
		 $('.repeat_every_month').hide();
		 $('.repeat_every_year').hide();
		 $('.repeat_on_week').hide();
		 $('.repeat_day_month').hide();
		 ////////////
		 $('#allday').removeAttr('checked');
		 $('#editeventheader').html("Create Event");
		 $('.delete_event').hide();
		// $('.delete_event').show();
		 $('.saveEvent').show();
		 $(".gen_content >table >tbody>tr>td>input[type='text'], select, textarea").prop("disabled", false);
		 $('#div_send_invite_mail').show();
		$('#calendar_select').show();
		$('#calendar_select_np').val("");
		$('#calendar_select_np').hide();
		});
	
	$('.cancel_cal').click(function(){
	
		 $('.calender_pop').hide();
		  $('.web_dialog_overlay').hide();
		  $('.clear_data').val("");
			 $('#defaultremindertable').html('<table><tbody><tr><td colspan="2" class="new_line">This event has no configured reminders</td></tr><tr><td colspan="2" class="new_line_1">New reminder:</td></tr><tr><td colspan="3"><span>Add New</span></td></tr></tbody></table>');
           $('.new_reminder').html("");
           $('#frequency').removeAttr("disabled");
           $('#frequency').val("no");
           
			 ///////////
//			 $('.repeat_every_day').hide();
			 $('.repeat_every_week').hide();
			 $('.repeat_every_month').hide();
			 $('.repeat_every_year').hide();
			 $('.repeat_on_week').hide();
			 $('.repeat_day_month').hide();
			 ////////////
			 $('#allday').removeAttr('checked');
			 $('#editeventheader').html("Create Event");
			 $('.delete_event').hide();
			// $('.delete_event').show();
			 $('.saveEvent').show();
			 $(".gen_content >table >tbody>tr>td>input[type='text'], select, textarea").prop("disabled", false);
			 $('#div_send_invite_mail').show();
			 $('#calendar_select').show();
				$('#calendar_select_np').val("");
				$('#calendar_select_np').hide();
				
		});
	
	
	
	/// CALENDER POP UP STARED HERE
	
	// GENERAL OPTION
	$('.gen_opt').click(function(){
		
		$('.work_active').removeClass('work_active');
		    if($('.gen_content').css('display')=='block' || $('.gen_content').css('display')=='none')
			{
				$('.gen_content').css('display','block');
				$('.repeat_cal').css('display','none');
				$('.reminder_cal').css('display','none');
				$('.workgroup_cal').css('display','none');
				$('.repe').removeClass('repe_active');
				$('.remind').removeClass('remind_active');
				$('.work_g').removeClass('work_active');
				$('.gen_opt').addClass('gen_active');
				
			 }
		
		});
	 /// GENERAL OPTION END HERE 
	 
	 
	 // REPEAT OPTION STARED HERE 
	 $('.repe').click(function(){
		
		 $('.work_active').removeClass('work_active');
		    if($('.repeat_cal').css('display')=='none')
			{
				$('.repeat_cal').css('display','block');
				//$('.repeat_cal').css('display','none');
				$('.reminder_cal').css('display','none');
				$('.workgroup_cal').css('display','none');
				$('.gen_content').css('display','none');
				$('.repe').addClass('repe_active');
				$('.gen_opt').removeClass('gen_active');
				$('.remind').removeClass('remind_active');
				$('.work_g').removeClass('work_active');
				$('.gen_opt').removeClass('repe_active');
				
			 }
		
		});
	 
	 // REPEAT OPTION END HERE
	 
	 
	 	 // REPEAT OPTION STARED HERE 
	 $('.remind').click(function(){
		 $('.work_active').removeClass('work_active');
		    if($('.reminder_cal').css('display')=='none')
			{
				$('.reminder_cal').css('display','block');
				$('.repeat_cal').css('display','none');
				//$('.reminder_cal').css('display','none');
				$('.workgroup_cal').css('display','none');
				$('.gen_content').css('display','none');
				$('.remind').addClass('remind_active');
				$('.repe').removeClass('repe_active');
				$('.gen_opt').removeClass('gen_active');
				$('.work_g').removeClass('work_active');
				$('.gen_opt').removeClass('repe_active');
				
			 }
		
		});
	 
	 // REPEAT OPTION END HERE
	 
	 
	  	 // WORKGROUP OPTION STARED HERE 
	 $('.work_g').click(function(){
		 $('.work_active').removeClass('work_active');
		    if($('.workgroup_cal').css('display')=='none')
			{
				$('.workgroup_cal').css('display','block');
				$('.repeat_cal').css('display','none');
				$('.reminder_cal').css('display','none');
				//$('.workgroup_cal').css('display','none');
				$('.gen_content').css('display','none');
				$('.work_g').addClass('work_active');
				$('.remind').removeClass('remind_active');
				$('.repe').removeClass('repe_active');
				$('.gen_opt').removeClass('gen_active');
				$('.gen_opt').removeClass('repe_active');
				
			 }
		
		});
	 
	 // REPEAT OPTION END HERE
	 
	 
	/// CALENDER POP UP END HERE 
	
	
	
	
	
	
	
	
	
	
	//// JS FOR CALENDER STRED HERE 
	
	///////////////////////////////////////////
	///                                    ///
	///    ONLY FOR MY CALENDER OPTION     ///
	///                                    ///
	/////////////////////////////////////////
	
	/// CALENDER OPTION HERE 
	/* 
	 * 
	 $(document.body).on('click', '.cal_option' ,function(){
	//$('.cal_option').click(function(){
		//alert('Hi');
		 $('.custom_color').hide();
		 $('.custom_check').hide();
		 $('.active_custom_color').removeClass('active_custom_color');
		
		       $('div.hi').removeClass('hi');
			   $('div.select_arrow').removeClass('select_arrow');
			   
		       var cho_arrow =  $(this).parent().parent().parent().children('.calender_option').addClass('show_option');
			   $(this).parent().children('.color_calender').addClass('select_arrow');
			   
			   //  var cho_arrow =  $(this)
			   var cho_box_left = $(this).offset().left ;
			   var cho_box_top = $(this).offset().top ;
			    $('.show_option').css('top',cho_box_top - 85);
		        $('.show_option').css('left',cho_box_left - 64); 
				//alert(cho_box_top);
				 $('.show_option').slideToggle("slow");
				  $('.other_calender_option').hide("slow");
				  // TEST 
				 // var new_color_find = $(this).parent().children().find('.select_arrow').background;
				//  alert(new_color_find);
				  // TEST
				//  alert(select_arrow_color);
				//  $('div.color_calender').find('.select_arrow').alert('hi');
				  
				//var find_color = $('.select_arrow').attr('style');
				//alert(find_color);
				
				/// TEST STTARED HERE 
				
			    var find_select_color = $('.select_arrow').css('background');
				var find_other_1 = $('.color_1').css('background');
				var find_other_2 = $('.color_2').css('background');
				var find_other_3 = $('.color_3').css('background');
				var find_other_4 = $('.color_4').css('background');
				var find_other_5 = $('.color_5').css('background');
				var find_other_6 = $('.color_6').css('background');
				var find_other_7 = $('.color_7').css('background');
				var find_other_8 = $('.color_8').css('background');
				var find_other_9 = $('.color_9').css('background');
				var find_other_10 = $('.color_10').css('background');
				var find_other_11 = $('.color_11').css('background');
				var find_other_12 = $('.color_12').css('background');
				if(find_select_color == find_other_1)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_1>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     }else if(find_select_color == find_other_2)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_2>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     }else if(find_select_color == find_other_3)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_3>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     }else if(find_select_color == find_other_4)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_4>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     } else if(find_select_color == find_other_5)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_5>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     }else if(find_select_color == find_other_6)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_6>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     } else if(find_select_color == find_other_7)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_7>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     } else if(find_select_color == find_other_8)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_8>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     }else if(find_select_color == find_other_9)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_9>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     }else if(find_select_color == find_other_10)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_10>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     }else if(find_select_color == find_other_11)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_11>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     }else if(find_select_color == find_other_12)
				{
					$('span.select_color').removeClass('select_color');
					$('.color_12>span').addClass('select_color');
					$('.custom_check').removeClass('custom_check');
					
			     }
			     else
			     {
			     $('.custom_color').addClass('active_custom_color'); 
			     $('.select_color').removeClass('select_color');
			     $('.choose_custom >span').addClass('custom_check');
			      }
				 
				
				/// TEST END HERE 
				
				
				
				/// TEST 
				
				$('.sp-choose').addClass('custom_color_find');
				
				
				// TEST 
				
				
				
				 
		
		});
		
		
*/
		
		
		
		
		
		
	
	
	/// My calender 
	$('.my_claender').click(function(){
		
						//$('.my_calender_content').slideToggle("slow");
						if($('.my_calender_content').css('display')=='block')
						{
							$('.my_calender_content').hide();
							$('.my_claender').addClass('bottom_arrow');
						}
						else
						{
							$('.my_calender_content').show();
							$('.my_claender').removeClass('bottom_arrow');
						 }
		
		});
	
    ///////////////////////////////////////////
	///                                    ///
	///   ONLY FOR MY CALENDER OPTION END  ///
	///                                    ///
	/////////////////////////////////////////
	
	
	
	
	 ////////////////////////////////////////////////
	///                                           ///
	///   ONLY FOR OTHER CALENDER OPTION STRAED   ///
	///                                           ///
	////////////////////////////////////////////////
	
	/// OTHER CALENDER OPTION HERE 
	//$('.other_cal_option').click(function(){
		
		  //    if($('.other_calender_option').css('display')=='none')
			//  {
				  
				//  $('.other_calender_option').css('display','block');
				  
		//	 }
		//	 else
			// {
				//  $('.other_calender_option').css('display','none');
				 
			//  }
		
	//	});
	
	
		$(document.body).on('click', '.other_cal_option' ,function(){
	//$('.cal_option').click(function(){
		//alert('Hi');
		
		       $('div.hi').removeClass('hi');
			   
		       var other_cho_arrow =  $(this).parent().parent().parent().children('.other_calender_option').addClass('other_show_option');
			   
			   //  var cho_arrow =  $(this)
			   var other_cho_box_left = $(this).offset().left ;
			   var other_cho_box_top = $(this).offset().top ;
			    $('.other_show_option').css('top',other_cho_box_top - 85);
		        $('.other_show_option').css('left',other_cho_box_left - 64); 
				//alert(cho_box_top);
				 $('.other_show_option').slideToggle("slow")
				 $('.calender_option').hide("slow");
				 
				 
				 
				
		
		});
	
		$(document.body).on('click','#update_calendar',function(){
			var calr=$("#hid_cal_file_name").val();
//			alert("update cal name : " + calr);
			var cal_name = $("#cal_name").val();
			//var cal_location = $("#cal_location").val();
			var cal_desc = $("#cal_desc").val();
			if(cal_name == "")
				{
				showmsg("alert","Name can not be empty!");
				return false;
				}
			$.ajax({
					type : "GET",
					url : "updateCalendarDetail",
					data : {'calendar':calr,'cal_name':cal_name,'cal_desc':cal_desc},
					contentType : "application/json",
					success : function(data) {
						
						
						var obj = jQuery.parseJSON(data);
						if(obj.success == "true")
						{
							showmsg("alert","updated successfully!");
							try
							{
							var parent = document.getElementById(calr);
							parent.childNodes[3].innerHTML=cal_name;
							}
							catch (e) {
								location.href="calendar";
							}
						}
						$('.cal_setting').hide();
						$('.web_dialog_overlay').hide();
						
							
					},
					error: function (xhr, ajaxOptions, thrownError) {
				        alert(xhr.status);
				      }
			    }) ;
			
			
			});

	
	
	
	
	
	
	
	/// OTHER My calender 
	/*$('.other_claender').click(function(){
		
						if($('.other_calender_content').css('display')=='none')
						{
							$('.other_calender_content').show();
							$('.other_claender').addClass('other_bottom_arrow');
						}
						else
						{
							$('.other_calender_content').hide();
							$('.other_claender').removeClass('other_bottom_arrow');
						 }
		
		});*/
	
	
	
	//// CREATE CALENDER 
	
	
	/////////////////////////////////////////////
	///                                        ///
	///   ONLY FOR OTHER CALENDER OPTION END   ///
	///                                        ///
	/////////////////////////////////////////////
	
	//$('.create_calender').click(function(){
		
		
		/// TEST STARED HERE
				
		$('.new_cal_color > .color_find').click(function(){
//			alert($('#hid_cal_file_name').val());
//			$.ajax({
//		        type:"get",
//		        data:{
//		        	'calendarfilename':$('#hid_cal_file_name').val(),
//		        	'changedcolor':$(this).css('background-color')
//		        },
//		        url:"changecalendarcolor",
//		        async:true,
//		        dataType: "json",
//		        success: function(data){
//		        	alert(data);
//		        	if(data)
//		        		{
//		        		
//		        		location.href="/webmail/calendar";
//		        		}
//		        }
		        	
//		        });
			/*$('span.select_color').removeClass('select_color');
			  window.background = $(this).css('background');
			  $(this).children().addClass('select_color');
			  $('.select_arrow').css('background',background);*/
			
			$.cssHooks.backgroundColor = {
					   get: function(elem) {
					       if (elem.currentStyle)
					           var bg = elem.currentStyle["background-color"];
					       else if (window.getComputedStyle)
					           var bg = document.defaultView.getComputedStyle(elem,
					               null).getPropertyValue("background-color");
					       if (bg.search("rgb") == -1)
					           return bg;
					       else {
					           bg = bg.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
					           function hex(x) {
					               return ("0" + parseInt(x).toString(16)).slice(-2);
					           }
					           return "#" + hex(bg[1]) + hex(bg[2]) + hex(bg[3]);
					       }
					   }
					}
			var fing_color =   $(this).css("backgroundColor") ;
			$('#calendarcolor').val(fing_color);
			
			 $('span.select_color').removeClass('select_color');
			  window.background = $(this).css('background');
			  $(this).children().addClass('select_color');
//			  $('.select_arrow').css('background',background);
			  $('.calender_option').hide();
			   
			 

			});
		
		
		/// TEST END HERE 
		$('.calender_color > .color_find').click(function(){
			
			 // alert('Hi');
			// $(this).css('backgroundColor');
			///rgb2hex($(this).css('background-color'))
			//match = fing_color;
			//alert(match);
			
			// TEST STRED 
			
			// NEW CODE STRED HERE 
			
			
			$.cssHooks.backgroundColor = {
			   get: function(elem) {
			       if (elem.currentStyle)
			           var bg = elem.currentStyle["background-color"];
			       else if (window.getComputedStyle)
			           var bg = document.defaultView.getComputedStyle(elem,
			               null).getPropertyValue("background-color");
			       if (bg.search("rgb") == -1)
			           return bg;
			       else {
			           bg = bg.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
			           function hex(x) {
			               return ("0" + parseInt(x).toString(16)).slice(-2);
			           }
			           return "#" + hex(bg[1]) + hex(bg[2]) + hex(bg[3]);
			       }
			   }
			}

// --------------- NEW CODE END HERE -------------------
			var fing_color =   $(this).css("backgroundColor") ;
			
			$('#calendarcolor').val(fing_color);
			$('.select_arrow').css('background-color',fing_color);
			
			var calr=$("#hid_cal_file_name").val();
			$.ajax({
				type : "GET",
				url : "changecalendarcolor",
				data : {'calendar':calr,'changedcolor':fing_color},
				contentType : "application/json",
				success : function(data)
				{
					var obj = jQuery.parseJSON(data);
					if(obj.success == "true")
					{
//						showmsg("alert","Color updated successfully !");
						refreshCalendar();
					}
					else
					{
						showmsg("alert","Ooops something wrong !");
					}
					
//					alert("success : " + obj.success);
//					$('.select_arrow').css('background',fing_color);
//					var eventList = obj.eventList;
//					$.each(eventList, function(i, item) 
//					{
//						var uid = item.uid
//						$('.'+uid).parent().css('backgroundColor',fing_color);
//						$('.'+uid).parent().css('border-color',fing_color);
//					});
						
				},
				error: function (xhr, ajaxOptions, thrownError) {
			        alert(xhr.status);
			      }
		    }) ;
			
			
			
			
		
			
			});
		// TSET 
		
		
		
		
		     
		
		//});
		
		
		
		
		});
		
		
		///  JS SCRIPT COLOR FOR CALENDER STRAED HERE 
	
//		-------------------- Refresh Calendar start------------------ //

			function refreshCalendar()
			{
				$("#action_gif").css("display","block");
				var date = $("#calendar").fullCalendar('getDate');
				//alert(date);
				var view = $("#calendar").fullCalendar( 'getView' ).name;
				$.ajax({
					type : "GET",
					url : "reloadFullCalendear",
					data : {'mon':date._d.getMonth(),'year':date._d.getYear(),'view':view},
					contentType : "application/json",
					success : function(data)
					{
						$('#calendar').fullCalendar('removeEvents');
						var obj = jQuery.parseJSON(data);
						if(obj.error == "true")
						{
							showmsg("alert","Ooops something wrong !");
							location.href="${pageContext.request.contextPath}/calendar";
						}
						$("#action_gif").css("display","none");
						for(var k = 0 ;obj.eventList.length; k++)
						{
							$('#calendar').fullCalendar('renderEvent', obj.eventList[k], true);
						}
						
					},
					error: function (xhr, ajaxOptions, thrownError) {
						showmsg("alert","Ooops something wrong !");
						location.href="${pageContext.request.contextPath}/calendar";
				      }
			    }) ;
				
			}
			
			$('body').on('click', 'button.fc-prev-button', function() {

				//$("#action_gif").css("display","block");
				var date = $("#calendar").fullCalendar('getDate');
				//alert(date);
				var view = $("#calendar").fullCalendar( 'getView' ).name;
				$(".fc-today-button").addClass("fc-today-button1");
				$(".fc-today-button1").removeClass("fc-today-button");
				$.ajax({
					type : "GET",
					url : "reloadFullCalendear",
					data : {'mon':date._d.getMonth(),'year':date._d.getYear(),'view':view},
					contentType : "application/json",
					success : function(data)
					{
						$('#calendar').fullCalendar('removeEvents');
						var obj = jQuery.parseJSON(data);
						if(obj.error == "true")
						{
							showmsg("alert","Ooops something wrong !");
							location.href="${pageContext.request.contextPath}/calendar";
						}
						//$("#action_gif").css("display","none");
						for(var k = 0 ;obj.eventList.length; k++)
						{
							$('#calendar').fullCalendar('renderEvent', obj.eventList[k], true);
						}
						
					},
					error: function (xhr, ajaxOptions, thrownError) {
						showmsg("alert","Ooops something wrong !");
						location.href="${pageContext.request.contextPath}/calendar";
				      }
			    }) ;
				
				});

				$('body').on('click', 'button.fc-next-button', function() {

					//$("#action_gif").css("display","block");
					var date = $("#calendar").fullCalendar('getDate');
					var view = $("#calendar").fullCalendar( 'getView' ).name;
					//alert(date);
					$(".fc-today-button").addClass("fc-today-button1");
					$(".fc-today-button1").removeClass("fc-today-button");
					$.ajax({
						type : "GET",
						url : "reloadFullCalendear",
						data : {'mon':date._d.getMonth(),'year':date._d.getYear(),'view':view},
						contentType : "application/json",
						success : function(data)
						{
							$('#calendar').fullCalendar('removeEvents');
							var obj = jQuery.parseJSON(data);
							if(obj.error == "true")
							{
								showmsg("alert","Ooops something wrong !");
								location.href="${pageContext.request.contextPath}/calendar";
							}
							//$("#action_gif").css("display","none");
							for(var k = 0 ;obj.eventList.length; k++)
							{
								$('#calendar').fullCalendar('renderEvent', obj.eventList[k], true);
							}
							
							
						},
						error: function (xhr, ajaxOptions, thrownError) {
							showmsg("alert","Ooops something wrong !");
							location.href="${pageContext.request.contextPath}/calendar";
					      }
				    }) ;
					
				});
			
				//this code is used today button magic code
				$('body').on('click','.fc-today-button1', function(){
					var date = $("#calendar").fullCalendar('getDate');
					var view = $("#calendar").fullCalendar( 'getView' ).name;
					
					$.ajax({
						type : "GET",
						url : "reloadFullCalendear",
						data : {'mon':date._d.getMonth(),'year':date._d.getYear(),'view':view},
						contentType : "application/json",
						success : function(data)
						{
							try
							{
								$(".fc-today-button1").addClass("fc-today-button");
								$(".fc-today-button").removeClass("fc-today-button1");
								$(".fc-today-button").addClass("fc-state-disabled");
								$(".fc-today-button").prop("disabled", true);
							}
							catch(eer)
							{
								
							}
							
							$('#calendar').fullCalendar('removeEvents');
							var obj = jQuery.parseJSON(data);
							if(obj.error == "true")
							{
								showmsg("alert","Ooops something wrong !");
								location.href="${pageContext.request.contextPath}/calendar";
							}
							//$("#action_gif").css("display","none");
							for(var k = 0 ;obj.eventList.length; k++)
							{
								$('#calendar').fullCalendar('renderEvent', obj.eventList[k], true);
							}
							
							
						},
						error: function (xhr, ajaxOptions, thrownError) {
							showmsg("alert","Ooops something wrong !");
							location.href="${pageContext.request.contextPath}/calendar";
					      }
				    }) ;
				  });
			
//				-------------------- Refresh Calendar start------------------ //