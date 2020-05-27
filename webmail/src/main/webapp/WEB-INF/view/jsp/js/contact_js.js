// JavaScript Document

jQuery(document).ready(function() {
	//window.alert('Hi');
	
	 /*$(document.body).on('click','.delete_group',function(){
      	  alert('Hi');

                var delete_name =  $('.my_con_active').children('span').text();
                alert(delete_name);
                $('.my_con_active').remove();
                $("#hid_active_contact_fldr").val("");
      });*/
	
	
	
	// JS FILE 
	// ACTIVE ROW
	
	
// ALL CHECKED BOX
	window.countChecked = 0
	$(document.body).on('click','.contact_check_all',function(){
		var pfldr=document.getElementById('hid_active_contact_fldr').value;
   	// if(!pfldr.startsWith("/sharedContacts/"))
	//	{
		var dir=  $('.my_con_active').children('span').text();
	
		if(dir!="Directory" && $('.my_con_active').attr("name")!="notmanage" && $('.my_con_active').attr("name")!="halfmanage")
			{
	// $('.delete_row_1').text('');
	 $('.active_row_delete').removeClass('active_row_delete');
	 $('.active_contact_row ').removeClass('active_contact_row');
    if($(this).prop('checked'))
	{
    	$('.con_edt').hide();
    	 $('.save_right').hide();
    	$('.cancel_right').hide();
    	var get_length = $('.contact_input').length;
		 window.countChecked=get_length;
		// $('.delete_row_1').text(get_length);
		// alert(get_length);
		 var msg=  "<div style='margin-top: 15%;  margin-left: 38%;'>"+get_length+" Contacts Selected.</div>";
		 $('#div_contact_disp_val').html(msg);
		 // alert('this is checked')
		 $('.contact_input').attr('checked','checked');
		 $('.delete_new_row').show();
		 $('#contact_cnt_div_full table tbody tr').addClass('active_row_delete');
		
     } 
	 else
	 {
		 window.countChecked = 0
		 $('#div_contact_disp_val').html("");
		 $('.active_row_delete').removeClass('active_row_delete');
		// alert('this is UNchecked')
		 $('.contact_input').removeAttr('checked');
		 $('.delete_new_row').hide();
	  }

			}
		else
			{
			 window.countChecked = 0
			 $('#div_contact_disp_val').html("");
			 $('.active_row_delete').removeClass('active_row_delete');
			// alert('this is UNchecked')
			 $('.contact_input').removeAttr('checked');
			 $('.delete_new_row').hide();
			}
		//}
});
	
	// ALL CHECKED BOX END 
	
	
	
	
	
$(document.body).on('click','#contact_cnt_div_full >table>tbody>tr',function(event){
	    //alert('Hi');
	    $('.active_contact_row').removeClass('active_contact_row');
	    $(this).addClass('active_contact_row');
		$('.active_contact_row_1').removeClass('active_contact_row_1');
		$('.active_row_delete').removeClass('active_row_delete');
		try
		{
		$('.contact_input').removeAttr('checked');
		$('.contact_check_all').removeProp('checked');
		$('.cancel_right_active').removeClass("cancel_right_active");
		}catch(err) {
			
		}
		$('.delete_new_row').hide();
		try
		{
		$('.contact_input_dir').removeAttr('checked');
		}
		catch(err) {
			
		}
		window.countChecked = 0
	    event.stopPropagation();
	});
	


$(document.body).on('click','.cal_next_and_Pre > .next',function(){
//		alert("Hello next");
		var con_name = document.getElementById('hid_active_contact_fldr').value
		var pn = $('#pn').val();
		var rpp = $('#rpp').val();
		var total = $('#total').text();
		
//		alert("total : " + parseInt(total));
		try
		{
			pn = parseInt(pn);
			rpp = parseInt(rpp);
			total = parseInt(total);
			if(pn * rpp >  total )
			{
				return false;
			}
			pn++;
			
			
		}
		catch(er){}
		
		document.getElementById('action_gif').style.display= 'block';
		//alert('hi');
		$.ajax({
			type : "GET",
			url : "getContactList",
			data: {'pn':pn},
			contentType : "application/json",
			success : function(data) {
				// $("#fileSystem").html(data);
				document.getElementById('action_gif').style.display= 'none';
				// alert(data);
				//	alert(data);
//				$("#contact_cnt_div_full").html(data);
				var obj = jQuery.parseJSON(data);
				$("#contact_cnt_div_full").html(obj.contacts);
				$('#start_end').html(obj.start+"-"+obj.end);
				$('#total').html(obj.total);
				$('.cal_next_and_Pre').show();
				$('#pn').val(pn);
				//getAllMailCount(fdrname);
				document.getElementById('div_progress').style.display= 'none';
			}
		});

});
	
$(document.body).on('click','.cal_next_and_Pre > .priv',function(){
		var con_name = document.getElementById('hid_active_contact_fldr').value
		var pn = $('#pn').val();
		var rpp = $('#rpp').val();
		var total = $('#total').text();
		
//		alert("total : " + parseInt(total));
		try
		{
			pn = parseInt(pn);
			rpp = parseInt(rpp);
			total = parseInt(total);
			pn--;
			if(pn < 1)
			{
				return false;
			}
			
			
		}
		catch(er){}
		
		document.getElementById('action_gif').style.display= 'block';
		//alert('hi');
		$.ajax({
			type : "GET",
			url : "getContactList",
			data: {'pn':pn},
			contentType : "application/json",
			success : function(data) {
				// $("#fileSystem").html(data);
				document.getElementById('action_gif').style.display= 'none';
				// alert(data);
				//	alert(data);
				var obj = jQuery.parseJSON(data);
				$("#contact_cnt_div_full").html(obj.contacts);
				$('#start_end').html(obj.start+"-"+obj.end);
				$('#total').html(obj.total);
				$('.cal_next_and_Pre').show();
				$('#pn').val(pn);
				//getAllMailCount(fdrname);
				document.getElementById('div_progress').style.display= 'none';
			}
		});
});
	

      
	  $(document.body).on('click','.con_box_left >.contact_input',function(event){
		  
		
	      //alert('Hi-1-1-1');
		//  $('.delete_row_1').text('');
		  
		   	 if($(this).prop("checked"))
							  {
		   		$('.con_edt').hide();
		   	 $('.save_right').hide();
		   	$('.cancel_right').hide();
		   		 window.countChecked++
								   var msg=  "<div style='margin-top: 15%;  margin-left: 38%;'>"+window.countChecked+" Contacts Selected.</div>";
								 //  alert('CCCCheckeddddddd');
								 //  alert(countChecked);
								   $(this).parent().parent().addClass('active_row_delete'); 
								 //  $('.delete_row_1').text(countChecked);
								   $(this).addClass('active_contact_row_1');
								   $('.active_contact_row').removeClass('active_contact_row')
								   $('#div_contact_disp_val').html(msg);
								  
							  }
								else 
							  {
									window.countChecked--
						         var msg=  "<div style='margin-top: 15%;  margin-left: 38%;'>"+window.countChecked+" Contacts Selected.</div>";
								// alert('UNCheckeddddddd');
								// alert(countChecked);
								 $(this).parent().parent().removeClass('active_row_delete');
								 //$('.delete_row_1').text(countChecked);
								 $(this).removeClass('active_contact_row_1');
								 $('.active_contact_row').removeClass('active_contact_row');
								 $('#div_contact_disp_val').html(msg);

							  } 


                      					
							  if( window.countChecked >= 1)
				               {
								 //  alert('Hi-1-1');
								var dir=  $('.my_con_active').children('span').text();
								if(dir!="Directory" && $('.my_con_active').attr("name")!="notmanage" && $('.my_con_active').attr("name")!="halfmanage")
									{
									$('.delete_new_row').show();
									}
								 
				              } 
							  else if(window.countChecked < 1)
							  {
								//  alert('Hi-1-1-11');
								  $('#div_contact_disp_val').html("");
								  $('.delete_new_row').hide();
							  }


			    
		   
		   
		   
		   
		
	       event.stopPropagation();
	  });
	  
	  
	  
	  $(document.body).on('click','.con_box_left >.contact_input_dir',function(event){
		  
			
	      // alert('Hi-1-1-1');
		//  $('.delete_row_1').text('');
		  
		   	 if($(this).prop("checked"))
							  {
		   		 				$('.active_row_delete >td>input').removeAttr('checked');
		   		 				$('.active_row_delete').removeClass('active_row_delete')
								$(this).parent().parent().addClass('active_row_delete'); 
								 //  $('.delete_row_1').text(countChecked);
								  
								  // $(this).addClass('active_contact_row_1');
								 
								  
							  }
								else 
							  {
									 $('.active_row_delete').removeClass('active_row_delete')

							  } 


	       event.stopPropagation();
	  });
	  
	  
	  
	// CHECKED BOX END 
	
	/// DELETE THE ROW
	
	 
	  
	/*$(document.body).on('click','.delete_new_row',function(){
		
		       $('.active_row_delete').hide();
			   $('.delete_new_row').hide();
			  // countChecked = 0;
			   delrowcontreset()
			   $('#div_contact_disp_val').html("");
		});*/
	
	// DELETE THE ROW
	
	
	
//	$(document.body).on('click','.save_right',function(){
//		
//		$('.save_right').hide();
//		$('.cancel_fl_vi').hide();
//		$('.cancel_right').removeClass('cancel_right_active');
//		 $('.edite_name').hide();
//		 $('.edite_address_edit').hide();
//		 $('.right_con_part >table >tbody>tr>td>input').attr('disabled','disabled');
//		 $('.right_con_part >table >tbody>tr>td>input').css('border','none');
//		 $('.right_con_part >table >tbody>tr>td>textarea').attr('disabled','disabled');
//		 $('.right_con_part >table >tbody>tr>td>textarea').addClass('disabled_textarea');
//	})
	
	 $(document.body).on('click','.delete_contact_row',function(){
	       
	       $(this).parent().remove();
	       
	       });
	
	$('.right_con_part >table >tbody>tr>td>input').attr('disabled','disabled');
	$('.right_con_part_pop_edit >table >tbody>tr>td>input').attr('disabled','disabled');
	
	/// EDITE ADDRESS STRED HERE 
	
	 $(document.body).on('click','.edite_address_edit',function(){
	
			
			//alert('Hi');
				
				     if($('.address_name_box').css('display')=='none')
					 {
						$('.address_name_box').show();
						$('.web_dialog_overlay').show();
					//	$('.web_dialog_overlay').css('z-index','10');  
					  }
					  else
					  {
					         $('.address_name_box').hide();
						     $('.web_dialog_overlay').hide();
							 $('.web_dialog_overlay').css('z-index','3');  
					   }
				
				});
		
		
		
		/// EDIT ADDRESS FOR FULL PEOPLE
		
		
	 $(document.body).on('click','.edite_address_edit_peole',function(){

			
			//alert
			     if($('.address_name_box').css('display')=='none')
				 {
			    	 $('.address_name_box').show();
					$('.web_dialog_overlay').show();
					//$('.web_dialog_overlay').css('z-index','13');  
					$('.web_dialog_overlay').addClass('bg_blur');  
				  }
				  else
				  {
					  $('.address_name_box').show();
					     $('.web_dialog_overlay').hide();
						// $('.web_dialog_overlay').css('z-index','3'); 
						 $('.web_dialog_overlay').removeClass('bg_blur'); 
				   }
			
			});
	// CANCEL NAME 
	
	
	
	
	
	
	
	
	
	
	/// MY CONTACT STARED HERE

	 $(document.body).on('click','.my_con',function(){
	 
		
						//$('.my_calender_content').slideToggle("slow");
						if($('.my_calender_con').css('display')=='block')
						{
							$('.my_calender_con').hide();
							$('.my_con').addClass('bottom_arrow');
						}
						else
						{
							$('.my_calender_con').show();
							$('.my_con').removeClass('bottom_arrow');
						 }
		
		});
		
		/// MY CONTACT END HERE
		
	   /// OTHER CALENDER  HERE 
	   
	/* $(document.body).on('click','.other_con',function(){
	
		
						
						if($('.other_calender_con').css('display')=='none')
						{
							$('.other_calender_con').show();
							$('.other_con').addClass('other_bottom_con');
						}
						else
						{
							$('.other_calender_con').hide();
							$('.other_con').removeClass('other_bottom_con');
						 }
		
		});*/
		
		/// OTHER CALENDER END HERE 
		
		
		/// MORE OPTION ARE HERE 

	 $(document.body).on('click','.first_con_option_3',function(){

			
			   //alert('Hi');
			   
			/*   if($('.con_more').css('display')=="none")
			   {
				   $('.con_more').show();
				   $('.first_con_option_3').addClass('more_active');
				}
				else
				{
					$('.con_more').hide();
				    $('.first_con_option_3').removeClass('more_active');
			     }
				*/
			   
		 var more_top = $(this).position().top;
         var more_left = $(this).position().left;
		  //  alert(more_top);
		//  alert(more_left);
		   //alert('Hi');
		   
		   if($('.con_more').css('display')=="none")
		   {
		   	   if($('.my_con_active').attr("name")=="notmanage" ||  $('.my_con_active').attr("name")=="manage" ||  $('.my_con_active').attr("name")=="halfmanage")
		   		   {
		   		   $(".delete_group").hide();
		   		   $(".mange_sharing").hide();
		   		   }
		   	   else
		   		   {
		   		   $(".delete_group").show();
		   		   $(".mange_sharing").show();
		   		   }
			   $('.con_more').css('left',more_left+10);
		   	   $('.con_more').css('top',more_top+28);
			   $('.con_more').show();
			   $('.first_con_option_3').addClass('more_active');
			}
			else
			{
				$('.con_more').hide();
			    $('.first_con_option_3').removeClass('more_active');
		     }
			
			   
			});
			
		/// LEFT VIEW CONTENT WINDOW HEIGHT 
		
		/*var con_left =$(window).height()-172;
		$('.left_con_part').css('height',con_left);
		$('.right_con_part').css('height',con_left + 72-69);*/
		//alert(con_left);
		
		
		/// EDITE OPTION STARED HERE 

	 $(document.body).on('click','.cancel_right',function(){

			
			     ///alert('Hi');
				/* if($('.save_chnage').css('display')=='none')
				 {
					 alert("none");*/
					 $('.edite_name').show();
					 $('.cancel_right').addClass('cancel_right_active');
					 $('.add_more').hide();
					 $('.save_chnage').show();
					 $('.cancel_fl_vi').show();
					 $('.edite_address_edit').show();
					 $('.right_con_part >table >tbody>tr>td>input').css('border','1px solid #ccc');
					 $('.right_con_part >table >tbody>tr>td>input').removeAttr('disabled');
					 $('.right_con_part >table >tbody>tr>td>textarea').removeAttr('disabled');
					 $('.right_con_part >table >tbody>tr>td>textarea').removeClass('disabled_textarea');
					 $('.save_right').show();
					 

				/*  }
				  else
				  {
					  alert("block");
					 $('.save_chnage').hide();
					 $('.edite_name').hide();
					 $('.cancel_right').removeClass('cancel_right_active');
					 $('.add_more').hide();
					 $('.cancel_fl_vi').hide();
					 $('.edite_address_edit').hide();
					 $('.right_con_part >table >tbody>tr>td>input').attr('disabled','disabled');
					 $('.right_con_part >table >tbody>tr>td>input').css('border','none');
					 $('.save_right').hide();
				  }*/
			
			})
			
			/// EDITE NAME  
			
	 $(document.body).on('click','.edite_name',function(){
		
				/*
				//alert
				     if($('.edite_name_box').css('display')=='none')
					 {
						$('.edite_name_box').show();
						$('.web_dialog_overlay').show();
						//$('.web_dialog_overlay').css('z-index','13');  
						$('.web_dialog_overlay').addClass('bg_blur'); 
					  }
					  else
					  {
					         $('.edite_name_box').hide();
						     $('.web_dialog_overlay').hide();
							// $('.web_dialog_overlay').css('z-index','3'); 
							 $('.web_dialog_overlay').removeClass('bg_blur'); 
					   }*/
				
				});
		// CANCEL NAME 
		
	 $(document.body).on('click','.cancel_right_name',function(){
		
			       
				             $('.edite_name_box').hide();
							 $('.address_name_box').hide();
						     $('.web_dialog_overlay').hide();
						     $('.error').removeClass("error");
			    
			  });
			  
	   /// EDITE NAME CANCEL TOP 

	 $(document.body).on('click','.cancel_top',function(){
	 
			       
				             $('.edite_name_box').hide();
							 $('.address_name_box').hide();
						     $('.web_dialog_overlay').hide() 
						     
			    
			  });
			
	   /// CREATE GROUP HERE 
	   
	 $(document.body).on('click','.first_con_option',function(){
	
		   
		      // alert('Hi');
			   if($('.group_name').css('display')=="none")
			   {
				   $('.group_name').show();
				   $('.web_dialog_overlay').show() 
				   
				}
				else
				{ 
				   $('.group_name').hide();
				   $('.web_dialog_overlay').hide();
			    
				}
		   
		   });
		// CANCEL GROUP 

	 $(document.body).on('click','.cancel_grop',function(){
	
			
			        $('.group_name').hide();
				     $('.web_dialog_overlay').hide();
			});
			// CANCEL GROUP 

	 $(document.body).on('click','.cancel_grop_top',function(){
	
			
			        $('.group_name').hide();
				   $('.web_dialog_overlay').hide();
			});
	
	/// CREATE CONTACT 
	

	 $(document.body).on('click','.cancel_fl_vi_create',function(){
		 $('.con_crt').hide();
		 $('.error').removeClass("error");
		 $('#div_contact_create').hide();
		 
	 });
	 

	 $(document.body).on('click','.first_con_option_1',function(){

		 $("#hid_contact_img_code").val("")
	    	var id="crt_con_img";
	    	document.getElementById(id).src="images/contact_photo.png";
		 $('#div_contact_disp_val').hide();
		 $('#div_contact_create').show();
		 $('.edite_name').show();
		 $('.con_crt').show();
		 $('.con_edt').hide();
		 $( "#div_contact_disp_val" ).html( "" );
		/* $('#gp_select')
		    .find('option')
		    .remove()
		    .end()
		    .append('<option value="/Contacts/Personal Contacts">Personal Contacts</option>')
		    .val('whatever')
		;*/
		 
		/* $.ajax({
	         type: "POST",
	         url: "${pageContext.request.contextPath}/conAllFolderList",
	         data: {'uid': uid,'to':to, 'cc':cc, 'bcc':bcc, 'sub':sub, 'cntt':cntt ,"texttype": texttype},
	          contentType: "application/json", 
	         success: function (data) {
	             
	          var arr =data.split("<$nirbhay$>");
	          var con_arr=arr[0].split(",");
	          if(arr.lenght>1 && arr[1]!="")
	        	  {
	        	  var sha_arr=arr[1].split(",");
	        	  }
	          
	            
	         }
	     });*/
		 
		 
		   //  alert('hi');
		/*	 if($('.create_contact').css('display') == 'none')
			 {
				
				 $('.create_contact').show();
				 $('.web_dialog_overlay').show(); 
				 $('.web_dialog_overlay').removeClass('bg_blur'); 
		     }
			 else 
			 {
		         $('.create_contact').hide();
				 $('.web_dialog_overlay').hide(); 
			 
			 }
		*/
		});
		
		/// CREATE CANCEL TOP
		
	 $(document.body).on('click','.create_top',function(){
		 		 $('.create_contact').hide();
				 $('.web_dialog_overlay').hide(); 
				 
			});
			
       /// ADD GROUP 
	// $(document.body).on('click','.save_right_name',function(){
			
	    /* if($('.Sk').val()=='')
	     {
                 //  alert("Please Fill Group Name")
	    	 showMsg("alert","Please Fill Group Name");
	     }
	     else if($('.Sk').val()=='Collected Contacts' || $('.Sk').val()=='collected contacts' || $('.Sk').val()=='Personal Contacts' || $('.Sk').val()=='personal contacts' || $('.Sk').val()=='Directory' || $('.Sk').val()=='directory')
	    	 {
	    	 showMsg("alert","This group is already exist.");
	    	 }
	     else 
	     {
	    	  var find_group = document.getElementById("contact_gp_val").value;
	    		//alert(fname);
	    				
	    	  document.getElementById('action_gif').style.display= 'block';
	    				
	    				$.ajax({
	    					type : "GET",
	    					url : "createContactGroup",
	    					data : {
	    						'folderName': find_group
	    					},
	    					contentType : "application/json",
	    					success : function(data) {
	    						// $("#fileSystem").html(data);
	    						// alert(data);
	    						//alert(data);
	    						//$("#contact_cnt_div_full").html(data);
	    						//document.getElementById('action_gif').style.display= 'none';
	    						//getAllMailCount(fdrname);
	    							//	document.getElementById('div_progress').style.display= 'none';
	    						document.getElementById('action_gif').style.display= 'none';
	    						var option = document.createElement("option");
	    						option.text = find_group;
	    						option.value = find_group;
	    						var select = document.getElementById("gp_select");
	    						select.appendChild(option);
	    					
		      
				//alert(find_group);
				$('.my_calender_con >ul').append('<li style="cursor: pointer;" onclick="getSelContactUserList(this.id)" id="/Contacts/'+find_group+'" > <img src="images/group_con.png" class="icon_con col_con group_img"> <span>'+ find_group +'</span><div class="clear"></div></li>');
				$('.my_calender_con ul li').hover(
		        		 function(){ $(this).css('border-left', "3px solid "+$("#hid_mail_bgColor").val()) },
		        		 function(){ 
		        			$(this).css('border-left', "3px solid #fff") 
		        			$(".my_con_active").css("border-left","3px solid "+$("#hid_mail_bgColor").val())	 
		        		 }
		        );
				$('.Sk').val('');
				$('.group_name').hide();
				$('.web_dialog_overlay').hide();
	    					}
	    				});	
	     } 
		   */
		 //  });
		   
		   
		   /// CREATE CONTACT ON FULL VIEW TD 
	//$('table.con_he_content >tbody >tr>td').click(function(){
		/* $(document.body).on('click', 'table.con_he_content >tbody >tr>td' ,function(){
		
		
		    alert('hi');
			 if($('.create_contact_edit').css('display') == 'none')
			 {
				
				 $('.create_contact_edit').show();
				 $('.web_dialog_overlay').show(); 
		     }
			 else 
			 {
		         $('.create_contact_edit').hide();
				 $('.web_dialog_overlay').hide(); 
			 
			 }
		
		});
		*/
		/// CREATE CANCEL TOP
		
	 $(document.body).on('click','.create_top_edit',function(){
	
			
			
			     $('.create_contact_edit').hide();
				 $('.web_dialog_overlay').hide(); 
				 
			});
		
		/// EDITE CONTACT STARED 
		
	 $(document.body).on('click','.cancel_right_edit',function(){

			
			    // alert('Hi');
				 if($('.save_chnage_pop_edit').css('display')=='none')
				 {
					 $('.edite_name_edit').show();
					 $('.cancel_right_edit').addClass('cancel_right_active_edit');
					 $('.add_more_edit').hide();
					 $('.cancel_edit').show();
					 $('.save_chnage_pop_edit').show();
					 $('.edite_address_edit').addClass('left_details');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>input').css('border','1px solid #ccc');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>input').removeAttr('disabled');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>textarea').removeAttr('disabled');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>textarea').removeClass('disabled_textarea');
				  }
				  else
				  {
					 $('.save_chnage_pop_edit').hide();
					 $('.edite_name_edit').hide();
					 $('.cancel_right_edit').removeClass('cancel_right_active_edit');
					 $('.add_more_edit').hide();
					 $('.cancel_edit').hide();
					 $('.edite_address_edit').removeClass('left_details');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>input').attr('disabled','disabled');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>textarea').attr('disabled','disabled');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>textarea').addClass('disabled_textarea');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>input').css('border','none');
				  }
			
			});
			
			
			
	 $(document.body).on('click','.edite_name_edit',function(){
	
				
				    /* if($('.edite_name_box').css('display')=='none')
					 {
						$('.edite_name_box').show();
						$('.web_dialog_overlay').show();
						$('.web_dialog_overlay').css('z-index','10');  
					  }
					  else
					  {
					         $('.edite_name_box').hide();
						     $('.web_dialog_overlay').hide();
							 $('.web_dialog_overlay').css('z-index','3');  
					   }*/
				
				});
				
			/// CANCEL EDITE POP 
	 $(document.body).on('click','.cancel_edit',function(){
		
				
				     $('.save_chnage_pop_edit').hide();
					 $('.edite_name_edit').hide();
					 $('.cancel_right_edit').removeClass('cancel_right_active_edit');
					 $('.add_more_edit').hide();
					 $('.cancel_edit').hide();
					 $('.edite_address_edit').removeClass('left_details');
					 $('.edite_address_edit').hide();
					 $('.right_con_part_pop_edit >table >tbody>tr>td>input').attr('disabled','disabled');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>input').css('border','none');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>textarea').attr('disabled','disabled');
					 $('.right_con_part_pop_edit >table >tbody>tr>td>textarea').addClass('disabled_textarea');

				
				});
				
				
	    /// CANCEL EDITE  DETAILS VIEW PAGES  
	 $(document.body).on('click','.cancel_fl_vi',function(){

				
				     $('.save_chnage').hide();
				     $('.save_right').hide();
					 $('.edite_name').hide();
					 $('.edite_address_edit').hide();
					 $('.cancel_right').removeClass('cancel_right_active');
					 $('.add_more').hide();
					 $('.cancel_fl_vi').hide();
					 $('.right_con_part >table >tbody>tr>td>input').attr('disabled','disabled');
					 $('.right_con_part >table >tbody>tr>td>input').css('border','none');
					 $('.right_con_part >table >tbody>tr>td>textarea').attr('disabled','disabled');
					 $('.right_con_part >table >tbody>tr>td>textarea').addClass('disabled_textarea');
				
				});		
				
				
				
				/// MANGE SHARING 
				
	
			
			   /// MANAGES CANCEL TOP 
	 $(document.body).on('click','.mange_can_top',function(){
				             $('.sharing_mange').hide();
							 $('.web_dialog_overlay').hide();
				   
			  });
			  
			   /// MANAGES CANCEL 
	 $(document.body).on('click','.mange_can',function(){
			
				   
				             $('.sharing_mange').hide();
							 $('.web_dialog_overlay').hide();
							 $('.cal_setting').hide();
							 
				   
			  });
	 
	 
	 /*$(document.body).on('click','.mange_can',function(){
		    $('.cal_setting').hide();
			$('.web_dialog_overlay').hide();
		   
});*/

			  // ADD MORE SHRE 
			  
			 /* $(document.body).on('click', '.share_more' ,function(){
				  //alert('hi');
				  $('.sharing_mange >.table_append >table.append_tr >tbody').append('<tr><td><input type="text"  class="initive_people" /></td><td><div class="can_edit"><div class="share_more">Add More</div><div class="can_edite">    <select><option>Can View </option><option>Can Edit </option><option>Can Mange </option></select></div></div></td></tr>');
				  });*/
	 
	 
	 
	 
	 var calender_height = $(window).innerHeight();
	 // alert(calender_height);
	  $('#calendar').css('height',calender_height-89);
	  
	  $(window ).resize(function() {
		   var calender_height = $(window).height();
	//  alert(calender_height);
	         $('#calendar').css('height',calender_height-89);
		});
	  
	  
	  
	  $(document.body).on('click', '.share_more' ,function(){
		  //alert('hi');
		  $('.initive_people').removeClass('error');
		  var share_text = $('.initive_people').val();
		  var select_val = $(this).parent().children().children().val();
		   var select_get = '<select class="permissionsforshare">';
		   if(select_val=="ur")
             {
               select_get +=      '<option value="ur" selected="selected">Read </option> <option value="uw">Read/Write </option> <option value="us">All</option>';
             }
		   if(select_val=="uw")
           {
			   select_get +=      '<option value="uw"  selected="selected">Read/Write </option> <option value="ur" >Read </option>  <option value="us">All</option>';
           }
             if(select_val=="us")
             {
            	  select_get +=      '<option value="us" selected="selected">All</option><option value="ur" >Read </option> <option value="uw">Read/Write </option> ';
             }
             
                                             
               select_get +=  ' </select>';
		 
		  function ValidateEmail(email) {
				var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
				return expr.test(email);
			};
		   
		  var domnm=$('#hid_dom').val();
		  var domnmid=$('#hid_dom_id').val();
		  // var r = new RegExp("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
		   if($('.initive_people').val()=='' || !(share_text.match("@"+domnm+"$")))
		   {
			   $('.initive_people').addClass('error');
			   $('.initive_people').focus();
//		        alert('Please Fill The Value !');
		   }
		   else {
			   
		//  $('.sharing_mange >.table_append >table.append_tr >tbody').append('<tr><td><input type="text"  class="initive_people" /></td><td><div class="can_edit"><div class="share_more">Add More</div><div class="can_edite">    <select><option>Can View </option><option>Can Edit </option><option>Can Mange </option></select></div></div></td></tr>');   
			
				   
				   if (!ValidateEmail($(".initive_people").val())) {
//						  alert("Invalid email address.");
						  $('.initive_people').addClass('error');
						  $('.initive_people').focus();
					  }
					  else if(domnmid==$(".initive_people").val())
					  {
//						  alert("Invalid email address.");
						  $('.initive_people').addClass('error');
						  $('.initive_people').focus();
					  }					 
					  else
					  {
						 //  alert("Valid email address.");
						  $('.invite_people_box').append('<div class="select_append"><div class="share_content"><input class="userforshare userforshhare"  type="text" readonly="readonly" value="'+ share_text +'" /></div><div class="select_option"> <div class="can_edite"> '+ select_get +'</div></div><div class="remove_select delete_shre"> <img id="delete'+share_text+'" src="images/tool.png" /></div><div class="clear"></div></div><div class="clear"></div></div>');
                          $(".initive_people").val('');
                          $('.npselect').val('ur');
					  }
				   
				   
				   
				   
				   
				   
				   
				   
			   
			   }
		  });

     $(document.body).on('click','.remove_select',function(){
		 
		  //  alert('Hi');
			$(this).parent().remove();
		 
		 });
	 
	 
	 
		
	
	
	 $(document.body).on('click','.import_can_top',function(){
	 
		                  $('.import_con').hide();
						  $('.web_dialog_overlay').hide();
		  
		  });
		  
	 $(document.body).on('click','.cancel_import',function(){
		 
		                  $('.import_con').hide();
						  $('.web_dialog_overlay').hide();
		  
		  });
		  
		  /// IMPORT FILE END HERE 
		  
		  	/// Export FILE STRED HERE 
		
	 $(document.body).on('click','.export_click',function(){
	
			
			         if($('.export_con').css('display')=='none')
					 {
			        	 var no=0;
			        	 $('input[name="chk_con"]:checked').each(function() {
			        		  no++;
			        		});
						  $('.export_con').show();
						  $('.web_dialog_overlay').show();
						  $('.con_more').hide();
						  $('div.more_active').removeClass('more_active');
						  $('#no_cont').html(no);
						  if(no!=0)
						  {
						  $('input:radio[name="source"]').filter('[value="contact"]').attr('checked', true);
						  }
						  else
						  {
						  $('input:radio[name="source"]').filter('[value="group"]').attr('checked', true);
						  }
					  }
			
	  });
	
	 $(document.body).on('click','.export_can_top',function(){

		                  $('.export_con').hide();
						  $('.web_dialog_overlay').hide();
		  
		  });
		  
	 $(document.body).on('click','.cancel_export',function(){
    
		                  $('.export_con').hide();
						  $('.web_dialog_overlay').hide();
		  
    });
		
		
		
});