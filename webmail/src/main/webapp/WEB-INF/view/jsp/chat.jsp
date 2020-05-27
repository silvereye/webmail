
<%@page import="org.jivesoftware.smackx.vcardtemp.VCardManager"%>
<%@page import="org.jivesoftware.smack.XMPPException.XMPPErrorException"%>
<%@page
	import="org.jivesoftware.smack.SmackException.NotConnectedException"%>
<%@page
	import="org.jivesoftware.smack.SmackException.NoResponseException"%>
<%@page import="org.jivesoftware.smackx.pubsub.PresenceState"%>
<%@page
	import="org.jivesoftware.smackx.vcardtemp.provider.VCardProvider"%>
<%@page import="org.jivesoftware.smack.provider.ProviderManager"%>
<%@page import="org.jivesoftware.smackx.vcardtemp.packet.VCard"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.jivesoftware.smack.packet.RosterPacket"%>
<%@page import="org.jivesoftware.smack.RosterListener"%>
<%@page import="org.jivesoftware.smack.Roster"%>
<%@page import="org.jivesoftware.smack.packet.Presence"%>
<%@page import="org.jivesoftware.smack.RosterEntry"%>
<%@page import="org.jivesoftware.smack.XMPPConnection"%>
<%@page import="org.jivesoftware.smack.tcp.XMPPTCPConnection"%>
<%@page import="java.util.Collection"%>


  <%
              String cssdisp="";
              HttpSession cleft_hs=request.getSession();
          	String cchatService=(String)cleft_hs.getAttribute("chatService");
          	boolean jsst=true;
          	if(!cchatService.equalsIgnoreCase("true"))
          	{
          		cssdisp="display: none;";
          		jsst=false;
          	}
              %>

<div class="chat_box" id="b" style="<%=cssdisp %>">

	<!---------/// MAIN CHAT BOX STARED HERE ------------->
<!--  <h1 id="hh">sdf</h1> -->

	<div id="chatBody" class="chat_box_inner">
		
		<div style="width: 100%; color: #ccc; text-align: center;">Loading...</div>
		
		</div>
	<!---------------//// CHAT SINGH IN ----------->
	<div class="chat_sign_box">
		<div class="chat_sign_images">
			<img src="images/chat-4-xxl.png" />
		</div>
		<div class="chat_sign_1_box">Sign in</div>
	</div>
	<!-----------------//// CHAT SINGH IN END ------->

</div>

</div>
</div>

<!--------/// LEFT MID CONTENT END HERE -------------->

<!-------/// LEFT BOTTOM ICON STARED HERE --->
<!--  <div id="mailview-bottom2" class="uibox bottom_mail">
                            <ul class="background_bottom bootom_icon_ul">
                                <li><a href="#"><img src="images/bootom_icon_1.png" class="icon1"></a></li>
                                <li><a href="#"><img src="images/bootom_icon_2.png" class="icon_2"></a></li>
                                <li><a href="#"><img src="images/bootom_icon_3.png" class="icon_3"></a></li>
                                <li><a href="#"><img src="images/bootom_icon_4.png" class="icon_4"></a></li>
                                <li><a href="#"><img src="images/bootom_icon_5.png"></a></li>
                                <li><a href="#"><img src="images/bootom_icon_6.png"></a></li>
                                <div class="clear"></div>
                            </ul>
                            <div class="clear"></div>
                        </div> -->

<!-------/// LEFT BOTTOM ICON END HERE --->
</div>
<!--------------///// WHEN LEFT IS OPEN End HERE ----->


<!-----------/// LEFT PANNEL END HERE ------->

<!-------/// LEFT PANNEL WHEN IT CLOSE ------>
<!-- <div class="left_close">
                    <div class="top_left"> <a href="#">
                            <div class="top_right_icon" id="show_left"> <img src="images/next_mail.png" /> </div>
                        </a> </div>
                    <div class="mid_close_content">
                        <ul>
                            <li> <a href="#"><img src="images/inbox_blue.png" /></a> </li>
                            <li> <a href="#"><img src="images/sent.png" /></a> </li>
                            <li> <a href="#"><img src="images/delet.png" /></a> </li>
                            <li> <a href="#"><img src="images/all_folder.png" /></a> </li>
                        </ul>
                    </div>
                    <div id="mailview-bottom1" class="uibox close_bottom">
                        <ul>
                            <li><a href="#"><img src="images/photo.jpg" /></a></li>

                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <div class="clear"></div>
                        </ul>
                        <div class="clear"></div>
                    </div>
                </div> -->



<script type="text/javascript">

function getChatBody() {
	
	var requestPage="${pageContext.request.contextPath}/openChatBody";
	jQuery.get(requestPage,
          {
            	cache: true 
          },
                    function( data ) {
            	
             $( "#chatBody" ).html( data );
             $(".chat_sign_1_box").css("background-color",$("#hid_mail_bgColor").val());
             
            }
	);
	
}

function chatSignin() {
	document.getElementById('action_gif').style.display= 'block';
	var requestPage="${pageContext.request.contextPath}/chatSignin";
	jQuery.get(requestPage,
          {
            	cache: true 
          },
                    function( data ) {
            	
             $( "#chatBody" ).html( data );
             $(".chat_sign_1_box").css("background-color",$("#hid_mail_bgColor").val());
     		document.getElementById('action_gif').style.display= 'none';
            }
	);
}

//CHAT SIGH OUT BOX 
$(document.body).on('click', '.chat_out' ,function(){ 

	if($('.chat_box_inner').css('display')=='block'){
		/*$('.chat_sign_box').css('display','block');*/
		//$('.chat_box_inner').css('display','none');
		$('.chat_search_11').css('display','none')
		document.getElementById('action_gif').style.display= 'block';
		var requestPage="${pageContext.request.contextPath}/chatout";
		jQuery.get(requestPage,
	         
	                    function( data ) {
	            	
	             $( "#chatBody" ).html( data );
	             document.getElementById('action_gif').style.display= 'none';
	             $(".chat_sign_1_box").css("background-color",$("#hid_mail_bgColor").val());
	            }
		);
		
		}
	
	});

	function getAltChatImage(imgid) {
		var pic = document.getElementById(imgid);
		pic.src = "chat/photo.jpg"
	}
</script>

  <script type="text/javascript">
	$('#contact_cnt_div_full table tbody tr')

	$(document.body).on(
			'click',
			'.cheat_row',
			function() {

				var username = $(this).attr('id');
				var frndUid= $(this).attr('name');
				// alert(getIdChat);
				//var getImagePath = $(this).children('.small_images').children('.online_green').attr('src');
				var getImagePath = $(this).children(".small_images").children("div").children("img").attr('src');
				//alert(getImagePath);
				//});

				//function getChatBox(username) {
				var box = null;
				
				var chatname = username.split("name");
				
				var userchatid = username.split("@");
				
				/* var imagesrc = document.getElementById(chatname[0] + "presence").src;
				var imagesp = imagesrc.split("images/"); */
				var image = getImagePath; //imagesp[1];

				//alert(getImagePath)
				var id = "#" + userchatid[0] + "open_chat_box";
				var frnduid=userchatid[0];
				//alert(chatname[0]);

				//var title_name_apppend = $('.title_name').html();
				//alert(title_name_apppend);

				// TEST1

				// TEST1

				var get_length = id.length;

				var Toopen = false;
				try {
					$(".title_name").each(function(index, element) {

						if ($(this).text() == chatname[0]) {

							//return false;
							Toopen = true;

						} else {

							//alert('This is Not match');
							//msgElement;

						}
					});
				} catch (err) {
				}

				if (!Toopen) {
					
					
					/* 		var requestPage="${pageContext.request.contextPath}/getChatHistory";
							$.get(requestPage,
						                        {
						                    'chatid' : chatname[0]
						            }, 
						               function( data ) {
						            	 */
						            	
							
							
							
							$("#appendchatdiv").append(
									"<div id='"+userchatid[0]+"open_chat_box'></div>");
						
							id=id.replace('.','\\.');
							/* if(data!="")
								{
								data=setEmoji(data);
								$(id).append("<div id='"+userchatid[0]+"chat_div'>"+data+"</div>");
								}
							else
								{ */
								$(id).append("<div id='"+userchatid[0]+"chat_div'></div>");
							//	}
							var chatdivid = "#" + userchatid[0] + "chat_div";
							chatdivid=chatdivid.replace('.','\\.');
							//chatdivid='#nirbhay\\.singhchat_div';
							try
							{
							box = $(chatdivid).chatbox(
									{

										/*
											unique id for chat box
										 */
										id : "me",
										user : {
											key : "value"
										},
										/*
											Title for the chat box
										 */
										title :chatname[0],
										imagenm : image,
										/*
											messageSend as name suggest,
											this will called when message sent.
										 */
										messageSent : function(id, user, msg) {
											$(chatdivid)
													.chatbox("option", "boxManager")
													.addMsg(id, msg, chatdivid);
										}

									});

						}
						
						catch (e) {
						//	alert('hii');
						//	alert(e);
							// TODO: handle exception
						}
						
						
						
						frnduid=frnduid.replace('.','\\.');
						frnduid=frnduid+"chatboxcreated";
						$("#"+frnduid).children('.new_header_top').children('span').children('.hid_frnd_uid').html(frndUid);
						$(".ui-widget-header").css("background-image", "none");
						$(".ui-widget-header").css("background",
								$("#hid_mail_bgColor").val());
						$(".ui-widget-header").css("border",
								"1px solid " + $("#hid_mail_bgColor").val());
						           // }
						//);
						var requestPage="${pageContext.request.contextPath}/getChatHistory";
						$.get(requestPage,
					                        {
					                    'chatid' : chatname[0]
					            }, 
					               function( data ) {
					            	 if(data!="")
					            		 {
					            		data=setEmoji(data);
					            		 $("#"+userchatid[0]+"chat_div").prepend(data);
					            		 $("#"+userchatid[0]+"chat_div").parents().find("textarea").focus();
					            		 }
					            });
				}
				else
					{
				frnduid=frnduid.replace('.','\\.');
				frnduid=frnduid+"chatboxcreated";
				$("#"+frnduid).children('.new_header_top').children('span').children('.hid_frnd_uid').html(frndUid);
				$(".ui-widget-header").css("background-image", "none");
				$(".ui-widget-header").css("background",
						$("#hid_mail_bgColor").val());
				$(".ui-widget-header").css("border",
						"1px solid " + $("#hid_mail_bgColor").val());
				//	}
				}
			});
</script>  
<!-------------------/// FOR TAB ONLY --------------------->
<style>

/*---- NEW CSS FOR CHAT STARED HERE ---------------*/
.main_chat_box {
	max-height: 316px;
	position: absolute;
	overflow: hidden;
	bottom: 0px;
	right: 18px;
	z-index: 1;
}

#head {
	position: relative;
}

.overflow_chat {
	/*background: #000;*/
	float: left;
	position: absolute;
	left: 0px;
	z-index: 0;
	bottom: 0px;
}

.inner_chat_box { /*background:#f00;*/
	padding: 10px;
	float: right;
	z-index: 3;
	position: relative;
	/* padding-left: 200px; */
	bottom: 0px;
	/* position: absolute; */
	overflow: hidden;
	height: 300px;
	padding-top: 20px;
	right: 0px;
}

.ui-chatbox {
	margin-left: 18px;
}

.ui-widget:last-child {
	margin-left: 0px;
}

.main_inner_box {
	position: relative;
	max-height: 316px;
}

.overflow_info {
	height: 252px;
	width: 190px;
	/*background: #f00;*/
	position: relative;
	z-index: 0;
}

.overflow_info_content {
	height: 221px;
	width: 100%;
	display: none;
	background: #fff;
	border: 1px solid #ccc;
}

.overflow_info_bottom {
	height: 28px;
	background: #DFDFDF;
	cursor: pointer;
	position: absolute;
	bottom: 0px;
	right: -2px;
	display: none;
	width: 64px;
	border: 1px solid #ccc;
}

.count_overflow {
	width: 26px;
	float: right;
	text-align: center;
	margin-top: 10px;
}

.overflow_close {
	background: url(images/close.png);
	height: 11px;
	width: 11px;
	cursor: pointer;
	float: right;
	margin-right: 15px;
}

.small_row {
	padding-left: 6px;
	padding-top: 5px;
	height: 16px;
}

.overflow_info_bottom>img {
	width: 23px;
	/* display: block; */
	margin: 0 auto;
	margin-top: 7px;
	margin-left: 11px;
}

.small_row>span {
	float: left;
}

.minimiz {
	float: right;
	position: relative;
	top: 264px;
}
/*------- NEW CSS FOR CHAT END HERE --------------*/
</style>

 <script type='text/javascript' src='dwr/engine.js'></script>
  <script type='text/javascript' src='dwr/interface/ReverseClass.js'></script>
  <script type='text/javascript' src='dwr/util.js'></script>

<script>
/* function reverseAjx(){
	dwr.engine.setActiveReverseAjax(true);
}*/

window.onload=dwr.engine.setActiveReverseAjax(true);  
function calltest(){
	console.log("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
}

</script>
<script type="text/javascript">
	function friendRequest(from) {
		var acceptfrom = from.split("acceptbtn");
		$.ajax({
			url : "${pageContext.request.contextPath}/respondFrndReq",
			data : {
				fromJID : acceptfrom[0],
			},
			success : function(data) {
				showmsg("success",data);
			}
		});
	}
</script>

<script type="text/javascript">
	function friendDeny(from) {
		var acceptfrom = from.split("denybtn");
		$.ajax({
			url : "${pageContext.request.contextPath}/denyFrndReq",
			data : {
				fromJID : acceptfrom[0],
			},
			success : function(data) {
				showmsg("success",data);
			}
		});
	}
</script>
