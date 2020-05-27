jQuery(document).ready(function() {
	
	
	 $(document.body).on('click','.att_disp_close',function(event){
		 $(".web_dialog_overlay_black").hide();
		 $("#att_disp_cnt").html("");
	 });
	
	 $(document.body).on('click','.att_disp_close_pdf',function(event){
		 $(".web_dialog_overlay_black_pdf").hide();
		 $("#att_disp_cnt_pdf").html("");
	 });
	
	 $(document.body).on('click','.contact_input',function(event){
		 event.stopPropagation();
	 });
	 
	 $(document.body).on('click','.contact_input_dir',function(event){
		 event.stopPropagation();
	 });
	 
	 $(document.body).on('click','.con_he_left_list >tbody>tr',function(event){
		 
	 
		var cls=	$(this).attr('class'); 
		if(cls!=null && cls.indexOf("active_row_delete")>=0)
			{
			$(this).removeClass('active_row_delete');
			$(this).children().children("input[name='chk_con']:checkbox").removeAttr('checked');
			}
		else
			{
			$(this).addClass('active_row_delete');
			//$(this).children("input[name='chk_con']:checkbox").prop('checked', true);
			$(this).children().children("input[name='chk_con']:checkbox").attr('checked', 'checked');
			}
		}); 
	
	/// user notification
	 $(document.body).on('click','.user_noti',function(event){

		if($('.user_noti_content').css('display')=='none')
		{
			$('.folder_div').hide();
			$('.tag_content').hide();
			
			$('.user_information').hide();
			$('.search_form_1').hide();
			$('.more_product_content').hide();
			$('.user_noti_content').css('display','block');
			$('.user_information').hide();
			$('div.user_info_top').removeClass('user_info_top');
			$('.search_form_1').removeClass('search_display');
			$('.search_form_1').css('display','none');
			showcalNoti();
		}
		else {
			$('.user_information').show();
			$('.search_form_1').show();
			    $('.user_noti_content').css('display','none');
			    $('.more_product_content').hide();
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
	
	
	
	/*chat */
	
	$(document.body).on('click','.new_chat_header',function(){
		
		$('.new_chat_header').addClass( 'new_overflow' );
		
	})
	
	
	 $(document.body).on('click', '.ui-chatbox-icon' ,function(){
		 $(this).parent().parent().toggleClass( 'chat_input' );
		// alert('Hi-Hi');
		});
	 
	 $(document.body).on('click', '.ui-widget-header' ,function(){
		 $(this).toggleClass( 'chat_input_new' );
		});
	 
	 
	$(document.body).on('click', '.ui-chatbox' ,function(){
	
		 $(this).toggleClass( 'chat_input' );
		});
	
	 $(document.body).on('click', '.ui-chatbox-icon' ,function(){
		 $(this).parent().parent().toggleClass( 'chat_input_new' );
		});
	 
	 
	 $(document.body).on('click','.minustick_1',function(){
		  
	 });
	 
	 $(document.body).on('click','.new_delete_chat',function(event){
		 
		  //alert('This is Delete !');
		  $(this).parent().parent().parent().remove();
		  event.stopPropagation();
		  
	 });

	 $(document.body).on('focus','.new_chat_header',function(){
		 
	     //alert('This Chat textarea are Here...') ;
	         $('.new_chat_header').addClass('new_overflow');
		     //$('.ui-chatbox-icon').addClass('active_chat_textarea_icon');
		 
	 });
	 
	// $(document.body).on('click','.active_chat_textarea_icon',function(){
	//	 alert('Hi input box');
	//	 $(this).parent().parent().toggleClass( 'chat_input_new' );
	// });
	 
	  $(document.body).on('click', '.active_chat_textarea_header' ,function(){
		  //alert('Hi this header');
		  // $('div.chat_input').removeClass('chat_input')
	        $(this).addClass( 'chat_input' );
		});
	 
	 
	
	/*chat */
});




function showHide(event) {
	try
	{
   var ele = document.getElementById("search_form");
   if (ele.style.display == "block") {
       ele.style.display = "none";
		$('.user_information').css('display','none');
		$('.more_product_content').hide();
		$('.user_noti_content').hide();
   }
   else {
        ele.style.display = "block";
		$('.user_information').css('display','none');
		$('.more_product_content').hide();
		$('.user_noti_content').hide();
   }
	event.stopPropagation();
	}
	catch (e) {
		// TODO: handle exception
	}
}
function Hide() {
	try
	{
    var ele = document.getElementById("search_form");
    if (ele.style.display == "block") {
       ele.style.display = "none";
   }
	}
	catch (e) {
		// TODO: handle exception
	}
}

function showHidetool() {
	try
	{
    var ele = document.getElementById("search_form_tool");
    if (ele.style.display == "block") {
        ele.style.display = "block";
        ele.style.opacity = "1";
    }
    else {
        ele.style.display = "block";
    }
	}
	catch (e) {
		// TODO: handle exception
	}
}

function showHidemenuleft() {
	try
	{
    var ele = document.getElementById("left_margin");
    if (ele.style.display == "block") {
        ele.style.display = "block";
        ele.style.opacity = "1";
    }
    else {
        ele.style.display = "none!important";
    }
	}
	catch (e) {
		// TODO: handle exception
	}
}
//----------/// ONLY FOR PAGE FORMAT CHANGE LEFT SIDE--//
function showHidepageleft() {
	try
	{
    var ele = document.getElementById("right_tab");
    if (ele.style.display == "block") {
        ele.style.display = "block";
        ele.style.opacity = "1";
    }
	}
	catch (e) {
		// TODO: handle exception
	}
}


function showHidepageright() {
	try
	{
    var ele = document.getElementById("right_tab");
    if (ele.style.display == "block") {
        ele.style.display = "block";
        ele.style.width = "block";
        ele.style.float = "left";
    }
    else {
        ele.style.display = "none";
    }
	}
	catch (e) {
		// TODO: handle exception
	}
}

function leftmenutool() {
	try
	{
    var ele = document.getElementById("open");
    if (ele.style.display == "block") {
        ele.style.display = "block";
    }
    else {
        ele.style.display = "block";
    }
	}
	catch (e) {
		// TODO: handle exception
	}
}

//$(document).ready(function($){

//$('#accordion-3').dcAccordion({
//eventType: 'click',
//autoClose:false,
//saveState: false,
//disableLink: false,
//showCount: false,
//speed: 'slow'
//});	
//$('.left_margin').css('display','block');
//$('.ac-small').css('height','140px');
//});

jQuery(document).ready(function() {
	
	///***********  TEST **************** ///
	//(document.body).on('keydown','.ac_over',function(e))
	
/*(document.body).on('click','.ac_over', function(e){
  	//   alert(e.which);
    if(e.which == 13)
    {
    	
           	alert('Hi');
    	
    }
 
}););
	*/
	
	
	/// **************** TEST END ********************///
	
	
	
	
	
	
//	alert("ready")
	$(document.body).on('click', '.file_upload_ck' ,function(event){	
	//$('.file_upload').click(function(){
//		$(document.body).on('click', '.file_upload' ,function(event){	
			   if($('#upl_ck').val()=="")
				   {
				   
				   //  alert("It Is Blank")
				   
				   
				   }
			   else {
				   $('#upl_ck').val("");
				   $('.pop_for_image_upload_ck').hide();
					  $('.web_dialog_overlay').hide();
					 
				   
			   }
			   event.stopPropagation();
			
			
		});
	
	
//	alert("ready")
$(document.body).on('click', '.file_upload' ,function(event){	
//$('.file_upload').click(function(){
//	$(document.body).on('click', '.file_upload' ,function(event){	
		   if($('#upl1').val()=="")
			   {
			   
			   //  alert("It Is Blank")
			   
			   
			   }
		   else {
			   
			   $('.pop_for_image_upload').hide();
				  $('.web_dialog_overlay').hide();
				  $('.user_information').removeClass('user_info_top');
					  $('.user_information').removeClass('removeUser_info');
			   
			   
		   }
		   event.stopPropagation();
		
		
	});
	
$(document.body).on('click', '.file_upload_contact' ,function(event){	
	//$('.file_upload').click(function(){
//		$(document.body).on('click', '.file_upload' ,function(event){	
			   if($('#upl2').val()=="")
				   {
				   
				   //  alert("It Is Blank")
				   
				   
				   }
			   else {
				   
				   $('.pop_for_image_upload_contact').hide();
					  $('.web_dialog_overlay').hide();
					 
				   
			   }
			   event.stopPropagation();
			
			
		});
	
$(document.body).on('click', '.tag_main' ,function(event){
		
		$('.folder_div').hide();
		   
	     event.stopPropagation();
		 
	   });
	
$(document.body).on('click', '.tag_inner_content>ul>li' ,function(event){
		
		$('.folder_div').hide();
		   
	     event.stopPropagation();
		 
	   });
	
	$(document.body).on('click', '.folder_view' ,function(event){
		$('.tag_content').hide();
		   
	     event.stopPropagation();
		 
	   });

	
	
	
	var window_height =$(window).innerHeight(); // NEW LINE ADD  Stared HERE
	 window.half_height = window_height;   
	
	/// TEST FOR MORE PRODUCT 
	
	
	/*  Start movetofolder   */
 $(document.body).on('click','.folder_view',function(event){
    ///$('.folder_view').click(function(e){
	 $('.search_form_1').hide();
	 $('.more_product_content').hide();
	 $('.user_noti_content').hide();
	 $('.user_information').hide();
		    var folder_left =$(this).offset().left;
			var folder_top =$(this).offset().top;
			$('.folder_div').css('top',(folder_top+27));
			$('.folder_div').css('left',(folder_left));
			$('.folder_div').css('display','block');
			event.stopPropagation();
			
			});

	/*  End movetofolder   */
	//$(document.body).on('click', 'html' ,function(event){	
	$('html').click(function(){
		//$(document.body).on('click', 'html' ,function(){	
        $('.more_product_content').hide();
        $('.user_noti_content').hide();
		  // $('.search_box_details').hide();
		  $('.chat_search').hide();
		  $('.con_more').hide();
		  $('.tag_content').css('display','none');
		  $('.folder_div').hide();
		  
		  // NEW TEST 
		  $('.chat_h_right>img').removeClass('img_block'); 
		  $('.left_arrow').css('display','none');
		  $('.left_arrow').removeClass('down_arrow_arrow');
		  $('.chat_search_11').hide();
			//	  $('.chat_search').hide();
           //$('.chat_down_main').hide();
		  $('.chat_h_right>img').removeClass('img_block'); 
		  $('.left_arrow').removeClass('down_arrow_arrow');
		  $('.left_arrow').css('display','none');
			

		  $('.chat_info').removeClass('chat_info_hide');
		  $('.chat_h_right').show();
		  // NEW  TEST 
		 
		  
        return true;
        });
		  
		  
	
	
	
		  $(document.body).on('click', '.first_con_option_3' ,function(event){

		 event.stopPropagation();
		
	});
		  $(document.body).on('click', '.datepick-popup' ,function(event){

				 event.stopPropagation();
				
			});
		   
		   /// MORE PRODUCTS
		  //  $(document.body).on('click', '.more_product' ,function(event){
       //  event.stopPropagation();
      //  });
		    
		 // $('.more_product').click(function(){
		// $(document.body).on('click', '.more_product' ,function(event){
			// alert('hii')
		    //     event.stopPropagation();
		    //    });
		   
		   /// CHAT DOWN ARROW 
		   
		    $(document.body).on('click', '.chat_search' ,function(event){
			   
			     event.stopPropagation();
				 
			   });
		    
		    $(document.body).on('click', '.chat_search_11' ,function(event){
				   
			     event.stopPropagation();
				 
			   });
	
	
	/// TEST FOR MORE PRODUCTS
	
	
	
	
	
	
	
	
	window.left_scollx = $(window).height();
	//alert(left_scollx);
	$('.mail_content_1').css('height',left_scollx-104);
	
	$('.fldr_height').css('height',left_scollx-104);
	$('.fc-view-container').css('height',left_scollx-104);
	//$('.full_view_content').css('height',left_scollx-71);
	//alert(left_scollx);
	/// INNER MAIL FILE STARED HERE
	var inbox_height = $(window).height();
	
	window.mail_inner_height = inbox_height -170;
	$('#widget_calender').css('height',mail_inner_height +85);
	$('.tab_first_content').css('height',mail_inner_height +3);

	$('.left_flag').css('height',mail_inner_height +51);
	$('#widget').css('height',mail_inner_height + 45 );
	
	
	
	
	
	//// ********** NEW JS CODE  ***************** ///
	
	$(window).resize(function() {
		  //update stuff
		
		window.left_scollx = $(window).height();
		//alert(left_scollx);
		/*$('.mail_content_1').css('height',left_scollx-104);*/
		
		$('.fldr_height').css('height',left_scollx-104);
		$('.fc-view-container').css('height',left_scollx-104);
		//$('.full_view_content').css('height',left_scollx-71);
		//alert(left_scollx);
		/// INNER MAIL FILE STARED HERE
		var inbox_height = $(window).height();
		
		window.mail_inner_height = inbox_height -170;
		$('#widget_calender').css('height',mail_inner_height +85);
		$('.tab_first_content').css('height',mail_inner_height +3);

		$('.left_flag').css('height',mail_inner_height +51);
		$('#widget').css('height',mail_inner_height + 45 );
		
		
		
			
		});
	
	//// ************ NES JS CODE **************** ///
	
	
	
	
	
	
	
	
	/*$('#widget_setting').css('height',mail_inner_height  );*/

/*	$('.tab_first_content').css('overflow','auto');*/
	//alert(mail_inner_height);
	/// INNER MAIL FILE END HERE 
	
	//test
	//$('.row_content').click(function(){
		//alert('Hi');
		
      // var rowclick  = 0;
	 // if( rowclick  == 0)
	//  {
	//	  $('.mail_checked').css('input[type="checkbox"]','checkbox')
	//  }
		  //		});
		  
		    $(document.body).on('click', '.down_arrow' ,function(){
			  
			   if($('.search_form_1').css('display')=='none')
			   {
				
				   $('.search_form_1').addClass('search_display');
				   $('.more_product_content').hide();
				   $('.user_noti_content').hide();
				   $('.folder_div').hide();
				   $('.tag_content').hide();
			   }
			   else
			   {
				   $('.search_form_1').removeClass('search_display');
				   $('.more_product_content').hide();
			   }
			    
			  
			  });
			  
			  
			    /* $(document.body).on('click', '.row_check_box:first-child' ,function(){	  */
		$(document.body).on('click', '.row_check_box' ,function(event){	
		   // $('.row_check_box:first-child').click(function(event){ 
		// $('.row_check_box').click(function(event){  
           //  alert('Hi');
		  $(this).parent().parent().parent().parent().toggleClass('selected_row');
		  
		  var count = $('.mail_checked:checked').length;
		 // alert(count);
		  if( count == 0)
		      {
			   // alert('Hi-1');
				 $('.row_content').removeClass('selected_row');
			     $('.search_form_tool').css('display', 'none');
				 $('.search_form_tool').removeClass('tool_display');
				 $('.tag_main').css('display','none');
				 $('.folder_view').css('display','none');
			  }
			  else if(count == 1)
			  {
				//  alert('Hi-2');
				  $('.search_form_tool').css('display', 'block');
				  $('.hidden_option').removeClass('disable');
				  $('.search_form_tool').addClass('tool_display');
				  $('.tag_main').css('display','block');
				  $('.folder_view').css('display','block');
		      }
			  
			  else if(count >1)
			  { 
				 // alert('Hi-3');
			       $('.search_form_tool').css('display', 'block');
				   $('.hidden_option').addClass('disable');
				   $('.tag_main').css('display','block');
				   $('.folder_view').css('display','block');
			  }
		 // alert("end");
		   event.stopPropagation();
		  
	});
    // initiate layout and plugins
    // App.init();
    // Charts.init();
    // Charts.initCharts();
    // Charts.initPieCharts();
    //$('.row_content').click(fortool);
});

function lunchboxOpen(lunchID) {
    document.getElementById('lunch_' + lunchID).style.display = "block";
    document.getElementById('clasp_' + lunchID).innerHTML = "<a href=\"javascript:lunchboxClose('" + lunchID + "');\">Close Lunchbox " + lunchID + "...</a>";
}
function lunchboxClose(lunchID) {
    document.getElementById('lunch_' + lunchID).style.display = "none";
    document.getElementById('clasp_' + lunchID).innerHTML = "<a href=\"javascript:lunchboxOpen('" + lunchID + "');\">Open Lunchbox " + lunchID + "...</a>";
}

// to show and hide mail view panel
// to show and hide mail view panel
function toggleViewPanel() {
	
	
	
	var rightInner = $('.right-pane').innerWidth();
 	$('.tab_first_content').css('width',rightInner-10);
 	window.mail_inner_height =( $(window).height()) -170;
 	$('.main_tab_first').css('height',mail_inner_height +3);
 	$('.tab_first_content').css('height',mail_inner_height +3);
	$('.vsplitbar').removeClass('new_spertator');
	 $('.mid_tab').addClass('overflow_auto');
	 $('.mail_content').removeClass('new_mail_right_1');
 	//********** TEST ---------------/
 	$(window).resize(function() {
		
 		var rightInner = $('.right-pane').innerWidth();
 	 	$('.tab_first_content').css('width',rightInner-10);
 	 	window.mail_inner_height =( $(window).height()) -170;
 	 	$('.main_tab_first').css('height',mail_inner_height +3);
 	 	$('.tab_first_content').css('height',mail_inner_height +3);
 		$('.vsplitbar').removeClass('new_spertator');
 		$('.mail_content').removeClass('new_mail_right_1');
 		
			
		});
 	
 	// *********** TEST ************ /
 	
 	
 	
 	
 	$('.tab_first_content').css('position','absolute');
 	$('.main_tab_first').css('position','initial');
	
	$('#widget').css('height','auto');
	$('.new_flag').css('display','none');
	$('.new_flag_color').css('display','none');
	$('.tab_first_content').removeClass('left_flag');
    //$('.widget_new').addClass('full_scroll');
    $('.widget_new').addClass('full_inbox');
    // $('#widget').css('overflow','hidden')
    $('.widget_new').removeClass('left_mail');
	$('.row_content').addClass('pading_main');

	$('.mid_tab').css('top','80');
	$('.top_bottom_1').removeClass('top_margin');
	
	$('.mail_content').removeClass('mail_con_righ');
	$('.mail_content_1').removeClass('new_mail_light');
	$('.tab_main_div').removeClass('tab_left_con');
	$('.tab_main_div').removeClass('tab_left_con_top');
	$('.row_first').removeClass('mail_de_op');
	$('.top_tool_information').css('display','block');
	$('.row_left').removeClass('left_view_con')
    $('.mid_tab').attr('style', '');
    $('.mail_content').attr('style', '');
    $('.row_content').removeClass('left_view_mess');
    // reverting back from bottom view        
    //$('.mid_tab').css('height', '85%');
    // reverting back from left view
    $('.mid_tab').css('width', '100%');
    $('.mid_tab').css('float', '');
   // $('.mail_content').css('height', '37%');
    $('.mail_content').css('width', '100%');
    $('.mail_content').css('display', 'none');
    $('.small_image_flag').css('display','block');
    // turn off splitter 
    $('.vsplitbar').remove();
    $('.hsplitbar').remove();
    leftCalled = false;
    bottomCalled = false;
    leftPane = false;
    rightPane = false;
}
// to shift the view panel to left of the mailbox panel
function shiftViewLeft() {
    if (!leftPane) {
        toggleViewPanel();
        
        $('.mid_tab').removeClass('overflow_auto');
        $('.tab_first_content').css('height',mail_inner_height +51);
        $('.mail_content').addClass('new_mail_right_1');
        
      //********** TEST ---------------/
     	$(window).resize(function() {
     		 $('.tab_first_content').css('height',mail_inner_height +51);
     		  $('.tab_first_content').css('width','auto');
     		 $('.mail_content').addClass('new_mail_right_1');
    		});
     	
     	// *********** TEST ************ /
        
        
        $('.main_tab_first').css('height','auto');
        $('.tab_first_content').css('width','auto');
        $('.tab_first_content').css('position','relative');
        
        $('.main_tab_first').css('position','auto');

                //$('.widget_new').addClass('full_scroll');
        $('.widget_new').removeClass('full_inbox');
        $('.new_flag').css('display','block');
        $('.new_flag_color').css('display','block');
        $('.tab_first_content').addClass('left_flag');         
		$('#widget').css('height','auto');
		$('.widget_new').addClass('left_mail');
		//$('#widget').css('overflow','hidden')
		$('.small_image_flag').css('display','none');
		$('.mid_tab').removeClass('bottom_tab');
		$('.row_content').removeClass('pading_main');
		$('.vsplitbar').addClass('new_spertator');
		$('.mid_tab').css('top','46');
		$('.top_tool_information').css('display','none');
		$('.top_bottom_1').removeClass('top_margin');
		
		$('.mail_content_1').addClass('new_mail_light');
		$('.tab_main_div').addClass('tab_left_con');
		$('.tab_main_div').addClass('tab_left_con_top');
		
		$('.mail_content').removeClass('mail_right_con');
        $('.mid_tab').css('float', 'left');
		$('.row_left').addClass('left_view_con');
		$('.row_content').addClass('left_view_mess');
		$('.row_first').addClass('mail_de_op');
        //$('.mid_tab').css('width', '50%');

       // $('.mail_content').css('height', '85%');
        //$('.mail_content').css('width', '49%');
        $('.mail_content').css('display', 'block');
		$('.mail_content').css('top','46');
        if (!leftCalled) {
            $('#widget').unbind();
            /*$('#widget').splitter({type: 'v'});*/
            $('#widget').splitter({splitVertical: true});
            leftCalled = true;
        } else {
            $('.vsplitbar').css('display', '');
        }
        $('.hsplitbar').remove();
        bottomCalled = false;
        leftPane = true;
    }
}
var leftCalled = false;
var bottomCalled = false;
var leftPane = false;
var rightPane = false;

function shiftViewBottom() {
    if (!rightPane) {
		$('.mid_tab').addClass('bottom_tab');
		$('.row_content').addClass('pading_main');
		$('.vsplitbar').removeClass('new_spertator');
		$('.mid_tab').css('top','80');
		$('.top_tool_information').css('display','block');
		$('.top_bottom_1').addClass('top_margin');
		$('.mail_content').addClass('mail_right_con');
		$('.mail_content').removeClass('new_mail_right_1');
		$('.mail_content').removeClass('mail_con_righ');
		$('.mail_content_1').removeClass('new_mail_light');
		$('.tab_main_div').removeClass('tab_left_con');
		$('.tab_main_div').removeClass('tab_left_con_top');
		 $('.small_image_flag').css('display','block');
	    $('.row_first').removeClass('mail_de_op');
		$('.row_left').removeClass('left_view_con')
		$('.row_content').removeClass('left_view_mess');
        toggleViewPanel();
        $('.mid_tab').css('height', '38%');
        $('.mail_content').css('display', 'block');
        //$('.mail_content').css('height', '37%');
		$('.mail_content').css('top','0');
	


        if (!bottomCalled) {
            $('#widget').unbind();
            $('#widget').splitter({type: 'h'});
            bottomCalled = true;

		
        
        } else {
            $('.hsplitbar').css('display', '');
        }
        $('.vsplitbar').remove();
        leftCalled = false;
        rightPane = true;
    }
}

function tabfirst() {

    if ($('.tab_first_content').css('display') == "block")
    {
		$('.tab_first_content').animate({
				height: "0px",
			},{
				duration: 500, 
				specialEasing: {
					height: "linear"
				},
				complete: function() {
					$('.tab_first_content').css('display', 'none');
				}
			}
		);
		
    }else {
		$('.tab_first_content').css('display', 'block');
		$('.tab_first_content').animate({
				height: "300px",
			},{
				duration: 500, 
				specialEasing: {
					height: "linear"
				},
				complete: function() {
					
				}
			}
		);
    }
}



function tabfirst1() {

    if ($('.tab_first_content1').css('display') == "block")
    {

        $('.tab_first_content1').animate({
				height: "0px",
			},{
				duration: 500, 
				specialEasing: {
					height: "linear"
				},
				complete: function() {
					$('.tab_first_content1').css('display', 'none');
				}
			}
		);

    }

    else {

        $('.tab_first_content1').css('display', 'block');
		$('.tab_first_content1').animate({
				height: "300px",
			},{
				duration: 500, 
				specialEasing: {
					height: "linear"
				},
				complete: function() {
					
				}
			}
		);


    }



}



function tabfirst2() {

    if ($('.tab_first_content2').css('display') == "block")
    {

        $('.tab_first_content2').animate({
				height: "0px",
			},{
				duration: 500, 
				specialEasing: {
					height: "linear"
				},
				complete: function() {
					$('.tab_first_content2').css('display', 'none');
				}
			}
		);

    }

    else {

        $('.tab_first_content2').css('display', 'block');
		$('.tab_first_content2').animate({
				height: "300px",
			},{
				duration: 500, 
				specialEasing: {
					height: "linear"
				},
				complete: function() {
					
				}
			}
		);


    }



}

function tabfirst3() {

    if ($('.tab_first_content3').css('display') == "block")
    {

        $('.tab_first_content3').animate({
				height: "0px",
			},{
				duration: 500, 
				specialEasing: {
					height: "linear"
				},
				complete: function() {
					$('.tab_first_content3').css('display', 'none');
				}
			}
		);

    }

    else {

        $('.tab_first_content3').css('display', 'block');
		$('.tab_first_content3').animate({
				height: "300px",
			},{
				duration: 500, 
				specialEasing: {
					height: "linear"
				},
				complete: function() {
					
				}
			}
		);


    }



}


function fortool()
{
    $('.search_form_tool').css('display', 'block');
    $('.row_content').removeClass('selected_row');
    $(this).addClass('selected_row');
}
// comment at 08-11-2014
function userinformation(e)
{

   if ($('.user_information').css('display') == 'none') {
		$('.search_form_1').css('display','none');


        $('.user_information').css('display', 'block');
		$('#search_form').css('display','none');
		$('.more_product_content').css('display','none');
		$('.user_noti_content').hide();
		$('.folder_div').hide();
		$('.tag_content').hide();
		
		showUqouta();

    }else {
         
		 $('.search_form_1').css('display','none');
        $('.user_information').css('display', 'none');
        $('.user_noti_content').hide();
        if($('.more_product_content').css('display')=='block')
    	{
    	//alert('hi');
    	$('.more_product_content').css('display','none');
    	}

   }
	e.stopPropagation();
	

}
// USER INFO TOP
//$('.new_user').click(function(){
	
	 //  if($('.user_information').css('display')=='none')
	 //  {
	//	 $('.user_information').addClass('user_info_top');
		   
	//	}
	//	else
		//   {
	//		$('.user_information').removeClass('user_info_top')
	//		}
	//
	//});
//
function option_here()
{
    if ($('.mail_down_option').css('display') == "none")
    {
        $('.mail_down_option').css('display', 'block');
    }

    else {

        $('.mail_down_option').css('display', 'none');

    }

}

$(document.body).on('click','.option_npmore',function(event){
	  
	   if ($('.mail_down_option_1').css('display') == "none")
	    {
		   var box_top = $(this).position().top;
		   var box_left = $(this).position().left;
		   $('.mail_down_option_1').css('top',box_top+25);
		 /*  $('.mail_down_option_1').css('left',box_left-122);*/
	        $('.mail_down_option_1').css('display', 'block');
	    }

	    else {

	        $('.mail_down_option_1').css('display', 'none');

	    }
	   $('.to_me').css('display', 'none');
		event.stopPropagation();
});

/*function option_here_1(event)
{
	
    if ($('.mail_down_option_1').css('display') == "none")
    {
        $('.mail_down_option_1').css('display', 'block');
    }

    else {

        $('.mail_down_option_1').css('display', 'none');

    }
	event.stopPropagation();

}*/



function mail_forward()
{

    if ($('.your_option').css('display') == "block")
    {

        $('.your_option').css('display', 'none');
        $('.mail_forward').css('display', 'block');


    }

    else {
        $('.your_option').css('display', 'block');
        $('.mail_forward').css('display', 'none');

    }



}


function mail_forward_1()
{

    if ($('.your_option_1').css('display') == "block")
    {

        $('.your_option_1').css('display', 'none');
        $('.mail_forward_1').css('display', 'block');


    }

    else {
        $('.your_option_1').css('display', 'block');
        $('.mail_forward_1').css('display', 'none');

    }



}


function mail_forward_11()
{

    if ($('.your_option_1').css('display') == "block")
    {

        $('.your_option_1').css('display', 'none');
        $('.mail_forward_11').css('display', 'block');


    }

    else {
        $('.your_option_1').css('display', 'block');
        $('.mail_forward_11').css('display', 'none');

    }



}





function down_mail(event)
{


    if ($('.main_bottom_option').css('display') == "none")
    {

        $('.main_bottom_option').css('display', 'block');



    }


    else {

        $('.main_bottom_option').css('display', 'none');


    }

event.stopPropagation();
}



function bootom_forward() {


    if ($('.for_bottom').css('display') == "none")
    {

        $('.for_bottom').css('display', 'block');

    }

    else {


        $('.for_bottom').css('display', 'none');

    }

event.stopPropagation();
}


function mail_to(event) {
   

  

}


/// MOUSE OVER TOOL TIPS 
	var popout=0;
$(document).ready(function(e) {
	/* scrollbar start */
	/// test !
	
	
	$(document.body).on('click', '.change_images' ,function(event){	
//$('.change_images').click(function(){
		//	$(document.body).on('click', '.change_images' ,function(event){			
//		  alert('Hi');
		  $('.pop_for_image_upload').show();
		  $('.web_dialog_overlay').show();
		  $('.user_information').removeClass('user_info_top');
		//  $('.user_info_top').hide();
		  $('.user_information').hide();
		  $('.user_information').addClass('removeUser_info');
		  
		
		   event.stopPropagation();
		
		});
		
	
	
$(document.body).on('click', '.pop_for_image_upload >h1 >span' ,function(event){	
	//$('.pop_for_image_upload >h1 >span').click(function(){
		$('.pop_for_image_upload').hide();
		  $('.web_dialog_overlay').hide();
		  $('.user_information').removeClass('user_info_top');
			//  $('.user_info_top').hide();
			 // $('.user_information').show();
			  $('.user_information').removeClass('removeUser_info');
			   event.stopPropagation();
		});

$(document.body).on('click', '.pop_for_image_upload_ck >h1 >span' ,function(event){	
	//$('.pop_for_image_upload >h1 >span').click(function(){
		$('.pop_for_image_upload_ck').hide();
		  $('.web_dialog_overlay').hide();
		
		/*  $('.user_information').removeClass('user_info_top');
			//  $('.user_info_top').hide();
			 // $('.user_information').show();
			  $('.user_information').removeClass('removeUser_info');*/
			   event.stopPropagation();
		});

$(document.body).on('click', '.pop_for_image_upload_contact >h1 >span' ,function(event){	
	//$('.pop_for_image_upload >h1 >span').click(function(){
		$('.pop_for_image_upload_contact').hide();
		  $('.web_dialog_overlay').hide();
		 //event.stopPropagation();
		});

$(document.body).on('click', '.pop_for_image_upload_contact_edit >h1 >span' ,function(event){	
	//$('.pop_for_image_upload >h1 >span').click(function(){
		$('.pop_for_image_upload_contact_edit').hide();
		  $('.web_dialog_overlay').hide();
		 //event.stopPropagation();
		});
	
	/* scrollbar end */
/*	$(document.body).on('mouseover', '.cheat_row' ,function(event){
		$('.chat_info').show();
		$('.chat_info' ).offset({  top:$(this).offset().top - $(this).height(), left: $(this).offset().left + $(this).width()+11 });
		var user_name = $(this).children('.contact_information').children().children('strong').html();
		var user_email = $(this).children('.contact_information').children().children('span').html();
		var user_images = $(this).children('.small_images').children('.user_images').attr('src');
		
		 $('p.name').html(user_name);
         $('.chat_info_left >span').html(user_email);
         $('.chat_info_right').html('<img src ="'+ user_images +'" />');
        
		
		var height = $(this).offset().top + $('.chat_info').height();
		var innerheight =window.innerHeight; + window.scrollY;
		
		if( height > innerheight){
			   var thatVal = $('.chat_info' ).offset().top;
			   var allHeight = height-innerheight - 30;
			   var total =  thatVal-allHeight;
			  $('.chat_info' ).css('top',total);
			  
			}
		   event.stopPropagation();
	});
	*/
	$('.chat_info').mouseout(function(){
		popout=0;
	        $('.chat_info').hide();
		
		});
		$('.chat_info').mouseover(function(){
			popout=1;
	        $('.chat_info').show();
		
		});
	
/*	$('.cheat_row').mouseout(function(){
		if(popout==0)
		{
			$('.chat_info').css('display','none');
		}
	});*/
});	
	
	
	
	
	
	
	
	
	
	/// Chat Search Option Here 
$(document.body).on('click', '.chat_h_right' ,function(event){
//$('.chat_h_right').click(function(event){
	// $(document.body).on('click', '.chat_h_right' ,function(){	
		// alert("hi");
			var adjust_height;
		     var chat_search_top = $(this).offset().top +  $('.chat_search').height();
		    var  chat_search_top_1 = $(this).offset().top;
		    var  chat_search_left = $(this).offset().left-157;
		   //  alert(chat_search_top)
			 var chat_search_1 = window.innerHeight + window.scrollY;
          //    alert(chat_search_1)  
			 $("#np_chat_search").show();
				$("#np_chat_searchajax").hide();
			 $("#serchCntId").val("");
		if($('.chat_search').css('display')=='none')
		{
			//alert('Hi');
			$('.left_arrow').css('display','block');
			$('.chat_search').css('top',chat_search_top_1);
			$('.chat_search').css('left',chat_search_left);
			$('.chat_search').css('display','block');
			$('.chat_search_11').css('display','none');
			
			/// TEST 1
			 // $('.chat_h_right>img').addClass('img_block'); 
			  
			//  $('.left_arrow').addClass('down_arrow_arrow');
			
			/// TEST 2
			
			
			
			if( chat_search_top > chat_search_1)
			 {
				 var adjust_height =(($(this).offset().top-30) - (chat_search_top - chat_search_1 )) ;
				$('.chat_search').offset({ top: adjust_height});
				 
			}
			 
			// $('.chat_search').offset( { top:adjust_height,left:$(this).offset().left + $(this).width() + 25 } )
			 $('.chat_info').addClass('chat_info_hide');
		
			}
			else
			{
				/// TEST
				//  $('.chat_h_right>img').addClass('img_block'); 
				  $('.left_arrow').css('display','none');
				 // $('.left_arrow').addClass('down_arrow_arrow');
				/// TEST END 
				$('.chat_search').css('display','none');
				$('.chat_info').removeClass('chat_info_hide');
			}
		event.stopPropagation();

		});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/// MOUSE OVER TOOL TIPS 
	var popout=0;
$(document).ready(function(e) {
	
$(document.body).on('mouseover', '.cheat_row_11' ,function(event){
	
 //$('.cheat_row_11').mouseover(function(){
		
		$('.chat_info_1').show();
		$('.chat_info_1' ).offset({  top:$(this).offset().top - $(this).height(), left: $(this).offset().left + $(this).width() });
		//alert(window.scrollTop()+ window.innerHeight());
		
		var user_name = $(this).children('.contact_information').children().children('strong').html();
		var user_email = $(this).children('.contact_information').children().children('span').html();
		var user_images = $(this).children('.small_images').children('.user_images').attr('src');
		
		 $('p.name').html(user_name);
         $('.chat_info_left >span').html(user_email);
         $('.chat_info_right').html('<img src ="'+ user_images +'" />');
         
         try
         {
        	 $('#chat_search_mail').attr('name',user_email.trim());
        	 $('.chat_mail_comp').attr('id',user_email.trim());
        	 $('#chat_search_mail').attr('title',"Search Mails");
        	 $('.chat_mail_comp').attr('title',"Write Mail");
         }
         catch (e) {
			// TODO: handle exception
		}
		 var listing=$('#roasterListing').val();
			//alert(listing.indexOf(user_email));
		 if(!listing)
			 listing="";
         if(listing.indexOf(user_email.trim())<0){
 	        $('#chat_mail_plus').attr('class','chat_mail_plus');
 	        $("#buddyInvite").attr('val',user_email.trim());
 	       $('#chat_mail_plus').attr('title','Add Friend');
	        $('#chat_mail_plus').attr('onclick','sendBuddyInvite()');
	   	 //	alert("tt");
         }
         else{
        	 // alert("ff");
             $('#chat_mail_plus').attr('class','chat_mail_minus');
             $('#chat_mail_plus').attr('title','Remove Friend');
             $('#chat_mail_plus').attr('onclick',"remChatFriend('"+user_email.trim()+"')");
         }
		var height = $(this).offset().top + $('.chat_info_1').height();
		var innerheight =window.innerHeight + window.scrollY;
		/*if( height > innerheight){
	           
			   $('.chat_info_1' ).offset({ top:$(this).offset().top - (height-innerheight)});
			  
			}
			
			*/
		
		
		if( height > innerheight){
			//alert('stop');
			
	           var nextposition = $(this).offset().top;
			  // var findNext = height-innerheight+60;
	           var findNext = height-innerheight;
			   var total = nextposition - findNext
			   $('.chat_info_1' ).css('top',total);
			  // alert(nextposition);
			  // alert(findNext);
			 //  alert(total);
			  
			}
		
		   event.stopPropagation();
		//alert($(this).offset().top + " " + $(this).offset().left); 
		//alert($(this).width());
	});
	
	$('.chat_info_1').mouseout(function(){
		popout=0;
	        $('.chat_info_1').hide();
		
		});
		$('.chat_info_1').mouseover(function(){
			popout=1;
	        $('.chat_info_1').show();
		
		});
	
	$('.cheat_row_11').mouseout(function(){
		if(popout==0)
		{
			$('.chat_info_1').css('display','none');
		}
	});
});	
	
	
	
	
	
	
	
	
	
		/// Chat Downarrow Only Option Here 
$(document.body).on('click', '.h_drop' ,function(event){
//$('.h_drop').click(function(event){
	
	//alert('hi-hh');
// $(document.body).on('click', '.h_drop' ,function(){		
			var adjust_height;
		     var chat_search_top = $(this).offset().top +  $('.chat_search').height();
		     var chat_search_top_11 = $(this).offset().top;
		     var chat_search_left = $(this).offset().left;
			 var chat_search_1 = window.innerHeight + window.scrollY;
			// $('.chat_search_11').css('display','block');
			// alert(chat_search_1);
		if($('.chat_search_11').css('display')=='none')
		{
			//$('.chat_h_right').hide();
			/*$('.chat_search_11').css('left',chat_search_left - 20);*/
			$('.chat_search_11').css('top',chat_search_top_11 -30);
			$('.chat_search').css('display','none');
			$('.chat_search_11').css('display','block');
			
			/// TEST 
			
			 // $('.chat_h_right>img').css('display','none');
			  $('.chat_h_right>img').addClass('img_block'); 
			  $('.left_arrow').css('display','block');
			  $('.left_arrow').addClass('down_arrow_arrow');
			
			
			// TEST END 
			
			
			
			// var adjust_height =($(this).offset().top - (chat_search_top - chat_search_1 )) ;
			$('.chat_search_11').css('top',chat_search_top_11);
		$('.chat_search_11').css('left',chat_search_left-131);
		//	alert('Outer alert');
			if( chat_search_top > chat_search_1)
			 {
			//	alert('Inner alert');
				var topDrop = chat_search_top - chat_search_1 ;
				//alert(topDrop);
				var FullDrop =  $(this).offset().top 
				var totalDrop = FullDrop -topDrop;
				// var adjust_height =($(this).offset().top - (chat_search_top - chat_search_1 )) ;
				$('.chat_search_11').css('top',totalDrop);
				//$('.chat_search').offset({ top: adjust_height});
				
			}
			 
			 // $('.chat_search_11').offset( { top:adjust_height,left:$('.chat_h_right').offset().left + 35 } )
		         $('.chat_info').addClass('chat_info_hide');
			}
			else
			{
				// TEST 
				
				$('.chat_h_right>img').removeClass('img_block'); 
				$('.left_arrow').removeClass('down_arrow_arrow');
				$('.left_arrow').css('display','none');
				
				
				// TEST END 
				
				$('.chat_search_11').css('display','none');
 
				$('.chat_info').removeClass('chat_info_hide');
				$('.chat_h_right').show();
				//$('.chat_h_right').css('display','block');
			}

		event.stopPropagation();
		});
		
		
		
		
/// CHAT DOWN ARROW EVENT

//// Part 1
$(document.body).on('click', '.chat_down_main ul li' ,function(){
	
	if($('.chat_down_submenu').css('display')=='none'){
		
		    $('.chat_down_submenu').css('display','block');
			$('.chat_downarrow').css('display','none')
		
		}
	
	}
);		

/// Part 2
$(document.body).on('click', '.chat_main_menu' ,function(){

	
	if($('.chat_downarrow').css('display')=='none'){
		$('.chat_downarrow').css('display','block');
		$('.chat_down_submenu').css('display','none');
		
		
		}
	
	
	});
	
	/// Chat Menu Stared Here 
	$(document.body).on('click', '.chat_status_menu' ,function(){       
	   if($('.chat_status').css('display')=='none'){
		   
		   $('.chat_status').css('display','block');
		   $('.Blocked_status').css('display','none');
		   $('.Invites_status').css('display','none');
		   $('.Share_status').css('display','none');
		   
		   }
	   
	   
	});
		
			/// Invites Stared Here 
			$(document.body).on('click', '.invites_menu' ,function(){ 
	
	   if($('.Blocked_status').css('display')=='none'){
		   
		   $('.chat_status').css('display','none');
		   $('.Blocked_status').css('display','block');
		   $('.Invites_status').css('display','none');
		   $('.Share_status').css('display','none');
		   
		   }
	});
	
				/// Invites Stared Here 
					$(document.body).on('click', '.blocked_menu' ,function(){ 
	
	   if($('.Invites_status').css('display')=='none'){
		   
		   $('.chat_status').css('display','none');
		   $('.Blocked_status').css('display','none');
		   $('.Invites_status').css('display','block');
		   $('.Share_status').css('display','none');
		   
		   }

	});
	
					/// Invites Stared Here
	$(document.body).on('click', '.share_your_menu' ,function(){ 
	
  if($('.Share_status').css('display')=='none'){
		   
		   $('.chat_status').css('display','none');
		   $('.Blocked_status').css('display','none');
		   $('.Invites_status').css('display','none');
		   $('.Share_status').css('display','block');
		   
		   }
	});
	
	
		
		
			// CHAT SIGN IN BOX 
				$(document.body).on('click', '.chat_sign_1_box' ,function(){ 
		
		if($('.chat_box_inner').css('display')=='none'){
			
			
			$('.chat_sign_box').css('display','none');
			$('.chat_box_inner').css('display','block');
			
			
			
			}
		
		});
		
		///Tool Menu 
		
		
	var popout_1=0;
$(document).ready(function(e) {	
	
	//alert($("#hid_previewPane").val());
	
	if($("#hid_previewPane").val()=="Vertical view")
		{
		shiftViewLeft();
		}
	else
		{
		toggleViewPanel();
		}
	
	/// TEST 
	//$(document.body).on('click', '.down_arrow' ,function(){ 
	
		//alert('Hi');
		//$('.more_product_content').hide();
		
	//});
	
	/// TEST 
	
	
	$(document.body).on('mouseover', 'li.right_menu_1' ,function(event){ 
	//$('li.right_menu_1').mouseover(function(){
			var left_space = $(this).offset().left + $('.for_setting_1').width();
			var fullbody = window.scrollX + window.innerWidth;
			var extraspace = window.innerWidth - $('.content').offset().left - $('.content').width();
			$('.for_setting_1').css('display','block');
			 $('.for_setting_1').offset({top:$(this).offset().top,left:$(this).offset().left});
			 if( left_space > fullbody){
				 $('.for_setting_1').offset({left:$(this).offset().left - ( left_space - fullbody ) - extraspace -33});
				 
				 
				 }	
			   event.stopPropagation();
		}
			);
			
			});
							
	
$(document.body).on('mouseout', 'li.right_menu_1' ,function(event){ 
 // $('li.right_menu_1').mouseout(function(){
	popout_1=0
	
			$('.for_setting_1').hide();
	   event.stopPropagation();
    });

	/// MOUSE OVER TOOL TIPS 
	$(document.body).on('mouseover', '.for_setting_1' ,function(){ 
 //$('.for_setting_1').mouseover(function(){
			popout_1=1;
	        $('.for_setting_1').show();
	        event.stopPropagation();
	});
		
	
	$(document.body).on('mouseout', '.for_setting_1' ,function(){ 
	//$('.for_setting_1').mouseout(function(){
		 if(popout==0)
		{
	       $('.for_setting_1').hide();
		}
		   event.stopPropagation();
		});
		
/// Main Height Staed Here 

   $('.mid_tab').height()

	