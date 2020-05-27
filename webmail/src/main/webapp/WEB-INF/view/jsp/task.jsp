<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="webmail.wsdl.TaskBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<!------/// RIGHT PANNEL ONLY FOR TOOL-------->
	<%
		HttpSession hsbd = request.getSession();
		String mailid_bd = hsbd.getAttribute("id").toString();
		String fname_bd = hsbd.getAttribute("fname").toString();
		byte[] jpegBytes2 = (byte[]) hsbd.getAttribute("img_myurl");
		String chat_img_bd = "";
		if (jpegBytes2 != null) {
			chat_img_bd = new sun.misc.BASE64Encoder().encode(jpegBytes2);
		}
	%>
	<div class="right-pane">

		<!-----------------------------/// Main Cointer Stared here --------------->
		<div id="widget">

			<!-----------/// Tasks Top Part SRED HERE ---------->

			<!-------/// CALENDER TOP THREE OPTION ----------->
			<%
				List<TaskBean> taskList = (List) request.getAttribute("taskList");
				int totaltask = 0;
				String totalst = "";
				if (taskList != null && !taskList.isEmpty()) {
					totaltask = taskList.size();
					if (totaltask == 0) {
						totalst = "0 - 0 of 0";
					} else if (totaltask < 20) {
						totalst = "1 - " + totaltask + " of " + totaltask;
					} else {
						totalst = "1 - 20 of " + totaltask;
					}

				}
			%>
			<div class="top_task">

				<div class="top_three_calender">
					<!---/// SECOND CALENDER ------>
					<div class="first_cal_option_1" title="Create Task">
						<div class="create_cal_icon_1"></div>
						Create Task
					</div>
					<!------/// SECOND CALENDER END HERE -------->
					<div class="delete_all_tasks" title="Delete Selected" style="cursor: pointer;">
						<div class="create_cal_icon_1"></div>
						Delete
					</div>

				</div>
				<!---------/// CALENDER TOP THREE OPTION END HERE -------->
				<div class="right_tool_part for_calender">

					<!--  <div class="right_tool"> <a href="#"> <img src="images/reload.png"> </a> </div>
                              <div class="right_tool_1">
                                <ul id="menu" >
                                  <li> <img src="images/setting_toll.png" class="four_margin" ></li>
                                  <li class="right_menu_1" > <img src="images/open_sub_menu.png" style="margin-left: 8px !important;"> 
                                  
                                  </li>
                                </ul>
                              </div> -->

					<div style="float: left;">
					<a href="#"><div class="right_tool_1" id="nextpage" title="Next">
							<img src="images/next_mail.png" class="next_imag">
						</div></a> <a href="#"><div class="right_tool_1" id="privpage" title="Previous">
							<img src="images/privious_mail.png" class="next_imag">
						</div></a>
					<div class="right_tool_2"
						style="margin-left: -14px; line-height: 29px;">
						<span id="totaltask"> <%=totalst%></span><input type="hidden"
							id="pagecount" value="1"><input type="hidden"
							id="taskcount" value="<%=totaltask%>">
					</div>
					</div>
					<div class="right_tool">
						<a onclick="refreshTask()" style="cursor: pointer;"> <img title="Refresh" src="images/reload.png">
						</a>
					</div>
					
				</div>
				<div class="clear"></div>

			</div>

			<!----------/// TASK TOP PART END HERE ------------>
			<!---------------/// Tab Content Stared Here Off VIEW ---------------------------->
			<div class="task_content">

				<table class="table_heading">
					<tr class="task_heading">
						<td class="task_check_box"><input type="checkbox"
							class="select_all" /></td>
						<td style="text-align: left;">Task Subject</td>
						<td>Status</td>
						<td>Due Date</td>
						<td>Modified Date</td>
						<td class="task_check_box"><img src="images/delet_white.png" /></td>
					</tr>
				</table>
				<!--                       <table class="table_content"> -->
				<!--                       </table> -->
				<%
					if (taskList != null && !taskList.isEmpty()) {
				%>
				<table class="table_content">
					<%
						
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
							int count = 0;
							for (int i = 0; i < taskList.size() && count < 20; i++, count++) {
								String startDate = "";
								String endDate = "";
								String modifyDate = "";
								TaskBean task = taskList.get(i);
								if(task.getStartdate()!=null && task.getStartdate().toString().length()>0)
								{
								startDate = task.getStartdate().toString();
								if(task.getStartdate().toString().length()>=10)
								startDate = startDate.substring(0, 10);
								System.out.println("stdt="+startDate);

								try {
									Date st = dateFormat1.parse(startDate);
									startDate = dateFormat.format(st);

								} catch (ParseException e) {
									e.printStackTrace();
								}
								}
								
								if(task.getDuedate()!=null && task.getDuedate().toString().length()>0)
								{
								endDate = task.getDuedate().toString();
								if(task.getDuedate().toString().length()>=10)
								endDate = endDate.substring(0, 10);

								try {
									Date st = dateFormat1.parse(endDate);
									endDate = dateFormat.format(st);

								} catch (ParseException e) {
									e.printStackTrace();
								}
								}
								
								if (task.getModifydate() != null &&  task.getModifydate().toString().length()>0) {
									modifyDate = task.getModifydate().toString();
									if(task.getModifydate().toString().length()>=10)
									modifyDate = modifyDate.substring(0, 10);
									try {

										Date md = dateFormat1.parse(modifyDate);
										modifyDate = dateFormat.format(md);
									} catch (ParseException e) {
										e.printStackTrace();
									}
								} else {
									modifyDate = "Not Modified";
								}
								String task_status = task.getStatus();
								System.out.println("=================== : " + task_status);
								if(task_status!=null)
								{
								 if (task_status.equalsIgnoreCase("CANCELLED")) {
									task_status = "Cancelled";
								} else if (task_status.equalsIgnoreCase("IN-PROCESS")) {
									task_status = "In Process";
								} else if (task_status.equalsIgnoreCase("Completed")) {
									task_status = "Completed";
								} 
								}
								else
								{
									task_status="";
								}
					%>
					<tr id="row_<%=task.getUid()%>">
						<td class="task_check_box new_chkbox"><input type="checkbox"
							name="selecttask" value="<%=task.getUid()%>" /></td>
						<%
						System.out.println(task.getDetail()+" >>>>>>>>>>>> : " + task.getProgress()+" : "+ task.getStatus());
						String prio="";
						if(task.getPriority() == 1)
						{
							 prio ="<img src='images/imp_high.png' width='5' title='High priority' style='float: right;padding-top: 2px;'>";
						}
						else if(task.getPriority() == 9)
						{
							 prio= "<img src='images/imp_low.png' width='5' title='Low priority' style='float: right;padding-top: 2px;'>";
						}
							if(task.getProgress() >= 100  && task.getStatus().equalsIgnoreCase("Completed"))
							{
								%>
									<td class="left_text"><s><%=task.getDetail()%></s><%=prio%></td>
								<%
							}
							else
							{
								%>
									<td class="left_text"><%=task.getDetail()%><%=prio%></td>
								<%
							}
							
							
							if(task.getProgress() >= 100  && task.getStatus().equalsIgnoreCase("Completed"))
							{		
						%>
						<td><s><%=task_status%></s></td>
						<%}
							else
							{
						%>
						<td><%=task_status%></td>
						<%}
							if(task.getProgress() >= 100  && task.getStatus().equalsIgnoreCase("Completed"))
							{
							%>
						<td><s><%=endDate%></s></td>
						<%}
							else
							{
							%>
							<td><%=endDate%></td>
							<%}
							if(task.getProgress() >= 100  && task.getStatus().equalsIgnoreCase("Completed"))
							{
							%>
						<td class="new_chkbox"><s><%=modifyDate%></s></s></td>
						<%}
							else
							{
							%>
							<td class="new_chkbox"><%=modifyDate%></td>
							<%} %>
						<td class="task_check_box new_chkbox"><div
								id="del_<%=task.getUid()%>" class="task_icon"></div></td>
					</tr>

					<%
						}
					%>
				</table>
				<%
					} else {
				%>
				<table class="table_content">
				</table>
				<%
					}
				%>


			</div>
			<!-----------------/// Tab Content End Here Off VIEW---------------------------->

			<!-------------------/// Right View and Bottom View Tab Stared Here --------------->


			<!-------------------/// Right View and Bottom View Tab End Here --------------->

		</div>

		<!--------------------/// Mail Cointer End Here ----------------->
		<div class="clear"></div>
	</div>
	</div>
	<div class="for_setting_1">
		<ul id="menu" class="extra_menu">
			<li><a href="#">&nbsp; </a> <!-- <ul class="for_setting new_submenu">
        <li> <a href="#">Settings</a></li>
        <li><a href="#">Themes</a></li>
        <li> <a href="#">Help</a></li>
      </ul> --></li>
		</ul>
	</div>










	<!-- chat start here -->


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
						<li><a style="cursor: pointer;">Add to contacts</a></li>
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
		<!---------------/// Chat Search End Here ----------->
		<!-------/// Chat Search option--------->
		<div class="chat_search">
			<div class="chat_search_option">
				<div class="heading_caht">
					<input type="text" placeholder="Search for people" />
				</div>
				<!-----//// Chat Search Content ----->
				<div class="chat_search_content">

					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							<img src="chat/photo.jpg" class="user_images" /><img
								src="chat/green.png" class="online_green">
						</div>
						<div class="contact_information">
							<p>
								<strong>Nitit Gupta</strong><br> nitin@silvereye.in
							</p>
						</div>

					</div>
					<!-----------/// FIRST ROW -------------->
					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							<img src="chat/photo.jpg" class="user_images" /><img
								src="chat/block.png" class="online_green">
						</div>
						<div class="contact_information">
							<p>
								<strong>Shiv Shankar</strong><br> shiv@silvereye.in
							</p>
						</div>

					</div>
					<!-----------/// FIRST ROW -------------->
					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							<img src="chat/photo.jpg" class="user_images" /><img
								src="chat/green.png" class="online_green">
						</div>
						<div class="contact_information">
							<p>
								<strong>Abhay Pratap Singh</strong><br> abhay@silvereye.in
							</p>
						</div>

					</div>
					<!-----------/// FIRST ROW -------------->
					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							<img src="chat/photo.jpg" class="user_images" /><img
								src="chat/green.png" class="online_green">
						</div>
						<div class="contact_information">
							<p>
								<strong>Lalit Maurya</strong><br> lalit@silvereye.in
							</p>
						</div>

					</div>
					<!-----------/// FIRST ROW -------------->
					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							<img src="chat/photo.jpg" class="user_images" /><img
								src="chat/busy.png" class="online_green">
						</div>
						<div class="contact_information">
							<p>
								<strong>RamSiya Tiwari</strong><br> ramsiya@silvereye.in
							</p>
						</div>

					</div>
					<!-----------/// FIRST ROW -------------->
					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							<img src="chat/photo.jpg" class="user_images" /><img
								src="chat/offline.png" class="online_green">
						</div>
						<div class="contact_information">
							<p>
								<strong>Rohan Pandey</strong><br> rohan@silvereye.in
							</p>
						</div>

					</div>
					<!-----------/// FIRST ROW -------------->
					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							<img src="chat/photo.jpg" class="user_images" /><img
								src="chat/busy.png" class="online_green">
						</div>
						<div class="contact_information">
							<p>
								<strong>Naredra Modi</strong><br> narendra@silvereye.in
							</p>
						</div>

					</div>


					<!-----------/// FIRST ROW -------------->
					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							<img src="chat/photo.jpg" class="user_images" /><img
								src="chat/offline.png" class="online_green">
						</div>
						<div class="contact_information">
							<p>
								<strong>Amit Pandey</strong><br> amit.pandey@silvereye.in
							</p>
						</div>

					</div>
					<!-----------/// FIRST ROW -------------->

					<!-----------/// FIRST ROW -------------->
					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							<img src="chat/photo.jpg" class="user_images" /><img
								src="chat/offline.png" class="online_green">
						</div>
						<div class="contact_information">
							<p>
								<strong>Ram Mishra</strong><br> ram@silvereye.in
							</p>
						</div>

					</div>
					<!-----------/// FIRST ROW -------------->

					<!-----------/// FIRST ROW -------------->
					<!------------//// FIRST ROW --------->
					<div class="cheat_row_11">
						<div class="small_images">
							<img src="chat/photo.jpg" class="user_images" /><img
								src="chat/offline.png" class="online_green">
						</div>
						<div class="contact_information">
							<p>
								<strong>Umesh Singh</strong><br> umesh@silvereye.in
							</p>
						</div>

					</div>
					<!-----------/// FIRST ROW -------------->



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
						<li class="invites_menu"><a style="cursor: pointer;">Invites</a></li>
						<li class="blocked_menu"><a style="cursor: pointer;">Blocked&nbsp;People
						</a></li>
						<li class="share_your_menu"><a style="cursor: pointer;">
								Share&nbsp;your&nbsp;status </a></li>
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
							<img src="images/portlet-remove-icon.png" />
						</div>
						<p>Chat Status</p>
						<div class="clear"></div>
					</div>
					<!-----------/// Chat Heading End Here ----->
					<!----------/// Chat Status Content ------->
					<div class="chat_status_content">

						<ul>
							<li><input type="radio" /><span>Online</span>
								<div class="online"></div></li>
							<li><input type="radio" /><span>Offline</span>
								<div class="offline"></div></li>
							<li><input type="radio" /><span>Busy</span>
								<div class="busy"></div></li>
							<li><input type="radio" /><span>Away</span>
								<div class="away"></div></li>
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
								<div class="invite_left">Rohit Tiwari</div>
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
								<div class="invite_left">Rohit Tiwari</div>
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
								<div class="invite_left">Rohit Tiwari</div>
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
								<div class="invite_left">Rohit Tiwari</div>
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
								<div class="invite_left">Rohit Tiwari</div>
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
								<div class="invite_left">Rohit Tiwari</div>
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
								<div class="invite_left">Rohit Tiwari</div>
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
								<div class="invite_left">Rohit Tiwari</div>
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
								<div class="invite_left">Rohit Tiwari</div>
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
								<div class="invite_left">Rohit Tiwari</div>
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
								<div class="invite_left">Rohit Tiwari</div>

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
						<img src="images/chat_icon.png" />
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
				$('#b').css('height', (height / 2 - 1));
				//$('.chat_inner_content').height(height / 2);
			}).trigger('resize');
		});
	</script>
	<!-- chat end here -->








	<script src="js/tab_event.js" type="text/javascript"
		language="javascript"></script>
	<script type='text/javascript' src='js/jquery.dcjqaccordion.2.7.min.js'></script>
	<script type="text/javascript">
		$(document).ready(function($) {
			$('#accordion-3').dcAccordion({
				eventType : 'click',
				autoClose : false,
				saveState : false,
				disableLink : false,
				showCount : false,
				speed : 'slow'
			});
		});
	</script>
	<link rel="stylesheet" type="text/css"
		href="css/jquery.jscrollpane.css" />
	<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			if (!$.browser.webkit) {
				$('.container').jScrollPane();
			}
		});
	</script>

	<!-----------/// COLOR PICKER STRED HERE ----------->
	<link rel="stylesheet" type="text/css" href="color_picker/spectrum.css">
	<script type="text/javascript" src="color_picker/spectrum.js"></script>
	<script type='text/javascript' src='color_picker/docs.js'></script>
	<!-------------/// COLOR PICKER END HERE --------------->
	<style>
#calendar {
	/*max-width: 900px;*/
	margin: 0 auto;
}
</style>
	<!--------------------/// JS CALENDER END HERE ------------------>

	 <!---/// Upload photo ---------> 
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


	<!------------/// CREATE CALENDER POP STARED HERE ----------->
	<div class="calender_pop">
		<form id="createTask" commandName="createTask">
			<!--------/// HEADER STARED HERE ----------->
			<div class="pop_header">
				<label class="popup_name">Create Tasks</label> <span>X</span>
			</div>
			<!----------/// HEADER END HERE ------->
			<!----------// POP TAB STRED HERE -------->
			<div class="pop_tab">
				<ul>
					<li class="gen_opt gen_active"><div class="gen_icon"></div>General options</li>
					<li class="repe"><div class="repe_icon"></div>Repeat</li>
					<li class="remind"><div class="reminder_icon"></div>Reminders</li>
				</ul>
			</div>
			<!---------/// POP TAB END HERE -------->
			<div class="clear"></div>
			<!----------/// POPUP CREATE CONTENT BOX ---------->

			<div class="pop_content">
				<!------------//// POP CONTENT GENERAL OPTION --------->
				<div class="gen_content">
				<%
				
				Date dt=new Date();
				System.out.println(dt);
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dts= sdf.format(dt);
				System.out.println(dts);
			//	dt.f
				%>
					<table>
						<tr>
							<td>Task Details</td>
							<td>
								<input type="text" id="tsk_detail" name="tsk_detail" class="summary clear_data" placeholder="Enter task title" style="line-height: 0px !important;"/>
							</td>
						</tr>
						<tr>
							<td>Start date</td>
							<td><input type="text" id="startDate" name="startDate" value="<%=dts %>" class="popupDatepicker clear_data" placeholder="yyyy-MM-dd" />
							 <input type="hidden" id="startTime" name="startTime" class="date_stared clear_data" placeholder="mm:ss" /></td>
						</tr>
						<tr>
							<td>Due date</td>
							<td><input type="text" id="endDate" name="endDate" value="" 
								class="popupDatepicker clear_data" placeholder="yyyy-MM-dd" /> <input
								type="hidden" id="endTime" name="endTime" class="date_stared clear_data"
								placeholder="mm:ss" /></td>
						</tr>
						<tr>
							<td>Status</td>
							<td><select id="status" name="status" class="pro_status clear_data">
									<option value="50" class="new_cl">In Process</option>
									<option value="100" class="new_cl">Completed</option>
									<option value="CANCELLED" class="new_cl">Cancelled</option>
							</select></td>
						</tr>
						<tr class="bar_height">
							<td>Progress</td>
							<td><input id="tsk_progress" value="0"
								class="progress_input clear_data" type="number" name="count">
								&nbsp; % Completed
								<div class="clear"></div></td>
						</tr>
						<tr>
							<td>Priority</td>
							<td><select id="tsk_priority" name="priority" class="clear_data">
									<option value="1">High</option>
									<option value="5">Normal</option>
									<option value="9">Low</option>
							</select></td>
						</tr>
						<tr>
							<td>Description</td>
							<td><textarea id="taskDesc" name="taskDesc" class="clear_data"></textarea></td>
						</tr>
					</table>
					<div class="clear"></div>
				</div>
				<!-- 				General option end               -->
				
				<!--                Repeat Option tab              -->

				<div class="repeat_cal">
					<table class="table">
						<tr>
							<td class="repeat_heading">Repeat</td>
							<td>
								<select class="select_repeat" id="frequency" name="frequency" class="clear_data">
										<option value="no">No repetitions</option>
										<option value="DAILY">Daily</option>
										<option value="WEEKLY">Weekly</option>
										<option value="MONTHLY">Monthly</option>
										<option value="YEARLY">Yearly</option>
								</select>
							</td>
						</tr>
						<tr class="repeat_every_day" style="display: none;">
							<td class="repeat_heading">Repeat every:</td>
							<td>
								<select title="Repeat every 1 days" class="select_every_day clear_data" name="interval">
									<option value="1" class="year_default">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
								</select> 
								<label id="inter_lebel">days </label>
							</td>
						</tr>
						<tr class="repeat_on_week" style="display: none;">
							<td class="repeat_heading">Repeat on:*</td>
							<td>
								<span class="repeat_week"> <input type="checkbox"
										value="SU" class="sunday" name="daysofweek"> <label
										title="Sunday">S</label>
								</span> <span class="repeat_week"> <input type="checkbox"
										value="MO" class="monday" name="daysofweek"> <label
										title="Monday">M</label>
								</span> <span class="repeat_week"> <input type="checkbox"
										value="TU" class="tuesday" name="daysofweek"> <label
										title="Tuesday">T</label>
								</span> <span class="repeat_week"> <input type="checkbox"
										value="WE" class="wednesday" name="daysofweek"> <label
										title="Wednesday">W</label>
								</span> <span class="repeat_week"> <input type="checkbox"
										value="TH" class="thursday" name="daysofweek"> <label
										title="Thursday">T</label>
								</span> <span class="repeat_week"> <input type="checkbox"
										value="FR" class="friday" name="daysofweek" > <label
										title="Friday">F</label>
								</span> <span class="repeat_week"> <input type="checkbox"
										value="SA" class="saturday" name="daysofweek"> <label
										title="Saturday">S</label>
								</span>
							</td>
						</tr>
						<tr class="repeat_day_month" style="display: none;">
							<td class="repeat_heading">Repeat by:</td>
							<td>
								<input type="radio" name="month" checked="checked" style="margin-left: 0;" />
								<label>day of the month</label>
								<input type="radio" name="month" />
								<label>day of the week</label>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr class="end_recur" style="margin-top: 5px; display: none;">
							<td style="vertical-align: top;">
								<label>End Recurrence:</label>
							</td>
							<td>
								<input type="radio" name="count" class="count_radio" value="never" /> 
								<label>Never</label><br><br>
								<input type="radio" name="count" class="count_radio" value="count" /> 
								<label>After</label> 
								<input type="number" disabled="disabled" class="positiveNumber count_input clear_data" name="count_input" id="count_input" value="10" /> 
								<label>occurrence(s)</label> <br><br>
								<input type="radio" name="count" class="count_radio " value="until" checked="checked" /> 
								<label>On</label> 
								<input type="text"  class="until_input popupDatepicker clear_data" name="until_input" id="until_input" /> 
								<br>
							</td>
						</tr>
						<tr style="display: none;">
							<td valign="top" class="summary repeat_heading">Summary</td>
							<td>
								<div class="weekly_summary">
									<span class="weekly_text" style="display: none;">Weekly on</span> 
									<span class="weekly_on_text"> 
										<span class="weekend_end"></span> 
										<span class="weekly_on"></span> 
										<span class="weekend_name"></span>
										<span class="month_days_date"></span>
										<span class="month_message"></span> 
										<span class="year_message">
											<span class="year_date"></span> 
											<span class="year_month_name"></span>
										</span> 
										<span class="every_select_day_date_1"></span>
									    <span class="every_select_day_date"></span> 
									    <span class="every_select_week_date"></span>
									</span>
									<div class="clear"></div>
								</div>
							</td>
						</tr>
					</table>
				</div>

				<!--     		Repeat tab end    -->

				<div class="reminder_cal">
					<table id="defaultremindertable">
						<tr>
							<td colspan="2" class="new_line">This event has no
								configured reminders</td>
						</tr>
						<tr>
							<td colspan="2" class="new_line_1">New reminder:</td>
							<td><span>Add New</span></td>
						</tr>
						<tr>
						</tr>
					</table>
					<input type="hidden" id="counter" value="0" class="clear_data" />
					<input type="hidden" id="reminderdata" name="reminderdata"
						class="clear_data" /> 
						<input type="hidden" id="uid" name="uid" class="clear_data" />
					<div class="append_reminder">
						<table class="new_reminder">

						</table>
					</div>
					<div class="clear"></div>
				</div>
				<!-----------/// REMINDER CAL END HERE ------->


				<div class="clear"></div>
				<div class="cancel_cal">Cancel</div>
				<div class="save_cal" onclick="saveTask()">Save</div>
				<div class="clear"></div>
			</div>
			<!-------------/// POPUP CREATE CONTENT BOX END HERE ------------>
		</form>
	</div>
	<div class="dymaic_am remi_date_opt">
		<div class="12a amvalue twitle_num">00:00am</div>
		<div class="12.30a amvalue">00:30am</div>
		<div class="1a amvalue">1:00am</div>
		<div class="1.30a amvalue">1:30am</div>
		<div class="2a amvalue">2:00am</div>
		<div class="2.3a amvalue">2:30am</div>
		<div class="3a amvalue">3:00am</div>
		<div class="3.3a amvalue">3:30am</div>
		<div class="4a amvalue">4:00am</div>
		<div class="4.3a amvalue">4:30am</div>
		<div class="5a amvalue">5:00am</div>
		<div class="5.30a amvalue">5:30am</div>
		<div class="6a amvalue">6:00am</div>
		<div class="6.30a amvalue">6:30am</div>
		<div class="7a amvalue">7:00am</div>
		<div class="7.30a amvalue">7:30am</div>
		<div class="8a amvalue">8:00am</div>
		<div class="8.3a amvalue">8:30am</div>
		<div class="9a amvalue">9:00am</div>
		<div class="9.3a amvalue">9:30am</div>
		<div class="10a amvalue">10:00am</div>
		<div class="10.3a amvalue">10:30am</div>
		<div class="11a amvalue">11:00am</div>
		<div class="11.3a amvalue">11:30am</div>
		<div class="12p amvalue">12:00pm</div>
		<div class="12.3p amvalue">12:30pm</div>
		<div class="1p amvalue">1:00pm</div>
		<div class="1.3p amvalue">1:30pm</div>
		<div class="2p amvalue">2:00pm</div>
		<div class="2.3p amvalue">2:30pm</div>
		<div class="3p amvalue">3:00pm</div>
		<div class="3.30p amvalue">3:30pm</div>
		<div class="4p amvalue">4:00pm</div>
		<div class="4.30p amvalue">4:30pm</div>
		<div class="5p amvalue">5:00pm</div>
		<div class="5.3p amvalue">5:30pm</div>
		<div class="6p amvalue">6:00pm</div>
		<div class="6.3p amvalue">6:30pm</div>
		<div class="7p amvalue">7:00pm</div>
		<div class="730p amvalue">7:30pm</div>
		<div class="8p amvalue">8:00pm</div>
		<div class="8.30p amvalue">8:30pm</div>
		<div class="9p amvalue">9:00pm</div>
		<div class="9.30p amvalue">9:30pm</div>
		<div class="10p amvalue">10:00pm</div>
		<div class="10.30p amvalue">10:30pm</div>
		<div class="11.p amvalue">11:00pm</div>
		<div class="11.30p amvalue">11:30pm</div>
	</div>
	
	<div class="web_dialog_overlay"></div>
	<!------------/// CREATE CALENDER POP END HERE -------------->

	<!-- ---------------// Add Task Script Start---------------------- -->


<script type="text/javascript">

	function saveTask() {
		
// 		alert("in createtask");
		var tsk_detail = $('#tsk_detail').val();
		var startDate = $('#startDate').val();
		var startTime = $('#startTime').val();
		var endDate = $('#endDate').val();
		var endTime = $('#endTime').val();
		var status = $('#status').val();
		var tsk_priority = $('#tsk_priority').val();
		var taskDesc = $('#taskDesc').val();
		var tsk_progress = $('#tsk_progress').val();
		
		var count_radio = $('.count_radio:checked').val();
//			alert("count_radio : " +count_radio);
		
		var frequency = $('#frequency').val();
		var interval = $('.select_every_day').val();
		$('.error').removeClass("error");
		
		
		var count = "";
		var until = "";
		var daysofweek ="";
		if(frequency!="no")
		{
		if(count_radio == "count")
		{
			count = $('#count_input').val();
			if(count == "")
			{
				$('#count_input').addClass("error");
				return false;
			}
		}
		else if(count_radio == "until")
		{
			until = $('#until_input').val();
			if(until == "")
			{
				$('#until_input').addClass("error");
				return false;
			}
		}
		
		
		
//			return false;
		

		$.each($("input[name='daysofweek']:checked"), function() {
		  if(daysofweek!="")
			  daysofweek=daysofweek+"`"+$(this).val() ;
		  else
			  daysofweek=$(this).val() ;
		});
		}
		
		// 	alert("tsk_progress "+ tsk_progress);
		$('#tsk_detail').css("border-color", "");
		$('#startDate').css("border-color", "");
		$('#startTime').css("border-color", "");
		$('#endDate').css("border-color", "");
		$('#endTime').css("border-color", "");

		var valid = true;
		if (tsk_detail == "") {
			$('#tsk_detail').css("border-color", "red");
			valid = false;
		}
		if (startDate == "") {
			$('#startDate').css("border-color", "red");
			valid = false;
		}

		if (tsk_progress == "") {
			$('#tsk_progress').css("border-color", "red");
			valid = false;
		}
		if (endDate == "") {
			$('#endDate').css("border-color", "red");
			valid = false;
		}
		
		if (!valid) {
			return false;
		}
		setreminder();
		var reminderdata = $('#reminderdata').val();
		// 	alert("go to ajax");
		
		var taskurl = "createTask";
		var uid = $('#uid').val();
		if(uid != "")
		{
			taskurl = "updateTask";
		}
		
		$("#action_gif").css("display", "block");
		$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/"+taskurl,
					data : {
						'uid' : uid,
						'tsk_detail' : tsk_detail,
						'startDate' : startDate,
						'startTime' : startTime,
						'endDate' : endDate,
						'endTime' : endTime,
						'status' : status,
						'tsk_progress' : tsk_progress,
						'tsk_priority' : tsk_priority,
						'taskDesc' : taskDesc,
						'frequency' : frequency,
						'interval' : interval,
						'rec_type' : count_radio,
						'count' : count,
						'until' : until,
						'daysofweek' : daysofweek,
						'reminderdata':reminderdata
					},
					contentType : "application/json",
					success : function(data) {
						// 			alert("Response Data : " +data);	
						var obj = jQuery.parseJSON(data);
						if (obj.enddateerror == "true") {
							//$('#endDate').val("");
							// 			   	$('#endTime').val("");
							$("#action_gif").css("display", "none");
							showmsg("alert",
									"The Due date you entered occurs before the Start date!")
						}
						if (obj.success == "true") {
							loadTaskList();
							$(".calender_pop").css("display", "none");
							$(".web_dialog_overlay").css("display", "none");
							
							if(uid != "")
							{
								showmsg("alert", "Task updated successfully !")
							}
							else
							{
								showmsg("alert", "Task created successfully !")
							}
							$('#createTask')[0].reset();
						}
						if (obj.success == "false") {
							showmsg("alert",
									"Task not created successfully !")
						}

					}
				});
	}
	
	
	function setreminder()
	{
		reminderdata="";
		$('.new_reminder > tbody  > tr').each(function() {
			reminderdata=reminderdata+$(this).find(".reminder_type").val()+"`"+$(this).find(".reminder_time").val()+"`"+$(this).find(".reminder_dur").val()+";";
			$('#reminderdata').val(reminderdata);
		});
	}
	
</script>


	<!-- ---------------// Add Task Script End---------------------- -->



	<!--------/// PROGRESS BAR ---->
	<script>
		var volume = {

			init : function() {
				$('#volume').on('click', volume.change);
				$('#volume .control').on('mousedown', volume.drag);
			},

			change : function(e) {
				e.preventDefault();
				window.percent = helper.getFrac(e, $(this)) * 100;
				$('#volume .control').animate({
					width : percent + '%'
				}, 100);
				volume.update(percent);
				//var new_get = $('.vol-box').html();
				//alert(percent);
				var id = $("#select1").val();
				//alert(id)
				if (percent <= 0) {

					$('#select1 option[value="0"]')
							.attr("selected", "selected");
				} else if (percent >= 1 && percent <= 99) {
					$('#select1 option[value="50"]').attr("selected",
							"selected");
				} else if (percent > 99) {
					$('#select1 option[value="100"]').attr("selected",
							"selected");

				}
				//else if(percent >=70  )
				//  {

				//    $('#select1 option[value="90"]').attr("selected", "selected");

				//  }

				// var get_value = $('#ddlViewBy').find().val('30'+'%').text();
				//alert($(control).width());
			},

			update : function(percent) {
				var val_ne = $('.vol-box').text(Math.round(percent) + '%');
				//alert(val_ne);

				//console.log(percent);
			},

			drag : function(e) {
				e.preventDefault();
				$(document).on('mousemove', volume.moveHandler);
				$(document).on('mouseup', volume.stopHandler);

			},

			moveHandler : function(e) {
				var holderOffset = $('#volume').offset().left, sliderWidth = $(
						'#volume').width(), posX = Math.min(Math.max(0, e.pageX
						- holderOffset), sliderWidth);

				$('#volume .control').width(posX);
				volume.update(posX / sliderWidth * 100);
				//alert(volume.update(posX / sliderWidth * 100));
			},

			stopHandler : function() {
				$(document).off('mousemove', volume.moveHandler);
				$(document).off('mouseup', volume.stopHandler);
			}

		}

		var helper = {
			getFrac : function(e, $this) {
				return (e.pageX - $this.offset().left) / $this.width();
			}
		//alert(getFrac);
		}

		volume.init();
		//alert(volume.init());
	</script>

	<!-------/// PROGRESS END HERE ---->
	<!--------// TEST STARED HERE --->

	<!----// TEST END HERE --------->

</body>
</html>