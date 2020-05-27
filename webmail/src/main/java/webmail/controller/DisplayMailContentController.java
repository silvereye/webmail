package webmail.controller;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;

import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import webmail.bean.CalDav;
import webmail.bean.TextDecode;
import webmail.webservice.client.CalendarClient;
import webmail.webservice.client.FileClient;
import webmail.webservice.client.WebmailClient;
import webmail.wsdlnew.Attachment;
import webmail.wsdl.CreateCalendarRequest;
import webmail.wsdl.CreateEventResponse;
import webmail.wsdl.EditFileResponse;
import webmail.wsdl.EventArray;
import webmail.wsdl.EventBean;
import webmail.wsdl.GetEventsResponse;
import webmail.wsdl.GetFileByPathResponse;
import webmail.wsdl.GetFileResponse;
import webmail.wsdl.GetInviteEventResponse;
import webmail.wsdl.GetLdapFNameResponse;
import webmail.wsdl.GetLdapOneAttOtrUserResponse;
import webmail.wsdlnew.GetMailDisplayResponse;
import webmail.wsdlnew.InboxDisplay;

@Controller
public class DisplayMailContentController {

	@Autowired
	WebmailClient webmailClient;

	@Autowired
	private FileClient fileClient;

	@Autowired
	private CalendarClient calendarclient;
	
	
	 public  String addLink(String s) {
	       String res="";
	        // separate input by spaces ( URLs don't have spaces )
	        String [] parts = s.split("&nbsp;|\\s+");

	        // Attempt to convert each item into an URL.  
	        boolean flg=true;
	        for( String item : parts )
	        {
	        	try {
	            URL url = new URL(item);
	            // If possible then replace with anchor...
	            item="<a href='" + url + "'>"+ url + "</a>";  
	        } catch (MalformedURLException e) {
	            // If there was an URL that was not it!...
	           // System.out.print( item + " " );
	        }
	        catch (Exception e) {
	            // If there was an URL that was not it!...
	           // System.out.print( item + " " );
	        }
	        	
	        	if(flg)
	        	{
	        		res=item;
	        	}
	        	else
	        	{
	        		res+=" "+item;
	        	}
	        	flg=false;
	        }
	       return res;
	    }
	
	
	@RequestMapping(value = "/getMailContentByUid", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String listWebmailInbox(ModelMap map, Principal principal,HttpServletResponse response, HttpServletRequest request) 
	{
	
		String res=" <script src='js/new_forword.js'></script>";
		
	/*	res+="<script>";
		res+="var cc='';";
		res+="var rulesForCssText = function (styleContent) {  ";  
		res+="var doc = document.implementation.createHTMLDocument(''),";
		res+="styleElement = document.createElement('style');  ";
		res+="styleElement.textContent = styleContent;";
		res+="doc.body.appendChild(styleElement);";
		res+="return styleElement.sheet.cssRules;};";
		res+="$('.mail_content_1 style').each(function(){";
		res+="var modfieddCss = '';	  var currentCss =  rulesForCssText($(this).html());  cc=currentCss;	  $(this).html('');";
		res+="for(var i =0; i< currentCss.length;i++)";
		res+="{if(currentCss[i].cssRules != undefined && currentCss[i].media != undefined)";
		res+="{ var media ='';";
		res+="media += '@media ' + currentCss[i].media[0] +'{';";
		res+="for(var j =0; j< currentCss[i].cssRules.length;j++)";
		res+="{media+= '.mail_content_1 '  + currentCss[i].cssRules[j].cssText;}";
		res+="media+='}';modfieddCss+= media;}";
		res+="else {modfieddCss+= '.mail_content_1 '  + currentCss[i].cssText; } }";
		res+="$(this).html(modfieddCss);alert(currentCss+'------------'+modfieddCss);}); ";
		res+="</script>";*/
		
		/*OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		try {
			connection.connect();
		} catch (ConnectException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		String uid= request.getParameter("uid");
		String folder= request.getParameter("folder");
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
		String fname = (String) hs.getAttribute("fname");
		
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@folder"+folder);
		String realPath = request.getServletContext().getRealPath("/");
	    String filePath  = realPath+"WEB-INF/view/jsp/temp/";
	    String uidstatus="same"; 
		GetMailDisplayResponse gdres=webmailClient.displayMailContentRequest(host, port, id, pass, uid, folder, filePath,uidstatus);
		InboxDisplay inbd= gdres.getGetInboxByUid();
		if(inbd.getErrorCnt()!=null && inbd.getErrorCnt().equalsIgnoreCase("<nps>"))
		{
			if(inbd.getErrorSt()!=null && inbd.getErrorSt().length()>0)
			{
				if(inbd.getErrorSt().startsWith("java.lang.NullPointerException"))
					inbd.setErrorSt("Message not found");
				res="<error>"+inbd.getErrorSt();
			}
			else
			{
			res="<nps>";
			}
		}
		else
		{
		List<Attachment> latt=inbd.getAttachment();
		
		
		GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "labelSetting");
   		String labset=ldapres.getGetFName();
		
		if(latt!=null)
		{
	///	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@list"+latt.size());
			
		String uids="";
		String fromid="";
		String disp_from="";
		String replyto="";
		String ovr_from1="";
		String ovr_from2="";
		String sub="";
		String attch="";
		String date="";
		String dt_title="";
		String mailcnt="";
		String to="";
		String cc="";
		String bcc="";
		String disp_to="";
		String disp_cc="";
		String disp_bcc="";
		String disp_to_tit="";
		String pri="";
		//attch=inbd.getAttachment();
		pri=inbd.getMailPriority();
		
		
		res+="<input type='hidden' id='hid_inline_sz' value='"+inbd.getInlineimgsize()+"' />";
		res+="<input type='hidden' id='hid_attch_sz' value='"+latt.size()+"' />";
		
		
		String spam="Report Spam";
		if(folder.equalsIgnoreCase("Junk"))
		{
			spam="Not Spam";
		}
		
		String ml_flg="bottom_flag";
		String tit_flg="Add Star";
		boolean flg_sts=false;
		if(inbd.isMailFlage())
		{
			flg_sts=true;
			ml_flg="bottom_flag_red";
			tit_flg="Remove Star";
		}
		uids=inbd.getUid();
		
			if(inbd.getSubject()!=null && inbd.getSubject().length()>0)
				sub=TextDecode.decodeUTF8Text(inbd.getSubject());
			else
				sub="(no Subject)";
		
		
		String sub_tit=sub;
		if(sub.length()>=300)
		{
			sub=sub.substring(0, 300)+"...";
		}
		
		
	
			if(inbd.getFromId()!=null)
				fromid=TextDecode.decodeUTF8Text(inbd.getFromId());
			else
				fromid="";
			if(inbd.getReplyId()!=null && inbd.getReplyId().length()>0)
				replyto=TextDecode.decodeUTF8Text(inbd.getReplyId());
			else
				replyto="";
		if(replyto.equalsIgnoreCase(fromid))	
			replyto="";
		
		date=inbd.getSendDtae();
		dt_title=inbd.getSendDtaeTitle();
		mailcnt=inbd.getMailContent();
		
	
			if(inbd.getToId()!=null)
				to=TextDecode.decodeUTF8Text(inbd.getToId());
			else
				to="";
		
	
			if(inbd.getCCId()!=null)
				cc=TextDecode.decodeUTF8Text(inbd.getCCId());
			else
				cc="";
		
			if(inbd.getBCCId()!=null)
				bcc=TextDecode.decodeUTF8Text(inbd.getBCCId());
			else
				bcc="";
		
		String from_img_id="";
		/*try {
			fromid = URLDecoder.decode(fromid, "UTF-8");
			System.out.println("@@@@@@@@@@="+fromid);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		disp_from=fromid;
		
		disp_from=HtmlUtils.htmlEscape(disp_from);
		
		 if(replyto!=null && replyto.length()>0)
			 replyto=HtmlUtils.htmlEscape(replyto);
		
		/*disp_from=disp_from.replace("<", "&lt;");
		disp_from=disp_from.replace("<", "&lt;");*/
		System.out.println("@@@@@@@@@@@@@fromid="+fromid);
		String fromarr[]=fromid.split("<");
		if(fromarr!=null && fromarr.length>1)
		{
			ovr_from1=fromarr[0];
			ovr_from2=fromarr[1].replace(">", "");
			from_img_id=ovr_from2;
		}
		else
		{
			ovr_from1=fromid;
			from_img_id=ovr_from1;
		}
		
		
		//String img_myurl=hs.getAttribute("img_myurl").toString();
		String path_img="images/unnamed.png";
		String idarr[]=id.split("@");
		if(from_img_id.contains("@"+idarr[1]))
		{
		GetLdapOneAttOtrUserResponse ldapres1=webmailClient.getLdapOneAttOtrUser(ldapurl, id, pass, ldapbase, from_img_id,"jpegPhoto");
		if(ldapres1.getGetLdapAttr()!=null && !ldapres1.getGetLdapAttr().equals("") && ldapres1.getGetLdapAttr().length()>5)
		{
		path_img="data:image/jpg;base64,"+ldapres1.getGetLdapAttr();
		}
		}
        String path_img_id=from_img_id+"nomyimage_cnt";
        String path_img_full_id=from_img_id+"nomyimage_cnt_full";
		
		disp_to=to;
		/*disp_to=disp_to.replace("<", "&lt;");
		disp_to=disp_to.replace("<", "&lt;");*/
		//disp_to=disp_to.replace(",", ",<br>");
		disp_to=HtmlUtils.htmlEscape(disp_to);
		
		disp_cc=cc;
		/*disp_cc=disp_cc.replace("<", "&lt;");
		disp_cc=disp_cc.replace("<", "&lt;");*/
		//disp_cc=disp_cc.replace(",", ",<br>");
		disp_cc=HtmlUtils.htmlEscape(disp_cc);
		
		disp_bcc=bcc;
		/*disp_cc=disp_cc.replace("<", "&lt;");
		disp_cc=disp_cc.replace("<", "&lt;");*/
		//disp_cc=disp_cc.replace(",", ",<br>");
		disp_bcc=HtmlUtils.htmlEscape(disp_bcc);
		
		String toarr[]=to.split(",");
		for(int i=0;i<toarr.length;i++)
		{
			String tit="";
			
		/*	if(toarr[i].contains(id))
			{
				tit="Me";
			}
			else
			{*/
			String toarr1[]=toarr[i].split("<");
			if(toarr1!=null && toarr1.length>0)
			{
				String att=toarr1[0].trim();
				String toarr2[]=att.split(" ");
				if(toarr2!=null && toarr2.length>0)
				{
					tit=toarr2[0];
				}
				else
				{
					tit=att;
				}
			}
			else
			{
			tit=toarr[i];
			}
			//}
			
			if(disp_to_tit== null || disp_to_tit.equals(""))
			{
				disp_to_tit=tit;
			}
			else
			{
				disp_to_tit=disp_to_tit+", "+tit;
			}
			
		}
		
		disp_to_tit=HtmlUtils.htmlEscape(disp_to_tit);
		
		disp_to=disp_to.replace("undisclosed-recipients:;", id);
		disp_to_tit=disp_to_tit.replace("undisclosed-recipients:;", id);
		
		
		String tags=inbd.getMailTag();
		
		//System.out.println("title="+sub);
		
		res+="<div class='top_bottom_1'> <h1 title='"+sub_tit+"'>"+sub+"</h1>";
		res+="<div class='mail_right_1'> <a target='_blank' href='mailcntprint?uid="+uid+"&folder="+folder+"' style='cursor: pointer;'><div title='Print' class='printer'></div></a>  ";
			/*	+ "<a target='_blank' href='nexttab?uid="+uid+"&folder="+folder+"'><div class='fullscreen'></div> </a> ";*/
		/*res+="<div class='mail_right_1'> <a style='cursor: pointer;'><div class='printer'></div></a> <a target='_blank' href='nexttab?uid="+uid+"&folder="+folder+"'><div class='fullscreen'></div> </a> ";*/
		res+=" </div>";
		res+="<div class='clear'></div>";
		res+="<div class='inbox_tag inbox_tag_1'>";
		if(tags!=null && !(tags.equals("")))
		{
			
			
			String tagnm[]=tags.split("~");
		//	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^tags="+tags);
        	for(int j=0;j<tagnm.length;j++)
        	{
        		boolean st=false;
        		String tagcss="";
        		String tag="";
        		String tagback="";
        		if( tagnm[j].equalsIgnoreCase("$label1"))
        		{
        			tagcss="Important";
            		tag="Important";
            		tagback="#FA6855";
            		st=true;
        		}
        		else if( tagnm[j].equalsIgnoreCase("$label2"))
        		{
        			tagcss="Work";
            		tag="Work";
            		tagback="#FC9449";
            		st=true;
        		}
        		else if( tagnm[j].equalsIgnoreCase("$label3"))
        		{
        			tagcss="Personal";
            		tag="Personal";
            		tagback="#98C95D";
            		st=true;
        		}
        		else if( tagnm[j].equalsIgnoreCase("$label4"))
        		{
        			tagcss="To_Do";
            		tag="To Do";
            		tagback="#486BF7";
            		st=true;
        		}
        		else if( tagnm[j].equalsIgnoreCase("$label5"))
        		{
        			tagcss="Later";
            		tag="Later";
            		tagback="#BD48F7";
            		st=true;
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
                            // System.out.println("~~~~~~~~~~~~~~~~~~"+tarr[0]+" ----- "+tagnm[j]);
                             if(tarr[0].equalsIgnoreCase(tagnm[j]))
                             {
                            	 tagback="#"+tarr[1];
                            	 st=true;
                            	 break;
                             }
                            }
                        }
                        }
        			
        			tag= tagnm[j].replace("_", " ");
            		tagcss= tagnm[j];
            		
        		}
        		if(st)
        		{
        		String tagid="disp_"+uid+"~"+tagnm[j];
        		String tagclsid=	"half-"+uid+"~"+tagnm[j];	
        		res+="	<div id='"+tagid+"' class='in_tag_disp in_tag "+tagcss+"' style='display: block; background: "+tagback+";'><span>"+tag+"</span><div id='"+tagclsid+"' onclick='remMailTagHalf(this.id)' class='close_tag'>x</div></div>";
        		}
        	}
		}
		
		
		res+="</div>";
		res+=" <div class='clear'></div> </div>";
		res+="  <div class='mail_header mail_header_top' style='padding-top: 0px;'>  <div class='mail_left'>  ";
		res+="  <div class='images_hover'>  <img src='"+path_img+"' onerror='getAltImageDisp(this.id)' id='"+path_img_id+"' />   <div class='images_details'> ";
		res+="  <div class='mail_left_1'>  <span>"+ovr_from1+"</span> <div class='clear'></div>"+ovr_from2+"  </div> ";
		res+="<img src='"+path_img+"' onerror='getAltImageDisp(this.id)' id='"+path_img_full_id+"' /> <div class='send_mail_deatils'> <a title='Add to contacts'  name='"+disp_from+"' onclick='addInContacts(this.name)' style='cursor: pointer;'>Add to contacts</a> <a title='View All emails with this contact'  name='"+disp_from+"' onclick='getAllEmails(this.name)' style='cursor: pointer;'>Emails</a> ";
		/*res+=" <div class='right_forw'>  <a style='cursor: pointer;'> <div class='mail_for'> <img src='images/plus_option.gif'> </div> </a> ";*/
		res+=" <div class='right_forw'> ";
		res+=" <a  title='Email this Contact' name='"+disp_from+"' onclick='openComposeWithTo(this.name)'  style='cursor: pointer;'> <img class='email_contact' src='images/mail_fow.png'>  </a>  </div> </div> </div>  </div> ";
		
		res+="<p class='first_p'> <a style='cursor: pointer;'>"+disp_from+"</a></p>";
		res+=" <div class='mail_row'> <p class='last_p new_paragraph'>To "+disp_to_tit+" </p> <a style='cursor: pointer;' onClick='mail_to(event);'> <div class='mail_deatil'></div> </a>";
		res+="<div class='to_me' style='display: none;'>";
		res+="<ul> <li class='comm_width'><span>From: </span></li><li> "+disp_from+" </li>";
		res+="<div class='clear'></div> ";
		String disp_ton=disp_to;
		disp_ton=disp_to.replaceAll(",", ",<br>");
		res+="<li class='comm_width'><span>To:</span></li><li> "+disp_ton+"</li>  <div class='clear'></div>";
		if(cc!=null && !cc.equals(""))
		{
			String disp_ccn=disp_cc;
			disp_ccn=disp_ccn.replaceAll(",", ",<br>");
			res+="<li class='comm_width'><span>Cc:</span></li><li> "+disp_ccn+"</li>  <div class='clear'></div>";
		}
		if(folder.equalsIgnoreCase("Sent") && bcc!=null && !bcc.equals(""))
		{
			String disp_bccn=disp_bcc;
			disp_bccn=disp_bccn.replaceAll(",", ",<br>");
			res+="<li class='comm_width'><span>Bcc:</span></li><li> "+disp_bccn+"</li>  <div class='clear'></div>";
		}
		if(replyto!=null && !replyto.equals(""))
		{
			res+="<li class='comm_width'><span>Reply-To:</span></li><li> "+replyto+"</li>  <div class='clear'></div>";
		}
		res+=" <li class='comm_width'><span>Date:</span></li><li> "+dt_title+"</li>  <div class='clear'></div>";
		res+=" <li class='comm_width'><span>Subject:</span></li><li title='"+sub_tit+"'>"+sub+"</li><div class='clear'></div>";
		/*res+="<li class='comm_width'><span>mailed-by:</span></li><li>Silvereye.in</li> <div class='clear'></div>";
		res+=" <li class='comm_width'><span>signed-by:</span></li><li>Silvereye.in</li> <div class='clear'></div>";*/
	//	res+="<li class='imag_upload'> <span>Images from this sender are always displayed.</span><a style='cursor: pointer;'>Don't display from now on.</a>";
	//	res+=" <div class='clear'></div> </li>";
		res+=" </ul> </div>  </div> </div>";
		
		 res+="<input type='hidden' id='hid_mail_disp_flg' value='"+flg_sts+"'/>";
		res+="<div class='mail_right'><div style='float:left;margin-top: 7px;'>"+date+" </div> <div style='cursor: pointer;' onclick='flagActionDisp("+uids+")'  id='flag_div_disp_"+uids+"' title='"+tit_flg+"' class='"+ml_flg+"'>&#9733;</div>";
		res+="  <div class='mail_option'> ";
		res+="  <a title='More' >   <div class='option option_npmore'> <img src='images/open_sub_menu.png'></div>  </a> </div>  </div>";
		res+="  <div class='clear'></div> </div>";
		res+=" <div class='mail_content_1 mail_content_p1'><div class='mail_down_option_1'>";
		res+=" <ul><li><a target='_blank' href='mailheadercntt?uid="+uid+"&folder="+folder+"' style='cursor: pointer;'><div>View Header</div></a></li><div class='clear'></div>";
		res+="  <li><a name='"+disp_from+"' onclick='addInContacts(this.name)' style='cursor: pointer;'><div>Add to Contact</div></a></li><div class='clear'></div>";
		res+="<li><a onclick='openReply()' style='cursor: pointer;'><div>Reply</div></a></li>";
        res+="<li><a onclick='openReplyAll()'  style='cursor: pointer;'><div>Reply All</div></a></li>";
        res+="<li><a onclick='openForword()' style='cursor: pointer;'><div>Forward</div></a></li>";
        res+="<li><a onclick='moveTrashAll()' style='cursor: pointer;'><div>Delete</div></a></li>";
        res+="<li><a onclick='moveSpamAll()' style='cursor: pointer;'><div>"+spam+"</div></a></li>";
        res+="<li><a onclick='setSetectedMailRead()' style='cursor: pointer;'><div>Mark as Read</div></a></li>";
        res+="<li><a onclick='setSetectedMailUnRead()' style='cursor: pointer;'><div>Mark as Unread</div></a></li>";
        res+="<li><a onclick='setSetectedMailFlag()'  style='cursor: pointer;'><div>Add Star</div></a></li>";
        res+="<li><a onclick='setSetectedMailUnFlag()' style='cursor: pointer;'><div>Remove Star</div></a></li>";
        res+="<li><a target='_blank' href='mailcntprint?uid="+uid+"&folder="+folder+"' style='cursor: pointer;'><div>Print</div></a></li>";
		res+="  </ul>";
	    res+=" <div class='clear'></div> </div>";
	    
	   
	    if(inbd.isMailFlageRecent() && inbd.getMailReadRecId()!=null && !(inbd.getMailReadRecId().equalsIgnoreCase("")))
	    {
	    	if(!tags.contains("$MDNSent"))
	    	{
	    	String rec_sub="Return Receipt (displayed) -"+inbd.getSubject();
        	String rec_cnt="This is a Return Receipt for the mail that you sent to "+id+". <br><br>Note: This Return Receipt only acknowledges that the message was displayed on the recipient's computer. There is no guarantee that the recipient has read or understood the message contents. <br>";
        	try
        	{
        		Properties prop = new Properties();
        		prop.load(getClass().getClassLoader().getResourceAsStream("webmail.properties"));
        		rec_sub=prop.getProperty("ReturnReceiptSub")+inbd.getSubject();
            	rec_cnt=prop.getProperty("ReturnReceiptCnttPart1")+" "+id+prop.getProperty("ReturnReceiptCnttPart2");
            	
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        	res+="<div id='hid_"+inbd.getMailReadRecId()+"_sub' style='display:none;'>"+rec_sub+"</div>";
        	res+="<div id='hid_"+inbd.getMailReadRecId()+"_cnt' style='display:none;'>"+rec_cnt+"</div>";
	    	res+=" <div class='new_notification'><div class='new_notification_left'><span class='notification_icon'></span>";
	    	res+="<span class='notification_message'><b>Sender</b>&nbsp;has asked to be notified when you read this message.</span></div>";
	    	res+="<div class='new_notification_right'><span id='"+inbd.getMailReadRecId()+"' name='"+uid+"' onclick='sendRecMail(this.id, "+uid+")' class='notification_button'>Send</span> <span class='notification_button' name='"+uid+"' onclick='ignoreRecMail("+uid+")'>Ignore</span>";
	    	res+="</div><div class='clear'></div></div><div class='clear'></div>";
	    	}
	    }
	    
	    if(pri!=null && !pri.equals(""))
	    {
	    	 
	    	 if(pri.equalsIgnoreCase("Highest"))	
				{
	    		 res+="<p><img width='20px' height='20px' style='margin-bottom: -6px;  opacity: 0.9;' src='images/impt.png' /> This message was sent with high importance.</p>";	
				}
				else if(pri.equalsIgnoreCase("Lowest"))	
				{
				res+="<p><img width='20px' height='20px' style='margin-bottom: -6px;  opacity: 0.9;' src='images/impt.png' /> This message was sent with low importance.</p>";		
				}
	    }
	    
	    
	    
	    try
	    {
	    	if(!(mailcnt.startsWith("<pre>") && mailcnt.endsWith("</pre>")))
	    	{
	    	Document doc = Jsoup.parse(mailcnt);
	    	Elements links1 = doc.select("style");
	    
	    	links1.remove();
	    	Element links = doc.select("*").removeAttr("class").first();
	    	mailcnt=links.html();
	    	}
	    	
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	mailcnt=inbd.getMailContent();
	    }
	    
	    try
	    {
	    	if(!(mailcnt.startsWith("<pre>") && mailcnt.endsWith("</pre>")))
	    	{
	    	mailcnt= addLink(mailcnt);
	    	}
	    }
	    catch(Exception eee)
	    {
	    	
	    }
	    
	    if(inbd.getICSstatus()!=null && inbd.getICSstatus().equalsIgnoreCase("Yes") )
	    {
	    	boolean isnotsame=true;
	    	String toidarr[]=id.split("@");
	    	String todomain=toidarr[1];
	    	String fdomain="";
	    	String frmiid=fromid.trim();
	    	if(frmiid.endsWith(">"));
	    		frmiid=frmiid.replaceAll(">", "");
	    	frmiid=frmiid.trim();	
	    	String fromidarr[]=frmiid.split("@");
	    	if(fromidarr.length>1)
	    		fdomain=fromidarr[1];
	    	if(fdomain.length()>0 && fdomain.equalsIgnoreCase(todomain))
	    	{
	    		isnotsame=false;
	    	}
	   	if(inbd.getICSMethod()!=null && inbd.getICSMethod().equals("REQUEST"))
	    	{
	    	try
	    	{
	    	
	    		String proid=(String)hs.getAttribute("caldevProId");
	    		String surl=(String)hs.getAttribute("caldevUrl");
	    		String calnm =(String)hs.getAttribute("default_calendar");
	    		CalDav cd=new CalDav();
	    	GetInviteEventResponse rescal =  cd.getInviteEventRequest(inbd.getICScntt(), proid, surl, id, pass, calnm,isnotsame);
//			System.out.println("res : " + res.getEventList());
	    	
	    List<EventBean> elst=	rescal.getEventList().getEventList();
	    if(elst!=null && !elst.isEmpty())
	    {
	    EventBean eb=elst.get(0);
	    String sum= eb.getSummary();
	    Date sdt=eb.getStarteventdate().toGregorianCalendar().getTime();
	    String loc=eb.getLocation();
	    if(loc==null)
	    	loc="";
	    Format formatter = new SimpleDateFormat("MMM"); 
	    String cmont = formatter.format(sdt);
	    formatter = new SimpleDateFormat("dd"); 
	    String cday = formatter.format(sdt);
	    String cal_uuid=eb.getUid();
	    	
	    res+="<table width='100%' > <tbody><tr><td width='20%'>";
	    res+="<table width='125px' style='background: url(images/cal_back.png) no-repeat 0 0;     opacity: 0.9;' height='125px'>"; 
	    res+="<tbody><tr><td style=' text-align: center;    padding-top: 11px;    font-size: 25px;    color: #fff;    font-weight: bold;'>"+cmont+"</td></tr>";
	    res+="<tr rowspan='2'><td style='    text-align: center;    font-size: 55px;    font-weight: bold;    padding-bottom: 11px;'>"+cday+"</td></tr></tbody></table></td>";
	    res+="<td width='80%'><table>";
	    res+="<tr><td colspan='2' style=' font-weight: bold;'> "+sum+"</td><tr>";
	    res+="<tr><td style=' color: #888;'> When: </td> <td> "+sdt+"</td><tr>";
	    res+="<tr><td style=' color: #888;'>  Who:</td> <td> "+fromid+" </td><tr>";
	    res+="<tr><td style=' color: #888;'>  Where:</td> <td> "+loc+" </td><tr>";
	    res+="<tr><td style=' color: #888;'>  Going?:</td> <td> ";
	    res+="<div><div onclick='sendresponse(this.id)' id='yes' style='     cursor: pointer;   float: left;    width: 50px;    text-align: center;    background: #7BDA7B;    border: 1px;    border-color: #847B7B;    border-style: solid;    padding: 3px;'>Yes</div>";
	    res+="<div onclick='sendresponse(this.id)' id='maybe' style='      cursor: pointer;  float: left;    width: 50px;    text-align: center;  background: #E8E8E8;    border-bottom: 1px;    border-top: 1px;    border-left: 0px;    border-right: 0px;    border-color: #847B7B;    border-style: solid;    padding: 3px;'>Maybe</div>";
	    res+="<div onclick='sendresponse(this.id)' id='no' style='     cursor: pointer;   float: left;    width: 50px;    text-align: center;    background: #E8E8E8;    border: 1px;    border-color: #847B7B;    border-style: solid;    padding: 3px;'>No</div></div>";
	    res+="</td><tr></table></td></tr></tbody></table>";
	    res+="<input type='hidden' id='hid_cal_inv_from' value='"+fromid+"' />";
	    res+="<input type='hidden' id='hid_cal_inv_uuid' value='"+cal_uuid+"' />";
	    
	    }
	    	}
	    	catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
			}
	    }
	   	else if(inbd.getICSMethod()!=null && inbd.getICSMethod().equals("REPLY"))
	   	{
	   		if(isnotsame)
	   		{
	   		String proid=(String)hs.getAttribute("caldevProId");
    		String surl=(String)hs.getAttribute("caldevUrl");
	   		try
	    	{
	   		
	   		String icsstring=inbd.getICSString();
	   		if(icsstring!=null && icsstring.length()>0)
	   		{
	   			
	   			
	   			String uid_cal="";
	   			String atten_id="";
	   			String atten_st="";
	   			String calnm="";
	   			EventBean neweb=null;
	   			byte flcntt[]=null;
	   			String flpth="";
	   			boolean calst=false;
	   			GetEventsResponse res_rep_cal = null;
	   			if(inbd.getICScntt()!=null)
	   			{
	   				//res_rep_cal= calendarclient.getallevents(inbd.getICScntt(), "");
	   				CalDav cld=new CalDav();
	   				EventBean event=cld.buildCalEvent(inbd.getICScntt());
	   				if(event!=null)
	   				{
	   					uid_cal=event.getUid();
	   					if(event.getOldguest()!=null)
	   					{
	   						atten_id=event.getOldguest();
	   						if(atten_id!=null && atten_id.length()>1 && atten_id.startsWith(","))
	   							atten_id=atten_id.substring(1);
	   						String arrid1[]=atten_id.split("`");
		   					if(arrid1.length>1)
		   					{
		   						atten_id=arrid1[1];
		   						atten_st=arrid1[0]+"`";
		   					}
	   					}
	   				}
	   			}
	   		
	   			
	   	
	   			CalDav cldav=new CalDav();
	   			List<CalDavCalendarCollection> collList= cldav.findAllCollections( proid,  surl,  id , pass);
	   			for(CalDavCalendarCollection coll : collList)
	   			{
	   				String cpath=coll.getPath();
	   				if(cpath!=null && !cpath.startsWith(surl))
	   				{
	   					cpath=surl+cpath;
	   				}
	   				calnm=cpath;
	   				neweb= cldav.getEventDetail(proid, surl, id, pass, cpath, uid_cal,"","");
	   				if(neweb!=null)
	   				{
	   					break;
	   				}
	   				calnm="";
	   			}
	   			
	   			
	   			
	   		
	   		if(neweb!=null)
	   		{
	   			String oldid=neweb.getOldguest();
	   			String arrid[]=oldid.split(",");
	   			String attlst="";
	   			for(String aid: arrid)
	   			{
	   				if(aid==null)
	   				{
	   					aid="";
	   				}
	   				if(aid.endsWith("`"+atten_id))
	   				{
	   					String arrid1[]=aid.split("`");
	   					if(arrid1.length>1)
	   					{
	   						aid=atten_st+arrid1[1];
	   					}
	   				}
	   				
	   					if(aid.length()>0)
	   						attlst+=","+aid;
	   					
	   				
	   			}
	   			
	   			if(attlst.length()>0)
	   			{
	   				neweb.setOldguest(attlst);
	   				cldav.createEvent(proid, surl, id, pass, calnm, neweb,fname, id,uid_cal);
	   			}
	   			
	   			
	   		}
	   		
	   		}
	    	}
	   		catch(Exception e)
	   		{
	   			
	   		}
	   		}
	   	}
	   	
	    }
	    	
	    
	   // String data="<SCRIpt type='java'>hiiiiiiiiii ggg</script> hellooo <SCRIpt>hiiiiiiiiii ggg</script> hiii &lt;script&gt;alert(&quot;I am destroyer&quot;);&lt;/script&gt; helllloooo";
		StringBuilder regex = new StringBuilder("<script[^>]*>(.*?)</script>");
		int flags = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern = Pattern.compile(regex.toString(), flags);
		Matcher matcher = pattern.matcher(mailcnt);
		mailcnt=matcher.replaceAll("");
		
		
		StringBuilder regex1 = new StringBuilder("<script[^>]*>");
		int flags1 = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern1 = Pattern.compile(regex1.toString(), flags1);
		Matcher matcher1 = pattern1.matcher(mailcnt);
		mailcnt=matcher1.replaceAll("");
		
		
		StringBuilder regex2 = new StringBuilder("onerror=[^>]*>");
		int flags2 = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern2 = Pattern.compile(regex2.toString(), flags2);
		Matcher matcher2 = pattern2.matcher(mailcnt);
		mailcnt=matcher2.replaceAll(">");
		
	//	System.out.println( data);
	    
	    res+="<p>"+mailcnt+"</p>";
	   
	    
	    if(latt!=null && latt.size()>0)
	    {
	    	String atstr=" Attachments";
	    	if(latt.size()==1)
	    	{
	    		atstr=" Attachment";
	    	}
	    	
	    	res+=" <div class='main_attachment'> <div class='attachment_file attachment_top'> <div class='left_attachment'>";
	    	res+=" <p><span>"+latt.size()+"</span>"+atstr+" </p><div class='clear'>";
	    	
	    	
	    	/*res+="</div></div> <div class='left_attachment'></div></div>";*/
	    	res+="</div> ";
	    	String sub1="";	
	        try
	        {
	        	sub1=URLEncoder.encode (sub, "UTF-8");
	        }
	        catch(Exception e)
	        {
	        	sub1=sub;
	        }
	    			if(latt.size()>1)
	    	    	{
	    	    		res+="<a href='downloadMailZipAllAttach?uid="+uid+"&subject="+sub1+"'> <div  data-tooltip='Download all Attachments' class='attachment_option_1'><img src='images/download.png'></div></a>" ;
	    	    	}
	    	    	
	    			res+= "</div> </div>";
	    	res+="  <div class='attachment_content'>";
	    	res+=" <ul> ";
	    	for(Attachment at : latt)
			{
	    	String nm=at.getAttachmentName();
	    	if(nm!=null)
	    	{
	    	
	    		if(nm!=null)
	    			nm=TextDecode.decodeUTF8Text(nm);
	    		else
	    			nm="";
			
	    	String sz=at.getAttachmentSize();
	    	if(((Integer.parseInt(sz))/(1024*1024))>0)
	    	{
	    		sz=""+((Integer.parseInt(sz))/(1024*1024))+" MB";
	    	}
	    	else if(((Integer.parseInt(sz))/(1024))>0)
	    	{
	    		sz=""+((Integer.parseInt(sz))/(1024))+" KB";
	    	}
	    	else
	    	{
	    		sz=sz+" bytes";
	    	}
	    	String nam=nm.replace('.', ';');
	    	String arr_ext[]=nam.split(";");
	    	/*System.out.println("$$$$$$$$$$$$$$$$$$$$$="+arr_ext.length);
	    	for(int j=0;j<arr_ext.length;j++)
	    	{
	    		System.out.println("**"+arr_ext[j]);
	    	}
	    	
	    	System.out.println("$$$$$$$$$$$$$$$$$$$$$");*/
	    	String ext=arr_ext[arr_ext.length-1];
	    	ext=ext.trim();
	    	String att_sml_icon="images/att_sml_file.png";
	    	String att_big_icon="images/att_big_file.png";
	    	if(ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("gif"))
	    	{
	    		att_sml_icon="images/att_sml_image.png";
	    		att_big_icon="images/att_big_image.png";
	    	}
	    	else if(ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx") )
	    	{
	    		att_sml_icon="images/att_sml_word.png";
	    		att_big_icon="images/att_big_word.png";
	    	}
	    	else if(ext.equalsIgnoreCase("ppt") || ext.equalsIgnoreCase("pptx") )
	    	{
	    		att_sml_icon="images/att_sml_ppt.png";
	    		att_big_icon="images/att_big_ppt.png";
	    	}
	    	else if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("csv") )
	    	{
	    		att_sml_icon="images/att_sml_excel.png";
	    		att_big_icon="images/att_big_excel.png";
	    	}
	    	else if(ext.equalsIgnoreCase("pdf")  )
	    	{
	    		att_sml_icon="images/att_sml_pdf.png";
	    		att_big_icon="images/att_big_pdf.png";
	    	}
	    	if(ext.equalsIgnoreCase("zip") || ext.equalsIgnoreCase("7z") || ext.equalsIgnoreCase("rar"))
	    	{
	    		att_sml_icon="images/att_sml_zip.png";
	    		att_big_icon="images/att_big_zip.png";
	    	}
	    	if(ext.equalsIgnoreCase("mp4") || ext.equalsIgnoreCase("mp3") || ext.equalsIgnoreCase("flv") || ext.equalsIgnoreCase("mkv") || ext.equalsIgnoreCase("webm") || ext.equalsIgnoreCase("ogg") || ext.equalsIgnoreCase("wav"))
	    	{
	    		att_sml_icon="images/att_sml_video.png";
	    		att_big_icon="images/att_big_video.png";
	    	}
	    	
	    	
	    	String url = "";
	    	String nm1="";
	    	try
	    	{
	    		url = "downloadMailAttachByName?uid=" +uid+ "&name=" + URLEncoder.encode (nm, "UTF-8");
	    		nm1=HtmlUtils.htmlEscape(nm);
	    	}
	    	catch(Exception e)
	    	{
	    		nm1=nm;
	    		e.printStackTrace();
	    	}
	    	
	    	res+="<li><div class='attachment_con_box'> <div class='attachment_images'> <img src='"+att_big_icon+"' /> </div>";
	    	res+=" <div class='attach_con_bottom'><img src='"+att_sml_icon+"' /> <span>"+nm+"</span> </div>";
	    	res+=" <div class='attach_mousehover'> <div class='attach_row'><img src='"+att_sml_icon+"' /> <span>"+nm+"</span>";
	    	res+=" <div class='clear'></div> <div class='attachment_size'>"+sz+"</div> </div> <div class='clear'></div>";
	    	res+="  <div class='attachment_option' ><a title='Download' href='"+url+"'> <img src='images/download.png'  /> "
	    			
	    			+ "</a>"
	    			+ "<a title='Preview' onclick='viewMailAttachByName(this.id,this.name)' id='"+uid+"' name='"+nm1+"' class='preview_icon'> <img src='images/preview.jpg'  /> </a>"
	    			+ "<a title='Save to Vault' onclick='saveEDMSMailAttachByName(this.id,this.name)' id='"+uid+"' name='"+nm+"' class='preview_icon'> <img src='images/edms_save.png'  /> </a>"
	    			+ "</div>   </div> </div> </li>";
	    	}
			}
	    	res+=" </ul> </div> </div>";
	    	
	    	/***** forword reply ************//*
	    	
	    	res+=" </div> <div class='clear'></div><div class='bottom_left' style='display: block;'></div>";
			res+="  <div class='your_option_1' onClick='mail_forward_11();' style='margin-top: 64px;'>Click here to <a style='cursor: pointer;' class='replay_li'>Reply</a> or <a style='cursor: pointer;' class='forward_li'>Forward</a></div>";
	       res+="   <div class='mail_forward_11'><div class='forward_top'><div class='forward_mail'  onClick='down_mail(event);'>";
	        res+="  <a style='cursor: pointer;' class='mail_left_for'> <div class='reply replay_display'></div>";
	        res+="  <div class='reply_all reply_all_display'></div><div class='forward forward_display'></div></a>";
	        res+="  <a style='cursor: pointer;' class='mail_right_for'> <img src='images/open_sub_menu.png'></a><div class='main_bottom_option'>";
	        res+="  <ul><li class='replay_li'><div class='reply'></div>Reply to <span>Hariom Srivastava</span></li>";
	        res+="  <li class='replay_all_li'><div class='reply_all'></div>Reply all to <span>Hariom Srivastava,Hariom Srivastava</span>+3</li>";
	        res+="  <li class='forward_li'><div class='forward'></div>Forward</li>";
	        res+="  <li class='edit'>Edit subject</li></ul> </div></div>";
	       res+="   <div class='forward_mail_left'><div class='combind_email'></div> <textarea class='forward_mail_event' ></textarea>";
	       res+="    </div> <div class='clear'></div> </div> <div class='clear'></div>";
	        res+="   <div class='forward_mid'> <textarea id='yourNavigationId'> </textarea> </div>";
	         res+="   <div class='forward_bottom'><div class='for_bottom_left'>Send </div>";
	         res+="    <div class='for_bottom_mid'> <a style='cursor: pointer;' class='font_image'>  <img src='images/a_fonts.gif'> </a>";
	         res+="    <div class='bor_1'></div>    <a style='cursor: pointer;' class='attach_image'> <img src='images/attachment.png'> </a>";
	         res+="    <a style='cursor: pointer;' class='plus'> <img src='images/plus_black.png'>   <div class='plus_option'></div>   </a> </div>";
	         res+="    <div class='for_bottom_right'><a style='cursor: pointer;' class='dustbin'><img src='images/tool.png'></a>  <div class='bor_1'></div>";
	         res+="     <a style='cursor: pointer;' class='bottom_down_1' onClick='bootom_forward(event);'><img src='images/open_sub_menu.png'></a> </div>";
	         res+="      </div> <div class='for_bottom'> </div> <div class='clear'></div>  </div> </div>   <div class='clear'></div>";
	    	*/
	    }
	    
		}
		}
		//Document doc = Jsoup.parse(res);
		//Elements links = doc.select("*");
		
		
		
		
		
		return res;
	}
	
	
	
	

	@RequestMapping(value = "/getMailContentFullPageByUid", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String listWebmailInboxfullpage(ModelMap map, Principal principal,HttpServletResponse response, HttpServletRequest request) 
	{
		String res="";
		
		// res+="<script src='js/tag.js'></script> <script src='js/event.js' ></script> <script src='js/new_forword.js'></script> ";
		 res+=" <script src='js/new_forword.js'></script> ";
		 
			/*res+="<script>";
			res+="var rulesForCssText = function (styleContent) {  ";  
			res+="var doc = document.implementation.createHTMLDocument(''),";
			res+="styleElement = document.createElement('style');  ";
			res+="styleElement.textContent = styleContent;";
			res+="doc.body.appendChild(styleElement);";
			res+="return styleElement.sheet.cssRules;};";
			res+="$('.mail_content_1 style').each(function(){";
			res+="var modfieddCss = '';	  var currentCss =  rulesForCssText($(this).html());	  $(this).html('');";
			res+="for(var i =0; i< currentCss.length;i++)";
			res+="{if(currentCss[i].cssRules != undefined && currentCss[i].media != undefined)";
			res+="{ var media ='';";
			res+="media += '@media ' + currentCss[i].media[0] +'{';";
			res+="for(var j =0; j< currentCss[i].cssRules.length;j++)";
			res+="{media+= '.mail_content_1 '  + currentCss[i].cssRules[j].cssText;}";
			res+="media+='}';modfieddCss+= media;}";
			res+="else {modfieddCss+= '.mail_content_1 '  + currentCss[i].cssText; } }";
			res+="$(this).html(modfieddCss); alert(currentCss+'------------'+modfieddCss);});";
			res+="</script>";
*/
		 
		 String search_st= request.getParameter("search_st");
		String uidstatus= request.getParameter("uidstatus");
		String uid= request.getParameter("uid");
		String folder= request.getParameter("folder");
		JSONObject obj = new JSONObject();
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
		{
			res="<$expire$>";
			 obj.put("success", "false");
			 obj.put("content", res);
			 return obj.toJSONString();
		}
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
		String fname = (String) hs.getAttribute("fname");
		
		
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@folder"+folder);
		String realPath = request.getServletContext().getRealPath("/");
	    String filePath  = realPath+"WEB-INF/view/jsp/temp/";
	    if(uidstatus==null || uidstatus.length()==0)
	    uidstatus="same";
		GetMailDisplayResponse gdres=webmailClient.displayMailContentRequest(host, port, id, pass, uid, folder,filePath, uidstatus);
		InboxDisplay inbd= gdres.getGetInboxByUid();
		if(inbd.getErrorCnt()!=null && inbd.getErrorCnt().equalsIgnoreCase("<nps>"))
		{
			if(inbd.getErrorSt()!=null && inbd.getErrorSt().length()>0)
			{
				if(inbd.getErrorSt().startsWith("java.lang.NullPointerException"))
					inbd.setErrorSt("Message not found");
				res="<error>"+inbd.getErrorSt();
				 obj.put("success", "false");
				 obj.put("content", res);
				 return obj.toJSONString();
			}
			else
			{
			res="<nps>";
			 obj.put("success", "false");
			 obj.put("content", res);
			 return obj.toJSONString();
			}
		}
		else
		{
		List<Attachment> latt=inbd.getAttachment();
		
		 GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "labelSetting");
   		 String labset=ldapres.getGetFName();
       
		
		if(latt!=null)
		{
			
		String uids="";
		String fromid="";
		String disp_from="";
		String ovr_from1="";
		String ovr_from2="";
		String sub="";
		String attch="";
		String date="";
		String dt_title="";
		String mailcnt="";
		String to="";
		String disp_to="";
		String cc="";
		String disp_cc="";
		String bcc="";
		String disp_bcc="";
		String disp_to_tit="";
		String replyto="";
		String pri="";
		
		res+="<input type='hidden' id='hid_inline_sz' value='"+inbd.getInlineimgsize()+"' />";
		res+="<input type='hidden' id='hid_attch_sz' value='"+latt.size()+"' />";
		
		//attch=inbd.getAttachment();
		pri=inbd.getMailPriority();
		
		String spam="Report Spam";
		String spam_img="images/restriction.png";
		if(folder.equalsIgnoreCase("Junk"))
		{
			 spam="Not Spam";
			 spam_img="images/safe.png";
		}
		
		String ml_flg="bottom_flag";
		boolean flg_sts=false;
		String tit_flg="Add Star";
		if(inbd.isMailFlage())
		{
			flg_sts=true;
			ml_flg="bottom_flag_red";
			tit_flg="Remove Star";
		}
		uids=inbd.getUid();
		 obj.put("uid", uids);
		 obj.put("msgno", inbd.getMsgNo());
		 obj.put("allmsgno", inbd.getAllMsgNo());
		//sub=inbd.getSubject();
		//fromid=inbd.getFromId();
		date=inbd.getSendDtae();
		dt_title=inbd.getSendDtaeTitle();
		mailcnt=inbd.getMailContent();
		
		
	
			if(inbd.getSubject()!=null && inbd.getSubject().length()>0)
				sub=TextDecode.decodeUTF8Text(inbd.getSubject());
			else
				sub="(no Subject)";
		
		
		String sub_tit=sub;
		if(sub.length()>=300)
		{
			sub=sub.substring(0, 300)+"...";
		}
		
	
			if(inbd.getFromId()!=null)
				fromid=TextDecode.decodeUTF8Text(inbd.getFromId());
			else
				fromid="";
		
			if(inbd.getFromId()!=null)
				fromid=TextDecode.decodeUTF8Text(inbd.getFromId());
			else
				fromid="";
			if(inbd.getReplyId()!=null && inbd.getReplyId().length()>0)
				replyto=TextDecode.decodeUTF8Text(inbd.getReplyId());
			else
				replyto="";

			if(replyto.equalsIgnoreCase(fromid))	
				replyto="";
			
			if(inbd.getToId()!=null)
				to=TextDecode.decodeUTF8Text(inbd.getToId());
			else
				to="";
		

			if(inbd.getCCId()!=null)
				cc=TextDecode.decodeUTF8Text(inbd.getCCId() );
			else
				cc="";
			
			if(inbd.getBCCId()!=null)
				bcc=TextDecode.decodeUTF8Text(inbd.getBCCId() );
			else
				bcc="";
		
		
		
		//System.out.println("^^^^^^^^^^^^^^^="+mailcnt);
		//to=inbd.getToId();
		//cc=inbd.getCCId();
		if(replyto!=null && replyto.length()>0)
			replyto=HtmlUtils.htmlEscape(replyto);
		disp_from=fromid;
		disp_from=disp_from.replace("<", "&lt;");
		disp_from=disp_from.replace("<", "&lt;");
		String from_img_id="";
		String fromarr[]=fromid.split("<");
		if(fromarr!=null && fromarr.length>1)
		{
			ovr_from1=fromarr[0];
			ovr_from2=fromarr[1].replace(">", "");
			from_img_id=ovr_from2;
		}
		else
		{
			ovr_from1=fromid;
			from_img_id=ovr_from1;
		}
		
		
	//	String img_myurl=hs.getAttribute("img_myurl").toString();
		String path_img="images/unnamed.png";
		String idarr[]=id.split("@");
		if(from_img_id.contains("@"+idarr[1]))
		{
		GetLdapOneAttOtrUserResponse ldapres1=webmailClient.getLdapOneAttOtrUser(ldapurl, id, pass, ldapbase, from_img_id,"jpegPhoto");
		if(ldapres1.getGetLdapAttr()!=null && !ldapres1.getGetLdapAttr().equals("") && ldapres1.getGetLdapAttr().length()>5)
		{
		path_img="data:image/jpg;base64,"+ldapres1.getGetLdapAttr();
		}
		}
        String path_img_id=from_img_id+"nomyimage_cnt";
        String path_img_full_id=from_img_id+"nomyimage_cnt_full";
		
		disp_to=to;
		disp_to=disp_to.replace("<", "&lt;");
		disp_to=disp_to.replace("<", "&lt;");
		//disp_to=disp_to.replace(",", ",<br>");
		
		disp_cc=cc;
		disp_cc=disp_cc.replace("<", "&lt;");
		disp_cc=disp_cc.replace("<", "&lt;");
		
		disp_bcc=bcc;
		disp_bcc=disp_bcc.replace("<", "&lt;");
		disp_bcc=disp_bcc.replace("<", "&lt;");
		//disp_cc=disp_cc.replace(",", ",<br>");
		
		String toarr[]=to.split(",");
		for(int i=0;i<toarr.length;i++)
		{
			String tit="";
			/*
			if(toarr[i].contains(id))
			{
				tit="Me";
			}
			else
			{*/
			String toarr1[]=toarr[i].split("<");
			if(toarr1!=null && toarr1.length>0)
			{
				String att=toarr1[0].trim();
				String toarr2[]=att.split(" ");
				if(toarr2!=null && toarr2.length>0)
				{
					tit=toarr2[0];
				}
				else
				{
					tit=att;
				}
			}
			else
			{
			tit=toarr[i];
			}
			//}
			
			if(disp_to_tit== null || disp_to_tit.equals(""))
			{
				disp_to_tit=tit;
			}
			else
			{
				disp_to_tit=disp_to_tit+", "+tit;
			}
			
		}
		
		
		disp_to=disp_to.replace("undisclosed-recipients:;",id);
		disp_to_tit=disp_to_tit.replace("undisclosed-recipients:;", id);
		
		
		String tags=inbd.getMailTag();
		
		res+="<div class='right_top_pannel'><div class='for_tool'>";
		res+="<ul><li><div class='tool_inner_box'><ul title='Back' id='menu'><li class='menu_space'><a onclick='backToMailList()'>";
		res+="<img src='images/back_option.png' ></a></li></ul></div></li>";
		res+="<li><div class='large_tool search_form_tool' style='display: block;width: 307px !important;'>";
		res+="<ul><li title='Reply' class='hidden_option'><a onclick='openReply()' style='cursor: pointer;'><img src='images/back_one.png'></a></li>";
        res+="<div class='right_border'></div> <li title='Reply All' class='hidden_option'><a onclick='openReplyAll()' style='cursor: pointer;'><img src='images/back_doble.png'></a></li>";
        res+="<div class='right_border'></div> <ul title='Forward' id='menu' style='width: 39px !important;' class='next_mail'>";
        res+="<li> <a onclick='openForword()' style='cursor: pointer;padding: 0px;'><img src='images/next.png'></a> ";
/*      res+="<ul><li> <a style='cursor: pointer;'>Forward Inline</a></li><li><a style='cursor: pointer;'>Forward As Attachment</a></li></ul> ";*/
        res+="</li></ul><div class='right_border'></div> <li title='Delete'><a onclick='moveTrashAllDisp()' style='cursor: pointer;'><img src='images/tool_delete.png'></a></li>";
        res+="<div class='right_border'></div> <li title='"+spam+"'><a onclick='moveSpamAllDisp()' style='cursor: pointer;'><img src='"+spam_img+"'></a></li> <div class='right_border'></div>";
        res+="<ul id='menu' class='next_mail more_arrow'> <li class='more'> More </li>";
        res+="<li><a style='cursor: pointer;' class='sub_menu_link'><img src='images/open_sub_menu.png'></a> <ul style='margin-left: -27px !important;'>";
        res+=" <li> <a style='cursor: pointer;' onclick='setSetectedMailUnReadDisp()'>Mark as Unread</a></li>  <li> <a style='cursor: pointer;' onclick='setSetectedMailReadDisp()'>Mark as Read</a></li>"
        		+ "<li><a style='cursor: pointer;' onclick='setSetectedMailFlagDisp()'>Add Star</a></li> <li><a style='cursor: pointer;' onclick='setSetectedMailUnFlagDisp()'>Remove Star</a></li>"
        		+ "</ul>";
        res+="</li></ul></ul></div>"
        	+ "<div class='tag_main' title='Labels' style='display: block;'>  <img src='images/tag_main.png'> <img src='images/cal-open.png' class='tag_arrow'> </div>"
        		+ "<div class='folder_view' title='Move To' style='display: block;'> <img src='images/folder_icon.png' class='folder_icon'>"
        		+ "<img src='images/cal-open.png' class='tag_arrow_1'>   </div>"
        		+ "</li></ul><div class='right_tool_part'><div class='right_tool'> <a href='inbox' title='Refresh'> <img src='images/reload.png'> </a> </div>";
        res+="<div onclick='openSettingTemp()' style='cursor: pointer;' class='right_tool_1'><ul id='menu'> <li title='Settings'> <img  src='images/setting_toll.png' class='four_margin'></li></ul></div>";
       /* res+="<li class='right_menu_1'><a style='cursor: pointer;'> <img src='images/open_sub_menu.png' style='margin-left: 8px !important;'></a>";*/
      if(search_st==null || !search_st.equalsIgnoreCase("true"))
      {
        res+="<span ><a  title='Next' style='cursor: pointer;' id='"+uids+"' class='prev_"+uids+"' name='next' onclick='findOtherMailCnt(this.id,this.name)'><div class='right_tool_1'><img src='images/next_mail.png' class='next_imag'></div></a>";
        res+="<a title='Previous' style='cursor: pointer;'  id='"+uids+"' class='next_"+uids+"' name='prev' onclick='findOtherMailCnt(this.id,this.name)'><div class='right_tool_1'><img src='images/privious_mail.png' class='next_imag'></div></a></span>";
      }
       res+="</div> </div></div>";
        
      
        
        
        res+="<div id='widget'  > <div class='full_width_mail'><div class='mail_content'><div class='mail_content_1 full_view_content' style='width: 48.5%; float: right; display: block; min-height: 75%; max-height: 95%;'> ";
        res+="<div class='mail_left_content'><div class='top_bottom_1'><h1 title='"+sub_tit+"'>"+sub+" </h1> ";
       /* res+="<div class='mail_right_1'> <a style='cursor: pointer;'><div class='printer'></div></a> <a target='_blank' href='nexttab?uid="+uid+"&folder="+folder+"'><div class='fullscreen'></div> </a>";*/
        res+="<div class='mail_right_1'> <a target='_blank' href='mailcntprint?uid="+uid+"&folder="+folder+"' style='cursor: pointer;'><div  title='Print' class='printer'></div></a> ";
/*        		+ "<a target='_blank' href='nexttab?uid="+uid+"&folder="+folder+"'><div class='fullscreen'></div> </a>";*/
        res+="</div><div class='clear'></div>";
        	
        
        
        res+="<div class='inbox_tag inbox_tag_1'>";
		
        if(tags!=null && !(tags.equals("")))
		{
			
			
			String tagnm[]=tags.split("~");
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^tags="+tags);
        	for(int j=0;j<tagnm.length;j++)
        	{
        		boolean st=false;
        		String tagcss="";
        		String tag="";
        		String tagback="";
        		if( tagnm[j].equalsIgnoreCase("$label1"))
        		{
        			tagcss="Important";
            		tag="Important";
            		tagback="#FA6855";
            		st=true;
        		}
        		else if( tagnm[j].equalsIgnoreCase("$label2"))
        		{
        			tagcss="Work";
            		tag="Work";
            		tagback="#FC9449";
            		st=true;
        		}
        		else if( tagnm[j].equalsIgnoreCase("$label3"))
        		{
        			tagcss="Personal";
            		tag="Personal";
            		tagback="#98C95D";
            		st=true;
        		}
        		else if( tagnm[j].equalsIgnoreCase("$label4"))
        		{
        			tagcss="To_Do";
            		tag="To Do";
            		tagback="#486BF7";
            		st=true;
        		}
        		else if( tagnm[j].equalsIgnoreCase("$label5"))
        		{
        			tagcss="Later";
            		tag="Later";
            		tagback="#BD48F7";
            		st=true;
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
                        //     System.out.println("~~~~~~~~~~~~~~~~~~"+tarr[0]+" ----- "+tagnm[j]);
                             if(tarr[0].equalsIgnoreCase(tagnm[j]))
                             {
                            	 st=true;
                            	 tagback="#"+tarr[1];
                            	 break;
                             }
                            }
                        }
                        }
        			
        			tag= tagnm[j].replace("_", " ");
            		tagcss= tagnm[j];
            		
        		}
        		if(st)
        		{
        			String tagid="full_"+uid+"~"+tagnm[j];
        			String tagclsid=	"full-"+uid+"~"+tagnm[j];		
        			res+="	<div id='"+tagid+"' class='in_tag_disp in_tag "+tagcss+"' style='display: block; background: "+tagback+";'><span>"+tag+"</span><div id='"+tagclsid+"' onclick='remMailTagFull(this.id)' class='close_tag'>x</div></div>";
        		}
        	}
		}
		
		
		res+="</div>";
        
        
       	res+="<div class='clear'></div> </div>";
        res+="<div class='mail_header mail_header_top' style='padding-top: 0px;'> <div class='mail_left'>"; 
        res+="<div class='images_hover'><img src='"+path_img+"' onerror='getAltImageDisp(this.id)' id='"+path_img_id+"' /><div class='images_details'><div class='mail_left_1'>";
        res+="<span>"+ovr_from1+"</span><div class='clear'></div>"+ovr_from2+"</div>";
        res+="<img src='"+path_img+"' onerror='getAltImageDisp(this.id)' id='"+path_img_full_id+"' /><div class='send_mail_deatils'><a title='Add to contacts'  name='"+disp_from+"' onclick='addInContacts(this.name)'  style='cursor: pointer;'>Add to contacts</a><a title='View all emails with this contact' name='"+disp_from+"' onclick='getAllEmails(this.name)' style='cursor: pointer;'>Emails</a>";
        /*res+="<div class='right_forw'><a style='cursor: pointer;'><div class='mail_for'><img src='images/plus_option.gif'></div></a><a <a name='"+disp_from+"' onclick='openComposeWithTo(this.name)' style='cursor: pointer;'>";*/
        res+="<div class='right_forw'> <a name='"+disp_from+"' onclick='openComposeWithTo(this.name)' title='Email this Contact' style='cursor: pointer;'>";
        res+="<img class='email_contact' src='images/mail_fow.png'></a></div></div></div> </div>";
              
		
        res+="<p class='first_p'><a style='cursor: pointer;'>"+disp_from+"</a></p>";
        res+="<div class='mail_row'><p class='last_p new_paragraph'>To "+disp_to_tit+" </p><a style='cursor: pointer;' onClick='mail_to(event);'> <div class='mail_deatil'></div> </a>";
        res+="<div class='to_me' style='display: none;'><ul><li class='comm_width'><span>From: </span></li><li> "+disp_from+"</li>";
        String disp_ton=disp_to;
		disp_ton=disp_to.replaceAll(",", ",<br>");
        res+="<div class='clear'></div><li class='comm_width'><span>To:</span></li><li>"+disp_ton+"</li>";
        if(cc!=null && !cc.equals(""))
		{
        	String disp_ccn=disp_cc;
        	disp_ccn=disp_cc.replaceAll(",", ",<br>");
        	res+="<div class='clear'></div><li class='comm_width'><span>Cc:</span></li><li>"+disp_cc+"</li>";
		}
        if(folder.equalsIgnoreCase("Sent") && bcc!=null && !bcc.equals(""))
		{
        	String disp_bccn=disp_bcc;
        	disp_bccn=disp_bcc.replaceAll(",", ",<br>");
        	res+="<div class='clear'></div><li class='comm_width'><span>Bcc:</span></li><li>"+disp_bcc+"</li>";
		}
        if(replyto!=null && !replyto.equals(""))
		{
			res+="<li class='comm_width'><span>Reply-To:</span></li><li> "+replyto+"</li>  <div class='clear'></div>";
		}
        res+="<div class='clear'></div><li class='comm_width'><span>Date:</span></li><li> "+dt_title+"</li>";
        res+="<div class='clear'></div><li class='comm_width'><span>Subject:</span></li><li title='"+sub_tit+"'>"+sub+"</li>";
     /*   res+="<div class='clear'></div><li class='comm_width'><span>mailed-by:</span></li><li>Silvereye.in</li>";
        res+=" <div class='clear'></div><li class='comm_width'><span>signed-by:</span></li><li>Silvereye.in</li>";*/
       // res+="<div class='clear'></div><li class='imag_upload'><span>Images from this sender are always displayed.</span><a style='cursor: pointer;'>Don't display from now on.</a>";
       // res+="<div class='clear'></div></li>";
        res+= "</ul></div> </div></div>";
		
        res+="<input type='hidden' id='hid_mail_disp_flg' value='"+flg_sts+"'/>";
        res+="<div class='mail_right'><div style='float:left;margin-top: 7px;'>"+date+" </div>";
        res+="<div style='cursor: pointer;' onclick='flagActionDisp("+uids+")' id='flag_div_disp_"+uids+"' title='"+tit_flg+"'  class='"+ml_flg+"'>&#9733;</div><div class='mail_option' >";
        res+="<a title='More' > <div class='option option_npmore'> <img src='images/open_sub_menu.png'></div></a>";
        res+="</div></div><div class='clear'></div></div>";
        res+="<div class='mail_content_1 mail_content_p'> <div class='mail_down_option_1'><ul>";
        res+="<li><a target='_blank' href='mailheadercntt?uid="+uid+"&folder="+folder+"' style='cursor: pointer;'><div>View Header</div></a></li><div class='clear'></div>";
        res+="<li><a style='cursor: pointer;' name='"+disp_from+"' onclick='addInContacts(this.name)'><div>Add to Contact</div></a></li>";
        res+="<li><a onclick='openReply()' style='cursor: pointer;'><div>Reply</div></a></li>";
        res+="<li><a onclick='openReplyAll()'  style='cursor: pointer;'><div>Reply All</div></a></li>";
        res+="<li><a onclick='openForword()' style='cursor: pointer;'><div>Forward</div></a></li>";
        res+="<li><a onclick='moveTrashAllDisp()' style='cursor: pointer;'><div>Delete</div></a></li>";
        res+="<li><a onclick='moveSpamAllDisp()' style='cursor: pointer;'><div>"+spam+"</div></a></li>";
        res+="<li><a onclick='setSetectedMailReadDisp()' style='cursor: pointer;'><div>Mark as Read</div></a></li>";
        res+="<li><a onclick='setSetectedMailUnReadDisp()' style='cursor: pointer;'><div>Mark as Unread</div></a></li>";
        res+="<li><a onclick='setSetectedMailFlagDisp()'  style='cursor: pointer;'><div>Add Star</div></a></li>";
        res+="<li><a onclick='setSetectedMailUnFlagDisp()' style='cursor: pointer;'><div>Remove Star</div></a></li>";
        res+="<li><a target='_blank' href='mailcntprint?uid="+uid+"&folder="+folder+"' style='cursor: pointer;'><div>Print</div></a></li>";
        res+="</ul>";
        res+="<div class='clear'></div></div>";
               
     			 
        
        if(inbd.isMailFlageRecent() && inbd.getMailReadRecId()!=null && !(inbd.getMailReadRecId().equalsIgnoreCase("")))
	    {
        	if(!tags.contains("$MDNSent"))
	    	{
        	String rec_sub="Return Receipt (displayed) -"+inbd.getSubject();
        	String rec_cnt="This is a Return Receipt for the mail that you sent to "+id+". <br><br>Note: This Return Receipt only acknowledges that the message was displayed on the recipient's computer. There is no guarantee that the recipient has read or understood the message contents. <br>";
        
        	try
        	{
        		Properties prop = new Properties();
        		prop.load(getClass().getClassLoader().getResourceAsStream("webmail.properties"));
        		rec_sub=prop.getProperty("ReturnReceiptSub")+inbd.getSubject();
            	rec_cnt=prop.getProperty("ReturnReceiptCnttPart1")+" "+id+prop.getProperty("ReturnReceiptCnttPart2");
            	
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        	res+="<div id='hid_"+inbd.getMailReadRecId()+"_sub' style='display:none;'>"+rec_sub+"</div>";
        	res+="<div id='hid_"+inbd.getMailReadRecId()+"_cnt' style='display:none;'>"+rec_cnt+"</div>";
        	res+=" <div class='new_notification'><div class='new_notification_left'><span class='notification_icon'></span>";
	    	res+="<span class='notification_message'><b>Sender</b>&nbsp;has asked to be notified when you read this message.</span></div>";
	    	res+="<div class='new_notification_right'><span id='"+inbd.getMailReadRecId()+"' name='"+uid+"'  onclick='sendRecMail(this.id,"+uid+")' class='notification_button'>Send</span> <span class='notification_button' name='"+uid+"' onclick='ignoreRecMail("+uid+")'>Ignore</span>";
	    	res+="</div><div class='clear'></div></div><div class='clear'></div>";
	    	}
	    }
        
        if(pri!=null && !pri.equals(""))
	    {
	    	 
	    	 if(pri.equalsIgnoreCase("Highest"))	
				{
	    		 res+="<p><img width='20px' height='20px' style='margin-bottom: -6px;  opacity: 0.9;' src='images/impt.png' /> This message was sent with high importance.</p>";	
				}
				else if(pri.equalsIgnoreCase("Lowest"))	
				{
				res+="<p><img width='20px' height='20px' style='margin-bottom: -6px;  opacity: 0.9;' src='images/impt.png' /> This message was sent with low importance.</p>";		
				}
	    }
        
        
        
        try
	    {
	    	if(!(mailcnt.startsWith("<pre>") && mailcnt.endsWith("</pre>")))
	    	{
	    	Document doc = Jsoup.parse(mailcnt);
	    	Elements links1 = doc.select("style");
	    	links1.remove();
	    	
	    	Element links = doc.select("*").removeAttr("class").first();
	    	mailcnt=links.html();
	    	}
	    	
	    	
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	mailcnt=inbd.getMailContent();
	    }
	    
        
        try
	    {
        	if(!(mailcnt.startsWith("<pre>") && mailcnt.endsWith("</pre>")))
	    	{
        	mailcnt= addLink(mailcnt);
	    	}
	    }
	    catch(Exception eee)
	    {
	    	
	    }
        
        if(inbd.getICSstatus()!=null && inbd.getICSstatus().equalsIgnoreCase("Yes") )
	    {
	    	boolean isnotsame=true;
	    	String toidarr[]=id.split("@");
	    	String todomain=toidarr[1];
	    	String fdomain="";
	    	String frmiid=fromid.trim();
	    	if(frmiid.endsWith(">"));
	    		frmiid=frmiid.replaceAll(">", "");
	    	frmiid=frmiid.trim();	
	    	String fromidarr[]=frmiid.split("@");
	    	if(fromidarr.length>1)
	    		fdomain=fromidarr[1];
	    	if(fdomain.length()>0 && fdomain.equalsIgnoreCase(todomain))
	    	{
	    		isnotsame=false;
	    	}
	   	if(inbd.getICSMethod()!=null && inbd.getICSMethod().equals("REQUEST"))
	    	{
	    	try
	    	{
	    	
	    		String proid=(String)hs.getAttribute("caldevProId");
	    		String surl=(String)hs.getAttribute("caldevUrl");
	    		String calnm =(String)hs.getAttribute("default_calendar");
	    		CalDav cd=new CalDav();
	    	GetInviteEventResponse rescal =  cd.getInviteEventRequest(inbd.getICScntt(), proid, surl, id, pass, calnm,isnotsame);
//			System.out.println("res : " + res.getEventList());
	    	
	    List<EventBean> elst=	rescal.getEventList().getEventList();
	    if(elst!=null && !elst.isEmpty())
	    {
	    EventBean eb=elst.get(0);
	    String sum= eb.getSummary();
	    Date sdt=eb.getStarteventdate().toGregorianCalendar().getTime();
	    String loc=eb.getLocation();
	    if(loc==null)
	    	loc="";
	    Format formatter = new SimpleDateFormat("MMM"); 
	    String cmont = formatter.format(sdt);
	    formatter = new SimpleDateFormat("dd"); 
	    String cday = formatter.format(sdt);
	    String cal_uuid=eb.getUid();
	    	
	    res+="<table width='100%' > <tbody><tr><td width='20%'>";
	    res+="<table width='125px' style='background: url(images/cal_back.png) no-repeat 0 0;     opacity: 0.9;' height='125px'>"; 
	    res+="<tbody><tr><td style=' text-align: center;    padding-top: 11px;    font-size: 25px;    color: #fff;    font-weight: bold;'>"+cmont+"</td></tr>";
	    res+="<tr rowspan='2'><td style='    text-align: center;    font-size: 55px;    font-weight: bold;    padding-bottom: 11px;'>"+cday+"</td></tr></tbody></table></td>";
	    res+="<td width='80%'><table>";
	    res+="<tr><td colspan='2' style=' font-weight: bold;'> "+sum+"</td><tr>";
	    res+="<tr><td style=' color: #888;'> When: </td> <td> "+sdt+"</td><tr>";
	    res+="<tr><td style=' color: #888;'>  Who:</td> <td> "+fromid+" </td><tr>";
	    res+="<tr><td style=' color: #888;'>  Where:</td> <td> "+loc+" </td><tr>";
	    res+="<tr><td style=' color: #888;'>  Going?:</td> <td> ";
	    res+="<div><div onclick='sendresponse(this.id)' id='yes' style='     cursor: pointer;   float: left;    width: 50px;    text-align: center;    background: #7BDA7B;    border: 1px;    border-color: #847B7B;    border-style: solid;    padding: 3px;'>Yes</div>";
	    res+="<div onclick='sendresponse(this.id)' id='maybe' style='      cursor: pointer;  float: left;    width: 50px;    text-align: center;  background: #E8E8E8;    border-bottom: 1px;    border-top: 1px;    border-left: 0px;    border-right: 0px;    border-color: #847B7B;    border-style: solid;    padding: 3px;'>Maybe</div>";
	    res+="<div onclick='sendresponse(this.id)' id='no' style='     cursor: pointer;   float: left;    width: 50px;    text-align: center;    background: #E8E8E8;    border: 1px;    border-color: #847B7B;    border-style: solid;    padding: 3px;'>No</div></div>";
	    res+="</td><tr></table></td></tr></tbody></table>";
	    res+="<input type='hidden' id='hid_cal_inv_from' value='"+fromid+"' />";
	    res+="<input type='hidden' id='hid_cal_inv_uuid' value='"+cal_uuid+"' />";
	    
	    }
	    	}
	    	catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
			}
	    }
	   	else if(inbd.getICSMethod()!=null && inbd.getICSMethod().equals("REPLY"))
	   	{
	   		if(isnotsame)
	   		{
	   		String proid=(String)hs.getAttribute("caldevProId");
    		String surl=(String)hs.getAttribute("caldevUrl");
	   		try
	    	{
	   		
	   		String icsstring=inbd.getICSString();
	   		if(icsstring!=null && icsstring.length()>0)
	   		{
	   			
	   			
	   			String uid_cal="";
	   			String atten_id="";
	   			String atten_st="";
	   			String calnm="";
	   			EventBean neweb=null;
	   			byte flcntt[]=null;
	   			String flpth="";
	   			boolean calst=false;
	   			GetEventsResponse res_rep_cal = null;
	   			if(inbd.getICScntt()!=null)
	   			{
	   				//res_rep_cal= calendarclient.getallevents(inbd.getICScntt(), "");
	   				CalDav cld=new CalDav();
	   				EventBean event=cld.buildCalEvent(inbd.getICScntt());
	   				if(event!=null)
	   				{
	   					uid_cal=event.getUid();
	   					if(event.getOldguest()!=null)
	   					{
	   						atten_id=event.getOldguest();
	   						if(atten_id!=null && atten_id.length()>1 && atten_id.startsWith(","))
	   							atten_id=atten_id.substring(1);
	   						String arrid1[]=atten_id.split("`");
		   					if(arrid1.length>1)
		   					{
		   						atten_id=arrid1[1];
		   						atten_st=arrid1[0]+"`";
		   					}
	   					}
	   				}
	   			}
	   		
	   			
	   	
	   			CalDav cldav=new CalDav();
	   			List<CalDavCalendarCollection> collList= cldav.findAllCollections( proid,  surl,  id , pass);
	   			for(CalDavCalendarCollection coll : collList)
	   			{
	   				String cpath=coll.getPath();
	   				if(cpath!=null && !cpath.startsWith(surl))
	   				{
	   					cpath=surl+cpath;
	   				}
	   				calnm=cpath;
	   				neweb= cldav.getEventDetail(proid, surl, id, pass, cpath, uid_cal,"","");
	   				if(neweb!=null)
	   				{
	   					break;
	   				}
	   				calnm="";
	   			}
	   			
	   			
	   			
	   		
	   		if(neweb!=null)
	   		{
	   			String oldid=neweb.getOldguest();
	   			String arrid[]=oldid.split(",");
	   			String attlst="";
	   			for(String aid: arrid)
	   			{
	   				if(aid==null)
	   				{
	   					aid="";
	   				}
	   				if(aid.endsWith("`"+atten_id))
	   				{
	   					String arrid1[]=aid.split("`");
	   					if(arrid1.length>1)
	   					{
	   						aid=atten_st+arrid1[1];
	   					}
	   				}
	   				
	   					if(aid.length()>0)
	   						attlst+=","+aid;
	   					
	   				
	   			}
	   			
	   			if(attlst.length()>0)
	   			{
	   				neweb.setOldguest(attlst);
	   				cldav.createEvent(proid, surl, id, pass, calnm, neweb,fname, id,uid_cal);
	   			}
	   			
	   			
	   		}
	   		
	   		}
	    	}
	   		catch(Exception e)
	   		{
	   			
	   		}
	   		}
	   	}
	   	
	    }
	    	
        StringBuilder regex = new StringBuilder("<script[^>]*>(.*?)</script>");
		int flags = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern = Pattern.compile(regex.toString(), flags);
		Matcher matcher = pattern.matcher(mailcnt);
		mailcnt=matcher.replaceAll("");
		
		StringBuilder regex1 = new StringBuilder("<script[^>]*>");
		int flags1 = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern1 = Pattern.compile(regex1.toString(), flags1);
		Matcher matcher1 = pattern1.matcher(mailcnt);
		mailcnt=matcher1.replaceAll("");
		
		
		StringBuilder regex2 = new StringBuilder("onerror=[^>]*>");
		int flags2 = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern2 = Pattern.compile(regex2.toString(), flags2);
		Matcher matcher2 = pattern2.matcher(mailcnt);
		mailcnt=matcher2.replaceAll(">");
		
        res+="<p>"+mailcnt+"</p>";
        
        
        
        
        if(latt!=null && latt.size()>0)
	    {
	    	
        	String atstr=" Attachments";
	    	if(latt.size()==1)
	    	{
	    		atstr=" Attachment";
	    	}
        res+="<div class='main_attachment'><div class='attachment_file attachment_top'><div class='left_attachment'>";
        res+="<p><span>"+latt.size()+"</span> "+atstr+"</p><div class='clear'></div> ";
        	String sub1="";	
	        try
	        {
	        	sub1=URLEncoder.encode (sub, "UTF-8");
	        }
	        catch(Exception e)
	        {
	        	sub1=sub;
	        }
        		if(latt.size()>1)
    	    	{
    	    		res+="<a href='downloadMailZipAllAttach?uid="+uid+"&subject="+sub1+"'> <div data-tooltip='Download all Attachments' class='attachment_option_1'><img src='images/download.png'></div></a>" ;
    	    	}
        res+="</div> <div class='left_attachment'></div></div>";
        res+="<div class='attachment_content'> <ul>";
        
        for(Attachment at : latt)
		{
    	String nm=at.getAttachmentName();

    		if(nm!=null)
    			nm=TextDecode.decodeUTF8Text(nm);
    		else
    			nm="";
		
    	String sz=at.getAttachmentSize();
    	if(((Integer.parseInt(sz))/(1024*1024))>0)
    	{
    		sz=""+((Integer.parseInt(sz))/(1024*1024))+" MB";
    	}
    	else if(((Integer.parseInt(sz))/(1024))>0)
    	{
    		sz=""+((Integer.parseInt(sz))/(1024))+" KB";
    	}
    	else
    	{
    		sz=sz+" bytes";
    	}
    	String nam=nm.replace('.', ';');
    	String arr_ext[]=nam.split(";");
    	
    	String ext=arr_ext[arr_ext.length-1];
    	ext=ext.trim();
    	String att_sml_icon="images/att_sml_file.png";
    	String att_big_icon="images/att_big_file.png";
    	if(ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("gif"))
    	{
    		att_sml_icon="images/att_sml_image.png";
    		att_big_icon="images/att_big_image.png";
    	}
    	else if(ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx") )
    	{
    		att_sml_icon="images/att_sml_word.png";
    		att_big_icon="images/att_big_word.png";
    	}
    	else if(ext.equalsIgnoreCase("ppt") || ext.equalsIgnoreCase("pptx") )
    	{
    		att_sml_icon="images/att_sml_ppt.png";
    		att_big_icon="images/att_big_ppt.png";
    	}
    	else if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("csv") )
    	{
    		att_sml_icon="images/att_sml_excel.png";
    		att_big_icon="images/att_big_excel.png";
    	}
    	else if(ext.equalsIgnoreCase("pdf")  )
    	{
    		att_sml_icon="images/att_sml_pdf.png";
    		att_big_icon="images/att_big_pdf.png";
    	}
    	if(ext.equalsIgnoreCase("zip") || ext.equalsIgnoreCase("7z") || ext.equalsIgnoreCase("rar"))
    	{
    		att_sml_icon="images/att_sml_zip.png";
    		att_big_icon="images/att_big_zip.png";
    	}
    	if(ext.equalsIgnoreCase("mp4") || ext.equalsIgnoreCase("mp3") || ext.equalsIgnoreCase("flv") || ext.equalsIgnoreCase("mkv") || ext.equalsIgnoreCase("webm") || ext.equalsIgnoreCase("ogg") || ext.equalsIgnoreCase("wav"))
    	{
    		att_sml_icon="images/att_sml_video.png";
    		att_big_icon="images/att_big_video.png";
    	}
        
    	String url = "";
    	String nm1="";
    	try
    	{
    		url = "downloadMailAttachByName?uid=" +uid+ "&name=" + URLEncoder.encode (nm, "UTF-8");
    		nm1=HtmlUtils.htmlEscape(nm);
    	}
    	catch(Exception e)
    	{
    		nm1=nm;
    		e.printStackTrace();
    	}
    	
        res+="<li><div class='attachment_con_box'><div class='attachment_images'> <img src='"+att_big_icon+"' /></div>";
        res+="<div class='attach_con_bottom'><img src='"+att_sml_icon+"' /> <span>"+nm+"</span> </div>";
        res+="<div class='attach_mousehover'> <div class='attach_row'><img src='"+att_sml_icon+"' /> <span>"+nm+"</span>";
        res+="<div class='clear'></div><div class='attachment_size'>"+sz+"</div> </div>";
        res+="<div class='clear'></div><div class='attachment_option'><a title='Download' href='"+url+"'> <img src='images/download.png'  /> </a> "
        		+ "<a title='Preview' onclick='viewMailAttachByName(this.id,this.name)' id='"+uid+"' name='"+nm1+"' class='preview_icon' > <img src='images/preview.jpg'  /></a>"
        		+ "<a title='Save to Vault' onclick='saveEDMSMailAttachByName(this.id,this.name)' id='"+uid+"' name='"+nm+"' class='preview_icon' > <img src='images/edms_save.png'  /></a>"
        		+ "</div> </div></div> </li>";
       
		}
        
        res+="</ul></div></div>";
        

    	/***** forword reply ************/
    	
    	/*res+=" </div> <div class='clear'></div><div class='bottom_left' style='display: block;'></div>";
		res+="  <div class='your_option_1' onClick='mail_forward_11();' style='margin-top: 64px;'>Click here to <a style='cursor: pointer;' class='replay_li'>Reply</a> or <a style='cursor: pointer;' class='forward_li'>Forward</a></div>";
       res+="   <div class='mail_forward_11'><div class='forward_top'><div class='forward_mail'  onClick='down_mail(event);'>";
        res+="  <a style='cursor: pointer;' class='mail_left_for'> <div class='reply replay_display'></div>";
        res+="  <div class='reply_all reply_all_display'></div><div class='forward forward_display'></div></a>";
        res+="  <a style='cursor: pointer;' class='mail_right_for'> <img src='images/open_sub_menu.png'></a><div class='main_bottom_option'>";
        res+="  <ul><li class='replay_li'><div class='reply'></div>Reply to <span>Hariom Srivastava</span></li>";
        res+="  <li class='replay_all_li'><div class='reply_all'></div>Reply all to <span>Hariom Srivastava,Hariom Srivastava</span>+3</li>";
        res+="  <li class='forward_li'><div class='forward'></div>Forward</li>";
        res+="  <li class='edit'>Edit subject</li></ul> </div></div>";
       res+="   <div class='forward_mail_left'><div class='combind_email'></div> <textarea class='forward_mail_event' ></textarea>";
       res+="    </div> <div class='clear'></div> </div> <div class='clear'></div>";
        res+="   <div class='forward_mid'> <textarea id='yourNavigationId'> </textarea> </div>";
         res+="   <div class='forward_bottom'><div class='for_bottom_left'>Send </div>";
         res+="    <div class='for_bottom_mid'> <a style='cursor: pointer;' class='font_image'>  <img src='images/a_fonts.gif'> </a>";
         res+="    <div class='bor_1'></div>    <a style='cursor: pointer;' class='attach_image'> <img src='images/attachment.png'> </a>";
         res+="    <a style='cursor: pointer;' class='plus'> <img src='images/plus_black.png'>   <div class='plus_option'></div>   </a> </div>";
         res+="    <div class='for_bottom_right'><a style='cursor: pointer;' class='dustbin'><img src='images/tool.png'></a>  <div class='bor_1'></div>";
         res+="     <a style='cursor: pointer;' class='bottom_down_1' onClick='bootom_forward(event);'><img src='images/open_sub_menu.png'></a> </div>";
         res+="      </div> <div class='for_bottom'> </div> <div class='clear'></div>  </div> </div>   <div class='clear'></div>";
    	*/
        
	    }
		}
		}
		 obj.put("success", "true");
		 obj.put("content", res);
		 return obj.toJSONString();
		
	}
	

	@RequestMapping(value = "/nexttab", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView nexttab(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return new ModelAndView("redirect:/inbox", map);
		String uid=request.getParameter("uid");
		String folder=request.getParameter("folder");
		map.addAttribute("webmailClient", webmailClient);
		map.addAttribute("uid", uid);
		map.addAttribute("folder", folder);
		return new ModelAndView("nexttab", map);
		
	}
	
	
	@RequestMapping(value = "/mailcntprint", method = RequestMethod.GET)

	public ModelAndView mailcntprint(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return new ModelAndView("redirect:/inbox", map);
		String uid=request.getParameter("uid");
		String folder=request.getParameter("folder");
		map.addAttribute("webmailClient", webmailClient);
		map.addAttribute("uid", uid);
		map.addAttribute("folder", folder);
		return new ModelAndView("mailcntprint", map);
		
	}
	
	
	
	@RequestMapping(value = "/mailheadercntt", method = RequestMethod.GET)

	public ModelAndView mailheadercntt(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return new ModelAndView("redirect:/inbox", map);
		String uid=request.getParameter("uid");
		String folder=request.getParameter("folder");
		map.addAttribute("webmailClient", webmailClient);
		map.addAttribute("uid", uid);
		map.addAttribute("folder", folder);
		return new ModelAndView("mailheadercntt", map);
		
	}
}
