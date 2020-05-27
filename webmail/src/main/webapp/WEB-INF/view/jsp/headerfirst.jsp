<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.security.Principal,webmail.webservice.client.WebmailClient,webmail.wsdl.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
    <head>
   
        <script src="js/jquery-1.8.3.min.js"></script>
        <script src="js/splitter.js"></script>
        <script src="js/sytem-script.js"></script>
      <script src="js/jquery-ui.js"></script>
      <script src="js/jquery.splitter-0.14.0.js"></script>
       <script src="js/more_product.js"></script>
       
       
    
       
       
       <link href='css/more_product.css' rel='stylesheet' type='text/css' />
      <link href='css/compose_new.css' rel='stylesheet' type='text/css' />
    <link type="text/css" rel="stylesheet" href="css/jquery-ui.css"/>
        <link rel="shortcut icon" href="images/favicon.ico"/>
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
        <link type="text/css" rel="stylesheet" href="css/blue.css"/>
        <link type="text/css" rel="stylesheet" href="css/main_css.css"/>
        <link href='css/setting.css' rel='stylesheet' type='text/css' />
        <link href='css/compose_new.css' rel='stylesheet' type='text/css' />
         <link href='css/jquery.splitter-bottom.css' rel='stylesheet' type='text/css' />
		 <script src="js/compose_event.js"></script>
		  <link href='css/new_forward.css' rel='stylesheet' type='text/css' />
		   <link href='css/tooltip.css' rel='stylesheet' type='text/css' />
		 <script src="js/new_forword.js"></script>
		 <script src="js/jquery.autocomplete.js"></script>
		 <!-----------/// CK EDITOR STARED HERE ------------>
	<!-- <script src="ckeditor/ckeditor.js"></script> -->
	<!-- <script src="ckeditor/jquery.js"></script> -->
	 <link rel="stylesheet" type="text/css" href="css/autocomplete.css" />
	<!--   <link rel="stylesheet" type="text/css" href="ckeditor/change_ck.css" />  -->
    <!-----------/// CK EDITOR END HERE -------------->
    <!-- tag cs js started -->
    <script src="js/tag.js"></script>
	 <link rel="stylesheet" type="text/css" href="css/tag.css" /> 
	<!--  <script src="//cdn.jsdelivr.net/ckeditor/4.0.1/ckeditor.js"></script> -->
	 <script src="//cdn.ckeditor.com/4.4.7/standard/ckeditor.js"></script>
	
       <!-------///tag end ------------>
	 

    
       <script type="text/javascript">
jQuery(function(){
	$("#to_id").autocomplete("${pageContext.request.contextPath}/autocomp");
	$("#cc_id").autocomplete("${pageContext.request.contextPath}/autocomp");
	$("#bcc_id").autocomplete("${pageContext.request.contextPath}/autocomp");
	$("#to_search").autocomplete("${pageContext.request.contextPath}/autocomp");
	$("#from_search").autocomplete("${pageContext.request.contextPath}/autocomp");
	$("#quick_search").autocomplete("${pageContext.request.contextPath}/autocomp");
});
</script>
       <!-----------/// Contact start HERE -------------->
        <!--   <link href='css/contact_css.css' rel='stylesheet' type='text/css' />
		 <script src="js/contact_js.js"></script> -->
       <!-----------/// Contact END HERE -------------->
       
       
       
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
        
        
      <script src="js/event.js" type="text/javascript" language="javascript" ></script> 
      <script src="js/tab_event.js" type="text/javascript" language="javascript" ></script> 
      <!-----------/// COLOR PICKER STRED HERE ----------->
    <!--  <link rel="stylesheet" type="text/css" href="color_picker/spectrum.css">
    <script type="text/javascript" src="color_picker/spectrum.js"></script>
    <script type='text/javascript' src='color_picker/docs.js'></script>  -->
<!-------------/// COLOR PICKER END HERE --------------->
            
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
               
        
        
<script type="text/javascript">
function getWebmailfldr(){
	//alert("meeeeeeeeeeeeeeeeeee");
	var fdrname=document.getElementById('hid_active_fldr').value;
	 $.ajax({
         type: "GET",
         url: "${pageContext.request.contextPath}/getWbmailfolder",
        // data: {'path':folderPath},
         contentType: "application/json",
         success: function (data) {
            // $("#fileSystem").html(data);
           // alert(data);
            $("#folder_div").html(data);
            document.getElementById(fdrname).className = "active_mailbox";
         }
     });
	
}

/* function getWebmailsubfldr(){
	alert("meeeeeeeeeeeeeeeeeeeSub=");
	 $.ajax({
         type: "GET",
         url: "${pageContext.request.contextPath}/getWbmailsubfolder",
        // data: {'path':folderPath},
         contentType: "application/json",
         async: false,
         success: function (data) {
            // $("#fileSystem").html(data);
            alert(data);
         }
     });
	
} */
</script>
<!-- start  bottom view splitter -->
<script>
       jQuery(function($) {
    $(window).on('resize', function() {
        var height = $(window).height()-135;
		//alert(height);
        console.log(height);
		// LEFT VIEW CHAT
		$('#foo').height(height).split({ orientation:'horizontal', limit:50 });
		$('#foo').css('height',height);
        $('#b').height(height / 2)-30;
		//$('.chat_inner_content').height(height / 2);
		
		/* 	$('#widget').height(height).split({ orientation:'horizontal', limit:50 });
		$('#widget').css('height',height);
        $('.mail_right_con').height(height / 2)-30; */
		
    }).trigger('resize');
});

  
    </script>
    <!-- end bottom view splitter -->

<script type="text/javascript">
	function getAltImage(imgid) {
		var pic = document.getElementById(imgid);
		pic.src = "images/blank_man.jpg"
	}
	
	
function getAltImageDisp(imgid) {
	var pic = document.getElementById(imgid);
	pic.src = "images/unnamed.png"
}

function sendmsg() {
	
}
</script>
<script type="text/javascript">

function checkEmail(email) {
	//alert(email);
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return filter.test(email);
}
</script>
<script type="text/javascript">
function mailSend() {
	var to=document.getElementById("hid_to_id").value;
		
	var cc=document.getElementById("hid_cc_id").value;
	
	
	var bcc=document.getElementById("hid_bcc_id").value;
	
	var ml_priority=document.getElementById("hid_priority").value;
	 
	var read_rec= document.getElementById("hid_read_rec").value;
	//alert(read_rec)
	var sub=document.getElementById("sub_id").value;
	var cntt = CKEDITOR.instances['editor1'].getData();
	var sts=false;
	if(sub==null || sub=="")
		{
		sts = confirm("The Subject field is empty. Would you like to enter Subject?");
		}
	if(sts==false)
		{
		document.getElementById('mail_sending').style.display= 'block';
		$.ajax({
	         type: "POST",
	         url: "${pageContext.request.contextPath}/sendComposeMail",
	        data: {'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt, 'read_rec': read_rec, 'ml_priority': ml_priority },
	         contentType: "application/json",
	         success: function (data) {
	            // $("#fileSystem").html(data);
	            
	            document.getElementById('mail_sending').style.display= 'none';
	            document.getElementById('mail_sent').style.display= 'block';
	            //alert(data);
	            document.getElementById('div_for_compose').style.display= 'none';
	            document.getElementById('div_for_inbox').style.display= 'block';
	            setTimeout( "jQuery('#mail_sent').hide();",3000 );
	         
	         //   $("#folder_div").html(data);
	        //    document.getElementById(fdrname).className = "active_mailbox";
	         }
	     });
		
		
		}
	
}

function sendRecMail(id){
	var sub=document.getElementById("hid_"+id+"_sub").innerHTML ;
	var cntt=document.getElementById("hid_"+id+"_cnt").innerHTML ;
	document.getElementById('mail_sending').style.display= 'block';
	$.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/sendMailReadRec",
       data: {'to':id, 'sub':sub, 'cntt':cntt },
        contentType: "application/json",
        success: function (data) {
           // $("#fileSystem").html(data);
           
           document.getElementById('mail_sending').style.display= 'none';
           document.getElementById('mail_sent').style.display= 'block';
           //alert(data);
           
           setTimeout( "jQuery('#mail_sent').hide();",3000 );
        
        //   $("#folder_div").html(data);
       //    document.getElementById(fdrname).className = "active_mailbox";
        }
    });
	
}
</script>

<script type="text/javascript">
function changeHBG(nm) {
	//alert(nm);
	$(".header").css("background-image","none");
	$(".header").css("background-color",nm);
	$(".search_button").css("background-image","none");
	$(".search_button").css("background-color",nm);
	$(".ui-widget-header").css("background-image","none");
	$(".ui-widget-header").css("background-color",nm);
	$(".composed_box_new").css("background-image","none");
	$(".composed_box_new").css("background-color",nm);

	
}
</script>
<script type="text/javascript">
function mailAdvSearch() {
	var to= document.getElementById('to_search').value;
	var from= document.getElementById('from_search').value;
	var sub= document.getElementById('sub_search').value;
	var con= document.getElementById('con_search').value;
	var dt= document.getElementById('date_search').value;
	if((to!=null && to!="") || (from!=null && from!="") || (sub!=null && sub!="") || (con!=null && con!="") || (dt!=null && dt!=""))
	{
		
		
		var element = document.getElementById("parent_fldr");
	    var path = element.options[element.selectedIndex].value;
		var pfldr=path;   //document.getElementById('hid_active_fldr').value
	$('.search_clear').val('');
	showHide(event);
	var start='0';
	var end='15';
	document.getElementById('hid_to_search').value=to;
	document.getElementById('hid_from_search').value=from;
	document.getElementById('hid_sub_search').value=sub;
	document.getElementById('hid_con_search').value=con;
	document.getElementById('hid_date_search').value=dt;
	document.getElementById('action_gif').style.display= 'block';
	
	 /* $.ajax({
	        type: "GET",
	        url: "${pageContext.request.contextPath}/advSearchMailSize",
	       data: {'to':to, 'from':from, 'sub':sub, 'dt':dt, 'con':con, 'fldrnm': pfldr},
	        contentType: "application/json",
	        success: function (data) {
	        alert(data);
	        	
	        }
	    });  */
	    
	    var pview=document.getElementById("hid_panel_view").value;
	
	 $.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/advSearchMail",
       data: {'to':to, 'from':from, 'sub':sub, 'dt':dt, 'con':con, 'start':start, 'end':end, 'fldrnm': pfldr, 'pview': pview},
        contentType: "application/json",
        success: function (data) {
       //   alert(data);
        	if(pfldr=="Sent")
			{
			$("#div_from").html("TO");
			}
		else
			{
			$("#div_from").html("FROM");
			}
        	
        	 var arr=data.split("<$nps$>");
        	// alert(arr[0]);
        	  var vl=arr[0];
        	  
              var ar=vl.split("$");
              var mx=ar[0]
             // alert("mx="+mx);
             var pagi_val=ar[1];
           //  alert("pagi_val="+pagi_val);
               document.getElementById('pagination_div').innerHTML=pagi_val;
               document.getElementById('hid_pagi_search_allmail').value=mx;
               document.getElementById('hid_pagi_search_pcnt').value="1";
               document.getElementById('hid_pagi_search_type').value="adv";
               document.getElementById('mail_pagi').style.display= 'none';
               document.getElementById('search_pagi').style.display= 'block';
        	 
             var res="";
             if(arr.length>1)
             	{
             	res=arr[1];
             	}
        	
		$("#inb_cnt_div").html(res);
		document.getElementById('action_gif').style.display= 'none';
		//getAllMailCount(fdrname);
       
        }
    }); 
    
   
	}	
}

function quickMailSearch()
{
	var quick_val= document.getElementById('quick_search').value;
	if(quick_val!=null && quick_val!="")
		{
	var pfldr="inbox";   //document.getElementById('hid_active_fldr').value
	
	//showHide(event);
	var start='0';
	var end='15';
	document.getElementById('action_gif').style.display= 'block';
	 document.getElementById('hid_quick_search').value=quick_val;
	/*  $.ajax({
	        type: "GET",
	        url: "${pageContext.request.contextPath}/quickSearchMailSize",
	       data: {'quick_val':quick_val,'fldrnm': pfldr},
	        contentType: "application/json",
	        success: function (data) {
	         alert(data);
	        	
	        }
	    });  */
	    var pview=document.getElementById("hid_panel_view").value;
	 $.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/quickSearchMail",
       data: {'quick_val':quick_val, 'start':start, 'end':end, 'fldrnm': pfldr, 'pview': pview},
        contentType: "application/json",
        success: function (data) {
       //   alert(data);
        	if(pfldr=="Sent")
			{
			$("#div_from").html("TO");
			}
		else
			{
			$("#div_from").html("FROM");
			}
        var arr=data.split("<$nps$>");
       var vl=arr[0];
      // alert(arr[0]);
       var ar=vl.split("$");
       var mx=ar[0]
      // alert("mx="+mx);
      var pagi_val=ar[1];
     // alert("val="+pagi_val);
      
        document.getElementById('pagination_div').innerHTML=pagi_val;
        document.getElementById('hid_pagi_search_allmail').value=mx;
        document.getElementById('hid_pagi_search_pcnt').value="1";
        document.getElementById('hid_pagi_search_type').value="quick";
        
        document.getElementById('mail_pagi').style.display= 'none';
        document.getElementById('search_pagi').style.display= 'block';
        
        var res="";
        if(arr.length>1)
        	{
        	res=arr[1];
        	}
		$("#inb_cnt_div").html(res);
		$('#quick_search').val('');
		document.getElementById('action_gif').style.display= 'none';
		//getAllMailCount(fdrname);
       
        }
    }); 
    
		}
}


$('#quick_search').keydown(function(e){
 //  alert(e.which);
    if(e.which == 13)
    {
    	quickMailSearch();
    }
});
</script>
<script type="text/javascript">
function pagiSearchNextPage() {
	
   var typ= document.getElementById('hid_pagi_search_type').value;
   
	var pcnt=parseInt(document.getElementById("hid_pagi_search_pcnt").value);
	var allml=parseInt(document.getElementById("hid_pagi_search_allmail").value);
	 var lmt=pcnt+1;
	 var sml=pcnt*15;
	var all=pcnt*15;
	if(all<=allml)
		{
		alert(all);
		var s=(pcnt*15)+1;
		var e=(pcnt*15)+15;
		if(e>allml)
			{
			e=allml;
			}
		document.getElementById("pagination_div").innerHTML=''+s+" - "+e+" of "+allml;
		document.getElementById("hid_pagi_search_pcnt").value=lmt;
		/* var dtsort=document.getElementById("hid_dt_sorting").value;
		var path="getMailInbox";
		if(dtsort=="down")
			{
			path="getMailInboxDesc";
			} */
	//var pfldr=document.getElementById('hid_active_fldr').value;
	//document.getElementById('hid_active_fldr').value=fdrname;
	//document.getElementById(pfldr).className = "";
	//document.getElementById(fdrname).className = "active_mailbox";
	var pfldr="inbox";
	var start=''+sml;
	var end='15';
	var pview=document.getElementById("hid_panel_view").value;
	document.getElementById('action_gif').style.display= 'block';
	if(typ=="quick")
		{
		quick_val=document.getElementById('hid_quick_search').value;
	$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/quickSearchMail",
					data : {
						'quick_val':quick_val, 'start':start, 'end':end, 'fldrnm': pfldr, 'pview': pview
					},
					contentType : "application/json",
					success : function(data) {
						// $("#fileSystem").html(data);
						// alert(data);
						 var arr=data.split("<$nps$>");
					     var res="";
					        if(arr.length>1)
					        	{
					        	res=arr[1];
					        	}
						$("#inb_cnt_div").html(res);
						document.getElementById('action_gif').style.display= 'none';
						//getAllMailCount(fdrname);
					}
				});
		}
	else if(typ=="adv")
	{
		to=document.getElementById('hid_to_search').value;
		from=document.getElementById('hid_from_search').value;
		sub=document.getElementById('hid_sub_search').value;
		con=document.getElementById('hid_con_search').value;
		dt=document.getElementById('hid_date_search').value;
		$.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}/advSearchMail",
						data : {
							'to':to, 'from':from, 'sub':sub, 'dt':dt, 'con':con, 'start':start, 'end':end, 'fldrnm': pfldr, 'pview': pview
						},
						contentType : "application/json",
						success : function(data) {
							// $("#fileSystem").html(data);
							// alert(data);
							 var arr=data.split("<$nps$>");
					     var res="";
					        if(arr.length>1)
					        	{
					        	res=arr[1];
					        	}
							$("#inb_cnt_div").html(res);
							document.getElementById('action_gif').style.display= 'none';
							//getAllMailCount(fdrname);
						}
					});
			}
		
		
		}
	
	
	
}


function pagiSearchPrevPage() {
	
	var typ= document.getElementById('hid_pagi_search_type').value;
	var pcnt=parseInt(document.getElementById("hid_pagi_search_pcnt").value);
	var allml=parseInt(document.getElementById("hid_pagi_search_allmail").value);
	 var lmt=pcnt-1;
	 var sml=(lmt*15)-15;
	var all=lmt*15;
	if(all>0)
		{
		
		var s=(lmt*15)-15+1;
		var e=(lmt*15);
		if(e>allml)
			{
			e=allml;
			}
		document.getElementById("pagination_div").innerHTML=''+s+" - "+e+" of "+allml;
		
		document.getElementById("hid_pagi_search_pcnt").value=lmt;
		/* var dtsort=document.getElementById("hid_dt_sorting").value;
		var path="getMailInbox";
		if(dtsort=="down")
			{
			path="getMailInboxDesc";
			} */
		var pfldr="inbox";   //document.getElementById('hid_active_fldr').value
		var start=''+sml;
		var end='15';
		document.getElementById('action_gif').style.display= 'block';
		var pview=document.getElementById("hid_panel_view").value;
		if(typ=="quick")
		{
		$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/quickSearchMail",
					data : {
						'quick_val':quick_val, 'start':start, 'end':end, 'fldrnm': pfldr, 'pview': pview
					},
					contentType : "application/json",
					success : function(data) {
						 var arr=data.split("<$nps$>");
					     var res="";
					        if(arr.length>1)
					        	{
					        	res=arr[1];
					        	}
						$("#inb_cnt_div").html(res);
						document.getElementById('action_gif').style.display= 'none';
						//getAllMailCount(fdrname);
					}
				});
		
		}
		else if(typ=="adv")
		{
			$.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}/advSearchMail",
						data : {
							'to':to, 'from':from, 'sub':sub, 'dt':dt, 'con':con, 'start':start, 'end':end, 'fldrnm': pfldr, 'pview': pview
						},
						contentType : "application/json",
						success : function(data) {
							 var arr=data.split("<$nps$>");
						     var res="";
						        if(arr.length>1)
						        	{
						        	res=arr[1];
						        	}
							$("#inb_cnt_div").html(res);
							document.getElementById('action_gif').style.display= 'none';
							//getAllMailCount(fdrname);
						}
					});
			
			}
		}
	
}
</script>
<script type="text/javascript">
/* window.om=''; */
 
 window.om='';
function addSignature() {
	 document.getElementById('action_gif').style.display= 'block';
		$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/addSignature",
					/* data : {
						'folder' : fdrname,
						'start' : start,
						'end' : end,
						'pview' : pview
					}, */
					contentType : "application/json",
					success : function(data) {
						//alert(data);
						window.om=data;
						document.getElementById('action_gif').style.display= 'none';
						
						var oEditor = CKEDITOR.instances;
						oEditor.editor1.insertHtml("<br>"+data);
						
						
					}
				});

}
</script>
</head>
    <body onload="getWebmailfldr(),getWebmailInboxRefresh()"> 
    
    
    
    
    
    
    
    
    




<%
    HttpSession head_hs=request.getSession();
	String host=(String)head_hs.getAttribute("host");
	String id=(String)head_hs.getAttribute("id");
	String pass=(String)head_hs.getAttribute("pass");
	String port=(String)head_hs.getAttribute("port");
    String act_fldr= head_hs.getAttribute("active_folder").toString();
    String mailid=head_hs.getAttribute("id").toString();
    String fname=head_hs.getAttribute("fname").toString();
    String img_myurl=head_hs.getAttribute("img_myurl").toString();
   
                        String path_img=img_myurl+mailid+".jpg";
                        String path_img_id=mailid+"nomyimage";
                        %>

<div style="top: 0px; left: 0px; width: 100%; background-color: white; height: 100%; position: absolute; z-index: 9999999;" id="div_progress">



<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
      
      <style>
        div.meter {
		border: 1px solid #b0b0b0;
    border-radius: 3px;
    box-shadow: 0 3px 5px 0 #d3d0d0 inset;
    height: 11px;
    margin-left: 43%;
   /* margin-top: 15%;*/
    position: absolute;
    width: 250px;
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
  border: 1px solid #3c84ad;
  background: #6eb2d1;
  background-image: -webkit-gradient(linear, 0 0, 100% 100%, color-stop(0.25, rgba(255, 255, 255, 0.2)), color-stop(0.25, transparent), color-stop(0.5, transparent), color-stop(0.5, rgba(255, 255, 255, 0.2)), color-stop(0.75, rgba(255, 255, 255, 0.2)), color-stop(0.75, transparent), to(transparent));
  background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  background-image: -moz-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  background-image: -ms-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  background-image: -o-linear-gradient(45deg, rgba(255, 255, 255, 0.2) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.2) 50%, rgba(255, 255, 255, 0.2) 75%, transparent 75%, transparent);
  -webkit-background-size: 45px 45px;
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
     
   <div style="margin-top: 14%; margin-left: 43%;">  Loading <%=mailid %>...</div>
     <div class="meter">
     
  <span style="width:97%"></span>
  <p></p>
</div>

</div>
  
    
    <input type='hidden' value='<%=act_fldr %>' id='hid_active_fldr' />
    <div id="action_gif" class="my_notification" style="display: none;left: 46%;">
    Loading...
    </div>
     <div id="mail_sending" class="my_notification" style="display: none;left: 46%;">
    Sending...
    </div>
     <div id="mail_sent" class="my_notification" style="display: none;left: 43%;">
    Your message has been sent. 
    </div>
     <div id="mail_spam" class="my_notification" style="display: none;left: 43%;">
   The mail has been marked as spam.  
    </div>
    
        <div class="minwidth">
            <!----------/// HEADER STARED HERE -------->
            <div class="header">
                <div class="header_inner">
                    <div class="logo">
                    <a href="#">
                     <img src="images/logo.png" /> 
                     </a>
                     </div>
                    <div class="header_right">
                     <!-- <div class="main_search_folder">
                        <div class="search_it">
                            <input type="text" id="quick_search" name="quick_search"  onClick="return Hide();" placeholder="Search Here....">
                            <a href="#">
                                <div class="down_arrow" onClick="return showHide(event);"></div>
                            </a> 
                            -------- /// SEARCH FROM STARTED HERE --- 

                            ---------/// SEARCH FROM END HERE ------- 
                            <a style="cursor: pointer;" onclick="quickMailSearch()">
                                <div class="search_icon"></div>
                            </a> 


                        </div>
                        
                     </div>    -->
                        <div class="new_user" onClick="userinformation(event);" > <img  src="<%=path_img %>" onerror="getAltImage(this.id)" id="<%=path_img_id %>" /></div>

     
              <!--   <!---------/// MORE PRODUCT STARED HERE 
      <div class="more_product"></div>
      <div class="more_product_content arrow_box_1">
                   -------/// MORE PRODUCT ITEM STARED --
                     <ul>
                     
                         <li>
                            <a href="inbox">
                               <div class="mail_ring">
                                  <span class="mail_prod"><img src="images/product_icon/new/mail.png" /></span>
                               </div>
                                  <span class="pro_info">Mail</span>
                            </a>
                         </li>
                         <li>
                            <a href="calendar">
                              <div class="calender_ring">
                                 <span class="calender_prod"><img src="images/product_icon/new/calender.png" /></span>
                              </div>
                                 <span class="pro_info">Calender</span>
                            </a>
                         </li>
                         <li>
                            <a href="contacts">
                              <div class="contact_ring">
                                 <span class="phone_book"><img src="images/product_icon/new/phone.png"/></span>
                              </div>
                                 <span class="pro_info">Contact</span>
                            </a>
                         </li>
                         <li>
                            <a href="#">
                             <div class="tasks_ring">
                                 <span class="task_pro"><img src="images/product_icon/new/tasks.png"/></span>
                             </div>
                                 <span class="pro_info">Tasks</span>
                            </a>
                         </li>

                         <li>
                            <a href="#">
                            <div class="brief_ring">
                                 <span class="folder_pro"><img src="images/product_icon/new/briefcase.png"/></span>
                             </div>
                                 <span class="pro_info">Briefcase</span>
                            </a>
                         </li>

                         <li>
                            <a href="#">
                            <div class="web_ring">
                                 <span class="web_prod"><img src="images/product_icon/new/web.png" /></span>
                            </div>
                                 <span class="pro_info">Web</span>
                            </a>
                         </li>
                       
                     
                     </ul>
                   ------/// MORE PRODUCT ITEM END HERE ----
                   
          
      
      </div>
      -----// MORE PRODUCT END HERE ------
                         -->

                    </div>

                </div>

            </div>
            <!----------/// HEADER END HERE ----------> 
            <!---------/// MID CONTENT STARED HERE ---->
            <div class="content"> 
            <%-- <div id="search_form" class="search_form_1">
            <%
            
            WebmailClient webmailClient=(WebmailClient) request.getAttribute("webmailClient");
         	String fldr_lst=(String) request.getAttribute("fldr_lst");
         	 HashMap hm_fldr = new HashMap();
        	 HashMap hm_fldr_path = new HashMap();
        	 int hm_i=0;
            %>
            
            
             <%
             String arr[]=fldr_lst.split(";");
     		//System.out.println("************************* webmail folder lenth="+arr.length);
     		for(int i=0; i<arr.length; i++)
     		{
     			String prm="";
     			if(arr[i].equalsIgnoreCase("Inbox") || arr[i].equalsIgnoreCase("Drafts") || arr[i].equalsIgnoreCase("Sent") || arr[i].equalsIgnoreCase("Junk") || arr[i].equalsIgnoreCase("Trash") || arr[i].equalsIgnoreCase("Archive")  )
     			{
     				prm=arr[i];
     				String hm_val=arr[i];
 					hm_fldr.put(hm_i, hm_val);
 					hm_fldr_path.put(hm_i, prm);
 					hm_i++;
     			
     			}
     			else
     			{
     				prm=arr[i];
     				int flg=Integer.parseInt(arr[i+1]);
     				
     				if(flg>0)
     				{
     					String hm_val=arr[i];
     					hm_fldr.put(hm_i, hm_val);
     					hm_fldr_path.put(hm_i, prm);
     					hm_i++;
     					
     					GetWebmailSubFolderResponse wsfr=webmailClient.getWebmailSubFolderRequest(host, id, pass, arr[i]);
     					String mysubfdr=wsfr.getGetWebmailSubFolder();
     					String subarr[]=mysubfdr.split(";");
    					String spath1=arr[i];
    					for(int j=0;j < subarr.length;j++)
    					{
    						prm=arr[i]+"."+subarr[j];
    						int flgsub=Integer.parseInt(subarr[j+1]);
    						
    						if(flgsub>0)
    						{
    							String hm_val2="&nbsp;&nbsp;&nbsp;&nbsp;"+subarr[j];
    		     				hm_fldr.put(hm_i, hm_val2);
    		 					hm_fldr_path.put(hm_i, prm);
    		 					hm_i++;
    							
    							GetWebmailSubFolderResponse wsfr1=webmailClient.getWebmailSubFolderRequest(host, id, pass, spath1+"."+subarr[j]);
    							String mysubfdr1=wsfr1.getGetWebmailSubFolder();
    							String subarr1[]=mysubfdr1.split(";");
    							String spath2=subarr[j];
    							for(int k=0;k < subarr1.length;k++)
    							{
    								prm=arr[i]+"."+subarr[j]+"."+subarr1[k];
    								
        							String hm_val1="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+subarr1[k];
        		     				hm_fldr.put(hm_i, hm_val1);
        		 					hm_fldr_path.put(hm_i, prm);
        		 					hm_i++;
    								
        							
        							k=k+3;
        							
    							}
    						}
    						else
    						{
    							String hm_val2="&nbsp;&nbsp;&nbsp;&nbsp;"+subarr[j];
    		     				hm_fldr.put(hm_i, hm_val2);
    		 					hm_fldr_path.put(hm_i, prm);
    		 					hm_i++;
    							
    						
    						}
    						
    						j=j+3;
    					}
    					}
     				else
     				{
     					String hm_val=arr[i];
     					hm_fldr.put(hm_i, hm_val);
     					hm_fldr_path.put(hm_i, prm);
     					hm_i++;
     					
     				}
     				}
     				
     				i=i+3;
     				
     				
     			}
     			
     		
             %>
            
                    <div class="form">
                        <form action="" method="get">
                            <div class="to">
                                <div class="name search_text">Search</div>
                                <a ><div >
 <select id="parent_fldr" name="parent_fldr" style="width: 227px;
height: 32px;
border: 1px solid #ccc !important;
padding-left: 3px;">
                    
                     <%
                     Set set = hm_fldr.entrySet();
                     Set set1 = hm_fldr_path.entrySet();
                   
                     // Get an iterator
                     Iterator i = set.iterator();
                     Iterator j = set1.iterator();
                     // Display elements
                     while(i.hasNext() && j.hasNext()) {
                        Map.Entry me = (Map.Entry)i.next();
                        Map.Entry me1 = (Map.Entry)j.next();
                        %>
                        <option value="<%=me1.getValue() %>"> <%=me.getValue() %></option>
                        <%
                     }
                     %>
                         <!--  <option> TEST 1</option>
                          <option> TEST 2</option>
                          <option> TEST 3</option>
                          <option> TEST 4</option>
                          <option> TEST 5</option>
                          <option> TEST 6</option>
                          <option> TEST 7</option>
                      -->
                     </select>
								</div></a>
                                
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
                                 <!-- <div class="check check_upper">
                                    <input name="" type="checkbox" value="">
                                    <span>Don't include chats</span></div> -->
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
                </div>
                 --%>
           
                
                <!-----------/// USER INFORMATION BOX STARED HERE --------->
                <div class="user_information arrow_box">

                    <!----------/// TOP SECTION STARED HERE ------------>
                    <div class="user_top">
                        <a href="#">
                       
                            <img height='96px' width='96px' src="<%=path_img %>" onerror="getAltImage(this.id)" id="<%=path_img_id %>" />
                            <div class="change_images">Change photo</div>
                        </a>

                        <div class="left_top">
                            <%=fname %>
                            <span><%=mailid %></span>
                            <div class="clear_2"></div>
                            <div class="progress_bar">
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
                                 <div class="clear"></div>
                            </div>
                               <div style="width: 170px;">
                                <!-- <img src="images/color.png" /> -->
                                <table><tr>
                              <td style="padding-right: 2px;" width="20px"> <a style="cursor: pointer;" name="#DC4FAD" onclick="changeHBG(this.name)"> <img style="width:20px !important; height: 20px;"  src="images/bg1.png" /> </a></td>
                               <td style="padding-right: 2px;" width="20px"><a style="cursor: pointer;" name="#AC193D" onclick="changeHBG(this.name)"> <img style="width:20px !important; height: 20px;" src="images/bg2.png" /> </a></td>
                               <td style="padding-right: 2px;" width="20px"><a style="cursor: pointer;" name="#D24726" onclick="changeHBG(this.name)"> <img style="width:20px !important; height: 20px;" src="images/bg3.png" /> </a></td>
                               <td style="padding-right: 2px;" width="20px"><a style="cursor: pointer;" name="#FF8F32" onclick="changeHBG(this.name)"> <img style="width:20px !important; height: 20px;" src="images/bg4.png" /> </a></td>
                               <td style="padding-right: 2px;" width="20px"><a style="cursor: pointer;" name="#82BA00" onclick="changeHBG(this.name)"> <img style="width:20px !important; height: 20px;" src="images/bg5.png" /> </a></td>
                               <td style="padding-right: 2px;" width="20px"><a style="cursor: pointer;" name="#03B3B2" onclick="changeHBG(this.name)"> <img style="width:20px !important; height: 20px;" src="images/bg6.png" /> </a></td>
                               <td style="padding-right: 2px;" width="20px"><a style="cursor: pointer;" name="#5DB2FF" onclick="changeHBG(this.name)"> <img style="width:20px !important; height: 20px;" src="images/bg7.png" /> </a></td>
                               <td width="20px"><a style="cursor: pointer;" name="#8C0095" onclick="changeHBG(this.name)"> <img style="width:20px !important; height: 20px;" src="images/bg8.png" /> </a></td>
                               </tr></table>
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
<%
//String QutPer=  request.getAttribute("QuotaPer").toString();
%>
                                Profile

                            </div>
                        </a>




                    </div>

                    <!------------/// BOTTOM SECTION END HERE -------------->


<script>

// The instanceReady event is fired, when an instance of CKEditor has finished
// its initialization.

	CKEDITOR.editorConfig = function( config )
	{
	    // Define changes to default configuration here. For example:
	    // config.language = 'fr';
	    // config.uiColor = '#AADC6E';
	    config.height = '800px';
	    config.removeButtons = 'About';
	};
	CKEDITOR.on( 'instanceReady', function( ev ) {
		// Show the editor name and description in the browser status bar.
		document.getElementById( 'eMessage' ).innerHTML = 'Instance <code>' + ev.editor.name + '<\/code> loaded.';
	
		// Show this sample buttons.
		document.getElementById( 'eButtons' ).style.display = 'block'; 
	});
	
	function InsertHTML() {
		// Get the editor instance that we want to interact with.
		var editor = CKEDITOR.instances.editor1;
		var value = document.getElementById( 'htmlArea' ).value;
	
		// Check the active editing mode.
		if ( editor.mode == 'wysiwyg' )
		{
			// Insert HTML code.
			// http://docs.ckeditor.com/#!/api/CKEDITOR.editor-method-insertHtml
			editor.insertHtml( value );
		}
		else
			alert( 'You must be in WYSIWYG mode!' );
	}
	
	function InsertText() {
		// Get the editor instance that we want to interact with.
		var editor = CKEDITOR.instances.editor1;
		var value = document.getElementById( 'txtArea' ).value;
	
		// Check the active editing mode.
		if ( editor.mode == 'wysiwyg' )
		{
			// Insert as plain text.
			// http://docs.ckeditor.com/#!/api/CKEDITOR.editor-method-insertText
			editor.insertText( value );
		}
		else
			alert( 'You must be in WYSIWYG mode!' );
	}
	
	function SetContents() {
		// Get the editor instance that we want to interact with.
		var editor = CKEDITOR.instances.editor1;
		var value = document.getElementById( 'htmlArea' ).value;
	
		// Set editor contents (replace current contents).
		// http://docs.ckeditor.com/#!/api/CKEDITOR.editor-method-setData
		editor.setData( value );
	}
	
	function GetContents() {
		// Get the editor instance that you want to interact with.
		var editor = CKEDITOR.instances.editor1;
	
		// Get editor contents
		// http://docs.ckeditor.com/#!/api/CKEDITOR.editor-method-getData
		alert( editor.getData() );
	}
	
	function ExecuteCommand( commandName ) {
		// Get the editor instance that we want to interact with.
		var editor = CKEDITOR.instances.editor1;
	
		// Check the active editing mode.
		if ( editor.mode == 'wysiwyg' )
		{
			// Execute the command.
			// http://docs.ckeditor.com/#!/api/CKEDITOR.editor-method-execCommand
			editor.execCommand( commandName );
		}
		else
			alert( 'You must be in WYSIWYG mode!' );
	}
	
	function CheckDirty() {
		// Get the editor instance that we want to interact with.
		var editor = CKEDITOR.instances.editor1;
		// Checks whether the current editor contents present changes when compared
		// to the contents loaded into the editor at startup
		// http://docs.ckeditor.com/#!/api/CKEDITOR.editor-method-checkDirty
		alert( editor.checkDirty() );
	}
	
	function ResetDirty() {
		// Get the editor instance that we want to interact with.
		var editor = CKEDITOR.instances.editor1;
		// Resets the "dirty state" of the editor (see CheckDirty())
		// http://docs.ckeditor.com/#!/api/CKEDITOR.editor-method-resetDirty
		editor.resetDirty();
		alert( 'The "IsDirty" status has been reset' );
	}
	
	function Focus() {
		CKEDITOR.instances.editor1.focus();
	}
	
	function onFocus() {
		document.getElementById( 'eMessage' ).innerHTML = '<b>' + this.name + ' is focused </b>';
	}
	
	function onBlur() {
		document.getElementById( 'eMessage' ).innerHTML = this.name + ' lost focus';
	}


	
			// Replace the <textarea id="editor1"> with an CKEditor instance.
			CKEDITOR.replace( 'editor1',
			 {
				 	//toolbar : 'Basic',uiColor : '#f00',
				 
				on: {
				
					focus: onFocus,
					blur: onBlur,

					// Check for availability of corresponding plugins.
					pluginsLoaded: function( evt ) {
						var doc = CKEDITOR.document, ed = evt.editor;
						if ( !ed.getCommand( 'bold' ) )
							doc.getById( 'exec-bold' ).hide();
						if ( !ed.getCommand( 'link' ) )
							doc.getById( 'exec-link' ).hide();
					}
				}
			});

		</script>
            <!------------/// CK EDITOR END HERE ------->
            


                </div>