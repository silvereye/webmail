// JavaScript Document
jQuery(document).ready(function() {
	
	/// TEST 
	
$(document.body).on('click', '.mail_deatil' ,function(event){			
		//$('.mail_deatil').click(function(){
			 // alert("Hi");
			   var box_top = $(this).position().top;
			   var box_left = $(this).position().left;
			 // alert(box_top);
			//   alert(box_left);
			   $('.to_me').css('top',box_top+12);
			   $('.to_me').css('left',box_left+10);
			   if ($('.to_me').css('display') == "none")
			   {

			       $('.to_me').css('display', 'block');

			   }

			   else {

			       $('.to_me').css('display', 'none');

			   }
			   $('.mail_down_option_1').css('display', 'none');
			event.stopPropagation();
			
			
});

		
$(document.body).on('click', '.to_me' ,function(event){

	 event.stopPropagation();
	
});
	
	 $(document.body).on('click', '.tag_inner_content' ,function(event){

		
		
		
		event.stopPropagation();
		
	});
	
	/// TEST
	
	
	
	
	
	/*chat */
	 $(document.body).on('click', '.ui-chatbox-icon' ,function(){
		 $(this).toggleClass( 'chat_input' );
		});

	$(document.body).on('click', '.ui-chatbox' ,function(){
	
		 $(this).toggleClass( 'chat_input' );
		});
	/*chat */
	/// TEST STRED HERE 
	
	$('.choose_custom').click(function(){
		//alert('Hi');
		
		if($('.custom_color').css('display')=='none')
		{
			
			$('.custom_color').show();
			
			}
		else {
			
			$('.custom_color').hide();
			
			}
		
		})
	
	/// TEST STRED HERE 
	
	
	
	
	
	
	
		/// TEST STRED HERE 
		$(document.body).on('click','.tag_option',function(){
			
			// alert('Hi');
			var tag_co_top = $(this).offset().top;
			var tag_co_left = $(this).offset().left;
			//alert(tag_co_top);
			if($('.tag_color_option').css('display')=='none')
			{
			$('.tag_color_option').css('top',tag_co_top -79 );
			$('.tag_color_option').css('left' , tag_co_left -238);
			$('.tag_color_option').show();
			}else {
				$('.tag_color_option').hide();
				
				}
			$('div.active_tag').removeClass('active_tag');
			$('div.active_tag_opt').removeClass('active_tag_opt');
			$('div.append_opt').removeClass('append_opt');
			$(this).addClass('active_tag_opt')
			$(this).parent().children().children('.append_color').addClass('append_opt')
			$(this).parent().children('span').children('.append_val').addClass('active_tag')
			
			
		});
	
	/// TEST END HERE 
	
	
	
	/// TAGE STRED HERE 
	//$('.tag_main').click(function(){
	$(document.body).on('click','.tag_main',function(event){
		
		  /// alert('Hi');
		
		 var find_top = $(this).offset().top;
		 var find_left = $(this).offset().left;
		 
		 $(".tag_check").prop('checked', false); 
		   if($('.tag_content').css('display')== 'none')
		   {
			   $('.tag_content').css('top',find_top+27);
			   $('.tag_content').css('left',find_left); 	     
		   $('.tag_content').css('display','block');
		   $('.apply_tag').hide();
		   
		   $('.search_form_1').hide();
		   $('.more_product_content').hide();
		   $('.user_information').hide();
		   $('.user_noti_content').hide();
		   
		   
		   
		   
		   
		   }else {
			   
			    $('.tag_content').css('display','none');
			   
			   }
		   event.stopPropagation();
		
		});
	
	
	/// TAG STRED HERE 
	
	

	
	$('.tag_sel_option').click(function(){
		
		
		var color_top = $(this).position().top;
		var color_top_left= $(this).position().left;
		
		//var color_top = $(this).offset().top;
		//var color_top_left= $(this).offset().left;
		//var color_top_left_1= $('.setting_color').css('left');
		//alert(color_top);
		//alert(color_top_left);
		//alert(color_top_left_1);
			if($('.select_tag').css('display')=='none')
			{
				
				$('.select_tag').css('display','block');
				$('.select_tag').css('top',color_top+28);
				$('.select_tag').css('left',color_top_left+20);
				
				}else {
					
					$('.select_tag').css('display','none');
					
					
					}

		
		 // alert('Hi');
			/*if($('.select_tag').css('display')=='none')
			{
				
				$('.select_tag').css('display','block');
				
				}else {
					
					$('.select_tag').css('display','none');
					
					
					}*/
		
		
		});
	
	
	/// SELECT TAG STRED HERE 
	
	$('.select_tag >ul >li').click(function(){
		
		   $('.test_color').css('background','none');
		  $('.tag_sel_option >ul>li>span').html('');
		  var tag_color =  $(this).children('.color_tag').css('background-color');
		  var tag_text =  $(this).children('span').html();
		  $('.test_color').css('background',tag_color);
		  $('.tag_sel_option >ul>li>span').html(tag_text);

		  $(this).parent().parent().hide();
		
		});
	
	/// CRAETE TAG STRED HERE 
	$('.craet_gat').click(function(){
		
		$('.craete_tag').show();
		$('.web_dialog_overlay').show();
		
		
		});

	/// CANCEL CRATE 
	$('.tag_can').click(function(){
		
		$('.craete_tag').hide();
		$('.web_dialog_overlay').hide();
		
		});	
	
	
	/// SAVE TAG STRED HERE 
	$('.tag_save').click(function(){
		
		  var tag_name =  $('.tag_name').val();
		//  alert(tag_name);
		 var sel_color = $(this).parents().children('.tag_sel_option').children().children().children('.test_color').css('background-color');
		 // LIST OF COLOR 
		
		 var color_green = $('.color_green').css('background-color');
		// alert(color_green);
		 var color_blue = $('.color_blue').css('background-color');
		 var color_yellow = $('.color_yellow').css('background-color');
		 var color_black = $('.color_black').css('background-color');
		 var color_gray = $('.color_gray').css('background-color');
	     var color_orange = $('.color_orange').css('background-color');
	     var color_pink = $('.color_pink').css('background-color');
	     var color_drak_bl = $('.color_drak_bl').css('background-color');
	     var color_dar_gree = $('.color_dar_gree').css('background-color');
		// alert(sel_color);
		// alert(color_dar_gree);
		 // LIST COLOR END 
           if($('.tag_name').val()=='')
		   {
			   alert('Please Give The Value !');
			   
			   }
			   
			   else {
		
         $('.tag_inner_content >ul').append('<li><input type="checkbox" name="my_chk_tag" value="'+ tag_name +'" class="tag_check" /><span><div class="append_color '+ tag_name +'" style="background:'+ sel_color +'"></div><div class="append_val">'+ tag_name +'</div></span><div class="tag_option" style="background:'+ sel_color +'"><img src="images/cal-open.png"></div></li>');
		 	  $('.tag_name').val('');
		  $('.craete_tag').hide();
		  $('.web_dialog_overlay').hide();
		   }
		// alert(sel_color );
		//  $('.tag_inner_content >ul').append('<li><input type="checkbox" class="tag_check" /><span style="background:'+ sel_color +'">'+ tag_name +'</span><div class="clear"></div></li>');
	
		
		});
	
	// CHECK THE TAG 
	$(document.body).on('click','.tag_check',function(){
		
		//alert('Hi')
		var sel_val = $(this).parent().children('span').children('.append_val').html();
		var sel_color = $(this).parent().children().children('.append_color').css('background-color');  /// APPLY IF CHECK BOX IS CHECKED THE APPLY IT
		 var result_22 = sel_val.replace(/\s/gi, "_"); 
		//alert($(this).parent().children('.tag_check').val());
		 //var tagid=$('.selected_row > .row_first > .row_left > .flag_first > .row_check_box >.mail_checked ').val()
		//$('.selected_row > .row_first >.inbox_tag').append('<div  class="in_tag '+ result_22 +'" style="background:' + sel_color + '"><span>'+ sel_val +'</span><div class="close_tag">x</div></div>');
		//$('.mail_left_content > .top_bottom_1 >.inbox_tag >').append('<div class="in_tag '+ result_22 +'" style="background:' + sel_color + '"><span>'+ sel_val +'</span><div class="close_tag">x</div></div>');
	//	$('.mail_left_content > .top_bottom_1 >.inbox_tag >').append('<div class="in_tag '+ result_22 +'" style="background:' + sel_color + '"><span>'+ sel_val +'</span><div class="close_tag">x</div></div>');
		var count_gat_check = $('.tag_check:checked').length;
	//	alert(count_gat_check);
		if(count_gat_check ==0)
		{
			$('.apply_tag').hide();
			}else if(count_gat_check == 1){
				
				$('.apply_tag').show();
				
				}
		
		})
		 
		// $('.mail_checked').on
		 
	/// TEST STRED HERE 
	$(document.body).on('click','.color_find',function(){
		
		//alert('Hi');
		var find_tag_col = $(this).css('background-color');
		//alert(find_tag_col);
		//$(this).parent().parent().parent().parent().children('.for_tool').children('.tag_content').children().children().children().children('.tag_option').addClass('Hi');
		$('.active_tag_opt').css('background-color',find_tag_col);
		$('.append_opt').css('background-color',find_tag_col);
		var active_val = $('.active_tag').html().replace(/_/g, ' ');
		//alert(active_val)
		//active_val
	  // active_val
	  var result = active_val.replace(/\s/gi, "_"); 
	 // alert(result);
		//var secondclass = $('.in_tag').attr('class').split(' ')[1];
		var secondclass = active_val;
	//	alert(secondclass);
		 var result_11 = secondclass.replace(/\s/gi, "_"); 
		//active_val
		//alert(result_11);
		if( result == result_11)
		{
			 $('.'+result_11).css('background-color',find_tag_col); 
		//	alert('Hello Test');
		///	$('.'+secondclass).val('');
			//$('.in_tag').text().add
			//secondclass.addClass('Hi');
			//$('.'+secondclass).text('');
			//$('.in_tag'+secondclass).css('background-color',find_tag_col);
			//$('.in_tag').find(secondclass).css('background-color',find_tag_col);
			
			}
		else 
		{
			//alert('By-Test');
			}
		
		//$('.change_color').css('background-color',find_tag_col);
		//$('.in_tag').hasClass(active_val)
		//active_val
		//alert($('.in_tag').attr('class').split(' ')[1]);
		$('.tag_color_option').hide();
		///  $('.'+ secondclass).removeAttr('class').split(' ')[1];
		//var secondclass = $('.in_tag').attr('class').split(' ')[''];
		//$('.in_tag').removeClass(secondclass);
		//var secondclass = $('.in_tag').attr('class').split(' ')[0];
		

		 

	});
	//$('.color_find')
	
	/// TEST END HERE
	
	// haedked value 
	
	
	
	/// TAG APPLY STARED HERE 
$('.apply_tag').click(function(){
	
		$('.tag_check:checked').each(function() {
	         // alert($(this).parent('li').children('span').html());
			var tg_val=$(this).val();
			  var get_all = $(this).parent('li').children('span').children('.append_val').html();
			  var get_all_color = $(this).parent('li').children('span').children('.append_color').css('background-color');
			  
			  var result_tag = get_all.replace(/\s/gi, "_"); 
			  $('.selected_row').each(function() {
			  var did=$(this).attr("id");
			 var alltg= $('#'+did+' >.row_first >.inbox_tag> .'+result_tag).attr("class");
			 var st=true
		
				 if(alltg=="in_tag "+result_tag)
					 {
					 st=false;
					 }
				
			 if(st)
			  {
			  
				 var uid=did.replace("div_","");
				 var disp="disp_"+uid+"~"+tg_val;
				 var list="list_"+uid+"~"+tg_val;
				 var half="half-"+uid+"~"+tg_val;
				 var full1="full_"+uid+"~"+tg_val;
				 var full2="full-"+uid+"~"+tg_val;
				 
			$('#'+did+' >.row_first >.inbox_tag').append('<div id="'+list+'" class="in_tag '+ result_tag +'" style="background:'+ get_all_color +'"><span>'+ get_all +'</span><div class="close_tag">x</div></div>');
			  try {
					 $(' #div_left_cnt> .top_bottom_1 >.inbox_tag_1').append('<div id="'+disp+'" class="in_tag in_tag_disp '+ result_tag +'" style="background:' + get_all_color + '"><span>'+ get_all +'</span><div id="'+half+'" onclick="remMailTagHalf(this.id)" class="close_tag">x</div></div>');
			  }
				catch(err) {}
				
			 try
			 {
			 $('.mail_left_content > .top_bottom_1 >.inbox_tag_1').append('<div id="'+full1+'" class="in_tag in_tag_disp '+ result_tag +'" style="background:' + get_all_color + '"><span>'+ get_all +'</span><div id="'+full2+'" onclick="remMailTagFull(this.id)" class="close_tag">x</div></div>');
			  }
				catch(err) {}
			  }
			  });
			  $('.tag_content').hide();
		});
		
		
			$('.in_tag').show();
			$('.tag_content').hide();
			$('.tag_check:checked').removeAttr('checked');
			$('.web_dialog_overlay').hide();
			$('.tag_content').hide();
		
		     // alert(sel_val);
		
		});
	
	
	/// CLOSE TAG STRED HERE 
		/*$(document.body).on('click','.close_tag',function(){
			
			//alert('Hi');
		$(this).parent().hide();
			
			
			
			
			})
	
	*/
	
	
	///  CLOSE TAG END 
	
	
	});