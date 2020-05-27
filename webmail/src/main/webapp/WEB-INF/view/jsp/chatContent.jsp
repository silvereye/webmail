<%@page import="org.apache.commons.codec.binary.StringUtils"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="webmail.bean.NPCompare"%>
<%@page import="java.util.Collections"%>
<%@page import="webmail.wsdl.VCFLdapDirAtt"%>
<%@page import="java.util.List"%>
<%@page import="webmail.wsdl.GetVCFLdapDirResponse"%>
<%@page import="webmail.webservice.client.WebmailClient"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	HttpSession chat_hs = request.getSession();
	String mailid_chat = chat_hs.getAttribute("id").toString();
	String fname_chat = chat_hs.getAttribute("fname").toString();
	String chatMode = chat_hs.getAttribute("chatMode").toString();
	byte[] jpegBytes3 = (byte[]) chat_hs.getAttribute("img_myurl");
	String chat_img = "chat/photo.jpg";
	String modeImg = "images/online_file.png";
	if(chatMode.equalsIgnoreCase("online"))
	{
		modeImg="chat/green.png";
	}
	else if(chatMode.equalsIgnoreCase("away"))
	{
		modeImg="chat/busy.png";
	}
	else if(chatMode.equalsIgnoreCase("offline"))
	{
		modeImg="chat/offline.png";
	}
	else if(chatMode.equalsIgnoreCase("dnd"))
	{
		modeImg="chat/block.png";
	}
	
	if (jpegBytes3 != null && jpegBytes3.length > 10) {
		chat_img = new sun.misc.BASE64Encoder().encode(jpegBytes3);
		chat_img = "data:image/jpg;base64," + chat_img;
	}
%>
	<div class="parent_head">
			<div class="chat_heading">
				<div class="chat_h_left">
					<img src="<%=chat_img%>" id="chat_id"
						onerror="getAltChatImage(this.id)" class="h_name" />
						<img src="<%=modeImg %>" class="online_green_1">
					<div class="chat_h_name"><%=fname_chat%></div>
					<div class="h_drop">
						<img src="images/down_mail.png" />
					</div>
				</div>
				<div class="chat_h_right" title="Find people to chat with">
					<img src="images/search_1.jpg" />
					<div class="left_arrow"></div>
				</div>
			</div>
		</div>
		<div class="chat_inner_content">

			<%
				HttpSession hsession = request.getSession();
				XMPPConnection connection = (XMPPTCPConnection) hsession.getAttribute("xmppConnection");
				if(connection!=null&&connection.getUser()!=null){
					System.out.println(connection+" and "+connection.getUser());
					System.out.println("mode---->>>"+connection.getFromMode());
				String loggedUser = connection.getUser().split("/")[0];
				System.out.println("loggedUser---->>>"+loggedUser);
			/* 	String url = (String) request.getAttribute("imageurl");
				String imgsrc = url + loggedUser + ".jpg"; */
				Roster roster = connection.getRoster();
				Collection<RosterEntry> entries = roster.getEntries();
				System.out.println("ROSTER SIZE=" + entries.size());
				Presence presence;
				VCard card = null;
				ArrayList<String> frndRequests = new ArrayList<String>();
				ArrayList<String> pendingRequests = new ArrayList<String>();
				String stln="";
				for (RosterEntry re : entries) {

					String user = re.getUser();
			//	System.out.println("in roster------"+re.);
					/* String imagesrc=url+user+".jpg"; */
					presence = roster.getPresence(re.getUser());
					 String rosterUid = presence.getFrom();
					// System.out.println("in roster------"+from);
				//	System.out.println(presence);
				/* 	System.out.println("Buddy==" + re.getName() + " & Status= " + re.getStatus() + " User=" + re.getUser()
							+ " type=" + re.getType() + "Mode=" + presence.getMode());
					System.out.println(
							"before if " + user + " is offline type= " + presence.getType() + " Mode=" + presence.getMode()); */
					RosterPacket.ItemType type = re.getType();
%>

<script type="text/javascript">
console.log('<%=re.getUser()%> having type = <%=type%> and status = <%=re.getStatus()%> mode=<%=presence.getType()%> ');
</script>
<% 					

/*  System.out.println("this is typeeeeeeeeeeeeeeeeeeeeeeee   "+re.getUser()+" status = "
		+ re.getStatus()+" " +(re.getStatus()!=null&&re.getStatus()==RosterPacket.ItemStatus.subscribe) + " : "+type+" "+re.getType().equals(RosterPacket.ItemType.none));

 */
					
					
					if (re.getStatus() == null && type == RosterPacket.ItemType.both) {
						//System.out.println("iiiiiiiiiiiiiifffffffffffffffffff");
						try {
							//  System.out.print(connection+"asdflkjasdfklsdajfalksjdflkasjdfkls");
							boolean cards = VCardManager.isSupported(re.getUser(), connection);
							card = new VCard();
							//System.out.println("is supported .................... " + cards);
							card.load(connection, re.getUser()); //load Buddy's VCard
						} catch (Exception e) {
							//e.printStackTrace();
						}
						String photo="chat/photo.jpg";
						if (card != null) {
							 byte[] jpegBytes1 = (byte[]) card.getAvatar();
							// String  photo = new sun.misc.BASE64Encoder().encode(jpegBytes1); 
							// Base64 base64 = new Base64();  
							// String  photo= base64.encodeBase64String(jpegBytes1);
							 //String ph="";
							 if(jpegBytes1!=null)
								{
								 String ph=StringUtils.newStringUtf8(Base64.encodeBase64(jpegBytes1, false));
								 if(ph!=null && ph.length()>0)
								 {
								 StringBuilder sb = new StringBuilder();
								 sb.append("data:image/png;base64,");
								 sb.append(ph);
								 photo = sb.toString();
								 }
								 else
								 {
									 photo = "chat/photo.jpg"; 
								 }
								}
							 else
							 {
								 photo = "chat/photo.jpg";
							 }
							 
						 /*  System.out.println("vcard fname----vvvvvvvvvvvvvvvv " + card.getFirstName());
							System.out.println("vcard lname----vvvvvvvvvvvvvvvv " + card.getLastName());
							System.out.println("vcard avatar----vvvvvvvvvvvvv " + photo );
							System.out.println("vcard avatar----vvvvvvvvvvvvv " + card.getAvatarHash() );
						
							System.out.println("vcard nickname----vvvvvvvvvvvvvvvv " + card.getNickName());
							System.out.println("vcard email home----vvvvvvvvvvvvvvvv " + card.getEmailHome());
							System.out.println("vcard email work----vvvvvvvvvvvvv " + card.getEmailWork());
							System.out.println("vcard avatar mime type----vvvvvvvvvvvvv " + card.getAvatarMimeType());
							System.out.println("vcard jabberid----vvvvvvvvvvvvv " + card.getJabberId());    */
						}
						if (presence.getType() == Presence.Type.available) {
							Presence.Mode mode = presence.getMode();
						//	System.out.println(user + " is online");
			%>

			<div class="cheat_row" name="<%=rosterUid %>" id="<%=re.getUser()%>name">

				<div class="small_images">
					<img src="<%=photo %>" class="user_images" />

					<div id="<%=re.getUser() %>">
					<%
						if (mode == Presence.Mode.available || mode == null) {
					%>
					<img src="chat/green.png" class="online_green" id="<%=re.getUser()%>presence">

					<%
						}
									if (mode == Presence.Mode.away) {
					%>
					<img src="chat/busy.png" class="online_green" id="<%=re.getUser()%>presence">


					<%
						}
									if (mode == Presence.Mode.dnd) {
					%>
					<img src="chat/block.png" class="online_green" id="<%=re.getUser()%>presence">

					<%
						}
					%></div>
				</div>
				<div class="contact_information">
				<p>

				<strong> <%
 				if (card != null && card.getNickName() != null) {
 					%> <%=card.getNickName()%>
							<%
					} else {
						if (re.getName() != null) {
						%> <%=re.getName()%> <%
 					}
 				}
				 %>
				</strong><br> <span><%=re.getUser()%></span>
				</p>
				</div>

			</div>
			<%
				} else {
						//	System.out.println(user + " is offline type=" + presence.getType());
			%>

			<div class="cheat_row" name="<%=rosterUid %>" id="<%=re.getUser()%>name">

				<div class="small_images">
					<img src="<%=photo %>" class="user_images" /> 
					<div id="<%=re.getUser() %>">
					<img
						src="chat/offline.png" class="online_green" /></div>
				</div>
				<div class="contact_information">
					<p>
						<strong> 
									<%
								 	if (card != null && card.getNickName() != null) {
									%> 
									<%=card.getNickName()%>
									<%
									} else {
									if (re.getName() != null) {
									%> <%=re.getName()%> <%
								 	}
					 				}
								 	%>
						</strong><br> <span><%=re.getUser()%></span>
					</p>
				</div>
			</div>
			<%
				}
						stln=stln+re.getUser()+",";
					} else if (re.getStatus()==null&&re.getType().equals(RosterPacket.ItemType.to)) {
				//		System.out.println("pending");
						pendingRequests.add(user); 
					}else if(re.getStatus()!=null&&re.getStatus()==RosterPacket.ItemStatus.subscribe) {
				//		System.out.println("111111111else if"   );
						frndRequests.add(user); 
						stln=stln+re.getUser()+",";
					}
					
				}
			%>
			<input type="hidden" id="roasterListing" value="<%=stln%>" />
			
			<%
			
				for (String frnds : frndRequests) {
			%>
				<div class="cheat_row1" id="<%=frnds%>name">

				<div class="small_images">
					<img src="chat/photo.jpg" class="user_images" /> 
					<div id="<%=frnds %>">
					<!-- <img
						src="chat/offline.png" class="online_green" /> --></div>
				</div>
				<div class="contact_information">
					<p>
						<strong> 
							<%=frnds%>		
						</strong><br> <span style="color: #b00;">Invitation sent</span>
					</p>
				</div>
			</div>


<%-- 
			<div class="cheat_row" style="margin-bottom: 10px;" >
				<div class="small_images">
					<img class="user_images" src="chat/photo.jpg" /> <a href="#"
						id="<%=frnds + "name"%>"><p>
							<strong><%=frnds%></strong>
						</p></a><br />
				</div>
				

			</div> --%>

			<%
				}
			%>
			<%
			
				for (String frnds : pendingRequests) {
			%>

				<div class="cheat_row1" >

				<div class="small_images">
					<img src="chat/photo.jpg" class="user_images" /> 
					<div id="<%=frnds %>">
				<!-- 	<img
						src="chat/offline.png" class="online_green" /> --></div>
				</div>
				<div class="contact_information">
					<p><strong> 
							<%=frnds%>		
						</strong><br>
							<a onclick="friendRequest(this.id)" id="<%=frnds + "acceptbtn"%>" style="cursor: pointer;">
					<!-- <img src="images/accept.png"  > -->
					Accept
				</a> <a onclick="friendDeny(this.id)" id="<%=frnds + "denybtn"%>"   style="cursor: pointer;margin-left: 3px;"> <!-- <img src="images/reject.png"  > -->Reject
				</a>
					</p>
				</div>
			</div>
			

			<%
				}}
			%>

		</div>
	
</body>
</html>