<html>
    <head>
    <script type="text/javascript">
	function getDirectoryUserList(){
	// alert("meeeeeeeeeeeeeeeeeee");
	//var fdrname=document.getElementById('hid_active_contact_fldr').value;
	try{
		 $('.cancel_right').hide();
		 $('.save_right').hide();
		 $('.cancel_fl_vi').hide();
		 $('.add_more').hide();
		 $( "#div_contact_disp_val" ).html( "" );
		 $('.na_li_active').removeClass('na_li_active');
		 $('#ctv_td').addClass('na_li_active');
	}
	catch(err)
	{
		
	}
	document.getElementById('action_gif').style.display= 'block';
	 $.ajax({
         type: "GET",
         url: "${pageContext.request.contextPath}/getDirectoryUserList",
        // data: {'path':folderPath},
         contentType: "application/json",
         success: function (data) {
            // $("#fileSystem").html(data);
            //alert(data);
            
            $("#contact_cnt_div_full").html(data);
            document.getElementById('action_gif').style.display= 'none';
           // document.getElementById(fdrname).className = "my_con_active";
         }
     });
	
}

function getSelContactUserList(con_name) {
	//alert(con_name);
	document.getElementById('action_gif').style.display= 'block';
	window.countChecked=0;
	try{
		 $('.cancel_right').hide();
		 $('.save_right').hide();
		 $('.cancel_fl_vi').hide();
		 $('.add_more').hide();
		 $( "#div_contact_disp_val" ).html( "" );
		 $('.na_li_active').removeClass('na_li_active');
		 $('#ctv_td').addClass('na_li_active');
		 $('#pn').val("1");
	}
	catch(err)
	{
		
	}
	var pfldr=document.getElementById('hid_active_contact_fldr').value
	document.getElementById('hid_active_contact_fldr').value=con_name;
	var pn = $('#pn').val();
	$.ajax({
         type: "GET",
         url: "${pageContext.request.contextPath}/getSelContactUserList",
         data: {'cname':con_name,'pn':pn},
         contentType: "application/json",
         success: function (data) 
         {
            // $("#fileSystem").html(data);
            //alert(data);
            
       
            
            document.getElementById('action_gif').style.display= 'none';
            $('.my_con_active').css('border-left', "3px solid #fff") 
//             $("#contact_cnt_div_full").html(data);
            var obj = jQuery.parseJSON(data);
			$("#contact_cnt_div_full").html(obj.contacts);
			$('#start_end').html(obj.start+"-"+obj.end);
			$('#total').html(obj.total);
			$('.cal_next_and_Pre').show();
			
            document.getElementById(con_name).className = "my_con_active";
           if(con_name!=pfldr)
       	   {
	            try
	            {
	            document.getElementById(pfldr).className = "";
	            }
	        	catch(err)
	        	{
	        		
	        	}
       	   }
       		$(".my_con_active").css("border-left","3px solid "+$("#hid_mail_bgColor").val());
          
           // document.getElementById(fdrname).className = "icon_con";
       /* if(con_name.startsWith("/sharedContacts/"))
		{
		$(".first_con_option_3").hide();
		}
       else
    	   { */
    	   $(".first_con_option_3").show();
    	 //  }
       $('.delete_new_row').hide();
         }
     });
}
</script>
    </head>
    <body>
             <div class="left-pane" > 
    
    <!--------------///// WHEN LEFT IS OPEN STARED HERE ----->
    <div class="left_open"> 
      <!------- /// LEFT TOP STARED HERE ------->
       <!--  <div class="top_left"> 
        <a href="#">
        <div class="top_right_icon" id="hide_left"> <img src="images/open_state.png" /> </div>
        </a> 
        </div> -->
      
      <!------- /// LEFT TOP END HERE ------->
      <div class="clear"></div>
                        <!-------/// LEFT MID CONTENT STARED HERE --------->
       <div class="left_tab_content"  >
        <div class="left_three_box calender_js" > 
          <!---------/// Create calender --------> 
          <!--  <div class="create_calender">Create</div>
                           <!---------/// create calender end here ------> 
          <!-----------/// MY CALENDER ----------------->
          <div class="my_con">My Contacts </div>
          <div id="contact_folder_div" class="my_calender_con">
              
              
              
              
              
              
              
             <!------/// CALENDER MORE OPTION ------->
             
                <!----------/// CALENDER MORE OPTION END HERE ---------->
            
          </div>
          <!-------------/// MY CALENDER END HERE --------> 
          
          <!-----------/// Other Activies CALENDER ----------------->
          </div>
          <div class="left_three_box calender_js" >
          <div  class="other_con other_bottom_con">Shared Contacts <img src="images/setting_toll.png" class="managecalsubs" title="Manage Subscription" style="float: right;   width: 17px;    opacity: 0.9; cursor: pointer;"></div>
            <div id="shared_contact_folder_div"  class="other_calender_con">
               
             <!--     <ul>
              <li>
                <img src="images/blank_man.png" />
                <span>Hariom Srivastava</span>
                <div class="clear"></div>
              </li>
              <li>
                <img src="images/photo.jpg" />
                <span>Hariom Srivastava</span>
                <div class="clear"></div>
              </li>
            </ul> -->
                   <!------/// CALENDER MORE OPTION ------->
               
                <!----------/// CALENDER MORE OPTION END HERE ---------->
          </div>
          <!-------------/// Other Activies CALENDER END HERE --------> 
                           
                           
               </div>
                 </div></div></div>           
                       
                </body>
                
</html>