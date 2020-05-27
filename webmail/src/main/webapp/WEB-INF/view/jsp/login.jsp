 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	 <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
  <head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Login Page</title>
	<meta http-equiv="Pragma" content="no-cache"> 
 <meta http-equiv="Cache-Control"      content="no-cache"> 

	<!-- Bootstrap -->
	<link href="login_css/bootstrap.css" rel="stylesheet" media="screen">
	<link href="login_css/custom.css" rel="stylesheet" media="screen">
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
	<!-- Animo css-->
	<link href="login_css/animate+animo.css" rel="stylesheet" media="screen">
	
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	  <script src="assets/js/html5shiv.js"></script>
	  <script src="assets/js/respond.min.js"></script>
	<![endif]-->
	<script type="text/javascript">
var appnm=navigator.appName;
if(appnm.indexOf("Internet Explorer")!=-1){ 
	 var ver=navigator.appVersion;
	 //alert(ver)
	 if(!(ver.indexOf("MSIE 10")>=0 || ver.indexOf("MSIE 9")>=0 ))
		 {
		// alert("good IE");
		 location.href ="${pageContext.request.contextPath}/errorPage";
		 }
	 /* else{
		 alert("bad IE");
	 } */
}
/* else{
	 alert("good brower");
} */

</script>
<script src="js/jquery-1.8.3.min.js"></script>
	
	<!-- Load jQuery -->
	<!-- <script src="login_js/jquery.v2.0.3.js"></script> -->



<script type="text/javascript">
function openMsg()
{
	
//alert('qqqqqqqqqqqqq');
// window.setTimeout(function bye(){errorMessage();document.getElementById("err_div").style.visibility='hidden';}, 2000);
	 window.setTimeout(function bye(){document.getElementById("err_div").style.visibility='hidden';}, 2000);
}

/* function openPro()
{
	var id=$('#id').val();
	var pass=$('#pass').val();
	if(id!=null && id!="" && pass!=null && pass!="")
		{
		  $('#load_div').css( "display","block");
		  $('#load_div').html( "<img src='images/ajax-login.gif' style=' width: 70px; margin-bottom: 2px;  margin-right: 12px;'>");
		
		//document.getElementById("load_div").style.display='block';
		}
} */
</script>
<script type="text/javascript">
 function isCapLockOn(e)

{

    var charKeyCode = e.keyCode ? e.keyCode : e.which; // To work with both MSIE & Netscape

    var shiftKey = e.shiftKey ? e.shiftKey : ((charKeyCode == 16) ? true : false);

     

    // Check both the condition as described above

    if (((charKeyCode >= 65 && charKeyCode <= 90) && !shiftKey)

    || ((charKeyCode >= 97 && charKeyCode <= 122) && shiftKey))

    {

        // Caps lock is on
        $('#load_div').css( "display","block");
		$('#load_div').html( "Caps lock : <b>On</b>");
       // document.getElementById('load_div').innerHTML = "Caps lock : <b>On</b>";

    }

    else

    {

        // Caps lock is off.
		$('#load_div').html( "");
		 $('#load_div').css( "display","none");
		//document.getElementById('load_div').innerHTML="";

    }

}
 
 
 
 $(document).ready(function() {

	 
     $('form[name=login-form]').submit(function() {
    	 var id=$('#id').val();
    		var pass=$('#pass').val();
    		if(id!=null && id!="" && pass!=null && pass!="")
    			{
    			  $('#load_div').css( "display","block");
    			  $('#load_div').html( "<img src='images/ajax-login.gif' style=' width: 70px; margin-bottom: 2px;  margin-right: 12px;'>");
    			return true;
    			//document.getElementById("load_div").style.display='block';
    			}
    		else
    			{
    			
    			 $('#alert_div').css( "display","block");
    			 $('#alert_div').html( "<div style='margin-top: 15px; color: white;' >Please Fill Id and Password.</div>");
        		 return false;
    			}
     });
     
     $( "#id" ).focus(function() {
    	 $('#alert_div').css( "display","none");
    	});
     
     $( "#pass" ).focus(function() {
    	 $('#alert_div').css( "display","none");
    	});
     
 });
 

    </script>

  </head>
  <body >

<%        
response.setHeader("Cache-Control", "no-cache");

//Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control", "no-store");

//Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0);

//Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma", "no-cache");
//HTTP 1.0 backward enter code here

%>

	<!-- 100% Width & Height container  -->
	<div class="login-fullwidith">

		<!-- error msg  -->
		<%
		String err=request.getParameter("stat");
		if(err!=null && !(err.equals("")))
		{
			String msg="Login Failed";
			if(err.equals("auth"))
			{
				msg="Authentication Failed";
			}
			else if(err.equals("conn"))
			{
				msg="Connection Refused";
			}
			else if(err.equals("ses"))
			{
				msg="Session Expired";
			}
		%>
		 <script type="text/javascript">
		
		 
		// alert('hiii');
		 openMsg();
		 
		 </script>
		<div id="err_div" style="margin-top: 50px ! important; width: 354px; text-align:center;" class="login-c3">
				
				<div style="margin-top: 15px; color: white;" ><%=msg %></div>
		</div>
		
		<%} %>
		<div id="alert_div" style="margin-top: 50px ! important; width: 354px; text-align:center; display: none;" class="login-c3">
				
				
		</div>
		<!-- Login Wrap  -->
		<form:form method="post" action="dologin" id="login-form"  name="login-form" >
		<div class="login-wrap">
            <div class="logo_new" >
                <img height="60px" src="images/logo_login.png" />
            </div>
			<div class="login-c1">
			
				<div class="cpadding50">
					<div class="username"></div><input type="text" name="id" id="id" required="required" class="form-control logpadding username" placeholder="Username" >
					<br/>
					<div class="password"></div><input type="password" onkeypress="isCapLockOn(event)" name="pass" id="pass" required="required" class="form-control logpadding password" placeholder="Password" >
				</div>
			
			</div>
			<div class="login-c2">
				<div class="logmargfix">
					<div class="chpadding50">
							<div class="alignbottom">
								<input class="btn-search4"   type="submit" value="Sign in" />					
							</div>
							<div class="alignbottom2">
							  <div class="checkbox">
								<label>
								  <!-- <input type="checkbox">Remember -->
								</label>
							  </div>
							</div>
							<div id="load_div" style="bottom: 6px;margin-right: 3%; position: absolute; right: 48px; display:none;"><img width="80px" src="images/ajax-login.gif" /></div>	
					</div>
				</div>
			</div>
			<div class="login-c3">
				
				<div class="right"><a href="forget" class="whitelink">Forgot Password?</a></div> 
			</div>			
		</div>
		</form:form>
		<!-- End of Login Wrap  -->
	
	</div>	
	<!-- End of Container  -->

	<!-- Javascript  -->
	<!--<script src="login_js/initialize-loginpage.js"></script>-->
	<script src="login_js/jquery.easing.js"></script>
	<!-- Load Animo -->
	<script src="login_js/animo.js"></script>
	<script>
	function errorMessage(){
		$('.login-wrap').animo( { animation: 'tada' } );
	}
	</script>
	
  </body>
</html>