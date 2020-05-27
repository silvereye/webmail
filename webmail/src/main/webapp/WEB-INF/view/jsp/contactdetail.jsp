<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="shortcut icon" href="images/favicon.ico"/>
<link type="text/css" rel="stylesheet" href="css/style.css"/>
<link type="text/css" rel="stylesheet" href="css/blue.css"/>
<link type="text/css" rel="stylesheet" href="css/jquery-ui.css"/>

<!------<link rel="stylesheet" href="css/jquery-ui.css">--->
<script src="js/jquery-1.8.3.min.js"></script>
<!--<script src="js/jquery-ui.js"></script>-->
<script src="js/splitter.js"></script>
<script src="js/sytem-script.js"></script>
<script src="js/jquery-ui.js"></script>
<!------/// SCRIPT FOR CALENDER---------->
<script src="js/calender_js.js"></script>
<!---------/// SCRIPT END ----------------->
<!------/// SCRIPT FOR CALENDER---------->
<script src="js/contact_js.js"></script>
<link type="text/css" rel="stylesheet" href="css/contact_css.css" />
<!---------/// SCRIPT END ----------------->

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
          </a> </div>
      </div>
      <div class="new_user" onClick="userinformation(event);" ><img src="images/photo.jpg" class="img" /></div>
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
          <a href="#">
          <div class="search_top">All Mail</div>
          </a>
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
    <div class="user_top"> <a href="#"> <img src="images/photo_1.jpg" />
      <div class="change_images">Change photo</div>
      </a>
      <div class="left_top"> Hariom Srivastava <span>hariom15791@gmail.com</span>
        <div class="clear_2"></div>
        <div class="progress_bar"> <img src="images/Progress_Bar.png" />
          <div class="percentage_value"></div>
          <div class="clear"></div>
        </div>
        <div class="color_pad"><img src="images/color.png" /></div>
      </div>
    </div>
    <!------------/// TOP SECTION END HERE --------------> 
    <!----------/// BOTTOM SECTION STARED HERE ------------>
    <div class="user_bottom"> <a href="#">
      <div class="sing_out"> Sign out </div>
      </a> <a href="#">
      <div class="sing_out right_space"> Profile </div>
      </a> </div>
    
    <!------------/// BOTTOM SECTION END HERE --------------> 
    
  </div>
  <!---------------/// USER INFORMATION BOX END HERE -------------> 
  <!------/// SEARCH BOX STARED HERE ---------> 
  
  <!------/// SEARCH BOX END HERE -----------> 
  
  <!-----/// LEFT PANEL STARED HERE ------->
  <div class="left-pane" id="MySplitter"> 
    
    <!--------------///// WHEN LEFT IS OPEN STARED HERE ----->
    <div class="left_open"> 
      <!------- /// LEFT TOP STARED HERE ------->
      <div class="top_left"> <a href="#">
        <div class="top_right_icon" id="hide_left"> <img src="images/open_state.png" /> </div>
        </a> </div>
      
      <!------- /// LEFT TOP END HERE ------->
      <div class="clear"></div>
      <!-------/// LEFT MID CONTENT STARED HERE --------->
      <div class="left_tab_content">
        <div class="left_three_box calender_js"> 
          <!---------/// Create calender --------> 
          <!--  <div class="create_calender">Create</div>
                           <!---------/// create calender end here ------> 
          <!-----------/// MY CALENDER ----------------->
          <div class="my_con">My Contacts</div>
          <div class="my_calender_con">
            <ul>
              <li class="my_con_active"> <img src="images/blank_man.png" class="icon_con" /> <span>Personal Contact</span>
                <div class="clear"></div>
              </li>
              <li> <img src="images/colt_con.png" class="collect_con col_con" /> <span>Collected Contact</span>
                <div class="clear"></div>
              </li>
              <li> <img src="images/contact_dir.png" class="icon_con col_con" /> <span>Directory</span>
                <div class="clear"></div>
              </li>
            </ul>
            <!------/// CALENDER MORE OPTION -------> 
            
            <!----------/// CALENDER MORE OPTION END HERE ----------> 
            
          </div>
          <!-------------/// MY CALENDER END HERE --------> 
          
          <!-----------/// Other Activies CALENDER ----------------->
          <div class="other_con">Shred Contact</div>
          <div class="other_calender_con">
            <ul>
              <li> <img src="images/blank_man.png" /> <span>Hariom Srivastava</span>
                <div class="clear"></div>
              </li>
              <li> <img src="images/photo.jpg" /> <span>Hariom Srivastava</span>
                <div class="clear"></div>
              </li>
            </ul>
            <!------/// CALENDER MORE OPTION -------> 
            
            <!----------/// CALENDER MORE OPTION END HERE ----------> 
          </div>
          <!-------------/// Other Activies CALENDER END HERE --------> 
          
        </div>
        <div class="chat_box">
          <div class="chat_box_inner">
            <div class="chat_heading">
              <div class="chat_h_left"> <img src="images/photo.jpg" class="h_name" />
                <div class="chat_h_name">Hariom Srivastava</div>
                <div class="h_drop"><img src="images/down_mail.png" /></div>
              </div>
              <div class="chat_h_right"> <img src="images/search_1.jpg" /> </div>
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
            <div class="chat_sign_images"> <img src="images/chat-4-xxl.png" /> </div>
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
              <div class="chat_info_right"> <img src="images/photo_1.jpg" /> </div>
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
              <div class="chat_info_right"> <img src="images/photo_1.jpg" /> </div>
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
                    <div class="chat_down_left"> <img src="images/photo.jpg" /> </div>
                    <div class="chat_down_right"> Hariom Srivastava <span>hari@silvereye.co</span> </div>
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
                      <li>
                        <input type="radio" />
                        <span>Online</span>
                        <div class="online"></div>
                      </li>
                      <li>
                        <input type="radio" />
                        <span>Offline</span>
                        <div class="offline"></div>
                      </li>
                      <li>
                        <input type="radio" />
                        <span>Busy</span>
                        <div class="busy"></div>
                      </li>
                      <li>
                        <input type="radio" />
                        <span>Away</span>
                        <div class="away"></div>
                      </li>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
                        <div class="invite_right"> <a href="#">Unblock</a> </div>
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
    
    <!-----------------------------/// Main Cointer Stared here --------------->
    <div id="widget" > 
      <!-----// CONTACT HEADER STARED ------->
      <div class="for_tool_contact"> 
        
        <!-------/// CALENDER TOP THREE OPTION ----------->
        <div class="top_three_con"> 
          
          <!---/// FIRST CALENDER ------>
          <div class="first_con_option">
            <div class="create_con_icon"></div>
            Create Group </div>
          <!------/// FIRST CALENDER END HERE --------> 
          <!---/// SECOND CALENDER ------>
          <div class="first_con_option_1">
            <div class="create_con_icon_1"></div>
            Create Contact</div>
          <!------/// SECOND CALENDER END HERE --------> 
          <!---/// THIRD CALENDER ------>
          <div class="first_con_option_2">
            <div class="create_con_icon_2"></div>
            E-Mail</div>
          <!------/// THIRD CALENDER END HERE --------> 
          <!---/// SECOND CALENDER ------>
          <div class="first_con_option_4">
            <div class="create_con_icon_4"></div>
            SMS</div>
          <!------/// SECOND CALENDER END HERE --------> 
          <!---/// THIRD CALENDER ------>
          <div class="first_con_option_3">
            <div class="create_con_icon_3"></div>
            More</div>
          <!---/// CONTACT MORE OPTION STARED ------->
          <div class="con_more">
            <ul>
              <li><a href="#">Merge contacts</a></li>
              <li><a href="#">Delete contacts</a></li>
              <li><a href="#">Manage Sharing</a></li>
              <li><a href="#">Import...</a></li>
              <li><a href="#">Export...</a></li>
              <li><a href="#">Print...</a></li>
              <li><a href="#">Find & merge duplicates...</a></li>
              <li><a href="#">Restore contacts...</a></li>
              <li><a href="#">Sort by</a></li>
              <li><a href="#">First Name</a></li>
              <li><a href="#">Last Name</a></li>
            </ul>
            <div class="clear"></div>
          </div>
          <!----------/// CONTACT END HERE ------------> 
          <!------/// THIRD CALENDER END HERE --------> 
          
        </div>
        <!---------/// CALENDER TOP THREE OPTION END HERE --------> 
        <!---// TOOL --->
        <div class="right_tool_part cal_rig"> 
          <!--/// CONTACT VIEW CHANGES OPTION STARED HERE --------->
          <div class="con_viw">
           <ul>
                         <a href="contact_pages.html"/> <li ><div class="con_list"></div>List View</li></a>
                         <a href="contact_pages(details_view).html"/> <li class="list_active"><div class="de_list"></div> Details View</li></a>
              </ul>
            <div class="clear"></div>
          </div>
          <!----/// CONTACT VIEW CHANGES END HERE -----------> 
          <!---------/// CONTACT VIEW CHAGES END ---->
          <div class="cal_next_and_Pre"> <a href="#">
            <div class="right_tool_1"> <img src="images/next_mail.png" class="next_imag"> </div>
            </a> <a href="#">
            <div class="right_tool_1"> <img src="images/privious_mail.png" class="next_imag"> </div>
            </a> </div>
          <div class="right_tool_part for_calender">
            <div class="right_tool"> <a href="#"> <img src="images/reload.png"> </a> </div>
            <div class="right_tool_1">
              <ul id="menu" >
                <li> <img src="images/setting_toll.png" class="four_margin" ></li>
                <li class="right_menu_1" > <img src="images/open_sub_menu.png" style="margin-left: 8px !important;"> 
                  <!-- <ul class="for_setting">
                                            <li> <a href="#">Settings</a></li>
                                            <li><a href="#">Themes</a></li>
                                            <li> <a href="#">Help</a></li>
                                        </ul>--> 
                </li>
              </ul>
            </div>
          </div>
        </div>
        <!---/// TOOL END ----> 
      </div>
      <!----------/// CONTACT HEADER END HERE -------> 
      <!--/// LIST ACCORDING TO A TO 10 STRAED HERE ------->
      <div class="na_li">
        <table>
          <tr>
            <td class="na_li_active">123</td>
            <td>A</td>
            <td>B</td>
            <td>C</td>
            <td>D</td>
            <td>E</td>
            <td>F</td>
            <td>G</td>
            <td>H</td>
            <td>I</td>
            <td>J</td>
            <td>K</td>
            <td>L</td>
            <td>M</td>
            <td>N</td>
            <td>O</td>
            <td>P</td>
            <td>Q</td>
            <td>R</td>
            <td>S</td>
            <td>T</td>
            <td>U</td>
            <td>V</td>
            <td>W</td>
            <td>X</td>
            <td>Y</td>
            <td>Z</td>
          </tr>
        </table>
      </div>
      <table class="con_he_list">
        <tr>
          <td class="left_head"><input type="checkbox"/></td>
          <td class="con_imag_head"><img src="images/white_man.png" /></td>
          <td class="con_name_head">Name</td>
          <td class="con_email">Email</td>
        </tr>
      </table>
      <!--// RIGHT OPTION HERE ------->
      <table class="con_he_list_right">
        <tr>
          <td  colspan="4"><div class="save_right">
              <div class="save_icon"></div>Save</div>
              <div class="cancel_fl_vi"><div class="cancel_icon_fl_vi">X</div>Cancel</div>
              <div class="cancel_right"><div class="edite_icon"></div>Edit</div>
              <div class="add_more"><div class="add_more_icon"></div>Add More</div>
              </td>
        </tr>
      </table>
      <!--------/// RIGHT OPTION END HERE -------> 
      <!-----// LIST ACCORDING TO A TO 10 END HERE --------> 
      <!--------/// SPACE FOR CONTACT PAGES -------------> 
      <!---//TABLE HAEDER STARED --------> 
      
      <!--------/// TABLE HEADER END HERE ------->
      <div class="contact_content_list"> 
        <!---------/// DETAILS VIEW STARED HERE --------> 
        
        <!---// LEFT PART CONTACT SATRED ---->
        <div class="left_con_part">
          <table class="con_he_left_list">
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Sriv</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@g</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
            <tr>
              <td class="con_box_left"><input type="checkbox"/></td>
              <td class="con_imag_left"><img src="images/blank_man.png" /></td>
              <td class="con_name_left"><div class="inner_text_left">Hariom Srivastava urf Hariom Srivastav</div></td>
              <td class="con_email_left"><div class="inner_text_left">hariom15791@gmail.com</div></td>
            </tr>
          </table>
        </div>
        <!---/// LEFT PART END HERE ---------> 
        <!---// RIGHT PART CONTACT SATRED ---->
        <div class="right_con_part"> 
          
          <!------------/// RIGHT CONTENT STARED HERE -------------->
          <table>
            <tr>
              <td colspan="3" class="right_info_heading">Personal Information</td>
            </tr>
            <tr>
              <td class="right_name_first">Full Name </td>
              <td class="right_text"><input type="text" value="Hariom Srivastava" />
                <div class="edite_name">
                  <div class="name_edit"></div>
                  Edit Name</div></td>
              <td rowspan="3"><img src="images/photo_1.jpg"/>
                <div class="save_chnage">Change Image</div></td>
            </tr>
            <tr>
              <td class="right_name_first">Company</td>
              <td><input type="text"  value="Silvereye IT solution Pvt.Ltd"/></td>
            </tr>
            <tr>
              <td class="right_name_first">Job Title</td>
              <td><input type="text" value="Web Designer"/></td>
            </tr>
            <tr class="hide_info">
              <td colspan="3" class="right_info_heading">Internet</td>
            </tr>
            <tr class="hide_info">
              <td class="right_name_first">email</td>
              <td colspan="2"><input type="text" value="hari@silvereye.co" /></td>
            </tr>
            <tr class="hide_info">
              <td class="right_name_first">Display as</td>
              <td colspan="2"><input type="text"  value="Hariom"/></td>
            </tr>
            <tr class="hide_info">
              <td class="right_name_first">Web page address</td>
              <td colspan="2"><input type="text" value="silvereye.co"/></td>
            </tr>
            <tr class="hide_info">
              <td class="right_name_first">IM Address</td>
              <td colspan="2"><input type="text" value="" /></td>
            </tr>
            <tr class="hide_info">
              <td colspan="3" class="right_info_heading">Phone Number</td>
            </tr>
            <tr class="hide_info">
              <td class="right_name_first">Business</td>
              <td colspan="2"><input type="text" value="" /></td>
            </tr>
            <tr class="hide_info">
              <td class="right_name_first">Home</td>
              <td colspan="2"><input type="text" value="Mirzapur" /></td>
            </tr>
            <tr class="hide_info">
              <td class="right_name_first">Business Fax</td>
              <td colspan="2"><input type="text" value="+91-8956890678" /></td>
            </tr>
            <tr class="hide_info">
              <td class="right_name_first">Mobile</td>
              <td colspan="2"><input type="text" value="+91-9067567897" /></td>
            </tr>
            <tr class="hide_info">
              <td colspan="3" class="right_info_heading">Address</td>
            </tr>
            <tr class="hide_info">
              <td class="right_name_first">Business</td>
              <td colspan="2"><input type="text" value="Silvereye IT Solution Pvt.Ltd" /></td>
            </tr>
          </table>
          <!------------/// RIGHT CONTENT END ---------- > 
          
        </div>
        <!---/// RIGHT PART END HERE ---------> 
        <!---------/// DETAILS VIEW END HERE -------------> 
        
      </div>
      
      <!----------/// SPACE FOR CONTECT PAGES END HERE ------>
      
      <div class="mail_content">
        <div class="mail_content_1" style="width: 48.5%; float: right; display: block; min-height: 75%; max-height: 95%;">
          <div class="mail_left_content"> 
            
            <!--------------/// Top Portion Started Here ------------>
            
            <div class="top_bottom_1">
              <h1>All New Job Opportunities for Fresher </h1>
              <div class="mail_right_1"> <a href="#">
                <div class="printer"></div>
                </a> <a href="#">
                <div class="fullscreen"></div>
                </a> </div>
              <div class="clear"></div>
              <div class="clear"></div>
            </div>
            
            <!------------------/// Top Portion End Here ----------------> 
            
            <!------// MAIL HEADER STARED HERE ------->
            <div class="mail_header mail_header_top" style="padding-top: 0px;"> 
              
              <!---- MAIL HEADER LEFT ------>
              <div class="mail_left">
                <div class="images_hover"> <img src="images/mail_icon.png"> 
                  
                  <!---------/// Images Details Stared Here ---------->
                  <div class="images_details">
                    <div class="mail_left_1"> <span>Timesjobs Job Alert</span>
                      <div class="clear"></div>
                      jobalert@timesjobs.com </div>
                    <img src="images/unnamed.png"> 
                    
                    <!-----------//// Bottom Images Details ------->
                    
                    <div class="send_mail_deatils"> <a href="#">Add to contacts</a> <a href="#">Emails</a> 
                      
                      <!-------/// Right Portion ------->
                      <div class="right_forw"> <a href="#">
                        <div class="mail_for"> <img src="images/plus_option.gif"> </div>
                        </a> <a href="#">
                        <div class="mail_for_1"> <img src="images/mail_fow.png"> </div>
                        </a> </div>
                      
                      <!-------/// Right Portion End -------> 
                      
                    </div>
                    
                    <!----------//// Bottom End Here -----------------> 
                    
                  </div>
                </div>
                
                <!--------/// Images End Here ----------------->
                
                <p class="first_p"><b>Techgig</b> <a href="#"><span>&lt;</span>jobs@techgig.com&gt;<span></span></a></p>
                <div class="mail_row">
                  <p class="last_p">to me </p>
                  <a href="#" onClick="mail_to(event);">
                  <div class="mail_deatil"></div>
                  </a> 
                  
                  <!-------------//// To Me ------------>
                  
                  <div class="to_me" style="display: none;">
                    <ul>
                      <li class="comm_width"><span>from: </span></li>
                      <li> Timesjobs Job Alert <span>&lt;</span>jobalert@timesjobs.com <span>&gt;</span></li>
                      <div class="clear"></div>
                      <li class="comm_width"><span>reply-to:</span></li>
                      <li> Timesjobs Job Alert &lt;jobalert@timesjobs.com&gt;</li>
                      <div class="clear"></div>
                      <li class="comm_width"><span>to:</span></li>
                      <li> hariom15791@gmail.com</li>
                      <div class="clear"></div>
                      <li class="comm_width"><span>date:</span></li>
                      <li> Sun, May 4, 2014 at 4:13 AM</li>
                      <div class="clear"></div>
                      <li class="comm_width"><span>subject:</span></li>
                      <li>Hariom, Optume IT Solutions Pvt. Ltd. jobs for you</li>
                      <div class="clear"></div>
                      <li class="comm_width"><span>mailed-by:</span></li>
                      <li>timesjobs.com</li>
                      <div class="clear"></div>
                      <li class="comm_width"><span>signed-by:</span></li>
                      <li>timesjobs.com</li>
                      <div class="clear"></div>
                      <li class="imag_upload"> <span>Images from this sender are always displayed.</span><a href="#">Don't display from now on.</a>
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
                <div class="mail_option"> <a title="Reply" href="#">
                  <div class="back"> <img src="images/back_one.png"></div>
                  </a> <a title="More" href="#" onClick="option_here_1(event);">
                  <div class="option"> <img src="images/open_sub_menu.png"></div>
                  </a> </div>
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
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
                        </div>
                      </div>
                    </li>
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  END HERE ------------> 
                    <!-------------/// MAIN ATTACHMENT ROW CONTENT  STARED HERE ------------>
                    <li>
                      <div class="attachment_con_box">
                        <div class="attachment_images"> <img src="images/photo_1.jpg" /> </div>
                        <div class="attach_con_bottom"> <img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span> </div>
                        <div class="attach_mousehover">
                          <div class="attach_row"><img src="images/icon_1_pdf_x16.png" /> <span>Important Images for Web.pdf</span>
                            <div class="clear"></div>
                            <div class="attachment_size">2 KB</div>
                          </div>
                          <div class="clear"></div>
                          <div class="attachment_option"> <img src="images/download.png" /> </div>
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
            <div class="bottom_left" style="display: block;"> </div>
            <!------------//// Bottom Portion Here --------------->
            
            <div class="your_option_1" onClick="mail_forward_11();" style="margin-top: 64px;">Click here to <a href="#">Reply</a> or <a href="#">Forward</a></div>
            <!-------------//// Replay Details Are Here --------------->
            <div class="mail_forward_11"> 
              
              <!-------------/// FORWARD TOP ------------->
              <div class="forward_top">
                <div class="forward_mail"> <a href="#" class="mail_left_for"> <img src="images/back_one.png"> </a> <a href="#" class="mail_right_for" onClick="down_mail(event);"> <img src="images/open_sub_menu.png"> </a>
                  <div class="main_bottom_option"> </div>
                </div>
                <div class="forward_mail_left"> </div>
              </div>
              <!-------------/// FORWARD TOP -------------> 
              
              <!-------------/// FORWARD TOP ------------->
              <div class="forward_mid"> </div>
              <!-------------/// FORWARD TOP -------------> 
              
              <!-------------/// FORWARD TOP ------------->
              <div class="forward_bottom"> 
                
                <!----------------/// Left Portion --------------->
                
                <div class="for_bottom_left"> Send </div>
                
                <!----------------/// Left Portion End ------------> 
                
                <!----------------/// Right Portion --------------->
                
                <div class="for_bottom_mid"> <a href="#" class="font_image"> <img src="images/a_fonts.gif"> </a>
                  <div class="bor_1"></div>
                  <a href="#" class="attach_image"> <img src="images/attachment.png"> </a> <a href="#" class="plus"> <img src="images/plus_black.png">
                  <div class="plus_option"></div>
                  </a> </div>
                
                <!----------------/// Right Portion End ------------> 
                
                <!----------------/// Right Portion --------------->
                
                <div class="for_bottom_right"> <a href="#" class="dustbin"><img src="images/tool.png"></a>
                  <div class="bor_1"></div>
                  <a href="#" class="bottom_down_1" onClick="bootom_forward(event);"><img src="images/open_sub_menu.png"></a> </div>
                
                <!----------------/// Right Portion End ------------> 
                
              </div>
              <!-------------/// FORWARD TOP ------------->
              <div class="for_bottom"> </div>
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
<!-- <script src="js/event.js" type="text/javascript" language="javascript" ></script>  -->
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

<!---------------------/// JS CALENDER STARED HERE ------------> 

<script>
            $(document).ready(function() {
		
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			defaultDate: '2014-11-12',
			selectable: true,
			selectHelper: true,
			select: function(start, end) {
				
				/// TEST ADD HERE 
				
				if($('.calender_pop').css('display')=='none')
				{
					
					$('.calender_pop').css('display','block');
					 $('.web_dialog_overlay').show();
					
					
			}
				
				$('.save_cal').click(function(){
					
				     var input_value = $('.summary').val();
					//alert(input_value);
					
					});
				
				/// ********************** ///
				///     NOTE  stred here  ///
				/// ******************** ///
				
				
				////  i am not change the value when or append the value in each box beause it affect in development
				  //   if  you want to new  state only remove the comment of //var title = prompt('Event Title:'); only 
				  /// and hide the new test event
				
				
				
				
				/// ********************** ///
				///     NOTE  end         ///
				/// ******************** ///
				
				
				/// TEST ADD END HERE 
				
				
				//var title = prompt('Event Title:');
				//var title = prompt('Event Title:');
				
				
				var eventData;
				if (title) {
					eventData = {
						title: title,
						start: start,
						end: end
					};
					alert(title);
					$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
				}
				$('#calendar').fullCalendar('unselect');
			},
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			events: [
				{
					title: 'All Day Event',
					start: '2014-11-01'
				},
				{
					title: 'Long Event',
					start: '2014-11-07',
					end: '2014-11-10'
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: '2014-11-09T16:00:00'
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: '2014-11-16T16:00:00'
				},
				{
					title: 'Conference',
					start: '2014-11-11',
					end: '2014-11-13'
				},
				{
					title: 'Meeting',
					start: '2014-11-12T10:30:00',
					end: '2014-11-12T12:30:00'
				},
				{
					title: 'Lunch',
					start: '2014-11-12T12:00:00'
				},
				{
					title: 'Meeting',
					start: '2014-11-12T14:30:00'
				},
				{
					title: 'Happy Hour',
					start: '2014-11-12T17:30:00'
				},
				{
					title: 'Dinner',
					start: '2014-11-12T20:00:00'
				},
				{
					title: 'Birthday Party',
					start: '2014-11-13T07:00:00'
				},
				{
					title: 'Click for Google',
					url: 'http://google.com/',
					start: '2014-11-28'
				}
			]
		});
		
	});


</script>
<link rel='stylesheet' href='css/jquery-ui.css' />
<link href='css/fullcalendar.css' rel='stylesheet' />
<link href='css/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='js/moment.min.js'></script> 
<!--<script src='../lib/jquery.min.js'></script>---> 
<script src='js/fullcalendar.min.js'></script> 
<!-----------/// COLOR PICKER STRED HERE ----------->
<link rel="stylesheet" type="text/css" href="color_picker/spectrum.css">
<script type="text/javascript" src="color_picker/spectrum.js"></script> 
<script type='text/javascript' src='color_picker/docs.js'></script> 
<!-------------/// COLOR PICKER END HERE --------------->
<style>
	#calendar {
		/*max-width: 900px;*/
		margin: 0 auto;
	}
</style>
<!--------------------/// JS CALENDER END HERE ------------------> 

<!------------------/// NEW SPILITTER STARED HERE --------------> 
<script>
       jQuery(function($) {
    $(window).on('resize', function() {
        var height = $(window).height()-135;
		//alert(height);
        console.log(height);
        $('#foo').height(height).split({ orientation:'horizontal', limit:50 });
		$('#foo').css('height',height)
        $('#b').height(height / 2)-30;
		//$('.chat_inner_content').height(height / 2);
		
		
    }).trigger('resize');
});

  
    </script> 
<!-------------------//// NEW SPILITTER END HERE ---------------> 
<!-------------/// EDITE NAME ------------>
<!--------// CREATE GROUP POP STRED HERE ----------->
<div class="group_name">
  <h1>Create Group<div class="cancel_top cancel_grop_top">X</div></h1>
  <table class="Sj">
    <tbody>
      <tr>
        <td class="Sl">Group Name</td>
        <td><input class="nr Sk" type="text" value=""></td>
      </tr>
      <tr>
          <td colspan="4">
                   <div class="save_right_name"> <div class="save_icon_name "></div> Save</div>
                   <div class="cancel_right_name cancel_grop"><div class="edite_icon_name"></div>Cancel</div>
          </td>
      </tr>
    </tbody>
  </table>
</div>
<!----------/// CREATE GROUP END HERE ---------------->
<div class="edite_name_box">
  <h1>Edit name<div class="cancel_top">X</div></h1>
  <table class="Sj">
    <tbody>
      <tr>
        <td class="Sl">Prefix:</td>
        <td><input class="nr Sk" type="text" value=""></td>
      </tr>
      <tr>
        <td class="Sl">First:</td>
        <td><input class="nr Sk" type="text" value="Hari"></td>
      </tr>
      <tr>
        <td class="Sl">Middle:</td>
        <td><input class="nr Sk" type="text" value="Om"></td>
      </tr>
      <tr>
        <td class="Sl">Last:</td>
        <td><input class="nr Sk" type="text" value="Srivastava"></td>
      </tr>
      <tr>
        <td class="Sl">Suffix:</td>
        <td><input class="nr Sk" type="text" value=""></td>
      </tr>
      <tr>
          <td colspan="4">
                   <div class="save_right_name"> <div class="save_icon_name"></div> Save</div>
                   <div class="cancel_right_name"><div class="edite_icon_name"></div>Cancel</div>
          </td>
      </tr>
    </tbody>
  </table>
</div>

<!----/// CREATE POP HERE ----->
<div class="create_contact">
   <h1>Create Contact
     <div class="cancel_top cancel_grop_top create_top">X</div>
   </h1>
   <!----// SAVE AND CANCEl --------->
   <table class="con_he_list_right_pop">
          <tbody>
              <tr>
                <td colspan="4"><div class="save_right_pop">
                    <div class="save_icon_pop"></div>
                    Save</div>
                    </td>
              </tr>
        </tbody>
    </table>
   <!------/// SAVE END HERE ---------->
   <div class="right_con_part_pop"> 
          
          <!------------/// RIGHT CONTENT STARED HERE -------------->
          <table>
            <tr>
              <td colspan="3" class="right_info_heading_pop">Personal Information</td>
            </tr>
            <tr>
              <td class="right_name_first_pop">Full Name </td>
              <td class="right_text_pop"><input type="text" value="Hariom Srivastava" />
                <div class="edite_name">
                  <div class="name_edit"></div>
                  Edit Name</div></td>
              <td rowspan="3"><img src="images/photo_1.jpg"/>
                <div class="save_chnage_pop">Change Image</div></td>
            </tr>
            <tr>
              <td class="right_name_first_pop">Company</td>
              <td><input type="text"  value="Silvereye IT solution Pvt.Ltd"/></td>
            </tr>
            <tr>
              <td class="right_name_first_pop">Job Title</td>
              <td><input type="text" value="Web Designer"/></td>
            </tr>
            <tr class="right_name_first_pop">
              <td colspan="3" class="right_info_heading">Internet</td>
            </tr>
            <tr class="right_name_first_pop">
              <td class="right_name_first">email</td>
              <td colspan="2"><input type="text" value="hari@silvereye.co" /></td>
            </tr>
            <tr class="right_name_first_pop">
              <td class="right_name_first">Display as</td>
              <td colspan="2"><input type="text"  value="Hariom"/></td>
            </tr>
            <tr class="right_name_first_pop">
              <td class="right_name_first">Web page address</td>
              <td colspan="2"><input type="text" value="silvereye.co"/></td>
            </tr>
            <tr class="right_name_first_pop">
              <td class="right_name_first">IM Address</td>
              <td colspan="2"><input type="text" value="" /></td>
            </tr>
            <tr class="right_name_first_pop">
              <td colspan="3" class="right_info_heading">Phone Number</td>
            </tr>
            <tr class="right_name_first_pop">
              <td class="right_name_first">Business</td>
              <td colspan="2"><input type="text" value="" /></td>
            </tr>
            <tr class="right_name_first_pop">
              <td class="right_name_first">Home</td>
              <td colspan="2"><input type="text" value="Mirzapur" /></td>
            </tr>
            <tr class="right_name_first_pop">
              <td class="right_name_first">Business Fax</td>
              <td colspan="2"><input type="text" value="+91-8956890678" /></td>
            </tr>
            <tr class="right_name_first_pop">
              <td class="right_name_first">Mobile</td>
              <td colspan="2"><input type="text" value="+91-9067567897" /></td>
            </tr>
            <tr class="right_name_first_pop">
              <td colspan="3" class="right_info_heading">Address</td>
            </tr>
            <tr class="right_name_first_pop">
              <td class="right_name_first">Business</td>
              <td colspan="2"><input type="text" value="Silvereye IT Solution Pvt.Ltd" /></td>
            </tr>
          </table>
          <!------------/// RIGHT CONTENT END ----------> 
          
        </div>
        <!---/// RIGHT PART END HERE ---------> 
        <!---------/// DETAILS VIEW END HERE -------------> 
        
      </div>

<!-------/// CREATE POP END HERE ------->
  <div class="web_dialog_overlay" ></div>
<!------------/// EDITE STARED HERE --------->

</body>
</html>