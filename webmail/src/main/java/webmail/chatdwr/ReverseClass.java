package webmail.chatdwr;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.directwebremoting.Browser;
import org.directwebremoting.Container;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.proxy.dwr.Util;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

public class ReverseClass {

	
	
	public void refreshInboxDWR(ServerContext serverContext, String loggedUser, String fid, String sub, String luid) {
		String page = serverContext.getContextPath() + "/inbox";
		System.out.println("current page == " + page);
		final String frmid=fid;
		final String subj=sub;
		final String msguid=luid;
		
		ScriptSessionFilter filter = new TestScriptSessionFilter("scriptAttribute", loggedUser);
		Browser.withPageFiltered(page, filter, new Runnable() {

			@Override
			public void run() {
				System.out.println("in run message ^^^^^^^^^^^^^^^^^");
				//utilAll.addFunctionCall("autoWebmailInboxRefreshNP", sub, ""+fid);
					ScriptSessions.addFunctionCall("autoWebmailInboxRefreshNP",  subj, frmid, msguid);
				
			}
		});
		
		
		
		
		
		/*Collection<ScriptSession> sessions = serverContext.getScriptSessionsByPage(currentPage);
	//	Util utilAll = new Util(sessions);
	//	utilAll.addFunctionCall("npchkk");
		ScriptSession ss1=null;
		for (ScriptSession ss : sessions) {
			System.out.println("in for ^^^^^^^^^^^^ " + ss.getAttribute("scriptAttribute"));
			if (ss != null && ss.getAttribute("scriptAttribute") != null
					&& ss.getAttribute("scriptAttribute").equals(loggedUser)) {
				ss1=ss;
				break;
			}
		}
	if(ss1!=null)
	{
		///String arr[]=from.split("<");
	// String	fid=arr[0];
		Util utilAll = new Util(ss1);
		utilAll.addFunctionCall("autoWebmailInboxRefreshNP", sub, ""+fid);
	}
	*/
		
	}
	
	
	
	public void changeMailByUidDWR(ServerContext serverContext, String loggedUser, String uid) {
		String page = serverContext.getContextPath() + "/inbox";
		System.out.println("current page == " + page);
		final String fuid=uid;
	
		
		ScriptSessionFilter filter = new TestScriptSessionFilter("scriptAttribute", loggedUser);
		Browser.withPageFiltered(page, filter, new Runnable() {

			@Override
			public void run() {
				//System.out.println("in run message ^^^^^^^^^^^^^^^^^");
				//utilAll.addFunctionCall("autoWebmailInboxRefreshNP", sub, ""+fid);
					ScriptSessions.addFunctionCall("changeMailByUid",  fuid);
				
			}
		});
		
				
	}

	public void remMailByUidDWR(ServerContext serverContext, String loggedUser, String tp, String cnt) {
		String page = serverContext.getContextPath() + "/inbox";
		System.out.println("current page == " + page);
		final String ftp=tp;
		final String fcnt=cnt;
	
		
		ScriptSessionFilter filter = new TestScriptSessionFilter("scriptAttribute", loggedUser);
		Browser.withPageFiltered(page, filter, new Runnable() {

			@Override
			public void run() {
				//System.out.println("in run message ^^^^^^^^^^^^^^^^^");
				//utilAll.addFunctionCall("autoWebmailInboxRefreshNP", sub, ""+fid);
					ScriptSessions.addFunctionCall("remMailByUid",  ftp, fcnt);
				
			}
		});
		
	}
	
	

	
	
	public void createRoster(XMPPConnection xmppConnection2, String loggedUser, final String imageUrl) {
		String page = null;
		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		Collection<ScriptSession> allSessions = manager.getAllScriptSessions();
		for (ScriptSession ss : allSessions) {
			System.out.println("in for ^^^^^^^^^^^^ " + ss.getAttribute("scriptAttribute"));
			if (ss != null && ss.getAttribute("scriptAttribute") != null
					&& ss.getAttribute("scriptAttribute").equals(loggedUser)) {
				page = ss.getPage();
				System.out.println("page ^^^^^^^^^^^ " + ss.getPage());
				break;
			}
		}
		ScriptSessionFilter filter = new TestScriptSessionFilter("scriptAttribute", loggedUser);
		final XMPPConnection xmppConnection = xmppConnection2;
		Browser.withPageFiltered(page, filter, new Runnable() {

			@Override
			public void run() {
				Roster roster = xmppConnection.getRoster();
				Collection<RosterEntry> entries = roster.getEntries();
				System.out.println("reverse ROSTER SIZE=" + entries.size());
				Presence presence;
				String addDivs = "";
				String pendDivs = "";
				String frndreqDivs = "";
				String  photo = "chat/photo.jpg";
				String stln="";
				VCard card = new VCard();
				for (RosterEntry re : entries) {
					System.out.println("REVERSE Buddy=" + re.getName() + " user=" + re.getUser() + " status="
							+ re.getStatus() + " type=" + re.getType());
					photo = "chat/photo.jpg";
					String user = re.getUser();
					// String imagesrc = imageUrl + user + ".jpg";

					presence = roster.getPresence(user);
					
					 String rosterUid = presence.getFrom();
					RosterPacket.ItemType type = re.getType();
					if (re.getStatus() == null && type == RosterPacket.ItemType.both) {
						try {
							boolean cards = VCardManager.isSupported(re.getUser(), xmppConnection);
							card = new VCard();
						//	System.out.println("is supported .................... " + cards);
							card.load(xmppConnection, re.getUser()); // load
																		// Buddy's
																		// VCard
						} catch (Exception e) {
							// e.printStackTrace();
						}
						if (card != null) {
							 byte[] jpegBytes1 = (byte[]) card.getAvatar();
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
							 
							/*System.out.println("vcard fname----vvvvvvvvvvvvvvvv " + card.getFirstName());
							System.out.println("vcard lname----vvvvvvvvvvvvvvvv " + card.getLastName());
							System.out.println("vcard avatar----vvvvvvvvvvvvv " + photo );
							System.out.println("vcard avatar----vvvvvvvvvvvvv " + card.getAvatarHash() );
							System.out.println("vcard nickname----vvvvvvvvvvvvvvvv " + card.getNickName());
							System.out.println("vcard email home----vvvvvvvvvvvvvvvv " + card.getEmailHome());
							System.out.println("vcard email work----vvvvvvvvvvvvv " + card.getEmailWork());
							System.out.println("vcard avatar mime type----vvvvvvvvvvvvv " + card.getAvatarMimeType());
							System.out.println("vcard jabberid----vvvvvvvvvvvvv " + card.getJabberId());*/
						}
						if (presence.getType() == Presence.Type.available) {
							Presence.Mode mode = presence.getMode();
							if (mode == Presence.Mode.available || mode == null) {
								
								if (presence.getStatus() != null) {

									addDivs += "<div id='" + re.getUser() + "name' class='cheat_row' name='"+rosterUid+"'>" +

									"<div class='small_images'>" + "	<img class='user_images' src='"+photo+"'> "
											+ "	<div id='" + re.getUser() + "' >"
											+ "	<img class='online_green' src='chat/green.png'></div>" + "</div>"
											+ "<div class='contact_information'><p><strong> ";

									if (card != null && card.getNickName() != null) {
										addDivs += card.getNickName();

									} else {
										if (re.getName() != null) {
											addDivs += re.getName();
										}
									}

									addDivs += "	</strong><br> <span>" + re.getUser() + "</span>"
											+ "</p>	</div>	</div>";

								} else {

									addDivs += "<div id='" + re.getUser() + "name' class='cheat_row' name='"+rosterUid+"'>" +

									"<div class='small_images'>" + "	<img class='user_images' src='"+photo+"'> "
											+ "	<div id='" + re.getUser() + "' >"
											+ "	<img class='online_green' src='chat/offline.png'></div>" + "</div>"
											+ "<div class='contact_information'><p><strong> ";

									if (card != null && card.getNickName() != null) {
										addDivs += card.getNickName();

									} else {
										if (re.getName() != null) {
											addDivs += re.getName();
										}
									}

									addDivs += "	</strong><br> <span>" + re.getUser() + "</span>"
											+ "</p>	</div>	</div>";
								}
							}
							if (mode == Presence.Mode.away) {
								

								addDivs += "<div id='" + re.getUser() + "name' class='cheat_row' name='"+rosterUid+"'>" +

								"<div class='small_images'>" + "	<img class='user_images' src='"+photo+"'> "
										+ "	<div id='" + re.getUser() + "' >"
										+ "	<img class='online_green' src='chat/busy.png'></div>" + "</div>"
										+ "<div class='contact_information'><p><strong> ";

								if (card != null && card.getNickName() != null) {
									addDivs += card.getNickName();

								} else {
									if (re.getName() != null) {
										addDivs += re.getName();
									}
								}

								addDivs += "	</strong><br> <span>" + re.getUser() + "</span>"
										+ "</p>	</div>	</div>";
							}
							if (mode == Presence.Mode.dnd) {
							
								addDivs += "<div id='" + re.getUser() + "name' class='cheat_row' name='"+rosterUid+"'>" +

								"<div class='small_images'>" + "	<img class='user_images' src='"+photo+"'> "
										+ "	<div id='" + re.getUser() + "' >"
										+ "	<img class='online_green' src='chat/block.png'></div>" + "</div>"
										+ "<div class='contact_information'><p><strong> ";

								if (card != null && card.getNickName() != null) {
									addDivs += card.getNickName();

								} else {
									if (re.getName() != null) {
										addDivs += re.getName();
									}
								}

								addDivs += "	</strong><br> <span>" + re.getUser() + "</span>"
										+ "</p>	</div>	</div>";
							}
						} else {
							if (presence.getStatus() != null) {
								addDivs += "<div id='" + re.getUser() + "name' class='cheat_row' name='"+rosterUid+"'>" +

								"<div class='small_images'>" + "	<img class='user_images' src='"+photo+"'> "
										+ "	<div id='" + re.getUser() + "' >"
										+ "	<img class='online_green' src='chat/offline.png'></div>" + "</div>"
										+ "<div class='contact_information'><p><strong> ";

								if (card != null && card.getNickName() != null) {
									addDivs += card.getNickName();

								} else {
									if (re.getName() != null) {
										addDivs += re.getName();
									}
								}

								addDivs += "	</strong><br> <span>" + re.getUser() + "</span>"
										+ "</p>	</div>	</div>";
							} else {
								addDivs += "<div id='" + re.getUser() + "name' class='cheat_row' name='"+rosterUid+"'>" +

								"<div class='small_images'>" + "	<img class='user_images' src='"+photo+"'> "
										+ "	<div id='" + re.getUser() + "' >"
										+ "	<img class='online_green' src='chat/offline.png'></div>" + "</div>"
										+ "<div class='contact_information'><p><strong> ";

								if (card != null && card.getNickName() != null) {
									addDivs += card.getNickName();

								} else {
									if (re.getName() != null) {
										addDivs += re.getName();
									}
								}

								addDivs += "	</strong><br> <span>" + re.getUser() + "</span>"
										+ "</p>	</div>	</div>";
							}
						}
						stln=stln+re.getUser()+",";
					} else if (re.getStatus()==null&&re.getType().equals(RosterPacket.ItemType.to)) {

						frndreqDivs += "<div id='" + re.getUser() + "name' class='cheat_row1'>" +

						"<div class='small_images'>" + "	<img class='user_images' src='"+photo+"'> "
								+ "	<div id='" + re.getUser() + "' >"
								+ "	</div>"
								+ "</div><div class='contact_information'><p><strong> " + re.getUser() + "</strong><br>"
								+ "<a onclick='friendRequest(this.id)' id='" + re.getUser()
								+ "acceptbtn' style='cursor: pointer;'>"
								+ "	Accept </a> <a onclick='friendDeny(this.id)' id='" + re.getUser() + "denybtn'  "
								+ " style='cursor: pointer;margin-left: 3px;'> Reject</a></p></div>		</div>";

					} else if (re.getStatus()!=null&&re.getStatus()==RosterPacket.ItemStatus.subscribe) {
						//System.out.println("in else ********************");

						pendDivs += "<div id='" + re.getUser() + "name' class='cheat_row1'>" +

						"<div class='small_images'>" + "	<img class='user_images' src='"+photo+"'> "
								+ "	<div id='" + re.getUser() + "' >"
								+ "	</div>"
								+ "</div><div class='contact_information'><p><strong> " + re.getUser()
								+ "</strong><br> <span style='color: #b00;'>Invitation sent</span></p></div></div>";
						stln=stln+re.getUser()+",";
					}
				}
				String hidlst="<input type='hidden' id='roasterListing' value='"+stln+"' />";
				addDivs += frndreqDivs + pendDivs+hidlst;
				ScriptSessions.addFunctionCall("createChatRow", addDivs);
			}
		});
	}

	@SuppressWarnings("deprecation")
	public void updatePresence(ServerContext serverContext, Presence presence) {
		System.out.println("sever context in reverse == " + serverContext);
		String[] divid = presence.getFrom().split("/");
		String[] name = presence.getFrom().split("@");
		 String avlblid = name[0] + "avlblimg";
		 String statusid = divid[0] + "status";
		System.out.println("page==========" + serverContext.getContextPath());
		String currentPage = serverContext.getContextPath() + "/inbox";
		System.out.println("current page == " + currentPage);
		Collection sessions = serverContext.getScriptSessionsByPage(currentPage);
		Util utilAll = new Util(sessions);
		if (presence.isAvailable()) {
			Presence.Mode mode = presence.getMode();
			//System.out.println(">>>>>>>>>>>mode="+mode);
			if (mode == Presence.Mode.available || mode == null) {
				utilAll.setValue(divid[0],
						"<img src='chat/green.png' class='online_green' id='" + divid[0] + "presence' >");
				 utilAll.setValue(avlblid, "<img src='chat/green.png' style='margin-left: 2px;margin-right: 4px; width: 10px;' />");
				 utilAll.setValue(statusid, presence.getStatus());
			} else if (mode == Presence.Mode.away) {
				utilAll.setValue(divid[0],
						"<img src='chat/busy.png' class='online_green' id='" + divid[0] + "presence' >");
				 utilAll.setValue(avlblid, "<img src='chat/busy.png' style='margin-left: 2px;margin-right: 4px; width: 10px;' />");
				 utilAll.setValue(statusid, presence.getStatus());
			} else if (mode == Presence.Mode.dnd) {
				utilAll.setValue(divid[0],
						"<img src='chat/block.png' class='online_green' id='" + divid[0] + "presence' >");
				 utilAll.setValue(avlblid, "<img src='chat/block.png' style='margin-left: 2px;margin-right: 4px; width: 10px;' />");
				 utilAll.setValue(statusid, presence.getStatus());
			}
		} else {
			utilAll.setValue(divid[0],
					"<img  src='chat/offline.png' class='online_green' id='" + divid[0] + "presence' >");
			 utilAll.setValue(avlblid, "<img src='chat/offline.png'	 style='margin-left: 2px;margin-right: 4px; width: 10px;' />");
			 utilAll.setValue(statusid, presence.getStatus());
		}
	}

	public void listeningForMessages(Message message) {
		String from = message.getFrom();
		final String frm=from.split("/")[0];
		final String newmsg = message.getBody();
		final String[] name = from.split("@");
		final String[] nameid = from.split("/");
		final String toid=message.getTo(); 
		String page = null;
		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		Collection<ScriptSession> allSessions = manager.getAllScriptSessions();
		String[] msgTo = message.getTo().split("/");
		System.out.println("script session size=" + allSessions.size());
		for (ScriptSession ss : allSessions) {
			if (ss != null && ss.getAttribute("scriptAttribute") != null) {
				System.out.println("in for ^^^^^^^^^^^^ " + ss.getAttribute("scriptAttribute") + " to " + msgTo[0]);
				if (ss.getAttribute("scriptAttribute").equals(msgTo[0])) {
					page = ss.getPage();
					System.out.println("page ^^^^^^^^^^^ " + ss.getPage());
					break;
				}
			}
		}
		ScriptSessionFilter filter = new TestScriptSessionFilter("scriptAttribute", msgTo[0]);
		Browser.withPageFiltered(page, filter, new Runnable() {

			@Override
			public void run() {
				System.out.println("in run message ^^^^^^^^^^^^^^^^^");
				if (newmsg != null) {
					System.out.println(toid);
					String msgarrived = "<div class='ui-chatbox-msg' style='float:right; background: #EACFF5;'><p><b>" + name[0] + " : </b>"
							+ newmsg + "</p></div>";
					String msgarrived1= "<div class='ui-chatbox-msg' style='float:right; background: #EACFF5;'><p>"+ newmsg + "</p></div>";
					String id = name[0] + "typing";
					// ScriptSessions.addFunctionCall("calltest");
					ScriptSessions.addFunctionCall("removeLastAppended", id);
					ArrayList<String> msglist = new ArrayList<String>();
					msglist.add(name[0]);
					msglist.add(msgarrived);
					msglist.add(nameid[0]);
					msglist.add(msgarrived1);
					msglist.add(frm);
					System.out.println("call updateChatBox ^^^^^^^^^^^^^^^^^");
					ScriptSessions.addFunctionCall("updateChatBox", msglist);
					//new Util().addFunctionCall("updateChatBox", msglist);
				} else {
					/*String typeid = name[0] + "typing";
					String typing = "<div id='" + typeid
							+ "' class='ui-chatbox-msg'><p style='bottom: 69px;position: fixed;'><b>Typing..</b></p></div>";
					ArrayList<String> msglist = new ArrayList<String>();
					msglist.add(name[0]);
					msglist.add(typing);
					msglist.add(nameid[0]);
					ScriptSessions.addFunctionCall("updateChatBox", msglist);*/
				}
			}
		});
	}
	
	public void listeningForMessagesOffline(Message message) {
		String from = message.getFrom();
		final String frm=from.split("/")[0];
		final String newmsg = message.getBody();
		final String[] name = from.split("@");
		final String[] nameid = from.split("/");
		final String toid=message.getTo(); 
		String page = null;
		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		Collection<ScriptSession> allSessions = manager.getAllScriptSessions();
		String[] msgTo = message.getTo().split("/");
		System.out.println("script session size=" + allSessions.size());
		for (ScriptSession ss : allSessions) {
			if (ss != null && ss.getAttribute("scriptAttribute") != null) {
				System.out.println("in for ^^^^^^^^^^^^ " + ss.getAttribute("scriptAttribute") + " to " + msgTo[0]);
				if (ss.getAttribute("scriptAttribute").equals(msgTo[0])) {
					page = ss.getPage();
					System.out.println("page ^^^^^^^^^^^ " + ss.getPage());
					break;
				}
			}
		}
		ScriptSessionFilter filter = new TestScriptSessionFilter("scriptAttribute", msgTo[0]);
		Browser.withPageFiltered(page, filter, new Runnable() {

			@Override
			public void run() {
				System.out.println("in run message ^^^^^^^^^^^^^^^^^");
				if (newmsg != null) {
					System.out.println(toid);
					String msgarrived = "<div class='ui-chatbox-msg' style='float:right; background: #EACFF5;'><p><b>" + name[0] + " : </b>"
							+ newmsg + "</p></div>";
					String msgarrived1= "<div class='ui-chatbox-msg' style='float:right; background: #EACFF5;'><p>"+ newmsg + "</p></div>";
					String id = name[0] + "typing";
					// ScriptSessions.addFunctionCall("calltest");
					ScriptSessions.addFunctionCall("removeLastAppended", id);
					ArrayList<String> msglist = new ArrayList<String>();
					msglist.add(name[0]);
					msglist.add(msgarrived);
					msglist.add(nameid[0]);
					msglist.add(msgarrived1);
					msglist.add(frm);
					ScriptSessions.addFunctionCall("updateChatBoxOffline", msglist);
					//new Util().addFunctionCall("updateChatBox", msglist);
				} else {
					/*String typeid = name[0] + "typing";
					String typing = "<div id='" + typeid
							+ "' class='ui-chatbox-msg'><p style='bottom: 69px;position: fixed;'><b>Typing..</b></p></div>";
					ArrayList<String> msglist = new ArrayList<String>();
					msglist.add(name[0]);
					msglist.add(typing);
					msglist.add(nameid[0]);
					ScriptSessions.addFunctionCall("updateChatBox", msglist);*/
				}
			}
		});
	}
	
}
