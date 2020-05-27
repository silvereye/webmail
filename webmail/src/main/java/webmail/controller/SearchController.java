package webmail.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.local.WebmailSearch;

import webmail.bean.TextDecode;
import webmail.webservice.client.WebmailClient;
import webmail.wsdl.GetLdapFNameResponse;
import webmail.wsdl.GetWebmailAllMailCountResponse;
import webmail.wsdlnew.GetMailInboxQuickSearchResponse;
import webmail.wsdlnew.GetMailInboxSearchResponse;
import webmail.wsdlnew.InboxQuickSearch;
import webmail.wsdlnew.InboxSearch;

import java.util.*;


import javax.mail.internet.*;

@Controller
public class SearchController {


	@Autowired
	private WebmailClient webmailClient;
	
	
	@RequestMapping(value = "/getAllMailCountSearchQck", method = RequestMethod.GET)
	@ResponseBody
	public synchronized String getAllMailCountSearchQck(ModelMap map, Principal principal,  HttpServletRequest request)
	{
		String res="";
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String quick_val= request.getParameter("quick_val");
		//String pview=request.getParameter("pview");
		
		String foldername= request.getParameter("fldrnm");
		
		//HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String inboxcnt="";
		WebmailSearch ws=new WebmailSearch();
		inboxcnt=ws.countAllMailSearchQck(host, port, id, pass, foldername, quick_val);
		
		return inboxcnt;
	}
	
	
	@RequestMapping(value = "/getAllMailCountSearchAdv", method = RequestMethod.GET)
	@ResponseBody
	public synchronized String getAllMailCountSearchAdv(ModelMap map, Principal principal,  HttpServletRequest request)
	{
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String to= request.getParameter("to");
		String from= request.getParameter("from");
		String subt= request.getParameter("sub");
		String con= request.getParameter("con");
		String dt1= request.getParameter("dt1");
		String dt2= request.getParameter("dt2");
		String foldername= request.getParameter("fldrnm");
		
		//HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");

		String tgnm=request.getParameter("tagnm");
		String stnm=request.getParameter("stnm");
		String inboxcnt="";
		WebmailSearch ws=new WebmailSearch();
		inboxcnt=ws.countAllMailSearchAvd(host, port, id, pass,  foldername, to, from, subt, con, dt1, dt2,tgnm,stnm );
		
		return inboxcnt;
	}
	
	
	@RequestMapping(value = "/quickSearchMail",produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String quickSearchMail(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String res="";
		String quick_val= request.getParameter("quick_val");
		if(quick_val.contains("<") && quick_val.contains(">"))
		{
			String qarr[]=quick_val.split("<");
			quick_val=qarr[1];
			quick_val=quick_val.replaceAll(">", "");
		}
		
		if(quick_val.contains("&lt;") && quick_val.contains("&gt;"))
		{
			String qarr[]=quick_val.split("&lt;");
			quick_val=qarr[1];
			quick_val=quick_val.replaceAll("&gt;", "");
		}
		//String pview=request.getParameter("pview");
		String start= request.getParameter("start");
		String end= request.getParameter("end");
		String foldername= request.getParameter("fldrnm");
		String folder= foldername;
		//HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		hs.setAttribute("active_folder",foldername);
		String pview=hs.getAttribute("previewPane").toString();
		
		GetMailInboxQuickSearchResponse wfre=webmailClient.getInboxMailQuickSearchRequest(host, port, id, pass, start, end, foldername, quick_val);
		List<InboxQuickSearch>  inblist=wfre.getGetInboxByMailLimitQuickSearch().getInboxListReturnQuickSearch().getInboxMailListQuickSearch();
		
		
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
		GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "labelSetting");
   		String labset=ldapres.getGetFName();
		
		
		int cnt=wfre.getGetInboxByMailLimitQuickSearch().getQuickCount();
		 long mcount=cnt;
		long endlmt=15;
		if(hs.getAttribute("limitMail").toString()!=null && !hs.getAttribute("limitMail").toString().equals(""))
		{
			endlmt=Integer.parseInt(hs.getAttribute("limitMail").toString());
		}
		if(endlmt>mcount)
		{
			endlmt=mcount;
		}
		if(mcount==0)
		{
			res="0$Search is Empty";	
		}
		else
		{
		res=mcount+"$1&nbsp;-&nbsp;"+endlmt+"&nbsp;of&nbsp;"+mcount;
		}
		
		res=res+"<$nps$>";//<script src='js/event.js' type='text/javascript' language='javascript' ></script> ";
		
		
		
		
		
		for(InboxQuickSearch inb : inblist)
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
			String rc_bk="";
			
			pri=inb.getMailPriority();
			
			
	
				if(inb.getSubject()!=null)
					sub=TextDecode.decodeUTF8Text(inb.getSubject());
				else
					sub="";
			
				if(inb.getToId()!=null)
					gen_to=TextDecode.decodeUTF8Text(inb.getToId());
				else
					gen_to="";
			
				if(inb.getCCId()!=null)
					gen_cc=TextDecode.decodeUTF8Text(inb.getCCId());
				else
					gen_cc="";
			
				if(inb.getBCCId()!=null)
					gen_bcc=TextDecode.decodeUTF8Text(inb.getBCCId());
				else
					gen_bcc="";
			
				if(inb.getFromId()!=null)
					gen_from=TextDecode.decodeUTF8Text(inb.getFromId());
				else
					gen_from="";
			
			
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
			
			String css_class1="row_content "+rc_bk;
			String css_class2="row_first";
			String css_class3="row_left";
			if(pview.equalsIgnoreCase("Vertical View"))
			{
				css_class1="row_content left_view_mess "+rc_bk;
				css_class2="row_first mail_de_op";
				css_class3="row_left left_view_con";
			}
			
			String tags=inb.getMailTag();
			
			
			uid=inb.getUid();
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
			/*fromid=fromid.trim();
			if(fromid==null || fromid.equalsIgnoreCase("null"))
			{
				fromid="";
			}*/
			
			if(sub==null || sub.equals(""))
			{
				sub="(no Subject)";
			}
			attch=inb.getAttachment();
			date=inb.getSendDtae();
			dt_title=inb.getSendDtaeTitle();
			String mailcnt=inb.getMailContent();
			String fwd_to=HtmlUtils.htmlEscape(gen_to);
			
			/*if(fwd_to!= null && ! fwd_to.equals(""))
			{
			fwd_to=fwd_to.replace("<", "&lt;");
			fwd_to=fwd_to.replace(">", "&gt;");
			}*/
			
			String fwd_from=HtmlUtils.htmlEscape(gen_from);
			/*if(fwd_from!= null && ! fwd_from.equals(""))
			{
			fwd_from=fwd_from.replace("<", "&lt;");
			fwd_from=fwd_from.replace(">", "&gt;");
			}
			*/
			String fwd_cnt="---------- Forwarded message ----------<br>";
			fwd_cnt+="From: "+fwd_from+"<br>";
			fwd_cnt+="To: "+fwd_to+"<br>";
			fwd_cnt+="Date: "+dt_title+"<br>";
			fwd_cnt+="Subject: "+sub+"<br><br><br>";
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
				
			String hid_to="hid_rep_to_"+uid;
			String hid_cc="hid_rep_cc_"+uid;
			String hid_sub="hid_rep_sub_"+uid;
			String hid_cnt="hid_rep_cnt_"+uid;
			String hid_fwd_cnt="hid_fwd_cnt_"+uid;
			String hid_fst_line="hid_fst_line_"+uid;
			
			
			
			/*if(folder.equalsIgnoreCase("Drafts"))
			{
				
				String hid_to_draft="hid_draft_to_"+uid;
				String hid_cc_draft="hid_draft_cc_"+uid;
				String hid_bcc_draft="hid_draft_bcc_"+uid;
				String hid_sub_draft="hid_draft_sub_"+uid;
				String hid_cnt_draft="hid_draft_cnt_"+uid;
				
				if(attch.equalsIgnoreCase("yes"))
				{
					List<String> flnmlst= inb.getAttachmentName();
					List<String> flszlst=inb.getAttachmentSize();
					int cntfl=0;
					for(int i=0;i< flnmlst.size() ; i++)
					{
						cntfl++;
						String hid_filenm="hid_draft_attach_name_"+uid+"_"+cntfl;
						String hid_filesz="hid_draft_attach_size_"+uid+"_"+cntfl;
						res+="<input type='hidden' id='"+hid_filenm+"' value='"+flnmlst.get(i)+"'/>";
						res+="<input type='hidden' id='"+hid_filesz+"' value='"+flszlst.get(i)+"'/>";
					}
					res+="<input type='hidden' id='hid_draft_attach_cnt_"+uid+"' value='"+cntfl+"'/>";
				}
				
				res+="<input type='hidden' id='hid_draft_attach_status_"+uid+"' value='"+attch+"'/>";
				//String results = HtmlUtils.htmlEscape(str);
				res+="<input type='hidden' id='"+hid_to_draft+"' value='"+ HtmlUtils.htmlEscape(gen_to)+"'/>";
				res+="<input type='hidden' id='"+hid_cc_draft+"' value='"+HtmlUtils.htmlEscape(gen_cc)+"'/>";
				res+="<input type='hidden' id='"+hid_bcc_draft+"' value='"+HtmlUtils.htmlEscape(gen_bcc)+"'/>";
				res+="<input type='hidden' id='"+hid_sub_draft+"'  value='"+HtmlUtils.htmlEscape(sub)+"' />";
				res+="<input type='hidden' id='"+hid_cnt_draft+"'  value='"+HtmlUtils.htmlEscape(inb.getMailContentOtr())+"' />";
			
			}
			else
			{
			res+="<input type='hidden' id='"+hid_fst_line+"' value='"+HtmlUtils.htmlEscape(fst_line)+"'/>";
			res+="<input type='hidden' id='"+hid_to+"' value='"+HtmlUtils.htmlEscape(send_to)+"'/>";
			res+="<input type='hidden' id='"+hid_cc+"' value='"+HtmlUtils.htmlEscape(send_cc)+"'/>";
			res+="<input type='hidden' id='"+hid_sub+"' value='"+HtmlUtils.htmlEscape(send_sub)+"' />";
			res+="<div style='display: none;' id='"+hid_cnt+"' >"+HtmlUtils.htmlEscape(send_cnt)+"</div>";
			res+="<div style='display: none;' id='"+hid_fwd_cnt+"' >"+HtmlUtils.htmlEscape(fwd_cnt)+"</div>";
			res+="<input type='hidden' id='hid_rep_attch_status_"+uid+"' value='"+attch+"'/>";
			
			}
			*/
			
			mailcnt = mailcnt.replaceAll("\\<.*?\\>", "");
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
			res+="<div title='"+titfromid+"' class='form_first'> "+fromid+" </div>";
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
			res+= ""+  sub;
			

			if(inb.isMailDraft())
				mailDraft="true";
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
			//	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^tags="+tags);
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
                           //      System.out.println("~~~~~~~~~~~~~~~~~~"+tarr[0]+" ----- "+tagnm[j]);
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
		if(inblist.size()==0)
		{
			
			res+="<div class='inbox_empty' >No messages found in this search.   <div class='clear'></div>  </div>";
			
		}
		return res;
	}
	
	
	
	
	
	@RequestMapping(value = "/advSearchMail",produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String advSearchMail(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		boolean status=false;
		String res="";
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String to= request.getParameter("to");
		String from= request.getParameter("from");
		String subt= request.getParameter("sub");
		String con= request.getParameter("con");
		String dt1= request.getParameter("dt1");
		String dt2= request.getParameter("dt2");
		String start= request.getParameter("start");
		String end= request.getParameter("end");
		String foldername= request.getParameter("fldrnm");
		String folder= foldername;
		//HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		//String pview=request.getParameter("pview");
		String tgnm=request.getParameter("tagnm");
		String stnm=request.getParameter("stnm");
		hs.setAttribute("active_folder",foldername);
		String pview=hs.getAttribute("previewPane").toString();
		
		
		GetMailInboxSearchResponse wfre=webmailClient.getInboxMailSearchRequest(host, port, id, pass, start, end, foldername, to, from, subt, con, dt1, dt2,tgnm,stnm );
		List<InboxSearch>  inblist=wfre.getGetInboxByMailLimitSearch().getInboxListReturnSearch().getInboxMailListSearch();
		
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
		 GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "labelSetting");
   		 String labset=ldapres.getGetFName();
		
		
		int cnt=wfre.getGetInboxByMailLimitSearch().getSearchCount();
		long mcount=cnt;
		long endlmt=15;
		if(hs.getAttribute("limitMail").toString()!=null && !hs.getAttribute("limitMail").toString().equals(""))
		{
			endlmt=Integer.parseInt(hs.getAttribute("limitMail").toString());
		}
		if(endlmt>mcount)
		{
			endlmt=mcount;
		}
		if(mcount==0)
		{
			res="0$Search is Empty";	
		}
		else
		{
			res=mcount+"$1&nbsp;-&nbsp;"+endlmt+"&nbsp;of&nbsp;"+mcount;
		}
		
		res=res+"<$nps$>";//<script src='js/event.js' type='text/javascript' language='javascript' ></script> ";
		
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!lst siz"+ inblist.size());
		for(InboxSearch inb : inblist)
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
			String mailDraft="false";
			String pri="";
			String rc_bk="";
			pri=inb.getMailPriority();
			

				if(inb.getSubject()!=null)
					sub=TextDecode.decodeUTF8Text(inb.getSubject());
				else
					sub="";
			
				if(inb.getToId()!=null)
					gen_to=TextDecode.decodeUTF8Text(inb.getToId());
				else
					gen_to="";
			
				if(inb.getCCId()!=null)
					gen_cc=TextDecode.decodeUTF8Text(inb.getCCId());
				else
					gen_cc="";
			
				if(inb.getBCCId()!=null)
					gen_bcc=TextDecode.decodeUTF8Text(inb.getBCCId());
				else
					gen_bcc="";
			
				if(inb.getFromId()!=null)
				gen_from=TextDecode.decodeUTF8Text(inb.getFromId());
				else
					gen_from="";
			
			
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
			
			String css_class1="row_content "+rc_bk;
			String css_class2="row_first";
			String css_class3="row_left";
			if(pview.equalsIgnoreCase("Vertical View"))
			{
				css_class1="row_content left_view_mess "+rc_bk;
				css_class2="row_first mail_de_op";
				css_class3="row_left left_view_con";
			}
			
			String tags=inb.getMailTag();
			
			
			uid=inb.getUid();
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
			/*fromid=fromid.trim();
			if(fromid==null || fromid.equalsIgnoreCase("null"))
			{
				fromid="";
			}*/
			
			if(sub==null || sub.equals(""))
			{
				sub="(no Subject)";
			}
			attch=inb.getAttachment();
			date=inb.getSendDtae();
			dt_title=inb.getSendDtaeTitle();
			String mailcnt=inb.getMailContent();
			String fwd_to=HtmlUtils.htmlEscape(gen_to);
			
			/*if(fwd_to!= null && ! fwd_to.equals(""))
			{
			fwd_to=fwd_to.replace("<", "&lt;");
			fwd_to=fwd_to.replace(">", "&gt;");
			}*/
			
			String fwd_from=HtmlUtils.htmlEscape(gen_from);
			/*if(fwd_from!= null && ! fwd_from.equals(""))
			{
			fwd_from=fwd_from.replace("<", "&lt;");
			fwd_from=fwd_from.replace(">", "&gt;");
			}
			*/
			String fwd_cnt="---------- Forwarded message ----------<br>";
			fwd_cnt+="From: "+fwd_from+"<br>";
			fwd_cnt+="To: "+fwd_to+"<br>";
			fwd_cnt+="Date: "+dt_title+"<br>";
			fwd_cnt+="Subject: "+sub+"<br><br><br>";
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
				
			String hid_to="hid_rep_to_"+uid;
			String hid_cc="hid_rep_cc_"+uid;
			String hid_sub="hid_rep_sub_"+uid;
			String hid_cnt="hid_rep_cnt_"+uid;
			String hid_fwd_cnt="hid_fwd_cnt_"+uid;
			String hid_fst_line="hid_fst_line_"+uid;
			
			
			/*
			if(folder.equalsIgnoreCase("Drafts"))
			{
				
				String hid_to_draft="hid_draft_to_"+uid;
				String hid_cc_draft="hid_draft_cc_"+uid;
				String hid_bcc_draft="hid_draft_bcc_"+uid;
				String hid_sub_draft="hid_draft_sub_"+uid;
				String hid_cnt_draft="hid_draft_cnt_"+uid;
				
				if(attch.equalsIgnoreCase("yes"))
				{
					List<String> flnmlst= inb.getAttachmentName();
					List<String> flszlst=inb.getAttachmentSize();
					int cntfl=0;
					for(int i=0;i< flnmlst.size() ; i++)
					{
						cntfl++;
						String hid_filenm="hid_draft_attach_name_"+uid+"_"+cntfl;
						String hid_filesz="hid_draft_attach_size_"+uid+"_"+cntfl;
						res+="<input type='hidden' id='"+hid_filenm+"' value='"+flnmlst.get(i)+"'/>";
						res+="<input type='hidden' id='"+hid_filesz+"' value='"+flszlst.get(i)+"'/>";
					}
					res+="<input type='hidden' id='hid_draft_attach_cnt_"+uid+"' value='"+cntfl+"'/>";
				}
				
				res+="<input type='hidden' id='hid_draft_attach_status_"+uid+"' value='"+attch+"'/>";
				//String results = HtmlUtils.htmlEscape(str);
				res+="<input type='hidden' id='"+hid_to_draft+"' value='"+ HtmlUtils.htmlEscape(gen_to)+"'/>";
				res+="<input type='hidden' id='"+hid_cc_draft+"' value='"+HtmlUtils.htmlEscape(gen_cc)+"'/>";
				res+="<input type='hidden' id='"+hid_bcc_draft+"' value='"+HtmlUtils.htmlEscape(gen_bcc)+"'/>";
				res+="<input type='hidden' id='"+hid_sub_draft+"'  value='"+HtmlUtils.htmlEscape(sub)+"' />";
				res+="<input type='hidden' id='"+hid_cnt_draft+"'  value='"+HtmlUtils.htmlEscape(inb.getMailContentOtr())+"' />";
			
			}
			else
			{
			res+="<input type='hidden' id='"+hid_fst_line+"' value='"+HtmlUtils.htmlEscape(fst_line)+"'/>";
			res+="<input type='hidden' id='"+hid_to+"' value='"+HtmlUtils.htmlEscape(send_to)+"'/>";
			res+="<input type='hidden' id='"+hid_cc+"' value='"+HtmlUtils.htmlEscape(send_cc)+"'/>";
			res+="<input type='hidden' id='"+hid_sub+"' value='"+HtmlUtils.htmlEscape(send_sub)+"' />";
			res+="<div style='display: none;' id='"+hid_cnt+"' >"+HtmlUtils.htmlEscape(send_cnt)+"</div>";
			res+="<div style='display: none;' id='"+hid_fwd_cnt+"' >"+HtmlUtils.htmlEscape(fwd_cnt)+"</div>";
			res+="<input type='hidden' id='hid_rep_attch_status_"+uid+"' value='"+attch+"'/>";
			
			}
			*/
			
			mailcnt = mailcnt.replaceAll("\\<.*?\\>", "");
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
			res+="<div title='"+titfromid+"' class='form_first'> "+fromid+" </div>";
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
			res+= ""+  sub;

			if(inb.isMailDraft())
				mailDraft="true";
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
			//	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^tags="+tags);
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
                           //      System.out.println("~~~~~~~~~~~~~~~~~~"+tarr[0]+" ----- "+tagnm[j]);
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
			
		if(inblist.size()==0)
		{
			
			res+="<div class='inbox_empty' >No messages found in this search.   <div class='clear'></div>  </div>";
			
		}
		
		return res;
		
		
	}
	
}
