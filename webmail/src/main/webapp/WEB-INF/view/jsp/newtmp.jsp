<%@page import="com.sun.mail.imap.ACL"%>
<%@page import="org.hsqldb.rights.Right"%>
<%@page import="com.sun.mail.imap.Rights"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sun.mail.imap.IMAPFolder"%>
<%@ page language="java" import="java.util.*,
 javax.activation.DataHandler,
 javax.mail.*,
 javax.mail.internet.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'createfolder.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

 <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
 
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
  <script>
  $( function() {
   
	  
	  
	   
	 var requestPage="${pageContext.request.contextPath}/autocomp_newest";
		jQuery.get(requestPage,function( data ) {
			var availableTags =data.split("\n");
			 $( "#tags" ).autocomplete({
			      source: availableTags
			    });
	            });
   
  } );
  </script>
  </head>

  <body>

 <%
 try {
    String host= "127.0.0.1"; //(String)hs.getAttribute('host');
	String id= "nirbhay@silvereye.in"   ;  //(String)hs.getAttribute('id');
	String pass= "";
	
	
	Properties props = System.getProperties();
    props.setProperty("mail.store.protocol", "imap");
      
            Session ses = Session.getDefaultInstance(props, null);
            Store store = ses.getStore("imap");
            store.connect(host, id, pass);
            IMAPFolder folder = (IMAPFolder) store.getFolder("nps");
	        folder.open(Folder.READ_WRITE);
	        Rights rights =new Rights();
	        rights.add(Rights.Right.WRITE);
	        rights.add(Rights.Right.READ);
	        rights.add(Rights.Right.LOOKUP);
	        rights.add(Rights.Right.POST);
	        rights.add(Rights.Right.INSERT);
	        ACL acl=new ACL("piyush@silvereye.in",rights);
	        folder.addACL(acl);
			folder.close(true);
            store.close();
 
}
catch(Exception e)
{
out.print(e);
}  
 
  %>

<input id="tags" s>




  </body>
</html>
