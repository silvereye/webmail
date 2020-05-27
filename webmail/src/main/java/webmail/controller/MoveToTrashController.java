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
import webmail.wsdl.RemoveWebmailFlagResponse;
import webmail.wsdl.SetWebmailFlagResponse;

@Controller
public class MoveToTrashController {
	
	@Autowired
	private WebmailClient webmailClient;
	
	@RequestMapping(value = "/webmailMoveToTrash", method = RequestMethod.GET)
	@ResponseBody
	public synchronized String mailFlagAction(ModelMap map, Principal principal, HttpServletRequest request) 
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
		String active_folder=(String)hs.getAttribute("active_folder");
		
		GetWebmailMoveTrashResponse mtot=webmailClient.moveToTrashRequest(host, port, id, pass, fldrnm, uid);
		status=mtot.isMoveTrashStatus();
	
		return ""+status;
	}
	
}


