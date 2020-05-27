<%@page import="webmail.bean.MailAccSetting"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page
	import="java.security.Principal,webmail.webservice.client.WebmailClient,webmail.wsdl.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<% HttpSession head_hs=request.getSession(); %>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/contact_css.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.jscrollpane.css" />
<link href="setting/jquery.datepick.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="css/tag.css" />
<link href='css/more_product.css' rel='stylesheet' type='text/css' />
<link href='css/compose_new.css' rel='stylesheet' type='text/css' />
<link type="text/css" rel="stylesheet" href="css/jquery-ui.css" />
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon.ico" />
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link type="text/css" rel="stylesheet" href="css/blue.css" />
<link type="text/css" rel="stylesheet" href="css/main_css.css" />
<link href='css/setting.css' rel='stylesheet' type='text/css' />
<link href='css/jquery.splitter-bottom.css' rel='stylesheet'	type='text/css' />
<link href='css/new_forward.css' rel='stylesheet' type='text/css' />
<link href='css/tooltip.css' rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="css/autocomplete.css" />
<link rel="stylesheet" type="text/css" href="css/buttons.css" />
<link href="setting/account_new.css" rel="stylesheet" type="text/css" />
<link href="setting/setting_css.css" rel="stylesheet" type="text/css" />
<link href="setting/setting.css" rel="stylesheet" type="text/css" />
<link href="setting/tabcontent.css" rel="stylesheet" type="text/css" />
<link href="setting/account.css" rel="stylesheet" type="text/css" />
<link href="css/chat.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/offline-theme-chrome.css" />
<link rel="stylesheet" href="css/offline-language-english.css" />

<link type="text/css" href="css/jquery.ui.chatbox.css" rel="stylesheet" />
<link type="text/css" href="css/jquery-ui_chat.css" rel="stylesheet" />

<!-- <link rel="stylesheet" href="css/jquery.mCustomScrollbar.css"> -->
<style>

div#scayt_banner {
    display: none !important;
}

div#adsBlock {
    display: none !important;
} 


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
<style>

.vsplitbar {
	width: 5px;
	background: #aaa;
}

#spliter2 .a {
	background-color: #2d2d2d;
}

#spliter2 .b {
	background-color: #2d002d;
}



#x {
	background-color: #EFBD73;
}

#y {
	background-color: #EF3e32;
}

#bar {
	background-color: #BEE927;
}

.vsplitbar {
	width: 2px;
	background: #f5f5f5;
	margin-top: -20px;
	cursor: col-resize !important;
/* 	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc; */
}
</style>

<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/splitter.js"></script>
<script src="js/sytem-script.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/jquery.splitter-0.14.0.js"></script>
<script src="js/more_product.js"></script>
<script src="js/compose_event.js"></script> 
<script src="js/new_forword.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script src="ckeditor/ckeditor.js"></script>
<script src="ckeditor/adapters/jquery.js"></script>
<script src="js/tag.js"></script>
<script src="setting/jquery.plugin.js"></script>
<script src="setting/jquery.datepick.js"></script>
 <script src="js/event.js" type="text/javascript"></script> 
<script src="js/tab_event.js" type="text/javascript"></script>
<script src='js/jquery.dcjqaccordion.2.7.min.js'></script>
<script src="js/jquery.jscrollpane.min.js"></script>
<script src="js/jquery.noty.packaged.js"></script>
<script type="text/javascript" src="js/jquery.ui.chatbox.js"></script>
<script src="js/tabcontent.js" type="text/javascript"></script>
<script src="js/briefcase.js" type="application/javascript"></script>
<link href='css/briefcase.css' rel='stylesheet' type='text/css' />
<script src="cmenu/jquery.contextMenu.js" type="text/javascript"></script>
<script src="cmenu/jquery.ui.position.js" type="text/javascript"></script>
<link href="cmenu/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
<script src="js/offline.min.js" type="text/javascript"></script>
<script src="js/contact_js.js"></script>
<script type="text/javascript">
/* try
{
var run = function(){
  var req = new XMLHttpRequest();
  req.timeout = 5000;
  req.open('GET', '${pageContext.request.contextPath}/offlinejs', true);
  req.send();
}

setInterval(run, 5000);
}
catch (e) {
	// TODO: handle exception
} */
</script>

       <!------------/// CK EDITOR END HERE ------->
            
   
      <!--  <script type="text/javascript" src="js_file_upload/js/jquery.knob.js"></script>
         <script type="text/javascript" src="js_file_upload/js/jquery.knob1.js"></script> -->
	
		<!-- <script type="text/javascript" src="js_file_upload/js/jquery.ui.widget.js" ></script>
		<script type="text/javascript" src="js_file_upload/js/jquery.iframe-transport.js"></script> -->
		<script type="text/javascript" src="js_file_upload/js/jquery.fileupload.js"></script>
		

		<!-- <script type="text/javascript" src="js_file_upload/js/script.js"></script>  -->
        
        <!----------/// FILE UPLOAD  END HERE ------------------->  

 <!-- <script type="text/javascript" src="js_file_upload/js/script.js"></script> --> 

	<!-- <script src="js/jquery.mCustomScrollbar.concat.min.js"></script> -->
	<!-- <script>
		(function($){
			$(window).load(function(){
				
				$("body").mCustomScrollbar({
					theme:"minimal"
				});
				
			});
		})(jQuery);
	</script> -->






<script>

function chkNP()
{
	alert("nps");
}

$(document).ready(function(){ 	
	
	/*upload image popup*/
	

	
 	// noty - notifications  
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
 	
 	 /* function generate_conf_sendmail(type,id) {
 	   	
 	         var n = noty({
 	           text        : type,
 	           type        : type,
 	           theme       : 'relax',
 	           dismissQueue: false,
 	           layout      : 'center',
 	           theme       : 'defaultTheme',
 	           buttons     : (type != 'confirm') ? false : [
 	               {addClass: 'btn btn-primary', text: 'Ok', onClick: function ($noty) {
 	   				
 	                 $noty.close();
 	                 
 	                 
 	                $('#'+id).val("");
	                   
	                 //  $('.web_dialog_overlay').hide(); 
	                 var n1 = noty({
	                       text        : 'You clicked "Ok" button',
	                       type        : 'alert',
	                       dismissQueue: false,
	                       layout      : 'topCenter',
	                       theme       : 'defaultTheme'
	                   });
	                  setTimeout(function () { $.noty.close(n1.options.id); }, 2000);
 	                // alert(foldernm);
 	                
 	               }
 	               },
 	               {addClass: 'btn btn-danger', text: 'Cancel', onClick: function ($noty) {
 	                   $noty.close();
 	                  $('#'+id).val("true").trigger('change');
  	               //  $('.web_dialog_overlay').hide(); 
 	               }
 	               }
 	           ]
 	       });
 	      
 	           
 	           //console.log(type + ' - ' + n.options.id);
 	           return n; 
 	            
 	       }
 	 
 	 $('#hid_confirm_box').change( function() {  
     	   //alert($("#hid_del").attr("data-id")); 
     	var r =$('#hid_confirm_box').val();
     	
     //	alert("change r="+r);
    // 	alert("change fldnm="+fldnm);
     	if (r == "true") {
  		
  	 alert("hii"); 
  	}
     }); */
      	

 	
     
     function npcall() {
     	/* var success = generate('alert');
     	$.noty.setText(success.options.id, 'I\'m closing now!'); // same as alert.setText('Text Override')
     	setTimeout(function () {
     	            $.noty.close(success.options.id);
     	        }, 3000);    */
    	 var confirm = generate_conf_sendmail('confirm','hid_confirm_box');
     	//$.noty.setText(confirm.options.id, 'The Subject field is empty. Would you like to enter Subject?');
     }
     
     $( "a[name*='userName']" ).click(function(){
 		npcall();
 	});
    // noty - notifications ends 
     
    // autocomplete and datepicker  
 	$('.popupDatepicker').datepick();
	$("#to_id").autocomplete("${pageContext.request.contextPath}/autocomp_new");
	$("#cc_id").autocomplete("${pageContext.request.contextPath}/autocomp_new");
	$("#bcc_id").autocomplete("${pageContext.request.contextPath}/autocomp_new");
	$("#to_search").autocomplete("${pageContext.request.contextPath}/autocomp_new");
	$("#from_search").autocomplete("${pageContext.request.contextPath}/autocomp_new");
	$("#quick_search").autocomplete("${pageContext.request.contextPath}/autocomp_new");
	/* $("#to_search").autocomplete("${pageContext.request.contextPath}/autocomp");
	$("#from_search").autocomplete("${pageContext.request.contextPath}/autocomp");
	$("#quick_search").autocomplete("${pageContext.request.contextPath}/autocomp"); */
	$("#share_input").autocomplete("${pageContext.request.contextPath}/autocomp");
	// autocomplete and datepicker ends
	
	// accordion 
	$('#accordion-3').dcAccordion({
        eventType: 'click',
        autoClose: false,
        saveState: false,
        disableLink: false,
        showCount: false,
        speed: 'slow'
    });
	// accordion ends
	
	// TODO: webkit specific code to review
	if (!$.browser.webkit) {
        $('.container').jScrollPane();
    }
	// review above code
	


	/* //$('#hid_to_id').val().trigger('change');
	$('#hid_to_id').change( function() {  
		 alert("hii to");
		 mailSaveInDraft();
	});

	//$('#hid_cc_id').val().trigger('change');
	$('#hid_cc_id').change( function() {
		 alert("hii cc");
		 mailSaveInDraft();
	});

	//$('#hid_bcc_id').val().trigger('change');
	$('#hid_bcc_id').change( function() {  
		 alert("hii bcc");
		 mailSaveInDraft();
	}); */

	

			
	
 });
 
 //folder tree

 
 
 
$(document).on('click','.child_level',function(){
$(this).parent().parent().children('ul.level').slideToggle( "slow");
$(this).toggleClass('minus');
});

$(document).on('click','.left_partent_tree >span',function(){
	$(".active_left_tree").css("border-left","3px solid #fff");
	$('div.active_left_tree').removeClass('active_left_tree');
	$(this).parent().addClass('active_left_tree');
});

//folder tree ends
 
  CKEDITOR.env.isCompatible = true;


// ckeditor config replacements	 
if(CKEDITOR.instances["sig"] != undefined)
{
       CKEDITOR.replace( 'sig', {
       	toolbar: [		// Defines toolbar group without name.
       	{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline' ] },
       	{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Scayt' ] },
       	{ name: 'paragraph', groups: [  'blocks', 'align', 'bidi' ], items: [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
       	{ name: 'links', items: [ 'Link', 'Unlink', '' ] },
       	{ name: 'insert', items: [ 'Image',  'Smiley'  ] },
       	{ name: 'styles', items: [ 'Font', 'FontSize' ] },
       	{ name: 'colors', items: [ 'TextColor', 'BGColor' ] }
       	//['newplugin']
       	]
       });
}
      

if(CKEDITOR.instances["editor1"] != undefined)
{
	
	
	CKEDITOR.replace( 'editor1', {	  
		
		
		toolbar: [		// Defines toolbar group without name.																			
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Scayt' ] },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [  'NumberedList', 'BulletedList','JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
		{ name: 'links', items: [ 'Link', 'Unlink', '' ] },
		{ name: 'insert', items: [ 'Image',  'Table',  'Smiley', 'SpecialChar',  ] },
		{ name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
		{ name: 'colors', items: [ 'TextColor', 'BGColor' ] }
		//['newplugin']
		],
		height: $(window).height()-355,
		enterMode: CKEDITOR.ENTER_BR,
		allowedContent : true
		
		
	});
	
	
	
	 CKEDITOR.config.contentsCss = '${pageContext.request.contextPath}/css/mycss.css' ; 
	
	
	CKEDITOR.on('dialogDefinition', function( ev ) {
		  var dialogName = ev.data.name;
		  var dialogDefinition = ev.data.definition;

		  if(dialogName === 'table') {
		    var infoTab = dialogDefinition.getContents('info');
		  
		    var border = infoTab.get('txtBorder');
		    border['default'] = "1";
		  }
		});
	
	
	

	/* CKEDITOR.on('instanceCreated', function(e) {
	    if (e.editor.name === 'editor1') { //editorId is the id of the textarea
	        e.editor.document.on('keyup', function(evt) {
	        	 alert('hii');
	        });
	    }
	}); */
	
	
	/* CKEDITOR.on('editor1', function(e) {
	    e.editor.on('contentDom', function() {
	        e.editor.document.on('keyup', function(event) {
	            alert('hii');
	        }
	    );
	});
	});  */
}



//ckeditor config replacements ends
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

function mailSaveInDraftNew1(val, hid_id) {
	
	var hid_val=$('#'+hid_id).val()
			
		if(hid_val!=null && hid_val!="")
			{
			if(window.draftProgress!=false)
			{
			mailSaveInDraft();
			}
			else
				{
				 setTimeout(mailSaveInDraftNew1(val, hid_id), 400);
				} 
			}
		
		
		}




function mailSaveInDraftNew(val, hid_id) {
	//alert(val)
	
	
	
			
		if(val!=null && val!="")
			{
			if(window.draftProgress!=false)
			{
			mailSaveInDraft();
			}
			else
				{
				 setTimeout(mailSaveInDraftNew(val, hid_id), 300);
				} 
			}
		
		
		}


function mailSaveInDraftSub(val) {
	if(val!=null && val!="")
		{
		if(window.draftProgress!=false)
		{
		mailSaveInDraft();
		}
		else
			{
			 setTimeout(mailSaveInDraftSub(val), 300);
			}
		}
}

function mailSaveInDraftFource() {
	//alert(window.draftProgress);
//	mailSaveInDraft();
	
	 
	 	var to=document.getElementById("hid_to_id").value;
		
		var uid=document.getElementById("hid_to_mail_uid").value;
		
		var cc=document.getElementById("hid_cc_id").value;
		
		
		var bcc=document.getElementById("hid_bcc_id").value;
		
		
		var sub=document.getElementById("sub_id").value;
		
		var cntt ="";
		var texttype="html";
		if($('#hid_editor_type').val()!="plain")
		{
		cntt = CKEDITOR.instances['editor1'].getData();
		}
		else
			{
			cntt = $('#editor1').val();
			texttype="plain";
			}
		
	
		
		if(!((to==null || to=="") && (cc==null || cc=="") && (bcc==null || bcc=="") && (sub==null || sub=="") && (cntt==null || cntt=="")))
		{
			if(window.draftProgress!=false)
			{
			window.draftProgress=false;
			$.ajax({
		         type: "POST",
		         url: "${pageContext.request.contextPath}/mailSaveInDraft",
		         data: {'uid': uid,'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt ,"texttype": texttype},
		        /*  contentType: "application/json", */
		         success: function (data) {
		             
		             window.draftProgress=true;
		          // alert(data);
		          if(data=="<$expire$>")
	{
//	location.href ="${pageContext.request.contextPath}/inbox";
		        	  window.close();
		}
		            var msg="Message save to Draft.";
		            if(data== "" || data==null || data=="null")
		            	{
		            	msg="An Error Occurred!";
		            	}
    				
	 				 if(!(data== "" || data==null || data=="null"))
		            	{
	 					var success = generate('alert');
		 				$.noty.setText(success.options.id, msg);
		 				setTimeout(function () {  $.noty.close(success.options.id); }, 3000);
	 					/*  $("#hid_to_mail_uid").val(data);
		           		 getDraftMailCount(); */
		 				window.close();
		            	}
		            
		            
		         }
		     });
		}
		}
}

function mailSaveInDraft() {
//alert("hi")
	var to=document.getElementById("hid_to_id").value;
	
	var uid=document.getElementById("hid_to_mail_uid").value;
	
	var cc=document.getElementById("hid_cc_id").value;
	
	
	var bcc=document.getElementById("hid_bcc_id").value;
	
	
	var sub=document.getElementById("sub_id").value;
	var texttype="html";
	var cntt ="";
	if($('#hid_editor_type').val()!="plain")
	{
	cntt = CKEDITOR.instances['editor1'].getData();
	}
	else
		{
		cntt = $('#editor1').val();
		texttype="plain";
		}
	
	if(!((to==null || to=="") && (cc==null || cc=="") && (bcc==null || bcc=="") && (sub==null || sub=="") && (cntt==null || cntt=="")))
	{
		window.draftProgress=false;
		$.ajax({
	         type: "POST",
	         url: "${pageContext.request.contextPath}/mailSaveInDraft",
	         data: {'uid': uid,'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt, 'texttype': texttype },
	       /*   contentType: "application/json", */
	         success: function (data) {
	        	 if(data=="<$expire$>")
	        		{
	        		location.href ="${pageContext.request.contextPath}/inbox";
	        			}
	        	 $("#hid_to_mail_uid").val(data);
	             window.draftProgress=true;
	           // alert(data);
	            
	            getDraftMailCount();
	             
	            
	            
	         }
	     });
	}
	
}


function getDraftMailCount() {
	$.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/getDraftMailCount",
        data: { },
        success: function (data) {
        	if(data=="<$expire$>")
        	{
        	location.href ="${pageContext.request.contextPath}/inbox";
        		}
        	$("#draft_mail_cnt").html(data);
              	            
        }
    });
}

function mailSaveInDraftAtt() {
	
	var to=document.getElementById("hid_to_id").value;
	
	var uid=document.getElementById("hid_to_mail_uid").value;
	
	var cc=document.getElementById("hid_cc_id").value;
	
	
	var bcc=document.getElementById("hid_bcc_id").value;
	
	
	var sub=document.getElementById("sub_id").value;
	var texttype="html";
	var cntt ="";
	if($('#hid_editor_type').val()!="plain")
	{
	cntt = CKEDITOR.instances['editor1'].getData();
	}
	else
		{
		cntt = $('#editor1').val();
		texttype="plain";
		}
	
	if(!window.draftProgress)
		window.draftProgress=true;
	
	//alert(window.draftProgress);
	if(window.draftProgress!=false)
		{
	window.draftProgress=false;
		$.ajax({
	         type: "POST",
	         url: "${pageContext.request.contextPath}/mailSaveInDraft",
	         data: {'uid': uid,'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt, 'texttype': texttype },
	     /*     contentType: "application/json", */
	         success: function (data) {
	             $("#hid_to_mail_uid").val(data);
	             if(data=="<$expire$>")
	         	{
	         	location.href ="${pageContext.request.contextPath}/inbox";
	         		}
	             //alert(data);
	             window.draftProgress=true;
	             getDraftMailCount();
	         }
	     });
	
		}
	else
	{
	 setTimeout( mailSaveInDraftAtt(), 300);
	} 
}


function mailSend() {
	
	 
	var to=document.getElementById("hid_to_id").value;
		
	var cc=document.getElementById("hid_cc_id").value;
	
	
	var bcc=document.getElementById("hid_bcc_id").value;
	
	var ml_priority=document.getElementById("hid_priority").value;
	 
	var read_rec= document.getElementById("hid_read_rec").value;
	//alert(read_rec)
	var sub=document.getElementById("sub_id").value;
	var mailuid=$("#hid_mailuid").val();
	var mailfolder=$("#hid_mailfolder").val();
	var mailtype=$("#hid_mailtype").val();
	
	var cntt ="";
	var texttype="html";
	if($('#hid_editor_type').val()!="plain")
	{
	cntt = CKEDITOR.instances['editor1'].getData();
	}
	else
		{
		cntt=$('#editor1').val();
		texttype="plain";
		}
	var sts=false;
	if((to==null || to=="") && (cc==null || cc=="") && (bcc==null || bcc==""))
		{
		var msg="Please enter at least one recipient";
        var success = generate('alert');
		 $.noty.setText(success.options.id, msg);
		 setTimeout(function () {  $.noty.close(success.options.id); }, 7000);
		}
	else
		{
	if(sub==null || sub=="")
		{
		$('.web_dialog_overlay').show(); 
		//sts = confirm("The Subject field is empty. Would you like to enter Subject?");
		var confirm = generate_conf_sendmail('confirm','hid_confirm_box');
    	$.noty.setText(confirm.options.id, 'The Subject field is empty. Would you like to enter Subject?');
			
		}
	else if(cntt==null || cntt=="")
		{
		$('.web_dialog_overlay').show(); 
		//sts = confirm("The Subject field is empty. Would you like to enter Subject?");
		var confirm = generate_conf_sendmail('confirm','hid_confirm_box');
    	$.noty.setText(confirm.options.id, 'Message body is empty. Would you like to enter message body?');
			
		}
	/* else if((cntt.indexOf("PFA") > -1 || cntt.indexOf("Attach") > -1) && $('#upload ul li').length==0)
	{
	$('.web_dialog_overlay').show(); 
	//sts = confirm("The Subject field is empty. Would you like to enter Subject?");
	var confirm = generate_conf_sendmail('confirm','hid_confirm_box');
	$.noty.setText(confirm.options.id, 'Message body PFA/Attach. Would you like to attach any document?');
		
	} */
	else		
		{
	if(sts==false)
		{
		if( window.uploadflag=="false")
			{
			 var success = generate('alert');
			  $.noty.setText(success.options.id, "Attachment is uploading. Please wait and send again.");
			  setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
			}
		else
		{
		$("#hid_active_compose").val("");
		var olddraftuid=$("#hid_to_draft_mail_uid").val();
		$("#hid_to_draft_mail_uid").val("");
		//alert("draft uid="+olddraftuid);
		var draftuid= $("#hid_to_mail_uid").val();
	
		 if(olddraftuid!=null && olddraftuid!="")
			 {
			 draftuid=draftuid+"-"+olddraftuid;
			 }
		document.getElementById('mail_sending').style.display= 'block';
		var hid_msg_ref=document.getElementById("hid_msg_ref").value;
		var hid_old_msg_id=document.getElementById("hid_old_msg_id").value;
	    var hid_dsn_status=$("#hid_dsn_status").val();
		$.ajax({
	         type: "POST",
	         url: "${pageContext.request.contextPath}/sendComposeMail",
	        data: {'hid_dsn_status': hid_dsn_status, 'hid_old_msg_id': hid_old_msg_id, 'hid_msg_ref': hid_msg_ref,'draftuid': draftuid, 'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt, 'read_rec': read_rec, 'ml_priority': ml_priority, 'texttype':texttype },
	        /*  contentType: "application/json", */
	         success: function (data) {
	            // $("#fileSystem").html(data);
	              document.getElementById('mail_sending').style.display= 'none';
	              if(data=="<$expire$>")
	          	{
	          	location.href ="${pageContext.request.contextPath}/inbox";
	          		}

	           if(data!="true")
	        	  {
	        	  var msg=data;
	        	  var success = generate('alert');
				  $.noty.setText(success.options.id, msg);
				  setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
	        	   }
	           else
	        	   {
	          
	           // document.getElementById('mail_sent').style.display= 'block';
	            //alert(data);
	           clearCompose();
	          //  document.getElementById('div_for_compose').style.display= 'none';
	          //  document.getElementById('div_for_inbox').style.display= 'block';
	           // setTimeout( "jQuery('#mail_sent').hide();",3000 );
	           var msg="Message sent successfully.";
	           
	            var success = generate('alert');
				 $.noty.setText(success.options.id, msg);
				 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
				 
				 $.ajax({
			         type: "POST",
			         url: "${pageContext.request.contextPath}/sendComposeMailPostAjax",
			        data: { 'hid_dsn_status': hid_dsn_status, 'hid_old_msg_id': hid_old_msg_id, 'hid_msg_ref': hid_msg_ref,'draftuid': draftuid, 'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt, 'read_rec': read_rec, 'ml_priority': ml_priority, 'texttype':texttype  },
			        /*  contentType: "application/json", */
			         success: function (data) {
			        	 
			        		var draftuid1= $("#hid_to_mail_uid").val();
							 $("#hid_to_mail_uid").val("");
							
							if(draftuid1!="" && draftuid1!=draftuid)
								{
								
								 var requestPage="${pageContext.request.contextPath}/deleteDraftMail";
									jQuery.get(requestPage,
								                            {
								                    'uid' : draftuid1
								            },
								                    function( data ) {
								            	
								            	window.close();
								            	if(data=="<$expire$>")
								            	{
								            	//location.href ="${pageContext.request.contextPath}/inbox";
								            		
								            		}
								            /* 	if($("#search_pagi").css('display')=='none')
									  			{
										  		var fdrname=document.getElementById('hid_active_fldr').value;
												if(fdrname=="Drafts" || fdrname=="drafts")
										  			getWebmailInbox( fdrname);
									  			}
												 getDraftMailCount();
								            	 */
								            }
								            );
								}
							else
								{
						/* 	if($("#search_pagi").css('display')=='none')
				  			{
					  		var fdrname=document.getElementById('hid_active_fldr').value;
							if(fdrname=="Drafts" || fdrname=="drafts")
					  			getWebmailInbox( fdrname);
				  			}
							 getDraftMailCount(); */
								}
			        	 
			         }
				 });
				 
				/*  if(mailuid!=null && mailuid!="" && mailfolder!=null && mailfolder!="" && mailtype!=null && mailtype!="")
					 {
				 $.ajax({
			         type: "POST",
			         url: "${pageContext.request.contextPath}/setReplyFarwordFlag",
			        data: { 'mailuid':mailuid, 'mailfolder':mailfolder, 'mailtype':mailtype },
			       
			         success: function (data) {
			        	 if(data=="true")
			        		 {
			        		 var idd="repfor_"+mailuid;
			        		 var tit=$("#"+idd).attr("title");
			        		 var imgsrc="";
			        		 if(tit=="This mail has been replied and forwarded")
			        			 {
			        			 
			        			 }
			        		 else
			        			 {
				        		 if(mailtype=="reply" &&  tit=="This mail has been forwarded")
				        			 {
				        			 imgsrc="<img src='images/ml_rep_for.png'>";
				        			 $("#"+idd).prop('title', 'This mail has been replied and forwarded');
				        			 $("#"+idd).html(imgsrc);
				        			 $("#"+idd).show();
				        			 }
				        		 else if(mailtype=="reply" && tit!="This mail has been replied")
				        			 {
				        			 imgsrc="<img src='images/ml_rep.png'>";
				        			 $("#"+idd).prop('title', 'This mail has been replied');
				        			 $("#"+idd).html(imgsrc);
				        			 $("#"+idd).show();
				        			 }
				        		 
					        		 if(mailtype=="forward" &&  tit=="This mail has been replied")
				        			 {
				        			 imgsrc="<img src='images/ml_rep_for.png'>";
				        			 $("#"+idd).prop('title', 'This mail has been replied and forwarded');
				        			 $("#"+idd).html(imgsrc);
				        			 $("#"+idd).show();
				        			 }
				        		 else if(mailtype=="forward" && tit!="This mail has been forwarded")
				        			 {
				        			 imgsrc="<img src='images/ml_for.png'>";
				        			 $("#"+idd).prop('title', 'This mail has been forwarded');
				        			 $("#"+idd).html(imgsrc);
				        			 $("#"+idd).show();
				        			 }
			        			 }
			        		 }
			         }
				 });
					 } */
				 
				 $.ajax({
			         type: "POST",
			         url: "${pageContext.request.contextPath}/newAtuoCollect",
			        data: { 'to':to, 'cc':cc, 'bcc':bcc },
			        /*  contentType: "application/json", */
			         success: function (data) {
			        	 
			         }
				 });
				 
				/* var fdrname=document.getElementById('hid_active_fldr').value;
					if( fdrname=="Drafts" || fdrname=="drafts")
					{
				 if(olddraftuid!=null && olddraftuid!="")
					 {
				 var elem = document.getElementById("div_" + olddraftuid);
					elem.parentNode.removeChild(elem);
					 }
					} */
				
					
					 
	        	   }
	         }
	     });
		}
		}
		}
		}
}



</script>

<script type="text/javascript">

function getWebmailfldr(){
	/* var x = document.cookie;
	//alert(x);
	if(x!=null && x!="")
		{
		//alert("BG color");
	var arr=x.split("=")
	var nm=arr[1];
	$(".header").css("background-image","none");
	$(".header").css("background-color",nm);
	$(".search_button").css("background-image","none");
	$(".search_button").css("background-color",nm);
	$(".ui-widget-header").css("background-image","none");
	$(".ui-widget-header").css("background-color",nm);
	//$(".composed_box_new").css("background-image","none");
	//$(".composed_box_new").css("background-color",nm);
			} */
	
			var bgColor=$("#hid_mail_bgColor").val();
			$("div.meter span").css("background-color",bgColor);
			$(".header").css("background-image","none");
			$(".header").css("background-color",bgColor);
			$(".search_button").css("background-image","none");
			$(".search_button").css("background-color",bgColor);
			
			
			 $(".ui-widget-header").css("background-image","none");
			$(".ui-widget-header").css("background",bgColor); 
			$(".ui-widget-header").css("border","1px solid "+bgColor);
			
			$(".chat_out").css("background-color",bgColor);
			$(".chat_sign_1_box").css("background-color",bgColor);
	
	var fdrname=document.getElementById('hid_active_fldr').value;
	/*  $.ajax({
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
     }); */
	 
	
		var requestPage="${pageContext.request.contextPath}/getWbmailfolderNew";
		jQuery.get(requestPage,
	            /*                  {
	                    'path' : folderPath
	            }, */
	                    function( data ) {
	            	 $(".folder_div_nps").html(data);
	            	 
	            	var fdrname=document.getElementById('hid_active_fldr').value;
	         		try
	         		{
	         		if(fdrname!=null && fdrname!="" && fdrname!="inbox" && fdrname!="INBOX")
	         			{
	         			var fnm=fdrname.replace(/ /g, '_');
	         			$('#fldr_INBOX').removeClass('active_left_tree');
	         			$('#fldr_INBOX').css('border-left-color','#fff');
	         			$('#fldr_'+fnm).addClass('active_left_tree');
	         			}
	         		$("#dom_css").empty();
	         	 	var domcss=document.getElementById("dom_css");
	         		var sheet = document.createElement('style')
	         		sheet.innerHTML = ".context-menu-hover {background-color: "+bgColor+" !important;}";
	         		domcss.appendChild(sheet);
	         		}
	         		catch (err) {
	         			// TODO: handle exception
	         		}
	            	             	 
	            	 $(".small_image_flag").css("color",bgColor);
	            	 $(".draft_img").css("color",bgColor);
	            	 $(".new_flag_color").css("color",bgColor);
	            	 $(".bottom_flag_red").css("color",bgColor);
	            	 $(".active_left_tree").css("border-left","3px solid ");
	            	$(".active_left_tree").css("border-left","3px solid "+bgColor);
	            	$('.left_partent_tree').hover(
      				 function(){ $(this).css('border-left', "3px solid "+$("#hid_mail_bgColor").val()) },
       				 function(){ $(this).css('border-left', "3px solid #fff") 
      					$(".active_left_tree").css("border-left","3px solid "+$("#hid_mail_bgColor").val())	 
      				 }
					);
	            	$('.row_content').hover(
	         				 function(){ $(this).css('border-left', "1px solid "+$("#hid_mail_bgColor").val()) },
	          				 function(){ $(this).css('border-left', "1px solid #fff") 
	         				 }
	   					);
	            	window.myhead="true";
	            		if(window.myinbox=="true")
							{
							document.getElementById('div_progress').style.display= 'none';
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
			
	        console.log(height);
			// LEFT VIEW CHAT
			$('#foo').height(height).split({ orientation:'horizontal', limit:50 });
			$('#foo').css('height',height);
	        $('#b').height(height / 2)-30;
			
			
	    }).trigger('resize'); 
	});

  
</script>
<!-- end bottom view splitter -->

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

function showmsg(type,msg) {
	var n = noty({
	text : msg,
	type : type,
	dismissQueue: false,
	layout : 'topCenter',
	theme : 'defaultTheme'
	});

	setTimeout(function () {
	$.noty.close(n.options.id);
	}, 4000); 
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
           if(data=="<$expire$>")
	{
	location.href ="${pageContext.request.contextPath}/inbox";
		}
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
        	if(data=="<$expire$>")
        	{
        	location.href ="${pageContext.request.contextPath}/inbox";
        		}
        	jQuery('.new_notification').hide();
        	document.getElementById('action_gif').style.display= 'none';
      
        }
    });
}
</script>

<script type="text/javascript">
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
        	$(".active_left_tree").css("border-left","3px solid "+nm);
        	$(".chat_out").css("background-color",nm);
        	$(".chat_sign_1_box").css("background-color",nm);
        	$(".small_image_flag").css("color",nm);
        	 $(".new_flag_color").css("color",nm);
        	 $(".bottom_flag_red").css("color",nm);
        	 $(".draft_img").css("color",nm);
        	 
        	 $(".ui-widget-header").css("background-image","none");
 			$(".ui-widget-header").css("background",nm); 
 			$(".ui-widget-header").css("border","1px solid "+nm);
 			
			//$('.left_partent_tree').hover( function(){ $(this).css('border-left', "3px solid "+$("#hid_mail_bgColor").val()) });
        	$('.left_partent_tree').hover(
      				 function(){ $(this).css('border-left', "3px solid "+$("#hid_mail_bgColor").val()) },
       				 function(){ $(this).css('border-left', "3px solid #fff") 
      					$(".active_left_tree").css("border-left","3px solid "+$("#hid_mail_bgColor").val())
      				 }
			);
        	$('.row_content').hover(
    				 function(){ $(this).css('border-left', "1px solid "+$("#hid_mail_bgColor").val()) },
     				 function(){ $(this).css('border-left', "1px solid #fff") 
    				 }
					);
			$(".ui-widget-header").css("background-image","none");
        	$(".ui-widget-header").css("background-color",nm);
        	try
        	{
        		$("#dom_css").empty();
         	 	var domcss=document.getElementById("dom_css");
         		var sheet = document.createElement('style')
         		sheet.innerHTML = ".context-menu-hover {background-color: "+nm+" !important;}";
         		domcss.appendChild(sheet);
        	}
        	catch (e) {
				// TODO: handle exception
			}
        }
    });  
	
}
</script>

<script type="text/javascript">
function mailAdvSearch(event) {
	var to= document.getElementById('to_search').value;
	var from= document.getElementById('from_search').value;
	var sub= document.getElementById('sub_search').value;
	var con= document.getElementById('con_search').value;
	var dt1=document.getElementById('startdate').value;
	var dt2=document.getElementById('enddate').value;
	var tagelement = document.getElementById("webmail_tag");
    var tagnm = tagelement.options[tagelement.selectedIndex].value;
    
    var stelement = document.getElementById("webmail_status");
    var stnm = stelement.options[stelement.selectedIndex].value;
    var stdt=true;
  /*   if(dt1!=null && dt1!="" && dt2!=null && dt2!="" )
    	{
    	var pattern = /^([0-9]{4})-([0-9]{2})-([0-9]{2})$/;
    	 if ( !pattern.test(dt1)) {
    		 stdt=false;
    	       var success = generate('alert');
 			  $.noty.setText(success.options.id, "Invalid date format.");
 			  setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
    	    }
    	 else if ( !pattern.test(dt2)) {
    		 stdt=false;
    	       var success = generate('alert');
 			  $.noty.setText(success.options.id, "Invalid date format.");
 			  setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
    	    }
    	}
    else if((dt1==null || dt1=="") && ( dt2==null || dt2=="" ))
    	{
    	stdt=true;
    	}
    else
    	{
    	 var success = generate('alert');
			  $.noty.setText(success.options.id, "Both dates must be filled.");
			  setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
    	stdt=false;
    	} */
	//alert(tagnm);
	if(stdt)
		{
	if((stnm!=null && stnm!="") ||(tagnm!=null && tagnm!="") || (to!=null && to!="") || (from!=null && from!="") || (sub!=null && sub!="") || (con!=null && con!="") || (dt1!=null && dt1!="" && dt2!=null && dt2!=""))
	{
		
		
		var element = document.getElementById("parent_fldr");
	    var path = element.options[element.selectedIndex].value;
		var pfldr=path;   //document.getElementById('hid_active_fldr').value
	$('.search_clear').val('');
		try
		{
		showHide(event);
		}
		catch (e) {
			var ele = document.getElementById("search_form");
		    if (ele.style.display == "block") {
		       ele.style.display = "none";
		   }
		}
		
		
	var start='0';
	//var end='15';
	var end=$("#hid_mail_list_limit").val();
	document.getElementById('hid_to_search').value=to;
	document.getElementById('hid_from_search').value=from;
	document.getElementById('hid_sub_search').value=sub;
	document.getElementById('hid_con_search').value=con;
	document.getElementById('hid_date1_search').value=dt1;
	document.getElementById('hid_date2_search').value=dt2;
	document.getElementById('hid_tag_search').value=tagnm;
	document.getElementById('hid_status_search').value=stnm;
	document.getElementById('action_gif_search').style.display= 'block';
	 try
     {
  	   $('#parent_fldr option[value=INBOX]').attr('selected','selected');
  	   $('#webmail_tag option[value=]').attr('selected','selected');
  	 $('#webmail_status option[value=]').attr('selected','selected');
     }
     catch (e) {
		// TODO: handle exception
	}
	 /* $.ajax({
	        type: "GET",
	        url: "${pageContext.request.contextPath}/advSearchMailSize",
	       data: {'to':to, 'from':from, 'sub':sub, 'dt':dt, 'con':con, 'fldrnm': pfldr},
	        contentType: "application/json",
	        success: function (data) {
	        alert(data);
	        	
	        }
	    });  */
	    
	    var pview=document.getElementById("hid_previewPane").value;
	    try
	    {
	    	noneMail();
	    	
	    }
	    catch (e) {
			// TODO: handle exception
		}
	 $.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/advSearchMail",
       data: {'to':to, 'from':from, 'sub':sub, 'dt1':dt1, 'dt2':dt2,'tagnm': tagnm, 'stnm': stnm, 'con':con, 'start':start, 'end':end, 'fldrnm': pfldr, 'pview': pview},
        contentType: "application/json",
        success: function (data) {
       //   alert(data);
      if(data=="<$expire$>")
	{
	location.href ="${pageContext.request.contextPath}/inbox";
		}
        	if(pfldr=="Sent" || pfldr=="Drafts" || pfldr=="sent" || pfldr=="drafts")
			{
			$("#div_from").html("TO");
			}
		else
			{
			$("#div_from").html("FROM");
			}
        	
        	var arr=data.split("<$nps$>");
        	  var vl=arr[0];
        	  
              var ar=vl.split("$");
              var mx=ar[0]
             // alert("mx="+mx);
             var pagi_val=ar[1];
           //alert("pagi_val="+pagi_val);
            $('#hid_open_search').val("true");
            $('#hid_active_fldr').val(pfldr);
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
             try
             {
            	if($('#div_for_setting').css('display') == "block")
         		{
         		$('#div_for_setting').css('display',"none");
         		$('#div_for_inbox').css('display',"block");
         		}
            	 if($('#div_for_compose').css('display')=='block')
         		{
         			backFromComposeNew();
         		}
             	backToMailList();
             	
             }
             catch (e) {
     			// TODO: handle exception
     		}
		$("#inb_cnt_div").html(res);
		$('.active_left_tree').css('border-left-color','#fff');
		$('.active_left_tree').removeClass('active_left_tree');
		$(".small_image_flag").css("color",$("#hid_mail_bgColor").val());
    	$(".new_flag_color").css("color",$("#hid_mail_bgColor").val());
    	$(".draft_img").css("color",$("#hid_mail_bgColor").val());
    	$('.row_content').hover(
				 function(){ $(this).css('border-left', "1px solid "+$("#hid_mail_bgColor").val()) },
 				 function(){ $(this).css('border-left', "1px solid #fff") 
				 }
				);
		document.getElementById('action_gif_search').style.display= 'none';
		//getAllMailCount(fdrname);
		//event.stopPropagation();
		try
		{
		$("#search_form").hide();	
		}
		catch (e) {
			// TODO: handle exception
		}
        }
       
    }); 
    
	}
	}	
}

function getAllEmails( id ) {
	//alert(id);
	//id=id.replace(/"/g, '');
	//alert(id);
	var pfldr="Inbox";
	/* var element = document.getElementById("parent_fldr");
    var path_fldr = element.options[element.selectedIndex].value;
    if(path_fldr!=null && path_fldr!=""  )
    	{
    	pfldr=path_fldr
    	} */
    quickMailSearchWorking(id,pfldr);
}

function quickMailSearch()
{
	var quick_val= document.getElementById('quick_search').value;
	if(quick_val!=null && quick_val!="")
		{
		var pfldr="Inbox";
		/* var element = document.getElementById("parent_fldr");
	    var path_fldr = element.options[element.selectedIndex].value;
	    if(path_fldr!=null && path_fldr!=""  )
	    	{
	    	pfldr=path_fldr
	    	} */
	    quickMailSearchWorking(quick_val,pfldr);
		}
}

function quickMailSearchWorking(quick_val,pfldr)
{
	//var pfldr="inbox";   //document.getElementById('hid_active_fldr').value
	
	//showHide(event);
	var start='0';
	//var end='15';
	var end=$("#hid_mail_list_limit").val();
	document.getElementById('action_gif_search').style.display= 'block';
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
	 var pview=document.getElementById("hid_previewPane").value;
	    try
	    {
	    	noneMail();
	 	
	    	
	    }
	    catch (e) {
			// TODO: handle exception
		}
	 $.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/quickSearchMail",
       data: {'quick_val':quick_val, 'start':start, 'end':end, 'fldrnm': pfldr, 'pview': pview},
        contentType: "application/json",
        success: function (data) {
       //   alert(data);
       if(data=="<$expire$>")
	{
	location.href ="${pageContext.request.contextPath}/inbox";
		} 	
       if(pfldr=="Sent" || pfldr=="Drafts" || pfldr=="sent" || pfldr=="drafts")
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
      $('#hid_open_search').val("true");
      $('#hid_active_fldr').val(pfldr);
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
        try
        {
        if($('#div_for_setting').css('display') == "block")
      		{
      		$('#div_for_setting').css('display',"none");
      		$('#div_for_inbox').css('display',"block");
      		}
        if($('#div_for_compose').css('display')=='block')
		{
			backFromComposeNew();
		}
        	backToMailList();	
        }
        catch (e) {
			// TODO: handle exception
		}
		$("#inb_cnt_div").html(res);
		
		$('.active_left_tree').css('border-left-color','#fff');
		$('.active_left_tree').removeClass('active_left_tree');
		
		$('#quick_search').val('');
		$(".small_image_flag").css("color",$("#hid_mail_bgColor").val());
    	$(".new_flag_color").css("color",$("#hid_mail_bgColor").val());
    	$(".draft_img").css("color",$("#hid_mail_bgColor").val());
    	$('.row_content').hover(
				 function(){ $(this).css('border-left', "1px solid "+$("#hid_mail_bgColor").val()) },
 				 function(){ $(this).css('border-left', "1px solid #fff") 
				 }
				);
		document.getElementById('action_gif_search').style.display= 'none';
		//getAllMailCount(fdrname);
       
        }
    }); 
    
		}


/* 
$('#quick_search').keydown(function(e){
  alert(e.which);
    if(e.which == 13)
    {
    	quickMailSearch();
    }
}); */

function keyPresSearch(e) {
	 var charKeyCode = e.keyCode ? e.keyCode : e.which;
	
	 if(charKeyCode == 13)
	    {
	    	quickMailSearch();
	    }
}


</script>
<script type="text/javascript">
function pagiSearchNextPage() {
	
   var typ= document.getElementById('hid_pagi_search_type').value;
   
	var pcnt=parseInt(document.getElementById("hid_pagi_search_pcnt").value);
	var allml=parseInt(document.getElementById("hid_pagi_search_allmail").value);
	var sch_lmt1=$("#hid_mail_list_limit").val();
	var sch_lmt=25;
	if(sch_lmt1!=null && sch_lmt1!="")
		{
		sch_lmt=parseInt(sch_lmt1);
		}
	 var lmt=pcnt+1;
	 var sml=pcnt*sch_lmt;
	var all=pcnt*sch_lmt;
	if(all<=allml)
		{
		//alert(all);
		var s=(pcnt*sch_lmt)+1;
		var e=(pcnt*sch_lmt)+(sch_lmt);
		if(e>allml)
			{
			e=allml;
			}
		document.getElementById("pagination_div").innerHTML=''+s+" - "+e+" of "+allml;
		 $("#pagination_div").attr('title', ''+s+" - "+e+" of "+allml);
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
	var pfldr=document.getElementById('hid_active_fldr').value;
	/* var element = document.getElementById("parent_fldr");
    var path_fldr = element.options[element.selectedIndex].value;
    if(path_fldr!=null && path_fldr!=""  )
    	{
    	pfldr=path_fldr
    	} */
	var start=''+sml;
	//var end='15';
	var end=$("#hid_mail_list_limit").val();
	var pview=document.getElementById("hid_previewPane").value;
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
						if(data=="<$expire$>")
						{
						location.href ="${pageContext.request.contextPath}/inbox";
						}
						var arr=data.split("<$nps$>");
					     var res="";
					        if(arr.length>1)
					        	{
					        	res=arr[1];
					        	}
						$("#inb_cnt_div").html(res);
						$(".small_image_flag").css("color",$("#hid_mail_bgColor").val());
				    	$(".new_flag_color").css("color",$("#hid_mail_bgColor").val());
				    	$(".draft_img").css("color",$("#hid_mail_bgColor").val());
				    	$('.row_content').hover(
		         				 function(){ $(this).css('border-left', "1px solid "+$("#hid_mail_bgColor").val()) },
		          				 function(){ $(this).css('border-left', "1px solid #fff") 
		         				 }
		   					);
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
		dt1=document.getElementById('hid_date1_search').value;
		dt2=document.getElementById('hid_date2_search').value;
		tagnm=document.getElementById('hid_tag_search').value;
		stnm=document.getElementById('hid_status_search').value;
		$.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}/advSearchMail",
						data : {
							'to':to, 'from':from, 'sub':sub, 'dt1':dt1, 'dt2':dt2, 'tagnm': tagnm,  'stnm': stnm, 'con':con, 'start':start, 'end':end, 'fldrnm': pfldr, 'pview': pview
						},
						contentType : "application/json",
						success : function(data) {
							// $("#fileSystem").html(data);
							// alert(data);
							if(data=="<$expire$>")
							{
							location.href ="${pageContext.request.contextPath}/inbox";
								}
							 var arr=data.split("<$nps$>");
					     var res="";
					        if(arr.length>1)
					        	{
					        	res=arr[1];
					        	}
							$("#inb_cnt_div").html(res);
							$(".small_image_flag").css("color",$("#hid_mail_bgColor").val());
					    	$(".new_flag_color").css("color",$("#hid_mail_bgColor").val());
					    	$(".draft_img").css("color",$("#hid_mail_bgColor").val());
					    	$('.row_content').hover(
			         				 function(){ $(this).css('border-left', "1px solid "+$("#hid_mail_bgColor").val()) },
			          				 function(){ $(this).css('border-left', "1px solid #fff") 
			         				 }
			   					);
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
	var sch_lmt1=$("#hid_mail_list_limit").val();
	var sch_lmt=25;
	if(sch_lmt1!=null && sch_lmt1!="")
		{
		sch_lmt=parseInt(sch_lmt1);
		}
	 var lmt=pcnt-1;
	 var sml=(lmt*sch_lmt)-sch_lmt;
	var all=lmt*sch_lmt;
	if(all>0)
		{
		
		var s=(lmt*sch_lmt)-sch_lmt+1;
		var e=(lmt*sch_lmt);
		if(e>allml)
			{
			e=allml;
			}
		document.getElementById("pagination_div").innerHTML=''+s+" - "+e+" of "+allml;
		 $("#pagination_div").attr('title', ''+s+" - "+e+" of "+allml);
		document.getElementById("hid_pagi_search_pcnt").value=lmt;
		/* var dtsort=document.getElementById("hid_dt_sorting").value;
		var path="getMailInbox";
		if(dtsort=="down")
			{
			path="getMailInboxDesc";
			} */
		var pfldr=document.getElementById('hid_active_fldr').value;
		/* var element = document.getElementById("parent_fldr");
	    var path_fldr = element.options[element.selectedIndex].value;
	    if(path_fldr!=null && path_fldr!=""  )
	    	{
	    	pfldr=path_fldr
	    	} */
		var start=''+sml;
		//var end='15';
		var end=$("#hid_mail_list_limit").val();
		document.getElementById('action_gif').style.display= 'block';
		var pview=document.getElementById("hid_previewPane").value;
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
						if(data=="<$expire$>")
						{
						location.href ="${pageContext.request.contextPath}/inbox";
							} 
						var arr=data.split("<$nps$>");
					     var res="";
					        if(arr.length>1)
					        	{
					        	res=arr[1];
					        	}
						$("#inb_cnt_div").html(res);
						$(".small_image_flag").css("color",$("#hid_mail_bgColor").val());
				    	$(".new_flag_color").css("color",$("#hid_mail_bgColor").val());
				    	$(".draft_img").css("color",$("#hid_mail_bgColor").val());
				    	$('.row_content').hover(
		         				 function(){ $(this).css('border-left', "1px solid "+$("#hid_mail_bgColor").val()) },
		          				 function(){ $(this).css('border-left', "1px solid #fff") 
		         				 }
		   					);
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
			dt1=document.getElementById('hid_date1_search').value;
			dt2=document.getElementById('hid_date2_search').value;
			tagnm=document.getElementById('hid_tag_search').value;
			stnm=document.getElementById('hid_status_search').value;
			$.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}/advSearchMail",
						data : {
							'to':to, 'from':from, 'sub':sub, 'dt1':dt1, 'dt2':dt2,'tagnm': tagnm, 'stnm': stnm, 'con':con, 'start':start, 'end':end, 'fldrnm': pfldr, 'pview': pview
						},
						contentType : "application/json",
						success : function(data) {
							if(data=="<$expire$>")
							{
							location.href ="${pageContext.request.contextPath}/inbox";
								}
							var arr=data.split("<$nps$>");
						     var res="";
						        if(arr.length>1)
						        	{
						        	res=arr[1];
						        	}
							$("#inb_cnt_div").html(res);
							$(".small_image_flag").css("color",$("#hid_mail_bgColor").val());
					    	$(".new_flag_color").css("color",$("#hid_mail_bgColor").val());
					    	$(".draft_img").css("color",$("#hid_mail_bgColor").val());
					    	$('.row_content').hover(
			         				 function(){ $(this).css('border-left', "1px solid "+$("#hid_mail_bgColor").val()) },
			          				 function(){ $(this).css('border-left', "1px solid #fff") 
			         				 }
			   					);
							document.getElementById('action_gif').style.display= 'none';
							//getAllMailCount(fdrname);
						}
					});
			
			}
		}
	
}
</script>
<script type="text/javascript">

function addSignature() {
	//var sts=document.getElementById('hid_auto_sign').value;
	//if(sts=="0")
	//	{
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
						//window.om=data;
						document.getElementById('action_gif').style.display= 'none';
						if(data=="<$expire$>")
						{
						location.href ="${pageContext.request.contextPath}/inbox";
							}

						try
						{
						var oEditor = CKEDITOR.instances;
						oEditor.editor1.insertHtml("<br><br><br>"+data);
						}
						catch (e) {
							// TODO: handle exception
							var cnt=$("#editor1").val();
							
							data=$(data).text()
							$("#editor1").val(cnt+"\n\n"+data);
						
						}
						//document.getElementById('hid_auto_sign').value="1";
						
					}
				});
		//}

}


function changeCKImg() {
	 var oMyForm = new FormData();
	  
	var flszMBimg=0;
	
	 // for(var i=0; i< upl1.files.length ;i++ )
	//	  {
		 var upl_ck = document.getElementById("upl_ck")   ;
		 var nm= upl_ck.files[0].name;
	  		oMyForm.append("file0", upl_ck.files[0]);
	  		var flsz=upl_ck.files[0].size;
	  		
	  		var flsz1=flsz/1024;
	  		
	  	var arr = nm.split(".");
	  
	  if((arr[arr.length-1]!="png" && arr[arr.length-1]!="PNG" && arr[arr.length-1]!="jpg" && arr[arr.length-1]!="JPG" && arr[arr.length-1]!="jpeg") && arr[arr.length-1]!="JPEG" && arr[arr.length-1]!="gif" && arr[arr.length-1]!="GIF"  && arr[arr.length-1]!="MBP"  && arr[arr.length-1]!="bmp")
		  {
		  var msg="Image type must be png/jpg/gif/bmp only.";
		  showmsg('alert',msg) ;
		  }
	/*   else  if(flsz1> 100)
		  {
		  var msg="Accepted image size is up to 100KB.";
		  showmsg('alert',msg) ;
		  } */
	  else
		  {
	  		
		  document.getElementById('action_gif').style.display= 'block';
	  $.ajax({
	    url: '${pageContext.request.contextPath}/uploadImageCK',
	    data: oMyForm,
	    dataType: 'text',
	    processData: false,
	    contentType: false,
	    type: 'POST',
	    success: function(data){
	    //	alert(data)
	    if(data!="false")
	    	{
	    	
	    	var imgck="<img src='data:image/jpg;base64,"+data+"' />";
	    	
	    	var imgHtml = CKEDITOR.dom.element.createFromHtml(imgck);
	    	if($("#div_for_compose").css('display')=='block')
	    		{
	    		CKEDITOR.instances['editor1'].insertElement(imgHtml);
	    		}
	    	else if($("#div_for_setting").css('display')=='block')
	    		{
	    		CKEDITOR.instances['sig'].insertElement(imgHtml);
	    		}
	    	
	    	
	    	} 
	    	document.getElementById('action_gif').style.display= 'none';
	    }
	  }); 
		  }
	  
}




function changeLDAPImg() {
	 var oMyForm = new FormData();
	  
	var flszMBimg=0;
	
	 // for(var i=0; i< upl1.files.length ;i++ )
	//	  {
		 var upl1 = document.getElementById("upl1")   ;
		 var nm= upl1.files[0].name;
	  		oMyForm.append("file0", upl1.files[0]);
	  		var flsz=upl1.files[0].size;
	  		
	  		var flsz1=flsz/1024;
	  		
	  	var arr = nm.split(".");
	  
	  if((arr[arr.length-1]!="png" && arr[arr.length-1]!="PNG" && arr[arr.length-1]!="jpg" && arr[arr.length-1]!="JPG" && arr[arr.length-1]!="jpeg"))
		  {
		  var msg="Image type must be png or jpg only.";
		  showmsg('alert',msg) ;
		  }
	  else  if(flsz1> 100)
		  {
		  var msg="Accepted image size is up to 100KB.";
		  showmsg('alert',msg) ;
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
	    	var set_id="set_"+mid+"nomyimage";
	    	//$(".new_user").html('<img alt="Embedded Image" onerror="getAltImage(this.id)" id="'+sml_id+'" src="data:image/jpg;base64,'+data+'" />');
	    	//$("#big_img").html('<img height="96px" width="96px" alt="Embedded Image"  onerror="getAltImage(this.id)" id="'+big_id+'" src="data:image/jpg;base64,'+data+'" /><div onclick="uploadDP()" class="change_images">Change photo</div>');
	    	/* $('#'+sml_id).attr("src","data:image/jpg;base64,"+data);
	    	$('#'+big_id).attr("src","data:image/jpg;base64,"+data);
	    	$('#chat_id_bd').attr("src","data:image/jpg;base64,"+data);
	    	 $('#chat_id').attr("src","data:image/jpg;base64,"+data); */
	    	
	    	try
	    	{
	    		document.getElementById(sml_id).src="data:image/jpg;base64,"+data;
		    	document.getElementById(big_id).src="data:image/jpg;base64,"+data;
		    	document.getElementById("chat_id_bd").src="data:image/jpg;base64,"+data;
		    	document.getElementById("chat_id").src="data:image/jpg;base64,"+data;
	    		document.getElementById(set_id).src="data:image/jpg;base64,"+data;
	    	}
	    	catch(err) {
	    		
	    	}
	    	} 
	    	document.getElementById('action_gif').style.display= 'none';
	    }
	  }); 
		  }
	  
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

function uploadImgCk(){	
	
	  //alert('Hi');
	  $('.pop_for_image_upload_ck').show();
	  $('.web_dialog_overlay').show();
	  
	  
		}



/* function chgEditor(typ) {
	if(typ=="text")
		{
		document.getElementById('editor1').style.display= 'none';
		document.getElementById('editor2').style.display= 'block';
		}
	else if(typ=="html")
		{
		document.getElementById('editor2').style.display= 'none';
		document.getElementById('editor1').style.display= 'block';
		}
} */
function getStart(i) {
		var info = ["Change your password regularly and keep it in a safe place.", 
	            "Don’t share your password with anyone.",
	            "Don’t open attachments from anyone you don’t know.",
	            "Log out or sign off from your account when you’ve finished looking at/sending your email.",
	            "Don’t reply to spam or forward chain emails.",
	            "Keep your personal information personal – don’t share bank or credit card information by email."
	            ];
	try
	{
		$("#sec_info").html(info[i]);
		i++;
		if(i==6)
			{
			i=0;
			}
		if($('#div_progress').css('display')=='block')
		 setTimeout(function () { getStart(i); }, 5000);
	}
	catch (e) {
		// TODO: handle exception 
	}
}


</script>

<script type="text/javascript">

/* jQuery(window).unload(function(){
	window.location.href="${pageContext.request.contextPath}/logout";
}); */

/* function bye() {
	window.location.href="${pageContext.request.contextPath}/logout";
} */
</script>

</head>
<!-- <body  onload="getStart(0),getWebmailfldr(),getWebmailInboxRefresh()"> -->
<body>

	<%


	 //WebmailClient webmailClient=(WebmailClient) request.getAttribute("webmailClient");
	// List<MailImapFolders> imapfldrlst_search=(List<MailImapFolders>) request.getAttribute("imapfldrlst");
	// System.out.println("^^^^^^^^^^^^^^^^^^^size="+imapfldrlst_search.size()); 
	// System.out.println("^^^^^^^^^^^^^^^^^^^0="+imapfldrlst_search.get(0));
   
	String host=(String)head_hs.getAttribute("host");
	String id=(String)head_hs.getAttribute("id");
	String pass=(String)head_hs.getAttribute("pass");
	String port=(String)head_hs.getAttribute("port");
    String act_fldr= head_hs.getAttribute("active_folder").toString();
    String mailid=head_hs.getAttribute("id").toString();
    String fname=head_hs.getAttribute("fname").toString();
    byte[] jpegBytes1=( byte[])head_hs.getAttribute("img_myurl");
	String ldapurl=(String)head_hs.getAttribute("ldapurl");
	String ldapbase=(String)head_hs.getAttribute("ldapbase");
	String bgColor=(String)head_hs.getAttribute("bgColor");
	String dom="";
	if(id!=null)
	{
	String arr[]=id.split("@");
    dom=arr[arr.length-1];
	}
                        //String path_img=img_myurl+mailid+".jpg";
                        
                       //  byte[] jpegBytes1 =img_myurl.getBytes();
                       String path_img="images/blank_man.jpg";
                       if(jpegBytes1!=null && jpegBytes1.length>10)
                       {
                    	   path_img= new sun.misc.BASE64Encoder().encode(jpegBytes1);
                    	   path_img= "data:image/jpg;base64,"+path_img;
                       }
                       //  System.out.println("base64******"+path_img);
                        String path_img_id=mailid+"nomyimage";
                       // String pri_previewPane=MailAccSetting.previewPane;
                       //int mail_list_limit=MailAccSetting.limitMail; 
                       String pri_previewPane=head_hs.getAttribute("previewPane").toString();
                       int mail_list_limit=Integer.parseInt(head_hs.getAttribute("limitMail").toString());
                       String composePage=head_hs.getAttribute("composePage").toString();
                       String previewPane=pri_previewPane;
                       String limitMail=head_hs.getAttribute("limitMail").toString();
                        %>
                         <input type="hidden" id="hid_limitMail_before_set" value="<%=limitMail %>"/>
                          <input type="hidden" id="hid_limitMail_after_set" value="<%=limitMail %>"/>
                           <input type="hidden" id="hid_previewPane_before_set" value="<%=previewPane %>"/>
                            <input type="hidden" id="hid_previewPane_after_set" value="<%=previewPane %>"/>
                            <input type="hidden" id="hid_composePage_before_set" value="<%=composePage %>"/>
                            <input type="hidden" id="hid_composePage_after_set" value="<%=composePage %>"/>
                        
                      
				

                        
                        
                     <!--     <div id="hid_img_disp" style="top: 20%; left: 20%; width: 40%; height: 40%; display: none;z-index: 111111; position: absolute;">
                         </div> -->
                        <input type="hidden" id="hid_unread_inbox" value=""/>
                        <input type="hidden" id="hid_logedin_id" value="<%=id %>"/>
                        <input type="hidden" id="hid_confirm_box" value=""/>
                        <input type="hidden" id="hid_previewPane" value="<%=pri_previewPane %>"/>
						<input type="hidden" id="hid_mail_list_limit" value="<%=mail_list_limit %>"/>
						<input type="hidden" id="hid_mail_bgColor" value="<%=bgColor %>"/>
						<input type="hidden" id="hid_mail_domain" value="<%=dom %>"/>
						<input type="hidden" id="hid_mail_disp_count" value=""/>
						<input type="hidden" id="hid_open_setting" value=""/>
						<input type="hidden" id="hid_open_search" value="false"/>
						
						
						<!---/// TAG CONTENT STRED HERE ------->
                           
                        
						
						

	


	<input type='hidden' value='<%=act_fldr %>' id='hid_active_fldr' />
	<div id="action_gif" class="my_notification"
		style="display: none; left: 46%;">Loading...</div>
		<div id="action_gif_search" class="my_notification"
		style="display: none; left: 46%;">Searching...</div>
	<div id="mail_sending" class="my_notification"
		style="display: none; left: 46%;">Sending...</div>
	<div id="mail_sent" class="my_notification"
		style="display: none; left: 43%;">Message send successfully.</div>
	<div id="mail_spam" class="my_notification"
		style="display: none; left: 43%;">The mail has been marked as
		spam.</div>
	<div id="mail_comm_msg" class="my_notification"
		style="display: none; left: 30%;">Task has been done.</div>

	


