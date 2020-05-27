<html>
    <head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    </head>
    <body>
              <%
              String foo="foo";
              String cssheight="";
              HttpSession left_hs=request.getSession();
          	String lchatService=(String)left_hs.getAttribute("chatService");
          	if(!lchatService.equalsIgnoreCase("true"))
          	{
          		cssheight="height: 100% !important;";
          		foo="";
          	}
              %>
                <!---------------/// USER INFORMATION BOX END HERE ------------->
                <!------/// SEARCH BOX STARED HERE --------->
                
                <!------/// SEARCH BOX END HERE -----------> 

                <!-----/// LEFT PANEL STARED HERE ------->
                <div class="left-pane " id="MySplitter"> 

                    <!--------------///// WHEN LEFT IS OPEN STARED HERE ----->
                    <div class="left_open"> 
                        <!------- /// LEFT TOP STARED HERE ------->
                       <!--  <div class="top_left">

                            <a href="#">
                                <div class="top_right_icon" id="hide_left"> <img src="images/open_state.png" /> </div>
                            </a> </div> -->

                        <!------- /// LEFT TOP END HERE ------->
                        <div class="clear"></div>
                        <!-------/// LEFT MID CONTENT STARED HERE --------->
                        <div class="left_tab_content" id="<%=foo %>">
                          <div  class="folder_div_nps left_three_box " id="a" style="<%=cssheight %>">
                          
                            
                           </div>      
                        
     