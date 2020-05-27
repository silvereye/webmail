package webmail.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.example.Connections;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import webmail.bean.MailAccSetting;
import webmail.bean.TextDecode;
import webmail.idle.MailVanished;
import webmail.webservice.client.WebmailClient;
import webmail.wsdl.GetLdapFNameResponse;
import webmail.wsdl.GetMailInboxDescResponse;
import webmail.wsdl.GetMailInboxResponse;
import webmail.wsdl.GetWebmailFolderResponse;
import webmail.wsdl.GetWebmailSubFolderResponse;
import webmail.wsdl.GetWebmailUnreadMailCountResponse;
import webmail.wsdl.Inbox;
import webmail.wsdl.InboxDesc;
import webmail.wsdl.InboxListReturn;

import java.util.List;
import java.util.Properties;

@Controller
public class InboxController {

	@Autowired
	private WebmailClient webmailClient;
	
	
	
	@RequestMapping(value = "/webmailMoveToInbox", method = RequestMethod.GET)
	@ResponseBody
	public String webmailMoveToInbox(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	String res="true";
	String uids=request.getParameter("uid");
	HttpSession hs=request.getSession();
	if(hs==null || hs.getAttribute("id")==null)
		return "<$expire$>";
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	String port=(String)hs.getAttribute("port");
	String arr[]=uids.split("-");
	long uidarr[]=new long[arr.length];
	for(int i=0;i< arr.length;i++)
	{
		uidarr[i]=Long.parseLong(arr[i].trim());
	}
	
	IMAPFolder folder = null;
    Store store = null;
    Flag flag = null;
    try 
    {
    	store=Connections.imapConnectionNP(host, port, id, pass);
		IMAPStore imapStore = (IMAPStore) store; 
     folder = (IMAPFolder) store.getFolder("Trash"); //This works for both email account
	
  UIDFolder uf = (UIDFolder)folder;
  if (!folder.exists()) 
  {
	  System.exit(0);
  }
  folder.open(Folder.READ_WRITE);
  Message[] msg =uf.getMessagesByUID(uidarr);
 // int umsg= folder.getUnreadMessageCount();

  Folder dfolder = store.getFolder("Inbox");
  if (!dfolder.exists())
  {
	  System.out.print("<br>Dfolder not found");
  }
  folder.copyMessages(msg, dfolder);

  for (int i = msg.length-1;  i>=0; i--)
  {

	  msg[i].setFlag(Flags.Flag.DELETED, true);

  }

folder.close(true);
store.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    res="false";
    }
	return res;
	}
	
	
	
	
	@RequestMapping(value = "/removeWebmailTag", method = RequestMethod.GET)
	@ResponseBody
	public String removeWebmailTag(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	String res="true";
	HttpSession hs=request.getSession();
	if(hs==null || hs.getAttribute("id")==null)
		return "<$expire$>";
	String uid= request.getParameter("uid");
	String tagnm= request.getParameter("tagnm");
	//HttpSession hs=request.getSession();
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	String port=(String)hs.getAttribute("port");
	String foldername=(String)hs.getAttribute("active_folder");
	//System.out.println("#################tagnm="+tagnm);
	IMAPFolder folder = null;
    Store store = null;
    Flag flag = null;
    try 
    {
    	store=Connections.imapConnectionNP(host, port, id, pass);
		IMAPStore imapStore = (IMAPStore) store; 
        folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account
	
        UIDFolder uf = (UIDFolder)folder;
        if (!folder.exists()) {
        	// inboxlist.setGetInboxByUid(inb);
        	// return   inboxlist;        
        }
        folder.open(Folder.READ_WRITE);
	
            long [] arr={Long.parseLong(uid)};
            Message[] msg =folder.getMessagesByUID(arr);
            for (int i = 0; i< msg.length; i++)
            {
            	 Flags processedFlag = new Flags(tagnm);
            	 msg[i].setFlags(processedFlag, false);
            	
	 
            }
	
            store.close();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	res="false";
        }
        finally
        {
        	if(store != null)
        	{
        		 try {
					store.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
	
	
	
	return res;
	}
	
	@RequestMapping(value = "/setWebmailTag", method = RequestMethod.GET)
	@ResponseBody
	public String setWebmailTag(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String uids= request.getParameter("uids");
		String tagsnm= request.getParameter("tagsnm");
		String arr_uid[]=uids.split("-");
		//System.out.println("^^^^^^^^^^^ uids="+uids+"----------flag="+tagsnm);
	//	HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String foldername=(String)hs.getAttribute("active_folder");
		
		IMAPFolder folder = null;
        Store store = null;
        Flag flag = null;
        try 
        {
        	store=Connections.imapConnectionNP(host, port, id, pass);
			IMAPStore imapStore = (IMAPStore) store; 
            folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account
		
            UIDFolder uf = (UIDFolder)folder;
            if (!folder.exists()) {
            	// inboxlist.setGetInboxByUid(inb);
            	// return   inboxlist;        
            }
            folder.open(Folder.READ_WRITE);
            
            tagsnm=tagsnm.replaceAll(" ", "_");
            
		for(int k=0;k<arr_uid.length;k++)
		{
			//int uid=Integer.parseInt(arr_uid[k]);
			
	            long [] arr={Long.parseLong(arr_uid[k])};
	            Message[] msg =folder.getMessagesByUID(arr);
	            for (int i = 0; i< msg.length; i++)
	            {
	            	String tagnm[]=tagsnm.split("~");
	            	for(int j=0;j<tagnm.length;j++)
	            	{
	            	// System.out.println("!!!!!!!!!!!!!!!!uid="+arr_uid[k]+"------ tag="+tagnm[j]);
	            	 Flags processedFlag = new Flags(tagnm[j]);
	            	 msg[i].setFlags(processedFlag, true);
	            	}
		 
	            }
		}
	            store.close();
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
	        finally
	        {
	        	if(store != null)
	        	{
	        		 try {
						store.close();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        }
	
		
		return "true";
	}
	
	

	@RequestMapping(value = "/getMailInbox", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public synchronized  String listWebmailInbox(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		String res="";//<script src='js/event.js' type='text/javascript' language='javascript' ></script> ";
		String start= request.getParameter("start");
		String end= request.getParameter("end");
		String folder= request.getParameter("folder");
		//String pview=request.getParameter("pview");
	
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
		{
			res="<$expire$>";
			return res;
		}
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
		/*String redisHost=(String)hs.getAttribute("redisHost");
		int redisPort=(int)hs.getAttribute("redisPort");*/
		hs.setAttribute("active_folder", folder);
		
		
		
		String pview=hs.getAttribute("previewPane").toString();
	/*	JedisPool pool = null;
		pool=(JedisPool)hs.getAttribute("JedisPool");
		if(pool==null)
		{
			pool=Connections.connectionJedis(redisHost, redisPort);
		}
		
		if(pool!=null)
		{
			
			 Jedis jedis = pool.getResource();  
			// List<webmail.wsdlnew.Inbox>  inblist= (List<webmail.wsdlnew.Inbox>)jedis.;
			 
			 
		}*/
		
		//String pview=MailAccSetting.previewPane;
		
		//System.out.println("inbox***********************folder="+folder);
		
		webmail.wsdlnew.GetMailInboxResponse wfre=webmailClient.getInboxMailRequest(host, port, id, pass, start, end, folder);
		boolean mcount= wfre.getGetInboxByMailLimit().isMailCount();
		if(!mcount)
		{
			res="<div class='inbox_empty' >No messages found in "+folder+".   <div class='clear'></div>  </div>";
			return res;
		}
		else
		{
		List<webmail.wsdlnew.Inbox>  inblist=wfre.getGetInboxByMailLimit().getInboxListReturn().getInboxMailList();
		
		
		
		 GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "labelSetting");
   		 String labset=ldapres.getGetFName();
		String uidlst="";
		for(webmail.wsdlnew.Inbox inb : inblist)
		{
			String uid="";
			String fromid="";
			String sub="";
			String attch="";
			String date="";
			String dt_title="";
			String seen_class=""; 
			String rc_bk="";
			String ckboxnm="";
			String gen_to="";
			String gen_cc="";
			String gen_bcc="";
			String gen_from="";
			String pri="";
			String mailDraft="false";
			String msgno="";
			
			msgno=inb.getMsgNo();
			pri=inb.getMailPriority();
	
			
			
				if(inb.getSubject()!=null)
					sub=TextDecode.decodeUTF8Text(inb.getSubject());
				else
					sub="";
			
	
				if(inb.getToId()!=null)
				{
				gen_to=TextDecode.decodeUTF8Text(inb.getToId());
				}
				else
				{
					gen_to="";
				}
			
			
		
				if(inb.getCCId()!=null)
				{
				gen_cc=TextDecode.decodeUTF8Text(inb.getCCId());
				}
				else
				{
					gen_cc="";
				}
			
			
		
				if(inb.getBCCId()!=null)
				{
				gen_bcc=TextDecode.decodeUTF8Text(inb.getBCCId());
				}
				else
				{
					gen_bcc="";
				}
			
			
		
				if(inb.getFromId()!=null)
				{
				gen_from=TextDecode.decodeUTF8Text(inb.getFromId());
				}
				else
				{
					gen_from="";
				}
			
			
			if(inb.isMailSeen()== false)
			{
				seen_class="unread_message";
				ckboxnm="unseen";
				rc_bk="unread_rc";
			}
			else
			{
				ckboxnm="seen";
			}
			
			String flag_class="small_image";
			String left_flg_class="new_flag";
			if(inb.isMailFlage()== true)
			{
				flag_class="small_image_flag";
				left_flg_class="new_flag_color";
				ckboxnm=ckboxnm+"-stared";
			}
			else
			{
				ckboxnm=ckboxnm+"-unstared";
			}
			
			String css_class1="msgno_"+msgno+" "+rc_bk+" row_content";
			String css_class2="row_first";
			String css_class3="row_left";
			if(pview.equalsIgnoreCase("Vertical View"))
			{
				css_class1="msgno_"+msgno+" "+rc_bk+" row_content left_view_mess";
				css_class2="row_first mail_de_op";
				css_class3="row_left left_view_con";
			}
			
			String tags=inb.getMailTag();
			
			
			uid=inb.getUid();
			if(uidlst.length()>0)
			{
				uidlst+="-"+uid;
			}
			else
			{
				uidlst=uid;
			}
			if(folder.equalsIgnoreCase("Sent") || folder.equalsIgnoreCase("Drafts") )
			{
				
					if(inb.getToId()!=null)
						fromid=TextDecode.decodeUTF8Text(inb.getToId());
					else
						fromid="";
				
			}
			else
			{
			
					if(inb.getFromId()!=null)
						fromid=TextDecode.decodeUTF8Text(inb.getFromId());
					else
						fromid="";
				
			}
			
			if(sub==null || sub.equals(""))
			{
				sub="(no Subject)";
			}
			attch=inb.getAttachment();
			date=inb.getSendDtae();
			dt_title=inb.getSendDtaeTitle();
			String mailcnt=inb.getMailContent();
			String fwd_to=HtmlUtils.htmlEscape(gen_to);
			
			
			String fwd_from=HtmlUtils.htmlEscape(gen_from);
			
	
			String fwd_cnt="---------- Forwarded message ----------<br>";
			fwd_cnt+="From: "+fwd_from+"<br>";
			fwd_cnt+="To: "+fwd_to+"<br>";
			fwd_cnt+="Date: "+dt_title+"<br>";
			fwd_cnt+="Subject: "+sub+"<br>";
			String s_to=HtmlUtils.htmlEscape(gen_to);
			String send_to=HtmlUtils.htmlEscape(gen_from);
			
			
			
			
			String send_cc="";
			String send_sub=sub;
			String send_cnt=inb.getMailContentOtr();
			String fst_line="On "+dt_title+","+send_to+" wrote:";
			
			if(s_to!= null && ! s_to.equals(""))
			{
		    String toarr[]=s_to.split(",");
			for(int i=0;i<toarr.length;i++)
			{
								
				if(!toarr[i].contains(id))
				{
					if(send_cc.equals(""))
					{
						send_cc=toarr[i];
					}
					else
					{
						send_cc+=toarr[i];
					}
				}
			}
			}
				
		//	System.out.println();
			mailcnt = mailcnt.replaceAll("\\n", "");
			mailcnt = mailcnt.replaceAll("\\r", "");
			mailcnt = mailcnt.replaceAll("\\<.*?\\>", "");
			
			
			
			//mailcnt="<pre>"+mailcnt+"</pre>";
			res+="<div  id='div_"+uid+"' class='"+css_class1+"' style='cursor: pointer;'    ><div class='"+css_class2+"'><div id='div_unread_"+uid+"' class='"+css_class3+" "+seen_class+" '>";
			res+="<div class='flag_first'><div class='row_check_box'>";
			res+=" <input type='checkbox' id='chk_id_"+uid+"' name='"+ckboxnm+"' value='"+uid+"'   class='mail_checked'>";
			res+=" </div> <a style='cursor: pointer;' id='a_div_flag_"+uid+"' class='npflag_mail'><div id='div_flag_"+uid+"' class='"+flag_class+"' >&#9733;</div> </a>";
			res+=" </div>";
			/*if(folder.equalsIgnoreCase("Trash"))
			{
			res+=" <div  class='form_first'> <a title='Move To Inbox' onclick='moveToInbox("+uid+")'><img style='opacity: 0.7;' src='images/move_inbox.png' /></a><a > "+fromid+" </a></div>";
			}
			else
			{*/
			String titfromid=fromid;
			if(titfromid.indexOf("'")>=0 || titfromid.indexOf("\"")>=0)
			{
				titfromid=titfromid.replaceAll("'", "");
				titfromid=titfromid.replaceAll("\"", "");
			}
			if(titfromid.indexOf("<")>=0)
			{
				String titarr[]=titfromid.split("<");
				titfromid=titarr[1];
				titfromid=titfromid.replaceAll(">", "");
			}
			res+="<div title='"+titfromid+"' class='form_first'>  "+fromid+" </div>";
			/*}*/
			
			res+="  <div  class='subject_first'>";
			
			if(pri!=null && !pri.equals(""))
			{
			if(pri.equalsIgnoreCase("Highest"))	
			{
				res+="<div title='This message was sent with high importance' class='imp_pri'><img src='images/imp_high.png'/></div>";	
			}
			else if(pri.equalsIgnoreCase("Lowest"))	
			{
				res+="<div title='This message was sent with low importance' class='imp_pri'><img src='images/imp_low.png'/></div>";		
			}
			
			}
			
			String idfr="repfor_"+uid;
			if(inb.isMailReplied() && inb.isMailForwared())
			{
				res+="<div id='"+idfr+"' title='This mail has been replied and forwarded' class='rep_for'><img src='images/ml_rep_for.png'></div>";		
			}
			else if(inb.isMailForwared())
			{
				res+="<div id='"+idfr+"' title='This mail has been forwarded' class='rep_for'><img src='images/ml_for.png'></div>";		
			}
			else if(inb.isMailReplied())
			{
				res+="<div id='"+idfr+"' title='This mail has been replied' class='rep_for'><img src='images/ml_rep.png'></div>";		
			}
			else
			{
				res+="<div id='"+idfr+"' title='none' class='rep_for' style='display:none;'></div>";	
			}
			
			
			if(inb.isMailDraft())
			{
				mailDraft="true";
				res+="<div title='Draft mail' class='draft_img'>Draft:</div>";		
			}
			res+= ""+  sub;
			String mailDraftid="mailDraft_"+uid;
			res+="<input type='hidden' id='"+mailDraftid+"' value='"+mailDraft+"' />";
			
			res+=" </div></div>";
			res+="<div  class='right_row_first'>";
			res+="<div  class='row_date' style='cursor: pointer;' title='"+dt_title+"'>"+date+"</div>";
		
			
		/*	if(pview==null || pview.equalsIgnoreCase("Vertical view"))
			{
				res+="<div  class='row_date' style='cursor: pointer;' title='"+dt_title+"'>"+date+"</div>";
			}
			else 
			{
				res+="<div  class='row_date' style='cursor: pointer;' title='"+dt_title+"'>"+dt_title+"</div>";
			}*/
			
			res+="<div  class='row_attach'>";
			if(attch.equalsIgnoreCase("yes"))
			{
				res+="	<img src='images/attachment.png'>";
			}
				
			res+="	</div> <a style='cursor: pointer;' ><div id='maildel_"+uid+"' class='row_delete'></div></a>";
			res+="   </div> </div>";
			res+="   <div  class='message'> "+mailcnt+" </div><a style='cursor: pointer;' id='a_left_div_flag_"+uid+"' class='npflag_mail'><div class='"+left_flg_class+"' id='left_div_flag_"+uid+"'  >&#9733;</div></a>";
			res+=" <div  class='row_first tag_row_new'><div  class='inbox_tag'>";
			
			if(tags!=null && !(tags.equals("")))
			{
				
				
				String tagnm[]=tags.split("~");
            	for(int j=0;j<tagnm.length;j++)
            	{
            		boolean stts=false;
            		String tagcss="";
            		String tag="";
            		String tagback="";
            		if( tagnm[j].equalsIgnoreCase("$label1"))
            		{
            			tagcss="Important";
                		tag="Important";
                		tagback="#FA6855";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label2"))
            		{
            			tagcss="Work";
                		tag="Work";
                		tagback="#FC9449";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label3"))
            		{
            			tagcss="Personal";
                		tag="Personal";
                		tagback="#98C95D";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label4"))
            		{
            			tagcss="To_Do";
                		tag="To Do";
                		tagback="#486BF7";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label5"))
            		{
            			tagcss="Later";
                		tag="Later";
                		tagback="#BD48F7";
                		stts=true;
            		}
            		else 
            		{
            			tagback="gray";
            			
            			if(labset!=null && labset.length()>0)
                            {
            				if(labset.indexOf(tagnm[j]) >=0)
            				{
            				
                             String tagarr[]=labset.split("~");
                             for(int i=0;i< tagarr.length; i++)
                                {
                                 String tarr[]=tagarr[i].split("#");
                                 if(tarr[0].equalsIgnoreCase(tagnm[j]))
                                 {
                                	 tagback="#"+tarr[1];
                                	 stts=true;
                                	 break;
                                 }
                                }
                            }
                            }
            			
            			tag= tagnm[j].replace("_", " ");
                		tagcss= tagnm[j];
                		
            		}
            		if(stts)
            		{
            		String tagid=	"list_"+uid+"~"+tagnm[j];
            		res+="	<div id='"+tagid+"' class='in_tag "+tagcss+"' style='display: block; background: "+tagback+";'><span>"+tag+"</span><div class='close_tag'>x</div></div>";
            		}
            		}
			}
			
			res+="</div><div class='clear'></div> </div><div class='clear'></div>  </div>";
		}
		
		/*if(inblist.size()==0)
		{
			
			res+="<div class='inbox_empty' >No messages found in "+folder+".   <div class='clear'></div>  </div>";
			
		}*/
		
		//MailVanished.inboxVanished(host, port, id, pass, uidlst);
		
		return res;
		}
		
	}
	
	
	
	@RequestMapping(value = "/getMailInboxRAjaxDel", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public synchronized  String getMailInboxRAjaxDel(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		String res="";//<script src='js/event.js' type='text/javascript' language='javascript' ></script> ";
		String start= request.getParameter("start");
		String end= request.getParameter("end");
		String folder= request.getParameter("folder");
		//String pview=request.getParameter("pview");
		JSONObject obj = new JSONObject();
		String uuid="";
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
		{
			res="<$expire$>";
			obj.put("uuid", uuid);
			obj.put("res", res);
			return obj.toJSONString();
			
		}
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
		/*String redisHost=(String)hs.getAttribute("redisHost");
		int redisPort=(int)hs.getAttribute("redisPort");*/
		hs.setAttribute("active_folder", folder);
		
		
		
		String pview=hs.getAttribute("previewPane").toString();
	/*	JedisPool pool = null;
		pool=(JedisPool)hs.getAttribute("JedisPool");
		if(pool==null)
		{
			pool=Connections.connectionJedis(redisHost, redisPort);
		}
		
		if(pool!=null)
		{
			
			 Jedis jedis = pool.getResource();  
			// List<webmail.wsdlnew.Inbox>  inblist= (List<webmail.wsdlnew.Inbox>)jedis.;
			 
			 
		}*/
		
		//String pview=MailAccSetting.previewPane;
		
		//System.out.println("inbox***********************folder="+folder);
		
		webmail.wsdlnew.GetMailInboxResponse wfre=webmailClient.getInboxMailRequest(host, port, id, pass, start, end, folder);
		boolean mcount= wfre.getGetInboxByMailLimit().isMailCount();
		if(!mcount)
		{
			res="<div class='inbox_empty' >No messages found in "+folder+".   <div class='clear'></div>  </div>";
			obj.put("uuid", uuid);
			obj.put("res", res);
			return obj.toJSONString();
		}
		else
		{
		List<webmail.wsdlnew.Inbox>  inblist=wfre.getGetInboxByMailLimit().getInboxListReturn().getInboxMailList();
		
		
		
		 GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "labelSetting");
   		 String labset=ldapres.getGetFName();
		String uidlst="";
		for(webmail.wsdlnew.Inbox inb : inblist)
		{
			String uid="";
			String fromid="";
			String sub="";
			String attch="";
			String date="";
			String dt_title="";
			String seen_class=""; 
			String rc_bk="";
			String ckboxnm="";
			String gen_to="";
			String gen_cc="";
			String gen_bcc="";
			String gen_from="";
			String pri="";
			String mailDraft="false";
			String msgno="";
			
			msgno=inb.getMsgNo();
			pri=inb.getMailPriority();
	
			
			
				if(inb.getSubject()!=null)
					sub=TextDecode.decodeUTF8Text(inb.getSubject());
				else
					sub="";
			
	
				if(inb.getToId()!=null)
				{
				gen_to=TextDecode.decodeUTF8Text(inb.getToId());
				}
				else
				{
					gen_to="";
				}
			
			
		
				if(inb.getCCId()!=null)
				{
				gen_cc=TextDecode.decodeUTF8Text(inb.getCCId());
				}
				else
				{
					gen_cc="";
				}
			
			
		
				if(inb.getBCCId()!=null)
				{
				gen_bcc=TextDecode.decodeUTF8Text(inb.getBCCId());
				}
				else
				{
					gen_bcc="";
				}
			
			
		
				if(inb.getFromId()!=null)
				{
				gen_from=TextDecode.decodeUTF8Text(inb.getFromId());
				}
				else
				{
					gen_from="";
				}
			
			
			if(inb.isMailSeen()== false)
			{
				seen_class="unread_message";
				ckboxnm="unseen";
				rc_bk="unread_rc";
			}
			else
			{
				ckboxnm="seen";
			}
			
			String flag_class="small_image";
			String left_flg_class="new_flag";
			if(inb.isMailFlage()== true)
			{
				flag_class="small_image_flag";
				left_flg_class="new_flag_color";
				ckboxnm=ckboxnm+"-stared";
			}
			else
			{
				ckboxnm=ckboxnm+"-unstared";
			}
			
			String css_class1="msgno_"+msgno+" "+rc_bk+" row_content";
			String css_class2="row_first";
			String css_class3="row_left";
			if(pview.equalsIgnoreCase("Vertical View"))
			{
				css_class1="msgno_"+msgno+" "+rc_bk+" row_content left_view_mess";
				css_class2="row_first mail_de_op";
				css_class3="row_left left_view_con";
			}
			
			String tags=inb.getMailTag();
			
			
			uid=inb.getUid();
			uuid=uid;
			if(uidlst.length()>0)
			{
				uidlst+="-"+uid;
			}
			else
			{
				uidlst=uid;
			}
			if(folder.equalsIgnoreCase("Sent") || folder.equalsIgnoreCase("Drafts") )
			{
				
					if(inb.getToId()!=null)
						fromid=TextDecode.decodeUTF8Text(inb.getToId());
					else
						fromid="";
				
			}
			else
			{
			
					if(inb.getFromId()!=null)
						fromid=TextDecode.decodeUTF8Text(inb.getFromId());
					else
						fromid="";
				
			}
			
			if(sub==null || sub.equals(""))
			{
				sub="(no Subject)";
			}
			attch=inb.getAttachment();
			date=inb.getSendDtae();
			dt_title=inb.getSendDtaeTitle();
			String mailcnt=inb.getMailContent();
			String fwd_to=HtmlUtils.htmlEscape(gen_to);
			
			
			String fwd_from=HtmlUtils.htmlEscape(gen_from);
			
	
			String fwd_cnt="---------- Forwarded message ----------<br>";
			fwd_cnt+="From: "+fwd_from+"<br>";
			fwd_cnt+="To: "+fwd_to+"<br>";
			fwd_cnt+="Date: "+dt_title+"<br>";
			fwd_cnt+="Subject: "+sub+"<br>";
			String s_to=HtmlUtils.htmlEscape(gen_to);
			String send_to=HtmlUtils.htmlEscape(gen_from);
			
			
			
			
			String send_cc="";
			String send_sub=sub;
			String send_cnt=inb.getMailContentOtr();
			String fst_line="On "+dt_title+","+send_to+" wrote:";
			
			if(s_to!= null && ! s_to.equals(""))
			{
		    String toarr[]=s_to.split(",");
			for(int i=0;i<toarr.length;i++)
			{
								
				if(!toarr[i].contains(id))
				{
					if(send_cc.equals(""))
					{
						send_cc=toarr[i];
					}
					else
					{
						send_cc+=toarr[i];
					}
				}
			}
			}
				
		//	System.out.println();
			mailcnt = mailcnt.replaceAll("\\n", "");
			mailcnt = mailcnt.replaceAll("\\r", "");
			mailcnt = mailcnt.replaceAll("\\<.*?\\>", "");
			
			
			
			//mailcnt="<pre>"+mailcnt+"</pre>";
			res+="<div  id='div_"+uid+"' class='"+css_class1+"' style='cursor: pointer;'    ><div class='"+css_class2+"'><div id='div_unread_"+uid+"' class='"+css_class3+" "+seen_class+" '>";
			res+="<div class='flag_first'><div class='row_check_box'>";
			res+=" <input type='checkbox' id='chk_id_"+uid+"' name='"+ckboxnm+"' value='"+uid+"'   class='mail_checked'>";
			res+=" </div> <a style='cursor: pointer;' id='a_div_flag_"+uid+"' class='npflag_mail'><div id='div_flag_"+uid+"' class='"+flag_class+"' >&#9733;</div> </a>";
			res+=" </div>";
			/*if(folder.equalsIgnoreCase("Trash"))
			{
			res+=" <div  class='form_first'> <a title='Move To Inbox' onclick='moveToInbox("+uid+")'><img style='opacity: 0.7;' src='images/move_inbox.png' /></a><a > "+fromid+" </a></div>";
			}
			else
			{*/
			String titfromid=fromid;
			if(titfromid.indexOf("'")>=0 || titfromid.indexOf("\"")>=0)
			{
				titfromid=titfromid.replaceAll("'", "");
				titfromid=titfromid.replaceAll("\"", "");
			}
			if(titfromid.indexOf("<")>=0)
			{
				String titarr[]=titfromid.split("<");
				titfromid=titarr[1];
				titfromid=titfromid.replaceAll(">", "");
			}
			res+="<div title='"+titfromid+"' class='form_first'>  "+fromid+" </div>";
			/*}*/
			
			res+="  <div  class='subject_first'>";
			
			if(pri!=null && !pri.equals(""))
			{
			if(pri.equalsIgnoreCase("Highest"))	
			{
				res+="<div title='This message was sent with high importance' class='imp_pri'><img src='images/imp_high.png'/></div>";	
			}
			else if(pri.equalsIgnoreCase("Lowest"))	
			{
				res+="<div title='This message was sent with low importance' class='imp_pri'><img src='images/imp_low.png'/></div>";		
			}
			
			}
			
			
			
			if(inb.isMailDraft())
			{
				mailDraft="true";
				res+="<div title='Draft mail' class='draft_img'>Draft:</div>";		
			}
			res+= ""+  sub;
			String mailDraftid="mailDraft_"+uid;
			res+="<input type='hidden' id='"+mailDraftid+"' value='"+mailDraft+"' />";
			
			res+=" </div></div>";
			res+="<div  class='right_row_first'>";
			res+="<div  class='row_date' style='cursor: pointer;' title='"+dt_title+"'>"+date+"</div>";
		
			
		/*	if(pview==null || pview.equalsIgnoreCase("Vertical view"))
			{
				res+="<div  class='row_date' style='cursor: pointer;' title='"+dt_title+"'>"+date+"</div>";
			}
			else 
			{
				res+="<div  class='row_date' style='cursor: pointer;' title='"+dt_title+"'>"+dt_title+"</div>";
			}*/
			
			res+="<div  class='row_attach'>";
			if(attch.equalsIgnoreCase("yes"))
			{
				res+="	<img src='images/attachment.png'>";
			}
				
			res+="	</div> <a style='cursor: pointer;' ><div id='maildel_"+uid+"' class='row_delete'></div></a>";
			res+="   </div> </div>";
			res+="   <div  class='message'> "+mailcnt+" </div><a style='cursor: pointer;' id='a_left_div_flag_"+uid+"' class='npflag_mail'><div class='"+left_flg_class+"' id='left_div_flag_"+uid+"'  >&#9733;</div></a>";
			res+=" <div  class='row_first tag_row_new'><div  class='inbox_tag'>";
			
			if(tags!=null && !(tags.equals("")))
			{
				
				
				String tagnm[]=tags.split("~");
            	for(int j=0;j<tagnm.length;j++)
            	{
            		boolean stts=false;
            		String tagcss="";
            		String tag="";
            		String tagback="";
            		if( tagnm[j].equalsIgnoreCase("$label1"))
            		{
            			tagcss="Important";
                		tag="Important";
                		tagback="#FA6855";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label2"))
            		{
            			tagcss="Work";
                		tag="Work";
                		tagback="#FC9449";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label3"))
            		{
            			tagcss="Personal";
                		tag="Personal";
                		tagback="#98C95D";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label4"))
            		{
            			tagcss="To_Do";
                		tag="To Do";
                		tagback="#486BF7";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label5"))
            		{
            			tagcss="Later";
                		tag="Later";
                		tagback="#BD48F7";
                		stts=true;
            		}
            		else 
            		{
            			tagback="gray";
            			
            			if(labset!=null && labset.length()>0)
                            {
            				if(labset.indexOf(tagnm[j]) >=0)
            				{
            				
                             String tagarr[]=labset.split("~");
                             for(int i=0;i< tagarr.length; i++)
                                {
                                 String tarr[]=tagarr[i].split("#");
                                 if(tarr[0].equalsIgnoreCase(tagnm[j]))
                                 {
                                	 tagback="#"+tarr[1];
                                	 stts=true;
                                	 break;
                                 }
                                }
                            }
                            }
            			
            			tag= tagnm[j].replace("_", " ");
                		tagcss= tagnm[j];
                		
            		}
            		if(stts)
            		{
            		String tagid=	"list_"+uid+"~"+tagnm[j];
            		res+="	<div id='"+tagid+"' class='in_tag "+tagcss+"' style='display: block; background: "+tagback+";'><span>"+tag+"</span><div class='close_tag'>x</div></div>";
            		}
            		}
			}
			
			res+="</div><div class='clear'></div> </div><div class='clear'></div>  </div>";
		}
		
		/*if(inblist.size()==0)
		{
			
			res+="<div class='inbox_empty' >No messages found in "+folder+".   <div class='clear'></div>  </div>";
			
		}*/
		
		//MailVanished.inboxVanished(host, port, id, pass, uidlst);
		obj.put("uuid", uuid);
		obj.put("res", res);
		return obj.toJSONString();
		}
		
	}
	
	
	@RequestMapping(value = "/changeMailInbox", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public synchronized  String changeMailInbox(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		String res="";//<script src='js/event.js' type='text/javascript' language='javascript' ></script> ";
		String luid= request.getParameter("uid");
		String folder= request.getParameter("folder");
		//String pview=request.getParameter("pview");
	
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
		{
			res="<$expire$>";
			return res;
		}
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
		/*String redisHost=(String)hs.getAttribute("redisHost");
		int redisPort=(int)hs.getAttribute("redisPort");*/
		hs.setAttribute("active_folder", folder);
		
		
		
		String pview=hs.getAttribute("previewPane").toString();
	/*	JedisPool pool = null;
		pool=(JedisPool)hs.getAttribute("JedisPool");
		if(pool==null)
		{
			pool=Connections.connectionJedis(redisHost, redisPort);
		}
		
		if(pool!=null)
		{
			
			 Jedis jedis = pool.getResource();  
			// List<webmail.wsdlnew.Inbox>  inblist= (List<webmail.wsdlnew.Inbox>)jedis.;
			 
			 
		}*/
		
		//String pview=MailAccSetting.previewPane;
		
		//System.out.println("inbox***********************folder="+folder);
		
		webmail.wsdlnew.GetMailInboxResponse wfre=webmailClient.getInboxChangeMailRequest(host, port, id, pass, luid,  folder);
		boolean mcount=wfre.getGetInboxByMailLimit().isMailCount();
		if(!mcount)
		{
			res+="<div class='inbox_empty' >No messages found in "+folder+".   <div class='clear'></div>  </div>";
			return res;
		}
		else
		{
		
		List<webmail.wsdlnew.Inbox>  inblist=wfre.getGetInboxByMailLimit().getInboxListReturn().getInboxMailList();
		
		
		
		 GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "labelSetting");
   		 String labset=ldapres.getGetFName();
		String uidlst="";
		for(webmail.wsdlnew.Inbox inb : inblist)
		{
			String uid="";
			String fromid="";
			String sub="";
			String attch="";
			String date="";
			String dt_title="";
			String seen_class=""; 
			String ckboxnm="";
			String gen_to="";
			String gen_cc="";
			String gen_bcc="";
			String gen_from="";
			String pri="";
			String mailDraft="false";
			String msgno="";
			String rc_bk="";
			
			msgno=inb.getMsgNo();
			pri=inb.getMailPriority();
	
			

				if(inb.getSubject()!=null)
					sub=TextDecode.decodeUTF8Text(inb.getSubject());
				else
					sub="";
			

				if(inb.getToId()!=null)
				{
				gen_to=TextDecode.decodeUTF8Text(inb.getToId());
				}
				else
				{
					gen_to="";
				}
			
			
		
				if(inb.getCCId()!=null)
				{
				gen_cc=TextDecode.decodeUTF8Text(inb.getCCId());
				}
				else
				{
					gen_cc="";
				}
			
			
			
				if(inb.getBCCId()!=null)
				{
				gen_bcc=TextDecode.decodeUTF8Text(inb.getBCCId());
				}
				else
				{
					gen_bcc="";
				}
			
			
		
				if(inb.getFromId()!=null)
				{
				gen_from=TextDecode.decodeUTF8Text(inb.getFromId());
				}
				else
				{
					gen_from="";
				}
			
			
			if(inb.isMailSeen()== false)
			{
				seen_class="unread_message";
				ckboxnm="unseen";
				rc_bk="unread_rc";
			}
			else
			{
				ckboxnm="seen";
			}
			
			String flag_class="small_image";
			String left_flg_class="new_flag";
			if(inb.isMailFlage()== true)
			{
				flag_class="small_image_flag";
				left_flg_class="new_flag_color";
				ckboxnm=ckboxnm+"-stared";
			}
			else
			{
				ckboxnm=ckboxnm+"-unstared";
			}
			
			String css_class1="msgno_"+msgno+" "+rc_bk+" row_content";
			String css_class2="row_first";
			String css_class3="row_left";
			if(pview.equalsIgnoreCase("Vertical View"))
			{
				css_class1="msgno_"+msgno+" "+rc_bk+" row_content left_view_mess";
				css_class2="row_first mail_de_op";
				css_class3="row_left left_view_con";
			}
			
			String tags=inb.getMailTag();
			
			
			uid=inb.getUid();
			if(uidlst.length()>0)
			{
				uidlst+="-"+uid;
			}
			else
			{
				uidlst=uid;
			}
			if(folder.equalsIgnoreCase("Sent") || folder.equalsIgnoreCase("Drafts") )
			{
		
					if(inb.getToId()!=null)
						fromid=TextDecode.decodeUTF8Text(inb.getToId());
					else
						fromid="";
				
			}
			else
			{
			
					if(inb.getFromId()!=null)
						fromid=TextDecode.decodeUTF8Text(inb.getFromId());
					else
						fromid="";
				
			}
			
			if(sub==null || sub.equals(""))
			{
				sub="(no Subject)";
			}
			attch=inb.getAttachment();
			date=inb.getSendDtae();
			dt_title=inb.getSendDtaeTitle();
			String mailcnt=inb.getMailContent();
			String fwd_to=HtmlUtils.htmlEscape(gen_to);
			
			
			String fwd_from=HtmlUtils.htmlEscape(gen_from);
			
	
			String fwd_cnt="---------- Forwarded message ----------<br>";
			fwd_cnt+="From: "+fwd_from+"<br>";
			fwd_cnt+="To: "+fwd_to+"<br>";
			fwd_cnt+="Date: "+dt_title+"<br>";
			fwd_cnt+="Subject: "+sub+"<br>";
			String s_to=HtmlUtils.htmlEscape(gen_to);
			String send_to=HtmlUtils.htmlEscape(gen_from);
			
			
			
			
			String send_cc="";
			String send_sub=sub;
			String send_cnt=inb.getMailContentOtr();
			String fst_line="On "+dt_title+","+send_to+" wrote:";
			
			if(s_to!= null && ! s_to.equals(""))
			{
		    String toarr[]=s_to.split(",");
			for(int i=0;i<toarr.length;i++)
			{
								
				if(!toarr[i].contains(id))
				{
					if(send_cc.equals(""))
					{
						send_cc=toarr[i];
					}
					else
					{
						send_cc+=toarr[i];
					}
				}
			}
			}
				
		//	System.out.println();
			mailcnt = mailcnt.replaceAll("\\n", "");
			mailcnt = mailcnt.replaceAll("\\r", "");
			mailcnt = mailcnt.replaceAll("\\<.*?\\>", "");
			
			
			
			//mailcnt="<pre>"+mailcnt+"</pre>";
			res+="<div  id='div_"+uid+"' class='"+css_class1+"' style='cursor: pointer;'    ><div class='"+css_class2+"'><div id='div_unread_"+uid+"' class='"+css_class3+" "+seen_class+" '>";
			res+="<div class='flag_first'><div class='row_check_box'>";
			res+=" <input type='checkbox' id='chk_id_"+uid+"' name='"+ckboxnm+"' value='"+uid+"'   class='mail_checked'>";
			res+=" </div> <a style='cursor: pointer;' id='a_div_flag_"+uid+"' class='npflag_mail'><div id='div_flag_"+uid+"' class='"+flag_class+"' >&#9733;</div> </a>";
			res+=" </div>";
			/*if(folder.equalsIgnoreCase("Trash"))
			{
			res+=" <div  class='form_first'> <a title='Move To Inbox' onclick='moveToInbox("+uid+")'><img style='opacity: 0.7;' src='images/move_inbox.png' /></a><a > "+fromid+" </a></div>";
			}
			else
			{*/
			String titfromid=fromid;
			if(titfromid.indexOf("'")>=0 || titfromid.indexOf("\"")>=0)
			{
				titfromid=titfromid.replaceAll("'", "");
				titfromid=titfromid.replaceAll("\"", "");
			}
			if(titfromid.indexOf("<")>=0)
			{
				String titarr[]=titfromid.split("<");
				titfromid=titarr[1];
				titfromid=titfromid.replaceAll(">", "");
			}
			res+="<div title='"+titfromid+"'  class='form_first'> "+fromid+" </div>";
			/*}*/
			
			res+="  <div  class='subject_first'>";
			
			if(pri!=null && !pri.equals(""))
			{
			if(pri.equalsIgnoreCase("Highest"))	
			{
				res+="<div title='This message was sent with high importance' class='imp_pri'><img src='images/imp_high.png'/></div>";	
			}
			else if(pri.equalsIgnoreCase("Lowest"))	
			{
				res+="<div title='This message was sent with low importance' class='imp_pri'><img src='images/imp_low.png'/></div>";		
			}
			
			}
			
			
			
			if(inb.isMailDraft())
			{
				mailDraft="true";
				res+="<div title='Draft mail' class='draft_img'>Draft:</div>";		
			}
			res+= ""+  sub;
			String mailDraftid="mailDraft_"+uid;
			res+="<input type='hidden' id='"+mailDraftid+"' value='"+mailDraft+"' />";
			
			res+=" </div></div>";
			res+="<div  class='right_row_first'>";
			res+="<div  class='row_date' style='cursor: pointer;' title='"+dt_title+"'>"+date+"</div>";
			res+="<div  class='row_attach'>";
			if(attch.equalsIgnoreCase("yes"))
			{
				res+="	<img src='images/attachment.png'>";
			}
				
			res+="	</div> <a style='cursor: pointer;' ><div id='maildel_"+uid+"' class='row_delete'></div></a>";
			res+="   </div> </div>";
			res+="   <div  class='message'> "+mailcnt+" </div><a style='cursor: pointer;' id='a_left_div_flag_"+uid+"' class='npflag_mail'><div class='"+left_flg_class+"' id='left_div_flag_"+uid+"'  >&#9733;</div></a>";
			res+=" <div  class='row_first tag_row_new'><div  class='inbox_tag'>";
			
			if(tags!=null && !(tags.equals("")))
			{
				
				
				String tagnm[]=tags.split("~");
            	for(int j=0;j<tagnm.length;j++)
            	{
            		boolean stts=false;
            		String tagcss="";
            		String tag="";
            		String tagback="";
            		if( tagnm[j].equalsIgnoreCase("$label1"))
            		{
            			tagcss="Important";
                		tag="Important";
                		tagback="#FA6855";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label2"))
            		{
            			tagcss="Work";
                		tag="Work";
                		tagback="#FC9449";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label3"))
            		{
            			tagcss="Personal";
                		tag="Personal";
                		tagback="#98C95D";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label4"))
            		{
            			tagcss="To_Do";
                		tag="To Do";
                		tagback="#486BF7";
                		stts=true;
            		}
            		else if( tagnm[j].equalsIgnoreCase("$label5"))
            		{
            			tagcss="Later";
                		tag="Later";
                		tagback="#BD48F7";
                		stts=true;
            		}
            		else 
            		{
            			tagback="gray";
            			
            			if(labset!=null && labset.length()>0)
                            {
            				if(labset.indexOf(tagnm[j]) >=0)
            				{
            				
                             String tagarr[]=labset.split("~");
                             for(int i=0;i< tagarr.length; i++)
                                {
                                 String tarr[]=tagarr[i].split("#");
                                 if(tarr[0].equalsIgnoreCase(tagnm[j]))
                                 {
                                	 tagback="#"+tarr[1];
                                	 stts=true;
                                	 break;
                                 }
                                }
                            }
                            }
            			
            			tag= tagnm[j].replace("_", " ");
                		tagcss= tagnm[j];
                		
            		}
            		if(stts)
            		{
            		String tagid=	"list_"+uid+"~"+tagnm[j];
            		res+="	<div id='"+tagid+"' class='in_tag "+tagcss+"' style='display: block; background: "+tagback+";'><span>"+tag+"</span><div class='close_tag'>x</div></div>";
            		}
            		}
			}
			
			res+="</div><div class='clear'></div> </div><div class='clear'></div>  </div>";
		}
		
		/*if(inblist.size()==0)
		{
			
			res+="<div class='inbox_empty' >No messages found in "+folder+".   <div class='clear'></div>  </div>";
			
		}
		*/
		
		
		return res;
		}
		
	}
	
	
	
	@RequestMapping(value = "/getMailInboxDesc", method = RequestMethod.GET)
	@ResponseBody
	public String listWebmailInboxDesc(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		String res="";// <script src='js/event.js' type='text/javascript' language='javascript' ></script> ";
		String start= request.getParameter("start");
		String end= request.getParameter("end");
		String folder= request.getParameter("folder");
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		hs.setAttribute("active_folder", folder);
		//System.out.println("inbox***********************folder="+folder);
		GetMailInboxDescResponse wfre=webmailClient.getInboxMailRequestDesc(host, port, id, pass, start, end, folder);
		List<InboxDesc>  inblist=wfre.getGetInboxByMailLimitDesc().getInboxListDescReturn().getInboxMailListDesc();
		
		for(InboxDesc inb : inblist)
		{
			String uid="";
			String fromid="";
			String sub="";
			String attch="";
			String date="";
			String dt_title="";
			String seen_class="";
			if(inb.isMailSeenDesc()== false)
			{
				seen_class="unread_message";
			}
			
			String flag_class="small_image";
			if(inb.isMailFlageDesc()== true)
			{
				flag_class="small_image_flag";
			}
			
			uid=inb.getUidDesc();
			fromid=inb.getFromIdDesc();
			sub=inb.getSubjectDesc();
			attch=inb.getAttachmentDesc();
			date=inb.getSendDtaeDesc();
			dt_title=inb.getSendDtaeTitleDesc();
			String mailcnt=inb.getMailContentDesc();
			mailcnt = mailcnt.replaceAll("\\<.*?\\>", "");
			res+="<div id='div_"+uid+"' class='row_content' ><div class='row_first_tag'><div class='row_left "+seen_class+" '>";
			res+="<div class='flag_first'><div class='row_check_box'>";
			res+=" <input type='checkbox' class='mail_checked'>";
			res+=" </div> <a href='#'><div id='div_flag_"+uid+"' class='"+flag_class+"' onclick='falgAction("+uid+")'></div> </a>";
			res+=" </div>";
			res+=" <div class='form_first'> "+fromid+" </div>";
			res+="  <div class='subject_first'><div class='forword_icon'></div>";
			res+=   sub;
			res+=" </div></div>";
			res+="<div class='right_row_first'>";
			res+="<div class='row_date' style='cursor: pointer;' title='"+dt_title+"'>"+date+"</div>";
			res+="<div class='row_attach'>";
			if(attch.equalsIgnoreCase("yes"))
			{
				res+="	<img src='images/attachment.png'>";
			}
				
			res+="	</div> <a style='cursor: pointer;' onclick='moveTrash("+uid+")'><div class='row_delete'></div></a>";
			res+="   </div> </div>";
			res+="   <div class='message'> "+mailcnt+" </div>";
			res+=" <div class='row_first '><div class='inbox_tag'></div><div class='clear'></div> </div><div class='clear'></div>  </div>";
		}
		//System.out.println("return inbox**********************="+res);
		
		return res;
		
	}
	
}
