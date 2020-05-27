// JavaScript Document

jQuery(document).ready(function() {
	
	
	
	
	
	
	/// MORE PRODUCT STARED HERE 
		 $(document.body).on('click','.more_product',function(event){
       ///  alert('Hi');
			if($('.more_product_content').css('display')=='none')
			{
				$('.folder_div').hide();
				$('.tag_content').hide();
				$('.user_noti_content').hide();
				$('.user_information').hide();
				$('.search_form_1').hide();
				$('.more_product_content').css('display','block');
				$('.user_information').hide();
				$('div.user_info_top').removeClass('user_info_top');
				$('.search_form_1').removeClass('search_display');
				$('.search_form_1').css('display','none');
				
			}
			else {
				$('.user_noti_content').hide();
				$('.user_information').show();
				$('.search_form_1').show();
				    $('.more_product_content').css('display','none');
				//$('.user_information').show();
					$('div.user_info_top').addClass('user_info_top');
					$('.search_form_1').css('display','none');
					if($('.user_information').css('display')=='block')
						{
						
						$('.user_information').css('display','none');
						
						}
				
				}
			
			   event.stopPropagation();
		
		});
	
	
	/// MORE PRODUCT END HERE 
	
	
	});