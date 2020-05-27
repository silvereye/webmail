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
        
        $(function() {
        $.contextMenu({
            selector: '.row_content', 
            callback: function(key, options) {
                
            	if(key=="reply")
            	{
            	openReply();
            	}
            else  if(key=="replyall")
        	{
            	openReplyAll();
        	}
            else  if(key=="forward")
        	{
            	openForword();
        	}
	        else  if(key=="read")
	    	{
	        	setSetectedMailRead();
	    	}
	        else  if(key=="unread")
        	{
	        	setSetectedMailUnRead();
        	}
            else  if(key=="removestar")
        	{
            	setSetectedMailUnFlag();
        	}
            else if(key=="addstar")
        	{
        	setSetectedMailFlag();
        	}
	        else  if(key=="delete")
	    	{
	        	moveTrashAll();
	    	}
	        else  if(key=="header")
	    	{
	        	cmenuHeader();
	    	}
	        else  if(key=="print")
	    	{
	        	cmenuPrint();
	    	}


 		    

                 

               // window.console && console.log(m) || alert(m); 
            },
            items: {
                "reply": {name: "Reply", icon: "reply"},
                "replyall": {name: "Rely All", icon: "replyall"},
                "forward": {name: "Forward", icon: "forward"},
                "sep1": "---------",
                "read": {name: "Mark as Read", icon: "read"},
                "unread": {name: "Mark as Unread", icon: "unread"},
                "addstar": {name: "Add Star", icon: "addstar"},
                "removestar": {name: "Remove Star", icon: "removestar"},
                "sep2": "---------",
                "header": {name: "View Header", icon: "header"},
                "print": {name: "Print", icon: "print"},
                "delete": {name: "Delete", icon: "delete"}
            }
            
        });

         
    });
        
        function cmenuPrint() {
			var id=$("#hid_mail_disp_count").val();
			var fdrname = $('#hid_active_fldr').val();
		
			    window.open("mailcntprint?uid="+id+"&folder="+fdrname);
        }
        
        function cmenuHeader() {
			var id=$("#hid_mail_disp_count").val();
			var fdrname = $('#hid_active_fldr').val();
		
			    window.open("mailheadercntt?uid="+id+"&folder="+fdrname);
        }
      
        
        
        
       $(document).on("contextmenu",'.row_content',function(e){
    	   try
     	   {
     		  $("#context-menu-layer").remove();
     	   }
     	   catch (e) {
			// TODO: handle exception
		} 
       	 noneMail();
           
             var id=this.id;
            $('#'+id).addClass('selected_row');
          
            // document.getElementById(id).className="row_content selected_row left_view_mess";
             if(id!=null && id!="")
 			{
 			var arr=id.split("_");
 			if(arr[1]!=null)
 				{
 				
 				$("#hid_mail_disp_count").val(arr[1]);
 				}
 			}
            
          });
       
       
      /*  
       $(function() {
           $.contextMenu({
               selector: '.context-menu-folder-other', 
               callback: function(key, options) {

                 
               },
               items: {
                  
            	  
            	   "newfolder": {name: "New Folder", icon: "newfolder"},
            	   "rename": {name: "Rename", icon: "rename"},
            	   "share": {name: "Share", icon: "share"},
            	   "unsubscribe": {name: "Unsubscribe", icon: "unsubscribe"},
            	   "empty": {name: "Empty", icon: "empty",disabled: true},
                   "delete": {name: "Delete", icon: "delete"}
               }
               
           });

            
       });
      
       
       $(document).on("contextmenu",'.context-menu-folder-other',function(e){
    	   try
     	   {
     		  $("#context-menu-layer").remove();
     	   }
     	   catch (e) {
			// TODO: handle exception
		}
    	   var id=this.id;
     	   $("#hid_cmenu_act_fldr").val(id);
              
            });
       
       $(function() {
           $.contextMenu({
               selector: '.context-menu-folder-system-in', 
               callback: function(key, options) {

                 
               },
               items: {
                  
            	   
            	   "newfolder": {name: "New Folder", icon: "newfolder"},
            	   "rename": {name: "Rename", icon: "rename",disabled: true},
            	   "share": {name: "Share", icon: "share"},
            	   "unsubscribe": {name: "Unsubscribe", icon: "unsubscribe",disabled: true},
            	   "empty": {name: "Empty", icon: "empty",disabled: true},
                   "delete": {name: "Delete", icon: "delete",disabled: true}
               }
               
           });

            
       });
      
       
       $(document).on("contextmenu",'.context-menu-folder-system-in',function(e){
    	   try
     	   {
     		  $("#context-menu-layer").remove();
     	   }
     	   catch (e) {
			// TODO: handle exception
		} 
    	   var id=this.id;
     	   $("#hid_cmenu_act_fldr").val(id);
              
            });
       
       $(function() {
           $.contextMenu({
               selector: '.context-menu-folder-system-out', 
               callback: function(key, options) {

                 
               },
               items: {
                  
            	   
            	   "newfolder": {name: "New Folder", icon: "newfolder",disabled: true},
            	   "rename": {name: "Rename", icon: "rename",disabled: true},
            	   "share": {name: "Share", icon: "share"},
            	   "unsubscribe": {name: "Unsubscribe", icon: "unsubscribe",disabled: true},
            	   "empty": {name: "Empty", icon: "empty",disabled: true},
                   "delete": {name: "Delete", icon: "delete",disabled: true}
               }
               
           });

            
       });
      
       
       $(document).on("contextmenu",'.context-menu-folder-system-out',function(e){
    	   try
     	   {
     		  $("#context-menu-layer").remove();
     	   }
     	   catch (e) {
			// TODO: handle exception
		} 
    	   var id=this.id;
     	   $("#hid_cmenu_act_fldr").val(id);
              
            });
       
       
       
       $(function() {
           $.contextMenu({
               selector: '.context-menu-folder-del', 
               callback: function(key, options) {

               if(key=="empty")
                	 {
                	 setFolderEmptyCMenu();
                	 }
               },
               items: {
            	   
            	   "newfolder": {name: "New Folder", icon: "newfolder",disabled: true},
            	   "rename": {name: "Rename", icon: "rename",disabled: true},
            	   "share": {name: "Share", icon: "share",disabled: true},
            	    "unsubscribe": {name: "Unsubscribe", icon: "unsubscribe",disabled: true},
            	   "empty": {name: "Empty", icon: "empty"},
                   "delete": {name: "Delete", icon: "delete",disabled: true}
            
                 
               }
               
           });

            
       });
      
       
       $(document).on("contextmenu",'.context-menu-folder-del',function(e){
     	   try
     	   {
     		  $("#context-menu-layer").remove();
     	   }
     	   catch (e) {
			// TODO: handle exception
		}
    	   var id=this.id;
             //  alert(id)
            $("#hid_cmenu_act_fldr").val(id);
              
            });
       $(document).on("click",'#context-menu-layer',function(e){
       try
 	   {
 		  $("#context-menu-layer").remove();
 	   }
 	   catch (e) {
		// TODO: handle exception
	}
       });
       
       function setFolderEmptyCMenu()
       {
    	   var fldr=$("#hid_cmenu_act_fldr").val();
    	   if(!fldr)
    		   {
    		   
    		   }
    	   else
    		   {
    			if(fldr=="Trash" || fldr=="TRASH" || fldr=="trash" || fldr=="Junk" || fldr=="junk" || fldr=="JUNK" )
    		   {
    				 $('.web_dialog_overlay').show(); 
    				 setFolderEmptyCMenu_conf("confirm","Do you want to empty "+fldr,fldr)
    		   }
    		   
    		   }
       }
       
       function setFolderEmptyCMenu_conf(type,msg,fldr) {
   	   	//alert(id);
   	         var n = noty({
   	           text        : msg,
   	           type        : type,
   	           theme       : 'relax',
   	           dismissQueue: false,
   	           layout      : 'center',
   	           theme       : 'defaultTheme',
   	           buttons     : (type != 'confirm') ? false : [
   	               {addClass: 'btn btn-primary', text: 'Yes', onClick: function ($noty) {
   	   				
   	                 $noty.close();
   	               
   	                 $('.web_dialog_overlay').hide(); 
   	                 
   	               $("action_gif").show();
   	    		   $.ajax({
   						type : "GET",
   						url : "${pageContext.request.contextPath}/setFolderEmptyCMenu",
   						data : {
   							'fldr' : fldr
   						},
   						contentType : "application/json",
   						success : function(data) {
   							$("#action_gif").hide();
   							if(data=="true")
   								{
   								var fdrname = document.getElementById('hid_active_fldr').value;
   								if(fdrname==fldr)
   									{
   									getWebmailInbox(fdrname);
   									}
   								}
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
   	            
   	       } */
    </script>
    <input type="hidden" id="hid_cmenu_act_fldr" />
</body>

</html>