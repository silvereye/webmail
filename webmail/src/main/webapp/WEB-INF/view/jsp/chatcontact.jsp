<%@page import="org.jivesoftware.smack.packet.RosterPacket"%>
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
<%@page import="org.jivesoftware.smack.RosterListener"%>
<%@page import="org.jivesoftware.smack.Roster"%>
<%@page import="org.jivesoftware.smack.packet.Presence"%>
<%@page import="org.jivesoftware.smack.RosterEntry"%>
<%@page import="org.jivesoftware.smack.XMPPConnection"%>
<%@page import="org.jivesoftware.smack.tcp.XMPPTCPConnection"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ReverseClass.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<!--jQuery and jQuery UI with jQuery Chat-->

<link type="text/css" href="css/jquery.ui.chatbox.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.ui.chatbox.js"></script>

<script type="text/javascript">
	function getChatBox(username) {
		var box = null;
		var chatname = username.split("name");
		var userchatid = username.split("@");

		var imagesrc = document.getElementById(chatname[0] + "presence").src;
		var imagesp = imagesrc.split("images/");
		var image = imagesp[1];

		var id = "#" + userchatid[0] + "open_chat_box";
		$("#appendchatdiv").append(
				"<div id='"+userchatid[0]+"open_chat_box'></div>");

		$(id).append("<div id='"+userchatid[0]+"chat_div'></div>");
		var chatdivid = "#" + userchatid[0] + "chat_div";

		box = $(chatdivid).chatbox({
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
			title : chatname[0],
			imagenm : image,
			/*
				messageSend as name suggest,
				this will called when message sent.
			 */
			messageSent : function(id, user, msg) {
				$(chatdivid).chatbox("option", "boxManager").addMsg(id, msg);
			}
		});
	}
</script>

<!-------------------/// FOR TAB ONLY --------------------->
<style>

/*---- NEW CSS FOR CHAT STARED HERE ---------------*/
.main_chat_box {
	height: 316px;
	position: absolute;
	overflow: hidden;
	bottom: 0px;
	right: 0px;
	z-index: 9;
}

#head {
	position: relative;
}

.overflow_chat {
	/*background: #000;*/
	float: left;
	position: absolute;
	left: 0px;
	z-index: 5;
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
	height: 316px;
}

.overflow_info {
	height: 252px;
	width: 190px;
	/*background: #f00;*/
	position: relative;
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

<script language="javascript">
	function onloadmethod() {
		dwr.engine.setActiveReverseAjax(true);
		document.getElementById("onlineradio").checked = true;
	}
</script>

<script type="text/javascript">
	function updateChatBox(msglist) {
		var id = "#" + msglist[0] + "chat_div";
		var boxid = msglist[0] + "chatboxcreated";
		var elementExists = document.getElementById(boxid);
		var isvis = $("#" + boxid).is(":visible");
		if (elementExists == null) {
			document.getElementById(msglist[2] + "name").click();
		} else if (!isvis) {
			document.getElementById(boxid).style.display = "block";
		}
		$(id).append(msglist[1]);
		var divid = msglist[0] + "chat_div";
		var divv = document.getElementById(divid);
		if (divv.scrollHeight > divv.clientHeight) {
			divv.scrollTop = divv.scrollHeight;
		}
	}
</script>

<script type="text/javascript">
	function removeLastAppended(anyid) {
		var id = "#" + anyid;
		$(id).remove();
	}
</script>

<script type="text/javascript">
	function sendBuddyInvite() {
		var buddyId = document.getElementById("buddyInvite").value;
		$.ajax({
			url : "${pageContext.request.contextPath}/inviteBuddy",
			data : {
				buddyJID : buddyId,
			},
			success : function(data) {
				$("#buddyInvite").val("");
				alert(data);
			}
		});
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
				alert(data);
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
				alert(data);
			}
		});
	}
</script>

<script type="text/javascript">
	function createChatRow(addDivs) {
		document.getElementById("test").innerHTML = addDivs;
	}
</script>

<script type="text/javascript">
	function closeConnection() {
		$
				.ajax({
					url : "${pageContext.request.contextPath}/logoutChat",
					success : function(data) {
					}
				});

	}
</script>

<script type="text/javascript">
	function reconnectConnection() {
		$
				.ajax({
					url : "${pageContext.request.contextPath}/reconnectChat",
					success : function(data) {
					}
				});

	}
</script>

<script type="text/javascript">
	function changePresence(pres) {
		//var pres = document.getElementById("statusChangeSelect").value;
		$.ajax({
			url : "${pageContext.request.contextPath}/changedPresence",
			data : {
				presmode : pres,
			},
			success : function(data) {
			}
		});
	}
</script>

<script type="text/javascript">
	function getAltImage(imgid) {
		var pic = document.getElementById(imgid);
		pic.src = "images/blank_man.jpg"
	}
</script>
</head>
<body >
	<%
		HttpSession hsession = request.getSession();
		XMPPConnection connection = (XMPPTCPConnection) hsession
				.getAttribute("xmppConnection");
		String loggedUser = connection.getUser().split("/")[0];
		String url = (String) request.getAttribute("imageurl");
		String imgsrc = url + loggedUser + ".jpg";
	%>
	<div class="chat_box">
          <div class="chat_box_inner">
            <div class="chat_heading">
              <div class="chat_h_left"> 
              <img src="images/photo.jpg" class="h_name" />
                <div class="chat_h_name">Hariom Srivastava</div>
                <div class="h_drop"><img src="images/down_mail.png" /></div>
              </div>
              <div class="chat_h_right"> <img src="images/search_1.jpg" /> </div>
            </div>
            <div class="chat_inner_content"> 
              <!---------------/// FIRST ROW STARTED HERE --------->
              <div class="cheat_row">
                <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                <div class="contact_information">
                  <p><strong>Jonathan Smith</strong><br>
                    Work for fun</p>
                </div>
                <div class="online_file"> <img src="images/online_file.png"> </div>
              </div>
              <!-------------/// Chat Info Box Stared here --------> 
              
              <!------------//// Chat Info Box End Here -----------> 
              <!--------------/// FIRST ROW END HERE ---------------> 
              
              <!---------------/// FIRST ROW STARTED HERE --------->
              <div class="cheat_row">
                <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                <div class="contact_information">
                  <p><strong>Jonathan Smith</strong><br>
                    Work for fun</p>
                </div>
                <div class="online_file"> <img src="images/online_file.png"> </div>
              </div>
              <!--------------/// FIRST ROW END HERE ---------------> 
              <!---------------/// FIRST ROW STARTED HERE --------->
              <div class="cheat_row">
                <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                <div class="contact_information">
                  <p><strong>Jonathan Smith</strong><br>
                    Work for fun</p>
                </div>
                <div class="online_file"> <img src="images/online_file.png"> </div>
              </div>
              <!--------------/// FIRST ROW END HERE ---------------> 
              <!---------------/// FIRST ROW STARTED HERE --------->
              <div class="cheat_row">
                <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                <div class="contact_information">
                  <p><strong>Jonathan Smith</strong><br>
                    Work for fun</p>
                </div>
                <div class="online_file"> <img src="images/online_file.png"> </div>
              </div>
              <!--------------/// FIRST ROW END HERE ---------------> 
              <!---------------/// FIRST ROW STARTED HERE --------->
              <div class="cheat_row">
                <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                <div class="contact_information">
                  <p><strong>Jonathan Smith</strong><br>
                    Work for fun</p>
                </div>
                <div class="online_file"> <img src="images/online_file.png"> </div>
              </div>
              <!--------------/// FIRST ROW END HERE ---------------> 
              <!---------------/// FIRST ROW STARTED HERE --------->
              <div class="cheat_row">
                <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                <div class="contact_information">
                  <p><strong>Jonathan Smith</strong><br>
                    Work for fun</p>
                </div>
                <div class="online_file"> <img src="images/online_file.png"> </div>
              </div>
              <!--------------/// FIRST ROW END HERE ---------------> 
              <!---------------/// FIRST ROW STARTED HERE --------->
              <div class="cheat_row">
                <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                <div class="contact_information">
                  <p><strong>Jonathan Smith</strong><br>
                    Work for fun</p>
                </div>
                <div class="online_file"> <img src="images/online_file.png"> </div>
              </div>
              <!--------------/// FIRST ROW END HERE ---------------> 
              <!---------------/// FIRST ROW STARTED HERE --------->
              <div class="cheat_row">
                <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                <div class="contact_information">
                  <p><strong>Jonathan Smith</strong><br>
                    Work for fun</p>
                </div>
                <div class="online_file"> <img src="images/online_file.png"> </div>
              </div>
              <!--------------/// FIRST ROW END HERE ---------------> 
              
            </div>
          </div>
          <!---------------//// CHAT SINGH IN ----------->
          <div class="chat_sign_box">
            <div class="chat_sign_images"> <img src="images/chat-4-xxl.png" /> </div>
            <div class="chat_sign_1_box">Sign in</div>
          </div>
          <!-----------------//// CHAT SINGH IN END ------->
          
          <div class="all_chat_option">
            <div class="chat_info arrow-left">
              <div class="chat_info_left">
                <p class="name">Hariom Srivastava</p>
                <span>hari@silvereye.co</span>
                <p class="com">Opportunities don't happen,you create them..</p>
              </div>
              <div class="chat_info_right"> <img src="images/photo_1.jpg" /> </div>
              <div class="clear"></div>
                <div class="bottom_option"> 
                <!-------/// Bottom _Left_part--->
                <div class="left_bottom">
                  <ul>
                    <li><a href="#">Contact info</a></li>
                    <li><a href="#">Emails</a></li>
                  </ul>
                </div>
                <!----------/// Bottom Left Part End ---> 
                <!--------/// Bottom Right part Stared Here ------>
                <div class="right_bottom">
                  <ul>
                    <li class="chat_mail"><a href="#"></a></li>
                    <li class="contact_mail"><a href="#"></a></li>
                    <li><a href="#"></a></li>
                  </ul>
                </div>
                <!---------------/// Bottom  Right Part End Here --------> 
                
              </div>
            </div>
            
            <!-----------------//// Chat Search option Here --------->
             <div class="chat_info_1 arrow-left">
              <div class="chat_info_left">
                <p class="name">Hariom Srivastava</p>
                <span>hari@silvereye.co</span>
                <p class="com">Opportunities don't happen,you create them..</p>
              </div>
              <div class="chat_info_right"> <img src="images/photo_1.jpg" /> </div>
              <div class="clear"></div>
              <div class="bottom_option"> 
                <!-------/// Bottom _Left_part--->
                <div class="left_bottom">
                  <ul>
                    <li><a href="#">Add to contacts</a></li>
                    <li><a href="#">Emails</a></li>
                  </ul>
                </div>
                <!----------/// Bottom Left Part End ---> 
                <!--------/// Bottom Right part Stared Here ------>
                <div class="right_bottom">
                  <ul>
                    <li class="chat_mail"><a href="#"></a></li>
                    <li class="contact_mail"><a href="#"></a></li>
                    <li><a href="#"></a></li>
                  </ul>
                </div>
                <!---------------/// Bottom  Right Part End Here --------> 
                
              </div>
            </div>
            <!---------------/// Chat Search End Here -----------> 
            <!-------/// Chat Search option--------->
            <div class="chat_search">
              <div class="heading_caht">
                <input type="text" placeholder="Search for people" />
              </div>
              <!-----//// Chat Search Content ----->
              <div class="chat_search_content"> 
                
                <!------------//// FIRST ROW --------->
                   <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                        <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                  <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                   <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                   <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                  <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                   <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                  <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                  <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                   <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                   <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                    <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                  <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                    <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                        <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                 <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                   <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                     <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                    <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                <!------------//// FIRST ROW --------->
                       <div class="cheat_row_11">
                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                  <div class="contact_information">
                    <p><strong>Jonathan Smith</strong><br>
                      Work for fun</p>
                  </div>
                  <div class="online_file"> <img src="images/online_file.png"> </div>
                </div>
                <!-----------/// FIRST ROW --------------> 
                
              </div>
              <!--------/// Chat Search End --------> 
              
            </div>
            <!------------/// Chat Search Option End ------> 
            <!-------/// Chat Downarrow option--------->
            <div class="chat_search_11"> 
              
              <!-----//// Chat Search Content ----->
              <div class="chat_downarrow"> 
                <!--------------//// Chat Down Main Page ------------->
                <div class="chat_down_main">
                  <div class="chat_down_top">
                    <div class="chat_down_left"> <img src="images/photo.jpg" /> </div>
                    <div class="chat_down_right"> Hariom Srivastava <span>hari@silvereye.co</span> </div>
                    <div class="clear"></div>
                  </div>
                  <div class="clear"></div>
                       <ul>
                    <li class="chat_status_menu"><a href="#" >Chat&nbsp;Status </a></li>
                    <li class="invites_menu"><a href="#">Invites</a></li>
                    <li class="blocked_menu"><a href="#">Blocked&nbsp;People </a></li>
                    <li class="share_your_menu"><a href="#"> Share&nbsp;your&nbsp;status </a></li>
                  </ul>
                  <div class="clear"></div>
                     <div class="chat_out">Sign out of Chat</div>
                </div>
                <!------------------/// Chat Down Menu End -------------> 
                
              </div>
              <!--------/// Chat Search End --------> 
              <!------------/// Chat Sub menu ----------->
              <div class="chat_down_submenu"> 
                <!-------------// Chat Status box Stared here----------->
                <div class="chat_status"> 
                  <!--------///Chat Haeding ---->
                        <div class="chat_subheading">
                    <div class="chat_main_menu"><img src="images/portlet-remove-icon.png"/></div>
                    <p>Chat Status </p>
                    <div class="clear"></div>
                  </div>
                  <!-----------/// Chat Heading End Here -----> 
                  <!----------/// Chat Status Content ------->
                  <div class="chat_status_content">
                    <ul>
                      <li>
                        <input type="radio" />
                        <span>Online</span>
                        <div class="online"></div>
                      </li>
                      <li>
                        <input type="radio" />
                        <span>Offline</span>
                        <div class="offline"></div>
                      </li>
                      <li>
                        <input type="radio" />
                        <span>Busy</span>
                        <div class="busy"></div>
                      </li>
                      <li>
                        <input type="radio" />
                        <span>Away</span>
                        <div class="away"></div>
                      </li>
                    </ul>
                  </div>
                  <!-----------/// Chat Status  End Here -----> 
                </div>
                <!-------------/// Chat Status Box End Here---------> 
                <!-------------// Invites box Stared here----------->
                <div class="Blocked_status"> 
                  <!--------///Chat Haeding ---->
                  <div class="chat_subheading">
                    <div class="chat_main_menu"><img src="images/portlet-remove-icon.png"/></div>
                    <p>Invites </p>
                    <div class="clear"></div>
                  </div>
                  <!-----------/// Chat Heading End Here -----> 
                  <!----------/// Chat Status Content ------->
                  <div class="chat_status_content"> 
                    <!----------/// Main ROW Content ---------->
                    <div class="chat_row_content"> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                    </div>
                    <!----------/// Main Row Content End Here ---------->
                    <div class="clear"></div>
                  </div>
                  <!-----------/// Chat Status  End Here ----->
                  <div class="clear"></div>
                </div>
                <!-------------/// Invites Box End Here---------> 
                <!-------------// Blocked People  box Stared here----------->
                <div class="Invites_status"> 
                  <!--------///Chat Haeding ---->
                    <div class="chat_subheading">
                    <div class="chat_main_menu"><img src="images/portlet-remove-icon.png"/></div>
                    <p>Blocked People</p>
                    <div class="clear"></div>
                  </div>
                  <!-----------/// Chat Heading End Here -----> 
                  <!----------/// Chat Status Content ------->
                  <div class="chat_status_content"> 
                    <!----------/// Main ROW Content ---------->
                    <div class="chat_row_content"> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                    </div>
                    <!----------/// Main Row Content End Here ----------> 
                  </div>
                  <!-----------/// Chat Status  End Here -----> 
                </div>
                <!-------------/// Blocked People  Box End Here---------> 
                <!-------------// Blocked People  box Stared here----------->
                <div class="Share_status"> 
                  <!--------///Chat Haeding ---->
                  <div class="chat_subheading">
                    <div class="chat_main_menu"><img src="images/portlet-remove-icon.png"/></div>
                    <p> Share your status </p>
                    <div class="clear"></div>
                  </div>
                  <!-----------/// Chat Heading End Here -----> 
                  <!----------/// Chat Status Content ------->
                  <div class="chat_status_content"> 
                    
                    <!----------/// Chat ---------> 
                    <!----------/// Main ROW Content ---------->
                    <div class="chat_row_content"> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                      <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                      <div class="invite_row"> 
                        <!---------------/// INVITE LEFT PART -------->
                        <div class="invite_left">Rohit Tiwari</div>
                        
                        <!--------------/// INVITE LEFT END HERE --------> 
                        <!---------------/// INVITE RIGHT PART -------->
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
                        <!--------------/// INVITE RIGHT END HERE -------->
                        <div class="clear"></div>
                      </div>
                      <!-------------/// INVITE Row END HERE --------------> 
                    </div>
                    <!----------/// Main Row Content End Here ----------> 
                    <!----------/// Chat End --------> 
                    
                  </div>
                  <!-----------/// Chat Status  End Here -----> 
                </div>
                <!-------------/// Blocked People  Box End Here---------> 
              </div>
              <!---------------//// Chat Down Sub Menu ----------> 
              
            </div>
            <!------------/// Chat Downarrow Option End ------> 
          </div>
        </div>
      </div>
      
      <!--------/// LEFT MID CONTENT END HERE --------------> 
      
      <!-------/// LEFT BOTTOM ICON STARED HERE --->
      <div id="mailview-bottom2" class="uibox bottom_mail">
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
      </div>
      
      <!-------/// LEFT BOTTOM ICON END HERE ---> 
    </div>
    <!--------------///// WHEN LEFT IS OPEN End HERE -----> 
    
  </div>
  <!-----------/// LEFT PANNEL END HERE -------> 
  
  <!-------/// LEFT PANNEL WHEN IT CLOSE ------>
  <div class="left_close">
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
  </div>
  <!-------/// LEFT PANNEL WHEN IT CLOSE END ------> 
</body>
</html>