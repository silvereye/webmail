package commons;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;



import webmail.wsdl.DateTimeList;
import webmail.wsdl.EventBean;;

public class JsonHelper {
public static EventBean parseJSON(String eventdetails) throws JsonParseException, IOException
{
	EventBean event=new EventBean();
	
		//org.codehaus.jackson.JsonParser jsonParser = new JsonFactory().createJsonParser(eventdetails);
	
	//create ObjectMapper instance
	ObjectMapper objectMapper = new ObjectMapper();
	
	//read JSON like DOM Parser
	JsonNode rootNode = objectMapper.readTree(eventdetails);
	 event.setSummary(rootNode.path("summary").getTextValue());
	 event.setLocation(rootNode.path("location").getTextValue());
	 event.setCalendar(rootNode.path("calendar").getTextValue());
	 
	 //date conversion
	  
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'h:mma");
		java.util.Date date;
		try {
			if(rootNode.path("starttime").getTextValue().equals(""))
				((ObjectNode) rootNode).put("starttime", "0:00am");
			date = df.parse(rootNode.path("startdate").getTextValue()+"T"+rootNode.path("starttime").getTextValue());
			GregorianCalendar gregoriancalendar=new GregorianCalendar();
			gregoriancalendar.setTime(date);
			XMLGregorianCalendar xmlgregoriancalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar);
			event.setStarteventdate(xmlgregoriancalendar);
			if(rootNode.path("endtime").getTextValue().equals(""))
			((ObjectNode) rootNode).put("endtime", "0:00am");
			date=df.parse(rootNode.path("enddate").getTextValue()+"T"+rootNode.path("endtime").getTextValue());
		    gregoriancalendar.setTime(date);
		    XMLGregorianCalendar xmlgregoriancalendar1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar);
		    event.setEndeventdate(xmlgregoriancalendar1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//date conversion end
	 
	 //event.setSummary(rootNode.path("enddate").getTextValue());
		if(rootNode.path("allday").getTextValue()==null)
			event.setAllday("off");
		else
			 event.setAllday("on");
	 event.setUid(rootNode.path("uid").getTextValue());
	 event.setClazz(rootNode.path("clazz").getTextValue());
	 event.setFreebusy(rootNode.path("freebusy").getTextValue());
	 event.setDescription(rootNode.path("description").getTextValue());
	 event.setReminderdata(rootNode.path("reminderdata").getTextValue());
	 event.setOldguest(rootNode.path("oldguest").getTextValue());
	 event.setNewguest(rootNode.path("newguest").getTextValue());
	 event.setExdateList(rootNode.path("exdate").getTextValue());
	 String frequency = rootNode.path("frequency").getTextValue();
	 if(frequency != "")
	 {
		 event.setFrequency(frequency);
		 if(!frequency.equals("no"))
		 {
			 if(rootNode.path("interval").getTextValue()!="")
			 {
				 event.setInterval(Integer.parseInt(rootNode.path("interval").getTextValue()));
			 }
			 if(rootNode.path("count").getTextValue()!="")
			 {
				 String count = rootNode.path("count").getTextValue();
				 if(count.equals("never"))
				 {
//					 event.setCount(100);
				 }
				 else if(count.equals("count"))
				 {
					 String count_input =  rootNode.path("count_input").getTextValue();
					 if(count_input != "")
					 {
						 event.setCount(Integer.parseInt(count_input));
					 }
					 else
					 {
						 event.setCount(1);
					 }
				 }
				 else if(count.equals("until"))
				 {
					 try 
					 {
						 DateFormat df1=new SimpleDateFormat("yyyy-MM-dd");
						 date=df1.parse(rootNode.path("until_input").getTextValue());
						 GregorianCalendar gregoriancalendar=new GregorianCalendar();
						 gregoriancalendar.setTime(date);
						 XMLGregorianCalendar xmlgregoriancalendar2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar);
						 event.setUntil(xmlgregoriancalendar2);
					 }
					 catch (ParseException e) 
					 {
						 // TODO Auto-generated catch block
						 e.printStackTrace();
					 }
					 catch (DatatypeConfigurationException e) {
						 // TODO Auto-generated catch block
						 e.printStackTrace();
					 }
				 }
				 
			 }
			 
			 if(frequency.equals("DAILY"))
			 {
				 
			 }
			 else if(frequency.equals("WEEKLY"))
			 {
				 DateTimeList rdays=new DateTimeList();
				 if(rootNode.path("daysofweek").getTextValue()!=null)
				 {
					 
					 String days[]=rootNode.path("daysofweek").getTextValue().split("`");
					 for(String day:days)
						 rdays.getDateTime().add(day);
					 
				 }
				 event.setRepeatdatetimelist(rdays);
				 
			 }
			 else if(frequency.equals("MONTHLY"))
			 {
				 
			 }
			 else if(frequency.equals("YEARLY"))
			 {
				 
			 }
		 }
		 
	 }
	 
	 
	 
	 
	 
	
	 	
	return event;
}
}
