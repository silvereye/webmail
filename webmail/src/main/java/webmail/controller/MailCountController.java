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

import webmail.bean.MailAccSetting;
import webmail.webservice.client.WebmailClient;
import webmail.wsdl.GetWebmailAllMailCountResponse;
import webmail.wsdl.GetWebmailUnreadMailCountResponse;

@Controller
public class MailCountController {

	
	@Autowired
	private WebmailClient webmailClient;
	
	
	
	@RequestMapping(value = "/getAllMailCountInfolderDiv", method = RequestMethod.GET)
	@ResponseBody
	public synchronized String getAllMailCntDiv(ModelMap map, Principal principal,  HttpServletRequest request)
	{
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String fdrenm=request.getParameter("path");
		String inboxcnt="";
		//System.out.println("********************folder="+fdrenm);
		GetWebmailAllMailCountResponse wfre=webmailClient.getWebmailAllMailCountRequest(host, port, id, pass,fdrenm);
		long mcount=wfre.getAllemailcount();
		//long end=MailAccSetting.limitMail;
		long end=Integer.parseInt(hs.getAttribute("limitMail").toString());
		if(end>mcount)
		{
			end=mcount;
		}
		if(mcount==0)
		{
			inboxcnt=fdrenm+" is Empty";	
		}
		else
		{
		inboxcnt="1&nbsp;-&nbsp;"+end+"&nbsp;of&nbsp;"+mcount;
		}
		inboxcnt+="$"+mcount;
		return inboxcnt;
	}
	
	
	
	
	@RequestMapping(value = "/getDraftMailCount", method = RequestMethod.GET)
	@ResponseBody
	public String getDraftMailCount(ModelMap map, Principal principal,  HttpServletRequest request)
	{
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String fdrenm="Drafts";
		String inboxcnt="";
		//System.out.println("********************folder="+fdrenm);
		GetWebmailAllMailCountResponse wfre=webmailClient.getWebmailAllMailCountRequest(host, port, id, pass,fdrenm);
		long mcount=wfre.getAllemailcount();
		
		if(mcount>0)
		{
			inboxcnt="("+ mcount +")";
		}
		
		return inboxcnt;
	}
	
	
	
	
	@RequestMapping(value = "/getUnreadMailCountInfolderDiv", method = RequestMethod.GET)
	@ResponseBody
	public String getUnreadMailCntDiv(ModelMap map, Principal principal,  HttpServletRequest request)
	{
	
		
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String fdr=request.getParameter("fdr");
		String inboxcnt="";
		GetWebmailUnreadMailCountResponse wfre=webmailClient.getWebmailUnreadMailCountRequest(host, port, id, pass,fdr);
		int mcnt  =wfre.getUnreademailcount();
		if(mcnt > 0)
		{
			inboxcnt="("+mcnt+")";
		}
		
		return inboxcnt;
		
	}
	
	@RequestMapping(value = "/getUnreadMailCountInbox", method = RequestMethod.GET)
	@ResponseBody
	public String getUnreadMailCountInbox(ModelMap map, Principal principal,  HttpServletRequest request)
	{
	
		
		HttpSession hs=request.getSession();
		if(hs==null || hs.getAttribute("id")==null)
			return "<$expire$>";
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String fdr=request.getParameter("fdr");
		
		GetWebmailUnreadMailCountResponse wfre=webmailClient.getWebmailUnreadMailCountRequest(host, port, id, pass,fdr);
		int mcnt  =wfre.getUnreademailcount();
		
		return ""+mcnt;
		
	}
	
}
