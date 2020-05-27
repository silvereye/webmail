<%@page import="webmail.wsdl.MailImapFolders"%>
<%@page import="webmail.wsdl.GetWebmailFolderOtherResponse"%>
<%@page import="webmail.wsdl.GetWebmailFolderSubscribedOtherResponse"%>
<%@page import="webmail.wsdl.SubsImapSubFolders"%>
<%@page import="webmail.wsdl.GetWebmailSubFolderSubscribedOtherResponse"%>
<%@page import="webmail.wsdl.SubsImapFolders"%>
<%@page import="webmail.webservice.client.WebmailClient"%>
<%@ page language="java" import="java.util.*,
 javax.activation.DataHandler,
 javax.mail.*,
 javax.mail.internet.*,java.io.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'folders.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

       
    <!--  <script>var runOnLoad=function(c,o,d,e){function x(){for(e=1;c.length;)c.shift()()}o[d]?(document[d]('DOMContentLoaded',x,0),o[d]('load',x,0)):o.attachEvent('onload',x);return function(t){e?o.setTimeout(t,0):c.push(t)}}([],window,'addEventListener');</script>
    <link rel="stylesheet" href="css/treefolder.css" type="text/css">

    <script type="text/javascript" src="js/treefolder.js"></script>
    <script type="text/javascript">

      runOnLoad(function(){ CollapsibleLists.apply(); });

    </script> -->
<style>
/*  .left_partent_tree {
  font-size: 12px;
  background: #eee;
  border-left: 3px solid #f00;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}  */
.left_partent_tree:hover {
  font-size: 12px;
  background: #eee;
  border-left: 3px solid #f00;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}
.left_partent_tree {
  font-size: 12px;
  background: #fff;
  border-left: 3px solid #fff;
  border-top: 1px solid #fff;
  border-bottom: 1px solid #fff;
  padding: -1px;
  height: 23px;
/*   text-transform: capitalize; */
}

.child_level {
  width: 12px;
  height: 11px;
  background: #f00;
  float: left;
  margin-right: 10px;
  background: url(images/button-closed.png);
  background-repeat: no-repeat;
  margin-left: 10px;
  margin-top: 6px;
  font-size: 12px;
  cursor: pointer;
}
.level{display: none;} 

.child_blank {
  width: 12px;
  height: 11px;
  /* background: #f00; */
  float: left;
  margin-right: 10px;
  /* background: url(images/button-closed.png); */
  background-repeat: no-repeat;
  margin-left: 10px;
  margin-top: 6px;
  font-size: 12px;
}
.active_left_tree {
  font-size: 12px;
  background: #f1f1f1;
  border-left: 3px solid #f00;
/*   border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc; */
}
.left_partent_tree {
  width: 187px;
  float: left;
}
.left_partent_tree >span {
  line-height: 25px;
  width: 110px;
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  float: left;
}
.minus{background: url('images/button-open.png');}
.leftfldr >ul {
  margin: 0px;
  padding: 0px;
  list-style-type: none;
}
/* ul.level {
  list-style-type: none;
} */
ul.level {
 list-style-type: none;
 padding: 0px;
 padding-left: 10px;
}
.inactive_fldr
{
  color: #d0d0d0;
}
</style>
  
  </head>
  
  <body >
 <%
 //System.out.println("!!!!!!!!!!!!!!!!!!!!seting jsp");
 WebmailClient webmailClient=(WebmailClient) request.getAttribute("webmailClient");
 List<MailImapFolders> imapfldrlst=(List<MailImapFolders>) request.getAttribute("imapfldrlst");
 String inboxcnt=(String)request.getAttribute("in_unread_cnt");
	HttpSession hs=request.getSession();
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
 %>
   
     
     
     <%!
public List<MailImapFolders> lFiles(List<MailImapFolders> list, String path, String host, String id, String pass, WebmailClient webmailClient) {
    	 
    	// GetWebmailFolderSubscribedOtherResponse sfres=webmailClient.getWebmailFolderSubscribedOtherRequest(host, id, pass, path);
 	 	// List<SubsImapFolders> sflst= sfres.getGetSubFolder().getSubsFolderListReturn().getSubsFolderList();
    	 
 	 	GetWebmailFolderOtherResponse sfres=webmailClient.getWebmailFolderOtherRequest(host, id, pass, path);
	 	List<MailImapFolders> sflst= sfres.getGetSubFolder().getMailFolderListReturn().getMailFolderList();
 	 	 
 	 	 
    	         for(MailImapFolders fd:sflst)
    	  			{
    	        	 list.add(fd);
    	        	if(fd.isHasChild())
    	   			{
    	   				
    	   				lFiles(list, fd.getFolderFullName(),host, id, pass,  webmailClient);
    	   			}
    	   			else
    	   			{
    	   			
    	   			}
    	   			
    	        	 
    	        	 
    	  			}
    	         
    	         
    
    return list;
} 
%>

     <div class="leftfldr"> <ul >
<%


  		  for(MailImapFolders fd : imapfldrlst)
  			{
  			List<MailImapFolders> list=new ArrayList(); 
  			 list.add(fd);
  			String cmenuCSS="context-menu-other";
  			if(fd.isHasChild())
  			{
  				List<MailImapFolders> list1=lFiles(list, fd.getFolderFullName(), host, id, pass, webmailClient);
  				int flag=1;
  				for(int i=0;i<list1.size();i++)
  				{
  					String nm=list1.get(i).getFolderFullName();
  					if(nm.equalsIgnoreCase("Junk") || nm.equalsIgnoreCase("Trash") )
  					{
  						cmenuCSS="context-menu-folder-del";
  					}
  					else if(nm.equalsIgnoreCase("Inbox") || nm.equalsIgnoreCase("Archive") )
  					{
  						cmenuCSS="context-menu-folder-system-in";
  					}
  					else if( nm.equalsIgnoreCase("Drafts") || nm.equalsIgnoreCase("Sent"))
  					{
  						cmenuCSS="context-menu-folder-system-out";
  					}
  					else
  					{
  						cmenuCSS="context-menu-folder-other";
  					}
  						
  					String arr[]=nm.split("\\.");
  					int l=arr.length;
  					//String sps="";
  					/* for(int j=1;j<l;j++)
  					{
  						sps+="&nbsp;&nbsp;&nbsp;&nbsp;";
  					} */
  					//out.print("<br>"+sps+arr[l-1]+"-----"+l);
  					%>
  					 <%
  					 if(!list1.get(i).isHasChild() && !list1.get(i).isIsSubs())
  					 {
  						 
  					 }
  					 else
  					 {
  				
  	  			  if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
  	  			  {
  	  			  %>
  	  			  <li><div id="fldr_INBOX" class="left_partent_tree active_left_tree ">
  	  			  <%}
  	  			  else
  	  			  {
  	  			  %>
  	  	  			<li><div id="fldr_<%=list1.get(i).getFolderFullName().replaceAll(" ", "_") %>" class="left_partent_tree">
  	  	  			<%
  	  			  }
  	  	  		if(list1.get(i).isHasChild() )
  	  	  		{
  	  	  			/* if(list1.get(i).getFolderFullName().startsWith("Customers"))
  	  	  			{
  	  	  			flag=0;
  	  	  			} */
  	  	  			if(list1.get(i).isIsSubs())
  	  	  			{
  	  	  			%>
  	  	  			
  	  	  			<div class="child_level"></div> 
  	  	  			<%if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
  	          		{
  	          			%>
  	  	  			<span id="<%=nm %>"  style="cursor: pointer;" onclick="getWebmailInbox(this.id)" class="<%=cmenuCSS %>">
  	          		<%=list1.get(i).getFolderName() %> 
  	          			&nbsp;<span id='unread_inbox'><%=inboxcnt %> </span>
  	          		 </span>
  	        	    <%} 
  	        	    else
  	        	    {%>
  	        	    <span id="<%=nm %>" style="cursor: pointer;" onclick="getWebmailInbox(this.id)" class="<%=cmenuCSS %>">
  	          		<%=list1.get(i).getFolderName() %> 
  	          	    </span>
  	        	    
  	        	    <%} %>
  	  	  			<%}
  	  	  			else
  	  	  			{
  	  	  				%>
  	  	  			<div class="child_level"></div> 
  	  	  			
  	  	  			<%if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
  	          		{
  	          			%>
  	  	  			<span id="<%=nm %>"  style="cursor: pointer;" onclick="getWebmailInbox(this.id)" class="<%=cmenuCSS %>">
  	          		<%=list1.get(i).getFolderName() %> 
  	          			&nbsp;<span id='unread_inbox'><%=inboxcnt %> </span>
  	          		 </span>
  	        	    <%} 
  	        	    else
  	        	    {%>
  	  	  			
  	  	  			<span id="<%=nm %>" class="inactive_fldr" class="<%=cmenuCSS %>">
  	          		<%=list1.get(i).getFolderName() %> 
  	        	    </span>
  	        	    <%
  	        	    }
  	  	  			}
  	  	  		}
  	  	  		else
  	  	  		{
  	  	  			%>
  	  	  			<div class="child_blank"></div>
  	  	  			<%if(list1.get(i).getFolderFullName().equalsIgnoreCase("INBOX"))
  	          		{
  	          			%>
  	  	  			<span id="<%=nm %>" style="cursor: pointer;"  onclick="getWebmailInbox(this.id)" class="<%=cmenuCSS %>">
  	          		<%=list1.get(i).getFolderName() %> 
  	          		&nbsp;<span id='unread_inbox'><%=inboxcnt %> </span>
  	        	    </span>
  	        	    <%}
  	  	  			else
  	  	  			{
  	  	  			%>
  	  	  			<span id="<%=nm %>" style="cursor: pointer;" onclick="getWebmailInbox(this.id)" class="<%=cmenuCSS %>">
  	          		<%=list1.get(i).getFolderName() %> 
  	          		</span>
  	  	  			<%} %>
  	  	  			<%} %>
  	  	  			</div>
  	  	  			<%
  					 }
  	  	  			if(i+1 != list1.size())
  	  	  			{
  	  	  				String nm1=list1.get(i+1).getFolderFullName();
  	  	  			String arr1[]=nm1.split("\\.");
  	  	  			int l1=arr1.length;
  	  	  			if(l<l1)
  	  	  			{
  	  	  			out.print("<ul class='level'>");
  	  	  		
  	  	  			}
  	  	  			else if(l==l1)
  	  	  			{
  	  	  			out.print("</li>");
  	  	  			}
  	  	  			else
  	  	  			{
  	  	  			out.print("</li></ul>");
  	  	  			
  	  	  			}
  	  	  			}
  	  	  			else
  	  	  			{
  	  	  				
  	  	  				 if(i>0)
  	  	  				{
  	  	  				for(;l>1;l--)
  	  	  				{
  	  	  				out.print("</li></ul>");
  	  	  				  	  	  				}
  	  	  				}
  	  	  				
  	  	  				
  	  	  				
  	  	  			}
  	  	  			/* if(i==list1.size()-1 && flag==0)
  	  	  			{
  	  	  			out.print("</ul>");
  	  	  			} */
  				}
  				
  				//out.print("<br>"+lFiles(list, fd.getFullName()));
  			%>
  			<%-- <%= lFiles(list, fd.getFullName()) %> --%>
  			<%
  			}
  			else
  			{
  				if(fd.getFolderFullName().equalsIgnoreCase("Junk") || fd.getFolderFullName().equalsIgnoreCase("Trash") )
					{
						cmenuCSS="context-menu-folder-del";
					}
					else if(fd.getFolderFullName().equalsIgnoreCase("Inbox") || fd.getFolderFullName().equalsIgnoreCase("Archive") )
					{
						cmenuCSS="context-menu-folder-system-in";
					}
					else if( fd.getFolderFullName().equalsIgnoreCase("Drafts") || fd.getFolderFullName().equalsIgnoreCase("Sent"))
  					{
  						cmenuCSS="context-menu-folder-system-out";
  					}
					else
					{
						cmenuCSS="context-menu-folder-other";
					}
  				//out.print("<br>"+fd.getFullName());
  				%>
  	  			<%-- <%=fd.getName() %> --%>
  	  			  <li>
  	  			  <%
  	  			  if(fd.getFolderFullName().equalsIgnoreCase("INBOX"))
  	  			  {
  	  			  %>
  	  			  <div id="fldr_INBOX" class="left_partent_tree active_left_tree ">
  	  			  <div class="child_blank"></div> 
  	  			  <span id="<%=fd.getFolderFullName() %>"   style="cursor: pointer;" onclick="getWebmailInbox(this.id)" class="<%=cmenuCSS %>">
          		  <%=fd.getFolderFullName()  %> 
          		  &nbsp;<span id='unread_inbox'><%=inboxcnt %> </span>
          		</span></div>
  	  			  <%}
  	  			  else  if(fd.getFolderFullName().equalsIgnoreCase("DRAFTS"))
  	  			  {
  	  				 int dcnt= fd.getMessageCount();
  	  				 String drtcnt="";
  	  				 if(dcnt>0)
  	  				 {
  	  					drtcnt="("+dcnt+")";
  	  				 }
  	  			  %>
  	  			  <div id="fldr_<%=fd.getFolderFullName().replaceAll(" ", "_") %>" class="left_partent_tree ">
  	  			  <div class="child_blank"></div> 
  	  			  <span id="<%=fd.getFolderFullName() %>"   style="cursor: pointer;" onclick="getWebmailInbox(this.id)" class="<%=cmenuCSS %>">
          		  <%=fd.getFolderFullName()  %> 
          		  &nbsp;<span id='draft_mail_cnt'><%=drtcnt %> </span>
          		</span></div>
  	  			  <%}
  	  			  else
  	  			  {
  	  				if(fd.isIsSubs())
  	  				{
  	  			  %>
  	  			  <div id="fldr_<%=fd.getFolderFullName().replaceAll(" ", "_") %>" class="left_partent_tree">
  	  			  <div class="child_blank"></div> 
  	  			  <span id="<%=fd.getFolderFullName() %>" style="cursor: pointer;" class="<%=cmenuCSS %>" onclick="getWebmailInbox(this.id)">
          <%=fd.getFolderFullName()  %> </span></div>
  	  			  <%} %>
  	  			  
          </li>
  	  			<%
  				}
  			}
  			}
  		  
  

%>
</ul></div>
 
     
  </body>
</html>
