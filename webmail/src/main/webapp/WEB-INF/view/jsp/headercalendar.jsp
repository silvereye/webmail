<%@page import="webmail.wsdl.CreateCalendarRequest"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.security.Principal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="images/favicon.ico" />
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link type="text/css" rel="stylesheet" href="css/blue.css" />
<link type="text/css" rel="stylesheet" href="css/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="css/main_css.css" />
<link href='css/jquery.splitter-bottom.css' rel='stylesheet'
	type='text/css' />
<link href="css/chat.css" rel="stylesheet" type="text/css" />
<link href="css/cal_setting.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" />
<!------<link rel="stylesheet" href="css/jquery-ui.css">--->
<script src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/jquery.noty.packaged.js"></script>
<script src="js/cal_setting.js"></script>
<script src="js/splitter.js"></script>
<script src="js/sytem-script.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/more_product.js"></script>
<script src="js/jquery.splitter-0.14.0.js"></script>
<script src="setting/jquery.plugin.js"></script>
<script src="setting/jquery.datepick.js"></script>
<link href='setting/jquery.datepick.css' rel='stylesheet'
	type='text/css' />
<link href='css/more_product.css' rel='stylesheet' type='text/css' />
<script src="js/jquery.autocomplete.js"></script>
<!------/// SCRIPT FOR CALENDER---------->
<script src="js/calender_js.js"></script>
<link type="text/css" rel="stylesheet" href="css/calender_css.css" />
<link rel="stylesheet" type="text/css" href="css/buttons.css" />
<link type="text/css" rel="stylesheet" href="css/calender_update.css" />
<script type="text/javascript">
	$(document).ready(
			function() {
				$('.popupDatepicker').datepick();
				/* 	$("#shareid_auto").autocomplete("${pageContext.request.contextPath}/autocomp");
				 $("#search_auto").autocomplete("${pageContext.request.contextPath}/autocomp"); 
				 $("#share_input").autocomplete("${pageContext.request.contextPath}/autocomp");*/

				$(".add_guest_name").autocomplete(
						"${pageContext.request.contextPath}/autocomp");
				$("#share_input").autocomplete("${pageContext.request.contextPath}/autocompldap");
			});
</script>
<!---------/// SCRIPT END ----------------->
<script type="text/javascript">
	/* function changeHBG(nm) {
	 //alert(nm);
	 $(".header").css("background-image","none");
	 $(".header").css("background-color",nm);
	 $(".search_button").css("background-image","none");
	 $(".search_button").css("background-color",nm);
	 $(".ui-widget-header").css("background-image","none");
	 $(".ui-widget-header").css("background-color",nm);
	 $(".composed_box_new").css("background-image","none");
	 $(".composed_box_new").css("background-color",nm);

	
	 } */
	function showmsg(type, msg) {
		var n = noty({
			text : msg,
			type : type,
			dismissQueue : false,
			layout : 'topCenter',
			theme : 'defaultTheme'
		});

		setTimeout(function() {
			$.noty.close(n.options.id);
		}, 5000);
	}
</script>
<style>
.vsplitbar {
	width: 5px;
	background: #aaa;
}
</style>
<style>
#spliter2 .a {
	background-color: #2d2d2d;
}

#spliter2 .b {
	background-color: #2d002d;
}
/* #foo {
	background-color: #E92727;
} */
#x {
	background-color: #EFBD73;
}

#y {
	background-color: #EF3e32;
}
/* #b {
	background-color: #73A4EF;
} */
#bar {
	background-color: #BEE927;
}

.vsplitbar {
	width: 2px;
	background: #f5f5f5;
	margin-top: -20px;
	cursor: col-resize !important;
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
}
/*---------------- CSS FOR TABLE ------*/
table.heading_guest {
	width: 95%;
	border-spacing: 0px;
}

.guest_content_11 {
	height: 149px;
	overflow: auto;
	padding-right: 15px;
}

table.append_guest {
	width: 95%;
	border-spacing: 0px;
}

.guest_td {
	width: 73px;
}

.close_guest>img {
	display: block;
	margin: 0 auto;
	cursor: pointer;
}

.close_guest_1>img {
	margin: 0 auto;
	display: block;
}

.heading_guest>tbody>tr>td>span {
	float: left;
}

li {
	list-style-type: none;
}
</style>


<script type="text/javascript">



	$(document).ready(function() {

		function generate(type) {
			var n = noty({
				text : type,
				type : type,
				dismissQueue : false,
				layout : 'topCenter',
				theme : 'defaultTheme'
			});

			return n;
		}

		/* function npcall() {
			var success = generate('alert');
			$.noty.setText(success.options.id, 'Event Created'); // same as alert.setText('Text Override')
			setTimeout(function () {
			            $.noty.close(success.options.id);
			        }, 3000);   
		}
		
		$( ".left_top" ).click(function(){
			npcall();
		}); */
		setTimeout(function(){ showcalNoti(); }, 12000);
	});

	function callDismiss(alluid, calnm)
	{
		var arr = alluid.split("$nps$");
		var divid=arr[0];
		var uid=arr[1];
		document.getElementById('action_gif').style.display= 'block';
		$.ajax({
	        type: "GET",
	        url: "${pageContext.request.contextPath}/remEventReminder",
	        data: {'uid': uid,'calnm': calnm},
	/*       contentType: "application/json",  */
	        success: function (data) {
	        	document.getElementById('action_gif').style.display= 'none';
	        	if(data=="true")
	        		{
	        		var cnt=parseInt($(".bell_right").text());
	        		cnt=cnt-1;
	        		if(cnt>0)
	        		{
	        		$(".bell_right").text(cnt);
	        		$(".bell_right").show();
	        		}
	        	else
	        		{
	        		$(".bell_right").hide();
	        		}
	        		$("#"+divid).hide();
	        		
	        		}
	        	
	        	
	        	//$(".noti_inner").html(obj.res);
	        	//var cnt=parseInt(obj.count);
	        	
	           
	        }
	    });
	}
	function showcalNoti()
	{

		//$(".noti_inner").html("loading...");
		$.ajax({
	        type: "GET",
	        url: "${pageContext.request.contextPath}/showcalNoti",
	       /*  data: {'uid': uid,'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt ,"texttype": texttype}, */
	      contentType: "application/json", 
	        success: function (data) {
	        	var obj = jQuery.parseJSON(data);
	        	$(".noti_inner").html(obj.res);
	        	var cnt=parseInt(obj.count);
	        	if(cnt>0)
	        		{
	        		$(".bell_right").text(cnt);
	        		$(".bell_right").show();
	        		}
	        	else
	        		{
	        		$(".bell_right").hide();
	        		}
	           
	        }
	    });
	}
	
	function dropEventUpdate(calnm,uid,days,hour,min)
	{
		document.getElementById('action_gif').style.display= 'block';
		$.ajax({
	        type: "GET",
	        url: "${pageContext.request.contextPath}/dropEventUpdate",
	        data: {'uid': uid,'calnm': calnm, 'days': days, 'hour': hour, 'min': min},
	/*       contentType: "application/json",  */
	        success: function (data) {
	        	document.getElementById('action_gif').style.display= 'none';
	       
	        }
	    });
	}
	
	function resizeEventUpdate(calnm,uid,days,hour,min)
	{
		document.getElementById('action_gif').style.display= 'block';
		$.ajax({
	        type: "GET",
	        url: "${pageContext.request.contextPath}/resizeEventUpdate",
	        data: {'uid': uid,'calnm': calnm, 'days': days, 'hour': hour, 'min': min},
	/*       contentType: "application/json",  */
	        success: function (data) {
	        	document.getElementById('action_gif').style.display= 'none';
	        }
	    });
	}
	
	function showUqouta() {
		$.ajax({
	        type: "GET",
	        url: "${pageContext.request.contextPath}/showUserQuota",
	     
	        contentType: "application/json",
	        success: function (data) {
	      
	        $("#uquota").html(data);
	        	
	        }
	    });
	}
	
	function getHeadLogo(imgid) {
		try {
			var pic = document.getElementById(imgid);
			pic.src = "images/logo.png";
		} catch (e) {
			// TODO: handle exception
		}
	}

	function getAltImage(imgid) {
		try {
			var pic = document.getElementById(imgid);
			pic.src = "images/blank_man.jpg";
		} catch (e) {
			// TODO: handle exception
		}
	}

	function getAltChatImage(imgid) {
		try {
			var pic = document.getElementById(imgid);
			pic.src = "chat/photo.jpg";
		} catch (e) {
			// TODO: handle exception
		}
	}

	function getAltImageDisp(imgid) {
		try {
			var pic = document.getElementById(imgid);
			pic.src = "images/unnamed.png";
		} catch (e) {
			// TODO: handle exception
		}
	}
</script>

<script type="text/javascript">
	function changeHBG(nm) {

		//	document.cookie="mycolor="+nm+";";
		document.getElementById('action_gif').style.display = 'block';

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/changeBGColor",
			data : {
				'bgColor' : nm
			},
			contentType : "application/json",
			success : function(data) {
				// alert(data);
				$("#hid_mail_bgColor").val(nm);
				document.getElementById('action_gif').style.display = 'none';
				$(".header").css("background-image", "none");
				$(".header").css("background-color", nm);
				$(".search_button").css("background-image", "none");
				$(".search_button").css("background-color", nm);
				$(".cancel_cal,.save_cal").css("background-color", nm);
				$(".cancel_cal_1, .save_cal_1").css("background-color", nm);
				$(".send_share,.cancel_share").css("background-color", nm);
				$(".chat_out").css("background-color", nm);
				$(".chat_sign_1_box").css("background-color", nm);
				$('.left_tab_content ul li').hover(
						function() {
							$(this)
									.css(
											'border-left',
											"3px solid "
													+ $("#hid_mail_bgColor")
															.val())
						}, function() {
							$(this).css('border-left', "3px solid #fff")
							/* $(".my_con_active").css("border-left","3px solid "+$("#hid_mail_bgColor").val())	 */
						});
				/* $(".search_button").css("background-image","none");
				$(".search_button").css("background-color",nm); */
				/* $(".active_left_tree").css("border-left","3px solid "+nm);
				$('.left_partent_tree').hover(
						 function(){ $(this).css('border-left', "3px solid "+$("#hid_mail_bgColor").val()) },
						 function(){ $(this).css('border-left', "3px solid #fff") 
							$(".active_left_tree").css("border-left","3px solid "+$("#hid_mail_bgColor").val())
						 }
				);
				$(".ui-widget-header").css("background-image","none");
				$(".ui-widget-header").css("background-color",nm); */

			}
		});

	}

	function ssetBG() {
		var bgColor = $("#hid_mail_bgColor").val();
		$("div.meter span").css("background-color", bgColor);
		$(".header").css("background-image", "none");
		$(".header").css("background-color", bgColor);
		$(".search_button").css("background-image", "none");
		$(".search_button").css("background-color", bgColor);
		$(".cancel_cal,.save_cal").css("background-color", bgColor);
		$(".cancel_cal_1, .save_cal_1").css("background-color", bgColor);
		$(".send_share,.cancel_share").css("background-color", bgColor);
		$(".chat_out").css("background-color", bgColor);
		$(".chat_sign_1_box").css("background-color", bgColor);
		$('.left_tab_content ul li').hover(
				function() {
					$(this).css('border-left',
							"3px solid " + $("#hid_mail_bgColor").val())
				}, function() {
					$(this).css('border-left', "3px solid #fff")
					/* $(".my_con_active").css("border-left","3px solid "+$("#hid_mail_bgColor").val())	 */
				});
		document.getElementById('div_progress').style.display = 'none';
	}

	function changeLDAPImg() {
		var oMyForm = new FormData();
		 var upl1 = document.getElementById("upl1")   ;
		var flszMBimg = 0;

		// for(var i=0; i< upl1.files.length ;i++ )
		//	  {
		// alert(upl1.files[0].name)
		oMyForm.append("file0", upl1.files[0]);
		var flsz = upl1.files[0].size;

		var flsz1=flsz/1024;

		//flszMBimg = flszMBimg + (flsz1 / 1024);
		//  alert(flszMBimg);
		// }
		
		 var nm= upl1.files[0].name;
	  		var arr = nm.split(".");
	  
	  if((arr[arr.length-1]!="png" && arr[arr.length-1]!="PNG" && arr[arr.length-1]!="jpg" && arr[arr.length-1]!="JPG" && arr[arr.length-1]!="jpeg"))
		  {
		  var msg="Image type must be png or jpg only.";
		  var error = generate('alert');
			$.noty.setText(error.options.id, msg);
			setTimeout(function() {
				$.noty.close(error.options.id);
			}, 5000);
		  }
	  else if (flsz1> 100) {
			var msg = "Accepted image size is up to 100KB.";
			var error = generate('alert');
			$.noty.setText(error.options.id, msg);
			setTimeout(function() {
				$.noty.close(error.options.id);
			}, 5000);
		} else {

			document.getElementById('action_gif').style.display = 'block';
			$
					.ajax({
						url : '${pageContext.request.contextPath}/uploadImage',
						data : oMyForm,
						dataType : 'text',
						processData : false,
						contentType : false,
						type : 'POST',
						success : function(data) {
							//	alert(data)
							if (data != "false") {
								var mid = $("#hid_logedin_id").val();
								var sml_id = mid + "nomyimage";
								var big_id = "big_" + mid + "nomyimage";
								//$(".new_user").html('<img alt="Embedded Image" onerror="getAltImage(this.id)" id="'+sml_id+'" src="data:image/jpg;base64,'+data+'" />');

								//$("#big_img").html('<img height="96px" width="96px" alt="Embedded Image"  onerror="getAltImage(this.id)" id="'+big_id+'" src="data:image/jpg;base64,'+data+'" /><div onclick="uploadDP()" class="change_images">Change photo</div>');
								//$('#nirbhay@silvereye.innomyimage').attr("src","data:image/jpg;base64,"+data);
								//	$('#big_nirbhay@silvereye.innomyimage').attr("src","data:image/jpg;base64,"+data);
								/* $('#chat_id_bd').attr("src","data:image/jpg;base64,"+data);
								 $('#chat_id').attr("src","data:image/jpg;base64,"+data); */
								document.getElementById(sml_id).src = "data:image/jpg;base64,"
										+ data;
								document.getElementById(big_id).src = "data:image/jpg;base64,"
										+ data;
							}
							document.getElementById('action_gif').style.display = 'none';
						}
					});
		}

	}
	function uploadDP(){	
		
		  //alert('Hi');
		  $('.pop_for_image_upload').show();
		  $('.web_dialog_overlay').show();
		  $('.user_information').removeClass('user_info_top');
		//  $('.user_info_top').hide();
		  $('.user_information').hide();
		  $('.user_information').addClass('removeUser_info');
		  
			}
</script>

</head>
<body onload="ssetBG()">







	<%
		HttpSession head_hs = request.getSession();
		String act_con_fldr = head_hs.getAttribute("active_contact").toString();
		String mailid = head_hs.getAttribute("id").toString();
		String fname = head_hs.getAttribute("fname").toString();
		String bgColor = (String) head_hs.getAttribute("bgColor");
		String path_img = "images/blank_man.jpg";
		String path_img_id = mailid + "nomyimage";
		byte[] jpegBytes1 = (byte[]) head_hs.getAttribute("img_myurl");
		if (jpegBytes1 != null && jpegBytes1.length > 10) {
			path_img = new sun.misc.BASE64Encoder().encode(jpegBytes1);
			path_img = "data:image/jpg;base64," + path_img;
		}
		String dom = "";
		if (mailid != null) {
			String arr[] = mailid.split("@");
			dom = arr[arr.length - 1];
		}
	%>
	<input type="hidden" id="hid_mail_bgColor" value="<%=bgColor%>" />
	<input type="hidden" id="hid_logedin_id" value="<%=mailid%>" />
	<input type="hidden" id="hid_mail_domain" value="<%=dom%>" />
	<input type="hidden" id="hid_cal_file_name" />
	<input type="hidden" id="hid_disp_calnm" />

	<div
		style="display: block; top: 0px; left: 0px; width: 100%; background-color: white; height: 100%; position: absolute; z-index: 9999999;"
		id="div_progress">



		<link
			href="//code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
			rel="stylesheet" />

		<style>
div.meter {
	border: 1px solid #b0b0b0;
	border-radius: 3px;
	box-shadow: 0 3px 5px 0 #d3d0d0 inset;
	height: 22px;
	margin-left: 39%;
	margin-top: 5px;
	position: absolute;
	width: 320px;
	/* viewing purposes */
	/* viewing purposes */
	-webkit-box-shadow: inset 0 3px 5px 0 #d3d0d0;
	-moz-box-shadow: inset 0 3px 5px 0 #d3d0d0;
	box-shadow: inset 0 3px 5px 0 #d3d0d0;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	-ms-border-radius: 3px;
	-o-border-radius: 3px;
}

div.meter span {
	display: block;
	height: 100%;
	animation: grower 19s linear;
	-moz-animation: grower 19s linear;
	-webkit-animation: grower 19s linear;
	-o-animation: grower 19s linear;
	position: relative;
	top: -1px;
	left: -1px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	-ms-border-radius: 3px;
	-o-border-radius: 3px;
	border-radius: 3px;
	-webkit-box-shadow: inset 0px 3px 5px 0px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: inset 0px 3px 5px 0px rgba(0, 0, 0, 0.2);
	box-shadow: inset 0px 3px 5px 0px rgba(0, 0, 0, 0.2);
	border: 1px solid #b5b5b5;
	background: #6eb2d1;
	/*  background-image: -webkit-gradient(linear, 0 0, 100% 100%, color-stop(0.25, rgba(255, 255, 255, 0.2)), color-stop(0.25, transparent), color-stop(0.5, transparent), color-stop(0.5, rgba(255, 255, 255, 0.2)), color-stop(0.75, rgba(255, 255, 255, 0.2)), color-stop(0.75, transparent), to(transparent));
  background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  background-image: -moz-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  background-image: -ms-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  background-image: -o-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  */
	-webkit-background-size: 45px 45px;
	-moz-background-size: 45px 45px;
	-o-background-size: 45px 45px;
	background-size: 45px 45px;
}

div.meter span:before {
	content: '';
	display: block;
	width: 100%;
	height: 50%;
	position: relative;
	top: 50%;
	background: rgba(0, 0, 0, 0.03);
}

div.meter p {
	color: #333;
	font-family: "Helvetica";
	font-size: 15px;
	font-weight: bold;
	line-height: 25px;
	margin: -4px 100px;
	position: absolute;
	text-shadow: 0 1px rgba(255, 255, 255, 0.6);
	top: 0;
}

@
keyframes grower { 0% {
	width: 0%;
}

}
@
-moz-keyframes grower { 0% {
	width: 0%;
}

}
@
-webkit-keyframes grower { 0% {
	width: 0%;
}

}
@
-o-keyframes grower { 0% {
	width: 0%;
}
}
</style>

		<div style="margin-top: 14%; text-align: center;">
			Loading
			<%=mailid%>...
		</div>
		<div class="meter">

			<span style="width: 97%"></span>
			<p></p>
		</div>

	</div>


	<input type='hidden' value='<%=act_con_fldr%>'
		id='hid_active_contact_fldr' />
	<div id="action_gif" class="my_notification"
		style="display: none; left: 46%;">Loading...</div>
	<div id="mail_sending" class="my_notification"
		style="display: none; left: 46%;">Sending...</div>
	<div id="mail_sent" class="my_notification"
		style="display: none; left: 43%;">Your message has been sent.</div>
	<div class="minwidth">
		<!----------/// HEADER STARED HERE -------->
		<div class="header">
			<div class="header_inner">
				<div class="logo">
					<a style="cursor: pointer;" href="calendar"> <img
						class="head_logo" src="logo/<%=dom%>.png"
						onerror="getHeadLogo(this.id)" id="main_logo" />
					</a>
				</div>
				<div class="header_right">
					<!-- <div class="main_search_folder">
						<div class="search_it">
							<input type="text" id="search_auto" onClick="return Hide();"
								placeholder="Search Here...."> <a href="#">
								<div class="down_arrow" onClick="return showHide(event);"></div>
							</a> <a href="#">
								<div class="search_icon"></div>
							</a>


						</div>

					</div> -->
					<div class="new_user" title="Profile" onClick="userinformation(event);">
						<img src="<%=path_img%>" onerror="getAltImage(this.id)"
							id="<%=path_img_id%>" />
					</div>


<div class="user_noti">
			<div class="bell_left" title="Notifications">		<img alt="Notification Image" src="images/notification.png"></div>
              <div class="bell_right">2</div>
				</div>
					<div class="user_noti_content arrow_box_2">
				<div class="noti_title">Reminders</div>
				<div class="noti_inner">
					<!-- <div class="noti_inner ">
					<div class="noti_row">
  <div class="noti_left">Happy birthay to you</div>
  <div class="noti_right">9 hours overdue </div>

</div>
<div class="noti_row">
  <div class="noti_left">Bday</div>
  <div class="noti_right">9 hours</div>

</div></div> --></div>
					</div>


					<!---------/// MORE PRODUCT STARED HERE -->
					<div class="more_product" title="Apps"></div>
					<div class="more_product_content arrow_box_1">
						<!--   -------/// MORE PRODUCT ITEM STARED -- -->
						<ul>

							<li><a href="inbox" target="_blank">
									<div class="mail_ring">
										<span class="mail_prod"><img
											src="images/product_icon/new/mail.png" /></span>
									</div> <span class="pro_info">Mail</span>
							</a></li>
							<li><a href="calendar">
									<div class="calender_ring">
										<span class="calender_prod"><img
											src="images/product_icon/new/calender.png" /></span>
									</div> <span class="pro_info">Calendar</span>
							</a></li>
							<li><a href="contacts" target="_blank">
									<div class="contact_ring">
										<span class="phone_book"><img
											src="images/product_icon/new/phone.png" /></span>
									</div> <span class="pro_info">Contact</span>
							</a></li>
							<li><a href="tasks" target="_blank">
									<div class="tasks_ring">
										<span class="task_pro"><img
											src="images/product_icon/new/tasks.png" /></span>
									</div> <span class="pro_info">Tasks</span>
							</a></li>

							<li><a href="/edms" target="_blank">
									<div class="brief_ring">
										<span class="folder_pro"><img
											src="images/product_icon/new/briefcase.png" /></span>
									</div> <span class="pro_info">Vault</span>
							</a></li>

							<li><a href="http://www.wihg.res.in" target="_blank">
									<div class="web_ring">
										<span class="web_prod"><img
											src="images/product_icon/new/web.png" /></span>
									</div> <span class="pro_info">Web</span>
							</a></li>


						</ul>




					</div>
					<!--   -----// MORE PRODUCT END HERE -------->





				</div>

			</div>

		</div>
		<!----------/// HEADER END HERE ---------->
		<!---------/// MID CONTENT STARED HERE ---->
		<div class="content">
			<div id="search_form" class="search_form_1">


				<div class="form">
					<form action="" method="get">
						<div class="to">
							<div style="padding-top: 6px;"></div>
							<div class="name search_text">Search</div>
							<a><div>
									<select id="parent_fldr" name="parent_fldr"
										style="width: 227px; height: 32px; border: 1px solid #ccc !important; padding-left: 3px;">

										<option value="all">All Calendar</option>
										<%
											List<CreateCalendarRequest> calendarlist = (List<CreateCalendarRequest>) request
													.getAttribute("calendarfilelist");
											List<String> filenames = (List<String>) request.getAttribute("filenames");
											int i = 0;
											for (CreateCalendarRequest cl : calendarlist) {
										%>
										<option value="<%=filenames.get(i)%>"><%=cl.getCalID()%></option>
										<%
											i++;
											}
											List<CreateCalendarRequest> sharedcalendarlist = (List<CreateCalendarRequest>) request
													.getAttribute("sharedcalendarfilelist");
											List<String> sharedfilenames = (List<String>) request.getAttribute("sharedfilenames");
											i = 0;
											for (CreateCalendarRequest cl : sharedcalendarlist) {
										%>
										<option value="<%=sharedfilenames.get(i)%>"><%=cl.getCalID()%>
											(
											<%=cl.getUserid()%> )
										</option>
										<%
											i++;
											}
										%>
									</select>
								</div></a>

							<div class="clear"></div>


							<div style="padding-top: 13px;"></div>


							<input type="text" placeholder="What" name="to_search" value=""
								class="search_clear border input" id="to_search"> <input
								type="text" placeholder="Who" name="from_search" value=""
								class="search_clear border input" id="from_search"> <input
								type="text" name="sub_search" placeholder="Where" value=""
								class="search_clear border input" id="sub_search"> <input
								type="text" name="con_search" placeholder="Date" value=""
								style="height: 28px;" class="search_clear  popupDatepicker"
								id="cal_search_dt1"> <span class="serch_to">To</span><input
								type="text" name="con_search" placeholder="Date" value=""
								style="height: 28px;" class="search_clear   popupDatepicker"
								id="cal_search_dt2">
							<!-- 	<div style="padding-top: 50px;"></div>
							<input type="date" name="date_search" placeholder="Date" value=""
								class="search_clear border input" id="date_search"> -->

							<div class="clear"></div>
							<!-- <div class="check check_upper">
                                    <input name="" type="checkbox" value="">
                                    <span>Don't include chats</span></div> -->
						</div>
						<div class="search_button cal_search_button">
							<a style="cursor: pointer;" title="Search"> <span
								class="search_icon2"> </span></a>
						</div>
					</form>


				</div>
			</div>




			<!-----------/// USER INFORMATION BOX STARED HERE --------->
			<div class="user_information arrow_box">

				<!----------/// TOP SECTION STARED HERE ------------>
				<div class="user_top">
					<a id="big_img" style="cursor: pointer;"> <img height='96px'
						width='96px' src="<%=path_img%>" onerror="getAltImage(this.id)"
						id="big_<%=path_img_id%>" />
						<div onclick="uploadDP()" class="change_images">Change photo</div>
					</a>

					<div class="left_top">
						<%=fname%>
						<span><%=mailid%></span>
						<div class="clear_2"></div>
						<div class="progress_bar">
						<div id="uquota">
							<%
								int per = 0;
								String per_name = "bar5.png";

								String qper = (String) request.getAttribute("QuotaPer");
								if (qper != null && !(qper.equals(""))) {
									int pval = Integer.parseInt(qper.trim());
									per = pval;
									if (pval <= 5) {
										per_name = "bar5.png";
									} else if (pval <= 10) {
										per_name = "bar10.png";
									} else if (pval <= 20) {
										per_name = "bar20.png";
									} else if (pval <= 30) {
										per_name = "bar30.png";
									} else if (pval <= 40) {
										per_name = "bar40.png";
									} else if (pval <= 50) {
										per_name = "bar50.png";
									} else if (pval <= 60) {
										per_name = "bar60.png";
									} else if (pval <= 70) {
										per_name = "bar70.png";
									} else if (pval <= 80) {
										per_name = "bar80.png";
									} else if (pval <= 85) {
										per_name = "bar85.png";
									} else if (pval <= 90) {
										per_name = "bar90.png";
									} else if (pval <= 100) {
										per_name = "bar100.png";
									}

								}
							%>
							<img src="images/<%=per_name%>" />
							<div class="percentage_value">

								<%=per%>%
							</div>
							</div>
							<div class="clear"></div>
						</div>
						<div style="width: 170px;">
							<!-- <img src="images/color.png" /> -->
							<table>
								<tr>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#DC4FAD"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg1.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#AC193D"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg2.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#D24726"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg3.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#FF8F32"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg4.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#82BA00"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg5.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#03B3B2"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg6.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#326a3e"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg7.png" />
									</a></td>
									<td width="20px"><a style="cursor: pointer;"
										name="#8C0095" onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg8.png" />
									</a></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<!------------/// TOP SECTION END HERE -------------->
				<!----------/// BOTTOM SECTION STARED HERE ------------>
				<div class="user_bottom">

					<a href="logout">
						<div class="sing_out">Sign out</div>
					</a> <a href="#">
						<div class="sing_out right_space">
							<%
								//String QutPer=  request.getAttribute("QuotaPer").toString();
							%>
							Profile

						</div>
					</a>




				</div>

				<!------------/// BOTTOM SECTION END HERE -------------->





			</div>
</body>
</html>