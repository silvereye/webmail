<%@page import="webmail.wsdlnew.GetMailDisplayResponse"%>
<%@page import="webmail.wsdlnew.Attachment"%>
<%@page import="webmail.wsdlnew.InboxDisplay"%>
<%@page import="webmail.wsdl.*"%>
<%@page import="webmail.webservice.client.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="images/favicon.ico"/>
<link type="text/css" rel="stylesheet" href="css/style.css"/>
<link type="text/css" rel="stylesheet" href="css/blue.css"/>
<link type="text/css" rel="stylesheet" href="css/jquery-ui.css"/>
<!--------------/// TAB STAED HERE ------------------->

<link href="css/setting.css" rel="stylesheet" type="text/css" />
<link href="css/tabcontent.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/tag.css"/>
<!-------------/// TAB STRED HERE  END ----------------->
<!------<link rel="stylesheet" href="css/jquery-ui.css">--->
<script src="js/jquery-1.8.3.min.js"></script>
<!--<script src="js/jquery-ui.js"></script>-->
<script src="js/splitter.js"></script>
<script src="js/sytem-script.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/tabcontent.js" type="text/javascript"></script>
<script src="js/event.js" type="text/javascript"></script>
<script src="js/tag.js"></script>
<style>
.vsplitbar {
	width: 5px;
	background: #aaa;
}
.to:hover{ background:#eee;}
.r_top{ padding-top:0px !important;}
#spliter2 .a {
	background-color: #2d2d2d;
}
#spliter2 .b {
	background-color: #2d002d;
}
#foo {
	background-color: #E92727;
}
#x {
	background-color: #EFBD73;
}
#y {
	background-color: #EF3e32;
}
#b {
	background-color: #73A4EF;
}
#bar {
	background-color: #BEE927;
}
.vsplitbar {
	width: 2px;
	background: #f5f5f5;
	margin-top: -20px;
	cursor: col-resize !important;
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
}
</style>
<script type="text/javascript">
function setEmailImg() {
	var inline=parseInt($('#hid_inline_sz').val())-parseInt($('#hid_attch_sz').val())
	if(inline>0)
		{
		
		 var i=1;
		 $('.mail_content_1 img').each(function(){
			 var str = $(this).attr('src');
		
			  if ( str.startsWith("cid:"))
	    	   {
				str=str.replace("cid:","");
				str = str.replace(/@/g, '_');
				  str = str.replace(/\./g, '_');
				$(this).attr('src',$('#'+str).val());
	   		   /* $(this).removeAttr('width');
			   $(this).removeAttr('height');
	    	   i++; */
	    	   
	    	   } 
			
			 });
		
		}
}



function getAltImageDisp(imgid) {
try
{
var pic = document.getElementById(imgid);
pic.src = "images/unnamed.png";
}
catch (e) {
// TODO: handle exception
}
}



function sendRecMail(id,mailid){
	var sub=document.getElementById("hid_"+id+"_sub").innerHTML ;
	var cntt=document.getElementById("hid_"+id+"_cnt").innerHTML ;
	document.getElementById('mail_sending').style.display= 'block';
	$.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/sendMailReadRec",
       data: {'to':id, 'sub':sub, 'cntt':cntt,'mailid':mailid },
        contentType: "application/json",
        success: function (data) {
           // $("#fileSystem").html(data);
           
           document.getElementById('mail_sending').style.display= 'none';
           document.getElementById('mail_sent').style.display= 'block';
           //alert(data);
           jQuery('.new_notification').hide();
           setTimeout( "jQuery('#mail_sent').hide();",3000 );
        
        //   $("#folder_div").html(data);
       //    document.getElementById(fdrname).className = "active_mailbox";
        }
    });
	
}

function ignoreRecMail(mailid) {
	document.getElementById('action_gif').style.display= 'block';
	$.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/ignoreMailReadRec",
        data: {'mailid':mailid },
        contentType: "application/json",
        success: function (data) {
          
        	jQuery('.new_notification').hide();
        	document.getElementById('action_gif').style.display= 'none';
      
        }
    });
}
function saveEDMSMailAttachByName(uid,nm) {
	document.getElementById('action_gif').style.display= 'block';
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/saveEDMSMailAttachByName",
		data : {
			'uid' : uid,'nm':nm
			},
		contentType : "application/json",
		success : function(data) {
			
			document.getElementById('action_gif').style.display= 'none';
		}	
		
	});
	
}

function viewMailAttachByName(uid,nm)
{
//alert(uid+"---"+nm);
var arr=nm.split(".");
var ext=arr[arr.length-1];
if(ext=="png" || ext=="PNG" || ext=="jpg" || ext=="JPG" || ext=="jpeg" || ext=="JPEG" || ext=="gif" || ext=="GIF")
{
//window.target="_blank";
//window.location = "${pageContext.request.contextPath}/viewattachment?uid="+uid+"&nm="+nm;
// window.location="http://www.smkproduction.eu5.org"; 
window.open("${pageContext.request.contextPath}/viewattachment?uid="+uid+"&nm="+nm,'_blank');
/* $.ajax({
	type : "GET",
	url : "${pageContext.request.contextPath}/viewIMGMailAttachByName",
	data : {
		'uid' : uid,'nm':nm
		},
	contentType : "application/json",
	success : function(data) {
		
		alert(data);
		var str="<img src='data:image/jpg;base64,"+data+" ' />";
		$("#hid_img_disp").show();
		$("#hid_img_disp").html(str);
		//window.open('${pageContext.request.contextPath}/js/web/viewer.html?file='+data, '_blank');
	}	
	
}); */
}
else
{
$.ajax({
	type : "GET",
	url : "${pageContext.request.contextPath}/viewMailAttachByName",
	data : {
		'uid' : uid,'nm':nm
		},
	contentType : "application/json",
	success : function(data) {
		
	//	alert(data);
		window.open('${pageContext.request.contextPath}/js/web/viewer.html?file='+data, '_blank');
	}	
	
});
}
}
</script>
</head>
<body onload="setEmailImg()">
<div id="action_gif" class="my_notification"
		style="display: none; left: 46%;">Loading...</div>
<%
String uid=(String)request.getAttribute("uid");
String folder=(String)request.getAttribute("folder");

//out.print(folder);
 WebmailClient webmailClient=(WebmailClient) request.getAttribute("webmailClient");


String res="";

res+="<script src='js/new_forword.js'></script> ";




HttpSession hs=request.getSession();
String host=(String)hs.getAttribute("host");
String id=(String)hs.getAttribute("id");
String pass=(String)hs.getAttribute("pass");
String port=(String)hs.getAttribute("port");
String ldapurl=(String)hs.getAttribute("ldapurl");
String ldapbase=(String)hs.getAttribute("ldapbase");
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@folder"+folder);
String realPath = request.getServletContext().getRealPath("/");
String filePath  = realPath+"WEB-INF/view/jsp/temp/";
GetMailDisplayResponse gdres=webmailClient.displayMailContentRequest(host, port, id, pass, uid, folder,filePath,"same");
InboxDisplay inbd= gdres.getGetInboxByUid();
List<Attachment> latt=inbd.getAttachment();

GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "labelSetting");
	 String labset=ldapres.getGetFName();


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
String disp_to="";
String disp_to_tit="";
String pri="";
//attch=inbd.getAttachment();
pri=inbd.getMailPriority();
res+="<input type='hidden' id='hid_inline_sz' value='"+inbd.getInlineimgsize()+"' />";
res+="<input type='hidden' id='hid_attch_sz' value='"+latt.size()+"' />";


String ml_flg="bottom_flag";
if(inbd.isMailFlage())
{
	ml_flg="bottom_flag_red";
}
uids=inbd.getUid();
sub=inbd.getSubject();
fromid=inbd.getFromId();
date=inbd.getSendDtae();
dt_title=inbd.getSendDtaeTitle();
mailcnt=inbd.getMailContent();
//out.println("^^^^^^^^^^^^^^^="+mailcnt);
to=inbd.getToId();

disp_from=fromid;
disp_from=disp_from.replace("<", "&lt;");
disp_from=disp_from.replace("<", "&lt;");
String from_img_id="";
String fromarr[]=fromid.split("<");
if(fromarr!=null && fromarr.length>1)
{
	ovr_from1=fromarr[0];
	ovr_from2=fromarr[1].replace(">", "");
	from_img_id=ovr_from2;
}
else
{
	ovr_from1=fromid;
	from_img_id=ovr_from1;
}





String path_img="";
String idarr[]=id.split("@");
if(from_img_id.contains("@"+idarr[1]))
{
GetLdapOneAttOtrUserResponse ldapres1=webmailClient.getLdapOneAttOtrUser(ldapurl, id, pass, ldapbase, from_img_id,"jpegPhoto");
if(ldapres1.getGetLdapAttr()!=null && !ldapres1.getGetLdapAttr().equals(""))
{
path_img="data:image/jpg;base64,"+ldapres1.getGetLdapAttr();
}
}
String path_img_id=from_img_id+"nomyimage_cnt";
String path_img_full_id=from_img_id+"nomyimage_cnt_full";



disp_to=to;
disp_to=disp_to.replace("<", "&lt;");
disp_to=disp_to.replace("<", "&lt;");
disp_to=disp_to.replace(",", ",<br>");

String toarr[]=to.split(",");
for(int i=0;i<toarr.length;i++)
{

	String tit="";
	
/* 	if(toarr[i].contains(id))
	{
		tit="Me";
	}
	else
	{ */
	String toarr1[]=toarr[i].split("<");
	if(toarr1!=null && toarr1.length>0)
	{
		String att=toarr1[0].trim();
		String toarr2[]=att.split(" ");
		if(toarr2!=null && toarr2.length>0)
		{
			tit=toarr2[0];
		}
		else
		{
			tit=att;
		}
	}
	else
	{
	tit=toarr[i];
	}
	//}
	
	if(disp_to_tit== null || disp_to_tit.equals(""))
	{
		disp_to_tit=tit;
	}
	else
	{
		disp_to_tit=disp_to_tit+", "+tit;
	}
	
}
String tags=inbd.getMailTag();
//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@uid"+uids);





res+="<div class='right_top_pannel'>";



res+="<div id='widget'  style='position: relative;top: -46px !important;' > <div class='full_width_mail'><div class='mail_content'><div class='mail_content_1 full_view_content' style='width: 48.5%; float: right; display: block; min-height: 75%; max-height: 95%;'> ";
res+="<div class='mail_left_content'><div class='top_bottom_1'><h1>"+sub+" </h1> ";
res+="<div class='mail_right_1'>  ";
res+="</div><div class='clear'></div>";
	


res+="<div class='inbox_tag'>";

if(tags!=null && !(tags.equals("")))
{
	
	
	String tagnm[]=tags.split("~");
	//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^tags="+tags);
	for(int j=0;j<tagnm.length;j++)
	{
		boolean st=false;
		String tagcss="";
		String tag="";
		String tagback="";
		if( tagnm[j].equalsIgnoreCase("$label1"))
		{
			tagcss="Important";
    		tag="Important";
    		tagback="#FA6855";
    		st=true;
		}
		else if( tagnm[j].equalsIgnoreCase("$label2"))
		{
			tagcss="Work";
    		tag="Work";
    		tagback="#FC9449";
    		st=true;
		}
		else if( tagnm[j].equalsIgnoreCase("$label3"))
		{
			tagcss="Personal";
    		tag="Personal";
    		tagback="#98C95D";
    		st=true;
		}
		else if( tagnm[j].equalsIgnoreCase("$label4"))
		{
			tagcss="To_Do";
    		tag="To Do";
    		tagback="#486BF7";
    		st=true;
		}
		else if( tagnm[j].equalsIgnoreCase("$label5"))
		{
			tagcss="Later";
    		tag="Later";
    		tagback="#BD48F7";
    		st=true;
		}
		else 
		{
			tagback="gray";
			
			if(labset!=null && labset.length()>0)
                {
				if(labset.indexOf(tagnm[j]) >=0)
				{
				
                 String tagarr[]=labset.split("~");
                 for(int i=0;i< tagarr.length; i++)
                    {
                     String tarr[]=tagarr[i].split("#");
                //     System.out.println("~~~~~~~~~~~~~~~~~~"+tarr[0]+" ----- "+tagnm[j]);
                     if(tarr[0].equalsIgnoreCase(tagnm[j]))
                     {
                    	 st=true;
                    	 tagback="#"+tarr[1];
                    	 break;
                     }
                    }
                }
                }
			
			tag= tagnm[j].replace("_", " ");
    		tagcss= tagnm[j];
    		
		}
		if(st)
		{
			String tagid="full_"+uid+"~"+tagnm[j];
			String tagclsid=	"full-"+uid+"~"+tagnm[j];		
			res+="	<div id='"+tagid+"' class='in_tag_disp in_tag "+tagcss+"' style='display: block; background: "+tagback+";'><span>"+tag+"</span><div id='"+tagclsid+"' onclick='remMailTagFull(this.id)' class='close_tag'>x</div></div>";
		}
	}
}

res+="</div>";


	res+="<div class='clear'></div> </div>";
res+="<div class='mail_header mail_header_top' style='padding-top: 0px;'> <div class='mail_left'>"; 
res+="<div class='images_hover'><img src='"+path_img+"' onerror='getAltImageDisp(this.id)' id='"+path_img_id+"' /><div class='images_details' style='height:130px !important;'><div class='mail_left_1'>";
res+="<span>"+ovr_from1+"</span><div class='clear'></div>"+ovr_from2+"</div>";
res+="<img src='"+path_img+"' onerror='getAltImageDisp(this.id)' id='"+path_img_full_id+"' /><div class='send_mail_deatils' style='display:none;'><a href='#'>Add to contacts</a><a href='#'>Emails</a>";
res+="<div class='right_forw'><a href='#'><div class='mail_for'><img src='images/plus_option.gif'></div></a><a href='#'>";
res+="<div class='mail_for_1'><img src='images/mail_fow.png'></div></a></div></div></div> </div>";
     

res+="<p class='first_p'><a href='#'>"+disp_from+"</a></p>";
res+="<div class='mail_row'><p class='last_p new_paragraph'>To "+disp_to_tit+" </p><a href='#' onClick='mail_to(event);'> <div class='mail_deatil'></div> </a>";
res+="<div class='to_me' style='display: none;'><ul><li class='comm_width'><span>From: </span></li><li> "+disp_from+"</li>";
res+="<div class='clear'></div><li class='comm_width'><span>To:</span></li><li>"+disp_to+"</li>";
res+="<div class='clear'></div><li class='comm_width'><span>Date:</span></li><li> "+dt_title+"</li>";
res+="<div class='clear'></div><li class='comm_width'><span>Subject:</span></li><li>"+sub+"</li>";

res+="<div class='clear'></div></ul></div> </div></div>";


res+="<div class='mail_right'><div style='float:left;margin-top: 7px;'>"+date+" </div>";
res+="<div class='"+ml_flg+"'></div><div class='mail_option' data-tooltip='More'>";
res+="<a title='More' href='#' onClick='option_here_1(event);'> <div class='option'> <img src='images/open_sub_menu.png'></div></a>";
res+="</div></div><div class='clear'></div></div>";
res+="<div class='mail_content_1 mail_content_p'> <div class='mail_down_option_1' style='top:84px;'><ul>";
res+="<li><a target='_blank' href='mailheadercntt?uid="+uid+"&folder="+folder+"' style='cursor: pointer;'>View Header</a></li><div class='clear'></div>";
res+="<li><a target='_blank' href='mailcntprint?uid="+uid+"&folder="+folder+"' style='cursor: pointer;'>Print</a></li>";
res+="</ul>";
res+="<div class='clear'></div></div>";


if(inbd.isMailFlageRecent() && inbd.getMailReadRecId()!=null && !(inbd.getMailReadRecId().equalsIgnoreCase("")))
{
	if(!tags.contains("$MDNSent"))
	{
	String rec_sub="Return Receipt (displayed) -"+inbd.getSubject();
	String rec_cnt="This is a Return Receipt for the mail that you sent to "+id+". <br>Note: This Return Receipt only acknowledges that the message was displayed on the recipient's computer. There is no guarantee that the recipient has read or understood the message contents. "+inbd.getMailContent();
	res+="<div id='hid_"+inbd.getMailReadRecId()+"_sub' style='display:none;'>"+rec_sub+"</div>";
	res+="<div id='hid_"+inbd.getMailReadRecId()+"_cnt' style='display:none;'>"+rec_cnt+"</div>";
	res+=" <div class='new_notification'><div class='new_notification_left'><span class='notification_icon'></span>";
	res+="<span class='notification_message'><b>Sender</b>&nbsp;has asked to be notified when you read this message.</span></div>";
	res+="<div class='new_notification_right'><span id='"+inbd.getMailReadRecId()+"' name='"+uid+"' onclick='sendRecMail(this.id, "+uid+")' class='notification_button'>Send</span> <span class='notification_button' name='"+uid+"' onclick='ignoreRecMail("+uid+")'>Ignore</span>";
	res+="</div><div class='clear'></div></div><div class='clear'></div>";
	}
}

if(pri!=null && !pri.equals(""))
{
	 
	 if(pri.equalsIgnoreCase("Highest"))	
		{
		 res+="<p><img width='20px' height='20px' style='margin-bottom: -6px;  opacity: 0.9;' src='images/impt.png' /> This message was sent with high importance.</p>";	
		}
		else if(pri.equalsIgnoreCase("Lowest"))	
		{
		res+="<p><img width='20px' height='20px' style='margin-bottom: -6px;  opacity: 0.9;' src='images/impt.png' /> This message was sent with low importance.</p>";		
		}
}

res+="<p>"+mailcnt+"</p>";




if(latt!=null && latt.size()>0)
{
	
	String atstr="Attachments";
	if(latt.size()==1)
	{
		atstr="Attachment";
	}
res+="<div class='main_attachment'><div class='attachment_file attachment_top'><div class='left_attachment'>";
res+="<p><span>"+latt.size()+"</span> "+atstr+"</p><div class='clear'></div> ";
		
		if(latt.size()>1)
   	{
   		res+="<a href='downloadMailZipAllAttach?uid="+uid+"&subject="+sub+"'> <div data-tooltip='Download all Attachments' class='attachment_option_1'><img src='images/download.png'></div></a>" ;
   	}
		
res+="</div> <div class='left_attachment'></div></div>";
res+="<div class='attachment_content'> <ul>";

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
String nam=nm.replace('.', ';');
String arr_ext[]=nam.split(";");


String ext=arr_ext[arr_ext.length-1];
ext=ext.trim();
String att_sml_icon="images/att_sml_file.png";
String att_big_icon="images/att_big_file.png";
if(ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("gif"))
{
	att_sml_icon="images/att_sml_image.png";
	att_big_icon="images/att_big_image.png";
}
else if(ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx") )
{
	att_sml_icon="images/att_sml_word.png";
	att_big_icon="images/att_big_word.png";
}
else if(ext.equalsIgnoreCase("ppt") || ext.equalsIgnoreCase("pptx") )
{
	att_sml_icon="images/att_sml_ppt.png";
	att_big_icon="images/att_big_ppt.png";
}
else if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("csv") )
{
	att_sml_icon="images/att_sml_excel.png";
	att_big_icon="images/att_big_excel.png";
}
else if(ext.equalsIgnoreCase("pdf")  )
{
	att_sml_icon="images/att_sml_pdf.png";
	att_big_icon="images/att_big_pdf.png";
}
if(ext.equalsIgnoreCase("zip") || ext.equalsIgnoreCase("7z") || ext.equalsIgnoreCase("rar"))
{
	att_sml_icon="images/att_sml_zip.png";
	att_big_icon="images/att_big_zip.png";
}

res+="<li><div class='attachment_con_box'><div class='attachment_images'> <img src='"+att_big_icon+"' /></div>";
res+="<div class='attach_con_bottom'><img src='"+att_sml_icon+"' /> <span>"+nm+"</span> </div>";

res+="<div class='attach_mousehover'> <div class='attach_row'><img src='"+att_sml_icon+"' /> <span>"+nm+"</span>";
res+="<div class='clear'></div><div class='attachment_size'>"+sz+"</div> </div>";
res+="<div class='clear'></div><div class='attachment_option'><a href='downloadMailAttachByName?uid="+uid+"&name="+nm+"'> <img src='images/download.png'  />"
		+ "</a>"
		+ "<a title='Preview' onclick='viewMailAttachByName(this.id,this.name)' id='"+uid+"' name='"+nm+"' class='preview_icon'> <img src='images/preview.jpg'  /> </a>"
		+ "<a title='Save to EDMS' onclick='saveEDMSMailAttachByName(this.id,this.name)' id='"+uid+"' name='"+nm+"' class='preview_icon'> <img src='images/edms_save.png'  /> </a>"
		+ "</div>   </div> </div> </li>";

}

res+="</ul></div></div>";


/***** forword reply ************/

res+=" </div> <div class='clear'></div><div class='bottom_left' style='display: block;'></div>";
res+="  <div class='your_option_1' onClick='mail_forward_11();' style='margin-top: 64px;'>Click here to <a href='#' class='replay_li'>Reply</a> or <a href='#' class='forward_li'>Forward</a></div>";
res+="   <div class='mail_forward_11'><div class='forward_top'><div class='forward_mail'  onClick='down_mail(event);'>";
res+="  <a href='#' class='mail_left_for'> <div class='reply replay_display'></div>";
res+="  <div class='reply_all reply_all_display'></div><div class='forward forward_display'></div></a>";
res+="  <a href='#' class='mail_right_for'> <img src='images/open_sub_menu.png'></a><div class='main_bottom_option'>";
res+="  <ul><li class='replay_li'><div class='reply'></div>Reply to <span>Hariom Srivastava</span></li>";
res+="  <li class='replay_all_li'><div class='reply_all'></div>Reply all to <span>Hariom Srivastava,Hariom Srivastava</span>+3</li>";
res+="  <li class='forward_li'><div class='forward'></div>Forward</li>";
res+="  <li class='edit'>Edit subject</li></ul> </div></div>";
res+="   <div class='forward_mail_left'><div class='combind_email'></div> <textarea class='forward_mail_event' ></textarea>";
res+="    </div> <div class='clear'></div> </div> <div class='clear'></div>";
res+="   <div class='forward_mid'> <textarea id='yourNavigationId'> </textarea> </div>";
res+="   <div class='forward_bottom'><div class='for_bottom_left'>Send </div>";
res+="    <div class='for_bottom_mid'> <a href='#' class='font_image'>  <img src='images/a_fonts.gif'> </a>";
res+="    <div class='bor_1'></div>    <a href='#' class='attach_image'> <img src='images/attachment.png'> </a>";
res+="    <a href='#' class='plus'> <img src='images/plus_black.png'>   <div class='plus_option'></div>   </a> </div>";
res+="    <div class='for_bottom_right'><a href='#' class='dustbin'><img src='images/tool.png'></a>  <div class='bor_1'></div>";
res+="     <a href='#' class='bottom_down_1' onClick='bootom_forward(event);'><img src='images/open_sub_menu.png'></a> </div>";
res+="      </div> <div class='for_bottom'> </div> <div class='clear'></div>  </div> </div>   <div class='clear'></div>";

}

}
out.print(res); 

%>

<a ></a> 
</body>
</html>