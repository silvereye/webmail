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



       
    <!--  <script>var runOnLoad=function(c,o,d,e){function x(){for(e=1;c.length;)c.shift()()}o[d]?(document[d]('DOMContentLoaded',x,0),o[d]('load',x,0)):o.attachEvent('onload',x);return function(t){e?o.setTimeout(t,0):c.push(t)}}([],window,'addEventListener');</script>
    <link rel="stylesheet" href="css/treefolder.css" type="text/css">

    <script type="text/javascript" src="js/treefolder.js"></script>
    <script type="text/javascript">

      runOnLoad(function(){ CollapsibleLists.apply(); });

    </script> -->

  </head>
  
  <body >
  <%
  List<webmail.wsdl.Folder> folderList = (List<webmail.wsdl.Folder>) request.getAttribute("folderList"); 
  List<webmail.wsdl.File> fileList = (List<webmail.wsdl.File>) request.getAttribute("fileList");
  HttpSession hs=request.getSession();
	String userid=(String)hs.getAttribute("id");
	Folder currentFolder=(Folder)request.getAttribute("currentFolder");

		String[] breadcm=((String)request.getAttribute("breadcumPath")).split("/");
	%>
	<!-----------------------/// RIGHT PANNEL ------------------->
	
	 <div class="briefcase_inner">
	<div class="strip_details index_heading">
		<!-- <div class="home_heading"></div> -->
		<div class="path" style="padding-left: 5px;">
		
			<span> <%
	
	 %> <%-- <span id='/<%=userid%>' style='cursor: pointer'
				onclick='getFileSystem(this.id)'> Home / </span>  --%><%
	  	String bdm="";
	  	for(int i=1;i<breadcm.length;i++){
	  		//System.out.println("clicked on Bread cum "+breadcm[i]);
	  		bdm+="/"+breadcm[i];
	  		
	  		if(i==1){
	  			 %>
	  			 <span id='<%="/"+userid%>' style='cursor: pointer'
	  						onclick='openBriefcase(this.id)'>Home /</span> 
	  						
	  						
	  						<%	
	  		}else{
	 %>
	 <span id='<%=bdm%>' style='cursor: pointer'
				onclick='openBriefcase(this.id)'><%=breadcm[i]%> /
				<%} %>
			</span> <%
	 	} 
	 %>


			</span>
			<!-- <span class="path_color">Main Folder</span> -->
		</div>
	
		

	</div><br>

<input type="hidden" id="breadcumPathHome" value="<%=(String)request.getAttribute("breadcumPath") %>" />
                   <!------/// FOLDER ICON ----->
                   <div class="folder_name"><div class="icon_folder folder_fold"></div> Folder <span class="folder_line"></span></div>

                    <div class="folder_briefcase" 
                    <%
                   if( folderList.size()==0)
                   {
                	   %>
                	   style="display: none;"
                	   <%
                   }
                    %>
                    
                    >
                            <ul>
                               <!-- <li>
                                   <div class="folder_brief"></div>
                                   <span>Test111111111111111111111111111</span>
                                   <div class="clear"></div>
                               </li> -->
                              	<%
						for (webmail.wsdl.Folder folder : folderList) {
							
							
							String folPath="";
							String folPat=folder.getFolderPath().substring(1);
							/* if(folPat.indexOf("/")==folPat.lastIndexOf("/")){

								 folPath="/"+userid+"/"+folder.getFolderName();
							}else{ */
								folPath=folder.getFolderPath();
							/* } */
							//folPath=folPath.substring(folPath.indexOf("/"));
					%>
					
						<li
						title="Name: <%=folder.getFolderPath().substring(folder.getFolderPath().lastIndexOf("/")+1)%> &#013;Type: Folder &#013;Author: <%=folder.getCreatedBy()%> &#013;Date: <%=folder.getCreationDate().replace('T', ' ').substring(0,folder.getCreationDate().indexOf("."))%>"
					ondblclick="openBriefcase(this.id)"	 class="space select_box target folderContextMenuClass"
						id="<%=folPath%>"  >
						<!-- oncontextmenu="getDocProperties(this.id);getRightClickMenuFolder(this.id)"> -->
					
						<div style="display: none;"
			id="folderPermissions<%=folPath%>"><%=folder.getUserRead().toString().indexOf(userid)>=0%>,<%=folder.getUserWrite().toString().indexOf(userid)>=0%>,<%=folder.getUserSecurity().toString().indexOf(userid)>=0%></div>
		
						<div class="folder_brief"></div> <span><%if(folder.getFolderName().length()>50){ %><%=folder.getFolderName().substring(0,50)+"..."%><%} else{%><%=folder.getFolderName()%><% }%></span>
					 <div class="clear"></div>
					</li>
					
                              
                    <%} %>          
                              
                               <div class="clear"></div>
                           </ul>
                        
                           <div class="clear"></div>
                    </div>
                   <!-----// FOLDER ICON END ------>
                   <div class="clear"></div>
                   <!------/// FOLDER ICON ----->
                    <div class="folder_name"><div class="icon_folder file_fold"></div> File <span class="file_line"></span></div>
                    <div class="file_briefcase">
                           <ul>
                           
                           <%
						for (webmail.wsdl.File file : fileList) {
							String size="";
							long sizee=0;
							if(file.getFileSize()/1024>1024){
								sizee=(file.getFileSize()/(1024*1024));
								size=sizee+" MB";
							}else if(file.getFileSize()>1024){
								sizee=(file.getFileSize()/1024);
								size=sizee+" KB";						
							}else{
								size=file.getFileSize()+" Byte";
							}
							
					//System.out.println("name="+file.getFileName()+"---size="+file.getFileSize());
					
					%>
                           
                               <!-- <li>
                                    <div class="file_brief"></div>  
                                    <span>Test111111111111111111111111111</span>  
                                    <div class="clear"></div> 
                               </li> -->
                              
                             <li ondblclick="getPrevious()" 
						title="Name: <%=file.getFilePath().substring(file.getFilePath().lastIndexOf("/")+1)%> &#013;Type: File &#013;Size: <%=size %> &#013;Author: <%=file.getCreatedBy()%> &#013;Date: <%=file.getCreationDate().replace('T', ' ').substring(0,file.getCreationDate().indexOf("."))%>"
						 class="space select_box target fileContextMenuClass"
						id="<%=file.getFilePath()%>" name="<%=file.getFileName()%>" >
						<!-- oncontextmenu="getFileProperties(this.id);getRightClickMenuFile(this.id)"> -->
						<input type="hidden" id="hidsize_<%=file.getFileName()%>" value="<%=file.getFileSize()%>"/>
						<div style="display: none;"
			id="filePermissions<%=file.getFilePath()%>"><%=file.getUserRead().toString().contains(userid)%>,<%=file.getUserWrite().toString().contains(userid)%>,<%=file.getUserSecurity().toString().contains(userid)%></div>
						
						 <%
 	if(file.getFileName().contains(".pdf")){
 %>
						<div class="new_pdf"></div> <%
 	}else if(file.getFileName().contains(".doc")){
 %>
						<div class="new_word"></div> <%
 	}else if(file.getFileName().contains(".xls")||file.getFileName().contains(".csv")){
 %>
						<div class="new_msexcel"></div> <%
 	}else if(file.getFileName().contains(".ppt")){
 %>
						<div class="new_ppt"></div> <%
 	}else if(file.getFileName().contains(".jpg")||file.getFileName().contains(".JPG")){
 %>
						<div class="new_jpg"></div> <%
 	}else if(file.getFileName().contains(".png")){
 %>
						<div class="new_png"></div> <%
 	}else if(file.getFileName().contains(".gif")){
 %>
						<div class="new_gif"></div> <%
 	}else if(file.getFileName().contains(".xml")||file.getFileName().contains(".vcf")||file.getFileName().contains(".pod")||file.getFileName().contains(".ics")){
 %>
						<div class="new_txt"></div> <%
 	}else if(file.getFileName().contains(".txt")){
 %>
						<div class="new_txt"></div> <%
 	}else if(file.getFileName().contains(".bmp")){
 %>
						<div class="blank_image"></div> <%
 	}else if(file.getFileName().contains(".ico")){
 %>
						<div class="new_ico"></div> <%
 	}else{
 %>
						<div class="blank_image"></div> <%
 	}
 %>
						<!-- <img src="images/ms_excel_big.png" />
										<div class="clear"></div>
										 --> 
<span><%=file.getFileName()%> </span>
					</li>
                             
                             <%} %>
                               <div class="clear"></div>
                           </ul>
                            <br>
                           <div class="clear"></div>
                    </div>
                   <!-----// FOLDER ICON END ------>
           
           </div>
 
     
  </body>
</html>
