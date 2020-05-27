<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!-- <script src="http://code.jquery.com/jquery-1.7.2.min.js" ></script> -->
 <script src="js/jquery-1.8.3.min.js"></script>
<style>
.web_dialog_overlay {
position: fixed;
top: 0;
right: 0;
bottom: 0;
left: 0;
height: 100%;
width: 100%;
margin: 0;
padding: 0;
background: #000000;
display: none;
opacity: inherit !important;
background: rgba(247, 247, 247, 0.7) !important;
}
.preview_images_pass{ z-index:10; width:390px;  margin:0 auto; background: #F0F0F0;box-shadow: 0 0 15px;  position:relative; display:none;margin-top: 179px;}
.close_prev_pass{position: absolute;
top: 0px;
background: #000;
width: 16px;
border-radius: 50%;
height: 16px;
cursor: pointer;
color: #fff;
text-align: center;
line-height: 13px;
right: 0px;
font-family: arial;
font-size: 13px;
margin-top: 6px;
margin-right: 10px;}
.preve_heading_pass{border-bottom: 1px solid #ccc;
background: #ccc;}
.preve_heading_pass>h1{margin: 0px;
font-size: 13px;
font-family: arial;
padding: 7px;}
.inner_prev_page_pass{overflow:auto;
padding-left: 11px;
padding-right: 11px;}
.pass_button{}
.save_pass,.cancel_pass{font-family: Arial, Helvetica, sans-serif;
font-size: 13px;
float: left;
padding: 5px;
background: #E1E1E1;
margin-right: 10px;
width: 72px;
text-align: center;
margin-bottom: 13px;
border: 1px solid #ccc;
border-radius: 3px;
cursor: pointer;
margin-top: 11px;}
.save_pass{}
.cancel_pass{}
.inner_prev_page_pass  >ul{margin: 0px;margin-top: 11px;
padding: 0px;
list-style-type: none;}
.inner_prev_page_pass >ul >li{float: left; font-family:Arial, Helvetica, sans-serif;padding: 5px;}
.inner_prev_page_pass >ul >li >input{ outline:none;padding: 5px;}
.clear{ clear:both;}
.pass_text{font-size: 13px;
width: 138px;
/* padding: 10px; */
line-height: 24px;}
</style>
<script type="text/javascript">
function hi() {
	$('.preview_images_pass').show();
	$('.web_dialog_overlay').show();
}

function chgPass() {
	var pswd=document.getElementById("pswd").value;
	var cpswd=document.getElementById("cpswd").value;
		if(pswd=="" || cpswd=="" || pswd==null || cpswd==null)
		{
			  alert("All Fields must be Filled .");
		}
		else
			{
			if(pswd!=cpswd)
				{
				 alert("Password is not matched.");
				}
			else
				{
				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/changeAccPasswordFirst",
					data : {
						 'new_pass': pswd, 'con_new_pass': cpswd
					},
					contentType : "application/json",
					success : function(data) {
						alert(data);
						window.location.href="${pageContext.request.contextPath}/logout";
						
					}
				});
				}
			
		 }
	}
		
			
	

</script>
</head>

<body onload="hi()">
<a class="cssbuttongo" href="logout"></a>
<!-- <div class="pop"> Click Here </div> -->
<div class="preview_images_pass">  

      <!-------// HEADING ------> 
       <div class="preve_heading_pass">
             <h1>Change Password </h1>
             <div class="close_prev_pass">x</div>
       </div>
     <!------// HEADING END -------->
   <!----// KEEP THE INNER CONTENT ------>
   <div class="inner_prev_page_pass">
          <ul>
              <li class="pass_text">New Password</li>
              <li><input id="pswd" type="password" /></li>
              <div class="clear"></div>
              <li class="pass_text">Confirm New Password</li>
              <li><input id="cpswd" type="password" /></li>
          </ul>
          <div class="clear"></div>
          <div class="pass_button">
              <div onclick="chgPass()" class="save_pass">Save</div>
              <div class="cancel_pass">Cancel</div>
          </div>
          <div class="clear"></div>
   </div>
   <!-----// KEET THE INNER CONTENT ------>
  </div>
<div class="web_dialog_overlay">
</div>
</body>
<script>
$('.pop').click(function(){
	
	$('.preview_images_pass').show();
	$('.web_dialog_overlay').show();

	});

// CLOSE THE FILE 
$('.close_prev_pass').click(function(){
	
	$('.preview_images_pass').hide();
	$('.web_dialog_overlay').hide();
	
	});	
	
	
	/// CANCEL 
	
	$('.cancel_pass').click(function(){
	
	$('.preview_images_pass').hide();
	$('.web_dialog_overlay').hide();
	
	});	
</script>
</html>