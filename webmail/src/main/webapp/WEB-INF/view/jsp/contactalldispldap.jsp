<%@ page language="java" import="webmail.wsdl.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
<%
GetContactDetailResponse conres=(GetContactDetailResponse)  request.getAttribute("GetFullConRes");

%>
<!-- <script src="js/jquery-1.8.3.min.js"></script>
<script src="js/contact_js.js"></script>
<link type="text/css" rel="stylesheet" href="css/contact_css.css" /> -->
 
       
    <% String flname=""; 
       String dept="";
       String email="";
       String mobwork="";
       String mobhome="";
       String mob="";
       String addrhome="";
       String photo="";
    
      
       if(conres.getGetContactFullDetail().getContactName()!= null && !(conres.getGetContactFullDetail().getContactName().equals("")  ))
       {
    	   flname=conres.getGetContactFullDetail().getContactName();
       }
       if(conres.getGetContactFullDetail().getContactDept()!= null && !(conres.getGetContactFullDetail().getContactDept().equals("")  ))
       {
    	   dept=conres.getGetContactFullDetail().getContactDept();
       }
       if(conres.getGetContactFullDetail().getContactAddress()!= null && !(conres.getGetContactFullDetail().getContactAddress().equals("")  ))
       {
    	   addrhome=conres.getGetContactFullDetail().getContactAddress();
       }
       if(conres.getGetContactFullDetail().getContactEmail()!= null && !(conres.getGetContactFullDetail().getContactEmail().equals("")  ))
       {
    	   email=conres.getGetContactFullDetail().getContactEmail();
       }
       if(conres.getGetContactFullDetail().getContactHomePhone()!= null && !(conres.getGetContactFullDetail().getContactHomePhone().equals("")  ))
       {
    	   mobhome=conres.getGetContactFullDetail().getContactHomePhone();
       }
       if( conres.getGetContactFullDetail().getContactMobile()!= null && !( conres.getGetContactFullDetail().getContactMobile().equals("")  ))
       {
    	   mob= conres.getGetContactFullDetail().getContactMobile();
       }
       if(conres.getGetContactFullDetail().getContactTel()!= null && !(conres.getGetContactFullDetail().getContactTel().equals("")  ))
       {
    	   mobwork= conres.getGetContactFullDetail().getContactTel();
       }
       if( conres.getGetContactFullDetail().getContactPhoto()!= null && !( conres.getGetContactFullDetail().getContactPhoto().equals("")  ))
       {
    	   photo=conres.getGetContactFullDetail().getContactPhoto();
       }
       
      //System.out.println("lpdap photo!!!!!!!!!!!!!!!!!!!!!!!!!!!!+"+photo);
      
         %>
          
          <table>
            <tr>
              <td colspan='3' class='right_info_heading'>Personal Information</td>
            </tr>
            <tr>
              <td class='right_name_first'>Full Name </td>
              <td class='right_text'><input type='text' value='<%=flname %>' />
                </td>
              <td rowspan='3'>
              <%
              if(photo!= null && !(photo.equals("")  ))
              {
            	 // String pht="http://mail.silvereye.in/photo/"+email+".jpg";
              %>
              <img style="height: 80px; width: 75px;" src='data:image/jpg;base64,<%=photo %>'/>
              <%}
              else
              {
              %>
              <img style="height: 80px; width: 75px;" src='images/contact_photo.png'/>
              <%} %>
              </td>
            </tr>
            <tr>
              <td class='right_name_first'>Email</td>
              <td><input type='text'  value='<%=email %>'/></td>
            </tr>
            <tr>
              <td class='right_name_first'>Department</td>
              <td><input type='text' value='<%=dept %>'/></td>
            </tr>
                       
           <tr class='hide_info'>
              <td colspan='3' class='right_info_heading'>Phone Number</td>
            </tr>
            <tr class='hide_info'>
              <td class='right_name_first'>Business</td>
              <td colspan='2'><input type='text' value='<%=mobwork %>' /></td>
            </tr>
            <tr class='hide_info'>
              <td class='right_name_first'>Home</td>
              <td colspan='2'><input type='text' value='<%=mobhome %>' /></td>
            </tr>
            <tr class='hide_info'>
              <td class='right_name_first'>Mobile</td>
              <td colspan='2'><input type='text' value='<%=mob %>' /></td>
            </tr>
            <tr class='hide_info'>
              <td colspan='3' class='right_info_heading'>Address</td>
            </tr>
            <tr class='hide_info'>
              <td class='right_name_first'>Home</td>
              <td colspan='2'><input type='text' value='<%=addrhome %>' />
              
              </td>
            </tr>
           
          </table>
         
          
 
</body>
</html>