package com.local;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.activation.MimetypesFileTypeMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;

import java.io.*;

import com.example.Connections;
import com.sun.mail.imap.AppendUID;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.protocol.FLAGS;

import webmail.wsdl.GetCalendarMailResponse;
import webmail.wsdlnew.GetComposeMailResponse;
import webmail.wsdlnew.GetSaveMailDraftResponse;
import webmail.wsdlnew.GetWebmailDeleteDraftResponse;


public class WebmailCompose {

	public GetComposeMailResponse sendComposeMail(String mailReplyTo,String oldmsgid, String ref, String ip, String xmailer, String draftuid, String host, String port,String imapport, boolean saveSent, String id, String pass,String fromname, String to, String cc, String bcc, String sub ,String cnt, boolean hasattach, List<String> atchfile, List<String> atchnewfile, String readrec, boolean dsn_status, String pri, String texttype, String filePath)
	{
		
		GetComposeMailResponse response=new GetComposeMailResponse();
		String status="true";
		
		
		final String username = id;//change accordingly
		final String password = pass;//change accordingly
		
		dsn_status=false;
		Session ses =Connections.smtpConnectionNP(host, port, id, pass,dsn_status);
		try {
			
			try {
				fromname=MimeUtility.encodeText(fromname, "utf-8", "B");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    Message message = new MimeMessage(ses);
		    try
		    {
		    if(mailReplyTo!=null && mailReplyTo.length()>0)
		    	message.setReplyTo(InternetAddress.parse(mailReplyTo));
		    }
		    catch(Exception ex)
		    {
		    	
		    }
		   try
		   {
		    message.setFrom(new InternetAddress(username, fromname));
		   }
		   catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
			   message.setFrom(new InternetAddress(username));
				e.printStackTrace();
			}
		    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		    if(cc!=null && !(cc.equals("")))
		    {
		    	 message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));	
		    }
		    if(bcc!=null && !(bcc.equals("")))
		    {
		    	 message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(bcc));	
		    }
		    
		   /* Return-Receipt-To: Nirbhay Pratap Singh <nirbhay@silvereye.in>
		    Disposition-Notification-To: Nirbhay Pratap Singh <nirbhay@silvereye.in>*/
		    if(pri!=null && !pri.equalsIgnoreCase(""))
		    {
		    	message.setHeader("X-Priority", pri);
		    }
		    
		    if(readrec!=null && !readrec.equalsIgnoreCase(""))
		    {
		    if(readrec.equalsIgnoreCase("1"))
		    {
		    message.setHeader("Return-Receipt-To", id);
		    message.setHeader("Disposition-Notification-To", id);
		    }
		    }
		    message.setHeader("X-Originating-IP", ip);
		    message.setHeader("X-Mailer", xmailer);
		    
		    if(oldmsgid!=null && !oldmsgid.equals(""))
		    {
		    	 message.setHeader("In-Reply-To", oldmsgid);
		    }
		    
		    ref+=oldmsgid;
		    if(ref!=null && !ref.equals(""))
		    {
		    	 message.setHeader("References", ref);
		    }
		    
		    Date dt=new Date();
		    SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
			String     DateToStr = format.format(dt);
			message.setHeader("Date", DateToStr);
			message.setSubject(sub);
			if(!hasattach)
			{
				if(texttype!=null && texttype.equalsIgnoreCase("plain"))
				{
					message.setContent(cnt,"text/plain");
				}
				else
				{
					message.setContent(cnt,"text/html; charset=UTF-8");
				}
			}
			else
			{
			  BodyPart messageBodyPart = new MimeBodyPart();
  
		      BodyPart messageBodyPart1 = new MimeBodyPart();  
		        // messageBodyPart1.setText(cnt);  
		         messageBodyPart1.setContent(cnt,"text/html; charset=UTF-8");
		         Multipart multipart = new MimeMultipart();  
		         multipart.addBodyPart(messageBodyPart1);
		        
		        for(int i=0; i< atchfile.size(); i++ )
		        {
		        	
		         MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		         
		       //  String filename = "/tmp/tomcat7-tomcat7-tmp/"+atchnewfile.get(i);
		    //     String filename = "C:\\Users\\nirbhay\\AppData\\Local\\Temp\\"+atchnewfile.get(i);
		         
		         String filename = filePath+atchnewfile.get(i);
		         DataSource source = new FileDataSource(filename);
		         messageBodyPart2.setDataHandler(new DataHandler(source));  
		         messageBodyPart2.setFileName(atchfile.get(i)); 
		         
		         multipart.addBodyPart(messageBodyPart2);
		        
		        
		        }
		        
		         message.setContent(multipart , "multipart/alternative");  
			} 
		         
			
		    Transport.send(message);
			//Transport.send(message, message.getAllRecipients());
		    
		    System.out.println("Message sent...");
		    
		    

		} catch (MessagingException e) {
			String exp=e.toString();
			exp=exp.replace("nested exception is:", "");
			String arr[]=exp.split(";");
			String err=arr[arr.length-1];
			err=err.replace("<", "&lt;");
			err=err.replace(">", "&gt;");
			status=err;
			
		    System.out.println(e);
		}
		
		
		
		
		response.setSetComposeStatus(status);
  return  response;        
}
	
	
	
	public GetComposeMailResponse sendComposeMailAfter(String mailReplyTo,String oldmsgid, String ref, String ip, String xmailer, String draftuid, String host, String port,String imapport, boolean saveSent, String id, String pass,String fromname, String to, String cc, String bcc, String sub ,String cnt, boolean hasattach, List<String> atchfile, List<String> atchnewfile, String readrec, boolean dsn_status, String pri, String texttype, String filePath)
	{
		
		GetComposeMailResponse response=new GetComposeMailResponse();
		String status="true";
		 Store store = null;
		
		final String username = id;//change accordingly
		final String password = pass;//change accordingly
		
		dsn_status=false;
		Session ses =Connections.smtpConnectionNP(host, port, id, pass,dsn_status);
		try {
			
			try {
				fromname=MimeUtility.encodeText(fromname, "utf-8", "B");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    Message message = new MimeMessage(ses);
		    try
		    {
		    if(mailReplyTo!=null && mailReplyTo.length()>0)
		    	message.setReplyTo(InternetAddress.parse(mailReplyTo));
		    }
		    catch(Exception ex)
		    {
		    	
		    }
		   try
		   {
		    message.setFrom(new InternetAddress(username, fromname));
		   }
		   catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
			   message.setFrom(new InternetAddress(username));
				e.printStackTrace();
			}
		    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		    if(cc!=null && !(cc.equals("")))
		    {
		    	 message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));	
		    }
		    if(bcc!=null && !(bcc.equals("")))
		    {
		    	 message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(bcc));	
		    }
		    
		   /* Return-Receipt-To: Nirbhay Pratap Singh <nirbhay@silvereye.in>
		    Disposition-Notification-To: Nirbhay Pratap Singh <nirbhay@silvereye.in>*/
		    if(pri!=null && !pri.equalsIgnoreCase(""))
		    {
		    	message.setHeader("X-Priority", pri);
		    }
		    
		    if(readrec!=null && !readrec.equalsIgnoreCase(""))
		    {
		    if(readrec.equalsIgnoreCase("1"))
		    {
		    message.setHeader("Return-Receipt-To", id);
		    message.setHeader("Disposition-Notification-To", id);
		    }
		    }
		    message.setHeader("X-Originating-IP", ip);
		    message.setHeader("X-Mailer", xmailer);
		    
		    if(oldmsgid!=null && !oldmsgid.equals(""))
		    {
		    	 message.setHeader("In-Reply-To", oldmsgid);
		    }
		    
		    ref+=oldmsgid;
		    if(ref!=null && !ref.equals(""))
		    {
		    	 message.setHeader("References", ref);
		    }
		    
		    Date dt=new Date();
		    SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
			String     DateToStr = format.format(dt);
			message.setHeader("Date", DateToStr);
			message.setSubject(sub);
			if(!hasattach)
			{
				if(texttype!=null && texttype.equalsIgnoreCase("plain"))
				{
					message.setContent(cnt,"text/plain");
				}
				else
				{
					message.setContent(cnt,"text/html; charset=UTF-8");
				}
			}
			else
			{
			  BodyPart messageBodyPart = new MimeBodyPart();
  
		      BodyPart messageBodyPart1 = new MimeBodyPart();  
		        // messageBodyPart1.setText(cnt);  
		         messageBodyPart1.setContent(cnt,"text/html; charset=UTF-8");
		         Multipart multipart = new MimeMultipart();  
		         multipart.addBodyPart(messageBodyPart1);
		        
		        for(int i=0; i< atchfile.size(); i++ )
		        {
		        	
		         MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		         
		       //  String filename = "/tmp/tomcat7-tomcat7-tmp/"+atchnewfile.get(i);
		    //     String filename = "C:\\Users\\nirbhay\\AppData\\Local\\Temp\\"+atchnewfile.get(i);
		         
		         String filename = filePath+atchnewfile.get(i);
		         DataSource source = new FileDataSource(filename);
		         messageBodyPart2.setDataHandler(new DataHandler(source));  
		         messageBodyPart2.setFileName(atchfile.get(i)); 
		         
		         multipart.addBodyPart(messageBodyPart2);
		        
		        
		        }
		        
		         message.setContent(multipart , "multipart/alternative");  
			} 
		         
			
		   // Transport.send(message);
		    
		   // System.out.println("Message sent...");
		    
		    
		    
		   
		   
		    
			store=Connections.imapConnectionNP(host, imapport, id, pass);
		   		    
		    if(saveSent==true)
		    {
	        
		 
        
	        Folder folder = (Folder) store.getFolder("Sent");
	        if (!folder.exists()) {
	            folder.create(Folder.HOLDS_MESSAGES);
	        }
	        folder.open(Folder.READ_WRITE);
	        System.out.println("appending...");
	        // folder.appendMessages(new Message[]{message});
	        try {
	        	message.setFlag(FLAGS.Flag.SEEN, true);
	            folder.appendMessages(new Message[]{message});
	           // Message[] msgs = folder.getMessages();
	          

	        } catch (Exception ignore) {
	            System.out.println("error processing message " + ignore.getMessage());
	        } finally {
	        	folder.close(true);
	          
	           // folder.close(false);
	            
	        }
		    }
		   // store.close();
		   // System.out.println("Done");

		
		
			 if(draftuid!=null && !(draftuid.equals("")))
			    {
				
				 IMAPFolder ifldr=null;
			     try {	
				
				 
					
					//store=Connections.imapConnectionNP(host, imapport, id, pass);
						Folder folder = (Folder) store.getFolder("Drafts");
				         ifldr=(IMAPFolder)folder;
				        if (!folder.exists()) {
				            folder.create(Folder.HOLDS_MESSAGES);
				        }
				        folder.open(Folder.READ_WRITE);
				       
				   
				        	
				        	String uidarr[]=draftuid.split("-");
				    		long arr[]=new long[uidarr.length];
				    		for(int i=0;i< arr.length;i++)
				    		{
				    			arr[i]=Long.parseLong(uidarr[i].trim());
				    		}
				        	
				        	
				        	 Message[] msg =ifldr.getMessagesByUID(arr);
				        	 if(msg!=null && msg.length>0)
				        	 {
				        		 for(int di=0;di< msg.length; di++)
				        		 {
				        		 msg[di].setFlag(FLAGS.Flag.DELETED, true);
				        		 }
				        	 }
				         } catch (Exception ignore) {
				        	ignore.printStackTrace();
				            System.out.println("error processing message " + ignore.getMessage());
				        } finally {
				        	//folder.close(true);
				        	try {
								ifldr.close(true);
								
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	//folder.close(true);
				           			            
				        }

			    }
			 
			 
			 if(hasattach)
				{
					 for(int i=0; i< atchfile.size(); i++ )
				        {
						 
						// String filename = "/tmp/tomcat7-tomcat7-tmp/"+atchnewfile.get(i);
						// String filename = "C:\\Users\\nirbhay\\AppData\\Local\\Temp\\"+atchnewfile.get(i);
						 String filename = filePath+atchnewfile.get(i);
						 File fl=new File(filename);
				         fl.delete();
				        }
				}
			 
			 

		} catch (MessagingException e) {
			String exp=e.toString();
			exp=exp.replace("nested exception is:", "");
			String arr[]=exp.split(";");
			String err=arr[arr.length-1];
			err=err.replace("<", "&lt;");
			err=err.replace(">", "&gt;");
			status=err;
			
		    System.out.println(e);
		}
		finally
		{
			if(store!=null)
			{
				try {
					store.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		response.setSetComposeStatus(status);
  return  response;        
}
	
	
	
	
	
	
	public GetSaveMailDraftResponse saveMailInDraft(String ip,String xmailer, String host, String port,String imapport, String uid, String id, String pass,String fromname, String to, String cc, String bcc, String sub ,String cnt, boolean hasattach, List<String> atchfile, List<String> atchnewfile, String texttype, String filePath)
	{
		
		String sendUid="";
		GetSaveMailDraftResponse response =new GetSaveMailDraftResponse();
		final String username = id;//change accordingly
		final String password = pass;//change accordingly
		boolean dsnst=false;
		Session ses =Connections.smtpConnectionNP(host, port, id, pass,dsnst);

		try {
			
			try {
				fromname=MimeUtility.encodeText(fromname, "utf-8", "B");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    Message message = new MimeMessage(ses);
		   try
		   {
		    message.setFrom(new InternetAddress(username, fromname));
		   }
		   catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
			   message.setFrom(new InternetAddress(username));
				e.printStackTrace();
			}
		    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		    if(cc!=null && !(cc.equals("")))
		    {
		    	 message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));	
		    }
		    if(bcc!=null && !(bcc.equals("")))
		    {
		    	 message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(bcc));	
		    }
		    
		 
		    message.setHeader("X-Mailer", xmailer);
		    message.setHeader("X-Originating-IP", ip);
		    
		    Date dt=new Date();
		    SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
			String     DateToStr = format.format(dt);
			message.setHeader("Date", DateToStr);
			message.setSubject(sub);
			
			//message.setContent(cnt,"text/html" );
			
			if(!hasattach)
			{
				if(texttype!=null && texttype.equalsIgnoreCase("plain"))
				{
					message.setContent(cnt,"text/plain");
				}
				else
				{
					message.setContent(cnt,"text/html; charset=UTF-8");
				}
			}
			else
			{
			  BodyPart messageBodyPart = new MimeBodyPart();
  
		      BodyPart messageBodyPart1 = new MimeBodyPart();  
		        // messageBodyPart1.setText(cnt);  
		         messageBodyPart1.setContent(cnt,"text/html; charset=UTF-8");
		         Multipart multipart = new MimeMultipart();  
		         multipart.addBodyPart(messageBodyPart1);
		        
		        for(int i=0; i< atchfile.size(); i++ )
		        {
		        
		         MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		      //   String filename = "/tmp/tomcat7-tomcat7-tmp/"+atchnewfile.get(i);
		       // String filename = "C:\\Users\\nirbhay\\AppData\\Local\\Temp\\"+atchnewfile.get(i);
		         String filename = filePath+atchnewfile.get(i);
		         DataSource source = new FileDataSource(filename);
		         messageBodyPart2.setDataHandler(new DataHandler(source));  
		         messageBodyPart2.setFileName(atchnewfile.get(i));  
		         multipart.addBodyPart(messageBodyPart2);
		       
		        
		        }
		        
		         message.setContent(multipart , "multipart/alternative");  
			} 
			
		         
			/*
		    Transport.send(message);
		    
		    System.out.println("Message sent...");*/
		   
	        // Copy message to "Sent Items" folder as read
		    Store store = null;
		  
		    
			store=Connections.imapConnectionNP(host, imapport, id, pass);
        
	        Folder folder = (Folder) store.getFolder("Drafts");
	       // UIDFolder uf = (UIDFolder)folder;
	        IMAPFolder ifldr=(IMAPFolder)folder;
	        if (!folder.exists()) {
	            folder.create(Folder.HOLDS_MESSAGES);
	        }
	        folder.open(Folder.READ_WRITE);
	        
	        //System.out.println("appending...");
	        // folder.appendMessages(new Message[]{message});
	        try {
	        	        	
	        	if(uid!=null && !(uid.equals("")))
	        	{
	        	long [] arr={Long.parseLong(uid.trim())};
	        	 Message[] msg =ifldr.getMessagesByUID(arr);
	        	 if(msg!=null && msg.length>0)
	        	 {
	        		 msg[0].setFlag(FLAGS.Flag.DELETED, true); 
	        	 }
	        	
	        	}
	        	 
	        	message.setFlag(FLAGS.Flag.SEEN, true);
	        	message.setFlag(FLAGS.Flag.DRAFT, true);
	        	AppendUID appuid[]= ifldr.appendUIDMessages((new Message[]{message}));
	        	if(appuid!=null && appuid.length>0)
	        	{
	        	sendUid =""+appuid[0].uid;
	        	}
	        	// System.out.println("uid="+sendUid);
	           // folder.appendMessages(new Message[]{message});
	           // Message[] msg =folder.getMessages();
	           // sendUid =""+ifldr.getUID(msg[msg.length-1]);
	           // Message[] msgs = folder.getMessages();
	           // sendUid =""+ uf.getUID(message);
	           //

	        } catch (MessagingException ignore) {
	        	//ignore.printStackTrace();
	            System.out.println("error processing message " + ignore.getMessage());
	        } finally {
	        	//folder.close(true);
	        	ifldr.close(true);
	        	//folder.close(true);
	            store.close();
	           // 
	            
	        }
		   
		  
		    System.out.println("Done");

		} catch (MessagingException e) {
			
		    System.out.println(e);
		}
		
		
		
		response.setSetMailID(sendUid);
		
  return  response;        
}
	
	public GetWebmailDeleteDraftResponse deleteDraft(String host, String port, String id, String pass,String uids)
	{
boolean status=true;
GetWebmailDeleteDraftResponse response=new GetWebmailDeleteDraftResponse();
		String arr[]=uids.split("-");
		long uidarr[]=new long[arr.length];
		for(int i=0;i< arr.length;i++)
		{
			uidarr[i]=Long.parseLong(arr[i].trim());
		}
		
		IMAPFolder folder = null;
        Store store = null;
        Flag flag = null;
        IMAPFolder dfolder=null;
        try 
        {
        	
			
			store=Connections.imapConnectionNP(host, port, id, pass);
			//IMAPStore imapStore = (IMAPStore) store; 
         folder = (IMAPFolder) store.getFolder("Drafts"); //This works for both email account
		
      UIDFolder uf = (UIDFolder)folder;
      if (!folder.exists()) 
      {
    	  System.exit(0);
      }
      folder.open(Folder.READ_WRITE);
      Message[] msg =uf.getMessagesByUID(uidarr);
     // int umsg= folder.getUnreadMessageCount();

      
      for (int i = msg.length-1;  i>=0; i--)
      {

    	  try
    	  {
    	  msg[i].setFlag(Flags.Flag.DELETED, true);
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
        e.printStackTrace();
        status=false;
        }
        response.setDeleteDraftStatus(status);
  return  response;  
	}
	
	
	
private static SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
	 
	
	public GetCalendarMailResponse sendCalendarMail(String webamilIp, String webamilId, String webamilPassword, String webamilFromName, String webamilHost, String webamilPort, String webamilTo, String webamilSubject, String webamilBodyContent, String webamilCalendarContent, String webmailXMailer)
	{
		GetCalendarMailResponse res = new GetCalendarMailResponse();
		

		
		final String username = webamilId;//change accordingly
		final String password = webamilPassword;//change accordingly
		
		boolean dsnst=false;
		Session ses =Connections.smtpConnectionNP(webamilHost, webamilPort, webamilId, webamilPassword,dsnst);
		
		try {
			
			try {
				webamilFromName=MimeUtility.encodeText(webamilFromName, "utf-8", "B");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    Message message = new MimeMessage(ses);
		   try
		   {
		    message.setFrom(new InternetAddress(username, webamilFromName));
		   }
		   catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
			   message.setFrom(new InternetAddress(username));
				e.printStackTrace();
			}
		    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(webamilTo));
		   
		   
		    
		    
		    message.setHeader("X-Originating-IP", webamilIp);
		    message.setHeader("X-Mailer", webmailXMailer);
		    
		    
		    java.util.Date dtn=new java.util.Date();
		    SimpleDateFormat form = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
			String     DateToStr = form.format(dtn);
			message.setHeader("Date", DateToStr);
			message.setSubject(webamilSubject);
			
			MimetypesFileTypeMap mimetypes = (MimetypesFileTypeMap)MimetypesFileTypeMap.getDefaultFileTypeMap();
		    mimetypes.addMimeTypes("text/calendar ics ICS");
			
		    MailcapCommandMap mailcap = (MailcapCommandMap) MailcapCommandMap.getDefaultCommandMap();
		    mailcap.addMailcap("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			
		    Multipart multipart = new MimeMultipart("alternative");
		    
		    
		    //part 1, html text
		    
		    BodyPart messageBodyPart = new MimeBodyPart();
		    String content = webamilBodyContent;
		    messageBodyPart.setContent(content, "text/html; charset=utf-8");
	 	    multipart.addBodyPart(messageBodyPart);
			
	 	    // Add part two, the calendar
	 	    
	 	    BodyPart calendarPart = new MimeBodyPart();
	 
	        
	        calendarPart.addHeader("Content-Class", "urn:content-classes:calendarmessage");
	        calendarPart.setContent(webamilCalendarContent, "text/calendar; method=REQUEST; charset=\"utf-8\"");
	 	    multipart.addBodyPart(calendarPart);
	 
	 	    message.setContent(multipart);
	 	   
		    Transport.send(message);
		    
		    System.out.println("Message sent...");
		    
		    
			res.setSetMailStatus(true);
		   		    
		    
//		    store.close();
		    System.out.println("Done");

		} catch (MessagingException e)
		{
			e.printStackTrace();
			res.setSetMailStatus(false);
			
		}
		
		return res;
	}
}
