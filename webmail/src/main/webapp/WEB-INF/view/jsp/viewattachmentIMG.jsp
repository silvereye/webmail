<%@page import="webmail.wsdl.Attachment"%>
<%@page import="webmail.wsdl.InboxDisplay"%>
<%@page import="webmail.wsdl.*"%>
<%@page import="webmail.webservice.client.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%


String nm=(String)request.getParameter("nm");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.8.3.min.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function(){ 	
	
$.ajax({
type : "GET",
url : "${pageContext.request.contextPath}/viewUploadedDocIMG",
data : {
	'nm':'<%=nm %>'
	},
contentType : "application/json",
success : function(data) {
	if(data=="<$expire$>")
	{
	location.href ="${pageContext.request.contextPath}/inbox";
		}
//	alert(data);
	var str="<img src='data:image/jpg;base64,"+data+" ' />";

	$("#hid_img_disp").html(str);
	
}	

});
});
</script>
</head>
<body >

 <div id="hid_img_disp" style="
    text-align: center;
">
Loading...
 </div>
</body>
</html>