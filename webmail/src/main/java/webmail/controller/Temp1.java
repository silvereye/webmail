package webmail.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.HasControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import javax.naming.ldap.SortResponseControl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import webmail.webservice.client.WebmailClient;
import webmail.wsdl.GetWebmailFolderOtherResponse;
import webmail.wsdl.MailImapFolders;
 
/**
 * This program demonstrates how to search for e-mail messages which satisfy
 * a search criterion.
 * @author www.codejava.net
 *
 */
public class Temp1 {
	@Autowired
	private static WebmailClient webmailClient;
    /**
     * Searches for e-mail messages containing the specified keyword in
     * Subject field.
     * @param host
     * @param port
     * @param userName
     * @param password
     * @param keyword
     */

    /**
     * Test this program with a Gmail's account
     */
   public static void main(String[] args) {
	   

	   //System.out.println('!!!!!!!!!!!!!!!!!!!!seting jsp');
	   //WebmailClient webmailClient=(WebmailClient) request.getAttribute('webmailClient');
	 //  List<MailImapFolders> imapfldrlst=(List<MailImapFolders>) request.getAttribute('imapfldrlst');
	   String inboxcnt="20";  //(String)request.getAttribute('in_unread_cnt');
	  	//HttpSession hs=request.getSession();
	  	String host= "mail.storkrubber.com"; //(String)hs.getAttribute('host');
	  	String id= "arangar@storkrubber.com"   ;  //(String)hs.getAttribute('id');
	  	String pass= "arjan@259";      //(String)hs.getAttribute('pass');

	     
	  	GetWebmailFolderOtherResponse sfres=webmailClient.getWebmailFolderOtherRequest(host, id, pass, "");
	 	List<MailImapFolders> imapfldrlst= sfres.getGetSubFolder().getMailFolderListReturn().getMailFolderList();
	       

	

	     System.out.println("  <div class='leftfldr'> <ul >");



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
	    					//String sps='';
	    					/* for(int j=1;j<l;j++)
	    					{
	    						sps+='&nbsp;&nbsp;&nbsp;&nbsp;';
	    					} */
	    					//out.print('<br>'+sps+arr[l-1]+'-----'+l);
	    					
	    					 if(!list1.get(i).isHasChild() && !list1.get(i).isIsSubs())
	    					 {
	    						 
	    					 }
	    					 else
	    					 {
	    					 
	    	  			  if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
	    	  			  {
	    	  			 
	    	  				System.out.println(" <li><div id='fldr_INBOX' class='left_partent_tree active_left_tree'>");
	    	  		}
	    	  			  else
	    	  			  {
	    	  			
	    	  				System.out.println("<li><div id='fldr_<%=list1.get(i).getFolderFullName().replaceAll(' ', '_') %>' class='left_partent_tree'>");
	    	  	  		
	    	  			  }
	    	  	  		if(list1.get(i).isHasChild() )
	    	  	  		{
	    	  	  			if(list1.get(i).isIsSubs())
	    	  	  			{
	    	  	  			
	    	  	  			
	    	  	  			System.out.println("<div class='child_level'></div> ");
	    	  	  			if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
	    	          		{
	    	          			
	    	  	  			System.out.print("<span id='<%=nm %>'  style='cursor: pointer;' onclick='getWebmailInbox(this.id)'>");
	    	  	  		System.out.print("list1.get(i).getFolderName() ");
	    	  	  	System.out.print("<span id='unread_inbox'>"+inboxcnt+" </span>");
	    	  	  System.out.print("	 </span>");
	    	        	  } 
	    	        	    else
	    	        	    {
	    	        	    	 System.out.println("  <span id='<%=nm %>' style='cursor: pointer;' onclick='getWebmailInbox(this.id)'>");
	    	        	    		 System.out.print("list1.get(i).getFolderName() ");
	    	        	    	 System.out.print(" </span>");
	    	        	    
	    	        	    }
	    	  	  			}
	    	  	  			else
	    	  	  			{
	    	  	  				
	    	  	  			 System.out.println("	<div class='child_level'></div> ");
	    	  	  			
	    	  	  			if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
	    	          		{
	    	          			
	    	  	  			 System.out.println("<span id='<%=nm %>'  style='cursor: pointer;' onclick='getWebmailInbox(this.id)'>");
	    	  	  		 System.out.print("list1.get(i).getFolderName() ");
	    	  	  	 System.out.print("<span id='unread_inbox'>"+inboxcnt +" </span>");
	    	  	   System.out.print("	 </span>");
	    	        	   } 
	    	        	    else
	    	        	    {
	    	  	  			
	    	        	    	 System.out.print("<span id='<%=nm %>' class='inactive_fldr'>");
	    	        	    	 System.out.print("list1.get(i).getFolderName()");
	    	        	    	 System.out.print(" </span>");
	    	        	   
	    	        	    }
	    	  	  			}
	    	  	  		}
	    	  	  		else
	    	  	  		{
	    	  	  		
	    	  	  		 System.out.println("<div class='child_blank'></div>");
	    	  	  			if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
	    	          		{
	    	          			
	    	  	  			 System.out.println("<span id='<%=nm %>' style='cursor: pointer;'  onclick='getWebmailInbox(this.id)'>");
	    	  	  		 System.out.print("list1.get(i).getFolderName() ");
	    	  	  	 System.out.print("<span id='unread_inbox'><%=inboxcnt %> </span>");
	    	  	   System.out.print("	    </span>");
	    	        	   }
	    	  	  			else
	    	  	  			{
	    	  	  			
	    	  	  			 System.out.println("<span id='<%=nm %>' style='cursor: pointer;' onclick='getWebmailInbox(this.id)'>");
	    	  	  		 System.out.print("list1.get(i).getFolderName() ");
	    	  	  	 System.out.print("	</span>");
	    	  	  			}
	    	  	  			} 
	    	  	  	 System.out.print("	</div>");
	    	  	  			
	    					 }
	    	  	  			if(i+1 != list1.size())
	    	  	  			{
	    	  	  				String nm1=list1.get(i+1).getFolderFullName();
	    	  	  			String arr1[]=nm1.split("\\.");
	    	  	  			int l1=arr1.length;
	    	  	  			if(l<l1)
	    	  	  			{
	    	  	  			System.out.println("<ul class='level'>");
	    	  	  			}
	    	  	  			else if(l==l1)
	    	  	  			{
	    	  	  			System.out.println("</li>");
	    	  	  			}
	    	  	  			else
	    	  	  			{
	    	  	  			System.out.println("</li></ul>");
	    	  	  			}
	    	  	  			}
	    	  	  			else
	    	  	  			{
	    	  	  				
	    	  	  				 if(i>0)
	    	  	  				{
	    	  	  				for(;l>1;l--)
	    	  	  				{
	    	  	  				System.out.println("</li></ul>");
	    	  	  				}
	    	  	  				}
	    	  	  				
	    	  	  				System.out.println("</li>");
	    	  	  				
	    	  	  			}
	    				}
	    				
	    				//out.print('<br>'+lFiles(list, fd.getFullName()));
	    			
	    			}
	    			else
	    			{
	    				
	    				//out.print('<br>'+fd.getFullName());
	    			
	    	  		
	    				System.out.println("<li>");
	    	  			  
	    	  			  if(fd.getFolderFullName().equalsIgnoreCase("INBOX"))
	    	  			  {
	    	  			 
	    	  				System.out.println(" <div id='fldr_INBOX' class='left_partent_tree active_left_tree'>");
	    	  				System.out.println(" <div class='child_blank'></div> ");
	    	  				System.out.println("  <span id='<%=fd.getFolderFullName() %>'   style='cursor: pointer;' onclick='getWebmailInbox(this.id)'>");
	    	  				System.out.print("fd.getFolderFullName()  ");
	    	  				System.out.print("<span id='unread_inbox'><%=inboxcnt %> </span>");
	    	  				System.out.print("</span></div>");
	    	  			  }
	    	  			  else  if(fd.getFolderFullName().equalsIgnoreCase("DRAFTS"))
	    	  			  {
	    	  				 int dcnt= fd.getMessageCount();
	    	  				 String drtcnt="";
	    	  				 if(dcnt>0)
	    	  				 {
	    	  					drtcnt="("+dcnt+")";
	    	  				 }
	    	  		
	    	  				System.out.println("  <div id='fldr_<%=fd.getFolderFullName().replaceAll(' ', '_') %>' class='left_partent_tree '>");
	    	  				System.out.println(" <div class='child_blank'></div>"); 
	    	  				System.out.println(" <span id='<%=fd.getFolderFullName() %>'   style='cursor: pointer;' onclick='getWebmailInbox(this.id)'>");
	    	  				System.out.print("fd.getFolderFullName() ");
	    	  				System.out.print("<span id='draft_mail_cnt'><%=drtcnt %> </span>");
	    	  				System.out.print("	</span></div>");
	    	  			}
	    	  			  else
	    	  			  {
	    	  				if(fd.isIsSubs())
	    	  				{
	    	  			 
	    	  					System.out.println("  <div id='fldr_<%=fd.getFolderFullName().replaceAll(' ', '_') %>' class='left_partent_tree'>");
	    	  					System.out.println(" <div class='child_blank'></div> ");
	    	  						System.out.print(" <span id='<%=fd.getFolderFullName() %>' style='cursor: pointer;' onclick='getWebmailInbox(this.id)'>");
	    	  					System.out.print("fd.getFolderFullName()  </span></div>");
	    	  			 }
	    	  			  
	    	  				System.out.print(" </li>");
	    	  			
	    				}
	    			}
	    			}
	    		  
	    		

	  
	    		  System.out.println("</ul></div>");
	   
	   
	   
	   
	   
	   
	  /* 
	   String text = "D:\\contacts.vcf";
       // put destination directory here - 
       String str = "D:\\np1";
       
       // this line would have to be changed for java File.
       //String[] strArrays = Files.readAllLines(text);
       List<String> lines=null;
	try {
		lines = IOUtils.readLines(new FileInputStream(text));
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	if(lines!=null)
	{
       String[] strArrays =lines.toArray(new String[lines.size()]); 
    		   
       String empty = "";
       String empty1 = "";
       boolean flag = false;
       boolean flag1 = false;
       int num = 0;
       
       String[] strArrays1 = strArrays;
       String name =""; // String.Empty;
       for (int i = 0; i < (int)strArrays1.length; i++)
       {
           String str1 = strArrays1[i];
           //this can be made case insensitive comparison
           if (str1.equals("BEGIN:VCARD"))
           {
               flag = true;
           }
           if (str1.equals("END:VCARD"))
           {
               flag1 = true;
           }
           if (str1.startsWith("FN:"))
           {
               // put java equivalent of concat
              // name = String.Concat(str1.substring(3), ".vcf");
        	   name = str1.substring(3)+ ".vcf";
           }
           if (str1.toLowerCase().startsWith("EMAIL;".toLowerCase()))
           {
               // make java specific changes. need to split emailSplits variable on 2 chars ';' and ':'
            //   String[] emailSplits = str1.split(new char[] { ';', ':' });
        	   String[] emailSplits = str1.split(":");
               empty1 = (emailSplits != null && emailSplits.length >= 2) ? emailSplits[emailSplits.length-1] : "";
           }
           if (flag)
           {
               // make java specific changes. Environment.NewLine is equivalent to '\n'
               //empty = String.Concat(empty, Environment.NewLine, str1);
        	   empty = empty+"\n"+ str1;
           }
           if (flag1)
           {
               
              // empty1 = String.IsNullOrEmpty(empty1) ? name : empty1 + "_stork.vcf";
               empty1 = (empty1==null || empty1.equalsIgnoreCase("")) ? name : empty1 + "_id.vcf";
               // make java specific changes - writes all lines to file
            //   File.WriteAllText(Path.Combine(str, empty1), empty);
               InputStream is = new ByteArrayInputStream(empty.getBytes());
               try {
				FileUtils.writeStringToFile(new File(str+ "/"+empty1), empty);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               
               flag = false;
               flag1 = false;
               empty = "";
               num++;
           }
           
       }
	}*/
   }
   
   public static List<MailImapFolders> lFiles(List<MailImapFolders> list, String path, String host, String id, String pass) {
    	 
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

 
}