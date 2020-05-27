package webmail.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.Connections;
import com.sieve.manage.examples.ConnectAndListScripts;

import webmail.bean.MailAccSetting;
import webmail.dao.ForgetpasswordDao;
import webmail.model.Forgetpassword;

import webmail.webservice.client.WebmailClient;
import webmail.wsdl.GetLdapFNameResponse;
import webmail.wsdl.GetLdapOneAttOtrUserResponse;
import webmail.wsdl.GetWebmailAllMailCountResponse;
import webmail.wsdl.GetWebmailFolderResponse;
import webmail.wsdl.GetWebmailUnreadMailCountResponse;

@Controller
public class ForgetPassController {

	
	@Autowired
	private WebmailClient webmailClient;
	
	public  DirContext getConnection(String url,String uid,String password)
	{
		    DirContext ctx=null;
		    try
		    {
		    	String username=uid;
		    	
		   
		    //System.out.println("@@@@@@@@@@@@@@@@@@ ldap user="+username);
		    Hashtable env = new Hashtable();
		    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		    env.put(Context.PROVIDER_URL, url); // LDAP host and base

		    // LDAP authentication options
		    env.put(Context.SECURITY_AUTHENTICATION, "simple");
		    env.put(Context.SECURITY_PRINCIPAL, username);
		    env.put(Context.SECURITY_CREDENTIALS, password);

		     ctx = new InitialDirContext(env);
		
		    }
		    catch(Exception e)
		    {
		    	System.out.print(e.toString());
		    	e.printStackTrace();
		    	
		    }
		    return ctx;
	}
	
	public void closeConn(DirContext ctx)
	{
		try
	    {
	   if(ctx!=null)
	   {
	     ctx.close();
	   }
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	//System.out.print(e.toString());
	    	
	    }
	}
	
	
	public boolean modyfyRepAttr(DirContext ctx, String MY_ENTRY, String attr, String val)
	{
		boolean status=true;
		try
	    {
			 ModificationItem[] mods = new ModificationItem[1];
				//Attribute mod1 = new BasicAttribute("FTP", "TRUE");
				  // Attribute mod1 = new BasicAttribute("phone", "555-555-5555");
			  javax.naming.directory.Attribute mod0 = new javax.naming.directory.BasicAttribute(attr, val);


				    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
				    ctx.modifyAttributes(MY_ENTRY, mods);

	    }
	    catch(Exception e)
	    {
	    	status=false;
	    	e.printStackTrace();
	    	//System.out.print(e.toString());
	    	
	    }
		return status;
	}
	
	
	public String getLdapAttOtrUser(String ldapurl,String uid, String pass, String base, String searchbase, String attname)
	{
		
		String attval="";
		try
		{
		DirContext ctx=getConnection(ldapurl,uid,pass);
		
		//System.out.println("@@@@@@@@@@@@@@@@@ctx="+ctx);
		
		SearchControls constraints = new SearchControls(); 
		constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		String attrList[] = {attname}; 
		constraints.setReturningAttributes(attrList);
		NamingEnumeration results =ctx.search(base,"mail="+searchbase, constraints);
		int f=0;
		int x=0;
		while (results.hasMore()) {
		f=1;
		    SearchResult si =(SearchResult)results.next();
		    String ck=si.getName();
		   // System.out.println("@@@@@@@@@@@ck="+ck);
		    Attributes attrs = si.getAttributes();
		    if (attrs == null) {
		        continue;
		    }
		    
		    NamingEnumeration ae = attrs.getAll(); 
		    while (ae.hasMoreElements()) {
		        Attribute attr =(Attribute)ae.next();
		        String id = attr.getID();
		        Enumeration vals = attr.getAll();
		        while (vals.hasMoreElements())
		        {
		      //  String str= vals.nextElement().toString();
		     //   System.out.print("********************str="+str);
		        if(id.equalsIgnoreCase(attname) )
		        {
		        	
		        	attval=vals.nextElement().toString();
		        	
		        }
		      }
		    }
		 

		}
		

		
		closeConn(ctx);
		}
		catch(NamingException e)
		{
			//System.out.print(e.toString());
			e.printStackTrace();
		}
		catch(Exception e)
		{
			//System.out.print(e.toString());
			e.printStackTrace();
		}
		//System.out.print("********************fname="+attval);
		return  attval;        
}
	
	
	@RequestMapping(value = "/forget", method = RequestMethod.GET)
	public String forget(ModelMap map) 
	{
		
		return "forget";
	}
	
	
	
@RequestMapping(value = "/setForgetPass", method = RequestMethod.GET)
@ResponseBody
	public String setForgetPass(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		String ldapurl ="ldap://127.0.0.1:389";	
		String dom="wihg.res.in";
	
		String searchbase=request.getParameter("email");
		String arr[]=searchbase.split("@");
		dom=arr[arr.length-1];
		String base1 ="dc=wihg,dc=res,dc=in";
		String sendmailfromid="postmaster@"+dom;;
		String sendpass="";
		String uid="cn=admin,"+base1;
		String pass="Nir9898Bhay89#9";
		String base="ou=Users,domainName="+dom+",o=domains,"+base1; 
		String host="127.0.0.1";
		String port="25"; 
		
		
		
		String sendmailtoid=getLdapAttOtrUser(ldapurl, uid,pass,  base, searchbase, "backupMailAddress");
		
		String res="true";
		if(sendmailtoid==null || sendmailtoid=="" )
		{
			res="Backup Email Address does not exist. Please contact to your IT Admin.";
		}
		else
		{
			try
			{
			DirContext ctx=getConnection(ldapurl,uid,pass);
			
			String MY_ENTRY="mail="+searchbase+","+base;
			String rand_pass=UUID.randomUUID().toString().replaceAll("-", "");
			boolean flg=modyfyRepAttr(ctx, MY_ENTRY, "userPassword", rand_pass);
			closeConn(ctx);
			if(flg)
			{
			
			
				
				final String username = sendmailfromid;//change accordingly
				final String password = sendpass;//change accordingly
				
				Session ses =Connections.smtpConnectionNP(host, port, sendmailfromid, sendpass,false);

			    Message message = new MimeMessage(ses);
			  
			    message.setFrom(new InternetAddress(username, "Postmaster of "+dom));
			   
			    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sendmailtoid));
			    Date dt=new Date();
			    SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
				String     DateToStr = format.format(dt);
				message.setHeader("Date", DateToStr);
				message.setSubject("New password of "+searchbase);
				
				String cntt="Dear user,<br><br> UserId: "+searchbase+"<br> Password:<strong>"+rand_pass+"</strong>";
				
				message.setContent(cntt,"text/html; charset=UTF-8");

			    Transport.send(message);
			    
			    System.out.println("Message sent...");
				
				
			}
			else
			{
				res="false";
			}
			
			}
			catch(Exception e)
		    {
				res=e.toString();
		    	e.printStackTrace();
		    	
		    }
		}
		
		
		return res;
	}
	
@Autowired  
private ForgetpasswordDao forgetpasswordDao; 
@RequestMapping(value = "/generateForgetPass", method = RequestMethod.GET)
@ResponseBody
	public String generateForgetPass(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	
	String ldapurl ="ldap://127.0.0.1:389";	
	String dom="wihg.res.in";
	
	String searchbase=request.getParameter("email");
	String arr[]=searchbase.split("@");
	if(arr.length>1)
	{
		dom=arr[1];
	}
	String base1 ="dc=wihg,dc=res,dc=in";
	String sendmailfromid="postmaster@"+dom;
	String sendpass="";
	String uid="cn=admin,"+base1;
	String pass="Nir9898Bhay89#9";
	String base="ou=Users,domainName="+dom+",o=domains,"+base1; 
	String host="127.0.0.1";
	String port="25";

	
	
	
	String sendmailtoid=getLdapAttOtrUser(ldapurl, uid,pass,  base, searchbase, "backupMailAddress");
	
	String res="true";
	if(sendmailtoid==null || sendmailtoid.length()==0 )
	{
		res="Backup Email Address does not exist. Please contact to your IT Admin.";
	}
	else
	{
	Forgetpassword fpwd=new Forgetpassword();
	
		Date previous_time=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(previous_time);
		calendar.add(Calendar.HOUR, 1);
		previous_time = calendar.getTime();
		 String uidnm=	  UUID.randomUUID().toString();
	fpwd.setEmail(searchbase);
	fpwd.setExpdate(previous_time);
	fpwd.setPasstoken(uidnm);
	fpwd.setRecoveryemail(sendmailtoid);
	int sn=forgetpasswordDao.addForgetPwd(fpwd);
	
	//String path=request.getServletPath();
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	 byte[] encoded1 = Base64.encodeBase64(uidnm.getBytes());     
	 String tok1=new String(encoded1);
    
	 byte[] encoded2 = Base64.encodeBase64(searchbase.getBytes());     
	 String tok2=new String(encoded2);
    
	 byte[] encoded3 = Base64.encodeBase64(sendmailtoid.getBytes());     
	 String tok3=new String(encoded3);
    
	 String gen_url=basePath+"resetpassword?tok="+sn+"&tok1="+tok1+"&tok2="+tok2+"&tok3="+tok3;
		try
		{
		final String username = sendmailfromid;//change accordingly
		final String password = sendpass;//change accordingly
		
		Session ses =Connections.smtpConnectionNP(host, port, sendmailfromid, sendpass,false);


	    Message message = new MimeMessage(ses);
	  
	    message.setFrom(new InternetAddress(username, "Postmaster of "+dom));
	   
	    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sendmailtoid));
	    Date dt=new Date();
	    SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
		String     DateToStr = format.format(dt);
		message.setHeader("Date", DateToStr);
		message.setSubject("New password of "+searchbase);
		
		String cntt="Dear "+searchbase+",<br><br> ";
		cntt+="This email was sent automatically by Sandesh Enterprise Messaging Solutions in response to your request to reset your password. You can take the next step in the password recovery process.<br><br>";
		cntt+="To reset your password and access your account, either click on the following link or copy and paste the following link (expires in 1 hours) into the address bar of your browser:<br><br>";
		cntt+="<a href='"+gen_url+"' target='_blank'>"+gen_url+"</a>";
		message.setContent(cntt,"text/html; charset=UTF-8");

	    Transport.send(message);
	    
	    System.out.println("Message sent...");
	   
		}
		catch(Exception ee)
		{
			res="false";
			ee.printStackTrace();
		}
	 
	 
	}
	return res;
	}
	
@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	public ModelAndView resetpassword(ModelMap map, Principal principal, HttpServletRequest request) 
	{
	String tok=request.getParameter("tok");
	String tok1=request.getParameter("tok1");
	String tok2=request.getParameter("tok2");
	String tok3=request.getParameter("tok3");
	int sn=-1;
	if(tok!=null && tok.length()>0)
	{
		sn=Integer.parseInt(tok);
	}
	
	 byte[] decoded1 = Base64.decodeBase64(tok1); 
	 String token= new String(decoded1);
	 
	 byte[] decoded2 = Base64.decodeBase64(tok2); 
	 String email1= new String(decoded2);
	 
	 byte[] decoded3 = Base64.decodeBase64(tok3); 
	 String email2= new String(decoded3);
	 
	
	Forgetpassword fpwd=forgetpasswordDao.getForgetpassword( email1,  email2,  token,  sn);
	if(fpwd!=null)
	{
		Date dt=fpwd.getExpdate();
		Date crdt=new Date();
		if(crdt.after(dt))
		{
			map.addAttribute("status", "expired");
		}
		else
		{
			map.addAttribute("status", "true");
		}
	}
	else
	{
		map.addAttribute("status", "invalid");
	}
	
	map.addAttribute("tok", tok);
	map.addAttribute("tok1", tok1);
	map.addAttribute("tok2", tok2);
	map.addAttribute("tok3", tok3);
	return new ModelAndView("resetpassword", map);
	//return "redirect:/recoverassword?tok1="+tok1+"&tok2="+tok2+"&tok3="+tok3;
	}


@RequestMapping(value = "/changeForgetPass", method = RequestMethod.GET)
@ResponseBody
public String changeForgetPass(ModelMap map, Principal principal, HttpServletRequest request) 
{

	

String res="true";	
String npass=request.getParameter("npass");	
String cpass=request.getParameter("cpass");
String tok=request.getParameter("tok");
String tok1=request.getParameter("tok1");
String tok2=request.getParameter("tok2");
String tok3=request.getParameter("tok3");
if(npass==null || npass.length()==0)
{
	return "New Password field must be filled.";
}

if(cpass==null || cpass.length()==0)
{
	return "Confirm Password field must be filled.";
}

if(cpass.equalsIgnoreCase("npass"))
{
	return "New Password & Confirm Password are not matched.";
}

try
{
int sn=-1;
if(tok!=null && tok.length()>0)
{
	sn=Integer.parseInt(tok);
}

 byte[] decoded1 = Base64.decodeBase64(tok1); 
 String token= new String(decoded1);
 
 byte[] decoded2 = Base64.decodeBase64(tok2); 
 String email1= new String(decoded2);
 
 byte[] decoded3 = Base64.decodeBase64(tok3); 
 String email2= new String(decoded3);
 

Forgetpassword fpwd=forgetpasswordDao.getForgetpassword( email1,  email2,  token,  sn);
if(fpwd!=null)
{
	Date dt=fpwd.getExpdate();
	Date crdt=new Date();
	if(crdt.after(dt))
	{
		return "Link is expired.";
	}
	else
	{

		
		String ldapurl ="ldap://127.0.0.1:389";	
		String dom="wihg.res.in";
		String base1 ="dc=wihg,dc=res,dc=in";
		String uid="cn=admin,"+base1;
		String pass="Nir9898Bhay89#9";
		
		String arr[]=email1.split("@");
		if(arr.length>1)
		{
			dom=arr[1];
		}
		String base="ou=Users,domainName="+dom+",o=domains,"+base1; 
		try
		{
		DirContext ctx=getConnection(ldapurl,uid,pass);
		String MY_ENTRY="mail="+email1+","+base;
		boolean flg=modyfyRepAttr(ctx, MY_ENTRY, "userPassword", npass);
		if(flg)
		{
			res="true";
		}
		else
		{
			res="false";
		}
		closeConn(ctx);
		
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
			res="false";
		}
	}
}
else
{
	return "Link is invalid.";
}
}
catch(Exception e)
{
	e.printStackTrace();
	res="false";
}

return res;
}

}
