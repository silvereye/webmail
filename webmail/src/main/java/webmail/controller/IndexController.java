package webmail.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.postgresql.util.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.SessionFactory;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sieve.manage.ManageSieveClient;
import com.sieve.manage.ManageSieveResponse;
import com.sieve.manage.examples.ConnectAndListScripts;
import com.sun.mail.imap.IMAPFolder;

import webmail.bean.CalDav;
import webmail.bean.CardDav;
//import ezvcard.util.org.apache.commons.codec.binary.Base64;
import webmail.chatdwr.ScriptSessList;
import webmail.chatdwr.XmppChatClass;
import webmail.dao.ChatArchiveDao;
import webmail.dao.LoginlogDao;
import webmail.dao.SharedDao;
import webmail.model.ChatArchive;
import webmail.model.Loginlog;
import webmail.model.Shared;
import webmail.webservice.client.CalendarClient;
import webmail.webservice.client.FileClient;
import webmail.webservice.client.FolderClient;
import webmail.webservice.client.WebmailClient;
import webmail.wsdl.CreateCalendarRequest;
import webmail.wsdl.DateTimeList;
import webmail.wsdl.EventArray;
import webmail.wsdl.EventBean;
import webmail.wsdl.Folder;
import webmail.wsdl.GetEventsResponse;
import webmail.wsdl.GetFileResponse;
import webmail.wsdl.GetFolderResponse;
import webmail.wsdl.GetLdapFNameResponse;
import webmail.wsdl.GetLdapModifyOneAttResponse;
import webmail.wsdl.GetSharedFilesByPathWithContentRequest;
import webmail.wsdl.GetSharedFilesByPathResponse;
import webmail.wsdl.GetSharedFilesByPathWithContentResponse;
import webmail.wsdl.GetSharedFoldersByPathResponse;
import webmail.wsdl.GetWebmailAllMailCountResponse;
import webmail.wsdl.GetWebmailFolderOtherResponse;
import webmail.wsdl.GetWebmailFolderResponse;
import webmail.wsdl.GetWebmailImapquotaResponse;
import webmail.wsdl.Imapquota;
import webmail.wsdl.MailImapFolders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.ReferralException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.mail.Store;

import junit.framework.Assert;
import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.connector.dav.CalDavCalendarStore;
import net.fortuna.ical4j.connector.dav.CardDavCollection;
import net.fortuna.ical4j.connector.dav.CardDavStore;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.filter.Rule;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.*;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.CompatibilityHints;

@Controller
public class IndexController {
	@Autowired private ScriptSessList scriptSessList;
	//@Autowired private Runner runner;
	
	@Value ("${xmppDomain}") private String xmppDomain;
	@Value ("${packetReplyTimeout}") private int packetReplyTimeout; // millis
	@Value ("${chatImageFolder}") private String chatImageFolder;
	@Value ("${onlineStatus}") private String onlineStatus;
	
	@Autowired
	WebmailClient webmailClient;
	@Autowired
	private CalendarClient calendarclient;
	@Autowired
	private FileClient fileClient;
	
	@Autowired
	private FolderClient folderClient;
	
	
	
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
	
	
	
	public boolean modyfyRepAttr(DirContext ctx, String MY_ENTRY, String attr, String val)
	{
		boolean status=true;
		try
	    {
			 ModificationItem[] mods = new ModificationItem[1];
				//Attribute mod1 = new BasicAttribute("FTP", "TRUE");
				  // Attribute mod1 = new BasicAttribute("phone", "555-555-5555");
			  javax.naming.directory.Attribute mod0 = new javax.naming.directory.BasicAttribute(attr, val);


			  mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
			  ctx.modifyAttributes(MY_ENTRY, mods);

	    }
	    catch(Exception e)
	    {
	    	status=false;
	    	e.printStackTrace();
	    	//System.out.print(e.toString());
	    	
	    }
		return status;
	}
	
@RequestMapping(value = "/offlinejs", method = RequestMethod.GET)
	public  @ResponseBody String offlinejs(ModelMap map, HttpServletRequest request) {
		String st="true";
		HttpSession hs=request.getSession();
		if(hs==null)
			st="false";
		return st;
	}

	
	
	
	 
	
	/*
	
	
	 @RequestMapping(value = "/upload", method = RequestMethod.POST)
	   public @ResponseBody String upload(MultipartHttpServletRequest request, HttpServletResponse response) {                 
	 
	     //0. notice, we have used MultipartHttpServletRequest
	 
	     //1. get the files from the request object
	     Iterator<String> itr =  request.getFileNames();
	     HashMap hm=null;
	     HttpSession hs=request.getSession();
	     hm =(HashMap)hs.getAttribute("uploadmap");
	     if(hm==null)
	     {
	    	 hm=new HashMap();
	    	 System.out.println("ssssssssssaaaaaaaaavvvvvveeeeeeee");
	     }
	     else
	     {
	    	 Set set = hm.entrySet();
		      // Get an iterator
		      Iterator i = set.iterator();
		      // Display elements
		      System.out.println("!!!!!!!!!!!!!!!!!!!!");
		      while(i.hasNext()) {
		         Map.Entry me = (Map.Entry)i.next();
		         System.out.println(me.getKey() + ":----- "+me.getValue());
		        
		      }
		      System.out.println("!!!!!!!!!!!!!!!!!!!!");
	     }
	     while (itr.hasNext()){
	     MultipartFile mpf = request.getFile(itr.next());
	     try {
	    	 
	    	
			//System.out.println(IOUtils.toString(mpf.getInputStream()));
			int idx = mpf.getOriginalFilename().lastIndexOf('.');
            String fileExtension = idx > 0 ? mpf.getOriginalFilename().substring(idx) : ".tmp";
            File fil = File.createTempFile(mpf.getOriginalFilename(), fileExtension);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!="+fil.getPath()+"--"+fil.getName());
            mpf.transferTo(fil);
          //  System.out.println("!!!!!!!!!!!!!!!!!!!!!!="+fil.getPath()+"--"+fil.getName());
          
            hm.put(mpf.getOriginalFilename(), fil.getName());
           // FileInputStream fs=new FileInputStream(fil);
		} catch (IOException e)
	     {
			e.printStackTrace();
	     }
	     if(!hm_old.isEmpty())
	     {
	     hm.putAll(hm_old);
	     }
	     hs.setAttribute("uploadmap", hm);
	    
	     //System.out.println(mpf.getOriginalFilename() +" uploaded!");
	     }
	  

	     try {
	                //just temporary save file info into ufile
	        ufile.length = mpf.getBytes().length;
	        ufile.bytes= mpf.getBytes();
	        ufile.type = mpf.getContentType();
	        ufile.name = mpf.getOriginalFilename();
	 
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	 
	     //2. send it back to the client as <img> that calls get method
	     //we are using getTimeInMillis to avoid server cached image 
	 
	     
	     
	   //  HashMap hm_old =(HashMap)hs.getAttribute("uploadmap");
	      // Get a set of the entries
	      Set set = hm.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
	      // Display elements
	      System.out.println("***************************");
	      while(i.hasNext()) {
	         Map.Entry me = (Map.Entry)i.next();
	         System.out.println(me.getKey() + ":----- "+me.getValue());
	        
	      }
	    
	     
	     return "true";
	 
	  }
	*/
	
	//
	

@RequestMapping(value = "/newtmp", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	
	public String newtmp(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		
		
		return "newtmp";
	}

@RequestMapping(value = "/errorPage", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)

public String errorPage(ModelMap map, Principal principal, HttpServletRequest request) 
{
	
	
	return "errorPage";
}

@RequestMapping(value = "/viewattachment", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	
	public ModelAndView viewattachment(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	String uid= request.getParameter("uid");
	//String foldernm= request.getParameter("folder");
	String name= request.getParameter("nm");
	map.addAttribute("folder", "nirbhay");
	return new ModelAndView("viewattachment", map);
		
		//return "viewattachment";
	}

	
@RequestMapping(value = "/changeBGColor", method = RequestMethod.GET)
@ResponseBody
public String changeBGColor(ModelMap map, Principal principal, HttpServletRequest request) {
	
	boolean st=true;
	HttpSession hs=request.getSession();
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	String ldapurl=(String)hs.getAttribute("ldapurl");
	String ldapbase=(String)hs.getAttribute("ldapbase");
	String bgColor=request.getParameter("bgColor");
	
	GetLdapModifyOneAttResponse modattone=webmailClient.getLdapModifyOneAtt(ldapurl, id, pass, ldapbase, "bgColor", bgColor);
	st= modattone.isGetStatus();
    hs.setAttribute("bgColor", bgColor);
return ""+st;
	
	
}
	

@RequestMapping(value = "/showUserQuota", method = RequestMethod.GET)
@ResponseBody
public String showUserQuota(ModelMap map, Principal principal, HttpServletRequest request) {
	
	String str="";
	
	HttpSession hs=request.getSession();
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	GetWebmailImapquotaResponse wfresponse = webmailClient.getWebmailImapquotaRequest(host, id, pass);
	Imapquota iqt = wfresponse.getImapquota();
	//System.out.println("*********iqt********Qlota=" + iqt.getQuotalimit());
	long ql = iqt.getQuotalimit();
	long qu = iqt.getQuotauses();
	long qper =0l;
	if( ql!=0)
	 qper = (qu * 100) / ql;
	
	 String per_name="bar5.png";
     
    long per=0l;
     if(ql!=0)
     {
    	 long pval=qper;
    	 per=pval;
    	 if(pval<=5)
    	 {
    		 per_name="bar5.png";
    	 }
    	 else if(pval<=10)
    	 {
    		 per_name="bar10.png";
    	 }
    	 else if(pval<=20)
    	 {
    		 per_name="bar20.png";
    	 }
    	 else if(pval<=30)
    	 {
    		 per_name="bar30.png";
    	 }
    	 else if(pval<=40)
    	 {
    		 per_name="bar40.png";
    	 }
    	 else if(pval<=50)
    	 {
    		 per_name="bar50.png";
    	 }
    	 else if(pval<=60)
    	 {
    		 per_name="bar60.png";
    	 }
    	 else if(pval<=70)
    	 {
    		 per_name="bar70.png";
    	 }
    	 else if(pval<=80)
    	 {
    		 per_name="bar80.png";
    	 }
    	 else if(pval<=85)
    	 {
    		 per_name="bar85.png";
    	 }
    	 else if(pval<=90)
    	 {
    		 per_name="bar90.png";
    	 }
    	 else if(pval<=100)
    	 {
    		 per_name="bar100.png";
    	 }
    	
     }
     
   
str="<img src='images/"+per_name+"' /><div class='percentage_value'>"+per+"%</div>";
	
return str;
	
	
}
	
@RequestMapping(value = "/delDPImage", method = RequestMethod.POST)
@ResponseBody
public String delDPImage(ModelMap map, Principal principal, HttpServletRequest request) {
	 	String st="false";
	 	HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
		DirContext ctx=getConnection(ldapurl,id,pass,ldapbase);
	 	String entry="mail="+id+","+ldapbase;
	 	
	 	try
	    {
			 ModificationItem[] mods = new ModificationItem[1];
			 javax.naming.directory.Attribute mod0 = new javax.naming.directory.BasicAttribute("jpegPhoto");

				    mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, mod0);
				    ctx.modifyAttributes(entry, mods);
				     
				     st="true";
				     byte []jpegBytes1=null;
				     hs.setAttribute("img_myurl", jpegBytes1);
	    }
	    catch(Exception e)
	    {
	    	st="false";
	    	e.printStackTrace();
	    	    	
	    }
 		
 		closeConn(ctx);
		
	 return st;
}
	
	 @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	   public @ResponseBody String uploadImage(MultipartHttpServletRequest request, HttpServletResponse response) {                 
	 
		 String st="false";
	     Iterator<String> itr =  request.getFileNames();
	 	HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
	   
	     while (itr.hasNext()){
	     MultipartFile mpf = request.getFile(itr.next());
	     try {
	    	 
	     int idx = mpf.getOriginalFilename().lastIndexOf('.');
         String fileExtension = idx > 0 ? mpf.getOriginalFilename().substring(idx) : ".tmp";
         if(fileExtension.equalsIgnoreCase(".png") || fileExtension.equalsIgnoreCase(".jpg") || fileExtension.equalsIgnoreCase(".jpeg"))
         {
         File fil = File.createTempFile(mpf.getOriginalFilename(), ".jpg");
       // System.out.println("!!!!!!!!!!!!!!!!!!!!!!="+fil.getPath()+"--"+fil.getName());
         mpf.transferTo(fil);
       
        
        
         byte[] bFile = new byte[(int) fil.length()];
         
      
             //convert file into array of bytes
         FileInputStream   fileInputStream = new FileInputStream(fil);
 	    fileInputStream.read(bFile);
      
         
 	   fileInputStream.close(); 
          DirContext ctx=getConnection(ldapurl,id,pass,ldapbase);
 		String entry="mail="+id+","+ldapbase;
 		
 		//boolean status= modyfyRepAttr(ctx, entry, "jpegPhoto", arr);
 		
 		try
	    {
			 ModificationItem[] mods = new ModificationItem[1];
			 javax.naming.directory.Attribute mod0 = new javax.naming.directory.BasicAttribute("jpegPhoto", bFile);

				    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
				    ctx.modifyAttributes(entry, mods);
				    
				    
				      Attributes testAttributes = ctx.getAttributes(entry);
				      Attribute jpegPhoto1 = testAttributes.get("jpegPhoto");
				      byte[] jpegBytes1 = (byte[]) jpegPhoto1.get();
				      if(jpegBytes1!=null)
				      {
				      hs.setAttribute("img_myurl", jpegBytes1);
				     
				     String s = new sun.misc.BASE64Encoder().encode(jpegBytes1);
				     st=""+s;
				      }
	    }
	    catch(Exception e)
	    {
	    	st="false";
	    	e.printStackTrace();
	    	    	
	    }
 		
 		closeConn(ctx);
         
         
         
         }
         
		} catch (IOException e)
	     {
			st="false";
			e.printStackTrace();
	     }
	   
	  break;
	    
	     }
	  

	    
	     
	     return st;
	 
	  }
	 
	 
	 @RequestMapping(value = "/uploadImageCK", method = RequestMethod.POST)
	   public @ResponseBody String uploadImageCK(MultipartHttpServletRequest request, HttpServletResponse response) {                 
	 
		 String st="false";
	     Iterator<String> itr =  request.getFileNames();
	 	HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
	   
	     while (itr.hasNext()){
	     MultipartFile mpf = request.getFile(itr.next());
	     try {
	    	 
	     int idx = mpf.getOriginalFilename().lastIndexOf('.');
       String fileExtension = idx > 0 ? mpf.getOriginalFilename().substring(idx) : ".tmp";
       if(fileExtension.equalsIgnoreCase(".png") || fileExtension.equalsIgnoreCase(".jpg") || fileExtension.equalsIgnoreCase(".jpeg") || fileExtension.equalsIgnoreCase(".gif") || fileExtension.equalsIgnoreCase(".bmp"))
       {
       File fil = File.createTempFile(mpf.getOriginalFilename(), ".jpg");
     // System.out.println("!!!!!!!!!!!!!!!!!!!!!!="+fil.getPath()+"--"+fil.getName());
       mpf.transferTo(fil);
     
      
      
       byte[] bFile = new byte[(int) fil.length()];
       
    
           //convert file into array of bytes
       FileInputStream   fileInputStream = new FileInputStream(fil);
	    fileInputStream.read(bFile);
    
       
	   fileInputStream.close(); 
	   String s = new sun.misc.BASE64Encoder().encode(bFile);
	   st=""+s;
	   
       
       
       
       }
       
		} catch (IOException e)
	     {
			st="false";
			e.printStackTrace();
	     }
	   
	  break;
	    
	     }
	  

	    
	     
	     return st;
	 
	  }
	 
	 
	 @RequestMapping(value = "/insertUploadedImg", method = RequestMethod.GET)
	 @ResponseBody
	 public String insertUploadedImg(ModelMap map, Principal principal, HttpServletRequest request) {
	 	String img="";
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String base=(String)hs.getAttribute("ldapbase");
		GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, base, "jpegPhoto");
		System.out.println("**************ldapfname="+ldapres.getGetFName());
	 	
	 	return img;
	 }
	 
	
	 public  List<MailImapFolders> lFiles(List<MailImapFolders> list, String path, String host, String id, String pass) {
    	 
	     	// GetWebmailFolderSubscribedOtherResponse sfres=webmailClient.getWebmailFolderSubscribedOtherRequest(host, id, pass, path);
	  	 	// List<SubsImapFolders> sflst= sfres.getGetSubFolder().getSubsFolderListReturn().getSubsFolderList();
	     	 
	  	 	GetWebmailFolderOtherResponse sfres=webmailClient.getWebmailFolderOtherRequest(host, id, pass, path);
	 	 	List<MailImapFolders> sflst= sfres.getGetSubFolder().getMailFolderListReturn().getMailFolderList();
	  	 	 
	  	 	 
	     	         for(MailImapFolders fd:sflst)
	     	  			{
	     	        	 list.add(fd);
	     	        	if(fd.isHasChild())
	     	   			{
	     	   				
	     	   				lFiles(list, fd.getFolderFullName(),host, id, pass);
	     	   			}
	     	   			else
	     	   			{
	     	   			
	     	   			}
	     	   			
	     	        	 
	     	        	 
	     	  			}
	     	         
	     	         
	     
	     return list;
	 } 

	
	 @Autowired  
	 private ChatArchiveDao chatArchiveDao;  
	@RequestMapping(value = "/tmp", method = RequestMethod.GET)
	public String gettmp(ModelMap map) 
	{
		//String id= "nirbhay@silvereye.in";
		
		//List<ChatArchive> clst=chatArchiveDao.getOldChatList(id);
		
		/*
		.
		   String inboxcnt="20";  //(String)request.getAttribute('in_unread_cnt');
		  	//HttpSession hs=request.getSession();
		  	String host= "mail.storkrubber.com"; //(String)hs.getAttribute('host');
		  	String id= "arangar@storkrubber.com"   ;  //(String)hs.getAttribute('id');
		  	String pass= "arjan@259";      //(String)hs.getAttribute('pass');

		     
		  	GetWebmailFolderOtherResponse sfres=webmailClient.getWebmailFolderOtherRequest(host, id, pass, "");
		 	List<MailImapFolders> imapfldrlst= sfres.getGetSubFolder().getMailFolderListReturn().getMailFolderList();
		       

		

		



		    		  for(MailImapFolders fd : imapfldrlst)
		    			{
		    			List<MailImapFolders> list=new ArrayList(); 
		    			list.add(fd);
		    			
		    			if(fd.isHasChild())
		    			{
		    				List<MailImapFolders> list1=lFiles(list, fd.getFolderFullName(), host, id, pass);
		    				
		    				for(int i=0;i<list1.size();i++)
		    				{
		    					String nm=list1.get(i).getFolderFullName();
		    					String arr[]=nm.split("\\.");
		    					int l=arr.length;
		    					
		    					 if(!list1.get(i).isHasChild() && !list1.get(i).isIsSubs())
		    					 {
		    						 
		    					 }
		    					 else
		    					 {
		    				
		    	  	  		if(list1.get(i).isHasChild() )
		    	  	  		{
		    	  	  			if(list1.get(i).isIsSubs())
		    	  	  			{
		    	  	  			
		    	  	  			
		    	  	  			
		    	  	  			if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
		    	          		{
		    	  	  			System.out.println(">"+list1.get(i).getFolderFullName() );
		    	  	  		System.out.println(list1.get(i).getFolderName() );
		    	  	  
		    	        	  } 
		    	        	    else
		    	        	    {System.out.println(">"+list1.get(i).getFolderFullName() );
		    	        	    	System.out.println(list1.get(i).getFolderName() );
		    	        	    
		    	        	    }
		    	  	  			}
		    	  	  			else
		    	  	  			{
		    	  	  				
		    	  	  			
		    	  	  			
		    	  	  			if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
		    	          		{
		    	  	  			System.out.println(">"+list1.get(i).getFolderFullName() );
		    	  	  			System.out.println(list1.get(i).getFolderName() );
		    	        	   } 
		    	        	    else
		    	        	    {
		    	        	    	System.out.println(">"+list1.get(i).getFolderFullName() );
		    	        	    	System.out.println(list1.get(i).getFolderName() );
		    	        	   
		    	        	    }
		    	  	  			}
		    	  	  		}
		    	  	  		else
		    	  	  		{
		    	  	  		
		    	  	  			if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
		    	          		{
		    	          			
		    	  	  			System.out.println(">"+list1.get(i).getFolderFullName() );	
		    	  	 System.out.println(list1.get(i).getFolderName() );
		    	        	   }
		    	  	  			else
		    	  	  			{
		    	  	  			System.out.println(">"+list1.get(i).getFolderFullName() );
		    	  	  			System.out.println(list1.get(i).getFolderName() );
		    	  	  			}
		    	  	  			} 
		    	  	  	
		    	  	  			
		    					 }
		    	  	  			
		    				}
		    				
		    				//out.print('<br>'+lFiles(list, fd.getFullName()));
		    			
		    			}
		    			else
		    			{
		    				
		    				//out.print('<br>'+fd.getFullName());
		    			 
		    	  			  if(fd.getFolderFullName().equalsIgnoreCase("INBOX"))
		    	  			  {
		    	  			 
		    	  				System.out.println("<"+fd.getFolderFullName() );
		    	  			  }
		    	  			  else  if(fd.getFolderFullName().equalsIgnoreCase("DRAFTS"))
		    	  			  {
		    	  				
		    	  				System.out.println("<"+fd.getFolderFullName() );
		    	  			}
		    	  			  else
		    	  			  {
		    	  				if(fd.isIsSubs())
		    	  				{
		    	  			 
		    	  					System.out.println("<"+fd.getFolderFullName() );
		    	  			 }
		    	  			  
		    	  				
		    	  			
		    				}
		    			}
		    			}
		    		  
		    		

		  
		    		 
		   */
		   
		   
		
		
		return "tmp";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLogin(ModelMap map,  HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		if(hs!=null && hs.getAttribute("id")!=null && hs.getAttribute("pass")!=null)
		{
			String id=hs.getAttribute("id").toString();
			String pass=hs.getAttribute("pass").toString();
			if(id!=null && pass!=null)
			{
				return "redirect:/inbox";	
			}
			else
			{
				return "login";	
			}
		}
		else
		{
			return "login";	
		}
		
		
	}
	
	
	
	@RequestMapping(value = "/firstinbox", method = RequestMethod.GET)
	public String firstinbox(ModelMap map) 
	{
		
		///System.out.println("*********************** map="+ses);
		return "firstinbox";
	}
	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getLoginindex(ModelMap map) 
	{
		return "redirect:/login";
	}
	
	 @Autowired  
		private LoginlogDao loginlogDao; 
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogout(ModelMap map, HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession hs=request.getSession();
		/*int llsno=0;
		try
		{
		if(hs.getAttribute("llsno")!=null)
			llsno=	(int)hs.getAttribute("llsno");
		}
		catch(Exception ne)
		{
			ne.printStackTrace();
		}*/
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
		
		/*try
		{
			if(llsno>0)
			{
			Loginlog ll=new Loginlog();
			loginlogDao.addUpdateRow(llsno, ll);
			}
		}
		catch(Exception eee)
		{
			eee.printStackTrace();
		}*/
		
		hs.invalidate();
	
		 response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	     response.setHeader("Pragma","no-cache");
	     response.setDateHeader("Expires", 0);
	    
		
	      return "redirect:/login";
	}
	
	@RequestMapping(value = "/newlogout", method = RequestMethod.GET)
	public String newgetLogout(ModelMap map, HttpServletRequest request, HttpServletResponse response) 
	{
		
		return "newlogout";
	}
	

	@RequestMapping(value = "/compose", method = RequestMethod.GET)
	public String getcompose(ModelMap map) 
	{
		return "compose";
	}
	

	public DateTimeList calculaterepeatdate(VEvent tempevent, DtStart startdate, Dur dur)
	{
		DateTimeList dtl=new DateTimeList();
		VEvent component=new VEvent();
		PeriodList pl=null;
		try
		{
			String tm=startdate.getValue();
			if(!tm.contains("T"))
			{
				tm=tm+"T000000";
			}
			pl = tempevent.calculateRecurrenceSet(new Period(new DateTime(tm), dur));
			
		}
		catch (ParseException e1) 
		{
			e1.printStackTrace();
		}
		for (Iterator<Period> pit = pl.iterator(); pit.hasNext();)
		{
			Period period=(Period)pit.next();
			try 
			{
				dtl.getDateTime().add(period.getStart().toString()+"`"+period.getEnd().toString());
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	
		return dtl;
   
	}

	@Autowired  
	private SharedDao sharedDao; 
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public ModelAndView getcalendar(ModelMap map, HttpServletRequest request) {
		HttpSession hs = request.getSession();
		String host = (String) hs.getAttribute("host");
		String id = (String) hs.getAttribute("id");
		
		System.out.println("*************ses="+id);
		if(id==null)
		{
			//ses="SesExp";
			return new ModelAndView("redirect:/login?stat=ses", map);
		}
		
		String pass = (String) hs.getAttribute("pass");
		String port = (String) hs.getAttribute("port");
		String fdrnm = (String) hs.getAttribute("active_folder");
		String proid=(String)hs.getAttribute("caldevProId");
		String surl=(String)hs.getAttribute("caldevUrl");
		GetWebmailImapquotaResponse wfresponse = webmailClient.getWebmailImapquotaRequest(host, id, pass);
		Imapquota iqt = wfresponse.getImapquota();
		System.out.println("*********iqt********Qlota=" + iqt.getQuotalimit());
		long ql = iqt.getQuotalimit();
		long qu = iqt.getQuotauses();
		long qper =0l;
		if( ql!=0)
		 qper = (qu * 100) / ql;
		// System.out.println("********************Quotalimi="+ per
		// System.out.println("********************Quota per="+qper);
		// GetWebmailAllMailCountResponse
		// wfre=folderClient.getWebmailAllMailCountRequest(host, port, id,
		// pass,fdrnm);
		// map.addAttribute("MailCount", ""+(wfre.getAllemailcount()));
		map.addAttribute("QuotaPer", "" + qper);
		map.addAttribute("QuotaLimit", "" + ql);
		map.addAttribute("QuotaUses", "" + qu);
		
		// <---------Start reading an .ics file----------->
		XMLGregorianCalendar fd =null;
		java.util.Calendar caldr = java.util.Calendar.getInstance();
		caldr.add(java.util.Calendar.MONTH, -1);
		java.util.Date rdt = caldr.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		String strdt=sdf.format(rdt);
		String filterdate = strdt+"-20T00:00:00+05:30";
		try {
			rdt=sdf1.parse(filterdate);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/*try {
			fd= DatatypeFactory.newInstance().newXMLGregorianCalendar(filterdate);
		} catch (DatatypeConfigurationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		*/
		Period p = new Period(new DateTime(rdt), new Dur(50, 0, 0,0));
		Rule[] rules = new Rule[1];
        rules[0] = new PeriodRule(p);
        Filter f = new Filter(rules, Filter.MATCH_ALL);
		
		
		
		CalDav cldav=new CalDav();
		List<CalDavCalendarCollection> collList= cldav.findAllCollections( proid,  surl,  id , pass);
		List<CreateCalendarRequest> calendarfilelist=new ArrayList<CreateCalendarRequest>();
		
		CreateCalendarRequest caldetails=null;
		EventArray eventarray=new EventArray();
		List<String> filenames=new ArrayList<String>();
		for(CalDavCalendarCollection coll : collList)
		{
			if(coll.getDisplayName() != null && !coll.getDisplayName().contains("proxy"))
			{
			
			caldetails=new CreateCalendarRequest();
			String colr=coll.getColor();
			if(colr!=null && colr.length()>=9)
			{
				colr=colr.substring(0, 7);
			}
			
			String cpath=coll.getPath();
			if(cpath!=null && !cpath.startsWith(surl))
			{
				cpath=surl+cpath;
			}
			
			caldetails.setCalColor(colr);
			caldetails.setCalID(coll.getDisplayName());
			caldetails.setPermission("manage");
			calendarfilelist.add(caldetails);
			filenames.add(cpath);
			GetEventsResponse res=new GetEventsResponse();
			int i=0;
			EventArray eventlist = new EventArray();
			try {
				Calendar arr[]= coll.getComponents();
			} catch (ObjectStoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				for(Calendar cal : coll.getComponents())
				{
					 List <EventBean> eblst=new ArrayList<EventBean>();
					 int idx=0;
				//	for (Iterator<VEvent> it = cal.getComponents("VEVENT").iterator(); it.hasNext();)
					 for (Iterator<VEvent> it = f.filter(cal.getComponents(net.fortuna.ical4j.model.Component.VEVENT)).iterator(); it.hasNext();)
					{
						

						EventBean eventbean = new EventBean();
						VEvent component = (VEvent) it.next();
						
						eventbean.setSummary(component.getSummary().getValue());
						//date type changed
						try{
						GregorianCalendar gregoriancalendar=new GregorianCalendar();
						
						
							  if(component.getStartDate().getDate().toString().indexOf("T",0)<0)
							  {	
								  eventbean.setAllday("on");
								  String sdt=component.getStartDate().getDate().toString();
								  sdt+="T000000+05:30";
								  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
								  java.util.Date dt=component.getStartDate().getDate();
								  try {
									dt=dateFormat.parse(sdt);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							  	gregoriancalendar.setTime(dt);
							  	eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
								 
								 String sdt1=component.getEndDate().getDate().toString();
								  sdt1+="T000000+05:30";
								  java.util.Date dt1=component.getEndDate().getDate();
								  try {
									dt1=dateFormat.parse(sdt1);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								 gregoriancalendar.setTime(dt1);
								 eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
							  
							  }
							  else
							  {
								  eventbean.setAllday("off");
								  gregoriancalendar.setTime(component.getStartDate().getDate());
								  eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
									 gregoriancalendar.setTime(component.getEndDate().getDate());
									 eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
							  }
							 
						
						
						}
						catch(DatatypeConfigurationException e)
						{
							e.printStackTrace();
						}
						// date type changed end
						
						eventbean.setUid(component.getUid().getValue());
						eventbean.setCalendar(cpath);
						try
						{
							if(component.getClassification()!=null)
								eventbean.setClazz(component.getClassification().getValue());
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						try
						{
							PropertyList list = component.getProperties(Property.ATTENDEE);
							String guests = "";
							for(Object obj : list)
							{
								if (obj instanceof Attendee) 
								{
									Attendee guest = (Attendee) obj;
									String name = guest.getCalAddress().toString();
									name = name.replace("mailto:", "");
									name = name.replace("MAILTO:", "");
									String status = guest.getParameter(Parameter.PARTSTAT).getValue();
									if(guests!=null && guests.length()>0)
									{
										guests +=",";
									}
									
									if(status.equals("ACCEPTED"))
									{
										guests += "a`"+name;
									}
									else if(status.equals("DECLINED"))
									{
										guests += "d`"+name;
									}
									else if(status.equals("TENTATIVE"))
									{
										guests += "t`"+name;
									}
									else if(status.equals("IN_PROCESS"))
									{
										guests += "i`"+name;
									}
									else
									{
										guests += "n`"+name;
									}
									//System.out.println("Attendee Status : " + guest.getParameters(Parameter.PARTSTAT));
								}
							}
							eventbean.setOldguest(guests);
						}
						catch(Exception ee)
						{
							ee.printStackTrace();
						}
						try
						{
						if(colr!=null)
						eventbean.setColor(colr);
						}
						catch(Exception e)
						{
							e.printStackTrace();
							eventbean.setColor("");
						}
						
						if(component.getTransparency() != null)
						{
							if(component.getTransparency().equals(Transp.TRANSPARENT))
							{
								eventbean.setFreebusy("free");
							}
							else
							{
								eventbean.setFreebusy("busy");
							}
						}
						
						if(component.getProperty("RRULE")!=null)
						{
							RRule rrule=(RRule)component.getProperty("RRULE");
							Recur recur=rrule.getRecur();
							try {
								
									Dur dur=null;
									if(recur.getCount()>0)
									{
										if(recur.getFrequency().equals("YEARLY"))
										{
											
											dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
										}
										else if(recur.getFrequency().equals("MONTHLY"))
										{
											dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
										}
										else
										{
											dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
										}
									 
									}
									else if(recur.getUntil() != null)
									{
										
										Date udt= recur.getUntil();
										java.util.Calendar cStart = java.util.Calendar.getInstance();
									    cStart.setTime(udt);
									    cStart.add(java.util.Calendar.DATE, 1);
									    java.util.Date  dStart = new Date(cStart.getTime());
										dur=new Dur(component.getStartDate().getDate(),dStart);
									}
									else
									{
										Date dt  =new Date(component.getEndDate().getDate().getTime());
										dt.setYear(dt.getYear() +5);
										dur=new Dur(component.getStartDate().getDate(),dt);
									}
									//DateTime stdate=new DateTime(component.getStartDate().getValue());
									
									///////////method testing
									try
									{
									eventbean.setRepeatdatetimelist(calculaterepeatdate(component,component.getStartDate(), dur));
									eventbean.setRepeatStatus("Yes");
									}
									catch(Exception ee)
									{
										ee.printStackTrace();
									}
								
									
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						
						if(component.getRecurrenceId()!=null)
						{
						String recid=component.getRecurrenceId().getValue();
						eventbean.setRecurrenceEventID(recid);
						int tmp=idx;
						for(idx--;idx>=0;idx--)
						{
							EventBean eb=eblst.get(idx);
							if(eb.getRepeatdatetimelist()==null)
							{
								continue;
							}
							else
							{
								DateTimeList dtl=eb.getRepeatdatetimelist();
								List<String> strlst=dtl.getDateTime();
								boolean sts=false;
								for(int ins=0;ins< strlst.size();ins++)
								{
									String strc=strlst.get(ins);
									if(strc.startsWith(recid))
									{
										strlst.remove(ins);
										sts=true;
										break;
									}
								}
								if(sts)
								{
									
									break;
								}
							}
						}
						idx=tmp;
						
						}
						
						eblst.add(eventbean);
						
						idx++;
					}
					eventlist.getEventList().addAll(eblst);
					res.setEventList(eventlist);
				}
			} catch (ObjectStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(res.getEventList()!=null)
				eventarray.getEventList().addAll(res.getEventList().getEventList());
			}
		
		}
		map.addAttribute("calendarfilelist", calendarfilelist);
		map.addAttribute("filenames", filenames);
		
		
		//GetFileResponse fileResponse1 = fileClient.getFileRequest("/"+id+"/sharedCalendars",id,pass);
		//fileList = fileResponse1.getGetFilesByParentFile().getFileListResult().getFileList();
		
		/*GetSharedFilesByPathWithContentResponse fileResponse1 = fileClient.getSharedFilesByPathWithContentRequest(id,pass, "/"+id+"/sharedCalendars") ;
		//System.out.println("shared cal");
		if(fileResponse1!=null)
		{
		fileList = fileResponse1.getGetSharedFilesByPath().getFileListResult().getFileList();
		}
		else
		{
			fileList=null;
		}*/
		//collList= cldav.findAllCollections( proid,  surl,  id , pass);
//		collList= cldav.findAllSharedCollections( proid,  surl,  id , pass);
		List<CreateCalendarRequest> sharedcalendarfilelist=new ArrayList<CreateCalendarRequest>();
		List<String> sharedfilenames=new ArrayList<String>();
		
		
	/*	for (webmail.wsdl.File file : fileList) 
		{
			caldetails=new CreateCalendarRequest();
			res = calendarclient.getallevents(file.getFileContent(), file.getFileName());
			caldetails.setCalColor(res.getCalendarcolor());
			caldetails.setCalID(res.getCalendarname());
			caldetails.setUserid(file.getCreatedBy());
			if(file.getUserSecurity().toString().contains(id))
			{
				caldetails.setPermission("manage");
			}
			else
			{
				caldetails.setPermission("read");
			}
			sharedcalendarfilelist.add(caldetails);
			sharedfilenames.add(file.getFileName());
			System.out.println(res.getEventList().getEventList().size());
			for(EventBean event:res.getEventList().getEventList())
			{
				if(event.getClazz()!=null)
				{
					if(event.getClazz().equalsIgnoreCase("public"))
					{
						event.setCalendar("/sharedCalendars/"+event.getCalendar());
						eventarray.getEventList().add(event);
					}
		
				}
			}
		}*/
		
		
		List<Shared> srdlst=sharedDao.getSharedDetailForLeft(id, true,true);
		if(srdlst.size()>0)
		{
			
			CalDavCalendarStore store=cldav.connect(proid, surl, id, pass);
		for(Shared srd: srdlst)
		{
			if(srd!=null)
			{
				
				String caluid=srd.getCalUID();
				String calurl=surl+"/caldav.php/"+srd.getUserfrom()+"/"+caluid+"/";
				
				try {
					CalDavCalendarCollection coll=store.getCollection(calurl);
					
					
					if(coll.getDisplayName() != null && !coll.getDisplayName().contains("proxy"))
					{
					
					caldetails=new CreateCalendarRequest();
					String colr=coll.getColor();
					if(colr!=null && colr.length()>=9)
					{
						colr=colr.substring(0, 7);
					}
					
					String cpath=coll.getPath();
					if(cpath!=null && !cpath.startsWith(surl))
					{
						cpath=surl+cpath;
					}
					
					caldetails.setCalColor(colr);
					caldetails.setCalID(coll.getDisplayName());
					caldetails.setUserid(srd.getUserfrom());
					if( srd.getWriteaccess().equalsIgnoreCase("us"))
					{
						caldetails.setPermission("manage");
						caldetails.setSharedPermission("All");
					}
					else if(srd.getWriteaccess().equalsIgnoreCase("uw") )
					{
						caldetails.setPermission("manage");
						caldetails.setSharedPermission("Read/Write");
					}
					else
					{
						caldetails.setPermission("read");
						caldetails.setSharedPermission("Read");
					}
					
					sharedcalendarfilelist.add(caldetails);
					sharedfilenames.add(cpath);
					GetEventsResponse res=new GetEventsResponse();
					int i=0;
					EventArray eventlist = new EventArray();
					
					try {
						CalDav cd=new CalDav();
						Calendar cal=cd.getSharedEvent(calurl, id, pass);
						
						if(cal!=null)
						{
							 List <EventBean> eblst=new ArrayList<EventBean>();
							 int idx=0;
						//	for (Iterator<VEvent> it = cal.getComponents("VEVENT").iterator(); it.hasNext();)
							 for (Iterator<VEvent> it = f.filter(cal.getComponents(net.fortuna.ical4j.model.Component.VEVENT)).iterator(); it.hasNext();)
							{
								

								EventBean eventbean = new EventBean();
								VEvent component = (VEvent) it.next();
								
								eventbean.setSummary(component.getSummary().getValue());
								//date type changed
								try{
								GregorianCalendar gregoriancalendar=new GregorianCalendar();
								
								
									  if(component.getStartDate().getDate().toString().indexOf("T",0)<0)
									  {	
										  eventbean.setAllday("on");
										  String sdt=component.getStartDate().getDate().toString();
										  sdt+="T000000+05:30";
										  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
										  java.util.Date dt=component.getStartDate().getDate();
										  try {
											dt=dateFormat.parse(sdt);
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									  	gregoriancalendar.setTime(dt);
									  	eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
										 
										 String sdt1=component.getEndDate().getDate().toString();
										  sdt1+="T000000+05:30";
										  java.util.Date dt1=component.getEndDate().getDate();
										  try {
											dt1=dateFormat.parse(sdt1);
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										 gregoriancalendar.setTime(dt1);
										 eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
									  
									  }
									  else
									  {
										  eventbean.setAllday("off");
										  gregoriancalendar.setTime(component.getStartDate().getDate());
										  eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
											 gregoriancalendar.setTime(component.getEndDate().getDate());
											 eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
									  }
									 
								
								
								}
								catch(DatatypeConfigurationException e)
								{
									e.printStackTrace();
								}
								// date type changed end
								
								eventbean.setUid(component.getUid().getValue());
								eventbean.setCalendar(cpath);
								try
								{
									if(component.getClassification()!=null)
										eventbean.setClazz(component.getClassification().getValue());
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
								try
								{
									PropertyList list = component.getProperties(Property.ATTENDEE);
									String guests = "";
									for(Object obj : list)
									{
										if (obj instanceof Attendee) 
										{
											Attendee guest = (Attendee) obj;
											String name = guest.getCalAddress().toString();
											name = name.replace("mailto:", "");
											name = name.replace("MAILTO:", "");
											String status = guest.getParameter(Parameter.PARTSTAT).getValue();
											if(guests!=null && guests.length()>0)
											{
												guests +=",";
											}
											
											if(status.equals("ACCEPTED"))
											{
												guests += "a`"+name;
											}
											else if(status.equals("DECLINED"))
											{
												guests += "d`"+name;
											}
											else if(status.equals("TENTATIVE"))
											{
												guests += "t`"+name;
											}
											else if(status.equals("IN_PROCESS"))
											{
												guests += "i`"+name;
											}
											else
											{
												guests += "n`"+name;
											}
											//System.out.println("Attendee Status : " + guest.getParameters(Parameter.PARTSTAT));
										}
									}
									eventbean.setOldguest(guests);
								}
								catch(Exception ee)
								{
									ee.printStackTrace();
								}
								try
								{
								if(colr!=null)
								eventbean.setColor(colr);
								}
								catch(Exception e)
								{
									e.printStackTrace();
									eventbean.setColor("");
								}
								
								if(component.getTransparency() != null)
								{
									if(component.getTransparency().equals(Transp.TRANSPARENT))
									{
										eventbean.setFreebusy("free");
									}
									else
									{
										eventbean.setFreebusy("busy");
									}
								}
								
								if(component.getProperty("RRULE")!=null)
								{
									RRule rrule=(RRule)component.getProperty("RRULE");
									Recur recur=rrule.getRecur();
									try {
										
											Dur dur=null;
											if(recur.getCount()>0)
											{
												if(recur.getFrequency().equals("YEARLY"))
												{
													
													dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
												}
												else if(recur.getFrequency().equals("MONTHLY"))
												{
													dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
												}
												else
												{
													dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
												}
											 
											}
											else if(recur.getUntil() != null)
											{
												
												Date udt= recur.getUntil();
												java.util.Calendar cStart = java.util.Calendar.getInstance();
											    cStart.setTime(udt);
											    cStart.add(java.util.Calendar.DATE, 1);
											    java.util.Date  dStart = new Date(cStart.getTime());
												dur=new Dur(component.getStartDate().getDate(),dStart);
											}
											else
											{
												Date dt  =new Date(component.getEndDate().getDate().getTime());
												dt.setYear(dt.getYear() +5);
												dur=new Dur(component.getStartDate().getDate(),dt);
											}
											//DateTime stdate=new DateTime(component.getStartDate().getValue());
											
											///////////method testing
											try
											{
											eventbean.setRepeatdatetimelist(calculaterepeatdate(component,component.getStartDate(), dur));
											eventbean.setRepeatStatus("Yes");
											}
											catch(Exception ee)
											{
												ee.printStackTrace();
											}
										
											
									} catch (Exception e) {
										e.printStackTrace();
									}
									
								}
								
								if(component.getRecurrenceId()!=null)
								{
								String recid=component.getRecurrenceId().getValue();
								eventbean.setRecurrenceEventID(recid);
								int tmp=idx;
								for(idx--;idx>=0;idx--)
								{
									EventBean eb=eblst.get(idx);
									if(eb.getRepeatdatetimelist()==null)
									{
										continue;
									}
									else
									{
										DateTimeList dtl=eb.getRepeatdatetimelist();
										List<String> strlst=dtl.getDateTime();
										boolean sts=false;
										for(int ins=0;ins< strlst.size();ins++)
										{
											String strc=strlst.get(ins);
											if(strc.startsWith(recid))
											{
												strlst.remove(ins);
												sts=true;
												break;
											}
										}
										if(sts)
										{
											
											break;
										}
									}
								}
								idx=tmp;
								
								}
								
								eblst.add(eventbean);
								
								idx++;
							}
							eventlist.getEventList().addAll(eblst);
							res.setEventList(eventlist);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					if(res.getEventList()!=null)
						eventarray.getEventList().addAll(res.getEventList().getEventList());
					}
				
				
					
					
					
					
					
					
					
					
					
				} catch (ObjectStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ObjectNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		}
		
		map.addAttribute("eventlist", eventarray.getEventList());
		map.addAttribute("sharedcalendarfilelist", sharedcalendarfilelist);
		map.addAttribute("sharedfilenames", sharedfilenames);
		map.addAttribute("webmailClient", webmailClient);
		return new ModelAndView("calendar", map);}
	
	
	
	@RequestMapping(value = "/calendar1", method = RequestMethod.GET)
	public ModelAndView getcalendar1(ModelMap map, HttpServletRequest request) {/*
		HttpSession hs = request.getSession();
		String host = (String) hs.getAttribute("host");
		String id = (String) hs.getAttribute("id");
		
		System.out.println("*************ses="+id);
		if(id==null)
		{
			//ses="SesExp";
			return new ModelAndView("redirect:/login?stat=ses", map);
		}
		
		String pass = (String) hs.getAttribute("pass");
		String port = (String) hs.getAttribute("port");
		String fdrnm = (String) hs.getAttribute("active_folder");
		GetWebmailImapquotaResponse wfresponse = webmailClient.getWebmailImapquotaRequest(host, id, pass);
		Imapquota iqt = wfresponse.getImapquota();
		System.out.println("*********iqt********Qlota=" + iqt.getQuotalimit());
		long ql = iqt.getQuotalimit();
		long qu = iqt.getQuotauses();
		long qper =0l;
		if( ql!=0)
		 qper = (qu * 100) / ql;
		// System.out.println("********************Quotalimi="+ per
		// System.out.println("********************Quota per="+qper);
		// GetWebmailAllMailCountResponse
		// wfre=folderClient.getWebmailAllMailCountRequest(host, port, id,
		// pass,fdrnm);
		// map.addAttribute("MailCount", ""+(wfre.getAllemailcount()));
		map.addAttribute("QuotaPer", "" + qper);
		map.addAttribute("QuotaLimit", "" + ql);
		map.addAttribute("QuotaUses", "" + qu);
		
		// <---------Start reading an .ics file----------->

		
		GetFileResponse fileResponse = fileClient.getFileRequest("/"+id+"/calendar", id,pass);
		List<webmail.wsdl.File> fileList = fileResponse.getGetFilesByParentFile().getFileListResult().getFileList();
		List<CreateCalendarRequest> calendarfilelist=new ArrayList<CreateCalendarRequest>();
		GetEventsResponse res = null;
		CreateCalendarRequest caldetails=null;
		EventArray eventarray=new EventArray();
		List<String> filenames=new ArrayList<String>();
		for (webmail.wsdl.File file : fileList) 
		{
			caldetails=new CreateCalendarRequest();
			res = calendarclient.getallevents(file.getFileContent(), file.getFileName());
			caldetails.setCalColor(res.getCalendarcolor());
			caldetails.setCalID(res.getCalendarname());
			calendarfilelist.add(caldetails);
			filenames.add(file.getFileName());
			for(EventBean event:res.getEventList().getEventList())
			{
				event.setCalendar("/calendar/"+event.getCalendar());
//				event.getEndeventdate().setDay(event.getEndeventdate().getDay()+1);	
				if(event.getAllday().equals("on"))
	       		 {
		        		 event.getEndeventdate().setDay(event.getEndeventdate().getDay()+1);
	       		 	
	       		 }
		       	 else
		       	 {
		       		 event.getEndeventdate().setDay(event.getEndeventdate().getDay());
			        	 
		       	 }
			}
			eventarray.getEventList().addAll(res.getEventList().getEventList());
			
		}
		map.addAttribute("calendarfilelist", calendarfilelist);
		map.addAttribute("filenames", filenames);
		
		
		//GetFileResponse fileResponse1 = fileClient.getFileRequest("/"+id+"/sharedCalendars",id,pass);
		//fileList = fileResponse1.getGetFilesByParentFile().getFileListResult().getFileList();
		
		GetSharedFilesByPathWithContentResponse fileResponse1 = fileClient.getSharedFilesByPathWithContentRequest(id,pass, "/"+id+"/sharedCalendars") ;
		//System.out.println("shared cal");
		if(fileResponse1!=null)
		{
		fileList = fileResponse1.getGetSharedFilesByPath().getFileListResult().getFileList();
		}
		else
		{
			fileList=null;
		}
		List<CreateCalendarRequest> sharedcalendarfilelist=new ArrayList<CreateCalendarRequest>();
		List<String> sharedfilenames=new ArrayList<String>();
	
		
		for (webmail.wsdl.File file : fileList) 
		{
			caldetails=new CreateCalendarRequest();
			res = calendarclient.getallevents(file.getFileContent(), file.getFileName());
			caldetails.setCalColor(res.getCalendarcolor());
			caldetails.setCalID(res.getCalendarname());
			caldetails.setUserid(file.getCreatedBy());
			if(file.getUserSecurity().toString().contains(id))
			{
				caldetails.setPermission("manage");
			}
			else
			{
				caldetails.setPermission("read");
			}
			sharedcalendarfilelist.add(caldetails);
			sharedfilenames.add(file.getFileName());
			System.out.println(res.getEventList().getEventList().size());
			for(EventBean event:res.getEventList().getEventList())
			{
				if(event.getClazz()!=null)
				{
					if(event.getClazz().equalsIgnoreCase("public"))
					{
						event.setCalendar("/sharedCalendars/"+event.getCalendar());
						eventarray.getEventList().add(event);
					}
		
				}
			}
		}
		map.addAttribute("eventlist", eventarray.getEventList());
		map.addAttribute("sharedcalendarfilelist", sharedcalendarfilelist);
		map.addAttribute("sharedfilenames", sharedfilenames);
		map.addAttribute("webmailClient", webmailClient);*/
		return new ModelAndView("calendar", map);}

	

	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public ModelAndView getcontacts(ModelMap map, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		
		
		System.out.println("*************ses="+id);
		if(id==null)
		{
			//ses="SesExp";
			return new ModelAndView("redirect:/login?stat=ses", map);
		}
		
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String fdrnm=(String)hs.getAttribute("active_folder");
		String surl=(String)hs.getAttribute("caldevUrl");
		String proid=(String)hs.getAttribute("caldevProId");
		GetWebmailImapquotaResponse wfresponse=webmailClient.getWebmailImapquotaRequest(host, id, pass);
		Imapquota iqt  =wfresponse.getImapquota();
		System.out.println("*********iqt********Qlota="+ iqt.getQuotalimit());
		long ql= iqt.getQuotalimit();
		long qu=iqt.getQuotauses();
		long qper=0l;
		if( ql!=0)
		qper=(qu*100)/ql;
		//System.out.println("********************Quotalimi="+ per
		//System.out.println("********************Quota per="+qper);
		//GetWebmailAllMailCountResponse wfre=folderClient.getWebmailAllMailCountRequest(host, port, id, pass,fdrnm);
		//map.addAttribute("MailCount", ""+(wfre.getAllemailcount()));
		map.addAttribute("QuotaPer", ""+qper);
		map.addAttribute("QuotaLimit", ""+ql);
		map.addAttribute("QuotaUses", ""+qu);
		
		CardDav carddav=new CardDav();
        List<CardDavCollection> cards=carddav.getAllContactFolder(proid,surl, id, pass);
	 		map.addAttribute("webmailClient", webmailClient);
	 		//map.addAttribute("folderClient", folderClient);
	 		map.addAttribute("cardList", cards);
	 		Map Srdconlst = new HashMap();

	 		List<Shared> srdlst=sharedDao.getSharedDetailForLeft(id, false,true);
			if(srdlst.size()>0)
			{
				CardDav crddav=new CardDav();
				CardDavStore store=crddav.connect(proid, surl, id, pass);
			for(Shared srd: srdlst)
			{
				if(srd!=null)
				{
					
					String caluid=srd.getCalUID();
					String calurl=surl+"/caldav.php/"+srd.getUserfrom()+"/"+caluid+"/";
					try {
						CardDavCollection cardcol=store.getCollection(calurl);
						String disp=cardcol.getDisplayName();
						disp=disp+"("+srd.getUserfrom()+")";
						Srdconlst.put(calurl, disp);
						
					} catch (ObjectStoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ObjectNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			}
	 		
			map.addAttribute("sharedCardList", Srdconlst);
		return new ModelAndView("contacts", map);
		
	}
	
	@RequestMapping(value = "/mycalendar", method = RequestMethod.GET)
	public String mycalendar(ModelMap map) 
	{
		//System.out.println("**************contactpeople");
		return "mycalendar";
	}
	
	@RequestMapping(value = "/contactpeople", method = RequestMethod.GET)
	public String contactpeople(ModelMap map) 
	{
		//System.out.println("**************contactpeople");
		return "contactpeople";
	}
	
	
	@RequestMapping(value = "/contactdetail", method = RequestMethod.GET)
	public String contactdetail(ModelMap map) 
	{
		//System.out.println("**************contactdetail");
		return "contactdetail";
	}
	
	@RequestMapping(value = "/inbox", method = RequestMethod.GET)
	public ModelAndView getinbox(ModelMap map, HttpServletRequest request) 
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
		try
		{
		if(hs!=null)
		{
		String fpc=hs.getAttribute("forcePasswordChange").toString() ;
		if(fpc.equalsIgnoreCase("true"))
		{
			return new ModelAndView("redirect:/firstinbox", map);
		}
		}
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		
		String host=(String)hs.getAttribute("host");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String fdrnm=(String)hs.getAttribute("active_folder");
		GetWebmailImapquotaResponse wfresponse=webmailClient.getWebmailImapquotaRequest(host, id, pass);
		Imapquota iqt  =wfresponse.getImapquota();
		System.out.println("*********iqt********Qlota="+ iqt.getQuotalimit());
		long qper=0l;
		long ql= iqt.getQuotalimit();
		long qu=iqt.getQuotauses();
		if( ql!=0)
		qper=(qu*100)/ql;
		//System.out.println("********************Quotalimi="+ per
		//System.out.println("********************Quota per="+qper);
		GetWebmailAllMailCountResponse wfre=webmailClient.getWebmailAllMailCountRequest(host, port, id, pass,fdrnm);
		map.addAttribute("MailCount", ""+(wfre.getAllemailcount()));
		map.addAttribute("QuotaPer", ""+qper);
		map.addAttribute("QuotaLimit", ""+ql);
		map.addAttribute("QuotaUses", ""+qu);
		
		/*GetWebmailFolderResponse wfresponse1=webmailClient.getWebmailFolderRequest(host, id, pass);
		String myfdr=wfresponse1.getGetWebmailFolder();
		map.addAttribute("webmailClient", webmailClient);
		map.addAttribute("fldr_lst", myfdr);*/
		GetWebmailFolderOtherResponse sfres=webmailClient.getWebmailFolderOtherRequest(host, id, pass, "");
	 	List<MailImapFolders> sflst= sfres.getGetSubFolder().getMailFolderListReturn().getMailFolderList();
		
	 	map.addAttribute("webmailClient", webmailClient);
		map.addAttribute("imapfldrlst", sflst);
		try
		{
		String chatService=(String)hs.getAttribute("chatService");
		boolean chatConfigStatus=(boolean)hs.getAttribute("chatConfigStatus");
		if(chatService.equalsIgnoreCase("true") && !chatConfigStatus)
		{
		//TODO: SEPERATE THE CONFIGURATION
		
		try
		{
		String chathost=(String)hs.getAttribute("chathost");
		String		xmppDomain=id.substring(id.indexOf("@")+1);
		XmppChatClass xmppChatClass=(XmppChatClass)hs.getAttribute("xmppChatClass");
		xmppChatClass.createConnection(chathost,xmppDomain, packetReplyTimeout, request);
		xmppChatClass.registerListeners(chatImageFolder);
		//xmppChatClass.performLogin(loginUser.getUserid(), loginUser.getPassword(), onlineStatus);
		xmppChatClass.performLogin(id, pass, onlineStatus);
		scriptSessList.listenScriptSession();
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
		}catch(Exception ee)
		{
			
		}
		/*XmppChatClass xmppChatClass=(XmppChatClass)hs.getAttribute("xmppChatClass");
		xmppChatClass.addChatListenerInbox();	*/
		
		//TODO: SEPERATE THE CONFIGURATION
	//	xmppDomain=id.substring(id.indexOf("@")+1);
		
	//	scriptSessList.listenScriptSession();
		//map.addAttribute("imageurl", chatImageFolder);
		
		
		return new ModelAndView("inbox", map);
	
	}
	
	

	
	/*@RequestMapping(value = "/firstinbox", method = RequestMethod.GET)
	public ModelAndView firstinbox(ModelMap map, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		
		
		String id=(String)hs.getAttribute("id");
		System.out.println("*************ses="+id);
		if(id==null)
		{
			//ses="SesExp";
			return new ModelAndView("redirect:/login?stat=ses", map);
		}
		String host=(String)hs.getAttribute("host");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String fdrnm=(String)hs.getAttribute("active_folder");
		GetWebmailImapquotaResponse wfresponse=webmailClient.getWebmailImapquotaRequest(host, id, pass);
		Imapquota iqt  =wfresponse.getImapquota();
		System.out.println("*********iqt********Qlota="+ iqt.getQuotalimit());
		long ql= iqt.getQuotalimit();
		long qu=iqt.getQuotauses();
		long qper=(qu*100)/ql;
		//System.out.println("********************Quotalimi="+ per
		//System.out.println("********************Quota per="+qper);
		GetWebmailAllMailCountResponse wfre=webmailClient.getWebmailAllMailCountRequest(host, port, id, pass,fdrnm);
		map.addAttribute("MailCount", ""+(wfre.getAllemailcount()));
		map.addAttribute("QuotaPer", ""+qper);
		map.addAttribute("QuotaLimit", ""+ql);
		map.addAttribute("QuotaUses", ""+qu);
		
		GetWebmailFolderResponse wfresponse1=webmailClient.getWebmailFolderRequest(host, id, pass);
		String myfdr=wfresponse1.getGetWebmailFolder();
		map.addAttribute("webmailClient", webmailClient);
		map.addAttribute("fldr_lst", myfdr);
		
		XmppChatClass xmppChatClass=new XmppChatClass();
		//TODO: SEPERATE THE CONFIGURATION
		xmppChatClass.createConnection(xmppDomain, packetReplyTimeout, request);
		xmppChatClass.registerListeners(chatImageFolder);
		//xmppChatClass.performLogin(loginUser.getUserid(), loginUser.getPassword(), onlineStatus);
		xmppChatClass.performLogin(id, pass, onlineStatus);
		scriptSessList.listenScriptSession();
		request.getSession().setAttribute("xmppChatClass", xmppChatClass);
		map.addAttribute("imageurl", chatImageFolder);
		return new ModelAndView("firstinbox", map);
	
	}
	*/
	
	/*
	 * @Autowired private PersonRepo personRepo;
	 */
	
}
