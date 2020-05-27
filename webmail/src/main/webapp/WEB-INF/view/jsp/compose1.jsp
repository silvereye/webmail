<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- <script src="js/jquery-1.8.3.min.js" type="text/javascript"></script> 
<script src="js/jquery.noty.packaged.js" type="text/javascript" /> -->
<!--  <script src="js/compose_event.js" type="text/javascript"></script>  -->
  <script type="text/javascript" src="js_file_upload/js/script.js"></script> 
<!-- <script src="js/tabcontent.js" type="text/javascript"></script> -->
<!--  <link rel="stylesheet" type="text/css" href="css/autocomplete.css" />
 <script src="js/jquery.autocomplete.js"></script> -->
<link type="text/css" rel="stylesheet" href="css/contact_css.css" />
<style>
.progress-bar {
  background-color: rgba(36, 221, 48, 0.35);
  width: 0%;
  position: absolute;
  /* height: 100%; */
  z-index: 2;
  height: 100%;
		}
		
		
		</style>

<style>

.setting_scroll {
  margin-top: 6px !important;
}   
.vsplitbar {
	width: 5px;
	background: #aaa;
}
.to:hover{ background:#eee;}
.r_top{ padding-top:0px !important;}
/* #spliter2 .a {
	background-color: #2d2d2d;
}
#spliter2 .b {
	background-color: #2d002d;
}
#foo {
	background-color: #E92727;
} */
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
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
}
</style>


<script type="text/javascript">
jQuery(function(){
	$("#to_id").autocomplete("${pageContext.request.contextPath}/autocomp_new");
	
	/* $(document.body).on('click','.briefase_attachment',function(){
		//$('.briefase_attachment').click(function(){
			 
			  
			
			  document.getElementById('action_gif').style.display= 'block';
			  
			  
			  var requestPage="${pageContext.request.contextPath}/openBriefcase";
				jQuery.get(requestPage,
			           
			                    function( data ) {
			            	$("#mybrief").html(data);
					$('.briefcase_box').show();
					document.getElementById('action_gif').style.display= 'none';
			            }); 
			  
			}); */
});
function generate_conf_sendmail(type,id) {
	   	
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
             
             
            $('#'+id).val("");
              
             $('.web_dialog_overlay').hide(); 
           /*  var n1 = noty({
                  text        : 'You clicked "Ok" button',
                  type        : 'success',
                  dismissQueue: false,
                  layout      : 'topCenter',
                  theme       : 'defaultTheme'
              });
             setTimeout(function () { $.noty.close(n1.options.id); }, 2000); */
            // alert(foldernm);
            
           }
           },
           {addClass: 'btn btn-danger', text: 'No', onClick: function ($noty) {
               $noty.close();
               $('.web_dialog_overlay').hide(); 
              $('#'+id).val("true").trigger('change');
            
           }
           }
       ]
   });
  
       
       //console.log(type + ' - ' + n.options.id);
       return n; 
        
   }
   









</script>
<script type="text/javascript">
jQuery(function(){
	$("#cc_id").autocomplete("${pageContext.request.contextPath}/autocomp_new");
});
</script>
<script type="text/javascript">
jQuery(function(){
	$("#bcc_id").autocomplete("${pageContext.request.contextPath}/autocomp_new");
});
</script>
<script type="text/javascript">
 function uploadFormData(){
   // $('#result').html('');
		
	  var oMyForm = new FormData();
	  //alert(file2.files[0]);
	 // alert(upl.files.length);
	//var ufiles = new Array();
 var upl = document.getElementById("upl")   ;
	window.flszMB=0;
	//alert("first="+window.flszMB)
	var hid_filesize=$("#hid_upload_file_size").val();
	if(hid_filesize!=null && hid_filesize!="")
		{
		window.flszMB=parseFloat(hid_filesize);
		
		}
	//alert("second="+window.flszMB)
	  for(var i=0; i< upl.files.length ;i++ )
		  {
		 
	  		oMyForm.append("file"+i, upl.files[i]);
	  		var flsz=upl.files[i].size;
	  	//	alert(flsz+"bytes");
	  		var flsz1=flsz/1000;
	  		//alert(flsz1+"KB");
	  		window.flszMB=window.flszMB+(flsz1/1024);
	  	//	alert(window.flszMB);
	  		//var flid=upl.files[i].name.replace(/\./g,'_');
	  		//ufiles[i]=flid;
	  		//alert(flid);
	  		//Update(flid);
	  		
		  }

	  if(window.flszMB> 41)
		  {
		  var msg="The uploaded file exceeds the maximum size of 40 MB.";
		   var error = generate('error');
			 $.noty.setText(error.options.id, msg);
			 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
		  }
	  else
		  {
		  window.uploadflag="false";
		  $("#hid_upload_file_size").val(flszMB); 
		//  alert("go")
	 $.ajax({
	    url: '${pageContext.request.contextPath}/upload',
	    data: oMyForm,
	    dataType: 'text',
	    processData: false,
	    contentType: false,
	    type: 'POST',
	    success: function(data){
	     //$('#result').html(data);
	  		window.uploadflag="true";
		 	mailSaveInDraftAtt();
		 	
		
	    }
	  }); 
		  }
	  
} 
 

 
</script>


 <script type="text/javascript">
  
 
function setPri(val) {
  if(val!=null && val!="")
      {
       if(val=="Normal")
          {
           document.getElementById("hid_priority").value="";
          }
       else 
          {
           document.getElementById("hid_priority").value=val;
          }
     }
}
                                  
                                  
function readRec() {
   var cnt=document.getElementById("hid_read_rec").value;
   if(cnt!=null && cnt!="")
        {
        if(cnt=="0")
             {
             document.getElementById("hid_read_rec").value="1";
             }
         else if(cnt=="1")
             {
              document.getElementById("hid_read_rec").value="0";
             }
        }
}
function statusDSN()
{
var cnt=document.getElementById("hid_dsn_status").value;
if(cnt!=null && cnt!="")
     {
     if(cnt=="0")
          {
          document.getElementById("hid_dsn_status").value="1";
          }
      else if(cnt=="1")
          {
           document.getElementById("hid_dsn_status").value="0";
          }
     }
}

function openBriefcase(fpath) {
	

	 document.getElementById('action_gif').style.display= 'block';
	 var breadcumPath="";
	  try
	  {
		 breadcumPath=$('#breadcumPathHome').val();
	  }
	  catch (e) {
		 breadcumPath="";
	}
	//  alert(breadcumPath);
	  var requestPage="${pageContext.request.contextPath}/openBriefcase";
		jQuery.get(requestPage,
				             {
                'fpath' : fpath, 'breadcumPath': breadcumPath
        },
	           
	                    function( data ) {
	            	$("#mybrief").html(data);
	            	$(".insert_color").css("border","none");
 	            	$(".insert_color").css("background-image","none");
 	    			$(".insert_color").css("background-color",$("#hid_mail_bgColor").val());
 	    			$(".cont_search").css("background-color",$("#hid_mail_bgColor").val());
 	    			$('.web_dialog_overlay_setting').show();
			$('.briefcase_box').show();
			document.getElementById('action_gif').style.display= 'none';
	            }); 
}


function openMyContacts(id) {
	
	
	  document.getElementById('action_gif').style.display= 'block';
	$("#hid_con_popup").val(id);
	  var requestPage="${pageContext.request.contextPath}/getconPopupList";
		jQuery.get(requestPage,
				             
	           
	                    function( data ) {
			$("#con_searchnp").val("");	
			$("#contactLst").html(data);
	            	
	            	var fpath="";
	            	 var requestPage="${pageContext.request.contextPath}/getconPopupListContacts";
	         		jQuery.get(requestPage,
	         				             {
	                         'fpath' : fpath
	                 },
	         	           
	         	                    function( data ) {
	         	            	$("#mycontact").html(data);
	         	            	
	         	            	$(".insert_color").css("border","none");
	         	            	$(".insert_color").css("background-image","none");
	         	    			$(".insert_color").css("background-color",$("#hid_mail_bgColor").val());
	         	    			$(".cont_search").css("background-color",$("#hid_mail_bgColor").val());
	         	    			$('.web_dialog_overlay_setting').show();
	            	$('.mycontact_box').show();
			document.getElementById('action_gif').style.display= 'none';
	                 }                 
		);
	            });  
}





function getAllPopupContacts(fpath) {
	
	 document.getElementById('action_gif').style.display= 'block';
	 var requestPage="${pageContext.request.contextPath}/getconPopupListContacts";
		jQuery.get(requestPage,
				             {
              'fpath' : fpath
      },
	           
	                    function( data ) {
	            	$("#mycontact").html(data);
 	
 	$('.mycontact_box').show();
document.getElementById('action_gif').style.display= 'none';
      }                 
);
}


function getAllPopupContactsSearch() {
	var fpath=$("#conPopupList").val();
		var search=$("#con_searchnp").val();
	
	 document.getElementById('action_gif').style.display= 'block';
	 var requestPage="${pageContext.request.contextPath}/getconPopupListContactsSearch";
		jQuery.get(requestPage,
				             {
             'fpath' : fpath , 'search' : search 
     },
	           
	                    function( data ) {
    	 $("#con_searchnp").val("");	
    	 $("#mycontact").html(data);
	
	$('.mycontact_box').show();
    document.getElementById('action_gif').style.display= 'none';
     }                 
); 
}

//$(".contact_check_all").change(function () {
	
	

	
 

function insertContacts(hid) {
	//var hid=$("#hid_con_popup").val();
	//alert(hid)
	var ids=""
	$('input[name="chk_con"]:checked').each(function() {
		if(ids=="")
			{
			ids=this.value;
			}
		else
			{
			ids+=","+this.value;
			}
		
		});
//alert(ids)
	if(ids!="")
		{
		if(hid=="to")
			{
			insertContactsTo(ids);
			}
		else if(hid=="cc")
		{
			insertContactsCc(ids);
		}
		else if(hid=="bcc")
		{
			insertContactsBcc(ids);
		}
		}
}


function insertContactsTo(txtval)
{
	
	 var valarr=txtval.split(",");
		// alert(valarr.length);
		 for(var z=0;z<valarr.length;z++)
			 {

	
		  var textarea = valarr[z].trim();

		  
		  //alert(textarea);
		  var textarea1=textarea;
		  textarea1=textarea1.replace("<","&lt;");
		  textarea1=textarea1.replace(">","&gt;");
		  if(textarea!="" && textarea!=null)
			  {
			//  e.preventDefault();
			  var hid_txt=$('#hid_to_id').val();
			  var tocnt="";
			  if(hid_txt==null || hid_txt=="")
				  {
				  tocnt=textarea;
				  }
			  else
				  {
				  tocnt=hid_txt+","+textarea;
				  }
			  
			  document.getElementById("hid_to_id").value =tocnt;
		//  $('.enter_email').val('');
	
	
	
	
	
		 // var n_to = $('vR').length;
		  //alert(n);
		  	$('.combind_email').append('<div class="vR to_content" id="'+textarea+'"><span class="vN Y7BVp a3q" email="hari@silvereye.co"><div class="vT">' + textarea1 +'</div><div class="vM closeit_to"></div></span></div>');
		   var n_to = $('.to_content').length;
		   window.get_width = $('.combind_email').width();
			 window.find_other = $('.composed_input').width();
			
			 
		     /// FOR EACH LOOP 
var append_width =$('.to_content').width();
var countwidth = 0;
var extracount =0;
if($('.email_id_info_to').css('display')=="block")
	extracount=1;
$('.to_content').each(function() {
	  
	  countwidth += $(this).width();
	  if( countwidth >= find_other)
	  {
		  
		  extracount++;
		  
		  $('.email_id_info_to').removeClass('remove_to_more');  

		  }
	  else
		  {
		  
		      $('.email_id_info_to').addClass('remove_to_more'); 
		  
		  }
	   });

	  $('.email_id_info_to').text(extracount+'') /// GET IT FINAL 
			 
	
		// EMAIL VALIDATION START HERE 
		try
	  {
    if (!checkEmail(textarea))
    {
			 $('#'+textarea).addClass('wrong_email')
    }
	  }
	  catch (e) {
		// TODO: handle exception
	}
	// EMAIL VALIDATION END HERE 
	
	
			  
			  }
		 
		  
		  
			 }
	 
}


function  insertContactsCc(txtval)
{
	  var valarr=txtval.split(",");
		// alert(valarr.length);
		 for(var z=0;z<valarr.length;z++)
			 {
			 

			 /* if(!e.shiftKey && e.which != 9)
    		{
    		e.preventDefault();
    		}*/
			  
			  var textarea = valarr[z].trim();
			  var textarea1=textarea;
			  textarea1=textarea1.replace("<","&lt;");
			  textarea1=textarea1.replace(">","&gt;");
			  if(textarea!="" && textarea!=null)
			  {
				  //e.preventDefault();
			  var hid_txt=$('#hid_cc_id').val();
			  var cccnt="";
			  if(hid_txt==null || hid_txt=="")
				  {
				  cccnt=textarea;
				  }
			  else
				  {
				  cccnt=hid_txt+","+textarea;
				  }
			  
			  document.getElementById("hid_cc_id").value =cccnt;
			  $('.enter_email_cc').val('');
			  
			  				
			  
			  $('.combind_email_cc').append('<div class="vR cc_content" id="'+textarea+'"><span class="vN Y7BVp a3q" email="hari@silvereye.co"><div class="vT">' + textarea1 +'</div><div class="vM closeit_cc"></div></span></div>');
			   var n_to = $('.cc_content').length;
			   window.get_width = $('.combind_email_cc').width();
				 window.find_other = $('.composed_input').width();
				
				 
			     /// FOR EACH LOOP 
	  var append_width =$('.cc_content').width();
	  var countwidth = 0;
	  var extracount =0;
	  if($('.email_id_info_cc').css('display')=="block")
			extracount=1;
	  $('.cc_content').each(function() {
		  
		  countwidth += $(this).width();
		  if( countwidth >= find_other)
		  {

			  extracount++;
			  $('.email_id_info_cc').removeClass('remove_to_more');  
			  
			  }
		  else
			  {
			  $('.email_id_info_cc').addClass('remove_to_more');  
			  }
		   });
	 
		  $('.email_id_info_cc').text(extracount+'') /// GET IT FINAL 
				 	// EMAIL VALIDATION START HERE 
			        try
		  			{
				 	if (!checkEmail(textarea))
			            {
							 $('#'+textarea).addClass('wrong_email')
			            }
		  			}
		 		 catch (e) {
				// TODO: handle exception
				}
					// EMAIL VALIDATION END HERE 
		   }
			  
			 
			 } 
}


function  insertContactsBcc(txtval)
{
	
	$('.bcc_hide').addClass('full_row');
	$('.green_sign').css('display','block');
	var valarr=txtval.split(",");
		// alert(valarr.length);
		 for(var z=0;z<valarr.length;z++)
			 {
			 

		 /* if(!e.shiftKey && e.which != 9)
  		{
  		e.preventDefault();
  		}*/
		  
		  var textarea = valarr[z].trim();

			
			  
			  var textarea1=textarea;
			  textarea1=textarea1.replace("<","&lt;");
			  textarea1=textarea1.replace(">","&gt;");
			  if(textarea!="" && textarea!=null)
			  {
				//  e.preventDefault();
			  var hid_txt=$('#hid_bcc_id').val();
			  var bcccnt="";
			  if(hid_txt==null || hid_txt=="")
				  {
				  bcccnt=textarea;
				  }
			  else
				  {
				  bcccnt=hid_txt+","+textarea;
				  }
			  
			  document.getElementById("hid_bcc_id").value =bcccnt;
			  $('.enter_email_bcc').val('');
			  
			  
			  $('.combind_email_bcc').append('<div class="vR bcc_content" id="'+textarea+'"><span class="vN Y7BVp a3q" email="hari@silvereye.co"><div class="vT">' + textarea1 +'</div><div class="vM closeit_bcc"></div></span></div>');
			   var n_to = $('.bcc_content').length;
			   window.get_width = $('.combind_email_bcc').width();
				 window.find_other = $('.composed_input').width();
				
				 
			     /// FOR EACH LOOP 
	  var append_width =$('.bcc_content').width();
	  var countwidth = 0;
	  var extracount =0;
	  if($('.email_id_info_bcc').css('display')=="block")
			extracount=1;
	  $('.bcc_content').each(function() {
		  
		  countwidth += $(this).width();
		  if( countwidth >= find_other)
		  {
			  
			  extracount++;
			  
			  $('.email_id_info_bcc').removeClass('remove_to_more');  
			  
		  }
	  else
		  {
		  $('.email_id_info_bcc').addClass('remove_to_more');  
		  }
		   });
	 
		  $('.email_id_info_bcc').text(extracount+'') /// GET IT FINAL 
			    
			    

				// EMAIL VALIDATION START HERE 
	          try
		  		{
				if (!checkEmail(textarea))
	            {
					 $('#'+textarea).addClass('wrong_email')
	            }
		  		}
		  catch (e) {
			// TODO: handle exception
		}
		  
			// EMAIL VALIDATION END HERE 
		   }
			  
			 }
		 $('.email_id_info_bcc').css('display','block');			
}

 $(document).ready(function(){ 	
	window.uploadflag="true";
	 var sub=$("#sub_id").val();
	 //alert(sub);
		if(sub!="")
			{
			var to=document.getElementById("hid_to_id").value;
			
			var uid=document.getElementById("hid_to_mail_uid").value;
			
			var cc=document.getElementById("hid_cc_id").value;
			
			
			var bcc=document.getElementById("hid_bcc_id").value;
			
			// alert(to);
			var sub=document.getElementById("sub_id").value;
			
			var cntt = "";
			var texttype="html";
			if($('#hid_editor_type').val()!="plain")
				{
			 	try
			 	{
				cntt = CKEDITOR.instances['editor1'].getData();
			 	}
			 	catch (e) {
					// TODO: handle exception
				}
				}
			else
				{
				cntt = $('#editor1').val();
				texttype="plain";
				}
		//	 alert(cntt);
			if(!((to==null || to=="") && (cc==null || cc=="") && (bcc==null || bcc=="") && (sub==null || sub=="") && (cntt==null || cntt=="")))
			{
			//	 alert("if");
				window.draftProgress=false;
				$.ajax({
			         type: "POST",
			         url: "${pageContext.request.contextPath}/mailSaveInDraft",
			         data: {'uid': uid,'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt, 'texttype': texttype },
			        /*  contentType: "application/json", */
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
	//	alert("hiii");
});

 
 function attach_file_frombriefcase() {
	var fil=$(".active_select").attr('id');
	 var nm=$(".active_select").attr('name');
	// var sz=$("#hidsize_"+nm).val();
	 document.getElementById('action_gif').style.display= 'block';
	 
	 window.flszMB=0;
	 var hid_filesize=$("#hid_upload_file_size").val();
	 if(hid_filesize!=null && hid_filesize!="")
	 	{
	 	window.flszMB=parseFloat(hid_filesize);
	 	//alert("first="+window.flszMB)
	 	}
	 
	   		
	   		//var flsz1=sz/1000;
	   		//window.flszMB=window.flszMB+(flsz1/1024);
	   		
	   	  if(window.flszMB> 41)
		  {
		  var msg="The uploaded file exceeds the maximum size of 40 MB.";
		   var error = generate('error');
			 $.noty.setText(error.options.id, msg);
			 setTimeout(function () {  $.noty.close(error.options.id); }, 5000);
		  }
	   	  else
	   		  {
	   		
	   		$("#hid_upload_file_size").val(window.flszMB);
	 window.uploadflag="false";
	// alert("go");
	 $.ajax({
         type: "GET",
         url: "${pageContext.request.contextPath}/attach_file_frombriefcase",
         data: {'file': fil,'name':nm},
         contentType: "application/json",
         success: function (data) {
            
           // alert(data);
            var ul = $('#upload ul');
           // var tpl = $('<li class="upload_li error"><p>'+ nm+'</p><span class="file_close">X</span></li>');
        	 var tpl = $('<li style="background-color: #b3f3b7;" class="working upload_li"><input type="text" value="0" data-width="48" data-height="48"'+
                ' data-fgColor="#0788a5" data-readOnly="1" data-bgColor="#3e4043" /><p class="fl_nm" style="color: red;">'+nm+'</p><span class="hidden_size">10</span><span class="file_close">X</span></li>');
            tpl.appendTo(ul);
            document.getElementById('action_gif').style.display= 'none';
            mailSaveInDraftAtt();
    	 	window.uploadflag="true";
         // alert(data);
         }
     });
	   		  }
}

                               </script>
                                  
</head>
<body >
 <%
    String signcnt="";
    String signStatus="";
    String bccField="";
    try
    {
    	HttpSession hsc=request.getSession();
    	signStatus=(String)hsc.getAttribute("signatureStatus");
    	signcnt=(String)hsc.getAttribute("signature");
    	bccField=(String)hsc.getAttribute("bccField");
    }
    catch(Exception ee)
    {
    	ee.printStackTrace();
    }
    %>
<input type="hidden" id="hid_active_compose" value="" />
<input type="hidden" id="hid_editor_type" value="html" />

<input type="hidden" id="hid_to_draft_mail_uid" value="" />
<input type="hidden" id="hid_to_mail_uid" value="" />

<input type="hidden" id="hid_msg_ref" value=">" />
<input type="hidden" id="hid_old_msg_id" value="" />
<input type="hidden" id="hid_upload_file_size" value="" />
  <div class="right_top_pannel right_top_pannel_new setting_pages"> 

                        <!-------// RIGHT TOP TOOL END HERE -------->
                        <div class="for_tool">
                           <div id="menu-wrap" class="composed_option">    
                                <ul id="menu">
                                   
                                      <li class="menu_space" title="Send"><a style="cursor: pointer;" onclick="mailSend()">Send </a></li>
                              
                              
                                      <li title="Discard"><a style="cursor: pointer;" onclick="discardCompose()">Discard</a></li>
                                      <li title="Save Draft"><a style="cursor: pointer;" onclick="mailSaveInDraftFource()">Save Draft</a></li>
                                     <!--  <li><a style="cursor: pointer;">Attach<img src="images/open_sub_menu.png" class="option_images" ></a>
                                           <ul>
                                              <li>
                                                  <form id="upload" class="upload" >
			                                                  <div id="drop" class="drop">
				                                                <a>My computer</a>
				                                                <input type="file" id="upl" name="upl" multiple onchange="uploadFormData()"/>
			                                                  </div>
		                                                </form> 
                                                                  
                                              </li>
                                              <li>
                                                  <a href="">Attach Inline</a>                  
                                              </li>
                              
                                              <li>
                                                  <a style="cursor: pointer;">Briefcase</a>
                                                                  
                                              </li>
                                              <li>
                                                  <a href="">Mail</a>				  
                                              </li>
                                                <li>
                                                  <a href="">Contacts</a>				  
                                              </li>
                                          </ul>
                                      </li> -->
                                     <!--  <li><a style="cursor: pointer;">Scheduled</a></li> -->
                                      <li>
                                          <a style="cursor: pointer;">Option <img src="images/open_sub_menu.png" class="option_images" ></a>
                                          <ul class="option_menu">
                                              <li>
                                                  <a style="cursor: pointer;" onclick="addSignature()">Insert Signature </a>
                                                  <!-- <ul>
                                                      <li><a style="cursor: pointer;">Default</a></li>
                                                      <li><a style="cursor: pointer;">Do Not Attach Signature</a></li>
                                                      
                                                  </ul> -->
                                                                  
                                              </li>
                                              <li class="plain_text" id="cke_1_top'">
                                                  <a  style="cursor: pointer;">Plain Text Mode
                                                       <div class="green_plain_text_sign"></div>
                                                  </a>              
                                              </li>
                                              
                                              
                                               <li class="html_text"><a  style="cursor: pointer;">Format As HTML
                                                    <div class="green_plain_text_sign_html"></div>
 											<input type="hidden" value="html" id="hid_editor_type" />
                                             </a></li>
                                              
                                              
                                              
                                              
                                            <!--   <li><a style="cursor: pointer;">Format As HTML</a></li> -->
                                             <!--  <li>
                                                  <a href="">Insert Templates</a>
                                                                  
                                              </li> -->
                                              
                                              <li>
                                                  <a style="cursor: pointer;">Priority</a>
                                                  <ul class="inner_option">
                                                      <li><a id="1 (Highest)" style="cursor: pointer;" onclick="setPri(this.id)">High Priority <div class="green_plain_text_sign"></div></a></li>
                                                      <li><a id="Normal" style="cursor: pointer;" onclick="setPri(this.id)">Normal Priority <div class="green_plain_text_sign"></div></a></li>
                                                      <li><a id="5 (Lowest)" style="cursor: pointer;" onclick="setPri(this.id)">Low Priority <div class="green_plain_text_sign"></div></a></li>
                                                  <input type="hidden" value="" id="hid_priority" />
                                                  </ul>				
                                                      
                                              </li>
                                              <li class="read_receipt">
                                                  <a style="cursor: pointer;" onclick="readRec()">Request Read Receipt <div class="green_plain_text_read"></div></a>
                                              <input type="hidden" value="0" id="hid_read_rec" />
                                              <input type="hidden" value="0" id="hid_dsn_status" />
                                              </li>
                                                <!-- <li class="dsn" title="Request Delivery status notification">
                                                  <a style="cursor: pointer;" onclick="statusDSN()">Request DSN<div class="green_plain_text_dsn"></div></a>
                                              	  
                                              </li> -->
                                              
                                                <!-- <li>
                                                  <a style="cursor: pointer;">Save Sent Message in</a>
                                              </li>  -->
                                               <li class="bcc_link">
                                                 <a style="cursor: pointer;">Show BCC
                                                 <%
                                                 if(bccField.length()==0 || bccField.equalsIgnoreCase("No"))
                                                 	{
                                                 %>
                                                    <div class="green_sign"></div>
                                                 <%
                                                 	}
                                                 else
                                                 	{
                                                 %>
                                                 	<div class="green_sign" style="display: block;"></div>
                                                 <%} %>
                                                 </a>
                                              </li>
                                          </ul>
                                      </li>
                                        <li  title="Close"><a style="cursor: pointer;" onclick="window.close();">Close</a></li>
                                  </ul>
                                 
                           </div>
                            <!---- RIGHT TOOL STARTED HERE ---->
                            <!--------/// Main Right Tool Stared Here -------->
                           <div class="right_tool_part">
                           
                           
                           
                           
                           <!--  <div class="right_tool"> <a href="javascript:void("Maximize");"> <img src="images/full_screen.png" class="full_screen_icon"/> </a> </div>
                            <div class="right_tool_1">
                                <ul id="menu">
                                    <li> <img src="images/setting_toll.png" class="four_margin"></li>
                                    <li class="right_menu_1">
                                   <a style="cursor: pointer;"> <img src="images/open_sub_menu.png" style="margin-left: 8px !important;"></a>
                                     - <ul >
                                            <li> <a style="cursor: pointer;">Settings</a></li>
                                            <li><a style="cursor: pointer;">Themes</a></li>
                                            <li> <a style="cursor: pointer;">Help</a></li>
                                        </ul>
                                    </li>
                                </ul>
                                
                            </div> -->
                            </div>
                            
                            <!-------------------/// Main Right Tool End Here ------------->
                        </div>
                        <!------ RIGHT TOOL END HERE TOP ---->

                        
                    </div>
 
    
    <!-----------------------------/// Main Cointer Stared here --------------->
    <div id="widget1" class="setting_scroll1" style="width: 100%; height: 100%;"> 
      <!---------------//// SETTING PAGES CONTENT STARED HERE ------------->
      <div class="composed_pages1" style="width: 100%; height:100%;
    background: #f1f1f1;
    padding-top: 10px;">
              <!-------/// ALL EMAIL SEND OPTION HERE --------->
          <div class="all_send">
              <!------------/// COMPOSED TO --------------->
             
          <div class="composed_row full_to">
                 <div class="to_mail" title="Select Contacts" style="cursor: pointer;" onclick="openMyContacts('to')">To</div>
                 <div class="composed_input to_row">
                     <!----/// EMAIL INFO ------>
                      <div class="email_id_info_to"></div>
                     <!------/// EMAIL INFO END ---->
                     <div class="combind_email">
                    
                     
                     </div>
                     <!------// After Focus div The Show Email ID ------------>
                     
                    <!---------/// AFTER FOCUS STARED HERE --------->
                
                 <!---------// END HERE -------------------->
                 <textarea id="to_id" value="" onblur="mailSaveInDraftNew1(this.value,'hid_to_id')"  name="to_id"   class="enter_email new_com_to"></textarea>
                 <input type="hidden"  value="" id="hid_to_id" value=""/>
                 </div>
                 <div class="clear"></div>
          </div>  
      <!----------/// COMPOSED END ------------------->
      <div class="clear"></div>
          <!--------------/// CC ROW  STARED HERE -------------->   
          
           <div class="composed_row  full_cc">
                 <div class="to_mail " title="Select Contacts" style="cursor: pointer;" onclick="openMyContacts('cc')">Cc</div>
                 <div class="composed_input cc_row new_com_cc" >
                  <!----/// EMAIL INFO ------>
                <div class="email_id_info_cc"></div> 
                     <!------/// EMAIL INFO END ---->
                    <!-- <div class="combind_email"></div>-->
                     <!------// After Focus div The Show Email ID ------------>
                     <div class="combind_email_cc">
                     
                    
                     </div>
                    <!---------/// AFTER FOCUS STARED HERE --------->
                      <textarea id="cc_id"   value="" onblur="mailSaveInDraftNew1(this.value,'hid_cc_id')" name="cc_id"  class="enter_email_cc "></textarea>
                       <input type="hidden"   value="" id="hid_cc_id" value=""/>
                 </div>
                 <div class="clear"></div>
          </div>         
          <!------------/// CC ROW END HERE ------------------->
                  <!--------------// BCC ROW STARTED HERE ------------> 
           <%
            if(bccField.length()==0 || bccField.equalsIgnoreCase("No"))
               {
           %>
           <div class="composed_row bcc_hide">
           <%
               }
            else
            {
           %>
            <div class="composed_row bcc_hide full_row">
           <%} %>
                 <div class="to_mail" style="cursor: pointer;" title="Select Contacts" onclick="openMyContacts('bcc')">Bcc</div>
                 <div class="composed_input bcc_row new_com_bcc">
                  <!----/// EMAIL INFO ------>
                     <div class="email_id_info_bcc"></div> 
                     <!------/// EMAIL INFO END ---->
                        <!------// After Focus div The Show Email ID ------------>
                     
                     
                     <div class="combind_email_bcc" style="display: block !important;">
                   
                     
                     
                    
                     </div>
                    <!---------/// AFTER FOCUS STARED HERE --------->
                     <!------// After Focus div The Show Email ID ------------>
                <textarea id="bcc_id" name="bcc_id"   onblur="mailSaveInDraftNew1(this.value,'hid_bcc_id')" class="enter_email_bcc "></textarea> 
                   <input type="hidden" id="hid_bcc_id" value=""/>
                    <!---------/// AFTER FOCUS STARED HERE --------->

                 </div>
                 <div class="clear"></div>
          </div>  
         <!-----------------/// BCC ROW END HERE --------------> 
               <!--------------// Subject ROW STARTED HERE ------------> 
           <div class="composed_row">
                 <div class="to_mail">Subject</div>
                 <div class="composed_input subject_row">
                         <input type="text"  value="" onblur="mailSaveInDraftSub(this.value)" id="sub_id" class="new_com_sub" />
                 </div>
                 <div class="clear"></div>
          </div>  
         <!-----------------/// Subject ROW END HERE --------------> 
           <!--------------// Attach ROW STARTED HERE ------------> 
           <div class="composed_row">
                                <div id="menu-wrap" class="composed_option attach_option">    
                                <ul id="menu">
                                      <li><a title="Attach Files" style="cursor: pointer;">Attach<img src="images/open_sub_menu.png" class="option_images" ></a>
                                           <ul>
                                              <li>
                                              
                                              
                                                    <form id="upload" class="upload" >
			                                                  <div id="drop" class="drop">
				                                                <a>My computer</a>
				                                                <input type="file" id="upl" name="upl" multiple onchange="uploadFormData()"/>
			                                                  </div>
		                                                </form>  
                                                                  
                                              </li>
                                             <!--  <li>
                                              
                                                    	<form id="upload" class="upload" >
			                                                  <div id="drop" class="drop">
				                                                <a>Attach Inline</a>
				                                                <input type="file" name="upl" multiple />
			                                                  </div>
		                                                </form>               
                                              </li> -->
                              
                                          <%-- <li>
                                                 <a onclick="openBriefcase('/<%=uid %>')"   style="cursor: pointer;">Vault</a>
                                                                  
                                              </li>  --%>
                                             <!--  <li>
                                                  <a href="">Mail</a>				  
                                              </li>
                                                <li>
                                                  <a href="">Contacts</a>				  
                                              </li> -->
                                          </ul>
                                      </li>
                                  </ul>
                                  </div>
                 <div class="composed_input attach_row new_com_attach">
                 <!-----------/// AFTER FOCUS EVENT STARED HERE ------------>
                         <!--  <input type="text" placeholder="Tip: drag and drop files from your desktop to add attachments to this message." disabled class="disable_row" > -->
                           <input type="text" placeholder="" disabled class="disable_row" style="display: none;" />  
                             <form id="upload" >
                                <ul>
                                    <!-- The file uploads will be shown here -->
                                    
                                    		                                  		
                                    		<%-- <li class="error"><span class="file_close">X</span>
                                    		
                                    		<div class="bg test">
                                    		<div class="inner" style="width: 100%;">
                                    		<span><%= array_name.get(i) %></span>
                                    		</div>
                                    		</div>
                                    		
                                    		</li> --%>
                                    		
                                  
                                    		
                                    		
                                </ul>
                             </form>
                         
                 <!------------/// AFTER FOCUS EVENT END HERE ---------------->
                 </div>
                 <div class="clear"></div>
          </div>  
         <!-----------------/// Attach ROW END HERE --------------> 
         </div>
         <!------------//// ALL EMAIL SEND OPTION END HERE ---------->
  
         <div class="written_space">
        <%
        if(signStatus.equalsIgnoreCase("TRUE"))
        {
        String cntt="<br><br>" +signcnt;
        %>
  	               <textarea cols="100" style="visibility: hidden;" id="editor1" onblur="mailSaveInDraftNew(this.value,'editor1')" name="editor1" rows="10" class="text_plugins for_plaintext"><%=cntt %></textarea>
         <%}
        else
        {
        %> 		
        <textarea cols="100" style="visibility: hidden;" id="editor1" onblur="mailSaveInDraftNew(this.value,'editor1')" name="editor1" rows="10" class="text_plugins for_plaintext"></textarea>
        <%} %>    
          </div>
     </div>
   
     <input type="hidden"  id="ck_sign" value="<%=StringEscapeUtils.escapeHtml(signcnt) %>" />
      <input type="hidden"  id="ck_sign_st" value="<%=signStatus %>" />
      <!---------------/// SETTING END HERE -----------------------> 
      
      <!-------------------/// Right View and Bottom View Tab End Here ---------------> 
      
    </div>
    
    <!--------------------/// Mail Cointer End Here ----------------->
    <div class="clear"></div>
 <input type="hidden" value="" id="hid_compose_txt" />

<!-- <script src="js/event.js" type="text/javascript" language="javascript" ></script>  -->
<!-- <script src="js/tab_event.js" type="text/javascript" language="javascript" ></script> 
<script type='text/javascript' src='js/jquery.dcjqaccordion.2.7.min.js'></script>  -->
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
<!-- <script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>  -->
<script type="text/javascript">
                                                $(document).ready(function() {
                                                    if (!$.browser.webkit) {
                                                        $('.container').jScrollPane();
                                                    }
                                                });
            </script>
                        <!------------/// CK EDITOR ----------->
                        	<script>

// The instanceReady event is fired, when an instance of CKEditor has finished
// its initialization.

/* $( document ).ready( function() {
			$( '#editor1' ).ckeditor(); // Use CKEDITOR.replace() if element is <textarea>.
		} ); */
		
		initCkeditor();
		 
		 
		 // TEST 	

 CKEDITOR.env.isCompatible = true;

function initCkeditor(){
			 
	
try
{
CKEDITOR.replace( 'editor1', {
  //  language: 'fr',
//uiColor: '#9AB8F3',

	toolbar: [		// Defines toolbar group without name.
																	// Line break - next group will be placed in new line.
{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source' ] },
{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Scayt' ] },
{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline' ] },
{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [  'NumberedList', 'BulletedList','JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
{ name: 'links', items: [ 'Link', 'Unlink', '' ] },
{ name: 'insert', items: [ 'Image',  'Table',  'Smiley', 'SpecialChar', ] },
{ name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
{ name: 'colors', items: [ 'TextColor', 'BGColor' ] }
//['newplugin']
],

height:$(window).height()-355,
enterMode: CKEDITOR.ENTER_BR,
allowedContent : true


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

/* nanospell.ckeditor('editor1',{
    dictionary : "en",  // 24 free international dictionaries  
    server : "java"      // can be php, asp, asp.net or java
 }); */

CKEDITOR.config.contentsCss = '${pageContext.request.contextPath}/css/mycss.css'; 
$('#editor1').show();

}
catch (e) {
	// TODO: handle exception
}
		 }
		
	
		 
CKEDITOR.on('dialogDefinition', function( ev ) {
	  var dialogName = ev.data.name;
	  var dialogDefinition = ev.data.definition;

	  if(dialogName === 'table') {
	    var infoTab = dialogDefinition.getContents('info');
	  
	    var border = infoTab.get('txtBorder');
	    border['default'] = "1";
	  }
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

function generate_conf_comp(type,id) {
 //alert(id);
  var n = noty({
    text        : type,
    type        : type,
    theme       : 'relax',
    dismissQueue: false,
    layout      : 'topCenter',
    theme       : 'defaultTheme',
    buttons     : (type != 'confirm') ? false : [
        {addClass: 'btn btn-primary', text: 'Yes', onClick: function ($noty) {
			
          $noty.close();
          $('.web_dialog_overlay').hide(); 
          $('#'+id).val("true").trigger('change');
		  
        }
        },
        {addClass: 'btn btn-danger', text: 'No', onClick: function ($noty) {
            $noty.close();
            $('.web_dialog_overlay').hide(); 
            $('#'+id).val("") ;
           
         /*  var n1 = noty({
                text        : 'You clicked "Cancel" button',
                type        : 'error',
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





$('#hid_compose_txt').change( function() {  
   //alert($("#hid_del").attr("data-id")); 
var r =$('#hid_compose_txt').val();

if (r == "true") {

	$('.for_plaintext').addClass('change_plaintext');
	   $('.green_plain_text_sign').show();
	   $('.green_plain_text_sign_html').hide();
	
	CKEDITOR.instances.editor1.destroy();
	$('.for_plaintext').addClass('textArea');
	$('.textArea').css('height', window.left_scollx-350 );
	var cntt = $("#editor1").val();
	$("#editor1").val( $(cntt).text());
		
					
	
}
});
		 
		 
		 
/// PLAIN TEXT EVENT HERE 
$('.plain_text').click(function(){
	
	//var r = confirm("Delete this folder and everything in it?");
	$('.web_dialog_overlay').show(); 
	var confirm = generate_conf_comp('confirm','hid_compose_txt');
	$.noty.setText(confirm.options.id, 'Switching to the plain text editor will cause all text formatting to be lost. Do you want to continue?'); // same as alert.setText('Text Override')
	
	$('#hid_editor_type').val("plain");
				  
	               /* var msg="Task has been changed";
				   var success = generate('success');
					 $.noty.setText(success.options.id, msg);
					 setTimeout(function () {  $.noty.close(success.options.id); }, 5000); */
	
	
	
	

	});


 	/// HTML TEXT STRED HERE 

$('.html_text').click(function(){

	var cntt=$('#editor1').val();
	cntt=cntt.replace(/\n/g , "<br>");
	initCkeditor();
	$('.for_plaintext').removeClass('textArea');
	  $('.for_plaintext').removeClass('change_plaintext');
		 $(this).children().children('.green_plain_text_sign_html').show();
		 $('.green_plain_text_sign').hide();
		 $('#hid_editor_type').val("html");
		 $('#editor1').val(cntt);
		 
		    /*  var msg="Task has been changed";
		     var success = generate('success');
			 $.noty.setText(success.options.id, msg);
			 setTimeout(function () {  $.noty.close(success.options.id); }, 5000); */
	});

/// HTML TEXT END HERE 


	</script>
            <!------------/// CK EDITOR END HERE ------->
            
        <!-----------/// FILE UPLOAD STARTED HERE -------------->
      <!--  <script type="text/javascript" src="js_file_upload/js/jquery.knob.js"></script>
         <script type="text/javascript" src="js_file_upload/js/jquery.knob1.js"></script> -->
		<!-- jQuery File Upload Dependencies -->
		<!-- <script type="text/javascript" src="js_file_upload/js/jquery.ui.widget.js" ></script>
		<script type="text/javascript" src="js_file_upload/js/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="js_file_upload/js/jquery.fileupload.js"></script>
	 -->
		<!-- Our main JS file -->
	
        
        <!----------/// FILE UPLOAD  END HERE ------------------->      


<div class="briefcase_box">
         <!---// HEADING --->
           <div class="pop_header_brief">Briefcase <span class="cancel_briefcase">X</span></div>
           <div id="mybrief">
           
           </div>
            <!----// BOTTOM PART ---->
           <div class="bottom_option_edms">
              <!-----// Bottom_left------>
               <div class="bottom_leftbrief">
                     <ul>
                         <li onclick="attach_file_frombriefcase()" class="button_main insert_color ">Insert</li>
                         <li class="button_main">Cancel</li>
                         
                     </ul>
               
               </div>
             <!--------// Bottom_left ----->
             <!-- <div class="center_text_bottom">
                 Because you selected more than 25 MB to attach to this email, the selected attachments will be shared via a Drive link.
             </div> -->
             <!---// Bottom_right ---->
             <!--  <div class="bottom_right">
                   <ul>
                      <li class="insert_line">Insert as</li>
                      <li class="button_main">Drive link</li>
                      <li class="button_main">Attachment</li>
                   </ul>
              
              </div> -->
             <!---// Bottom_right_end--->
           </div>
           <!-------// BOTTOM END HERE --->
           </div>
            
         
         <input type="hidden" id="hid_con_popup" value="" />
         <div class="mycontact_box" style="display: none;">
         <!---// HEADING --->
           <div class="pop_header_brief" style=" height: 19px;">
           <div id="contactLst" style="       margin-top: -3px;    float: left;">
           <select id="conPopupList" onchange="getAllPopupContacts(this.value)">
           <option value="">Personal Contacts</option>
           </select>
           </div>
           <div>
           <input type="text" id="con_searchnp" value="" placeholder="search here" style="width: 250px"/>
           <span class="cont_search" onclick="getAllPopupContactsSearch()"> </span>
          </div>
            <span class="cancel_briefcase">X</span></div>
           <div id="mycontact" style="    overflow-x: scroll;    height: 90%;">
           
           </div>
            <!----// BOTTOM PART ---->
           <div class="bottom_option_edms">
              <!-----// Bottom_left------>
               <div class="bottom_leftbrief">
                     <ul>
                         <li onclick="insertContacts('to')" class="button_main insert_color " style="margin-right: 1px !important;">To</li>
                         <li onclick="insertContacts('cc')" class="button_main insert_color " style="margin-right: 1px !important;">Cc</li>
                         <li onclick="insertContacts('bcc')" class="button_main insert_color " style="margin-right: 1px !important;">Bcc</li>
                         <li class="button_main">Cancel</li>
                         
                     </ul>
               
               </div>
            
             <!---// Bottom_right_end--->
           </div>
           <!-------// BOTTOM END HERE --->
           </div>   
             <div class="web_dialog_overlay_setting"></div>
</body>
</html>