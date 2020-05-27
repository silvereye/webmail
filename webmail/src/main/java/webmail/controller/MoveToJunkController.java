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

import webmail.wsdl.GetWebmailMoveTrashResponse;
import webmail.wsdl.GetWebmailSpamResponse;
import webmail.wsdl.RemoveWebmailFlagResponse;
import webmail.wsdl.SetWebmailFlagResponse;

@Controller
public class MoveToJunkController {
	
	@Autowired
	private WebmailClient webmailClient;
	
	@RequestMapping(value = "/webmailMoveToJunk", method = RequestMethod.GET)
	@ResponseBody
	public String mailSpamAction(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		boolean status=false;
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String fldrnm= request.getParameter("folder");
		String folder_dest= request.getParameter("folder_dest");
		String act_fldr=hs.getAttribute("active_folder").toString();
		if(!fldrnm.equalsIgnoreCase(act_fldr))
		{
			fldrnm=act_fldr;
		}
		
		if(fldrnm.equalsIgnoreCase("Junk"))
		{
			if(!folder_dest.equalsIgnoreCase("Inbox"))
			{
				folder_dest="Inbox";
			}
		}
		else
		{
			if(!folder_dest.equalsIgnoreCase("Junk"))
			{
				folder_dest="Junk";
			}
		}
		String uid= request.getParameter("uid");
		
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		GetWebmailSpamResponse mtot=webmailClient.webmailSpam(host, port, id, pass, fldrnm, folder_dest, uid);
		status=mtot.isSpamStatus();
		return ""+status;
	}
	
	
	
	@RequestMapping(value = "/webmailMoveToFolder", method = RequestMethod.GET)
	@ResponseBody
	public String mailMoveToFolder(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		boolean status=false;
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String fldrnm= request.getParameter("folder");
		String act_fldr=hs.getAttribute("active_folder").toString();
		if(!fldrnm.equalsIgnoreCase(act_fldr))
		{
			fldrnm=act_fldr;
		}
		
		String folder_dest= request.getParameter("folder_dest");
		if(fldrnm.equalsIgnoreCase(folder_dest))
		{
			return ""+status;
		}
		String uid= request.getParameter("uid");
		
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		GetWebmailSpamResponse mtot=webmailClient.webmailSpam(host, port, id, pass, fldrnm, folder_dest, uid);
		status=mtot.isSpamStatus();
		return ""+status;
	}
	
	
}


