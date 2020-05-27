<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.security.Principal"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
    <head>
<link rel="shortcut icon" href="images/favicon.ico"/>


<script src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/jquery.noty.packaged.js"></script>
<script src='js/moment.min.js'></script> 
<script src='js/fullcalendar.min.js'></script>
<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
<script src="js/tab_event.js" type="text/javascript" language="javascript" ></script> 
<script src="js/event.js" type="text/javascript" language="javascript" ></script>
<script src="js/splitter.js"></script>
<script src="js/sytem-script.js"></script>
<script src="js/jquery-ui.js"></script> 
<script src="js/more_product.js"></script>  
<script src="js/contact_js.js"></script>
<script src="js/calender_js.js"></script>

<script type='text/javascript' src='js/jquery.dcjqaccordion.2.7.min.js'></script>
<script src="js/jquery.splitter-0.14.0.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/jquery.ui.chatbox.js"></script>

<link type="text/css" rel="stylesheet" href="css/style.css"/>
<link type="text/css" rel="stylesheet" href="css/blue.css"/>
<link type="text/css" rel="stylesheet" href="css/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="css/main_css.css"/>
<!-- <link rel='stylesheet' href='css/jquery-ui.min.css' /> -->
<link href='css/fullcalendar.css' rel='stylesheet' />
<link href='css/fullcalendar.print.css' rel='stylesheet' media='print' />
<link rel="stylesheet" type="text/css" href="css/jquery.jscrollpane.css" />
<link href='css/more_product.css' rel='stylesheet' type='text/css' />
<link type="text/css" rel="stylesheet" href="css/contact_css.css" />
<link rel="stylesheet" type="text/css" href="css/buttons.css"/>
<link href='css/jquery.splitter-bottom.css' rel='stylesheet'	type='text/css' />
<link href="css/chat.css" rel="stylesheet" type="text/css" />
<link href="css/cal_setting.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/jquery.ui.chatbox.css" rel="stylesheet" />
<link type="text/css" href="css/jquery-ui_chat.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" />
<script type="text/javascript">
/* function changeHBG(nm) {
	//alert(nm);
	$(".header").css("background-image","none");
	$(".header").css("background-color",nm);
	$(".search_button").css("background-image","none");
	$(".search_button").css("background-color",nm);
	$(".ui-widget-header").css("background-image","none");
	$(".ui-widget-header").css("background-color",nm);
	$(".composed_box_new").css("background-image","none");
	$(".composed_box_new").css("background-color",nm);


} */
</script>


 
<script type="text/javascript">


                                                $(document).ready(function($) {
                                                	
                                                	setTimeout(function(){ showcalNoti(); }, 12000);
                                                	
                                                	$("#share_input").autocomplete("${pageContext.request.contextPath}/autocomp");
                                                	
                                                	
                                                	
                                                    $('#accordion-3').dcAccordion({
                                                        eventType: 'click',
                                                        autoClose: false,
                                                        saveState: false,
                                                        disableLink: false,
                                                        showCount: false,
                                                        speed: 'slow'
                                                    });
                                                });
                                                
                                                function callDismiss(alluid, calnm)
                                                {
                                                	var arr = alluid.split("$nps$");
                                                	var divid=arr[0];
                                                	var uid=arr[1];
                                                	document.getElementById('action_gif').style.display= 'block';
                                                	$.ajax({
                                                        type: "GET",
                                                        url: "${pageContext.request.contextPath}/remEventReminder",
                                                        data: {'uid': uid,'calnm': calnm},
                                                /*       contentType: "application/json",  */
                                                        success: function (data) {
                                                        	document.getElementById('action_gif').style.display= 'none';
                                                        	if(data=="true")
                                                        		{
                                                        		var cnt=parseInt($(".bell_right").text());
                                                        		cnt=cnt-1;
                                                        		if(cnt>0)
                                                        		{
                                                        		$(".bell_right").text(cnt);
                                                        		$(".bell_right").show();
                                                        		}
                                                        	else
                                                        		{
                                                        		$(".bell_right").hide();
                                                        		}
                                                        		$("#"+divid).hide();
                                                        		
                                                        		}
                                                        	
                                                        	
                                                        	//$(".noti_inner").html(obj.res);
                                                        	//var cnt=parseInt(obj.count);
                                                        	
                                                           
                                                        }
                                                    });
                                                }
                                                function showcalNoti()
                                                {

                                                	//$(".noti_inner").html("loading...");
                                                	$.ajax({
                                                        type: "GET",
                                                        url: "${pageContext.request.contextPath}/showcalNoti",
                                                       /*  data: {'uid': uid,'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt ,"texttype": texttype}, */
                                                      contentType: "application/json", 
                                                        success: function (data) {
                                                        	var obj = jQuery.parseJSON(data);
                                                        	$(".noti_inner").html(obj.res);
                                                        	var cnt=parseInt(obj.count);
                                                        	if(cnt>0)
                                                        		{
                                                        		$(".bell_right").text(cnt);
                                                        		$(".bell_right").show();
                                                        		}
                                                        	else
                                                        		{
                                                        		$(".bell_right").hide();
                                                        		}
                                                           
                                                        }
                                                    });
                                                }
                                                function showUqouta() {
                                                	$.ajax({
                                                        type: "GET",
                                                        url: "${pageContext.request.contextPath}/showUserQuota",
                                                     
                                                        contentType: "application/json",
                                                        success: function (data) {
                                                      
                                                        $("#uquota").html(data);
                                                        	
                                                        }
                                                    });
                                                }
            </script>

<script type="text/javascript">
                                                $(document).ready(function() {
                                                    if (!$.browser.webkit) {
                                                        $('.container').jScrollPane();
                                                    }
                                                });
            </script>
            
            <!-- noty -->

<script>
$(document).ready(function(){	
 	
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
     
 	
 	
 	
 	/* 
     function npcall() {
     	var success = generate('success');
     	$.noty.setText(success.options.id, 'I\'m closing now!'); // same as alert.setText('Text Override')
     	setTimeout(function () {
     	            $.noty.close(success.options.id);
     	        }, 3000);   
     }
     
     $( "a[name*='userName']" ).click(function(){
 		npcall();
 	}); */

 
     $(document.body).on('click','.delete_new_row',function(){
 		
    	 var pfldr=document.getElementById('hid_active_contact_fldr').value;
    	
    	 $('.web_dialog_overlay').show();
     	 var confirm = generate_conf('confirm',"hid_contact_del_confirm");
     	$.noty.setText(confirm.options.id, "Do you want to Delete Selected Contacts?");
 		
	});
 
     $(document.body).on('click','.import',function(){
    	 $('.import_con').hide();
    	 $('.web_dialog_overlay').hide();
    	/* var success = generate('alert');
      	$.noty.setText(success.options.id, 'not implemented for Demo.'); // same as alert.setText('Text Override')
      	setTimeout(function () {    $.noty.close(success.options.id);  }, 5000);    */
      	var gpnm=$("#import_combo").find('option:selected').val();
    	 var oMyForm = new FormData();
   	  
    	 var upl_con = document.getElementById("upl_con")   ;
    		
    			 var nm= "";
    			 if(upl_con.files.length>=1)
    				 {
    			     nm= upl_con.files[0].name;
    		  		oMyForm.append("file0", upl_con.files[0]);
    				 }
    		  		
    		  	
    		  		
    		  	var arr = nm.split(".");
    		  
    		  	 
    		  if((arr[arr.length-1]!="vcf" &&  arr[arr.length-1]!="VCF"))
    			  {
    			  var msg="File type must be vcf only.";
    			  var success = generate('alert');
    		      	$.noty.setText(success.options.id, msg); // same as alert.setText('Text Override')
    		      	setTimeout(function () {    $.noty.close(success.options.id);  }, 4000); 
    			  }
    		    else
    			  {
    		  		
    			  document.getElementById('action_gif').style.display= 'block';
    		  $.ajax({
    		    url: '${pageContext.request.contextPath}/importContacts',
    		    data: oMyForm,
    		    dataType: 'text',
    		    processData: false,
    		    contentType: false,
    		    type: 'POST',
    		    success: function(data){
    		    //	alert(data)
    		   
    		    	document.getElementById('action_gif').style.display= 'none';
    		    if(data!="true")
    		    	{
    		    	 var success = generate('alert');
     		      	$.noty.setText(success.options.id, data); // same as alert.setText('Text Override')
     		      	setTimeout(function () {    $.noty.close(success.options.id);  }, 4000); 
    		    	}
    		    else
    		    	{
    		    	getSelContactUserList($(".my_con_active").attr('id'))
    		    	}
    		    }
    		  }); 
    			  }
    		  
     });
     
     
 	/// IMPORT FILE STRED HERE 
		
	 $(document.body).on('click','.import_click',function(){
					var id=$(".my_con_active").attr('id');
					if(id=="Directory" || id=="/Contacts/Collected Contacts")
					{
						var msg="Please select another contact except Directory and Collected Contacts";
						 var success = generate('alert');
		     		      	$.noty.setText(success.options.id, msg); // same as alert.setText('Text Override')
		     		      	setTimeout(function () {    $.noty.close(success.options.id);  }, 4000); 
					}
					else if($('.import_con').css('display')=='none')
					 {
						  $('.import_con').show();
						  $('.web_dialog_overlay').show();
						  $('.con_more').hide();
						  $('div.more_active').removeClass('more_active');
					  }
			
			});
     
     
     $(document.body).on('click','.mange_sharing',function(){
			
    	 var delete_name =  $('.my_con_active').children('span').text();
    	    if(delete_name=="" || delete_name==null )
    		  {
    			
    			  var alert = generate('alert');
    			  $.noty.setText(alert.options.id, "Please select at least one contact.");
    			  setTimeout(function () {  $.noty.close(alert.options.id); }, 5000);
    		  }
    	    else
    	    	{
		 //   alert("gg");
			 $('.con_more').hide();
			 $('.calender_option').hide();
			 $('div.more_active').removeClass('more_active');
			 var folderPath=$("#hid_active_contact_fldr").val();
		

			 var cond1=folderPath.indexOf("/contacts/", folderPath.length - "/contacts/".length) !== -1;
			 var cond2=folderPath.indexOf("/contacts", folderPath.length - "/contacts".length) !== -1;
			 var cond3=folderPath.indexOf("/collected/", folderPath.length - "/collected/".length) !== -1;
			 var cond4=folderPath.indexOf("/collected", folderPath.length - "/collected".length) !== -1;
			 if(folderPath=='Directory' || folderPath=='directory')
	    	 {
	    	 showMsg("alert","Directory could not be shared.");
	    	 }
			 else if(cond1 || cond2)
	    	 {
		    	 showMsg("alert","Personal Contacts could not be shared.");
		    	 }
				 else if(cond3 || cond4)
		    	 {
			    	 showMsg("alert","Collected Contacts could not be shared.");
			    	 }
				 else
				 {
			 $("#spopuphead").html("("+delete_name+")");
				//alert(folderPath)
				 var requestPage="sharingPopupCon";
					jQuery.get(requestPage,
				                            {
				                    'path' : folderPath
				            },
				                    function( data ) {
				            	$( "#srd_cnt" ).html( data );
				            //	$(".send_share, .cancel_share").css("background-color",$("#hid_mail_bgColor").val());
						 $('.sharing_mange').show();
						 $('.web_dialog_overlay').show();
						 
				             
				            });
				 
				
	
				 }
    	    	}
		});	
     
     
     $(document.body).on('click','.export',function(){
    	 
    	 $('.export_con').hide();
    	 $('.web_dialog_overlay').hide();
    	 
    	var fldr= $("#export_combo").find('option:selected').val();
    	 
    	window.open( '${pageContext.request.contextPath}/exportVCFDownload?fldr='+fldr, '_blank' );
    	/*  var selectedVal="";
    	 var selected = $("input[type='radio'][name='source']:checked");
    	 if (selected.length > 0) {
    	     selectedVal = selected.val();
    	 }
    	 var fldr="";
    	 var con_arr = new Array();
    	 var i=0;
    	 if(selectedVal=="contact")
    		 {
    		
    		
    		 $('input[name="chk_con"]:checked').each(function() {
    			//alert( $(this).val());
    			con_arr[i]=$(this).val();
    			i++;
       		});
    		 }
    	 
    	 else if(selectedVal=="group")
    		 {
    		 fldr= $("#export_combo").find('option:selected').val();
    		 }
    	 if(i==0 && selectedVal=="contact")
    		 {
    		 
    		 }
    	 else
    		 {
    		 
    		  
    	    document.getElementById('action_gif').style.display= 'block';
    		var requestPage="${pageContext.request.contextPath}/exportVCFFile";
    		jQuery.post(requestPage,
    	                            {
    	                 'group_name': fldr, 'export_type': selectedVal, 'con_arr' : JSON.stringify(con_arr)
    	            }, 
    	                    function( data ) {
    	            	
    	            	document.getElementById('action_gif').style.display= 'none';
    	          
    	            }); 
    		 }
    	  */
    	 
     	
      });
 
$(document.body).on('click','.delete_group',function(){
	 

    var delete_name =  $('.my_con_active').children('span').text();
  
    if(delete_name=="" || delete_name==null )
	  {
		
		  var alert = generate('alert');
		  $.noty.setText(alert.options.id, "Please select at least one contact.");
		  setTimeout(function () {  $.noty.close(alert.options.id); }, 5000);
	  }
    else  if(delete_name=="Personal Contacts" || delete_name=="Collected Contacts"  || delete_name=="Directory")
  	  {
  		
		  var alert = generate('alert');
		  $.noty.setText(alert.options.id, "Selected folder can not be deleted.");
		  setTimeout(function () {  $.noty.close(alert.options.id); }, 5000);
  	  }
    else
  	  {
    	
     	 $('.web_dialog_overlay').show();
     	 var confirm = generate_conf('confirm',"hid_contact_confirm");
     	$.noty.setText(confirm.options.id, "Do you want to Delete "+delete_name+" Group?");
  	  }
    
});







function generate_conf(type,id) {
   	
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
            $('.web_dialog_overlay').hide();
            $('#'+id).val("true").trigger('change');
            
          
          
           // alert(foldernm);
           
          }
          },
          {addClass: 'btn btn-danger', text: 'No', onClick: function ($noty) {
              $noty.close();
              $('#'+id).val("");
              $('.web_dialog_overlay').hide();
           /*    var n1 = noty({
                  text        : 'You clicked "Cancel" button',
                  type        : 'success',
                  dismissQueue: false,
                  layout      : 'topCenter',
                  theme       : 'defaultTheme'
              });
             setTimeout(function () { $.noty.close(n1.options.id); }, 2000); */
          }
          }
      ]
  });
 
      
      //console.log(type + ' - ' + n.options.id);
      return n; 
       
  }
  
$('#hid_contact_confirm').change( function() {  
	  
	var r =$('#hid_contact_confirm').val();
	
	if (r == "true") {
		// var delete_name =  $('.my_con_active').children('span').text();
		
     	var conid=$('.my_con_active').attr("id");
     	
     	
     	document.getElementById('action_gif').style.display= 'block';
    	//alert(conid);
    	
    	
    	$.ajax({
    		type : "GET",
    		url : "${pageContext.request.contextPath}/delConFldr",
    		data : {
    			'conid' : conid
    			
    		},
    		contentType : "application/json",
    		success : function(data) {
    			if(data!="")
    				{
    				$('.my_con_active').remove();
    	    		 $("#hid_active_contact_fldr").val(data);
    	    		 $("#contact_cnt_div_full").html("");
    	    	 	$("#div_contact_disp_val").html("");
    	    	 	$("#gp_select option[value='"+conid+"']").remove();
    				}
    	    	 document.getElementById('action_gif').style.display= 'none';
    	    	
    		}
    	});
}
});



$('#hid_contact_del_confirm').change( function() {  
	  
	var r =$('#hid_contact_del_confirm').val();
	
	if (r == "true") {
		
	
		var con_file="";
		$('.active_row_delete').each(function()
			{
			   var row_delete=$(this).attr('id');
			   if(con_file=="")
				   {
				   con_file=row_delete;
				   }
			   else
				   {
				   con_file=con_file+","+row_delete;
				   }
			});
		//alert(del_con)
	 document.getElementById('action_gif').style.display= 'block';
			$.ajax({
	    		type : "GET",
	    		url : "${pageContext.request.contextPath}/deleteSelContacts",
	    		data : {
	    			  'con_file' : con_file
	    			   },
	    		contentType : "application/json",
	    		success : function(data) {
	    			if("true")
	    				{
	    				showMsg('alert',"Deleted successfully.") ;
	    				}
	    			else
	    				{
	    				showMsg('alert',"Unable to deleted contact.") ;
	    				}
	               $('.active_row_delete').hide();
	      		   $('.delete_new_row').hide();
	      		   $('#div_contact_disp_val').html("");
	      		   window.countChecked = 0;
	      		   $('.contact_check_all').removeProp('checked');
	      		   getWebmailContactRefresh();
	      		   document.getElementById('action_gif').style.display= 'none';
	    			
	    		}
	    	});
			
			
		  
		  
}
});




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
				//	alert(title);
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

<style>
	#calendar {
		/*max-width: 900px;*/
		margin: 0 auto;
	}
</style>
<!--------------------/// JS CALENDER END HERE ------------------> 

<!------------------/// NEW SPILITTER STARED HERE --------------> 
<!--<script>
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
-->



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
/* #foo {
	background-color: #E92727;
} */
#x {
	background-color: #EFBD73;
}
#y {
	background-color: #EF3e32;
}
/* #b {
	background-color: #73A4EF;
} */
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
/* #widget {
width: 100% !important;
height: 100% !important;
/* overflow: auto; */
} */
</style>
        
 
<script type="text/javascript">

function getHeadLogo(imgid) {
	try
	{
	var pic = document.getElementById(imgid);
	pic.src = "images/logo.png";
	}
	catch (e) {
		// TODO: handle exception
	}
}


	function getAltImage(imgid) {
		try
		{
		var pic = document.getElementById(imgid);
		pic.src = "images/blank_man.jpg";
		}
		catch (e) {
			// TODO: handle exception
		}
	}
	
	 function getAltChatImage(imgid) {
		 try
		 {
			var pic = document.getElementById(imgid);
			pic.src = "chat/photo.jpg";
	 }
		catch (e) {
			// TODO: handle exception
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


</script>


<script type="text/javascript">
function changeHBG(nm) {

//	document.cookie="mycolor="+nm+";";
		document.getElementById('action_gif').style.display= 'block';
	
	$.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/changeBGColor",
       data: {'bgColor':nm},
        contentType: "application/json",
        success: function (data) {
        // alert(data);
        $("#hid_mail_bgColor").val(nm);
        document.getElementById('action_gif').style.display= 'none';
        	$(".header").css("background-image","none");
        	$(".header").css("background-color",nm);
        	$(".search_button").css("background-image","none");
        	$(".search_button").css("background-color",nm);
        	$(".send_share,.cancel_share").css("background-color",nm);
        	$(".cancel_right_name, .save_right_name").css("background-color",nm);
        	$(".edite_address_edit_peole").css("background-color",nm);
        	$(".edite_name,.edite_address_edit").css("background-color",nm);
        	
        	$(".my_con_active").css("border-left","3px solid "+nm);
        	$(".chat_out").css("background-color",nm);
			$(".chat_sign_1_box").css("background-color",nm);
        	$('.my_calender_con ul li').hover(
        		 function(){ $(this).css('border-left', "3px solid "+$("#hid_mail_bgColor").val()) },
        		 function(){ 
        			$(this).css('border-left', "3px solid #fff") 
        			$(".my_con_active").css("border-left","3px solid "+$("#hid_mail_bgColor").val())	 
        		 }
        	);
        }
    });  
	
}
</script>

<script type="text/javascript">
function getWebmailContactfldr(){
	//alert("meeeeeeeeeeeeeeeeeee");
	var nm= $("#hid_mail_bgColor").val();
	$("div.meter span").css("background-color",nm);
      $(".header").css("background-image","none");
        	$(".header").css("background-color",nm);
        	$(".search_button").css("background-image","none");
        	$(".search_button").css("background-color",nm);
        	$(".send_share,.cancel_share").css("background-color",nm);
        	$(".cancel_right_name, .save_right_name").css("background-color",nm);
        	$(".edite_address_edit_peole").css("background-color",nm);
        	$(".edite_name,.edite_address_edit").css("background-color",nm);
        	
        	$(".chat_out").css("background-color",nm);
			$(".chat_sign_1_box").css("background-color",nm);
	var fdrname=document.getElementById('hid_active_contact_fldr').value;
	/* if(fdrname.startsWith("/sharedContacts/"))
		{
		$(".first_con_option_3").hide();
		}
	else
	   {
	   $(".first_con_option_3").show();
	   } */
	 $.ajax({
         type: "GET",
         url: "${pageContext.request.contextPath}/getWbmailContactFolder",
        // data: {'path':folderPath},
         contentType: "application/json",
         success: function (data) {
        	 var arrdata=data.split("<$nirbhay$>");
            // $("#fileSystem").html(data);
           // alert(data);
           
           
         
            $("#contact_folder_div").html(arrdata[0]);
            $("#shared_contact_folder_div").html(arrdata[1]);
            $('li.my_con_active').css('border-left', "3px solid #fff") 
            $('li.my_con_active').removeClass('my_con_active');
            document.getElementById(fdrname).className = "my_con_active";
            setGroupSelVal();
            $(".my_con_active").css("border-left","3px solid "+nm);
        	$('.my_calender_con ul li').hover(
        		 function(){ $(this).css('border-left', "3px solid "+$("#hid_mail_bgColor").val()) },
        		 function(){ $(this).css('border-left', "3px solid #fff") 
        			$(".my_con_active").css("border-left","3px solid "+$("#hid_mail_bgColor").val())	 
        		 }
        	);
        	$('.other_calender_con ul li').hover(
           		 function(){ $(this).css('border-left', "3px solid "+$("#hid_mail_bgColor").val()) },
           		 function(){ $(this).css('border-left', "3px solid #fff") 
           			$(".my_con_active").css("border-left","3px solid "+$("#hid_mail_bgColor").val())	 
           		 }
           	);
        	/* if(fdrname.startsWith("/sharedContacts/"))
    		{
    		$(".first_con_option_3").hide();
    		}
        	else
     	   {
     	   $(".first_con_option_3").show();
     	   } */
         }
     });
	
}

function uploadDP(){	
	
	  //alert('Hi');
	  $('.pop_for_image_upload').show();
	  $('.web_dialog_overlay').show();
	  $('.user_information').removeClass('user_info_top');
	//  $('.user_info_top').hide();
	  $('.user_information').hide();
	  $('.user_information').addClass('removeUser_info');
	  
		}


function changeLDAPImg() {
	var oMyForm = new FormData();
	  
	var flszMBimg=0;
	 var upl1 = document.getElementById("upl1")   ;
	 // for(var i=0; i< upl1.files.length ;i++ )
	//	  {
		    var nm= upl1.files[0].name;
	  		oMyForm.append("file0", upl1.files[0]);
	  		var flsz=upl1.files[0].size;
	  		
	  		var flsz1=flsz/1024;
	  		
	  	var arr = nm.split(".");
	  
	  if((arr[arr.length-1]!="png" && arr[arr.length-1]!="PNG" && arr[arr.length-1]!="jpg" && arr[arr.length-1]!="JPG" && arr[arr.length-1]!="jpeg"))
		  {
		  var msg="Image type must be png or jpg only.";
		  showMsg('alert',msg) ;
		  }
	  else  if(flsz1> 100)
		  {
		  var msg="Accepted image size is up to 100KB.";
		  showMsg('alert',msg) ;
		  }
	  else
		  {
	  		
		  document.getElementById('action_gif').style.display= 'block';
	  $.ajax({
	    url: '${pageContext.request.contextPath}/uploadImage',
	    data: oMyForm,
	    dataType: 'text',
	    processData: false,
	    contentType: false,
	    type: 'POST',
	    success: function(data){
	    //	alert(data)
	    if(data!="false")
	    	{
	    	var mid=$("#hid_logedin_id").val();
	    	var sml_id=mid+"nomyimage";
	    	var big_id="big_"+mid+"nomyimage";
	    	//$(".new_user").html('<img alt="Embedded Image" onerror="getAltImage(this.id)" id="'+sml_id+'" src="data:image/jpg;base64,'+data+'" />');
	    	
	    	//$("#big_img").html('<img height="96px" width="96px" alt="Embedded Image"  onerror="getAltImage(this.id)" id="'+big_id+'" src="data:image/jpg;base64,'+data+'" /><div onclick="uploadDP()" class="change_images">Change photo</div>');
	    	//$('#nirbhay@silvereye.innomyimage').attr("src","data:image/jpg;base64,"+data);
	    //	$('#big_nirbhay@silvereye.innomyimage').attr("src","data:image/jpg;base64,"+data);
	    	/* $('#chat_id_bd').attr("src","data:image/jpg;base64,"+data);
	    	 $('#chat_id').attr("src","data:image/jpg;base64,"+data); */
	    	document.getElementById(sml_id).src="data:image/jpg;base64,"+data;
	    	document.getElementById(big_id).src="data:image/jpg;base64,"+data;
	    	} 
	    	document.getElementById('action_gif').style.display= 'none';
	    }
	  }); 
		  }
	  
}

function uploadContactDP(){	
	
	  //alert('Hi');
	  $('.pop_for_image_upload_contact').show();
	  $('.web_dialog_overlay').show();
	 
	  
		}

function changeContactImg() {
	var oMyForm = new FormData();
	 var upl2 = document.getElementById("upl2")   ;
	var flszMBimg=0;
	
	 // for(var i=0; i< upl1.files.length ;i++ )
	//	  {
		    var nm= upl2.files[0].name;
	  		oMyForm.append("file0", upl2.files[0]);
	  		var flsz=upl2.files[0].size;
	  		
	  		var flsz1=flsz/1024;
	  		
	  	var arr = nm.split(".");
	  
	  if((arr[arr.length-1]!="png" && arr[arr.length-1]!="PNG" && arr[arr.length-1]!="jpg" && arr[arr.length-1]!="JPG" && arr[arr.length-1]!="jpeg"))
		  {
		  var msg="Image type must be png or jpg only.";
		  showMsg('alert',msg) ;
		  }
	  else  if(flsz1> 10)
		  {
		  var msg="Accepted image size is up to 10KB.";
		  showMsg('alert',msg) ;
		  }
	  else
		  {
	  		
		  document.getElementById('action_gif').style.display= 'block';
	  $.ajax({
	    url: '${pageContext.request.contextPath}/uploadImageContact',
	    data: oMyForm,
	    dataType: 'text',
	    processData: false,
	    contentType: false,
	    type: 'POST',
	    success: function(data){
	    //	alert(data)
	    if(data!="false")
	    	{
	    	$("#hid_contact_img_code").val(data)
	    	var id="crt_con_img";
	    	
	    	//$(".new_user").html('<img alt="Embedded Image" onerror="getAltImage(this.id)" id="'+sml_id+'" src="data:image/jpg;base64,'+data+'" />');
	    	
	    	//$("#big_img").html('<img height="96px" width="96px" alt="Embedded Image"  onerror="getAltImage(this.id)" id="'+big_id+'" src="data:image/jpg;base64,'+data+'" /><div onclick="uploadDP()" class="change_images">Change photo</div>');
	    	//$('#nirbhay@silvereye.innomyimage').attr("src","data:image/jpg;base64,"+data);
	    //	$('#big_nirbhay@silvereye.innomyimage').attr("src","data:image/jpg;base64,"+data);
	    	/* $('#chat_id_bd').attr("src","data:image/jpg;base64,"+data);
	    	 $('#chat_id').attr("src","data:image/jpg;base64,"+data); */
	    	document.getElementById(id).src="data:image/jpg;base64,"+data;
	    	
	    	} 
	    	document.getElementById('action_gif').style.display= 'none';
	    }
	  }); 
		  }
}




function uploadContactDPEdit(){	
	
	  //alert('Hi');
	  $('.pop_for_image_upload_contact_edit').show();
	  $('.web_dialog_overlay').show();
	 
	  
		}

function changeContactImgEdit() {
	var oMyForm = new FormData();
	 var upl3 = document.getElementById("upl3")   ;
	var flszMBimg=0;
	
	 // for(var i=0; i< upl1.files.length ;i++ )
	//	  {
		    var nm= upl3.files[0].name;
	  		oMyForm.append("file0", upl3.files[0]);
	  		var flsz=upl3.files[0].size;
	  		
	  		var flsz1=flsz/1024;
	  		
	  	var arr = nm.split(".");
	  
	  if((arr[arr.length-1]!="png" && arr[arr.length-1]!="PNG" && arr[arr.length-1]!="jpg" && arr[arr.length-1]!="JPG" && arr[arr.length-1]!="jpeg"))
		  {
		  var msg="Image type must be png or jpg only.";
		  showMsg('alert',msg) ;
		  }
	  else  if(flsz1> 10)
		  {
		  var msg="Accepted image size is up to 10KB.";
		  showMsg('alert',msg) ;
		  }
	  else
		  {
		  $('.pop_for_image_upload_contact_edit').hide();
		  $('.web_dialog_overlay').hide();
		  document.getElementById('action_gif').style.display= 'block';
	  $.ajax({
	    url: '${pageContext.request.contextPath}/uploadImageContactEdit',
	    data: oMyForm,
	    dataType: 'text',
	    processData: false,
	    contentType: false,
	    type: 'POST',
	    success: function(data){
	    //	alert(data)
	    if(data!="false")
	    	{
	    	$("#hid_contact_img_code_edit").val(data)
	    	var id="con_img_id";
	    	
	    	
	    	document.getElementById(id).src="data:image/jpg;base64,"+data;
	    	
	    	} 
	    	document.getElementById('action_gif').style.display= 'none';
	    }
	  }); 
		  }
}

</script>
</head>
    <body onLoad="getWebmailContactfldr(),getWebmailContactRefresh()"> 
    
    
    
    
    



<%
   	HttpSession head_hs=request.getSession();
    String act_con_fldr= head_hs.getAttribute("active_contact").toString();
    String mailid=head_hs.getAttribute("id").toString();
    String fname=head_hs.getAttribute("fname").toString();
   // String img_myurl=head_hs.getAttribute("img_myurl").toString();
    String bgColor=(String)head_hs.getAttribute("bgColor");
    String path_img="images/blank_man.jpg";
    String path_img_id=mailid+"nomyimage";
    
    byte[] jpegBytes1=( byte[])head_hs.getAttribute("img_myurl");
    if(jpegBytes1!=null)
    {
 	   path_img= new sun.misc.BASE64Encoder().encode(jpegBytes1);
 	  path_img= "data:image/jpg;base64,"+path_img;
    }
    String dom="";
	if(mailid!=null)
	{
	String arr[]=mailid.split("@");
    dom=arr[arr.length-1];
	}
 %>

<input type="hidden" id="hid_contact_del_confirm" value=""/>
<input type="hidden" id="hid_mail_bgColor" value="<%=bgColor %>"/>
<input type="hidden" id="hid_mail_domain" value="<%=dom %>"/>
  
  <div style="top: 0px; left: 0px; width: 100%; background-color: white; height: 100%; position: absolute; z-index: 9999999;" id="div_progress">




      
      <style>
           div.meter {
		border: 1px solid #b0b0b0;
    border-radius: 3px;
    box-shadow: 0 3px 5px 0 #d3d0d0 inset;
    height: 22px;
    margin-left: 39%;
    margin-top: 5px;
    position: absolute;
    width: 320px;
    /* viewing purposes */
 
  /* viewing purposes */
  -webkit-box-shadow: inset 0 3px 5px 0 #d3d0d0;
  -moz-box-shadow: inset 0 3px 5px 0 #d3d0d0;
  box-shadow: inset 0 3px 5px 0 #d3d0d0;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  -ms-border-radius: 3px;
  -o-border-radius: 3px;

}

div.meter span {
  display: block;
  height: 100%;
  animation: grower 19s linear;
  -moz-animation: grower 19s linear;
  -webkit-animation: grower 19s linear;
  -o-animation: grower 19s linear;
  position: relative;
  top: -1px;
  left: -1px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  -ms-border-radius: 3px;
  -o-border-radius: 3px;
  border-radius: 3px;
  -webkit-box-shadow: inset 0px 3px 5px 0px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: inset 0px 3px 5px 0px rgba(0, 0, 0, 0.2);
  box-shadow: inset 0px 3px 5px 0px rgba(0, 0, 0, 0.2);
  border: 1px solid #b5b5b5;
  background: #6eb2d1;
 /*  background-image: -webkit-gradient(linear, 0 0, 100% 100%, color-stop(0.25, rgba(255, 255, 255, 0.2)), color-stop(0.25, transparent), color-stop(0.5, transparent), color-stop(0.5, rgba(255, 255, 255, 0.2)), color-stop(0.75, rgba(255, 255, 255, 0.2)), color-stop(0.75, transparent), to(transparent));
  background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  background-image: -moz-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  background-image: -ms-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  background-image: -o-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  */ -webkit-background-size: 45px 45px;
  -moz-background-size: 45px 45px;
  -o-background-size: 45px 45px;
  background-size: 45px 45px;
}
div.meter span:before {
  content: '';
  display: block;
  width: 100%;
  height: 50%;
  position: relative;
  top: 50%;
  background: rgba(0, 0, 0, 0.03);
}
div.meter p {
    color: #333;
    font-family: "Helvetica";
    font-size: 15px;
    font-weight: bold;
    line-height: 25px;
    margin: -4px 100px;
    position: absolute;
    text-shadow: 0 1px rgba(255, 255, 255, 0.6);
    top: 0;
}
@keyframes grower {
  0% {
    width: 0%;
  }
}

@-moz-keyframes grower {
  0% {
    width: 0%;
  }
}

@-webkit-keyframes grower {
  0% {
    width: 0%;
  }
}

@-o-keyframes grower {
  0% {
    width: 0%;
  }
}
     </style>
     
   <div style="margin-top: 14%; text-align: center;">  Loading <%=mailid %>...</div>
     <div class="meter">
     
  <span style="width:97%"></span>
  <p></p>
</div>

</div>
  
    <input type="hidden" id="hid_logedin_id" value="<%=mailid %>"/>
   <input type='hidden' value='<%=act_con_fldr %>' id='hid_active_contact_fldr' /> 
    <div id="action_gif" class="my_notification" style="display: none;left: 46%;">
    Loading...
    </div>
     <div id="mail_sending" class="my_notification" style="display: none;left: 46%;">
    Sending...
    </div>
     <div id="mail_sent" class="my_notification" style="display: none;left: 43%;">
    Your message has been sent. 
    </div>
        <div class="minwidth">
            <!----------/// HEADER STARED HERE -------->
            <div class="header">
                <div class="header_inner">
                    <div class="logo">
					<a style="cursor: pointer;" href="contacts"> <img class="head_logo" src="logo/<%=dom %>.png" onerror="getHeadLogo(this.id)" id="main_logo"/>
					</a>
				</div>
                    <div class="header_right">
                   <div class="main_search_folder">
                        <div class="search_it">
                          <!--   <input type="text"   onClick="return Hide();" placeholder="Search Here...."> -->
                            <input type="text"   id="searchid" placeholder="Search Here....">
                          
                            <a onclick="searchContacts()">
                                <div class="search_icon"></div>
                            </a> 


                        </div>
                        
                     </div>  
                        <div class="new_user" title="Profile" onClick="userinformation(event);" > <img  src="<%=path_img %>" onerror="getAltImage(this.id)" id="<%=path_img_id %>" /></div>


                      <div class="user_noti">
			<div class="bell_left" title="Notifications">		<img alt="Notification Image" src="images/notification.png"></div>
              <div class="bell_right">2</div>
				</div>
					<div class="user_noti_content arrow_box_2">
				<div class="noti_title">Reminders</div>
				<div class="noti_inner">
					<!-- <div class="noti_inner ">
					<div class="noti_row">
  <div class="noti_left">Happy birthay to you</div>
  <div class="noti_right">9 hours overdue </div>

</div>
<div class="noti_row">
  <div class="noti_left">Bday</div>
  <div class="noti_right">9 hours</div>

</div></div> --></div>
					</div>  
                        
                        
                 <!---------/// MORE PRODUCT STARED HERE ----->
  <div class="more_product" title="Apps"></div>
      <div class="more_product_content arrow_box_1">
                  
                     <ul>
                     
                         <li>
                            <a href="inbox" target="_blank">
                               <div class="mail_ring">
                                  <span class="mail_prod"><img src="images/product_icon/new/mail.png" /></span>
                               </div>
                                  <span class="pro_info">Mail</span>
                            </a>
                         </li>
                         <li>
                            <a href="calendar" target="_blank">
                              <div class="calender_ring">
                                 <span class="calender_prod"><img src="images/product_icon/new/calender.png" /></span>
                              </div>
                                 <span class="pro_info">Calendar</span>
                            </a>
                         </li>
                         <li>
                            <a href="contacts" >
                              <div class="contact_ring">
                                 <span class="phone_book"><img src="images/product_icon/new/phone.png"/></span>
                              </div>
                                 <span class="pro_info">Contact</span>
                            </a>
                         </li>
                          <li>
                            <a href="tasks" target="_blank">
                             <div class="tasks_ring">
                                 <span class="task_pro"><img src="images/product_icon/new/tasks.png"/></span>
                             </div>
                                 <span class="pro_info">Tasks</span>
                            </a>
                         </li> 

                        <li>
                            <a href="/edms" target="_blank">
                            <div class="brief_ring">
                                 <span class="folder_pro"><img src="images/product_icon/new/briefcase.png"/></span>
                             </div>
                                 <span class="pro_info">Vault</span>
                            </a>
                         </li> 

                       <li>
                            <a  href="http://www.wihg.res.in" target="_blank">
                            <div class="web_ring">
                                 <span class="web_prod"><img src="images/product_icon/new/web.png" /></span>
                            </div>
                                 <span class="pro_info">Web</span>
                            </a>
                         </li> 
                       
                     
                     </ul>
                   
          
      
      </div> 
      <!-------// MORE PRODUCT END HERE -------->
                        
                        
                        
                        

                    </div>

                </div>

            </div>
            <!----------/// HEADER END HERE ----------> 
            <!---------/// MID CONTENT STARED HERE ---->
            <div class="content"> 
            <!-- <div id="search_form" class="search_form_1">
                    <div class="form">
                        <form action="" method="get">
                            <div class="to">
                                <div class="name search_text">Search</div>
                                <a href="#"><div class="search_top">Inbox</div></a>
                                <a href="#"><div style="background-color: #fff;" class="search_top">All Mail</div></a>
                                <div class="clear"></div>
                                <div style="padding-top: 15px;"></div>
                                <input type="text" placeholder="To" name="to_search" value="" class="search_clear border input" id="to_search">
                                <div  style="padding-top: 50px;"></div>
                                <input type="text" placeholder="From" name="from_search" value="" class="search_clear border input" id="from_search">
                                <div  style="padding-top: 50px;"></div>
                                <input type="text" name="sub_search" placeholder="Subject" value="" class="search_clear border input" id="sub_search">
                                <div  style="padding-top: 50px;"></div>
                                <input type="text" name="con_search" placeholder="Content" value="" class="search_clear border input" id="con_search">
                                <div  style="padding-top: 50px;"></div>
                                <input type="date" name="date_search" placeholder="Date" value="" class="search_clear border input" id="date_search">
                                <div  style="padding-top: 65px;"></div>
                                <div class="clear"></div>
                                 <div class="check check_upper">
                                    <input name="" type="checkbox" value="">
                                    <span>Don't include chats</span></div>
                            </div>
                            <div class="search_button"><a onclick="mailAdvSearch()" style="cursor: pointer;" title="Search"> <span class="search_icon2"> </span></a> </div>
                        </form>
                        
                        <input type="hidden" value="" id="hid_quick_search" />
                        <input type="hidden" value="" id="hid_to_search" />
                        <input type="hidden" value="" id="hid_from_search" />
                        <input type="hidden" value="" id="hid_sub_search" />
                        <input type="hidden" value="" id="hid_con_search" />
                        <input type="hidden" value="" id="hid_date_search" />
                    </div>
                </div> -->
                
                
                
                <!-----------/// USER INFORMATION BOX STARED HERE --------->
                <div class="user_information arrow_box">

                    <!----------/// TOP SECTION STARED HERE ------------>
                    <div class="user_top">
                        <a id="big_img" style="cursor: pointer;">
                       
                            <img height='96px' width='96px' src="<%=path_img %>" onerror="getAltImage(this.id)" id="big_<%=path_img_id %>" />
                            <div onclick="uploadDP()" class="change_images">Change photo</div>
                        </a>

                        <div class="left_top">
                          <a  name="userName">  <%=fname %> </a>
                            <span><%=mailid %></span>
                            <div class="clear_2"></div>
                            <div class="progress_bar">
                            <div id="uquota">
                             <%
                             int per=0;
                             String per_name="bar5.png";
                             
                                 String qper=(String)request.getAttribute("QuotaPer");
                                 if(qper!=null && !(qper.equals("")))
                                 {
                                	 int pval=Integer.parseInt(qper.trim());
                                	 per=pval;
                                	 if(pval<=5)
                                	 {
                                		 per_name="bar5.png";
                                	 }
                                	 else if(pval<=10)
                                	 {
                                		 per_name="bar10.png";
                                	 }
                                	 else if(pval<=20)
                                	 {
                                		 per_name="bar20.png";
                                	 }
                                	 else if(pval<=30)
                                	 {
                                		 per_name="bar30.png";
                                	 }
                                	 else if(pval<=40)
                                	 {
                                		 per_name="bar40.png";
                                	 }
                                	 else if(pval<=50)
                                	 {
                                		 per_name="bar50.png";
                                	 }
                                	 else if(pval<=60)
                                	 {
                                		 per_name="bar60.png";
                                	 }
                                	 else if(pval<=70)
                                	 {
                                		 per_name="bar70.png";
                                	 }
                                	 else if(pval<=80)
                                	 {
                                		 per_name="bar80.png";
                                	 }
                                	 else if(pval<=85)
                                	 {
                                		 per_name="bar85.png";
                                	 }
                                	 else if(pval<=90)
                                	 {
                                		 per_name="bar90.png";
                                	 }
                                	 else if(pval<=100)
                                	 {
                                		 per_name="bar100.png";
                                	 }
                                	
                                 }
                                 
                                 %>
                                 <img src="images/<%=per_name %>" />
                                 <div class="percentage_value">
                                
                                 <%=per %>%
                                 </div>
                                 </div>
                                 <div class="clear"></div>
                            </div>
                              <div style="width: 170px;">
							<!-- <img src="images/color.png" /> -->
							<table>
								<tr>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#DC4FAD"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg1.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#AC193D"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg2.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#D24726"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg3.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#FF8F32"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg4.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#82BA00"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg5.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#03B3B2"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg6.png" />
									</a></td>
									<td style="padding-right: 2px;" width="20px"><a
										style="cursor: pointer;" name="#326a3e"
										onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg7.png" />
									</a></td>
									<td width="20px"><a style="cursor: pointer;"
										name="#8C0095" onclick="changeHBG(this.name)"> <img
											style="width: 20px !important; height: 20px;"
											src="images/bg8.png" />
									</a></td>
								</tr>
							</table>
						</div>
                        </div>
					</div>
        <!------------/// TOP SECTION END HERE -------------->
        <!----------/// BOTTOM SECTION STARED HERE ------------>
                    <div class="user_bottom">
                     
                            <a href="logout">
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
  </body>
  </html>