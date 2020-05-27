package webmail.bean;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.jackrabbit.webdav.DavServletResponse;
import org.apache.jackrabbit.webdav.client.methods.DavMethodBase;
import org.apache.jackrabbit.webdav.property.DavProperty;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertyNameSet;
import org.apache.jackrabbit.webdav.property.DavPropertySet;
import org.apache.jackrabbit.webdav.property.DefaultDavProperty;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor.ValueTransformer;
import org.springframework.security.crypto.codec.Base64;
import org.w3c.dom.Element;

import com.sun.star.report.Function;

import de.measite.minidns.record.A;
import net.fortuna.ical4j.connector.FailedOperationException;
import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.connector.dav.CalDavCalendarStore;
import net.fortuna.ical4j.connector.dav.CalDavConstants;
import net.fortuna.ical4j.connector.dav.DavClient;
import net.fortuna.ical4j.connector.dav.PathResolver;
import net.fortuna.ical4j.connector.dav.enums.ResourceType;
import net.fortuna.ical4j.connector.dav.method.PutMethod;
import net.fortuna.ical4j.connector.dav.property.CalDavPropertyName;
import net.fortuna.ical4j.connector.dav.property.ICalPropertyName;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.filter.Rule;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.Standard;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.parameter.*;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.Calendars;
import net.fortuna.ical4j.util.CompatibilityHints;
import webmail.wsdl.CreateEventResponse;
import webmail.wsdl.DateTimeList;
import webmail.wsdl.DateTimeTaskList;
import webmail.wsdl.EventArray;
import webmail.wsdl.EventBean;
import webmail.wsdl.GetAddTaskResponse;
import webmail.wsdl.GetFilterEventsResponse;
import webmail.wsdl.GetInviteEventResponse;
import webmail.wsdl.GetTaskDetailResponse;
import webmail.wsdl.GetUpdateTaskResponse;
import webmail.wsdl.TaskArray;
import webmail.wsdl.TaskBean;
public class CalDav  implements Serializable
{
		static URL url ;
		static
		{
			try 
			{
				url = new URL("http://caldav.silvereye.in:81");
			}
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		public DateTimeList calculaterepeatdate(VEvent tempevent, DtStart startdate, Dur dur)
		{
			DateTimeList dtl=new DateTimeList();
			VEvent component=new VEvent();
			PeriodList pl=null;
			try
			{
				String tm=startdate.getValue();
				if(!tm.contains("T"))
				{
					tm=tm+"T000000";
				}
				pl = tempevent.calculateRecurrenceSet(new Period(new DateTime(tm), dur));
				
			}
			catch (ParseException e1) 
			{
				e1.printStackTrace();
			}
			for (Iterator<Period> pit = pl.iterator(); pit.hasNext();)
			{
				Period period=(Period)pit.next();
				try 
				{
					dtl.getDateTime().add(period.getStart().toString()+"`"+period.getEnd().toString());
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		
			return dtl;
	   
		}
		
	
		
		
		private HttpClient getHttpClient(CalDavCalendarStore store) {

	        DavClient davClient = store.getClient();

	        Class<DavClient> cl = DavClient.class;
	        try {
	            Field fld = cl.getDeclaredField("httpClient");
	            fld.setAccessible(true);
	            return (HttpClient) fld.get(davClient);
	        } catch (SecurityException e) {
	            throw new RuntimeException(e);
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException(e);
	        } catch (NoSuchFieldException e) {
	            throw new RuntimeException(e);
	        } catch (IllegalAccessException e) {
	            throw new RuntimeException(e);
	        }
	    }
		
		
		public CalDavCalendarStore connect(String proid, String surl, String id ,String pass){
			CalDavCalendarStore store=null;
		//	id="vbede";
		//	pass="bedework";
		//	proid = "-//bedework.org//tzsvr//EN";
			try
			{
			URL url = new URL(surl);
		    store = new CalDavCalendarStore(proid, url, PathResolver.DAVICAL) ;
			store.connect(id, pass.toCharArray());
			//store.
			try {
			//	setUp();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			getHttpClient(store).getParams().setAuthenticationPreemptive(true);
			return store;
		}
		
		
		public List<EventBean>  showcalNoti(String proid, String surl, String id ,String pass)
		{
			List<EventBean> lstbn=new ArrayList<EventBean>();
			List<CalDavCalendarCollection> colls=null;
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			if(store!=null)
			{
				try {
					
					Period p = new Period(new DateTime(new Date()), new Dur(7, 0, 0,0));
					Rule[] rules = new Rule[1];
			        rules[0] = new PeriodRule(p);
			        Filter f = new Filter(rules, Filter.MATCH_ALL);
					
					colls = store.getCollections();
					for(CalDavCalendarCollection coll : colls)
					{
						if(coll.getDisplayName() != null && !coll.getDisplayName().contains("proxy"))
						{
							Calendar[] calarr=coll.getCalendars();
							for(Calendar cal: calarr)
							{
								for (Iterator<VEvent> it = f.filter(cal.getComponents(net.fortuna.ical4j.model.Component.VEVENT)).iterator(); it.hasNext();)
						        {
									
									VEvent componentevent = (VEvent)it.next();
									
			 
									
									boolean st=false;
									for( Iterator<VAlarm> iterator=componentevent.getAlarms().iterator();iterator.hasNext();)
							           {
										st=true;
										break;
							           }
									
									if(st)
									{
										EventBean event=new EventBean();
										event.setUid(componentevent.getUid().getValue());
										event.setSummary(componentevent.getSummary().getValue());
										event.setCalendar(coll.getPath());
										GregorianCalendar gregoriancalendar=new GregorianCalendar();
										 try 
										  {
											  if(componentevent.getStartDate().getDate().toString().indexOf("T",0)<0)
											  {	
												  event.setAllday("on");
												  String sdt=componentevent.getStartDate().getDate().toString();
												  sdt+="T000000+05:30";
												  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
												  java.util.Date dt=componentevent.getStartDate().getDate();
												  try {
													dt=dateFormat.parse(sdt);
												} catch (ParseException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											  	 gregoriancalendar.setTime(dt);
												 event.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
												 
												/* String sdt1=componentevent.getEndDate().getDate().toString();
												  sdt1+="T000000+05:30";
												  java.util.Date dt1=componentevent.getEndDate().getDate();
												  try {
													dt1=dateFormat.parse(sdt1);
												} catch (ParseException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												 gregoriancalendar.setTime(dt1);
												 event.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
											  */
											  }
											  else
											  {
												  event.setAllday("off");
												  gregoriancalendar.setTime(componentevent.getStartDate().getDate());
													 event.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
													// gregoriancalendar.setTime(componentevent.getEndDate().getDate());
													// event.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
											  }
											 
										  }
										  catch (DatatypeConfigurationException e1) {
												e1.printStackTrace();
										  }
										 
										 lstbn.add(event);
									}
						        }
							}
						}
					}
				} catch (ObjectStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ObjectNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			return lstbn;
		}
		
		public GetFilterEventsResponse buildFilterEvents(Calendar calendar, String calendarfilename,String cnm, String calcolor, XMLGregorianCalendar filterDate) 
		{
			
			GetFilterEventsResponse res=new GetFilterEventsResponse();
			EventArray eventlist = new EventArray();
			
			
			Period p = new Period(new DateTime(filterDate.toGregorianCalendar().getTime()), new Dur(50, 0, 0,0));
			Rule[] rules = new Rule[1];
	        rules[0] = new PeriodRule(p);
	        Filter f = new Filter(rules, Filter.MATCH_ALL);
	        List <EventBean> eblst=new ArrayList<EventBean>();
	        int idx=0;
			for (Iterator<VEvent> it = f.filter(calendar.getComponents(net.fortuna.ical4j.model.Component.VEVENT)).iterator(); it.hasNext();)
	        {
				EventBean eventbean = new EventBean();
				VEvent component = (VEvent) it.next();
				
				eventbean.setSummary(component.getSummary().getValue());
				//date type changed
				try{
				GregorianCalendar gregoriancalendar=new GregorianCalendar();
				gregoriancalendar.setTime(component.getStartDate().getDate());
				if(component.getStartDate().getDate().toString().indexOf("T",0)<0 )
				{
					eventbean.setAllday("on");
					eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					gregoriancalendar.setTime(component.getEndDate().getDate());
					gregoriancalendar.add(java.util.Calendar.DAY_OF_YEAR, -1);
					eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				}
				else
				{
					eventbean.setAllday("off");
				eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				gregoriancalendar.setTime(component.getEndDate().getDate());
				eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				}
				
				}
				catch(DatatypeConfigurationException e)
				{
					e.printStackTrace();
				}
				// date type changed end
				eventbean.setUid(component.getUid().getValue());
				eventbean.setCalendar(calendarfilename);
				try
				{
				if(component.getClassification()!=null)
				eventbean.setClazz(component.getClassification().getValue());
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				eventbean.setColor(calcolor);
				if(component.getProperty("RRULE")!=null)
				{
					RRule rrule=(RRule)component.getProperty("RRULE");
					Recur recur=rrule.getRecur();
					try {
						//Date startdate=new Date(component.getStartDate().getValue());
						
						//String until=recur.getUntil();
						
						/*if(recur.getCount()!=0 || recur.getUntil()==null)
						{*/
							
						eventbean.setRepeatStatus("Yes");
						Dur dur=null;
							if(recur.getCount()>0)
							{
								if(recur.getFrequency().equals("YEARLY"))
								{
									
									dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
								}
								else if(recur.getFrequency().equals("MONTHLY"))
								{
									dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
								}
								else
								{
									dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
								}
							 
							}
							else if(recur.getUntil() != null)
							{
								//until date increased by 1 
								Date udt= recur.getUntil();
								java.util.Calendar cStart = java.util.Calendar.getInstance();
							    cStart.setTime(udt);
							    cStart.add(java.util.Calendar.DATE, 1);
							    java.util.Date  dStart = new net.fortuna.ical4j.model.Date(cStart.getTime());
								dur=new Dur(component.getStartDate().getDate(),dStart);
							}
							else
							{
								Date dt  =new Date(component.getEndDate().getDate().getTime());
								dt.setYear(dt.getYear() +5);
								dur=new Dur(component.getStartDate().getDate(),dt);
							}
							//DateTime stdate=new DateTime(component.getStartDate().getValue());
							
							///////////method testing
							try
							{
							eventbean.setRepeatdatetimelist(calculaterepeatdate(component,component.getStartDate(), dur));
							}
							catch(Exception e){}
						
							/////////methid testing end here
							/*PeriodList pl=component.calculateRecurrenceSet(new Period(new DateTime(component.getStartDate().getValue()), dur));
							for (Iterator<Period> pit = pl.iterator(); pit.hasNext();)
							{
								Period period=(Period)pit.next();
								try {
									dtl.getDateTime().add(period.getStart().toString()+"`"+period.getEnd().toString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}*/
					/*eventbean.setRepeatdatetimelist(dtl);*/
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				
				if(component.getRecurrenceId()!=null)
				{
				String recid=component.getRecurrenceId().getValue();
				eventbean.setRecurrenceEventID(recid);
				int tmp=idx;
				for(idx--;idx>=0;idx--)
				{
					EventBean eb=eblst.get(idx);
					if(eb.getRepeatdatetimelist()==null)
					{
						continue;
					}
					else
					{
						DateTimeList dtl=eb.getRepeatdatetimelist();
						List<String> strlst=dtl.getDateTime();
						boolean sts=false;
						for(int ins=0;ins< strlst.size();ins++)
						{
							String strc=strlst.get(ins);
							if(strc.startsWith(recid))
							{
								strlst.remove(ins);
								sts=true;
								break;
							}
						}
						if(sts)
						{
							/*dtl.getDateTime().addAll(strlst);
							eb.setRepeatdatetimelist(dtl);
							eblst.add(idx, eb);*/
							break;
						}
					}
				}
				idx=tmp;
				
				}
				
				eblst.add(eventbean);
				idx++;
			}
			eventlist.getEventList().addAll(eblst);
			res.setEventList(eventlist);
			res.setCalendarname(cnm);
			res.setCalendarcolor(calcolor);
			return res ;


		}
		
		
		
		public CreateEventResponse createEvent(String proid, String surl, String id ,String pass,String calnm,EventBean eventbean,String orgname, String orgemail, String hid_uid)
		{
			Dur dur=null;
			boolean ust=false;
			boolean updt=false;
			String orgnm="";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
			CreateEventResponse response=new CreateEventResponse();
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			
			if(store!=null)
			{
				Calendar cal = new Calendar();
				
				cal.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
				cal.getProperties().add(Version.VERSION_2_0);
				cal.getProperties().add(CalScale.GREGORIAN);
				
				
				/*net.fortuna.ical4j.model.PropertyList p	=new net.fortuna.ical4j.model.PropertyList();
				DateTime tdm;
				try {
					tdm = new DateTime("20160614T131234Z");
					p.add(new DtStamp(tdm));
					p.add(new Created(tdm));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
				VEvent event = new VEvent();
				

				//---------create event----------
				if(hid_uid!=null && hid_uid.length()>0)
				{
					updt=true;
					event.getProperties().add(new Uid(hid_uid));
				}
				else
				{
				event.getProperties().add(new Uid(UUID.randomUUID().toString()));
				
				}
				eventbean.setUid(event.getUid().getValue());				
				event.getProperties().add(new Created(new DateTime()));
				if(updt)
				{
				
					event.getProperties().add(new LastModified(new DateTime()));
				
				}
				else
				{
					//event.getProperties().add(new Created(new DateTime()));
				}
				event.getProperties().add(new Summary(eventbean.getSummary()));
				
				
				DtStart dtstart=null;
				DtEnd dtend=null;
				//String c=eventbean.getAllday();	
				try
				{
					if(eventbean.getAllday()!=null && eventbean.getAllday().equals("off"))
					{
						dtstart=new DtStart(dateFormat.format(eventbean.getStarteventdate().toGregorianCalendar().getTime()));
						dtend=new DtEnd(dateFormat.format(eventbean.getEndeventdate().toGregorianCalendar().getTime()));
					}
					else
					{
						dtstart=new DtStart();
						dtend=new DtEnd();
						net.fortuna.ical4j.model.Date dt = new net.fortuna.ical4j.model.Date(eventbean.getStarteventdate().toGregorianCalendar().getTime());
						dt.setDate(dt.getDate()+1);
						net.fortuna.ical4j.model.Date dt1 = new net.fortuna.ical4j.model.Date(eventbean.getEndeventdate().toGregorianCalendar().getTime());
						dt1.setDate(dt1.getDate()+1);
						dtstart.setDate(dt);
						dtend.setDate(dt1);
					}
					
				}
				catch(ParseException e)
				{
					e.printStackTrace();
					response.setResponse(null);
					response.setUpdateStatus(false);
					return response;
				}
				event.getProperties().add(dtstart);
				event.getProperties().add(dtend);
				if(eventbean.getLocation()!=null && !eventbean.getLocation().equals(""))
				event.getProperties().add(new Location(eventbean.getLocation()));
				if(eventbean.getDescription()!=null && !eventbean.getDescription().equals(""))
				event.getProperties().add(new Description(eventbean.getDescription()));
				event.getProperties().add(new Sequence(1));
				event.getProperties().add(Status.VEVENT_CONFIRMED);
				try
				{
				event.getProperties().add(new Clazz(eventbean.getClazz()));
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
				
				if(eventbean.getFreebusy()!=null && eventbean.getFreebusy().equals("free"))
				{
					event.getProperties().add(Transp.TRANSPARENT);
				}
				else
				{
					event.getProperties().add(Transp.OPAQUE);
				}
				
				if(eventbean.getNewguest()==null)
					eventbean.setNewguest("");
				if(eventbean.getOldguest()==null)
					eventbean.setOldguest("");
				Attendee attendee = null;
				boolean orst=true;
				if(eventbean.getNewguest() != "" || eventbean.getOldguest() != "")
				{
					boolean ischk=true;
					String guests[] = (eventbean.getNewguest()+""+eventbean.getOldguest()).split(",");
					for(String guest : guests)
					{
						
						if(guest != null && guest.length() != 0)
						{
							String act="";
							String arrid1[]=guest.split("`");
		   					if(arrid1.length>1)
		   					{
		   						guest=arrid1[1];
		   						act=arrid1[0];
		   					}
		   					else
		   						guest=arrid1[0];
		   					
		   					/*if(guest.equals(id))
							{
								ischk=false;
							}*/
							attendee = new Attendee(URI.create("mailto:"+guest));
							attendee.getParameters().add(Role.REQ_PARTICIPANT);
							//attendee.getParameters().add(CuType.INDIVIDUAL);
							attendee.getParameters().add(Rsvp.TRUE);
							if(guest.equalsIgnoreCase(id) && eventbean.getOrganizerstatus()!=null && eventbean.getOrganizerstatus().length()>0)
							{
								if(eventbean.getOrganizerstatus().equalsIgnoreCase("ACCEPTED"))
									attendee.getParameters().add(PartStat.ACCEPTED);
								else if(eventbean.getOrganizerstatus().equalsIgnoreCase("TENTATIVE"))
									attendee.getParameters().add(PartStat.TENTATIVE);
								else if(eventbean.getOrganizerstatus().equalsIgnoreCase("DECLINED"))
									attendee.getParameters().add(PartStat.DECLINED);
								else
										attendee.getParameters().add(PartStat.NEEDS_ACTION);
								
								ischk=false;
							}
							else
							{
							if(act.equalsIgnoreCase("a"))
								attendee.getParameters().add(PartStat.ACCEPTED);
							else if(act.equalsIgnoreCase("t"))
								attendee.getParameters().add(PartStat.TENTATIVE);
							else if(act.equalsIgnoreCase("d"))
								attendee.getParameters().add(PartStat.DECLINED);
							else
									attendee.getParameters().add(PartStat.NEEDS_ACTION);
							}
							
							if(guest.equalsIgnoreCase(id))
							{
								ischk=false;
							}
							event.getProperties().add(attendee);
						}
					}
					
					if(ischk && eventbean.getOrganizerstatus()!=null && eventbean.getOrganizerstatus().length()>0)
					{
						attendee = new Attendee(URI.create("mailto:"+id));
						attendee.getParameters().add(Role.REQ_PARTICIPANT);
						attendee.getParameters().add(Rsvp.TRUE);
						if(eventbean.getOrganizerstatus().equalsIgnoreCase("ACCEPTED"))
							attendee.getParameters().add(PartStat.ACCEPTED);
						else if(eventbean.getOrganizerstatus().equalsIgnoreCase("TENTATIVE"))
							attendee.getParameters().add(PartStat.TENTATIVE);
						else if(eventbean.getOrganizerstatus().equalsIgnoreCase("DECLINED"))
							attendee.getParameters().add(PartStat.DECLINED);
						else
								attendee.getParameters().add(PartStat.NEEDS_ACTION);
						//attendee.getParameters().add(PartStat.ACCEPTED);
						event.getProperties().add(attendee);
						orst=false;
						if(eventbean.getOrganizer()!=null && eventbean.getOrganizer().length()>0)
						{
							String ornr=eventbean.getOrganizer();
							if(!ornr.startsWith("mailto:"))
							{
								ornr="mailto:"+ornr;
							}
							orgnm=ornr;
						Organizer organizer = new Organizer(URI.create(ornr));
						organizer.getParameters().add(Role.CHAIR);
						organizer.getParameters().add(Rsvp.TRUE);
						organizer.getParameters().add(PartStat.ACCEPTED);
						event.getProperties().add(organizer);
						}
						else
						{
							Organizer organizer = new Organizer(URI.create("mailto:"+orgemail));
							//organizer.getParameters().add(new Cn(orgname));
							organizer.getParameters().add(Role.CHAIR);
							organizer.getParameters().add(Rsvp.TRUE);
							organizer.getParameters().add(PartStat.ACCEPTED);
							event.getProperties().add(organizer);
							orgnm="mailto:"+orgemail;
						}
					}
				}
				
				if(eventbean.getOrganizer()!=null && eventbean.getOrganizer().length()>0 && orst)
				{
					String ornr=eventbean.getOrganizer();
					if(!ornr.startsWith("mailto:"))
					{
						ornr="mailto:"+ornr;
					}
					Organizer organizer = new Organizer(URI.create(ornr));
					//organizer.getParameters().add(new Cn(orgname));
					organizer.getParameters().add(Role.CHAIR);
					organizer.getParameters().add(Rsvp.TRUE);
					organizer.getParameters().add(PartStat.ACCEPTED);
					event.getProperties().add(organizer);
					orgnm=ornr;
				}

				if(eventbean.getFrequency()!=null && !eventbean.getFrequency().equalsIgnoreCase("no"))
				{
					Recur recur=null;
					if(eventbean.getCount()!=null)
					{
						recur=new Recur(eventbean.getFrequency(),eventbean.getCount());
						recur.setInterval(eventbean.getInterval());
						if(recur.getFrequency().equals("YEARLY"))
						{
							
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
						}
						else if(recur.getFrequency().equals("MONTHLY"))
						{
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
						}
						else
						{
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
						}
						
					}
					else if(eventbean.getUntil()!=null)
					{
						net.fortuna.ical4j.model.Date dt = new net.fortuna.ical4j.model.Date(eventbean.getUntil().toGregorianCalendar().getTime());
						dt.setDate(dt.getDate()+1);
						recur=new Recur(eventbean.getFrequency(),new net.fortuna.ical4j.model.Date(dt));
						dur=new Dur(event.getStartDate().getDate(),recur.getUntil());
					}
					else
					{
						recur = new Recur(eventbean.getFrequency(),null);
						if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
						{
							List<String> days=eventbean.getRepeatdatetimelist().getDateTime();
							for(String day:days)
								recur.getDayList().add(new WeekDay(day));
						}
						recur.setInterval(eventbean.getInterval());
						event.getProperties().add(new RRule(recur));
						
						Date dt  =new Date(event.getEndDate().getDate().getTime());
						dt.setYear(dt.getYear() +5);
						dur=new Dur(event.getStartDate().getDate(),dt);
						response.setRepeatdates(calculaterepeatdate(event,event.getStartDate(), dur));
					}
					

					
					
					if(eventbean.getCount()!=null || eventbean.getUntil()!=null)
					{
						// for( Iterator<String> i=eventbean.getRepeatdatetimelist().getDateTime().iterator();i.hasNext();)
						if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
						{
							List<String> days=eventbean.getRepeatdatetimelist().getDateTime();
							for(String day:days)
								recur.getDayList().add(new WeekDay(day));
						}
						recur.setInterval(eventbean.getInterval());
						
						event.getProperties().add(new RRule(recur));
						///////////method testing
						response.setRepeatdates(calculaterepeatdate(event,event.getStartDate(), dur));
					}
				
				}
				if(eventbean.getReminderdata()!=null && !eventbean.getReminderdata().equals(""))
				{
					String[] reminderarray = eventbean.getReminderdata().split(";");
					for (String reminderdata : reminderarray) 
					{
						String[] reminders = reminderdata.split("`");
						VAlarm alarm = new VAlarm();
						String triggerduration=null;
						if(reminders[2].equals("W") || reminders[2].equals("D"))
							triggerduration="-P"+reminders[1]+reminders[2];
						else if (reminders[2].equals("M") || reminders[2].equals("H"))
							triggerduration="-PT"+reminders[1]+reminders[2];
						if (reminders[0].equalsIgnoreCase("Pop-up")) {
							alarm.getProperties().add(Action.DISPLAY);	                  
						}
						else if (reminders[0].equalsIgnoreCase("Email"))
						{
							alarm.getProperties().add(Action.EMAIL);
							alarm.getProperties().add(new Summary("Alarm Notification"));
							try {
								alarm.getProperties().add(new Attendee("mailto:"+id));
							} catch (URISyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						alarm.getProperties().add(new Description("Event Reminder"));
						Dur duration = new Dur(triggerduration);
						alarm.getProperties().add(new Trigger(duration));
						
						event.getAlarms().add(alarm);
					}
				}
						//---------------finish create event------------
				cal.getComponents().add(event);
				try
				{
				CalDavCalendarCollection caldc= store.getCollection(calnm);
				
				if(updt)
				{
					//caldc.writeCalendarOnServer(cal, false);
					try
					{
					//caldc.removeCalendar(hid_uid);
					//caldc.addCalendar(cal);
						//caldc.updateCalendar(cal);
						String url = calnm+hid_uid+".ics";
						String bd=cal.toString();
						boolean stt=npEventUpdate(bd,url,id, pass);
						//writeCalendarOnServer(store,cal, false);
						if(!stt)
						{
							eventbean.setUid("-1");
						}
						response.setUpdateStatus(stt);
					}
					catch(Exception ee)
					{
						ee.printStackTrace();
						response.setUpdateStatus(false);
					}
				}
				else
				{
				caldc.addCalendar(cal);
				//	writeCalendarOnServer(store,cal, true);
				response.setUpdateStatus(true);
				}
				response.setEventcolor(caldc.getColor());
				response.setOrganizername(orgnm);
				//ust=true;
				//response.setUpdateStatus(ust);
				response.setEventuid(eventbean.getUid());
				
				}catch(Exception e)
				{
					e.printStackTrace();
					response.setUpdateStatus(false);
				}
				try{
					//store.disconnect();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			return response;
		}
		
	
		
		public CreateEventResponse justeditedEvent(String proid, String surl, String id ,String pass,String calnm,EventBean eventbean,String orgname, String orgemail, String hid_uid)
		{
			Dur dur=null;
			boolean ust=false;
			boolean updt=false;
			String orgnm="";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
			CreateEventResponse response=new CreateEventResponse();
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			
			if(store!=null)
			{
				Calendar cal = new Calendar();
				
				cal.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
				cal.getProperties().add(Version.VERSION_2_0);
				cal.getProperties().add(CalScale.GREGORIAN);
				
				
				try {
					Calendar caldr=store.getCollection(calnm).getCalendar(hid_uid);
					 for (Iterator<VEvent> it = caldr.getComponents("VEVENT").iterator(); it.hasNext();)
						{
							VEvent component = (VEvent) it.next();
							boolean stt=true;
							if(component.getRecurrenceId()!=null && eventbean.getRecurrenceEventID()!=null)
							{
								if(eventbean.getRecurrenceEventID().equals(component.getRecurrenceId().getValue()))
								{
									stt=false;
									VEvent event = new VEvent();
									

									//---------create event----------
									
										event.getProperties().add(new Uid(hid_uid));
									
									
									eventbean.setUid(event.getUid().getValue());				
									
									event.getProperties().add(new LastModified(new DateTime()));
									
									event.getProperties().add(new Summary(eventbean.getSummary()));
									
									try {
										SimpleDateFormat rdateFormat = new SimpleDateFormat("yyyyMMdd");
										Date rdt=rdateFormat.parse(eventbean.getRecurrenceEventID());
										net.fortuna.ical4j.model.Date rrdt = new net.fortuna.ical4j.model.Date(rdt);
										rrdt.setDate(rrdt.getDate()+1);
										event.getProperties().add(new RecurrenceId(rrdt));
									} catch (ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
										
									}
									DtStart dtstart=null;
									DtEnd dtend=null;
									//String c=eventbean.getAllday();	
									try
									{
										if(eventbean.getAllday()!=null && eventbean.getAllday().equals("off"))
										{
											dtstart=new DtStart(dateFormat.format(eventbean.getStarteventdate().toGregorianCalendar().getTime()));
											dtend=new DtEnd(dateFormat.format(eventbean.getEndeventdate().toGregorianCalendar().getTime()));
										}
										else
										{
											dtstart=new DtStart();
											dtend=new DtEnd();
											net.fortuna.ical4j.model.Date dt = new net.fortuna.ical4j.model.Date(eventbean.getStarteventdate().toGregorianCalendar().getTime());
											dt.setDate(dt.getDate()+1);
											net.fortuna.ical4j.model.Date dt1 = new net.fortuna.ical4j.model.Date(eventbean.getEndeventdate().toGregorianCalendar().getTime());
											dt1.setDate(dt1.getDate()+1);
											dtstart.setDate(dt);
											dtend.setDate(dt1);
										}
										
									}
									catch(ParseException e)
									{
										e.printStackTrace();
										response.setResponse(null);
										response.setUpdateStatus(false);
										return response;
									}
									event.getProperties().add(dtstart);
									event.getProperties().add(dtend);
									if(eventbean.getLocation()!=null && !eventbean.getLocation().equals(""))
									event.getProperties().add(new Location(eventbean.getLocation()));
									if(eventbean.getDescription()!=null && !eventbean.getDescription().equals(""))
									event.getProperties().add(new Description(eventbean.getDescription()));
									event.getProperties().add(new Sequence(1));
									event.getProperties().add(Status.VEVENT_CONFIRMED);
									try
									{
									event.getProperties().add(new Clazz(eventbean.getClazz()));
									}
									catch(Exception ee)
									{
										ee.printStackTrace();
									}
									
									if(eventbean.getFreebusy()!=null && eventbean.getFreebusy().equals("free"))
									{
										event.getProperties().add(Transp.TRANSPARENT);
									}
									else
									{
										event.getProperties().add(Transp.OPAQUE);
									}
									
									if(eventbean.getNewguest()==null)
										eventbean.setNewguest("");
									if(eventbean.getOldguest()==null)
										eventbean.setOldguest("");
									Attendee attendee = null;
									boolean orst=true;
									if(eventbean.getNewguest() != "" || eventbean.getOldguest() != "")
									{
										boolean ischk=true;
										String guests[] = (eventbean.getNewguest()+""+eventbean.getOldguest()).split(",");
										for(String guest : guests)
										{
											
											if(guest != null && guest.length() != 0)
											{
												String act="";
												String arrid1[]=guest.split("`");
							   					if(arrid1.length>1)
							   					{
							   						guest=arrid1[1];
							   						act=arrid1[0];
							   					}
							   					else
							   						guest=arrid1[0];
							   					
							   					/*if(guest.equals(id))
												{
													ischk=false;
												}*/
												attendee = new Attendee(URI.create("mailto:"+guest));
												attendee.getParameters().add(Role.REQ_PARTICIPANT);
												//attendee.getParameters().add(CuType.INDIVIDUAL);
												attendee.getParameters().add(Rsvp.TRUE);
												if(guest.equalsIgnoreCase(id) && eventbean.getOrganizerstatus()!=null && eventbean.getOrganizerstatus().length()>0)
												{
													if(eventbean.getOrganizerstatus().equalsIgnoreCase("ACCEPTED"))
														attendee.getParameters().add(PartStat.ACCEPTED);
													else if(eventbean.getOrganizerstatus().equalsIgnoreCase("TENTATIVE"))
														attendee.getParameters().add(PartStat.TENTATIVE);
													else if(eventbean.getOrganizerstatus().equalsIgnoreCase("DECLINED"))
														attendee.getParameters().add(PartStat.DECLINED);
													else
															attendee.getParameters().add(PartStat.NEEDS_ACTION);
													
													ischk=false;
												}
												else
												{
												if(act.equalsIgnoreCase("a"))
													attendee.getParameters().add(PartStat.ACCEPTED);
												else if(act.equalsIgnoreCase("t"))
													attendee.getParameters().add(PartStat.TENTATIVE);
												else if(act.equalsIgnoreCase("d"))
													attendee.getParameters().add(PartStat.DECLINED);
												else
														attendee.getParameters().add(PartStat.NEEDS_ACTION);
												}
												
												if(guest.equalsIgnoreCase(id))
												{
													ischk=false;
												}
												event.getProperties().add(attendee);
											}
										}
										
										if(ischk && eventbean.getOrganizerstatus()!=null && eventbean.getOrganizerstatus().length()>0)
										{
											attendee = new Attendee(URI.create("mailto:"+id));
											attendee.getParameters().add(Role.REQ_PARTICIPANT);
											attendee.getParameters().add(Rsvp.TRUE);
											if(eventbean.getOrganizerstatus().equalsIgnoreCase("ACCEPTED"))
												attendee.getParameters().add(PartStat.ACCEPTED);
											else if(eventbean.getOrganizerstatus().equalsIgnoreCase("TENTATIVE"))
												attendee.getParameters().add(PartStat.TENTATIVE);
											else if(eventbean.getOrganizerstatus().equalsIgnoreCase("DECLINED"))
												attendee.getParameters().add(PartStat.DECLINED);
											else
													attendee.getParameters().add(PartStat.NEEDS_ACTION);
											//attendee.getParameters().add(PartStat.ACCEPTED);
											event.getProperties().add(attendee);
											orst=false;
											if(eventbean.getOrganizer()!=null && eventbean.getOrganizer().length()>0)
											{
												String ornr=eventbean.getOrganizer();
												if(!ornr.startsWith("mailto:"))
												{
													ornr="mailto:"+ornr;
												}
												orgnm=ornr;
											Organizer organizer = new Organizer(URI.create(ornr));
											organizer.getParameters().add(Role.CHAIR);
											organizer.getParameters().add(Rsvp.TRUE);
											organizer.getParameters().add(PartStat.ACCEPTED);
											event.getProperties().add(organizer);
											}
											else
											{
												Organizer organizer = new Organizer(URI.create("mailto:"+orgemail));
												//organizer.getParameters().add(new Cn(orgname));
												organizer.getParameters().add(Role.CHAIR);
												organizer.getParameters().add(Rsvp.TRUE);
												organizer.getParameters().add(PartStat.ACCEPTED);
												event.getProperties().add(organizer);
												orgnm="mailto:"+orgemail;
											}
										}
									}
									
									if(eventbean.getOrganizer()!=null && eventbean.getOrganizer().length()>0 && orst)
									{
										String ornr=eventbean.getOrganizer();
										if(!ornr.startsWith("mailto:"))
										{
											ornr="mailto:"+ornr;
										}
										Organizer organizer = new Organizer(URI.create(ornr));
										//organizer.getParameters().add(new Cn(orgname));
										organizer.getParameters().add(Role.CHAIR);
										organizer.getParameters().add(Rsvp.TRUE);
										organizer.getParameters().add(PartStat.ACCEPTED);
										event.getProperties().add(organizer);
										orgnm=ornr;
									}

									/*if(eventbean.getFrequency()!=null && !eventbean.getFrequency().equalsIgnoreCase("no"))
									{
										Recur recur=null;
										if(eventbean.getCount()!=null)
										{
											recur=new Recur(eventbean.getFrequency(),eventbean.getCount());
											recur.setInterval(eventbean.getInterval());
											if(recur.getFrequency().equals("YEARLY"))
											{
												
												dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
											}
											else if(recur.getFrequency().equals("MONTHLY"))
											{
												dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
											}
											else
											{
												dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
											}
											
										}
										else if(eventbean.getUntil()!=null)
										{
											net.fortuna.ical4j.model.Date dt = new net.fortuna.ical4j.model.Date(eventbean.getUntil().toGregorianCalendar().getTime());
											dt.setDate(dt.getDate()+1);
											recur=new Recur(eventbean.getFrequency(),new net.fortuna.ical4j.model.Date(dt));
											dur=new Dur(event.getStartDate().getDate(),recur.getUntil());
										}
										else
										{
											recur = new Recur(eventbean.getFrequency(),null);
											if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
											{
												List<String> days=eventbean.getRepeatdatetimelist().getDateTime();
												for(String day:days)
													recur.getDayList().add(new WeekDay(day));
											}
											recur.setInterval(eventbean.getInterval());
											event.getProperties().add(new RRule(recur));
											
											Date dt  =new Date(event.getEndDate().getDate().getTime());
											dt.setYear(dt.getYear() +5);
											dur=new Dur(event.getStartDate().getDate(),dt);
											response.setRepeatdates(calculaterepeatdate(event,event.getStartDate(), dur));
										}
										

										
										
										if(eventbean.getCount()!=null || eventbean.getUntil()!=null)
										{
											// for( Iterator<String> i=eventbean.getRepeatdatetimelist().getDateTime().iterator();i.hasNext();)
											if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
											{
												List<String> days=eventbean.getRepeatdatetimelist().getDateTime();
												for(String day:days)
													recur.getDayList().add(new WeekDay(day));
											}
											recur.setInterval(eventbean.getInterval());
											
											event.getProperties().add(new RRule(recur));
											///////////method testing
											response.setRepeatdates(calculaterepeatdate(event,event.getStartDate(), dur));
										}
									
									}*/
									if(eventbean.getReminderdata()!=null && !eventbean.getReminderdata().equals(""))
									{
										String[] reminderarray = eventbean.getReminderdata().split(";");
										for (String reminderdata : reminderarray) 
										{
											String[] reminders = reminderdata.split("`");
											VAlarm alarm = new VAlarm();
											String triggerduration=null;
											if(reminders[2].equals("W") || reminders[2].equals("D"))
												triggerduration="-P"+reminders[1]+reminders[2];
											else if (reminders[2].equals("M") || reminders[2].equals("H"))
												triggerduration="-PT"+reminders[1]+reminders[2];
											if (reminders[0].equalsIgnoreCase("Pop-up")) {
												alarm.getProperties().add(Action.DISPLAY);	                  
											}
											else if (reminders[0].equalsIgnoreCase("Email"))
											{
												alarm.getProperties().add(Action.EMAIL);
												alarm.getProperties().add(new Summary("Alarm Notification"));
												try {
													alarm.getProperties().add(new Attendee("mailto:"+id));
												} catch (URISyntaxException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
											alarm.getProperties().add(new Description("Event Reminder"));
											Dur duration = new Dur(triggerduration);
											alarm.getProperties().add(new Trigger(duration));
											
											event.getAlarms().add(alarm);
										}
									}
											//---------------finish create event------------
									cal.getComponents().add(event);
								}
							}
							
								if(stt)
								{
									cal.getComponents().add(component);
								}
							
							
						}
					
					
			
				
				
				
				CalDavCalendarCollection caldc= store.getCollection(calnm);
			
					
						String url = calnm+hid_uid+".ics";
						String bd=cal.toString();
						boolean stt= npEventUpdate(bd,url,id, pass);
						if(!stt)
						{
							eventbean.setUid("-1");
						}
						response.setUpdateStatus(true);
					
				
				
				response.setEventcolor(caldc.getColor());
				response.setOrganizername(orgnm);
				//ust=true;
				//response.setUpdateStatus(ust);
				response.setEventuid(eventbean.getUid());
				
				}
				catch (ObjectStoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					response.setUpdateStatus(false);
				} catch (ObjectNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					response.setUpdateStatus(false);
				}	
				catch(Exception e)
				{
					e.printStackTrace();
					response.setUpdateStatus(false);
				}
				
			}
			return response;
		}
		
		
		public boolean repEventDeleteEditedOrJust(String proid, String surl, String id ,String pass,String calnm, String hid_uid, String flg, String hid_recureventid, String hid_repStartDate)
		{
			
			boolean stt=true;
			
		
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			
			if(store!=null)
			{
				
				
				Calendar cal = new Calendar();
				
				cal.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
				cal.getProperties().add(Version.VERSION_2_0);
				cal.getProperties().add(CalScale.GREGORIAN);
				
				
				try {
					Calendar caldr=store.getCollection(calnm).getCalendar(hid_uid);
					
					 for (Iterator<VEvent> it = caldr.getComponents("VEVENT").iterator(); it.hasNext();)
						{
							VEvent component = (VEvent) it.next();
							RRule rrule=(RRule)component.getProperty("RRULE");
							if(component.getRecurrenceId()==null && rrule!=null)
							{
								if(hid_repStartDate!=null && hid_repStartDate.length()>0)
								{
									if(hid_repStartDate.contains("T"))
									{
									SimpleDateFormat infd = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
									SimpleDateFormat outfd = new SimpleDateFormat("yyyyMMdd");
									
									Date xdt=infd.parse(hid_repStartDate);
									ParameterList pl = new ParameterList();
									pl.add(Value.DATE);
									component.getProperties().add(new ExDate(pl,outfd.format(xdt)));
									}
									else
									{
										SimpleDateFormat infd = new SimpleDateFormat("yyyy-MM-dd");
										SimpleDateFormat outfd = new SimpleDateFormat("yyyyMMdd");
										Date xdt=infd.parse(hid_repStartDate);
										
										/*DateList dateList=new DateList();
										net.fortuna.ical4j.model.Date rrdt = new net.fortuna.ical4j.model.Date(xdt.getTime());
										rrdt.setDate(rrdt.getDate()+1);
										ParameterList pl = new ParameterList();
									 	pl.add(Value.DATE);
										dateList.add(rrdt);
									    TimeZoneRegistry tr = TimeZoneRegistryFactory.getInstance().createRegistry();
									    TimeZone tz = tr.getTimeZone("Asia/Kolkata");
									    ExDate exDate = new ExDate();
								        exDate.setTimeZone(tz);
										exDate.setUtc(false);
								        exDate.getDates().add();*/
										ParameterList pl = new ParameterList();
										pl.add(Value.DATE);
										component.getProperties().add(new ExDate(pl,outfd.format(xdt)));
									}
									
								}
							}
							
							
							if(flg.equalsIgnoreCase("edited"))
							{
								if(!(component.getRecurrenceId()!=null && hid_recureventid!=null && hid_recureventid.length()>0 && component.getRecurrenceId().getValue().equalsIgnoreCase(hid_recureventid)))
								{
									cal.getComponents().add(component);
								}
								
							}
							else
							{
								cal.getComponents().add(component);
							}
							
						}
					
					 
			
				
				
				
				CalDavCalendarCollection caldc= store.getCollection(calnm);
			
					
						String url = calnm+hid_uid+".ics";
						String bd=cal.toString();
						npEventUpdate(bd,url,id, pass);
						
				
				
				}
				
				catch(Exception e)
				{
					e.printStackTrace();
					stt=false;
				}
				
			}
			else
			{
				stt=false;
			}
			return stt;
		}
		
		
		public CreateEventResponse editRepEventJustorAll(String proid, String surl, String id ,String pass,String calnm,EventBean eventbean,String orgname, String orgemail, String hid_uid,String repaction)
		{
			Dur dur=null;
			boolean ust=false;
			boolean updt=false;
			String orgnm="";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
			CreateEventResponse response=new CreateEventResponse();
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			
			if(store!=null)
			{
				
				VEvent event = new VEvent();
				

				//---------create event----------
				
				event.getProperties().add(new Uid(hid_uid));
				
				eventbean.setUid(event.getUid().getValue());				
				event.getProperties().add(new Created(new DateTime()));
				event.getProperties().add(new LastModified(new DateTime()));
				
				
				event.getProperties().add(new Summary(eventbean.getSummary()));
				
				
				DtStart dtstart=null;
				DtEnd dtend=null;
				//String c=eventbean.getAllday();	
				try
				{
					if(eventbean.getAllday()!=null && eventbean.getAllday().equals("off"))
					{
						dtstart=new DtStart(dateFormat.format(eventbean.getStarteventdate().toGregorianCalendar().getTime()));
						dtend=new DtEnd(dateFormat.format(eventbean.getEndeventdate().toGregorianCalendar().getTime()));
					}
					else
					{
						dtstart=new DtStart();
						dtend=new DtEnd();
						net.fortuna.ical4j.model.Date dt = new net.fortuna.ical4j.model.Date(eventbean.getStarteventdate().toGregorianCalendar().getTime());
						dt.setDate(dt.getDate()+1);
						net.fortuna.ical4j.model.Date dt1 = new net.fortuna.ical4j.model.Date(eventbean.getEndeventdate().toGregorianCalendar().getTime());
						dt1.setDate(dt1.getDate()+1);
						dtstart.setDate(dt);
						dtend.setDate(dt1);
					}
					
				}
				catch(ParseException e)
				{
					e.printStackTrace();
					response.setResponse(null);
					response.setUpdateStatus(false);
					return response;
				}
				event.getProperties().add(dtstart);
				event.getProperties().add(dtend);
				if(eventbean.getLocation()!=null && !eventbean.getLocation().equals(""))
				event.getProperties().add(new Location(eventbean.getLocation()));
				if(eventbean.getDescription()!=null && !eventbean.getDescription().equals(""))
				event.getProperties().add(new Description(eventbean.getDescription()));
				event.getProperties().add(new Sequence(1));
				event.getProperties().add(Status.VEVENT_CONFIRMED);
				try
				{
				event.getProperties().add(new Clazz(eventbean.getClazz()));
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
				
				if(eventbean.getFreebusy()!=null && eventbean.getFreebusy().equals("free"))
				{
					event.getProperties().add(Transp.TRANSPARENT);
				}
				else
				{
					event.getProperties().add(Transp.OPAQUE);
				}
				
				if(eventbean.getNewguest()==null)
					eventbean.setNewguest("");
				if(eventbean.getOldguest()==null)
					eventbean.setOldguest("");
				Attendee attendee = null;
				boolean orst=true;
				if(eventbean.getNewguest() != "" || eventbean.getOldguest() != "")
				{
					boolean ischk=true;
					String guests[] = (eventbean.getNewguest()+""+eventbean.getOldguest()).split(",");
					for(String guest : guests)
					{
						
						if(guest != null && guest.length() != 0)
						{
							String act="";
							String arrid1[]=guest.split("`");
		   					if(arrid1.length>1)
		   					{
		   						guest=arrid1[1];
		   						act=arrid1[0];
		   					}
		   					else
		   						guest=arrid1[0];
		   					
		   					/*if(guest.equals(id))
							{
								ischk=false;
							}*/
							attendee = new Attendee(URI.create("mailto:"+guest));
							attendee.getParameters().add(Role.REQ_PARTICIPANT);
							//attendee.getParameters().add(CuType.INDIVIDUAL);
							attendee.getParameters().add(Rsvp.TRUE);
							if(guest.equalsIgnoreCase(id) && eventbean.getOrganizerstatus()!=null && eventbean.getOrganizerstatus().length()>0)
							{
								if(eventbean.getOrganizerstatus().equalsIgnoreCase("ACCEPTED"))
									attendee.getParameters().add(PartStat.ACCEPTED);
								else if(eventbean.getOrganizerstatus().equalsIgnoreCase("TENTATIVE"))
									attendee.getParameters().add(PartStat.TENTATIVE);
								else if(eventbean.getOrganizerstatus().equalsIgnoreCase("DECLINED"))
									attendee.getParameters().add(PartStat.DECLINED);
								else
										attendee.getParameters().add(PartStat.NEEDS_ACTION);
								
								ischk=false;
							}
							else
							{
							if(act.equalsIgnoreCase("a"))
								attendee.getParameters().add(PartStat.ACCEPTED);
							else if(act.equalsIgnoreCase("t"))
								attendee.getParameters().add(PartStat.TENTATIVE);
							else if(act.equalsIgnoreCase("d"))
								attendee.getParameters().add(PartStat.DECLINED);
							else
									attendee.getParameters().add(PartStat.NEEDS_ACTION);
							}
							
							if(guest.equalsIgnoreCase(id))
							{
								ischk=false;
							}
							event.getProperties().add(attendee);
						}
					}
					
					if(ischk && eventbean.getOrganizerstatus()!=null && eventbean.getOrganizerstatus().length()>0)
					{
						attendee = new Attendee(URI.create("mailto:"+id));
						attendee.getParameters().add(Role.REQ_PARTICIPANT);
						attendee.getParameters().add(Rsvp.TRUE);
						if(eventbean.getOrganizerstatus().equalsIgnoreCase("ACCEPTED"))
							attendee.getParameters().add(PartStat.ACCEPTED);
						else if(eventbean.getOrganizerstatus().equalsIgnoreCase("TENTATIVE"))
							attendee.getParameters().add(PartStat.TENTATIVE);
						else if(eventbean.getOrganizerstatus().equalsIgnoreCase("DECLINED"))
							attendee.getParameters().add(PartStat.DECLINED);
						else
								attendee.getParameters().add(PartStat.NEEDS_ACTION);
						//attendee.getParameters().add(PartStat.ACCEPTED);
						event.getProperties().add(attendee);
						orst=false;
						if(eventbean.getOrganizer()!=null && eventbean.getOrganizer().length()>0)
						{
							String ornr=eventbean.getOrganizer();
							if(!ornr.startsWith("mailto:"))
							{
								ornr="mailto:"+ornr;
							}
							orgnm=ornr;
						Organizer organizer = new Organizer(URI.create(ornr));
						organizer.getParameters().add(Role.CHAIR);
						organizer.getParameters().add(Rsvp.TRUE);
						organizer.getParameters().add(PartStat.ACCEPTED);
						event.getProperties().add(organizer);
						}
						else
						{
							Organizer organizer = new Organizer(URI.create("mailto:"+orgemail));
							//organizer.getParameters().add(new Cn(orgname));
							organizer.getParameters().add(Role.CHAIR);
							organizer.getParameters().add(Rsvp.TRUE);
							organizer.getParameters().add(PartStat.ACCEPTED);
							event.getProperties().add(organizer);
							orgnm="mailto:"+orgemail;
						}
					}
				}
				
				if(eventbean.getOrganizer()!=null && eventbean.getOrganizer().length()>0 && orst)
				{
					String ornr=eventbean.getOrganizer();
					if(!ornr.startsWith("mailto:"))
					{
						ornr="mailto:"+ornr;
					}
					Organizer organizer = new Organizer(URI.create(ornr));
					//organizer.getParameters().add(new Cn(orgname));
					organizer.getParameters().add(Role.CHAIR);
					organizer.getParameters().add(Rsvp.TRUE);
					organizer.getParameters().add(PartStat.ACCEPTED);
					event.getProperties().add(organizer);
					orgnm=ornr;
				}
				if(repaction.equalsIgnoreCase("all"))
				{
				if(eventbean.getExdateList()!=null && eventbean.getExdateList().length()>0)
				{
					String exarr[]=eventbean.getExdateList().split(",");
					for(int i=0;i<exarr.length;i++)
					{
						ParameterList pl = new ParameterList();
						pl.add(Value.DATE);
						try {
							event.getProperties().add(new ExDate(pl,exarr[i]));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
					
				if(eventbean.getFrequency()!=null && !eventbean.getFrequency().equalsIgnoreCase("no"))
				{
					Recur recur=null;
					if(eventbean.getCount()!=null)
					{
						recur=new Recur(eventbean.getFrequency(),eventbean.getCount());
						recur.setInterval(eventbean.getInterval());
						if(recur.getFrequency().equals("YEARLY"))
						{
							
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
						}
						else if(recur.getFrequency().equals("MONTHLY"))
						{
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
						}
						else
						{
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
						}
						
					}
					else if(eventbean.getUntil()!=null)
					{
						net.fortuna.ical4j.model.Date dt = new net.fortuna.ical4j.model.Date(eventbean.getUntil().toGregorianCalendar().getTime());
						dt.setDate(dt.getDate()+1);
						recur=new Recur(eventbean.getFrequency(),new net.fortuna.ical4j.model.Date(dt));
						dur=new Dur(event.getStartDate().getDate(),recur.getUntil());
					}
					else
					{
						recur = new Recur(eventbean.getFrequency(),null);
						if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
						{
							List<String> days=eventbean.getRepeatdatetimelist().getDateTime();
							for(String day:days)
								recur.getDayList().add(new WeekDay(day));
						}
						recur.setInterval(eventbean.getInterval());
						event.getProperties().add(new RRule(recur));
						
						Date dt  =new Date(event.getEndDate().getDate().getTime());
						dt.setYear(dt.getYear() +5);
						dur=new Dur(event.getStartDate().getDate(),dt);
						response.setRepeatdates(calculaterepeatdate(event,event.getStartDate(), dur));
					}
					

					
					
					if(eventbean.getCount()!=null || eventbean.getUntil()!=null)
					{
						// for( Iterator<String> i=eventbean.getRepeatdatetimelist().getDateTime().iterator();i.hasNext();)
						if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
						{
							List<String> days=eventbean.getRepeatdatetimelist().getDateTime();
							for(String day:days)
								recur.getDayList().add(new WeekDay(day));
						}
						recur.setInterval(eventbean.getInterval());
						
						event.getProperties().add(new RRule(recur));
						///////////method testing
						response.setRepeatdates(calculaterepeatdate(event,event.getStartDate(), dur));
					}
				
				}
				}
				else
				{
					if(eventbean.getAllday()!=null && eventbean.getAllday().equals("off"))
					{
						try {
							event.getProperties().add(new RecurrenceId(dateFormat.format(eventbean.getStarteventdate().toGregorianCalendar().getTime())));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
					net.fortuna.ical4j.model.Date rrdt = new net.fortuna.ical4j.model.Date(eventbean.getStarteventdate().toGregorianCalendar().getTime());
					rrdt.setDate(rrdt.getDate()+1);
					event.getProperties().add(new RecurrenceId(rrdt));
					}
				}
				
				
				if(eventbean.getReminderdata()!=null && !eventbean.getReminderdata().equals(""))
				{
					String[] reminderarray = eventbean.getReminderdata().split(";");
					for (String reminderdata : reminderarray) 
					{
						String[] reminders = reminderdata.split("`");
						VAlarm alarm = new VAlarm();
						String triggerduration=null;
						if(reminders[2].equals("W") || reminders[2].equals("D"))
							triggerduration="-P"+reminders[1]+reminders[2];
						else if (reminders[2].equals("M") || reminders[2].equals("H"))
							triggerduration="-PT"+reminders[1]+reminders[2];
						if (reminders[0].equalsIgnoreCase("Pop-up")) {
							alarm.getProperties().add(Action.DISPLAY);	                  
						}
						else if (reminders[0].equalsIgnoreCase("Email"))
						{
							alarm.getProperties().add(Action.EMAIL);
							alarm.getProperties().add(new Summary("Alarm Notification"));
							try {
								alarm.getProperties().add(new Attendee("mailto:"+id));
							} catch (URISyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						alarm.getProperties().add(new Description("Event Reminder"));
						Dur duration = new Dur(triggerduration);
						alarm.getProperties().add(new Trigger(duration));
						
						event.getAlarms().add(alarm);
					}
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				Calendar cal = new Calendar();
				
				cal.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
				cal.getProperties().add(Version.VERSION_2_0);
				cal.getProperties().add(CalScale.GREGORIAN);
				
				
				try {
					Calendar caldr=store.getCollection(calnm).getCalendar(hid_uid);
					boolean stt=true;
					 for (Iterator<VEvent> it = caldr.getComponents("VEVENT").iterator(); it.hasNext();)
						{
							VEvent component = (VEvent) it.next();
							
							if(repaction.equalsIgnoreCase("one"))
							{
								cal.getComponents().add(component);
							}
							else
							{
								RRule rrule=(RRule)component.getProperty("RRULE");
								if(component.getRecurrenceId()==null && rrule!=null)
								{
									stt=false;
									cal.getComponents().add(event);
								}
								else
								{
									cal.getComponents().add(component);
								}
							}
								
							
							
						}
					
					 if(stt)
						{
							cal.getComponents().add(event);
						}
			
				
				
				
				CalDavCalendarCollection caldc= store.getCollection(calnm);
			
					
						String url = calnm+hid_uid+".ics";
						String bd=cal.toString();
						npEventUpdate(bd,url,id, pass);
						response.setUpdateStatus(true);
					
				
				
				response.setEventcolor(caldc.getColor());
				response.setOrganizername(orgnm);
				//ust=true;
				//response.setUpdateStatus(ust);
				response.setEventuid(eventbean.getUid());
				
				}
				catch (ObjectStoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					response.setUpdateStatus(false);
				} catch (ObjectNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					response.setUpdateStatus(false);
				}	
				catch(Exception e)
				{
					e.printStackTrace();
					response.setUpdateStatus(false);
				}
				
			}
			return response;
		}
		
		
	public boolean updateCalendar(String url, String id ,String pass,String calnm, String calClr, String desc, boolean iscol)
	{
		boolean st=true;
		String body="";
		if(iscol)
		{
			body+="<?xml version='1.0' encoding='utf-8' ?>";
			body+="<propertyupdate xmlns='DAV:' xmlns:C='urn:ietf:params:xml:ns:caldav' xmlns:ical='http://apple.com/ns/ical/' xmlns:C1='http://apple.com/ns/ical/:calendar-color'>";
			body+="<set> <prop>  <ical:calendar-color>"+calClr+"</ical:calendar-color> </prop></set></propertyupdate>";
			
		}
		else
		{
			body+="<?xml version='1.0' encoding='utf-8' ?>";
			body+="<propertyupdate xmlns='DAV:' xmlns:C='urn:ietf:params:xml:ns:caldav' xmlns:caldav='urn:ietf:params:xml:ns:caldav' xmlns:C1='urn:ietf:params:xml:ns:caldav:calendar-description'>";
			body+="<set> <prop> <displayname>"+calnm+"</displayname> <caldav:calendar-description>"+desc+"</caldav:calendar-description> </prop></set></propertyupdate>";
			
		}
		try
		{
		String str=id+":"+pass;
		byte[]   bytesEncoded = org.apache.commons.codec.binary.Base64.encodeBase64( str .getBytes());
		String auth=new String(bytesEncoded );
		URL obj = new URL(url);
		
		if(url.startsWith("https://"))
		{
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestProperty("X-HTTP-Method-Override", "PROPPATCH");
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Sandesh");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Accept", "text/xml");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");
			con.setRequestProperty("Accept-Charset", "utf-8,*;q=0.1");
			con.setRequestProperty("Content-Type", "application/xml");
			//con.setRequestProperty("If-Match", etag);
			con.setRequestProperty("Authorization", "Basic "+auth);
			con.setRequestProperty("Keep-Alive", "true");
			con.setRequestProperty("Pragma", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Agent", "Sandesh");
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("res code-"+responseCode);
			con.disconnect();
		}
		else
		{
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("X-HTTP-Method-Override", "PROPPATCH");
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Sandesh");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept", "text/xml");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate");
		con.setRequestProperty("Accept-Charset", "utf-8,*;q=0.1");
		con.setRequestProperty("Content-Type", "application/xml");
		//con.setRequestProperty("If-Match", etag);
		con.setRequestProperty("Authorization", "Basic "+auth);
		con.setRequestProperty("Keep-Alive", "true");
		con.setRequestProperty("Pragma", "no-cache");
		con.setRequestProperty("Cache-Control", "no-cache");
		con.setRequestProperty("Agent", "Sandesh");
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(body);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("res code-"+responseCode);
		con.disconnect();
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			st=false;
		}
		return st;
	}
		
	
	public boolean shareCollection(String url, String id ,String pass, String surl, String whitchid, String permission)
	{
		boolean st=true;
		String whitchurl=surl+"/caldav.php/"+whitchid+"/";
		String body="";
		body+="<?xml version='1.0' encoding='utf-8' ?><acl xmlns='DAV:' xmlns:C='urn:ietf:params:xml:ns:caldav'>";
		body+="<ace>  <principal>   <property>  <owner/> </property>  </principal>  <grant>   <privilege>   <all/>  </privilege>";
		body+="<privilege>   <C:read-free-busy/>   </privilege>  </grant></ace>";
		body+="<ace>  <principal>   <href>"+whitchurl+"</href>  </principal> <grant>   <privilege> <C:read-free-busy/> </privilege>";
		if(permission.equalsIgnoreCase("ur"))
		{
			body+="<privilege> <read/> </privilege>";
		}
		else if(permission.equalsIgnoreCase("uw"))
		{
			body+="<privilege><read/> </privilege> <privilege><write-content/> </privilege><privilege><bind/> </privilege>";
		}
		else if(permission.equalsIgnoreCase("us"))
		{
			body+="<privilege> <all/> </privilege>";
		}
		body+=" </grant></ace>";
		body+="<ace> <principal> <authenticated/>  </principal> <grant> <privilege> <C:read-free-busy/> </privilege> </grant></ace></acl>";
		try
		{
		String str=id+":"+pass;
		byte[]   bytesEncoded = org.apache.commons.codec.binary.Base64.encodeBase64( str .getBytes());
		String auth=new String(bytesEncoded );
		URL obj = new URL(url);
		
		if(url.startsWith("https://"))
		{
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestProperty("X-HTTP-Method-Override", "ACL");
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Sandesh");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Accept", "text/xml");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");
			con.setRequestProperty("Accept-Charset", "utf-8,*;q=0.1");
			con.setRequestProperty("Content-Type", "application/xml");
			//con.setRequestProperty("If-Match", etag);
			con.setRequestProperty("Authorization", "Basic "+auth);
			con.setRequestProperty("Keep-Alive", "true");
			con.setRequestProperty("Pragma", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Agent", "Sandesh");
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("res code-"+responseCode);
			con.disconnect();
		}
		else
		{
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("X-HTTP-Method-Override", "ACL");
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Sandesh");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept", "text/xml");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate");
		con.setRequestProperty("Accept-Charset", "utf-8,*;q=0.1");
		con.setRequestProperty("Content-Type", "application/xml");
		//con.setRequestProperty("If-Match", etag);
		con.setRequestProperty("Authorization", "Basic "+auth);
		con.setRequestProperty("Keep-Alive", "true");
		con.setRequestProperty("Pragma", "no-cache");
		con.setRequestProperty("Cache-Control", "no-cache");
		con.setRequestProperty("Agent", "Sandesh");
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(body);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("res code-"+responseCode);
		con.disconnect();
		if(responseCode>=400)
		{
			st=false;
		}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			st=false;
		}
		return st;
	}
		
	
	public Calendar getSharedEvent(String url, String id, String pass)
	{
		Calendar calr=null;
		 CalendarBuilder calendarbuilder = new CalendarBuilder();
		
		try
		{
		
			String str=id+":"+pass;
			byte[]   bytesEncoded = org.apache.commons.codec.binary.Base64.encodeBase64( str .getBytes());
			String auth=new String(bytesEncoded );
			URL obj = new URL(url);
			///URLConnection conn = obj.openConnection();
			if(url.startsWith("https://"))
			{
			HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
			conn.setRequestProperty("Authorization", "Basic "+auth);
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			
			
			try {
				calr = calendarbuilder.build(conn.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		int responseCode = conn.getResponseCode();
		System.out.println("res code-"+responseCode);
		conn.disconnect();
			
		}
		else
			{
				
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestProperty("Authorization", "Basic "+auth);
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			
			try {
				calr = calendarbuilder.build(conn.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}

		int responseCode = conn.getResponseCode();
		System.out.println("res code-"+responseCode);
		conn.disconnect();
				
			}
		
		
		
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return calr;
	}
	
		public boolean npEventUpdate(String body, String url, String id, String pass)
		{
			boolean st=true;
			try
			{
			
				String str=id+":"+pass;
				byte[]   bytesEncoded = org.apache.commons.codec.binary.Base64.encodeBase64( str .getBytes());
				String auth=new String(bytesEncoded );
				URL obj = new URL(url);
				///URLConnection conn = obj.openConnection();
				if(url.startsWith("https://"))
				{
				HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
				conn.setRequestProperty("Authorization", "Basic "+auth);
				conn.setRequestMethod("GET");
				conn.setDoOutput(true);
				
				String etag = conn.getHeaderField("ETag");
			
				conn.disconnect();
			
			
			
			
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			//add reuqest header
			con.setRequestMethod("PUT");
			con.setRequestProperty("User-Agent", "Sandesh");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Accept", "text/xml");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");
			con.setRequestProperty("Accept-Charset", "utf-8,*;q=0.1");
			con.setRequestProperty("Content-Type", "text/calendar; charset=utf-8");
			con.setRequestProperty("If-Match", etag);
			con.setRequestProperty("Authorization", "Basic "+auth);
			con.setRequestProperty("Keep-Alive", "true");
			con.setRequestProperty("Pragma", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Agent", "Sandesh");
			con.setRequestProperty("Agent", "Sandesh");
			
			
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("res code-"+responseCode);
			if(responseCode>=400)
				st=false;
			con.disconnect();
				
			}
			else
				{
					
				HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
				conn.setRequestProperty("Authorization", "Basic "+auth);
				conn.setRequestMethod("GET");
				conn.setDoOutput(true);
				
				String etag = conn.getHeaderField("ETag");
			
				conn.disconnect();
			
			
			
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			//add reuqest header
			con.setRequestMethod("PUT");
			con.setRequestProperty("User-Agent", "Sandesh");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Accept", "text/xml");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");
			con.setRequestProperty("Accept-Charset", "utf-8,*;q=0.1");
			con.setRequestProperty("Content-Type", "text/calendar; charset=utf-8");
			con.setRequestProperty("If-Match", etag);
			con.setRequestProperty("Authorization", "Basic "+auth);
			con.setRequestProperty("Keep-Alive", "true");
			con.setRequestProperty("Pragma", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Agent", "Sandesh");
			con.setRequestProperty("Agent", "Sandesh");
			
			
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("res code-"+responseCode);
			if(responseCode>=400)
				st=false;
			con.disconnect();
					
				}
			
			
			
			
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
					st=false;
			}
			return st;
		}
		
		 public void writeCalendarOnServer(CalDavCalendarStore store,Calendar calendar, boolean isNew) throws ObjectStoreException, ConstraintViolationException {
		        Uid uid = Calendars.getUid(calendar);

		        String path = store.getPath();
		        if (!path.endsWith("/")) {
		            path = path.concat("/");
		        }
		        PutMethod putMethod = new PutMethod(path + uid.getValue() + ".ics");
		        // putMethod.setAllEtags(true);
		        
		        if (isNew) {
		            putMethod.addRequestHeader("If-None-Match", "*");
		        } else {
		            putMethod.addRequestHeader("If-Match", "*");
		        }
		        // putMethod.setRequestBody(calendar);

		        try {
		            putMethod.setCalendar(calendar);
		        } catch (Exception e) {
		            throw new ObjectStoreException("Invalid calendar", e);
		        }

		        try {
		            // TODO: get ETag and Schedule-Tag headers and store them locally
		        	store.getClient().execute(putMethod);
		            if ((putMethod.getStatusCode() != DavServletResponse.SC_CREATED)
		                    && (putMethod.getStatusCode() != DavServletResponse.SC_NO_CONTENT)) {
		                throw new ObjectStoreException("Error creating calendar on server: " + putMethod.getStatusLine());
		            }
		        } catch (IOException ioe) {
		            throw new ObjectStoreException("Error creating calendar on server", ioe);
		        }
		    }
		
		
		public boolean  deleteCal(String proid, String surl, String id ,String pass,String calnm)
		{
			boolean res=false;
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			if(store!=null)
			{
				try {
					store.getCollection(calnm).delete();
					res=true;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				try{
					//store.disconnect();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			return res;
		}
		
		
		public CalDavCalendarCollection  exportICS(String proid, String surl, String id ,String pass,String calnm)
		{
			CalDavCalendarCollection cal=null;
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			if(store!=null)
			{
				try {
					cal=store.getCollection(calnm);
					}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				try{
					//store.disconnect();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			return cal;
		}
		
		public boolean  deleteCalEvent(String proid, String surl, String id ,String pass,String calnm, String eventuid)
		{
			boolean res=false;
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			if(store!=null)
			{
				try {
					Calendar calr=	store.getCollection(calnm).removeCalendar(eventuid);
					if(calr!=null)
					{
						res=true;
					}
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				try{
					//store.disconnect();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			return res;
		}
		
		
		public void importCalEvent( InputStream inputstream, String proid, String surl, String id ,String pass,String calnm)
		{
			Calendar calr=null;
			 CalendarBuilder calendarbuilder = new CalendarBuilder();
			// InputStream inputstream=null;
			/* byte[] encodedImage = org.apache.commons.codec.binary.Base64
	                 .decodeBase64(calendarfilecontent);*/
			try {
				//inputstream = new ByteArrayInputStream(calendarfilecontent);
				calr = calendarbuilder.build(inputstream);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(calr!=null)
			{
				try
				{
				CalDavCalendarStore store=connect(proid, surl, id, pass);
				CalDavCalendarCollection caldc= store.getCollection(calnm);
				if(store!=null)
				{
					
					for (Iterator<VEvent> it = calr.getComponents("VEVENT").iterator(); it.hasNext();)
					{
						try
						{
							Calendar cal = new Calendar();
							cal.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
							cal.getProperties().add(Version.VERSION_2_0);
							cal.getProperties().add(CalScale.GREGORIAN);
							VEvent componentevent = (VEvent)it.next();
							cal.getComponents().add(componentevent);
							caldc.addCalendar(cal);
						}
						catch(Exception ee)
						{
							ee.printStackTrace();
						}
					}
					
				}
				
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			try
			{
				inputstream.close();
			}
			catch(Exception e)
			{
				
			}
		}
		
		public EventBean  buildCalEvent(byte[] calendarfilecontent)
		{
			 Calendar calr=null;
			 EventBean event= new EventBean();
			 CalendarBuilder calendarbuilder = new CalendarBuilder();
			 InputStream inputstream=null;
			 byte[] encodedImage = org.apache.commons.codec.binary.Base64
	                 .decodeBase64(calendarfilecontent);
			try {
				inputstream = new ByteArrayInputStream(encodedImage);
				calr = calendarbuilder.build(inputstream);
				inputstream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(calr!=null)
			{
				try {
				 
					for (Iterator<VEvent> it = calr.getComponents("VEVENT").iterator(); it.hasNext();)
					{
						VEvent componentevent = (VEvent)it.next();

						event.setSummary(componentevent.getSummary().getValue());
						if(componentevent.getUid()!=null)
							event.setUid(componentevent.getUid().getValue());
						//date type change start
						
						  GregorianCalendar gregoriancalendar=new GregorianCalendar();
						
						  
						  try 
						  {
							  if(componentevent.getStartDate().getDate().toString().indexOf("T",0)<0)
							  {	
								  event.setAllday("on");
								  String sdt=componentevent.getStartDate().getDate().toString();
								  sdt+="T000000+05:30";
								  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
								  java.util.Date dt=componentevent.getStartDate().getDate();
								  try {
									dt=dateFormat.parse(sdt);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							  	 gregoriancalendar.setTime(dt);
								 event.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
								 
								 String sdt1=componentevent.getEndDate().getDate().toString();
								  sdt1+="T000000+05:30";
								  java.util.Date dt1=componentevent.getEndDate().getDate();
								  try {
									dt1=dateFormat.parse(sdt1);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								 gregoriancalendar.setTime(dt1);
								 event.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
							  
							  }
							  else
							  {
								  event.setAllday("off");
								  gregoriancalendar.setTime(componentevent.getStartDate().getDate());
									 event.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
									 gregoriancalendar.setTime(componentevent.getEndDate().getDate());
									 event.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
							  }
							 
						  }
						  catch (DatatypeConfigurationException e1) {
								e1.printStackTrace();
						  }
						//date type change end
						  
						  try
						  {
							  if(componentevent.getProperty(Property.SEQUENCE).getValue()!=null)
								{
								int sequence = Integer.parseInt(componentevent.getProperty(Property.SEQUENCE).getValue());
								event.setSequence(sequence);
								}
								else
								{
									event.setSequence(0);
								} 
						  }
						  catch(Exception ee)
						  {
							  ee.printStackTrace();
						  }
						if(componentevent.getLocation()!=null)
						event.setLocation(componentevent.getLocation().getValue());
						if(componentevent.getDescription()!=null)
						event.setDescription(componentevent.getDescription().getValue());
						try
						{
						if(componentevent.getClassification().getValue()!=null)
						event.setClazz(componentevent.getClassification().getValue());
						}
						catch(Exception ee)
						{
							ee.printStackTrace();
						}
						if(componentevent.getTransparency() != null)
						{
							if(componentevent.getTransparency().equals(Transp.TRANSPARENT))
							{
								event.setFreebusy("free");
							}
							else
							{
								event.setFreebusy("busy");
							}
						}
						
						
						PropertyList list = componentevent.getProperties(Property.ATTENDEE);
						String guests = "";
						for(Object obj : list)
						{
							if (obj instanceof Attendee) 
							{
								Attendee guest = (Attendee) obj;
								String name = guest.getCalAddress().toString();
								name = name.replace("mailto:", "");
								name = name.replace("MAILTO:", "");
								String status ="";
								try
								{
								status=	guest.getParameter(Parameter.PARTSTAT).getValue();
								}
								catch(Exception ee)
								{
									ee.printStackTrace();
								}
								if(status.equals("ACCEPTED"))
								{
									guests += ",a`"+name;
								}
								else if(status.equals("DECLINED"))
								{
									guests += ",d`"+name;
								}
								else if(status.equals("TENTATIVE"))
								{
									guests += ",t`"+name;
								}
								else if(status.equals("IN_PROCESS"))
								{
									guests += ",i`"+name;
								}
								else
								{
									guests += ",n`"+name;
								}
								//System.out.println("Attendee Status : " + guest.getParameters(Parameter.PARTSTAT));
							}
						}
						event.setOldguest(guests);
						
				       RRule rrule=(RRule)componentevent.getProperty("RRULE");
				       if(rrule!=null)
				       {
				       Recur recur=rrule.getRecur();
				       if(recur.getCount()>0)
				       {
				    	   event.setCount(recur.getCount());
				       }
				       else if(recur.getUntil() != null)
				       {
				    	   gregoriancalendar.setTime(recur.getUntil());
							try {
								event.setUntil( DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
							} catch (DatatypeConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
				    	  // event.setUntil(recur.getUntil().toString());
				       }
				       else
				       {
				    	   
				       }
				       
				       event.setFrequency(recur.getFrequency());
				       event.setInterval(recur.getInterval()); 
				       if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
				       {
				    	   DateTimeList dt=new DateTimeList();
				       for(Iterator<WeekDay> itw =recur.getDayList().iterator(); itw.hasNext();)
				    	   dt.getDateTime().add(itw.next().toString());
				    	   event.setRepeatdatetimelist(dt);
				       }
				       if(recur.getFrequency().equalsIgnoreCase(recur.MONTHLY))
				    	   System.out.println();//////////////////////////////////////////////
				       }
						event.setReminderdata(""); 
			           for( Iterator<VAlarm> iterator=componentevent.getAlarms().iterator();iterator.hasNext();)
			           {
			        	   VAlarm alarm=(VAlarm)iterator.next();
			        	   //String action=alarm.getAction().getValue();
			        	   if(alarm.getAction().getValue().equals("DISPLAY"))
			        	   {
			        		   event.setReminderdata(event.getReminderdata()+"Pop-up"+"`");
			        	   }
			        	   else if (alarm.getAction().getValue().equals("EMAIL")) {
			        		   event.setReminderdata(event.getReminderdata()+"Email"+"`");
			        	   }
			        	   
			        	   Dur duration=new Dur(alarm.getTrigger().getValue());
			        	  
			        	// System.out.println((triggerduration.substring(1, triggerduration.indexOf("D")).equals("0")));
			        	   if (duration.getWeeks()!=0)
			        	   {
			        		   event.setReminderdata(event.getReminderdata()+duration.getWeeks()+"`"+"W"+";");
			        	   }
			        	   else  if(duration.getHours()!=0)
			               {
			        		  
			        		   event.setReminderdata(event.getReminderdata()+duration.getHours()+"`"+"H"+";");
			        	   }
			        	   else if (duration.getDays()!=0) {
			        		   event.setReminderdata(event.getReminderdata()+duration.getDays()+"`"+"D"+";");
			        	   }
			        	   else if (duration.getMinutes()!=0) {
			        		   event.setReminderdata(event.getReminderdata()+duration.getMinutes()+"`"+"M"+";");
			        	   }
			           }
			           System.out.println(event.getReminderdata());
					}
					
				 
				 
				 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			return event;
		}
		
		
		public CalDavCalendarCollection  getCalendarDetail(String proid, String surl, String id ,String pass,String calnm)
		{
			CalDavCalendarCollection caldt=null;
			 CalDavCalendarStore store=connect(proid, surl, id, pass);
			 try {
				caldt=store.getCollection(calnm);
			} catch (ObjectStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return caldt;
		}
		
		public EventBean  getEventDetail(String proid, String surl, String id ,String pass,String calnm, String eventuid, String recurrenceID, String repeatStatus)
		{
			Calendar calr=null;
			 EventBean event= new EventBean();
			if(eventuid!=null)
			{
			 CalDavCalendarStore store=connect(proid, surl, id, pass);
			if(store!=null)
			{
				try {
				 calr=	store.getCollection(calnm).getCalendar(eventuid);
				 
				 
					for (Iterator<VEvent> it = calr.getComponents("VEVENT").iterator(); it.hasNext();)
					{
						VEvent componentevent = (VEvent)it.next();
						
						event= new EventBean();
						
						PropertyList prt=componentevent.getProperties();
 
						event.setSummary(componentevent.getSummary().getValue());
						if(componentevent.getUid()!=null)
							event.setUid(componentevent.getUid().getValue());
						if(componentevent.getOrganizer()!=null)
							event.setOrganizer(componentevent.getOrganizer().getValue());
						//date type change start
						
						  GregorianCalendar gregoriancalendar=new GregorianCalendar();
						  String exdate="";
						  if(componentevent.getProperty(ExDate.EXDATE)!=null )
						  {
						  
						  PropertyList pl = componentevent.getProperties();
						  Iterator<Property> iterator= pl.iterator();
						  while (iterator.hasNext()){
						  Property exdateProperty=iterator.next();
						  if ( exdateProperty instanceof ExDate ) {
							  ExDate ex = (ExDate)exdateProperty;
							  if(exdate.length()<=0)
							  {
								  exdate =ex.getValue();
							  }
							  else
							  {
								  exdate +=","+ex.getValue();
							  }
						  }
						  }
										  
						  }
						  event.setExdateList(exdate);
						  try 
						  {
							  if(componentevent.getStartDate().getDate().toString().indexOf("T",0)<0)
							  {	
								  event.setAllday("on");
								  String sdt=componentevent.getStartDate().getDate().toString();
								  sdt+="T000000+05:30";
								  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
								  java.util.Date dt=componentevent.getStartDate().getDate();
								  try {
									dt=dateFormat.parse(sdt);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							  	 gregoriancalendar.setTime(dt);
								 event.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
								 
								 String sdt1=componentevent.getEndDate().getDate().toString();
								  sdt1+="T000000+05:30";
								  java.util.Date dt1=componentevent.getEndDate().getDate();
								  try {
									dt1=dateFormat.parse(sdt1);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								 gregoriancalendar.setTime(dt1);
								 event.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
							  
							  }
							  else
							  {
								  event.setAllday("off");
								  gregoriancalendar.setTime(componentevent.getStartDate().getDate());
								  event.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
									 gregoriancalendar.setTime(componentevent.getEndDate().getDate());
									 event.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
							  }
							 
						  }
						  catch (DatatypeConfigurationException e1) {
								e1.printStackTrace();
						  }
						//date type change end
						  
						  try
						  {
							  if(componentevent.getProperty(Property.SEQUENCE)!=null)
								{
								int sequence = Integer.parseInt(componentevent.getProperty(Property.SEQUENCE).getValue());
								event.setSequence(sequence);
								}
								else
								{
									event.setSequence(0);
								} 
						  }
						  catch(Exception ee)
						  {
							  ee.printStackTrace();
						  }
						if(componentevent.getLocation()!=null)
						event.setLocation(componentevent.getLocation().getValue());
						if(componentevent.getDescription()!=null)
						event.setDescription(componentevent.getDescription().getValue());
						try
						{
						if(componentevent.getClassification()!=null)
						event.setClazz(componentevent.getClassification().getValue());
						}
						catch(Exception ee)
						{
							ee.printStackTrace();
						}
						if(componentevent.getTransparency() != null)
						{
							if(componentevent.getTransparency().equals(Transp.TRANSPARENT))
							{
								event.setFreebusy("free");
							}
							else
							{
								event.setFreebusy("busy");
							}
						}
						
						
						PropertyList list = componentevent.getProperties(Property.ATTENDEE);
						String guests = "";
						for(Object obj : list)
						{
							if (obj instanceof Attendee) 
							{
								Attendee guest = (Attendee) obj;
								String name = guest.getCalAddress().toString();
								name = name.replace("mailto:", "");
								name = name.replace("MAILTO:", "");
								String status ="";
								try
								{
								status=	guest.getParameter(Parameter.PARTSTAT).getValue();
								}
								catch(Exception ee)
								{
									ee.printStackTrace();
								}
								if(status.equals("ACCEPTED"))
								{
									guests += ",a`"+name;
								}
								else if(status.equals("DECLINED"))
								{
									guests += ",d`"+name;
								}
								else if(status.equals("TENTATIVE"))
								{
									guests += ",t`"+name;
								}
								else if(status.equals("IN_PROCESS"))
								{
									guests += ",i`"+name;
								}
								else
								{
									guests += ",n`"+name;
								}
								//System.out.println("Attendee Status : " + guest.getParameters(Parameter.PARTSTAT));
							}
						}
						event.setOldguest(guests);
						
				       RRule rrule=(RRule)componentevent.getProperty("RRULE");
				       if(rrule!=null)
				       {
				       Recur recur=rrule.getRecur();
				       if(recur.getCount()>0)
				       {
				    	   event.setCount(recur.getCount());
				       }
				       else if(recur.getUntil() != null)
				       {
				    	   gregoriancalendar.setTime(recur.getUntil());
							try {
								event.setUntil( DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
							} catch (DatatypeConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
				    	  // event.setUntil(recur.getUntil().toString());
				       }
				       else
				       {
				    	   
				       }
				       
				       event.setFrequency(recur.getFrequency());
				       event.setInterval(recur.getInterval()); 
				       if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
				       {
				    	   DateTimeList dt=new DateTimeList();
				       for(Iterator<WeekDay> itw =recur.getDayList().iterator(); itw.hasNext();)
				    	   dt.getDateTime().add(itw.next().toString());
				    	   event.setRepeatdatetimelist(dt);
				       }
				       if(recur.getFrequency().equalsIgnoreCase(recur.MONTHLY))
				    	   System.out.println();//////////////////////////////////////////////
				       event.setRepeatStatus("Yes");
				       }
				       
				       if(componentevent.getRecurrenceId()!=null)
				    	   event.setRecurrenceEventID(componentevent.getRecurrenceId().getValue());
				       
						event.setReminderdata(""); 
			           for( Iterator<VAlarm> iterator=componentevent.getAlarms().iterator();iterator.hasNext();)
			           {
			        	   VAlarm alarm=(VAlarm)iterator.next();
			        	   //String action=alarm.getAction().getValue();
			        	   if(alarm.getAction().getValue().equals("DISPLAY"))
			        	   {
			        		   event.setReminderdata(event.getReminderdata()+"Pop-up"+"`");
			        	   }
			        	   else if (alarm.getAction().getValue().equals("EMAIL")) {
			        		   event.setReminderdata(event.getReminderdata()+"Email"+"`");
			        	   }
			        	   
			        	   Dur duration=new Dur(alarm.getTrigger().getValue());
			        	  
			        	// System.out.println((triggerduration.substring(1, triggerduration.indexOf("D")).equals("0")));
			        	   if (duration.getWeeks()!=0)
			        	   {
			        		   event.setReminderdata(event.getReminderdata()+duration.getWeeks()+"`"+"W"+";");
			        	   }
			        	   else  if(duration.getHours()!=0)
			               {
			        		  
			        		   event.setReminderdata(event.getReminderdata()+duration.getHours()+"`"+"H"+";");
			        	   }
			        	   else if (duration.getDays()!=0) {
			        		   event.setReminderdata(event.getReminderdata()+duration.getDays()+"`"+"D"+";");
			        	   }
			        	   else if (duration.getMinutes()!=0) {
			        		   event.setReminderdata(event.getReminderdata()+duration.getMinutes()+"`"+"M"+";");
			        	   }
			           }
			           System.out.println(event.getReminderdata());
			           if(repeatStatus!=null && repeatStatus.equalsIgnoreCase("Yes") && event.getRepeatStatus()!=null && event.getRepeatStatus().equalsIgnoreCase("Yes"))
			           {
			        	  
			        		   break;
			           }
			           else if(repeatStatus==null || repeatStatus.length()<=0)
			           {
			        	   if(recurrenceID!=null && recurrenceID.length()>0 && componentevent.getRecurrenceId()!=null && componentevent.getRecurrenceId().getValue().equalsIgnoreCase(recurrenceID))
			        	   {
			        		   break;
			        	   }
			           }
					}
					
				 
				 
				 
				} catch (ObjectStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ObjectNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try{
					//store.disconnect();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			}
			return event;
		}
		
		public CalDavCalendarCollection  createCal(String proid, String surl, String id ,String pass,String calnm, String calClr)
		{
			
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			CalDavCalendarCollection coll=null;
			/*String cnttyp[]={"VEVENT", "VTODO", "VJOURNAL", "VTIMEZONE", "VFREEBUSY", "VPOLL", "VAVAILABILITY"};
			
			
			Calendar newcal = new Calendar();
			newcal.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
			newcal.getProperties().add(Version.VERSION_2_0);
			newcal.getProperties().add(CalScale.GREGORIAN);
			VTimeZone vtz=new VTimeZone();
			try
			{
				net.fortuna.ical4j.model.property.TzId tz=new net.fortuna.ical4j.model.property.TzId("India Standard Time");
				vtz.getProperties().add(tz);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			Standard std=new Standard();
			try {
				std.getProperties().add(new DtStart("16010101T000000"));
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			std.getProperties().add(new TzOffsetFrom("+0530"));
			std.getProperties().add(new TzOffsetTo("+0530"));
			
			try
			{
				vtz.getObservances().add(std);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			newcal.getComponents().add(vtz);*/
			
			try {
				String uidnm= UUID.randomUUID().toString();
//				coll=store.addCollection(surl+"/caldav.php/"+id+"/"+uidnm, calnm, "This new calendar from caldav", null , newcal);
				
				// DavProperty property=
					/*	 ICalPropertyName ic=new ICalPropertyName();
						ic.CALENDAR_COLOR;*/
				// DavProperty<?> writeForProperty =DavProperty.
				DavPropertySet dps=new DavPropertySet();
				dps.add(new DefaultDavProperty(DavPropertyName.DISPLAYNAME, calnm));
				dps.add(new DefaultDavProperty(CalDavPropertyName.CALENDAR_DESCRIPTION, ""));
				DavPropertyName  dp = DavPropertyName.create(CalDavConstants.PROPERTY_CALENDAR_COLOR, CalDavConstants.ICAL_NAMESPACE);
				dps.add(new DefaultDavProperty(dp, calClr));
				
				coll=store.addCollection(surl+"/caldav.php/"+id+"/"+uidnm, dps);
				
				//store.addCollection(uidnm, properties)*/
			} catch (ObjectStoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try{
				//store.disconnect();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			/*try {
				store.addCollection(id, calnm, calClr, null, null);
			} catch (ObjectStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			//String url=surl+"/caldav.php/nirbhay@silvereye.in/130f4104-86eb-423b-9d5b-6878dbad3fda/";
			//npCalUpdate(url, id,  pass);
			return coll;
		}
		
		public CalDavCalendarCollection  UpdateCal(String proid, String surl, String id ,String pass,String calnm, String calClr)
		{
			
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			CalDavCalendarCollection coll= null;
						
			try {
				String uidnm=UUID.randomUUID().toString();
//				coll=store.addCollection(surl+"/caldav.php/"+id+"/"+uidnm, calnm, "This new calendar from caldav", null , newcal);
				
				// DavProperty property=
					/*	 ICalPropertyName ic=new ICalPropertyName();
						ic.CALENDAR_COLOR;*/
				// DavProperty<?> writeForProperty =DavProperty.
				DavPropertySet dps=new DavPropertySet();
				dps.add(new DefaultDavProperty(DavPropertyName.DISPLAYNAME, "nps"));
				dps.add(new DefaultDavProperty(CalDavPropertyName.CALENDAR_DESCRIPTION, ""));
				DavPropertyName  dp = DavPropertyName.create(CalDavConstants.PROPERTY_CALENDAR_COLOR, CalDavConstants.ICAL_NAMESPACE);
				dps.add(new DefaultDavProperty(dp, "#000000"));
				
				coll=store.addCollection("https://mail.silvereye.in:9443/caldav.php/nirbhay%40silvereye.in/23f09a9b-788a-4e48-a513-47dcfe6977f2", dps);
				//store.addCollection(uidnm, properties)*/
			} catch (ObjectStoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try{
				//store.disconnect();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			/*try {
				store.addCollection(id, calnm, calClr, null, null);
			} catch (ObjectStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			return coll;
		}
		
		public List<CalDavCalendarCollection> findAllCollections(String proid, String surl, String id ,String pass)
		{
			List<CalDavCalendarCollection> colls=null;
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			if(store!=null)
			{
				try {
					colls = store.getCollections();
				} catch (ObjectStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ObjectNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try{
					//store.disconnect();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
			return colls;
		}
		
		
		public List<CalDavCalendarCollection> findAllSharedCollections(String proid, String surl, String id ,String pass)
		{
			List<CalDavCalendarCollection> colls=null;
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			
			if(store!=null)
			{
				try {
					colls = store.getDelegatedCollections();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
			return colls;
		}
		
		
		public boolean deleteSelTask(String proid, String surl, String id ,String pass,String calnm, String uid)
		{
			boolean st=true;
			
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			
			if(store!=null)
			{
				try {
					store.getCollection(calnm).removeCalendar(uid);
				} catch (FailedOperationException e) {
					// TODO Auto-generated catch block
					st=false;
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					st=false;
					e.printStackTrace();
				}
			}
			return st;
		}
		
		public GetUpdateTaskResponse updateTask(String proid, String surl, String id ,String pass,String calnm,TaskBean taskBean )
		{
			GetUpdateTaskResponse response = new GetUpdateTaskResponse();
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			if(store!=null)
			{
			Calendar cal = new Calendar();
			cal.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
			cal.getProperties().add(Version.VERSION_2_0);
			cal.getProperties().add(CalScale.GREGORIAN);
			Dur dur=null;
			
			
					String uid=taskBean.getUid();
					VToDo  task = new VToDo();
					task.getProperties().add(new Uid(uid));
					task.getProperties().add(new DtStart(new net.fortuna.ical4j.model.DateTime(taskBean.getStartdate().toGregorianCalendar().getTime())));
					task.getProperties().add(new Due(new net.fortuna.ical4j.model.DateTime(taskBean.getDuedate().toGregorianCalendar().getTime())));
					task.getProperties().add(new Summary(taskBean.getDetail()));
					task.getProperties().add(new Description(taskBean.getTaskdesc()));
					task.getProperties().add(new Priority(taskBean.getPriority()));
					task.getProperties().add(new PercentComplete(taskBean.getProgress()));
					task.getProperties().add(new Status(taskBean.getStatus()));
					
					
					if(taskBean.getFrequency()!=null && !taskBean.getFrequency().equalsIgnoreCase("no"))
					{
						
						Recur recur=null;
						if(taskBean.getCount()!=null)
						{
							recur=new Recur(taskBean.getFrequency(),taskBean.getCount());
							recur.setInterval(taskBean.getInterval());
							if(recur.getFrequency().equals("YEARLY"))
							{
								
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
							}
							else if(recur.getFrequency().equals("MONTHLY"))
							{
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
							}
							else
							{
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
							}
						}
						else if(taskBean.getUntil()!=null)
						{
							net.fortuna.ical4j.model.Date dt = new net.fortuna.ical4j.model.Date(taskBean.getUntil().toGregorianCalendar().getTime());
							dt.setDate(dt.getDate()+1);
							recur=new Recur(taskBean.getFrequency(),new net.fortuna.ical4j.model.Date(dt));
							dur=new Dur(task.getStartDate().getDate(),recur.getUntil());
						}
						else
						{
							recur = new Recur(taskBean.getFrequency(),null);
							if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
							{
								List<String> days=taskBean.getRepeatdatetimelist().getDateTime();
								for(String day:days)
									recur.getDayList().add(new WeekDay(day));
							}
							recur.setInterval(taskBean.getInterval());
							task.getProperties().add(new RRule(recur));
							
							Date dt  =new net.fortuna.ical4j.model.Date(taskBean.getDuedate().toGregorianCalendar().getTime());
							
							dur=new Dur(task.getStartDate().getDate(),dt);
							response.setRepeatdates(teskrepeatdate(task,task.getStartDate(), dur));
						}
						
						
						//if(taskBean.getCount()!=null || taskBean.getUntil()!=null)
						{
							// for( Iterator<String> i=eventbean.getRepeatdatetimelist().getDateTime().iterator();i.hasNext();)
							if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
							{
								if(taskBean.getRepeatdatetimelist()!=null)
								{
								List<String> days=taskBean.getRepeatdatetimelist().getDateTime();
								for(String day:days)
									recur.getDayList().add(new WeekDay(day));
								}
							}
							recur.setInterval(taskBean.getInterval());
							/*if(recur.getFrequency().equalsIgnoreCase(recur.MONTHLY))
							recur.set*/
							task.getProperties().add(new RRule(recur));
							///////////method testing
							response.setRepeatdates(teskrepeatdate(task,task.getStartDate(), dur));
						}
						try {
							task.getProperties().add(new Organizer("Silvereye"));
						} catch (URISyntaxException e) {
							e.printStackTrace();
						}
						
					}
					
					
					
					
					if(taskBean.getReminderdata()!=null && !taskBean.getReminderdata().equals(""))
					{
						String[] reminderarray = taskBean.getReminderdata().split(";");
						for (String reminderdata : reminderarray) 
						{
							String[] reminders = reminderdata.split("`");
							VAlarm alarm = new VAlarm();
							String triggerduration=null;
							if(reminders[2].equals("W") || reminders[2].equals("D"))
								triggerduration="P"+reminders[1]+reminders[2];
							else if (reminders[2].equals("M") || reminders[2].equals("H"))
								triggerduration="PT"+reminders[1]+reminders[2];
							if (reminders[0].equalsIgnoreCase("Pop-up")) {
								alarm.getProperties().add(Action.DISPLAY);	                  
							}
							else if (reminders[0].equalsIgnoreCase("Email"))
							{
								alarm.getProperties().add(Action.EMAIL);
								alarm.getProperties().add(new Summary("Alarm Notification"));
								try {
									alarm.getProperties().add(new Attendee("mailto:"+id));
								} catch (URISyntaxException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							alarm.getProperties().add(new Description("Event Reminder"));
							Dur duration = new Dur(triggerduration);
							alarm.getProperties().add(new Trigger(duration));
							task.getAlarms().add(alarm);
						}
					}
					
					
					
					
					
					
					
					
					
					/*String property = "MODIFYDATE";
					Property prop = task.getProperties().getProperty(property);
					if(prop != null)
					{
						task.getProperties().remove(prop);
					}
					Date date = new Date();
					SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
					prop=new XProperty(property,dateFormat.format(date));*/
					//task.getProperties().add(prop);
					task.getProperties().add(new LastModified(new net.fortuna.ical4j.model.DateTime(new Date())));
					//task.getProperties().add(new Status(taskBean.getStatus()));
					cal.getComponents().add(task);
					
					boolean st=true;
					
						
						try
						{
							/*CalDavCalendarCollection caldc= store.getCollection(calnm);
							caldc.removeCalendar(uid);
							caldc.addCalendar(cal);*/
				
							String url = calnm+uid+".ics";
							String bd=cal.toString();
							npEventUpdate(bd,url,id, pass);
							
						}
						catch(Exception ee)
						{
							ee.printStackTrace();
						}
			
			response.setUpdateSuccess(true);
			
			}
			return response;
		}
		
		
		public TaskArray getAllTasks(String proid, String surl, String id ,String pass,String calnm)
		{
			
			TaskArray tasklist = new TaskArray();
			
			
			
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			
			if(store!=null)
			{
				try
				{
				CalDavCalendarCollection	coll=	store.getCollection(calnm);
			for(Calendar cal : coll.getTasks())
				{
				for (Iterator<VToDo> it = cal.getComponents("VTODO").iterator(); it.hasNext();)
				{
				TaskBean taskBean = new TaskBean();
				VToDo  task = (VToDo)it.next();
				
				taskBean.setDetail(task.getSummary().getValue());
				
				try
				{
					GregorianCalendar gregoriancalendar=new GregorianCalendar();
					if(task.getStartDate() != null)
					{
						gregoriancalendar.setTime(task.getStartDate().getDate());
						taskBean.setStartdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					}
					DtEnd end=(DtEnd)task.getProperty(Property.DTEND);

					if(end != null)
					{
						gregoriancalendar.setTime(end.getDate());
						taskBean.setEnddate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					}
					
					Due due=(Due)task.getProperty(Property.DUE);

					if(due != null)
					{
						gregoriancalendar.setTime(due.getDate());
						taskBean.setDuedate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					}
					
					
					LastModified lstmod=(LastModified)task.getProperty(Property.LAST_MODIFIED);
					if(lstmod != null)
					{
						gregoriancalendar.setTime(lstmod.getDate());
						taskBean.setModifydate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					}
					
					if(task.getPercentComplete()!=null)
					{
					taskBean.setProgress(task.getPercentComplete().getPercentage());
					}
					else
					{
						taskBean.setProgress(0);
					}
					RRule rrule=(RRule)task.getProperty("RRULE");
					if(rrule != null)
					{
						
						Recur recur=rrule.getRecur();
						
						Dur dur=null;
						if(recur.getCount()>0)
						{
							if(recur.getFrequency().equals("YEARLY"))
							{
								
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
							}
							else if(recur.getFrequency().equals("MONTHLY"))
							{
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
							}
							else
							{
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
							}
						 
						}
						else if(recur.getUntil() != null)
						{
							dur=new Dur(task.getStartDate().getDate(),recur.getUntil());
						}
						else
						{
							Date dt  =new Date(task.getStartDate().getDate().getTime());
							dt.setYear(dt.getYear() +1);
							dur=new Dur(task.getStartDate().getDate(),dt);
						}

						taskBean.setRepeatdatetimelist(teskrepeatdate(task,task.getStartDate(), dur));
					}
					
					
				}
				catch(DatatypeConfigurationException e)
				{
					e.printStackTrace();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
				taskBean.setUid(task.getUid().getValue());
				if(task.getStatus()!=null)
				taskBean.setStatus(task.getStatus().getValue());
				if(task.getDescription()!=null)
				taskBean.setTaskdesc(task.getDescription().getValue());
				
				try 
				{
					taskBean.setPriority(Integer.parseInt(task.getPriority().getValue()));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				tasklist.getTaskList().add(taskBean);
	        }
				}
				}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				
			}
			return tasklist;
		}
		
		
		public GetTaskDetailResponse getTaskDetail(String proid, String surl, String id ,String pass,String calnm,String eventuid )
		{
			GetTaskDetailResponse response = new GetTaskDetailResponse();
			Calendar calendar=null;
			 EventBean event= new EventBean();
			if(eventuid!=null)
			{
			 CalDavCalendarStore store=connect(proid, surl, id, pass);
			
			if(store!=null)
			{
				try {
					calendar=	store.getCollection(calnm).getCalendar(eventuid);
				 
			TaskBean taskBean = null;
			for (Iterator<VToDo> it = calendar.getComponents("VTODO").iterator(); it.hasNext();)
	        {
				VToDo  task = (VToDo)it.next();
				
					taskBean = new TaskBean();
					taskBean.setDetail(task.getSummary().getValue());
					
					try
					{
						GregorianCalendar gregoriancalendar=new GregorianCalendar();
						if(task.getStartDate() != null)
						{
							gregoriancalendar.setTime(task.getStartDate().getDate());
							taskBean.setStartdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
						}
						DtEnd end=(DtEnd)task.getProperty(Property.DTEND);

						if(end != null)
						{
							gregoriancalendar.setTime(end.getDate());
							taskBean.setEnddate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
						}
						
						Due due=(Due)task.getProperty(Property.DUE);

						if(due != null)
						{
							gregoriancalendar.setTime(due.getDate());
							taskBean.setDuedate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
						}
						if(task.getLastModified() != null)
						{
							gregoriancalendar.setTime(task.getLastModified().getDate());
							taskBean.setModifydate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
						}
					}
					catch(DatatypeConfigurationException e)
					{
						e.printStackTrace();
					}
					
					
					taskBean.setUid(task.getUid().getValue());
					if(task.getStatus()!=null)
					{
					taskBean.setStatus(task.getStatus().getValue());
					}
					else
					{
						taskBean.setStatus("0");
					}
					
					if(task.getDescription()!=null)
						taskBean.setTaskdesc(task.getDescription().getValue());
					if(task.getPriority()!=null)
					{
					taskBean.setPriority(Integer.parseInt(task.getPriority().getValue()));
					}
					else
					{
						taskBean.setPriority(5);
					}
					if(task.getPercentComplete()!=null)
					{
					taskBean.setProgress(Integer.parseInt(task.getPercentComplete().getValue()));
					}
					else
					{
						taskBean.setProgress(0);
					}
					
					GregorianCalendar gregoriancalendar=new GregorianCalendar();
					
					RRule rrule=(RRule)task.getProperty("RRULE");
				    if(rrule!=null)
			        {
					       Recur recur=rrule.getRecur();
					       if(recur.getCount()>0)
					       {
					    	   taskBean.setCount(recur.getCount());
					       }
					       else if(recur.getUntil() != null)
					       {
					    	   gregoriancalendar.setTime(recur.getUntil());
								try 
								{
									taskBean.setUntil( DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
								} 
								catch (DatatypeConfigurationException e) 
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
					    	  // event.setUntil(recur.getUntil().toString());
					       }
					       else
					       {
					    	   
					       }
					       
					       taskBean.setFrequency(recur.getFrequency());
					       taskBean.setInterval(recur.getInterval()); 
					       if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
					       {
					    	   DateTimeTaskList dt=new DateTimeTaskList();
					       for(Iterator<WeekDay> itw =recur.getDayList().iterator(); itw.hasNext();)
					    	   dt.getDateTime().add(itw.next().toString());
					       taskBean.setRepeatdatetimelist(dt);
					       }
					       if(recur.getFrequency().equalsIgnoreCase(recur.MONTHLY))
					    	   System.out.println();//////////////////////////////////////////////
		        	}  
				    else
				    {
				    	taskBean.setFrequency("no");
				    }
				    
				    
				    
				    /*if(rrule != null)
					{
						
						Recur recur=rrule.getRecur();
						try {
							
							//String until=recur.getUntil();
							
							if(recur.getCount()!=0 || recur.getUntil()==null)
							{
								Dur dur=null;
								if(recur.getCount()>0)
								{
									if(recur.getFrequency().equals("YEARLY"))
									{
										
										dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
									}
									else if(recur.getFrequency().equals("MONTHLY"))
									{
										dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
									}
									else
									{
										dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
									}
								 
								}
								else if(recur.getUntil() != null)
								{
									dur=new Dur(task.getStartDate().getDate(),recur.getUntil());
								}
								else
								{
									Date dt  =new Date(task.getStartDate().getDate().getTime());
									dt.setYear(dt.getYear() +1);
									dur=new Dur(task.getStartDate().getDate(),dt);
								}
								//DateTime stdate=new DateTime(component.getStartDate().getValue());
								
								///////////method testing
//								taskBean.setRepeatdatetimelist(teskrepeatdate(task,task.getStartDate(), dur));
							
								
						eventbean.setRepeatdatetimelist(dtl);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				    
				    */
				    
				    
				    
				    
				    
				    taskBean.setReminderdata(""); 
		           for( Iterator<VAlarm> iterator=task.getAlarms().iterator();iterator.hasNext();)
		           {
		        	   VAlarm alarm=(VAlarm)iterator.next();
		        	   //String action=alarm.getAction().getValue();
		        	   if(alarm.getAction().getValue().equals("DISPLAY"))
		        	   {
		        		   taskBean.setReminderdata(taskBean.getReminderdata()+"Pop-up"+"`");
		        	   }
		        	   else if (alarm.getAction().getValue().equals("EMAIL")) {
		        		   taskBean.setReminderdata(taskBean.getReminderdata()+"Email"+"`");
		        	   }
		        	   
		        	   Dur duration=new Dur(alarm.getTrigger().getValue());
		        	  
		        	// System.out.println((triggerduration.substring(1, triggerduration.indexOf("D")).equals("0")));
		        	   if (duration.getWeeks()!=0)
		        	   {
		        		   taskBean.setReminderdata(taskBean.getReminderdata()+duration.getWeeks()+"`"+"W"+";");
		        	   }
		        	   else  if(duration.getHours()!=0)
		               {
		        		  
		        		   taskBean.setReminderdata(taskBean.getReminderdata()+duration.getHours()+"`"+"H"+";");
		        	   }
		        	   else if (duration.getDays()!=0) {
		        		   taskBean.setReminderdata(taskBean.getReminderdata()+duration.getDays()+"`"+"D"+";");
		        	   }
		        	   else if (duration.getMinutes()!=0) {
		        		   taskBean.setReminderdata(taskBean.getReminderdata()+duration.getMinutes()+"`"+"M"+";");
		        	   }
		           }
		           System.out.println(taskBean.getReminderdata());
					
				    
				    
					response.getTaskList().add(taskBean);
					
					
					
					break;
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			
			}
	        }
			return response;
		}
		
		public GetAddTaskResponse createTask(String proid, String surl, String id ,String pass,String calnm, TaskBean taskBean)
		{
			GetAddTaskResponse response = new GetAddTaskResponse();
			
			CalDavCalendarStore store=connect(proid, surl, id, pass);
			if(store!=null)
			{
			Calendar cal = new Calendar();
			cal.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
			cal.getProperties().add(Version.VERSION_2_0);
			cal.getProperties().add(CalScale.GREGORIAN);
			VToDo task = new VToDo();
			
			Dur dur=null;
			
			
			
			
			task.getProperties().add(new DtStart(new net.fortuna.ical4j.model.DateTime(taskBean.getStartdate().toGregorianCalendar().getTime())));
			if(taskBean.getDuedate()!=null)
			task.getProperties().add(new Due(new net.fortuna.ical4j.model.DateTime(taskBean.getDuedate().toGregorianCalendar().getTime())));
			task.getProperties().add(new Summary(taskBean.getDetail()));
			task.getProperties().add(new Description(taskBean.getTaskdesc()));
			task.getProperties().add(new Priority(taskBean.getPriority()));
			task.getProperties().add(new PercentComplete(taskBean.getProgress()));
			
			String uid =UUID.randomUUID().toString();
			task.getProperties().add(new Uid(uid));
			task.getProperties().add(new Status(taskBean.getStatus()));
			
			
			
			if(taskBean.getFrequency()!=null && !taskBean.getFrequency().equalsIgnoreCase("no"))
			{
				
				Recur recur=null;
				if(taskBean.getCount()!=null)
				{
					recur=new Recur(taskBean.getFrequency(),taskBean.getCount());
					recur.setInterval(taskBean.getInterval());
					if(recur.getFrequency().equals("YEARLY"))
					{
						
						dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
					}
					else if(recur.getFrequency().equals("MONTHLY"))
					{
						dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
					}
					else
					{
						dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
					}
				}
				else if(taskBean.getUntil()!=null)
				{
					net.fortuna.ical4j.model.Date dt = new net.fortuna.ical4j.model.Date(taskBean.getUntil().toGregorianCalendar().getTime());
					dt.setDate(dt.getDate()+1);
					recur=new Recur(taskBean.getFrequency(),new net.fortuna.ical4j.model.Date(dt));
					dur=new Dur(task.getStartDate().getDate(),recur.getUntil());
				}
				else
				{
					recur = new Recur(taskBean.getFrequency(),null);
					if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
					{
						List<String> days=taskBean.getRepeatdatetimelist().getDateTime();
						for(String day:days)
							recur.getDayList().add(new WeekDay(day));
					}
					recur.setInterval(taskBean.getInterval());
					task.getProperties().add(new RRule(recur));
					
					if(taskBean.getDuedate()!=null)
					{
					Date dt  =new net.fortuna.ical4j.model.Date(taskBean.getDuedate().toGregorianCalendar().getTime());
					
					dur=new Dur(task.getStartDate().getDate(),dt);
					response.setRepeatdates(teskrepeatdate(task,task.getStartDate(), dur));
					}
				}
				
			//this cmnt for testing	
				if(taskBean.getCount()!=null || taskBean.getUntil()!=null)
				{
					// for( Iterator<String> i=eventbean.getRepeatdatetimelist().getDateTime().iterator();i.hasNext();)
					if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
					{
						if(taskBean.getRepeatdatetimelist()!=null)
						{
						List<String> days=taskBean.getRepeatdatetimelist().getDateTime();
						for(String day:days)
							recur.getDayList().add(new WeekDay(day));
						}
					}
					recur.setInterval(taskBean.getInterval());
					/*if(recur.getFrequency().equalsIgnoreCase(recur.MONTHLY))
					recur.set*/
					task.getProperties().add(new RRule(recur));
					///////////method testing
					response.setRepeatdates(teskrepeatdate(task,task.getStartDate(), dur));
				}
				try {
					task.getProperties().add(new Organizer("Sandesh"));
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				
			}
			
			
			
			
			if(taskBean.getReminderdata()!=null && !taskBean.getReminderdata().equals(""))
			{
				String[] reminderarray = taskBean.getReminderdata().split(";");
				for (String reminderdata : reminderarray)
					try {
						{
							String[] reminders = reminderdata.split("`");
							VAlarm alarm = new VAlarm();
							String triggerduration=null;
							if(reminders[2].equals("W") || reminders[2].equals("D"))
								triggerduration="P"+reminders[1]+reminders[2];
							else if (reminders[2].equals("M") || reminders[2].equals("H"))
								triggerduration="PT"+reminders[1]+reminders[2];
							if (reminders[0].equalsIgnoreCase("Pop-up")) {
								alarm.getProperties().add(Action.DISPLAY);	                  
							}
							else if (reminders[0].equalsIgnoreCase("Email"))
							{
								alarm.getProperties().add(Action.EMAIL);
								alarm.getProperties().add(new Summary("Alarm Notification"));
								try {
									alarm.getProperties().add(new Attendee("mailto:"+id));
								} catch (URISyntaxException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							alarm.getProperties().add(new Description("Event Reminder"));
							Dur duration = new Dur(triggerduration);
							alarm.getProperties().add(new Trigger(duration));
							task.getAlarms().add(alarm);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			
			cal.getComponents().add(task);
			
			try {
				CalDavCalendarCollection caldc= store.getCollection(calnm);
				caldc.addCalendar(cal);
			} 
			catch (ConstraintViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ObjectStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
			response.setUid(uid);
			}
			return response;
			
		}
		
		
		
		
		public DateTimeTaskList teskrepeatdate(VToDo tempevent, DtStart startdate, Dur dur)
		{
			DateTimeTaskList dtl=new DateTimeTaskList();
//			VEvent component=new VEvent();
			PeriodList pl=null;
			try
			{
				
				pl = tempevent.calculateRecurrenceSet(new Period(new DateTime(startdate.getValue()), dur));
				
			}
			catch (ParseException e1) 
			{
				e1.printStackTrace();
			}
			for (Iterator<Period> pit = pl.iterator(); pit.hasNext();)
			{
				Period period=(Period)pit.next();
				try 
				{
					dtl.getDateTime().add(period.getStart().toString()+"`"+period.getEnd().toString());
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		
			return dtl;
	   
		}
		
		
		public GetInviteEventResponse getInviteEventRequest(byte[] filecontect,String proid, String surl, String id ,String pass,String calnm, boolean isnotsame)
		{
			GetInviteEventResponse res = new GetInviteEventResponse();
			
			Calendar calFrom = new Calendar();
			InputStream inputstream=null;
			byte[] encodedImageFrom = org.apache.commons.codec.binary.Base64.decodeBase64(filecontect);
			CalendarBuilder calendarbuilder = new CalendarBuilder();
			try 
			{
				inputstream = new ByteArrayInputStream(encodedImageFrom);
				calFrom = calendarbuilder.build(inputstream);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			 EventArray eventlist = new EventArray();
			if(calFrom!=null)
			{
			
				 CalDavCalendarStore store=connect(proid, surl, id, pass);
				 try
				 {
				 CalDavCalendarCollection caldc= store.getCollection(calnm);
				 
				 for (Iterator<VEvent> it = calFrom.getComponents("VEVENT").iterator(); it.hasNext();)
			        {
						VEvent event = (VEvent) it.next();
						String eventuid=event.getUid().getValue();
						if(isnotsame)
						{
						 if(eventuid!=null)
						 {
							 Calendar calr=	caldc.getCalendar(eventuid);	
							 if(calr!=null)
							 {
								try
								{
									caldc.removeCalendar(eventuid);
									caldc.addCalendar(calFrom);
								 
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
							 }
							 else
							 {
								 try
									{

									 caldc.addCalendar(calFrom);
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
							 }
						 }
						 else
						 {
							 try
								{
								 		event.getProperties().add(new Uid(UUID.randomUUID().toString()));
										Calendar cal=new Calendar();
										cal.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
										cal.getProperties().add(Version.VERSION_2_0);
										cal.getProperties().add(CalScale.GREGORIAN);
										cal.getComponents().add(event);
										caldc.addCalendar(cal);
							       							 
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
						 }
						 
						}
						 
						 EventBean eventbean = new EventBean();
							eventbean.setSummary(event.getSummary().getValue());
							//date type changed
							if(event.getLocation().getValue()!=null)
							eventbean.setLocation(event.getLocation().getValue());
							try
							{
								GregorianCalendar gregoriancalendar=new GregorianCalendar();
								gregoriancalendar.setTime(event.getStartDate().getDate());
								if(event.getStartDate().getDate().toString().indexOf("T",0)<0)
									eventbean.setAllday("on");
								else
									eventbean.setAllday("off");
								eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
								gregoriancalendar.setTime(event.getEndDate().getDate());
								eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
								
							}
							catch(DatatypeConfigurationException e)
							{
								e.printStackTrace();
							}
							// date type changed end
							eventbean.setUid(event.getUid().getValue());
							PropertyList list = event.getProperties(Property.ATTENDEE);
							String guests = "";
							for(Object obj : list)
							{
								if (obj instanceof Attendee) 
								{
									Attendee guest = (Attendee) obj;
									String name = guest.getCalAddress().toString();
									name = name.replace("mailto:", "");
									name = name.replace("MAILTO:", "");
									String status ="";
									try
									{
										status=guest.getParameter(Parameter.PARTSTAT).getValue();
									}
									catch(Exception ee)
									{
										ee.printStackTrace();
									}
									
									if(status.equals("ACCEPTED"))
									{
										guests += ",a`"+name;
									}
									else if(status.equals("DECLINED"))
									{
										guests += ",d`"+name;
									}
									else if(status.equals("TENTATIVE"))
									{
										guests += ",t`"+name;
									}
									else if(status.equals("IN_PROCESS"))
									{
										guests += ",i`"+name;
									}
									else
									{
										guests += ",n`"+name;
									}
								//	System.out.println("Attendee Status : " + guest.getParameters(Parameter.PARTSTAT));
								}
							}
							eventbean.setOldguest(guests);
							
							eventlist.getEventList().add(eventbean);
							
				        }
			}
				 catch(Exception ee)
				 {
					 ee.printStackTrace();
				 }
			}
						
						res.setEventList(eventlist);
						try {
							inputstream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			return res;
		}
		
		public void connect(){
		
			try 
			{
				CalDavCalendarStore store = new CalDavCalendarStore("//davical.org//NONSGML AWL Calendar//EN", url, PathResolver.DAVICAL) ;
				store.connect("piyush@silvereye.in", "yahoo@2009".toCharArray());
		
				System.out.println(store.isSupportCalendarProxy());
/*		Iterate all calendars events  begin */
				/*List<CalDavCalendarCollection> colls = store.getCollections();
				for(CalDavCalendarCollection coll : colls)
				{
					if(coll.getDisplayName() != null && !coll.getDisplayName().contains("proxy"))
					{
						System.out.println("Color : "+coll.getColor());
						
						for(Calendar cal : coll.getEvents())
						{
							
							for (Iterator<VEvent> it = cal.getComponents("VEVENT").iterator(); it.hasNext();)
							{
								VEvent event = (VEvent)it.next();
								System.out.println("out : " + event.getStartDate());
								System.out.println("out : " + event.getSummary());
							}
						}
						
					}
				
				}*/
/*		Iterate all calendars events end */
				
/*		Iterate default calendar events begin */
				CalDavCalendarCollection callection =  store.getCollection(url+PathResolver.DAVICAL.getPrincipalPath("piyush@silvereye.in")+"/default");

				if(callection != null)
				{
					
					Calendar calList[] = callection.getEvents();
					for(Calendar cal :calList)
					{
						for (Iterator<VEvent> it = cal.getComponents("VEVENT").iterator(); it.hasNext();)
						{
							VEvent event = (VEvent)it.next();
							System.out.println("out : " + event.getStartDate());
						}
					}
				}
				
/*		Iterate default calendar events end */
					
/*		create calendar object begin */

					Calendar newcal = new Calendar();
					newcal.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
					newcal.getProperties().add(Version.VERSION_2_0);
					newcal.getProperties().add(CalScale.GREGORIAN);
					
/*					create calendar object end  */
					
/*					Create New Event begin           */
					VEvent newEvent = new VEvent(new DateTime(), new DateTime(), "This is new event");
					newEvent.getProperties().add(new Uid(UUID.randomUUID().toString()));
					newcal.getComponents().add(newEvent);
					//newEvent.
/*					Create New Event end           */
					
/*					Create New Task begin           */
					/*VToDo task = new VToDo(new DateTime(), "New Task");
					task.getProperties().add(new Uid(UUID.randomUUID().toString()));
					newcal.getComponents().add(task);*/
					callection.writeCalendarOnServer(newcal, true);

/*					Create New Task end           */
					
					
//					Create new Calendar Begin
					
					/*VTimeZone vtz=new VTimeZone();
					try
					{
						net.fortuna.ical4j.model.property.TzId tz=new net.fortuna.ical4j.model.property.TzId("India Standard Time");
						vtz.getProperties().add(tz);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					Standard std=new Standard();
					std.getProperties().add(new DtStart("16010101T000000"));
					std.getProperties().add(new TzOffsetFrom("+0530"));
					std.getProperties().add(new TzOffsetTo("+0530"));
					
					try
					{
						vtz.getObservances().add(std);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					newcal.getComponents().add(vtz);
					
					store.addCollection("http://caldav.silvereye.in:81/caldav.php/piyush@silvereye.in/birthdays", "new Calendar", "This new calendar from caldav", col.getSupportedComponentTypes(), newcal);
					*/
//		Create new Calendar end

//				}
				
				
				
				
	  		}
			
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
}
