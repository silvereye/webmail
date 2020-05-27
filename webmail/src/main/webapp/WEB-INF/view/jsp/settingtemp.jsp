<%@page import="webmail.model.UserPref"%>
<%@page import="java.io.IOException"%>
<%@page import="com.sieve.manage.SieveScript"%>
<%@page import="com.sieve.manage.ManageSieveResponse"%>
<%@page import="com.sieve.manage.ManageSieveClient"%>
<%@page import="java.util.*"%>
<%@page import="javax.naming.*"%>
<%@page import="javax.naming.directory.*"%>


<%@page import="webmail.webservice.client.WebmailClient,webmail.wsdl.*,java.text.*"%>
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/buttons.css" />
<!-- <script src="js/jquery-1.8.3.min.js"></script> -->
<script src="setting/account.js" type="text/javascript"></script>
<script src="setting/tabcontent.js" type="text/javascript"></script>
<script src="setting/setting_tab.js" type="text/javascript"></script>
<script src="setting/setting_js.js" type="text/javascript"></script>


<script type="text/javascript">
$(document).ready(function(){ 
	$('.popupDatepicker').datepick();
	$("#fromFilter").autocomplete("${pageContext.request.contextPath}/autocomp");
	$("#toFilter").autocomplete("${pageContext.request.contextPath}/autocomp");
});

function generate(type) {
    var n = noty({
        text        : type,
        type        : type,
        dismissQueue: false,
        layout      : 'topCenter',
        theme       : 'defaultTheme'
    });
  
    return n;
}

</script>

<style>
.vsplitbar {
	width: 5px;
	background: #aaa;
}
/*.to:hover{ background:#eee;}*/
.r_top{ padding-top:0px !important;}
#spliter2 .a {
	background-color: #2d2d2d;
}
#spliter2 .b {
	background-color: #2d002d;
}
#foo {
/* 	background-color: #E92727; */
}
#x {
	background-color: #EFBD73;
}
#y {
	background-color: #EF3e32;
}
#b {
	/* background-color: #73A4EF; */
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
 <style type="text/css">
   

 .short{
	color:#FF0000;
}

.weak{
	color:#E66C2C;
}

 .good{
	color:#2D98F3;
}

 .strong{
	color:#006400;
}

 #view2,#view3,#view4,#view5,#view6,#view7,#view8,#view9{display:none;}

   </style>
    <script type="text/javascript">
   function genSet() {
	
   }
    
   </script> 
  
</head>
<body >

  <div class="right_top_pannel setting_pages"> 

                        <!-------// RIGHT TOP TOOL END HERE -------->
                        <div class="for_tool">
                        <a onclick="backFromSetting()" title="Back"  ><span  class="setting_back">
<img style="  margin-left: 7px;" src="images/back_option.png">
</span></a>
                               <h2 class="dt">Settings</h2> 
                              
                           
                           <div class="right_tool_part">
                            <div class="right_tool"> <!-- <a href=""> <img src="images/reload.png"> </a>  --></div>
                          
                              </div>
                            
                          
                            </div>
                            
                         
                    </div>
 
    <%
  System.out.println("!!!!!!!!!!!!!!!!!!!!seting jsp");
    WebmailClient webmailClient=(WebmailClient) request.getAttribute("webmailClient");
    List<MailImapFolders> imapfldrlst=(List<MailImapFolders>) request.getAttribute("imapfldrlst");
 	HttpSession hs=request.getSession();
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	String port=(String)hs.getAttribute("port");
	String ldapurl=(String)hs.getAttribute("ldapurl");
	String ldapbase=(String)hs.getAttribute("ldapbase");
	 byte[] jpegBytes4=(byte[])hs.getAttribute("img_myurl");
     String set_img="";
     if(jpegBytes4!=null)
     {
    	 set_img= new sun.misc.BASE64Encoder().encode(jpegBytes4);
     }
	
     GetLdapFNameResponse ldapres=webmailClient.getLdapFNmae(ldapurl, id, pass, ldapbase, "labelSetting");
		String labset=ldapres.getGetFName();
	 //HashMap hm_fldr = new HashMap();
	// HashMap hm_fldr_path = new HashMap();
	// int hm_i=0;
	List<String> all_fldr_lst=new ArrayList();
    %>
    <%!
public List<MailImapFolders> lFiles(List<MailImapFolders> list, String path, String host, String id, String pass, WebmailClient webmailClient) {
    	 
    	GetWebmailFolderOtherResponse sfres=webmailClient.getWebmailFolderOtherRequest(host, id, pass, path);
	 	List<MailImapFolders> sflst= sfres.getGetSubFolder().getMailFolderListReturn().getMailFolderList();      
    	         for(MailImapFolders fd:sflst)
    	  			{
    	        	 list.add(fd);
    	        	 //System.out.println("^^^^^^^^^^^^^"+fd);
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
<input type="hidden" id="hid_usermail" value="<%=id %>" /> 
 <input type="hidden" id="hid_del_fldr" value="" data-foldername="" /> 
 <input type="hidden" id="hid_del_label" value="" data-foldername="" /> 
    <!-----------------------------/// Main Cointer Stared here --------------->
    <div id="widget_setting" class="setting_scroll" > 
      <!---------------//// SETTING PAGES CONTENT STARED HERE ------------->
         <div class="right_top_pannel_1 main_tab"> 
      <!---------------/// SETTING TAB STARED HERE -------------->
      <ul class="tabs" data-persist="true">
        <li class="view_1 setting_active">General</li>
        <li class="view_2">Folders</li>
        <li class="view_3">Profile</li>
        <li class="view_4" >Password</li>
        <li class="view_5">Filters</li>
        <li class="view_6">Vacation</li>
        <li class="view_7">Labels</li>
         <li class="view_8">Forwarding</li>
         <li class="view_9">White/Blacklist</li>
        
     
      </ul>
    </div>
      
      <div class="tabcontents">
      
        <div id="view1">
        <form id="frm">
          <table cellpadding="0" class="cf" width="100%">
            <tbody>
              
              
              <tr class="r7">
                <td class="r8"><span class="rc">Maximum page size:</span></td>
                <td class="r9"><table>
                    <tbody>
                      <tr class="rS" >
                        <td>Show
                          <select name="limitMail" class="rA">
                           <option >15</option>
                            <option >20</option>
                            <option >25</option>
                            <option >50</option>
                            <option >100</option>
                          </select>
                          conversations per page</td>
                      </tr>
                      <tr class="rS">
                        <td>Show
                          <select name="limitContact" class="rA" >
                            <option >50</option>
                            <option >100</option>
                            <option >250</option>
                          </select>
                          contacts per page</td>
                      </tr>
                    </tbody>
                  </table></td>
              </tr>
              <tr class="r7">
                <td class="r8"><span class="rc">Images:</span><br></td>
                <td class="r9"><table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input id="generalSettingImages1" name="generalSettingImages" type="radio" data-val="abc" value="Always display external images" /></td>
                        <td class="C6"><span class="rS">
                          <label for="generalSettingImages1">Always display external images</label>
                          </span> </td>
                      </tr>
                    </tbody>
                  </table>
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input id="generalSettingImages2" name="generalSettingImages"  type="radio" value="Ask before displaying external images"/></td>
                        <td class="C6"><span class="rS">
                          <label for="generalSettingImages2" >Ask before displaying external images</label>
                          </span></td>
                      </tr>
                    </tbody>
                  </table></td>
              </tr>
              
               <tr class="r7" guidedhelpid="desktop_notifications_row">
                <td class="r8"><span class="rc">Desktop Notifications:</span></td>
                <td class="r9">
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="generalSettingNotification1" name="generalSettingNotification" value="New mail notifications on"/></td>
                        <td class="C6"><span class="rS">
                          <label for="generalSettingNotification1">New mail notifications on</label>
                          </td>
                      </tr>
                    </tbody>
                  </table>
                
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio"  id="generalSettingNotification3" name="generalSettingNotification"  value="Mail notifications off"/></td>
                        <td class="C6"><span class="rS">
                          <label for="generalSettingNotification3" >Mail notifications off</label>
                          </span></td>
                      </tr>
                    </tbody>
                  </table></td>
              </tr>
              <tr class="r7">
                <td class="r8"><span class="rc">My picture:</span></td>
                <td class="r9">
                  <div  >
                    <table cellpadding="0" class="cf">
                      <tbody>
                        <tr>
                          <td class="set_img"><div onclick="uploadDP()" class="qC change_images"><span  class="rX sA ou" >Change picture</span></div>
                           <img height="80px" width="75px" src="data:image/jpg;base64,<%=set_img %>" onerror="getAltImage(this.id)" id="set_<%=id %>nomyimage"></td>
                          
                        </tr>
                        <tr>
                        <td>
                        <a style="cursor: pointer;" onclick="delImage()"> Remove Picture </a>
                        </td>
                        </tr>
                      </tbody>
                    </table>
                  </div></td>
              </tr>
              <tr class="r7" guidedhelpid="desktop_notifications_row">
                <td class="r8"><span class="rc">Preview Pane:</span></td>
                <td class="r9">
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="previewPane1" name="previewPane" value="Off view"/></td>
                        <td class="C6"><span class="rS">
                          <label for="previewPane1" >Full View</label>
                          </span></td>
                      </tr>
                    </tbody>
                  </table>
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="previewPane2"  name="previewPane" value="Vertical view"/></td>
                        <td class="C6"><span class="rS">
                          <label for="previewPane2" >Left View</label>
                          </span> </td>
                      </tr>
                    </tbody>
                  </table>
                </td>
              </tr>
              
               <tr class="r7" guidedhelpid="desktop_notifications_row">
                <td class="r8"><span class="rc">Compose Page:</span></td>
                <td class="r9">
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="composePage1" name="composePage" value="Full View"/></td>
                        <td class="C6"><span class="rS">
                          <label for="composePage1" >Full View</label>
                          </span></td>
                      </tr>
                    </tbody>
                  </table>
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="composePage2"  name="composePage" value="Popup View"/></td>
                        <td class="C6"><span class="rS">
                          <label for="composePage2" >Popup View</label>
                          </span> </td>
                      </tr>
                    </tbody>
                  </table>
                </td>
              </tr>
              
              
              
              <tr class="r7" guidedhelpid="desktop_notifications_row">
                <td class="r8"><span class="rc">BCC Field default open:</span></td>
                <td class="r9">
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="bccField1" name="bccField" value="Yes"/></td>
                        <td class="C6"><span class="rS">
                          <label for="bccField1" >Yes</label>
                          </span></td>
                      </tr>
                    </tbody>
                  </table>
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="bccField2"  name="bccField" value="No"/></td>
                        <td class="C6"><span class="rS">
                          <label for="bccField2" >No</label>
                          </span> </td>
                      </tr>
                    </tbody>
                  </table>
                </td>
              </tr>
              
              
              <tr class="r7" guidedhelpid="desktop_notifications_row">
                <td class="r8"><span class="rc">When reply/forward place signature above message:</span></td>
                <td class="r9">
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="signatureInReply1" name="signatureInReply" value="true"/></td>
                        <td class="C6"><span class="rS">
                          <label for="signatureInReply1" >Yes</label>
                          </span></td>
                      </tr>
                    </tbody>
                  </table>
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="signatureInReply2"  name="signatureInReply" value="false"/></td>
                        <td class="C6"><span class="rS">
                          <label for="signatureInReply2" >No</label>
                          </span> </td>
                      </tr>
                    </tbody>
                  </table>
                </td>
              </tr>
              
              
              
              <tr class="r7" guidedhelpid="desktop_notifications_row">
                <td class="r8"><span class="rc">Chat Service:</span></td>
                <td class="r9">
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="chatService1" name="chatService" value="true"/></td>
                        <td class="C6"><span class="rS">
                          <label for="chatService1" >Enable</label>
                          </span></td>
                      </tr>
                    </tbody>
                  </table>
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6"><input type="radio" id="chatService2"  name="chatService" value="false"/></td>
                        <td class="C6"><span class="rS">
                          <label for="chatService2" >Disable</label>
                          </span> </td>
                      </tr>
                    </tbody>
                  </table>
                </td>
              </tr>
              
              <tr class="r7" guidedhelpid="save_changes_row">
                <td colspan="2"><div class="rU general_save" role="navigation" style="width: 200px;">
                <div onclick="saveGen()" style="cursor: pointer;" class="search_button account_save"> Save </div>
                   
                    &nbsp;&nbsp;
                   <!--  <button class="gengral_cancel search_button">Cancel</button> -->
                  </div></td>
              </tr>
            </tbody>
          </table>
          </form>
          <script type="text/javascript">
          function saveGen() {
        	  document.getElementById('action_gif').style.display= 'block';
        	  var data = JSON.stringify($('#frm').serializeArray());
        	
        	  $.ajax({
  				type : "get",
  				url : "${pageContext.request.contextPath}/saveGeneralSetting",
  				data : {
  					'data':data
  				},
  				contentType : "application/json",
  				success : function(data) {
  					// alert(data);
  					if(data=="<$expire$>")
					{
					location.href ="${pageContext.request.contextPath}/inbox";
						}
  					try
  					{
  					var n = data.indexOf("<$set$>")
  					if(n>=0)
  						{
  						 var res = data.split("<$set$>");
  						 var lmt=res[0];
  						var pn=res[1];
  						var cp=res[2];
  						$("#hid_limitMail_after_set").val(lmt);
  						$("#hid_previewPane_after_set").val(pn); 
  						$("#hid_composePage_after_set").val(cp);
  						}
  					}
  					catch (e) {
						// TODO: handle exception
					}
  					
  				var success = generate('alert');
							 $.noty.setText(success.options.id, "Saved successfully");
							 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);	
  				document.getElementById('action_gif').style.display= 'none';
  				}
  			});
		}
          </script>
        </div>
        <div id="view2">
          <table width="100%" id="fldr_tbl" class="cf alO new_folder_append" >
            <tbody>
              <tr>
                <td class="r8 folder_width alL">Folders</td>
                <td class="alL new_show_hide">Subscribed</td>
                <td class="alL new_show_hide">Messages</td>
                 <td class="alL new_show_hide">Delete &nbsp;/ &nbsp;Rename</td>
                 <!-- <td class="alL new_show_hide">Manage Sharing</td> -->
              </tr>
              
              
              
              <%


  		  for(MailImapFolders fd : imapfldrlst)
  			{
  			List<MailImapFolders> list=new ArrayList(); 
  			 list.add(fd);
  			
  			if(fd.isHasChild())
  			{
  				List<MailImapFolders> list1=lFiles(list, fd.getFolderFullName(), host, id, pass, webmailClient);
  				
  				for(int i=0;i<list1.size();i++)
  				{
  					String nm=list1.get(i).getFolderFullName();
  					String arr[]=nm.split("\\.");
  					int l=arr.length;
  					String sps="";
  					 for(int j=1;j<l;j++)
  					{
  						sps+="&nbsp;&nbsp;&nbsp;&nbsp;";
  					} 
  					all_fldr_lst.add(list1.get(i).getFolderFullName());
 					String prm=list1.get(i).getFolderFullName();
					String fnm=list1.get(i).getFolderName();
					String fnm_tmp=fnm;
					if(fnm.length()>25)
					{
						fnm_tmp=fnm.substring(0, 24)+"....";
					}
					String chk="";
     				if(list1.get(i).isIsSubs())
					{
						 chk="checked='checked'";
					}
     				
     				String msgcnt="";
     				if(list1.get(i).getFolderMode()!=2)
     				{
     					msgcnt=list1.get(i).getMessageCount().toString();
     				}
     				if(prm.equalsIgnoreCase("Inbox") || prm.equalsIgnoreCase("Drafts") || prm.equalsIgnoreCase("Sent") || prm.equalsIgnoreCase("Junk") || prm.equalsIgnoreCase("Trash") || prm.equalsIgnoreCase("Archive")  )
         			{
  					%>
  	  	  			
  	  	  			<tr id="tr_<%=prm %>" class="To">
     	                <td class="alT"><div id="con_<%=prm %>" title="<%=fnm %>" style="cursor: pointer; width: 100%;" class="al6"><%=sps+fnm_tmp %></div></td>
     	                <td class="alQ"><input type="checkbox" disabled="disabled" checked="checked"  /></td>
     	                <td class="alQ"><%=msgcnt %></td>
     	              	<td class="alQ"></td>
     	              	<td class="alQ"></td>
     	                </tr>
  	  	  			<%
         			}
     				else
     				{
     					%>
      	  	  			
      	  	  			<tr id="tr_<%=prm %>" class="To">
         	                <td class="alT"><div id="con_<%=prm %>" title="<%=fnm %>" style="cursor: pointer; width: 100%;" class="al6"><%=sps+fnm_tmp %></div></td>
         	                <%if(list1.get(i).getFolderMode()==2) 
         	                {%>
         	                <td class="alQ"><input type="checkbox"  id="chk_<%=prm %>" disabled="disabled"/></td>
         	                <td class="alQ"></td>
         	              	<td class="alQ"></td>
         	              	<td class="alQ"></td>
         	                <%}
         	                else
         	                {
         	                %>
         	                 <td class="alQ"><input type="checkbox"  <%=chk %> onclick="folderSubscribe(this.id)" id="chk_<%=prm %>" /></td>
         	                <td class="alQ"><%=msgcnt %></td>
         	              	<td class="alQ">
         	              	
         	              	<img id="del_<%=prm %>" onclick="deleteFolder(this.id)" style="cursor: pointer;" src="images/tool.png">
         	              	&nbsp;/&nbsp;
         	              	<img id="rename_<%=prm %>" onclick="renameFolderOpen(this.id)"  style="cursor: pointer;" height="17px" width="20px" src="images/edit_fldr.png">
         	              	</td>
         	              	<%
         	              	//if(!prm.startsWith("shared."))
         	              	//{
         	              	%>
         	              	<%-- <td class="alQ"><img id="share_<%=prm %>" onclick="manageFolderSharing(this.id)" class="img_share" src="images/msharing.png">
         	              	</td> --%>
         	                <%
         	               // }
         	                }
         	              	%>
         	                
         	                </tr>
      	  	  			<%
     				}
  				}


  			}
  			else
  			{
  					all_fldr_lst.add(fd.getFolderFullName());
					String prm=fd.getFolderFullName();
					String fnm=fd.getFolderName();
					String fnm_tmp=fnm;
					if(fnm.length()>25)
					{
						fnm_tmp=fnm.substring(0, 24)+"....";
					}
					String chk="";
     				if(fd.isIsSubs())
					{
						 chk="checked='checked'";
					}
     				String msgcnt="";
     				if(fd.getFolderMode()!=2)
     				{
     					msgcnt=fd.getMessageCount().toString();
     				}
     				if(prm.equalsIgnoreCase("Inbox") || prm.equalsIgnoreCase("Drafts") || prm.equalsIgnoreCase("Sent") || prm.equalsIgnoreCase("Junk") || prm.equalsIgnoreCase("Trash") || prm.equalsIgnoreCase("Archive")  )
         			{
  					%>
  	  			
						<tr id="tr_<%=prm %>" class="To">
     	                <td class="alT"><div id="con_<%=prm %>" title="<%=fnm %>" style="cursor: pointer; width: 100%;" class="al6"><%=fnm_tmp %></div></td>
     	                <td class="alQ"><input type="checkbox" checked="checked"  disabled="disabled"  /></td>
     	                <td class="alQ"><%=msgcnt %></td>
     	              	<td class="alQ"></td>
     	              	<td class="alQ"></td>
     	                </tr>

  	  				<%}
     				else
     				{
     				%>
     				<tr id="tr_<%=prm %>" class="To">
     	                <td class="alT"><div id="con_<%=prm %>" title="<%=fnm %>" style="cursor: pointer; width: 100%;" class="al6"><%=fnm_tmp %></div></td>
     	                <%if(fd.getFolderMode()==2) 
     	                {
     	                %>
     	                <td class="alQ"><input type="checkbox" disabled="disabled" id="chk_<%=prm %>" /></td>
     	               <td class="alQ"></td>
     	              	<td class="alQ"></td>
     	              	<td class="alQ"></td>
     	                <%}
     	                else
     	                {
     	                %>
     	                <td class="alQ"><input type="checkbox"  <%=chk %> onclick="folderSubscribe(this.id)" id="chk_<%=prm %>" /></td>
     	                <td class="alQ"><%=msgcnt %></td>
     	              	<td class="alQ">
     	              	
     	              	<img id="del_<%=prm %>" onclick="deleteFolder(this.id)" style="cursor: pointer;" src="images/tool.png">
     	              	&nbsp;/&nbsp;
     	              	<img id="rename_<%=prm %>" onclick="renameFolderOpen(this.id)"  style="cursor: pointer;" height="17px" width="20px" src="images/edit_fldr.png">
     	              	</td>
     	              	<%
     	              	//if(!prm.startsWith("shared."))
     	              	//{
     	              	%>
     	                <%-- 	<td class="alQ"><img id="share_<%=prm %>" onclick="manageFolderSharing(this.id)" class="img_share" src="images/msharing.png">
         	              	</td> --%>
     	                <%
     	               // } 
     	                }
     	                %>
     	                
     	                </tr>
     				<%	
     				}
  			}
  			}
  		  
  		

%>
              </tbody>
              </table>

             <table class="table_border">
             <tbody>
              <tr>
                <td class="al0" colspan="5"><div class="">
                    <button  class="alZ new_folder search_button gengral_cancel_1">Create Folder</button>
                  </div></td>
              </tr>

            </tbody>
          </table>
        </div>
        
        <div id="view3">
        
        
        <%
       String mail="";
		String fn="";
		String mob="";
		String hmob="";
		String tel="";
		String organiz="";
		String addr="";
		String addrpcode="";
		String back_mail=""; 
		String sig="";
		String sig_st="";
		String repto="";
		String dob="";
		String anni="";
		String doj="";
		String plang="";
		Date dt_dob=null;
		Date dt_doj=null;
		Date dt_anni=null;
		String accountSetting="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
        	 String username="mail="+id+","+ldapbase;
 		    System.out.println("@@@@@@@@@@@@@@@@@@ ldap user="+username);
 		    Hashtable env = new Hashtable();
 		    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
 		    env.put(Context.PROVIDER_URL, ldapurl); // LDAP host and base

 		    // LDAP authentication options
 		    env.put(Context.SECURITY_AUTHENTICATION, "simple");
 		    env.put(Context.SECURITY_PRINCIPAL, username);
 		    env.put(Context.SECURITY_CREDENTIALS, pass);

 		    DirContext  ctx = new InitialDirContext(env);
       
		    System.out.println("^^^^^^^^^^^^^^^^^ctx="+ctx);
			SearchControls constraints = new SearchControls(); 
			constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			String attrList[] = {"accountSetting","preferredLanguage","anniversary","dateOfJoining","dateOfBirth","backupMailAddress","signature","signatureStatus","replyTo","cn","mail","mobile","homePhone","telephoneNumber","postalAddress","postalCode","jpegPhoto","company"}; 
			constraints.setReturningAttributes(attrList);
			NamingEnumeration results =ctx.search(ldapbase,"mail="+id, constraints);
			int f=0;
			int x=0;
			
			while (results.hasMore()) {
				f=1;
				SearchResult si =(SearchResult)results.next();
				String ck=si.getName();
				System.out.println("<br><br><br>"+ck);
				    Attributes attrs = si.getAttributes();
				

				    if (attrs == null) {
				       System.out.println("   No attributes");
				        continue;
				    }
				    
				    
				    
				    	NamingEnumeration ae = attrs.getAll(); 
				    	while (ae.hasMoreElements()) {
				        Attribute attr =(Attribute)ae.next();
				        String lid = attr.getID();
				        Enumeration vals = attr.getAll();
				        while (vals.hasMoreElements())
				        {
				        String str= vals.nextElement().toString();
				       
				        if(lid.equalsIgnoreCase("accountSetting"))
				        {
				        	accountSetting=str;
				        }
				        else if(lid.equalsIgnoreCase("cn"))
				        {
				        fn=str;
				        }
				        else if(lid.equalsIgnoreCase("preferredLanguage"))
				        {
				        	plang=str;
				        }
				        else if(lid.equalsIgnoreCase("backupMailAddress"))
				        {
				        	back_mail=str;
				        }
				        else if(lid.equalsIgnoreCase("signature"))
				        {
				        sig=str;
				        }
				        else if(lid.equalsIgnoreCase("signatureStatus"))
				        {
				        	System.out.println("!!!!!!!!!!!!!!sig st="+str);
				        sig_st=str;
				        }
				        else if(lid.equalsIgnoreCase("replyTo"))
				        {
				        repto=str;
				        }
				        else if(lid.equalsIgnoreCase("anniversary"))
				        {
				        	String yy="";
				            String mm="";
				            String dd="";
				            
				            for(int i=0;i<8;i++)
				              {
				              char ss=str.charAt(i);
				              if(i<=3)
				              {
				              yy=yy+ss;
				              }
				              else if(i<=5)
				              {
				              mm=mm+ss;
				              }
				              else
				              {
				              dd=dd+ss;
				              }
				              }
				            anni=""+yy+"-"+mm+"-"+dd;
				        }
				        else if(lid.equalsIgnoreCase("dateOfJoining"))
				        {
				        	
				        	String yy="";
				            String mm="";
				            String dd="";
				            
				            for(int i=0;i<8;i++)
				              {
				              char ss=str.charAt(i);
				              if(i<=3)
				              {
				              yy=yy+ss;
				              }
				              else if(i<=5)
				              {
				              mm=mm+ss;
				              }
				              else
				              {
				              dd=dd+ss;
				              }
				              }
				            doj=""+yy+"-"+mm+"-"+dd;
				       
				        }
				        else if(lid.equalsIgnoreCase("dateOfBirth"))
				        {
				        
				        
				        String yy="";
				           String mm="";
				           String dd="";
				           
				           for(int i=0;i<8;i++)
				             {
				             char ss=str.charAt(i);
				             if(i<=3)
				             {
				             yy=yy+ss;
				             }
				             else if(i<=5)
				             {
				             mm=mm+ss;
				             }
				             else
				             {
				             dd=dd+ss;
				             }
				             }
				           dob=""+yy+"-"+mm+"-"+dd;
				       
				        }
				        else if(lid.equalsIgnoreCase("mail"))
				        {
				        mail=str;
				        }
				         else if(lid.equalsIgnoreCase("mobile"))
				        {
				        mob=str;
				        }
				        else if(lid.equalsIgnoreCase("homePhone"))
				        {
				        hmob=str;
				        }
				        else if(lid.equalsIgnoreCase("telephoneNumber"))
				        {
				        tel=str;
				        }
				        else if(lid.equalsIgnoreCase("postalAddress"))
				        {
				        addr=str;
				        }
				        else if(lid.equalsIgnoreCase("postalCode"))
				        {
				        	addrpcode=str;
				        }
				        else if(lid.equalsIgnoreCase("company"))
				        {
				        System.out.println("!!!!!!!!!!!!!!!ogr="+str);
				        organiz=str;
				        }
				       
				     }
				    }
			}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        } 
        %>
        <input type="hidden" id="accountSetting" value="<%=accountSetting.replace("\"", "'") %>" />
        
          <table cellpadding="0" class="cf" width="100%">
            <tbody>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Display Name</span></td>
                <td class="r9" width="70%"><input id="fn" value="<%=fn %>"  type="text" /></td>
              </tr>
               <tr class="r7 r_top">
                <td class="r8"><span class="rc">Email</span></td>
                <td class="r9" width="70%"><input  disabled="disabled" type="text" value="<%=id %>" /></td>
                </tr>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Organization</span></td>
                <td class="r9" width="70%"><input disabled="disabled" id="organiz" type="text" value="<%=organiz %>" /></td>
              </tr>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Reply-To</span></td>
                <td class="r9" width="70%"><input id="repto"  type="text" value="<%=repto %>"/></td>
              </tr>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Recovery Email</span></td>
                <td class="r9" width="70%"><input id="back_mail" type="text" value="<%=back_mail %>" /></td>
              </tr>
               <tr class="r7 r_top">
                <td class="r8"><span class="rc">Date of Birth</span></td>
                <td class="r9" width="70%">
               
                <input value="<%=dob %>" id="dob" type="text" class="cal_icon_1 popupDatepicker" /></td>
              </tr>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Date of Joinning</span></td>
                <td class="r9" width="70%">
                
                <input disabled="disabled"  type="text" id="doj" value="<%=doj %>" /></td>
              </tr>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Anniversary</span></td>
                <td class="r9"  width="70%">
                 
                <input  type="text" id="anni" value="<%=anni %>" class="cal_icon_1 popupDatepicker"/></td>
              </tr>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Postal Address</span></td>
                <td class="r9" width="70%"><textarea rows="6" cols="70" id="addr"><%=addr %></textarea></td>
              </tr>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Postal Code</span></td>
                <td class="r9" width="70%"><input type="text" id="addrpcode" value="<%=addrpcode %>" /></td>
              </tr>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Mobile</span></td>
                <td class="r9" width="70%"><input type="text" id="mob" value="<%=mob %>"/></td>
              </tr>
               <tr class="r7 r_top">
                <td class="r8"><span class="rc">Home Mobile</span></td>
                <td class="r9" width="70%"><input type="text" id="hmob" value="<%=hmob %>"/></td>
              </tr>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Telephone Number</span></td>
                <td class="r9" width="70%"><input type="text" id="tel" value="<%=tel %>"/></td>
              </tr>
               
                <tr class="r7">
                <td class="r8"><span class="rc">Signature</span></td>
                <td class="r9"><table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6">
                        <%
                        String sigchk="";
                        if(sig_st.equalsIgnoreCase("true"))
                        {
                        	sigchk="checked='checked'";
                        }
                        %>
                        <input type="checkbox" <%=sigchk %> id="sigchk" name="signature" >
                        </td>
                        <td class="C6"><span class="rS">
                          <label >Auto Add Signature</label>
                          </span></td>
                      </tr>
                    </tbody>
                  </table>
                  <table cellspacing="0" cellpadding="0" class="cf ntxFbe">
                    <tbody>
                      <tr class="C7">
                        <td class="C6">
                       
                         <div class="written_space">
  	               <textarea cols="100" id="sig" name="sig" rows="10" class="text_plugins for_plaintext"><%=sig %></textarea>
          
          </div>
                        </td>
                        
                      </tr>
                    </tbody>
                  </table></td>
              </tr>
              
              <tr class="r7">
                <td class="r8"><span class="rc">Language</span></td>
                <td class="r9"><div>
                 <%
                              String ru="";
                              String tw="";
                              String br="";
                              String fr="";
                              String zhcn="";
                              String fi="";
                              String kr="";
                              String it="";
                              String cz="";
                              String nl="";
                              String lt="";
                              String us="";
                              String de="";
                              String es="";
                              String pl="";
                              String si="";
                              if(plang.equalsIgnoreCase("ru_RU"))
                              {
                              ru="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("zh_TW"))
                              {
                              tw="selected='selected'";
                              }
                              else
                              if(plang.equalsIgnoreCase("pt_BR"))
                              {
                              br="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("fr_FR"))
                              {
                              fr="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("zh_CN"))
                              {
                              zhcn="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("fi_FI"))
                              {
                              fi="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("it_IT"))
                              {
                              it="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("ko_KR"))
                              {
                              kr="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("nl_NL"))
                              {
                              nl="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("sr_LT"))
                              {
                              lt="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("de_DE"))
                              {
                              de="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("es_ES"))
                              {
                              es="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("pl_PL"))
                              {
                              pl="selected='selected'";
                              }
                              else if(plang.equalsIgnoreCase("sl_SI"))
                              {
                              si="selected='selected'";
                              }
                              else
                              {
                              us="selected='selected'";
                              }
                             
                               %>
                    <select id="plang" name="plang">
        <option value="ru_RU" <%=ru %>>Русский</option>
        
            <option value="zh_TW" <%=tw %>>繁體中文</option>
        
            <option value="pt_BR" <%=br %>>Portuguese (Brazilian)</option>
        
            <option value="fr_FR" <%=fr %>>Français</option>
        
            <option value="zh_CN" <%=zhcn %>>简体中文</option>
        
            <option value="fi_FI" <%=fi %>>Finnish (Suomi)</option>
        
            <option value="ko_KR" <%=kr %>>Korean</option>
        
            <option value="it_IT" <%=it %>>Italiano</option>
        
            <option value="cs_CZ" <%=cz %>>Čeština</option>
        
            <option value="nl_NL" <%=nl %>>Netherlands</option>
        
            <option value="sr_LT" <%=lt %>>Serbian (Latin)</option>
        
            <option value="en_US" <%=us %>>English (US)</option>
        
            <option value="de_DE" <%=de %>>Deutsch (Deutsch)</option>
        
            <option value="es_ES" <%=es %>>Español</option>
        
            <option value="pl_PL" <%=pl %>>Polski</option>
        
            <option value="sl_SI" <%=si %>>Slovenian</option>
        
    </select>
                   
                  </div></td>
              </tr>
              
              
              
              
              <tr class="r7 r_top">
                <td class="r8"><span class="rc"></span></td>
                <td class="r9" width="70%"> <div class="clear"></div>
          <div onclick="saveIdentities()" style="cursor: pointer;" class="search_button account_save"> Save </div></td>
              </tr>

            </tbody>
          </table>
          
        </div>
        <div id="view4" >
          <table cellpadding="0" class="cf" width="100%">
            <tbody>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Current Password</span></td>
                <td class="r9" width="50%"><input id="cur_pass" type="password" /></td>
                <td class="r8"> </td>
              </tr>
               <tr class="r7 r_top">
                <td class="r8"><span class="rc">New Password</span></td>
                <td class="r9" width="50%">
			<input name="np" id="password" required type="password"  onblur="setNCP()"  />
   		 	<input type="hidden" id="hid_ps" name="hid_ps" value="0" />
   		  <div class="ermsg" style="clear: both;float: left; margin-top:3px;" id="result"></div>
				</td>
                <td class="r8"> 
                 <div  style="height: 20px; margin-top: -20px;">
  <p >
       <span class="rc"> Need a random password?</span></p>
        <p style="margin-top: -10px;">
  
     
     <table><tbody><tr><td>   
        <input type="checkbox" id="setpass" value="1" name="setpass" onclick="setPath(this.id)"/>
         </td><td style="   padding-bottom: 4px;">	
		<%Random ran=new Random();
        int rno=ran.nextInt(100000);
        int dg=rno%10;
        String x = Integer.toString(rno);
        char c1=(char)(70+dg);
        char c2=(char)(110+dg);
		x = x.substring(0, 1) + c1+(char)(80+dg) + x.substring(1, x.length());
		x = x.substring(0, x.length()-1) + c2 + x.substring(x.length()-1, x.length());
         out.print(x); %>
         <input type="hidden" name="rpass" id="rpass" value="<%=x %>"/>
         </td></tr></tbody></table>
		</p>
    </div>
                </td>
              </tr>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Confirm New Password</span></td>
                <td class="r9" width="50%">
                <input name="cnp" id="cnp" required type="password"  onblur="checkNCP(this.value)"  />
        		<div style="clear: both;float: left; margin-top:3px;" id="cnp_msg1"></div> 
                </td>
                <td class="r8"> </td>
              </tr>
             <tr class="r7 r_top">
                <td class="r8"><span class="rc"></span></td>
                <td class="r9" width="50%">
                 <div class="clear"></div>
          <div class="search_button account_save" onclick="changePass()"> Save </div>
                </td>
                <td class="r8"> </td>
              </tr>

            </tbody>
          </table>
          
        </div>
        <div id="view5">
        <table cellpadding="0" class="cf filter_data" width="100%" role="list">
            <tbody>
              <tr class="r7" role="listitem">
                <td class="CZ" colspan="3"><b>The following filters are applied to all incoming mail:</b></td>
              </tr>
           
              <%
              
              ManageSieveClient client = (ManageSieveClient)request.getAttribute("sieveclient");
               ManageSieveResponse    resp= (ManageSieveResponse)request.getAttribute("sieveManageResponse");
              List<SieveScript> scripts=(List<SieveScript>)request.getAttribute("scriptList");
              String scriptTotal="";
              // Dump the scripts to STDOUT
              for (SieveScript ss : scripts) {
                  
                  if (ss.isActive()) {
                      System.out.print(" (active)");
                  
                  resp = client.getScript(ss);
                  scriptTotal=ss.getBody();
                  System.out.println("is name of script "+ss.getName());
                  System.out.println("is body of script "+ss.getBody());
                  String[] scriptArray=ss.getBody().split("#");
                  System.out.println("no of filteres is "+scriptArray.length);
                  int i=0;
                  %>
                  <div style="display:none;" id="requireFilter"><%=scriptArray[0] %></div>
                  <%
                  for(i=1;i<scriptArray.length;i++){
                      String tobeShown="";
                      boolean flag=false;
                      boolean show=false;
                      String tobeShownAction="";
                      boolean flagAction=false;
                      boolean showAction=false;
                  %>
              <tr class="r7">
             <!--  <td class="qX r5"><input type="checkbox"></td>
               -->
              
              <%for(int j=0;j<scriptArray[i].length();j++){
                  if(scriptArray[i].charAt(j)==']'){
                      flag=false;
                      show=true;
                  }
                  if(flag){
                      tobeShown+=scriptArray[i].charAt(j);
                  }
                 
                  if(scriptArray[i].charAt(j)=='['){
                      tobeShown+=" : ";
                      flag=true;
                      show=false;
                  }
                  
                  
                  if(show){
                        %>
                     
                       <%
                       show=false;
                  }
                  
                  if(scriptArray[i].charAt(j)=='}'){
                      flagAction=false;
                      showAction=true;
                  }
                  if(flagAction){
                      tobeShownAction+=scriptArray[i].charAt(j);
                  }
                 
                  if(scriptArray[i].charAt(j)=='{'){
                      tobeShownAction="";
                      flagAction=true;
                      showAction=false;
                  }
                  if(showAction){
                        %>
                      
                       <%
                       showAction=false;
                  }
                  }
                  
                  
                  if(scriptArray[i].indexOf("body :text :contains")>=0)
                  {
                	 int j= tobeShown.lastIndexOf(':');
                	 if(j>=0)
                	 {
                		 tobeShown= tobeShown.substring(0,j)+": \"Body Contains\" "+tobeShown.substring(j);
                	 }
                	 else
                	 {
                		 tobeShown="\"Body Contains\" "+tobeShown;
                	 }
                	
                  }
                  
                  
                      if(scriptArray[i].indexOf("size :over")>=0){
                      int indexofSubject=scriptArray[i].indexOf("size :over");
                      int k=indexofSubject;
                          while(scriptArray[i].charAt(k)!='K'){
                              k++;
                          }
                          tobeShown+=", "+scriptArray[i].substring(indexofSubject,k)+"KB";
                          
                      }
                      if(scriptArray[i].indexOf("size :under")>=0){
                          int indexofSubject=scriptArray[i].indexOf("size :under");
                          int k=indexofSubject;
                              while(scriptArray[i].charAt(k)!='K'){
                                  k++;
                              }
                              tobeShown+=", "+scriptArray[i].substring(indexofSubject,k)+"KB";
                              
                          }
                  
                  String idscript="beforeScriptFilter"+i;
                 
                  %>
                 <td class="qV r5" colspan="2"><span>Matches:</span><div class="filter_subject_new"><%=tobeShown.replace("/n", "") %></div><br>
                 <!--  <td class="qV r5"> --><span class="qW">Action:</span>
                  <% tobeShownAction=tobeShownAction.replace("$label1", "Important");
                  tobeShownAction=tobeShownAction.replace("$label2", "Work");
                  tobeShownAction=tobeShownAction.replace("$label3", "personal");
                  tobeShownAction=tobeShownAction.replace("$label4", "todo");
                  tobeShownAction=tobeShownAction.replace("$label5", "later");
                
                  
                  %>
                  <div class="filter_subject_mid"> <%=tobeShownAction.replace("addflag", "Mark as ").replace("setflag", "Mark as ").replace("fileinto", "Move to").replace("\\\\Seen","Read").replace("\\\\Flagged","Flagged").replace("/n", "") %>
                  <div style="display: none;" id='<%=idscript %>' ><%=scriptArray[i]%></div>
                  </div></td>
                    
                        
                     
                       
                     <td class="qS r5 edite_and_detele">
                     <span class="sA filter_edite" id="edit,<%=i %>" onclick="editFilter(this.id)" >edit</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="sA filter_delete" id="<%=i %>" onclick="deleteFilter(this.id)" >delete</span></td></tr>
                  
                  <%
                  
                  }
                  
                  %>
                  
                   
                  <div style="display: none;" id='countFilter' ><%=i%></div>
                  <%
                  if (!resp.isOk()) {
                      throw new IOException("Could not get body of script [" + ss.getName() + "]: " + resp.getMessage());
                  }
               //   System.out.println(ss.getBody());
                  %>
                
                  <%
                  }
                  }
              
              %>
            
              </tbody>
              
              </table>
               <div style="display: none;" id="beforeScriptFilter"><%=scriptTotal%></div>
              <script type="text/javascript">
              function deleteFilter(delFilter){
                  var count=$('#countFilter').html();
               //   alert($('#countFilter').html());
                //  alert("beforeScriptFilter"+delFilter);
                  var bodyScript=$('#requireFilter').html();
                  var bodyMaking='';
                //  alert(bodyScript);
                  for(var i=1;i<count;i++){
                	  if(i==2 && delFilter==1){
                    	//  alert("else");
                         bodyMaking=  $("#beforeScriptFilter"+(i)).html();
                        bodyScript+="#"+(i-1)+""+bodyMaking.substring(1).replace('elsif','if');
                      }
                	  
                	  else if(i!=delFilter ){
                    	//  alert("if");
                         
                         bodyMaking=  $("#beforeScriptFilter"+i).html();
                          
                          if(i>delFilter){
                              
                              bodyScript+="#"+(i-1)+""+bodyMaking.substring(1);
                          }else{
                              bodyScript+="#"+(i)+""+bodyMaking.substring(1);
                          }
                      
                          }
                      
                   //   alert("bodyMaking-->"+bodyMaking);
                   //   alert("bodyScript-->"+bodyScript);
                      }
              //  alert("*****"+bodyScript);
          uploadScript(bodyScript);
                  
              }
              </script>
              
              <script type="text/javascript">
              function editFilter(editid){
                clearAllTags();
                var filterId= editid.split(",");
                var  bodyMaking=  $("#beforeScriptFilter"+filterId[1]).html();
                var delFilter=filterId[1];
               // alert('hi');
                //alert(bodyMaking);
                if(bodyMaking.indexOf('Subject')>=0){
                    var indexofSubject=bodyMaking.indexOf('["Subject"]')+14;
                    var i=indexofSubject;
                    while(bodyMaking.charAt(i)!='"'){
                        i++;
                    }
                    $('#subjectFilter').val(bodyMaking.substring(indexofSubject,i));
                    
                }
              //  alert('hi');
                if(bodyMaking.indexOf('From')>=0){
                    var indexofSubject=bodyMaking.indexOf('["From"]')+11;
                    var i=indexofSubject;
                    while(bodyMaking.charAt(i)!='"'){
                        i++;
                    }
                    $('#fromFilter').val(bodyMaking.substring(indexofSubject,i));
                }
                if(bodyMaking.indexOf('To')>=0){
                    var indexofSubject=bodyMaking.indexOf('["To"]')+9;
                    var i=indexofSubject;
                    while(bodyMaking.charAt(i)!='"'){
                        i++;
                    }
                    $('#toFilter').val(bodyMaking.substring(indexofSubject,i));
                }
                if(bodyMaking.indexOf('body')>=0){
                    var indexofSubject=bodyMaking.indexOf('body :text :contains')+23;
                    var i=indexofSubject;
                    while(bodyMaking.charAt(i)!='"'){
                        i++;
                    }
                    $('#containsFilter').val(bodyMaking.substring(indexofSubject,i));
                    
                }
                if(bodyMaking.indexOf('size :over')>=0){
                    var indexofSubject=bodyMaking.indexOf('size :over')+11;
                    var i=indexofSubject;
                    while(bodyMaking.charAt(i)!='K'){
                        i++;
                    }
                    $('#sz_filter').val("greater than");
                    $('#sizeFilter').val(bodyMaking.substring(indexofSubject,i));
                    
                }
                if(bodyMaking.indexOf('size :under')>=0){
                    var indexofSubject=bodyMaking.indexOf('size :under')+12;
                    var i=indexofSubject;
                    while(bodyMaking.charAt(i)!='K'){
                        i++;
                    }
                    $('#sz_filter').val("less than");
                    $('#sizeFilter').val(bodyMaking.substring(indexofSubject,i));
                    
                }
                if(bodyMaking.indexOf('"\\\\Seen"')>=0){
                    checkCheckBox('seenFilter');
                }
                if(bodyMaking.indexOf('"\\\\Flagged"')>=0){
                    checkCheckBox('staritFilter');
                }
                var bodyMak = bodyMaking.substring(bodyMaking.indexOf('"\\\\Flagged"')+1);
                if(bodyMak.indexOf('addflag')>=0){
                	
                    checkCheckBox('applyLabelFilter');
                    var indexofSubject=bodyMak.indexOf('addflag')+9;
                    var i=indexofSubject;
                    while(bodyMak.charAt(i)!='"'){
                        i++;
                    }
                    $('#labelFilter').val(bodyMak.substring(indexofSubject,i));
                    
                    
                    
                }
                else
                	{
                	if(bodyMaking.indexOf('setflag "\\\\Seen"')<0 && bodyMaking.indexOf('setflag "\\\\Flagged"')<0 && bodyMaking.indexOf('setflag')>=0){
                        checkCheckBox('applyLabelFilter');
                        var indexofSubject=bodyMaking.indexOf('setflag')+9;
                        var i=indexofSubject;
                        while(bodyMaking.charAt(i)!='"'){
                            i++;
                        }
                        $('#labelFilter').val(bodyMaking.substring(indexofSubject,i));
                        
                        
                        
                    }
                	}
                if(bodyMaking.indexOf('fileinto')>=0){
                if(bodyMaking.indexOf('"Trash"')>=0){
                    checkCheckBox('deleteFilter');
                }else{
                        checkCheckBox('moveToFolderFilter');
                        var indexofSubject=bodyMaking.indexOf('fileinto')+10;
                        var i=indexofSubject;
                        while(bodyMaking.charAt(i)!='"')
                        {
                            i++;
                        }
                        $('#moveToFolderName').val(bodyMaking.substring(indexofSubject,i));
                    }
                }
                if(bodyMaking.indexOf('redirect')>=0){
                    
                            checkCheckBox('forwardToOtherFilter');
                            var indexofSubject=bodyMaking.indexOf('redirect :copy')+16;
                            var i=indexofSubject;
                            while(bodyMaking.charAt(i)!='"')
                            {
                                i++;
                            }
                            $('#forwardTo').val(bodyMaking.substring(indexofSubject,i));
                        
                    }
                
               $('.filter_buttom_create').attr('onclick','saveEditedFilter('+delFilter+')');
               $('.filter_buttom_create').html('Edit Filter');
                   
              }
              
              </script>
              <script type="text/javascript">
              function saveEditedFilter(delFilter){

                  var fromFilter=$('#fromFilter').val();
                     var toFilter=$('#toFilter').val();
                     var subjectFilter=$('#subjectFilter').val();
                     var containsFilter=$('#containsFilter').val();
                     var sizeFilter=$('#sizeFilter').val();
                    var beforeScriptFilter=$("#beforeScriptFilter").html();
                    var filterCondition="";
                    var arryRule=beforeScriptFilter.split("#");
                    
                      if(delFilter>1){
                         filterCondition=    '\n#'+delFilter+'\n elsif allof ('    ;
                     }else{ 
                            filterCondition="\n"+"#1\nif allof (";
                      }
                  
                     if(checkEmpty(fromFilter)){
                         filterCondition+='header :contains ["From"] ["'+fromFilter+'"],\n';
                     }
                     if(checkEmpty(toFilter)){
                         filterCondition+='envelope ["To"] ["'+toFilter+'"],\n';
                     }
                     if(checkEmpty(subjectFilter)){
                         filterCondition+='header :contains ["Subject"] ["'+subjectFilter+'"],\n';
                     }
                     if(checkEmpty(containsFilter)){

                         filterCondition+='body :text :contains ["'+containsFilter+'"],\n';
                     }
                     //alert(sizeFilter);
                     if(checkEmpty(sizeFilter)){
                          // if(window.findvalue=="greator than"){
                        	   if(sz_filter=="greater than"){
                               filterCondition+='size :over '+sizeFilter+'K,\n';
                           }
                           if(sz_filter=="less than"){
                               filterCondition+='size :under '+sizeFilter+'K,\n';
                           }}
                    
                     filterCondition=filterCondition.substring(0,filterCondition.length-2);
                     filterCondition+='\n){\n'
                        var filterAction="";
                        var flagtit="setflag";
                        
                    
                     if(checkedOrNot('seenFilter')){
                         filterAction+=flagtit+' "\\\\Seen";\n';
                         flagtit="addflag";
                     }
                     if(checkedOrNot('staritFilter')){

                         filterAction+=flagtit+' "\\\\Flagged";\n';
                         flagtit="addflag";
                     }

                     if(checkedOrNot('applyLabelFilter')){
                         if($('#labelFilter').val()!='0'){
                             filterAction+=flagtit+' "'+$('#labelFilter').val()+'";\n';
                             flagtit="addflag";
                         }
                     }
                     if(checkedOrNot('deleteFilter')){
                         filterAction+='fileinto "Trash";\n';
                     }
                     if(checkedOrNot('moveToFolderFilter')){
                         var address=$('#moveToFolderName').val();
                         if(checkEmpty(address)){
                             filterAction+='fileinto "'+address+'";\n';
                         }
                     }
                     if(checkedOrNot('forwardToOtherFilter')){
                         var address=$('#forwardTo').val();
                         if(checkEmpty(address)){
                             filterAction+='redirect :copy "'+address+'";\n';
                         }
                     }
                     
                     var scriptBody1=filterCondition+filterAction+"}";
                     
                    var count=$('#countFilter').html();
                   var bodyScript=$('#requireFilter').html();
                   var bodyMaking='';
                   for(var i=1;i<count;i++){
                       if(delFilter==i){
                          bodyScript+=scriptBody1;
                       }
                       else  if(i!=delFilter){
                          bodyMaking=  $("#beforeScriptFilter"+i).html();
                          bodyScript+="#"+(i)+""+bodyMaking.substring(1);
                           }
                       }
                    uploadScript(bodyScript);
              }
              </script>
              <script type="text/javascript">
              function checkCheckBox(chkboxid){
                  $('#'+chkboxid).prop('checked',true);
              }
              </script>
              <script type="text/javascript">
              function uncheckCheckBox(chkboxid){
                  $('#'+chkboxid).prop('checked',false);
              }
              </script>
              <script type="text/javascript">
              function clearAllTags(){
                      $('#subjectFilter').val('');
                      $('#fromFilter').val('');
                      $('#toFilter').val('');
                      $('#containsFilter').val('');
                      $('#sizeFilter').val('');
                      $('#moveToFolderFilter').val('Inbox');
                      $('#labelFilter').val('0');
                      
                      uncheckCheckBox('moveToFolderFilter');
                      uncheckCheckBox('seenFilter');
                      uncheckCheckBox('staritFilter');
                      uncheckCheckBox('deleteFilter');
                      uncheckCheckBox('moveToFolderFilter');
                      uncheckCheckBox('applyLabelFilter');
                  
              }
              </script>
              <script type="text/javascript">
              function clearAllTagsForCreate(){
            	  $('.filter_buttom_create').attr('onclick','createFilter()');
                 	$('.filter_buttom_create').html('Create Filter');
                    $('#subjectFilter').val('');
                    $('#fromFilter').val('');
                    $('#toFilter').val('');
                    $('#containsFilter').val('');
                    $('#sizeFilter').val('');
                    $('#moveToFolderFilter').val('Inbox');
                    $('#labelFilter').val('0');
                    $('#forwardTo').val('');
                    
                    uncheckCheckBox('moveToFolderFilter');
                    uncheckCheckBox('seenFilter');
                    uncheckCheckBox('staritFilter');
                    uncheckCheckBox('deleteFilter');
                    uncheckCheckBox('moveToFolderFilter');
                    uncheckCheckBox('applyLabelFilter');
                    uncheckCheckBox('forwardToOtherFilter');
                  
              }
              </script>
              <table width="100%">
              <tr role="listitem">
                <td class="rG" colspan="3"><span class="sA new_create_filter" onclick="clearAllTagsForCreate()">Create a new filter</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              </tr>
            </tbody>
          </table>
        </div>
        
         <div id="view6">
        <table cellpadding="0" class="cf" width="100%" style="    margin-top: 25px !important;">
            <tbody>
              
       
       
          
       
     <%
     SieveScript vacation=null;
     for (SieveScript ss : scripts) {
         
         if (ss.getName().indexOf("vacation")>=0) {
        	 resp = client.getScript(ss);
        	 vacation=ss;
         }}
     if(vacation!=null){
    	 System.out.println("body of vacation is : "+vacation.getBody());
    	 System.out.println("name of vacation is : "+vacation.getName());
    	 String vacatioBody=vacation.getBody();

         int indexofSubject=vacatioBody.indexOf("\"ge\" \"date\"")+13;
        int	 l=indexofSubject;
         while(vacatioBody.charAt(l)!='"'){
             l++;
         }
        

    	 
    	 
    	 
    	 
     %>
 
       <tr class="r7 r_top">
                <td class="r8"><span class="rc">Enable Out Of Office: </span> </td>
            <td class="r9" width="70%">
          <%if(vacation!=null) 
          {
          %>
          <input type="checkbox" name="vacationReminderStatus" checked="checked" class="cal_enable_checkbox" />
          <%}
          else
          {
          %>
          <input type="checkbox" name="vacationReminderStatus" class="cal_enable_checkbox" />
          <%}
          %>
          </td>
        </tr>
       
        <tr class="r7 r_top">
                <td class="r8"><span class="rc"> Start: </span></td>
           <td class="r9" width="70%"><input  type="text" id="vacationStartDate"   maxlength="250" class="vac_set Da cal_icon datepicker  cal_textbox popupDatepicker"   value="<%=vacatioBody.substring(indexofSubject,l)%>"></td>
        </tr>
        <%
        indexofSubject=vacatioBody.indexOf("\"le\" \"date\"")+13;
   	 l=indexofSubject;
    while(vacatioBody.charAt(l)!='"'){
        l++;
    }
        %>
        <tr class="r7 r_top">
                <td class="r8"><span class="rc"> End: </span></td>
        <td class="r9" width="70%"><input  type="text" id="vacationEndDate"  maxlength="250" class="vac_set Da cal_icon datepicker cal_textbox popupDatepicker"    value="<%=vacatioBody.substring(indexofSubject,l)%>" ></td>
        </tr>
         <%
        indexofSubject=vacatioBody.indexOf(":subject")+10;
   	 l=indexofSubject;
    while(vacatioBody.charAt(l)!=':'){
        l++;
    }
        %>
       <tr class="r7 r_top">
                <td class="r8"><span class="rc"> Subject: </span></td>
         <td class="r9" width="70%"><input id="vacationSubject" type="text"  maxlength="250" class="vac_set Da cal_subject cal_textbox"   value="<%=vacatioBody.substring(indexofSubject,l)%>" ></td>
        </tr>
        
         <%
         vacatioBody=   vacatioBody.substring(l+15);

       //  System.out.println("Bacha hua content : "+vacatioBody);
		 int jj=1;  
         while(vacatioBody.charAt(jj)!=';'){
		        jj++;
		    } 

        // System.out.println("Bacha hua content : "+vacatioBody.substring(1,jj-1));
        %>
       <tr class="r7 r_top">
                <td class="r8"><span class="rc">  Message: </span></td>
         <td class="r9" width="70%">
          <textarea rows="6" cols="45" id="vacationMessageBody" class="cal_message cal_textbox"  ><%= vacatioBody.substring(1,jj-1)%></textarea>
         </td>
        </tr>
        
         
       <%}else{ %>
       
             <tr class="r7 r_top">
                <td class="r8"><span class="rc"> Enable Out Of Office: </span></td>
         <td class="r9" width="70%">
         
          <input type="checkbox" name="vacationReminderStatus"  class="cal_enable_checkbox" />
         
          </td>
        </tr>
       
          <tr class="r7 r_top">
                <td class="r8"><span class="rc"> Start: </span></td>
         <td class="r9" width="70%"><input id="vacationStartDate" placeholder="YYYY-MM-DD" type="text"  maxlength="250"   class="vac_set Da cal_icon datepicker cal_textbox popupDatepicker" ></td>
        </tr>
      <tr class="r7 r_top">
                <td class="r8"><span class="rc"> End: </span></td>
         <td class="r9" width="70%"><input id="vacationEndDate" placeholder="YYYY-MM-DD" type="text"  maxlength="250"   class="vac_set Da cal_icon datepicker cal_textbox popupDatepicker"  ></td>
        </tr>
       <tr class="r7 r_top">
                <td class="r8"><span class="rc"> Subject: </span></td>
         <td class="r9" width="70%"><input id="vacationSubject"  type="text"  maxlength="250"   class="vac_set cal_textbox Da"></td>
        </tr>
        <tr class="r7 r_top">
                <td class="r8"><span class="rc"> Message: </span></td>
        <td class="r9" width="70%">
          <textarea rows="6" cols="45" id="vacationMessageBody"  class="cal_textbox"></textarea>
          </td>
        </tr>
        
        
       <%} %>
          
      
   
              
            <tr class="r7 r_top">
                <td class="r8"><span class="rc"> 
                    <button onclick="createVacation()" class="button_color search_button cal_save_changes" >Save Changes</button>
                    &nbsp;&nbsp;
                    <!-- <button class="button_color search_button cal_save_changes " >Cancel</button> -->
                  </span></td><td class="r9" width="70%"></td>
              </tr>
            </tbody>
          </table>
         
         
        </div>
        
        <div id="view7">
          <table width="100%" id="label_tbl" class="cf alO new_folder_append new_setting_tag" >
            <tbody>
              <tr>
                <td class="r8 folder_width alL">Labels</td>
                <td class="alL new_show_hide">Color</td>
                <td class="alL new_show_hide">Type</td>
                <td class="alL new_show_hide">Delete</td>
                 
              </tr>
              
              <tr  class="To">
     	                <td class="alT"><div  style="cursor: pointer; width: 100%;" class="al6">Important</div></td>
     	                <td class="alQ"><div class="tag_col" style="background-color:#FA6855; "></div></td>
     	                <td class="alQ">System Defined</td>
     	              	<td class="alQ"></td>
     	                </tr>
     	                
     	                 <tr  class="To">
     	                <td class="alT"><div  style="cursor: pointer; width: 100%;" class="al6">Work</div></td>
     	                <td class="alQ"><div class="tag_col"  style="background-color: #FC9449;"></div></td>
     	                <td class="alQ">System Defined</td>
     	              	<td class="alQ"></td>
     	                </tr>
     	                
     	                 <tr  class="To">
     	                <td class="alT"><div  style="cursor: pointer; width: 100%;" class="al6">Personal</div></td>
     	                <td class="alQ"><div class="tag_col"  style="background-color: #98C95D;"></div></td>
     	                <td class="alQ">System Defined</td>
     	              	<td class="alQ"></td>
     	                </tr>
     	                
     	                 <tr  class="To">
     	                <td class="alT"><div  style="cursor: pointer; width: 100%;" class="al6">To Do</div></td>
     	                <td class="alQ"><div class="tag_col"  style="background-color: #486BF7;"></div></td>
     	                <td class="alQ">System Defined</td>
     	              	<td class="alQ"></td>
     	                </tr>
     	                
     	                 <tr  class="To">
     	                <td class="alT"><div  style="cursor: pointer; width: 100%;" class="al6">Later</div></td>
     	                <td class="alQ"><div class="tag_col"  style="background-color: #BD48F7;"></div></td>
     	                <td class="alQ">System Defined</td>
     	              	<td class="alQ"></td>
     	                </tr>
  	  	  			
              <%

      		
             if(labset!=null && labset.length()>0)
             {
            	 String tagarr[]=labset.split("~");
            	 for(int i=0;i< tagarr.length; i++)
            	 {
            		 String tarr[]=tagarr[i].split("#");
            	 String tgnm=tarr[0];
            	 tgnm=tgnm.replace("_", " ");
      		%>
             <tr id="<%=tarr[0] %>" class="To">
             <td class="alT"><div style="cursor: pointer; width: 100%;" class="al6"><%=tgnm %></div></td>
             <td class="alQ"><div class="tag_col" style="background-color: #<%=tarr[1]  %>"></div></td>
             <td class="alQ">User Defined</td>
             <td class="alQ"><img id="deltag~<%=tarr[0] %>" style="cursor: pointer;" onclick="deleteSettingTag(this.id)" src="images/tool.png"></td>
             </tr>
<%} 
}%>
              </tbody>
              </table>
            

             <table class="table_border">
             <tbody>
              <tr>
                <td class="al0" colspan="5"><div class="">
                    <button  class="alZ search_button setting_tag_create button_color">Create Label</button>
                  </div></td>
              </tr>

            </tbody>
          </table>
        </div>
        
        
          <div id="view8">
          
          <%
  // String dn="";
  if( id!=null && !(id.equals("")) )
   {
   				 String acc="";
String mailfd="";
int fd=0; 
int infd=0;
String serv="";      
try
{


	 String username="mail="+id+","+ldapbase;
	    System.out.println("@@@@@@@@@@@@@@@@@@ ldap user="+username);
	    Hashtable env = new Hashtable();
	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, ldapurl); // LDAP host and base

	    // LDAP authentication options
	    env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    env.put(Context.SECURITY_PRINCIPAL, username);
	    env.put(Context.SECURITY_CREDENTIALS, pass);

	    DirContext  ctx = new InitialDirContext(env);

SearchControls constraints = new SearchControls(); 
constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
String attrList[] = {"enabledService","mailForwardingAddress"}; 
constraints.setReturningAttributes(attrList);
NamingEnumeration results =ctx.search(ldapbase,"mail="+id, constraints);
int f=0;

while (results.hasMore()) {
f=1;
    SearchResult sii =(SearchResult)results.next();
    String ck=sii.getName();
   System.out.println("<br><br><br>"+ck);
    Attributes attrs = sii.getAttributes();

    if (attrs == null) {
       out.println("   No attributes");
        continue;
    }
    
   
    
    NamingEnumeration ae = attrs.getAll(); 
    while (ae.hasMoreElements()) {
        Attribute attr =(Attribute)ae.next();
        String idd = attr.getID();
        Enumeration vals = attr.getAll();
        while (vals.hasMoreElements())
        {
        String str= vals.nextElement().toString();
        if(idd.equalsIgnoreCase("mailForwardingAddress"))
        {
        if(str.equalsIgnoreCase(id))
        {
        infd=1;
        }
        else
        {
        if(mailfd.equalsIgnoreCase(""))
        	{
        	mailfd=str;
        	}
        	else
        	{
        	mailfd=mailfd+","+str;
        	
        	}
        	}
        }
        if(idd.equalsIgnoreCase("enabledService"))
        {
        	if(str.equalsIgnoreCase("forward"))
        	{
        	fd=1;
        	}
        	if(serv.equalsIgnoreCase(""))
        	{
        	serv=str;
        	}
        	else
        	{
        	serv=serv+","+str;
        	
        	}
        }
        
           
           
           
           //System.out.println("  <br> "+idd + ":--- " +str);
           }
    }
    //NoOfUser nof=new NoOfUser();
 

}
if(f==0)
{
System.out.print("<tr class='grey'><td></td><td>Search not found</td></tr>");
}

}
catch(Exception e){System.out.print("<tr class='grey'><td></td><td>"+e+"</td></tr>");}

 







%>  
           <table cellpadding="0" class="cf" width="100%" style="    margin-top: 25px !important;">
            <tbody>
              <tr class="r7 r_top">
                <td class="r8"><span class="rc">Enable Mail Forwarding</span></td>
                <td class="r9" width="70%">
                  <input type="hidden" name="hid_serv" id="hid_serv" value="<%=serv %>" />
                <%
                            	if(fd==1)
                            	{
                            	 %>  
                                <input name="accf" checked="checked" type="checkbox" value="yes" class="checkbox"/>
                                <%}
                                else
                                {
                                 %>
                                <input name="accf"  type="checkbox" value="no" class="checkbox"/>
                                
                                <%} %>
                </td>
              </tr>
              
               <tr class="r7 r_top">
                <td class="r8"><span class="rc"> Save a copy in mailbox</span></td>
                <td class="r9" width="70%">
                
                 <%
                            	if(infd==1)
                            	{
                            	 %>  
                                <input name="inbox" checked="checked" type="checkbox" value="yes" class="checkbox"/>
                                <%}
                                else
                                {
                                 %>
                                <input name="inbox"  type="checkbox" value="no" class="checkbox"/>
                                
                                <%} %>
                </td>
              </tr>
              
               <tr class="r7 r_top">
                <td class="r8"><span class="rc"> Forward Mails to Address</span></td>
                <td class="r9" width="70%">
                <%
                            	String arr[]=mailfd.split(",");
     	                    String fmlfst="";
     	                    if(arr.length>=1)
     	                    	fmlfst=arr[0];
                            	 %> 
                            	 <table><tr><td>
                            	 <input  type="hidden" id="hid_cadd" name="hid_cadd" value="<%=arr.length %>" />
                                <input name="cadd1" id="cadd1" type="text" value="<%=fmlfst %>" placeholder="abc@example.com"  class="add_textbox" onblur="chkFMail(this.value,this.name)"/>
                               </td><td> &nbsp;<img style="cursor: pointer;    padding-bottom: 4px;" alt="" onclick="addCatchAdd()" src="images/plus.png" />
                               </td></tr></table>
                               <br>
                                                         
                                <div id="fooBar1" class="ermsg">
                            <%
                            for(int i=1;i<arr.length;i++)
                            {
                            int j=i+1;
                            String for_id="cadd"+j;
                            %>
                            <input name="<%=for_id %>" id="<%=for_id %>" type="text" value="<%=arr[i] %>" class="add_textbox" onblur="chkFMail(this.value,this.name)" size="35"/>
                          <%
                            }
                             %>
                </div>
                </td>
              </tr>
              
              <tr class="r7 r_top">
                <td class="r8"><span class="rc"><button style="    margin-top: 0px !important;"  class="alZ search_button button_color" onclick="saveForwarding()">Save</button> </span></td>
                 <td class="r9" width="70%"></td>
                </tr>
               </tbody>
               </table>
              
             
     	                <%} %>
     	                </div>
     	                
     	                <div id="view9">
     	                <table cellpadding="0" class="cf" width="100%" style="    margin-top: 25px !important;">
            <tbody>
              <tr class="r7 r_top">
                <td class="r9" width="70%">
                <table width="100%" class="wb_table">
                <tr>
                <td width="30%"> <span class="rc">Whitelist Email/Domain</span></td>
                <td width="70%"> 
               <div class="checklist clear" style="max-width: 424px;min-height: 11em;" id="Wlistdiv">
                                  
		<%
		List<UserPref> wlst=(List<UserPref>) request.getAttribute("uWhiteList");
		if(wlst.size()>0)
		{
			for(UserPref upref: wlst)
			{
				
			long sn=upref.getPrefid();
		%>
		
		<div id="div_bwlist_<%=sn %>">
			
			<div class="bt_left" > <%=upref.getValue() %></div>
			<div class="bt_right" >
			<span style="margin-right: 4px;cursor: pointer;" id="<%=sn %>" onclick="delBWlist(this.id)">
			<img src="images/tool_delete.png" ></span>
			</div></div>
<%
			}
		}
%>
			
			
			  </div>
                                    	 
                                    	 
                                    	 
                                    	 
                </td>
                </tr>
                <tr><td></td>
                <td> <input class="wb_text" type="text" size="42" value=""  placeholder="Ex: user@example.com" id="add_ws_mail">
             <input name="bt2" type="button" value="Add" class="alZ search_button bw_bt" onclick="addBWSenMail('white')">
             </td>
                </tr>
                </table>
                
                
                <table width="100%" >
                <tr>
                <td width="30%"> <span class="rc">Blacklist Email/Domain</span></td>
                <td width="70%"> 
                <div class="checklist clear" style=" max-width: 424px;     min-height: 11em;" id="Blistdiv">
                               
                <%
		List<UserPref> blst=(List<UserPref>) request.getAttribute("uBlackList");
		if(blst.size()>0)
		{
			for(UserPref upref: blst)
			{
				
			long sn=upref.getPrefid();
		%>               
                               
                                  <div id="div_bwlist_<%=sn %>">
			
			<div class="bt_left" > <%=upref.getValue() %></div>
			<div class="bt_right" >
			<span style="margin-right: 4px;cursor: pointer;" id="<%=sn %>" onclick="delBWlist(this.id)">
			<img src="images/tool_delete.png" ></span>
			</div></div>
<%}
			}%>
			    	 
                                    	 </div>
                                    	 
                                    	 
                                    	 
                                    	 
                </td>
                </tr>
                <tr><td></td>
                <td> <input class="wb_text" type="text" size="42" value="" placeholder="Ex: user@example.com" id="add_bs_mail">
             <input name="bt2" type="button" value="Add" class="alZ search_button bw_bt" onclick="addBWSenMail('black')">
             </td>
                </tr>
                </table>
                </td>
                <td class="r9" width="30%" style="padding-bottom: 300px !important">
                 <ul class="ul_space">
                       
                        <li class="bt-space5">Whitelist has higher priority than blacklist.</li>
                    </ul>

                    <span class="rc">Format for Whitelist/Blacklist</span>
                    <ul class="ul_space">
                        <li class="bt-space5"><span class="rc">Single user:</span> user@example.com</li>
                        <li class="bt-space5"><span class="rc">Entire domain:</span> *@example.com</li>
                       
                    </ul>
                </td></tr></tbody></table>
     	                </div>
     	                
     	                
        
      </div>
       </div>
    
  <script type="text/javascript">
  function delBWlist(bwid) {
	  document.getElementById('action_gif').style.display= 'block';
	  $.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/deleteBlackWhitelistRow",
			data : {
				'bwid':bwid
			},
			/* contentType : "application/json", */
			success : function(data) {
				if(data=="<$expire$>")
				{
				location.href ="${pageContext.request.contextPath}/inbox";
					}

				if(data=="true")
					{
					var idd="div_bwlist_"+bwid;
					$("#"+idd).remove();
					var msg="Removed Successfully.";
					var success = generate('alert');
					$.noty.setText(success.options.id, msg);
					setTimeout(function () {  $.noty.close(success.options.id); }, 3000);  
					}
				document.getElementById('action_gif').style.display= 'none';
			}
	  });
}
  
  function addBWSenMail(bwtype) {
	var val="";
	var msg="";
	var flg=false;
	if(bwtype=="white")
		{
		val=$("#add_ws_mail").val();
		
		}
	else if(bwtype=="black")
		{
		val=$("#add_bs_mail").val();
		}
	
	if(!val)
	{
	msg="Please enter any email or domain";
	}
else
	{
	if(val.indexOf("@")<=0)
	{
		msg="Please enter correct email.";
	}
	else
	{
		flg=true;
	}
	}
	
	if(flg==false)
		{
		var success = generate('alert');
		 $.noty.setText(success.options.id, msg);
		 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);  
		}
	else
		{
		document.getElementById('action_gif').style.display= 'block';
		$.ajax({
  			type : "GET",
  			url : "${pageContext.request.contextPath}/addBlackWhitelistRow",
  			data : {
  				'bwtype':bwtype, 'val':val
  			},
  			/* contentType : "application/json", */
  			success : function(data) {
  				if(data=="<$expire$>")
  				{
  				location.href ="${pageContext.request.contextPath}/inbox";
  					}

  				var msg="Added Successfully.";
  				if(data=="false")
  					{
  					msg="Error Occurred.";
  					}
  				else
  					{
  					var cnt="<div id='div_bwlist_"+data+"'><div class='bt_left'> "+val+"</div><div class='bt_right'>";
						cnt+="<span style='margin-right: 4px;cursor: pointer;' id='"+data+"' onclick='delBWlist(this.id)'>";
						cnt+="<img src='images/tool_delete.png'></span></div></div>";
  					if(bwtype=="white")
  					{
  						$("#add_ws_mail").val('');
  						$( "#Wlistdiv" ).append( cnt);
  					}
  					else if(bwtype=="black")
  					{
  						$("#add_bs_mail").val('');
  						$( "#Blistdiv" ).append( cnt);
  					}
  					}
  				var success = generate('alert');
				 $.noty.setText(success.options.id, msg);
				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);  
  				document.getElementById('action_gif').style.display= 'none';
  			}
		});
		}
}
  
  
  function saveForwarding() {
	  var no=document.getElementById('hid_cadd').value;
	  document.getElementById('action_gif').style.display= 'block';
	  var hid_cadd="";
	  for(i=1;i<=parseInt(no);i++)
	  {
	  var idd="cadd"+i;
	
	      if (document.getElementById(idd).value!="" ) {
	    	 if(hid_cadd=="")
	    		 {
	    		 hid_cadd=document.getElementById(idd).value;
	    		 }
	    	 else
	    		 {
	    		 hid_cadd=hid_cadd+","+document.getElementById(idd).value;
	    		 }
	      }
	  }
	  
	  //alert(hid_cadd);
	  var accf="no";
	  if(!$("input[name='accf']:checked").val())
		  {
		  	accf="no";
		  }
	  else
		  {
		  accf="yes";
		  }
	 // alert(accf);
	  var inbox="no";
	  if(!$("input[name='inbox']:checked").val())
	  {
	  	inbox="no";
	  }
	  else
		  {
		  inbox="yes";
		  }
	 // alert(inbox);
	  var hid_serv=$("#hid_serv").val();
	 // alert(hid_serv);
		$.ajax({
  			type : "GET",
  			url : "${pageContext.request.contextPath}/saveMailForwarding",
  			data : {
  				'hid_cadd':hid_cadd, 'hid_serv':hid_serv, 'accf': accf, 'inbox':inbox
  			},
  			contentType : "application/json",
  			success : function(data) {
  				if(data=="<$expire$>")
  				{
  				location.href ="${pageContext.request.contextPath}/inbox";
  					}

  				if(data=="true")
  					{
  					var success = generate('alert');
     				 $.noty.setText(success.options.id, "Saved Successfully.");
     				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);  
  					}
  				document.getElementById('action_gif').style.display= 'none';
  			}
		});
	 // alert(hid_serv);
}
  </script> 
    

<script type="text/javascript">
                                                $(document).ready(function($) {
                                                    $('#accordion-3').dcAccordion({
                                                        eventType: 'click',
                                                        autoClose: false,
                                                        saveState: false,
                                                        disableLink: false,
                                                        showCount: false,
                                                        speed: 'slow'
                                                    });
                                                });
            </script>


<script type="text/javascript">
                                                $(document).ready(function() {
                                                    if (!$.browser.webkit) {
                                                        $('.container').jScrollPane();
                                                    }
                                                });
            </script>
            
  
            
   <div id="search_form" class="filter_serach">
     
    <div class="form">
      <form action="" method="get" class="filter_form">
        <div class="to">
          <div class="name search_text">Filter</div>
          <div class="clear"></div>
          <input type="email"  name="" value=""  class="border input filter_from " id="fromFilter" placeholder="From:-  Email or @domain">
          <input type="email"  name="" value="" class="border input filter_to" id="toFilter" placeholder="To:-  Email or @domain">
          <input type="text" name="" value="" class="border input filter_subject" id="subjectFilter" placeholder="Subject">
          <input type="text" name="" value="" class="border input filter_has" id="containsFilter" placeholder="Body Contains">
    
          <div class="clear"></div>
          <div class="check check_upper dont">
            <input name="" type="checkbox" value="">
            <span>Don't include chats</span></div>
        </div>
        <div class="filter_size">
               <span>Size</span>
               <div class="all_greater">
               <select id="sz_filter">
               <option value="greater than">Greater Than</option>
               <option value="less than">Less Than</option>
               </select> 
             
               </div>
               <input type="text" id="sizeFilter"/>
               <!----------/// KB MB ---------->
               <div class="km_mb_main">
                         <div class="filter_mb_kb">
                               <!-- <span class="mb">MB</span> -->
                               <span class="kb">KB</span>
                            <!--    <span class="byte">Bytes</span> -->
                         </div>
                        
                         
               </div>
              <!--------/// KB MB -------------->
               
        <div class="clear"></div>
        </div>
        <div class="clear"></div>
        <div class="search_button filter_buttom">Cancel </div>
        <div class="search_button filter_new_event next_filter" >Next </div>
      
 
      </form>
      
    <div class="clear"></div>
    </div>
    <div class="clear"></div>
  </div>
          
    <div id="search_form" class=" filter_next_page">
    <div class="form">
      <form action="" method="get" class="filter_form">
        <div class="to">
          <div class="name">Choose Action: </div>
          <div class="clear"></div>
          
        <div  class="main_check_box">
  <div class="inner_check_box">
 
    
    <div class="check_box_row">
      <input type="checkbox" id="seenFilter" onclick="chkCondition(this.id)">
      <label for=":x5" class="aD">Mark as read</label>
       <div class="clear"></div>
       <hr/>
    </div>
    <div class="check_box_row">
      <input type="checkbox" id="staritFilter" onclick="chkCondition(this.id)">
      <label for=":x6" class="aD">Star it</label>
       <div class="clear"></div>
       <hr/>
    </div>
    <div class="check_box_row">
      <input type="checkbox" id="applyLabelFilter" onclick="chkCondition(this.id)">
      <label for=":x7" class="aD">Apply the label : </label>
       <select id="labelFilter">
       
           <option value="0">Choose Label...</option>
           <option value="$label1">Important</option>
           <option value="$label2">Work</option>
           <option value="$label3">Personal</option>
           <option value="$label4">ToDo</option>
           <option value="$label5">Later</option>
           <%
           if(labset!=null && labset.length()>0)
           {
          	 String tagarr[]=labset.split("~");
         
          	 for(int i=0;i< tagarr.length; i++)
          	 {
          		 String tarr[]=tagarr[i].split("#");
          
          	 String tgnm=tarr[0];
          	 tgnm=tgnm.replace("_", " ");

           %>
            <option value="<%=tarr[0] %>"><%=tgnm %></option>
           <%}} %>
           </select>
  
      <div class="clear"></div>
      <hr/>
    </div>
   <div class="check_box_row">
      <input id="moveToFolderFilter" type="checkbox" onclick="chkCondition(this.id)">
      <label for=":x4" class="aD">Move to Folder : </label>
       <select id="moveToFolderName">
        <%
                     for(String val_fdr: all_fldr_lst)
                     {
                    	if(val_fdr.equalsIgnoreCase("Sent") || val_fdr.equalsIgnoreCase("Drafts") || val_fdr.equalsIgnoreCase("Trash"))
                    	{
                    		continue;
                    	}
                    	/*  String val_arr[]=val_fdr.split("\\.");
                    	String sps="";
     					 for(int j=1;j<val_arr.length;j++)
     					{
     						sps+="&nbsp;&nbsp;&nbsp;&nbsp;";
     					}  */
                    	 %>
                         <option value="<%=val_fdr %>" title="<%=val_fdr %>"> <%=val_fdr %></option>
                         <%
                     }
                     %>
         
           </select>
   
       <div class="clear"></div>
       <hr/>
    </div>
    <div class="check_box_row">
      <input id="forwardToOtherFilter" type="checkbox" onclick="chkCondition(this.id)">
      <label for=":x4" class="aD">Forwarding mail to : </label>
       <input type="text" style="width: 140px;" id="forwardTo" />
   
       <div class="clear"></div>
       <hr/>
    </div>
    <div class="check_box_row">
      <input type="checkbox" id="deleteFilter" onclick="chkCondition(this.id)">
      <label  class="aD">Delete it</label>
       <div class="clear"></div>
       <hr/>
    </div>
  
  </div>
</div>

        </div>
 
        <div class="clear"></div>
         <div class="search_button  filter_back">Back </div>
        <div class="search_button filter_buttom_create"  onclick="createFilter()">Create filter </div>
              <div class="search_button filter_buttom filter_space">Cancel </div>
        
      </form>
      <script type="text/javascript">
      
      function chkCondition(id) {
    	  if(checkedOrNot(id))
    		  {
    		  if(id=='deleteFilter')
    			  {
    			  if(checkedOrNot('seenFilter')== true || checkedOrNot('staritFilter')==true || checkedOrNot('applyLabelFilter')==true ||  checkedOrNot('forwardToOtherFilter')==true ||  checkedOrNot('moveToFolderFilter')==true)
    				  {
    				  $('#'+id).prop('checked', false);
    				  var success = generate('alert');
    			   		 $.noty.setText(success.options.id, "Please Select either Delete Action or Other Action!");
    			   		 setTimeout(function () {  $.noty.close(success.options.id); }, 4000);
    				  }
    			  }
    		  else
    			  {
    			  if(checkedOrNot('deleteFilter')== true )
    				  {
    				  $('#'+id).prop('checked', false);
    				  var success = generate('alert');
    			   		 $.noty.setText(success.options.id, "Please Select either Delete Action or Other Action!");
    			   		 setTimeout(function () {  $.noty.close(success.options.id); }, 4000);
    				  
    				  }
    			  }
    		  }
	}
      
      function checkEmpty(valu){
          if(valu!=""){
              return true;
          }
         return false;         
      }
      
      function checkedOrNot(valu){
          return $('#'+valu).is(':checked');
      }
      </script>
      <script type="text/javascript">
      function createFilter(){
        
    if(checkedOrNot('seenFilter')!= true && checkedOrNot('staritFilter')!=true && checkedOrNot('applyLabelFilter')!=true && checkedOrNot('deleteFilter')!=true && checkedOrNot('moveToFolderFilter')!=true && checkedOrNot('forwardToOtherFilter')!=true)
      {
    	var success = generate('alert');
		 $.noty.setText(success.options.id, "Please Choose at least one Action!");
		 setTimeout(function () {  $.noty.close(success.options.id); }, 4000);	  
      }
    else 
    	{
    	
    	if(checkedOrNot('applyLabelFilter')==true && $('#labelFilter').val()=='0')
    		{
    		var success = generate('alert');
   		 $.noty.setText(success.options.id, "Please Select any Label!");
   		 setTimeout(function () {  $.noty.close(success.options.id); }, 4000);	  
    		}
    	else if(checkedOrNot('deleteFilter')==true && (checkedOrNot('seenFilter')== true || checkedOrNot('staritFilter')==true || checkedOrNot('applyLabelFilter')==true ||  checkedOrNot('moveToFolderFilter')==true || checkedOrNot('forwardToOtherFilter')==true))
    		{
    		var success = generate('alert');
   		 $.noty.setText(success.options.id, "Please Select either Delete Action or Other Action!");
   		 setTimeout(function () {  $.noty.close(success.options.id); }, 4000);	  
    		}
    	else
    		{
	    	var bodyFilter=makeFilterBody();
	        $("#beforeScriptFilter").val(bodyFilter);
	      //  alert(bodyFilter);
	        uploadScript(bodyFilter);
	        $('.filter_next_page').hide();
			$('.web_dialog_overlay_setting').hide();
    		}
    	}  
      }
      </script>
     <script type="text/javascript">
      
  
		$(document.body).on('click','.filter_new_event',function(){
	   
	     
			 var fromFilter=$('#fromFilter').val();
	           var toFilter=$('#toFilter').val();
	           var subjectFilter=$('#subjectFilter').val();
	           var containsFilter=$('#containsFilter').val();
	          var sizeFilter=$("#sizeFilter").val();
	          
	          if((fromFilter==null || fromFilter=="" ) && ( toFilter==null || toFilter=="") && (subjectFilter==null || subjectFilter=="" ) && ( containsFilter==null || containsFilter=="") &&( sizeFilter==null || sizeFilter==""))
	        	  {
	        	  var success = generate('alert');
					 $.noty.setText(success.options.id, "Please fill at least one field!");
					 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
	        	  }
	          else
	        	  {
	        	  
	        	 
	        	  if(sizeFilter!=null && sizeFilter!="" && $.isNumeric(sizeFilter)!=true)
	        		  {
	        		  var success = generate('alert');
						 $.noty.setText(success.options.id, "Size should be numeric only!");
						 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
	        		  }
	        	  else
	        		  {
	        		  if(fromFilter!=null && fromFilter!="" && (fromFilter.indexOf('@')<0))
	        				  {
	        			  var success = generate('alert');
							 $.noty.setText(success.options.id, "Please enter Email or @domain name in From field!");
							 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
	        				  }
	        		  else if(toFilter!=null && toFilter!="" && toFilter.indexOf("@")<0)
	        				  {
	        			  var success = generate('alert');
							 $.noty.setText(success.options.id, "Please enter Email or @domain name in To field!");
							 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
	        				  }
	        		  else
	        		  {
						 $('.filter_next_page').show();
						 $('.filter_serach').hide();
					     $('.web_dialog_overlay_setting').show();
	        		  }
	        		  }
	        	  }
	   });

      function makeFilterBody(){
    
    	 var sz_filter=$("#sz_filter").val();
          var fromFilter=$('#fromFilter').val();
           var toFilter=$('#toFilter').val();
           var subjectFilter=$('#subjectFilter').val();
           var containsFilter=$('#containsFilter').val();
           var beforeScriptFilter=$("#beforeScriptFilter").html();
          var sizeFilter=$("#sizeFilter").val();
          var filterCondition="";
          var arryRule=beforeScriptFilter.split("#");
        
            if( beforeScriptFilter.indexOf('if')>=0){
               filterCondition+=    beforeScriptFilter+'\n#'+(arryRule.length)+'\n elsif allof (';
           }else{ 
                  filterCondition="require [\"fileinto\",\"imap4flags\",\"envelope\",\"body\",\"copy\"];\n"
                                          + "\n"+"#1\nif allof (";
            }
          // alert("fil----"+filterCondition);
             if(checkEmpty(fromFilter)){
               filterCondition+='header :contains ["From"] ["'+fromFilter+'"],\n';
           }
           if(checkEmpty(toFilter)){
               filterCondition+='envelope ["To"] ["'+toFilter+'"],\n';
           }
           if(checkEmpty(subjectFilter)){
               filterCondition+='header :contains ["Subject"] ["'+subjectFilter+'"],\n';
           }
           if(checkEmpty(containsFilter)){

               filterCondition+='body :text :contains ["'+containsFilter+'"],\n';
           }
         if(checkEmpty(sizeFilter)){
            	   if(sz_filter=="greater than"){
                   filterCondition+='size :over '+sizeFilter+'K,\n';
               }
               if(sz_filter=="less than"){
                   filterCondition+='size :under '+sizeFilter+'K,\n';
               }}
           
          
           filterCondition=filterCondition.substring(0,filterCondition.length-2);
           filterCondition+='\n){\n'
              var filterAction="";
           var flagtit="setflag";
              
          
           if(checkedOrNot('seenFilter')){
               filterAction+=flagtit+' "\\\\Seen";\n';
               flagtit="addflag";
           }
           if(checkedOrNot('staritFilter')){

               filterAction+=flagtit+' "\\\\Flagged";\n';
               flagtit="addflag";
           }
           if(checkedOrNot('applyLabelFilter')){
               if($('#labelFilter').val()!='0'){
                   filterAction+=flagtit+' "'+$('#labelFilter').val()+'";\n';
                   flagtit="addflag";
               }
           }
           if(checkedOrNot('deleteFilter')){
        	   

               uncheckCheckBox('moveToFolderFilter');
               filterAction+='fileinto "Trash";\n';
           }
           if(checkedOrNot('moveToFolderFilter')){
        	   

               uncheckCheckBox('deleteFilter');
               var address=$('#moveToFolderName').val();
               if(checkEmpty(address)){
                   filterAction+='fileinto "'+address+'";\n';
               }
           }
		if(checkedOrNot('forwardToOtherFilter')){
        	   

               uncheckCheckBox('forwardToOtherFilter');
               var address=$('#forwardTo').val();
               if(checkEmpty(address)){
                   filterAction+='redirect :copy "'+address+'";\n';
                   $('#forwardTo').val('');
               }
           }
           var scriptBody1=filterCondition+filterAction+"}";
           return scriptBody1;
    	 }
    	
      
      </script>
      
      <script type="text/javascript">
      
      function dtFormat(dt)
      {
    	  
    	  try
    	  {
    		var arr=dt.split("-");
    		if(arr.length!=3)
    			{
    			return "false";
    			}
    		else
    			{
    			if(arr[0].length!=4 || arr[1].length!=2  || arr[2].length!=2)
    				{
    				return "false";
    				}
    			}
    		return "true"
    	  }
    	  catch (e) {
			return "false";
		}
      }
      
      function daydiff(first, second) {
    	  try
    	  {
    	 // alert(first);
    	    return (second-first)/(1000*60*60*24);
    	  }
    	    catch (e) {
    			
    		}
    	}
      
    	   function createVacation(){	   
    	 
    	   var selectedVal = "";
    	   
    	   var val =  $("input[name=vacationReminderStatus]:checked").val();
    	  
    	    
    	   if(val=='on'){
    		var id=$("#hid_logedin_id").val();	   
           var vacationStartDate=$('#vacationStartDate').val();
           var vacationEndDate=$('#vacationEndDate').val();
             var vacationSubject=$('#vacationSubject').val();
             var vacationMessageBody=$('#vacationMessageBody').val();
         
           if(vacationStartDate==null || vacationStartDate=='' || vacationEndDate==null || vacationEndDate=='' || vacationSubject==null || vacationSubject=='' || vacationMessageBody==null || vacationMessageBody==''  )
        	   {
        	   var success = generate('alert');
				 $.noty.setText(success.options.id, "Please fill all the fields!");
				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
        	   }
           else
        	   {
        	   
        	   var first = new Date(vacationStartDate);
     		 	var second= new Date(vacationEndDate);
        	  
        	   
        	  
        	  if(dtFormat(vacationStartDate)=='false' || dtFormat(vacationEndDate)=='false')
             {
        		  var success = generate('alert');
 				 $.noty.setText(success.options.id, "Please fill dates in correct format: YYYY-MM-DD");
 				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);  
             }
        	  else
        		  {
        		  
        		 
        		  var vacationDays= parseInt(daydiff(first, second));
        		  vacationDays+=1;
        		  if (vacationDays<1)
        			  {
        			  var success = generate('alert');
      				 $.noty.setText(success.options.id, "End Date should be greater than Start Date!");
      				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);  
        			  }
        		  else
            {
         
            var vacationBody='require ["date","relational","vacation","variables"];\n'+
            'set "subject" "";if header :matches "subject" "*" { set "subject" "$\{1\}"; }\n'+
            'if allof(currentdate :value "ge" "date" "'+vacationStartDate+'", currentdate :value "le" "date" "'+vacationEndDate+'")'+
                            '\n {'+

                               '\n vacation'+

                                   '\n :days 2'+

                                    '\n :addresses ["'+id+'"]'+ 

                                    '\n :subject "'+vacationSubject+': $\{subject\}"'+
                                 
                                  '\n "'+vacationMessageBody+'";'+

                            '\n}';
            document.getElementById('action_gif').style.display= 'block';
              $.ajax({
                 type : "GET",
                   url : "${pageContext.request.contextPath}/saveVacation",
                   data : {
                   scriptBody:vacationBody,
                   scriptName:'01_vacation'
                   },
                   contentType : "application/json",
                   success : function(data) {
                     
                        var success = generate('alert');
      				 $.noty.setText(success.options.id, "Activated Successfully.");
      				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);  
                	   document.getElementById('action_gif').style.display= 'none';
                       }
                     }); 
            
            }
        		  }
        	   }
    	   }else{
    		  
    		  deleteVacation(); 
    	   }
       }
      
      </script>
      
      <script type="text/javascript">
      function deleteVacation(){
    	  document.getElementById('action_gif').style.display= 'block';
    	  $.ajax({
              type : "GET",
                url : "${pageContext.request.contextPath}/deleteVacation",
                data : {
                         scriptName:'01_vacation'
                },
                contentType : "application/json",
                success : function(data) {
                	if(data=="true")
                		{
                   
                    $('#vacationStartDate').val("");
          			$('#vacationEndDate').val("");
            		$('#vacationSubject').val("");
             		$('#vacationMessageBody').val("");
                      var success = generate('alert');
      				 $.noty.setText(success.options.id, "Deleted Successfully.");
      				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);  
                     document.getElementById('action_gif').style.display= 'none';
                  
                		}
                    }
                  });
      }
      
      
      </script>
      <script type="text/javascript">
      function uploadScript(uploadBody){
    	  document.getElementById('action_gif').style.display= 'block';
           $.ajax({
                  type : "GET",
                    url : "${pageContext.request.contextPath}/saveFilter",
                    data : {
                    scriptBody:uploadBody,
                    scriptName:'mail-filter'
                    },
                    contentType : "application/json",
                    success : function(data) {
                       openSettingFilter();
                        }
                      });
           
           try
           {
        	   $('.filter_next_page').hide();
   			$('.web_dialog_overlay_setting').hide();
           }
           catch (e) {
		}
      }
      
      </script>
    </div>
    
    <div class="clear"></div>
  </div>
  
  <!---------// FILTER NEXT PAGES END HERE ------------>
            <!------/// FILTER END HERE --------------->
           
 
    <!---------//EDIT FOLDER IN ACCOUNT --------->
            
            <div class="account_edit_folder">
                     <input type="hidden" id="hid_edit_old_fldr"  name="hid_edit_old_fldr" />
                     <h1 class="folder_heading">Rename Folder</h1>
                     <input type="text" id="edit_old_fldr" disabled="disabled" name="edit_old_fldr" placeholder="Folder Name" class="folder_icon" />
                     <div class="clear"></div><div class="clear"></div>
                     <input type="text" id="edit_new_fldr" name="edit_new_fldr" placeholder="New Folder Name" class="folder_icon" />
                     <div class="clear"></div>
                   
                     <div onclick="renameFolder()" class="popup_margin search_button  create_folder">Save </div>
                     <div class="search_button filter_buttom filter_space_1" style="  margin-top: 15px !important;">Cancel </div>
                     <div class="clear"></div>
                      </div>
            
            <!---------// CREATE NEW FOLDER IN ACCOUNT --------->
              <div class="account_new_folder">
                     <h1 class="folder_heading">Create Folder</h1>
                     <input type="text" id="new_fldr" name="new_fldr" style="float: none !important;" placeholder="Folder Name" class="folder_icon" />
                     <div class="clear"></div>
                    <select id="parent_flder" name="parent_flder">
                     <option value="-">Choose Parent</option>
                     <%
                     for(String val_fdr: all_fldr_lst)
                     {
                    	/* String val_arr[]=val_fdr.split("\\.");
                    	String sps="";
    					 for(int j=1;j<val_arr.length;j++)
    					{
    						sps+="&nbsp;&nbsp;&nbsp;&nbsp;";
    					} */
                    	 %>
                         <option value="<%=val_fdr %>" title="<%=val_fdr %>"> <%=val_fdr %></option>
                         <%
                     }
                     %>
                     
                     </select>
                    
                     <div class="clear"></div>
                     <div onclick="creatFolder()" class="popup_margin search_button  create_folder">Create </div>
                     <div class="search_button filter_buttom filter_space_1" style="  margin-top: 15px !important;">Cancel </div>
                     <div class="clear"></div>
                      </div>
   <script type="text/javascript">
   
   $(document).ready(function() {

	$('#password').keyup(function(){
		$('#result').html(checkStrength($('#password').val()))
	})	
	
	function checkStrength(password){
    var strength = 0
    if (password.length < 6) { 
		$('#result').removeClass()
		$('#result').addClass('short')
		document.getElementById("hid_ps").value="0";
		return 'Too short' 
	}
    
    //length is ok, lets continue.
	
	//if length is 8 characters or more, increase strength value
	if (password.length > 7) strength += 1
	
	//if password contains both lower and uppercase characters, increase strength value
	if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))  strength += 1
	
	//if it has numbers and characters, increase strength value
	if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/))  strength += 1 
	
	//if it has one special character, increase strength value
    if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/))  strength += 1
	
	//if it has two special characters, increase strength value
    if (password.match(/(.*[!,%,&,@,#,$,^,*,?,_,~].*[!,%,&,@,#,$,^,*,?,_,~])/)) strength += 1
	
	//now we have calculated strength value, we can return messages
	
	//if value is less than 2
	if (strength < 2 ) {
		$('#result').removeClass()
		$('#result').addClass('weak')
		document.getElementById("hid_ps").value="0";
		return 'Weak'			
	} else if (strength == 2 ) {
		$('#result').removeClass()
		$('#result').addClass('good')
		document.getElementById("hid_ps").value="1";
		return 'Good'		
	} else {
		$('#result').removeClass()
		$('#result').addClass('strong')
		document.getElementById("hid_ps").value="2";
		return 'Strong'
	}
}
});
   </script>  
   <script type="text/javascript">
function removeMsg(id)
{
var element = document.getElementById(id);
element.parentNode.removeChild(element);
//alert(id);
}

</script>


<script type="text/javascript">
function changePass()
{
	var pass=document.getElementById("password").value;
	var cnp=document.getElementById("cnp").value;
	var cur_pass=document.getElementById("cur_pass").value;
var no=document.getElementById('hid_ps').value;
 
if(cur_pass==null ||  cur_pass=="" || pass==null ||  pass=="" || cnp==null ||  cnp=="")
	  {
	 // alert("All Fields must be Filled .");
	  var msg="All Fields must be Filled.";
	  var error = generate('alert');
		$.noty.setText(error.options.id, msg);
		 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
	  }
else  if(parseInt(no)<1) {
    //alert('Password must be Good or Strong.');
    var msg="Password must be Good or Strong.";
	  var error = generate('alert');
		$.noty.setText(error.options.id, msg);
		 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
}
else if(pass != cnp)
	  {
	  //alert("Confirm password is not matched");
	  var msg="Confirm password is not matched.";
	  var error = generate('alert');
		$.noty.setText(error.options.id, msg);
		 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
	  }
else
	  {
	document.getElementById('action_gif').style.display= 'block';
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/changeAccPassword",
		data : {
			'cur_pass':cur_pass, 'new_pass': pass, 'con_new_pass': cnp
		},
		contentType : "application/json",
		success : function(data) {
			// alert(data);
			// var msg="Confirm password is not matched.";
			if(data=="<$expire$>")
	{
	location.href ="${pageContext.request.contextPath}/inbox";
		}
	  var error = generate('alert');
		$.noty.setText(error.options.id, data);
		 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
			document.getElementById('action_gif').style.display= 'none';
		}
	}); 
	  }
	  
}




function setNCP() {
	document.getElementById("cnp").value="";
	document.getElementById("cnp_msg1").innerHTML="";
}


function checkNCP(val) {
	if(val!="")
	{
	var val1=document.getElementById("password").value;
	if(val==val1)
	{
      document.getElementById("cnp_msg1").innerHTML = "<font color='green'>Confirm password is matched..</font>";
	 }
	 else
	 {
	 document.getElementById("cnp_msg1").innerHTML = "<font color='red'>Confirm password is not matched.</font>";
	document.getElementById("cnp").value="";
	 }
	 }
}

function setPath(val)
{

var chk=document.getElementById(val).checked;
if(chk)
{
var ps=document.getElementById("rpass").value;
document.getElementById("password").value=ps;
document.getElementById("cnp").value=ps;
document.getElementById("hid_ps").value="2";
document.getElementById("result").innerHTML="";
document.getElementById("cnp_msg1").innerHTML="";
/* document.getElementById("cnp_msg1").innerHTML"";  */
}
else
{
document.getElementById("password").value="";
document.getElementById("cnp").value="";
document.getElementById("hid_ps").value="0";
}
}
</script>
   
   
   
                      <script type="text/javascript">
                      function renameFolderOpen(chk_id) {
                    	
                    	var fldnm="";
                      	var arr=chk_id.split("_");
                      	for( i=1; i<arr.length; i++)
                      		{
                      		fldnm +=arr[i];
                      		}
                      	//alert(fldnm);
                      	var arr1=fldnm.split(".");
                      	var oldnm="";
                      	if(arr1.length>0)
                      		{
                      		oldnm=arr1[arr1.length-1];
                      		}
                      	document.getElementById('hid_edit_old_fldr').value=fldnm;
                      	document.getElementById('edit_old_fldr').value=oldnm;
                      	document.getElementById("edit_new_fldr").value="";
                    	$('.account_edit_folder').show();
                		$('.web_dialog_overlay_setting').show();
					}
                      
                      
                      function renameFolder() {
                    	  var nm=document.getElementById("edit_new_fldr").value;
                    	 var oldnm= document.getElementById('hid_edit_old_fldr').value;
                    	 
              			if ( nm.indexOf('.') > -1 /* || nm.indexOf('/') > -1 || nm.indexOf('\\') > -1 || nm.indexOf(';') > -1  */) 
              				{
              				
              				var msg="Folder name contains a forbidden character. (.)";
                  			var error = generate('alert');
              				$.noty.setText(error.options.id, msg);
              				 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
              				}
              			else if(nm.length>100)
        				{
        				
        				var msg="The name is too long. Please try another name.";
              			var error = generate('alert');
          				$.noty.setText(error.options.id, msg);
          				 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
        				}
        			    else
              				{
        			    	document.getElementById('action_gif').style.display= 'block';
              				var arr1=oldnm.split(".");
                          	var old="";
                          	if(arr1.length>0)
                          		{
                          		old=arr1[arr1.length-1];
                          		}
                          	var newnm = oldnm.replace(old, nm);
              				
              				 $.ajax({
              					type : "GET",
              					url : "${pageContext.request.contextPath}/renameFolder",
              					data : {
              						'oldnm':oldnm, 'newnm':newnm
              					},
              					contentType : "application/json",
              					success : function(data) {
              						// alert(data);
              						if(data=="<$expire$>")
									{
									location.href ="${pageContext.request.contextPath}/inbox";
										} 
              						if(data=="true")
              							 {
              							document.getElementById('action_gif').style.display= 'none';
              							try
             							 {
              							var cnt= document.getElementById('con_'+oldnm).innerHTML;
              							cnt=cnt.replace(old, nm);
              							document.getElementById('con_'+oldnm).innerHTML=cnt;
              							document.getElementById("con_"+oldnm).id = "con_"+newnm;
              							document.getElementById("tr_"+oldnm).id = "tr_"+newnm;
              							document.getElementById("chk_"+oldnm).id = "chk_"+newnm;
              							document.getElementById("del_"+oldnm).id = "del_"+newnm;
              							document.getElementById("rename_"+oldnm).id = "rename_"+newnm;
             							 }
              							catch (e) {
											// TODO: handle exception
										}
              							
              							try
              							 {
              							$("#moveToFolderName option[value="+oldnm+"]").remove();
              							$('#moveToFolderName').append($('<option>', {
              				            	 value: newnm,
              				            	text: newnm
              							}));
              							}
              							catch (e) {
              							// TODO: handle exception
              							}
              							
              							try
             							 {
             							$("#parent_flder option[value="+oldnm+"]").remove();
             							$('#parent_flder').append($('<option>', {
             				            	 value: newnm,
             				            	text: newnm
             							}));
             							}
             							catch (e) {
             							// TODO: handle exception
             							}
              							
              							
              							
              							 }
              						document.getElementById("edit_new_fldr").value="";
                                	document.getElementById('hid_edit_old_fldr').value="";
                                	document.getElementById('edit_old_fldr').value="";
              						 $('.account_edit_folder').hide();
              						 $('.web_dialog_overlay_setting').hide();
              						 
              						 if(data=="true")
              							 {
              							//var msg="The folder '"+old+"' has been Renamed to '"+nm+"'";
              		 					var msg="Folder successfully Renamed.'";
              		 					var success = generate('alert');
       		    					 $.noty.setText(success.options.id, msg);
       		    					 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
              							 }
              					
              					}
              				}); 
              				}
					}
                      </script>
                     
          <script type="text/javascript">
          function creatFolder() 
          {
			var nm=document.getElementById("new_fldr").value;
			var element = document.getElementById("parent_flder");
		    var path = element.options[element.selectedIndex].value;
		    var arr=path.split(".");
			if ( nm.indexOf('.') > -1) /* > -1 || nm.indexOf('/') > -1 || nm.indexOf('\\') > -1 || nm.indexOf(';') > -1 ) */
				{
				
				
				var msg="Folder name contains a forbidden character. (.)" ;
				 var error = generate('alert');
				  $.noty.setText(error.options.id, msg);
				 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
				}
			else if(nm.length>100)
				{
				
				var msg="The name is too long. Please try another name.";
				 var error = generate('alert');
				  $.noty.setText(error.options.id, msg);
				 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
				}
			else if(arr.length>15)
				{
				 var msg="hierarchical levels can only be up to 16.";
				 var error = generate('alert');
				  $.noty.setText(error.options.id, msg);
				 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
				}
			else
				{
				
				document.getElementById('action_gif').style.display= 'block';
				
				document.getElementById("new_fldr").value="";
				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/createNewFolder",
					data : {
						'path':path, 'name':nm
					},
					contentType : "application/json",
					success : function(data) {
						// alert(data);
						if(data=="<$expire$>")
						{
						location.href ="${pageContext.request.contextPath}/inbox";
						}

						if(data=="true")
							 {
							 if(path=="-")
								 {
								 var cnt_rt="<tr id='tr_"+nm+"' class='To'><td class='alT'><div style=' cursor:pointer; width:100%;' class='al6'>"+nm+"</div></td> <td class='alQ'><input type='checkbox' checked='checked' onclick='folderSubscribe(this.id)' id='chk_"+nm+"'></td><td class='alQ'>0</td><td class='alQ'><img id='del_"+nm+"' onclick='deleteFolder(this.id)' src='images/tool.png'>&nbsp;/&nbsp;<img id='rename_"+nm+"' onclick='renameFolderOpen(this.id)'  style='cursor: pointer;' height='17px' width='20px' src='images/edit_fldr.png'></td>";
								
								 /* if(nm!="shared")
									 {
								 	 cnt_rt+="<td class='alQ'><img id='share_"+nm+"' onclick='manageFolderSharing(this.id)' class='img_share' src='images/msharing.png'></td>";
									 } */
								 	 cnt_rt+="</tr>";
								 $('table.new_folder_append >tbody').append(cnt_rt);	
								 
								 try
								 {

								$('#moveToFolderName').append($('<option>', {
								            	 value: nm,
								            	text: nm
								})); 
								}
								catch (e) {
								// TODO: handle exception
								}
								try
								 {

								$('#parent_flder').append($('<option>', {
								            	 value: nm,
								            	text: nm
								})); 
								}
								catch (e) {
								// TODO: handle exception
								}
								
								
								 }
							 else
								 {
								 var r_in= document.getElementById("tr_"+path).rowIndex;
								 var fldr_nm="";
								 var arr=path.split(".");
								 for(i=0;i< arr.length; i++)
									 {
									 fldr_nm+="&nbsp;&nbsp;&nbsp;&nbsp;";
									 }
								 fldr_nm+=nm;
								 var share_rt="<tr id='tr_"+path+"."+nm+"' class='To'><td class='alT'><div class='al6' style=' cursor:pointer; width:100%;'>"+fldr_nm+"</div></td> <td class='alQ'><input type='checkbox' checked='checked' onclick='folderSubscribe(this.id)' id='chk_"+path+"."+nm+"'></td><td class='alQ'>0</td><td class='alQ'><img id='del_"+path+"."+nm+"' onclick='deleteFolder(this.id)' src='images/tool.png'>&nbsp;/&nbsp;<img id='rename_"+path+"."+nm+"' onclick='renameFolderOpen(this.id)'  style='cursor: pointer;' height='17px' width='20px' src='images/edit_fldr.png'></td>";
								 /* var nm_chk=path+"."+nm;
								 if(nm_chk.indexOf("shared.")!=0)
									 {
									 share_rt+="<td class='alQ'><img id='share_"+path+"."+nm+"' onclick='manageFolderSharing(this.id)' class='img_share' src='images/msharing.png'></td>";
									 } */
									 share_rt+="</tr>";
								 $('#fldr_tbl tr').eq(r_in).after(share_rt);
								 <%-- <img id='rename_' onclick='renameFolderOpen(this.id)'  style='cursor: pointer;' height='17px' width='20px' src='images/edit_fldr.png'> --%>
								 
								 try
								 {

								$('#moveToFolderName').append($('<option>', {
								            	 value: path+"."+nm,
								            	text: path+"."+nm
								})); 
								}
								catch (e) {
								// TODO: handle exception
								}
								 try
								 {

								$('#parent_flder').append($('<option>', {
								            	 value: path+"."+nm,
								            	text: path+"."+nm
								})); 
								}
								catch (e) {
								// TODO: handle exception
								}
								
								
								 }
							 
							 
			 					//var msg="The folder '"+nm+"' has been Created.";successfully
			 					var msg="Folder successfully Created.";
			 					
			 					var success = generate('alert');
		    					 $.noty.setText(success.options.id, msg);
		    					 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
			 					
			 					
							 
							 }
						 else if(data=="false")
							 {
							 var alert = generate('alert');
	    					  $.noty.setText(alert.options.id, "An error occurred while creating folder.");
	    					 setTimeout(function () {  $.noty.close(alert.options.id); }, 5000);
							 }
						 else
							 {
							 //alert(data);
							 var alert = generate('alert');
	    					  $.noty.setText(alert.options.id, data);
	    					 setTimeout(function () {  $.noty.close(alert.options.id); }, 5000);
							 }
						 $("#parent_flder").val("-");
						 $('.account_new_folder').hide();
						 $('.web_dialog_overlay_setting').hide();
						 document.getElementById('action_gif').style.display= 'none';
						 
					}
				});
				}
		}
          
          function deleteFolder(nm) 
          {
        	
        	var fldnm="";
        	var arr=nm.split("_");
        	if(arr.length>1)
        		{
        		fldnm=arr[1];
        		}
        	$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/isEmptyWebmailFolder",
				data : {
					'fldnm':fldnm
				},
			/* 	contentType : "application/json", */
				success : function(data) {
					// alert(data);
					if(data=="<$expire$>")
					{
					location.href ="${pageContext.request.contextPath}/inbox";
						}

					if(data=="true")
						 {	
				        	$('.web_dialog_overlay_setting').show();
				        	var confirm = generate_conf_set('confirm','hid_del_fldr', fldnm);
				        	$.noty.setText(confirm.options.id, 'Delete this folder and everything in it?'); // same as alert.setText('Text Override')
						 }
					 else
						 {
						 var alert = generate('alert');
   					     $.noty.setText(alert.options.id, "Delete all mails and sub folders of this folder.");
   					     setTimeout(function () {  $.noty.close(alert.options.id); }, 5000);
						 }
				}
        	});
          }
          
         
          
          function generate_conf_set(type,id,foldernm) {
        	 //alert(id);
              var n = noty({
                text        : type,
                type        : type,
                theme       : 'relax',
                dismissQueue: false,
                layout      : 'center',
                theme       : 'defaultTheme',
                buttons     : (type != 'confirm') ? false : [
                    {addClass: 'btn btn-primary', text: 'Yes', onClick: function ($noty) {
        				
                      $noty.close();
                      $('.web_dialog_overlay_setting').hide();
                     // alert(foldernm);
                      $("#"+id).attr("data-foldername", foldernm);
                      $('#'+id).val("true").trigger('change');
        			  
                    }
                    },
                    {addClass: 'btn btn-danger', text: 'No', onClick: function ($noty) {
                        $noty.close();
                        $('.web_dialog_overlay_setting').hide();
                        $('#'+id).val("") ;
                        $("#"+id).attr("data-foldername", "");
                      var n1 = noty({
                            text        : 'You clicked "Cancel" button',
                            type        : 'error',
                            dismissQueue: false,
                            layout      : 'topCenter',
                            theme       : 'defaultTheme'
                        });
                       setTimeout(function () { $.noty.close(n1.options.id); }, 2000);
                    }
                    }
                ]
            });
           
                
                //console.log(type + ' - ' + n.options.id);
                return n; 
                 
            }

          
          
          
          
          $('#hid_del_fldr').change( function() {  
       	   //alert($("#hid_del").attr("data-id")); 
       	var r =$('#hid_del_fldr').val();
       	var fldnm=$("#hid_del_fldr").attr("data-foldername");
       //	alert("change r="+r);
      // 	alert("change fldnm="+fldnm);
       	if (r == "true") {
    		document.getElementById('action_gif').style.display= 'block';
    	
    	$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/deleteFolder",
			data : {
				'fldnm':fldnm
			},
			contentType : "application/json",
			success : function(data) {
				// alert(data);
				if(data=="<$expire$>")
				{
				location.href ="${pageContext.request.contextPath}/inbox";
					}
				var r_in= document.getElementById("tr_"+fldnm).rowIndex;
				 var table = document.getElementById("fldr_tbl");
				 $("#parent_flder option[value='"+fldnm+"']").remove();
				 table.deleteRow(r_in);
				 document.getElementById('action_gif').style.display= 'none';
				 
				 var arr1=fldnm.split(".");
				
				 try
				 {
				$("#moveToFolderName option[value="+fldnm+"]").remove();
				}
				catch (e) {
				// TODO: handle exception
				}
				
				try
				 {
				$("#parent_flder option[value="+fldnm+"]").remove();
				}
				catch (e) {
				// TODO: handle exception
				}
				 
				 
				 //var msg= "Folder "+arr1[arr1.length-1]+" deleted successfully.";   //"The folder '"+arr1[arr1.length-1]+"' has been Deleted.";
					var msg= "Folder successfully deleted.";
					var success = generate('alert');
				 $.noty.setText(success.options.id, msg);
				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
								
			}
		});
    	
    	}
       });
        	
        	function folderSubscribe(chk_id) {
        		
        		document.getElementById('action_gif').style.display= 'block';
        		 var nm= document.getElementById(chk_id).checked;
        		//alert(nm);
        		var fldnm="";
            	var arr=chk_id.split("_");
            	if(arr.length>1)
            		{
            		fldnm=arr[1];
            		}
            	$.ajax({
    				type : "GET",
    				url : "${pageContext.request.contextPath}/folderSubscription",
    				data : {
    					'fldnm':fldnm, 'stat': nm
    				},
    				contentType : "application/json",
    				success : function(data) {
    					// alert(data);
    				if(data=="<$expire$>")
					{
					location.href ="${pageContext.request.contextPath}/inbox";
						}	
    					document.getElementById('action_gif').style.display= 'none';
    					var subs="";
    					if(nm)
						{
    						subs="Subscribed"
						}
						else
						{
							subs="Unsubscribed"
						}
    					
    					var arr1=fldnm.split(".");
    					//var msg="The folder '"+arr1[arr1.length-1]+"' has been "+subs;
    					var msg="Folder successfully "+subs;
    					 var success = generate('alert');
    					 $.noty.setText(success.options.id, msg);
    					 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
    				
    				}
    			}); 
			} 
          
          </script>    
              <script type="text/javascript">
             
              

              function saveIdentities(){
              	
              	  document.getElementById('action_gif').style.display= 'block';
              	var fn=document.getElementById('fn').value;
              	var mob=document.getElementById('mob').value;
              	var hmob=document.getElementById('hmob').value;
              	var tel=document.getElementById('tel').value;
              	var addr=document.getElementById('addr').value;
              	var addrpcode=document.getElementById('addrpcode').value;
              	var back_mail=document.getElementById('back_mail').value; 
              	var sig = CKEDITOR.instances['sig'].getData();
              	var sig_st=document.getElementById('sigchk').checked;
              	var repto=document.getElementById('repto').value;
              	var dob=document.getElementById('dob').value;
              	var anni=document.getElementById('anni').value;
              	var e = document.getElementById("plang"); 
                 	var plang = e.options[e.selectedIndex].value;
              	
              $.ajax({
              	type : "POST",
              	url : "${pageContext.request.contextPath}/saveIdentities",
              	data : {'fn':fn, 'mob': mob, 'hmob': hmob, 'tel': tel,  'addr': addr, 'addrpcode': addrpcode,'back_mail': back_mail, 'sig': sig, 'sig_st': sig_st, 'repto': repto, 'dob': dob, 'anni': anni,  'plang': plang },
              	/* contentType : "application/json", */
              	success : function(data) {
              		if(data=="<$expire$>")
              		{
              		location.href ="${pageContext.request.contextPath}/inbox";
              		}
              		document.getElementById('action_gif').style.display= 'none';
              		 var success = generate('alert');
              		 $.noty.setText(success.options.id, "Profile updated successfully.");
              		 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
              	}
              });  
              	
              }
            	
            	
            	
            	$('.setting_tag_save').click(function(){
            		
            		var setting_tag_val = $(this).parent().children('.setting_val').val();
            		var setting_tag_nm = setting_tag_val.replace(/ /g, '_');
            		
            		var rgb =$(this).parent().children('.setting_color').children().children().children('.setting_tag_color').css('background-color');
            rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
			var hex ="#" +  ("0" + parseInt(rgb[1],10).toString(16)).slice(-2) +  ("0" + parseInt(rgb[2],10).toString(16)).slice(-2) +  ("0" + parseInt(rgb[3],10).toString(16)).slice(-2);
            		var len=  setting_tag_val.length;
            		var n1 = setting_tag_val.indexOf("$");
            		var n2 = setting_tag_val.indexOf("#");
            		var n3 = setting_tag_val.indexOf("~");
            		var n4 = setting_tag_val.indexOf("_");
            		if(len==0)
        			{
        			 var error = generate('alert');
					 $.noty.setText(error.options.id, "Label name should not empty.");
					 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
        			}
            		else	if(len>50)
            			{
            			 var error = generate('alert');
    					 $.noty.setText(error.options.id, "Maximum length of label is 50.");
    					 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
            			}
            		else if(n1>=0 || n2>=0 || n3>=0 || n4>=0)
            			{
            			var msg="Label contains a forbidden character. (~ or _ or $ or #)" ;
            			var error = generate('alert');
   					    $.noty.setText(error.options.id, msg);
   					    setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
            			}
            		else if(setting_tag_val=="Important" || setting_tag_val=="Work" || setting_tag_val=="Personal" || setting_tag_val=="To Do" || setting_tag_val=="Later" )
        			{
        			var msg="Label is already exist." ;
        			var error = generate('alert');
					    $.noty.setText(error.options.id, msg);
					    setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
        			}
            		else
            		{
            			document.getElementById('action_gif').style.display= 'block';
            			
            			 $.ajax({
            	  				type : "GET",
            	  				url : "${pageContext.request.contextPath}/addNewLabel",
            	  				data : {
            	  					'lname':setting_tag_val, 'lcolor': hex
            	  				},
            	  				contentType : "application/json",
            	  				success : function(data) {
            	  					if(data=="<$expire$>")
            	  					{
            	  					location.href ="${pageContext.request.contextPath}/inbox";
            	  						}
            	  					document.getElementById('action_gif').style.display= 'none';
            	  					if(data=="true")
            	  					{
            	  						
            	  						$('table.new_setting_tag >tbody').append('<tr id="'+setting_tag_nm+'" class="To"><td class="alT"><div style="cursor: pointer; width: 100%;" class="al6">'+setting_tag_val+'</div></td><td class="alQ"><div class="tag_col" style="background-color:'+ hex +'"></div></td><td class="alQ">User Defined</td><td class="alQ"><img id="deltag~'+setting_tag_nm+'"  style="cursor: pointer;" onclick="deleteSettingTag(this.id)" src="images/tool.png"></td></tr>');
            	  	            		$('.setting_tag').hide();
            	  	            		$('.web_dialog_overlay').hide();
            	  						
            	  					 var success = generate('alert');
            						 $.noty.setText(success.options.id, "Label "+setting_tag_val+ " created successfully." );
            						 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
            						 
            						 try
            						 {
            							var opt_val= setting_tag_val.replace(/\ /g, '_');
            							 $('#labelFilter').append($('<option>', {
            								    value: opt_val,
            								    text: setting_tag_val
            								})); 
            						 }
            						 catch (e) {
										// TODO: handle exception
									}
            	  					}
            	  					else
            	  						{
            	  						var error = generate('error');
            	   					    $.noty.setText(error.options.id, data);
            	   					    setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
            	  						}
            	  				}
            	  			});  
            			
            		
            		}
            	});
            	
            	
            	
            	function deleteSettingTag(tagnm)
            	{
            		var lblnm="";
                	var arr=tagnm.split("~");
                	if(arr.length>1)
                		{
                		lblnm=arr[1];
                		}
                	 $('.web_dialog_overlay_setting').show();
                	 var confirm = generate_conf_set('confirm','hid_del_label', lblnm);
                 	$.noty.setText(confirm.options.id, 'Do you want to delete this label?');
                	 
                	 
            	}
            	
            	

                
                $('#hid_del_label').change( function() {  
             	 
             	var r =$('#hid_del_label').val();
             	var tagnm=$("#hid_del_label").attr("data-foldername");
           
             	if (r == "true") {
          		document.getElementById('action_gif').style.display= 'block';
          	
          	$.ajax({
      			type : "GET",
      			url : "${pageContext.request.contextPath}/deleteSettingTag",
      			data : {
      				'tagnm':tagnm
      			},
      			contentType : "application/json",
      			success : function(data) {
      				if(data=="<$expire$>")
      				{
      				location.href ="${pageContext.request.contextPath}/inbox";
      					}

      				if(data=="true")
      					{
      				 var r_in= document.getElementById(tagnm).rowIndex;
      				 var table = document.getElementById("label_tbl");
      				 table.deleteRow(r_in);
      				 document.getElementById('action_gif').style.display= 'none';
      				 
      				 var msg="Label "+tagnm+" deleted successfully." ;  //"The label '"+tagnm+"' has been Deleted.";
      					
      					var success = generate('alert');
      				 $.noty.setText(success.options.id, msg);
      				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
      				
      				 try
      				 {
      					var opt_val= tagnm.replace(/\ /g, '_');
      				$("#labelFilter option[value="+opt_val+"]").remove();
      				 }
      				 catch (e) {
						// TODO: handle exception
					}
      				 
      					}	
      				else
      					{
      					document.getElementById('action_gif').style.display= 'none';
      					var success = generate('error');
         				 $.noty.setText(success.options.id, data);
         				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
      					}
      					
      			}
      		});
          	 
          	}
             });
            	
            
            	
            	
              </script>
             <script type="text/javascript">
             
             function addCatchAdd()
             {
             //document.getElementById("maildiv").innerHTML="";
             var no=parseInt($("#hid_cadd").val());
             no=no+1;
            // document.f.hid_cadd.value=no;
             $("#hid_cadd").val(no);
             var name="cadd"+no;
             var element = document.createElement("input");
             element.setAttribute("type", "text");
             element.setAttribute("name", name);
             element.setAttribute("id", name);
             element.setAttribute("placeholder", "abc@example.com");
             element.setAttribute("class", "add_textbox");
              element.setAttribute("onblur", "chkFMail(this.value,this.name)");
              
              
             //element.setAttribute("onblur", "makeMail(this.value)");

              var brElement = document.createElement("br");
               var brElement1 = document.createElement("br");
               var brElement2 = document.createElement("br");
              
             var foo = document.getElementById("fooBar1");
              foo.appendChild(element);
                   foo.appendChild(brElement );
                   foo.appendChild(brElement1 );
                   foo.appendChild(brElement2 );
                     
                      

             }
             
             function chkFMail(val,nm)
             {

            	 var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				var uerml =$("#hid_usermail").val();
            	    if (val!="" && !filter.test(val)) {
            	    var msg='Please Input a valid email address';
            		
  					var success = generate('alert');
  				 $.noty.setText(success.options.id, msg);
  				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
            	    $("#"+nm).val("");
            	    }
            	    else if (val==uerml) {
                	    var msg='Please Input an other email address';
                		
      					var success = generate('alert');
      				 $.noty.setText(success.options.id, msg);
      				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
                	    $("#"+nm).val("");
                	    }
             }
             </script>

            <!--------/// CREATE END HERE ------------>
            
            <!------------/// CK EDITOR ----------->
            <script type="text/javascript">
            CKEDITOR.replace( 'sig', {
            	  //  language: 'fr',
            	//uiColor: '#9AB8F3',

            		toolbar: [		// Defines toolbar group without name.
            																		// Line break - next group will be placed in new line.
            	
            	{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline' ] },
            	{ name: 'paragraph', groups: [  'blocks', 'align', 'bidi' ], items: [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
            	{ name: 'links', items: [ 'Link', 'Unlink', '' ] },
            	{ name: 'insert', items: [ 'Image',  'Smiley'  ] },
            	{ name: 'styles', items: [ 'Font', 'FontSize' ] },
            	{ name: 'colors', items: [ 'TextColor', 'BGColor' ] }
            	//['newplugin']
            	],
            	
            	enterMode: CKEDITOR.ENTER_BR



            	});
            </script>
     
            <!------------/// CK EDITOR END HERE ------->
            
            
            
            
             <div class="web_dialog_overlay_setting"></div>
            
            
</body>
</html>