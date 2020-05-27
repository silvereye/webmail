package webmail.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import webmail.webservice.client.WebmailClient;
import webmail.wsdl.SetWebmailSeenResponse;
import webmail.wsdl.SetWebmailUnSeenResponse;


@Controller
public class MailSeenActionController {
	
	@Autowired
	private WebmailClient webmailClient;
	
	@RequestMapping(value = "/webmailUnReadAtion", method = RequestMethod.GET)
	@ResponseBody
	public String mailUnseenAction(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		boolean status=false;
		String fldrnm= request.getParameter("folder");
		String uid= request.getParameter("uid");
		//HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		System.out.println("********************uids="+uid);
		System.out.println("******************** unread fldrnm="+fldrnm);
		SetWebmailUnSeenResponse sflag=webmailClient.setUnSeenActionRequest(host, port, id, pass, fldrnm, uid);
		status=sflag.isSetWebmailUnSeenStatus();
		System.out.println("********************"+status);
		
		return ""+status;
	}
	
	
	
	
	@RequestMapping(value = "/webmailReadAtion", method = RequestMethod.GET)
	@ResponseBody
	public String mailSeenAction(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		boolean status=false;
		String fldrnm= request.getParameter("folder");
		String uid= request.getParameter("uid");
		//HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		System.out.println("********************uids="+uid);
		System.out.println("******************** read fldrnm="+fldrnm);
		SetWebmailSeenResponse sflag=webmailClient.setSeenActionRequest(host, port, id, pass, fldrnm, uid);
		status=sflag.isSetWebmailSeenStatus();
		System.out.println("********************"+status);
		
		return ""+status;
	}

}
