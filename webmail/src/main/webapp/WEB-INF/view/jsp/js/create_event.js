// JavaScript Document






jQuery(document).ready(function() {



	/// SELECT UNTILE 
	
	$('.select_repeat').change(function(){
		  //   alert($(this).val());
		   //  alert('Hi')
			 if($(this).val() == "no")
			 {
				 $('.weekly_text').hide();
				 $('.every_select_day_date_1').hide();
				 $('.weekly_on').hide();
				 $('.weekend_name').hide();
				 $('.year_default').attr('selected','selected');
				 $('.year_month_name').hide();
				 $('.every_select_day_date').hide();
				 $('.repeat_every_day').hide();
				 $('.repeat_on_week').hide();
				 $('.repeat_day_month').hide();
				 $('.end_recur').hide();
//				 $('.repeat_every_month').hide();
//				 $('.repeat_every_week').hide();
			//     alert('This No repetitions');
			 }
			 else  if($(this).val() == "DAILY")
			 {
				 $('.every_select_day_date').show();
				 $('.every_select_day_date_1').show();
				 $('.year_month_name').hide();
				 $('.weekly_on').hide();
				 $('.year_default').attr('selected','selected');
				 $('.select_every_day:eq(1)').attr('selected', 'selected')
				 $('.weekend_name').hide();
				 $('.every_select_day_date').show();
				 $('.weekly_text').hide();
				 $('.every_select_day_date_1').text('Daily');
				 $('#inter_lebel').html("Days")
			//	 alert('This is Daily');
				 $('.repeat_every_day').show();
//				 $('.repeat_every_month').hide();
				 $('.repeat_day_month').hide();
//				 $('.repeat_every_week').hide();
				 $('.repeat_on_week').hide();
//				 $('.repeat_every_year').hide();
				 $('.end_recur').show();
				 if($("#count_input").val()==null || $("#count_input").val()=="")
					 {
					 $("#count_input").val(10);
					 }
			 }
			 else  if($(this).val() == "WEEKLY")
			 {
				 $('.every_select_day_date_1').hide();
				 $('.weekly_on').show();
				 $('.weekly_on').text('Weekly on');
				 $('.weekly_text').hide();
				 $('.year_default').attr('selected','selected');
				 $('.month_message').hide();
				 $('.weekend_name').show();
				 $('.month_days_date').hide();
				 $('.every_select_day_date').hide();
				 $('.repeat_week input').removeAttr('checked');
				 $('.weekend_name span').remove();
				 $('.end_recur').show();
				 if($("#count_input").val()==null || $("#count_input").val()=="")
				 {
				 $("#count_input").val(10);
				 }
				 var today_name = new Date().getDay();
			//	 alert(today_name);
				 // check the day 
				 if(today_name == 1)
				 {
				      // alert('Today is wednesday !');
					   $('.monday').attr('checked','checked');	
					   $('.weekend_name').append('<span>&nbsp;Monday</span>');  
				 }
				 if(today_name == 2)
				 {
				      // alert('Today is wednesday !');
					   $('.weekend_name').append('<span>&nbsp;Tuesday</span>'); 
					   $('.tuesday').attr('checked','checked');	 
				 }
				 if(today_name == 3)
				 {
				      // alert('Today is wednesday !');
					   $('.weekend_name').append('<span>&nbsp;Wednesday</span>'); 
					   $('.wednesday').attr('checked','checked');	 
				 }
				 if(today_name == 4)
				 {
				      // alert('Today is wednesday !');
					   $('.weekend_name').append('<span>&nbsp;Thursday</span>');
					   $('.thursday').attr('checked','checked');	 
				 }
				 if(today_name == 5)
				 {
				     //  alert('Today is wednesday !');
					   $('.weekend_name').append('<span>&nbsp;Friday</span>'); 
					   $('.friday').attr('checked','checked');	 
				 }
				 if(today_name == 6)
				 {
				    //   alert('Today is wednesday !');
					   $('.weekend_name').append('<span>&nbsp;Saturday</span>'); 
					   $('.saturday').attr('checked','checked');	 
				 }
				 if(today_name == 7)
				 {
				       //alert('Today is wednesday !');
					   $('.weekend_name').append('<span>&nbsp;Sunday</span>'); 
					   $('.sunday').attr('checked','checked');	 
				 }
//				 $('.repeat_every_week').show();
				 $('.repeat_on_week').show();
				 $('.year_month_name').hide();
				 $('.year_date').hide();
				 $('#inter_lebel').html("Weeks")
				 $('.repeat_every_day').show();
//				 $('.repeat_every_day').hide();
//				 $('.repeat_every_month').hide();
				 $('.repeat_day_month').hide();
//				 $('.repeat_every_year').hide();
			//	 alert('This is Weekly');
			 }
			 else  if($(this).val() == "MONTHLY")
			 {
				 $('.every_select_day_date_1').hide();
				 $('.monthly_text').remove();
				 $('.weekly_on').hide();
				 $('.every_select_day_date').hide();
                 $('.year_date').hide();
				 $('.year_month_name').hide();
				 $('.year_default').attr('selected','selected');
			//	 alert('This is Monthly');
				 $('.month_message').show();
				 $('.month_days_date').show();
				 var date_name = new Date().getDate();
//				 $('.repeat_every_week').hide();
				 $('.repeat_on_week').hide();
				 $('#inter_lebel').html("Months")
				 $('.repeat_every_day').show();
//				 $('.repeat_every_day').hide();
				 $('.repeat_day_month').show();
//				 $('.repeat_every_month').show();
//				 $('.repeat_every_year').hide();
				 $('.month_message').append('<span class="monthly_text"><span class="monthly">&nbsp;Monthly&nbsp; </span> on day '+ date_name +' </span>');
				 $('.weekly_text').hide();
				 $('.weekend_end').hide();
				 $('.weekend_name').hide();
				 $('.year_message').show();
				 $('.end_recur').show();
				 if($("#count_input").val()==null || $("#count_input").val()=="")
				 {
				 $("#count_input").val(10);
				 }
			 }
			 else  if($(this).val() == "YEARLY")
			 {
				 $('.every_select_day_date_1').hide();
				 $('.year_default').attr('selected','selected');
				 $('.year_default').attr('selected','selected');
				 $('.year_date').hide();
				 $('.every_select_day_date').hide();
				 $('.year_month_name').html('');
				 $('.year_month_name').show();
				 $('.month_message').hide();
				 $('.weekly_text').hide();
				 $('.month_days_date').hide();
				 $('.year_month_name').show();
				 $('.weekend_name').hide();
		//		 alert('This is Yearly');
				 $('#inter_lebel').html("Years")
				 $('.repeat_every_day').show();
//				 $('.repeat_every_week').hide();
				 $('.repeat_on_week').hide();
//				 $('.repeat_every_day').hide();
				 $('.repeat_day_month').hide();
//				 $('.repeat_every_month').hide();
//				 $('.repeat_every_year').show();
				 $('.weekend_end').show();
				 $('.year_message').show();
				 $('.end_recur').show();
				 if($("#count_input").val()==null || $("#count_input").val()=="")
				 {
				 $("#count_input").val(10);
				 }
				 var year_name = new Date().getMonth();
				 var year_date = new Date().getDate();
			//	 alert(year_name);
			//	 alert($('.select_every').val());
				 // GET YEAR NAME 
				 if(year_name == 1)
                  {
			//	      alert('This Jaunary 1');
					  $('.year_month_name').append('<span class="annually">&nbsp;Annually </span> &nbsp;on ' + year_date + ' February');
				  
				  } 
				 if(year_name == 2)
                  {
				      $('.year_month_name').append('<span class="annually">&nbsp;Annually </span> &nbsp;on ' + year_date + ' March');
				  
				  }	
				 if(year_name == 3)
                  {
				   
				    $('.year_month_name').append('<span class="annually">&nbsp;Annually </span>&nbsp;on ' + year_date + ' April');
				  
				  }	
				 if(year_name == 4)
                  {
				     $('.year_month_name').append('<span class="annually">&nbsp;Annually </span>&nbsp;on ' + year_date + ' May');
				  
				  }	
				 if(year_name == 5)
                  {
				     $('.year_month_name').append('<span class="annually">&nbsp;Annually </span>&nbsp;on ' + year_date + ' June');
				  
				  }	
				 if(year_name == 6)
                  {
				  
				      $('.year_month_name').append('<span class="annually">&nbsp;Annually </span>&nbsp;on ' + year_date + ' July');
				  }	 
				 if(year_name == 7)
                  {
				  
				      $('.year_month_name').append('<span class="annually">&nbsp;Annually </span> &nbsp;on ' + year_date + ' August' );
				  }	
				 if(year_name == 8)
                  {
				  
				      $('.year_message').append('<span class="annually">&nbsp;Annually </span> &nbsp;on ' + year_date + ' September' );
				  }	
				 if(year_name == 9)
                  {
				      $('.year_month_name').append('<span class="annually">&nbsp;Annually </span> &nbsp;on ' + year_date + ' October' );
				  
				  }	
				 if(year_name == 10)
                  {
				      $('.year_month_name').append('<span class="annually">&nbsp;Annually </span> &nbsp;on ' + year_date + ' November' );
				  
				  }
				 if(year_name == 11)
                  {
				     $('.year_month_name').append('<span class="annually">&nbsp;Annually </span> &nbsp;on ' + year_date + ' December' );
				  
				  }
				 if(year_name == 12)
                  {
				     $('.year_month_name').append('<span class="annually">&nbsp;Annually </span> &nbsp;on ' + year_date + ' January' );
				  
				  }		
				  			 
				 
				 
				 
			 }
			 
			 
			 
		
		})

/// Summery field 
   var countCheckCal = 1
   $('.repeat_week input').click(function(){
	       $('.month_message').hide();
	       $('.weekly_summary').show();
	       if($(this).prop('checked'))
		   {
				   var getWeek =  $(this).val();
		//		   alert(getWeek);
		//		   alert(countCheckCal++);
                   $('.weekly_on_text >.weekend_name').append('<span>'+ getWeek +'</span>');
				    $('.weekend_end').hide();
					$('.weekend_name').show();
				   if(countCheckCal == 7 )
				   {
					   $('.weekly_on_text >.weekend_end').text('all days');
					   $('.weekend_name').hide();
					   $('.weekend_end').show();
				   }


		   }
		   else 
		   {
			   countCheckCal--
			     
		//	     alert(countCheckCal--);
			    var getWeek_1 =  $(this).val();
				//   alert(getWeek_1);
				   $('.weekly_on_text span').each(function(){
					       var getWeekRemove =  $(this).text();
						 //  alert(getWeekRemove);
						    if(getWeek_1 == getWeekRemove)
							{
								$(this).addClass('removeNew');
								$('.removeNew').remove();
						    }
							
					   
					   })
					   
			     $('.weekend_name').show();
			     $('.weekend_end').hide();
			   
		   }
	       // var getWeek =  $(this).val();
		  
			
	   
	   });
	
	
	
	
	
	/// Calendar  STARTED HERE 
	
	$('.count_radio').click(function(){
		
			var rec_end = $(this).val();
			if(rec_end == "never")
			{
				$('#count_input').attr('disabled','disabled');
				$('#until_input').attr('disabled','disabled');
			}
			else if(rec_end == "count")
			{
				$('.count_input').removeAttr('disabled');
				$('.count_input').val("10");
				
				$('#until_input').attr('disabled','disabled');
			}
			else if(rec_end == "until")
			{
				$('#count_input').attr('disabled','disabled');
				$('.until_input').removeAttr('disabled');
			}
		});
	
	
	
	/// UNTILE RADIO BUTTON 
	
	
	
	
	///  Year Select  for Year
	
	$('.select_every').change(function(){
		 //  alert('Hi');
		   $('.year_date').show();
		   var getTopSelectValue = $('.select_repeat').val();
		//   alert(getTopSelectValue)
		   var getYearValue = $(this).val();
		   $('.annually').addClass('hide_annually');
		   if(getYearValue == 1 )
		   {
			   $('.annually').removeClass('hide_annually');
			   $('.year_date').addClass('removeDateYaer');
		   }
		   else
		   {
			    $('.annually').addClass('hide_annually');
				$('.year_date').removeClass('removeDateYaer')
		   }
		 //  alert(getYearValue);
		   $('.year_date').text('Every ' + getYearValue + '  years ');
		
		
		});

   // EVERY MONTH  for Month 
   
		$('.select_every_months').change(function(){
			
		   var getYearValue = $(this).val();
		//   alert(getYearValue);
		   $('.monthly').addClass('hide_monthly');
		  if( getYearValue == 1)
		  {
		//	  alert('hi-1')
			  $('.monthly').removeClass('hide_monthly');
			  $('.month_days_date').addClass('hide_month_day');
		  }
		  else
		  {
		      $('.month_days_date').removeClass('hide_month_day');
		  }
		   $('.month_days_date').text('Every ' + getYearValue + ' months ');
		
		
		});
		
   /// EVERY DAYS  for Daily
   
     $('.select_every_day').change(function(){
		 
		  $('.every_select_day_date').text('');
		  var getYearValue = $(this).val();
		//  alert(getYearValue);
		  if(getYearValue == 1)
		  {
			  $('.every_select_day_date').hide();
			  $('.every_select_day_date_1').show();
			  
		  }
		  else
		  {
		         $('.every_select_day_date').show();
				 $('.every_select_day_date_1').hide();
		  }
		
		  $('.every_select_day_date').text('Every ' + getYearValue + ' days ');
		 
		 });
	
	/// EVERY WEEK for Week
	$('.select_every_weeks').change(function(){
		
		
		
		   var getYearValue = $(this).val();
		//   alert(getYearValue);
		   if( getYearValue == 1)
		   {
			  $('.weekly_text').hide();
			  $('.weekly_on').show();
			  
		   }
		   else
		   {
			 $('.weekly_text').show();
			 $('.weekly_on').hide();
		   
		   }
		   
		   
		   $('.weekly_text').text('Every ' + getYearValue + '  weeks on ');
		   
		
		}); 
	
	
	
	/// CREATE THE POP STARED HERE 
	
		$('.create_event').click(function(event){
			       
			      $('.pop_tab ul li.repe_active').removeClass('repe_active');
				  $('.gen_opt').addClass('repe_active');
				  $('.repeat_cal').hide();
				  $('.gen_content').show();
				  $('.end_recur').hide();
				  $('.repeat_every_day').hide();
				  
			});
			
	 // Active 

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/// CALENDRA END 
	
});