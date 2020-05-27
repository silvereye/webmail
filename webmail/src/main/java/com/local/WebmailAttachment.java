package com.local;

import javax.activation.DataHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;

import java.io.*;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.example.Connections;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import webmail.bean.TextDecode;
import webmail.wsdlnew.GetMailAttachmentResponse;
import webmail.wsdlnew.ReplyDisplay;


public class WebmailAttachment {


	 private boolean textIsHtml = false;
	 String mail_cont="";
	 String mail_cont_otr="";
	 String nfilePath="";
	 int flg=0;
	   int inline=0;
	  
	   boolean ret_Att=false;
	   String ret_cnt="";
	   String ret_sub="";
	   String ret_bou="";
	  
	   List<String> flnm= new ArrayList<String>();
	     List<String> flnewnm= new ArrayList<String>();
	     List<String> flsz= new ArrayList<String>();
	  
	public GetMailAttachmentResponse listWebmailAttachment(String host, String port, String id, String pass, String uid, String foldername, String filePath)
	{
		GetMailAttachmentResponse response=new GetMailAttachmentResponse();
		ReplyDisplay inb= new ReplyDisplay();
		
		
		
		 
		IMAPFolder folder = null;
       Store store = null;
       Flag flag = null;
       mail_cont="";
       flg=0;
       inline=0;
       nfilePath=filePath;
       ret_Att=false;
       ret_cnt="";
	    ret_sub="";
	  ret_bou="";
	  flnm.clear();
	  flnewnm.clear();
	  flsz.clear();
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
	 
	 Enumeration headers = msg[i].getAllHeaders();
	 int hd=0;
	 while (headers.hasMoreElements()) {
	 Header h = (Header) headers.nextElement();
	 /* out.print("<br>%%%%%%% "+h.getName() + ": " + h.getValue());*/
	  if(h.getName().equalsIgnoreCase("Message-ID"))
	  {
		  hd++;
		  inb.setMessageID(h.getValue());
		 
	  }
	  
	  if(h.getName().equalsIgnoreCase("References"))
	  {
		  hd++;
		  inb.setReferences(h.getValue());
		 
	  }
	  if(hd>1)
	  {
		  break; 
	  }
	  }
	 
	 String repTo= InternetAddress.toString( msg[i].getReplyTo());
	 try
	 {
	 if(repTo.contains("?utf-8?") || repTo.contains("?UTF-8?"))
		 repTo=repTo.replaceAll("\"", "");
	 }
  	  catch(Exception ee)
  	  {
  		 
  	  }
	 
	  try
		{
		 repTo=TextDecode.decodeUTF8Text(repTo);
		}
	  catch(Exception ee)
	  {
		  ee.printStackTrace();
	  }
	 if(repTo==null)
	 {
		 repTo="";
	 }
	  inb.setReplyToId(repTo);
	 
	 String from = InternetAddress.toString(msg[i].getFrom());
	 try
	 {
	 if(from.contains("?utf-8?") || from.contains("?UTF-8?"))
		  from=from.replaceAll("\"", "");
	 }
 	  catch(Exception ee)
 	  {
 		 
 	  }
	 
	 
	  try
		{
		 from=TextDecode.decodeUTF8Text(from);
		}
	  catch(Exception ee)
	  {
		  ee.printStackTrace();
	  }
	  inb.setFromId(from);
	 
	  List<String> to_lst= new  ArrayList<String>();
	  Address arr_to[]=msg[i].getRecipients(Message.RecipientType.TO);
	  if(arr_to!=null)
	  {
	  for(int toi=0; toi< arr_to.length; toi++)
	  {
		  if(arr_to[toi].toString()!=null)
		  to_lst.add( HtmlUtils.htmlEscape(TextDecode.decodeUTF8Text(arr_to[toi].toString())));
	  }
	  }
	  inb.getToId().addAll(to_lst);
	  
	  List<String> cc_lst= new  ArrayList<String>();
	  Address arr_cc[]=msg[i].getRecipients(Message.RecipientType.CC);
	  if(arr_cc!=null)
	  {
	  for(int cci=0;cci< arr_cc.length; cci++)
	  {
		  if(arr_cc[cci].toString()!=null)
		  cc_lst.add( HtmlUtils.htmlEscape(TextDecode.decodeUTF8Text(arr_cc[cci].toString())));
	  }
	  }
	  inb.getCCId().addAll(cc_lst);
	 
	  List<String> bcc_lst= new  ArrayList<String>();
	  Address arr_bcc[]=msg[i].getRecipients(Message.RecipientType.BCC);
	  if(arr_bcc!=null)
	  {
	  for(int bcci=0;bcci< arr_bcc.length; bcci++)
	  {
		  if(arr_bcc[bcci].toString()!=null)
		  bcc_lst.add(HtmlUtils.htmlEscape(TextDecode.decodeUTF8Text(arr_bcc[bcci].toString())));
	  }
	  }
	  inb.getBCCId().addAll(bcc_lst);
	  
	 
	  
	  String subject = msg[i].getSubject();
	  inb.setSubject(subject);

	  Date sent = msg[i].getReceivedDate();
	  SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
	  SimpleDateFormat format1 = new SimpleDateFormat("E, MMM dd, yyyy");
	  SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a"); 
	  SimpleDateFormat format3 = new SimpleDateFormat("MMM dd"); 
	  String dip_dt="";
	  Date cdt=new Date();
	  String dt1=format0.format(cdt);
	  String dt2=format0.format(sent);
	  if(dt1.equalsIgnoreCase(dt2))
	  {
		  dip_dt=format2.format(sent);
	  }
	  else
	  {
		  dip_dt=format3.format(sent);
	  }
	  
	  String ttl_dt=format1.format(sent)+" at "+format2.format(sent);
	  
	  inb.setSendDtae(dip_dt);
	  inb.setSendDtaeTitle(ttl_dt);
	 
	 
	  
	 /* String text =msg[i].getContent().toString();
	    Message reply = msg[i].reply(true);
	    String replyText = text.replaceAll("(?m)^", "> ");
	    // allow user to edit replyText,
	    // e.g., using a Swing GUI or a web form
	    reply.setText(replyText);
	  
	    Transport.send(reply);*/
	  
	  
	  
	  
	 

 Object content = msg[i].getContent();  

 String attch="No";
// String mail_cont="";
 
 Message message1 = msg[i];
 //System.out.println("---------------------------------");
 writePart(message1);
 
 
/* if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
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
       	  attch="Yes";
             DataHandler handler = bodyPart.getDataHandler();
             flsz.add(""+bodyPart.getSize());
             InputStream inputStream = bodyPart.getInputStream();
             String filenm=handler.getName();
             flnm.add(filenm);
             String newfilenm=java.util.UUID.randomUUID()+"_"+filenm;
             flnewnm.add(newfilenm);
           
             OutputStream outputStream =  new FileOutputStream(new File(filePath+newfilenm));
             int read = 0;
     		byte[] bytes = new byte[1024];
      
     		while ((read = inputStream.read(bytes)) != -1)
     		{
     			outputStream.write(bytes, 0, read);
     		}
               
          
           }
         else 
         	{
       	
           }
  
 }
   
 }
 }*/
 if( msg[i].getContentType().contains("text/plain;"))
 {
	 if(!mail_cont.startsWith("<pre>")) 
		 mail_cont="<pre>"+mail_cont+"</pre>";  
 }
/* else
 {
	  mail_cont=mail_cont.replaceAll("<pre", "<span")  ;
	  mail_cont=mail_cont.replaceAll("</pre>", "</span>")  ;
 }*/
 if(flnm!=null && flnm.size()>0)
	 attch="Yes";
 inb.setMailContent(mail_cont.trim());
 inb.setInlineimgsize(""+inline);
inb.setAttachStatus(attch);
inb.getAttachmentNameList().addAll(flnm);
inb.getAttachmentNewNameList().addAll(flnewnm);
inb.getAttachmentSizeList().addAll(flsz);
flnm.clear();
flnewnm.clear();
flsz.clear();
 }

 folder.close(true);
 store.close();
 }
 catch(MessagingException e)
       {
	    e.printStackTrace();
       }
 catch(IOException e)
       {
	  e.printStackTrace();
       }    
 catch(Exception e)
       {
	  e.printStackTrace();
       }
       response.setGetAttachByUid(inb);
       nfilePath="";
 return   response;        
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
	    	  
	    	 
	    	  if(flg!=2 && flg!=1)
	    	  {
	        // System.out.println("This is plain text");
	        // System.out.println("---------------------------flg="+flg);
	         //System.out.println((String) p.getContent());
	    		  try
	    		  {
	    			  mail_cont="<pre>" +p.getContent()+"</pre>";
	    		  }
	    		  catch(Exception e)
	    		  {
	    			  mail_cont="<pre>" +IOUtils.toString(p.getInputStream())+"</pre>";
	    		  }
	    	  }
	    	  if(flg==0)
	    	  {
	    		  flg=1;
	    	  }
	    	  System.out.println("content type" + p.getContentType());
	    	  try
	    	  {
		         String nm= p.getFileName();
		         
		         
		         if(nm!=null && nm.length()>0)
		         {
		        int sz=p.getSize();
		        
		   	  try
		   		{
		   		nm=TextDecode.decodeUTF8Text(nm);
		   		}
		   	  catch(Exception ee)
		   	  {
		   		  ee.printStackTrace();
		   	  }
	        	
	          InputStream input =  null;
	          try
	          {
	          input=new ByteArrayInputStream(p.getContent().toString().getBytes());   // (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          }
	          catch(Exception e)
	          {
	        	  input=new ByteArrayInputStream((IOUtils.toString(p.getInputStream())).getBytes()); 
	          }
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(nfilePath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          flnm.add(nm);
	          flnewnm.add(uidnm+"_"+nm);
	         flsz.add(""+sz);
		         }
	    	  }
		         catch (Exception e) {
					e.printStackTrace();
				}
	      } 
	      else if (p.isMimeType("text/html")) {
	    	  
	    	  if(ret_Att)
	    	  {
	    	  Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		// System.out.println(h.getName()+": "+ h.getValue());
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
	    		 ret_cnt+="\n\n--"+ret_bou+"--";
	    		 
	    		 ret_sub=ret_sub.replaceAll("[^\\w\\s]","");
	    		 InputStream input = new ByteArrayInputStream(ret_cnt.getBytes());
	    		 String uidnm=	  UUID.randomUUID().toString();
	    		 File fl=new File(nfilePath+uidnm+"_"+ret_sub+".eml");
		          OutputStream output =  new FileOutputStream(fl);
		          byte[] buffer = new byte[4096];

		          int byteRead;

		          while ((byteRead = input.read(buffer)) != -1) {
		             output.write(buffer, 0, byteRead);
		          }
		          input.close();
		          output.close();
		         
		          flnm.add(ret_sub+".eml");
		          flnewnm.add(uidnm+"_"+ret_sub+".eml");
		         flsz.add(""+fl.length());
	    	  }
	    	  
	    	  if(flg!=2)
	    	  {
		       // System.out.println("This is plain text");
		      //  System.out.println("---------------------------flg="+flg);
		      //   System.out.println((String) p.getContent());
	    		  try
	    		  {
	    			  mail_cont=(String) p.getContent();
	    		  }
	    		  catch(Exception e)
	    		  {
	    			  mail_cont=IOUtils.toString(p.getInputStream());
	    		  }
	    		  
	    	  }
	    	  flg=2;
	    	  try
	    	  {
		         String nm= p.getFileName();
		        
		         if(nm!=null && nm.length()>0)
		         {
		        int sz=p.getSize();
		       
		   	  try
		   		{
		   		nm=TextDecode.decodeUTF8Text(nm);
		   		}
		   	  catch(Exception ee)
		   	  {
		   		  ee.printStackTrace();
		   	  }
	        	
	          InputStream input = new ByteArrayInputStream(p.getContent().toString().getBytes()); //(InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(nfilePath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          flnm.add(nm);
	          flnewnm.add(uidnm+"_"+nm);
	         flsz.add(""+sz);
		         }
	    	  }
		         catch (Exception e) {
		        	 e.printStackTrace();
				}
		      }
	      else if (p.isMimeType("text/*")) {
	    	  System.out.println("content type" + p.getContentType());
	    	  try
	    	  {
		         String nm= p.getFileName();
		        
		   	  try
		   		{
		   		nm=TextDecode.decodeUTF8Text(nm);
		   		}
		   	  catch(Exception ee)
		   	  {
		   		  ee.printStackTrace();
		   	  }
		         
		         if(nm!=null && nm.length()>0)
		         {
		        int sz=p.getSize();
		       
	        	
	          InputStream input =   new ByteArrayInputStream(p.getContent().toString().getBytes());   // (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(nfilePath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          flnm.add(nm);
	          flnewnm.add(uidnm+"_"+nm);
	         flsz.add(""+sz);
		         }
	    	  }
		         catch (Exception e) {
					e.printStackTrace();
		         }
	      }
	      
	      else if (p.isMimeType("video/*")) {
	    	  System.out.println("content type" + p.getContentType());
	    	  try
	    	  {
		         String nm= p.getFileName();
		         
		   	  try
		   		{
		   		nm=TextDecode.decodeUTF8Text(nm);
		   		}
		   	  catch(Exception ee)
		   	  {
		   		  ee.printStackTrace();
		   	  }
		         
		         if(nm!=null && nm.length()>0)
		         {
		        int sz=p.getSize();
		       
	        	
	          InputStream input =   (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();   // (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(nfilePath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          flnm.add(nm);
	          flnewnm.add(uidnm+"_"+nm);
	         flsz.add(""+sz);
		         }
	    	  }
		         catch (Exception e) {
					e.printStackTrace();
		         }
	      }
	      
	      else if (p.isMimeType("audio/*")) {
	    	  System.out.println("content type" + p.getContentType());
	    	  try
	    	  {
		         String nm= p.getFileName();
		         
		   	  try
		   		{
		   		nm=TextDecode.decodeUTF8Text(nm);
		   		}
		   	  catch(Exception ee)
		   	  {
		   		  ee.printStackTrace();
		   	  }
		         
		         if(nm!=null && nm.length()>0)
		         {
		        int sz=p.getSize();
		       
	        	
	          InputStream input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();  // (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
	          String uidnm=	  UUID.randomUUID().toString();
	          OutputStream output =  new FileOutputStream(new File(nfilePath+uidnm+"_"+nm));
	          byte[] buffer = new byte[4096];

	          int byteRead;

	          while ((byteRead = input.read(buffer)) != -1) {
	             output.write(buffer, 0, byteRead);
	          }
	          input.close();
	          output.close();
	          flnm.add(nm);
	          flnewnm.add(uidnm+"_"+nm);
	         flsz.add(""+sz);
		         }
	    	  }
		         catch (Exception e) {
					e.printStackTrace();
		         }
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
	    			 try {
	    				 if(ret_sub!=null)
	    				 ret_sub=TextDecode.decodeUTF8Text(ret_sub);
	    				 else
	    					 ret_sub="";
	    				} catch (Exception e) {
	    					// TODO Auto-generated catch block
	    					ret_sub=h.getValue();
	    					e.printStackTrace();
	    				}
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
	        // System.out.println("This is a Nested Message");
	        // System.out.println("---------------------------");
	    	  ret_Att=true;
	    	  flg=2;
	         writePart((Part) p.getContent());
	      } 
 else if (p.isMimeType("message/delivery-status")) {
	    	  
	    	  String nm="details.txt";
	           
	           
		          Object o = p.getContent();
		          InputStream input = (InputStream) o;
		          
		          String uidnm=	  UUID.randomUUID().toString();
		          File fl=new File(nfilePath+uidnm+"_"+nm);
		          OutputStream output =  new FileOutputStream(fl);
		          byte[] buffer = new byte[4096];

		          int byteRead;

		          while ((byteRead = input.read(buffer)) != -1) {
		             output.write(buffer, 0, byteRead);
		          }
		          input.close();
		          output.close();
		         
		          
		          flnm.add(nm);
		          flnewnm.add(uidnm+"_"+nm);
		         flsz.add(""+fl.length());
	      }
	      
 else if(p.isMimeType("text/calendar"))
 {
	 
    
	  try
	  {
		  String nm="invite.ics";
          
          
          Object o = p.getContent();
          InputStream input = (InputStream) o;
          
          String uidnm=	  UUID.randomUUID().toString();
          File fl=new File(nfilePath+uidnm+"_"+nm);
          OutputStream output =  new FileOutputStream(fl);
          byte[] buffer = new byte[4096];

          int byteRead;

          while ((byteRead = input.read(buffer)) != -1) {
             output.write(buffer, 0, byteRead);
          }
          input.close();
          output.close();
         
          
          flnm.add(nm);
          flnewnm.add(uidnm+"_"+nm);
         flsz.add(""+fl.length());
	  }
	  catch(Exception ee)
	  {
		  ee.printStackTrace();
	  }

 }
	      
	      else if (p.isMimeType("application/*")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	         if(nm!=null)
	         {
	        
	   	  try
	   		{
	   		nm=TextDecode.decodeUTF8Text(nm);
	   		}
	   	  catch(Exception ee)
	   	  {
	   		  ee.printStackTrace();
	   	  }
	         
	        int sz=p.getSize();
	       
        	
          InputStream input = null;
          if(nm.endsWith(".ics") || nm.endsWith(".ICS") || p.getContentType().startsWith("application/octet-stream"))
          {
        	  input= (InputStream)p.getContent();
          }
          else
          {
        	 try
        	 {
        	  input=(InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
        	 }
        	 catch(Exception e)
        	 {
        		 input= (InputStream)p.getContent();
        	 }
          }
        		  
          String uidnm=	  UUID.randomUUID().toString();
          OutputStream output =  new FileOutputStream(new File(nfilePath+uidnm+"_"+nm));
          byte[] buffer = new byte[4096];

          int byteRead;

          while ((byteRead = input.read(buffer)) != -1) {
             output.write(buffer, 0, byteRead);
          }
          input.close();
          output.close();
         
          flnm.add(nm);
          flnewnm.add(uidnm+"_"+nm);
         flsz.add(""+sz);
	      }}
	      
	      else if (p.isMimeType("binary/octet-stream")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	         if(nm!=null)
	         {
	        
	   	  try
	   		{
	   		nm=TextDecode.decodeUTF8Text(nm);
	   		}
	   	  catch(Exception ee)
	   	  {
	   		  ee.printStackTrace();
	   	  }
	         
	        int sz=p.getSize();
	       
        	
          InputStream input = null;
          
        	 try
        	 {
        	  input=(InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
        	 }
        	 catch(Exception e)
        	 {
        		 input= (InputStream)p.getContent();
        	 }
       
        		  
          String uidnm=	  UUID.randomUUID().toString();
          OutputStream output =  new FileOutputStream(new File(nfilePath+uidnm+"_"+nm));
          byte[] buffer = new byte[4096];

          int byteRead;

          while ((byteRead = input.read(buffer)) != -1) {
             output.write(buffer, 0, byteRead);
          }
          input.close();
          output.close();
         
          flnm.add(nm);
          flnewnm.add(uidnm+"_"+nm);
         flsz.add(""+sz);
	      }}
	      
	      else if (p.getContentType().startsWith("binary/octet-stream")) {
	          System.out.println("content type" + p.getContentType());
	         String nm= p.getFileName();
	         if(nm!=null)
	         {
	        
	   	  try
	   		{
	   		nm=TextDecode.decodeUTF8Text(nm);
	   		}
	   	  catch(Exception ee)
	   	  {
	   		  ee.printStackTrace();
	   	  }
	         
	        int sz=p.getSize();
	       
        	
          InputStream input = null;
          
        	 try
        	 {
        	  input=(InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
        	 }
        	 catch(Exception e)
        	 {
        		 input= (InputStream)p.getContent();
        	 }
       
        		  
          String uidnm=	  UUID.randomUUID().toString();
          OutputStream output =  new FileOutputStream(new File(nfilePath+uidnm+"_"+nm));
          byte[] buffer = new byte[4096];

          int byteRead;

          while ((byteRead = input.read(buffer)) != -1) {
             output.write(buffer, 0, byteRead);
          }
          input.close();
          output.close();
         
          flnm.add(nm);
          flnewnm.add(uidnm+"_"+nm);
         flsz.add(""+sz);
	      }}
	      
	      else if (p.getContentType().contains("image/")) {

		    	//  System.out.println("content type"+p.getContentType());
		    	  boolean attachimg=false;
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
		    		  con_id= ""+inline;
		    		  attachimg=true;
		    	  }
		    	  else
		    	  {
		    		  con_id=con_id.replace("<", "");
		    		  con_id=con_id.replace(">", "");
		    		 // con_id=con_id.replaceAll("@", "_");
		    		//  con_id=con_id.replaceAll("\\.", "_");
		    		  if(!mail_cont.contains("src=\"cid:"+con_id+"\""))
		    		  {
		    			  attachimg=true;
		    		  }
		    		  con_id="cid:"+con_id; 
		    	  }
		    	  
		    	  if(attachimg)
		    	  {
		    		  String nm= p.getFileName();
		    		 
				   	  try
				   		{
				   		nm=TextDecode.decodeUTF8Text(nm);
				   		}
				   	  catch(Exception ee)
				   	  {
				   		  ee.printStackTrace();
				   	  }
		  	        int sz=p.getSize();
		  	       
		          	
		            InputStream input =  (InputStream)(com.sun.mail.util.BASE64DecoderStream)p.getContent();
		            String uidnm=	  UUID.randomUUID().toString();
		            OutputStream output =  new FileOutputStream(new File(nfilePath+uidnm+"_"+nm));
		            byte[] buffer = new byte[4096];

		            int byteRead;

		            while ((byteRead = input.read(buffer)) != -1) {
		               output.write(buffer, 0, byteRead);
		            }
		            input.close();
		            output.close();
		           
		            flnm.add(nm);
		            flnewnm.add(uidnm+"_"+nm);
		           flsz.add(""+sz);
		    	  }
		    	  else
		    	  {
		    	  File f = new File(nfilePath+p.getFileName());
		    	 
		    //	File f = new File("C:\\Users\\nirbhay\\AppData\\Local\\Temp\\"+p.getFileName());
		    // File f = new File("/tmp/tomcat7-tomcat7-tmp/"+p.getFileName());
		    	  FileOutputStream output = new FileOutputStream(f);
		    	  com.sun.mail.util.BASE64DecoderStream test=(com.sun.mail.util.BASE64DecoderStream)p.getContent();
		    	  byte[] buffer = new byte[1024];
		    	  int bytesRead;
		    	  while((bytesRead = test.read(buffer)) != -1)
		    	  {
		    	  output.write(buffer,0,bytesRead);
		    	  }
		    	  test.close();
		    	  output.close();
		    	  
		    	//  File fi = new File("myfile.jpg");
		    	  byte[] fileContent = Files.readAllBytes(f.toPath());
		    	  
		    	 String path_img= new sun.misc.BASE64Encoder().encode(fileContent);
	       	   path_img= "data:image/jpg;base64,"+path_img;
	       	   inline++;
	       	   mail_cont=mail_cont.replace(con_id, path_img);
	       	   // String inline_id="inline_img_"+inline;
	          /// 	mail_cont_otr+="<input type='hidden' id='"+con_id+"' value='" +path_img+ "' />";
		         /*System.out.println("content type" + p.getContentType());
		         File f = new File("C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\image" + new Date().getTime() + ".jpg");
		         DataOutputStream output = new DataOutputStream(
		            new BufferedOutputStream(new FileOutputStream(f)));
		            com.sun.mail.util.BASE64DecoderStream test = 
		                 (com.sun.mail.util.BASE64DecoderStream) p
		                  .getContent();
		         byte[] buffer = new byte[1024];
		         int bytesRead;
		         while ((bytesRead = test.read(buffer)) != -1) {
		            output.write(buffer, 0, bytesRead);
		         }
		        String s = new String();
		         mail_cont+=s;*/
		    	  }
	      } 
	      else {
	         Object o = p.getContent();
	         if (o instanceof String) {
	            System.out.println("This is a string");
	            System.out.println("---------------------------");
	            System.out.println((String) o);
	           // mail_cont+=(String) o;
	         } 
	         else if (o instanceof InputStream) {
	            /*System.out.println("This is just an input stream");
	            System.out.println("---------------------------");
	            InputStream is = (InputStream) o;
	            is = (InputStream) o;
	            int c;
	            while ((c = is.read()) != -1)
	               System.out.write(c);*/
	         } 
	         else {
	            System.out.println("This is an unknown type");
	            System.out.println("---------------------------");
	            System.out.println(o.toString());
	           // mail_cont+=(String) o;
	         }
	   }

	}
	
}
