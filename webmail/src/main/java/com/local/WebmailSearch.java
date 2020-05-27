package com.local;


import javax.activation.DataHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import org.springframework.stereotype.Component;

import com.example.Connections;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.SortTerm;
import webmail.wsdlnew.ArrayOfInboxMailQuickSearch;
import webmail.wsdlnew.ArrayOfInboxMailSearch;
import webmail.wsdlnew.GetMailInboxQuickSearchResponse;
import webmail.wsdlnew.GetMailInboxSearchResponse;
import webmail.wsdlnew.InboxListReturnQuickSearch;
import webmail.wsdlnew.InboxListReturnSearch;
import webmail.wsdlnew.InboxQuickSearch;
import webmail.wsdlnew.InboxSearch;




public class WebmailSearch {


	 private boolean textIsHtml = false;
	  String mail_cont_otr="";
	 String mail_cont="";
	 String mail_cont_s="";
	 int flg=0;
	
	public String countAllMailSearchAvd(String host, String port, String id, String pass, String foldername, String to_cnt, String from_cnt, String sub_cnt, String cntt, String sdt,String edt, String tgnm, String stat)
	{
		String res="";
		
		
		final String to= to_cnt;
		final String from= from_cnt;
		final String sub= sub_cnt;
		final String con=cntt ;
		//final String dt= dat;
		final String tagnm=tgnm;
		final String act=stat;
		// String sdt="2016-01-25";
		 //String edt="2016-01-27";
		 
		 Date endDate=null;
    	 Date beginDate=null;
    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	 if(sdt!=null && !(sdt.equalsIgnoreCase("")))
    	 {
    		 try {
				beginDate= formatter.parse(sdt);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				beginDate=null;
			}
    	 }
    	 if(edt!=null && !(edt.equalsIgnoreCase("")))
		 {
    		 try {
				endDate= formatter.parse(edt);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				endDate=null;
			}
		 }
		
		
		boolean status=true;		
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
   	 return "0";      
 		}
     folder.open(Folder.READ_ONLY);
 
 
 
     SearchTerm searchCondition=null;
     int sflg=0;
     
     if(from!=null && !(from.equals("")))
	 {
   	 
    if(from.contains("<") && from.contains(">"))
    {
    	try
    	{
    	String arr_from[]=from.split("<");
    	String nfrom=arr_from[1].replace(">", "");
    	nfrom=nfrom.trim();
    	 if(sflg==0)
      	  {
      		  searchCondition=new OrTerm( new FromStringTerm(from), new FromStringTerm(nfrom));
      		  sflg=1;
      	  }
      	  else
      	  {
      		 // FromStringTerm frm=new FromStringTerm(from);
      		  searchCondition = new AndTerm( searchCondition,  new OrTerm( new FromStringTerm(from), new FromStringTerm(nfrom)));
      	  }
    	}
    	catch(Exception ee)
    	{
    		ee.printStackTrace();

    	    if(sflg==0)
    	   	  {
    	   		  searchCondition=new FromStringTerm(from);
    	   		  sflg=1;
    	   	  }
    	   	  else
    	   	  {
    	   		  FromStringTerm frm=new FromStringTerm(from);
    	   		  searchCondition = new AndTerm( searchCondition,  frm);
    	   	  }
    	   		
    	}
    }
    else
    {
    if(sflg==0)
   	  {
   		  searchCondition=new FromStringTerm(from);
   		  sflg=1;
   	  }
   	  else
   	  {
   		  FromStringTerm frm=new FromStringTerm(from);
   		  searchCondition = new AndTerm( searchCondition,  frm);
   	  }
   		
    }
	 }

     if(to!=null && !(to.equals("")))
 	 {
    	 if(to.contains("<") && to.contains(">"))
    	    {
    		 try
    		 {
    		 String arr_to[]=to.split("<");
    	    	String nto=arr_to[1].replace(">", "");
    	    	nto=nto.trim();
    	    	if(sflg==0)
    	    	  {
    	    		  searchCondition=new OrTerm( new RecipientStringTerm(Message.RecipientType.TO,to),  new RecipientStringTerm(Message.RecipientType.TO,nto));
    	    		  sflg=1;
    	    	  }
    	    	  else
    	    	  {
    	    		// RecipientStringTerm sto=new RecipientStringTerm(Message.RecipientType.TO,to);
    	    		  searchCondition = new AndTerm( searchCondition,  new OrTerm( new RecipientStringTerm(Message.RecipientType.TO,to),  new RecipientStringTerm(Message.RecipientType.TO,nto)));
    	    	  }
    		 }
    		 catch(Exception ee)
    		 {
    			 ee.printStackTrace();

    	    	  if(sflg==0)
    	    	  {
    	    		  searchCondition=new RecipientStringTerm(Message.RecipientType.TO,to);
    	    		  sflg=1;
    	    	  }
    	    	  else
    	    	  {
    	    		 RecipientStringTerm sto=new RecipientStringTerm(Message.RecipientType.TO,to);
    	    		  searchCondition = new AndTerm( searchCondition,  sto);
    	    	  }
    		 }
    	    }
    	 else
    	 {
    	  if(sflg==0)
    	  {
    		  searchCondition=new RecipientStringTerm(Message.RecipientType.TO,to);
    		  sflg=1;
    	  }
    	  else
    	  {
    		 RecipientStringTerm sto=new RecipientStringTerm(Message.RecipientType.TO,to);
    		  searchCondition = new AndTerm( searchCondition,  sto);
    	  }
    	 }   
 	 }
		
     if(sub!=null && !(sub.equals("")))
     {
   	  if(sflg==0)
    	  {
    		  searchCondition=new  SubjectTerm(sub);
    		  sflg=1;
    	  }
    	  else
    	  {
    		  searchCondition = new AndTerm( searchCondition, new  SubjectTerm(sub));
    	  }
    		   
     }
     
     
     if(endDate!=null && !(sdt.equals("")) && beginDate!=null && !(edt.equals("")))
     {
    	
   	  if(sflg==0)
    	  {
    		  searchCondition= new AndTerm( new ReceivedDateTerm( ComparisonTerm.LE, endDate ), new ReceivedDateTerm( ComparisonTerm.GE, beginDate ) ) ;
    		  sflg=1;
    	  }
    	  else
    	  {
    		  searchCondition = new AndTerm( searchCondition,new AndTerm( new ReceivedDateTerm( ComparisonTerm.LE, endDate ), new ReceivedDateTerm( ComparisonTerm.GE, beginDate ) ));
    		 // searchCondition = new AndTerm( searchCondition, new ReceivedDateTerm( ComparisonTerm.GT, beginDate ));
    	  }
    		   
     }
     
     
     
     if(tagnm!=null && !(tagnm.equals("")))
	 {
   	  Flags processedFlag = new Flags(tagnm);
   	  if(sflg==0)
    	  {
    		  searchCondition=new FlagTerm(processedFlag, true);
    		  sflg=1;
    	  }
    	  else
    	  {
    		  searchCondition = new AndTerm( searchCondition, new FlagTerm(processedFlag, true));
    	  }  
	 }
     
     if(con!=null && !(con.equals("")))
 	 {
    	  if(sflg==0)
     	  {
     		  searchCondition= new BodyTerm(con);
     		  sflg=1;
     	  }
     	  else
     	  {
     		  searchCondition = new AndTerm( searchCondition, new BodyTerm(con));
     	  }  
 	 }
     
     if(act!=null && !(act.equals("")))
     {
   	  boolean st=true;
   	  Flags flg = new Flags(Flags.Flag.SEEN);
   	  if(act.equalsIgnoreCase("Stared") || act.equalsIgnoreCase("Unstared"))
   	  {
   		  flg = new Flags(Flags.Flag.FLAGGED);
   	  }
   	  
   	  if(act.equalsIgnoreCase("Unread") || act.equalsIgnoreCase("Unstared"))
   	  {
   		  st=false;
   	  }
   	  //FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
   	  if(sflg==0)
     	  {
     		  searchCondition= new FlagTerm(flg, st);
     		  sflg=1;
     	  }
     	  else
     	  {
     		  searchCondition = new AndTerm( searchCondition,  new FlagTerm(flg, st));
     	  }  
     }

    /* SearchTerm searchCondition = 
   		   new AndTerm(
   				 new  SubjectTerm(sub), 
   		      new BodyTerm(con)
   		      );*/
 
/*SortTerm[] term1=new SortTerm[1];
if(folder.getFullName().equalsIgnoreCase("Sent"))
{
	term1[0] = SortTerm.DATE;
}
else
{
	term1[0] = SortTerm.ARRIVAL;
}
 

Message[] sort_msg =folder.getSortedMessages(term1);  */
Message[] msg =folder.search(searchCondition)  ;         //(searchCondition);

res=""+msg.length;
       }
       catch(Exception e)
       {
    	   e.printStackTrace();
    	   res="0";
       }
try {
	folder.close(true);
	store.close();
} catch (MessagingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return res;
	}
	
	
	
	public GetMailInboxSearchResponse listWebmailSearch(String host, String port, String id, String pass, String start, String end, String foldername, String to_cnt, String from_cnt, String sub_cnt, String cntt, String sdt,String edt, String tgnm, String stat)
	{
		GetMailInboxSearchResponse response =new GetMailInboxSearchResponse();
		final String to= to_cnt;
		final String from= from_cnt;
		final String sub= sub_cnt;
		final String con=cntt ;
		//final String dt= dat;
		final String tagnm=tgnm;
		final String act=stat;
		// String sdt="2016-01-25";
		 //String edt="2016-01-27";
		 
		 Date endDate=null;
    	 Date beginDate=null;
    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	 if(sdt!=null && !(sdt.equalsIgnoreCase("")))
    	 {
    		 try {
				beginDate= formatter.parse(sdt);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				beginDate=null;
			}
    	 }
    	 if(edt!=null && !(edt.equalsIgnoreCase("")))
		 {
    		 try {
				endDate= formatter.parse(edt);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				endDate=null;
			}
		 }
		
		InboxListReturnSearch inboxlist= new InboxListReturnSearch();
		ArrayOfInboxMailSearch inboxmail= new ArrayOfInboxMailSearch();
		boolean status=true;		
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
   	  inboxlist.setInboxListReturnSearch(inboxmail);
   	  inboxlist.setSuccess(false);
   	  inboxlist.setSearchCount(0);
   	response.setGetInboxByMailLimitSearch(inboxlist);
   	  return   response;        
 		}
     folder.open(Folder.READ_ONLY);
 
 
 /*
 // creates a search criterion
 SearchTerm searchCondition = new SearchTerm() {
     @Override
     public boolean match(Message message) {
         try {
       	  boolean st=false;
       	               
       	  if(from!=null && !(from.equals("")))
	        	 {
       		  Address[] fromAddress = message.getFrom();
	        	 boolean st_frm=false;
	              for (int i=0;i<fromAddress.length;i++ ) {
	                  if (fromAddress[i].toString().contains(from)  ) {
	                	  st_frm= true;
	                	  st=true;
	                	  break;
	                  } 
	              }
	              if(!st_frm)
	              {
	            	  return st_frm;
	              }
	        	 }
       	 
       	  
       	  if(to!=null && !(to.equals("")))
	        	 {
       		  Address[] toAddress = message.getAllRecipients();
	        	 boolean st_to=false;
	              for (int i=0;i<toAddress.length;i++ ) {
	                  if (toAddress[i].toString().contains(to)  ) {
	                	  st_to= true;
	                	  st=true;
	                	  break;
	                  } 
	              }
	              if(!st_to)
	              {
	            	  return st_to;
	              }
	        	 }
       	  
       	  
       	 if(dt!=null && !(dt.equals("")))
       	 {
       	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       	 Date date = formatter.parse(dt);
         	 Date date1= formatter.parse(formatter.format(message.getReceivedDate()));
         	
         	 if (date1.equals(date) )
         	  {
                st= true;
             }
         	 else
         	 {
         		 return false;
         	 }
       	 }
       	 
       	 
       	 if(sub!=null && !(sub.equals("")))
       	 {
       	  if (message.getSubject().contains(sub) ) {
                st= true;
             }
         	 else
         	 {
         		 return false;
         	 }
       	 }
       	 
       	 
       	 
       	 
       	 if(tagnm!=null && !(tagnm.equals("")))
       	 {
       		 boolean tgst=false;
       		  String arr[]= message.getFlags().getUserFlags();
                
                	 for(int b=0;b<arr.length;b++)
                	 {
                    if (arr[b].equalsIgnoreCase(tagnm))
                    {
                   	 tgst=true;
                   	 break;
                    }
                	 }
       	  if (tgst) 
       	  {
                st= true;
             }
         	 else
         	 {
         		 return false;
         	 }
       	 }
       	 
       	 
       	 
       	 if(con!=null && !(con.equals("")))
       	 {
       		 String contentType = message.getContentType().toLowerCase();
                if (contentType.contains("text/plain")
                        || contentType.contains("text/html")) {
                    String messageContent = message.getContent().toString();
                    if (messageContent.contains(con)) {
                        st= true;
                    }
                    else
                    {
                   	 return false;
                    }
                }
       		/* mail_cont_s="";
       		 
       		 
       		 String cnt_att="";
       		 Object content = message.getContent();  
       		 if(message.getContentType().contains("text/html;") || message.getContentType().contains("text/plain;"))
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
       		             
       		           
       		              InputStream in = bodyPart.getInputStream();
       		             
       		              cnt_att+= org.apache.commons.io.IOUtils.toString(in);
       		          	in.close();
       		              
       		            
       		            }
       		         
       		   
       		  }
       		  }
       		  }
       		 
       		 
       		 
                writePartSearch(message);
               // mail_cont_s+= cnt_att;
                if (mail_cont.contains(con)) {
                    st= true;
                }
                else
                {
               	 return false;
                }
                
       	 }
       	 
       	 return st;
       	 
         } catch (MessagingException ex) {
             ex.printStackTrace();
         }
         catch (Exception ex) {
             ex.printStackTrace();
         }
         return false;
     }
 };
 */
     SearchTerm searchCondition=null;
     int sflg=0;
     
     if(from!=null && !(from.equals("")))
	 {
    	 if(from.contains("<") && from.contains(">"))
    	 {
    		 try
    	    	{
    	    	String arr_from[]=from.split("<");
    	    	String nfrom=arr_from[1].replace(">", "");
    	    	nfrom=nfrom.trim();

    	        if(sflg==0)
    	      	  {
    	      		  searchCondition=new OrTerm( new FromStringTerm(from), new FromStringTerm(nfrom) );
    	      		  sflg=1;
    	      	  }
    	      	  else
    	      	  {
    	      		 // FromStringTerm frm=new FromStringTerm(from);
    	      		  searchCondition = new AndTerm( searchCondition,  new OrTerm( new FromStringTerm(from), new FromStringTerm(nfrom) ));
    	      	  }
    	       	
    	    	
    	    	}
    		 catch(Exception ee)
    		 {
    			 ee.printStackTrace();

    		     if(sflg==0)
    		   	  {
    		   		  searchCondition=new FromStringTerm(from);
    		   		  sflg=1;
    		   	  }
    		   	  else
    		   	  {
    		   		  FromStringTerm frm=new FromStringTerm(from);
    		   		  searchCondition = new AndTerm( searchCondition,  frm);
    		   	  }
    		    	
    		 }
    	 }
    	 else
    	 {
     if(sflg==0)
   	  {
   		  searchCondition=new FromStringTerm(from);
   		  sflg=1;
   	  }
   	  else
   	  {
   		  FromStringTerm frm=new FromStringTerm(from);
   		  searchCondition = new AndTerm( searchCondition,  frm);
   	  }
    	 }
	 }

     if(to!=null && !(to.equals("")))
 	 {
    	 if(to.contains("<") && to.contains(">"))
    	 {
    		 try
    	    	{
    	    	String arr_to[]=to.split("<");
    	    	String nto=arr_to[1].replace(">", "");
    	    	nto=nto.trim(); 

    	    	 if(sflg==0)
    	    	  {
    	    		  searchCondition=new OrTerm(new RecipientStringTerm(Message.RecipientType.TO,to), new RecipientStringTerm(Message.RecipientType.TO,nto));
    	    		  sflg=1;
    	    	  }
    	    	  else
    	    	  {
    	    		// RecipientStringTerm sto=new RecipientStringTerm(Message.RecipientType.TO,to);
    	    		  searchCondition = new AndTerm( searchCondition,  new OrTerm(new RecipientStringTerm(Message.RecipientType.TO,to), new RecipientStringTerm(Message.RecipientType.TO,nto)));
    	    	  }
    	 
    	    	}
    		 catch(Exception ee)
    		 {
    			 ee.printStackTrace();

    	    	 if(sflg==0)
    	    	  {
    	    		  searchCondition=new RecipientStringTerm(Message.RecipientType.TO,to);
    	    		  sflg=1;
    	    	  }
    	    	  else
    	    	  {
    	    		 RecipientStringTerm sto=new RecipientStringTerm(Message.RecipientType.TO,to);
    	    		  searchCondition = new AndTerm( searchCondition,  sto);
    	    	  }
    		 }
    	 }
    	 else
    	 {
    	 if(sflg==0)
    	  {
    		  searchCondition=new RecipientStringTerm(Message.RecipientType.TO,to);
    		  sflg=1;
    	  }
    	  else
    	  {
    		 RecipientStringTerm sto=new RecipientStringTerm(Message.RecipientType.TO,to);
    		  searchCondition = new AndTerm( searchCondition,  sto);
    	  }
    	 }	   
 	 }
		
     if(sub!=null && !(sub.equals("")))
     {
   	  if(sflg==0)
    	  {
    		  searchCondition=new  SubjectTerm(sub);
    		  sflg=1;
    	  }
    	  else
    	  {
    		  searchCondition = new AndTerm( searchCondition, new  SubjectTerm(sub));
    	  }
    		   
     }
     
     
     if(endDate!=null && !(sdt.equals("")) && beginDate!=null && !(edt.equals("")))
     {
    	
   	  if(sflg==0)
    	  {
    		  searchCondition= new AndTerm( new ReceivedDateTerm( ComparisonTerm.LE, endDate ), new ReceivedDateTerm( ComparisonTerm.GE, beginDate ) ) ;
    		  sflg=1;
    	  }
    	  else
    	  {
    		  searchCondition = new AndTerm( searchCondition,new AndTerm( new ReceivedDateTerm( ComparisonTerm.LE, endDate ), new ReceivedDateTerm( ComparisonTerm.GE, beginDate ) ));
    		 // searchCondition = new AndTerm( searchCondition, new ReceivedDateTerm( ComparisonTerm.GT, beginDate ));
    	  }
    		   
     }
     
     
     
     if(tagnm!=null && !(tagnm.equals("")))
	 {
   	  Flags processedFlag = new Flags(tagnm);
   	  if(sflg==0)
    	  {
    		  searchCondition=new FlagTerm(processedFlag, true);
    		  sflg=1;
    	  }
    	  else
    	  {
    		  searchCondition = new AndTerm( searchCondition, new FlagTerm(processedFlag, true));
    	  }  
	 }
     
     if(con!=null && !(con.equals("")))
 	 {
    	  if(sflg==0)
     	  {
     		  searchCondition= new BodyTerm(con);
     		  sflg=1;
     	  }
     	  else
     	  {
     		  searchCondition = new AndTerm( searchCondition, new BodyTerm(con));
     	  }  
 	 }
     
     if(act!=null && !(act.equals("")))
     {
   	  boolean st=true;
   	  Flags flg = new Flags(Flags.Flag.SEEN);
   	  if(act.equalsIgnoreCase("Stared") || act.equalsIgnoreCase("Unstared"))
   	  {
   		  flg = new Flags(Flags.Flag.FLAGGED);
   	  }
   	  
   	  if(act.equalsIgnoreCase("Unread") || act.equalsIgnoreCase("Unstared"))
   	  {
   		  st=false;
   	  }
   	  //FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
   	  if(sflg==0)
     	  {
     		  searchCondition= new FlagTerm(flg, st);
     		  sflg=1;
     	  }
     	  else
     	  {
     		  searchCondition = new AndTerm( searchCondition,  new FlagTerm(flg, st));
     	  }  
     }

    /* SearchTerm searchCondition = 
   		   new AndTerm(
   				 new  SubjectTerm(sub), 
   		      new BodyTerm(con)
   		      );*/
 
SortTerm[] term1=new SortTerm[1];
if(folder.getFullName().equalsIgnoreCase("Sent"))
{
	term1[0] = SortTerm.DATE;
}
else
{
	term1[0] = SortTerm.ARRIVAL;
}
 

Message[] sort_msg =folder.getSortedMessages(term1);  
Message[] msg =folder.search(searchCondition, sort_msg)  ;         //(searchCondition);
inboxlist.setSearchCount(msg.length);
//int umsg= folder.getUnreadMessageCount();
for (int i = msg.length-((Integer.parseInt(start))+1),k=0; k< (Integer.parseInt(end)) && i>=0;k++, i--)
 {
	 InboxSearch inb= new InboxSearch();
	 

	
		flg=0;
		
		String tag="";
		String arr[]= msg[i].getFlags().getUserFlags();
		 for(int b=0;b<arr.length;b++)
		  {
			 if(tag.equals(""))
			 {
				 tag=arr[b];
			 }
			 else
			 {
				 tag=tag+"~"+arr[b];
			 }
		  // out.print("<br>user flag="+arr[b]);
		  } 
		inb.setMailTag(tag);
		
		
		Enumeration headers = msg[i].getAllHeaders();
		 while (headers.hasMoreElements()) {
			 Header h = (Header) headers.nextElement();
			 /* out.print("<br>%%%%%%% "+h.getName() + ": " + h.getValue());*/
			  if(h.getName().equalsIgnoreCase("X-Priority"))
			  {
				if( h.getValue().contains("Lowest"))
				{
					inb.setMailPriority("Lowest");
				}
				else if(h.getValue().contains("Highest"))
				{
					inb.setMailPriority("Highest");
				}
				  break;
			  }
			  }
		
		
		 long uid = uf.getUID(msg[i]);
		 inb.setUid(""+uid);
		 Message message = msg[i];
		
		 boolean chkst=	message.isSet(Flags.Flag.FLAGGED);
		 inb.setMailFlage(chkst);
		 
		 boolean chkseen=	message.isSet(Flags.Flag.SEEN);
		 inb.setMailSeen(chkseen);
		 
		 boolean chkdraft= false;
		 chkdraft=message.isSet(Flags.Flag.DRAFT);
		 inb.setMailDraft(chkdraft);
		 
		 String from1 = InternetAddress.toString(msg[i].getFrom());
		 inb.setFromId(from1);
	 
	//System.out.println("inbox repository***from="+from);
	  String replyTo = InternetAddress.toString(msg[i].getReplyTo());
	  if(replyTo==null)
	  {
		  replyTo="";
	  }
	  inb.setReplyId(replyTo);
	//  //System.out.println("inbox repository***replyto="+replyTo);
	  String to1 = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.TO));
	 if(to1==null)
	 {
		 to1="";
	 }
	  inb.setToId(to1);
	  
	  String cc = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.CC));
	  if(cc==null)
	  {
		  cc="";
	  }
	  inb.setCCId(cc);
	  
	  String bcc = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.BCC));
	  if(bcc==null)
	  {
		  bcc="";
	  }
	  inb.setBCCId(bcc);
	//System.out.println("inbox repository***to="+to);
	  String subject = msg[i].getSubject();
	  if(subject==null)
	  {
		  subject="";
	  }
	  inb.setSubject(subject);
	  //System.out.println("inbox repository***sub="+subject);
	  Date sent = msg[i].getReceivedDate();
	  //System.out.println("inbox repository***date="+sent);
	  SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
	  SimpleDateFormat format1 = new SimpleDateFormat("E, MMM dd, yyyy");
	  SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a"); 
	  SimpleDateFormat format3 = new SimpleDateFormat("MMM dd"); 
	  SimpleDateFormat format4 = new SimpleDateFormat("dd/MM/yyyy"); 
	  String dip_dt="";
	  Date cdt=new Date();
	  String dt1=format0.format(cdt);
	  String dt2=format0.format(sent);
	  
	  SimpleDateFormat df = new SimpleDateFormat("yyyy");
	  String year = df.format(cdt);
	  String year1 = df.format(sent);
	  
	  if(dt1.equalsIgnoreCase(dt2))
	  {
	 	  dip_dt=format2.format(sent);
	  }
	  else
	  {
	 	 if(year.equalsIgnoreCase(year1))
	 	 {
	 	  dip_dt=format3.format(sent);
	 	 }
	 	 else
	 	 {
	 		 dip_dt=format4.format(sent);
	 	 }
	  }
	  
	  String ttl_dt=format1.format(sent)+" at "+format2.format(sent);
	  //System.out.println("inbox repository*** tit date="+ttl_dt);
	  inb.setSendDtae(dip_dt);
	  inb.setSendDtaeTitle(ttl_dt);
	 
	  Object content = msg[i].getContent();  

	  
	  /*Message message1 = msg[i];
	  writePart(message1);
	  if(mail_cont==null || mail_cont.equals(""))
	  {
		  if(mail_cont.length()>170)
		  {
		    mail_cont=mail_cont.substring(0, 169)+"....";
		  }
		 
	  }
	 writePart_otr(message1);
	  inb.setMailContent(mail_cont);
	  inb.setMailContentOtr(mail_cont_otr);
	 
	  mail_cont="";
	  mail_cont_otr="";*/
	  String attch="No";
	 
	  
	  if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
	  {
		  mail_cont=msg[i].getContent().toString();
	  	  
	  }
	  else
	  {
	if (content instanceof String)  
	{  
	    String body = (String)content;  
	    attch="No";
	}  
	else if (content instanceof Multipart)  
	{  
	    Multipart multipart = (Multipart)content;  
	    
	   List<String> attflnm=new ArrayList();
	   List<String> attflsize=new ArrayList();
	   for (int j = 0; j < multipart.getCount(); j++) {

	    BodyPart bodyPart = multipart.getBodyPart(j);
	        
	       
	        
	        String disposition = bodyPart.getDisposition();

	        if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
         	{ 
       	  attch="Yes";
       	  DataHandler handler = bodyPart.getDataHandler();
             attflnm.add( handler.getName());  
             attflsize.add(""+bodyPart.getSize());
           }
         else 
         	{ 
             String cnt=bodyPart.getContent().toString();
             if(mail_cont==null || mail_cont.equals(""))
             {
         	 
         		mail_cont=cnt;
         	  
             }
           }
	   
	  }
	    inb.getAttachmentName().addAll(attflnm);
	    inb.getAttachmentSize().addAll(attflsize);
	  }
	  }
	  
	  mail_cont = mail_cont.replaceAll("\\n", "");
	  mail_cont = mail_cont.replaceAll("\\r", "");
	  mail_cont = mail_cont.replaceAll("\\<.*?\\>", "");
	  
	  if(mail_cont==null || mail_cont.equals(""))
	  {
		  if(mail_cont.length()>170)
		  {
		    mail_cont=mail_cont.substring(0, 169)+"....";
		  }
		 
	  }
	  
	  if(mail_cont.startsWith("javax.mail.internet.MimeMultipart"))
	  {
		  mail_cont="This mail has attachment or Inline Image";  
	  }
	  inb.setMailContent("<pre>"+mail_cont.trim()+"</pre>");
	  inb.setMailContentOtr(mail_cont_otr.trim());
	 
	  mail_cont="";
	  mail_cont_otr="";
	  
	  
	  //System.out.println("inbox repository***attch="+attch);
	  inb.setAttachment(attch);
	
	  
	  inboxmail.getInboxMailListSearch().add(inb) ;
 }

 folder.close(true);
 store.close();
 }
 catch(MessagingException e)
       {
	  e.printStackTrace();
	  	status=false;
       }
       
catch(Exception e)
       {
	 e.printStackTrace();
		status=false;
       }
       
      inboxlist.setInboxListReturnSearch(inboxmail);
      inboxlist.setSuccess(status);
      response.setGetInboxByMailLimitSearch(inboxlist);                  
 return   response;        
}
	
	
	
	public String countAllMailSearchQck(String host, String port, String id, String pass,  String foldername, String quickval)
	{
		String res="0";
		String to_from="";
		String nto="";
		String nfrom="";
		String sbjt="";
		if(quickval.contains("@"))
		{
			to_from=quickval;
			try
			{
			if(to_from.contains("<") && to_from.contains(">"))
			{
				String arr_tf[]=to_from.split("<");
				nto=arr_tf[1].replace(">", "");
				nto=nto.trim();
				nfrom=nto;
			}
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
		else
		{
			sbjt=quickval;
		}
		
		final String to= to_from;
		final String from= to_from;
		final String sub= sbjt;
		
		
		
		boolean status=true;		
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
   	  return "0";        
 		}
     folder.open(Folder.READ_ONLY);
 
 
     SearchTerm searchCondition =null; 
     if(sbjt!=null && !sbjt.equals(""))
     {
   	  searchCondition= new  SubjectTerm(sub);
     }
     else
     {
    	 if(nto!=null && nto.length()>0 && nfrom!=null && nfrom.length()>0)
    	 {
    		 searchCondition= new OrTerm( new OrTerm( new FromStringTerm(from),  new FromStringTerm(nfrom)), new OrTerm(new RecipientStringTerm(Message.RecipientType.TO,to), new RecipientStringTerm(Message.RecipientType.TO,nto)));
    	 }
    	 else
    	 {
    		 searchCondition= new OrTerm(  new FromStringTerm(from),   new RecipientStringTerm(Message.RecipientType.TO,to));
    	 }
     }
 
/* SortTerm[] term1=new SortTerm[1];
 if(folder.getFullName().equalsIgnoreCase("Sent"))
 {
	term1[0] = SortTerm.DATE;
}
 else
 {
	term1[0] = SortTerm.ARRIVAL;
 }
  

 Message[] sort_msg =folder.getSortedMessages(term1);  */
 Message[] msg =folder.search(searchCondition)  ;
 res=""+msg.length;

       }
       catch(Exception e)
       {
    	   e.printStackTrace();
    	   res="0";
       }
       try {
		folder.close(true);
		store.close();
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       
 return res;
	}
	
	
	public GetMailInboxQuickSearchResponse listWebmailQuickSearch(String host, String port, String id, String pass, String start, String end, String foldername, String quickval)
	{
		
		GetMailInboxQuickSearchResponse response= new GetMailInboxQuickSearchResponse();
		String to_from="";
		String sbjt="";
		String nto="";
		String nfrom="";
		if(quickval.contains("@"))
		{
			to_from=quickval;
			try
			{
			if(to_from.contains("<") && to_from.contains(">"))
			{
				String arr_tf[]=to_from.split("<");
				nto=arr_tf[1].replace(">", "");
				nto=nto.trim();
				nfrom=nto;
			}
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
		else
		{
			sbjt=quickval;
		}
		
		final String to= to_from;
		final String from= to_from;
		final String sub= sbjt;
		
		
		InboxListReturnQuickSearch inboxlist= new InboxListReturnQuickSearch();
		ArrayOfInboxMailQuickSearch inboxmail= new ArrayOfInboxMailQuickSearch();
		boolean status=true;		
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
   	  inboxlist.setInboxListReturnQuickSearch(inboxmail);
   	  inboxlist.setQuickSuccess(false);
   	  inboxlist.setQuickCount(0);
   	response.setGetInboxByMailLimitQuickSearch(inboxlist);
   	  return   response;        
 		}
     folder.open(Folder.READ_ONLY);
 
 /*
 
 // creates a search criterion
 SearchTerm searchCondition = new SearchTerm() {
     @Override
     public boolean match(Message message) {
         try {
       	  boolean st=false;
       	               
       	  if(from!=null && !(from.equals("")))
	        	 {
       		  Address[] fromAddress = message.getFrom();
	        	 boolean st_frm=false;
	              for (int i=0;i<fromAddress.length;i++ ) {
	                  if (fromAddress[i].toString().contains(from)  ) {
	                	 
	                	  return true;
	                  } 
	              }
	              
	        	 }
       	 
       	  
       	  if(to!=null && !(to.equals("")))
	        	 {
       		  Address[] toAddress = message.getAllRecipients();
	        	 boolean st_to=false;
	              for (int i=0;i<toAddress.length;i++ ) {
	                  if (toAddress[i].toString().contains(to)  ) {
	                	  
	                	  return true;
	                  } 
	              }
	             
	        	 }
       	  
       	  
       	 
       	 
       	 if(sub!=null && !(sub.equals("")))
       	 {
       		 if(message.getSubject()!=null)
       		 {
	        	  if (message.getSubject().contains(sub) ) {
	        		  return true;
	              }
       		 }
       	 }
       	 
       	
       	 return st;
       	 
         } catch (MessagingException ex) {
             ex.printStackTrace();
         }
         catch (Exception ex) {
             ex.printStackTrace();
         }
         return false;
     }
 };
 

 */
     SearchTerm searchCondition =null; 
     if(sbjt!=null && !sbjt.equals(""))
     {
   	  searchCondition= new  SubjectTerm(sub);
     }
     else
     {
    	 if(nto!=null && nto.length()>0 && nfrom!=null && nfrom.length()>0)
    	 {
    		 searchCondition= new OrTerm( new OrTerm( new FromStringTerm(from),  new FromStringTerm(nfrom)), new OrTerm(new RecipientStringTerm(Message.RecipientType.TO,to), new RecipientStringTerm(Message.RecipientType.TO,nto)));
    	 }
    	 else
    	 {
    	 searchCondition= new OrTerm(  new FromStringTerm(from),   new RecipientStringTerm(Message.RecipientType.TO,to));
    	 }
     }
 
 SortTerm[] term1=new SortTerm[1];
 if(folder.getFullName().equalsIgnoreCase("Sent"))
 {
	term1[0] = SortTerm.DATE;
}
 else
 {
	term1[0] = SortTerm.ARRIVAL;
 }
  

 Message[] sort_msg =folder.getSortedMessages(term1);  
 Message[] msg =folder.search(searchCondition, sort_msg)  ;
inboxlist.setQuickCount(msg.length);
int umsg= folder.getUnreadMessageCount();
for (int i = msg.length-((Integer.parseInt(start))+1),k=0; k< (Integer.parseInt(end)) && i>=0;k++, i--)
 {
	 InboxQuickSearch inb= new InboxQuickSearch();
	 

	
		flg=0;

		String tag="";
		String arr[]= msg[i].getFlags().getUserFlags();
		 for(int b=0;b<arr.length;b++)
		  {
			 if(tag.equals(""))
			 {
				 tag=arr[b];
			 }
			 else
			 {
				 tag=tag+"~"+arr[b];
			 }
		  // out.print("<br>user flag="+arr[b]);
		  } 
		inb.setMailTag(tag);
		
		
		Enumeration headers = msg[i].getAllHeaders();
		 while (headers.hasMoreElements()) {
			 Header h = (Header) headers.nextElement();
			 /* out.print("<br>%%%%%%% "+h.getName() + ": " + h.getValue());*/
			  if(h.getName().equalsIgnoreCase("X-Priority"))
			  {
				if( h.getValue().contains("Lowest"))
				{
					inb.setMailPriority("Lowest");
				}
				else if(h.getValue().contains("Highest"))
				{
					inb.setMailPriority("Highest");
				}
				  break;
			  }
			  }
		
		
		 long uid = uf.getUID(msg[i]);
		 inb.setUid(""+uid);
		 
		// System.out.println("uid:-"+uid+"-----msgno:-"+msg[i].getMessageNumber());
		 Message message = msg[i];
		
		 boolean chkst=	message.isSet(Flags.Flag.FLAGGED);
		 inb.setMailFlage(chkst);
		 
		 boolean chkseen=	message.isSet(Flags.Flag.SEEN);
		 inb.setMailSeen(chkseen);
		 
		 boolean chkdraft=false;
		 chkdraft=message.isSet(Flags.Flag.DRAFT);
		 inb.setMailDraft(chkdraft);
		 
		 String from1 = InternetAddress.toString(msg[i].getFrom());
		 inb.setFromId(from1);
	 
	//System.out.println("inbox repository***from="+from);
	  String replyTo = InternetAddress.toString(msg[i].getReplyTo());
	  if(replyTo==null)
	  {
		  replyTo="";
	  }
	  inb.setReplyId(replyTo);
	//  //System.out.println("inbox repository***replyto="+replyTo);
	  String to1 = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.TO));
	 if(to1==null)
	 {
		 to1="";
	 }
	  inb.setToId(to1);
	  
	  String cc = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.CC));
	  if(cc==null)
	  {
		  cc="";
	  }
	  inb.setCCId(cc);
	  
	  String bcc = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.BCC));
	  if(bcc==null)
	  {
		  bcc="";
	  }
	  inb.setBCCId(bcc);
	//System.out.println("inbox repository***to="+to);
	  String subject = msg[i].getSubject();
	  if(subject==null)
	  {
		  subject="";
	  }
	  inb.setSubject(subject);
	  //System.out.println("inbox repository***sub="+subject);
	  Date sent = msg[i].getReceivedDate();
	  //System.out.println("inbox repository***date="+sent);
	  SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
	  SimpleDateFormat format1 = new SimpleDateFormat("E, MMM dd, yyyy");
	  SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a"); 
	  SimpleDateFormat format3 = new SimpleDateFormat("MMM dd"); 
	  SimpleDateFormat format4 = new SimpleDateFormat("dd/MM/yyyy"); 
	  String dip_dt="";
	  Date cdt=new Date();
	  String dt1=format0.format(cdt);
	  String dt2=format0.format(sent);
	  
	  SimpleDateFormat df = new SimpleDateFormat("yyyy");
	  String year = df.format(cdt);
	  String year1 = df.format(sent);
	  
	  if(dt1.equalsIgnoreCase(dt2))
	  {
	 	  dip_dt=format2.format(sent);
	  }
	  else
	  {
	 	 if(year.equalsIgnoreCase(year1))
	 	 {
	 	  dip_dt=format3.format(sent);
	 	 }
	 	 else
	 	 {
	 		 dip_dt=format4.format(sent);
	 	 }
	  }
	  
	  String ttl_dt=format1.format(sent)+" at "+format2.format(sent);
	  //System.out.println("inbox repository*** tit date="+ttl_dt);
	  inb.setSendDtae(dip_dt);
	  inb.setSendDtaeTitle(ttl_dt);
	 
	  Object content = msg[i].getContent();  

	  
	  /*Message message1 = msg[i];
	  writePart(message1);
	  if(mail_cont==null || mail_cont.equals(""))
	  {
		  if(mail_cont.length()>170)
		  {
		    mail_cont=mail_cont.substring(0, 169)+"....";
		  }
		 
	  }
	 writePart_otr(message1);
	  inb.setMailContent(mail_cont);
	  inb.setMailContentOtr(mail_cont_otr);
	 
	  mail_cont="";
	  mail_cont_otr="";*/
	  String attch="No";
	 
	  
	  if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
	  {
		  mail_cont=msg[i].getContent().toString();
	  	  
	  }
	  else
	  {
	if (content instanceof String)  
	{  
	    String body = (String)content;  
	    attch="No";
	}  
	else if (content instanceof Multipart)  
	{  
	    Multipart multipart = (Multipart)content;  
	    
	   List<String> attflnm=new ArrayList();
	   List<String> attflsize=new ArrayList();
	   for (int j = 0; j < multipart.getCount(); j++) {

	    BodyPart bodyPart = multipart.getBodyPart(j);
	        
	       
	        
	        String disposition = bodyPart.getDisposition();

	        if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
         	{ 
       	  attch="Yes";
       	  DataHandler handler = bodyPart.getDataHandler();
             attflnm.add( handler.getName());  
             attflsize.add(""+bodyPart.getSize());
           }
         else 
         	{ 
             String cnt=bodyPart.getContent().toString();
             if(mail_cont==null || mail_cont.equals(""))
             {
         	 
         		mail_cont=cnt;
         	  
             }
           }
	   
	  }
	    inb.getAttachmentName().addAll(attflnm);
	    inb.getAttachmentSize().addAll(attflsize);
	  }
	  }
	  
	  mail_cont = mail_cont.replaceAll("\\n", "");
	  mail_cont = mail_cont.replaceAll("\\r", "");
	  mail_cont = mail_cont.replaceAll("\\<.*?\\>", "");
	  
	  if(mail_cont==null || mail_cont.equals(""))
	  {
		  if(mail_cont.length()>170)
		  {
		    mail_cont=mail_cont.substring(0, 169)+"....";
		  }
		 
	  }
	  
	  if(mail_cont.startsWith("javax.mail.internet.MimeMultipart"))
	  {
		  mail_cont="This mail has attachment or Inline Image";  
	  }
	  inb.setMailContent("<pre>"+mail_cont.trim()+"</pre>");
	  inb.setMailContentOtr(mail_cont_otr.trim());
	 
	  mail_cont="";
	  mail_cont_otr="";
	  
	  
	  //System.out.println("inbox repository***attch="+attch);
	  inb.setAttachment(attch);
	 
	  
	  inboxmail.getInboxMailListQuickSearch().add(inb);   
 }

 folder.close(true);
 store.close();
 }
 catch(MessagingException e)
       {
	  e.printStackTrace();
	  	status=false;
       }
       
catch(Exception e)
       {
	 e.printStackTrace();
		status=false;
       }
       
      inboxlist.setInboxListReturnQuickSearch(inboxmail);
      inboxlist.setQuickSuccess(status);
      response.setGetInboxByMailLimitQuickSearch(inboxlist);         
 return   response;        
}
	
	
	
	
	
	
	
	
	
	
	

	  public  void writePart(Part p) throws Exception {
	      if (p instanceof Message)
	    	  p.getContentType();
	      if (p.isMimeType("text/plain")) {
	    	  
	         mail_cont=(String) p.getContent();
	    	 
	      } 
	      else if (p.isMimeType("text/html")) {
	    	  if(mail_cont==null || mail_cont.equals(""))
	    	  {
		       mail_cont=(String) p.getContent();
	    	  }
	    	
		      } 
	      else if (p.isMimeType("multipart/*")) {
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	            writePart(mp.getBodyPart(i));
	      } 
	      else if (p.isMimeType("message/rfc822")) {
	         writePart((Part) p.getContent());
	      } 
	  }
	

	  
	  
	  

	  public  void writePartSearch(Part p) throws Exception {
	      if (p instanceof Message)
	    	  p.getContentType();
	      if (p.isMimeType("text/plain")) {
	    	  
	         mail_cont_s=(String) p.getContent();
	    	 
	      } 
	      else if (p.isMimeType("text/html")) {
	    	  if(mail_cont_s==null || mail_cont_s.equals(""))
	    	  {
	    		  mail_cont_s=(String) p.getContent();
	    	  }
	    	
		      } 
	      else if (p.isMimeType("multipart/*")) {
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	        	 writePartSearch(mp.getBodyPart(i));
	      } 
	      else if (p.isMimeType("message/rfc822")) {
	    	  writePartSearch((Part) p.getContent());
	      } 
	  }
	  
	  
	  
	  
	  public  void writePart_otr(Part p) throws Exception {
	      if (p instanceof Message)
	         //Call methos writeEnvelope
	    //     writeEnvelope((Message) p);

	     // System.out.println("----------------------------");
	    	  p.getContentType();
	     // System.out.println("CONTENT-TYPE: " + p.getContentType());

	      //check if the content is plain text
	      if (p.isMimeType("text/plain")) {
	    	  if(flg==0)
	    	  {
	    		  flg=1;
	    	  }
	    	  if(flg!=2)
	    	  {
	        // System.out.println("This is plain text");
	        // System.out.println("---------------------------flg="+flg);
	         //System.out.println((String) p.getContent());
	         mail_cont_otr=(String) p.getContent();
	    	  }
	      } 
	      else if (p.isMimeType("text/html")) {
	    	  
	    	  if(flg!=2)
	    	  {
		       // System.out.println("This is plain text");
		      //  System.out.println("---------------------------flg="+flg);
		      //   System.out.println((String) p.getContent());
		         mail_cont_otr=(String) p.getContent();
	    	  }
	    	  flg=2;
		      } 
	      //check if the content has attachment
	      else if (p.isMimeType("multipart/*")) {
	        // System.out.println("This is a Multipart");
	        // System.out.println("---------------------------");
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	        	 writePart_otr(mp.getBodyPart(i));
	      } 
	      //check if the content is a nested message
	      else if (p.isMimeType("message/rfc822")) {
	        // System.out.println("This is a Nested Message");
	        // System.out.println("---------------------------");
	         writePart_otr((Part) p.getContent());
	      } 
	 

	}
	  
	  
	
}
