package webmail.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;














import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.local.CMenuMethodsImpl;
import com.sieve.manage.examples.ConnectAndListScripts;

import webmail.bean.NPCompare;
import webmail.bean.NPFolderCompare;
import webmail.webservice.client.WebmailClient;
import webmail.wsdl.AssignSinglePermissionResponse;
import webmail.wsdl.GetWebmailFolderOtherResponse;
import webmail.wsdl.GetWebmailFolderResponse;
import webmail.wsdl.GetWebmailFolderSubscribedOtherResponse;
import webmail.wsdl.GetWebmailFolderSubscribedResponse;
import webmail.wsdl.GetWebmailImapquotaResponse;
import webmail.wsdl.GetWebmailQuotaResponse;
import webmail.wsdl.GetWebmailSubFolderResponse;
import webmail.wsdl.GetWebmailSubFolderSubscribedOtherResponse;
import webmail.wsdl.GetWebmailSubFolderSubscribedResponse;
import webmail.wsdl.GetWebmailUnreadMailCountResponse;
import webmail.wsdl.Imapquota;
import webmail.wsdl.MailImapFolders;
import webmail.wsdl.SubsImapFolders;
import webmail.wsdl.SubsImapSubFolders;


@Controller
public class FolderController {

	@Autowired
	private WebmailClient webmailClient;
		
@RequestMapping(value = "/getWbmailfolderNew", method = RequestMethod.GET)
	
	public String getWbmailfolderNew(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String inboxcnt="";
		GetWebmailUnreadMailCountResponse wfre=webmailClient.getWebmailUnreadMailCountRequest(host, port, id, pass,"inbox");
		int mcnt  =wfre.getUnreademailcount();
		if(mcnt > 0)
		{
			inboxcnt="("+mcnt+")";
		}
		
		//GetWebmailFolderSubscribedOtherResponse sfres=webmailClient.getWebmailFolderSubscribedOtherRequest(host, id, pass, "");
	 	//List<SubsImapFolders> sflst= sfres.getGetSubFolder().getSubsFolderListReturn().getSubsFolderList();
	 	
	 	GetWebmailFolderOtherResponse sfres=webmailClient.getWebmailFolderOtherRequest(host, id, pass, "");
	 	List<MailImapFolders> sflst= sfres.getGetSubFolder().getMailFolderListReturn().getMailFolderList();
	 	//Collections.sort(sflst,new NPFolderCompare());
	 	map.addAttribute("webmailClient", webmailClient);
		map.addAttribute("imapfldrlst", sflst);
		map.addAttribute("in_unread_cnt", inboxcnt);
		
		
		return "webmailFolder";
	}

/*

public List<SubsImapFolders> lFiles(List<SubsImapFolders> list, String path) {
	
	GetWebmailFolderSubscribedOtherResponse sfres=webmailClient.getWebmailFolderSubscribedOtherRequest(host, id, pass);
 	List<SubsImapFolders> sflst= sfres.getGetSubFolder().getSubsFolderListReturn().getSubsFolderList();
	
	         Folder[] f = store.getFolder(path).list();
	         for(Folder fd:f)
	  			{
	        	 list.add(fd.getFullName());
	        	 Folder t[]=fd.list();
	   			if(t.length>0)
	   			{
	   				
	   				lFiles(list, fd.getFullName());
	   			}
	   			else
	   			{
	   			 
	   			}
	   			
	        
	        	 
	  			}
	         
	         
	         
	       

	    
	       
	         

return list;
} */
	

@RequestMapping(value = "/setFolderEmptyCMenu", method = RequestMethod.GET)
@ResponseBody
public String setFolderEmptyCMenu(ModelMap map, Principal principal, HttpServletRequest request) {
	String res="";
	String fldr=request.getParameter("fldr");
	HttpSession hs=request.getSession();
	String host=(String)hs.getAttribute("host");
	String id=(String)hs.getAttribute("id");
	String pass=(String)hs.getAttribute("pass");
	String port=(String)hs.getAttribute("port");
	res=CMenuMethodsImpl.emptyWebmailFolder(host, port, id, pass, fldr);
	
	return res;
	
}
	
	@RequestMapping(value = "/getWbmailfolder", method = RequestMethod.GET)
	@ResponseBody
	public String listWebmailFolder(ModelMap map, Principal principal, HttpServletRequest request) {
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String inboxcnt="";
		GetWebmailUnreadMailCountResponse wfre=webmailClient.getWebmailUnreadMailCountRequest(host, port, id, pass,"inbox");
		int mcnt  =wfre.getUnreademailcount();
		if(mcnt > 0)
		{
			inboxcnt="("+mcnt+")";
		}
		
		GetWebmailFolderSubscribedResponse wfresponse=webmailClient.getWebmailFolderSubscribedRequest(host, id, pass);
		String res="<ul class='left_margin' style='display: block;'>";
		String myfdr=wfresponse.getGetWebmailFolderSubscribed();
		System.out.println("*************************^^^^^ webmail folder="+myfdr);
		String arr[]=myfdr.split(";");
		System.out.println("************************* webmail folder lenth="+arr.length);
		for(int i=0; i<arr.length; i++)
		{
			String prm="";
			if(arr[i].equalsIgnoreCase("Inbox"))
			{
				prm="inbox";
				res+="<li><a id='"+prm+"' style='cursor: pointer;' onclick='getWebmailInbox(this.id)' >Inbox &nbsp;<span id='unread_inbox'>"+inboxcnt+" </span></a></li>";
			}
			else if(arr[i].equalsIgnoreCase("Drafts") || arr[i].equalsIgnoreCase("Sent") || arr[i].equalsIgnoreCase("Junk") || arr[i].equalsIgnoreCase("Trash") || arr[i].equalsIgnoreCase("Archive")  )
			{
				prm=arr[i];
				res+="<li><a style='cursor: pointer;' id='"+prm+"' onclick='getWebmailInbox(this.id)'>"+arr[i]+"</a></li>";
			}
			else
			{
				prm=arr[i];
				int flg=Integer.parseInt(arr[i+1]);
				if(flg>0)
				{
					res+="<li class='bottom dcjq-parent-li'>";
					res+="<a style='cursor: pointer;'  id='"+prm+"'  onclick='getWebmailInbox(this.id)' class='dcjq-parent active' style='padding-left: 9px;'>"+arr[i]+"<span class='dcjq-icon'></span></a>";
					res+=" <ul style='display: block;' class='subfolder_onhover'>";
					GetWebmailSubFolderSubscribedResponse wsfr=webmailClient.getWebmailSubFolderSubscribedRequest(host, id, pass, arr[i]);
					String mysubfdr=wsfr.getGetWebmailSubFolderSubscribed();
					System.out.println("************************* webmail sub folder="+mysubfdr);
					String subarr[]=mysubfdr.split(";");
					String spath1=arr[i];
					for(int j=0;j < subarr.length;j++)
					{
						prm=arr[i]+"."+subarr[j];
						int flgsub=Integer.parseInt(subarr[j+1]);
						if(flgsub>0)
						{
							
							res+="<li class='dcjq-parent-li'><a style='cursor: pointer;' id='"+prm+"'  onclick='getWebmailInbox(this.id)' class='dcjq-parent active sub_folder_inner'>"+subarr[j]+"<span class='dcjq-icon'></span></a>";
							res+="<ul class='left_margin' style='display: block;'>";
							GetWebmailSubFolderSubscribedResponse wsfr1=webmailClient.getWebmailSubFolderSubscribedRequest(host, id, pass, spath1+"."+subarr[j]);
							String mysubfdr1=wsfr1.getGetWebmailSubFolderSubscribed();
							System.out.println("************************* webmail sub folder="+mysubfdr1);
							String subarr1[]=mysubfdr1.split(";");
							String spath2=subarr[j];
							for(int k=0;k < subarr1.length;k++)
							{
								prm=arr[i]+"."+subarr[j]+"."+subarr1[k];
								res+="<li><a style='cursor: pointer;' id='"+prm+"'  onclick='getWebmailInbox(this.id)'>"+subarr1[k]+"</a></li>";
								k++;
							}
							res+="</ul></li>";
						}
						else
						{
						res+="<li><a style='cursor: pointer;' id='"+prm+"'  onclick='getWebmailInbox(this.id)'>"+subarr[j]+"</a></li>";
						}
						j++;
					}
					res+="</ul></li>";
				}
				else
				{
					res+="<li><a style='cursor: pointer;' id='"+prm+"'  onclick='getWebmailInbox(this.id)'>"+arr[i]+"</a></li>";
				}
			}
			i++;
			
		}
		
		res+="</ul>";
		
		
		
		return res;
		
	}

	@RequestMapping(value = "/getWbmailsubfolder", method = RequestMethod.GET)
	@ResponseBody
	public String listWebmailQuota(ModelMap map, Principal principal,
			HttpServletRequest request, @RequestParam String path) {
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		GetWebmailUnreadMailCountResponse wfresponse=webmailClient.getWebmailUnreadMailCountRequest(host, port, id, pass,"inbox");
		int iqt  =wfresponse.getUnreademailcount();
		System.out.println("************************* unread="+iqt);
		return ""+iqt;
		
	}
	


}
