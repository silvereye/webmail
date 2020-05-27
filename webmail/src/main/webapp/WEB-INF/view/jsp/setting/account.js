// JavaScript Document

jQuery(document).ready(function() {
	
	
	// CREATE NEW FOLDER
	$('.new_folder').click(function(){
		
		 // alert('Hi');
		  $('.account_new_folder').show();
		  $('.web_dialog_overlay_setting').show();
		
		});
	// CREATE NEW FOLDER END 
	
	
	// CANCEL 
	$('.filter_buttom').click(function(){
		
		   $('.account_new_folder').hide();
		   $('.web_dialog_overlay_setting').hide();
		
		});
	
	// CANCEL END 
	
	// CANCEL 
	$('.filter_buttom').click(function(){
		
		   $('.account_edit_folder').hide();
		   $('.web_dialog_overlay_setting').hide();
		
		});
	
	// CANCEL END 
	
	/// CREATE FOLDER 
	
/*	$('.create_folder').click(function(){
		
		var folder_name = $('.folder_icon').val();
		
       $('table.new_folder_append >tbody').append('<tr class="To"><td class="alT"><div class="al6">' +  folder_name +'</div></td><td class="alQ"><span class="alP">show</span>&nbsp;<span class="alR">hide</span></td><td class="alQ"></td><td class="alQ"></td><td class="YQh8id">&nbsp;</td></tr>')		
		$('.folder_icon').val('');
		//alert(folder_name);
		 $('.account_new_folder').hide();
		 $('.web_dialog_overlay').hide();
		
		});*/
	
	/// CREATE END HERE 
	
	
	});