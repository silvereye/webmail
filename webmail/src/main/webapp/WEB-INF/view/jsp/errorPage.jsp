<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sun.mail.imap.IMAPFolder"%>
<%@ page language="java" import="java.util.*,
 javax.activation.DataHandler,
 javax.mail.*,
 javax.mail.internet.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href='http://fonts.googleapis.com/css?family=Fjalla+One' rel='stylesheet' type='text/css'>
<style type="text/css">
body{
	background:url(./images/bg.jpg) ;
}	
.wrap{
	margin:0 auto;
	width:1000px;
}
.title{
	margin-bottom: 40px;
}	
.title h1{
	font-size:100px;
	color:yellow;
	text-align:center;
	margin-top:100px;
	text-shadow:6px 1px 6px #333;
	font-family: 'Fjalla One', sans-serif;
}
.title h4{
	font-size:20px;
	color:#fff;
	text-align:center;
	margin-bottom:1px;
	text-shadow:6px 1px 6px #333;
	font-family: 'Fjalla One', sans-serif;
	margin-top: 50px;
}		
.logo p{
	color:white;
	font-size:25px;
	margin-top:1px;
	font-family: 'Fjalla One', sans-serif;
}	
.gray {
	margin-bottom: 20px;
	background: rgba(12, 52, 77, 0.34);
	text-shadow: 0 -1px 1px rgba(0, 0, 0, 0.25);
	border-radius: 4px;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	-o-border-radius: 4px;
	color:yellow;
	text-decoration:none;
	padding:30px 30px;
	font-size: 20px;
	font-weight:bold;
	font-family: 'Fjalla One', sans-serif;
	text-align: center;
}	
.ag-3d_button.orange {
	box-shadow: #d75124 0 3px 0px, #d75124 0 3px 3px;
}
.ag-3d_button {
	vertical-align: top;
	border-radius: 4px;
	border: none;
	padding: 10px 25px 12px;
}
.orange {
	background: #d75124;
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#fdde02', endColorstr='#dec829',GradientType=0 );
	color:#fff;
	text-shadow:1px 1px 3px #d75124;
	border: 1px solid #d75124;
	text-decoration: none;
}
.orange:hover {
	background: #d75124;
	
}
.footer{
	color:white;
	position:absolute;
	right:10px;
	bottom:10px;
}	
.footer a{
	color:yellow;
}	
</style>
</head>
<body>
	<div class="wrap">
	   <div class="logo">
	   		<p><img class="head_logo" src="logo/silvereye.in.png"  id="main_logo"></p>
	   </div>
	   <div class="title">
	  		<h4 >
	  		The webmail you are visiting can only be viewed using a modern browser. Please update your browser to increase safety and your browsing experience.
	  		</h4>
	  </div>
	</div>
	<div class="wrap">
	    <div class="gray">
  	    	<a href="login" class="ag-3d_button orange">Go Back </a>
  	     </div>
   </div>
	
</body>