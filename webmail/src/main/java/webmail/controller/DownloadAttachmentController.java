package webmail.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.mail.BodyPart; 
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.Flags.Flag;
import javax.mail.internet.InternetAddress;
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
import org.springframework.web.servlet.ModelAndView;

import webmail.bean.TextDecode;
import webmail.webservice.client.FileClient;
import webmail.webservice.client.WebmailClient;
import webmail.wsdl.CreateFileResponse;
import webmail.wsdl.GetMailAttachDownloadResponse;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.example.Connections;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.star.io.ConnectException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class DownloadAttachmentController {

	@Autowired
	WebmailClient webmailClient;

	@Autowired
	private FileClient fileClient;
	
	 String fpath="";
	 String flname="";
	 boolean dnstate=true;
	
	  boolean ret_Att1=false;
	   String ret_cnt1="";
	   String ret_sub1="";
	   String ret_bou1="";
	  
	   boolean ret_Att=false;
	   String ret_cnt="";
	   String ret_sub="";
	   String ret_bou="";
	   InputStream insSave=null;
	   boolean savest=false;
	 HashMap hm=new HashMap();
	
	@RequestMapping(value = "/downloadMailZipAllAttach", method = RequestMethod.GET)
	public void downloadMailZipAllAttach(ModelMap map, Principal principal, HttpServletRequest request,HttpServletResponse response) 
	{
		String res="";
		boolean status=true;
		String uid= request.getParameter("uid");
		//String foldernm= request.getParameter("folder");
		String subject= request.getParameter("subject");
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String foldername=(String)hs.getAttribute("active_folder");
		 String realPath = request.getServletContext().getRealPath("/");
		    String filePath  = realPath+"WEB-INF/view/jsp/temp/";
		    fpath=filePath;
		    hm.clear();  
		    
		    ret_Att=false;
	          ret_cnt="";
	  	    ret_sub="";
	  	  ret_bou="";
		//System.out.println("***********************hiiiiiiiiiiiiiiiiiiii");
		/*GetMailAttachDownloadResponse attres=folderClient.downloadMailAttachRequest(host, port, id, pass, uid, foldername, name);
		
		InputStream input =(InputStream) attres.getWebamilInputStream();
		
		System.out.println("^^^^^^^^^^^^"+input);
		
		 String headerKey = "Content-Disposition";
         String headerValue = String.format("attachment; filename=\"%s\"",  name);
         response.setHeader(headerKey, headerValue);
         String destFilePath = "/" + name;
         try
         {
         //OutputStream output = response.getOutputStream();
         FileOutputStream output = new FileOutputStream(destFilePath);
         byte[] buffer = new byte[4096];

         int byteRead;

         while ((byteRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, byteRead);
         }
         output.close();
         }
         catch(IOException e)
         {
        	 System.out.println(e);
         }
		*/
		
	//	boolean status=true;
		//	GetMailDisplayResponse inboxlist= new GetMailDisplayResponse();
			
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
	int umsg= folder.getUnreadMessageCount();
	 for (int mi = 0; mi< msg.length; mi++)
	  {
	
		// FileOutputStream fos = new FileOutputStream("C:\\Users\\nirbhay\\AppData\\Local\\Temp"+subject+".zip");
      //  FileOutputStream fos = new FileOutputStream("C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\"+subject+".zip");
		// FileOutputStream fos = new FileOutputStream("/tmp/tomcat7-tomcat7-tmp/"+subject+".zip");
		
		 FileOutputStream fos = new FileOutputStream(filePath+subject+".zip");
		 ZipOutputStream zos = new ZipOutputStream(fos);
     	 byte[] buffer = new byte[1024];
		
     	Message message1 = msg[mi];
		 writePart(message1);
	 
	  Object content = msg[mi].getContent();  

	 // String attch="";
	 // String mail_cont="";
	  
	  if(msg[mi].getContentType().contains("text/html;") || msg[mi].getContentType().contains("text/plain;"))
	  {
		 /* String cnt=msg[i].getContent().toString();
	  	  
	  		mail_cont=cnt;
	  	  */
	  }
	  else
	  {/*
		  
		if (content instanceof Multipart)  
	{  
	    Multipart multipart = (Multipart)content;  
	    
	 

	    for (int j = 0; j < multipart.getCount(); j++) {

	        BodyPart bodyPart = multipart.getBodyPart(j);
	       
	        
	        String disposition = bodyPart.getDisposition();

	          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
	          	{ 
	        	 
	              DataHandler handler = bodyPart.getDataHandler();
	             
	           
	              InputStream in = bodyPart.getInputStream();
	              ZipEntry ze= new ZipEntry( handler.getName());
	          		zos.putNextEntry(ze);
	   
	   
	          	int len;
	          	while ((len = in.read(buffer)) > 0) {
	          		zos.write(buffer, 0, len);
	          	}
	   
	          	in.close();
	              
	            
	            }
	         
	   
	  }
	  }
	 */ }
	 // String arr[]=attch.split(",");
	  //inb.setMailContent(mail_cont);
	  
	  
	  
	  Set set = hm.entrySet();
	     // Get an iterator
	     Iterator i = set.iterator();
	     // Display elements
	    // System.out.println("!!!!!!!!!!!!!!!!!!!!");
	     while(i.hasNext()) {
	        Map.Entry me = (Map.Entry)i.next();
	       // if(me.getKey().toString().equalsIgnoreCase(filnm))
	       
	        	try
	        	{
	        	String new_flnm=me.getValue().toString();
	        	File file = new File(filePath+new_flnm);
	        	InputStream in = new FileInputStream(file);
	        	
	        	 ZipEntry ze= new ZipEntry( me.getKey().toString());
	          		zos.putNextEntry(ze);
	   
	   
	          	int len;
	          	while ((len = in.read(buffer)) > 0) {
	          		zos.write(buffer, 0, len);
	          	}
	   
	          	in.close();
	          	file.delete();
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	       
	     }
	  
	  
	  zos.closeEntry();
  	//remember close it
  	zos.close();
  	

  //	File fl=new File("C:\\Users\\nirbhay\\AppData\\Local\\Temp"+subject+".zip");
  //	File fl=new File("C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\"+subject+".zip");
  	File fl=new File(filePath  +subject+".zip");
 
  String headerKey = "Content-Disposition";
  String headerValue = String.format("attachment; filename=\"%s\"", subject+".zip");
  response.setHeader(headerKey, headerValue);
  InputStream input = new FileInputStream(fl);
 
  OutputStream output = response.getOutputStream();
  byte[] buffer1 = new byte[4096];

  int byteRead;

  while ((byteRead = input.read(buffer1)) != -1) {
     output.write(buffer1, 0, byteRead);
  }
  
 
  input.close();
  output.close();
  fl.delete();
  
	  }
	 
	  folder.close(true);
	  store.close();
	  }
	  catch(MessagingException e)
	        {
		  	status=false;
	        }
	  catch(IOException e)
	        {
			status=false;
	        }    
	  catch(Exception e)
	        {
			status=false;
	        }
	      
	        hm.clear();  
	      
		//return "downloadMailAttachByName";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/downloadMailAttachByName", method = RequestMethod.GET)
	public void listWebmailInbox(ModelMap map, Principal principal, HttpServletRequest request,HttpServletResponse response) 
	{
		String res="";
		boolean status=true;
		String uid= request.getParameter("uid");
		//String foldernm= request.getParameter("folder");
		String name= request.getParameter("name");
		flname=name;
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String foldername=(String)hs.getAttribute("active_folder");
		System.out.println("***********************hiiiiiiiiiiiiiiiiiiii");
		
		  ret_Att1=false;
          ret_cnt1="";
  	    ret_sub1="";
  	  ret_bou1="";
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
	int umsg= folder.getUnreadMessageCount();
	 for (int i = 0; i< msg.length; i++)
	  {
		 Message message1 = msg[i];
		 writePart(message1, response);
		 if(dnstate)
		 {
		
		 
	  Object content = msg[i].getContent();  

	 // String attch="";
	 // String mail_cont="";
	  
	  if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
	  {
		 /* String cnt=msg[i].getContent().toString();
	  	  
	  		mail_cont=cnt;
	  	  */
	  }
	  else
	  {
		  /*if (content instanceof String)  
	{
			  
	    String body = (String)content;  
	  
	     attch="";
	}  
	else */
		if (content instanceof Multipart)  
	{  
	    Multipart multipart = (Multipart)content;  
	    
	 

	    for (int j = 0; j < multipart.getCount(); j++) {

	        BodyPart bodyPart = multipart.getBodyPart(j);
	       // Attachment at=new Attachment();
	        /*mail_cont=getText(bodyPart);
	        if(mail_cont.length()>100)
	    	  {
	    	    mail_cont=mail_cont.substring(0, 99);
	    	  }
	    	 */
	        
	        String disposition = bodyPart.getDisposition();

	          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
	          	{ 
	        	 
	              DataHandler handler = bodyPart.getDataHandler();
	              if(handler.getName().equalsIgnoreCase(name))
	              {
	            	 // HttpServletResponse response=null;
	            	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
	              String headerKey = "Content-Disposition";
	              String headerValue = String.format("attachment; filename=\"%s\"",  handler.getName());
	              response.setHeader(headerKey, headerValue);
	              InputStream input = bodyPart.getInputStream();
	              //String destFilePath = "/" +  handler.getName();
	              //OutputStream output = response.getOutputStream();
	            //  FileOutputStream output = new FileOutputStream(destFilePath);
	              //FileOutputStream output = new FileOutputStream();
	              OutputStream output = response.getOutputStream();
	              byte[] buffer = new byte[4096];

	              int byteRead;

	              while ((byteRead = input.read(buffer)) != -1) {
	                 output.write(buffer, 0, byteRead);
	              }
	              input.close();
	              output.close();
	              }
	              
	              
	        
	            }
	          else 
	          	{
	        	  
	        	  
	            }
	   
	  }
	  }
	  }
	 // String arr[]=attch.split(",");
	  //inb.setMailContent(mail_cont);
	  
		 }
	  }
	 
	  folder.close(true);
	  store.close();
	  }
	  catch(MessagingException e)
	        {
		  	status=false;
		  	e.printStackTrace();
	        }
	  catch(IOException e)
	        {
			status=false;
			e.printStackTrace();
	        }    
	  catch(Exception e)
	        {
			status=false;
			e.printStackTrace();
	        }
	        flname="";
	        dnstate=true;
		//return "downloadMailZipAllAttach";
	}
	
	
	
	
	

	@RequestMapping(value = "/viewMailAttachByName", method = RequestMethod.GET)
	@ResponseBody
	public String viewMailAttachByName(ModelMap map, Principal principal, HttpServletRequest request,HttpServletResponse response) 
	{
		String realPath1 = request.getServletContext().getRealPath("/");
	    String filePath  = realPath1+"WEB-INF/view/jsp/js/temp/";
	    fpath=filePath;
	    hm.clear();  
	    String retname="";
		String uidnm=	  UUID.randomUUID().toString();
		String res="";
		boolean status=true;
		String uid= request.getParameter("uid");
		//String foldernm= request.getParameter("folder");
		String name= request.getParameter("nm");
		if(!(name.toLowerCase().endsWith(".txt")|| name.toLowerCase().endsWith(".html") ||  name.toLowerCase().endsWith(".odt")|| name.toLowerCase().endsWith(".ppt")||  name.toLowerCase().endsWith(".pptx")|| name.toLowerCase().endsWith(".ods")|| name.toLowerCase().endsWith(".odp") || name.toLowerCase().endsWith(".pdf") || name.toLowerCase().endsWith(".doc") || name.toLowerCase().endsWith(".docx") || name.toLowerCase().endsWith(".xlsx") || name.toLowerCase().endsWith(".xls") ))
		{
			return "notsupported";
		}
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String foldername=(String)hs.getAttribute("active_folder");
		System.out.println("***********************hiiiiiiiiiiiiiiiiiiii");
		
		 String path = request.getContextPath();
         String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		       
	  	}
	  folder.open(Folder.READ_WRITE);
	  long [] arr={Long.parseLong(uid)};
	 Message[] msg =folder.getMessagesByUID(arr);
	int umsg= folder.getUnreadMessageCount();
	 for (int im = 0; im< msg.length; im++)
	  {
		 

		 Message message1 = msg[im];
		 writePart(message1);
	  Object content = msg[im].getContent();  

	
	  
	  if(msg[im].getContentType().contains("text/html;") || msg[im].getContentType().contains("text/plain;"))
	  {
		
	  }
	  else
	  {/*
		  
		if (content instanceof Multipart)  
		{  
	    Multipart multipart = (Multipart)content;  
	    
	 

	    for (int j = 0; j < multipart.getCount(); j++) {

	        BodyPart bodyPart = multipart.getBodyPart(j);
	      
	        
	        String disposition = bodyPart.getDisposition();

	          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
	          	{ 
	        	 
	              DataHandler handler = bodyPart.getDataHandler();
	              if(handler.getName().equalsIgnoreCase(name))
	              {
	            
	              InputStream input = bodyPart.getInputStream();
	             
	              String realPath = request.getServletContext().getRealPath("/");
	            FileOutputStream output = new FileOutputStream(realPath+"WEB-INF/view/jsp/js/temp/"+uidnm+"_"+name);
	            retname=uidnm+"_"+name;
	              byte[] buffer = new byte[4096];

	              int byteRead;

	              while ((byteRead = input.read(buffer)) != -1) {
	                 output.write(buffer, 0, byteRead);
	              }
	              input.close();
	              output.close();
	              
	              if(!(name.toLowerCase().endsWith(".pdf") || name.toLowerCase().endsWith(".PDF")))
	              {
	            	  try
	            	  {
	            	  java.io.File inputFile = new java.io.File(realPath+"WEB-INF/view/jsp/js/temp/"+uidnm+"_"+name); //
	            	  if(!inputFile.exists()) 
	            	  {
	            		  System.out.println("JOD input file not found");
	            	  }
	            	  java.io.File outputFile = new java.io.File(realPath+"WEB-INF/view/jsp/js/temp/"+uidnm+"_"+name+".pdf"); //
	            	       OpenOfficeConnection connection = new      SocketOpenOfficeConnection("127.0.0.1",8100);
	            	      try {
	            	        connection.connect();
	            	        retname=retname+".pdf";
	            	      } 
	            	      catch (Exception e) {
		            	        // TODO Auto-generated catch block
		            	        e.printStackTrace();
		            	    } 
	            	      DocumentConverter     converter = new  OpenOfficeDocumentConverter(connection);
	            	      converter.convert(inputFile, outputFile); // close
	            	      connection.disconnect(); 
	            	  }
	            	  catch(Exception e)
	            	  {
	            		  System.out.println("JOD Error");
	            		  e.printStackTrace();
	            	  }
	              }
	              
	              
	              }
	              
	              
	              
	           
	            }
	          else 
	          	{
	        	  
	        	  
	            }
	   
	  }
	  }
	 */ }
	
	  
	  Set set = hm.entrySet();
	     // Get an iterator
	     Iterator i = set.iterator();
	     // Display elements
	    // System.out.println("!!!!!!!!!!!!!!!!!!!!");
	     while(i.hasNext()) {
	        Map.Entry me = (Map.Entry)i.next();
	       if(me.getKey().toString().equalsIgnoreCase(name))
	       {
	        	try
	        	{
	        	String new_flnm=me.getValue().toString();
	        	//File file = new File(filePath+new_flnm);
	        	//InputStream in = new FileInputStream(file);
	        	retname=new_flnm;
	        	 if(!(name.toLowerCase().endsWith(".pdf") || name.toLowerCase().endsWith(".PDF")))
	              {
	            	  try
	            	  {
	            	  java.io.File inputFile = new java.io.File(realPath1+"WEB-INF/view/jsp/js/temp/"+new_flnm); //
	            	  if(!inputFile.exists()) 
	            	  {
	            		  System.out.println("JOD input file not found");
	            	  }
	            	  java.io.File outputFile = new java.io.File(realPath1+"WEB-INF/view/jsp/js/temp/"+new_flnm+".pdf"); //
	            	  /*  java.io.File inputFile = new java.io.File(basePath+"js/temp/"+uidnm+"_"+name); //
	            	    java.io.File outputFile = new java.io.File(basePath+"js/temp/"+uidnm+"_"+name+".pdf"); //
*/	            	      OpenOfficeConnection connection = new      SocketOpenOfficeConnection("127.0.0.1",8100);
	            	      try {
	            	        connection.connect();
	            	        retname=retname+".pdf";
	            	      } 
	            	      catch (Exception e) {
		            	        // TODO Auto-generated catch block
		            	        e.printStackTrace();
		            	    } 
	            	      DocumentConverter     converter = new  OpenOfficeDocumentConverter(connection);
	            	      converter.convert(inputFile, outputFile); // close
	            	      connection.disconnect(); 
	            	  }
	            	  catch(Exception e)
	            	  {
	            		  System.out.println("JOD Error");
	            		  e.printStackTrace();
	            	  }
	              }
	        	
	        
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	       }
	       
	     }
	   
	  
	  
	  
	  }
	 
	  folder.close(true);
	  store.close();
	  }
	  catch(MessagingException e)
	        {
		  e.printStackTrace();
		  	status=false;
	        }
	  catch(IOException e)
	        {
		  e.printStackTrace();
			status=false;
	        }    
	  catch(Exception e)
	        {
		  e.printStackTrace();
			status=false;
	        }
	      
	        hm.clear();  
		return basePath+"js/temp/"+retname;
	}
	


	@RequestMapping(value = "/viewIMGMailAttachByName", method = RequestMethod.GET)
@ResponseBody
	public String viewIMGMailAttachByName(ModelMap map, Principal principal, HttpServletRequest request,HttpServletResponse response) 
	{
		String res="";
		boolean status=true;
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String uid= request.getParameter("uid");
		//String foldernm= request.getParameter("folder");
		String name= request.getParameter("nm");
		//HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String foldername=(String)hs.getAttribute("active_folder");
		
		
		 String path = request.getContextPath();
         String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		       
	  	}
	  folder.open(Folder.READ_WRITE);
	  long [] arr={Long.parseLong(uid)};
	 Message[] msg =folder.getMessagesByUID(arr);
	int umsg= folder.getUnreadMessageCount();
	 for (int i = 0; i< msg.length; i++)
	  {
		 

	 
	  Object content = msg[i].getContent();  

	
	  
	  if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
	  {
		
	  }
	  else
	  {
		  
		if (content instanceof Multipart)  
	{  
	    Multipart multipart = (Multipart)content;  
	    
	 

	    for (int j = 0; j < multipart.getCount(); j++) {

	        BodyPart bodyPart = multipart.getBodyPart(j);
	      
	        
	        String disposition = bodyPart.getDisposition();

	          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
	          	{ 
	        	 
	              DataHandler handler = bodyPart.getDataHandler();
	              if(handler.getName().equalsIgnoreCase(name))
	              {
	            	
	              InputStream input = bodyPart.getInputStream();
	              byte[] bytes = IOUtils.toByteArray(input);
	             res = new sun.misc.BASE64Encoder().encode(bytes);
	             input.close();
	              }
	              
	              
	              
	           
	            }
	          else 
	          	{
	        	  
	        	  
	            }
	   
	  }
	  }
	  }
	
	  
	  
	  }
	 
	  folder.close(true);
	  store.close();
	  }
	  catch(MessagingException e)
	        {
		  e.printStackTrace();
		  	status=false;
	        }
	  catch(IOException e)
	        {
		  e.printStackTrace();
			status=false;
	        }    
	  catch(Exception e)
	        {
		  e.printStackTrace();
			status=false;
	        }
	       
			return res;
	}
	

	/*@RequestMapping(value = "/viewattachment", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView viewattachment(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	
		String uid= request.getParameter("uid");
		//String foldernm= request.getParameter("folder");
		String name= request.getParameter("nm");
		map.addAttribute("folder", "nirbhay");
		return new ModelAndView("viewattachment", map);
		
	}*/
	
	
	@RequestMapping(value = "/saveEDMSMailAttachByName", method = RequestMethod.GET)
	@ResponseBody
	public String saveEDMSMailAttachByName(ModelMap map, Principal principal, HttpServletRequest request,HttpServletResponse response) 
		{
			
		String uid= request.getParameter("uid");
		//String foldernm= request.getParameter("folder");
		String name= request.getParameter("nm");	
		String res="";
			boolean status=true;
			insSave=null;
			savest=false;
			flname=name;
			
			HttpSession hs=request.getSession();
			String host=(String)hs.getAttribute("host");
			String id=(String)hs.getAttribute("id");
			String pass=(String)hs.getAttribute("pass");
			String port=(String)hs.getAttribute("port");
			String foldername=(String)hs.getAttribute("active_folder");
			
			
			 String path = request.getContextPath();
	         String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
			       
		  	}
		  folder.open(Folder.READ_WRITE);
		  long [] arr={Long.parseLong(uid)};
		 Message[] msg =folder.getMessagesByUID(arr);
		int umsg= folder.getUnreadMessageCount();
		 for (int i = 0; i< msg.length; i++)
		  {
			 
			 writePartSave(msg[i]);
		 
		  Object content = msg[i].getContent();  

		
		  
		  if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
		  {
			
		  }
		  else
		  {
			  
			if(savest)
			{
				byte[] encodedFile = null;
				 encodedFile = org.apache.commons.codec.binary.Base64.encodeBase64(IOUtils.toByteArray(insSave));
		             CreateFileResponse cr = fileClient.createFileBC(name, "/" + id, id, pass, "", "", encodedFile, 0);
		            
			}
			else
			{
			  
			  if (content instanceof Multipart)  
		{  
		    Multipart multipart = (Multipart)content;  
		    
		 

		    for (int j = 0; j < multipart.getCount(); j++) {

		        BodyPart bodyPart = multipart.getBodyPart(j);
		      
		        
		        String disposition = bodyPart.getDisposition();

		          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
		          	{ 
		        	 
		              DataHandler handler = bodyPart.getDataHandler();
		              if(handler.getName().equalsIgnoreCase(name))
		              {
		            	
		             InputStream input = bodyPart.getInputStream();
		             byte[] encodedFile = null;
		             encodedFile = org.apache.commons.codec.binary.Base64
		 					.encodeBase64(IOUtils.toByteArray(input));
		             CreateFileResponse cr = fileClient.createFileBC(name, "/" + id, id, pass, "", "", encodedFile, 0);
		             input.close();
		             
		              }
		              
		              
		              
		           
		            }
		          else 
		          	{
		        	  
		        	  
		            }
		   
		  }
		  }
		  }
		  }
		  
		  
		  }
		 
		  folder.close(true);
		  store.close();
		  }
		  catch(MessagingException e)
		        {
			  e.printStackTrace();
			  	status=false;
		        }
		  catch(IOException e)
		        {
			  e.printStackTrace();
				status=false;
		        }    
		  catch(Exception e)
		        {
			  e.printStackTrace();
				status=false;
		        }
		       
		        insSave=null;
				savest=false;
				flname="";
				return ""+status;
		}
	
	
	
	public  void writePart(Part p) throws Exception {
	      if (p instanceof Message)
	         //Call methos writeEnvelope
	    //     writeEnvelope((Message) p);

	     // System.out.println("----------------------------");
	    	  p.getContentType();
	     // System.out.println("CONTENT-TYPE: " + p.getContentType());

	      //check if the content is plain text
	      if (p.isMimeType("text/plain")) {
	    	
	    	  if(ret_Att)
	    	  {
	    	     Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 //System.out.println(h.getName()+": "+ h.getValue());
	    		 ret_cnt+="\n"+h.getName()+": "+ h.getValue();
	    		 }
	    		 try
	    		 {
	    		 ret_cnt+="\n"+p.getContent().toString();
	    		 }
	    		 catch(Exception e)
	    		 {
	    			 ret_cnt+="\n"+IOUtils.toString(p.getInputStream());
	    		 }
	    		 ret_cnt+="\n\n--"+ret_bou;
	    	  }
	    	  
	    	  try
	    	  {
	    	  String nm= p.getFileName();
	    	  if(nm!=null && nm.length()>0)
		         {	
	          InputStream input =  null;
	          try
	          {
	          input=new ByteArrayInputStream(p.getContent().toString().getBytes());
	          }
	    		 catch(Exception e)
	    		 {
	    			 input=new ByteArrayInputStream((IOUtils.toString(p.getInputStream())).getBytes()); 
	    		 }
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	         
	          hm.put(nm, uidnm+"_"+nm);
		         }
	    	  }
	    	  catch(Exception e){}
	      } 
	      else if (p.isMimeType("text/html")) {
	    	  if(ret_Att)
	    	  {
	    	  Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		// System.out.println(h.getName()+": "+ h.getValue());
	    		 if(ret_cnt==null || ret_cnt.length()<=0)
	    		 {
	    			 ret_cnt=h.getName()+": "+ h.getValue(); 
	    		 }
	    		 else
	    		 {
	    			 ret_cnt+="\n"+h.getName()+": "+ h.getValue();
	    		 }
	    		 }
	    		 try
	    		 {
	    		 ret_cnt+="\n\n\n"+p.getContent().toString();
	    		 }
	    		 catch(Exception e)
	    		 {
	    			 ret_cnt+="\n\n\n"+IOUtils.toString(p.getInputStream()); 
	    		 }
	    		 ret_cnt+="\n\n--"+ret_bou+"--";
	    		 
	    			
	    		 InputStream input = new ByteArrayInputStream(ret_cnt.getBytes());
	    		 String uidnm=	  UUID.randomUUID().toString();
	    		 if(ret_sub==null || ret_sub.length()<=0)
	    			 ret_sub="Message";
	    		 ret_sub=ret_sub.replaceAll("[^\\w\\s]","");	
		          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+ret_sub+".eml"));
		          byte[] buffer = new byte[4096];

		          int byteRead;

		          while ((byteRead = input.read(buffer)) != -1) {
		             output.write(buffer, 0, byteRead);
		          }
		          input.close();
		          output.close();
		         
		          hm.put(ret_sub+".eml", uidnm+"_"+ret_sub+".eml");
	    	  }
	    	  
	    	  
	    	  try
	    	  {
	    	  String nm= p.getFileName();
	    	  if(nm!=null && nm.length()>0)
		         {	
	          InputStream input =null;
	          try
	          {
	         input =  new ByteArrayInputStream(p.getContent().toString().getBytes());
	          }
	          catch(Exception e)
	          {
	        	  input =  new ByteArrayInputStream((IOUtils.toString(p.getInputStream())).getBytes()); 
	          }
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	         
	          hm.put(nm, uidnm+"_"+nm);
		         }
	    	  }
	    	  catch(Exception e){}
	    	  
		      } 
	      
	      else if (p.isMimeType("text/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
	    	  if(nm!=null && nm.length()>0)
		         {	
	          InputStream input =  new ByteArrayInputStream(p.getContent().toString().getBytes());
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	         
	          hm.put(nm, uidnm+"_"+nm);
		         }
	    	  }
	    	  catch(Exception e){}
	      }
	      
	      
	      else if (p.isMimeType("audio/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
	    	  if(nm!=null && nm.length()>0)
		         {	
	          InputStream input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	         
	          hm.put(nm, uidnm+"_"+nm);
		         }
	    	  }
	    	  catch(Exception e){}
	      }
	      
	      
	      else if (p.isMimeType("video/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
	    	  if(nm!=null && nm.length()>0)
		         {	
	          InputStream input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	         
	          hm.put(nm, uidnm+"_"+nm);
		         }
	    	  }
	    	  catch(Exception e){}
	      }
	      
	      //check if the content has attachment
	      else if (p.isMimeType("multipart/*")) {
	    	  if(ret_Att)
	    	  {
	    	  Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 if(h.getName().equalsIgnoreCase("Subject"))
	    		 {
	    			 ret_sub=h.getValue();
	    			
	    				if(ret_sub!=null)
	    					ret_sub=TextDecode.decodeUTF8Text(ret_sub);
	    				else
	    					ret_sub="";
	    				
	    		 }
	    		 else  if(h.getName().equalsIgnoreCase("Content-Type"))
	    		 {
	    			 String val=h.getValue();
	    			 String arr[]=val.split("\"");
	    			 if(arr.length>1)
	    			 {
	    				 ret_bou=arr[1];
	    			 }
	    		 }
	    		 if(ret_cnt.length()==0)
	    		 {
	    			 ret_cnt=h.getName()+": "+ h.getValue();
	    		 }
	    		 else
	    		 {
	    			 ret_cnt+="\n"+h.getName()+": "+ h.getValue();
	    		 }
	    		// System.out.println(h.getName()+": "+ h.getValue());
	    		 }
	    		 ret_cnt+="\n\n--"+ret_bou;
	    	  }
	    	 
	    	  
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	            writePart(mp.getBodyPart(i));
	      } 
	      //check if the content is a nested message
	      else if (p.isMimeType("message/rfc822")) {
	    
	  	    	  ret_Att=true;
	  	    	  
	  	         writePart((Part) p.getContent());
	  	      } 
	  
	      else if (p.getContentType().contains("image/")) {
	    	  String nm= p.getFileName();
		        
	    	  
	    	  Enumeration headers = p.getAllHeaders();
	    		 String con_id="";
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 if(h.getName().equalsIgnoreCase("Content-ID"))
	    		  {
	    			 con_id= h.getValue();
	    			 break;
	    		  }
	    		 }
	    	  
	    	  if(con_id.equalsIgnoreCase(""))
	    	  {
	        	
	          InputStream input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	         
	          hm.put(nm, uidnm+"_"+nm);
	    	  }
	      } 
	      
 else if (p.isMimeType("message/delivery-status")) {
	    	  
	    	  String nm="details.txt";
	           
	           
		          Object o = p.getContent();
		          InputStream input = (InputStream) o;
		          
		          String uidnm=	  UUID.randomUUID().toString();
		          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
		          byte[] buffer = new byte[4096];

		          int byteRead;

		          while ((byteRead = input.read(buffer)) != -1) {
		             output.write(buffer, 0, byteRead);
		          }
		          input.close();
		          output.close();
		         
		          hm.put(nm, uidnm+"_"+nm);
	      }
	      
 else if(p.isMimeType("text/calendar"))
 {
	  
       
       
    	   String nm="invite.ics";
    	   Object o = p.getContent();
	          InputStream input = (InputStream) o;
	          
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	         
	          hm.put(nm, uidnm+"_"+nm);
        
 }
	      
	      else if (p.isMimeType("application/*")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	        
	       
        	
          InputStream input = null;
          if(p.getContentType().startsWith("application/octet-stream"))
          {
        	  input=(InputStream)p.getContent();
          }
          else
          {
        	  try
        	  {
        	  input=(InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
        	  }
        	  catch(Exception e)
        	  {
        		  input=(InputStream)p.getContent();
        	  }
          }
          
          
          String uidnm=	  UUID.randomUUID().toString();
          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
          byte[] buffer = new byte[4096];

          int byteRead;

          while ((byteRead = input.read(buffer)) != -1) {
             output.write(buffer, 0, byteRead);
          }
          input.close();
          output.close();
         
          hm.put(nm, uidnm+"_"+nm);
         
	       }
	      else if (p.isMimeType("binary/octet-stream")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	        
	       
        	
          InputStream input = null;
         try
        	  {
        	  input=(InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
        	  }
        	  catch(Exception e)
        	  {
        		  input=(InputStream)p.getContent();
        	  }
        
          
          
          String uidnm=	  UUID.randomUUID().toString();
          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
          byte[] buffer = new byte[4096];

          int byteRead;

          while ((byteRead = input.read(buffer)) != -1) {
             output.write(buffer, 0, byteRead);
          }
          input.close();
          output.close();
         
          hm.put(nm, uidnm+"_"+nm);
         
	       }
	      else if (p.getContentType().startsWith("binary/octet-stream")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	        
	       
        	
          InputStream input = null;
         try
        	  {
        	  input=(InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
        	  }
        	  catch(Exception e)
        	  {
        		  input=(InputStream)p.getContent();
        	  }
        
          
          
          String uidnm=	  UUID.randomUUID().toString();
          OutputStream output =  new FileOutputStream(new File(fpath+uidnm+"_"+nm));
          byte[] buffer = new byte[4096];

          int byteRead;

          while ((byteRead = input.read(buffer)) != -1) {
             output.write(buffer, 0, byteRead);
          }
          input.close();
          output.close();
         
          hm.put(nm, uidnm+"_"+nm);
         
	       }
	      
	      else {
	         Object o = p.getContent();
	        
	   }

	}
	
	
	
	public  void writePart(Part p,HttpServletResponse response) throws Exception {
	      if (p instanceof Message)
	         //Call methos writeEnvelope
	    //     writeEnvelope((Message) p);

	     // System.out.println("----------------------------");
	    	  p.getContentType();
	     // System.out.println("CONTENT-TYPE: " + p.getContentType());

	      //check if the content is plain text
	      if (p.isMimeType("text/plain")) {
	    	  if(ret_Att1)
	    	  {
	    	  Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 //System.out.println(h.getName()+": "+ h.getValue());
	    		 ret_cnt1+="\n"+h.getName()+": "+ h.getValue();
	    		 }
	    		 try
	    		 {
	    		 ret_cnt1+="\n"+p.getContent().toString();
	    		 }
	    		 catch(Exception e)
	    		 {
	    			 ret_cnt1+="\n"+IOUtils.toString(p.getInputStream());
	    		 }
	    		 ret_cnt1+="\n\n--"+ret_bou1;
	    	  }
	    	  
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		         if(nm!=null && nm.length()>0)
		         {
	    	  if(nm.equalsIgnoreCase(flname))
	          {
	        	 // HttpServletResponse response=null;
	        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
	          String headerKey = "Content-Disposition";
	          String headerValue = String.format("attachment; filename=\"%s\"", flname);
	          response.setHeader(headerKey, headerValue);
	          Object o = p.getContent();
	          InputStream input = null;
	          try
	          {
	          input=new ByteArrayInputStream(p.getContent().toString().getBytes());
	          }
	          catch(Exception e)
	          {
	        	  input=new ByteArrayInputStream((IOUtils.toString(p.getInputStream())).getBytes()); 
	          }
	         
	          OutputStream output = response.getOutputStream();
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          dnstate=false;
	          }
		         }
	    	  }
	    	  catch(Exception e){}
	    	  
	      } 
	      else if (p.isMimeType("text/html")) {
	    	  if(ret_Att1)
	    	  {
	    	  Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		// System.out.println(h.getName()+": "+ h.getValue());
	    		 if(ret_cnt1==null || ret_cnt1.length()<=0)
	    		 {
	    			 ret_cnt1=h.getName()+": "+ h.getValue();
	    		 }
	    		 else
	    		 {
	    			 ret_cnt1+="\n"+h.getName()+": "+ h.getValue();
	    		 }
	    		 }
	    		 try
	    		 {
	    		 ret_cnt1+="\n\n\n"+p.getContent().toString();
	    		 }
	    		 catch(Exception e)
	    		 {
	    			 ret_cnt1+="\n\n\n"+IOUtils.toString(p.getInputStream());
	    		 }
	    		 ret_cnt1+="\n\n--"+ret_bou1+"--";
	    		 
	    			
	    		 if(ret_sub1==null || ret_sub1.length()<=0)
	    			 ret_sub1="Message";
	    		 ret_sub1=ret_sub1.replaceAll("[^\\w\\s]","");	
	           
	             if((ret_sub1+".eml").equalsIgnoreCase(flname))
		          {
		        	 // HttpServletResponse response=null;
		        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
		          String headerKey = "Content-Disposition";
		          String headerValue = String.format("attachment; filename=\"%s\"", flname);
		          response.setHeader(headerKey, headerValue);
		          Object o = p.getContent();
		          InputStream input = new ByteArrayInputStream(ret_cnt1.getBytes());
		         
		          OutputStream output = response.getOutputStream();
		          byte[] buffer = new byte[4096];

		          int byteRead;

		          while ((byteRead = input.read(buffer)) != -1) {
		             output.write(buffer, 0, byteRead);
		          }
		          input.close();
		          output.close();
		          dnstate=false;
		          }
	             
	    	  }
	    	  
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		         if(nm!=null && nm.length()>0)
		         {
	    	  if(nm.equalsIgnoreCase(flname))
	          {
	        	 // HttpServletResponse response=null;
	        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
	          String headerKey = "Content-Disposition";
	          String headerValue = String.format("attachment; filename=\"%s\"", flname);
	          response.setHeader(headerKey, headerValue);
	          Object o = p.getContent();
	          InputStream input = null;
	          try
	          {
	          input=new ByteArrayInputStream(p.getContent().toString().getBytes());
	          }
	          catch(Exception e)
	          {
	        	  input=new ByteArrayInputStream((IOUtils.toString(p.getInputStream())).getBytes());
	          }
	         
	          OutputStream output = response.getOutputStream();
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          dnstate=false;
	          }
		         }
	    	  }
	    	  catch(Exception e){}
		      } 
	      else if(p.isMimeType("text/calendar"))
	      {
	    	  
	            
	            if(("invite.ics").equalsIgnoreCase(flname))
		          {
		        	 // HttpServletResponse response=null;
		        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
		          String headerKey = "Content-Disposition";
		          String headerValue = String.format("attachment; filename=\"%s\"", flname);
		          response.setHeader(headerKey, headerValue);
		          Object o = p.getContent();
		          InputStream input = null;
		        	try
		        	{
		        		input=new ByteArrayInputStream(IOUtils.toByteArray((InputStream)p.getContent()));
		        	}
		        	catch(Exception e)
		        	{
		        		input=new ByteArrayInputStream(p.getContent().toString().getBytes());
		        	}
		         
		          OutputStream output = response.getOutputStream();
		          byte[] buffer = new byte[4096];

		          int byteRead;

		          while ((byteRead = input.read(buffer)) != -1) {
		             output.write(buffer, 0, byteRead);
		          }
		          input.close();
		          output.close();
		          dnstate=false;
		          }
	      }
	      else if (p.isMimeType("text/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		         if(nm!=null && nm.length()>0)
		         {
	    	  if(nm.equalsIgnoreCase(flname))
	          {
	        	 // HttpServletResponse response=null;
	        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
	          String headerKey = "Content-Disposition";
	          String headerValue = String.format("attachment; filename=\"%s\"", flname);
	          response.setHeader(headerKey, headerValue);
	          Object o = p.getContent();
	          InputStream input = new ByteArrayInputStream(p.getContent().toString().getBytes());
	         
	          OutputStream output = response.getOutputStream();
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          dnstate=false;
	          }
		         }
	    	  }
	    	  catch(Exception e){}
	      }
	      
	      
	      else if (p.isMimeType("video/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		         if(nm!=null && nm.length()>0)
		         {
	    	  if(nm.equalsIgnoreCase(flname))
	          {
	        	 // HttpServletResponse response=null;
	        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
	          String headerKey = "Content-Disposition";
	          String headerValue = String.format("attachment; filename=\"%s\"", flname);
	          response.setHeader(headerKey, headerValue);
	          Object o = p.getContent();
	          InputStream input = (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	         
	          OutputStream output = response.getOutputStream();
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          dnstate=false;
	          }
		         }
	    	  }
	    	  catch(Exception e){}
	      }
	      
	      
	      else if (p.isMimeType("audio/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		         if(nm!=null && nm.length()>0)
		         {
	    	  if(nm.equalsIgnoreCase(flname))
	          {
	        	 // HttpServletResponse response=null;
	        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
	          String headerKey = "Content-Disposition";
	          String headerValue = String.format("attachment; filename=\"%s\"", flname);
	          response.setHeader(headerKey, headerValue);
	          Object o = p.getContent();
	          InputStream input =(InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	         
	          OutputStream output = response.getOutputStream();
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          dnstate=false;
	          }
		         }
	    	  }
	    	  catch(Exception e){}
	      }
	      
	   
	      //check if the content has attachment
	      else if (p.isMimeType("multipart/*")) {
	        // System.out.println("This is a Multipart");
	        // System.out.println("---------------------------");
	    	  
	    	  if(ret_Att1)
	    	  {
	    	  Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 if(h.getName().equalsIgnoreCase("Subject"))
	    		 {
	    			 ret_sub1=h.getValue();
	    		
	    				 if(ret_sub1!=null)
	    					 ret_sub1=TextDecode.decodeUTF8Text(ret_sub1);
	    				 else
	    					 ret_sub1="";
	    				
	    		 }
	    		 else  if(h.getName().equalsIgnoreCase("Content-Type"))
	    		 {
	    			 String val=h.getValue();
	    			 String arr[]=val.split("\"");
	    			 if(arr.length>1)
	    			 {
	    				 ret_bou1=arr[1];
	    			 }
	    		 }
	    		 if(ret_cnt1.length()==0)
	    		 {
	    			 ret_cnt1=h.getName()+": "+ h.getValue();
	    		 }
	    		 else
	    		 {
	    			 ret_cnt1+="\n"+h.getName()+": "+ h.getValue();
	    		 }
	    		// System.out.println(h.getName()+": "+ h.getValue());
	    		 }
	    		 ret_cnt1+="\n\n--"+ret_bou1;
	    	  }
	    	 
	    	  
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	            writePart(mp.getBodyPart(i),response);
	      } 
	      //check if the content is a nested message
	      else if (p.isMimeType("message/rfc822")) {
	        // System.out.println("This is a Nested Message");
	        // System.out.println("---------------------------");
	    	  ret_Att1=true;
	         writePart((Part) p.getContent(),response);
	      } 
	      else if (p.isMimeType("message/delivery-status")) {
	    	  
	    	  String nm="details.txt";
	           
	            //is = (InputStream) o;
	            if(nm.equalsIgnoreCase(flname))
		          {
		        	 // HttpServletResponse response=null;
		        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
		          String headerKey = "Content-Disposition";
		          String headerValue = String.format("attachment; filename=\"%s\"", nm);
		          response.setHeader(headerKey, headerValue);
		          Object o = p.getContent();
		          InputStream input = (InputStream) o;
		          //InputStream input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
		          //String destFilePath = "/" +  handler.getName();
		          //OutputStream output = response.getOutputStream();
		        //  FileOutputStream output = new FileOutputStream(destFilePath);
		          //FileOutputStream output = new FileOutputStream();
		          OutputStream output = response.getOutputStream();
		          byte[] buffer = new byte[4096];

		          int byteRead;

		          while ((byteRead = input.read(buffer)) != -1) {
		             output.write(buffer, 0, byteRead);
		          }
		          input.close();
		          output.close();
		          dnstate=false;
		          }
	      }
	      else if (p.getContentType().contains("image/")) {
	    	  String nm= p.getFileName();
	    	  
	    	  Enumeration headers = p.getAllHeaders();
	    		 String con_id="";
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 if(h.getName().equalsIgnoreCase("Content-ID"))
	    		  {
	    			 con_id= h.getValue();
	    			 break;
	    		  }
	    		 }
	    	  
	    	  if(con_id.equalsIgnoreCase(""))
	    	  {
		        if(nm.equalsIgnoreCase(flname))
	          {
	        	 // HttpServletResponse response=null;
	        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
	          String headerKey = "Content-Disposition";
	          String headerValue = String.format("attachment; filename=\"%s\"", nm);
	          response.setHeader(headerKey, headerValue);
	          InputStream input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          //String destFilePath = "/" +  handler.getName();
	          //OutputStream output = response.getOutputStream();
	        //  FileOutputStream output = new FileOutputStream(destFilePath);
	          //FileOutputStream output = new FileOutputStream();
	          OutputStream output = response.getOutputStream();
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          dnstate=false;
	          }
	    	  }
	      } 
	      
	      else if (p.isMimeType("application/*")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	        
	        if(nm.equalsIgnoreCase(flname))
          {
        	 // HttpServletResponse response=null;
        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
          String headerKey = "Content-Disposition";
          String headerValue = String.format("attachment; filename=\"%s\"", nm);
          response.setHeader(headerKey, headerValue);
          InputStream input=null;
          if(nm.endsWith(".ics") || nm.endsWith(".ICS") || p.getContentType().startsWith("application/octet-stream"))
        	  input =  (InputStream)p.getContent();
          else        	 
          {
        	try{
        	  input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
        	}
        	catch(Exception e)
        	{
        		 input =  (InputStream)p.getContent();
        	}
        	  
          }
        
          OutputStream output = response.getOutputStream();
          byte[] buffer = new byte[4096];

          int byteRead;

          while ((byteRead = input.read(buffer)) != -1) {
             output.write(buffer, 0, byteRead);
          }
          input.close();
          output.close();
          dnstate=false;
          }
	       } 
	      else if (p.isMimeType("binary/octet-stream")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	        
	        if(nm.equalsIgnoreCase(flname))
          {
        	 // HttpServletResponse response=null;
        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
          String headerKey = "Content-Disposition";
          String headerValue = String.format("attachment; filename=\"%s\"", nm);
          response.setHeader(headerKey, headerValue);
          InputStream input=null;
        
        	try{
        	  input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
        	}
        	catch(Exception e)
        	{
        		 input =  (InputStream)p.getContent();
        	}
        	  
          
        
          OutputStream output = response.getOutputStream();
          byte[] buffer = new byte[4096];

          int byteRead;

          while ((byteRead = input.read(buffer)) != -1) {
             output.write(buffer, 0, byteRead);
          }
          input.close();
          output.close();
          dnstate=false;
          }
	       }
	      else if (p.getContentType().startsWith("binary/octet-stream")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	        
	        if(nm.equalsIgnoreCase(flname))
          {
        	 // HttpServletResponse response=null;
        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
          String headerKey = "Content-Disposition";
          String headerValue = String.format("attachment; filename=\"%s\"", nm);
          response.setHeader(headerKey, headerValue);
          InputStream input=null;
        
        	try{
        	  input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
        	}
        	catch(Exception e)
        	{
        		 input =  (InputStream)p.getContent();
        	}
        	  
          
        
          OutputStream output = response.getOutputStream();
          byte[] buffer = new byte[4096];

          int byteRead;

          while ((byteRead = input.read(buffer)) != -1) {
             output.write(buffer, 0, byteRead);
          }
          input.close();
          output.close();
          dnstate=false;
          }
	       }
	      else {
	         Object o = p.getContent();
	        
	   }

	}
	
	
	
	
	
	
	
	
	public  void writePartSave(Part p) throws Exception {
	      if (p instanceof Message)
	         //Call methos writeEnvelope
	    //     writeEnvelope((Message) p);

	     // System.out.println("----------------------------");
	    	  p.getContentType();
	     // System.out.println("CONTENT-TYPE: " + p.getContentType());

	      //check if the content is plain text
	      if (p.isMimeType("text/plain")) {
	    	  if(ret_Att1)
	    	  {
	    	  Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 //System.out.println(h.getName()+": "+ h.getValue());
	    		 ret_cnt1+="\n"+h.getName()+": "+ h.getValue();
	    		 }
	    		 try
	    		 {
	    		 ret_cnt1+="\n"+p.getContent().toString();
	    		 }
	    		 catch(Exception e)
	    		 {
	    			 ret_cnt1+="\n"+IOUtils.toString(p.getInputStream()); 
	    		 }
	    		 ret_cnt1+="\n\n--"+ret_bou1;
	    	  }
	    	  
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		         if(nm!=null && nm.length()>0)
		         {
	    	  if(nm.equalsIgnoreCase(flname))
	          {
	        	
	          Object o = p.getContent();
	          InputStream input = null;
	          try
	          {
	          input=new ByteArrayInputStream(p.getContent().toString().getBytes());
	          }
	          catch(Exception e)
	          {
	        	  input=new ByteArrayInputStream((IOUtils.toString(p.getInputStream())).getBytes());  
	          }
	          insSave=input;
	          savest=true;
	          input.close();
	          }
		         }
	    	  }
	    	  catch(Exception e){}
	    	  
	      } 
	      else if (p.isMimeType("text/html")) {
	    	  if(ret_Att1)
	    	  {
	    	  Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		// System.out.println(h.getName()+": "+ h.getValue());
	    		 if(ret_cnt1==null || ret_cnt1.length()<=0)
	    		 {
	    			 ret_cnt1=h.getName()+": "+ h.getValue();
	    		 }
	    		 else
	    		 {
	    			 ret_cnt1+="\n"+h.getName()+": "+ h.getValue();
	    		 }
	    		 
	    		 }
	    		 try
	    		 {
	    		 ret_cnt1+="\n\n\n"+p.getContent().toString();
	    		 }
	    		 catch(Exception e)
	    		 {
	    			 ret_cnt1+="\n\n\n"+IOUtils.toString(p.getInputStream()); 
	    		 }
	    		 ret_cnt1+="\n\n--"+ret_bou1+"--";
	    		 
	    			
	    		 if(ret_sub1==null || ret_sub1.length()<=0)
	    			 ret_sub1="Message";
	    		 ret_sub1=ret_sub1.replaceAll("[^\\w\\s]","");	
	           
	             if((ret_sub1+".eml").equalsIgnoreCase(flname))
		          {
		        	 // HttpServletResponse response=null;
		        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
		        
		          Object o = p.getContent();
		          InputStream input = new ByteArrayInputStream(ret_cnt1.getBytes());
		         
		          insSave=input;
		          savest=true;
		          input.close();
		          }
	             
	    	  }
	    	  
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		         if(nm!=null && nm.length()>0)
		         {
	    	  if(nm.equalsIgnoreCase(flname))
	          {
	        
	         // Object o = p.getContent();
	          InputStream input = null;
	          try
	          {
	          input=new ByteArrayInputStream(p.getContent().toString().getBytes());
	          }
	          catch(Exception e)
	          {
	        	  input=new ByteArrayInputStream((IOUtils.toString(p.getInputStream())).getBytes()); 
	          }
	          insSave=input;
	          savest=true;
	          input.close();
	          }
		         }
	    	  }
	    	  catch(Exception e){}
		      } 
	      else if(p.isMimeType("text/calendar"))
	      {
	    	  
	            
	            if(("invite.ics").equalsIgnoreCase(flname))
		          {
		        	 // HttpServletResponse response=null;
		        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
		         
		          Object o = p.getContent();
		          InputStream input = new ByteArrayInputStream(IOUtils.toByteArray((InputStream)p.getContent()));
		          insSave=input;
		          savest=true;
		          input.close();
		          }
	      }
	      else if (p.isMimeType("text/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		         if(nm!=null && nm.length()>0)
		         {
	    	  if(nm.equalsIgnoreCase(flname))
	          {
	        
	          Object o = p.getContent();
	          InputStream input = new ByteArrayInputStream(p.getContent().toString().getBytes());
	          insSave=input;
	          savest=true;
	          input.close();
	          }
		         }
	    	  }
	    	  catch(Exception e){}
	      }
	      
	      else if (p.isMimeType("audio/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		         if(nm!=null && nm.length()>0)
		         {
	    	  if(nm.equalsIgnoreCase(flname))
	          {
	        
	          Object o = p.getContent();
	          InputStream input =(InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          insSave=input;
	          savest=true;
	          input.close();
	          }
		         }
	    	  }
	    	  catch(Exception e){}
	      }
	      
	      else if (p.isMimeType("video/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		         if(nm!=null && nm.length()>0)
		         {
	    	  if(nm.equalsIgnoreCase(flname))
	          {
	        
	          Object o = p.getContent();
	          InputStream input =(InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          insSave=input;
	          savest=true;
	          input.close();
	          }
		         }
	    	  }
	    	  catch(Exception e){}
	      }
	     
	      //check if the content has attachment
	      else if (p.isMimeType("multipart/*")) {
	        // System.out.println("This is a Multipart");
	        // System.out.println("---------------------------");
	    	  
	    	  if(ret_Att1)
	    	  {
	    	  Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 if(h.getName().equalsIgnoreCase("Subject"))
	    		 {
	    			 ret_sub1=h.getValue();
	    
	    				 if(ret_sub1!=null)
	    					 ret_sub1=TextDecode.decodeUTF8Text(ret_sub1);
	    				 else
	    					 ret_sub1="";
	    				
	    		 }
	    		 else  if(h.getName().equalsIgnoreCase("Content-Type"))
	    		 {
	    			 String val=h.getValue();
	    			 String arr[]=val.split("\"");
	    			 if(arr.length>1)
	    			 {
	    				 ret_bou1=arr[1];
	    			 }
	    		 }
	    		 if(ret_cnt1.length()==0)
	    		 {
	    			 ret_cnt1=h.getName()+": "+ h.getValue();
	    		 }
	    		 else
	    		 {
	    			 ret_cnt1+="\n"+h.getName()+": "+ h.getValue();
	    		 }
	    		// System.out.println(h.getName()+": "+ h.getValue());
	    		 }
	    		 ret_cnt1+="\n\n--"+ret_bou1;
	    	  }
	    	 
	    	  
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	            writePartSave(mp.getBodyPart(i));
	      } 
	      //check if the content is a nested message
	      else if (p.isMimeType("message/rfc822")) {
	        // System.out.println("This is a Nested Message");
	        // System.out.println("---------------------------");
	    	  ret_Att1=true;
	         writePartSave((Part) p.getContent());
	      } 
	      else if (p.isMimeType("message/delivery-status")) {
	    	  
	    	  String nm="details.txt";
	           
	            //is = (InputStream) o;
	            if(nm.equalsIgnoreCase(flname))
		          {
		        	 // HttpServletResponse response=null;
		        	//  MockHttpServletResponse response = new MockHttpServletResponse(); 
		        
		          Object o = p.getContent();
		          InputStream input = (InputStream) o;
		          insSave=input;
		          savest=true;
		          input.close();
		          }
	      }
	      else if (p.getContentType().contains("image/")) {
	    	  String nm= p.getFileName();
	    	  
	    	  Enumeration headers = p.getAllHeaders();
	    		 String con_id="";
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 if(h.getName().equalsIgnoreCase("Content-ID"))
	    		  {
	    			 con_id= h.getValue();
	    			 break;
	    		  }
	    		 }
	    	  
	    	  if(con_id.equalsIgnoreCase(""))
	    	  {
		        if(nm.equalsIgnoreCase(flname))
	          {
	        	
	          InputStream input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          insSave=input;
	          savest=true;
	          input.close();
	          }
	    	  }
	      } 
	      
	      else if (p.isMimeType("application/*")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	        
	        if(nm.equalsIgnoreCase(flname))
        {
      	
        InputStream input=null;
        if(nm.endsWith(".ics") || nm.endsWith(".ICS") || p.getContentType().startsWith("application/octet-stream"))
      	  input =  (InputStream)p.getContent();
        else 
        {
      	 try
      	 {
        	input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
      	 }
      	 catch(Exception e)
      	 {
      		input =  (InputStream)p.getContent();
      	 }
        }
        insSave=input;
        savest=true;
        input.close();
        }
	       } 
	      
	      else if (p.isMimeType("binary/octet-stream")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	        
	        if(nm.equalsIgnoreCase(flname))
        {
      	
        InputStream input=null;
      
      	 try
      	 {
        	input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
      	 }
      	 catch(Exception e)
      	 {
      		input =  (InputStream)p.getContent();
      	 }
       
        insSave=input;
        savest=true;
        input.close();
        }
	       }
	      
	      else if (p.getContentType().startsWith("binary/octet-stream")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	        
	        if(nm.equalsIgnoreCase(flname))
        {
      	
        InputStream input=null;
      
      	 try
      	 {
        	input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
      	 }
      	 catch(Exception e)
      	 {
      		input =  (InputStream)p.getContent();
      	 }
       
        insSave=input;
        savest=true;
        input.close();
        }
	       }
	      
	      else {
	         Object o = p.getContent();
	        
	   }

	}
	
	
}
