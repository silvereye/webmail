package webmail.controller;

import java.util.Date;
import java.util.Hashtable;

import javax.mail.Store;
import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.Connections;
import com.sieve.manage.ManageSieveClient;
import com.sieve.manage.ManageSieveResponse;
import com.sieve.manage.examples.ConnectAndListScripts;
import com.sun.mail.imap.IMAPFolder;

import redis.clients.jedis.JedisPool;
import webmail.bean.MailAccSetting;
import webmail.bean.SessionLogoutListener;
import webmail.chatdwr.ScriptSessList;
import webmail.chatdwr.XmppChatClass;
import webmail.dao.LoginlogDao;
import webmail.dao.SharedDao;
import webmail.idle.IdleMail;
import webmail.model.Loginlog;
import webmail.webservice.client.CalendarClient;
import webmail.webservice.client.FileClient;
import webmail.webservice.client.FolderClient;
import webmail.webservice.client.WebmailClient;
import webmail.wsdl.CreateCalendarResponse;
import webmail.wsdl.CreateEssentialFoldersResponse;
import webmail.wsdl.CreateFileResponse;
import webmail.wsdl.DeleteFileResponse;
import webmail.wsdl.GetFileByPathResponse;
import webmail.wsdl.GetLdapFNameResponse;
import webmail.wsdl.GetLdapModifyOneAttResponse;
import webmail.wsdl.GetWebmailAuthResponse;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;

@Controller
public class LoginAuthenticationController {


	@Autowired private ScriptSessList scriptSessList;
	@Value ("${xmppDomain}") private String xmppDomain;
	@Value ("${packetReplyTimeout}") private int packetReplyTimeout; // millis
	@Value ("${chatImageFolder}") private String chatImageFolder;
	@Value ("${onlineStatus}") private String onlineStatus;
	
	 static Logger log = Logger.getLogger(LoginAuthenticationController.class.getName());
	@Autowired  
	private LoginlogDao loginlogDao; 
	@Autowired
	private WebmailClient webmailClient;
	@Autowired
	private FolderClient folderClient;
	
	@Autowired
	private FileClient fileClient;
	
	@Autowired
	private CalendarClient calendarclient;
	
	public  DirContext getConnection(String url,String uid,String password, String base)
	{
		    DirContext ctx=null;
		    try
		    {
		    	String username="";
		    	/*String arr[]=uid.split("@");
		    	String dom="";
		    	if(arr!=null && arr.length==2)
		    	{
		    		dom=arr[1];
		    	}*/
		   // String base="ou=Users,domainName="+dom+",o=domains,"+mbase;
		    username="mail="+uid+","+base;
		    //System.out.println("@@@@@@@@@@@@@@@@@@ ldap user="+username);
		    Hashtable env = new Hashtable();
		    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		    env.put(Context.PROVIDER_URL, url); // LDAP host and base
		    env.put("java.naming.ldap.attributes.binary", "jpegPhoto");
		    // LDAP authentication options
		    env.put(Context.SECURITY_AUTHENTICATION, "simple");
		    env.put(Context.SECURITY_PRINCIPAL, username);
		    env.put(Context.SECURITY_CREDENTIALS, password);

		     ctx = new InitialDirContext(env);
		
		    }
		    catch(Exception e)
		    {
		    	System.out.print(e.toString());
		    	e.printStackTrace();
		    	
		    }
		    return ctx;
	}
	
	public void closeConn(DirContext ctx)
	{
		try
	    {
	   if(ctx!=null)
	   {
	     ctx.close();
	   }
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	//System.out.print(e.toString());
	    	
	    }
	}
	
	
	
	
	
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String dologin(ModelMap map,  HttpServletRequest request) 
	{
		String devtype=request.getHeader("User-Agent");
				System.out.println("ddevice>>>>>>>>>>"+devtype);
		
		String id=request.getParameter("id");
		String pass=request.getParameter("pass");
		
		String host = "127.0.0.1";
	    String port = "143";
	    String smtpport = "25";
	    String ldapurl = "ldap://127.0.0.1:389";
	    String chathost = "mail.wihg.res.in";
	    String caldevUrl = "https://mail.wihg.res.in:9443";
	    
		/*String host="mail.travelunravel.com";
		String port="993";
		String smtpport="587";
		String ldapurl ="ldap://mail.travelunravel.com:389";
		String chathost="mail.travelunravel.com";
		String caldevUrl="https://mail.travelunravel.com:9443";*/
		
		String XMailer="Sandesh Webmail 1.0";
		String caldevProId="//davical.org//NONSGML AWL Calendar//EN";
		String sieveport="2000";
		String base1 ="dc=wihg,dc=res,dc=in";
	    String base="";
	    String listbase="";
	    String dom="wihg.res.in";
		if(!id.contains("@"))
		{
		id=id+"@"+dom;	
		}
		
		HttpSession hs=request.getSession();
		GetWebmailAuthResponse wauthres= webmailClient.getWebmailAuthRequest(host, port, id, pass);
		int flag=wauthres.getGetWebmailAuth();
		//System.out.println("********************auth="+flag);
		if(flag==1)
		{
			
			
			String mid=id;
			String arr[]=mid.split("@");
	    	if(arr!=null && arr.length==2)
	    	{
	    	dom=arr[1];
	    	}
	    	base="ou=Users,domainName="+dom+",o=domains,"+base1; 
	    	listbase="ou=Groups,domainName="+dom+",o=domains,"+base1;
	    	//hs.setMaxInactiveInterval(2*60);
			hs.setAttribute("id", id);
			hs.setAttribute("pass", pass);
			hs.setAttribute("chathost",chathost);
			hs.setAttribute("host",host);
			hs.setAttribute("port", port);
			hs.setAttribute("smtpport", smtpport);
			hs.setAttribute("sieveport", sieveport);
			hs.setAttribute("active_folder", "INBOX");
			hs.setAttribute("active_contact",caldevUrl +"/caldav.php/"+id+"/contacts/");
			hs.setAttribute("default_contact",caldevUrl +"/caldav.php/"+id+"/contacts/");
			hs.setAttribute("default_calendar",caldevUrl +"/caldav.php/"+id+"/calendar/");
			hs.setAttribute("ldapurl", ldapurl);
			hs.setAttribute("ldapbase", base);
			hs.setAttribute("ldaplistbase", listbase);
			hs.setAttribute("XMailer", XMailer);
			hs.setAttribute("forcePasswordChange", "false");
			hs.setAttribute("caldevProId", caldevProId);
			hs.setAttribute("caldevUrl", caldevUrl);
			String cn="";
			String mailset="";
			String bgColor="";
			String replyTo="";
			String forcePassChange="";
			String signatureStatus="true";
			String chatService="true";
			String signature="";
			 DirContext ctx=getConnection(ldapurl,id,pass,base);
		 	 String entry="mail="+id+","+base;
		 	 byte[] jpegBytes1=null;
		 	 try
		 	 {
		 	  Attributes testAttributes = ctx.getAttributes(entry);
		      Attribute ldapatt = testAttributes.get("jpegPhoto");
		      if(ldapatt != null)
		      {
		    	  jpegBytes1 = (byte[]) ldapatt.get();
		      }
		      ldapatt = testAttributes.get("cn");
		      if(ldapatt != null)
		      {
		    	  cn = ldapatt.get().toString();
		      }
		      ldapatt = testAttributes.get("accountSetting");
		      if(ldapatt != null)
		      {
		    	  mailset = ldapatt.get().toString();
		      }
		      ldapatt = testAttributes.get("bgColor");
		      if(ldapatt != null)
		      {
		    	  bgColor = ldapatt.get().toString();
		      }
		      ldapatt = testAttributes.get("replyTo");
		      if(ldapatt != null)
		      {
		    	  replyTo = ldapatt.get().toString();
		      }
		      ldapatt = testAttributes.get("forcePasswordChange");
		      if(ldapatt != null)
		      {
		    	  forcePassChange = ldapatt.get().toString();
		      }
		      ldapatt = testAttributes.get("signatureStatus");
		      if(ldapatt != null)
		      {
		    	  signatureStatus = ldapatt.get().toString();
		      }
		      ldapatt = testAttributes.get("signature");
		      if(ldapatt != null)
		      {
		    	  signature = ldapatt.get().toString();
		      }
		      
		 	 }
		 	 catch(Exception e)
		 	 {
		 		 e.printStackTrace();
		 	 }
		 	closeConn(ctx);
		 	hs.setAttribute("img_myurl", jpegBytes1);
		 	hs.setAttribute("fname", cn);
		 	hs.setAttribute("mailReplyTo", replyTo);
		 	 hs.setAttribute("signatureStatus", signatureStatus);
			 hs.setAttribute("signature", signature);
		
		 	hs.setAttribute("previewPane", "Vertical view");
		 	hs.setAttribute("composePage", "Full View");
		 	hs.setAttribute("bccField", "No");
			 hs.setAttribute("limitMail", "15");
			 hs.setAttribute("limitContact", "20");
			 hs.setAttribute("generalSettingNotification", "New mail notifications on");
			 hs.setAttribute("chatService", chatService);
			 hs.setAttribute("signatureInReply", "true");
			
			
			 if(mailset!=null && !mailset.equals(""))
		        {
				 mailset=mailset.replace("\"", "");
				 mailset=mailset.replace("[", "");
				 mailset=mailset.replace("{", "");
				 mailset=mailset.replace("}]", "");
		         String 	arr_set[]=mailset.split("},");
		       
		        	for(int i=0;i< arr_set.length;i++)
		        	{
		        String new_arr[]=arr_set[i].split(",");
		        String arr_nm[]=new_arr[0].split(":");
		        String arr_val[]=new_arr[1].split(":");
		        String arrnm=arr_nm[1];
		        String arrval=arr_val[1];
		       
		        if(arrnm.equalsIgnoreCase("limitMail"))
		        {
		        	hs.setAttribute("limitMail", arrval);
		        }
		        else if(arrnm.equalsIgnoreCase("limitContact"))
		        {
		        	hs.setAttribute("limitContact", arrval);
		        }
		        else if(arrnm.equalsIgnoreCase("generalSettingImages"))
		        {
		        	hs.setAttribute("generalSettingImages", arrval);
		        }
		        else if(arrnm.equalsIgnoreCase("generalSettingReply"))
		        {
		        	hs.setAttribute("generalSettingReply", arrval);
		        }
		        else if(arrnm.equalsIgnoreCase("generalSettingNotification"))
		        {
		        	hs.setAttribute("generalSettingNotification", arrval);
		        }
		        else if(arrnm.equalsIgnoreCase("generalSettingOutgoing"))
		        {
		        	hs.setAttribute("generalSettingOutgoing", arrval);
		        }
		        else if(arrnm.equalsIgnoreCase("previewPane"))
		        {
		        	if(arrval!=null && arrval.length()>0)
		        	{
		        	hs.setAttribute("previewPane", arrval);
		        	}
		        }
		        else if(arrnm.equalsIgnoreCase("composePage"))
		        {
		        	if(arrval!=null && arrval.length()>0)
		        	{
		        	hs.setAttribute("composePage", arrval);
		        	}
		        }
		        else if(arrnm.equalsIgnoreCase("bccField"))
		        {
		        	if(arrval!=null && arrval.length()>0)
		        	{
		        	hs.setAttribute("bccField", arrval);
		        	}
		        }
		        else if(arrnm.equalsIgnoreCase("chatService"))
		        {
		        	if(arrval!=null && arrval.length()>0)
		        	{
		        	hs.setAttribute("chatService", arrval);
		        	chatService=arrval;
		        	}
		        }
		        else if(arrnm.equalsIgnoreCase("signatureInReply"))
		        {
		        	if(arrval!=null && arrval.length()>0)
		        	{
		        	hs.setAttribute("signatureInReply", arrval);
		        	}
		        }
		       
		        
		        	
		        }
		       
		       
		        
		        }
			
			 
			
			if(bgColor==null || bgColor.length()==0)
			{
				//bgColor="#03B3B2";
				bgColor="#326a3e";
			}
			 
			 hs.setAttribute("bgColor", bgColor);
			
			if(forcePassChange!=null && forcePassChange.length()>0)
			{
				if(forcePassChange.equalsIgnoreCase("false"))
				{
					
						String		xmppDomain=id.substring(id.indexOf("@")+1);
						scriptSessList.listenScriptSession();
						XmppChatClass xmppChatClass=new XmppChatClass();
					if(chatService.equalsIgnoreCase("true"))
					{
					//TODO: SEPERATE THE CONFIGURATION
					
					try
					{
					xmppChatClass.createConnection(chathost,xmppDomain, packetReplyTimeout, request);
					xmppChatClass.registerListeners(chatImageFolder);
					//xmppChatClass.performLogin(loginUser.getUserid(), loginUser.getPassword(), onlineStatus);
					xmppChatClass.performLogin(id, pass, onlineStatus);
					
					request.getSession().setAttribute("xmppChatClass", xmppChatClass);
					hs.setAttribute("chatStatus",true);
					hs.setAttribute("chatMode","online");
					hs.setAttribute("chatConfigStatus",true);
					//map.addAttribute("imageurl", chatImageFolder);
					}
					catch(Exception ex)
					{
						hs.setAttribute("chatStatus",false);
						hs.setAttribute("chatConfigStatus",false);
					}
					}
					else
					{
						hs.setAttribute("xmppChatClass", xmppChatClass);
						hs.setAttribute("chatStatus",false);
						hs.setAttribute("chatConfigStatus",false);
					}
					try
					{
						/*JedisPool jedisPool=Connections.connectionJedis(redisHost, redisPort);
						hs.setAttribute("jedisPool", jedisPool);*/
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					 
					new IdleMail().inboxIdle(hs,host, port, id, pass);
					/*try
					{
						Loginlog ll=new Loginlog();
						ll.setUserid(id);
						String ipAddress=null;
						String getWay = request.getHeader("VIA");   // Gateway
						ipAddress = request.getHeader("X-FORWARDED-FOR");   // proxy
						if(ipAddress==null)
						{
						    ipAddress = request.getRemoteAddr();
						}
						System.out.println("IP Address: "+ipAddress);
						ll.setIp(ipAddress);
						ll.setLogindate(new Date());
						int llsno=loginlogDao.addUpdateRow(0, ll);
						hs.setAttribute("llsno", llsno);
					}
					catch(Exception eee)
					{
						eee.printStackTrace();
					}*/
					return "redirect:/inbox";
				}
				else
				{
					hs.setAttribute("forcePasswordChange", "true");
					return "redirect:/firstinbox";
				}
			}
			else
			{
				//hs.setAttribute("forcePasswordChange", "true");
				//return "redirect:/firstinbox";
				new IdleMail().inboxIdle( hs,host, port, id, pass);
				/*try
				{
					Loginlog ll=new Loginlog();
					ll.setUserid(id);
					String ipAddress=null;
					String getWay = request.getHeader("VIA");   // Gateway
					ipAddress = request.getHeader("X-FORWARDED-FOR");   // proxy
					if(ipAddress==null)
					{
					    ipAddress = request.getRemoteAddr();
					}
					System.out.println("IP Address: "+ipAddress);
					ll.setIp(ipAddress);
					ll.setLogindate(new Date());
					int llsno=loginlogDao.addUpdateRow(0, ll);
					hs.setAttribute("llsno", llsno);
				}
				catch(Exception eee)
				{
					eee.printStackTrace();
				}*/
				return "redirect:/inbox";
			}
		}
		else if(flag==0)
		{
			return "redirect:/login?stat=auth";
		}
		else if(flag==2)
		{
			return "redirect:/login?stat=conn";
		}
		return "redirect:/login?stat=failed";
		 
	}
	
	
	@RequestMapping(value = "/dologin", method = RequestMethod.GET)
	public String dologinget(ModelMap map,  HttpServletRequest request, HttpServletResponse response) 
	{
try
{
		HttpSession hs=request.getSession();
		
	      XMPPConnection xmppChatClass=(XMPPConnection)hs.getAttribute("xmppConnection");
		if(xmppChatClass!=null){
			try {
				xmppChatClass.disconnect();
			} catch (NotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try
		{
			if(hs!=null)
			{
				if(hs.getAttribute("sieveclient")!=null && hs.getAttribute("sieveManageResponse")!=null)
				{
				 ManageSieveClient client=(ManageSieveClient)hs.getAttribute("sieveclient");
				 ManageSieveResponse    resp= (ManageSieveResponse) hs.getAttribute("sieveManageResponse");
				 ConnectAndListScripts.closeConnection(client,resp);
				}
			}
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		
		Store store=(Store)hs.getAttribute("idleStore");
		IMAPFolder folder=(IMAPFolder)hs.getAttribute("idleFolder");
		try
		{
			folder.close(true);
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		try
		{
			store.close();
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		
		
		
		hs.invalidate();
	
		 response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	     response.setHeader("Pragma","no-cache");
	     response.setDateHeader("Expires", 0);
}
catch(Exception eee){eee.printStackTrace();}
		
	      return "redirect:/login";
	}
}
