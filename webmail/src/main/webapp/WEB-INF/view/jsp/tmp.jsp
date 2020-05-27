<!doctype html>
<%@page import="javax.mail.Store"%>
<%@page import="javax.mail.Session"%>
<%@page import="java.util.Properties"%>
<%@page import="java.util.Enumeration"%>
<%@page import="javax.mail.internet.*"%>
<%@page import="javax.naming.directory.Attributes"%>
<%@page import="javax.naming.directory.SearchResult"%>
<%@page import="javax.naming.NamingEnumeration"%>
<%@page import="javax.naming.directory.SearchControls"%>
<%@page import="com.local.WebmailLDAP"%>
<%@page import="javax.naming.directory.DirContext"%>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Autocomplete - Default functionality</title>
  
</head>
<body>
 

 
 <%
 Store store = null;
 HttpSession hs=request.getSession();
 String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
 try
 {
	 Properties props = System.getProperties();
    // props.setProperty("mail.store.protocol", "imaps");
     props.setProperty("mail.store.protocol", "imap");
     Session ses = Session.getDefaultInstance(props, null);
     //store = ses.getStore("imaps");
    store = ses.getStore("imap");
     store.connect(host,id, pass);
     javax.mail.Folder parent = store.getDefaultFolder(); 
     javax.mail.Folder newFolder = parent.getFolder("Promotional"); 
     newFolder.setSubscribed(true);
     
     store.close();
 out.print("yes");
 }
 catch(Exception e)
 {
	 e.printStackTrace();
 }
 %>
</body>
</html>