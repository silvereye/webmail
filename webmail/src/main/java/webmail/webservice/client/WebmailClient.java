package webmail.webservice.client;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.local.DisplayMailContent;
import com.local.InboxList;
import com.local.WebmailAttachment;
import com.local.WebmailCompose;
import com.local.WebmailSearch;

import webmail.wsdl.CreateContactRequest;
import webmail.wsdl.CreateContactResponse;
import webmail.wsdl.DeleteSelectedTaskRequest;
import webmail.wsdl.DeleteSelectedTaskResponse;
import webmail.wsdl.DeleteTaskRequest;
import webmail.wsdl.DeleteTaskResponse;
import webmail.wsdl.DisplayContactRequest;
import webmail.wsdl.GetAddTaskRequest;
import webmail.wsdl.GetAddTaskResponse;
import webmail.wsdl.GetComposeMailRequest;
import webmail.wsdl.GetComposeMailResponse;
import webmail.wsdl.GetContactDetailRequest;
import webmail.wsdl.GetContactDetailResponse;
import webmail.wsdl.GetFullContactDetailResponse;
import webmail.wsdl.GetInboxMailDescRequest;
import webmail.wsdl.GetInboxMailQuickSearchRequest;
import webmail.wsdl.GetInboxMailRequest;
import webmail.wsdl.GetInboxMailSearchRequest;
import webmail.wsdl.GetLdapFNameRequest;
import webmail.wsdl.GetLdapFNameResponse;
import webmail.wsdl.GetLdapModifyListAttRequest;
import webmail.wsdl.GetLdapModifyListAttResponse;
import webmail.wsdl.GetLdapModifyOneAttRequest;
import webmail.wsdl.GetLdapModifyOneAttResponse;
import webmail.wsdl.GetLdapOneAttOtrUserRequest;
import webmail.wsdl.GetLdapOneAttOtrUserResponse;
import webmail.wsdl.GetLdapUserDirectoryRequest;
import webmail.wsdl.GetLdapUserDirectoryResponse;
import webmail.wsdl.GetMailAttachDownloadRequest;
import webmail.wsdl.GetMailAttachDownloadResponse;
import webmail.wsdl.GetMailAttachmentRequest;
import webmail.wsdl.GetMailAttachmentResponse;
import webmail.wsdl.GetMailDisplayRequest;
import webmail.wsdl.GetMailDisplayResponse;
import webmail.wsdl.GetMailHeaderRequest;
import webmail.wsdl.GetMailHeaderResponse;
import webmail.wsdl.GetMailInboxDescResponse;
import webmail.wsdl.GetMailInboxQuickSearchResponse;
import webmail.wsdl.GetMailInboxResponse;
import webmail.wsdl.GetMailInboxSearchResponse;
import webmail.wsdl.GetSaveMailDraftRequest;
import webmail.wsdl.GetSaveMailDraftResponse;
import webmail.wsdl.GetTaskDetailRequest;
import webmail.wsdl.GetTaskDetailResponse;
import webmail.wsdl.GetTasksRequest;
import webmail.wsdl.GetTasksResponse;
import webmail.wsdl.GetUpdateTaskRequest;
import webmail.wsdl.GetUpdateTaskResponse;
import webmail.wsdl.GetVCFLdapDirRequest;
import webmail.wsdl.GetVCFLdapDirResponse;
import webmail.wsdl.GetWebmailAllMailCountRequest;
import webmail.wsdl.GetWebmailAllMailCountResponse;
import webmail.wsdl.GetWebmailAuthRequest;
import webmail.wsdl.GetWebmailAuthResponse;
import webmail.wsdl.GetWebmailCreateFolderRequest;
import webmail.wsdl.GetWebmailCreateFolderResponse;
import webmail.wsdl.GetWebmailDeleteDraftRequest;
import webmail.wsdl.GetWebmailDeleteDraftResponse;
import webmail.wsdl.GetWebmailDeleteFolderRequest;
import webmail.wsdl.GetWebmailDeleteFolderResponse;
import webmail.wsdl.GetWebmailFolderOtherRequest;
import webmail.wsdl.GetWebmailFolderOtherResponse;
import webmail.wsdl.GetWebmailFolderRequest;
import webmail.wsdl.GetWebmailFolderResponse;
import webmail.wsdl.GetWebmailFolderSubscribedOtherRequest;
import webmail.wsdl.GetWebmailFolderSubscribedOtherResponse;
import webmail.wsdl.GetWebmailFolderSubscribedRequest;
import webmail.wsdl.GetWebmailFolderSubscribedResponse;
import webmail.wsdl.GetWebmailImapquotaRequest;
import webmail.wsdl.GetWebmailImapquotaResponse;
import webmail.wsdl.GetWebmailMoveTrashRequest;
import webmail.wsdl.GetWebmailMoveTrashResponse;
import webmail.wsdl.GetWebmailQuotaRequest;
import webmail.wsdl.GetWebmailQuotaResponse;
import webmail.wsdl.GetWebmailSpamRequest;
import webmail.wsdl.GetWebmailSpamResponse;
import webmail.wsdl.GetWebmailSubFolderRequest;
import webmail.wsdl.GetWebmailSubFolderResponse;
import webmail.wsdl.GetWebmailSubFolderSubscribedOtherRequest;
import webmail.wsdl.GetWebmailSubFolderSubscribedOtherResponse;
import webmail.wsdl.GetWebmailSubFolderSubscribedRequest;
import webmail.wsdl.GetWebmailSubFolderSubscribedResponse;
import webmail.wsdl.GetWebmailSubscriptionFolderRequest;
import webmail.wsdl.GetWebmailSubscriptionFolderResponse;
import webmail.wsdl.GetWebmailUnreadMailCountRequest;
import webmail.wsdl.GetWebmailUnreadMailCountResponse;
import webmail.wsdl.RemoveWebmailFlagRequest;
import webmail.wsdl.RemoveWebmailFlagResponse;
import webmail.wsdl.SetWebmailFlagResponse;
import webmail.wsdl.SetWebmailFlageRequest;
import webmail.wsdl.SetWebmailSeenRequest;
import webmail.wsdl.SetWebmailSeenResponse;
import webmail.wsdl.SetWebmailUnSeenRequest;
import webmail.wsdl.SetWebmailUnSeenResponse;
import webmail.wsdl.TaskBean;

public class WebmailClient extends WebServiceGatewaySupport {
	
	
	
	public webmail.wsdlnew.GetMailAttachmentResponse getMailAttachmentRequest(String host, String port, String id, String pass, String uid, String foldername, String filePath)
	{
		/*GetMailAttachmentRequest request = new GetMailAttachmentRequest();
		request.setWebmailFolder(foldername);
		request.setWebmailHost(host);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		request.setWebmailPort(port);
		request.setWebmailUid(uid);
		
		GetMailAttachmentResponse response =(GetMailAttachmentResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/GetMailAttachmentRequest"));*/
		webmail.wsdlnew.GetMailAttachmentResponse response=new webmail.wsdlnew.GetMailAttachmentResponse();
		WebmailAttachment watt=new WebmailAttachment();
		response=watt.listWebmailAttachment(host, port, id, pass, uid, foldername, filePath);
		return response;
		
	}
	
	
	
	public GetWebmailFolderResponse getWebmailFolderRequest(String host, String id, String pass)
	{
		GetWebmailFolderRequest request = new GetWebmailFolderRequest();
		request.setWebamilHost(host);
		
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		
		GetWebmailFolderResponse response =(GetWebmailFolderResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/GetWebmailFolderRequest"));
		
		return response;
		
	}
	
	
	public GetWebmailFolderOtherResponse getWebmailFolderOtherRequest(String host, String id, String pass, String path)
	{
		GetWebmailFolderOtherRequest request = new GetWebmailFolderOtherRequest();
		request.setWebmailHost(host);
		request.setWebmailFolderPath(path);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		
		GetWebmailFolderOtherResponse response =(GetWebmailFolderOtherResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/GetWebmailFolderOtherRequest"));
		return response;
		
	}
	
	public GetWebmailSubFolderResponse getWebmailSubFolderRequest(String host, String id, String pass, String path)
	{
		GetWebmailSubFolderRequest request=new GetWebmailSubFolderRequest();
		request.setWebamilFolderPath(path);
		request.setWebamilHost(host);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		
		GetWebmailSubFolderResponse response=(GetWebmailSubFolderResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailSubFolderRequest"));
		
		return response;
		
	}
	
	
	
	public GetWebmailCreateFolderResponse getWebmailCreateFolder(String host, String id, String pass, String fldrnm)
	{
		GetWebmailCreateFolderRequest request=new GetWebmailCreateFolderRequest();
		request.setWebmailFolderName(fldrnm);
		request.setWebmailHost(host);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		
		GetWebmailCreateFolderResponse response=(GetWebmailCreateFolderResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailCreateFolderRequest"));
		
		return response;
		
	}
	
	
	
	public GetWebmailDeleteFolderResponse getWebmailDeleteFolder(String host, String id, String pass, String fldrnm)
	{
		GetWebmailDeleteFolderRequest request=new GetWebmailDeleteFolderRequest();
		request.setWebmailFolderName(fldrnm);
		request.setWebmailHost(host);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		
		GetWebmailDeleteFolderResponse response=(GetWebmailDeleteFolderResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailDeleteFolderRequest"));
		
		return response;
		
	}
	
	
	
	
	public GetWebmailSubscriptionFolderResponse getWebmailSubscriptionFolder(String host, String id, String pass, String fldrnm, boolean subst)
	{
		GetWebmailSubscriptionFolderRequest request=new GetWebmailSubscriptionFolderRequest();
		request.setWebmailFolderName(fldrnm);
		request.setWebmailHost(host);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		request.setWebmailFolderSubStatus(subst);
		
		
		GetWebmailSubscriptionFolderResponse response=(GetWebmailSubscriptionFolderResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailSubscriptionFolderRequest"));
		
		return response;
		
	}
	
	
	
	
	public GetWebmailFolderSubscribedOtherResponse getWebmailFolderSubscribedOtherRequest(String host, String id, String pass, String path)
	{
		GetWebmailFolderSubscribedOtherRequest request = new GetWebmailFolderSubscribedOtherRequest();
		request.setWebamilHost(host);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebmailFolderPath(path);
		
		GetWebmailFolderSubscribedOtherResponse response =(GetWebmailFolderSubscribedOtherResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/GetWebmailFolderSubscribedOtherRequest"));
		
		return response;
		
	}
	
	
	public GetWebmailFolderSubscribedResponse getWebmailFolderSubscribedRequest(String host, String id, String pass)
	{
		GetWebmailFolderSubscribedRequest request = new GetWebmailFolderSubscribedRequest();
		request.setWebamilHost(host);
		
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		
		GetWebmailFolderSubscribedResponse response =(GetWebmailFolderSubscribedResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/GetWebmailFolderSubscribedRequest"));
		
		return response;
		
	}
	
	
	public GetWebmailSubFolderSubscribedOtherResponse getWebmailSubFolderSubscribedOtherRequest(String host, String id, String pass, String path)
	{
		GetWebmailSubFolderSubscribedOtherRequest request=new GetWebmailSubFolderSubscribedOtherRequest();
		request.setWebamilFolderPath(path);
		request.setWebamilHost(host);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		
		GetWebmailSubFolderSubscribedOtherResponse response=(GetWebmailSubFolderSubscribedOtherResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailSubFolderSubscribedOtherRequest"));
		
		return response;
		
	}
	
	
	
	public GetWebmailSubFolderSubscribedResponse getWebmailSubFolderSubscribedRequest(String host, String id, String pass, String path)
	{
		GetWebmailSubFolderSubscribedRequest request=new GetWebmailSubFolderSubscribedRequest();
		request.setWebamilFolderPath(path);
		request.setWebamilHost(host);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		
		GetWebmailSubFolderSubscribedResponse response=(GetWebmailSubFolderSubscribedResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailSubFolderSubscribedRequest"));
		
		return response;
		
	}

	
	
	
	public GetWebmailAuthResponse getWebmailAuthRequest(String host, String port, String id, String pass)
	{
		GetWebmailAuthRequest request = new GetWebmailAuthRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		
		GetWebmailAuthResponse response =(GetWebmailAuthResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailAuthRequest"));
		
		return response;
		
	}

	
	public GetWebmailImapquotaResponse getWebmailImapquotaRequest(String host, String id, String pass)
	{
		GetWebmailImapquotaRequest request = new GetWebmailImapquotaRequest();
		request.setWebamilHost(host);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		
		GetWebmailImapquotaResponse response =(GetWebmailImapquotaResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailImapquotaRequest"));
		
		return response;
		
	}
	
	
	public GetWebmailUnreadMailCountResponse getWebmailUnreadMailCountRequest(String host,String port, String id, String pass, String folder)
	{
		GetWebmailUnreadMailCountRequest request = new GetWebmailUnreadMailCountRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilFolder(folder);
		GetWebmailUnreadMailCountResponse response =(GetWebmailUnreadMailCountResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailUnreadMailCountRequest"));
		
		return response;
		
	}
	
	
	public GetWebmailAllMailCountResponse getWebmailAllMailCountRequest(String host,String port, String id, String pass, String folder)
	{
		GetWebmailAllMailCountRequest request = new GetWebmailAllMailCountRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilFolder(folder);
		GetWebmailAllMailCountResponse response =(GetWebmailAllMailCountResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailAllMailCountRequest"));
		
		return response;
		
	}
	
	
	
	
	public webmail.wsdlnew.GetMailInboxResponse getInboxMailRequest(String host,String port, String id, String pass, String start, String end, String folder)
	{
		/*GetInboxMailRequest request = new GetInboxMailRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilStart(start);
		request.setWebamilEnd(end);
		request.setWebamilFolder(folder);
		GetMailInboxResponse response =(GetMailInboxResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetInboxMailRequest"));*/
		InboxList inl=new InboxList();
		webmail.wsdlnew.GetMailInboxResponse response =inl.listWebmailInbox( host,  port,  id,  pass,  start,  end,  folder);
		return response;
		
	}
	
	
	public webmail.wsdlnew.GetMailInboxResponse getInboxChangeMailRequest(String host,String port, String id, String pass, String uid,  String folder)
	{
		/*GetInboxMailRequest request = new GetInboxMailRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilStart(start);
		request.setWebamilEnd(end);
		request.setWebamilFolder(folder);
		GetMailInboxResponse response =(GetMailInboxResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetInboxMailRequest"));*/
		InboxList inl=new InboxList();
		webmail.wsdlnew.GetMailInboxResponse response =inl.listWebmailInboxUid( host,  port,  id,  pass,  uid,    folder);
		return response;
		
	}
	
	public webmail.wsdlnew.GetMailInboxSearchResponse getInboxMailSearchRequest(String host,String port, String id, String pass, String start, String end, String folder, String to, String from, String sub, String cntt, String dt1,  String dt2,String tagnm, String stnm)
	{
		/*GetInboxMailSearchRequest request = new GetInboxMailSearchRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilStart(start);
		request.setWebamilEnd(end);
		request.setWebamilFolder(folder);
		request.setSearchTo(to);
		request.setSearchFrom(from);
		request.setSearchSubject(sub);
		request.setSearchDate(dt);
		request.setSearchTag(tagnm);
		request.setSearchContent(cntt);
		request.setSearchStatus(stnm);
		GetMailInboxSearchResponse response =(GetMailInboxSearchResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetInboxMailSearchRequest"));*/
		WebmailSearch ws=new WebmailSearch();
		webmail.wsdlnew.GetMailInboxSearchResponse response=ws.listWebmailSearch(host, port, id, pass, start, end, folder, to, from, sub, cntt, dt1, dt2, tagnm, stnm);
		return response;
		
	}
	
	
	
	public webmail.wsdlnew.GetMailInboxQuickSearchResponse getInboxMailQuickSearchRequest(String host,String port, String id, String pass, String start, String end, String folder, String quickval)
	{
		/*GetInboxMailQuickSearchRequest request = new GetInboxMailQuickSearchRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilStart(start);
		request.setWebamilEnd(end);
		request.setWebamilFolder(folder);
		request.setQuickVal(quickval);
		
		GetMailInboxQuickSearchResponse response =(GetMailInboxQuickSearchResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetInboxMailQuickSearchRequest"));*/
		WebmailSearch ws=new WebmailSearch();
		webmail.wsdlnew.GetMailInboxQuickSearchResponse response=ws.listWebmailQuickSearch(host, port, id, pass, start, end, folder, quickval);
		return response;
		
	}
	
	
	
	public GetMailInboxDescResponse getInboxMailRequestDesc(String host,String port, String id, String pass, String start, String end, String folder)
	{
		GetInboxMailDescRequest request = new GetInboxMailDescRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilStart(start);
		request.setWebamilEnd(end);
		request.setWebamilFolder(folder);
		GetMailInboxDescResponse response =(GetMailInboxDescResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetInboxMailDescRequest"));
		
		return response;
		
	}
	
	
	
	public SetWebmailFlagResponse setFlagActionRequest(String host,String port, String id, String pass, String folder, String uids)
	{
		SetWebmailFlageRequest request = new SetWebmailFlageRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebmailFolder(folder);
		request.setWebamilUids(uids);
		
		SetWebmailFlagResponse response =(SetWebmailFlagResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/SetWebmailFlageRequest"));
		
		return response;
		
	}
	
	
	public RemoveWebmailFlagResponse removeFlagActionRequest(String host,String port, String id, String pass, String folder, String uids)
	{
		RemoveWebmailFlagRequest request = new RemoveWebmailFlagRequest();
		request.setWebmailHost(host);
		request.setWebmailPost(port);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		request.setWebmailFolder(folder);
		request.setWebmailUids(uids);
		
		RemoveWebmailFlagResponse response =(RemoveWebmailFlagResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/RemoveWebmailFlagRequest"));
		
		return response;
		
	}
	
	
	
	public SetWebmailSeenResponse setSeenActionRequest(String host,String port, String id, String pass, String folder, String uids)
	{
		SetWebmailSeenRequest request = new SetWebmailSeenRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebmailFolder(folder);
		request.setWebamilUids(uids);
		
		SetWebmailSeenResponse response =(SetWebmailSeenResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/SetWebmailSeenRequest"));
		
		return response;
		
	}
	
	
	
	public SetWebmailUnSeenResponse setUnSeenActionRequest(String host,String port, String id, String pass, String folder, String uids)
	{
		SetWebmailUnSeenRequest request = new SetWebmailUnSeenRequest();
		request.setWebmailHost(host);
		request.setWebmailPort(port);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		request.setWebmailFolder(folder);
		request.setWebmailUids(uids);
		
		SetWebmailUnSeenResponse response =(SetWebmailUnSeenResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/SetWebmailUnSeenRequest"));
		
		return response;
		
	}
	
	
	
	
	
	
	public GetWebmailMoveTrashResponse  moveToTrashRequest(String host,String port, String id, String pass, String folder, String uids)
	{
		GetWebmailMoveTrashRequest request = new GetWebmailMoveTrashRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilFolder(folder);
		request.setWebamilUids(uids);
		
		GetWebmailMoveTrashResponse response =(GetWebmailMoveTrashResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailMoveTrashRequest"));
		
		return response;
		
	}
	
	
	public GetWebmailSpamResponse  webmailSpam(String host,String port, String id, String pass, String folder, String folder_dest, String uids)
	{
		GetWebmailSpamRequest request = new GetWebmailSpamRequest();
		request.setWebmailHost(host);
		request.setWebmailPort(port);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		request.setWebmailFolder(folder);
		request.setWebmailFolderDestination(folder_dest);
		request.setWebmailUids(uids);
		
		GetWebmailSpamResponse response =(GetWebmailSpamResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailSpamRequest"));
		
		return response;
		
	}
	
	
	
	/*public GetWebmailMoveJunkResponse  moveToJunkRequest(String host,String port, String id, String pass, String folder,String folder_desti, String uids)
	{
		GetWebmailMoveJunkRequest request = new GetWebmailMoveJunkRequest();
		request.setWebmailHost(host);
		request.setWebmailPort(port);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		request.setWebmailFolder(folder);
		request.setWebmailUids(uids);
		request.setWebmailFolderDestination(folder_desti);
		
		GetWebmailMoveJunkResponse response =(GetWebmailMoveJunkResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailMoveJunkRequest"));
		
		return response;
		
	}*/
	
	
	
	public webmail.wsdlnew.GetMailDisplayResponse  displayMailContentRequest(String host,String port, String id, String pass,String uid, String folder, String filePath, String uidstatus)
	{
		/*GetMailDisplayRequest request = new GetMailDisplayRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilFolder(folder);
		request.setWebamilUid(uid);
		
		GetMailDisplayResponse response =(GetMailDisplayResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetMailDisplayRequest"));*/
		DisplayMailContent dmc=new DisplayMailContent();
		webmail.wsdlnew.GetMailDisplayResponse  response =dmc.listWebmailContent( host,  port,  id,  pass,  uid,  folder, filePath, uidstatus);
		return response;
		
	}
	
	
	
	public GetMailHeaderResponse  getMailHeaderRequest(String host,String port, String id, String pass,String uid, String folder)
	{
		GetMailHeaderRequest request = new GetMailHeaderRequest();
		request.setWebmailHost(host);
		request.setWebmailPort(port);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		request.setWebmailFolder(folder);
		request.setWebmailUid(uid);
		
		GetMailHeaderResponse response =(GetMailHeaderResponse) getWebServiceTemplate().marshalSendAndReceive(
				request, 
				new SoapActionCallback("http://localhost:11235/ws/GetMailHeaderRequest"));
		
		return response;
		
	}
	
	
	
	
	public GetMailAttachDownloadResponse  downloadMailAttachRequest(String host,String port, String id, String pass,String uid, String folder, String name)
	{
		GetMailAttachDownloadRequest request = new GetMailAttachDownloadRequest();
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilFolder(folder);
		request.setWebamilUid(uid);
		request.setWebamilFileName(name);
		//request.setWebamilResponse(resp);
		
		GetMailAttachDownloadResponse response =(GetMailAttachDownloadResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetMailDisplayRequest"));
		
		return response;
		
	}
	
	
	public webmail.wsdlnew.GetComposeMailResponse  comoseMailRequest(String mailReplyTo,String oldmsgid, String ref,String ip, String xmailer, String draftuid, String host, String port,String imapport, boolean savesent, String id, String pass,String fromname, String to, String cc, String bcc, String sub ,String cnt, boolean isattach, List<String> flname, List<String> newflname, String readrec, boolean dsn_status, String pri, String texttype, String filePath)
	{
		/*GetComposeMailRequest request = new GetComposeMailRequest();
		
		request.setWebmailXMailer(xmailer);
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilIp(ip);
		request.setWebamilIMAPPort(imapport);
		request.setWebamilSaveSent(savesent);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilFromName(fromname);
		request.setWebamilTo(to);
		request.setWebamilCc(cc);
		request.setWebamilBcc(bcc);
		request.setWebamilSubject(sub);
		request.setWebamilBodyContent(cnt);
		request.setWebamilAttach(isattach);
		request.getAttachmentFileName().addAll(flname);
		request.getAttachmentFileNewName().addAll(newflname);
		request.setWebamilPriority(pri);
		request.setWebamilReadRec(readrec);
		request.setWebmailDraftUid(draftuid);
		request.setWebmailTextType(texttype);
		request.setWebmailOldMessageID(oldmsgid);
		request.setWebmailReferences(ref);
		//request.setWebamilResponse(resp);
		
		GetComposeMailResponse response =(GetComposeMailResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetComposeMailRequest"));*/
		WebmailCompose wcom=new WebmailCompose();
		webmail.wsdlnew.GetComposeMailResponse response=wcom.sendComposeMail(mailReplyTo,oldmsgid, ref, ip, xmailer, draftuid, host, port, imapport, savesent, id, pass, fromname, to, cc, bcc, sub, cnt, isattach, flname, newflname, readrec,dsn_status, pri, texttype, filePath);
		return response;
		
	}
	
	
	
	public webmail.wsdlnew.GetComposeMailResponse  comoseMailRequestAfter(String mailReplyTo,String oldmsgid, String ref,String ip, String xmailer, String draftuid, String host, String port,String imapport, boolean savesent, String id, String pass,String fromname, String to, String cc, String bcc, String sub ,String cnt, boolean isattach, List<String> flname, List<String> newflname, String readrec, boolean dsn_status, String pri, String texttype, String filePath)
	{
		/*GetComposeMailRequest request = new GetComposeMailRequest();
		
		request.setWebmailXMailer(xmailer);
		request.setWebamilHost(host);
		request.setWebamilPort(port);
		request.setWebamilIp(ip);
		request.setWebamilIMAPPort(imapport);
		request.setWebamilSaveSent(savesent);
		request.setWebamilId(id);
		request.setWebamilPassword(pass);
		request.setWebamilFromName(fromname);
		request.setWebamilTo(to);
		request.setWebamilCc(cc);
		request.setWebamilBcc(bcc);
		request.setWebamilSubject(sub);
		request.setWebamilBodyContent(cnt);
		request.setWebamilAttach(isattach);
		request.getAttachmentFileName().addAll(flname);
		request.getAttachmentFileNewName().addAll(newflname);
		request.setWebamilPriority(pri);
		request.setWebamilReadRec(readrec);
		request.setWebmailDraftUid(draftuid);
		request.setWebmailTextType(texttype);
		request.setWebmailOldMessageID(oldmsgid);
		request.setWebmailReferences(ref);
		//request.setWebamilResponse(resp);
		
		GetComposeMailResponse response =(GetComposeMailResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetComposeMailRequest"));*/
		WebmailCompose wcom=new WebmailCompose();
		webmail.wsdlnew.GetComposeMailResponse response=wcom.sendComposeMailAfter(mailReplyTo,oldmsgid, ref, ip, xmailer, draftuid, host, port, imapport, savesent, id, pass, fromname, to, cc, bcc, sub, cnt, isattach, flname, newflname, readrec,dsn_status, pri, texttype, filePath);
		return response;
		
	}
	
	
	public webmail.wsdlnew.GetSaveMailDraftResponse  draftMailRequest(String ip, String xmailer, String host, String port,String imapport, String uid, String id, String pass,String fromname, String to, String cc, String bcc, String sub ,String cnt, boolean isattach, List<String> flname, List<String> newflname, String texttype, String filePath)
	{
		/*GetSaveMailDraftRequest request = new GetSaveMailDraftRequest();
		
		request.setWebmailXMailer(xmailer);
		request.setWebmailIp(ip);
		request.setWebamilHost(host); 
		request.setWebmailPort(port);
		request.setWebmailIMAPPort(imapport);
		request.setWebmailUid(uid);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		request.setWebmailFromName(fromname);
		request.setWebmailTo(to);
		request.setWebmailCc(cc);
		request.setWebmailBcc(bcc);
		request.setWebmailSubject(sub);
		request.setWebmailBodyContent(cnt);
		request.setWebmailAttach(isattach);
		request.getAttachmentFileName().addAll(flname);
		request.getAttachmentFileNewName().addAll(newflname);
		request.setWebmailTextType(texttype);
		//request.setWebamilResponse(resp);
		
		GetSaveMailDraftResponse response =(GetSaveMailDraftResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetSaveMailDraftRequest"));*/
		WebmailCompose wcom=new WebmailCompose();
		webmail.wsdlnew.GetSaveMailDraftResponse  response=wcom.saveMailInDraft(ip, xmailer, host, port, imapport, uid, id, pass, fromname, to, cc, bcc, sub, cnt, isattach, flname, newflname, texttype, filePath);
		return response;
		
	}
	
	
	public GetLdapOneAttOtrUserResponse getLdapOneAttOtrUser(String ldapurl, String uid, String pass, String base,  String searchbase, String attname)
	{
		GetLdapOneAttOtrUserRequest request = new GetLdapOneAttOtrUserRequest();
		request.setWebmailUrl(ldapurl);
		request.setWebmailId(uid);
		request.setWebmailPassword(pass);
		request.setWebmailBase(base);
		request.setWebmailSearchBase(searchbase);
		request.setWebmailAttName(attname);
		
		GetLdapOneAttOtrUserResponse response =(GetLdapOneAttOtrUserResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetLdapOneAttOtrUserRequest"));
		
		return response;
	}

	public GetLdapFNameResponse  getLdapFNmae(String ldapurl, String uid, String pass, String base, String attname)
	{
		GetLdapFNameRequest request = new GetLdapFNameRequest();
		request.setWebamilUrl(ldapurl);
		request.setWebamilId(uid);
		request.setWebamilPassword(pass);
		request.setWebmailBase(base);
		request.setWebamilAttName(attname);
		
		GetLdapFNameResponse response =(GetLdapFNameResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetLdapFNameRequest"));
		
		return response;
		
	}
	
	
	public GetLdapModifyOneAttResponse  getLdapModifyOneAtt(String ldapurl, String uid, String pass, String base, String attname, String attval)
	{
		GetLdapModifyOneAttRequest request = new GetLdapModifyOneAttRequest();
		request.setWebmailUrl(ldapurl);
		request.setWebmailId(uid);
		request.setWebmailPassword(pass);
		request.setWebmailBase(base);
		request.setWebmailAttName(attname);
		request.setWebmailAttValue(attval);
		
		GetLdapModifyOneAttResponse response =(GetLdapModifyOneAttResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetLdapModifyOneAttRequest"));
		
		return response;
		
	}
	
	public GetLdapModifyListAttResponse  getLdapModifyListAtt(String ldapurl, String uid, String pass, String base, List <String>  attname, List <String> attval)
	{
		GetLdapModifyListAttRequest request = new GetLdapModifyListAttRequest();
		request.setWebmailUrl(ldapurl);
		request.setWebmailId(uid);
		request.setWebmailPassword(pass);
		request.setWebmailBase(base);
		request.getWebmailAttList().addAll(attname);
		request.getWebmailAttListValue().addAll(attval);
		
		GetLdapModifyListAttResponse response =(GetLdapModifyListAttResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetLdapModifyListAttRequest"));
		
		return response;
		
	}
	
	
	public GetVCFLdapDirResponse  getLdapDirectory(String ldapurl, String uid, String pass, String base, String searchgBase)
	{
		GetVCFLdapDirRequest request = new GetVCFLdapDirRequest();
		request.setWebmailDirUrl(ldapurl);
		request.setWebmailDirId(uid);
		request.setWebmailDirPassword(pass);
		request.setWebmailDirBase(base);
		request.setWebmailSearchBase(searchgBase);
		GetVCFLdapDirResponse response =(GetVCFLdapDirResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetVCFLdapDirRequest"));
		
		return response;
		
	}
	
	
	public GetLdapUserDirectoryResponse  getLdapDirUsers(String ldapurl, String uid, String pass, String base)
	{
		GetLdapUserDirectoryRequest request = new GetLdapUserDirectoryRequest();
		request.setWebmailUrl(ldapurl);
		request.setWebmailId(uid);
		request.setWebmailPassword(pass);
		request.setWebmailBase(base);
		
		
		GetLdapUserDirectoryResponse response =(GetLdapUserDirectoryResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
		new SoapActionCallback("http://localhost:11235/ws/GetLdapUserDirectoryRequest"));
		
		return response;
		
	}
	
	
	
	public GetContactDetailResponse  getLdapContactFullDetail(String ldapurl, String uid, String pass, String base, String searchid)
	{
		GetContactDetailRequest request = new GetContactDetailRequest();
		request.setWebmailDetailUrl(ldapurl);
		request.setWebmailDetailId(uid);
		request.setWebmailDetailPassword(pass);
		request.setWebmailDetailBase(base);
		request.setWebmailDetailSearchId(searchid);
		
		GetContactDetailResponse response =(GetContactDetailResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
		new SoapActionCallback("http://localhost:11235/ws/GetContactDetailRequest"));
		
		return response;
		
	}
	
	
	
	public CreateContactResponse  getVCFIOStream(String vcffilename,String note,String fullname,String company,String job,String email,String web_page,String phone_work,String phone_home,String phone_fax,String phone_mob,String addr_work,String addr_home,String pre,String fnm,String mnm,String lnm,String suf,    List< Byte> con_img)
	{
		CreateContactRequest request = new CreateContactRequest();
		request.setWebamilAddrHome(addr_home);
		request.setWebamilCompany(company);
		request.setWebamilEmail(email);
		request.setWebamilFNM(fnm);
		request.setWebamilFullName(fullname);
		request.setWebamilJob(job);
		request.setWebamilLNM(lnm);
		request.setWebamilPhoneFax(phone_fax);
		request.setWebamilPhoneHome(phone_home);
		request.setWebamilPhoneMob(phone_mob);
		request.setWebamilPhoneWork(phone_work);
		request.setWebamilPre(pre);
		request.setWebamilSuf(suf);
		request.setWebamilVCFFileName(vcffilename);
		request.setWebamilWebPage(web_page);
		request.setWebmailAddrWork(addr_work);
		request.setWebmailMNM(mnm);
		request.setWebamilNote(note);
		request.getWebamilPhoto().addAll(con_img);
		CreateContactResponse response =(CreateContactResponse) getWebServiceTemplate().marshalSendAndReceive(
		request,
				new SoapActionCallback("http://localhost:11235/ws/CreateContactRequest"));
		
		return response;
		
	}
	
	
	public GetFullContactDetailResponse getFullDetailContact(String vcfiostrm, String flname)
	{
		DisplayContactRequest request= new DisplayContactRequest();
		request.setGetVFCFileName(flname);
		request.setGetVFCIOStream(vcfiostrm);
		
		GetFullContactDetailResponse response =(GetFullContactDetailResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/DisplayContactRequest"));
		
		return response;
	}
	
	
	public webmail.wsdlnew.GetWebmailDeleteDraftResponse deleteDraftRequest(String host,String port, String id, String pass,  String uids)
	{/*
		GetWebmailDeleteDraftRequest request = new GetWebmailDeleteDraftRequest();
		request.setWebmailHost(host);
		request.setWebmailPort(port);
		request.setWebmailId(id);
		request.setWebmailPassword(pass);
		request.setWebmailUids(uids);
		
		GetWebmailDeleteDraftResponse response =(GetWebmailDeleteDraftResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetWebmailDeleteDraftRequest"));*/
		WebmailCompose wcom=new WebmailCompose();
		webmail.wsdlnew.GetWebmailDeleteDraftResponse response=wcom.deleteDraft(host, port, id, pass, uids);
		return response;
		
	}
	

	public GetAddTaskResponse  addTaskRequest(TaskBean taskBean,byte[] filecontent)
	{
		System.out.println("in side webmail clain");
		GetAddTaskRequest request = new GetAddTaskRequest();
		request.setEventbean(taskBean);
		request.setFilecontent(filecontent);
		GetAddTaskResponse response =(GetAddTaskResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/GetAddTaskRequest"));
		return response;
		
	}
	
	public GetTasksResponse  buildTask(byte[] calendarfilecontent, String calendarfilename)
	{
		System.out.println("in side webmail clain");
		GetTasksRequest request = new GetTasksRequest();
		request.setTaskFileName(calendarfilename);
		request.setCalendarFileContent(calendarfilecontent);
		GetTasksResponse response =(GetTasksResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetTasksRequest"));
		return response;
		
	}
	
	public DeleteTaskResponse  deleteTask(byte[] filecontent, String uid)
	{
		System.out.println("in side webmail clain");
		DeleteTaskRequest request = new DeleteTaskRequest();
		request.setUid(uid);
		request.setCalendarFileContent(filecontent);
		DeleteTaskResponse response =(DeleteTaskResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/DeleteTaskRequest"));
		return response;
		
	}
	public DeleteSelectedTaskResponse  deleteSelectedTask(byte[] filecontent, String uid)
	{
		System.out.println("in side webmail clain");
		DeleteSelectedTaskRequest request = new DeleteSelectedTaskRequest();
		request.setUid(uid);
		request.setCalendarFileContent(filecontent);
		DeleteSelectedTaskResponse response =(DeleteSelectedTaskResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/DeleteSelectedTaskRequest"));
		return response;
		
	}
	public GetTaskDetailResponse  getTaskDetail(byte[] filecontent, String uid)
	{
		System.out.println("in side webmail clain");
		GetTaskDetailRequest request = new GetTaskDetailRequest();
		request.setUid(uid);
		request.setCalendarFileContent(filecontent);
		GetTaskDetailResponse response =(GetTaskDetailResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetTaskDetailRequest"));
		return response;
		
	}
	public GetUpdateTaskResponse  getUpdateTask(TaskBean taskBean, byte[] filecontent)
	{
		System.out.println("in side webmail clain");
		GetUpdateTaskRequest request = new GetUpdateTaskRequest();
		request.setEventbean(taskBean);
		request.setFilecontent(filecontent);
		GetUpdateTaskResponse response =(GetUpdateTaskResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:11235/ws/GetUpdateTaskRequest"));
		return response;
		
	}
	

	
}
