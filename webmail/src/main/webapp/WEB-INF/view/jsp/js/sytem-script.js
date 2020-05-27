$(document).ready(function() {
    $('#hide_left').click(function(event) {
            $('.left-pane').css('display', 'none');
            $('.left_close').css('display', 'block');
            $('.right-pane').css('left','48px');
    });
    $('#show_left').click(function(event) {
            $('.left-pane').css('display', 'block');
            $('.left_close').css('display', 'none');
            $('.right-pane').css('left','192px');
    });
	$('.user_information.arrow_box').click(function(event){
		$(this).css('display','block');
		event.stopPropagation();
	});
	
	$('#search_form').click(function(event){
		$(this).css('display','block');
		event.stopPropagation();
	});
	
	// Mail Option
	
		$('.mail_down_option_1').click(function(event){
		$(this).css('display','block');
		event.stopPropagation();
	});
	
	
		// Mail Option
	
		$('.to_me').click(function(event){
		$(this).css('display','block');
		event.stopPropagation();
	});
	
	
			// Forward Mail
	
		$('.main_bottom_option').click(function(event){
		$(this).css('display','block');
		event.stopPropagation();
	});
	
	
			// Forward Mail
	
		$('.for_bottom').click(function(event){
		$(this).css('display','block');
		event.stopPropagation();
	});
	
	
	
	
	
    $(document).click(function(event) {
		hideMe();
    });
	
});

function hideMe(){
	$('.user_information.arrow_box').css('display','none');
	$('#search_form').css('display','none');
	$('.mail_down_option_1').css('display','none');
	$('.to_me').css('display','none');
	$('.main_bottom_option').css('display','none');
	$('.for_bottom').css('display','none');
	
	
}

