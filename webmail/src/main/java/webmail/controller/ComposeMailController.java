package webmail.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.example.Connections;
import com.local.WebmailLDAP;
import com.sieve.manage.ManageSieveClient;
import com.sieve.manage.ManageSieveResponse;
import com.sieve.manage.examples.ConnectAndListScripts;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import net.fortuna.ical4j.connector.dav.CardDavCollection;
import net.fortuna.ical4j.vcard.VCard;
import webmail.bean.CardDav;
import webmail.bean.TextDecode;
import webmail.chatdwr.XmppChatClass;
import webmail.webservice.client.FileClient;
import webmail.webservice.client.FolderClient;
import webmail.webservice.client.WebmailClient;
import webmail.wsdl.CreateContactResponse;
import webmail.wsdl.CreateFileResponse;
import webmail.wsdl.Folder;
import webmail.wsdlnew.GetComposeMailResponse;
import webmail.wsdl.GetFileByPathResponse;
import webmail.wsdl.GetFileResponse;
import webmail.wsdl.GetFileWithOutStreamResponse;
import webmail.wsdl.GetFolderByPathResponse;
import webmail.wsdl.GetFolderResponse;
import webmail.wsdl.GetLdapFNameResponse;
import webmail.wsdl.GetMailAttachmentResponse;
import webmail.wsdlnew.GetSaveMailDraftResponse;
import webmail.wsdl.GetVCFFileResponse;
import webmail.wsdl.GetVCFLdapDirResponse;
import webmail.wsdl.GetWebmailAllMailCountResponse;
import webmail.wsdlnew.GetWebmailDeleteDraftResponse;
import webmail.wsdl.GetWebmailFolderOtherResponse;
import webmail.wsdl.GetWebmailImapquotaResponse;
import webmail.wsdl.GetWebmailUnreadMailCountResponse;
import webmail.wsdl.Imapquota;
import webmail.wsdl.MailImapFolders;
import webmail.wsdl.VCFFileAtt;
import webmail.wsdl.VCFLdapDirAtt;
import webmail.wsdl.VCFLdapDirListReturn;

import javax.naming.*;
import javax.naming.directory.*;
@Controller
public class ComposeMailController {

	
	@Autowired
	private WebmailClient webmailClient;
	
	@Autowired
	private FolderClient folderClient;
	
	@Autowired
	private FileClient fileClient;
	
	
	@RequestMapping(value = "/composepopup", method = RequestMethod.GET)
	public ModelAndView composepopup(ModelMap map, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		
		
		String id=(String)hs.getAttribute("id");
		System.out.println("*************ses="+id);
		if(id==null)
		{
			String cnt=request.getParameter("cnt");
			System.out.println("*************cnt="+cnt);
			//ses="SesExp";
			
			return new ModelAndView("redirect:/login?stat=ses", map);
		}
		
		
		
			
	 	map.addAttribute("webmailClient", webmailClient);
		
		
		
		
		return new ModelAndView("composepopup", map);
	
	}
	
	@RequestMapping(value = "/composeMailView", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	
	public String composeView(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		//System.out.println("compose controller");
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
		String sign_st=(String)hs.getAttribute("signatureStatus");
		String signature=(String)hs.getAttribute("signature");
		/*GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signatureStatus");
		String sign_st=ldapres.getGetFName();*/
		map.addAttribute("signStatus", sign_st);
		if(sign_st.equalsIgnoreCase("true"))
		{
			/*ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signature");
			String signature=ldapres.getGetFName();*/
			map.addAttribute("signature", signature);
		}
		map.addAttribute("draftStatus", "false");
		List<String> bcc_id=new  ArrayList<String>();
		List<String> cc_id=new  ArrayList<String>();
		List<String> to_id=new  ArrayList<String>();
		
		map.addAttribute("att_status", "No");
		map.addAttribute("att_cnt", "0");
		hs.removeAttribute("uploadmap");
		map.addAttribute("rep_bcc_id", bcc_id);
		map.addAttribute("rep_to_id", to_id);
		map.addAttribute("rep_cc_id", cc_id);
		
		return "compose";
	}
	
	
	
	
@RequestMapping(value = "/composeMailForword", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
	
	public 	String composeMailForword(ModelMap map, Principal principal, HttpServletRequest request,HttpServletResponse response) 
	{
		//System.out.println("composeMailForword controller");
	HttpSession hs=request.getSession();
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	String ldapurl=(String)hs.getAttribute("ldapurl");
	String ldapbase=(String)hs.getAttribute("ldapbase");
	String signatureInReply=(String)hs.getAttribute("signatureInReply");
	String port=(String)hs.getAttribute("port");
	String fdrname=request.getParameter("fdrname");
	String uid=request.getParameter("uid");
	String realPath = request.getServletContext().getRealPath("/");
    String filePath  = realPath+"WEB-INF/view/jsp/temp/";
    hs.removeAttribute("uploadmap");
	
	webmail.wsdlnew.GetMailAttachmentResponse attres=webmailClient.getMailAttachmentRequest(host, port, id, pass, uid, fdrname, filePath);
	String to_id="";
	List<String> too_id= new  ArrayList<String>();
	List<String> too_idt= attres.getGetAttachByUid().getToId();
	try {
		for(String sto: too_idt)
		{
			if(to_id.equals(""))
			{
				to_id = sto;
			}
			else
			{
				to_id +=","+sto;
			}
		
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String from_id="";
	try {
		if(attres.getGetAttachByUid().getFromId()!=null)
		from_id = HtmlUtils.htmlEscape(TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getFromId()));
		else
			from_id="";
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

	List<String> bcc_id=new  ArrayList<String>();
	List<String> cc_id=new  ArrayList<String>();
	List<String> cc_idt=attres.getGetAttachByUid().getCCId();
	String cc="";
	try {
		for(String scc: cc_idt)
		{
			if(cc.equals(""))
			{
				cc = scc;
			}
			else
			{
				cc +=","+scc;
			}
		
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	/*try {
		if(attres.getGetAttachByUid().getCCId()!=null)
		cc_id =HtmlUtils.htmlEscape( TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getCCId()));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	String sub="";
	try {
		if(attres.getGetAttachByUid().getSubject()!=null)
		sub = TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getSubject());
		else
			sub="";
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String cnt=attres.getGetAttachByUid().getMailContent();
	String hid_attch_status=attres.getGetAttachByUid().getAttachStatus();
	String dt=attres.getGetAttachByUid().getSendDtaeTitle();
	String fst_line=" ";
		int att_cnt=0;
		if(hid_attch_status.equalsIgnoreCase("Yes"))
		{
			
			List<String> attflnm= attres.getGetAttachByUid().getAttachmentNameList();
			List<String> attflnmnew= attres.getGetAttachByUid().getAttachmentNewNameList();
			List<String> attflsz= attres.getGetAttachByUid().getAttachmentSizeList();
			
			map.addAttribute("array_name", attflnm);
			map.addAttribute("array_size", attflsz);
			HashMap hm=hm=new HashMap();
			 
			 for(int i=0;i< attflnm.size() && i< attflnmnew.size(); i++ )
			 {
				 att_cnt++;
				 hm.put(attflnm.get(i), attflnmnew.get(i));
			 }
			 hs.setAttribute("uploadmap", hm); 
			
		}
		
		String fwd_cnt="---------- Forwarded message ----------<br>";
		String fwd_from=from_id.replace("<", ":");
		fwd_from=fwd_from.replace(">", "");
		//String fwd_to=to_id.replace("<", ":");
		//fwd_to=fwd_to.replace(">", "");
		fwd_from=fwd_from.replace("&lt;", ":");
		fwd_from=fwd_from.replace("&gt;", "");
		to_id=to_id.replace("&lt;", ":");
		to_id=to_id.replace("&gt;", "");
		
		fwd_cnt+="<strong>From: </strong>"+fwd_from+"<br>";
		fwd_cnt+="<strong>To: </strong>"+to_id+"<br>";
		if(cc!=null && !(cc.equalsIgnoreCase("")))
		{
			cc=cc.replace("&lt;", ":");
			cc=cc.replace("&gt;", "");
			fwd_cnt+="<strong>Cc: </strong>"+cc+"<br>";
		}
		fwd_cnt+="<strong>Date: </strong>"+dt+"<br>";
		fwd_cnt+="<strong>Subject: </strong>"+sub+"<br>";
		map.addAttribute("mailuid", uid);
		map.addAttribute("mailfolder", fdrname);
		map.addAttribute("mailtype", "forward");
		map.addAttribute("rep_bcc_id", bcc_id);
		map.addAttribute("rep_to_id", too_id);
		map.addAttribute("rep_fst_line", fst_line);
		map.addAttribute("rep_cc_id", cc_id);
		map.addAttribute("rep_sub", sub);
		map.addAttribute("rep_cnt", fwd_cnt+"<br>"+cnt);
		map.addAttribute("type", "Fwd");		
		map.addAttribute("att_status", hid_attch_status);
		map.addAttribute("att_cnt", ""+att_cnt);
		map.addAttribute("signatureInReply",signatureInReply);
		String sign_st=(String)hs.getAttribute("signatureStatus");
		String signature=(String)hs.getAttribute("signature");
		map.addAttribute("draftStatus", "false");
		/*GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signatureStatus");
		String sign_st=ldapres.getGetFName();*/
		map.addAttribute("signStatus", sign_st);
		if(sign_st.equalsIgnoreCase("true"))
		{
			/*ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signature");
			String signature=ldapres.getGetFName();*/
			map.addAttribute("signature", signature);
		}
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		return "compose";
	}
	


@RequestMapping(value = "/draftToComposeOpen", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)

public 		String draftToComposeOpen(ModelMap map, Principal principal, HttpServletRequest request, HttpServletResponse response) 
{
	
	//System.out.println("composeMailReply controller");
	HttpSession hs=request.getSession();
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	String port=(String)hs.getAttribute("port");
	String draft_mail_uid=request.getParameter("draft_mail_uid");
	hs.removeAttribute("uploadmap");
	String foldername=request.getParameter("fdrname");
	String realPath = request.getServletContext().getRealPath("/");
    String filePath  = realPath+"WEB-INF/view/jsp/temp/";
	webmail.wsdlnew.GetMailAttachmentResponse attres=webmailClient.getMailAttachmentRequest(host, port, id, pass, draft_mail_uid, foldername, filePath);
	
	/*String to_id=attres.getGetAttachByUid().getFromId();
	String cc_id=attres.getGetAttachByUid().getCCId();
	String bcc_id=attres.getGetAttachByUid().getBCCId();
	String sub=attres.getGetAttachByUid().getSubject();*/
	
	List<String> to_id=attres.getGetAttachByUid().getToId();
	/*try {
		if(attres.getGetAttachByUid().getToId()!=null)
		{
		to_id = HtmlUtils.htmlEscape(TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getToId()));
		}
		
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	
	List<String> cc_id=attres.getGetAttachByUid().getCCId();
	/*try {
		if(attres.getGetAttachByUid().getCCId()!=null)
		cc_id =HtmlUtils.htmlEscape( TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getCCId()));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	List<String> bcc_id=attres.getGetAttachByUid().getBCCId();
	/*try {
		if(attres.getGetAttachByUid().getBCCId()!=null)
		bcc_id =HtmlUtils.htmlEscape( TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getBCCId()));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	String sub="";
	try {
		if(attres.getGetAttachByUid().getSubject()!=null)
		sub = TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getSubject());
		else
			sub="";
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String cnt=attres.getGetAttachByUid().getMailContent();
	String hid_attch_status=attres.getGetAttachByUid().getAttachStatus();
	
	
	
	
	
	int att_cnt=0;
	if(hid_attch_status.equalsIgnoreCase("Yes"))
	{
		
		
		List<String> attflnm= attres.getGetAttachByUid().getAttachmentNameList();
		List<String> attflnmnew= attres.getGetAttachByUid().getAttachmentNewNameList();
		List<String> attflsz= attres.getGetAttachByUid().getAttachmentSizeList();
		
		map.addAttribute("array_name", attflnm);
		map.addAttribute("array_size", attflsz);
		 HashMap hm=hm=new HashMap();
		 
		 for(int i=0;i< attflnm.size() && i< attflnmnew.size(); i++ )
		 {
			 att_cnt++;
			 hm.put(attflnm.get(i), attflnmnew.get(i));
		 }
		 hs.setAttribute("uploadmap", hm); 
		
	}
	
	map.addAttribute("draft_mail_uid", draft_mail_uid);
	map.addAttribute("rep_to_id", to_id);
	map.addAttribute("rep_cc_id", cc_id);
	map.addAttribute("rep_bcc_id", bcc_id);
	map.addAttribute("rep_sub", sub);
	map.addAttribute("rep_cnt", cnt);
	map.addAttribute("type", "draft");
	String sign_st=(String)hs.getAttribute("signatureStatus");
	String signature=(String)hs.getAttribute("signature");
	/*GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signatureStatus");
	String sign_st=ldapres.getGetFName();*/
	map.addAttribute("signStatus", sign_st);
	if(sign_st.equalsIgnoreCase("true"))
	{
		/*ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signature");
		String signature=ldapres.getGetFName();*/
		map.addAttribute("signature", signature);
	}	
	map.addAttribute("draftStatus", "true");
	
	map.addAttribute("att_status", hid_attch_status);
	map.addAttribute("att_cnt", ""+att_cnt);
	//return new ModelAndView("compose", map);
	
	response.setContentType("text/html; charset=UTF-8");
	response.setCharacterEncoding("UTF-8");
	return "compose";
	
	
	
	
}

	



@RequestMapping(value = "/openComposeWithTo", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)

public 		ModelAndView openComposeWithTo(ModelMap map, Principal principal, HttpServletRequest request) 
{
	
//String arr[]=request.getParameterValues("rep_json_cnt");

	//System.out.println("composeMailReply controller");
	
	HttpSession hs=request.getSession();
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	String ldapurl=(String)hs.getAttribute("ldapurl");
	String ldapbase=(String)hs.getAttribute("ldapbase");
	String port=(String)hs.getAttribute("port");
	String to_id=request.getParameter("hid_to");
	String fst_line=request.getParameter("fst_line");
	hs.removeAttribute("uploadmap");
	String cc_id1=request.getParameter("hid_cc");
	String sub=request.getParameter("hid_sub");
	String cnt=request.getParameter("hid_cnt");
	String hid_attch_status=request.getParameter("hid_attch_status");
	String fdrname=request.getParameter("fdrname");
	String uid=request.getParameter("uid");
	int att_cnt=0;
	String realPath = request.getServletContext().getRealPath("/");
    String filePath  = realPath+"WEB-INF/view/jsp/temp/";
    String sign_st=(String)hs.getAttribute("signatureStatus");
	String signature=(String)hs.getAttribute("signature");
	/*if(hid_attch_status.equalsIgnoreCase("Yes"))
	{
		webmail.wsdlnew.GetMailAttachmentResponse attres=webmailClient.getMailAttachmentRequest(host, port, id, pass, uid, fdrname, filePath);
		List<String> attflnm= attres.getGetAttachByUid().getAttachmentNameList();
		List<String> attflnmnew= attres.getGetAttachByUid().getAttachmentNewNameList();
		List<String> attflsz= attres.getGetAttachByUid().getAttachmentSizeList();
		
		map.addAttribute("array_name", attflnm);
		map.addAttribute("array_size", attflsz);
		 HashMap hm=hm=new HashMap();
		 
		 for(int i=0;i< attflnm.size() && i< attflnmnew.size(); i++ )
		 {
			 att_cnt++;
			 hm.put(attflnm.get(i), attflnmnew.get(i));
		 }
		 hs.setAttribute("uploadmap", hm); 
		
	}*/
	
	List<String> too_id=new  ArrayList<String>();
	too_id.add(to_id);
	List<String> bcc_id=new  ArrayList<String>();
	List<String> cc_id=new  ArrayList<String>();
	
	map.addAttribute("rep_fst_line", fst_line);
	map.addAttribute("rep_to_id", too_id);
	map.addAttribute("rep_cc_id", cc_id);
	map.addAttribute("rep_bcc_id", bcc_id);
	map.addAttribute("rep_sub", sub);
	map.addAttribute("rep_cnt", cnt);
	map.addAttribute("type", "No");
	map.addAttribute("draftStatus", "false");
	/*GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signatureStatus");
	String sign_st=ldapres.getGetFName();*/
	map.addAttribute("signStatus", sign_st);
	if(sign_st.equalsIgnoreCase("true"))
	{
		/*ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signature");
		String signature=ldapres.getGetFName();*/
		map.addAttribute("signature", signature);
	}
	map.addAttribute("att_status", hid_attch_status);
	map.addAttribute("att_cnt", ""+att_cnt);
	return new ModelAndView("compose", map);
}




	
@RequestMapping(value = "/composeMailReply", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
	
	public 		String composeMailReply(ModelMap map, Principal principal, HttpServletRequest request, HttpServletResponse response) 
	{
		
	//String arr[]=request.getParameterValues("rep_json_cnt");
	
	//	System.out.println("composeMailReply controller");
		
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
		String port=(String)hs.getAttribute("port");
		hs.removeAttribute("uploadmap");
		String fdrname=request.getParameter("fdrname");
		String uid=request.getParameter("uid");
		String repType=request.getParameter("repType");
		String sign_st=(String)hs.getAttribute("signatureStatus");
		String signature=(String)hs.getAttribute("signature");
		String signatureInReply=(String)hs.getAttribute("signatureInReply");
		String realPath = request.getServletContext().getRealPath("/");
	    String filePath  = realPath+"WEB-INF/view/jsp/temp/";
		webmail.wsdlnew.GetMailAttachmentResponse attres=webmailClient.getMailAttachmentRequest(host, port, id, pass, uid, fdrname, filePath);
		
		String msgid="";
		msgid=attres.getGetAttachByUid().getMessageID();
		String refs="";
		refs=attres.getGetAttachByUid().getReferences();
		
		String to_id="";
		String test_to="";
		String test_to1="";
		String tmp_toid="";
		List <String> rep_to_id=new  ArrayList<String>();
		try {
			
			if(attres.getGetAttachByUid().getReplyToId()!=null)
				to_id =HtmlUtils.htmlEscape( TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getReplyToId()));
			else
				to_id="";
			if(to_id.equalsIgnoreCase("") || to_id==null)
			{
			if(attres.getGetAttachByUid().getFromId()!=null)
				to_id =HtmlUtils.htmlEscape( TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getFromId()));
			else
				to_id="";
			}
			tmp_toid=to_id;
			String arr1[]=to_id.split(" ");
			test_to=arr1[arr1.length-1];
			test_to1=test_to.replace("&lt;", "");
			test_to1=test_to1.replace("&gt;", "");
			
			
			rep_to_id.add(to_id);
			to_id="";
			if(attres.getGetAttachByUid().getToId()!=null)
			{
				
				//String tmp=HtmlUtils.htmlEscape( TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getToId()));
				List <String> tmp=attres.getGetAttachByUid().getToId();
				for(String arr: tmp)
				{
					if(to_id.equalsIgnoreCase(""))
					{
						to_id =arr;
					}
					else
					{
						to_id +=","+arr;
					}
					if(repType.equalsIgnoreCase("ReplyAll"))
					{
				if(arr!=null && arr.length()!=0 &&  !(arr.equalsIgnoreCase(test_to1)) && !(arr.equalsIgnoreCase(test_to)) && !(arr.endsWith(" "+test_to)) && !(arr.equalsIgnoreCase(id)) && !(arr.endsWith(HtmlUtils.htmlEscape("<"+id+">"))))
				{
					
					rep_to_id.add(arr);
				}
					}
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		List <String> bcc_id=new  ArrayList<String>();
		String cc_id="";
		List <String> rep_cc_id=new  ArrayList<String>();
		//if(repType.equalsIgnoreCase("ReplyAll"))
		//{
		try {
			if(attres.getGetAttachByUid().getCCId()!=null)
			{
				List<String> tmp=attres.getGetAttachByUid().getCCId();
				
				for(String arr: tmp)
				{
					if(cc_id.equalsIgnoreCase(""))
					{
					cc_id =arr;
					}
					else
					{
						cc_id +=","+arr;
					}
				if(repType.equalsIgnoreCase("ReplyAll") && arr!=null && arr.length()!=0 &&  !(arr.equalsIgnoreCase(test_to1)) && !(arr.equalsIgnoreCase(tmp_toid)) && !(arr.equalsIgnoreCase(test_to)) && !(arr.endsWith(" "+test_to)) && !(arr.equalsIgnoreCase(id)) && !(arr.endsWith(HtmlUtils.htmlEscape("<"+id+">"))))
				{
					
					rep_cc_id.add(arr);
				}
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//}
		String sub="";
		try {
			if(attres.getGetAttachByUid().getSubject()!=null)
				sub = TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getSubject());
			else
				sub="";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cnt=attres.getGetAttachByUid().getMailContent();
		String hid_attch_status=attres.getGetAttachByUid().getAttachStatus();
		String dt=attres.getGetAttachByUid().getSendDtaeTitle();
		
		String to_rep_id="";
		try {
			if(attres.getGetAttachByUid().getFromId()!=null)
				to_rep_id = TextDecode.decodeUTF8Text(attres.getGetAttachByUid().getFromId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String rep_to=to_rep_id.replace("<", ":");
		rep_to=rep_to.replace(">", "");
		
		to_id=to_id.replace("&lt;", ":");
		to_id=to_id.replace("&gt;", "");
		//String fst_line=HtmlUtils.htmlEscape(dt+","+rep_to+" wrote:");
		String fst_line="<strong>From: </strong>"+rep_to+"<br>";
		fst_line+="<strong>Sent: </strong>"+dt+"<br>";
		fst_line+="<strong>To: </strong>"+to_id+"<br>";
		if(cc_id!=null && !(cc_id.equalsIgnoreCase("")))
		{
			cc_id=cc_id.replace("&lt;", ":");
			cc_id=cc_id.replace("&gt;", "");
			fst_line+="<strong>Cc: </strong>"+cc_id+"<br>";	
		}
		fst_line+="<strong>Subject: </strong>"+sub+"<br>";
		
		
		int att_cnt=0;
		hid_attch_status="No";
		/*if(hid_attch_status.equalsIgnoreCase("Yes"))
		{
			
			
			List<String> attflnm= attres.getGetAttachByUid().getAttachmentNameList();
			List<String> attflnmnew= attres.getGetAttachByUid().getAttachmentNewNameList();
			List<String> attflsz= attres.getGetAttachByUid().getAttachmentSizeList();
			
			map.addAttribute("array_name", attflnm);
			map.addAttribute("array_size", attflsz);
			 HashMap hm=hm=new HashMap();
			 
			 for(int i=0;i< attflnm.size() && i< attflnmnew.size(); i++ )
			 {
				 att_cnt++;
				 hm.put(attflnm.get(i), attflnmnew.get(i));
			 }
			 hs.setAttribute("uploadmap", hm); 
			
		}*/
		map.addAttribute("mailuid", uid);
		map.addAttribute("mailfolder", fdrname);
		map.addAttribute("mailtype", "reply");
		map.addAttribute("msgid", msgid);
		map.addAttribute("refs", refs);
		map.addAttribute("rep_fst_line", fst_line);
		map.addAttribute("rep_to_id", rep_to_id);
		map.addAttribute("rep_cc_id", rep_cc_id);
		map.addAttribute("rep_bcc_id", bcc_id);
		map.addAttribute("rep_sub", sub);
		map.addAttribute("rep_cnt", cnt);
		map.addAttribute("type", "Re");
		map.addAttribute("signatureInReply",signatureInReply);
		
		/*GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signatureStatus");
		String sign_st=ldapres.getGetFName();*/
		map.addAttribute("signStatus", sign_st);
		if(sign_st.equalsIgnoreCase("true"))
		{
			/*ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signature");
			String signature=ldapres.getGetFName();*/
			map.addAttribute("signature", signature);
		}
		map.addAttribute("att_status", hid_attch_status);
		map.addAttribute("att_cnt", ""+att_cnt);
		map.addAttribute("draftStatus", "false");
		//return new ModelAndView("compose", map);
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		return "compose";
	}
	


@RequestMapping(value = "/deleteUploadedDoc", method = RequestMethod.GET)
@ResponseBody

public String deleteUploadedDoc(ModelMap map, Principal principal, HttpServletRequest request) {                 

	boolean status=false;
	String new_flnm="";
	//String path="C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\";
	//String path="/tmp/tomcat7-tomcat7-tmp/";
	String realPath = request.getServletContext().getRealPath("/");
	String path=realPath+"WEB-INF/view/jsp/temp/";
	String filnm=request.getParameter("filenm");
	HttpSession hs=request.getSession();
	if(hs==null || hs.getAttribute("id")==null)
		return "<$expire$>";
	HashMap hm =(HashMap)hs.getAttribute("uploadmap");
	
	 Set set = hm.entrySet();
     // Get an iterator
     Iterator i = set.iterator();
     // Display elements
    // System.out.println("!!!!!!!!!!!!!!!!!!!!");
     while(i.hasNext()) {
        Map.Entry me = (Map.Entry)i.next();
        if(me.getKey().toString().equalsIgnoreCase(filnm))
        {
        	
        	new_flnm=me.getValue().toString();
        	File file = new File(path+new_flnm);
        	try
        	{
        	status=file.delete();
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}
        break;
        }
     }
	
if(status)
  hm.remove(filnm);
 
  
  hs.setAttribute("uploadmap", hm);
 
  return ""+status;

}




@RequestMapping(value = "/viewUploadedDocPDF", method = RequestMethod.GET)
@ResponseBody

public String viewUploadedDocPDF(ModelMap map, Principal principal, HttpServletRequest request) {                 
	String uidnm=	  UUID.randomUUID().toString();
	 String path1 = request.getContextPath();
     String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
	String new_flnm="";
	//String path="C:\\Users\\nirbhay\\AppData\\Local\\Temp\\";
	//String path="/tmp/tomcat7-tomcat7-tmp/";
	String realPath = request.getServletContext().getRealPath("/");
    String path  = realPath+"WEB-INF/view/jsp/temp/";

	String filnm=request.getParameter("nm");
	String name=filnm;
	if(!(name.toLowerCase().endsWith(".txt")|| name.toLowerCase().endsWith(".html") || name.toLowerCase().endsWith(".js")|| name.toLowerCase().endsWith(".css")|| name.toLowerCase().endsWith(".odt")||  name.toLowerCase().endsWith(".ppt")||  name.toLowerCase().endsWith(".pptx")|| name.toLowerCase().endsWith(".ods")|| name.toLowerCase().endsWith(".odp") || name.toLowerCase().endsWith(".pdf") || name.toLowerCase().endsWith(".doc") || name.toLowerCase().endsWith(".docx") || name.toLowerCase().endsWith(".xlsx") || name.toLowerCase().endsWith(".xls")))
	{
		return "notsupported";
	}
	HttpSession hs=request.getSession();
	
	HashMap hm =(HashMap)hs.getAttribute("uploadmap");
	
	 Set set = hm.entrySet();
     // Get an iterator
     Iterator i = set.iterator();
     // Display elements
    // System.out.println("!!!!!!!!!!!!!!!!!!!!");
     while(i.hasNext()) {
        Map.Entry me = (Map.Entry)i.next();
        if(me.getKey().toString().equalsIgnoreCase(filnm))
        {
        	try
        	{
        	new_flnm=me.getValue().toString();
        	File file = new File(path+new_flnm);
        	
        	InputStream input = new FileInputStream(file);
             
            // String realPath = request.getServletContext().getRealPath("/");
           FileOutputStream output = new FileOutputStream(realPath+"WEB-INF/view/jsp/js/temp/"+new_flnm);
        
             byte[] buffer = new byte[4096];

             int byteRead;

             while ((byteRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, byteRead);
             }
             input.close();
             output.close();
             
             if(!(new_flnm.toLowerCase().endsWith(".pdf") || new_flnm.toLowerCase().endsWith(".PDF")))
             {
           	  java.io.File inputFile = new java.io.File(realPath+"WEB-INF/view/jsp/js/temp/"+new_flnm); //
           	    java.io.File outputFile = new java.io.File(realPath+"WEB-INF/view/jsp/js/temp/"+new_flnm+".pdf"); //
           	  /*  java.io.File inputFile = new java.io.File(basePath+"js/temp/"+uidnm+"_"+name); //
           	    java.io.File outputFile = new java.io.File(basePath+"js/temp/"+uidnm+"_"+name+".pdf"); //
*/	            	      OpenOfficeConnection connection = new      SocketOpenOfficeConnection("127.0.0.1",8100);
           	      try {
           	        connection.connect();
           	     new_flnm=new_flnm+".pdf";
           	      } 
           	      catch (Exception e) {
	            	        // TODO Auto-generated catch block
	            	        e.printStackTrace();
	            	    } 
           	      DocumentConverter     converter = new  OpenOfficeDocumentConverter(connection);
           	      converter.convert(inputFile, outputFile); // close
           	      connection.disconnect(); 
             }
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        break;
        }
     }
	
	
  
 
  return basePath+"js/temp/"+new_flnm;

}





@RequestMapping(value = "/viewUploadedDocIMG", method = RequestMethod.GET)
@ResponseBody

public String viewUploadedDocIMG(ModelMap map, Principal principal, HttpServletRequest request) {                 

	String res="";
	String new_flnm="";
	//String path="C:\\Users\\nirbhay\\AppData\\Local\\Temp\\";
	//String path="/tmp/tomcat7-tomcat7-tmp/";
	String realPath = request.getServletContext().getRealPath("/");
    String path  = realPath+"WEB-INF/view/jsp/temp/";

	String filnm=request.getParameter("nm");
	HttpSession hs=request.getSession();
	if(hs==null || hs.getAttribute("id")==null)
		return "<$expire$>";
	HashMap hm =(HashMap)hs.getAttribute("uploadmap");
	
	 Set set = hm.entrySet();
     // Get an iterator
     Iterator i = set.iterator();
     // Display elements
    // System.out.println("!!!!!!!!!!!!!!!!!!!!");
     while(i.hasNext()) {
        Map.Entry me = (Map.Entry)i.next();
        if(me.getKey().toString().equalsIgnoreCase(filnm))
        {
        	try
        	{
        	new_flnm=me.getValue().toString();
        	File file = new File(path+new_flnm);
        	
        	InputStream input = new FileInputStream(file);
        	 byte[] bytes = IOUtils.toByteArray(input);
             res = new sun.misc.BASE64Encoder().encode(bytes); 
            input.close();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        break;
        }
     }
	
	
  
 
  return res;

}

@RequestMapping(value = "/viewattachmentIMG", method = RequestMethod.GET)
public  String viewattachmentIMG(HttpServletRequest request, HttpServletResponse response) {   
	return "viewattachmentIMG";
}
	
	 @RequestMapping(value = "/upload", method = RequestMethod.POST)
	   public @ResponseBody String upload(MultipartHttpServletRequest request, HttpServletResponse response) {                 
	 
	     
		 Iterator<String> itr =  request.getFileNames();
	     HashMap hm=null;
	     HttpSession hs=request.getSession();
	 	if(hs==null || hs.getAttribute("id")==null)
	    		return "<$expire$>";
	     hm =(HashMap)hs.getAttribute("uploadmap");
	     if(hm==null)
	     {
	    	 hm=new HashMap();
	    	// System.out.println("ssssssssssaaaaaaaaavvvvvveeeeeeee");
	     }
	   
	     while (itr.hasNext()){
	     MultipartFile mpf = request.getFile(itr.next());
	     try {
	    	 String realPath = request.getServletContext().getRealPath("/");
	    	 File fl=new File(realPath+"WEB-INF/view/jsp/temp");
	    	// System.out.println(">>>>"+mpf.getOriginalFilename());
	    	int idx = mpf.getOriginalFilename().lastIndexOf('.');
           String fileExtension = idx > 0 ? mpf.getOriginalFilename().substring(idx) : ".tmp";
           File fil = File.createTempFile(mpf.getOriginalFilename(), fileExtension,fl);
          // File fil = File.createTempFile(prefix, suffix, directory);
           System.out.println("!!!!!!!!!!!!!!!!!!!!!!="+fil.getPath()+"--"+fil.getName());
           mpf.transferTo(fil);
          hm.put(mpf.getOriginalFilename(), fil.getName());
         
		} catch (IOException e)
	     {
			e.printStackTrace();
	     }
	   
	     hs.setAttribute("uploadmap", hm);
	    
	    
	     }
	  

	    
	     
	     return "true";
	 
	  }
	 
	

	 @RequestMapping(value = "/addSignature", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	   public @ResponseBody String addSignature(HttpServletRequest request, HttpServletResponse response) {                 
			
			String res="";
			HttpSession hs=request.getSession();
			if(hs==null || hs.getAttribute("id")==null)
				return "<$expire$>";
			String host=(String)hs.getAttribute("host");
			String id=(String)hs.getAttribute("id");
			String pass=(String)hs.getAttribute("pass");
			String port=(String)hs.getAttribute("port");
			String ldapurl=(String)hs.getAttribute("ldapurl");
			String ldapbase=(String)hs.getAttribute("ldapbase");
			
			String sig="";
			GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "signature");
			sig=ldapres.getGetFName();
						
			return sig;
	 }
	 
	 
	 @RequestMapping(value = "/autocomp_newest", method = RequestMethod.GET)
	   public @ResponseBody String autocomp_newest(HttpServletRequest request, HttpServletResponse response) {                 
			//System.out.println("#############hi");
		 String res="";
		 HttpSession hs=request.getSession();
			String id=(String)hs.getAttribute("id");
			String pass=(String)hs.getAttribute("pass");
		 try{      
			

			
		    	
		    	
		    	//String pass=(String)hs.getAttribute("pass");
				String ldapurl=(String)hs.getAttribute("ldapurl");
				String ldpabase=(String)hs.getAttribute("ldapbase");
				String ldaplistbase=(String)hs.getAttribute("ldaplistbase");
				String surl=(String)hs.getAttribute("caldevUrl");
				//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
				WebmailLDAP wldap=new WebmailLDAP();
				VCFLdapDirListReturn getdirres=wldap.getVCFLdapDirAtt(ldapurl, id, pass, ldpabase,"*");
		 		List<VCFLdapDirAtt> ldapDirList=getdirres.getVCFLdapDirListResult().getVCFLdapDirList();
				for(VCFLdapDirAtt ulst : ldapDirList)
				{
					if(ulst.getContactEmail()!=null && ulst.getContactEmail().length()>0)
					{
					
						if(ulst.getContactName()!=null && ulst.getContactName().trim().length()>0)
						{
						String lnm=remProbChars(ulst.getContactName().trim());
						lnm=lnm.replaceAll(",", "");
						lnm=lnm.replaceAll("\"", "");
							if(lnm!=null && lnm.length()>0)
							{
							res+=lnm +" <"+ulst.getContactEmail()+">\n";
							}
							else
							{
								res+=ulst.getContactEmail()+"\n";
							}
						}
						else
						{
							res+=ulst.getContactEmail()+"\n";
						}
					}
				}
				
				
				VCFLdapDirListReturn getdirres1=wldap.getLdapListAtt(ldapurl, id, pass, ldpabase,ldaplistbase,"*");
		 		List<VCFLdapDirAtt> ldapDirList1=getdirres1.getVCFLdapDirListResult().getVCFLdapDirList();
				for(VCFLdapDirAtt ulst : ldapDirList1)
				{
					if(ulst.getContactEmail()!=null && ulst.getContactEmail().length()>0)
					{
					
						if(ulst.getContactName()!=null && ulst.getContactName().trim().length()>0)
						{
						String lnm=remProbChars(ulst.getContactName().trim());
						lnm=lnm.replaceAll(",", "");
						lnm=lnm.replaceAll("\"", "");
							if(lnm!=null && lnm.length()>0)
							{
							res+=lnm +" <"+ulst.getContactEmail()+">\n";
							}
							else
							{
								res+=ulst.getContactEmail()+"\n";
							}
						}
						else
						{
							res+=ulst.getContactEmail()+"\n";
						}
					}
				}
		    	
		    try{
		    	
		    	CardDav carddav=new CardDav();
		    	List<CardDavCollection>colst= carddav.getAllContactFolder( "", surl,  id , pass);
		    	for(CardDavCollection crd: colst)
		    	{
		    		VCard[]vcarr= crd.getComponents();
		    		for(VCard c:vcarr)
		    		{
		    			String fname="";
		    			String email="";
		    			if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!= null )
		    		       {
		    				 email=c.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue() ;
		    		       }
		    			if(email!=null && email.length()>0)
		    			{
		    			 if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME)!= null )
		    		       {
		    				 fname=c.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME).getValue() ;
		    		       }
		    			 
		    			 if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN)!= null && fname.length()<=0)
		    		       {
		    				 fname=c.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN).getValue();
		    		       }
		    			 fname=fname.replaceAll(",", "");
		    			 fname=fname.replaceAll("\"", "");
		    			 if(fname!=null && fname.length()>0 && fname.replaceAll("null", "").trim().length()>=0)
		    			 {
		    				 fname=remProbChars(fname);
		    				 res+=fname +" <"+email+">\n";
		    			 }
		    			 else
		    			 {
		    				 res+=email+"\n";
		    			 }
		    			}
		    		}
		    		
		    	}
		    	
		    	

		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		 		
				
		    	
				// Collections.sort(li);
		    	
		    	
			
				
			
				//jQuery related start		
					
					
			
				//jQuery related end	
			
				
	 	

			    } 
			catch(Exception e){ 
	 			e.printStackTrace(); 
	 		}
	 return res;
	  }
	 
	 
	 @RequestMapping(value = "/autocomp_new", method = RequestMethod.GET)
	   public @ResponseBody String autocomp_new(HttpServletRequest request, HttpServletResponse response) {                 
			//System.out.println("#############hi");
		 String res="";
		 HttpSession hs=request.getSession();
			String id=(String)hs.getAttribute("id");
			String pass=(String)hs.getAttribute("pass");
		 try{      
			 String s[]=null;

			
		    	List li = new ArrayList();
		    	
		    	//String pass=(String)hs.getAttribute("pass");
				String ldapurl=(String)hs.getAttribute("ldapurl");
				String ldpabase=(String)hs.getAttribute("ldapbase");
				String ldaplistbase=(String)hs.getAttribute("ldaplistbase");
				String surl=(String)hs.getAttribute("caldevUrl");
				//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
				WebmailLDAP wldap=new WebmailLDAP();
				VCFLdapDirListReturn getdirres=wldap.getVCFLdapDirAtt(ldapurl, id, pass, ldpabase,"*");
		 		List<VCFLdapDirAtt> ldapDirList=getdirres.getVCFLdapDirListResult().getVCFLdapDirList();
				for(VCFLdapDirAtt ulst : ldapDirList)
				{
					if(ulst.getContactEmail()!=null && ulst.getContactEmail().length()>0)
					{
					
						if(ulst.getContactName()!=null && ulst.getContactName().trim().length()>0)
						{
						String lnm=remProbChars(ulst.getContactName().trim());
						lnm=lnm.replaceAll(",", "");
						lnm=lnm.replaceAll("\"", "");
							if(lnm!=null && lnm.length()>0)
							{
								li.add(lnm +" <"+ulst.getContactEmail()+">");
							}
							else
							{
								li.add(ulst.getContactEmail());
							}
						}
						else
						{
							li.add(ulst.getContactEmail());
						}
					}
				}
		    	
				
				VCFLdapDirListReturn getdirres1=wldap.getLdapListAtt(ldapurl, id, pass,ldpabase, ldaplistbase,"*");
		 		List<VCFLdapDirAtt> ldapDirList1=getdirres1.getVCFLdapDirListResult().getVCFLdapDirList();
				for(VCFLdapDirAtt ulst : ldapDirList1)
				{
					if(ulst.getContactEmail()!=null && ulst.getContactEmail().length()>0)
					{
					
						if(ulst.getContactName()!=null && ulst.getContactName().trim().length()>0)
						{
						String lnm=remProbChars(ulst.getContactName().trim());
						lnm=lnm.replaceAll(",", "");
						lnm=lnm.replaceAll("\"", "");
							if(lnm!=null && lnm.length()>0)
							{
								li.add(lnm +" <"+ulst.getContactEmail()+">");
							}
							else
							{
								li.add(ulst.getContactEmail());
							}
						}
						else
						{
							li.add(ulst.getContactEmail());
						}
					}
				}
				
				
		    try{
		    	
		    	CardDav carddav=new CardDav();
		    	List<CardDavCollection>colst= carddav.getAllContactFolder( "", surl,  id , pass);
		    	for(CardDavCollection crd: colst)
		    	{
		    		VCard[]vcarr= crd.getComponents();
		    		for(VCard c:vcarr)
		    		{
		    			String fname="";
		    			String email="";
		    			if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!= null )
		    		       {
		    				 email=c.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue() ;
		    		       }
		    			if(email!=null && email.length()>0)
		    			{
		    			 if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME)!= null )
		    		       {
		    				 fname=c.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME).getValue() ;
		    		       }
		    			 
		    			 if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN)!= null && fname.length()<=0)
		    		       {
		    				 fname=c.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN).getValue();
		    		       }
		    			 fname=fname.replaceAll(",", "");
		    			 fname=fname.replaceAll("\"", "");
		    			 if(fname!=null && fname.length()>0 && fname.replaceAll("null", "").trim().length()>=0)
		    			 {
		    				 fname=remProbChars(fname);
		    				 li.add(fname +" <"+email+">");
		    			 }
		    			 else
		    			 {
		    				 li.add(email);
		    			 }
		    			}
		    		}
		    		
		    	}
		    	
		    	/*
		    	 String path="/"+id+"/Contacts";
		  		GetFolderResponse folderResponse = folderClient.getFolderRequest(path,id,pass);
		  		List<Folder> folderList = folderResponse.getGetFoldersByParentFolder().getFolderListResult().getFolderList();
		  		
		  		
		         
		 		for ( Folder fEntry : folderList) {
		 			
		 			String contactFdrname=fEntry.getFolderName();
		 			
		 			GetFileResponse fileResponse=fileClient.getFileRequest(path+"/"+contactFdrname, id,pass);
					List<webmail.wsdl.File> fileList=fileResponse.getGetFilesByParentFile().getFileListResult().getFileList();
		 			for(webmail.wsdl.File fllst: fileList)
		 			{
		 				System.out.println("%%%%%%%%%%%%% file name="+fllst.getFileName()); 
		 				if(fllst.getFileName()!=null && !(fllst.getFileName().equals("")))
		 				{
		 					String flname=fllst.getFileName();
		 					
		 					String name_email = flname.substring(0, flname.lastIndexOf("_"));
		 					
		 					int dolorIndex = name_email.indexOf("$");
		 					if(dolorIndex > 0)
		 					{
		 						String name = name_email.substring(dolorIndex + 1).trim();
		 						if(!name.equals(""))
		 						{
		 							li.add(name_email.substring(dolorIndex + 1) +" &lt;"+name_email.substring(0, dolorIndex)+"&gt;");
		 						}
		 						else
		 						{
		 							li.add(name_email.substring(0, dolorIndex));
		 						}
		 						
		 					}
		 					else
		 					{
		 						li.add(name_email);
		 					}

		 				}
		 			}
		 			
		 		}*/

		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		 		
				
		    	
				// Collections.sort(li);
		    	
		    	
			
				
				String[] str = new String[li.size()];			
				Iterator it = li.iterator();
				
				int i = 0;
				while(it.hasNext())
				{
					String p = (String)it.next();	
					str[i] = p;
					i++;
				}
			
				//jQuery related start		
					String query = (String)request.getParameter("q");
					
					int cnt=1;
				
					for(int j=0;j<str.length;j++)
					{
						
						if(str[j].toUpperCase().contains(query.toUpperCase()))
						{
							
							res+=str[j]+"\n";
							if(cnt>=1200)
								break;
							cnt++;
							
						}
						
					}
				//jQuery related end	
			
				
	 	

			    } 
			catch(Exception e){ 
	 			e.printStackTrace(); 
	 		}
		// System.out.println("np>>>"+res.split("\n").length);
		 
	 return res;
	  }
	 
	 
	 

	 @RequestMapping(value = "/autocomp", method = RequestMethod.GET)
	   public @ResponseBody String autocomp(HttpServletRequest request, HttpServletResponse response) {                 
			//System.out.println("#############hi");
		 String res="";
		 HttpSession hs=request.getSession();
			String id=(String)hs.getAttribute("id");
			String pass=(String)hs.getAttribute("pass");
		 try{      
			 String s[]=null;

			
		    	List li = new ArrayList();
		    	
		    	//String pass=(String)hs.getAttribute("pass");
				String ldapurl=(String)hs.getAttribute("ldapurl");
				String ldpabase=(String)hs.getAttribute("ldapbase");
				String ldaplistbase=(String)hs.getAttribute("ldaplistbase");
				String surl=(String)hs.getAttribute("caldevUrl");
				//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
				WebmailLDAP wldap=new WebmailLDAP();
				VCFLdapDirListReturn getdirres=wldap.getVCFLdapDirAtt(ldapurl, id, pass, ldpabase,"*");
		 		List<VCFLdapDirAtt> ldapDirList=getdirres.getVCFLdapDirListResult().getVCFLdapDirList();
				for(VCFLdapDirAtt ulst : ldapDirList)
				{
					li.add(ulst.getContactEmail());
				}
				
				VCFLdapDirListReturn getdirres1=wldap.getLdapListAtt(ldapurl, id, pass,ldpabase, ldaplistbase,"*");
		 		List<VCFLdapDirAtt> ldapDirList1=getdirres1.getVCFLdapDirListResult().getVCFLdapDirList();
				for(VCFLdapDirAtt ulst : ldapDirList1)
				{
					li.add(ulst.getContactEmail());
				}
		    	
		    try{
		    	
		    	CardDav carddav=new CardDav();
		    	List<CardDavCollection>colst= carddav.getAllContactFolder( "", surl,  id , pass);
		    	for(CardDavCollection crd: colst)
		    	{
		    		VCard[]vcarr= crd.getComponents();
		    		for(VCard c:vcarr)
		    		{
		    			String email="";
		    			if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!= null )
		    		       {
		    				 email=c.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue() ;
		    		       }
		    			if(email!=null && email.length()>0)
		    			{
		    				 li.add(email);
		    			}
		    		}
		    		
		    	}
		    	
		    	

		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		 		
				
		    	
				 Collections.sort(li);
		    	
		    	
			
				
				String[] str = new String[li.size()];			
				Iterator it = li.iterator();
				
				int i = 0;
				while(it.hasNext())
				{
					String p = (String)it.next();	
					str[i] = p;
					i++;
				}
			
				//jQuery related start		
					String query = (String)request.getParameter("q");
					
					int cnt=1;
				
					for(int j=0;j<str.length;j++)
					{
						if(str[j].toUpperCase().startsWith(query.toUpperCase()))
						{
							res+=str[j]+"\n";
							if(cnt>=1200)
								break;
							cnt++;
						}
					}
				//jQuery related end	
			
				
	 	

			    } 
			catch(Exception e){ 
	 			e.printStackTrace(); 
	 		}
	 return res;
	  }
	
	
	

	 @RequestMapping(value = "/autocompldap", method = RequestMethod.GET)
	   public @ResponseBody String autocompldap(HttpServletRequest request, HttpServletResponse response) {                 
			//System.out.println("#############hi");
		 String res="";
		 HttpSession hs=request.getSession();
			String id=(String)hs.getAttribute("id");
			String pass=(String)hs.getAttribute("pass");
		 try{      
			 String s[]=null;

			
		    	List li = new ArrayList();
		    
				//String pass=(String)hs.getAttribute("pass");
				String ldapurl=(String)hs.getAttribute("ldapurl");
				String ldpabase=(String)hs.getAttribute("ldapbase");
				//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
				WebmailLDAP wldap=new WebmailLDAP();
				VCFLdapDirListReturn getdirres=wldap.getVCFLdapDirAtt(ldapurl, id, pass, ldpabase,"*");
		 		List<VCFLdapDirAtt> ldapDirList=getdirres.getVCFLdapDirListResult().getVCFLdapDirList();
				for(VCFLdapDirAtt ulst : ldapDirList)
				{
					li.add(ulst.getContactEmail());
				}
		    	
				
				
				 Collections.sort(li);
		    	
		    	
			
				
				String[] str = new String[li.size()];			
				Iterator it = li.iterator();
				
				int i = 0;
				while(it.hasNext())
				{
					String p = (String)it.next();	
					str[i] = p;
					i++;
				}
			
				//jQuery related start		
					String query = (String)request.getParameter("q");
					
					int cnt=1;
				
					for(int j=0;j<str.length;j++)
					{
						if(str[j].toUpperCase().startsWith(query.toUpperCase()))
						{
							res+=str[j]+"\n";
							if(cnt>=1200)
								break;
							cnt++;
						}
					}
				//jQuery related end	
			
				
	 	

			    } 
			catch(Exception e){ 
	 			e.printStackTrace(); 
	 		}
	 return res;
	  }
	
	 
	 
	 
	 
	@RequestMapping(value = "/sendComposeMail", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String sendCompose(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		
	String status="true";
	HttpSession hs=request.getSession();
	if(hs==null || hs.getAttribute("id")==null)
		return "<$expire$>";
		String draftuid=request.getParameter("draftuid");
		String to1=request.getParameter("to");
		String to="";
		String arrto[]=to1.split(",");
		for(int i=0;i< arrto.length;i++)
		{
			if(arrto[i].indexOf("<")>=0)
			{
				String arrto1[]=arrto[i].split("<");
				if(arrto1.length>1)
				{
					try {
						arrto[i]= MimeUtility.encodeText(arrto1[0], "utf-8", "B")+"<"+arrto1[1];
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(to.equalsIgnoreCase(""))
			{
				to=arrto[i];
			}
			else
			{
				to+=","+arrto[i];
			}
		}
		
		
		
		String cc1=request.getParameter("cc");
		String cc="";
		String arrcc[]=cc1.split(",");
		for(int i=0;i< arrcc.length;i++)
		{
			if(arrcc[i].indexOf("<")>=0)
			{
				String arrcc1[]=arrcc[i].split("<");
				if(arrcc1.length>1)
				{
					try {
						arrcc[i]= MimeUtility.encodeText(arrcc1[0], "utf-8", "B")+"<"+arrcc1[1];
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(cc.equalsIgnoreCase(""))
			{
				cc=arrcc[i];
			}
			else
			{
				cc+=","+arrcc[i];
			}
		}
		
		
		
		String bcc1=request.getParameter("bcc");
		String bcc="";
		String arrbcc[]=bcc1.split(",");
		for(int i=0;i< arrbcc.length;i++)
		{
			if(arrbcc[i].indexOf("<")>=0)
			{
				String arrbcc1[]=arrbcc[i].split("<");
				if(arrbcc1.length>1)
				{
					try {
						arrbcc[i]= MimeUtility.encodeText(arrbcc1[0], "utf-8", "B")+"<"+arrbcc1[1];
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(bcc.equalsIgnoreCase(""))
			{
				bcc=arrbcc[i];
			}
			else
			{
				bcc+=","+arrbcc[i];
			}
		}
		
		
		
		
		String sub="";
		try {
			sub = MimeUtility.encodeText(request.getParameter("sub"), "utf-8", "B");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cntt=request.getParameter("cntt");
		String pri=request.getParameter("ml_priority");
		String readrec=request.getParameter("read_rec");
		String dsn=request.getParameter("hid_dsn_status");
		boolean dsn_status=false;
		if(dsn!=null && dsn.length()>0 && dsn.trim().equalsIgnoreCase("1"))
			dsn_status=true;
		String texttype=request.getParameter("texttype");
		if(texttype==null || texttype=="" )
		{
			texttype	="html";
		}
		String ipAddress=null;
		String getWay = request.getHeader("VIA");   // Gateway
		ipAddress = request.getHeader("X-FORWARDED-FOR");   // proxy
		if(ipAddress==null)
		{
		    ipAddress = request.getRemoteAddr();
		    if(ipAddress!=null && !ipAddress.equalsIgnoreCase(""))
		    {
		    	ipAddress="["+ipAddress+"]";
		    }
		}
		System.out.println("getWay: "+getWay);
		System.out.println("IP Address: "+ipAddress);
		
		String hid_old_msg_id=request.getParameter("hid_old_msg_id");
		String hid_msg_ref=request.getParameter("hid_msg_ref");
		
		boolean saveSent=true;
		
		
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String smtpport=(String)hs.getAttribute("smtpport");
		String imapport=(String)hs.getAttribute("port");
		String fromname=(String)hs.getAttribute("fname");
		String xmailer=(String)hs.getAttribute("XMailer");
		String mailReplyTo=(String)hs.getAttribute("mailReplyTo");
		
		boolean isatach=true;
		
		 HashMap hm=null;
		 List<String> attachflname=new ArrayList<String>();
		 List<String> attachnewflname=new ArrayList<String>();
	    // HttpSession hs=request.getSession();
	     hm =(HashMap)hs.getAttribute("uploadmap");
	     if(hm==null)
	     {
	    	 isatach=false;
	    	// hm=new HashMap();
	    	// System.out.println("ssssssssssaaaaaaaaavvvvvveeeeeeee");
	     }
	     else
	     {
	    	 Set set = hm.entrySet();
		      // Get an iterator
		      Iterator i = set.iterator();
		      // Display elements
		     // System.out.println("!!!!!!!!!!!!!!!!!!!!");
		      while(i.hasNext()) {
		         Map.Entry me = (Map.Entry)i.next();
		         attachflname.add(me.getKey().toString());
		         attachnewflname.add(me.getValue().toString());
		         //System.out.println(me.getKey() + ":----- "+me.getValue());
		        
		      }
		    //  System.out.println("!!!!!!!!!!!!!!!!!!!!");
	     }
		
	     String realPath = request.getServletContext().getRealPath("/");
	     String filePath  = realPath+"WEB-INF/view/jsp/temp/";
		GetComposeMailResponse res=webmailClient.comoseMailRequest(mailReplyTo,hid_old_msg_id,hid_msg_ref,ipAddress,xmailer,draftuid, host, smtpport,imapport,saveSent, id, pass, fromname, to, cc, bcc, sub, cntt, isatach, attachflname, attachnewflname,readrec,dsn_status,pri,texttype, filePath);
		status=res.getSetComposeStatus();
	
		if(status.equalsIgnoreCase("true"))
		{
			/*if(to!=null && !to.equals(""))
			{
				addCollectedContact(to, id, pass);
			}
			if(cc!=null && !cc.equals(""))
			{
				addCollectedContact(cc, id, pass);
			}
			if(bcc!=null && !bcc.equals(""))
			{
				addCollectedContact(bcc, id, pass);
			}*/
			/*try
			{
				hs.removeAttribute("uploadmap");
			}
			catch(Exception eee){eee.printStackTrace();}*/
		}
		System.out.println(status);
		/*String arr_st[]=status.split(";");
		status=arr_st[0];*/
		return status;
	}
	
	
	@RequestMapping(value = "/sendComposeMailPostAjax", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String sendComposeMailPostAjax(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String status="true";
		/*String to=request.getParameter("to");
		String cc=request.getParameter("cc");
		String bcc=request.getParameter("bcc");*/
		
		
		
		boolean isatach=true;
		
		 HashMap hm=null;
		 List<String> attachflname=new ArrayList<String>();
		 List<String> attachnewflname=new ArrayList<String>();
	    // HttpSession hs=request.getSession();
	     hm =(HashMap)hs.getAttribute("uploadmap");
	     if(hm==null)
	     {
	    	 isatach=false;
	    	// hm=new HashMap();
	    	// System.out.println("ssssssssssaaaaaaaaavvvvvveeeeeeee");
	     }
	     else
	     {
	    	 Set set = hm.entrySet();
		      // Get an iterator
		      Iterator i = set.iterator();
		      // Display elements
		     // System.out.println("!!!!!!!!!!!!!!!!!!!!");
		      while(i.hasNext()) {
		         Map.Entry me = (Map.Entry)i.next();
		         attachflname.add(me.getKey().toString());
		         attachnewflname.add(me.getValue().toString());
		         //System.out.println(me.getKey() + ":----- "+me.getValue());
		        
		      }
		    //  System.out.println("!!!!!!!!!!!!!!!!!!!!");
	     }
		
	     try
			{
				hs.removeAttribute("uploadmap");
			}
			catch(Exception eee){eee.printStackTrace();}
		
		/*String status="true";
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";*/
			String draftuid=request.getParameter("draftuid");
			String to1=request.getParameter("to");
			String to="";
			String arrto[]=to1.split(",");
			for(int i=0;i< arrto.length;i++)
			{
				if(arrto[i].indexOf("<")>=0)
				{
					String arrto1[]=arrto[i].split("<");
					if(arrto1.length>1)
					{
						try {
							arrto[i]= MimeUtility.encodeText(arrto1[0], "utf-8", "B")+"<"+arrto1[1];
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				if(to.equalsIgnoreCase(""))
				{
					to=arrto[i];
				}
				else
				{
					to+=","+arrto[i];
				}
			}
			
			
			
			String cc1=request.getParameter("cc");
			String cc="";
			String arrcc[]=cc1.split(",");
			for(int i=0;i< arrcc.length;i++)
			{
				if(arrcc[i].indexOf("<")>=0)
				{
					String arrcc1[]=arrcc[i].split("<");
					if(arrcc1.length>1)
					{
						try {
							arrcc[i]= MimeUtility.encodeText(arrcc1[0], "utf-8", "B")+"<"+arrcc1[1];
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				if(cc.equalsIgnoreCase(""))
				{
					cc=arrcc[i];
				}
				else
				{
					cc+=","+arrcc[i];
				}
			}
			
			
			
			String bcc1=request.getParameter("bcc");
			String bcc="";
			String arrbcc[]=bcc1.split(",");
			for(int i=0;i< arrbcc.length;i++)
			{
				if(arrbcc[i].indexOf("<")>=0)
				{
					String arrbcc1[]=arrbcc[i].split("<");
					if(arrbcc1.length>1)
					{
						try {
							arrbcc[i]= MimeUtility.encodeText(arrbcc1[0], "utf-8", "B")+"<"+arrbcc1[1];
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				if(bcc.equalsIgnoreCase(""))
				{
					bcc=arrbcc[i];
				}
				else
				{
					bcc+=","+arrbcc[i];
				}
			}
			
			
			
			
			String sub="";
			try {
				sub = MimeUtility.encodeText(request.getParameter("sub"), "utf-8", "B");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String cntt=request.getParameter("cntt");
			String pri=request.getParameter("ml_priority");
			String readrec=request.getParameter("read_rec");
			String dsn=request.getParameter("hid_dsn_status");
			boolean dsn_status=false;
			if(dsn!=null && dsn.length()>0 && dsn.trim().equalsIgnoreCase("1"))
				dsn_status=true;
			String texttype=request.getParameter("texttype");
			if(texttype==null || texttype=="" )
			{
				texttype	="html";
			}
			String ipAddress=null;
			String getWay = request.getHeader("VIA");   // Gateway
			ipAddress = request.getHeader("X-FORWARDED-FOR");   // proxy
			if(ipAddress==null)
			{
			    ipAddress = request.getRemoteAddr();
			    if(ipAddress!=null && !ipAddress.equalsIgnoreCase(""))
			    {
			    	ipAddress="["+ipAddress+"]";
			    }
			}
			System.out.println("getWay: "+getWay);
			System.out.println("IP Address: "+ipAddress);
			
			String hid_old_msg_id=request.getParameter("hid_old_msg_id");
			String hid_msg_ref=request.getParameter("hid_msg_ref");
			
			boolean saveSent=true;
			
			
			String host=(String)hs.getAttribute("host");
		/*	String id=(String)hs.getAttribute("id");
			String pass=(String)hs.getAttribute("pass");*/
			String smtpport=(String)hs.getAttribute("smtpport");
			String imapport=(String)hs.getAttribute("port");
			String fromname=(String)hs.getAttribute("fname");
			String xmailer=(String)hs.getAttribute("XMailer");
			String mailReplyTo=(String)hs.getAttribute("mailReplyTo");
			
			
			
		     String realPath = request.getServletContext().getRealPath("/");
		     String filePath  = realPath+"WEB-INF/view/jsp/temp/";
			GetComposeMailResponse res=webmailClient.comoseMailRequestAfter(mailReplyTo,hid_old_msg_id,hid_msg_ref,ipAddress,xmailer,draftuid, host, smtpport,imapport,saveSent, id, pass, fromname, to, cc, bcc, sub, cntt, isatach, attachflname, attachnewflname,readrec,dsn_status,pri,texttype, filePath);
			status=res.getSetComposeStatus();
		
			if(status.equalsIgnoreCase("true"))
			{
				/*if(to!=null && !to.equals(""))
				{
					addCollectedContact(to, id, pass);
				}
				if(cc!=null && !cc.equals(""))
				{
					addCollectedContact(cc, id, pass);
				}
				if(bcc!=null && !bcc.equals(""))
				{
					addCollectedContact(bcc, id, pass);
				}*/
				
			}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return status;
	}
	
	
	@RequestMapping(value = "/newAtuoCollect", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String newAtuoCollect(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String status="true";
		String to=request.getParameter("to");
		String cc=request.getParameter("cc");
		String bcc=request.getParameter("bcc");
		
		if(to!=null && !to.equals(""))
		{
			addCollectedContact(to, id, pass,surl);
		}
		if(cc!=null && !cc.equals(""))
		{
			addCollectedContact(cc, id, pass,surl);
		}
		if(bcc!=null && !bcc.equals(""))
		{
			addCollectedContact(bcc, id, pass,surl);
		}
		
		return status;
	}
	

	public void addCollectedContact(String toid, String id, String pass,String surl)
	{
		try
		{
			String arrto[]=toid.split(",");
			for(int k=0;k< arrto.length; k++)
			{
				String fullname="";
				String email="";
				String sto=arrto[k];
				String pre= "";
				String fnm= "";
				String mnm= "";
				String lnm= "";
				String suf="";
				
				boolean st=	sto.contains("<");
				if(st)
				{
					sto=sto.replace(">", "");
					String arr[]=sto.split("<");
					if(arr.length>=2)
					{
						fullname=arr[0].trim();
						email=arr[1].trim();
					}
				}
				else
				{
					email=sto;
					String arr[]=sto.split("@");
					if(arr.length>=1)
					{
						fullname=arr[0];
					}
				}
				email=email.replaceAll("<", "");
				email=email.replaceAll(">", "");
				fullname=fullname.replaceAll("\"", "");
				fullname=fullname.replaceAll("'", "");
				String arr1[]=email.split("@");
				String arr2[]=id.split("@");
				if(arr1.length==2)
				{
					if(!arr1[1].equalsIgnoreCase(arr2[1]))
					{
						
						
						 try{
							 	int flg=0;
						    	CardDav carddav=new CardDav();
						    	List<CardDavCollection>colst= carddav.getAllContactFolder( "", surl,  id , pass);
						    	for(CardDavCollection crd: colst)
						    	{
						    		VCard[]vcarr= crd.getComponents();
						    		for(VCard c:vcarr)
						    		{
						    			
						    			if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!= null )
						    		       {
						    				 if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue().toString().equalsIgnoreCase(email) )
						    				 {
						    					 flg=1;
													break;
						    				 }
						    		       }
						    			
						    		}
						    		if(flg==1)
						    		{
						    			break;
						    		}
						    	}
						    	
						    	if(flg==0)
						    	{
						    		String conid=surl+"/caldav.php/"+id+"/collected/";
						    		carddav.addAtuoContactval(surl, id, pass, fullname, email, conid);
						    	}
						 }
						 catch(Exception e)
						 {
							 e.printStackTrace();
						 }
						
						/*String parentFolder = "/"+id+"/Contacts/Personal Contacts";
						GetVCFFileResponse fileResponse =fileClient.getVCFFileRequest(parentFolder, id,pass);
						List<VCFFileAtt> vcffileList=fileResponse.getGetVCFFilesByParentFile().getVCFFileListResult().getVCFFileList();
						int flg=0;
						for(VCFFileAtt vfclst: vcffileList )
						{
							if(vfclst.getContactEmail().equalsIgnoreCase(email))
							{
								flg=1;
								break;
							}
						}
						if(flg==0)
						{
							parentFolder = "/"+id+"/Contacts/Collected Contacts";
							fileResponse =fileClient.getVCFFileRequest(parentFolder, id,pass);
							vcffileList=fileResponse.getGetVCFFilesByParentFile().getVCFFileListResult().getVCFFileList();
							
							for(VCFFileAtt vfclst: vcffileList )
							{
								if(vfclst.getContactEmail().equalsIgnoreCase(email))
								{
									flg=1;
									break;
								}
							}
						}
						*/
						
							/*
							if(flg==0)
							{
						
						String arr[]=fullname.split(" ");
						if(arr.length==1)
						{
							if(arr[0]!=null && !arr[0].equals(""))
							{
								 pre= "";
								fnm= arr[0];
								mnm= "";
								lnm= "";
								suf="";
							}
							else
							{
								pre= "";
								fnm= "";
								mnm= "";
								lnm= "";
								suf="";
							}
						}
						else if(arr.length==2)
						{
							pre= "";
							fnm= arr[0];
							mnm= "";
							lnm= arr[1];
							suf="";
						}
						else if(arr.length>2)
						{
							int i=0;
							int l=1;
							if(arr[i].equalsIgnoreCase(pre))
								{
								i++;
								fnm=arr[i];
								}
							else
								{
								pre="";
								fnm=arr[i];
								}
							
							
							if(arr[arr.length-l].equalsIgnoreCase(suf))
								{
								
								l++;
								lnm=arr[arr.length-l];
								
								}
							else
								{
								suf="";
								lnm=arr[arr.length-l];
								}
							
							int j=1;
							String mid="";
							for(i++;i< arr.length-l; i++,j++)
								{
								if(arr[i]!=" ")
									{
								if(j==1)
									{
									mid=arr[i];
									}
								else
									{
									mid=mid+" "+arr[i];
									}
									}
								}
							mnm=mid;
						}
						
						
						parentFolder = "/"+id+"/Contacts/Collected Contacts";
						List <Byte> Bt_img=new ArrayList<Byte>();
						String vcffilename="";
						 UUID uuid = UUID.randomUUID();
					     String randomUUIDString = uuid.toString();
					     vcffilename=email+"$"+fullname+"_"+randomUUIDString+".vcf";
					     CreateContactResponse resp= webmailClient.getVCFIOStream(vcffilename,"", fullname, "", "", email, "", "", "", "", "", "", "", pre, fnm, mnm, lnm, suf,Bt_img);
					     String iostrm=resp.getGetVFCIOStream();
					     InputStream is=IOUtils.toInputStream(iostrm);
					    
					     try {
					    byte[]	 iostream=org.apache.commons.codec.binary.Base64.encodeBase64(IOUtils.toByteArray(is));
					    //	 System.out.println("^^^^^^^^^^^^^^^^^^"+resp.getGetVFCIOStream());
						     CreateFileResponse cfileres=fileClient.createFile(vcffilename, parentFolder, id,pass,"", "",iostream,0);
						String   res=""+ cfileres.isSuccess();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}*/
					}
					}
				}				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	
	@RequestMapping(value = "/mailSaveInDraft", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public  String autoSaveDraft(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		String sendUid="";
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		try
		{
		String uid=request.getParameter("uid");
		String to=request.getParameter("to");
		String cc=request.getParameter("cc");
		String bcc=request.getParameter("bcc");
		String sub=request.getParameter("sub");
		String cntt=request.getParameter("cntt");
		String texttype=request.getParameter("texttype");
		if(texttype==null || texttype=="" )
		{
			texttype	="html";
		}
		String ipAddress=null;
		String getWay = request.getHeader("VIA");   // Gateway
		ipAddress = request.getHeader("X-FORWARDED-FOR");   // proxy
		if(ipAddress==null)
		{
		    ipAddress = request.getRemoteAddr();
		    if(ipAddress!=null && !ipAddress.equalsIgnoreCase(""))
		    {
		    	ipAddress="["+ipAddress+"]";
		    }
		}
		//System.out.println("getWay: "+getWay);
		//System.out.println("IP Address: "+ipAddress);
		
		
		
		
		
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String smtpport=(String)hs.getAttribute("smtpport");
		String imapport=(String)hs.getAttribute("port");
		String fromname=(String)hs.getAttribute("fname");
		String xmailer=(String)hs.getAttribute("XMailer");
		
		boolean isatach=true;
		 HashMap hm=null;
		 List<String> attachflname=new ArrayList<String>();
		 List<String> attachnewflname=new ArrayList<String>();
	    // HttpSession hs=request.getSession();
	     hm =(HashMap)hs.getAttribute("uploadmap");
	     if(hm==null)
	     {
	    	 isatach=false;
	    	// hm=new HashMap();
	    	// System.out.println("ssssssssssaaaaaaaaavvvvvveeeeeeee");
	     }
	     else
	     {
	    	 Set set = hm.entrySet();
		      // Get an iterator
		      Iterator i = set.iterator();
		      // Display elements
		     // System.out.println("!!!!!!!!!!!!!!!!!!!!");
		      while(i.hasNext()) {
		         Map.Entry me = (Map.Entry)i.next();
		         attachflname.add(me.getKey().toString());
		         attachnewflname.add(me.getValue().toString());
		         //System.out.println(me.getKey() + ":----- "+me.getValue());
		        
		      }
		    //  System.out.println("!!!!!!!!!!!!!!!!!!!!");
	     }
	     
	     if(attachflname.size()==0 && attachnewflname.size()==0)
	     {
	    	 isatach=false;
	     }
	     String realPath = request.getServletContext().getRealPath("/");
	     String filePath  = realPath+"WEB-INF/view/jsp/temp/";
		GetSaveMailDraftResponse res=webmailClient.draftMailRequest(ipAddress,xmailer, host, smtpport,imapport,uid, id, pass, fromname, to, cc, bcc, sub, cntt, isatach, attachflname, attachnewflname, texttype, filePath);
		sendUid=res.getSetMailID();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sendUid;
	}
	
	
	
	
	
@RequestMapping(value = "/sendMailReadRec", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
@ResponseBody
public String sendMailReadRec(ModelMap map, Principal principal, HttpServletRequest request) 
	{
String status="true";
HttpSession hs=request.getSession();
if(hs==null || hs.getAttribute("id")==null)
	return "<$expire$>";

String to=request.getParameter("to");
String sub=request.getParameter("sub");
String cntt=request.getParameter("cntt");
String mailid=	request.getParameter("mailid");
boolean saveSent=true;

//HttpSession hs=request.getSession();
String host=(String)hs.getAttribute("host");
String id=(String)hs.getAttribute("id");
String pass=(String)hs.getAttribute("pass");
String smtpport=(String)hs.getAttribute("smtpport");
String imapport=(String)hs.getAttribute("port");
String fromname=(String)hs.getAttribute("fname");
String xmailer=(String)hs.getAttribute("XMailer");

String ipAddress=null;
String getWay = request.getHeader("VIA");   // Gateway
ipAddress = request.getHeader("X-FORWARDED-FOR");   // proxy
if(ipAddress==null)
{
    ipAddress = request.getRemoteAddr();
    if(ipAddress!=null && !ipAddress.equalsIgnoreCase(""))
    {
    	ipAddress="["+ipAddress+"]";
    }
}
System.out.println("getWay: "+getWay);
System.out.println("IP Address: "+ipAddress);
boolean isatach=false;
HashMap hm=null;
List<String> attachflname=new ArrayList<String>();
List<String> attachnewflname=new ArrayList<String>();
// HttpSession hs=request.getSession();
hm =(HashMap)hs.getAttribute("uploadmap");
if(hm==null)
{
	 isatach=false;
	// hm=new HashMap();
	// System.out.println("ssssssssssaaaaaaaaavvvvvveeeeeeee");
}
else
{
	 Set set = hm.entrySet();
     // Get an iterator
     Iterator i = set.iterator();
     // Display elements
    // System.out.println("!!!!!!!!!!!!!!!!!!!!");
     while(i.hasNext()) {
        Map.Entry me = (Map.Entry)i.next();
        attachflname.add(me.getKey().toString());
        attachnewflname.add(me.getValue().toString());
        //System.out.println(me.getKey() + ":----- "+me.getValue());
       
     }
   //  System.out.println("!!!!!!!!!!!!!!!!!!!!");
}
String realPath = request.getServletContext().getRealPath("/");
String filePath  = realPath+"WEB-INF/view/jsp/temp/";
GetComposeMailResponse res=webmailClient.comoseMailRequest("","","",ipAddress, xmailer, "",host, smtpport,imapport,saveSent, id, pass, fromname, to, null, null, sub, cntt, isatach,  attachflname, attachnewflname, null,false, null, "html", filePath);

status=res.getSetComposeStatus();
System.out.println(status);

if(status.equalsIgnoreCase("true"))
{
	
	try
	{
		hs.removeAttribute("uploadmap");
	}
	catch(Exception eee){eee.printStackTrace();}
}







String port=(String)hs.getAttribute("port");
String foldername=(String)hs.getAttribute("active_folder");

IMAPFolder folder = null;
Store store = null;

try 
{
	store=Connections.imapConnectionNP(host, port, id, pass);
	
    folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account

   if (!folder.exists()) {
    	// inboxlist.setGetInboxByUid(inb);
    	// return   inboxlist;        
    }
    folder.open(javax.mail.Folder.READ_WRITE);
    
        long [] arr={Long.parseLong(mailid)};
        Message[] msg =folder.getMessagesByUID(arr);
        for (int i = 0; i< msg.length; i++)
        {
        	
        	 Flags processedFlag = new Flags("$MDNSent");
        	 msg[i].setFlags(processedFlag, true);
        	
 
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





return status;
	}




@RequestMapping(value = "/ignoreMailReadRec", method = RequestMethod.GET)
@ResponseBody
public String ignoreMailReadRec(ModelMap map, Principal principal, HttpServletRequest request) 
{
boolean status=true;
HttpSession hs=request.getSession();
if(hs==null || hs.getAttribute("id")==null)
	return "<$expire$>";
String mailid=	request.getParameter("mailid");


//HttpSession hs=request.getSession();
String host=(String)hs.getAttribute("host");
String id=(String)hs.getAttribute("id");
String pass=(String)hs.getAttribute("pass");
String port=(String)hs.getAttribute("port");
String foldername=(String)hs.getAttribute("active_folder");

IMAPFolder folder = null;
Store store = null;

try 
{
	store=Connections.imapConnectionNP(host, port, id, pass);

folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account

if (!folder.exists()) {
	// inboxlist.setGetInboxByUid(inb);
	// return   inboxlist;        
}
folder.open(javax.mail.Folder.READ_WRITE);

    long [] arr={Long.parseLong(mailid)};
    Message[] msg =folder.getMessagesByUID(arr);
    for (int i = 0; i< msg.length; i++)
    {
    	
    	 Flags processedFlag = new Flags("$MDNSent");
    	 msg[i].setFlags(processedFlag, true);
    	

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





return ""+status;
}


@RequestMapping(value = "/deleteDraftMail", method = RequestMethod.GET)
@ResponseBody
public String deleteDraftMail(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	boolean status=true;
	HttpSession hs=request.getSession();
	if(hs==null || hs.getAttribute("id")==null)
		return "<$expire$>";
	String uids=	request.getParameter("uid");


	//HttpSession hs=request.getSession();
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	String port=(String)hs.getAttribute("port");
	
	GetWebmailDeleteDraftResponse res= webmailClient.deleteDraftRequest(host, port, id, pass,  uids);
	
	return ""+status;
	}


@RequestMapping(value = "/openBriefcase", method = RequestMethod.GET)

public String openBriefcase(ModelMap map, Principal principal, HttpServletRequest request) 
{
	HttpSession hs=request.getSession();
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	String breadcumPath=request.getParameter("breadcumPath");
	if(breadcumPath==null || breadcumPath=="")
	{
		breadcumPath="/"+id;
	}
	
	String fpath=request.getParameter("fpath");
	System.out.println(">>>>>>>>>>>>>path="+fpath);
	GetFolderResponse folderResponse = folderClient.getFolderRequestBC(fpath, id, pass);
	List<webmail.wsdl.Folder> folderList = folderResponse.getGetFoldersByParentFolder().getFolderListResult().getFolderList();
//System.out.println("length of folderlist "+folderList.size());
GetFileWithOutStreamResponse fileResponse = fileClient.getFileWithOutStreamRequestBC(fpath, id,pass);
List<webmail.wsdl.File> fileList = fileResponse.getGetFilesByParentFile()
            .getFileListResult().getFileList();
	
GetFolderByPathResponse folderByPath = folderClient.getFolderByPathBC(fpath, id,pass);
Folder folderNode = folderByPath.getFolder();
	
if(folderNode!=null){
if(folderNode.getFolderName()!=null){
int indexOfPath=breadcumPath.indexOf("/"+folderNode.getFolderName());
if(indexOfPath>=0)
{
breadcumPath=breadcumPath.substring(0,indexOfPath)+"/"+folderNode.getFolderName();
}else{
breadcumPath+="/"+folderNode.getFolderName();
}}
}

            map.addAttribute("breadcumPath",breadcumPath);

 	map.addAttribute("fileClient", fileClient);
 	map.addAttribute("folderClient", folderClient);
	map.addAttribute("folderList", folderList);
	map.addAttribute("fileList", fileList);
	map.addAttribute("breadcumPath", breadcumPath);
	
	
	return "briefcase";
}


@RequestMapping(value = "/setReplyFarwordFlag", method = RequestMethod.GET)
@ResponseBody
public String setReplyFarwordFlag(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	String status="true";
	try
	{ 
		String mailuid=	request.getParameter("mailuid");
		String mailfolder=	request.getParameter("mailfolder");
		String mailtype=	request.getParameter("mailtype");
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");

		String arr[]=mailuid.split("-");
		long uidarr[]=new long[arr.length];
		for(int i=0;i< arr.length;i++)
		{
			uidarr[i]=Long.parseLong(arr[i].trim());
		}
		Store store = null;
		IMAPFolder folder = null;
		store=Connections.imapConnectionNP(host, port, id, pass);
		//IMAPStore imapStore = (IMAPStore) store; 
     folder = (IMAPFolder) store.getFolder(mailfolder); //This works for both email account
	
  UIDFolder uf = (UIDFolder)folder;
  if (!folder.exists()) 
  {
	  System.exit(0);
  }
  folder.open(javax.mail.Folder.READ_WRITE);
  Message[] msg =uf.getMessagesByUID(uidarr);
 // int umsg= folder.getUnreadMessageCount();

  
  for (int i = msg.length-1;  i>=0; i--)
  {

	  try
	  {
		  if(mailtype.equalsIgnoreCase("reply"))
		  {
		  msg[i].setFlag(Flags.Flag.ANSWERED, true);
		  }
		  else if(mailtype.equalsIgnoreCase("forward"))
		  {
			  Flags processedFlag = new Flags("$Forwarded");
         	 	msg[i].setFlags(processedFlag, true);
		  }
	  }
	  catch(NullPointerException e)
	  {
		  e.printStackTrace();
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }

  }
  folder.close(true);
  store.close();
	}
	catch(Exception e)
	{
		status="false";
	}
	return status;
	}

@RequestMapping(value = "/attach_file_frombriefcase", method = RequestMethod.GET)
@ResponseBody
public String attach_file_frombriefcase(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	boolean status=true;
	
	String name=	request.getParameter("name");
	String file=	request.getParameter("file");
	HttpSession hs=request.getSession();
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	
	GetFileByPathResponse fileres = fileClient.getFileByPathBC(file, id, pass);
//	fileByPath.getFile().
	webmail.wsdl.File fileNode=fileres.getFile();
	//iostrm=fileNode.getFileContent();
	byte[] imageBytes = org.apache.commons.codec.binary.Base64.decodeBase64(fileNode.getFileContent());
	InputStream is = new ByteArrayInputStream(imageBytes);
	
	
	
	
	 
     HashMap hm=null;
    
     hm =(HashMap)hs.getAttribute("uploadmap");
     if(hm==null)
     {
    	 hm=new HashMap();
     }
   
 
     OutputStream outputStream = null;
     try {
    	 
    	 
    	 String realPath = request.getServletContext().getRealPath("/");
    	   	File fl=new File(realPath+"WEB-INF/view/jsp/temp");
    	int idx = name.lastIndexOf('.');
       String fileExtension = idx > 0 ? name.substring(idx) : ".tmp";
       File fil = File.createTempFile(name, fileExtension, fl);
       System.out.println("!!!!!!!!!!!!!!!!!!!!!!="+fil.getPath()+"--"+fil.getName());
      
       
    // write the inputStream to a FileOutputStream
    		/*outputStream = new FileOutputStream(fil);

    		int read = 0;
    		byte[] bytes = new byte[1024];

    		while ((read = is.read(bytes)) != -1) {
    			outputStream.write(bytes, 0, read);
    		}*/

       FileOutputStream fileOuputStream =  new FileOutputStream(fil.getPath()); 
	    fileOuputStream.write(imageBytes);
	    fileOuputStream.close();
       
      hm.put(name, fil.getName());
      is.close();
	} catch (IOException e)
     {
		e.printStackTrace();
     }
   
     hs.setAttribute("uploadmap", hm);
	
	return ""+status;
	}

public String remProbChars(String str)
{
	return str.replaceAll("[,'\"]", "");
}

}
