<%@page import="javax.mail.internet.MimeUtility"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="webmail.wsdl.Attachment"%>
<%@page import="webmail.wsdl.InboxDisplay"%>
<%@page import="webmail.wsdl.*"%>
<%@page import="webmail.webservice.client.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
body,td,div,p,a,input {font-family: arial, sans-serif;}
</style>
<style type="text/css">
body, td {font-size:13px} a:link, a:active {color:#1155CC; text-decoration:none} a:hover {text-decoration:underline; cursor: pointer} a:visited{color:##6611CC} img{border:0px} pre { white-space: pre; white-space: -moz-pre-wrap; white-space: -o-pre-wrap; white-space: pre-wrap; word-wrap: break-word; max-width: 800px; overflow: auto;}
</style></head>
<body>
<%
String uid=(String)request.getAttribute("uid");
String folder=(String)request.getAttribute("folder");
String headercntt="";
//out.print(folder);
 WebmailClient webmailClient=(WebmailClient) request.getAttribute("webmailClient");


HttpSession hs=request.getSession();
String host=(String)hs.getAttribute("host");
String id=(String)hs.getAttribute("id");
String pass=(String)hs.getAttribute("pass");
String port=(String)hs.getAttribute("port");

GetMailHeaderResponse resp =webmailClient.getMailHeaderRequest(host, port, id, pass, uid, folder);
headercntt=resp.getGetMailHeaderCont();



%>
<pre style="word-wrap: break-word; white-space: pre-wrap;"><%=headercntt %></pre>
</body>
</html>