// JavaScript Document
jQuery(document).ready(function() {
// TAB 1

	
	$('ul.tabs >li').click(function(){
		$('li.setting_active').removeClass('setting_active');
		$(this).addClass('setting_active');


		});

	
	// WINDOW HIGHT FOR SETTING TAB STRAED HERE

	window.setting_tab = $(window).height();
	$('.tabcontents').css('height',setting_tab -179);

	// WINDOW HEIGHT FOR SETTING TAB END HERE
	
$('.view_1').click(function(){
	
	$('#view1').show();
	$('#view2').hide();
	$('#view3').hide();
	$('#view4').hide();
	$('#view5').hide();
	$('#view6').hide();
	$('#view7').hide();
	$('#view8').hide();
	$('#view9').hide();
	
	});
// TAB 1 END
// TAB 2
$('.view_2').click(function(){
	
	$('#view1').hide();
	$('#view2').show();
	$('#view3').hide();
	$('#view4').hide();
	$('#view5').hide();
	$('#view6').hide();
	$('#view7').hide();
	$('#view8').hide();
	$('#view9').hide();

	});
// TAB 2 END
// TAB 3
$('.view_3').click(function(){
	
	$('#view1').hide();
	$('#view2').hide();
	$('#view3').show();
	$('#view4').hide();
	$('#view5').hide();
	$('#view6').hide();
	$('#view7').hide();
	$('#view8').hide();
	$('#view9').hide();

	});
// TAB 3 END
// TAB 4
$('.view_4').click(function(){
	
	$('#view1').hide();
	$('#view2').hide();
	$('#view3').hide();
	$('#view4').show();
	$('#view5').hide();
	$('#view6').hide();
	$('#view7').hide();
	$('#view8').hide();
	$('#view9').hide();

	});
	

// TAB 4 END
$('.view_5').click(function(){
	
	$('#view1').hide();
	$('#view2').hide();
	$('#view3').hide();
	$('#view4').hide();
	$('#view5').show();
	$('#view6').hide();
	$('#view7').hide();
	$('#view8').hide();
	$('#view9').hide();
	
	});

$('.view_6').click(function(){
	
	$('#view1').hide();
	$('#view2').hide();
	$('#view3').hide();
	$('#view4').hide();
	$('#view5').hide();
	$('#view6').show();
	$('#view7').hide();
	$('#view8').hide();
	$('#view9').hide();
	
	});


$('.view_7').click(function(){
	
	$('#view1').hide();
	$('#view2').hide();
	$('#view3').hide();
	$('#view4').hide();
	$('#view5').hide();
	$('#view6').hide();
	$('#view7').show();
	$('#view8').hide();
	$('#view9').hide();

	});

$('.view_8').click(function(){
	
	$('#view1').hide();
	$('#view2').hide();
	$('#view3').hide();
	$('#view4').hide();
	$('#view5').hide();
	$('#view6').hide();
	$('#view7').hide();
	$('#view8').show();
	$('#view9').hide();
	
	});

$('.view_9').click(function(){
	
	$('#view1').hide();
	$('#view2').hide();
	$('#view3').hide();
	$('#view4').hide();
	$('#view5').hide();
	$('#view6').hide();
	$('#view7').hide();
	$('#view8').hide();
	$('#view9').show();

	});


});