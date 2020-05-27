<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
function clearCompose() {
	try
	{
	
		if($('#div_for_compose').css('display')=='block')
	   {

			$("#hid_to_id").val(""); 
			$("#to_id").val("");
			$('.combind_email').empty();

			$("#hid_cc_id").val(""); 
			$("#cc_id").val(""); 
			$('.combind_email_cc').empty();

			$("#hid_bcc_id").val(""); 
			$("#bcc_id").val(""); 
			$('.combind_email_bcc').empty();

			$("#sub_id").val("");
			$('#upload > ul').empty();

			try
			{
			var oEditor = CKEDITOR.instances;
			oEditor.editor1.setData("");
			}
			catch (e) {
			$("#editor1").val("");
			}
	   }
	}catch (ee) {
		// TODO: handle exception
	}
}
function mailCompose() {
	//alert('hi');
	var cview=$("#hid_composePage_after_set").val();
	if(cview=="Popup View")
		{
		var uid=Math.random(); 
		window.open('composepopup', "popupWindow"+uid, "width=1050, height=700, scrollbars=yes");

		}
	else
		{
	document.getElementById('div_for_inbox').style.display= 'none';
				try
				{
					
					if($('.green_plain_text_sign').css('display')=='block')
						{
						initCkeditor();
						$('.for_plaintext').removeClass('textArea');
						  $('.for_plaintext').removeClass('change_plaintext');
							 $('.green_plain_text_sign_html').show();
							 $('.green_plain_text_sign').hide();
							 $('#hid_editor_type').val("html");
						}
				}
				catch (ee) {
					// TODO: handle exception
				}
				
				try
				{
					$(".priority_check").removeClass("priority_check");
					$("#hid_priority").val("");
				}
				catch (eee) {
					// TODO: handle exception
				}
				
				try
				{
					$('.green_plain_text_read').hide();
					$("#hid_read_rec").val("0");
				}
				catch (eeee) {
					// TODO: handle exception
				}
				var sigct=$("#ck_sign").val();
				var sigst=$("#ck_sign_st").val();
				if(sigst=="true" || sigst=="TRUE" || sigst=="True")
					{
				try
				{
				var oEditor = CKEDITOR.instances;
				oEditor.editor1.setData("<br><br><br>"+sigct);
				}
				catch (e) {
					// TODO: handle exception
					sigct=$(sigct).text();
					$("#editor1").val("\n\n"+sigct);
				
				}
					}
				
				       if($('.bcc_hide').css('display')=='none')
						   {
							   /* $('.bcc_hide').addClass('full_row');
							   $('.green_sign').css('display','block'); */
					       }	
						else
							{
							/* $('.bcc_hide').removeClass('full_row');
								$('.green_sign').css('display','none'); */
							}   
			
				     
            	document.getElementById('div_for_compose').style.display= 'block';
            	  $('#to_id').focus();
	
	/* document.getElementById('action_gif').style.display= 'block';
	var requestPage="${pageContext.request.contextPath}/composeMailView";
	jQuery.get(requestPage,
            
          {
            	cache: true 
          },
                    function( data ) {
            	document.getElementById('div_for_inbox').style.display= 'none';
            	document.getElementById('div_for_compose').style.display= 'block';
            	document.getElementById('action_gif').style.display= 'none';
             $( "#div_for_compose" ).html( data );
             $("#hid_to_mail_uid").val("");
             $("#hid_upload_file_size").val("");
             
             var cntt ="";
             if($('#hid_editor_type').val()!="plain")
     		{
             cntt = CKEDITOR.instances['editor1'].getData();
     		}
             else
            	 {
            	 cntt = $('#editor1').val();
            	 }
             $('#to_id').focus();
              var miliSecond = 30*1000;
             $("#hid_active_compose").val("true");
            // setTimeout(function(){ ckadd(); }, 1000); 
             setTimeout(function(){ autoDraftSetTime(cntt,false); }, miliSecond); 
            
            
             
            }
	); */
		}
}

/* function ckadd() {
	var ck_sign=$("#ck_sign").val();
  	//alert(cntt)
  	var cntt = CKEDITOR.instances['editor1'].getData();
  	cntt="<br><div style='padding-left: 5px; border-left: #1010ff 2px solid;  margin-left: 5px;  width: 98%;'>"+cntt+"</div>";
  	cntt=cntt+"<br>"+ck_sign;
  	var oEditor = CKEDITOR.instances;
	oEditor.editor1.setData(cntt); 
     
} */

function autoDraftSetTime(cntt, sts) {
	var act_com= $("#hid_active_compose").val();
	//alert(act_com);
	if(act_com!=null && act_com!="")
		{
		var new_cntt="";
		if($('#hid_editor_type').val()!="plain")
 		{
		 new_cntt=  CKEDITOR.instances['editor1'].getData();
 		}
		else
			{
			new_cntt=  $('#editor1').val();
			}
	// alert(cntt)
	 //alert(new_cntt)
	 if(sts== true)
		 {
	if(cntt==new_cntt)
		{
		console.info('same');
		
		}
	else
		{
		console.info('not same');
		 mailSaveInDraft()
		
		}
	}
	
	 	var miliSecond = 30*1000;
	 	setTimeout(function(){ autoDraftSetTime(new_cntt, true); }, miliSecond);
		}
	
}
 
 
 
function backFromCompose() {
	
	$("#hid_active_compose").val("");
		$("#hid_to_draft_mail_uid").val("");
		$("#hid_to_mail_uid").val("");
		
		document.getElementById('div_for_inbox').style.display=  'block'; 
		clearCompose();
		document.getElementById('div_for_compose').style.display='none'; 
}
	
	
function backFromComposeNew() {
	//alert("hi")
	
	
	
	  $('.web_dialog_overlay').show(); 
		var confirm = generate_savedraft('confirm');
   	$.noty.setText(confirm.options.id, 'Do you want to save your message in Draft?');
	
}


function discardCompose(){
	 $('.web_dialog_overlay').show(); 
		var confirm = generate_backcomp('confirm');
  	$.noty.setText(confirm.options.id, 'Do you want to discard your message?');
}


function delImage(){
	 $('.web_dialog_overlay').show(); 
		var confirm = generate_delImage('confirm');
 	$.noty.setText(confirm.options.id, 'Do you want to remove your picture?');
}


function generate_delImage(type) {
	 //  alert(id);
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
			 document.getElementById('action_gif').style.display= 'block';
			 $.ajax({
		         type: "POST",
		         url: "${pageContext.request.contextPath}/delDPImage",
		        
		        /*  contentType: "application/json", */
		         success: function (data) {
		        	 
		        	 var mid=$("#hid_logedin_id").val();
		 	    	var sml_id=mid+"nomyimage";
		 	    	var big_id="big_"+mid+"nomyimage";
		 	    	var set_id="set_"+mid+"nomyimage";
		 	    	try
		 	    	{
		 	    	document.getElementById(sml_id).src="images/blank_man.jpg";
		 	    	document.getElementById(big_id).src="images/blank_man.jpg";
		 	    	document.getElementById("chat_id_bd").src="chat/photo.jpg";
		 	    	document.getElementById("chat_id").src="chat/photo.jpg";
		 	    	
		 	    		document.getElementById(set_id).src="images/blank_man.jpg";
		 	    	}
		 	    	catch(err) {
		 	    		
		 	    	}
		 	    	 
		 	    	document.getElementById('action_gif').style.display= 'none';
		         }
			 });
}
         },
         {addClass: 'btn btn-danger', text: 'No', onClick: function ($noty) {

			 $noty.close();
			 $('.web_dialog_overlay').hide(); 
        
		}
         }
     ]
 });

     
     //console.log(type + ' - ' + n.options.id);
     return n; 
      
 }

function generate_savedraft(type) {
	 //  alert(id);
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
				var cntt = CKEDITOR.instances['editor1'].getData();
		 		}
				else
					{
					var cntt = $('#editor1').val();
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
				         data: {'uid': uid,'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt, 'texttype': texttype },
				        /*  contentType: "application/json", */
				         success: function (data) {
				            
				            window.draftProgress=true;
				           // alert(data);
				            if(data=="<$expire$>")
							{
							location.href ="${pageContext.request.contextPath}/inbox";
								}
				            getDraftMailCount();
				             
				            $('.web_dialog_overlay').hide(); 
				            $("#hid_active_compose").val("");
				      		$("#hid_to_draft_mail_uid").val("");
				      		$("#hid_to_mail_uid").val("");
				      		
				      		document.getElementById('div_for_inbox').style.display=  'block'; 
				      		clearCompose();
				      		document.getElementById('div_for_compose').style.display='none'; 
				      		if($("#search_pagi").css('display')=='none')
				      		{
				      		var fdrname=document.getElementById('hid_active_fldr').value;
				      		//alert(fdrname)
				      		$('.active_left_tree').css('border-left-color','#fff');
							$('.active_left_tree').removeClass('active_left_tree');
							$("#fldr_"+fdrname).addClass('active_left_tree');
							$(".active_left_tree").css("border-left","3px solid "+$("#hid_mail_bgColor").val());
							if(fdrname=="Drafts" || fdrname=="drafts")
						  		getWebmailInbox( fdrname);
				      		}
				         }
				     });
						}
					else
						{
						
						getDraftMailCount();
			             
			            $('.web_dialog_overlay').hide(); 
			            $("#hid_active_compose").val("");
			      		$("#hid_to_draft_mail_uid").val("");
			      		$("#hid_to_mail_uid").val("");
			      		
			      		document.getElementById('div_for_inbox').style.display=  'block'; 
			      		clearCompose();
			      		document.getElementById('div_for_compose').style.display='none'; 
			      		if($("#search_pagi").css('display')=='none')
			      		{
			      		var fdrname=document.getElementById('hid_active_fldr').value;
			      		//alert(fdrname)
			      		$('.active_left_tree').css('border-left-color','#fff');
						$('.active_left_tree').removeClass('active_left_tree');
						$("#fldr_"+fdrname).addClass('active_left_tree');
						$(".active_left_tree").css("border-left","3px solid "+$("#hid_mail_bgColor").val());
						if(fdrname=="Drafts" || fdrname=="drafts")
					  		getWebmailInbox( fdrname);
			      		}
						
						}
				}
				else
					{
					 $('.web_dialog_overlay').hide(); 
			         $("#hid_active_compose").val("");
			   		$("#hid_to_draft_mail_uid").val("");
			   		$("#hid_to_mail_uid").val("");
			   		
			   		document.getElementById('div_for_inbox').style.display=  'block'; 
			   		clearCompose();
			   		document.getElementById('div_for_compose').style.display='none';
			   		if($("#search_pagi").css('display')=='none')
		      		{
			   		var fdrname=document.getElementById('hid_active_fldr').value;
			   		//alert(fdrname)
			   		$('.active_left_tree').css('border-left-color','#fff');
					$('.active_left_tree').removeClass('active_left_tree');
					$("#fldr_"+fdrname).addClass('active_left_tree');
					$(".active_left_tree").css("border-left","3px solid "+$("#hid_mail_bgColor").val());
					if(fdrname=="Drafts" || fdrname=="drafts")
				  		getWebmailInbox( fdrname);
					}
					}
			 
        
          }
          },
          {addClass: 'btn btn-danger', text: 'No', onClick: function ($noty) {

 			 $noty.close();
         
 			 
 		var duid=	 $("#hid_to_draft_mail_uid").val();
 		var muid=  		$("#hid_to_mail_uid").val();
 			// alert("duid="+duid);
 			// alert("muid="+muid);
 			 
 			 if(duid!="" || muid!="" )
 				 {
 				 var uid=duid;
 				 if(muid!=null && muid!="")
 					 {
 				 	if(uid!=null && uid!="")
 					 {
 				 		uid=uid+"-"+muid;
 					 }
 				 	else
 				 		{
 				 		uid=muid;
 				 		}
 					 }
 				 document.getElementById('action_gif').style.display= 'block';
 				 var requestPage="${pageContext.request.contextPath}/deleteDraftMail";
 					jQuery.get(requestPage,
 				                            {
 				                    'uid' : uid
 				            },
 				                    function( data ) {
 				            	if(data=="<$expire$>")
 				           	{
 				           	location.href ="${pageContext.request.contextPath}/inbox";
 				           		} 
 				            	$('.web_dialog_overlay').hide(); 
 						         $("#hid_active_compose").val("");
 						  		$("#hid_to_draft_mail_uid").val("");
 						  		$("#hid_to_mail_uid").val("");
 						  		document.getElementById('action_gif').style.display= 'none';
 						  		document.getElementById('div_for_inbox').style.display=  'block';
 						  		clearCompose();
 						  		document.getElementById('div_for_compose').style.display='none';
 						  		getDraftMailCount();
 						  		
 						  		if($("#search_pagi").css('display')=='none')
 						  			{
 							  		var fdrname=document.getElementById('hid_active_fldr').value;
 							  		//alert(fdrname)
 							  		$('.active_left_tree').css('border-left-color','#fff');
 									$('.active_left_tree').removeClass('active_left_tree');
 									$("#fldr_"+fdrname).addClass('active_left_tree');
 									$(".active_left_tree").css("border-left","3px solid "+$("#hid_mail_bgColor").val());
 							  		if(fdrname=="Drafts" || fdrname=="drafts")
 							  		getWebmailInbox( fdrname);
 						  			}
 				            });
 				 
 				 }
 			 else
 				 {
 				  $('.web_dialog_overlay').hide(); 
 			        $("#hid_active_compose").val("");
 			  		$("#hid_to_draft_mail_uid").val("");
 			  		$("#hid_to_mail_uid").val("");
 			  		
 			  		document.getElementById('div_for_inbox').style.display=  'block'; 
 			  		clearCompose();
 			  		document.getElementById('div_for_compose').style.display='none';
 			  		if($("#search_pagi").css('display')=='none')
 		  			{
 			  		var fdrname=document.getElementById('hid_active_fldr').value;
 			  		//alert(fdrname)
 			  		$('.active_left_tree').css('border-left-color','#fff');
 					$('.active_left_tree').removeClass('active_left_tree');
 					$("#fldr_"+fdrname).addClass('active_left_tree');
 					$(".active_left_tree").css("border-left","3px solid "+$("#hid_mail_bgColor").val());
 		  			}
 				 }
        
           
           
          }
          }
      ]
  });
 
      
      //console.log(type + ' - ' + n.options.id);
      return n; 
       
  }

function generate_backcomp(type) {
	 //  alert(id);
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
        
			 
		var duid=	 $("#hid_to_draft_mail_uid").val();
		var muid=  		$("#hid_to_mail_uid").val();
			// alert("duid="+duid);
			// alert("muid="+muid);
			 
			 if(duid!="" || muid!="" )
				 {
				 var uid=duid;
				 if(muid!=null && muid!="")
					 {
				 	if(uid!=null && uid!="")
					 {
				 		uid=uid+"-"+muid;
					 }
				 	else
				 		{
				 		uid=muid;
				 		}
					 }
				 document.getElementById('action_gif').style.display= 'block';
				 var requestPage="${pageContext.request.contextPath}/deleteDraftMail";
					jQuery.get(requestPage,
				                            {
				                    'uid' : uid
				            },
				                    function( data ) {
				            	if(data=="<$expire$>")
				            	{
				            	location.href ="${pageContext.request.contextPath}/inbox";
				            		}
				            	$('.web_dialog_overlay').hide(); 
						         $("#hid_active_compose").val("");
						  		$("#hid_to_draft_mail_uid").val("");
						  		$("#hid_to_mail_uid").val("");
						  		document.getElementById('action_gif').style.display= 'none';
						  		document.getElementById('div_for_inbox').style.display=  'block'; 
						  		clearCompose();
						  		document.getElementById('div_for_compose').style.display='none';
						  		getDraftMailCount();
						  		
						  		if($("#search_pagi").css('display')=='none')
						  			{
							  		var fdrname=document.getElementById('hid_active_fldr').value;
							  		//alert(fdrname)
							  		$('.active_left_tree').css('border-left-color','#fff');
									$('.active_left_tree').removeClass('active_left_tree');
									$("#fldr_"+fdrname).addClass('active_left_tree');
									$(".active_left_tree").css("border-left","3px solid "+$("#hid_mail_bgColor").val());
							  		if(fdrname=="Drafts" || fdrname=="drafts")
							  		getWebmailInbox( fdrname);
						  			}
				            });
				 
				 }
			 else
				 {
				  $('.web_dialog_overlay').hide(); 
			        $("#hid_active_compose").val("");
			  		$("#hid_to_draft_mail_uid").val("");
			  		$("#hid_to_mail_uid").val("");
			  		
			  		document.getElementById('div_for_inbox').style.display=  'block'; 
			  		clearCompose();
			  		document.getElementById('div_for_compose').style.display='none';
			  		if($("#search_pagi").css('display')=='none')
		  			{
			  		var fdrname=document.getElementById('hid_active_fldr').value;
			  		//alert(fdrname)
			  		$('.active_left_tree').css('border-left-color','#fff');
					$('.active_left_tree').removeClass('active_left_tree');
					$("#fldr_"+fdrname).addClass('active_left_tree');
					$(".active_left_tree").css("border-left","3px solid "+$("#hid_mail_bgColor").val());
		  			}
				 }
       
         /*
           $('.web_dialog_overlay').hide(); 
         $("#hid_active_compose").val("");
  		$("#hid_to_draft_mail_uid").val("");
  		$("#hid_to_mail_uid").val("");
  		
  		document.getElementById('div_for_inbox').style.display=  'block'; 
  		document.getElementById('div_for_compose').style.display='none'; */ 
          }
          },
          {addClass: 'btn btn-danger', text: 'No', onClick: function ($noty) {
              $noty.close();
           
              $('.web_dialog_overlay').hide(); 
       /*      var n1 = noty({
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



</script>
<script type="text/javascript">
function addTag() {
	var tagnm="";
	document.getElementById('action_gif').style.display= 'block';
	var chktag = document.getElementsByName('my_chk_tag');
	for(var i=0; chktag[i]; ++i)
	{
	      if(chktag[i].checked)
	      {
	    	  if(tagnm=="")
	    		  {
	    		  tagnm=chktag[i].value;
	    		  }
	    	  else
	    		  {
	    		  tagnm=tagnm+"~"+chktag[i].value;
	    		  }
			  	
		}
	}
	var uids="";
	var inputElements1 = document.getElementsByName('unseen-stared');
	for(var i=0; inputElements1[i]; ++i){
	      if(inputElements1[i].checked){
				if(uids=="")
					{
					uids=inputElements1[i].value;
					}
				else
					{
					uids=uids+"-"+inputElements1[i].value;
					}
	          
	      }
	}

	var inputElements2 = document.getElementsByName('unseen-unstared');
	for(var i=0; inputElements2[i]; ++i){
	      if(inputElements2[i].checked){
	    	  if(uids=="")
				{
				uids=inputElements2[i].value;
				}
			else
				{
				uids=uids+"-"+inputElements2[i].value;
				}
	      }
	}

	var inputElements3 = document.getElementsByName('seen-unstared');
	for(var i=0; inputElements3[i]; ++i){
	      if(inputElements3[i].checked){
	    	  if(uids=="")
				{
				uids=inputElements3[i].value;
				}
			else
				{
				uids=uids+"-"+inputElements3[i].value;
				}
	      }
	}


	var inputElements4 = document.getElementsByName('seen-stared');
	for(var i=0; inputElements4[i]; ++i){
	      if(inputElements4[i].checked){
	    	  if(uids=="")
				{
				uids=inputElements4[i].value;
				}
			else
				{
				uids=uids+"-"+inputElements4[i].value;
				}
	      }
	}
	if(uids==null || uids=="")
	{
	uids=$("#hid_mail_disp_count").val();
	}
	//alert("ids="+uids);
	//alert(tagnm)
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/setWebmailTag",
		data : {
			'tagsnm' : tagnm,
			'uids' : uids
		},
		contentType : "application/json",
		success : function(data) {
			//alert(data)
			if(data=="<$expire$>")
	{
	location.href ="${pageContext.request.contextPath}/inbox";
		}
			document.getElementById('action_gif').style.display= 'none';
			//document.getElementById('hid_no_cnt').style.display= 'none';
			//$("#div_left_cnt").html(data);
			
		}
	});
}
</script>
<script type="text/javascript">
function remMailTagFull(id) {
	//alert("id="+id);
	document.getElementById('action_gif').style.display= 'block';
	var arr=id.split("-");
	var nm=arr[1];
	var lastarr=nm.split("~");
	var uid=lastarr[0];
	var tagnm=lastarr[1];

	
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/removeWebmailTag",
		data : {
			'tagnm' : tagnm,
			'uid' : uid
		},
		contentType : "application/json",
		success : function(data) {
		//	alert(data)
		if(data=="<$expire$>")
		{
		location.href ="${pageContext.request.contextPath}/inbox";
			}
			if(data=="true")
				{
				try
				{
				var id1="full_"+arr[1];
				document.getElementById(id1).remove();
				}
				catch (e) {
					// TODO: handle exception
				}
				try
				{
				var id2="list_"+arr[1];
				document.getElementById(id2).remove();
				}
				catch (e) {
					// TODO: handle exception
				}
				}
			document.getElementById('action_gif').style.display= 'none';
		}
	});
	
	
	
}
function remMailTagHalf(id) {
	//alert("id="+id);
	document.getElementById('action_gif').style.display= 'block';
	var arr=id.split("-");
	var nm=arr[1];
	var lastarr=nm.split("~");
	var uid=lastarr[0];
	var tagnm=lastarr[1];
	
	
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/removeWebmailTag",
		data : {
			'tagnm' : tagnm,
			'uid' : uid
		},
		contentType : "application/json",
		success : function(data) {
			//alert(data)
			if(data=="<$expire$>")
			{
			location.href ="${pageContext.request.contextPath}/inbox";
			}
			if(data=="true")
				{
				
				try
				{
				var id1="disp_"+arr[1];
				document.getElementById(id1).remove();
				}
				catch (e) {
					// TODO: handle exception
				}
				try
				{
				var id2="list_"+arr[1];
				document.getElementById(id2).remove();
				}
				catch (e) {
					// TODO: handle exception
				}
				}
			document.getElementById('action_gif').style.display= 'none';
		}
	});
	
	
}
</script>
<script type="text/javascript">
function openSettingTemp() {
	document.getElementById('action_gif').style.display= 'block';
	var requestPage="${pageContext.request.contextPath}/settingOpenTemp";
	jQuery.get(requestPage,
            /*                  {
                    'path' : folderPath
            }, */
                    function( data ) {
            	document.getElementById('div_for_inbox').style.display= 'none';
            	document.getElementById('div_for_setting').style.display= 'block';
            	document.getElementById('action_gif').style.display= 'none';
            //alert(data);
            	$( "#div_for_setting" ).html( data );
            	$(".search_button").css("background-image","none");
    			$(".search_button").css("background-color",$("#hid_mail_bgColor").val());
    			 $('#hid_open_setting').val("true");
             var val = $('#accountSetting').val();
    	     val = val.replace(/\'/g, '\"');
    	     obj = JSON.parse(val);
    	     //alert(val);
    	      var np_pane=true;
    	     var np_dsn=true;
    	      $.each(obj, function(key,value) {
    	    	  
    	    
    	    	  if( value.name=="generalSettingNotification")
	    		  {
	    		  np_dsn=false;
	    		  }
	    	  if( value.name=="previewPane")
    		  {
	    		  np_pane=false;
    		  }

    	                  if ($('input[name="' + value.name + '"][value="' + value.value + '"]').length > 0)
    	                  {
    	                    
    	               $('input[name="' + value.name + '"][value="' + value.value + '"]').prop('checked', true);
    	                  }
    	                  else{
    	               $("[name=" + value.name + "]").val(value.value);
    	                  }
    	                  
    	                }); 
    	      try
    	      {
    	    	  if(np_pane)
    	    		  {
    	    		  $('input[name="previewPane"][value="Vertical view"]').prop('checked', true);
    	    		  }
    	      }
    	      catch (e) {
				// TODO: handle exception
			}
    	      
    	      try
    	      {
    	    	  if(np_dsn)
    	    		  {
    	    		  $('input[name="generalSettingNotification"][value="New mail notifications on"]').prop('checked', true);
    	    		  }
    	      }
    	      catch (e) {
				// TODO: handle exception
			}
             
            });
}



function openSettingProfile() {
	document.getElementById('action_gif').style.display= 'block';
	var requestPage="${pageContext.request.contextPath}/settingOpenTemp";
	jQuery.get(requestPage,
            /*                  {
                    'path' : folderPath
            }, */
                    function( data ) {
            	document.getElementById('div_for_inbox').style.display= 'none';
            	document.getElementById('div_for_setting').style.display= 'block';
            	document.getElementById('action_gif').style.display= 'none';
            //alert(data);
            	$( "#div_for_setting" ).html( data );
            	$(".search_button").css("background-image","none");
    			$(".search_button").css("background-color",$("#hid_mail_bgColor").val());
             var val = $('#accountSetting').val();
    	     val = val.replace(/\'/g, '\"');
    	     obj = JSON.parse(val);
    	     //alert(val);
    	     var np_pane=true;
    	     var np_dsn=true;
    	      $.each(obj, function(key,value) {
    	              
    	    	  if( value.name=="generalSettingNotification")
    	    		  {
    	    		  np_dsn=false;
    	    		  }
    	    	  if( value.name=="previewPane")
	    		  {
    	    		  np_pane=false;
	    		  }
    	    	  
    	                  if ($('input[name="' + value.name + '"][value="' + value.value + '"]').length > 0)
    	                  {
    	                    
    	               $('input[name="' + value.name + '"][value="' + value.value + '"]').prop('checked', true);
    	                  }
    	                  else{
    	               $("[name=" + value.name + "]").val(value.value);
    	                  }
    	                  
    	                }); 
    	      
    	      try
    	      {
    	    	  if(np_pane)
    	    		  {
    	    		  $('input[name="previewPane"][value="Vertical view"]').prop('checked', true);
    	    		  }
    	      }
    	      catch (e) {
				// TODO: handle exception
			}
    	      
    	      try
    	      {
    	    	  if(np_dsn)
    	    		  {
    	    		  $('input[name="generalSettingNotification"][value="New mail notifications on"]').prop('checked', true);
    	    		  }
    	      }
    	      catch (e) {
				// TODO: handle exception
			}
    	      
    	      $('.view_1').removeClass('setting_active');
    	      $('.view_3').addClass('setting_active');
    	      $('#view1').hide();
    	      $('#view3').css("display","block");
    	      $('#view2').hide();
    	      $('#view4').hide();
    	      $('#view5').hide();
    	      $('#view6').hide();
    	      $('#view7').hide();
             
            });
}



function openSettingFilter() {
	document.getElementById('action_gif').style.display= 'block';
	var requestPage="${pageContext.request.contextPath}/settingOpenTemp";
	jQuery.get(requestPage,
            /*                  {
                    'path' : folderPath
            }, */
                    function( data ) {
            	document.getElementById('div_for_inbox').style.display= 'none';
            	document.getElementById('div_for_setting').style.display= 'block';
            	document.getElementById('action_gif').style.display= 'none';
            //alert(data);
            	$( "#div_for_setting" ).html( data );
            	$(".search_button").css("background-image","none");
    			$(".search_button").css("background-color",$("#hid_mail_bgColor").val());
             var val = $('#accountSetting').val();
             var np_pane=true;
    	     var np_dsn=true;
             
    	     val = val.replace(/\'/g, '\"');
    	     obj = JSON.parse(val);
    	     //alert(val);
    	      $.each(obj, function(key,value) {
    	              
    	    	  if( value.name=="generalSettingNotification")
	    		  {
	    		  np_dsn=false;
	    		  }
	    	  if( value.name=="previewPane")
    		  {
	    		  np_pane=false;
    		  }
        
    	    	  
    	    	  if ($('input[name="' + value.name + '"][value="' + value.value + '"]').length > 0)
    	                  {
    	                    
    	               $('input[name="' + value.name + '"][value="' + value.value + '"]').prop('checked', true);
    	                  }
    	                  else{
    	               $("[name=" + value.name + "]").val(value.value);
    	                  }
    	                  
    	                });
    	      
    	      try
    	      {
    	    	  if(np_pane)
    	    		  {
    	    		  $('input[name="previewPane"][value="Vertical view"]').prop('checked', true);
    	    		  }
    	      }
    	      catch (e) {
				// TODO: handle exception
			}
    	      
    	      try
    	      {
    	    	  if(np_dsn)
    	    		  {
    	    		  $('input[name="generalSettingNotification"][value="New mail notifications on"]').prop('checked', true);
    	    		  }
    	      }
    	      catch (e) {
				// TODO: handle exception
			}
    	      
    	      $('.view_1').removeClass('setting_active');
    	      $('.view_5').addClass('setting_active');
    	      $('#view1').hide();
    	      $('#view5').css("display","block");
    	      $('#view2').hide();
    	      $('#view3').hide();
    	      $('#view4').hide();
    	      $('#view6').hide();
    	      $('#view7').hide();
             
            });
}


function backFromSetting() {
	document.getElementById('action_gif').style.display= 'block';
	var lm1=$("#hid_limitMail_after_set").val();
	var pp1=$("#hid_previewPane_after_set").val();
	var lm2=$("#hid_limitMail_before_set").val();
	var pp2=$("#hid_previewPane_before_set").val();
	if(lm1!=lm2 || pp1!=pp2)
		{
		location.href ="${pageContext.request.contextPath}/inbox";
		}
	else
		{
		document.getElementById('div_for_inbox').style.display=  'block'; 
		document.getElementById('div_for_setting').style.display='none';
		document.getElementById('action_gif').style.display= 'none';
		}
	//getWebmailInboxRefresh();
}
</script>
<script type="text/javascript">
function openReply() 
{
	var cnt= getSelectdMailUid();
	if(cnt==null || cnt=="")
		{
		cnt=$("#hid_mail_disp_count").val();
		}
	
	
	var arr=cnt.split("-");
	if(arr.length==1)
		{
		var uid=arr[0];
		//alert(uid);
	
		var fdrname=document.getElementById('hid_active_fldr').value;
		
		document.getElementById('action_gif').style.display= 'block';
		var requestPage="${pageContext.request.contextPath}/composeMailReply";
		
	
		jQuery.post(requestPage,
	                            {
	                   "uid": uid, "fdrname": fdrname, "repType": "Reply"
	               
	            }, 
	            
	              function( data ) {
	            	document.getElementById('div_for_inbox').style.display= 'none';
	            	document.getElementById('div_for_compose').style.display= 'block';
	            	document.getElementById('action_gif').style.display= 'none';
	             $( "#div_for_compose" ).html( data );
	            // setTimeout(function(){ ckadd(); }, 500); 
	            /*  var editor = CKEDITOR.instances.editor1;
	             editor.focusManager.focus(); */
	             
	             setTimeout(function(){ CKEDITOR.instances.editor1.focus(); }, 1200); 
	            }
	            
	            
		);
		}
	
}

function openReplyAll() {
	var cnt= getSelectdMailUid();
	if(cnt==null || cnt=="")
	{
	cnt=$("#hid_mail_disp_count").val();
	}
	var arr=cnt.split("-");
	if(arr.length==1)
		{
		var uid=arr[0];
		
		var fdrname=document.getElementById('hid_active_fldr').value;
		document.getElementById('action_gif').style.display= 'block';
		var requestPage="${pageContext.request.contextPath}/composeMailReply";
		jQuery.post(requestPage,
	            {
	                 "uid": uid, "fdrname": fdrname, "repType": "ReplyAll"

	            }, 
	                    function( data ) {
	            	document.getElementById('div_for_inbox').style.display= 'none';
	            	document.getElementById('div_for_compose').style.display= 'block';
	            	document.getElementById('action_gif').style.display= 'none';
	             $( "#div_for_compose" ).html( data );
	            // setTimeout(function(){ ckadd(); }, 500); 
	            // setTimeout(function(){ CKEDITOR.instances.editor1.focus(); }, 1200);
	             $('#sub_id').focus();
	            });
		}
}


function openForword()
{
	var cnt= getSelectdMailUid();
	if(cnt==null || cnt=="")
	{
	cnt=$("#hid_mail_disp_count").val();
	}
	var arr=cnt.split("-");
	if(arr.length==1)
		{
		var uid=arr[0];

		var fdrname=document.getElementById('hid_active_fldr').value;
		document.getElementById('action_gif').style.display= 'block';
		var requestPage="${pageContext.request.contextPath}/composeMailForword";
		jQuery.post(requestPage,
	            {
	                "uid": uid, "fdrname": fdrname
	            }, 
	                    function( data ) {
	            	document.getElementById('div_for_inbox').style.display= 'none';
	            	document.getElementById('div_for_compose').style.display= 'block';
	            	document.getElementById('action_gif').style.display= 'none';
	             $( "#div_for_compose" ).html( data );
	            //setTimeout(function(){ ckadd(); }, 1000); 
	             $('#to_id').focus();
	            });
		}	
}


function openComposeWithTo(hid_to) 
{
	
	
		var uid="";
		var hid_sub="";
		var hid_cnt="" ;
		var fdrname=document.getElementById('hid_active_fldr').value;
		
		document.getElementById('action_gif').style.display= 'block';
		var requestPage="${pageContext.request.contextPath}/openComposeWithTo";
		
	
		jQuery.post(requestPage,
	                            {
	                    'hid_to' : hid_to, "hid_cc": "" , "hid_sub": hid_sub , "hid_cnt": hid_cnt, "hid_attch_status": "No", "uid": uid, "fdrname": fdrname
	               
	            }, 
	            
	              function( data ) {
	            	document.getElementById('div_for_inbox').style.display= 'none';
	            	document.getElementById('div_for_compose').style.display= 'block';
	            	document.getElementById('action_gif').style.display= 'none';
	             $( "#div_for_compose" ).html( data );
	            // $('#editor1').focus();
	            setTimeout(function(){ CKEDITOR.instances.editor1.focus(); }, 1200); 
	            // setTimeout(function(){ ckadd(); }, 1000); 
	            }
	            
	            
		);
	
	
}


function addInContacts( con_id ) {
	//alert(con_id);
	
	 document.getElementById('action_gif').style.display= 'block';
	  
	  $.post( "${pageContext.request.contextPath}/saveToContactFromInbox", 
			  { 
			 'con_id': con_id
			  },
			  function( data ) {
				  if(data=="<$expire$>")
					{
					location.href ="${pageContext.request.contextPath}/inbox";
						}
				  if(data=="true")
					  {
					  	var success = generate_in('alert');
						 $.noty.setText(success.options.id, "Contact added Successfully.");
						 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
					  }
				  else if(data=="already")
					  {
					  var success = generate_in('alert');
						 $.noty.setText(success.options.id, "Contact already exists.");
						 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
					  }
				  document.getElementById('action_gif').style.display= 'none';
			  
				  
		});
}

</script>
<script type="text/javascript">
function moveToInbox(uid) {
	
		
		document.getElementById('action_gif').style.display= 'block';
		
		
		$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/webmailMoveToInbox",
					data : {
						'uid' : uid
					},
					contentType : "application/json",
					success : function(data) {
						/* alert('hi'); */
						if(data=="<$expire$>")
						{
						location.href ="${pageContext.request.contextPath}/inbox";
						}
						var elem = document.getElementById("div_" + uid);
						elem.parentNode.removeChild(elem);
						document.getElementById('action_gif').style.display = "none";
						$('.tag_main').css('display','none');
						$('.folder_view').css('display','none');
					    $('#div_search_tool').css('display','none');

						// $("#fileSystem").html(data);
						// alert(data);
						// $("#inb_cnt_div").html(data);
					}
				});
	
}



$(document.body).on('change', '.contact_check_all' ,function(){ 
	

$("input[name='chk_con']:checkbox").prop('checked', $(this).prop("checked"));
if($(this).prop('checked'))
{
	$('.con_he_left_list > tbody > tr').addClass('active_row_delete');
}
else
	{
	 $('.active_row_delete').removeClass('active_row_delete');
	}
});


$(document.body).on('change', '.contact_input' ,function(){ 
	 if($(this).prop('checked'))
		{
		 $(this).parent().parent().addClass('active_row_delete');
		}
	    else
	    	{
	    	$(this).parent().parent().removeClass('active_row_delete');
	    	}
}); 

$(document.body).on('change', '.contact_input_dir' ,function(){ 
	 if($(this).prop('checked'))
		{
		 $(this).parent().parent().addClass('active_row_delete');
		}
	    else
	    	{
	    	$(this).parent().parent().removeClass('active_row_delete');
	    	}
}); 

$(document.body).on('click', 'span.file_close' ,function(){
	
	
	
	var get_file_name = $(this).parent().children('p').text();
	var get_file_name_size = $(this).parent().children('.hidden_size').text();
	var rem_sz=0;
	if(get_file_name_size!=null && get_file_name_size!="")
		{
		var flsz1=get_file_name_size/1000;
		var flsz2=flsz1/1024;
		//alert(flsz2);
		window.flszMB=window.flszMB-(flsz2);
		$("#hid_upload_file_size").val(window.flszMB);
		}
	
	//alert(get_file_name);
  //alert(get_file_name_size);
  $(this).parent().remove();
  
	document.getElementById('action_gif').style.display= 'block';
	$.ajax({
         type: "GET",
         url: "${pageContext.request.contextPath}/deleteUploadedDoc",
         data: {'filenm':get_file_name},
         contentType: "application/json",
         success: function (data) {
        
         document.getElementById('action_gif').style.display= 'none';
         if(data=="<$expire$>")
     	{
     	location.href ="${pageContext.request.contextPath}/inbox";
     		}

         // $(this).parent().remove();
         mailSaveInDraftAtt();
         }
     });

   
    // $(this).parent().remove();
  
	
});

$(document.body).on('click', 'p.fl_nm' ,function(){
	var nm=$(this).text();
	
	var arr=nm.split(".");
	var ext=arr[arr.length-1];
	if(ext=="png" || ext=="PNG" || ext=="jpg" || ext=="JPG" || ext=="jpeg" || ext=="JPEG" || ext=="gif" || ext=="GIF")
		{
		 document.getElementById('action_gif').style.display= 'block';
		//window.open("${pageContext.request.contextPath}/viewattachmentIMG?nm="+nm,'_blank');
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/viewUploadedDocIMG",
			data : {
				 'nm' : nm
				},
			contentType : "application/json",
			success : function(data) {
				
				 document.getElementById('action_gif').style.display= 'none';
				 if(data=="<$expire$>")
					{
					location.href ="${pageContext.request.contextPath}/inbox";
						}
				var str="<img style='max-width:100%; max-height:100%;' src='data:image/jpg;base64,"+data+" ' />";

				$("#att_disp_cnt").html(str);
				$(".web_dialog_overlay_black").show();	
				
			}	

			});
		
		}
	else
		{
		 document.getElementById('action_gif').style.display= 'block';
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/viewUploadedDocPDF",
			data : {
				'nm':nm
				},
			contentType : "application/json",
			success : function(data) {
				 document.getElementById('action_gif').style.display= 'none';
				//alert(data);
				if(data=="notsupported")
			{
					 var success = generate('alert');
					 $.noty.setText(success.options.id, "File format not supported to view");
					 setTimeout(function () {  $.noty.close(success.options.id); }, 4000);
			}
		else
			{
			/* var uri = data;
			var res = encodeURIComponent(uri);	
			window.open('${pageContext.request.contextPath}/js/web/viewer.html?file='+res, '_blank'); */
			  var x = document.createElement("EMBED");
				//path=path.replace(/\//g, "////");
	            x.setAttribute("src", data);
	            x.setAttribute("height", "100%");
	            x.setAttribute("width", "100%");
	                $('#att_disp_cnt_pdf').append(x);
	                $(".web_dialog_overlay_black_pdf").show();	
			}
			}	
			
		});
		}
});


$('#hid_confirm_box').change( function() {  
	   //alert($("#hid_del").attr("data-id")); 
	var r =$('#hid_confirm_box').val();
	
//	alert("change r="+r);
//	alert("change fldnm="+fldnm);
	if (r == "true") {
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
		 $("#hid_to_mail_uid").val("");
		 if(olddraftuid!=null && olddraftuid!="")
			 {
			 draftuid=draftuid+"-"+olddraftuid;
			 }
		var to=document.getElementById("hid_to_id").value;
		//alert(to);
		var cc=document.getElementById("hid_cc_id").value;
		
		var hid_msg_ref=document.getElementById("hid_msg_ref").value;
		var hid_old_msg_id=document.getElementById("hid_old_msg_id").value;
		
		
		var bcc=document.getElementById("hid_bcc_id").value;
		
		var ml_priority=document.getElementById("hid_priority").value;
		 
		var read_rec= document.getElementById("hid_read_rec").value;
		//alert(read_rec)
		var sub=document.getElementById("sub_id").value;
		var cntt = "";

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
		var hid_dsn_status=$("#hid_dsn_status").val();
		document.getElementById('mail_sending').style.display= 'block';
		$.ajax({
	         type: "POST",
	         url: "${pageContext.request.contextPath}/sendComposeMail",
	        data: {'hid_dsn_status': hid_dsn_status, 'hid_old_msg_id': hid_old_msg_id, 'hid_msg_ref': hid_msg_ref, 'draftuid': draftuid, 'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt, 'read_rec': read_rec, 'ml_priority': ml_priority ,'texttype':texttype},
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
			            document.getElementById('div_for_compose').style.display= 'none';
			            document.getElementById('div_for_inbox').style.display= 'block';
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
									            	if(data=="<$expire$>")
									            	{
									            	location.href ="${pageContext.request.contextPath}/inbox";
									            		}
									            	if($("#search_pagi").css('display')=='none')
										  			{
											  		var fdrname=document.getElementById('hid_active_fldr').value;
													if(fdrname=="Drafts" || fdrname=="drafts")
											  			getWebmailInbox( fdrname);
										  			}
													 getDraftMailCount();
									            	
									            }
									            );
									}
								else
									{						
								
								
								if($("#search_pagi").css('display')=='none')
					  			{
						  		var fdrname=document.getElementById('hid_active_fldr').value;
								if(fdrname=="Drafts" || fdrname=="drafts")
						  		getWebmailInbox( fdrname);
					  			}
								 getDraftMailCount();
									}
					        	 
					         }
						 });
						 
						 
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
});



//code of folders' sharing -- START
function manageFolderSharing(fldr){
	     fldr=fldr.replace("share_","");
	     $("#hid_active_shared_fldr").val(fldr);
		 $("#spopuphead").html("("+fldr+")");
			//alert(folderPath)
			 var requestPage="sharingPopupFldr";
				jQuery.get(requestPage,
			                            {
			                    'path' : fldr
			            },
			                    function( data ) {
			            	$( "#srd_cnt" ).html( data );
			         $(".send_share, .cancel_share").css("background-color",$("#hid_mail_bgColor").val()); 	
			         $(".initive_people").removeClass("error");
					 $('.sharing_mange').show();
					 $('.web_dialog_overlay').show();
				});
			
	}
	
function assignSinglePermissions() {
    
    $('.sharing_mange').hide();
    $('.web_dialog_overlay').hide();
 
  var count=0;
 
  var userss="";
  var valuess="";
 
  $(".userforshare").each(function() {
      count++;
      var counter=0;
      var userforshare=$(this).val();
      
      $(".permissionsforshare").each(function() {
          counter++;
          
          if(count==counter){
          var permissionforshare=$(this).val();

          var values=userforshare+","+permissionforshare;
          var vale = values;
          var valu = vale.split(",");
          var user = "";
          vale = valu[1];
          user = valu[0];
         
          var sourcePaths=$("#hid_active_shared_fldr").val();
          docPath1=sourcePaths;
         
          
          value = valu[1];
      
      if(user!=""){
          if(userss=="" && valuess=="")
          	{
      		userss=user;
          	valuess=value;
          	}
          else
          	{
          	{
          		userss+=","+user;
              	valuess+=","+value;
              }
          	}
          }
      }
      });
  });
  if(userss!="" && valuess!="")
  {
  document.getElementById('action_gif').style.display= 'block'; 
  $.ajax({
      type : "GET",
      url : "${pageContext.request.contextPath}/assignSinglePermissionFldr",
      data : {
          'user' : userss,
          'value' : valuess,
          'multipath':docPath1,
      },
      contentType : "application/json",
      async : true,
      success : function(data) {
    	  if(data=="<$expire$>")
    		{
    		location.href ="${pageContext.request.contextPath}/inbox";
    			}

    	  var msg="";
    	  if(data=="true")
	    	  {
	        	 msg="Folder Shared Successfully!";
	          }
          else
        	  {
        	  msg="An error occurred.";
        	  }
          document.getElementById('action_gif').style.display= 'none';
          $('.sharing_mange').hide();
		  $('.web_dialog_overlay').hide();
		  var msg="Folder Shared Successfully!";
          var success = generate('alert');
		  $.noty.setText(success.options.id, msg);
		  setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
          
      }
  });

  }
}


function shareDelete(remid,per){
    
    var sourcePaths=$("#hid_active_shared_fldr").val();
   
    $.ajax({
       type : "GET",
       url : "${pageContext.request.contextPath}/removeAssignedPermissionFldr",
       data : {
           'folderPath' : sourcePaths,
           'remid' : remid
       },
       contentType : "application/json",
       async : true,
       success : function(data) {
    	   if(data=="<$expire$>")
    		{
    		location.href ="${pageContext.request.contextPath}/inbox";
    			}

    	   if(data=="ajaxTrue"){location.href="ajaxTrue";}else{
         var msg="Folder Sharing Removed Successfully!";
         var success=generate('alert');
		 $.noty.setText(success.options.id, msg);
		 setTimeout(function () {  $.noty.close(success.options.id); }, 5000);
           }
       }
   });
    
    
    
}
////code of folders' sharing -- END
</script>
</body>
</html>