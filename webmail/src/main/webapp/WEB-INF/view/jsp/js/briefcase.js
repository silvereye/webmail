jQuery(document).ready(function() {
// SHOW THE POP UP

	
// CANCEL 
$(document.body).on('click','.cancel_briefcase',function(){
//$('.cancel_briefcase').click(function(){
	
	  //  alert('Hi');
		$('.briefcase_box').hide();
		$('.mycontact_box').hide();
		$('.web_dialog_overlay_setting').hide(); 
		// $('.web_dialog_overlay').hide(); 
	});	

$(document.body).on('click','.button_main',function(){
	//$('.cancel_briefcase').click(function(){
		
		 //   alert('Hi');
			$('.briefcase_box').hide();
			$('.mycontact_box').hide();
 $('.web_dialog_overlay_setting').hide(); 
		});	
// FOLDER FOLDE 
$(document.body).on('click','.folder_fold',function(){
//$('.folder_fold').click(function(){
	 // alert('Hi');
	      if($('.folder_briefcase').css('display')=='none')
		  {
		        $(this).removeClass('active_folder');
		//		alert('This is Block');
				$('.folder_briefcase').css('display','block');
		  
		  }
		  else
		  {
		       $(this).addClass('active_folder');
			//   alert('This Not is Block');
			   $('.folder_briefcase').css('display','none');
		  
		  }
	
	});

// FILE FOLD
$(document.body).on('click','.file_fold',function(){
//$('.file_fold').click(function(){
	//  alert('Hi');
	      if($('.file_briefcase').css('display')=='none')
		  {
		        $(this).removeClass('active_file');
		//		alert('This is Block');
				$('.file_briefcase').css('display','block');
		  
		  }
		  else
		  {
		       $(this).addClass('active_file');
		//	   alert('This Not is Block');
			   $('.file_briefcase').css('display','none');
		  
		  }
	
	});

/// ACTIVE SELECT 

$(document.body).on('click','.briefcase_inner li',function(){
//$('.briefcase_inner li').click(function(){
	   
	    $('.active_select').removeClass('active_select');
		$(this).addClass('active_select');
	
	});
	});
