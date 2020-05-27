// JavaScript Document

jQuery(document).ready(function() {
	
	
	//// TEST  
	
	$('.setting_tag_create').click(function(){
		
		//alert("Hi");
		$('.setting_val').val('');
		$('.setting_tag').show();
		//var tag_name = $('.setting_val').val();
		//alert( tag_name);
		$('.web_dialog_overlay').show();
		
	});
	
	
	
	
	
	
	$('.setting_tag_cancel').click(function(){
		
		$('.setting_tag').hide();
		$('.web_dialog_overlay').hide();
		
		
	})
	///  TEST END 
	
	
	
	/// CREATE FILTER 
	$(document.body).on('click','.new_create_filter',function(){
		
		//alert('Hi');
		$('.filter_serach').show();
		$('.web_dialog_overlay_setting').show();

		
		
		
		});
		
   // CREATE FILTER END 
   
   
   /// IMPORT FILETR
   
   // IMPORTA FILTER END 
   
   /// GREATER STRED HERE 
	$(document.body).on('click','.filter_greater',function(){
	     
		 $('.filter_greate_all').show();
	   
	   });
   
   /// GREATERR END HERE 
   
   
   // GREATER SELECT 

	$(document.body).on('click','.greater_list',function(){
	  
	  $('.greater_than').show();
	  $('.less_than').hide();
	  $('.filter_greate_all').hide();
	  
	  });
	  

	$(document.body).on('click','.less_list',function(){
			
			$('.greater_than').hide();
	        $('.less_than').show();
			$('.filter_greate_all').hide();
	  
	  });
   
   
   /// GRAETER END 
   
   
   // MB STARED HERE 

		$(document.body).on('click','.filter_mb_kb',function(){
		  
		  $('.mb_kb_list').show()
		  
		  });

		$(document.body).on('click','.mb_list',function(){
		  
		  $('.mb').show();
		  $('.kb').hide();
		  $('.byte').hide();
		  $('.mb_kb_list').hide();
	   
	   
	   });
	   

		$(document.body).on('click','.kb_list',function(){
	   
	   
	       $('.mb').hide();
		   $('.kb').show();
		   $('.byte').hide();
		    $('.mb_kb_list').hide();
	   
	   });
	   

		$(document.body).on('click','.bytes_list',function(){
			
			$('.mb').hide();
		    $('.kb').hide();
		    $('.byte').show();
			 $('.mb_kb_list').hide();
	   
	   
	   });
   
   /// MB END HERE 
   
   
   
   //  SELECT LABLE 
   

		$(document.body).on('click','.select_lable >ul >li',function(){
   
	   
	     var find_lable = $(this).html();
		// alert(find_lable);
		 document.getElementById('lable_option').innerHTML = find_lable;
		// $(this).parent().parent().hide();
		// $('.select_lable').addClass('hide_lable');
		  //$('.select_lable').hide();
		  // $('.category_option').hide();
		  // alert('Hi');
				 $('.select_lable').hide();
			      $('.category_option').hide();

	
	   
	   });
	   
		
	   $('.lable_option').click(function(){
	   

				 $('.select_lable').show();
				 $('.category_option').hide();

		  
	  // $('.select_lable').removeClass('hide_lable');
	   
	   });
   
   /// SELECT LABLE  END 
		
		
		/// SELECT CATEGORY 
		
		$('.category_option >ul >li').click(function(){
			
			var find_category = $(this).html();
			 document.getElementById('select_category').innerHTML = find_category;
			//alert(find_category);
			$('.category_option').hide();
			$('.select_lable').hide();
			
			
			});
			
			
			$('.select_category').click(function(){
				
				
			//	alert('Hi');
				$('.category_option').show();
				$('.select_lable').hide();
				
				});
		
		// SELECT CATEGOERY END 

			/*$(document.body).on('click','.filter_buttom_create',function(){
			
			var filter_sub = $('.filter_subject').val();
			//alert(filter_sub);
			//location.reload();
			//$('table.filter_data >tbody').append('<tr class="r7"><td class="qV r5"><span>Matches:</span><div class="filter_subject_new"></div></td><td class="qV r5"><span class="qW">Action:</span><div class="filter_subject_mid"> </div></td><td class="qS r5 edite_and_detele"><span class="sA filter_edite" >edit</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="sA filter_delete">delete</span></td></tr>')
			$('.filter_next_page').hide();
			$('.web_dialog_overlay_setting').hide();
			});*/




/// DELETE THE FILETER
			$(document.body).on('click','.filter_delete',function(){
       
	  // alert('Hi');
	   $(this).parent().parent().remove();

	});

/// DELETE THE FILTER END
		
		
		/// CANCEL 

			$(document.body).on('click','.filter_buttom',function(){
			
			    $('.filter_serach').hide();
				$('.web_dialog_overlay_setting').hide();
				$('.filter_next_page').hide();
			
			});
		
		// CANCEL END 
	
	
	
	  /// EDITE 

	$(document.body).on('click', '.filter_edite' ,function(){
		
		
		
				$('.filter_serach').show();
				$('.web_dialog_overlay_setting').show();
		
		
		
		
		
	});
	  
	  // EDITE END HERE 

	$(document.body).on('click','.filter_back',function(){
		  
		     // alert('Hi')
		  
		       $('.filter_serach').show();
			   $('.filter_next_page').hide();
			   $('.web_dialog_overlay_setting').show();
		  
		  
		  });
	  ///  END HERE 
	
	
	
});