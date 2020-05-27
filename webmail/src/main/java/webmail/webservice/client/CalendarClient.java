package webmail.webservice.client;

import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.local.WebmailCompose;

import webmail.wsdl.CalendarBean;
import webmail.wsdl.ChangeColorRequest;
import webmail.wsdl.ChangeColorResponse;
import webmail.wsdl.CreateCalendarRequest;
import webmail.wsdl.CreateCalendarResponse;
import webmail.wsdl.CreateEventRequest;
import webmail.wsdl.CreateEventResponse;
import webmail.wsdl.EditFileRequest;
import webmail.wsdl.EditFileResponse;
import webmail.wsdl.EventBean;
import webmail.wsdl.GetCalendarDetailRequest;
import webmail.wsdl.GetCalendarDetailResponse;
import webmail.wsdl.GetCalendarMailRequest;
import webmail.wsdl.GetCalendarMailResponse;
import webmail.wsdl.GetComposeMailRequest;
import webmail.wsdl.GetComposeMailResponse;
import webmail.wsdl.GetDeleteEventRequest;
import webmail.wsdl.GetDeleteEventResponse;
import webmail.wsdl.GetEventsRequest;
import webmail.wsdl.GetEventsResponse;
import webmail.wsdl.GetFilterEventsRequest;
import webmail.wsdl.GetFilterEventsResponse;
import webmail.wsdl.GetImportCalendarRequest;
import webmail.wsdl.GetImportCalendarResponse;
import webmail.wsdl.GetInboxMailDescRequest;
import webmail.wsdl.GetInboxMailRequest;
import webmail.wsdl.GetInviteEventRequest;
import webmail.wsdl.GetInviteEventResponse;
import webmail.wsdl.GetLdapFNameRequest;
import webmail.wsdl.GetLdapFNameResponse;
import webmail.wsdl.GetMailAttachDownloadRequest;
import webmail.wsdl.GetMailAttachDownloadResponse;
import webmail.wsdl.GetMailDisplayRequest;
import webmail.wsdl.GetMailDisplayResponse;
import webmail.wsdl.GetMailInboxDescResponse;
import webmail.wsdl.GetMailInboxResponse;
import webmail.wsdl.GetUpdateCalendarRequest;
import webmail.wsdl.GetUpdateCalendarResponse;
import webmail.wsdl.GetWebmailAllMailCountRequest;
import webmail.wsdl.GetWebmailAllMailCountResponse;
import webmail.wsdl.GetWebmailAuthRequest;
import webmail.wsdl.GetWebmailAuthResponse;
import webmail.wsdl.GetWebmailFolderRequest;
import webmail.wsdl.GetWebmailFolderResponse;
import webmail.wsdl.GetWebmailImapquotaRequest;
import webmail.wsdl.GetWebmailImapquotaResponse;
import webmail.wsdl.GetWebmailMoveTrashRequest;
import webmail.wsdl.GetWebmailMoveTrashResponse;
import webmail.wsdl.GetWebmailQuotaRequest;
import webmail.wsdl.GetWebmailQuotaResponse;
import webmail.wsdl.GetWebmailSubFolderRequest;
import webmail.wsdl.GetWebmailSubFolderResponse;
import webmail.wsdl.GetWebmailUnreadMailCountRequest;
import webmail.wsdl.GetWebmailUnreadMailCountResponse;
import webmail.wsdl.GeteventdetailsRequest;
import webmail.wsdl.GeteventdetailsResponse;
import webmail.wsdl.ImportCalendarRequest;
import webmail.wsdl.RemoveWebmailFlagRequest;
import webmail.wsdl.RemoveWebmailFlagResponse;
import webmail.wsdl.SetWebmailFlagResponse;
import webmail.wsdl.SetWebmailFlageRequest;
import webmail.wsdl.SetWebmailSeenRequest;
import webmail.wsdl.SetWebmailSeenResponse;
import webmail.wsdl.SetWebmailUnSeenRequest;
import webmail.wsdl.SetWebmailUnSeenResponse;

public class CalendarClient extends WebServiceGatewaySupport {
	
	
	public GetCalendarMailResponse sendCalendarMailRequest(String webamilIp, String webamilId, String webamilPassword, String webamilFromName, String webamilHost, String webamilPort, String webamilTo, String webamilSubject, String webamilBodyContent,String calendarContent, String webmailXMailer)
	{
		/*GetCalendarMailRequest request = new GetCalendarMailRequest();
		request.setWebamilIp(webamilIp);
		request.setWebamilId(webamilId);
		request.setWebamilPassword(webamilPassword);
		request.setWebamilFromName(webamilFromName);
		request.setWebamilHost(webamilHost);
		request.setWebamilPort(webamilPort);
		request.setWebamilTo(webamilTo);
		request.setWebamilSubject(webamilSubject);
		request.setWebamilBodyContent(webamilBodyContent);
		request.setWebmailXMailer(webmailXMailer);
		request.setWebamilCalendarContent(calendarContent);
		
		GetCalendarMailResponse response =(GetCalendarMailResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/getCalendarMailRequest"));*/
		
		WebmailCompose wcs=new WebmailCompose();
		GetCalendarMailResponse response=wcs.sendCalendarMail(webamilIp, webamilId, webamilPassword, webamilFromName, webamilHost, webamilPort, webamilTo, webamilSubject, webamilBodyContent, calendarContent, webmailXMailer);
		return response;
		
	}
	
	public CreateCalendarResponse createCalendar(String calid, String calcolor, String calMethod)
	{
		CreateCalendarRequest request = new CreateCalendarRequest();
		request.setCalID(calid);
		request.setCalColor(calcolor);
		request.setCalMethod(calMethod);
		CreateCalendarResponse response =(CreateCalendarResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/createCalendarRequest"));
		
		return response;
		
	}

	public CreateEventResponse createEvent(EventBean eventbean,byte[] filecontent, String orgname, String orgemail) 
	{
		CreateEventRequest request = new CreateEventRequest();
		request.setFilecontent(filecontent);
		request.setEventbean(eventbean);
		request.setOrgname(orgname);
		request.setOrgemail(orgemail);
		CreateEventResponse response =(CreateEventResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/createEventRequest"));
		return response;
	}

	public GetEventsResponse getallevents(byte[] calendarfilecontent , String calendarfilename) 
	{
		GetEventsRequest request = new GetEventsRequest();
		request.setCalendarFileContent(calendarfilecontent);
		request.setCalendarFileName(calendarfilename);
		GetEventsResponse response =(GetEventsResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/getEventsRequest"));
		return response;
	}
	
	public GetInviteEventResponse getInviteEventRequest(byte[] filecontent , byte[] defaultCalendar) 
	{
		GetInviteEventRequest request = new GetInviteEventRequest();
		request.setFilecontent(filecontent);
		request.setDefaultCalendar(defaultCalendar);
		GetInviteEventResponse response =(GetInviteEventResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/getInviteEventRequest"));
		return response;
	}
	
	
	public GetImportCalendarResponse getImportCalendar(byte[] newcal , byte[] existcal) 
	{
		GetImportCalendarRequest request = new GetImportCalendarRequest();
		request.setNewCalendar(newcal);
		request.setExistingCalendar(existcal);
		GetImportCalendarResponse response =(GetImportCalendarResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/getImportCalendarRequest"));
		return response;
	}
	
	
	
	public GetFilterEventsResponse getFilterEvents(byte[] calendarfilecontent , String calendarfilename,XMLGregorianCalendar filterDate) 
	{
		GetFilterEventsRequest request = new GetFilterEventsRequest();
		request.setCalendarFileContent(calendarfilecontent);
		request.setCalendarFileName(calendarfilename);
		request.setFilterDate(filterDate);
		GetFilterEventsResponse response =(GetFilterEventsResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/getFilterEventsRequest"));
		return response;
	}
	
	public GeteventdetailsResponse geteventdetails(String uid,String calendarname,byte[] filecontent) 
	{

		GeteventdetailsRequest request=new GeteventdetailsRequest();
		request.setFilecontent(filecontent);
		EventBean eventbean=new EventBean();
		eventbean.setCalendar(calendarname);
		eventbean.setUid(uid);
		request.setEventbean(eventbean);
		GeteventdetailsResponse response =(GeteventdetailsResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/geteventdetailsRequest"));
		return response;
	}
	public ChangeColorResponse changecalendarcolor(String changedcolor,byte[] filecontent) 
	{

		ChangeColorRequest request=new ChangeColorRequest();
		request.setFilecontent(filecontent);
		request.setChangedcolor(changedcolor);
		ChangeColorResponse response =(ChangeColorResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/changeColorRequest"));
		return response;
	}
	
	public GetDeleteEventResponse deleteEvent(String uid,byte[] filecontent) 
	{

		GetDeleteEventRequest request=new GetDeleteEventRequest();
		request.setFilecontent(filecontent);
		request.setUid(uid);
		GetDeleteEventResponse response =(GetDeleteEventResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/GetDeleteEventRequest"));
		return response;
	}
	public GetCalendarDetailResponse getCalendarDetail(String calname,byte[] filecontent) 
	{

		GetCalendarDetailRequest request=new GetCalendarDetailRequest();
		request.setFilecontent(filecontent);
		request.setCalname(calname);
		GetCalendarDetailResponse response =(GetCalendarDetailResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/getCalendarDetailRequest"));
		return response;
	}
	public GetUpdateCalendarResponse getUpdateCalendarRequest(CalendarBean calBean,byte[] filecontent) 
	{

		GetUpdateCalendarRequest request=new GetUpdateCalendarRequest();
		request.setFilecontent(filecontent);
		request.setCalendarBean(calBean);
		GetUpdateCalendarResponse response =(GetUpdateCalendarResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/getUpdateCalendarRequest"));
		return response;
	}
	
	
	public CreateCalendarResponse importCalendarRequest(byte[] fromfilecontent, byte[] tofilecontent)
	{
		ImportCalendarRequest request = new ImportCalendarRequest();
		request.setFromfilecontent(fromfilecontent);
		request.setTofilecontent(tofilecontent);
		
		CreateCalendarResponse response =(CreateCalendarResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback(
						"http://localhost:11235/ws/importCalendarRequest"));
		
		return response;
		
	}

}
