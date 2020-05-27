// JavaScript Document

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

jQuery(document).ready(function() {
	
	
	
	


/// OPTION HERE FOR MAIL FORWARD  STARED HERE
	// REPLAY
		$(document.body).on('click','.replay_li',function(){		
		$('.replay_display').css('display','block');
		$('.forward_display').css('display','none');
		$('.reply_all_display').css('display','none');
		});
    // REPLAY ALL
		$(document.body).on('click','.replay_all_li',function(){
		$('.replay_display').css('display','none');
		$('.forward_display').css('display','none');
		$('.reply_all_display').css('display','block');
		});
    // FORWARD
	$(document.body).on('click','.forward_li',function(){
		$('.replay_display').css('display','none');
		$('.forward_display').css('display','block');
		$('.reply_all_display').css('display','none');
		});
		
	/// OPTION END HERE MAIL FORWARD END















  /// FOR ENTER UP THE FOR TO      
	           $('.forward_mail_event').keypress(function(e){
				 //  alert('Hi');
		        if(e.which == 13)
		        {
			  e.preventDefault();
			  var textarea = $('.forward_mail_event').val();
			  $('.forward_mail_event').val('');
		
		/// EMAIL VALIDATION HERE 
                var testEmail = /^[A-Z0-9._%+-]+@([A-Z0-9-]+\.)+[A-Z]{2,4}$/i;
              if (testEmail.test(this.value)){ 
                          $('input').siblings(".check").css('visibility', 'visible');
						  $('.to_content').removeClass('wrong_email')
                 }
                else{
					   $('.to_content').addClass('wrong_email')
                    }
		
		// EMAIL VALIDATION END HERE 
		
		
		
		
		
			 // var n_to = $('vR').length;
			  //alert(n);
			  	$('.combind_email').append('<div class="vR to_content"><span class="vN Y7BVp a3q" email="hari@silvereye.co"><div class="vT">' + textarea +'</div><div class="vM closeit"></div></span></div>');
			   var n_to = $('.to_content').length;
			  $('.email_id_info_to').text(n_to + " more");
		   }
		  })
	
	
	
		/// FOR ENTER UP THE FOR CC
});