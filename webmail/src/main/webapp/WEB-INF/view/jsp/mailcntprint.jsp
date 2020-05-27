<%@page import="webmail.bean.TextDecode"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="webmail.wsdlnew.GetMailDisplayResponse"%>
<%@page import="javax.mail.internet.MimeUtility"%>
<%@page import="webmail.wsdlnew.Attachment"%>
<%@page import="webmail.wsdlnew.InboxDisplay"%>
<%@page import="webmail.wsdl.*"%>
<%@page import="webmail.webservice.client.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="js/jquery-1.8.3.min.js"></script>
<style type="text/css">
body,td,div,p,a,input {font-family: arial, sans-serif;}
</style>
<style type="text/css">
body, td {font-size:13px} a:link, a:active {color:#1155CC; text-decoration:none} a:hover {text-decoration:underline; cursor: pointer} a:visited{color:##6611CC} img{border:0px} pre { white-space: pre; white-space: -moz-pre-wrap; white-space: -o-pre-wrap; white-space: pre-wrap; word-wrap: break-word; max-width: 800px; overflow: auto;}
</style>
<script type="text/javascript">






</script>
</head>
<body >
<%
String uid=(String)request.getAttribute("uid");
String folder=(String)request.getAttribute("folder");

//out.print(folder);
 WebmailClient webmailClient=(WebmailClient) request.getAttribute("webmailClient");


String res="";





HttpSession hs=request.getSession();
String host=(String)hs.getAttribute("host");
String id=(String)hs.getAttribute("id");
String pass=(String)hs.getAttribute("pass");
String port=(String)hs.getAttribute("port");
//String fname=(String)hs.getAttribute("fname");

String realPath = request.getServletContext().getRealPath("/");
String filePath  = realPath+"WEB-INF/view/jsp/temp/";
GetMailDisplayResponse gdres=webmailClient.displayMailContentRequest(host, port, id, pass, uid, folder,filePath,"same");
InboxDisplay inbd= gdres.getGetInboxByUid();
List<Attachment> latt=inbd.getAttachment();




if(latt!=null)
{

String uids="";
String fromid="";
String disp_from="";
String ovr_from1="";
String ovr_from2="";
String sub="";
String attch="";
String date="";
String dt_title="";
String mailcnt="";
String to="";
String cc="";
String bcc="";
String disp_to="";
String disp_cc="";
String disp_bcc="";
String disp_to_tit="";
%>
<input type='hidden' id='hid_inline_sz' value='<%= inbd.getInlineimgsize() %>' />
<input type='hidden' id='hid_attch_sz' value='<%=latt.size() %>' />
<%

uids=inbd.getUid();
sub=inbd.getSubject();
fromid=inbd.getFromId();
dt_title=inbd.getSendDtaeTitle();
mailcnt=inbd.getMailContent();
 StringBuilder regex = new StringBuilder("<script[^>]*>(.*?)</script>");
		int flags = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern = Pattern.compile(regex.toString(), flags);
		Matcher matcher = pattern.matcher(mailcnt);
		mailcnt=matcher.replaceAll("");
		
		StringBuilder regex1 = new StringBuilder("<script[^>]*>");
		int flags1 = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern1 = Pattern.compile(regex1.toString(), flags1);
		Matcher matcher1 = pattern1.matcher(mailcnt);
		mailcnt=matcher1.replaceAll("");
		
		StringBuilder regex2 = new StringBuilder("onerror=[^>]*>");
		int flags2 = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern2 = Pattern.compile(regex2.toString(), flags2);
		Matcher matcher2 = pattern2.matcher(mailcnt);
		mailcnt=matcher2.replaceAll(">"); 
		
to=inbd.getToId();

try {
	fromid=TextDecode.decodeUTF8Text(fromid);
} catch (Exception e) {
	// TODO Auto-generated catch block
	fromid=inbd.getFromId();
	e.printStackTrace();
}
cc=inbd.getCCId();
try {
	cc=TextDecode.decodeUTF8Text(cc);
} catch (Exception e) {
	// TODO Auto-generated catch block
	cc=inbd.getCCId();
	e.printStackTrace();
}
bcc=inbd.getBCCId();
try {
	bcc=TextDecode.decodeUTF8Text(bcc);
} catch (Exception e) {
	// TODO Auto-generated catch block
	bcc=inbd.getBCCId();
	e.printStackTrace();
}

try {
	to=TextDecode.decodeUTF8Text(to);
} catch (Exception e) {
	// TODO Auto-generated catch block
	fromid=inbd.getFromId();
	e.printStackTrace();
}


try {
	sub=TextDecode.decodeUTF8Text(sub);
} catch (Exception e) {
	// TODO Auto-generated catch block
	fromid=inbd.getFromId();
	e.printStackTrace();
}




disp_from=fromid;
disp_from=disp_from.replace("<", "&lt;");
disp_from=disp_from.replace(">", "&gt;");


disp_to=to;
disp_to=disp_to.replace("<", "&lt;");
disp_to=disp_to.replace(">", "&gt;");
//disp_to=disp_to.replace(",", ",<br>");

disp_cc=cc;
disp_cc=disp_cc.replace("<", "&lt;");
disp_cc=disp_cc.replace(">", "&gt;");

disp_bcc=bcc;
disp_bcc=disp_bcc.replace("<", "&lt;");
disp_bcc=disp_bcc.replace(">", "&gt;");

String disp_head=id;
%>



<div class="bodycontainer">
<table width=100% cellpadding=0 cellspacing=0 border=0>
<tr height=14px><td width=143><img style="height: 65px;" src="images/logo_login.png"  ></td>
<td align=right>
<font size=-1 color=#777><b><%=disp_head %></b></font> 
</td></tr></table>
<hr>
<div class="maincontent">
<table width=100% cellpadding=0 cellspacing=0 border=0><tr><td><font size=+1>
<b><%=sub %></b></font></td></tr></table>
<hr>
<table width=100% cellpadding=0 cellspacing=0 border=0 class="message">
<tr><td><font size=-1>From:v<%=disp_from %></font></td>
<td align=right><font size=-1><%=dt_title %></font></td></tr>
<tr><td style=" padding-top: 5px;" colspan=2><font size=-1 class="recipient"><div>To: <%=disp_to %></div></font></td></tr>
<% if(cc!=null && cc.length()>0)
{
%>
<tr><td style=" padding-top: 5px;" colspan=2><font size=-1 class="recipient"><div>Cc: <%=disp_cc %></div></font></td></tr>
<%} %>
<% if(folder.equalsIgnoreCase("Sent") && bcc!=null && bcc.length()>0)
{
%>
<tr><td style=" padding-top: 5px;" colspan=2><font size=-1 class="recipient"><div>Bcc: <%=disp_bcc %></div></font></td></tr>
<%} %>
<tr><td colspan=2><table width=100% cellpadding=12 cellspacing=0 border=0><tr><td><div style="overflow: hidden;"><font size=-1>

<%=mailcnt %>

</font></div>



<%

if(latt!=null && latt.size()>0)
{
%>
<br clear=all><div style="width:50%;border-top:2px #AAAAAA solid"></div><table class=att cellspacing=0 cellpadding=5 border=0><tr><td><table cellspacing=0 cellpadding=0>
<%
for(Attachment at : latt)
{
String nm=at.getAttachmentName();
String sz=at.getAttachmentSize();
if(((Integer.parseInt(sz))/(1024*1024))>0)
{
	sz=""+((Integer.parseInt(sz))/(1024*1024))+" MB";
}
else if(((Integer.parseInt(sz))/(1024))>0)
{
	sz=""+((Integer.parseInt(sz))/(1024))+" KB";
}
else
{
	sz=sz+" bytes";
}


%>


<tr><td><b><%=nm %></b>&nbsp;<%=sz %> </td></tr>


<%

}
%>
</table></td></tr></table>
<%
}

%>
</table></table></div></div>
<%

}


%>


<script type="text/javascript">
document.body.onload=function(){
	
	var inline=parseInt($('#hid_inline_sz').val())-parseInt($('#hid_attch_sz').val())
	if(inline>0)
		{
		
		 var i=1;
		 $('img').each(function(){
			 var str = $(this).attr('src');
		
			  if ( str.startsWith("cid:"))
	    	   {
				str=str.replace("cid:","");
				str = str.replace(/@/g, '_');
				  str = str.replace(/\./g, '_');
				$(this).attr('src',$('#'+str).val());
	   		   
	    	   } 
			
			 });
		
		}
	
	
	document.body.offsetHeight;window.print()};
</script>

</body>
</html>