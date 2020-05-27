// JavaScript Document
jQuery(document).ready(function() {

$(document.body).on('click','.cal_set_top',function(){
	 $('.cal_setting').hide();
	 $('.cal_showdetail').hide();
	 $('.cal_showdetail_sub').hide();
	 $('.web_dialog_overlay').hide();
	});

$(document.body).on('click','.cal_detail_stop',function(){
	 $('.cal_showdetail').hide();
	 $('.cal_showdetail_sub').hide();
	 $('.web_dialog_overlay').hide();
	});
//
$(document.body).on('click','.show_cal_detail',function(){
	
	

	var calr=$("#hid_cal_file_name").val();
//	alert("cal file name : " + calr);
	document.getElementById('action_gif').style.display= 'block';
	$.ajax({
			type : "GET",
			url : "getCalendarDetail",
			data : {'calendar':calr},
			contentType : "application/json",
			success : function(data) {
				
				var obj = jQuery.parseJSON(data);
				var calpath=obj.calpath;
				$("#srd_cal_nm").html(obj.calname);
				
				$("#share_cal_desc").html(obj.description);
				
				$("#share_cal_link").html("<a href='"+calpath+"' target='_blank'> Calendar URL for CalDAV clients</a>");
				document.getElementById('action_gif').style.display= 'none';
//				alert("response data "+ data);
				$(".cal_showdetail").show();
				$('.web_dialog_overlay').show();
				
					
			},
			error: function (xhr, ajaxOptions, thrownError) {
		        alert(xhr.status);
		      }
	    }) ;
	
	
});
$(document.body).on('click','.managecalsubs',function(){


	//var calr=$("#hid_cal_file_name").val();
//	alert("cal file name : " + calr);
	document.getElementById('action_gif').style.display= 'block';
	$.ajax({
			type : "GET",
			url : "manageCalendarSubs",
		/*	data : {'calendar':calr},*/
			contentType : "application/json",
			success : function(data) {
				$("#cal_sub_data").html(data);
				
				document.getElementById('action_gif').style.display= 'none';
//				alert("response data "+ data);
				$(".cal_showdetail_sub").show();
				$('.web_dialog_overlay').show();
				
					
			},
			error: function (xhr, ajaxOptions, thrownError) {
		        alert(xhr.status);
		      }
	    }) ;
	
	
});



$(document.body).on('click','.cal_setting_opt',function(){
	var calr=$("#hid_cal_file_name").val();
//	alert("cal file name : " + calr);
	document.getElementById('action_gif').style.display= 'block';
	$.ajax({
			type : "GET",
			url : "getCalendarDetail",
			data : {'calendar':calr},
			contentType : "application/json",
			success : function(data) {
				
				var obj = jQuery.parseJSON(data);
				var calpath=obj.calpath;
				$("#cal_name").val(obj.calname);
				 var cond1=calpath.indexOf("/calendar/", calpath.length - "/calendar/".length) !== -1;
				  var cond2=calpath.indexOf("/calendar", calpath.length - "/calendar".length) !== -1;
				  if(cond1 || cond2)
				  {
					$("#cal_name").prop('disabled','disabled');
				}
				else
				{
					$("#cal_name").removeProp('disabled');
				}
				
				$("#cal_desc").val(obj.description);
				$("#owner_id").html(obj.ownerid);
				$("#cal_link").html("<a href='"+calpath+"' target='_blank'> Calendar URL for CalDAV clients</a>");
				document.getElementById('action_gif').style.display= 'none';
//				alert("response data "+ data);
				$('.cal_setting').show();
				$('.web_dialog_overlay').show();
					
			},
			error: function (xhr, ajaxOptions, thrownError) {
		        alert(xhr.status);
		      }
	    }) ;
	
	
	});

$(document.body).on('click','.add_notification',function(){
	
	$('.cal_notification').append('<div class="cal_row"><div class="clear"></div><select class="cal_email cal_setting_select"><option value="1">Email</option><option value="3">Pop-up</option></select><input  class="text_cal_notifi" type="text"/><select class="cal_time cal_setting_select"><option >minutes</option><option >hours</option><option >days</option><option >weeks</option></select> <span> before each event </span> <span class="cal_set_del"><img src="images/tool.png" /></span></div>');
	
	
	});	
	
	$(document.body).on('click','.cal_set_del',function(){
		
//						if (confirm("Are you sure?")) {
//							 your deletion code
//						}
						$(this).parent().remove();
						return false;
		});
		
});