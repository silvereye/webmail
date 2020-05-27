<%@page import="webmail.wsdl.VCFLdapDirAtt"%>
<%@page import="java.util.Collections"%>
<%@page import="webmail.bean.NPCompare"%>
<%@page import="java.util.List"%>
<%@page import="webmail.wsdl.GetVCFLdapDirResponse"%>
<%@page import="webmail.webservice.client.WebmailClient"%>
<%@page import="org.jivesoftware.smack.tcp.XMPPTCPConnection"%>
<%@page import="org.jivesoftware.smack.XMPPConnection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.jivesoftware.smack.packet.Presence"%>
<%@page import="org.jivesoftware.smack.RosterEntry"%>
<%@page import="java.util.Collection"%>
<%@page import="org.jivesoftware.smack.Roster"%>
<%@page import="webmail.bean.MailAccSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>

<jsp:include page="newinboxpopup.jsp" /> 
<jsp:include page="newinbox1popup.jsp" /> 
</head>
<body>

<%
HttpSession hsbd=request.getSession();
String mcnt="";
String dnoti=hsbd.getAttribute("generalSettingNotification").toString();
String mailid_bd=hsbd.getAttribute("id").toString();
String fname_bd=hsbd.getAttribute("fname").toString();
byte[] jpegBytes2=(byte[])hsbd.getAttribute("img_myurl");
String chat_img_bd="";
if(jpegBytes2!=null)
{
	chat_img_bd= new sun.misc.BASE64Encoder().encode(jpegBytes2);
}

/* String url = (String) request.getAttribute("imageurl");
String imgsrc = url + loggedUser + ".jpg"; */
%>

	<!------/// RIGHT PANNEL ONLY FOR TOOL-------->
<input type="hidden" id="hid_del_email" value="" data-mailuid="" />

	<div class="right-pane_popup">
	
	<div id="div_for_compose" >
	
	
	
<jsp:include page="compose1.jsp" /> 	
	
	
	
	
	
	
	
	
	</div>
	
	
	
	
	
	
	<!-- setting start -->
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<!-- setting end -->
	
	
	</div>



	<!-- <div class="for_setting_1">

		<ul id="menu" class="extra_menu">
			<li><a style="cursor: pointer;">&nbsp; </a>
				<ul class="for_setting new_submenu">
					<li><a style="cursor: pointer;">Settings</a></li>
					<li><a style="cursor: pointer;">Themes</a></li>
					<li><a style="cursor: pointer;">Help</a></li>
				</ul></li>
		</ul>

	</div> -->

               <!-------/// CREATE TAG STRED HERER ---->
               
               
               
               
               
      <!-------/// CREATE TAG STRED HERER ---->
               
               
    <!-------/// CREATE TAG end HERER ---->
               
               
               <div class="web_dialog_overlay"></div>
                <div class="web_dialog_overlay_black">
                <div id="att_disp_cnt" style="max-width: 80% !important;    margin: 50px auto;     height: 90% !important; text-align: center;"></div>
                <div class="att_disp_close"><img alt="" src="images/w_close.png"></div></div>
                
                <div class="web_dialog_overlay_black_pdf">
                <div id="att_disp_cnt_pdf" style="    max-width: 80% !important;    margin: 5px auto;    height: 99% !important;    text-align: center;"></div>
                <div class="att_disp_close_pdf"><img alt="" src="images/w_close.png"></div></div>
                

               <!---////  CREATE TAG END HERE ----->   
               
               
              
               
               <div class="pop_for_image_upload_ck" >
                 <h1>Upload Image <span>X</span></h1>
                      
                      
                       <form id="uploadImageCK" >
			       
			     
                      <table>
                      
                           <tbody><tr>
                              <td colspan="2"> <input type="file" id="upl_ck" name="upl_ck"  /></td>
                           </tr>
                          <tr>
 							<td colspan="2" style="  font-size: 11px; ">
 					</td>
                           </tr>
                           <tr>
                              <td></td>
                              <td><input type="button" onclick="changeCKImg()" value="Insert" class="file_upload_ck search_button"></td>
                           
                           </tr>
                      </tbody></table>
                  </form>
                 </div>
               
               
               
               
               
               
               
               
               










	
	

	<script>
		jQuery(function($) {
			$(window).on('resize', function() {
				var height = $(window).height() - 90;
				//alert(height);
				console.log(height);
				$('#foo').height(height).split({
					orientation : 'horizontal',
					limit : 50
				});
				//$('#foo').css('height',height)
				//  $('.b').height((height / 2)+60);
				$('#b').css('height', (height / 2 + 22));
				//$('.chat_inner_content').height(height / 2);
			}).trigger('resize');
		});
	</script>


<div id="dom_css"></div>
</body>
</html>