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

import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.example.Connections;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import webmail.bean.TextDecode;
import webmail.wsdlnew.Attachment;
import webmail.wsdlnew.GetMailDisplayResponse;
import webmail.wsdlnew.InboxDisplay;

import java.io.*;
import java.nio.file.Files;

public class DisplayMailContent {

	   String mail_cont="";
	   int flg=0;
	   int inline=0;
	   String nfilePath="";
	   boolean ret_Att=false;
	   String ret_cnt="";
	   String ret_sub="";
	   String ret_bou="";
	    List<String> attach_nm=new ArrayList<String>();
	  	List<String> attach_size=new ArrayList<String>();
	   String icsSt="No";
	   byte []icsCntt=null;
	   String icsMethod="";
	   String icsString="";
	   String chk_cal="No";
	  
	/**
	 * @param host
	 * @param port
	 * @param id
	 * @param pass
	 * @param uid
	 * @param foldername
	 * @param filePath
	 * @return
	 */
	public GetMailDisplayResponse listWebmailContent(String host, String port, String id, String pass, String uid, String foldername, String filePath, String uidstatus)
	{
		GetMailDisplayResponse response = new GetMailDisplayResponse();
		InboxDisplay inb= new InboxDisplay();
		
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
  	icsSt="No";
  	icsCntt=null;
  	icsMethod="";
  	icsString="";
  	chk_cal="No";
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
  Message[] msg= folder.getMessagesByUID(arr);
  long allmsg= folder.getMessageCount();
  if(uidstatus!=null && uidstatus.length()>0)
  {
	  if(uidstatus.equalsIgnoreCase("next"))
	  {
		 int no =folder.getMessageByUID(Long.parseLong(uid)).getMessageNumber();
		 no--;
		 if(allmsg>=no && no>0)
		 {
			 int noarr[]={no};
			 msg=folder.getMessages(noarr);
		 }
		 else
		 {
			 inb.setErrorCnt("<nps>");
		 }
	  }
	  else if(uidstatus.equalsIgnoreCase("prev"))
	  {
		  int no=folder.getMessageByUID(Long.parseLong(uid)).getMessageNumber();
			 no++;
			 if(allmsg>=no && no>0)
			 {
				 int noarr[]={no};
				 msg=folder.getMessages(noarr);
			 }
			 else
			 {
				 inb.setErrorCnt("<nps>");
			 }
	  }
  }
  
 


int umsg= folder.getUnreadMessageCount();
 for (int i = 0; i< msg.length; i++)
  {
	 
	 inb.setUid(""+uf.getUID(msg[i]));
	 inb.setMsgNo(""+msg[i].getMessageNumber());
	 Message message = msg[i];
	 msg[i].setFlag(Flags.Flag.SEEN, true);
	 boolean chkst=	message.isSet(Flags.Flag.FLAGGED);
	 inb.setMailFlage(chkst);
	 boolean chkst1=	message.isSet(Flags.Flag.RECENT);
	 inb.setMailFlageRecent(chkst1); 
	 inb.setAllMsgNo(""+allmsg);
	 boolean readSt=false;
	 
	 String harr[]= msg[i].getHeader("List-Unsubscribe");
	 
	 Enumeration headers = msg[i].getAllHeaders();
	 int hd=0;
	 while (headers.hasMoreElements()) {
	 Header h = (Header) headers.nextElement();
	 /* out.print("<br>%%%%%%% "+h.getName() + ": " + h.getValue());*/
	  if(h.getName().equalsIgnoreCase("Disposition-Notification-To"))
	  {
		  hd++;
		  readSt=true;
		  inb.setMailReadRecId(h.getValue());
	  }
	  
	  if(h.getName().equalsIgnoreCase("X-Priority"))
	  {
		  hd++;
		if( h.getValue().contains("Lowest"))
		{
			inb.setMailPriority("Lowest");
		}
		else if(h.getValue().contains("Highest"))
		{
			inb.setMailPriority("Highest");
		}
		 
	  }
	  if(hd>1)
	  {
		  break; 
	  }
	  }
	 inb.setMailFlageRecent(readSt);
	 
	 
	 String tag="";
		String arrt[]= msg[i].getFlags().getUserFlags();
		 for(int b=0;b<arrt.length;b++)
		  {
			 if(tag.equals(""))
			 {
				 tag=arrt[b];
			 }
			 else
			 {
				 tag=tag+"~"+arrt[b];
			 }
		  // out.print("<br>user flag="+arr[b]);
		  } 
		inb.setMailTag(tag);
	 
	 
	 boolean chkseen=	message.isSet(Flags.Flag.SEEN);
	 inb.setMailSeen(chkseen);
	 
  String from = InternetAddress.toString(msg[i].getFrom());
  
  from= TextDecode.decodeUTF8Text(from);
  inb.setFromId(from);
 

  String replyTo = InternetAddress.toString(msg[i].getReplyTo());
  inb.setReplyId(replyTo);

  String to = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.TO));
  if(to==null)
  {
	  to="";
  }
  
  
  inb.setToId(to);

  String cc=InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.CC));
  if(cc==null)
  {
	  cc="";
  }
  inb.setCCId(cc);
  
  String bcc=InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.BCC));
  if(bcc==null)
  {
	  bcc="";
  }
  inb.setBCCId(bcc);
  
  String subject = msg[i].getSubject();
  
 
  subject= TextDecode.decodeUTF8Text(subject);
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
 
  //Object content = msg[i].getContent();  

  String attch="";
  //String mail_cont="";
  Message message1 = msg[i];
  //System.out.println("---------------------------------");
  writePart(message1);
  
 //mail_cont=mail_cont.replaceAll("\n\n\n\n\n", "<br />");
// mail_cont=mail_cont.replaceAll("\n\n\n\n", "<br />");
// mail_cont=mail_cont.replaceAll("\n\n\n", "<br />");
// mail_cont=mail_cont.replaceAll("\n\n", "<br />");
// mail_cont=mail_cont.replaceAll("\n", "<br />");
  /*mail_cont=mail_cont.replaceAll("\r", "<br />");*/
  //System.out.println("#############="+mail_cont);
 String tp=msg[i].getContentType();
 /* if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
  {
	 // String cnt=msg[i].getContent().toString();
  	  
  	//	mail_cont=cnt;
  	  
  }
  else
  {
	  if (content instanceof String)  
{  
    String body = (String)content;  
  
     attch="";
}  
else if (content instanceof Multipart)  
{  
    Multipart multipart = (Multipart)content;  
    
 

    for (int j = 0; j < multipart.getCount(); j++) {

        BodyPart bodyPart = multipart.getBodyPart(j);
        Attachment at=new Attachment();
      //  mail_cont=getText(bodyPart);
       // if(mail_cont.length()>100)
    	///  {
    	 ///   mail_cont=mail_cont.substring(0, 99);
    	///  }
    	 
        
        String disposition = bodyPart.getDisposition();

          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
          	{ 
        	 
              DataHandler handler = bodyPart.getDataHandler();
              if(handler.getName()!=null && ""+bodyPart.getSize()!=null)
              {
              at.setAttachmentName(handler.getName());
              at.setAttachmentSize(""+bodyPart.getSize());
                            
              inb.getAttachment().add(at);
              }
            }
          else 
          	{
        	  
        	  if(bodyPart.getContentType().contains("text/html;"))
    		  {
        	  String cnt=bodyPart.getContent().toString();
              if(mail_cont==null || mail_cont.equals(""))
              {
          	   //  	mail_cont=cnt;
          	  }
    		  }
        	  else  if(bodyPart.getContentType().contains("text/plain;"))
    		  {
            	  continue;
    		  }
        	  else
        	  {
        		  String cnt=bodyPart.getContent().toString();
                  if(mail_cont==null || mail_cont.equals(""))
                  {
              	     //	mail_cont=cnt;
              	  }
        	  }
            }
   
  }
  }
  }*/
// String arr[]=attch.split(",");
 // String str="<div dir='ltr'>find document<br clear='all'><div><div><br>-- <br><div class='gmail_signature'><div dir='ltr'><div dir='ltr' style='color:rgb(34,34,34);font-family:arial;font-size:small;font-style:normal;font-variant:normal;font-weight:normal;letter-spacing:normal;line-height:normal;text-align:start;text-indent:0px;text-transform:none;white-space:normal;word-spacing:0px'><p style='margin:0px'><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif;background-image:initial;background-repeat:initial'>We look forward to a long and fruitful association with you.</span><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif'><br><br></span></p><p style='margin:0px'><b><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif;color:rgb(38,38,38)'>Thanks and Regards</span></b></p><p style='margin:0px'><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif'><br></span><span style='font-size:12pt;font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif;color:rgb(38,38,38)'>Nirbhay Pratap Singh</span></p><p style='margin:0px'><font size='3' face='Microsoft New Tai Lue, sans-serif'>Hindi Poet</font><b style='color:rgb(38,38,38);font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif'></b></p><p style='margin:0px'><b><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif;color:rgb(38,38,38)'>Call  us @:</span></b><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif;color:rgb(38,38,38)'> +91-9532392409<br><b>Visit us @:</b>  <a href='http://www.nirbhaypratapsingh.com' target='_blank'>www.nirbhaypratapsingh.com</a></span><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif'><a href='http://www.google.com/url?q=http%3A%2F%2Fwww.silvereye.co&amp;sa=D&amp;sntz=1&amp;usg=AFQjCNEkmf2lpjcs1nZBAD2YV2lQ3jGqDw' style='color:rgb(17,85,204)' target='_blank'><span style='color:blue'></span></a></span></p></div></div></div></div></div></div>";
 // mail_cont=mail_cont.replace('"', '\'');
 // System.out.println("$$$$$$$$$$$$$$="+mail_cont);
  if( msg[i].getContentType().contains("text/plain;"))
  {
	  mail_cont="<pre>"+mail_cont+"</pre>";  
  }
 /* else
  {
	  mail_cont=mail_cont.replaceAll("<pre", "<span")  ;
	  mail_cont=mail_cont.replaceAll("</pre>", "</span>")  ;
  }*/
  
  if(attach_nm!=null && attach_size!=null)
  {
	 
	  for(int ai=0; ai<attach_nm.size() && ai< attach_size.size();ai++)
	  {
		  Attachment at=new Attachment();
		  at.setAttachmentName(attach_nm.get(ai));
          at.setAttachmentSize(attach_size.get(ai));
          inb.getAttachment().add(at);
	  }

	 
  }
  inb.setICSString(icsString);
inb.setICScntt(icsCntt);
inb.setICSstatus(icsSt);
inb.setICSMethod(icsMethod);
  inb.setMailContent(mail_cont.trim());
  inb.setInlineimgsize(""+inline);
  inline=0;
  nfilePath="";
  attach_nm.clear();
  attach_size.clear();
  icsSt="No";
	icsCntt=null;
  //mail_cont="";
	icsMethod="";
	icsString="";
	chk_cal="No";
  }
 
  folder.close(true);
  store.close();
  }
  catch(MessagingException e)
        {
	  //	status=false;
	  e.printStackTrace();
	  String ecnt=e.toString();
	  if(ecnt.length()>200)
		  ecnt=ecnt.substring(0, 199);
	  inb.setErrorSt(ecnt);
	  inb.setErrorCnt("<nps>");
        }
        
catch(Exception e)
        {
		//status=false;
	e.printStackTrace();
	  String ecnt=e.toString();
	  if(ecnt.length()>200)
		  ecnt=ecnt.substring(0, 199);
	  inb.setErrorSt(ecnt);
	 inb.setErrorCnt("<nps>");
        }
        
        response.setGetInboxByUid(inb);           
  return  response;   
}
	
	
	
	
	public String listWebmailHeader(String host, String port, String id, String pass, String uid, String foldername)
	{
		String mailheader="";
		
		IMAPFolder folder = null;
        Store store = null;
       // Flag flag = null;
        //mail_cont="";
       // flg=0;
        try 
        {
        	store=Connections.imapConnectionNP(host, port, id, pass); 
			IMAPStore imapStore = (IMAPStore) store; 
			folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account
    
if (!folder.exists()) {
	// inboxlist.setGetInboxByUid(inb);
    // return   inboxlist;        
  	}
  folder.open(Folder.READ_ONLY);
  long [] arr={Long.parseLong(uid)};
  Message[] msg =folder.getMessagesByUID(arr);

 for (int i = 0; i< msg.length; i++)
  {
	
	
	 Enumeration headers = msg[i].getAllHeaders();
	 while (headers.hasMoreElements()) {
	 Header h = (Header) headers.nextElement();
	 /* out.print("<br>%%%%%%% "+h.getName() + ": " + h.getValue());*/
	 String tmp=h.getName() + ":&nbsp; " + h.getValue();
	 tmp=tmp.replace("<", "&lt;");
	 tmp=tmp.replace(">", "&gt;");

	 
	 mailheader=mailheader+tmp+"<br />";
	  }
  }
       
        folder.close(true);
        store.close();
        }
        catch(MessagingException e)
              {
      	  //	status=false;
              }
              
      catch(Exception e)
              {
      		//status=false;
              }
		
		return mailheader;
	}
	
	
	 /*
	   * This method checks for content-type 
	   * based on which, it processes and
	   * fetches the content of the message
	   */
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
	    			  mail_cont="<pre>"+ HtmlUtils.htmlEscape(p.getContent().toString())+"</pre>";
	    		  }
	    		  catch(Exception e)
	    		  {
	    			  mail_cont="<pre>"+ HtmlUtils.htmlEscape(IOUtils.toString(p.getInputStream()))+"</pre>";
	    		  }
	    	  }
	    	  if(flg==0)
	    	  {
	    		  flg=1;
	    	  }
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		        int sz=  p.getSize();
		        if(nm!=null  && nm.length()>0)
		        {
		        attach_nm.add(nm);
		        attach_size.add(""+sz);
		        }
	    	  }
	    	  catch(Exception e)
	    	  {
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
	    		 if(ret_sub==null || ret_sub.length()<=0)
	    			 ret_sub="Message";
	    		 InputStream input = new ByteArrayInputStream(ret_cnt.getBytes());
	    		 File fl=new File(nfilePath+ret_sub+".eml");
	             OutputStream output =  new FileOutputStream(fl);
	             byte[] buffer = new byte[4096];

	             int byteRead;

	             while ((byteRead = input.read(buffer)) != -1) {
	                output.write(buffer, 0, byteRead);
	             }
	             input.close();
	             output.close();
	             
	             attach_nm.add(ret_sub+".eml");
			      attach_size.add(""+fl.length());
	    	  }
	    		
	    	  
	    	  if(flg!=2)
	    	  {
		       // System.out.println("This is plain text");
		      //  System.out.println("---------------------------flg="+flg);
		      //   System.out.println((String) p.getContent());
	    		  
		            // mail_cont= IOUtils.toString(p.getInputStream());
	    		try
	    		{
		         mail_cont=(String) p.getContent();
	    		}
	    		catch(Exception e)
	    		{
	    			mail_cont= IOUtils.toString(p.getInputStream());
	    		}
	    	  }
	    	 
	    	  
	    	  flg=2;
	    	  
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		        int sz=  p.getSize();
		        if(nm!=null  && nm.length()>0)
		        {
		        attach_nm.add(nm);
		        attach_size.add(""+sz);
		        }
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  e.printStackTrace();
	    	  }
		      }
	      else if(p.isMimeType("text/calendar"))
	      {
	    	  chk_cal="Yes";
	    	  attach_nm.add("invite.ics");
		      attach_size.add("1024");
	           // Object o = p.getContent();
	          // InputStream is = (InputStream) o;
	            try
	            {
	            icsCntt =  org.apache.commons.codec.binary.Base64.encodeBase64(IOUtils.toByteArray((InputStream)p.getContent()));  
	            }
	            catch(Exception e)
	            {
	            	try
	            	{
	            	icsCntt =  IOUtils.toByteArray((InputStream)p.getContent());  
	            	}
	            	catch(Exception ee )
	            	{
	            		icsCntt =  p.getContent().toString().getBytes();  
	            	}
	            }
	            String cntt="";
	            try
	            {
	            cntt=IOUtils.toString((InputStream)p.getContent(), "UTF-8");
	            }
	            catch(Exception e)
	            {
	            	 cntt=p.getContent().toString();
	            }
	            if(cntt!=null && (cntt.contains("METHOD") || cntt.contains("method")))
	            {	
	            icsSt="Yes";
	            icsString=cntt;
	            String arr[]=cntt.split("\\n");
	            for(String ar: arr)
	            {
	            	if(ar!=null)
	            	{
	            	ar=ar.toUpperCase().trim();
	            	if(ar.startsWith("METHOD"))
	            	{
	            		String arr1[]=ar.split(":");
	            		if(arr1.length>1)
	            		{
	            			icsMethod=arr1[1];
	            			break;
	            		}
	            	}
	            		
	            	}
	            }
	            }

	      }
	      else if (p.isMimeType("text/rfc822-headers")) {
	    	  
	    	/*  attach_nm.add("details.txt");
		      attach_size.add("1024");
	    	  System.out.println("rfc822");
	    	  System.out.println("This is just an input stream");
	            System.out.println("---------------------------");
	            Object o = p.getContent();
	            InputStream is = (InputStream) o;
	            //is = (InputStream) o;
	            int c;
	            while ((c = is.read()) != -1)
	            {
	               System.out.write(c);
	            }
	          
	            System.out.println("---------------------------");*/
	      }
	      else if (p.isMimeType("text/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		        int sz=  p.getSize();
		        if(nm!=null  && nm.length()>0)
		        {
		        attach_nm.add(nm);
		        attach_size.add(""+sz);
		        }
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  e.printStackTrace();
	    	  }
	      }
	      else if (p.isMimeType("video/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		        int sz=  p.getSize();
		        if(nm!=null  && nm.length()>0)
		        {
		        attach_nm.add(nm);
		        attach_size.add(""+sz);
		        }
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  e.printStackTrace();
	    	  }
	      }
	      else if (p.isMimeType("audio/*")) {
	    	  try
	    	  {
	    	  String nm= p.getFileName();
		        int sz=  p.getSize();
		        if(nm!=null  && nm.length()>0)
		        {
		        attach_nm.add(nm);
		        attach_size.add(""+sz);
		        }
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  e.printStackTrace();
	    	  }
	      }
	      
	      //check if the content has attachment
	      else if (p.isMimeType("multipart/*")) {
	        // System.out.println("This is a Multipart");
	        // System.out.println("---------------------------");
	    	 // System.out.println("---------------------------");
	    	 // System.out.println("content type" + p.getContentType());
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
	    	  
	    /*	  System.out.println("---------------------------");
	    	  Enumeration headers = p.getAllHeaders();
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 System.out.println(h.getName()+":-"+ h.getValue());
	    		 }
	    		 System.out.println("---------------------------");*/
	    	  ret_Att=true;
	    	  flg=2;
	         writePart((Part) p.getContent());
	      } 
	     //check if the content is an inline image
	    /*  else if (p.isMimeType("image/jpeg")) {
	    	  
	    	 
	    	  
	         System.out.println("--------> image/jpeg");
	         Object o = p.getContent();

	         InputStream x = (InputStream) o;
	         // Construct the required byte array
	         System.out.println("x.length = " + x.available());
	         int i = 0;
	         byte[] bArray = new byte[x.available()];

	         while ((i = (int) ((InputStream) x).available()) > 0) {
	            int result = (int) (((InputStream) x).read(bArray));
	            if (result == -1)
	            break;
	         }
	        
	         FileOutputStream f2 = new FileOutputStream("/tmp/"+ p.getFileName());
	         f2.write(bArray);
	         
	         mail_cont+="<img src='C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\" + p.getFileName() + "' />";
	      } */
	      else if (p.getContentType().contains("image/")) {
	    	  System.out.println("content type"+p.getContentType());
	    	  
	    	 
	    	  
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
	    	  String imgpath="";
	    	  if(con_id.equalsIgnoreCase(""))
	    	  {
	    		  con_id= ""+inline;
	    		  try
		    	  {
		    		String nm= p.getFileName();
		  	        int sz=  p.getSize();
		  	        attach_nm.add(nm);
		  	        attach_size.add(""+sz);
		    	  }
		    	  catch(Exception ex)
		    	  {
		    		  
		    	  }
	    	  }
	    	  else
	    	  {
	    		  con_id=con_id.replace("<", "");
	    		  con_id=con_id.replace(">", "");
	    		  if(!mail_cont.contains("src=\"cid:"+con_id+"\""))
	    		  {
	    			  con_id= ""+inline;
		    		  try
			    	  {
			    		String nm= p.getFileName();
			  	        int sz=  p.getSize();
			  	        attach_nm.add(nm);
			  	        attach_size.add(""+sz);
			    	  }
			    	  catch(Exception ex)
			    	  {
			    		  
			    	  }
	    		  }
	    		  else
	    		  {
	    		  con_id=con_id.replaceAll("@", "_");
	    		  con_id=con_id.replaceAll("\\.", "_");
	    		  //con_id="cid:"+con_id; 
	    	
	    	  String uidnm=	  UUID.randomUUID().toString();
	    	  String flnm=p.getFileName();
	    	  if(flnm!=null)
	    	  flnm=flnm.replaceAll("'", "");
	    	  if(flnm!=null)
	    	  flnm=flnm.replaceAll("\"", "");
	    	  imgpath= "temp/"+uidnm+"_"+flnm;
	    	  File f = new File(nfilePath+uidnm+"_"+flnm);
	    
	    	// File f = new File("C:\\Users\\nirbhay\\AppData\\Local\\Temp\\"+p.getFileName());
	    	//  File f = new File("/tmp/tomcat7-tomcat7-tmp/"+p.getFileName());
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
	    	 /* byte[] fileContent = Files.readAllBytes(f.toPath());
	    	  
	    	 String path_img= new sun.misc.BASE64Encoder().encode(fileContent);
       	   path_img= "data:image/jpg;base64,"+path_img;*/
       	   inline++;
       	   mail_cont+="<input type='hidden' id='"+con_id+"' value='" +imgpath+ "' />";
	    		  }
	    	  }
       	  // String inline_id="inline_img_"+inline;
	    	
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
	      
	      else if (p.isMimeType("application/*")) {
	        System.out.println("content type" + p.getContentType());
	        String nm= p.getFileName();
	        int sz=  p.getSize();
	      //  String desc= p.getDescription().trim();
	        if(p.isMimeType("application/pkcs7-mime"))
	        {
	        	mail_cont="This is an encrypted message and can not be displayed. Sorry!";
	        }
	        else if(!(chk_cal.equalsIgnoreCase( "Yes") && nm.equalsIgnoreCase("invite.ics")))
	        {
	        attach_nm.add(nm);
	        attach_size.add(""+sz);
	        }
	       } 
	      else if (p.isMimeType("binary/octet-stream")) {
		        System.out.println("content type" + p.getContentType());
		        String nm= p.getFileName();
		        int sz=  p.getSize();
		      //  String desc= p.getDescription().trim();
		     
		        attach_nm.add(nm);
		        attach_size.add(""+sz);
		      
		       }
	      else if (p.getContentType().startsWith("binary/octet-stream")) {
	    	  System.out.println("content type" + p.getContentType());
		        String nm= p.getFileName();
		        int sz=  p.getSize();
		      //  String desc= p.getDescription().trim();
		     
		        attach_nm.add(nm);
		        attach_size.add(""+sz);
	      }
	      else if (p.isMimeType("message/delivery-status"))
	      {
	    	  
	    	/*  System.out.println("---------------------------");
	    	  System.out.println("content type" + p.getContentType());
	    	  Enumeration headers = p.getAllHeaders();
	    		 String con_id="";
	    		 while (headers.hasMoreElements()) {
	    		 Header h = (Header) headers.nextElement();
	    		 System.out.println(h.getName()+":-"+ h.getValue());
	      }
	    		 System.out.println("---------------------------");
	    		 
	    		 Object o = p.getContent();
		            InputStream is = (InputStream) o;
		            //is = (InputStream) o;
		            int c;
		            while ((c = is.read()) != -1)
		            {
		               System.out.write(c);
		            }
		          
		            System.out.println("---------------------------");*/
	    		 
	    		 attach_nm.add("details.txt");
			      attach_size.add("1024");
		        
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
	           /* System.out.println("This is just an input stream");
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
	          //  mail_cont+=(String) o;
	         }
	   }

	}
	
}
