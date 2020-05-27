<html>
    <head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="images/favicon.ico"/>
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <link type="text/css" rel="stylesheet" href="css/blue.css"/>
         <link type="text/css" rel="stylesheet" href="css/main_css.css"/>
		<!------<link rel="stylesheet" href="css/jquery-ui.css">--->
        <script src="js/jquery-1.8.3.min.js"></script>
		<!--<script src="js/jquery-ui.js"></script>-->
        <script src="js/splitter.js"></script>
        <script src="js/sytem-script.js"></script>
        <script src="css/jquery-ui.css"></script>
      <script src="js/jquery-ui.js"></script>
       
        <style>
            .vsplitbar {
                width: 5px;
                background: #aaa;
            }
        </style>

        <style>
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

    </head>
    <body>
        <div class="minwidth">
            <!----------/// HEADER STARED HERE -------->
            <div class="header">
                <div class="header_inner">
                    <div class="logo"> <img src="images/logo.png" /> </div>
                    <div class="header_right">
                     <div class="main_search_folder">
                        <div class="search_it">
                            <input type="text" onClick="return Hide();" placeholder="Search Here....">
                            <a href="#">
                                <div class="down_arrow" onClick="return showHide(event);"></div>
                            </a> 
                            <!---------- /// SEARCH FROM STARTED HERE -----> 

                            <!-----------/// SEARCH FROM END HERE ---------> 
                            <a href="#">
                                <div class="search_icon"></div>
                            </a> 


                        </div>
                        
                     </div>   
                        <div class="new_user" onClick="userinformation(event);" ><img src="images/photo.jpg" /></div>


                        

                    </div>

                </div>

            </div>
            <!----------/// HEADER END HERE ----------> 
            <!---------/// MID CONTENT STARED HERE ---->
            <div class="content"> 
            <div id="search_form" class="search_form_1">
                    <div class="form">
                        <form action="" method="get">
                            <div class="to">
                                <div class="name search_text">Search</div>
                                <a href="#"><div class="search_top">All Mail</div></a>
                                <div class="clear"></div>
                                <div class="name">From</div>
                                <input type="text" name="" value="" class="border input" id="mytext">
                                <div class="name">To</div>
                                <input type="text" name="" value="" class="border input" id="mytext">
                                <div class="name">Subject</div>
                                <input type="text" name="" value="" class="border input" id="mytext">
                                <div class="name">Has the words</div>
                                <input type="text" name="" value="" class="border input" id="mytext">
                                <div class="name">Dosen't have</div>
                                <input type="text" name="" value="" class="border input" id="mytext">
                                <div class="check">
                                    <input name="" type="checkbox" value="" id="mytext">
                                    <span>Has attachment</span></div>
                                <div class="clear"></div>
                                <div class="check check_upper">
                                    <input name="" type="checkbox" value="">
                                    <span>Don't include chats</span></div>
                            </div>
                            <div class="search_button"><a href="#" title="Search"> <span class="search_icon2"> </span></a> </div>
                        </form>
                    </div>
                </div>
                <!-----------/// USER INFORMATION BOX STARED HERE --------->
                <div class="user_information arrow_box">

                    <!----------/// TOP SECTION STARED HERE ------------>
                    <div class="user_top">
                        <a href="#">
                            <img src="images/photo_1.jpg" />
                            <div class="change_images">Change photo</div>
                        </a>

                        <div class="left_top">
                            Hariom Srivastava
                            <span>hariom15791@gmail.com</span>
                            <div class="clear_2"></div>
                            <div class="progress_bar">
                                 <img src="images/Progress_Bar.png" />
                                 <div class="percentage_value"></div>
                                 <div class="clear"></div>
                            </div>
                               <div class="color_pad"><img src="images/color.png" /></div>
                        </div>
					</div>
        <!------------/// TOP SECTION END HERE -------------->
        <!----------/// BOTTOM SECTION STARED HERE ------------>
                    <div class="user_bottom">
                     
                            <a href="#">
                            <div class="sing_out">

                                Sign out

                            </div>
                        </a>
                           <a href="#">
                            <div class="sing_out right_space">

                                Profile

                            </div>
                        </a>




                    </div>

                    <!------------/// BOTTOM SECTION END HERE -------------->





                </div>
                <!---------------/// USER INFORMATION BOX END HERE ------------->
                <!------/// SEARCH BOX STARED HERE --------->
                
                <!------/// SEARCH BOX END HERE -----------> 

                <!-----/// LEFT PANEL STARED HERE ------->
                <div class="left-pane"> 

                    <!--------------///// WHEN LEFT IS OPEN STARED HERE ----->
                    <div class="left_open"> 
                        <!------- /// LEFT TOP STARED HERE ------->
                        <div class="top_left">

                            <a href="#">
                                <div class="top_right_icon" id="hide_left"> <img src="images/open_state.png" /> </div>
                            </a> </div>

                        <!------- /// LEFT TOP END HERE ------->
                        <div class="clear"></div>
                        <!-------/// LEFT MID CONTENT STARED HERE --------->
                        <div class="left_tab_content">
                          <div class="left_three_box">
                             <ul class="left_margin" style="display: block;">
                                            <li><a href="#" class="active_mailbox">Inbox &nbsp;<span>(4)</span></a></li>

                                            <li><a href="#">Drafts</a></li>
                                            <li><a href="#">Sent Mail</a></li>
                                            <li><a href="#" >Spam</a></li>
                                            <li><a href="#">Trash fdfdsds ds fds dsfdsfdsf dsf ds</a></li>
                                            <div class="subfolder_create">
                                                         
                                                           <p title="All Project Related To Working">All Project Related To Working</p>
                                                           <p>Backup Folder</p>
                                                        
                                             </div>
										  <li class="dcjq-parent-li"><a href="#" class="dcjq-parent active sub_folder_inner">Css Animation<span class="dcjq-icon"></span></a>
                                         <ul style="display: block;" class="subfolder_onhover">
										 <li><a href="#">Animation Part 1</a></li>
                                                            <li><a href="#">Animation Part 2</a></li>
                                                            <li><a href="#">Animation Part 3</a></li>
                                                            <li><a href="#">Animation Part 4sd  dsfdsfds dsfds trete rtr e</a></li>
															</ul>
															</li>
                                            <li class="bottom dcjq-parent-li">
                                                <a href="#" class="dcjq-parent active" style="padding-left: 9px;">Interesting css codes<span class="dcjq-icon"></span></a>
                                                <ul style="display: block;" class="subfolder_onhover">
												 <li><a href="#">Animation Part 1</a></li>
                                                            <li><a href="#">Animation Part 2</a></li>
                                                            <li><a href="#">Animation Part 3</a></li>
                                                            <li><a href="#">Animation Part 4</a></li>
                                                            <li><a href="#">Animation Part 1</a></li>
                                                    <li class="dcjq-parent-li"><a href="#" class="dcjq-parent active sub_folder_inner">Css Animation<span class="dcjq-icon"></span></a>
                                                        <ul class="left_margin" style="display: block;">
                                                            <li><a href="#">Animation Part 1</a></li>
                                                            <li><a href="#">Animation Part 2</a></li>
                                                            <li><a href="#">Animation Part 3</a></li>
                                                            <li><a href="#">Animation Part 4</a></li>
                                                            <li><a href="#">Animation Part 1</a></li>
                                                            <li><a href="#">Animation Part 2</a></li>
                                                            <li><a href="#">Animation Part 3</a></li>
                                                            <li><a href="#">Animation Part 4</a></li>
                                                            <li><a href="#">Animation Part 1</a></li>
                                                            <li><a href="#">Animation Part 2</a></li>
                                                            <li><a href="#">Animation Part 3</a></li>
                                                            <li><a href="#">Animation Part 4</a></li>
                                                            <li><a href="#">Animation Part 1</a></li>
                                                            <li><a href="#">Animation Part 2</a></li>
                                                            <li><a href="#">Animation Part 3</a></li>
                                                            <li><a href="#">Animation Part 4</a></li>
                                                            <li><a href="#">Animation Part 1</a></li>
                                                            <li><a href="#">Animation Part 2</a></li>
                                                            <li><a href="#">Animation Part 3</a></li>
                                                            <li><a href="#">Animation Part 4</a></li>
                                                            <li><a href="#">Animation Part 1</a></li>
                                                            <li><a href="#">Animation Part 2</a></li>
                                                            <li><a href="#">Animation Part 3</a></li>
                                                            <li><a href="#">Animation Part 4</a></li><li><a href="#">Animation Part 1</a></li>
                                                            <li><a href="#">Animation Part 2</a></li>
                                                            <li><a href="#">Animation Part 3</a></li>
                                                            <li><a href="#">Animation Part 4</a></li>
                                                            <li><a href="#">Animation Part 1</a></li>
                                                            <li><a href="#">Animation Part 2</a></li>
                                                            <li><a href="#">Animation Part 3</a></li>
                                                            <li><a href="#">Animation Part 4</a></li>
                                                            
                                                            
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li><a href="#"></a></li>
                             

                                        </ul>
                           </div>
                           
                           
                              <div class="chat_box">
                              
                                              <div class="chat_box_inner">
                                                          <div class="chat_heading">
                                          <div class="chat_h_left">
                                               <img src="images/photo.jpg" class="h_name" />
                                               <div class="chat_h_name">Hariom Srivastava</div>
                                               <div class="h_drop"><img src="images/down_mail.png" /></div>
                                          </div>
                                           <div class="chat_h_right" title="Find people to chat with" >
                                               <img src="images/search_1.jpg" />
                                           </div>
                                       </div>
                                       
                                                        <div class="chat_inner_content">
                                       <!---------------/// FIRST ROW STARTED HERE --------->
                                       <div class="cheat_row">
                                            <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                                            <div class="contact_information">
                                              <p><strong>Jonathan Smith</strong><br>
                                                Work for fun</p>
                                            </div>
                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                      </div>
                                      <!-------------/// Chat Info Box Stared here -------->
                                   
                                      <!------------//// Chat Info Box End Here ----------->
                                       <!--------------/// FIRST ROW END HERE --------------->
                                       
                                       
                                       
                                       
                                             <!---------------/// FIRST ROW STARTED HERE --------->
                                       <div class="cheat_row">
                                            <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                                            <div class="contact_information">
                                              <p><strong>Jonathan Smith</strong><br>
                                                Work for fun</p>
                                            </div>
                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                      </div>
                                       <!--------------/// FIRST ROW END HERE --------------->
                                             <!---------------/// FIRST ROW STARTED HERE --------->
                                       <div class="cheat_row">
                                            <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                                            <div class="contact_information">
                                              <p><strong>Jonathan Smith</strong><br>
                                                Work for fun</p>
                                            </div>
                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                      </div>
                                       <!--------------/// FIRST ROW END HERE --------------->
                                             <!---------------/// FIRST ROW STARTED HERE --------->
                                       <div class="cheat_row">
                                            <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                                            <div class="contact_information">
                                              <p><strong>Jonathan Smith</strong><br>
                                                Work for fun</p>
                                            </div>
                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                      </div>
                                       <!--------------/// FIRST ROW END HERE --------------->
                                             <!---------------/// FIRST ROW STARTED HERE --------->
                                       <div class="cheat_row">
                                            <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                                            <div class="contact_information">
                                              <p><strong>Jonathan Smith</strong><br>
                                                Work for fun</p>
                                            </div>
                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                      </div>
                                       <!--------------/// FIRST ROW END HERE --------------->
                                             <!---------------/// FIRST ROW STARTED HERE --------->
                                       <div class="cheat_row">
                                            <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                                            <div class="contact_information">
                                              <p><strong>Jonathan Smith</strong><br>
                                                Work for fun</p>
                                            </div>
                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                      </div>
                                       <!--------------/// FIRST ROW END HERE --------------->
                                             <!---------------/// FIRST ROW STARTED HERE --------->
                                       <div class="cheat_row">
                                            <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                                            <div class="contact_information">
                                              <p><strong>Jonathan Smith</strong><br>
                                                Work for fun</p>
                                            </div>
                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                      </div>
                                       <!--------------/// FIRST ROW END HERE --------------->
                                             <!---------------/// FIRST ROW STARTED HERE --------->
                                       <div class="cheat_row">
                                            <div class="small_images"> <img src="images/blank_man.jpg" /> </div>
                                            <div class="contact_information">
                                              <p><strong>Jonathan Smith</strong><br>
                                                Work for fun</p>
                                            </div>
                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                      </div>
                                       <!--------------/// FIRST ROW END HERE --------------->
                                       
                                       
                                       
                                       </div>
                                       
                                               </div>
                        <!---------------//// CHAT SINGH IN ----------->
                                  <div class="chat_sign_box">
                                         <div class="chat_sign_images">
                                             <img src="images/chat-4-xxl.png" />
                                         </div>
                                         <div class="chat_sign_1_box">Sign in</div>
                                  </div>
                        <!-----------------//// CHAT SINGH IN END ------->
                                       
        
                                        <div class="all_chat_option">
                                       <div class="chat_info arrow-left">
                                           <div class="chat_info_left">
                                              <p class="name">Hariom Srivastava</p>
                                              <span>hari@silvereye.co</span>
                                              <p class="com">Opportunities don't happen,you create them..</p>
                                           </div>
                                           <div class="chat_info_right">
                                              <img src="images/photo_1.jpg" />
                                           
                                           </div>
                                           <div class="clear"></div>
                                           <div class="bottom_option">
                                              <!-------/// Bottom _Left_part--->
                                                <div class="left_bottom">
                                                    <ul>  
                                                       <li><a href="#">Contact info</a></li>
                                                       <li><a href="#">Emails</a></li>
                                                    </ul>
                                                </div>
                                              <!----------/// Bottom Left Part End --->
                                              <!--------/// Bottom Right part Stared Here ------>
                                               <div class="right_bottom">
                                                      <ul>
                                                         <li class="chat_mail"><a href="#"></a></li>
                                                         <li class="contact_mail"><a href="#"></a></li>
                                                         <li><a href="#"></a></li>

                                                      </ul>
                                               
                                               </div>
                                              <!---------------/// Bottom  Right Part End Here -------->	
                                           
                                           </div>
                                      
                                      </div>
                                      
                                       <!-----------------//// Chat Search option Here --------->
                                       <div class="chat_info_1 arrow-left">
                                           <div class="chat_info_left">
                                              <p class="name">Hariom Srivastava</p>
                                              <span>hari@silvereye.co</span>
                                              <p class="com">Opportunities don't happen,you create them..</p>
                                           </div>
                                           <div class="chat_info_right">
                                              <img src="images/photo_1.jpg" />
                                           
                                           </div>
                                           <div class="clear"></div>
                                           <div class="bottom_option">
                                              <!-------/// Bottom _Left_part--->
                                                <div class="left_bottom">
                                                    <ul>  
                                                       <li><a href="#">Add to contacts</a></li>
                                                       <li><a href="#">Emails</a></li>
                                                    </ul>
                                                </div>
                                              <!----------/// Bottom Left Part End --->
                                              <!--------/// Bottom Right part Stared Here ------>
                                               <div class="right_bottom">
                                                      <ul>
                                                         <li class="chat_mail"><a href="#"></a></li>
                                                         <li class="contact_mail"><a href="#"></a></li>
                                                         <li><a href="#"></a></li>

                                                      </ul>
                                               
                                               </div>
                                              <!---------------/// Bottom  Right Part End Here -------->	
                                           
                                           </div>
                                      
                                      </div>
                                       <!---------------/// Chat Search End Here ----------->
                                                        <!-------/// Chat Search option--------->
                                         <div class="chat_search">
                                              <div class="heading_caht">
                                                   <input type="text" placeholder="Search for people" />
                                              </div>
                                              <!-----//// Chat Search Content ----->
                                                                      <div class="chat_search_content">
                                              
                                                        <!------------//// FIRST ROW --------->
                                                           <div class="cheat_row_11">
                                                                  <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                      <div class="contact_information">
                                                                      <p><strong>Jonathan Smith</strong><br>
                                                                        Work for fun</p>
                                                                    </div>
                                                                  <div class="online_file"> <img src="images/online_file.png"> </div>
                                                           </div>
                                                        <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>

                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                                              <!------------//// FIRST ROW --------->
                                                     <div class="cheat_row_11">
                                                            <div class="small_images"> <img src="images/blank_man.jpg"> </div>
                                                                <div class="contact_information">
                                                                <p><strong>Jonathan Smith</strong><br>
                                                                  Work for fun</p>
                                                              </div>
                                                            <div class="online_file"> <img src="images/online_file.png"> </div>
                                                     </div>
                                                  <!-----------/// FIRST ROW -------------->
                                              
                                              </div>
                                              <!--------/// Chat Search End -------->
                                         
                                         </div>
                        <!------------/// Chat Search Option End ------>
                          <!-------/// Chat Downarrow option--------->
                                         <div class="chat_search_11">
                                            
                                              <!-----//// Chat Search Content ----->
                                               <div class="chat_downarrow">
                                               <!--------------//// Chat Down Main Page ------------->
                                               <div class="chat_down_main">
                                                          <div class="chat_down_top">
                                                            <div class="chat_down_left">
                                                                <img src="images/photo.jpg" />
                                                            </div>
                                                            <div class="chat_down_right">
                                                                Hariom Srivastava
                                                                <span>hari@silvereye.co</span>
                                                            </div>

                                                            <div class="clear"></div>
                                                       </div>
                                                       <div class="clear"></div>
                                                          <ul>
                                                            <li class="chat_status_menu"><a href="#" >Chat&nbsp;Status </a></li>
                                                            <li class="invites_menu"><a href="#">Invites</a></li>
                                                            <li class="blocked_menu"><a href="#">Blocked&nbsp;People </a></li>
                                                            <li class="share_your_menu"><a href="#"> Share&nbsp;your&nbsp;status </a></li>
                                                       </ul>
                                                       <div class="clear"></div>
                                                       <div class="chat_out">Sign out of Chat</div>
                                                </div>
                                              <!------------------/// Chat Down Menu End ------------->
                                       
                                              </div>
                                              <!--------/// Chat Search End -------->
                                              <!------------/// Chat Sub menu ----------->
                                              <div class="chat_down_submenu">
                                              <!-------------// Chat Status box Stared here----------->
                                                <div class="chat_status">  
                                                   <!--------///Chat Haeding ---->
                                                   <div class="chat_subheading">
                                                      <div class="chat_main_menu"><img src="images/portlet-remove-icon.png"/></div>
                                                      <p>Chat Status </p>
                                                      <div class="clear"></div>
                                                    </div>
                                                   <!-----------/// Chat Heading End Here ----->
                                                   <!----------/// Chat Status Content ------->
                                                   <div class="chat_status_content">
                                                        
                                                        <ul>
                                                          <li><input type="radio" /><span>Online</span> <div class="online"></div></li>
                                                          <li><input type="radio" /><span>Offline</span> <div class="offline"></div></li>
                                                          <li><input type="radio" /><span>Busy</span> <div class="busy"></div></li>
                                                          <li><input type="radio" /><span>Away</span> <div class="away"></div></li>
                                                        </ul>
                                                   
                                                   
                                                   </div>
                                                   <!-----------/// Chat Status  End Here ----->
                                                 </div>
                                              <!-------------/// Chat Status Box End Here--------->
                                               <!-------------// Invites box Stared here----------->
                                                         <div class="Blocked_status">  
                                                   <!--------///Chat Haeding ---->
                                                   <div class="chat_subheading">
                                                      <div class="chat_main_menu"><img src="images/portlet-remove-icon.png"/></div>
                                                      <p>Invites </p>
                                                      <div class="clear"></div>
                                                    </div>
                                                   <!-----------/// Chat Heading End Here ----->
                                                   <!----------/// Chat Status Content ------->
                                                   <div class="chat_status_content">
                                                        <!----------/// Main ROW Content ---------->
                                                        <div class="chat_row_content">
                                                     <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                        <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                        <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                        <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                     </div>
                                                   <!----------/// Main Row Content End Here ---------->
                                                   <div class="clear"></div>
                                                   </div>
                                                   <!-----------/// Chat Status  End Here ----->
                                                   <div class="clear"></div>
                                                 </div>
                                              <!-------------/// Invites Box End Here--------->
                                                  <!-------------// Blocked People  box Stared here----------->
                                                         <div class="Invites_status">  
                                                   <!--------///Chat Haeding ---->
                                                   <div class="chat_subheading">
                                                      <div class="chat_main_menu"><img src="images/portlet-remove-icon.png"/></div>
                                                      <p>Blocked People</p>
                                                      <div class="clear"></div>
                                                    </div>
                                                   <!-----------/// Chat Heading End Here ----->
                                                   <!----------/// Chat Status Content ------->
                                                   <div class="chat_status_content">
                                                           <!----------/// Main ROW Content ---------->
                                                        <div class="chat_row_content">
                                                     <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                        <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                        <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                        <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                     </div>
                                                   <!----------/// Main Row Content End Here ---------->
                                                   </div>
                                                   <!-----------/// Chat Status  End Here ----->
                                                 </div>
                                              <!-------------/// Blocked People  Box End Here--------->
                                                <!-------------// Blocked People  box Stared here----------->
                                                         <div class="Share_status">  
                                                   <!--------///Chat Haeding ---->
                                                   <div class="chat_subheading">
                                                      <div class="chat_main_menu"><img src="images/portlet-remove-icon.png"/></div>
                                                      <p> Share your status </p>
                                                      <div class="clear"></div>
                                                    </div>
                                                   <!-----------/// Chat Heading End Here ----->
                                                   <!----------/// Chat Status Content ------->
                                                   <div class="chat_status_content">
                                                        
                                                   
                                                   <!----------/// Chat --------->
                                                     <!----------/// Main ROW Content ---------->
                                                        <div class="chat_row_content">
                                                     <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">

                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                        <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                        <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>
                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                        <!-----------/// INVITE ROW FIRST STARED HERE ---------->
                                                              <div class="invite_row">
                                                     <!---------------/// INVITE LEFT PART -------->
                                                     <div class="invite_left">Rohit Tiwari</div>

                                                     <!--------------/// INVITE LEFT END HERE -------->
                                                     <!---------------/// INVITE RIGHT PART -------->
                                                     <div class="invite_right">
                                                     <a href="#">Unblock</a>
                                                     </div>
                                                     <!--------------/// INVITE RIGHT END HERE -------->
                                                     <div class="clear"></div>
                                                     </div>
                                                     <!-------------/// INVITE Row END HERE -------------->
                                                     </div>
                                                   <!----------/// Main Row Content End Here ---------->
                                                   <!----------/// Chat End -------->
                                                   
                                                   </div>
                                                   <!-----------/// Chat Status  End Here ----->
                                                 </div>
                                              <!-------------/// Blocked People  Box End Here--------->
                                              </div>
                                              <!---------------//// Chat Down Sub Menu ---------->
                                         
                                         </div>
                        <!------------/// Chat Downarrow Option End ------>
                        </div>
                                       
                                       
                              </div>
                        </div>
                        
               
                   
                        
                        
                        <!--------/// LEFT MID CONTENT END HERE --------------> 

                        <!-------/// LEFT BOTTOM ICON STARED HERE --->
                        <div id="mailview-bottom2" class="uibox bottom_mail">
                            <ul class="background_bottom bootom_icon_ul">
                                <li><a href="#"><img src="images/bootom_icon_1.png" class="icon1"></a></li>
                                <li><a href="#"><img src="images/bootom_icon_2.png" class="icon_2"></a></li>
                                <li><a href="#"><img src="images/bootom_icon_3.png" class="icon_3"></a></li>
                                <li><a href="#"><img src="images/bootom_icon_4.png" class="icon_4"></a></li>
                                <li><a href="#"><img src="images/bootom_icon_5.png"></a></li>
                                <li><a href="#"><img src="images/bootom_icon_6.png"></a></li>
                                <div class="clear"></div>
                            </ul>
                            <div class="clear"></div>
                        </div>

                        <!-------/// LEFT BOTTOM ICON END HERE ---> 
                    </div>
                    <!--------------///// WHEN LEFT IS OPEN End HERE -----> 

                </div>
                <!-----------/// LEFT PANNEL END HERE -------> 

                <!-------/// LEFT PANNEL WHEN IT CLOSE ------>
                <div class="left_close">
                    <div class="top_left"> <a href="#">
                            <div class="top_right_icon" id="show_left"> <img src="images/next_mail.png" /> </div>
                        </a> </div>
                    <div class="mid_close_content">
                        <ul>
                            <li> <a href="#"><img src="images/inbox_blue.png" /></a> </li>
                            <li> <a href="#"><img src="images/sent.png" /></a> </li>
                            <li> <a href="#"><img src="images/delet.png" /></a> </li>
                            <li> <a href="#"><img src="images/all_folder.png" /></a> </li>
                        </ul>
                    </div>
                    <div id="mailview-bottom1" class="uibox close_bottom">
                        <ul>
                            <li><a href="#"><img src="images/photo.jpg" /></a></li>

                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <li><a href="#"><img src="images/blank_man.jpg" /></a></li>
                            <div class="clear"></div>
                        </ul>
                        <div class="clear"></div>
                    </div>
                </div>
                <!-------/// LEFT PANNEL WHEN IT CLOSE END ------> 

                <!------/// RIGHT PANNEL ONLY FOR TOOL-------->

                <div class="right-pane">
                    <div class="right_top_pannel"> 

                        <!-------// RIGHT TOP TOOL END HERE -------->
                        <div class="for_tool">
                            <ul>
                                <li>
                                    <div class="tool_inner_box">
                                        <ul id="menu">
                                            <li>
                                                <input type="checkbox" class="check_box">
                                                <a href="#" class="sub_menu_link"><img src="images/open_sub_menu.png" style="margin-left: -5px;"></a>
                                                <ul>
                                                    <li> <a href="#">All</a></li>
                                                    <li><a href="#">None</a></li>
                                                    <li> <a href="#">Read</a></li>
                                                    <li> <a href="#">Unread</a></li>
                                                    <li> <a href="#">Starred</a></li>
                                                    <li> <a href="#">Unstarred</a></li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <div class="large_tool search_form_tool" >
                                        <ul>
                                            <li class="hidden_option"><a href="#"><img src="images/back_one.png"></a></li>
                                            <div class="right_border"></div>
                                            <li class="hidden_option"><a href="#"><img src="images/back_doble.png"></a></li>
                                            <div class="right_border"></div>
                                            <ul id="menu" class="next_mail">
                                                <li> <a href="#" style="padding:0px;"><img src="images/next.png"></a> <a href="#" class="sub_menu_link" style="padding:0px;"><img src="images/open_sub_menu.png"></a>
                                                    <ul>
                                                        <li> <a href="#">Forward Inline</a></li>
                                                        <li><a href="#">Forward As Attachment</a></li>
                                                    </ul>
                                                </li>
                                            </ul>
                                            <div class="right_border"></div>
                                            <li><a href="#"><img src="images/tool_delete.png"></a></li>
                                            <div class="right_border"></div>
                                            <li><a href="#"><img src="images/restriction.png"></a></li>
                                            <div class="right_border"></div>
                                            <ul id="menu" class="next_mail more_arrow">
                                                <li class="more"> More </li>
                                                <li><a href="#" class="sub_menu_link"><img src="images/open_sub_menu.png"></a>
                                                    <ul style="margin-left: -27px !important;">
                                                        <li> <a href="#">Mark as unread</a></li>
                                                        <li><a href="#">Add Star</a></li>
                                                        <li> <a href="#">Add To Task</a></li>
                                                        <li> <a href="#">Create Filter </a></li>
                                                        <li> <a href="#">Add To Task </a></li>
                                                        <li> <a href="#">Create Event </a></li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </ul>
                                    </div>
                                    <div class="calender_tool">
                                        <ul id="menu">
                                            <li > <img src="images/tool_calender.png" class="four_margin calender_images"> </li>
                                            <li style="margin-left: 12px;"><a href="#" class="sub_menu_link"><img src="images/open_sub_menu.png" ></a>
                                                <ul class="for_calenderand_date">
                                                    <li> <a href="#">Compose Messages</a></li>
                                                    <li><a href="#">Compose SMS</a></li>
                                                     <li> <a href="#">Create Contact</a></li>
                                                    <li> <a href="#">Create Event</a></li>
                                                    <li> <a href="#">Create Task</a></li>
                                                   
                                                </ul>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                            </ul>
                            <!---- RIGHT TOOL STARTED HERE ---->
                            <!--------/// Main Right Tool Stared Here -------->
                           <div class="right_tool_part">
                            <div class="right_tool"> <a href="#"> <img src="images/reload.png"> </a> </div>
                            <div class="right_tool_1">
                                <ul id="menu" >
                                    <li> <img src="images/setting_toll.png" class="four_margin" ></li>
                                    <li class="right_menu_1" >
                                    <img src="images/open_sub_menu.png" style="margin-left: 8px !important;">
                                       <!-- <ul class="for_setting">
                                            <li> <a href="#">Settings</a></li>
                                            <li><a href="#">Themes</a></li>
                                            <li> <a href="#">Help</a></li>
                                        </ul>-->
                                    </li>
                                </ul>
                                
                            </div>
                            <div class="right_tool_1">
                                <ul id="menu">
                                    <li> <img src="images/multi_level.png"> <a href="#" class="sub_menu_link"><img src="images/open_sub_menu.png" style="margin-left: 8px;"></a>
                                        <ul>
                                            <li> <a href="#" onClick="toggleViewPanel()">Off</a></li>
                                            <li><a href="#" onClick="shiftViewLeft();">Left view</a></li>
                                            <li> <a href="#" onClick="shiftViewBottom();">Bottom view</a></li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                            <a href="#">
                                <div class="right_tool_1"> <img src="images/next_mail.png" class="next_imag"> </div>
                            </a> <a href="#">
                                <div class="right_tool_1"> <img src="images/privious_mail.png" class="next_imag"> </div>
                            </a>
                            <div class="right_tool_2" style="margin-left: -14px;line-height: 29px;"> 1-10 of 300 </div>
                            </div>
                            
                            <!-------------------/// Main Right Tool End Here ------------->
                        </div>
                        <!------ RIGHT TOOL END HERE TOP ---->

                        <div class="top_tool_information">
                            <div class="left_function">
                                <ul>
                                    <li class="flag_heading"><img src="images/black_star.png"></li>
                                    <li class="form_heading">FROM</li>
                                    <li>SUBJECT</li>
                                </ul>
                            </div>
                            <div class="right_bortion">
                                <ul >
                                    <li> <a href="#"><img src="images/attachment.png"></a> </li>
                                    <li> <a href="#"><img src="images/tool.png"></a> </li>
                                </ul>
                            </div>
                            <div class="right_bortion  date">
                                <ul id="menu" class="">
                                    <li style="height: 23px;">
                                        <div class="date_div"> DATE <a href="#"><img src="images/open_menu.png"></a></div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>


<!-----------------------------/// Main Cointer Stared here --------------->
                    <div id="widget" class="widget_new" >
                    
                    
                    
                    <!---------------/// Tab Content Stared Here Off VIEW ---------------------------->
                        <div class="mid_tab tab_main_div">
                        
                        
                               <div class="mid_tab_1 right_tab">
      <div class="inner_tab_content"> 
        
        <!-------/// TAB HEADING FIRST ----->
        <div class="main_tab_first"> 
          <!-------/// TAB HEADING STARTED HERE -----> 
     
          
          <!-------/// TAB HEADING END HERE -----> 
          <!----/// TAB CONTENT MAIN STARTED HERE -------> 
          
          <!----------// TAB FIRST CONTENT STARED HERE -------->
          <div class="tab_first_content">
          
             <!-----------/// ROW Stared Here ------------>
            <div class="row_content">
                        <!-------------/// ROW FIRST ------------->
                        <div class="row_first ">
                        
                                  <!--------/// ROW FIRST LEFT STARED HERE --------->
                                  <div class="row_left unread_message">
                                  
                                                  <div class="flag_first">
                                                  
                                                              <div class="row_check_box">
                                                                 
                                                                  <input type="checkbox" class="mail_checked">
                                                              
                                                              </div>
                                                  
                                                              <a href="#">
                                                                         <div class="small_image" onClick="star();"></div>
                                                              </a>
                                                  
                                                  
                                                  </div>
                                                  
                                                  <div class="form_first">
                                                  
                                                              Preeti Singh
                                                  
                                                  </div>
                                                  
                                                  <div class="subject_first">
                                                   <div class="forword_icon"></div>
                                                  Microsoft Outlook Test Message
                                                  </div>
                                               
                                                  
                                  </div>
                                  <!-----------/// ROW FIRST LEFT END HERE ------------->
                                  
                        <!--------/// ROW FIRST RIGHT STARED HERE ----------->
                        <div class="right_row_first">
                             
                                 <div class="row_date">8:36 am</div>
                                 <div class="row_attach"><img src="images/attachment.png"></div>
                                 <a href="#"><div class="row_delete"></div></a>
                        
                        
                        </div>
                        <!---------/// ROW FIRST RIGHT END HERE----------->
                        
                        
                        </div>
                         <!-------------/// ROW FIRST END ------------->
             
             <!-------------/// ROW SECOND START ------------>
                       <div class="message">
                      
                       This is an e-mail message sent automatically by Microsoft This is an e-mail message sent automatically by MicrosoftThis is an e-mail message sent automatically by Microsoft.... 
                       </div>
             <!-------------/// ROW SECOND SEND ------------->
               <div class="clear"></div>

            </div>

         
          <!--------------//// ROW End Here --------------->
             <!-----------/// ROW Stared Here ------------>
            <div class="row_content" >
                        <!-------------/// ROW FIRST ------------->
                        <div class="row_first">
                        
                                  <!--------/// ROW FIRST LEFT STARED HERE --------->
                                  <div class="row_left">
                                  
                                                  <div class="flag_first">
                                                  
                                                              <div class="row_check_box">
                                                                 
                                                                  <input type="checkbox" class="mail_checked">
                                                              
                                                              </div>
                                                  
                                                              <a href="#">
                                                                         <div class="small_image"></div>
                                                              </a>
                                                  
                                                  
                                                  </div>
                                                  
                                                  <div class="form_first">
                                                  
                                                              Preeti Singh
                                                  
                                                  </div>
                                                  
                                                  <div class="subject_first">
                                                   <div class="forword_icon"></div>
                                                  Microsoft Outlook Test Message Microsoft Outlook Test Message Microsoft Outlook Test Message Microsoft Outlook Test Message 
                                                  </div>
                                                  
                                  </div>
                                  <!-----------/// ROW FIRST LEFT END HERE ------------->
                                  
                        <!--------/// ROW FIRST RIGHT STARED HERE ----------->
                        <div class="right_row_first">
                             
                                 <div class="row_date">8:36 am</div>
                                 <div class="row_attach"><img src="images/attachment.png"></div>
                                 <a href="#"><div class="row_delete"></div></a>
                        
                        
                        </div>
                        <!---------/// ROW FIRST RIGHT END HERE----------->
                        
        
                        </div>
                         <!-------------/// ROW FIRST END ------------->
             
             <!-------------/// ROW SECOND START ------------>
                       <div class="message">
                       This is an e-mail message sent automatically by Microsoft.... 
                       </div>
             <!-------------/// ROW SECOND SEND ------------->
               <div class="clear"></div>

            </div>

          <!--------------//// ROW End Here --------------->
          </div>
          <!----------// TAB FIRST CONTENT END HERE -------->
          <div class="clear"></div>
        </div>
              
        
        <!-----/// RIGHT MID CONTENT TAB STARED HERE --------> 
      </div>
    </div>
                        
                        
                        
                        
                        </div>
                    <!-----------------/// Tab Content End Here Off VIEW---------------------------->  
                    
                    
                    
                    <!-------------------/// Right View and Bottom View Tab Stared Here --------------->
                      
                        <div class="mail_content">
                                       
                                       
                                       
                                       <div class="mail_content_1" style="width: 48.5%; float: right; display: block; min-height: 75%; max-height: 95%;"> 
    
    <div class="mail_left_content">
    
    <!--------------/// Top Portion Started Here ------------>
    
           <div class="top_bottom_1">
        <h1>All New Job Opportunities for Fresher </h1> 
        <div class="mail_right_1"> 
          <a href="#"><div class="printer"></div></a> 
        <a href="#"><div class="fullscreen"></div> </a>
       </div>
        <div class="clear"></div>
        <div class="clear"></div>
        </div>
    
    <!------------------/// Top Portion End Here ---------------->
    
      <!------// MAIL HEADER STARED HERE ------->
      <div class="mail_header mail_header_top" style="padding-top: 0px;"> 
        
        <!---- MAIL HEADER LEFT ------>
    <div class="mail_left"> 
        
       <div class="images_hover">
        <img src="images/mail_icon.png">
        
              <!---------/// Images Details Stared Here ---------->
        <div class="images_details">
                  
                       <div class="mail_left_1">
                    <span>Timesjobs Job Alert</span>
                      <div class="clear"></div>
                    jobalert@timesjobs.com
                    </div>
                  
                  <img src="images/unnamed.png">
                  
                  
                       <!-----------//// Bottom Images Details ------->
                  
                                     <div class="send_mail_deatils">
                     <a href="#">Add to contacts</a>
                 
                         <a href="#">Emails</a>
                         
                         <!-------/// Right Portion ------->
                         <div class="right_forw">
                         
                         
                         <a href="#">
                               <div class="mail_for">
                              <img src="images/plus_option.gif">
                              </div>
                              </a>
                              
                              <a href="#">
                              
                              <div class="mail_for_1">
                              <img src="images/mail_fow.png">
                              </div>
                              
                              </a>
                         
                         </div>

                         <!-------/// Right Portion End ------->
                         
                         
                         
                         </div>
                         
                       <!----------//// Bottom End Here ----------------->
                  
                  </div>
                  </div>
            
              <!--------/// Images End Here ----------------->
       
        
        <p class="first_p"><b>Techgig</b> <a href="#"><span>&lt;</span>jobs@techgig.com&gt;<span></span></a></p>
          <div class="mail_row">
          <p class="last_p">to me </p>
         <a href="#" onClick="mail_to(event);"> <div class="mail_deatil"></div> </a>
         
  
                        <!-------------//// To Me ------------>
                  
                  <div class="to_me" style="display: none;">
                  
                         <ul>
                             
                             <li class="comm_width"><span>from: </span></li><li> Timesjobs Job Alert <span>&lt;</span>jobalert@timesjobs.com <span>&gt;</span></li>
                             <div class="clear"></div>
                             <li class="comm_width"><span>reply-to:</span></li><li> Timesjobs Job Alert &lt;jobalert@timesjobs.com&gt;</li>
                             <div class="clear"></div>
                             <li class="comm_width"><span>to:</span></li><li> hariom15791@gmail.com</li>
                             <div class="clear"></div>
                             <li class="comm_width"><span>date:</span></li><li> Sun, May 4, 2014 at 4:13 AM</li>
                             <div class="clear"></div>
                             <li class="comm_width"><span>subject:</span></li><li>Hariom, Optume IT Solutions Pvt. Ltd. jobs for you</li>
                             <div class="clear"></div>
                             <li class="comm_width"><span>mailed-by:</span></li><li>timesjobs.com</li>
                             <div class="clear"></div>
                             <li class="comm_width"><span>signed-by:</span></li><li>timesjobs.com</li>
                             <div class="clear"></div>
                             <li class="imag_upload">
                             <span>Images from this sender are always displayed.</span><a href="#">Don't display from now on.</a>
                              <div class="clear"></div>
                             </li>
                          
                         </ul>
                  
                  </div>
              
              <!--------------//// To Me End ------------>
                  
         
          </div>
        </div>
        <!------- MAIL HEADER RIGHT ---> 
        <!---- MAIL HEADER LEFT ------>
        <div class="mail_right">
        
        <div style="float:left;margin-top: 7px;">10:21 AM (6 hours ago) </div>
        <div class="bottom_flag"></div>
        <div class="mail_option">
             
                <a title="Reply" href="#"> 
              <div class="back"> <img src="images/back_one.png"></div>
              </a>
              <a title="More" href="#" onClick="option_here_1(event);"> 
              <div class="option"> <img src="images/open_sub_menu.png"></div>
              </a>

        </div> 
        </div>
        <!------- MAIL HEADER RIGHT --->
        <div class="clear"></div>
      </div>
      <!-----/// MAIL END HEADER -----------> 
      <!----- /// MAIL MID CONTENT ----->
      <div class="mail_content_1">
      
      <!---------// Mail Option Are Here ------------>
                     <div class="mail_down_option_1">
            
            <ul>
                  <li><a href="#">Reply</a></li>
                  <div class="clear"></div>
                  <li><a href="#">Reply to all</a></li>
                  <div class="clear"></div>
                  <li><a href="#">Forward</a></li>
                  <div class="clear"></div>
                  <li><a href="#">Open chat</a></li>
                  <div class="clear"></div>
                  <li><a href="#">Filter messages like this</a></li>
                  <div class="clear"></div>
                  <li><a href="#">Print</a></li>
                  <div class="clear"></div>
                  <li><a href="#">Add Techgig to Contacts list</a></li>
                  <div class="clear"></div>
                  <li><a href="#">Delete this message</a></li>
                  <div class="clear"></div>
                  <li><a href="#">Report spam</a></li>
            </ul>
            <div class="clear"></div>
       
       </div>
      <!-----------/// Mail Option End Here -------------->
      
      
      
                  <p>Hi there!</p>
        <p>We hope you enjoy this webmail frontend as we here at Afterlogic, do. </p>
        <p>Please feel free to click, tap and drag-n-drop everything around. :) </p>
        <p>However, please note with this demo you can send emails to this demo email account only. It's just not to allow folks to send spam from here. </p>
        <p>Should you find that you need some help with getting this thing to work on your network, here are your options:</p>
        <p>Documentation, which should be your starting point<br>
          Helpdesk, to get assistance from our swift &amp; friendly Webmail Support Team
          Community Forum, where folks can help each other, discuss things and just hang around.<br>
          Support Team is also there, but Helpdesk is preferred.</p>
        <p>Also don't forget to get in touch with us at Facebook and Twitter.</p>
        <p>For those of you who are Big Guys, and should require dedicated support and the team behind the product, please call us +1-973-784-1100 or just drop us a message here.</p>
        <!---------------/// ATTACHMENT STARTED HERE ----------->
        <div class="main_attachment">
                        <div class="attachment_file attachment_top">
                        <!-------------/// LEFT ATTACHMENT ---------->
                             <div class="left_attachment">
                                       <p><span>2</span> Attachments</p>
                                       <div class="clear"></div>
                             </div>
                        <!------------/// LEFT ATTACHMENT END ------->
                        <!------------/// RIGHT ATTACHMENT ------->
                             <div class="left_attachment"></div>
                        <!----------/// RIGHT ATTACHMENT --------->
                        </div>
                        <!--------/// ATTACHMENT CONTENT ---->
                        <div class="attachment_content">
                               <ul>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />

                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                             <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                                                <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                                      <li>
                                            <div class="attachment_con_box">
                                                <div class="attachment_images">
                                                    <img src="images/photo_1.jpg" />
                                                </div>
                                                <div class="attach_con_bottom">
                                                   <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                
                                                </div>
                                                <div class="attach_mousehover">
                                                    <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                                                        <div class="clear"></div>
                                                        <div class="attachment_size">2 KB</div>
                                                    </div>
                                                    <div class="clear"></div>
                                                    <div class="attachment_option">
                                                         <img src="images/download.png" />
                                                    </div>
                                                
                                                </div>
                                            
                                            </div>
                                      </li>
                                        <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------>
                               </ul>
                        
                        
                        </div>
                        <!----------/// ATTACHMENT CONTENT END ----->
                        
        </div>
        <!---------------/// ATTACHMENT END HERE ------------>
      </div>
      
      <div class="clear"></div>
        <!------------/// Bottom Portion Here(Mail Replay) --------------->
      <div class="bottom_left" style="display: block;">
      
                  
                      
                 
      
      
          
      
      </div>
      <!------------//// Bottom Portion Here ---------------> 

      <div class="your_option_1" onClick="mail_forward_11();" style="margin-top: 64px;">Click here to <a href="#">Reply</a> or <a href="#">Forward</a></div>
           <!-------------//// Replay Details Are Here --------------->
                      <div class="mail_forward_11">
                      
                            <!-------------/// FORWARD TOP ------------->
                            <div class="forward_top">
                                
                                
                                      <div class="forward_mail">
                                         
                                         <a href="#" class="mail_left_for">
                                         <img src="images/back_one.png">
                                         </a>
                                         <a href="#" class="mail_right_for" onClick="down_mail(event);">
                                         <img src="images/open_sub_menu.png">
                                         </a>
                                           
                                           <div class="main_bottom_option">
                                           
                                           
                                           </div>
                                        
                                      </div>
                                      
                                      <div class="forward_mail_left">
                                      
                                            
                                      
                                      </div>
                              
                                 


                            </div>
                           <!-------------/// FORWARD TOP ------------->
                           
                             <!-------------/// FORWARD TOP ------------->
                            <div class="forward_mid">
                            </div>
                            <!-------------/// FORWARD TOP ------------->
                           
                             <!-------------/// FORWARD TOP ------------->
                            <div class="forward_bottom">
                            
                                    <!----------------/// Left Portion --------------->
                                      
                                      <div class="for_bottom_left">
                                      
                                            Send 
                                      
                                      </div>
                                    
                                    <!----------------/// Left Portion End ------------>
                                    
                                     <!----------------/// Right Portion --------------->
                                      
                                      <div class="for_bottom_mid">
                                      
                                                <a href="#" class="font_image">
                                                     
                                                     <img src="images/a_fonts.gif">
                                                    
                                                </a>
                                                
                                                <div class="bor_1"></div>
                                                
                                                <a href="#" class="attach_image">
                                                
                                                       <img src="images/attachment.png">
                                                
                                                     
                                                </a>
                                                
                                                
                                                <a href="#" class="plus">
                                                   
                                                      <img src="images/plus_black.png">
                                                   <div class="plus_option"></div>
                                                </a>
                                                
                                                 
                                                
                                     
                                      
                                      </div>
                                    
                                    <!----------------/// Right Portion End ------------>
                                    
                                    <!----------------/// Right Portion --------------->
                                      
                                      
                                      <div class="for_bottom_right">
                                      
                                      
                                             <a href="#" class="dustbin"><img src="images/tool.png"></a>
                                      
                                        <div class="bor_1"></div>
                                        
                                        
                                            <a href="#" class="bottom_down_1" onClick="bootom_forward(event);"><img src="images/open_sub_menu.png"></a>
                                            
  
                                      </div>
                                        
                                        
                                             
                                    
                                    <!----------------/// Right Portion End ------------>
                                     
                                     
                            
                            
                            
                            
                            </div>
                           <!-------------/// FORWARD TOP ------------->
                             <div class="for_bottom">
                                                
                                                </div>
                
                </div>
                      <!--------------/// Replay Details End Here ---------------->
      
  </div>
      
      <!------/// MAIL MID CONTENT ----> 
         <div class="clear"></div>
      

      <div class="clear"></div>
    </div>
                        
                        
                        
                        </div>
                        
                    <!-------------------/// Right View and Bottom View Tab End Here --------------->    
                        
                    </div>
                    
                    <!--------------------/// Mail Cointer End Here ----------------->
                    <div class="clear"></div>
                </div>
            </div>


<div class="for_setting_1">
                            
                                         <ul id="menu" class="extra_menu">
                                           <li> <a href="#">&nbsp; </a>
                                                 <ul class="for_setting new_submenu">
                                                    <li> <a href="#">Settings</a></li>
                                                    <li><a href="#">Themes</a></li>
                                                    <li> <a href="#">Help</a></li>
                                                </ul>
                                           </li>
                                        </ul>
                            
                            </div>

                      

            <script src="js/event.js" type="text/javascript" language="javascript" ></script> 
            <script src="js/tab_event.js" type="text/javascript" language="javascript" ></script> 
            <script type='text/javascript' src='js/jquery.dcjqaccordion.2.7.min.js'></script> 
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

            <link rel="stylesheet" type="text/css" href="css/jquery.jscrollpane.css" />
            <script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
            <script type="text/javascript">
                                                $(document).ready(function() {
                                                    if (!$.browser.webkit) {
                                                        $('.container').jScrollPane();
                                                    }
                                                });
            </script>
            
    </body>
</html>