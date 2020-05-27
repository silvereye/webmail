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




function changePass() {
	var npass=$("#npass").val();
	var cpass=$("#cpass").val();
	if(npass==null || npass=="" )
		{
		showmsg("alert","Enter new password");
		}
	else if(cpass==null || cpass=="")
		{
		showmsg("alert","Enter confirm password");
		}
	else if(cpass!=npass)
	{
	showmsg("alert","New password & confirm password are not matched.");
	}
	else
		{
		
		var tok=$("#tok").val();
		var tok1=$("#tok1").val();
		var tok2=$("#tok2").val();
		var tok3=$("#tok3").val();
		$.ajax({
   	         type: "GET",
   	         url: "${pageContext.request.contextPath}/changeForgetPass",
   	         data: {'npass': npass, 'cpass': cpass, 'tok': tok, 'tok1': tok1, 'tok2': tok2, 'tok3': tok3  },
   	         contentType: "application/json",
   	         success: function (data) {
   	        	 $("#action_gif").hide();
   	             if(data=="true")
   	            	 {
   	        	 	showmsg("success","your password has been changed.");
   	        	 $("#npass").val("");
   	      		 $("#cpass").val("");
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
            <%
            String st=request.getAttribute("status").toString();
            System.out.println(">>>>>>>>>>>>>>>>>>statuc="+st);
            if(st.equalsIgnoreCase("expired"))
            {
            	%>
            <div  class="my_notification"
		style="display: block; left: 47%;     top: 15%;">This link is expired.</div>    
		<%}
            else if(st.equalsIgnoreCase("invalid"))
            {
        %>
            <div class="my_notification"
		style="display: block; left: 47%;     top: 15%;">This link is invalid.</div>    
		<%}
            
            else
            {
            	 String tok=request.getAttribute("tok").toString();
            	 String tok1=request.getAttribute("tok1").toString();
            	 String tok2=request.getAttribute("tok2").toString();
            	 String tok3=request.getAttribute("tok3").toString();
            %>
            <input type="hidden" id="tok" value="<%=tok %>"/>
            <input type="hidden" id="tok1" value="<%=tok1 %>"/>
            <input type="hidden" id="tok2" value="<%=tok2 %>"/>
            <input type="hidden" id="tok3" value="<%=tok3 %>"/>
            	<div class="heading clearfix">
                	<div class="logo"><h2 class="mgbt-xs-5"><img style="max-height: 90px;" src="images/logo_login.png" alt="logo"></h2></div>
                    <h4 class="text-center font-semibold vd_grey">RESET PASSWORD FORM</h4>                     
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
                                 	
                                   <!--  <div class="vd_input-wrapper" id="email-input-wrapper">
                                        <span class="menu-icon">
                                           <img src="forget_pass/email.png" style=" opacity: 0.5;">
                                        </span>
                                        <input type="email" placeholder="Email" id="email" name="email" required="required" />
                                    </div>    -->
                                
                                 <div class="vd_input-wrapper" id="email-input-wrapper">
                                        <span class="menu-icon">
                                           <img src="forget_pass/lock1.png" height="20px" width="30px" style=" opacity: 0.7;">
                                        </span>
                                        <input type="password" placeholder="New Password" id="npass" name="npass" required="required" />
                                    </div>
                                     <div class="vd_input-wrapper" id="email-input-wrapper" style="margin-top: 20px">
                                        <span class="menu-icon">
                                           <img src="forget_pass/lock1.png" height="20px" width="30px" style=" opacity: 0.7;">
                                        </span>
                                        <input type="password" placeholder="Confirm Password" id="cpass" name="cpass" required="required" />
                                    </div>
                                  </div>                            
                            </div>   
                            
                                                                                                        
                            <div class="form-group" id="submit-password-wrapper">
                              <div class="col-md-12 text-center mgbt-xs-5">
                                  <button onclick="changePass()" class="btn vd_bg-green vd_white width-100" type="submit" id="submit-password" name="submit-password">Change Password</button>   
                              </div>

                            </div>
                      

                          
                    </div>
                </div> <!-- Panel Widget --> 
                <div class="register-panel text-center font-semibold">	
                	<a href="login">LOGIN</a> 	
                </div>
                
                <%} %>
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