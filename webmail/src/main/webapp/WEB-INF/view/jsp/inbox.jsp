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
<jsp:include page="cmenu.jsp" /> 
<jsp:include page="newinbox.jsp" /> 
<jsp:include page="newinbox1.jsp" /> 
</head>
<body>

<%
HttpSession hsbd=request.getSession();
String mcnt=request.getAttribute("MailCount").toString();
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

	<div class="right-pane">
	<div id="div_for_inbox">
	<div id="div_half_pg_disp" style="display: block">
		<div class="right_top_pannel" >

			<!-------// RIGHT TOP TOOL END HERE -------->
			<div class="for_tool">
				<ul>
					<li>
						<div title="Select" class="tool_inner_box">
							<ul id="menu">
								<li><input class="check_box" type="checkbox" name="all" id="allmail" onclick="toggle(this)"  />
								<a	style="cursor: pointer;" class="sub_menu_link"><img
										src="images/open_sub_menu.png" ></a>
									<ul>
										<li><a style="cursor: pointer;" onclick="allMail()">All</a></li>
										<li><a style="cursor: pointer;" onclick="noneMail()">None</a></li>
										<li><a style="cursor: pointer;" onclick="seenMail()">Read</a></li>
										<li><a style="cursor: pointer;" onclick="unseenMail()">Unread</a></li>
										<li><a style="cursor: pointer;" onclick="staredMail()">Starred</a></li>
										<li><a style="cursor: pointer;" onclick="unstaredMail()">Unstarred</a></li>
									</ul></li>
							  
							</ul>
						</div>
					</li>
					<!-- <li><div onclick="mailCompose()" title="Compose Mail" class="composed_icon"><img src="images/composed_new.png" style="  width: 22px;"></div></li> -->
					<li><div onclick="mailCompose()" title="Compose Mail" class="composed_icon" style="width: 46px; height: 22px; cursor: pointer;">
					<div style="float: left; margin-left: -6px; margin-top: 2px;"><img width="18px" src="images/plus.png" ></div>
					<div style="margin-left: 8px;" class="more">NEW</div></div></li>
					<li>
						<div id="div_search_tool" style="width: 308px !important;" class="large_tool search_form_tool">
							<ul>
								<li title="Reply" id="div_hid_opt1" class="hidden_option">
								<a onclick="openReply()" style="cursor: pointer;">
								<img	src="images/back_one.png"></a></li>
								<div class="right_border"></div>
								<li title="Reply All" id="div_hid_opt2" class="hidden_option">
								<a onclick="openReplyAll()" style="cursor: pointer;"><img
										src="images/back_doble.png"></a></li>
								<div class="right_border"></div>
								<ul title="Forward" id="menu" style="width: 39px !important;" class="next_mail">
									<li class="hidden_option"><a onclick="openForword()" style="cursor: pointer;padding: 0px;">
									<img src="images/next.png"></a>
									<!--  <a style="cursor: pointer;"
										class="sub_menu_link" style="padding: 0px;"><img
											src="images/open_sub_menu.png"></a>
										<ul>
											<li><a style="cursor: pointer;">Forward Inline</a></li>
											<li><a style="cursor: pointer;">Forward As Attachment</a></li>
										</ul> -->
										</li>
								</ul>
								<div class="right_border"></div>
								<li title="Delete" ><a onclick="moveTrashAll()" style="cursor: pointer;"><img src="images/tool_delete.png"></a></li>
								<div class="right_border"></div>
								<li id="li_spam" title="Report Spam"><a onclick="moveSpamAll()" style="cursor: pointer;"><img id="img_spam" style="opacity: 0.8;" src="images/restriction.png"></a></li>
								<div class="right_border"></div>
								<ul id="menu" class="next_mail more_arrow">
									<li class="more">More</li>
									<li><a style="cursor: pointer;    height: 20px !important;    width: 20px !important;" class="sub_menu_link"><img
											src="images/open_sub_menu.png" style="    padding-top: 0px;"></a>
										<ul style="margin-left: -27px !important;">
											<li><a style="cursor: pointer;" onclick="setSetectedMailUnRead()">Mark as Unread</a></li>
											<li><a style="cursor: pointer;" onclick="setSetectedMailRead()">Mark as Read</a></li>
											<li><a style="cursor: pointer;" onclick="setSetectedMailFlag()">Add Star</a></li>
											<li><a style="cursor: pointer;" onclick="setSetectedMailUnFlag()">Remove Star</a></li>
											<!-- <li><a style="cursor: pointer;">Add To Task</a></li>
											<li><a style="cursor: pointer;">Create Filter </a></li>
											<li><a style="cursor: pointer;">Add To Task </a></li>
											<li><a style="cursor: pointer;">Create Event </a></li> -->
										</ul></li>
								</ul>
								
							</ul>
						
							
							
							
						</div>
						<!-- <div class="calender_tool">
							<ul id="menu">
								<li><img src="images/tool_calender.png"
									class="four_margin calender_images"></li>
								<li style="margin-left: 12px;"><a style="cursor: pointer;"
									class="sub_menu_link"><img src="images/open_sub_menu.png"></a>
									<ul class="for_calenderand_date">
										<li><a style="cursor: pointer;" onclick="mailCompose()">Compose Messages</a></li>
										<li><a style="cursor: pointer;">Compose SMS</a></li>
										<li><a style="cursor: pointer;">Create Contact</a></li>
										<li><a style="cursor: pointer;">Create Event</a></li>
										<li><a style="cursor: pointer;">Create Task</a></li>

									</ul></li>
							</ul>
							
						</div> -->
							<!-- <div onclick="mailCompose()" class="composed_box_new">
							<img alt="COMPOSE" title="COMPOSE" src="images/composed.png" />
							</div> -->
							
							
							<!---/// TAG STRED HERE --->
                                    <div title="Labels" class="tag_main">
                                        <img src="images/tag_main.png" />
                                        <img src="images/cal-open.png" class="tag_arrow" />
                                    </div>
                                    <!---/// TAG END HERE ---->
                                     <!---/// Movefolder START HERE ---->
                                     <div title="Move To" class="folder_view">
                                                <img src="images/folder_icon.png" class="folder_icon" />
                                                <img src="images/cal-open.png" class="tag_arrow_1" />
                                              </div>
                                     <!---/// movefolder END HERE ---->
                                    
                                    
					</li>
				</ul>
				
				<!---- RIGHT TOOL STARTED HERE ---->
				<!--------/// Main Right Tool Stared Here -------->
				<div class="right_tool_part">
					<div class="right_tool">
						<a onclick="refreshInbox( )"  style="cursor: pointer;"> <img title="Refresh" src="images/reload.png"/>
						</a>
					</div>
					<div title="Settings" onclick="openSettingTemp()" class="right_tool_1" style="cursor: pointer;">
						<ul id="menu">
							<li><img src="images/setting_toll.png" class="four_margin"></li>
							<!-- <li class="right_menu_1"><img
								src="images/open_sub_menu.png"
								style="margin-left: 8px !important;">  <ul class="for_setting">
                                            <li> <a style="cursor: pointer;">Settings</a></li>
                                            <li><a style="cursor: pointer;">Themes</a></li>
                                            <li> <a style="cursor: pointer;">Help</a></li>
                                        </ul></li> -->
						</ul>

					</div>
					<!-- <div class="right_tool_1">
						<ul id="menu">
							<li><img src="images/multi_level.png"> <a style="cursor: pointer;"
								class="sub_menu_link"><img src="images/open_sub_menu.png"
									style="margin-left: 8px;"></a>
								<ul>
									<li><a style="cursor: pointer;" onClick="toggleViewPanel();panelView('offview')">Full view</a></li>
									<li><a style="cursor: pointer;" onClick="shiftViewLeft();panelView('leftview')">Left view</a></li>
									<li><a style="cursor: pointer;" onClick="shiftViewBottom();">Bottom
											view</a></li>
								</ul></li>
								
						</ul>
						<input type="hidden" id="hid_previewPane" value="offview"/>
					</div> -->
					<span id="mail_pagi" style="display: block;float: right;">
					<a  title="Older" style="cursor: pointer;" onclick="pagiNextPage()">
						<div class="right_tool_1">
							<img src="images/next_mail.png" class="next_imag" >
						</div>
					</a> <a title="Newer" style="cursor: pointer;" onclick="pagiPrevPage()">
						<div class="right_tool_1">
							<img src="images/privious_mail.png" class="next_imag" >
						</div>
					</a>
					</span>
					<span id="search_pagi" style="display: none;float: right;">
					<a  title="Older" style="cursor: pointer;" onclick="pagiSearchNextPage()">
						<div class="right_tool_1">
							<img src="images/next_mail.png" class="next_imag" >
						</div>
					</a> <a title="Newer" style="cursor: pointer;" onclick="pagiSearchPrevPage()">
						<div class="right_tool_1">
							<img src="images/privious_mail.png" class="next_imag" >
						</div>
					</a>
					</span>
					
					
					<!-- <span id="search_pagi" style="display: none;">
					<a style="cursor: pointer;" onclick="pagiNextPage()">
						<div class="right_tool_1">
							<img src="images/next_mail.png" class="next_imag" >
						</div>
					</a> <a style="cursor: pointer;" onclick="pagiPrevPage()">
						<div class="right_tool_1">
							<img src="images/privious_mail.png" class="next_imag" >
						</div>
					</a>
					</span> -->
					
					<%
					long mcount=Long.parseLong(mcnt);
					//long end=MailAccSetting.limitMail;
					long end=Integer.parseInt(hsbd.getAttribute("limitMail").toString());
                       
					if(end>mcount)
					{
						end=mcount;
					}
					%>
					<input type="hidden" id="hid_dnoti" value="<%=dnoti %>">
					<input  type="hidden" id="hid_pagi_pcnt" value="1" />
					<input type="hidden" id="hid_pagi_allmail" value="<%=mcount %>">
					<input type="hidden" id="hid_pagi_search_pcnt" value="" />
					<input type="hidden" id="hid_pagi_search_type" value="" />
					<input type="hidden" id="hid_pagi_search_allmail" value="">
					<%
					if(mcount==0)
					{
						 String act_fldrnm= hsbd.getAttribute("active_folder").toString();
					%>
					<div id="pagination_div" class="right_tool_2" title="<%=act_fldrnm %> is Empty "
						style="margin-left: -14px; line-height: 29px;"><%=act_fldrnm %> is Empty </div>
					<%}
					else
					{
					%>
					<div id="pagination_div" class="right_tool_2"  title="1-<%=end %> of <%=mcount %>"
						style="margin-left: -14px; line-height: 29px;">1-<%=end %> of <%=mcount %></div>
						<%} %>
				</div>

				<!-------------------/// Main Right Tool End Here ------------->
			</div>
			<!------ RIGHT TOOL END HERE TOP ---->
<!------/// COLOR OPTION STRED HERE ------------>
                                       <div class="tag_color_option">
                                                                            <ul>

                    <li><a style="cursor: pointer;">Label color:</a></li>
                    <li class="calender_color">
                         <div class="color_1 color_find"><span> &#x2713 </span></div>
                         <div class="color_2 color_find"> <span> &#x2713 </span></div>
                         <div class="color_3 color_find"> <span> &#x2713 </span></div>
                         <div class="color_4 color_find"> <span> &#x2713 </span></div>
                         <div class="color_5 color_find"> <span> &#x2713 </span></div>
                         <div class="color_6 color_find"> <span> &#x2713 </span></div>
                         <div class="clear"></div>
                         <div class="color_7 color_find"> <span> &#x2713 </span></div>
                         <div class="color_8 color_find"> <span> &#x2713 </span></div>
                         <div class="color_9 color_find"> <span> &#x2713 </span></div>
                         <div class="color_10 color_find"> <span> &#x2713 </span></div>
                         <div class="color_11 color_find"> <span> &#x2713 </span></div>
                         <div class="color_12 color_find"> <span> &#x2713 </span></div>
                    
                    
                    </li>
                           <li class="custom_color">
                       <table>
                          <tr>
                              <td><input type="text" class="custom" /></td>
                          </tr>
                       </table>
                    
                    </li>
                    <li class="choose_custom"><a style="cursor: pointer;">Choose custom color</a> <span class="custom_check"> &#x2713 </span></li>
                  </ul>
                                             <div class="clear"></div>
                                       </div>
                                       <!--------/// COLOR OPTION END HERE -------------->
			<div class="top_tool_information">
				<div class="left_function">
					<ul>
						<!-- <li class="flag_heading"><img src="images/black_star.png"></li> -->
						<li class="flag_heading"><!-- <img src="images/star-flag.png"> --><span class="new_star">&#9733;</span></li>
						<li class="form_heading" id="div_from">FROM</li>
						<li>SUBJECT</li>
					</ul>
				</div>
				<div class="right_bortion">
					<ul>
						<li><a style="cursor: pointer;"><img src="images/attachment.png"></a></li>
						<li><a style="cursor: pointer;"><img src="images/tool.png"></a></li>
					</ul>
				</div>
				<div class="right_bortion  date">
					<ul id="menu" class="">
						<li style="height: 23px;">
							<div class="date_div">
								DATE <a style="margin-top: -2px;margin-left: -6px;cursor: pointer;"> <!-- onclick="dtSorting()" -->
								<span id="dt_sorting"><img style="  opacity: 0.6;" src="images/down_date.png"></span>
								</a>
								<input type="hidden" id="hid_dt_sorting" value="up"/>
							</div>
							
						</li>
					</ul>
				</div>
			</div>
		</div>


		<!-----------------------------/// Main Cointer Stared here --------------->
		<div id="widget" class="widget_new">



			<!---------------/// Tab Content Stared Here Off VIEW ---------------------------->
			<div class="mid_tab tab_main_div context-menu-one"> <!--  content11 mCustomScrollbar"> -->


				<div class="mid_tab_1 right_tab">
					<div class="inner_tab_content">

						<!-------/// TAB HEADING FIRST ----->
						<div class="main_tab_first">
							<!-------/// TAB HEADING STARTED HERE ----->


							<!-------/// TAB HEADING END HERE ----->
							<!----/// TAB CONTENT MAIN STARTED HERE ------->

							<!----------// TAB FIRST CONTENT STARED HERE -------->
							<div id="inb_cnt_div" class="tab_first_content"></div>
							<!----------// TAB FIRST CONTENT END HERE -------->
							<div class="clear"></div>
						</div>


						<!-----/// RIGHT MID CONTENT TAB STARED HERE -------->
					</div>
				</div>




			</div>
			<!-----------------/// Tab Content End Here Off VIEW---------------------------->



			<!-------------------/// Right View and Bottom View Tab Stared Here --------------->

			<div class="mail_content">



				<div class="mail_content_1"
					style="width: 48.5%; float: right; display: block; min-height: 75%; max-height: 95%;">

					<div class="mail_left_content">
					
					<div id="hid_no_cnt" style="margin-top: 100px;text-align: center;">
  					
  					<span id="span_con">No</span> message selected<br><br>
  					<%
  					String qper=(String)request.getAttribute("QuotaPer");
  					String ql=(String)request.getAttribute("QuotaLimit");
  					String qu=(String)request.getAttribute("QuotaUses");
  					String qutu="0";
  					String qutl="0";
  					if(qper!=null && !(qper.equals("")) && ql!=null && !(ql.equals(""))  && qu!=null && !(qu.equals("")))
  					{
  						if(((Integer.parseInt(ql))/(1024))>=1024)
  						{
  							qutl=""+((Integer.parseInt(ql))/(1024*1024))+" GB";
  						}
  						else
  						{
  							qutl=""+((Integer.parseInt(ql))/(1024))+" MB";
  						}
  						
  						if(((Integer.parseInt(qu))/(1024))>=1024)
  						{
  							qutu=""+((Integer.parseInt(qu))/(1024*1024))+" GB";
  						}
  						else
  						{
  							qutu=""+((Integer.parseInt(qu))/(1024))+" MB";
  						}
  					}
  					%>
  					<div>
  					You are currently using <%=qutu %>  (<%=qper %> %) of your <%=qutl %><br>
  					<div style="border: 1px solid #c7c7c7;width: 300px;margin-left: auto;margin-right: auto;height: 10px;margin-top: 5px; ">
  					<div  style="height: 95%;background-color: #c7c7c7;width: <%=qper %>%"></div>
  					</div>
  					</div>
  					
  					</div>
					
					
					<div id="div_left_cnt">

					</div>
					</div>

					<!------/// MAIL MID CONTENT ---->
					<div class="clear"></div>


					<div class="clear"></div>
				</div>



			</div>

			<!-------------------/// Right View and Bottom View Tab End Here --------------->

		</div>

		<!--------------------/// Mail Cointer End Here ----------------->
		<div class="clear"></div>
		</div>
		<!--------------------/// Mail full apge display Here ----------------->
		<div id="div_full_pg_disp" style="display: none">
				
		</div>
		
	</div>
	<div id="div_for_compose" style="display: none;">
	
	
	
<jsp:include page="compose1.jsp" /> 	
	
	
	
	
	
	
	
	
	</div>
	
	<div id="div_for_setting" style="display: none; position: relative;">
	</div>
	
	
	
	
	<!-- setting start -->
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<!-- setting end -->
	
	
	</div>
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

<div class="sharing_mange">
         <h1>Sharing <span id="spopuphead"></span>
<div class="mange_can_top">X</div>
</h1>
<input type="hidden" value="" id="hid_active_shared_fldr"/>
<div class="table_append">
<table class="append_tr">
<tr class="share_bottom">
  <td colspan="2">Who has access</td>
</tr>
<tr>
   
<td colspan="2" class="own_row">
<span class="you_share"><%=mailid_bd %> </span>
<%
String arrr[]=mailid_bd.split("@");

%>
<input type="hidden" id="hid_dom" value="<%=arrr[1] %>">
<input type="hidden" id="hid_dom_id" value="<%=mailid_bd %>">
<span class="text_right">Is Owner </span>
</td> 
                       </tr>
                       <tr>
                         <td colspan="2" class="invite_people_list">
                            <div id="srd_cnt">
                             
                             
                             
                                
                                      </div>
                            
                         </td>
                       </tr>
                       
                       <tr class="share_bottom">
                           <td colspan="2">Invite people:</td>
                       </tr>
                       <tr>
                           <td><input type="text"  class="initive_people " id="share_input"/></td>
                           <td>
                              <div class="can_edit">
                                     
                                      <div class="can_edite">
                                            <select  class="permissionsforshare npselect">
                                               <option value="ur">Read </option>
                                                <option value="uw">Read/Write </option>
                                                <option value="us">All</option> 
                                           </select>
                                      </div>
                                       <div class="share_more">Add</div>
                                     
                              </div>
                          </td>
                       </tr>
                   </table>
				
                   <div class="clear"></div>
                     <div class="share_save_close">
               <!--   <div class="your_self"><input type="checkbox"/><a href="#">Notify via Email </a></div> -->
                    <div class="share_save_box">
                              <div class="cancel_share mange_can">Cancel</div>
                               <div class="send_share cancel_share" id="shareButtonId" onclick="assignSinglePermissions()" >Save</div>
                               <div class="clear"></div>
                    </div>
                 <div class="clear"></div>
                 </div>
                 <div class="clear"></div>
                 </div>
                 
                 
</div>
               <!-------/// CREATE TAG STRED HERER ---->
               <div class="craete_tag">
                    <p>Create Label</p>
                    <input type="text" class="tag_name" /> <div class="tag_sel_option"> <ul><li><div class="color_green test_color"></div> <span></span> <img src="images/cal-open.png" /> </li></ul></div>
                    <div class="select_tag">
                        <ul>
                           <li><div class="color_green color_tag"></div> <span></span>  </li>
                           <li><div class="color_blue color_tag"></div> <span></span>  </li>
                           <li><div class="color_yellow color_tag"></div> <span> </span> </li>
                           <li><div class="color_black color_tag"></div><span>  </span> </li>
                           <li><div class="color_gray color_tag"></div><span></span> </li>
                           <li><div class="color_orange color_tag"></div> <span></span>  </li>
                           <li><div class="color_pink color_tag"></div> <span> </span> </li>
                           <li><div class="color_drak_bl color_tag"></div> <span> </span> </li>
                           <li><div class="color_dar_gree color_tag"></div><span> </span>  </li>
                        
                        </ul>
                    </div>
                    <div class="clear"></div>
                    <div class="tag_can search_button">Cancel</div>
                    <div class="tag_save search_button">Create</div>

                    
               <div class="clear"></div>
               
               </div>
               
               
               
               
      <!-------/// CREATE TAG STRED HERER ---->
               <div class="craete_tag setting_tag">
                    <p>Create Label</p>
                    <input type="text" class="tag_name setting_val" /> <div class="tag_sel_option setting_color"> <ul><li><div class="color_green test_color setting_tag_color"></div> <span></span> <img src="images/cal-open.png" /> </li></ul></div>
                    <div class="select_tag">
                        <ul>
                           <li><div class="color_green color_tag"></div> <span></span>  </li>
                           <li><div class="color_blue color_tag"></div> <span></span>  </li>
                           <li><div class="color_yellow color_tag"></div> <span> </span> </li>
                           <li><div class="color_black color_tag"></div><span>  </span> </li>
                           <li><div class="color_gray color_tag"></div><span></span> </li>
                           <li><div class="color_orange color_tag"></div> <span></span>  </li>
                           <li><div class="color_pink color_tag"></div> <span> </span> </li>
                           <li><div class="color_drak_bl color_tag"></div> <span> </span> </li>
                           <li><div class="color_dar_gree color_tag"></div><span> </span>  </li>
                        
                        </ul>
                    </div>
                    <div class="clear"></div>
                    <div class="setting_tag_cancel search_button">Cancel</div>
                    <div class="setting_tag_save search_button">Create</div>

                    
               <div class="clear"></div>
               
               </div>
               
    <!-------/// CREATE TAG end HERER ---->
               
               
               <div class="web_dialog_overlay"></div>
                <div class="web_dialog_overlay_black">
                <div id="att_disp_cnt" style="max-width: 80% !important;    margin: 50px auto;     height: 90% !important; text-align: center;"></div>
                <div class="att_disp_close"><img alt="" src="images/w_close.png"></div></div>
                
                <div class="web_dialog_overlay_black_pdf">
                <div id="att_disp_cnt_pdf" style="    max-width: 80% !important;    margin: 5px auto;    height: 99% !important;    text-align: center;"></div>
                <div class="att_disp_close_pdf"><img alt="" src="images/w_close.png"></div></div>
                

               <!---////  CREATE TAG END HERE ----->   
               
               
               <div class="pop_for_image_upload" >
                 <h1>Change Your Profile Picture <span>X</span></h1>
                      
                      
                       <form id="uploadImage" >
			         
			       
                      <table>
                      
                           <tbody><tr>
                              <td colspan="2"> <input type="file" id="upl1" name="upl1"  /></td>
                           </tr>
                          <tr>
 							<td colspan="2" style="  font-size: 11px; ">
 							Accepted image size is up to 100KB and File type must be png or jpg only. </td>
                           </tr>
                           <tr>
                              <td></td>
                              <td><input type="button" onclick="changeLDAPImg()" value="Upload" class="file_upload search_button"></td>
                           
                           </tr>
                      </tbody></table>
                  </form>
                 </div>
               
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
               
               
               
               
               
               
               
               
               


<%
if(true)
{

//XMPPConnection connection = (XMPPTCPConnection) hsbd.getAttribute("xmppConnection");

//if(connection!=null&&connection.getUser()!=null){
//	String loggedUser = connection.getUser().split("/")[0];	
//Roster roster = connection.getRoster();
//Collection<RosterEntry> entries = roster.getEntries();
//System.out.println("ROSTER SIZE=" + entries.size());
//Presence presence;
//ArrayList<String> pendingRequests = new ArrayList<String>();
//ArrayList<String> frndRequests = new ArrayList<String>();

%>








	<div class="all_chat_option">
		<div class="chat_info arrow-left">
			<div class="chat_info_left">
				<p class="name">Hariom Srivastava</p>
				<span>hari@silvereye.co</span>
				<p class="com"></p>
			</div>
			<div class="chat_info_right">
				<img src="images/photo_1.jpg" />

			</div>
			<div class="clear"></div>
			<div class="bottom_option">
				<!-------/// Bottom _Left_part--->
				<div class="left_bottom">
					<ul>
						<li><a style="cursor: pointer;">Contact info</a></li>
						<li><a style="cursor: pointer;">Emails</a></li>
					</ul>
				</div>
				<!----------/// Bottom Left Part End --->
				<!--------/// Bottom Right part Stared Here ------>
				<div class="right_bottom">
					<ul>
						<li class="chat_mail"><a style="cursor: pointer;"></a></li>
						<li class="contact_mail"><a style="cursor: pointer;"></a></li>
						<li><a style="cursor: pointer;"></a></li>

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
			</div>
			<div class="chat_info_right">
				<img src="images/photo_1.jpg" />

			</div>
			<div class="clear"></div>
			<div class="bottom_option">
				<!-------/// Bottom _Left_part--->
				<div class="left_bottom">
					<ul>
						
						<li><a name="" id="chat_search_mail" onclick="getAllEmails(this.name)"
						 style="cursor: pointer;">Emails</a></li>
					</ul>
				</div>
				<!----------/// Bottom Left Part End --->
				<!--------/// Bottom Right part Stared Here ------>
				<div class="right_bottom">
					<ul>
						<li id="chat_mail_plus" class="chat_mail"><a style="cursor: pointer;"></a></li>
						<li   id="" onclick="openComposeWithTo(this.id)" class="contact_mail chat_mail_comp"><a style="cursor: pointer;"></a></li>
						<li><a style="cursor: pointer;"></a></li>

					</ul>

				</div>
				<!---------------/// Bottom  Right Part End Here -------->

			</div>

		</div>
		<!---------------/// Chat Search End Here ----------->
		<!-------/// Chat Search option--------->
		<div class="chat_search">
			<div class="chat_search_option">
				<div class="heading_caht">
					<input type="text" id="serchCntId"  placeholder="Search for people" onkeypress="keyPresChatSearch(event)"/>
					<div class="chat_search_btn" onclick="getAllUserListChat()"></div>
				</div>
				
				<!-----//// Chat Search Content ----->
				<div class="chat_search_content">
				<div id="np_chat_searchajax" style="display: none;"></div>
<div id="np_chat_search">
<%
WebmailClient webmailClient=(WebmailClient)request.getAttribute("webmailClient");
String host=(String)hsbd.getAttribute("host");
String id=(String)hsbd.getAttribute("id");
String ldapurl=(String)hsbd.getAttribute("ldapurl");
String ldpabase=(String)hsbd.getAttribute("ldapbase");
String pass=(String)hsbd.getAttribute("pass");
//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
GetVCFLdapDirResponse getdirres=webmailClient.getLdapDirectory(ldapurl, id, pass, ldpabase, "*");

List<VCFLdapDirAtt> ldapDirList=getdirres.getGetVCFLdapDirByParentFile().getVCFLdapDirListResult().getVCFLdapDirList();

Collections.sort(ldapDirList,new NPCompare());
for(VCFLdapDirAtt ulst : ldapDirList)
{
if(!ulst.getContactEmail().equalsIgnoreCase(id)){

	String photo=ulst.getContactPhoto();
%>


					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							
							<% if(photo!= null && !(photo.equals("")  ))
		              {
					%>
						  <img src='data:image/jpg;base64,<%=photo %>' class="user_images"  />
		            <%  }
					  else
					  {
						  %>
						 <img src="chat/photo.jpg" class="user_images" />
					  <%}
						%>	
							<!-- <img
								src="chat/green.png" class="online_green"> -->
						</div>
						<div id="serchContactAndFill" class="contact_information">
							<p>
								<strong><%=ulst.getContactName() %></strong><br><span> <%=ulst.getContactEmail() %></span>
							</p>
						</div>

					</div>
					<!-----------/// FIRST ROW -------------->
					
<%} }%>

</div>
				</div>
				<!--------/// Chat Search End -------->
			</div>
		</div>
		<!------------/// Chat Search Option End ------>
		<!-------/// Chat Downarrow option--------->
		<div class="chat_search_11">

			<!-----//// Chat Search Content ----->
			<div class="chat_downarrow">
				<!--------------//// Chat Down Main Page ------------->
				<div class="chat_down_main">
					<div class="chat_down_top">
						<div class="chat_down_left">
							<img style="height: 28px;"
								src="data:image/jpg;base64,<%=chat_img_bd%>" id="chat_id_bd"
								onerror="getAltChatImage(this.id)" />
						</div>
						<div class="chat_down_right">
							<%=fname_bd%>
							<span><%=mailid_bd%></span>
						</div>

						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<ul>
						<li class="chat_status_menu"><a style="cursor: pointer;">Chat&nbsp;Status
						</a></li>
						<!-- <li class="invites_menu"><a style="cursor: pointer;">Invites</a></li>
						<li class="blocked_menu"><a style="cursor: pointer;">Blocked&nbsp;People
						</a></li>
						<li class="share_your_menu"><a style="cursor: pointer;">
								Share&nbsp;your&nbsp;status </a></li> -->
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
						<div class="chat_main_menu">
							<img src="images/portlet-remove-icon.png" style="    margin-top: 5px;cursor: pointer;" />
						</div>
						<p>Chat Status</p>
						<div class="clear"></div>
					</div>
					<!-----------/// Chat Heading End Here ----->
					<!----------/// Chat Status Content ------->
					<div class="chat_status_content">
							<ul>
								<li><div style="float: left;    margin-right: 2px;
    margin-top: -2px;" class="online"></div>
							<span>Online</span>
									<input type="radio" name="presenceStatus"
									value="online" style="float: right;    margin-top: 2px !important; " id="onlineradio"
									onclick="changePresence(this.value)" />	
									</li>
								<li><input type="radio" name="presenceStatus"
									value="offline" style="float: right;     margin-top: 2px !important;"  onclick="changePresence(this.value)" /><span>Offline</span>
									<div class="offline" style="float: left;    margin-right: 2px;
    margin-top: -2px;" ></div></li>
								<li><input type="radio" style="float: right;    margin-top: 2px !important; "  name="presenceStatus" value="dnd"
									onclick="changePresence(this.value)" /><span>Busy</span>
									<div class="busy" style="float: left;    margin-right: 2px;
    margin-top: -2px;" ></div></li>
								<li><input type="radio" style="float: right;    margin-top: 2px !important; "  name="presenceStatus" value="away"
									onclick="changePresence(this.value)" /><span>Away</span>
									<div class="away" style="float: left;    margin-right: 2px;
    margin-top: -2px;" ></div></li>
							</ul>
						</div>
					<!-----------/// Chat Status  End Here ----->
				</div>
				<!-------------/// Chat Status Box End Here--------->
				<!-------------// Invites box Stared here----------->
				<div class="Blocked_status">
					<!--------///Chat Haeding ---->
					<div class="chat_subheading">
						<div class="chat_main_menu">
							<img src="images/portlet-remove-icon.png" />
						</div>
						<p>Invites</p>
						<input type="text" id="buddyInvite"/> 
					<!-- 	<input type="button" onclick="sendBuddyInvite()"> -->
						<input type="button" >
						<div class="clear"></div>
					</div>
					<!-----------/// Chat Heading End Here ----->
					<!----------/// Chat Status Content ------->
					<div class="chat_status_content">
						<!----------/// Main ROW Content ---------->
						<div class="chat_row_content">

							<%
							//	for (String pending : pendingRequests) {
							%>
							<%-- <div class="invite_row">
								<div class="invite_left"><%=pending%></div>
							
								<div class="invite_right">
									<a href="#">Unblock</a>
								</div>
								<div class="clear"></div>
							</div> --%>
							<%
								//}
							}
							%>
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
						<div class="chat_main_menu">
							<img src="images/portlet-remove-icon.png" />
						</div>
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
								<div class="invite_left">Nirbhay</div>
								<!--------------/// INVITE LEFT END HERE -------->
								<!---------------/// INVITE RIGHT PART -------->
								<div class="invite_right">
									<a style="cursor: pointer;">Unblock</a>
								</div>
								<!--------------/// INVITE RIGHT END HERE -------->
								<div class="clear"></div>
							</div>
							<!-------------/// INVITE Row END HERE -------------->
							<!-----------/// INVITE ROW FIRST STARED HERE ---------->
							<div class="invite_row">
								<!---------------/// INVITE LEFT PART -------->
								<div class="invite_left">Nirbhay</div>
								<!--------------/// INVITE LEFT END HERE -------->
								<!---------------/// INVITE RIGHT PART -------->
								<div class="invite_right">
									<a style="cursor: pointer;">Unblock</a>
								</div>
								<!--------------/// INVITE RIGHT END HERE -------->
								<div class="clear"></div>
							</div>
							<!-------------/// INVITE Row END HERE -------------->
							<!-----------/// INVITE ROW FIRST STARED HERE ---------->
							<div class="invite_row">
								<!---------------/// INVITE LEFT PART -------->
								<div class="invite_left">Nirbhay</div>
								<!--------------/// INVITE LEFT END HERE -------->
								<!---------------/// INVITE RIGHT PART -------->
								<div class="invite_right">
									<a style="cursor: pointer;">Unblock</a>
								</div>
								<!--------------/// INVITE RIGHT END HERE -------->
								<div class="clear"></div>
							</div>
							<!-------------/// INVITE Row END HERE -------------->
							<!-----------/// INVITE ROW FIRST STARED HERE ---------->
							<div class="invite_row">
								<!---------------/// INVITE LEFT PART -------->
								<div class="invite_left">Nirbhay</div>
								<!--------------/// INVITE LEFT END HERE -------->
								<!---------------/// INVITE RIGHT PART -------->
								<div class="invite_right">
									<a style="cursor: pointer;">Unblock</a>
								</div>
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
						<div class="chat_main_menu">
							<img src="images/portlet-remove-icon.png" />
						</div>
						<p>Share your status</p>
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
								<div class="invite_left">Nirbhay</div>
								<!--------------/// INVITE LEFT END HERE -------->
								<!---------------/// INVITE RIGHT PART -------->
								<div class="invite_right">
									<a style="cursor: pointer;">Unblock</a>
								</div>
								<!--------------/// INVITE RIGHT END HERE -------->
								<div class="clear"></div>
							</div>
							<!-------------/// INVITE Row END HERE -------------->
							<!-----------/// INVITE ROW FIRST STARED HERE ---------->
							<div class="invite_row">
								<!---------------/// INVITE LEFT PART -------->
								<div class="invite_left">Nirbhay</div>
								<!--------------/// INVITE LEFT END HERE -------->
								<!---------------/// INVITE RIGHT PART -------->
								<div class="invite_right">
									<a style="cursor: pointer;">Unblock</a>
								</div>
								<!--------------/// INVITE RIGHT END HERE -------->
								<div class="clear"></div>
							</div>
							<!-------------/// INVITE Row END HERE -------------->
							<!-----------/// INVITE ROW FIRST STARED HERE ---------->
							<div class="invite_row">
								<!---------------/// INVITE LEFT PART -------->
								<div class="invite_left">Nirbhay</div>
								<!--------------/// INVITE LEFT END HERE -------->
								<!---------------/// INVITE RIGHT PART -------->
								<div class="invite_right">
									<a style="cursor: pointer;">Unblock</a>
								</div>
								<!--------------/// INVITE RIGHT END HERE -------->
								<div class="clear"></div>
							</div>
							<!-------------/// INVITE Row END HERE -------------->
							<!-----------/// INVITE ROW FIRST STARED HERE ---------->
							<div class="invite_row">
								<!---------------/// INVITE LEFT PART -------->
								<div class="invite_left">Nirbhay</div>

								<!--------------/// INVITE LEFT END HERE -------->
								<!---------------/// INVITE RIGHT PART -------->
								<div class="invite_right">
									<a style="cursor: pointer;">Unblock</a>
								</div>
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
	<div id="appendchatdiv"></div>
	<!-- --CHAT BOX HERE -->
	<div class="main_chat_box">
		<div class="main_inner_box">
			<div class="overflow_chat">
				<div class="overflow_info">
					<div class="overflow_info_content"></div>
					<div class="overflow_info_bottom">
						<!-- <img src="images/chat_icon.png" /> -->
						<div class="count_overflow"></div>
					</div>

				</div>
			</div>
			<div class="inner_chat_box"></div>
		</div>
	</div>
	<!-- CHAT BOX END HERE -->


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