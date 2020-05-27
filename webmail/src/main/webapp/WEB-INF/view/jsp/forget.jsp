<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Forget Password </title>
    

 
    <!-- CSS -->
       
    <!-- Bootstrap & FontAwesome & Entypo CSS -->
    <link href="forget_pass/bootstrap.min.css" rel="stylesheet" type="text/css">



               
    
	    
     
    <!-- Theme CSS -->
    <link href="forget_pass/theme.min.css" rel="stylesheet" type="text/css">
    <!--[if IE]> <link href="forget_pass/ie.css" rel="stylesheet" > <![endif]-->
 


        
    <!-- Responsive CSS -->
        	<link href="forget_pass/theme-responsive.min.css" rel="stylesheet" type="text/css"> 

	  
 
 <script src="js/jquery-1.8.3.min.js"></script>
 <script src="js/jquery.noty.packaged.js"></script>
        
    

<script type="text/javascript">

	
function showmsg(type,msg) {
    var n = noty({
        text        : msg,
        type        : type,
        dismissQueue: false,
        layout      : 'topCenter',
        theme       : 'defaultTheme'
    });
  
    setTimeout(function () {
        $.noty.close(n.options.id);
    }, 6000); 
}


function ValidateEmail(email) {
    var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return expr.test(email);
};

function resetPass() {
	var email=$("#email").val();
	if(email==null || email=="")
		{
		showmsg("alert","Enter the email address you use to sign in.");
		}
	else
		{
		if (!ValidateEmail(email)) {
            showmsg("alert","Enter valid email address.");
        }
        else {
        	
        	 $("#action_gif").show();
        	$.ajax({
   	         type: "GET",
   	         url: "${pageContext.request.contextPath}/generateForgetPass",
   	         data: {'email': email },
   	         contentType: "application/json",
   	         success: function (data) {
   	        	 $("#action_gif").hide();
   	             if(data=="true")
   	            	 {
   	        	 	showmsg("success","mail of password recovery has been sent to your Backup Email Address.");
   	            	 }
   	             else if(data=="false")
   	            	 {
   	            	 showmsg("error","Error Occurred!");
   	            	 }
   	             else 
               	 {
               	 showmsg("alert",data);
               	 }
   	            
   	         }
   	     });
        	
       // showmsg("alert","Not for Demo!");
		/* $("#action_gif").show();
		$.ajax({
	         type: "GET",
	         url: "${pageContext.request.contextPath}/setForgetPass",
	         data: {'email': email },
	         contentType: "application/json",
	         success: function (data) {
	        	 $("#action_gif").hide();
	             if(data=="true")
	            	 {
	        	 	showmsg("success","Your new password has been sent to your Backup Email Address.");
	            	 }
	             else if(data=="false")
	            	 {
	            	 showmsg("error","Error Occurred!");
	            	 }
	             else 
            	 {
            	 showmsg("error",data);
            	 }
	            
	         }
	     }); */
		
		}
		}
	
}

</script>

 <style type="text/css">
 
.my_notification {
    margin: -6px 0;
   background-color: #f9edbe;
   border: 1px solid ;
	border-color: #f0c36d;
    border-radius: 2px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
    padding: 6px 10px;
	top:4%;  position: absolute; z-index: 99999;
}
 
 </style>
    
</head>    

<body id="pages" class="full-layout no-nav-left no-nav-right  nav-top-fixed background-login     responsive remove-navbar login-layout   clearfix" data-active="pages "  data-smooth-scrolling="1">     
<div class="vd_body">
<!-- Header Start -->

<!-- Header Ends --> 
<div class="content"><div class="container">


<!-- Middle Content Start -->

<div class="vd_content-wrapper">
    <div class="vd_container">
    
        <div class="vd_content clearfix">               
            <div class="vd_content-section clearfix">  

            <div class="vd_login-page"> 
            <div id="action_gif" class="my_notification"
		style="display: none; left: 47%;">Loading...</div>    
            	<div class="heading clearfix">
                	<div class="logo"><h2 class="mgbt-xs-5"><img style="max-height: 90px;" src="images/logo_login.png" alt="logo"></h2></div>
                    <h4 class="text-center font-semibold vd_grey" style="color: #fff !important;">Reset Password Form</h4>                     
                </div>
               
                <div class="panel widget">
                    <div class="panel-body">
                    
                          <div class="login-icon">
                                <img src="forget_pass/lock1.png" height="64px" width="64px" style=" margin-top: 23px !important; opacity: 0.8;">
                          </div>      
                          <div id="password-success" class="alert alert-success vd_hidden"><i class="fa fa-exclamation-circle fa-fw"></i> Your reset password form has been sent to your email </div>              
                        
                <!--   <div class="alert alert-danger vd_hidden">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true"><i class="icon-cross"></i></button>
                    <span class="vd_alert-icon"><i class="fa fa-exclamation-circle vd_red"></i></span><strong>Oh snap!</strong> Change a few things up and try submitting again. </div>
                  <div class="alert alert-success vd_hidden">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true"><i class="icon-cross"></i></button>
                    <span class="vd_alert-icon"><i class="fa fa-check-circle vd_green"></i></span>Your reset password form has been sent to your email. </div>    -->                         
                             <div class="form-group mgbt-xs-20">
                                 <div class="col-md-12">
                                 	<p class="text-center"><strong style="color: #fff !important;">To reset your password, enter the email address you use to sign in.</strong> </p>
                                    <div class="vd_input-wrapper" id="email-input-wrapper">
                                        <span class="menu-icon">
                                           <img src="forget_pass/email.png" style=" opacity: 0.5;">
                                        </span>
                                        <input type="email" placeholder="Email" id="email" name="email" required="required" />
                                    </div>   
                                
                                  </div>                            
                            </div>   
                            
                                                                                                        
                            <div class="form-group" id="submit-password-wrapper">
                              <div class="col-md-12 text-center mgbt-xs-5">
                                  <button onclick="resetPass()" class="btn vd_bg-green vd_white width-100" type="submit" id="submit-password" name="submit-password">Send me my password</button>   
                              </div>

                            </div>
                      

                          
                    </div>
                </div> <!-- Panel Widget --> 
                <div class="register-panel text-center font-semibold">	
                	<a href="login" style="color: #fff !important;">LOGIN</a> 	
                </div>
                </div> <!-- vd_login-page -->

                
                                                           
            </div>   
          <!-- .vd_content-section --> 
          
        </div>
        <!-- .vd_content --> 
      </div>
      <!-- .vd_container --> 
    </div>
    <!-- .vd_content-wrapper --> 
    
    <!-- Middle Content End --> 
    
  </div>
  <!-- .container --> 
</div>
<!-- .content -->
  
  </div>


</body>
</html>