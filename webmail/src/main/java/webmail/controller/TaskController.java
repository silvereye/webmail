package webmail.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




import org.springframework.web.servlet.ModelAndView;

import webmail.bean.CalDav;
import webmail.bean.NPCompare;
import webmail.bean.TaskCompare;
import webmail.webservice.client.CalendarClient;
import webmail.webservice.client.FileClient;
import webmail.webservice.client.WebmailClient;
import webmail.wsdl.CreateCalendarResponse;
import webmail.wsdl.CreateFileResponse;
import webmail.wsdl.DateTimeList;
import webmail.wsdl.DateTimeTaskList;
import webmail.wsdl.DeleteSelectedTaskResponse;
import webmail.wsdl.DeleteTaskResponse;
import webmail.wsdl.EditFileResponse;
import webmail.wsdl.GetAddTaskResponse;
import webmail.wsdl.GetFileByPathResponse;
import webmail.wsdl.GetTaskDetailResponse;
import webmail.wsdl.GetTasksResponse;
import webmail.wsdl.GetUpdateTaskResponse;
import webmail.wsdl.GetWebmailImapquotaResponse;
import webmail.wsdl.Imapquota;
import webmail.wsdl.TaskArray;
import webmail.wsdl.TaskBean;


@Controller
public class TaskController {

	
	@Autowired
	WebmailClient webmailClient;
	
	@Autowired
	private FileClient fileClient;
	
	@Autowired
	private CalendarClient calendarclient;
	
	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public ModelAndView mycalendar(ModelMap map, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		
		
		System.out.println("*************ses="+id);
		if(id==null)
		{
			//ses="SesExp";
			return new ModelAndView("redirect:/login?stat=ses", map);
		}
		
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");
		String fdrnm=(String)hs.getAttribute("default_calendar");
		String proid=(String)hs.getAttribute("caldevProId");
		String surl=(String)hs.getAttribute("caldevUrl");
		GetWebmailImapquotaResponse wfresponse=webmailClient.getWebmailImapquotaRequest(host, id, pass);
		Imapquota iqt  =wfresponse.getImapquota();
		System.out.println("*********iqt********Qlota="+ iqt.getQuotalimit());
		long ql= iqt.getQuotalimit();
		long qu=iqt.getQuotauses();
		long qper=0l;
		if(ql>0)
		qper=(qu*100)/ql;
		map.addAttribute("QuotaPer", ""+qper);
		map.addAttribute("QuotaLimit", ""+ql);
		map.addAttribute("QuotaUses", ""+qu); 
		map.addAttribute("createTask", new TaskBean());
		
		
		CalDav cdav=new CalDav();
		TaskArray taskres=cdav.getAllTasks(proid, surl, id, pass, fdrnm);
		if(taskres!=null)
		{
		List<TaskBean> taskarray  = taskres.getTaskList();
		try
		{
    	Collections.sort(taskarray,new TaskCompare());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    	map.addAttribute("taskList", taskarray);
		}
		
		/*GetFileByPathResponse fileByPath=fileClient.getFileByPath("/"+id+"/Task/"+id+"_task.ics",id,pass);
	    webmail.wsdl.File fileNode=fileByPath.getFile();
	    if(fileNode.getFileContent() != null)
	    {
	    	GetTasksResponse taskres = webmailClient.buildTask(fileNode.getFileContent(), id+"_task.ics");
	    	List<TaskBean> taskarray  = taskres.getTaskList().getTaskList();
	    	Collections.sort(taskarray,new TaskCompare());
	    	map.addAttribute("taskList", taskarray);
	    }
	   /* else
	    {
	    	CreateCalendarResponse res = calendarclient.createCalendar(id,"","PUBLISH");
	    	String filename=id+"_task.ics";
			System.out.println(filename);

			CreateFileResponse cr = fileClient.createFile(filename,"/"+id+"/Task", id,pass, "", "",res.getResponse(),0);
			System.out.println(cr.isSuccess());
			map.addAttribute("taskList", new ArrayList<TaskBean>());
	    }*/
	    	
		return new ModelAndView("tasks", map);
		
	}
	
	@RequestMapping(value = "/createTask", method = RequestMethod.GET)
	@ResponseBody
	public String createTask(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		String id=(String)hs.getAttribute("id");
//		System.out.println("User id : " + id);
		String pass=(String)hs.getAttribute("pass");
		String tsk_detail = request.getParameter("tsk_detail");
		String startDate = request.getParameter("startDate");
		String startTime = request.getParameter("startTime");
		String endDate = request.getParameter("endDate");
		String endTime = request.getParameter("endTime");
		String status = request.getParameter("status");
		String tsk_priority = request.getParameter("tsk_priority");
		String taskDesc = request.getParameter("taskDesc");
		String tsk_progress = request.getParameter("tsk_progress");
		String proid=(String)hs.getAttribute("caldevProId");
		String surl=(String)hs.getAttribute("caldevUrl");
		String calnm=(String)hs.getAttribute("default_calendar");
		String frequency = request.getParameter("frequency");
		String interval = request.getParameter("interval");
		String count = request.getParameter("count");
		String until = request.getParameter("until");
		String daysofweek = request.getParameter("daysofweek");
		String rec_type = request.getParameter("rec_type");
		
		String reminderdata = request.getParameter("reminderdata");
		
//		-------------hidden time --------------
		startTime="00:00am";
		endTime="00:00am";
		
//		System.out.println("AQQQQQQQQQQQQQQQQQQQQQQQQQQQQtsk_detail"+tsk_detail+"startDate"+startDate+"startTime"+startTime+"endDate"+endDate+"endTime"+endTime+"status"+status+"tsk_priority"+tsk_priority+"taskDesc"+taskDesc);
		JSONObject obj = new JSONObject();
		if(tsk_detail != null && tsk_detail.length() != 0 && status != null &&  status.length() != 0 && tsk_priority != null && tsk_priority.length() != 0)
		{
//			System.out.println("file node : " );
					
			
			
			
		    //GetFileByPathResponse fileByPath=fileClient.getFileByPath("/"+id+"/Task/"+id+"_task.ics",id,pass);
		   // webmail.wsdl.File fileNode=fileByPath.getFile();
		    
		    TaskBean taskBean = new TaskBean();
		    taskBean.setDetail(tsk_detail);
		    taskBean.setTaskdesc(taskDesc);
		    /*
		     * <select id="status" name="status" class="pro_status clear_data">
									<option value="0" class="new_cl">Not Started</option>
									<option value="50" class="new_cl">In Progress</option>
									<option value="100" class="new_cl">Completed</option>
									<option value="Deferred" class="new_cl">Deferred</option>
							</select>
		     */
		    try{
		    	if(!status.equalsIgnoreCase("CANCELLED"))
		    	{
		    	int st=Integer.parseInt(status);
		    	 if(st<100)
		    	{
		    		status="IN-PROCESS";
		    	}
		    	else if(st>=100)
		    	{
		    		status="COMPLETED";
		    	}
		    		
		    	}
		    }
		    catch(Exception e)
		    {
		    	status="IN-PROCESS";
		    	e.printStackTrace();
		    }
		    taskBean.setStatus(status);
		    
		    taskBean.setReminderdata(reminderdata);
		    
		    if(frequency != "")
		    {
				taskBean.setFrequency(frequency);
				if (!frequency.equals("no")) {
					if (interval != "") {
						taskBean.setInterval(Integer.parseInt(interval));
					}
					if (count != "") {

						if (rec_type.equals("never")) {
							// event.setCount(100);
						} else if (rec_type.equals("count")) {
							if (count != "") {
								taskBean.setCount(Integer.parseInt(count));
							} else {
								taskBean.setCount(1);
							}
						} else if (rec_type.equals("until")) {
							try {
								DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
								java.util.Date date = df1.parse(until);
								GregorianCalendar gregoriancalendar = new GregorianCalendar();
								gregoriancalendar.setTime(date);
								XMLGregorianCalendar xmlgregoriancalendar2 = DatatypeFactory.newInstance()
										.newXMLGregorianCalendar(gregoriancalendar);
								taskBean.setUntil(xmlgregoriancalendar2);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (DatatypeConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

					if (frequency.equals("DAILY")) {

					} else if (frequency.equals("WEEKLY")) {
						DateTimeTaskList rdays = new DateTimeTaskList();
						if (daysofweek != null) {
							String days[] = daysofweek.split("`");
							for (String day : days)
								rdays.getDateTime().add(day);
						}
						taskBean.setRepeatdatetimelist(rdays);
					} else if (frequency.equals("MONTHLY")) {

					} else if (frequency.equals("YEARLY")) {

					}
				}

			}
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    try
		    {
		    	taskBean.setProgress(Integer.parseInt(tsk_progress));
		    	taskBean.setPriority(Integer.parseInt(tsk_priority));
		    }
		    catch(NumberFormatException e)
		    {
		    	e.printStackTrace();
		    }
		    try 
		    {
		    	if(startTime.length() == 6)
		    	{
		    		startTime = "0"+startTime;
		    	}
		    	if(endTime.length() == 6)
		    	{
		    		endTime = "0"+endTime;
		    	}
		    	
		    	if(startTime.contains("am"))
		    	{
		    		startTime = startTime.replace("am", "");
		    		startTime = startTime +":00+05:30";
		    		
		    	}
		    	else
		    	{
		    		startTime = startTime.replace("pm", "");
		    		String[] tm = startTime.split(":");
		    		int hour = Integer.parseInt(tm[0]);
		    		startTime = String.valueOf(hour+12)+":"+tm[1]+":00+05:30";
		    	}
		    	if(endTime.contains("am"))
		    	{
		    		endTime = endTime.replace("am", "");
//		    		endTime = endTime.replace(":", "");
		    		endTime = endTime +":00+05:30";
		    		
		    	}
		    	else
		    	{
		    		endTime = endTime.replace("pm", "");
		    		String[] tm = endTime.split(":");
		    		int hour = Integer.parseInt(tm[0]);
		    		endTime = String.valueOf(hour+12)+":"+tm[1]+":00+05:30";
		    	}
		    	startDate = startDate+"T"+startTime;
		    	endDate = endDate+"T"+endTime;
		    	XMLGregorianCalendar stcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
		    	XMLGregorianCalendar endcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate);
		    	if(stcal.toGregorianCalendar().getTime().getTime() < endcal.toGregorianCalendar().getTime().getTime())
		    	{
		    		taskBean.setStartdate(stcal);
		    		taskBean.setDuedate(endcal);
		    		
		    		//String calnm=surl+"/caldav.php/"+id+"/calendar/";
		    		CalDav cdav=new CalDav();
		    		GetAddTaskResponse response =cdav.createTask(proid, surl, id, pass, calnm, taskBean);
					if(response.getUid() != null && response.getUid().length() != 0 )
					{
						//EditFileResponse res2 = fileClient.editEvent(fileNode.getFilePath(),response.getResponse(),id,pass);
						obj.put("success", "true");
						return obj.toJSONString();
					}
		    	}
		    	else
		    	{
		    		obj.put("enddateerror", "true");
		    		return obj.toJSONString();
		    	}
				
				
			}
		    catch (DatatypeConfigurationException e1) 
		    {
				e1.printStackTrace();
				obj.put("success", "false");
				return obj.toJSONString();
			}
		    catch (Exception e) 
		    {
				e.printStackTrace();
				obj.put("success", "false");
				return obj.toJSONString();
			}
		    
			
		    obj.put("success", "false");
		}
		return obj.toJSONString();
	}
	
	@RequestMapping(value = "loadTaskList", method = RequestMethod.GET)
	@ResponseBody
	public String loadTaskList(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String fdrnm=(String)hs.getAttribute("default_calendar");
		String proid=(String)hs.getAttribute("caldevProId");
		String surl=(String)hs.getAttribute("caldevUrl");
//		System.out.println("User id : " + id);
		String pagecount  = request.getParameter("pagecount");
		System.out.println("in loadTaskList  pagecount : " + pagecount);
		//GetFileByPathResponse fileByPath=fileClient.getFileByPath("/"+id+"/Task/"+id+"_task.ics",id,pass);
	    //webmail.wsdl.File fileNode=fileByPath.getFile();
	    JSONObject res = new JSONObject();
	    if(fdrnm != null)
	    {
	    	CalDav cdav=new CalDav();
			TaskArray taskres=cdav.getAllTasks(proid, surl, id, pass, fdrnm);
	    	List<TaskBean> taskarray  = taskres.getTaskList();
	    	try
	    	{
	    	Collections.sort(taskarray,new TaskCompare());
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    	int totalTask = taskarray.size();
	    	res.put("totaltask", totalTask);
	    	JSONArray jsonArray = new JSONArray();
	    	if(!taskarray.isEmpty() && pagecount != null && pagecount.length() != 0)
	    	{
	    		JSONObject obj = null;
	    		
	    		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	    		int pcount = 1;
	    		try
	    		{
	    			pcount = Integer.parseInt(pagecount);
	    		}
	    		catch(NumberFormatException e)
	    		{
	    			e.printStackTrace();
	    		}
	    		if(pcount > 0  && totalTask  > (pcount-1)*20 )
	    		{
	    			res.put("pagecount", pcount);
	    			
	    			int count =  0;
	    			for(int i = (pcount-1)*20 ; i < taskarray.size() && count < 20 ; i ++, count++ )
	    			{
	    				String startDate = "";
	    	    		String endDate = "";
	    	    		String modifyDate = "";
	    				TaskBean task = taskarray.get(i);
	    				obj = new JSONObject();
	    				obj.put("uid", task.getUid());
	    				obj.put("detail", task.getDetail());
	    				
	    				if(task.getStartdate()!=null && task.getStartdate().toString().length()>0)
	    				{
	    				startDate  = task.getStartdate().toString();
	    				if(task.getStartdate().toString().length()>=10)
	    					startDate = startDate.substring(0, 10);
	    				
	    				try
	    				{
	    					Date st = dateFormat1.parse(startDate);
	    					startDate = dateFormat.format(st);
	    					
	    				}
	    				catch(ParseException e)
	    				{
	    					e.printStackTrace();
	    				}
	    				}
	    				
	    				if(task.getDuedate()!=null && task.getDuedate().toString().length()>0)
	    				{
	    				endDate  = task.getDuedate().toString();
	    				if(task.getDuedate().toString().length()>=10)
	    				endDate = endDate.substring(0, 10);
	    				
	    				try
	    				{
	    					Date st = dateFormat1.parse(endDate);
	    					endDate = dateFormat.format(st);
	    					
	    				}
	    				catch(ParseException e)
	    				{
	    					e.printStackTrace();
	    				}
	    				}
	    				if(task.getModifydate() != null && task.getModifydate().toString().length()>0)
	    				{
	    					modifyDate  = task.getModifydate().toString();
	    					if(task.getModifydate().toString().length()>=10)
	    					modifyDate = modifyDate.substring(0, 10);
	    					try
	    					{
	    						
	    						Date md = dateFormat1.parse(modifyDate);
	    						modifyDate = dateFormat.format(md);
	    					}
	    					catch(ParseException e)
	    					{
	    						e.printStackTrace();
	    					}
	    				}
	    				else
	    				{
	    					modifyDate =  "Not Modified";
	    				}
	    				
	    				
	    				obj.put("startDate", startDate);
	    				obj.put("endDate", endDate);
	    				obj.put("modifyDate", modifyDate);
	    				String task_status= task.getStatus();
	    				if(task_status!=null)
						{
						 if (task_status.equalsIgnoreCase("CANCELLED")) {
							task_status = "Cancelled";
						} else if (task_status.equalsIgnoreCase("IN-PROCESS")) {
							task_status = "In Process";
						} else if (task_status.equalsIgnoreCase("Completed")) {
							task_status = "Completed";
						}
						else
						{
						try{
							int st=Integer.parseInt(task_status);
					    	 if(st<100)
					    	{
					    		 task_status="In Process";
					    	}
					    	else if(st>=100)
					    	{
					    		task_status="Completed";
					    	}
						}
						catch(Exception e)
						{
							task_status="In Process";
							e.printStackTrace();
						}
						}
						}
						else
						{
							task_status="";
						}
	    				obj.put("status",task_status);
	    				obj.put("progress", task.getProgress());
	    				obj.put("priority", task.getPriority());
	    				/*if (task.getRepeatdatetimelist() != null)
	     				{
	    					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    					
	    					DateTimeTaskList dttl = task.getRepeatdatetimelist();
	    					
	    					String rec_date = "";
	    					
	    					for(String dt : dttl.getDateTime())
	    					{
	    						String aa[] = dt.split("`");
	    						try 
	    						{
	    							Date sdt = sdf.parse(aa[0].substring(0, aa[0].indexOf("T")));
									Date edt = sdf.parse(aa[1].substring(0, aa[1].indexOf("T")));
									if(sdt.after(new Date()))
									{
										
										rec_date+= dateFormat.format(sdt)+"`"+dateFormat.format(edt)+"`I";
										break;
									}
									else
									{
										rec_date+= dateFormat.format(sdt)+"`"+dateFormat.format(edt)+"`C";
									}
								}
	    						catch (ParseException e) 
	    						{
									e.printStackTrace();
								}
	    						System.out.println(" <<<<<< : " + dt);
	    					}
	    					obj.put("daylist", rec_date);
//	    					obj.put("daylist", task.getRepeatdatetimelist().getDateTime().toString().replace("[", "").replace("]", "").replace(" ", ""));
	     				}*/
	    				jsonArray.add(obj);
	    			}
	    			
	    		}
	    	}
	    	res.put("tasklist", jsonArray);
	    	
	    }
		return res.toJSONString();
	}

	@RequestMapping(value = "/deleteTask", method = RequestMethod.GET)
	@ResponseBody
	public String deleteTask(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		String id=(String)hs.getAttribute("id");
		String calnm=(String)hs.getAttribute("default_calendar");
		String proid=(String)hs.getAttribute("caldevProId");
		String surl=(String)hs.getAttribute("caldevUrl");
		boolean response=true;
		String uid = request.getParameter("uid");
		uid = uid.substring(4);
//		System.out.println("Uid : " + uid);
		String pass=(String)hs.getAttribute("pass");
		/*GetFileByPathResponse fileByPath=fileClient.getFileByPath("/"+id+"/Task/"+id+"_task.ics",id,pass);
	    webmail.wsdl.File fileNode=fileByPath.getFile();
		DeleteTaskResponse response = webmailClient.deleteTask(fileNode.getFileContent(), uid);
		EditFileResponse res2 = fileClient.editEvent(fileNode.getFilePath(),response.getResponse(),id,pass);*/
		CalDav cdd=new CalDav();
		cdd.deleteSelTask(proid, surl, id, pass, calnm, uid);
		JSONObject obj = new JSONObject();
		if(response)
		{
			obj.put("success", "true");
		}
		
		return obj.toJSONString();
	}
	
	@RequestMapping(value = "/deleteSelectedTask", method = RequestMethod.GET)
	@ResponseBody
	public String deleteSelectedTask(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		String id=(String)hs.getAttribute("id");
		String calnm=(String)hs.getAttribute("default_calendar");
		String proid=(String)hs.getAttribute("caldevProId");
		String surl=(String)hs.getAttribute("caldevUrl");
		boolean response=true;
		String uid = request.getParameter("uid");
//		System.out.println("Uid : " + uid);
		String pass=(String)hs.getAttribute("pass");
		/*GetFileByPathResponse fileByPath=fileClient.getFileByPath("/"+id+"/Task/"+id+"_task.ics",id,pass);
	    webmail.wsdl.File fileNode=fileByPath.getFile();
	    DeleteSelectedTaskResponse response = webmailClient.deleteSelectedTask(fileNode.getFileContent(), uid);
		EditFileResponse res2 = fileClient.editEvent(fileNode.getFilePath(),response.getResponse(),id,pass);*/
		String arr[]=uid.split(",");
		CalDav cdd=new CalDav();
		for(int i=0;i<arr.length;i++)
		{
			cdd.deleteSelTask(proid, surl, id, pass, calnm, arr[i]);
		}
		JSONObject obj = new JSONObject();
		if(response)
		{
			obj.put("success", "true");
		}
		
		
		return obj.toJSONString();
	}
	
	@RequestMapping(value = "/getTaskDetailByUid", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskDetailByUid(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		String id=(String)hs.getAttribute("id");
		String calnm=(String)hs.getAttribute("default_calendar");
		String proid=(String)hs.getAttribute("caldevProId");
		String surl=(String)hs.getAttribute("caldevUrl");
		String pass=(String)hs.getAttribute("pass");
		String uid = request.getParameter("uid");
		JSONObject obj = new JSONObject();
		if(uid != null && uid.length() != 0)
		{
			
		    uid =uid.substring(4);
		    CalDav cld=new CalDav();
		    GetTaskDetailResponse response =cld.getTaskDetail(proid, surl, id, pass, calnm, uid);
		    List<TaskBean> taskList = response.getTaskList();
		    if(!taskList.isEmpty())
		    {
		    	TaskBean task = taskList.get(0);
		    	obj.put("uid", task.getUid());
				obj.put("detail", task.getDetail());
				String st=task.getStatus();
				if(st.equalsIgnoreCase("completed"))
				{
					st="100";
				}
				obj.put("status",st );
				obj.put("priority", String.valueOf(task.getPriority()));
				obj.put("progress", task.getProgress());
				obj.put("taskDesc", task.getTaskdesc());
				obj.put("reminderdata", task.getReminderdata());
				
				
 				XMLGregorianCalendar startcal = task.getStartdate();
 				if(startcal != null)
 				{
 					String mon = String.valueOf(startcal.getMonth());
 					String day = String.valueOf(startcal.getDay());
 					if(startcal.getMonth() <10)
 					{
 						mon = "0"+startcal.getMonth();
 					}
 					if(startcal.getDay() <10)
 					{
 						day = "0"+startcal.getDay();
 					}
 					obj.put("startDate", startcal.getYear()+"-"+mon+"-"+day);
 					
 					if(startcal.getHour() <12)
 					{
 						String hr = String.valueOf(startcal.getHour());
 						if(startcal.getHour() <10)
 						{
 							hr = ""+startcal.getHour();
 						}
 						String mn = String.valueOf(startcal.getMinute());
 						if(startcal.getMinute() <10)
 						{
 							mn = "0"+startcal.getMinute();
 						}
 						obj.put("startTime", hr + ":"+mn+"am");
 						
 					}
 					else
 					{
 						String hr = "";
 						if(startcal.getHour()-12 <10)
 						{
 							hr = ""+(startcal.getHour()-12);
 						}
 						else
 						{
 							hr = String.valueOf(startcal.getHour()-12);
 						}
 						String mn = String.valueOf(startcal.getMinute());
 						if(startcal.getMinute() <10)
 						{
 							mn = "0"+startcal.getMinute();
 						}
 						obj.put("startTime", hr + ":"+mn+"pm");
 						
 					}
 				}
 				XMLGregorianCalendar endcal = task.getDuedate();
 				if(endcal != null)
 				{
 					String mon = String.valueOf(endcal.getMonth());
 					String day = String.valueOf(endcal.getDay());
 					if(endcal.getMonth() <10)
 					{
 						mon = "0"+endcal.getMonth();
 					}
 					if(endcal.getDay() <10)
 					{
 						day = "0"+endcal.getDay();
 					}
 					obj.put("endDate", endcal.getYear()+"-"+mon+"-"+day);
 					
 					if(endcal.getHour() <12)
 					{
 						String hr = String.valueOf(endcal.getHour());
 						if(endcal.getHour() <10)
 						{
 							hr = ""+endcal.getHour();
 						}
 						String mn = String.valueOf(endcal.getMinute());
 						if(endcal.getMinute() <10)
 						{
 							mn = "0"+endcal.getMinute();
 						}
 						obj.put("endTime", hr + ":"+mn+"am");
 						
 					}
 					else
 					{
 						String hr = "";
 						if(endcal.getHour()-12 <10)
 						{
 							hr = ""+(endcal.getHour()-12);
 						}
 						else
 						{
 							hr = String.valueOf(endcal.getHour()-12);
 						}
 						String mn = String.valueOf(endcal.getMinute());
 						if(endcal.getMinute() <10)
 						{
 							mn = "0"+endcal.getMinute();
 						}
 						obj.put("endTime", hr + ":"+mn+"pm");
 						
 					}
 					
 				}
 				XMLGregorianCalendar modcal = task.getModifydate();
 				if(modcal != null)
 				{
 					obj.put("modiDate", modcal.getYear()+"-"+modcal.getMonth()+"-"+modcal.getDay());
 					
 				}
 				DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
 				
 				obj.put("frequency", task.getFrequency());
 				if(task.getCount() != null)
 				{
 					obj.put("count", task.getCount());
 				}
 				else
 				{
 					obj.put("count", "");
 				}
 				obj.put("interval", task.getInterval());
 				if (task.getUntil() != null)
 				{
 					obj.put("until", dateformat.format(task.getUntil().toGregorianCalendar() .getTime()));
 				}
 				else
 				{
 					obj.put("until", "");
 				}
 				if (task.getRepeatdatetimelist() != null)
 				{
 					obj.put("daylist", task.getRepeatdatetimelist().getDateTime().toString().replace("[", "").replace("]", "").replace(" ", ""));
 				}
 				obj.put("success", "true");
				
				
		    }
		}
		return obj.toJSONString();
	}
	
	
	@RequestMapping(value = "/updateTask", method = RequestMethod.GET)
	@ResponseBody
	public String updateTask(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		String id=(String)hs.getAttribute("id");
		String calnm=(String)hs.getAttribute("default_calendar");
		String proid=(String)hs.getAttribute("caldevProId");
		String surl=(String)hs.getAttribute("caldevUrl");
		String pass=(String)hs.getAttribute("pass");
		String tsk_detail = request.getParameter("tsk_detail");
		String startDate = request.getParameter("startDate");
		String startTime = request.getParameter("startTime");
		String endDate = request.getParameter("endDate");
		String endTime = request.getParameter("endTime");
		String status = request.getParameter("status");
		String tsk_priority = request.getParameter("tsk_priority");
		String taskDesc = request.getParameter("taskDesc");
		String tsk_progress = request.getParameter("tsk_progress");
		String uid = request.getParameter("uid");
		
		String frequency = request.getParameter("frequency");
		String interval = request.getParameter("interval");
		String count = request.getParameter("count");
		String until = request.getParameter("until");
		String daysofweek = request.getParameter("daysofweek");
		String rec_type = request.getParameter("rec_type");
		
		String reminderdata = request.getParameter("reminderdata");
		
//		System.out.println("AQQQQQQQQQQQQQQQQQQQQQQQQQQQQtsk_detail"+tsk_detail+"startDate"+startDate+"startTime"+startTime+"endDate"+endDate+"endTime"+endTime+"status"+status+"tsk_priority"+tsk_priority+"taskDesc"+taskDesc);
		JSONObject obj = new JSONObject();
		if(uid != null && uid.length() != 0 &&tsk_detail != null && tsk_detail.length() != 0 && status != null &&  status.length() != 0 && tsk_priority != null && tsk_priority.length() != 0)
		{
//			System.out.println("file node : " );
					
			
			
			
		    //GetFileByPathResponse fileByPath=fileClient.getFileByPath("/"+id+"/Task/"+id+"_task.ics",id,pass);
		   // webmail.wsdl.File fileNode=fileByPath.getFile();
		    
		    TaskBean taskBean = new TaskBean();
		    
		    taskBean.setUid(uid);
		    taskBean.setDetail(tsk_detail);
		    taskBean.setTaskdesc(taskDesc);
		    try{
		    	if(!status.equalsIgnoreCase("CANCELLED"))
		    	{
		    	int st=Integer.parseInt(status);
		    	 if(st<100)
		    	{
		    		status="IN-PROCESS";
		    	}
		    	else if(st>=100)
		    	{
		    		status="COMPLETED";
		    	}
		    		
		    	}
		    }
		    catch(Exception e)
		    {
		    	status="IN-PROCESS";
		    	e.printStackTrace();
		    }
		    taskBean.setStatus(status);
		    
		    taskBean.setReminderdata(reminderdata);

		    
		    if(frequency != "")
		    {
				taskBean.setFrequency(frequency);
				if (!frequency.equals("no")) 
				{
					if (interval != "") 
					{
						taskBean.setInterval(Integer.parseInt(interval));
					}
					
					if (rec_type.equals("never")) 
					{
						// event.setCount(100);
					} 
					else if (rec_type.equals("count")) 
					{
						if (count != "") 
						{
							taskBean.setCount(Integer.parseInt(count));
						} 
						else 
						{
							taskBean.setCount(1);
						}
					}
					else if (rec_type.equals("until")) 
					{
						try 
						{
							DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
							java.util.Date date = df1.parse(until);
							GregorianCalendar gregoriancalendar = new GregorianCalendar();
							gregoriancalendar.setTime(date);
							XMLGregorianCalendar xmlgregoriancalendar2 = DatatypeFactory.newInstance()
									.newXMLGregorianCalendar(gregoriancalendar);
							taskBean.setUntil(xmlgregoriancalendar2);
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

					if (frequency.equals("DAILY")) 
					{

					} 
					else if (frequency.equals("WEEKLY")) 
					{
						DateTimeTaskList rdays = new DateTimeTaskList();
						if (daysofweek != null) 
						{
							String days[] = daysofweek.split("`");
							for (String day : days)
								rdays.getDateTime().add(day);
						}
						taskBean.setRepeatdatetimelist(rdays);
					}
					else if (frequency.equals("MONTHLY")) 
					{

					} 
					else if (frequency.equals("YEARLY"))
					{

					}
				}

			}
		    
		    
		    
		    
		    try
		    {
//		    	tsk_progress = tsk_progress.trim().replace("%", "");
		    	taskBean.setProgress(Integer.parseInt(tsk_progress));
		    	taskBean.setPriority(Integer.parseInt(tsk_priority));
		    }
		    catch(NumberFormatException e)
		    {
		    	e.printStackTrace();
		    }
		    try 
		    {
		    	if(startTime==null || startTime.length()<=0)
		    	{
		    		startTime ="00:00:00+05:30";
		    	}
		    	else
		    	{
		    	if(startTime.length() == 6)
		    	{
		    		startTime = "0"+startTime;
		    	}
		    			    	
		    	if(startTime.contains("am"))
		    	{
		    		startTime = startTime.replace("am", "");
		    		startTime = startTime +":00+05:30";
		    		
		    	}
		    	else
		    	{
		    		startTime = startTime.replace("pm", "");
		    		String[] tm = startTime.split(":");
		    		int hour = Integer.parseInt(tm[0]);
		    		startTime = String.valueOf(hour+12)+":"+tm[1]+":00+05:30";
		    	}
		    	}
		    	
		    	
		    	if(endTime==null || endTime.length()<=0)
		    	{
		    		endTime ="00:00:00+05:30";
		    	}
		    	else
		    	{
		    	if(endTime.length() == 6)
		    	{
		    		endTime = "0"+endTime;
		    	}
		    	if(endTime.contains("am"))
		    	{
		    		endTime = endTime.replace("am", "");
		    		endTime = endTime +":00+05:30";
		    		
		    	}
		    	else
		    	{
		    		endTime = endTime.replace("pm", "");
		    		String[] tm = endTime.split(":");
		    		int hour = Integer.parseInt(tm[0]);
		    		endTime = String.valueOf(hour+12)+":"+tm[1]+":00+05:30";
		    	}
		    	}
		    	startDate = startDate+"T"+startTime;
		    	endDate = endDate+"T"+endTime;
		    	XMLGregorianCalendar stcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
		    	XMLGregorianCalendar endcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate);
		    	if(stcal.toGregorianCalendar().getTime().getTime() < endcal.toGregorianCalendar().getTime().getTime())
		    	{
		    		taskBean.setStartdate(stcal);
		    		taskBean.setDuedate(endcal);
		    		
		    		CalDav cld=new CalDav();
		    		GetUpdateTaskResponse response =cld.updateTask(proid, surl, id, pass, calnm, taskBean);
		    		if(response.isUpdateSuccess())
		    		{
		    			//EditFileResponse res2 = fileClient.editEvent(fileNode.getFilePath(),response.getResponse(),id,pass);
		    			
		    			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		    			if(taskBean.getProgress()>= 100 && frequency != "" && !frequency.equals("no"))
		    			{
		    				if(taskBean.getCount()!=null || taskBean.getUntil()!=null)
		    				{
		    				DateTimeTaskList dtl = response.getRepeatdates();
		    				List<String> list =  dtl.getDateTime();
		    				if(list != null && !list.isEmpty() && list.size() >1)
		    				{
		    					String firstRepeatDate = list.get(1);
		    					String aa[] = firstRepeatDate.split("`");
	    						try 
	    						{
	    							Date sdt = sdf.parse(aa[0].substring(0, aa[0].indexOf("T")));
	    							Date edt = sdf.parse(aa[1].substring(0, aa[0].indexOf("T")));
	    							GregorianCalendar  newst = new GregorianCalendar();
	    							newst.setTime(sdt);
	    							taskBean.setStartdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(newst));
	    							newst.setTime(edt);
	    							taskBean.setDuedate(DatatypeFactory.newInstance().newXMLGregorianCalendar(newst));
	    							taskBean.setStatus("IN-PROCESS");
	    							taskBean.setProgress(0);
	    							taskBean.setUid("");
	    							if (rec_type.equals("count")) 
	    							{
	    								if (count != "") 
	    								{
	    									taskBean.setCount(Integer.parseInt(count) - 1);
	    								}
	    								else 
	    								{
	    									taskBean.setCount(1);
	    								}
	    							}
	    							GetAddTaskResponse newres =cld.createTask(proid, surl, id, pass, calnm, taskBean);
	    							if(newres.getUid() != null && newres.getUid().length() != 0 )
	    							{
	    								//EditFileResponse newres2 = fileClient.editEvent(fileNode.getFilePath(),newres.getResponse(),id,pass);
	    								obj.put("success", "true");
	    								return obj.toJSONString();
	    							}
	    							
								}
	    						catch (ParseException e) 
	    						{
									e.printStackTrace();
								}
		    				}
		    				
		    				}
		    				else
		    				{
		    				
		    					

		    						try 
		    						{
		    							Date sdt = new Date();
		    							
		    							GregorianCalendar  newst = new GregorianCalendar();
		    							newst.setTime(sdt);
		    							taskBean.setStartdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(newst));
		    							taskBean.setDuedate(null);
		    							taskBean.setStatus("IN-PROCESS");
		    							taskBean.setProgress(0);
		    							taskBean.setUid("");
		    						
		    							GetAddTaskResponse newres =cld.createTask(proid, surl, id, pass, calnm, taskBean);
		    							if(newres.getUid() != null && newres.getUid().length() != 0 )
		    							{
		    								//EditFileResponse newres2 = fileClient.editEvent(fileNode.getFilePath(),newres.getResponse(),id,pass);
		    								obj.put("success", "true");
		    								return obj.toJSONString();
		    							}
		    							
									}
		    						catch (Exception e) 
		    						{
										e.printStackTrace();
									}
			    				
			    				
		    					
		    					
		    				}
		    			}
		    			
		    			
		    			obj.put("success", "true");
		    			return obj.toJSONString();
		    		}
		    		
		    	}
		    	else
		    	{
		    		obj.put("enddateerror", "true");
		    		return obj.toJSONString();
		    	}
				
			}
		    catch (DatatypeConfigurationException e1) 
		    {
				e1.printStackTrace();
				obj.put("success", "false");
				return obj.toJSONString();
			}
		    catch (Exception e) 
		    {
				e.printStackTrace();
				obj.put("success", "false");
				return obj.toJSONString();
			}
		    
			
		    obj.put("success", "false");
//			System.out.println("JSON Object : " + obj.toJSONString());
		}
		return obj.toJSONString();
	}
	
	
}
