package webmail.controller;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.stream.FileImageInputStream;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sieve.manage.examples.ConnectAndListScripts;

import ezvcard.*;
import ezvcard.io.text.VCardReader;
import ezvcard.property.*;
import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.connector.dav.CalDavCalendarStore;
import net.fortuna.ical4j.connector.dav.CardDavCollection;
import net.fortuna.ical4j.connector.dav.CardDavStore;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.VCard;
import webmail.bean.CalDav;
import webmail.bean.CardDav;
import webmail.bean.NPCompare;
import webmail.bean.NPCompareCon;
import webmail.dao.SharedDao;
import webmail.model.Shared;
import webmail.webservice.client.FileClient;
import webmail.webservice.client.FolderClient;
import webmail.webservice.client.WebmailClient;
import webmail.wsdl.AssignSinglePermissionResponse;
import webmail.wsdl.CreateContactResponse;
import webmail.wsdl.CreateFileResponse;
import webmail.wsdl.CreateFolderResponse;
import webmail.wsdl.DeleteFileResponse;
import webmail.wsdl.DeleteFolderResponse;
import webmail.wsdl.Folder;
import webmail.wsdl.GetContactDetailResponse;
import webmail.wsdl.GetFileByPathResponse;
import webmail.wsdl.GetFileWithOutStreamResponse;
import webmail.wsdl.GetFolderByPathResponse;
import webmail.wsdl.GetFolderResponse;
import webmail.wsdl.GetFullContactDetailResponse;
import webmail.wsdl.GetLdapUserDirectoryResponse;
import webmail.wsdl.GetSharedFilesByPathResponse;
import webmail.wsdl.GetSharedFoldersByPathResponse;
import webmail.wsdl.GetSharedFoldersResponse;
import webmail.wsdl.GetVCFFileResponse;
import webmail.wsdl.GetVCFLdapDirResponse;
import webmail.wsdl.GetWebmailFolderResponse;
import webmail.wsdl.LdapDirectory;
import webmail.wsdl.RecycleFolderResponse;
import webmail.wsdl.RemoveAssignedPermissionResponse;
import webmail.wsdl.VCFFileAtt;
import webmail.wsdl.VCFLdapDirAtt;

import java.io.*;

@Controller
public class ContactController {

	
	@Autowired
	private WebmailClient webmailClient;
	
	@Autowired
	private FolderClient folderClient;
	
	@Autowired
	private FileClient fileClient;
	
	public final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		public boolean validate(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		        return matcher.find();
		}
	
	
	@RequestMapping(value = "/uploadImageContactEdit",  produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
	   public @ResponseBody String uploadImageContactEdit(MultipartHttpServletRequest request, HttpServletResponse response) {                 
	 
		 
		 
		 String st="false";
	     Iterator<String> itr =  request.getFileNames();
	 	HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
	   
	     while (itr.hasNext()){
	     MultipartFile mpf = request.getFile(itr.next());
	     try {
	    	 
	     int idx = mpf.getOriginalFilename().lastIndexOf('.');
    String fileExtension = idx > 0 ? mpf.getOriginalFilename().substring(idx) : ".tmp";
    if(fileExtension.equalsIgnoreCase(".png") || fileExtension.equalsIgnoreCase(".jpg") || fileExtension.equalsIgnoreCase(".jpeg"))
    {
    File fil = File.createTempFile(mpf.getOriginalFilename(), ".jpg");
  // System.out.println("!!!!!!!!!!!!!!!!!!!!!!="+fil.getPath()+"--"+fil.getName());
    mpf.transferTo(fil);
  
    byte[] bFile = new byte[(int) fil.length()];
    
    //convert file into array of bytes
    FileInputStream   fileInputStream = new FileInputStream(fil);
	    fileInputStream.read(bFile);
    fileInputStream.close(); 
    
    
 
		String entry="mail="+id+","+ldapbase;
		 st = new sun.misc.BASE64Encoder().encode(bFile);
	  hs.setAttribute("ContactImgEdit", bFile);
		/* InputStream stream = new ByteArrayInputStream(st.getBytes(StandardCharsets.UTF_8));
		 byte [] bt0=IOUtils.toByteArray(stream);
    byte [] bt=st.getBytes();
    byte[] bt1 = st.getBytes("UTF-8");
    byte[] bt2 = st.getBytes(Charset.forName("UTF-8"));
    byte[] bt3= IOUtils.toByteArray(st);
    
    InputStream in = IOUtils.toInputStream(st, "UTF-8");
    byte [] bt4=IOUtils.toByteArray(in);
    
    
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int reads = in.read(); 
    while(reads != -1)
    {
 	   baos.write(reads); 
 	   reads = in.read();
    }
    byte [] bt5=baos.toByteArray();*/
    
    }
    
		} catch (IOException e)
	     {
			st="false";
			e.printStackTrace();
	     }
	   
	  break;
	    
	     }
	  

	    
	     
	     return st;
	 
	  }
	
	
	
	
	 @RequestMapping(value = "/uploadImageContact", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
	   public @ResponseBody String uploadImage(MultipartHttpServletRequest request, HttpServletResponse response) {                 
	 
		 
		 
		 String st="false";
	     Iterator<String> itr =  request.getFileNames();
	    // request.getp
	 	HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldapbase=(String)hs.getAttribute("ldapbase");
	   
	     while (itr.hasNext()){
	     MultipartFile mpf = request.getFile(itr.next());
	     try {
	    	 
	     int idx = mpf.getOriginalFilename().lastIndexOf('.');
       String fileExtension = idx > 0 ? mpf.getOriginalFilename().substring(idx) : ".tmp";
       if(fileExtension.equalsIgnoreCase(".png") || fileExtension.equalsIgnoreCase(".jpg") || fileExtension.equalsIgnoreCase(".jpeg"))
       {
       File fil = File.createTempFile(mpf.getOriginalFilename(), ".jpg");
     // System.out.println("!!!!!!!!!!!!!!!!!!!!!!="+fil.getPath()+"--"+fil.getName());
       mpf.transferTo(fil);
     
       byte[] bFile = new byte[(int) fil.length()];
       
       //convert file into array of bytes
       FileInputStream   fileInputStream = new FileInputStream(fil);
	    fileInputStream.read(bFile);
       fileInputStream.close(); 
       
       
    
		String entry="mail="+id+","+ldapbase;
		 st = new sun.misc.BASE64Encoder().encode(bFile);
	  hs.setAttribute("ContactImg", bFile);
		/* InputStream stream = new ByteArrayInputStream(st.getBytes(StandardCharsets.UTF_8));
		 byte [] bt0=IOUtils.toByteArray(stream);
       byte [] bt=st.getBytes();
       byte[] bt1 = st.getBytes("UTF-8");
       byte[] bt2 = st.getBytes(Charset.forName("UTF-8"));
       byte[] bt3= IOUtils.toByteArray(st);
       
       InputStream in = IOUtils.toInputStream(st, "UTF-8");
       byte [] bt4=IOUtils.toByteArray(in);
       
       
       
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       int reads = in.read(); 
       while(reads != -1)
       {
    	   baos.write(reads); 
    	   reads = in.read();
       }
       byte [] bt5=baos.toByteArray();*/
       
       }
       
		} catch (IOException e)
	     {
			st="false";
			e.printStackTrace();
	     }
	   
	  break;
	    
	     }
	  

	    
	     
	     return st;
	 
	  }
	
	 
	 @RequestMapping(value = "/importContacts", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
	   public @ResponseBody String importContacts(MultipartHttpServletRequest request, HttpServletResponse response) 
	 {
		 
		 String st="true";
	     Iterator<String> itr =  request.getFileNames();
	    // request.getp
	 	HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String cfolder=(String)hs.getAttribute("active_contact");
			
		String surl=(String)hs.getAttribute("caldevUrl");
	   if(cfolder.equals("Directory") )
	   {
		   return "Contacts can not import into Directory";
	   }
	     while (itr.hasNext())
	     {
		     MultipartFile mpf = request.getFile(itr.next());
		     try
		     {
		    	 
				 int idx = mpf.getOriginalFilename().lastIndexOf('.');
			     String fileExtension = idx > 0 ? mpf.getOriginalFilename().substring(idx) : ".tmp";
			     if(fileExtension.equalsIgnoreCase(".vcf") || fileExtension.equalsIgnoreCase(".VCF") )
			     {
			     File fil = File.createTempFile(mpf.getOriginalFilename(), ".vcf");
			     System.out.println("!!!!!!!!!!!!!!!!!!!!!!="+fil.getPath()+"--"+fil.getName());
			     mpf.transferTo(fil);
   
			    // byte []newcal=IOUtils.toByteArray((InputStream) new FileInputStream(fil));
			     InputStream ins=(InputStream) new FileInputStream(fil);
			     CardDav cdd=new CardDav();
			    st= cdd.importContacts(ins, "", surl, id, pass, cfolder);
			    
				 // String text = "D:\\contacts.vcf";
			     // put destination directory here - 
			     
			     // this line would have to be changed for java File.
			     //String[] strArrays = Files.readAllLines(text);
			   /*  
			    List<String> lines=null;
				try 
				{
					lines = IOUtils.readLines(new FileInputStream(fil));
				}
				catch (FileNotFoundException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				catch (IOException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(lines!=null)
				{
			     String[] strArrays =lines.toArray(new String[lines.size()]); 
			  		   
			     String empty = "";
			     String empty1 = "";
			     boolean flag = false;
			     boolean flag1 = false;
			     int num = 0;
			     
			     String[] strArrays1 = strArrays;
			     String name =""; // String.Empty;
			     for (int i = 0; i < (int)strArrays1.length; i++)
			     {
			         String str1 = strArrays1[i];
			         //this can be made case insensitive comparison
			         if (str1.equals("BEGIN:VCARD"))
			         {
			             flag = true;
			         }
			         if (str1.equals("END:VCARD"))
			         {
			             flag1 = true;
			         }
			         if (str1.startsWith("FN:"))
			         {
			             // put java equivalent of concat
			            // name = String.Concat(str1.substring(3), ".vcf");
			      	   name = str1.substring(3);
			         }
			         if (str1.toLowerCase().startsWith("EMAIL;".toLowerCase()))
			         {
			             // make java specific changes. need to split emailSplits variable on 2 chars ';' and ':'
			          //   String[] emailSplits = str1.split(new char[] { ';', ':' });
			      	   String[] emailSplits = str1.split(":");
			             empty1 = (emailSplits != null && emailSplits.length >= 2) ? emailSplits[emailSplits.length-1] : "";
			         }
			         if (flag)
			         {
			             // make java specific changes. Environment.NewLine is equivalent to '\n'
			             //empty = String.Concat(empty, Environment.NewLine, str1);
			      	   empty = empty+"\n"+ str1;
			         }
			         if (flag1)
			         {
			            if(empty1!=null && !empty1.equalsIgnoreCase("") && validate(empty1))
			            {
				            // empty1 = String.IsNullOrEmpty(empty1) ? name : empty1 + "_stork.vcf";
			            	
			            	UUID uuid = UUID.randomUUID();
			       	     	String randomUUIDString = uuid.toString();
			       	     	empty1=empty1+"$"+name+"_"+randomUUIDString+".vcf";
//				             empty1 = (empty1==null || empty1.equalsIgnoreCase("")) ? name : empty1 + "_id.vcf";
				             // make java specific changes - writes all lines to file
				             //    File.WriteAllText(Path.Combine(str, empty1), empty);
				             InputStream is = new ByteArrayInputStream(empty.getBytes());
				             
				             
				         	String notes="";
				    		String keywords="";
				    		String parentFolder = "/"+id+cfolder;
				    		String fileuserid=id;
				    	     
				    	     try 
				    	     {
				    	    	 byte[]	 iostream=org.apache.commons.codec.binary.Base64.encodeBase64(IOUtils.toByteArray(is));
				    		     CreateFileResponse cfileres=fileClient.createFile(empty1, parentFolder, fileuserid,pass,  keywords, notes,iostream,0);
				    		     boolean res=cfileres.isSuccess();
				    		     is.close();
				    		 }
				    	     catch (IOException e)
				    	     {
				    			// TODO Auto-generated catch block
				    			e.printStackTrace();
				    	     }
			             
			            }
			             
			             
			             flag = false;
			             flag1 = false;
			             empty = "";
			             empty1 = "";
			             name = "";
			             num++;
			         }
			         
			     }
				}
     
     
     
     */
     ins.close();
     }
     
		} catch (IOException e)
	     {
			st="false";
			e.printStackTrace();
	     }
	   
	  break;
	    
	     }
	  

	    
	     
	     return st;
	 
	  }
	
	@RequestMapping(value = "/getAllValueContactLdap",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)

	public String getAllValueContactLdap(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		String searchid=request.getParameter("searchid");
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldpabase=(String)hs.getAttribute("ldapbase");
		System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
		
		GetContactDetailResponse conres= webmailClient.getLdapContactFullDetail(ldapurl, id, pass, ldpabase, searchid);
		map.addAttribute("GetFullConRes", conres);
		
		
		return "contactalldispldap";
	}
@RequestMapping(value = "/getAllValueContact", method = RequestMethod.GET)

	public String getAllValueContact(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		HttpSession hs=request.getSession();
		
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String vcid=request.getParameter("filename");
		String surl=(String)hs.getAttribute("caldevUrl");
		String cfolder=(String)hs.getAttribute("active_contact");
		String gpid=cfolder;
		if(!cfolder.startsWith(surl))
		{
			gpid=surl+cfolder;
		}
		CardDav carddav=new CardDav();
		//String strm=carddav.getAllValueContact(surl, id, pass,gpid,vcid);
		/* VCard vc=carddav.getAllValueContact(surl, id, pass,gpid,vcid);
		 map.addAttribute("GetFullConRes", vc);*/
		 
	/*	System.out.println("openContactForm controller"+request.getParameter("filename"));
		String iostrm="";
		String parentFolder = "/"+id;
		String fileuserid=id;
		parentFolder=parentFolder+"/"+cfolder+"/"+flname;
		GetFileByPathResponse fileres= fileClient.getFileByPath(flname, fileuserid,pass);
		webmail.wsdl.File fileNode=fileres.getFile();
		//iostrm=fileNode.getFileContent();
		byte[] imageBytes = org.apache.commons.codec.binary.Base64.decodeBase64(fileNode.getFileContent());
		try {
			iostrm = IOUtils.toString(imageBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//InputStream inStream = IOUtils.toInputStream(iss);
		//System.out.println("^^^^^^^^^^^^^^^^="+iostrm);
		GetFullContactDetailResponse conres= webmailClient.getFullDetailContact(iostrm, flname);
		map.addAttribute("GetFullConRes", conres);
		//System.out.println("^^^^^^^^^^^^="+conres.getGetFullContactDetail().getWebamilFullName());
	*/
		 GetFullContactDetailResponse conres= carddav.getAllValueContact(surl, id, pass,gpid,vcid);
		 map.addAttribute("GetFullConRes", conres);
		 return "contactalldisp";
	}
	

		@RequestMapping("/getSelContactUserList")
		public String getSelContactUserList(ModelMap map, Principal principal,RedirectAttributes attributes,
		HttpServletRequest request, @RequestParam String cname, @RequestParam(value="pn") int pn)
		  {
			System.out.println("^^^^^^^cname="+cname);
			System.out.println("^^^^^^^pns="+pn);
			
			HttpSession hs=request.getSession();
			hs.setAttribute("active_contact",cname);
			attributes.addAttribute("pn", pn);
//			attributes.addAttribute("rpp", rpp);
			
			return "redirect:/getContactList";
		  }

/*		@RequestMapping("/getSharedSelContactUserList")
		public String getSharedSelContactUserList(ModelMap map, Principal principal,
		HttpServletRequest request, @RequestParam String cname)
		  {
			System.out.println("^^^^^^^cname="+cname);
			HttpSession hs=request.getSession();
			hs.setAttribute("active_contact",cname);
			return "redirect:/getContactList";
		  }
*/
	
	
	
/*		@RequestMapping("/getAllValueContact")
		@ResponseBody
		public String getAllValueContact(ModelMap map, Principal principal,
		HttpServletRequest request)
		  {
			String res="<link type='text/css' rel='stylesheet' href='css/contact_css.css' />";
			
			res+="<table> <tr><td colspan='3' class='right_info_heading_edit'>Personal Information</td> </tr>";
			res+="<tr> <td class='right_name_first_pop_edit'>Full Name </td><td class='right_text_pop_edit'><input type='text' value='Hariom Srivastava' />";
	        res+="<div class='edite_name_edit'><div class='name_edit_edit'></div> Edit Name</div></td><td rowspan='3'><img src='images/photo_1.jpg'/>";
	        res+="<div class='save_chnage_pop_edit'>Change Image</div></td></tr>";
	        res+="<tr><td class='right_name_first_pop_edit'>Company</td><td><input type='text'  value='Silvereye IT solution Pvt.Ltd'/></td>";
	        res+="</tr><tr><td class='right_name_first_pop_edit'>Job Title</td>";
	        res+="  <td><input type='text' value='Web Designer'/></td></tr> </table>";
	
			return res;
		  }*/
	
	
	
	
    @RequestMapping(value = "/saveContactForm", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
	public String saveContactForm(ModelMap map, Principal principal,
			HttpServletRequest request)
			  {
		HttpSession hs=request.getSession();
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		
		String res="false";
		String cfolder= request.getParameter("selectedGp");
		String fullname= request.getParameter("fullname");
		String company= request.getParameter("company");
		String job= request.getParameter("job"); 
		String email= request.getParameter("email");
		String web_page= request.getParameter("web_page");
		String note= request.getParameter("note");
		String phone_work= request.getParameter("phone_work");
		String phone_home= request.getParameter("phone_home"); 
		String phone_fax= request.getParameter("phone_fax");
		String phone_mob= request.getParameter("phone_mob");
		String addr_work= request.getParameter("addr_work");
		String addr_home= request.getParameter("addr_home");
		String pre= request.getParameter("pre");
		String fnm= request.getParameter("fnm");
		String mnm= request.getParameter("mnm");
		String lnm= request.getParameter("lmn");
		String suf= request.getParameter("suf");
		String con_img=request.getParameter("con_img");
		byte bt[]=null;
		List <Byte> Bt_img=new ArrayList<Byte>();
		if(con_img!=null && !(con_img.equals("")) && !(con_img.equalsIgnoreCase("null")))
		{
			bt=(byte[])hs.getAttribute("ContactImg");
			hs.removeAttribute("ContactImg");
			/*if(bt!=null)
			{
			Byte[] byteObjects = new Byte[bt.length];

			int i=0;    
			// Associating Byte array values with bytes. (byte[] to Byte[])
			for(byte b: bt)
			byteObjects[i++] = b; 
			
			Bt_img = Arrays.asList(byteObjects);
			}*/
		}
		
		String arr[]=fullname.split(" ");
		if(arr.length==1)
		{
			if(arr[0]!=null && !arr[0].equals(""))
			{
				 pre= "";
				fnm= arr[0];
				mnm= "";
				lnm= "";
				suf="";
			}
			else
			{
				pre= "";
				fnm= "";
				mnm= "";
				lnm= "";
				suf="";
			}
		}
		else if(arr.length==2)
		{
			pre= "";
			fnm= arr[0];
			mnm= "";
			lnm= arr[1];
			suf="";
		}
		else if(arr.length>2)
		{
			int i=0;
			int l=1;
			if(arr[i].equalsIgnoreCase(pre))
				{
				i++;
				fnm=arr[i];
				}
			else
				{
				pre="";
				fnm=arr[i];
				}
			
			
			if(arr[arr.length-l].equalsIgnoreCase(suf))
				{
				
				l++;
				lnm=arr[arr.length-l];
				
				}
			else
				{
				suf="";
				lnm=arr[arr.length-l];
				}
			
			int j=1;
			String mid="";
			for(i++;i< arr.length-l; i++,j++)
				{
				if(arr[i]!=" ")
					{
				if(j==1)
					{
					mid=arr[i];
					}
				else
					{
					mid=mid+" "+arr[i];
					}
					}
				}
			mnm=mid;
		}
		
		
		if(fullname == null)
		{
			fullname = "";
		}
		else
		{
			fullname = fullname.trim();
		}
		
		 CardDav carddav=new CardDav();
		res= carddav.addContactval(surl, id, pass, cfolder, note, fullname, company, job, email, web_page, phone_work, phone_home,phone_mob,phone_fax,  addr_work, addr_home, pre, fnm, mnm, lnm, suf, bt,null);
		
		/*String notes="";
		String keywords="";
		String parentFolder = "/"+id+"/Contacts";
		if(cfolder.contains("/sharedContacts/"))
		{
			parentFolder="/"+id+cfolder;
		}
		else
		{
			parentFolder=parentFolder+"/"+cfolder;
		}
		String fileuserid=id;
		
		String vcffilename="";
		 UUID uuid = UUID.randomUUID();
	     String randomUUIDString = uuid.toString();
	     vcffilename=email+"$"+fullname+"_"+randomUUIDString+".vcf";
	     CreateContactResponse resp= webmailClient.getVCFIOStream(vcffilename,note, fullname, company, job, email, web_page, phone_work, phone_home, phone_fax, phone_mob, addr_work, addr_home, pre, fnm, mnm, lnm, suf,Bt_img);
	     String iostrm=resp.getGetVFCIOStream();
	     InputStream is=IOUtils.toInputStream(iostrm);
	     
	     try {
	    byte[]	 iostream=org.apache.commons.codec.binary.Base64.encodeBase64(IOUtils.toByteArray(is));
	    	 System.out.println("^^^^^^^^^^^^^^^^^^"+resp.getGetVFCIOStream());
		     CreateFileResponse cfileres=fileClient.createFile(vcffilename, parentFolder, fileuserid,pass,  keywords, notes,iostream,0);
		    res=""+ cfileres.isSuccess();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	     */
	     
	     
		 return res;
			  }
	
	
    
    @RequestMapping(value = "/saveToContactFromInbox", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String saveToContactFromInbox(ModelMap map, Principal principal,
			HttpServletRequest request)
			  {
    	HttpSession hs=request.getSession();
    	if(hs==null || hs.getAttribute("id")==null)
    		return "<$expire$>";
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String res="false";
		
		

		String fullname="";
		String email="";
		String sto=request.getParameter("con_id");
		String pre= "";
		String fnm= "";
		String mnm= "";
		String lnm= "";
		String suf="";
		
		boolean st=	sto.contains("<");
		if(st)
		{
			sto=sto.replace(">", "");
			String arr[]=sto.split("<");
			if(arr.length>=2)
			{
				fullname=arr[0].trim();
				email=arr[1].trim();
			}
		}
		else
		{
			email=sto;
			String arr[]=sto.split("@");
			if(arr.length>=1)
			{
				fullname=arr[0];
			}
		}
		
		String arr1[]=email.split("@");
		String arr2[]=id.split("@");
		if(arr1.length==2)
		{
			if(!arr1[1].equalsIgnoreCase(arr2[1]))
			{
				
				
				 try{
					 	int flg=0;
				    	CardDav carddav=new CardDav();
				    	List<CardDavCollection>colst= carddav.getAllContactFolder( "", surl,  id , pass);
				    	for(CardDavCollection crd: colst)
				    	{
				    		VCard[]vcarr= crd.getComponents();
				    		for(VCard c:vcarr)
				    		{
				    			
				    			if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!= null )
				    		       {
				    				 if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue().toString().equalsIgnoreCase(email) )
				    				 {
				    					 flg=1;
				    					 res="already";
											break;
				    				 }
				    		       }
				    			
				    		}
				    		if(flg==1)
				    		{
				    			res="already";
				    			break;
				    		}
				    	}
				    	
				    	if(flg==0)
				    	{
				    		String conid=surl+"/caldav.php/"+id+"/contacts/";
				    		carddav.addAtuoContactval(surl, id, pass, fullname, email, conid);
				    		res="true";
				    	}
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
					 res="false";
				 }
			}
		}
		
		/*String cfolder= "Personal Contacts";
		String con_id= request.getParameter("con_id");
		String email= "";
		String fullname= "";
		String pre= "";
		String fnm= "";
		String mnm= "";
		String lnm= "";
		String suf= "";
	
		List <Byte> Bt_img=new ArrayList<Byte>();
		

		String notes="";
		String keywords="";
		String parentFolder = "/"+id+"/Contacts";
		String fileuserid=id;
		parentFolder=parentFolder+"/"+cfolder;
	
		if(con_id!=null  && con_id!="")
		{
		
		boolean st=	con_id.contains("<");
		if(st)
		{
			con_id=con_id.replace(">", "");
			String arr[]=con_id.split("<");
			if(arr.length>=2)
			{
			fullname=arr[0].trim();
			email=arr[1].trim();
			}
		}
		else
		{
			email=con_id;
			String arr[]=con_id.split("@");
			if(arr.length>=1)
			{
			fullname=arr[0];
			}
		}
		
		String []arr1=id.split("@");
		String []arr2=email.split("@");
		if(!arr1[1].equalsIgnoreCase(arr2[1]))
		{
		
			GetVCFFileResponse fileResponse =fileClient.getVCFFileRequest(parentFolder, id, pass);
			List<VCFFileAtt> vcffileList=fileResponse.getGetVCFFilesByParentFile().getVCFFileListResult().getVCFFileList();
			int flg=0;
			for(VCFFileAtt vfclst: vcffileList )
			{
				if(vfclst.getContactEmail().equalsIgnoreCase(email))
				{
					flg=1;
					break;
				}
			}
			if(flg==0)
			{
		
		String arr[]=fullname.split(" ");
		if(arr.length==1)
		{
			if(arr[0]!=null && !arr[0].equals(""))
			{
				 pre= "";
				fnm= arr[0];
				mnm= "";
				lnm= "";
				suf="";
			}
			else
			{
				pre= "";
				fnm= "";
				mnm= "";
				lnm= "";
				suf="";
			}
		}
		else if(arr.length==2)
		{
			pre= "";
			fnm= arr[0];
			mnm= "";
			lnm= arr[1];
			suf="";
		}
		else if(arr.length>2)
		{
			int i=0;
			int l=1;
			if(arr[i].equalsIgnoreCase(pre))
				{
				i++;
				fnm=arr[i];
				}
			else
				{
				pre="";
				fnm=arr[i];
				}
			
			
			if(arr[arr.length-l].equalsIgnoreCase(suf))
				{
				
				l++;
				lnm=arr[arr.length-l];
				
				}
			else
				{
				suf="";
				lnm=arr[arr.length-l];
				}
			
			int j=1;
			String mid="";
			for(i++;i< arr.length-l; i++,j++)
				{
				if(arr[i]!=" ")
					{
				if(j==1)
					{
					mid=arr[i];
					}
				else
					{
					mid=mid+" "+arr[i];
					}
					}
				}
			mnm=mid;
		}
		
		
		if(fullname == null)
		{
			fullname = "";
		}
		else
		{
			fullname = fullname.trim();
		}
		
		
		String vcffilename="";
		 UUID uuid = UUID.randomUUID();
	     String randomUUIDString = uuid.toString();
	     vcffilename=email+"$"+fullname+"_"+randomUUIDString+".vcf";
	     CreateContactResponse resp= webmailClient.getVCFIOStream(vcffilename,"", fullname, "", "", email, "", "", "", "", "", "", "", pre, fnm, mnm, lnm, suf,Bt_img);
	     String iostrm=resp.getGetVFCIOStream();
	     InputStream is=IOUtils.toInputStream(iostrm);
	     
	     try {
	    byte[]	 iostream=org.apache.commons.codec.binary.Base64.encodeBase64(IOUtils.toByteArray(is));
	    	 System.out.println("^^^^^^^^^^^^^^^^^^"+resp.getGetVFCIOStream());
		     CreateFileResponse cfileres=fileClient.createFile(vcffilename, parentFolder, fileuserid, pass,keywords, notes,iostream,0);
		    res=""+ cfileres.isSuccess();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     res="true";
			}
			else
			{
				res="already";	
			}
		}
		else
		{
			res="already";
		}
		  }*/
	     
	 return res;
			
	
			  }
    
    
    @RequestMapping(value = "/editContactForm",  produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
	public String editContactForm(ModelMap map, Principal principal,
			HttpServletRequest request)
	  {
		HttpSession hs=request.getSession();
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String cfolder=(String)hs.getAttribute("active_contact");
		String gpid=cfolder;
		if(!cfolder.startsWith(surl))
		{
			gpid=surl+cfolder;
		}
		
		String res="false";
		String fullname= request.getParameter("fullname");
		String company= request.getParameter("company");
		String job= request.getParameter("job"); 
		String email= request.getParameter("email");
		String web_page= request.getParameter("web_page");
		String note= request.getParameter("note");
		String phone_work= request.getParameter("phone_work");
		String phone_home= request.getParameter("phone_home"); 
		String phone_fax= request.getParameter("phone_fax");
		String phone_mob= request.getParameter("phone_mob");
		String addr_work= request.getParameter("addr_work");
		String addr_home= request.getParameter("addr_home");
		String pre= request.getParameter("pre");
		String fnm= request.getParameter("fnm");
		String mnm= request.getParameter("mnm");
		String lnm= request.getParameter("lmn");
		String suf= request.getParameter("suf");
		String con_img=request.getParameter("con_img");
		String conuid=request.getParameter("conuid");
		
		
		byte bt[]=null;
		//List <Byte> Bt_img=new ArrayList<Byte>();
		if(con_img!=null && !(con_img.equals("")) && !(con_img.equalsIgnoreCase("null")))
		{
			bt=(byte[])hs.getAttribute("ContactImgEdit");
			hs.removeAttribute("ContactImgEdit");
			/*if(bt!=null)
			{
			Byte[] byteObjects = new Byte[bt.length];

			int i=0;    
			// Associating Byte array values with bytes. (byte[] to Byte[])
			for(byte b: bt)
			byteObjects[i++] = b; 
			
			Bt_img = Arrays.asList(byteObjects);
			}*/
		}
		else
		{
			String Bt_imgcpy=null;
			if(hs.getAttribute("ContactPhotoByte")!=null)
				Bt_imgcpy=hs.getAttribute("ContactPhotoByte").toString();
			//hs.removeAttribute("ContactPhotoByte");
			if(Bt_imgcpy!=null && Bt_imgcpy.length()>0)
			{
				//Bt_img=Bt_imgcpy;
				sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
				try {
					bt = decoder.decodeBuffer(Bt_imgcpy);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					bt=null;
				}
				
			}
		}
		
		String arr[]=fullname.split(" ");
		if(arr.length==1)
		{
			if(arr[0]!=null && !arr[0].equals(""))
			{
				 pre= "";
				fnm= arr[0];
				mnm= "";
				lnm= "";
				suf="";
			}
			else
			{
				pre= "";
				fnm= "";
				mnm= "";
				lnm= "";
				suf="";
			}
		}
		else if(arr.length==2)
		{
			pre= "";
			fnm= arr[0];
			mnm= "";
			lnm= arr[1];
			suf="";
		}
		else if(arr.length>2)
		{
			int i=0;
			int l=1;
			if(arr[i].equalsIgnoreCase(pre))
				{
				i++;
				fnm=arr[i];
				}
			else
				{
				pre="";
				fnm=arr[i];
				}
			
			
			if(arr[arr.length-l].equalsIgnoreCase(suf))
				{
				
				l++;
				lnm=arr[arr.length-l];
				
				}
			else
				{
				suf="";
				lnm=arr[arr.length-l];
				}
			
			int j=1;
			String mid="";
			for(i++;i< arr.length-l; i++,j++)
				{
				if(arr[i]!=" ")
					{
				if(j==1)
					{
					mid=arr[i];
					}
				else
					{
					mid=mid+" "+arr[i];
					}
					}
				}
			mnm=mid;
		}
		
		 CardDav carddav=new CardDav();
			res= carddav.addContactval(surl, id, pass, gpid, note, fullname, company, job, email, web_page, phone_work, phone_home,phone_mob,phone_fax, addr_work, addr_home, pre, fnm, mnm, lnm, suf, bt,conuid);
		if(res.equalsIgnoreCase("true"))
		{
			hs.removeAttribute("ContactPhotoByte");
		}
		
		/*String notes="";
		String keywords="";
		String parentFolder ="/"+id+"/Contacts";
		if(flnm.contains("/sharedContacts/"))
		{
			 parentFolder ="/"+id+"/sharedContacts";
		}
		String fileuserid=id;
		String arrfl[]=flnm.split("/");
	
		
		if(arrfl.length>3)
		{
		parentFolder=parentFolder+"/"+arrfl[3];
		}
	
		String vcffilename="";
		 UUID uuid = UUID.randomUUID();
	     String randomUUIDString = uuid.toString();
	     if(fullname == null)
	     {
	    	 fullname = "";
	     }
	     else
	     {
	    	 fullname = fullname.trim();
	     }
	     vcffilename=email+"$"+fullname+"_"+randomUUIDString+".vcf";
	     CreateContactResponse resp= webmailClient.getVCFIOStream(vcffilename,note, fullname, company, job, email, web_page, phone_work, phone_home, phone_fax, phone_mob, addr_work, addr_home, pre, fnm, mnm, lnm, suf,Bt_img);
	     String iostrm=resp.getGetVFCIOStream();
	     InputStream is=IOUtils.toInputStream(iostrm);
	     
	     try {
	    byte[]	 iostream=org.apache.commons.codec.binary.Base64.encodeBase64(IOUtils.toByteArray(is));
	    	 System.out.println("^^^^^^^^^^^^^^^^^^"+resp.getGetVFCIOStream());
		     CreateFileResponse cfileres=fileClient.createFile(vcffilename, parentFolder, fileuserid,pass, keywords, notes,iostream,0);
		    res=""+ cfileres.isSuccess();
		    

		    fileClient.deleteFile(flnm, id,pass);
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     */
	     

	     
	     
	     
		 return res;
			  }
    
    
	
	
	
	@RequestMapping("/createContactGroup")
	@ResponseBody
	public String createContactGroup(ModelMap map, Principal principal,
			HttpServletRequest request, @RequestParam String folderName)
			  {
		HttpSession hs=request.getSession();
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String proid=(String)hs.getAttribute("caldevProId");
		System.out.println("^^^^^^^fname="+folderName);
		folderName=folderName.trim();
		String res="";
		CardDav carddav=new CardDav();
		String crd=carddav.createContactGp(proid,surl, id, pass,folderName);
		if(crd!=null && crd.length()>0)
		{
			
				res=crd;
			
		}
				//HttpSession hs = request.getSession(false);
				/*String notes="";
				String keywords="";
				String parentFolder = "/"+id+"/Contacts";
				CreateFolderResponse createFolderResponse = folderClient.createFolder(
						folderName , parentFolder,id,pass ,keywords,notes);
				
				Folder folder = createFolderResponse.getFolder();
				String newFolder="";
				if(folder!=null ){
				res="true";
				}else{
					//return "access denied"; 
				}*/
				return res;
	}
	
	@RequestMapping(value = "/getDirectoryUserList",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getDirectoryUserList(ModelMap map, Principal principal,
		HttpServletRequest request) {
		String res=" <table class='con_he_content'>  ";
		//System.out.println("!!!!!!!!!!! directory");
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String ldapurl=(String)hs.getAttribute("ldapurl");
		String ldpabase=(String)hs.getAttribute("ldapbase");
		//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
		GetVCFLdapDirResponse getdirres=webmailClient.getLdapDirectory(ldapurl, id, pass, ldpabase,"*");
		
		List<VCFLdapDirAtt> ldapDirList=getdirres.getGetVCFLdapDirByParentFile().getVCFLdapDirListResult().getVCFLdapDirList();
		
		Collections.sort(ldapDirList,new NPCompare());
		
		for(VCFLdapDirAtt ulst : ldapDirList)
		{
			//System.out.println("!!!!!!!!!!!!!!! name="+ulst.getContactName()+"  email="+ulst.getContactEmail()+" phone="+ulst.getContactPhone()+" addr="+ulst.getContactAddress()+" photo="+ulst.getContactPhoto()); 
			 	  res+="<tr >";
				  res+="  <td class='con_box contact_input'><input class='contact_input_dir' type='checkbox'/></td>";
				  res+="<td class='con_td_img'><img src='images/blank_man.png' /></td>";
		          res+="<td class='con_name'><div class='inner_text'>"+ulst.getContactName()+"</div></td>";
		          res+="<td class='con_email'><div class='inner_text'>"+ulst.getContactEmail()+"</div></td>";
		          res+=" <td class='con_email'><div class='inner_text'>"+ulst.getContactPhone()+"</div></td>";
		          res+="<td class='con_department'><div class='inner_text'>"+ulst.getContactDept()+"</div></td>";
		          res+=" <td class='con_address'><div class='inner_text address_div'>"+ulst.getContactAddress()+"</div></td>";
		          res+="</tr>";
		}
		 res+="  </table>";
		return res;
	}
	
	
	
	@RequestMapping(value = "/getconPopupListContacts",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getconPopupListContacts(ModelMap map, Principal principal, HttpServletRequest request ) 
	{
		
		String res="<table class='con_he_list' style='    width: 100%;    position: relative;    top: 0px;'>     <tbody><tr> <td style='padding-left: 5px;' class='left_head'><input  class='contact_check_all' type='checkbox'></td>";
		 res+="<td class='con_imag_head'><img src='images/white_man.png'></td>   <td class='con_name_head'>Name</td> <td class='con_email'>Email</td>";
		 res+="</tr>  </tbody></table>";
		
		 res+="<table class='con_he_left_list'>  ";
		//String start= request.getParameter("start");
		///String end= request.getParameter("end");
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String cfolder=request.getParameter("fpath");
		if(cfolder==null || cfolder.length()<=0)
		{
			cfolder=surl +"/caldav.php/"+uid+"/contacts/";
		}
		
		
		if(cfolder.equalsIgnoreCase("Directory"))
		{
//			String host=(String)hs.getAttribute("host");
			String id=(String)hs.getAttribute("id");
			String ldapurl=(String)hs.getAttribute("ldapurl");
			String ldpabase=(String)hs.getAttribute("ldapbase");
			//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
			GetVCFLdapDirResponse getdirres=webmailClient.getLdapDirectory(ldapurl, id, pass, ldpabase, "*");
			
			List<VCFLdapDirAtt> ldapDirList=getdirres.getGetVCFLdapDirByParentFile().getVCFLdapDirListResult().getVCFLdapDirList();
			
			Collections.sort(ldapDirList,new NPCompare());
			
			if(ldapDirList != null && !ldapDirList.isEmpty())
			{
				int total =  ldapDirList.size();
				
				for(int i = 0; i < total; i++)
				{
					VCFLdapDirAtt ulst = ldapDirList.get(i);
					String photo=ulst.getContactPhoto();
					String tit="";
							tit=ulst.getContactName().replace(",", "");
							tit=tit.replace("'", "");
						tit=tit.replace("\"", "");
					if(tit.length()>0)
					{
						tit=tit+" <"+ulst.getContactEmail()+">";
					}
					else
					{
						tit=ulst.getContactEmail();
					}
					res+="<tr name='Directory' id='"+ulst.getContactEmail()+"'>";
					res+="  <td class='con_box_left' style='padding-left: 5px;'><input  name='chk_con' value='"+tit+"' type='checkbox'/></td>";
					if(photo!= null && !(photo.equals("")  ))
					{
						res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
					}
					else
					{
						res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
					}
					res+="<td class='con_name_left'><div class='inner_text_left'>"+ulst.getContactName()+"</div></td>";
					res+="<td class='con_email_left'><div class='inner_text_left'>"+ulst.getContactEmail()+"</div></td>";
					res+="  </tr>";
				
				}
				
				
			}
			
		}
		else
		{
			try
			{
			
				String gpid=cfolder;
				if(!cfolder.startsWith(surl))
				{
					gpid=surl+cfolder;
				}
					
				 CardDav carddav=new CardDav();
				 VCard []vcarr=carddav.getAllContactList(surl, uid, pass,gpid);
				 for(VCard vc: vcarr)
				 {
					 String email="";
					 String fn="";
					 String vcid="";
					 String photo="";
						if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!=null)
							 email= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue();
						
						
						
							if(email != null && email.length() > 0)
							{
									if( vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME)!=null)
										fn= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME).getValue();
									
									if( vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN)!=null && fn.length()<=0)
										 fn= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN).getValue();
									
									if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID)!=null)
									 vcid= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID).getValue();
										
									if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO)!=null)
										 photo= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO).getValue();
									if(fn.trim().equals("nullnull"))
									{
										fn="";
									}
									
									String tit="";
									tit=fn.replace(",", "");
									tit=tit.replace("'", "");
									tit=tit.replace("\"", "");
									if(tit.length()>0)
									{
										tit=tit+" <"+email+">";
									}
									else
									{
										tit=email;
									}
									
									res+="<tr name='othercontact' class='del_row'  id='"+vcid+"'>";
									res+="<td class='con_box_left' style='padding-left: 5px;'>";
									
										res+= "<input name='chk_con' value='"+tit+"'  type='checkbox'/>";
								
									res+= "</td>";
									if(photo!= null && !(photo.equals("")  ))
									{
										res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
									}
									else
									{
									res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
									}
									res+="<td class='con_name_left'><div class='inner_text_left'>"+fn+"</div></td>";
									res+="<td class='con_email_left'><div class='inner_text_left'>"+email+"</div></td>";
									res+="</tr>";
									
									
									/*
									res+="<tr name='othercontact' class='del_row'  id='"+vcid+"'>";
									res+="<td class='con_box_left'>";
									
										res+= "<input name='chk_con' value='"+vcid+"' class='contact_input' type='checkbox'/>";
									
									res+= "</td>";
									if(photo!= null && !(photo.equals("")  ))
									{
										res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
									}
									else
									{
									res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
									}
									res+="<td class='con_name_left'><div class='inner_text_left'>"+fn+"</div></td>";
									res+="<td class='con_email_left'><div class='inner_text_left'>"+email+"</div></td>";
									
									res+="</tr>";*/
							}
						}
				
				
    		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	        		 
	        	 
		}  
		
		
		
		res+="  </table> ";
		
		
		
		return res;
		
	}
	
	
	
	
	@RequestMapping(value = "/getconPopupListContactsSearch",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getconPopupListContactsSearch(ModelMap map, Principal principal, HttpServletRequest request ) 
	{
		
		String res="<table class='con_he_list' style='    width: 100%;    position: relative;    top: 0px;'>     <tbody><tr> <td style='padding-left: 5px;' class='left_head'><input  class='contact_check_all' type='checkbox'></td>";
		 res+="<td class='con_imag_head'><img src='images/white_man.png'></td>   <td class='con_name_head'>Name</td> <td class='con_email'>Email</td>";
		 res+="</tr>  </tbody></table>";
		
		 res+="<table class='con_he_left_list'>  ";
		String alpha= request.getParameter("search");
		///String end= request.getParameter("end");
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String cfolder=request.getParameter("fpath");
		if(cfolder==null || cfolder.length()<=0)
		{
			cfolder=surl +"/caldav.php/"+uid+"/contacts/";
		}
		
		if(cfolder.equalsIgnoreCase("Directory"))
		{
//			String host=(String)hs.getAttribute("host");
			String id=(String)hs.getAttribute("id");
			String ldapurl=(String)hs.getAttribute("ldapurl");
			String ldpabase=(String)hs.getAttribute("ldapbase");
			//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
			GetVCFLdapDirResponse getdirres=webmailClient.getLdapDirectory(ldapurl, id, pass, ldpabase, alpha+"*");
			
			List<VCFLdapDirAtt> ldapDirList=getdirres.getGetVCFLdapDirByParentFile().getVCFLdapDirListResult().getVCFLdapDirList();
			
			Collections.sort(ldapDirList,new NPCompare());
			
			if(ldapDirList != null && !ldapDirList.isEmpty())
			{
				int total =  ldapDirList.size();
				
				for(int i = 0; i < total; i++)
				{
					VCFLdapDirAtt ulst = ldapDirList.get(i);
					String photo=ulst.getContactPhoto();
					String tit="";
							tit=ulst.getContactName().replace(",", "");
							tit=tit.replace("'", "");
						tit=tit.replace("\"", "");
					if(tit.length()>0)
					{
						tit=tit+" <"+ulst.getContactEmail()+">";
					}
					else
					{
						tit=ulst.getContactEmail();
					}
					//System.out.println("!!!!!!!!!!!!!!! name="+ulst.getContactName()+"  email="+ulst.getContactEmail()+" phone="+ulst.getContactPhone()+" addr="+ulst.getContactAddress()+" photo="+ulst.getContactPhoto()); 
					res+="<tr name='Directory' id='"+ulst.getContactEmail()+"'>";
					res+="  <td class='con_box_left' style='padding-left: 5px;'><input  name='chk_con' value='"+tit+"' type='checkbox'/></td>";
					if(photo!= null && !(photo.equals("")  ))
					{
						res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
					}
					else
					{
						res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
					}
					res+="<td class='con_name_left'><div class='inner_text_left'>"+ulst.getContactName()+"</div></td>";
					res+="<td class='con_email_left'><div class='inner_text_left'>"+ulst.getContactEmail()+"</div></td>";
					
					res+="  </tr>";
				
				}
				

				
			}
			
		}
		else
		{
			try
			{

				
				String gpid=cfolder;
				if(!cfolder.startsWith(surl))
				{
					gpid=surl+cfolder;
				}
					
				 CardDav carddav=new CardDav();
				 VCard []vcarr=carddav.getAllContactList(surl, uid, pass,gpid);
				 
				 for(VCard vc: vcarr)
				 {
					 String email="";
					 String fn="";
					 String vcid="";
					 String photo="";
						if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!=null)
							 email= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue();
						if( vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME)!=null)
							fn= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME).getValue();
						
						if( vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN)!=null && fn.length()<=0)
							 fn= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN).getValue();
						if(fn.trim().equals("nullnull"))
						{
							fn="";
						}
						
							if((email != null && email.length() > 0) && (email.startsWith(alpha) || (email.toUpperCase()).startsWith(alpha.toUpperCase()) || fn.startsWith(alpha) || (fn.toUpperCase()).startsWith(alpha.toUpperCase())))
							{
									
									
									if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID)!=null)
									 vcid= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID).getValue();
										
									if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO)!=null)
										 photo= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO).getValue();
									
									String tit="";
									tit=fn.replace(",", "");
									tit=tit.replace("'", "");
									tit=tit.replace("\"", "");
									if(tit.length()>0)
									{
										tit=tit+" <"+email+">";
									}
									else
									{
										tit=email;
									}
									
									res+="<tr name='othercontact' class='del_row'  id='"+vcid+"'>";
									res+="<td class='con_box_left' style='padding-left: 5px;'>";
									
										res+= "<input name='chk_con' value='"+tit+"'  type='checkbox'/>";
								
									res+= "</td>";
									if(photo!= null && !(photo.equals("")  ))
									{
										res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
									}
									else
									{
									res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
									}
									res+="<td class='con_name_left'><div class='inner_text_left'>"+fn+"</div></td>";
									res+="<td class='con_email_left'><div class='inner_text_left'>"+email+"</div></td>";
									res+="</tr>";
									
							}
						}
				
				
				
    		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	        		 
	        	 
		}  
		
		
		
		res+="  </table> ";
		
		
		
		return res;
		
	}
	
	
	@RequestMapping(value = "/getContactList",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getContactList(ModelMap map, Principal principal, HttpServletRequest request , @RequestParam(value="pn") int pn) 
	{
		String res="<table class='con_he_left_list'>  ";
		//String start= request.getParameter("start");
		///String end= request.getParameter("end");
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String limitContact=(String)hs.getAttribute("limitContact");
		String surl=(String)hs.getAttribute("caldevUrl");

		/*String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String port=(String)hs.getAttribute("port");*/
		String cfolder=(String)hs.getAttribute("active_contact");
		System.out.println("inbox***********************contact folder="+cfolder);
		System.out.println("inbox***********************contact folder="+pn);
		
		int rpp = Integer.parseInt(limitContact);
		
		JSONObject obj = new JSONObject();
		if(cfolder.equalsIgnoreCase("Directory"))
		{
//			String host=(String)hs.getAttribute("host");
			String id=(String)hs.getAttribute("id");
			String ldapurl=(String)hs.getAttribute("ldapurl");
			String ldpabase=(String)hs.getAttribute("ldapbase");
			
			
			GetVCFLdapDirResponse getdirres=webmailClient.getLdapDirectory(ldapurl, id, pass, ldpabase, "*");
			
			List<VCFLdapDirAtt> ldapDirList=getdirres.getGetVCFLdapDirByParentFile().getVCFLdapDirListResult().getVCFLdapDirList();
			
			Collections.sort(ldapDirList,new NPCompare());
			
			if(ldapDirList != null && !ldapDirList.isEmpty())
			{
				int total =  ldapDirList.size();
				int start = (pn-1)*rpp;
				int  end = start;
				obj.put("total", total);
				obj.put("start", start+1);
				
				for(int i = start; i < start+rpp && i < total; i++)
				{
					VCFLdapDirAtt ulst = ldapDirList.get(i);
					String photo=ulst.getContactPhoto();
					//System.out.println("!!!!!!!!!!!!!!! name="+ulst.getContactName()+"  email="+ulst.getContactEmail()+" phone="+ulst.getContactPhone()+" addr="+ulst.getContactAddress()+" photo="+ulst.getContactPhoto()); 
					res+="<tr name='Directory' id='"+ulst.getContactEmail()+"'>";
					res+="  <td class='con_box_left'><input class='contact_input_dir' type='checkbox'/></td>";
					//ical4j photo has issue. I wiil fix
					if(photo!= null && !(photo.equals("")  ))
					{
						res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
					}
					else
					{
						res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
					}
					res+="<td class='con_name_left'><div class='inner_text_left'>"+ulst.getContactName()+"</div></td>";
					res+="<td class='con_email_left'><div class='inner_text_left'>"+ulst.getContactEmail()+"</div></td>";
					/* res+=" <td class='con_email'><div class='inner_text'>"+ulst.getContactPhone()+"</div></td>";
			          res+="<td class='con_department'><div class='inner_text'>"+ulst.getContactDept()+"</div></td>";
			          res+=" <td class='con_address'><div class='inner_text address_div'>"+ulst.getContactAddress()+"</div></td>";
					 */
					res+="  </tr>";
					end++;
				}
				
//				FOR(VCFLDAPDIRATT ULST : LDAPDIRLIST)
//				{
//				}	
				obj.put("end", end);
			}
			else
			{
				obj.put("start", 0);
				obj.put("end", 0);
			}
		}
		else
		{
			try
			{
				
				//GetVCFFileResponse fileResponse =fileClient.getVCFFileRequest("/"+uid+cfolder, uid,pass);
				//List<VCFFileAtt> vcffileList=fileResponse.getGetVCFFilesByParentFile().getVCFFileListResult().getVCFFileList();
				
				//System.out.println("path is "+"/"+uid+cfolder);
				String gpid=cfolder;
				if(!cfolder.startsWith(surl))
				{
					gpid=surl+cfolder;
				}
					
				 CardDav carddav=new CardDav();
				 VCard []vcarr=carddav.getAllContactList(surl, uid, pass,gpid);
				 List<VCard> vclist = Arrays.asList(vcarr); 
				 try
				 {
				 Collections.sort(vclist,new NPCompareCon());
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 }
				int total =  vcarr.length;
				int start = (pn-1)*rpp;

				obj.put("total", total);
				obj.put("start", start+1);
				
				
				
				if(vcarr != null && total>0)
				{
					int  end = start;
					for(int i = start; i < start+rpp && i < total; i++)
					{
						String email= "";
						String fn="";
						String cno="";
						String vcid= "";
						String photo="";
						VCard vc=vclist.get(i);
						if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!=null)
							 email= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue();
						
						if( vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME)!=null)
						 fn= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME).getValue();
						
						if( vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN)!=null && fn.length()<=0)
							 fn= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN).getValue();
						
						/*if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.TEL)!=null)
						 cno= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.TEL).getValue();*/
						
						if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID)!=null)
						 vcid= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID).getValue();
							
						if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO)!=null)
							 photo= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO).getValue();
						
						if(fn.trim().equals("nullnull"))
						{
							fn="";
						}
						res+="<tr name='othercontact' class='del_row'  id='"+vcid+"'>";
						res+="<td class='con_box_left'>";
						
							res+= "<input name='chk_con' value='"+vcid+"' class='contact_input' type='checkbox'/>";
						
						res+= "</td>";
						if(photo!= null && !(photo.equals("")  ))
						{
							res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
						}
						else
						{
						res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
						}
						res+="<td class='con_name_left'><div class='inner_text_left'>"+fn+"</div></td>";
						res+="<td class='con_email_left'><div class='inner_text_left'>"+email+"</div></td>";
						
						res+="</tr>";
						end++;
					}
					obj.put("end", end);
				}
				else
				{
					obj.put("start", 0);
					obj.put("end", 0);
				}
    		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	        		 
	        	 
		}  
		
		
		
		res+="  </table> ";
		obj.put("contacts", res);
		
		
		return obj.toJSONString();
		
	}
	
	
	
	@RequestMapping(value = "/getContactListAlphaSearch", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getContactListAlphaSearch(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		String res="<table class='con_he_left_list'>  ";
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		
		String cfolder=(String)hs.getAttribute("active_contact");
		String alpha=request.getParameter("alpha");
		String surl=(String)hs.getAttribute("caldevUrl");
		//System.out.println("inbox***********************contact folder="+cfolder);
		
		JSONObject obj = new JSONObject();
		
		if(cfolder.equalsIgnoreCase("Directory"))
		{
			String host=(String)hs.getAttribute("host");
			String id=(String)hs.getAttribute("id");
			String ldapurl=(String)hs.getAttribute("ldapurl");
			String ldpabase=(String)hs.getAttribute("ldapbase");
			//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
			
			
			
			GetVCFLdapDirResponse getdirres=webmailClient.getLdapDirectory(ldapurl, id, pass, ldpabase, alpha+"*");
			
			List<VCFLdapDirAtt> ldapDirList=getdirres.getGetVCFLdapDirByParentFile().getVCFLdapDirListResult().getVCFLdapDirList();
			
			
			for(VCFLdapDirAtt ulst : ldapDirList)
			{
				String photo=ulst.getContactPhoto();
				res+="<tr name='Directory' id='"+ulst.getContactEmail()+"'>";
				res+="  <td class='con_box_left'><input class='contact_input_dir' type='checkbox'/></td>";
				if(photo!= null && !(photo.equals("")  ))
				{
					res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
				}
				else
				{
					res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
				}
				res+="<td class='con_name_left'><div class='inner_text_left'>"+ulst.getContactName()+"</div></td>";
				res+="<td class='con_email_left'><div class='inner_text_left'>"+ulst.getContactEmail()+"</div></td>";
				res+="  </tr>";
			}
				
				
			
		}
		else
		{
			try
			{
			
				
				String gpid=cfolder;
				if(!cfolder.startsWith(surl))
				{
					gpid=surl+cfolder;
				}
					
				 CardDav carddav=new CardDav();
				 VCard []vcarr=carddav.getAllContactList(surl, uid, pass,gpid);
				 for(VCard vc: vcarr)
				 {
					 String email="";
					 String fn="";
					 String cno="";
					 String vcid="";
					 String photo="";
						if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!=null)
							 email= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue();
						if( vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME)!=null)
							 fn= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME).getValue();
							
							if( vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN)!=null && fn.length()<=0)
								 fn= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN).getValue();
							
							/*if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.TEL)!=null)
							 cno= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.TEL).getValue();*/
							if(fn.trim().equals("nullnull"))
							{
								fn="";
							}
						
						
							if(email.startsWith(alpha) || (email.toUpperCase()).startsWith(alpha.toUpperCase()) || fn.startsWith(alpha) || (fn.toUpperCase()).startsWith(alpha.toUpperCase()) || cno.startsWith(alpha) || (cno.toUpperCase()).startsWith(alpha.toUpperCase()))
							{
								
									
									if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID)!=null)
									 vcid= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID).getValue();
										
									if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO)!=null)
										 photo= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO).getValue();
									
									res+="<tr name='othercontact' class='del_row'  id='"+vcid+"'>";
									res+="<td class='con_box_left'>";
									
										res+= "<input name='chk_con' value='"+vcid+"' class='contact_input' type='checkbox'/>";
									
									res+= "</td>";
									if(photo!= null && !(photo.equals("")  ))
									{
										res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
									}
									else
									{
									res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
									}
									res+="<td class='con_name_left'><div class='inner_text_left'>"+fn+"</div></td>";
									res+="<td class='con_email_left'><div class='inner_text_left'>"+email+"</div></td>";
									
									res+="</tr>";
								
							}
						
				 }
				 
				 
				
			}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
		}  
		
		
		
		res+="  </table> ";
		
		obj.put("contacts", res);
		return obj.toJSONString();
		
		
	}
	
	
	@RequestMapping(value = "/getContactListAlpha", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getContactListAlpha(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		String res="<table class='con_he_left_list'>  ";
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		
		String cfolder=(String)hs.getAttribute("active_contact");
		String alpha=request.getParameter("alpha");
		String surl=(String)hs.getAttribute("caldevUrl");
		//System.out.println("inbox***********************contact folder="+cfolder);
		
		JSONObject obj = new JSONObject();
		
		if(cfolder.equalsIgnoreCase("Directory"))
		{
			String host=(String)hs.getAttribute("host");
			String id=(String)hs.getAttribute("id");
			String ldapurl=(String)hs.getAttribute("ldapurl");
			String ldpabase=(String)hs.getAttribute("ldapbase");
			//System.out.println("!!!!!!!!!!!! ldap dn="+ldpabase);
			
			
			
			GetVCFLdapDirResponse getdirres=webmailClient.getLdapDirectory(ldapurl, id, pass, ldpabase, alpha+"*");
			
			List<VCFLdapDirAtt> ldapDirList=getdirres.getGetVCFLdapDirByParentFile().getVCFLdapDirListResult().getVCFLdapDirList();
			
			
			for(VCFLdapDirAtt ulst : ldapDirList)
			{
				String photo=ulst.getContactPhoto();
				res+="<tr name='Directory' id='"+ulst.getContactEmail()+"'>";
				res+="  <td class='con_box_left'><input class='contact_input_dir' type='checkbox'/></td>";
				if(photo!= null && !(photo.equals("")  ))
				{
					res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
				}
				else
				{
					res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
				}
				res+="<td class='con_name_left'><div class='inner_text_left'>"+ulst.getContactName()+"</div></td>";
				res+="<td class='con_email_left'><div class='inner_text_left'>"+ulst.getContactEmail()+"</div></td>";
				/* res+=" <td class='con_email'><div class='inner_text'>"+ulst.getContactPhone()+"</div></td>";
		          res+="<td class='con_department'><div class='inner_text'>"+ulst.getContactDept()+"</div></td>";
		          res+=" <td class='con_address'><div class='inner_text address_div'>"+ulst.getContactAddress()+"</div></td>";
				 */
				res+="  </tr>";
			}
				
				
			
		}
		else
		{
			try
			{
			
				
				String gpid=cfolder;
				if(!cfolder.startsWith(surl))
				{
					gpid=surl+cfolder;
				}
					
				 CardDav carddav=new CardDav();
				 VCard []vcarr=carddav.getAllContactList(surl, uid, pass,gpid);
				 for(VCard vc: vcarr)
				 {
					 String email="";
					 String fn="";
					 String cno="";
					 String vcid="";
					 String photo="";
						if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL)!=null)
							 email= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.EMAIL).getValue();
						if( vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME)!=null)
							 fn= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.NAME).getValue();
							
							if( vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN)!=null && fn.length()<=0)
								 fn= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.FN).getValue();
							
							/*if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.TEL)!=null)
							 cno= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.TEL).getValue();*/
							
							if(fn.trim().equals("nullnull"))
							{
								fn="";
							}
						
						if(alpha.equals("123"))
						{
							if((email != null && email.length() > 0 && Character.isDigit(email.charAt(0))) || (fn != null && email.length() > 0 && Character.isDigit(fn.charAt(0))) )
							{
								
									if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID)!=null)
									 vcid= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID).getValue();
										
									if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO)!=null)
										 photo= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO).getValue();
									res+="<tr name='othercontact' class='del_row'  id='"+vcid+"'>";
									res+="<td class='con_box_left'>";
									
										res+= "<input name='chk_con' value='"+vcid+"' class='contact_input' type='checkbox'/>";
									
									res+= "</td>";
									if(photo!= null && !(photo.equals("")  ))
									{
										res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
									}
									else
									{
									res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
									}
									res+="<td class='con_name_left'><div class='inner_text_left'>"+fn+"</div></td>";
									res+="<td class='con_email_left'><div class='inner_text_left'>"+email+"</div></td>";
									
									res+="</tr>";
							}
						}
						else
						{
							if(email.startsWith(alpha) || (email.toUpperCase()).startsWith(alpha.toUpperCase()) || fn.startsWith(alpha) || (fn.toUpperCase()).startsWith(alpha.toUpperCase()))
							{
								
									
									if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID)!=null)
									 vcid= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID).getValue();
										
									if(vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO)!=null)
										 photo= vc.getProperty(net.fortuna.ical4j.vcard.Property.Id.PHOTO).getValue();
									
									res+="<tr name='othercontact' class='del_row'  id='"+vcid+"'>";
									res+="<td class='con_box_left'>";
									
										res+= "<input name='chk_con' value='"+vcid+"' class='contact_input' type='checkbox'/>";
									
									res+= "</td>";
									if(photo!= null && !(photo.equals("")  ))
									{
										res+="<td class='con_imag_left'><img src='data:image/jpg;base64,"+photo+"' /></td>"; 
									}
									else
									{
									res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
									}
									res+="<td class='con_name_left'><div class='inner_text_left'>"+fn+"</div></td>";
									res+="<td class='con_email_left'><div class='inner_text_left'>"+email+"</div></td>";
									
									res+="</tr>";
								
							}
						}
						
				 }
				 
				 

					/*if(alpha.equals("123"))
					{
						for(VCFFileAtt vfclst: vcffileList )
						{
							if(vfclst.getContactEmail() != null && vfclst.getContactEmail().length() > 0 && Character.isDigit(vfclst.getContactEmail().charAt(0)))
							{
								res+=" <tr name='othercontact' class='del_row'  id='/"+uid+cfolder+"/"+vfclst.getContactFileName()+"'>";
								res+="<td class='con_box_left'>";
								if(!cfolder.startsWith("/sharedContacts/"))
								{
									res+= "<input name='chk_con' value='/"+uid+cfolder+"/"+vfclst.getContactFileName()+"' class='contact_input' type='checkbox'/>";
								}
								res+= "</td>";
								res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
								res+="<td class='con_name_left'><div class='inner_text_left'>"+vfclst.getContactName()+"</div></td>";
								res+="<td class='con_email_left'><div class='inner_text_left'>"+vfclst.getContactEmail()+"</div></td>";
								   res+=" <td class='con_email'><div class='inner_text'>"+vfclst.getContactPhone()+"</div></td>";
			       		          res+="<td class='con_department'><div class='inner_text'>"+vfclst.getContactDept()+"</div></td>";
			       		          res+=" <td class='con_address'><div class='inner_text address_div'>"+vfclst.getContactAddress()+"</div></td>";
								 
								res+="  </tr>";
							}
						}
					}
					else
					{
						for(VCFFileAtt vfclst: vcffileList )
						{
							if(vfclst.getContactEmail().startsWith(alpha) || vfclst.getContactEmail().startsWith(alpha.toLowerCase()) || vfclst.getContactEmail().startsWith(alpha.toUpperCase()) || (vfclst.getContactEmail().toUpperCase()).startsWith(alpha.toUpperCase())  || (vfclst.getContactEmail().toLowerCase()).startsWith(alpha.toLowerCase()) )
							{
								
								res+=" <tr name='othercontact' class='del_row'  id='/"+uid+cfolder+"/"+vfclst.getContactFileName()+"'>";
								res+="<td class='con_box_left'>";
								if(!cfolder.startsWith("/sharedContacts/"))
								{
									res+= "<input name='chk_con' value='/"+uid+cfolder+"/"+vfclst.getContactFileName()+"' class='contact_input' type='checkbox'/>";
								}
								res+= "</td>";
								res+="<td class='con_imag_left'><img src='images/blank_man.png' /></td>";
								res+="<td class='con_name_left'><div class='inner_text_left'>"+vfclst.getContactName()+"</div></td>";
								res+="<td class='con_email_left'><div class='inner_text_left'>"+vfclst.getContactEmail()+"</div></td>";
								   res+=" <td class='con_email'><div class='inner_text'>"+vfclst.getContactPhone()+"</div></td>";
		       		          res+="<td class='con_department'><div class='inner_text'>"+vfclst.getContactDept()+"</div></td>";
		       		          res+=" <td class='con_address'><div class='inner_text address_div'>"+vfclst.getContactAddress()+"</div></td>";
								 
								res+="  </tr>";
							}
							
						}
						
					}*/
				
			}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
		}  
		
		
		
		res+="  </table> ";
		
		obj.put("contacts", res);
		return obj.toJSONString();
		
		
	}
	
	
	
	@RequestMapping(value = "/setGroupSelVal",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getSetGroupSelVal(ModelMap map, Principal principal,
			HttpServletRequest request) {
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String proid=(String)hs.getAttribute("caldevProId");
		String res="<select id='gp_select'>";
		
		res+="<option value='"+surl+"/caldav.php/"+uid+"/contacts/'>Personal Contacts</option>";
		res+="<option value='"+surl+"/caldav.php/"+uid+"/collected/'>Collected Contacts</option>";
		res+="<option value='Directory'>Directory</option>";
		
		
		
		
         CardDav carddav=new CardDav();
         List<CardDavCollection> cards=carddav.getAllContactFolder(proid,surl, uid, pass);
         for(CardDavCollection crd : cards)
			{
				
				String cpath=crd.getPath();
				if(cpath!=null && !cpath.startsWith(surl))
				{
					cpath=surl+cpath;
				}
				if(!((crd.getDisplayName().equals("Personal Contacts") && cpath.endsWith("/contacts/")) || (crd.getDisplayName().equals("Collected Contacts") && cpath.endsWith("/collected/")))) 
				{
					res+="<option value='"+cpath+"'>"+crd.getDisplayName()+"</option>";
				}
			}
		
		/*res+="<option value='Personal Contacts'>Personal Contacts</option>";
		 String path="/"+uid+"/Contacts";
	 		GetFolderResponse folderResponse = folderClient.getFolderRequest(path,uid,pass);
	 		List<Folder> folderList = folderResponse.getGetFoldersByParentFolder().getFolderListResult().getFolderList();
	 		for ( Folder fEntry : folderList) {
	        	// System.out.println(fileEntry.getName());
	        	 if(!(fEntry.getFolderName().equalsIgnoreCase("Personal Contacts") || fEntry.getFolderName().equalsIgnoreCase("Collected Contacts") || fEntry.getFolderName().equalsIgnoreCase("Directory")))
	        	 {
	        		 res+="<option value='"+fEntry.getFolderName()+"'>"+fEntry.getFolderName()+"</option>";
	        	 }
	         
	    }*/
		
		
		 res+="</select>";
		return res;
	}
	
	
	@RequestMapping(value = "/conAllFolderList",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String conAllFolderList(ModelMap map, Principal principal,
			HttpServletRequest request) {
		String res1="";
		String res2="";
	/*	
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
	
        String path="/"+uid+"/Contacts";
  		GetFolderResponse folderResponse = folderClient.getFolderRequest(path,uid,pass);
  		List<Folder> folderList = folderResponse.getGetFoldersByParentFolder().getFolderListResult().getFolderList();
 		for ( Folder fEntry : folderList) {
 	        	 if(!(fEntry.getFolderName().equalsIgnoreCase("Personal Contacts") || fEntry.getFolderName().equalsIgnoreCase("Collected Contacts") || fEntry.getFolderName().equalsIgnoreCase("Directory")))
 	        	 {
 	        		if(res2.equals(""))
 	        		{
 	        		 res1+=","+fEntry.getFolderName();
 	        	 }
 	         
 	    }
 		
 		

         path="/"+uid+"/sharedContacts";
         GetSharedFoldersByPathResponse sharedfolderResponse = folderClient.getSharedFoldersByPathRequest(uid, pass, path);//   getSharedFoldersRequest(path.substring(1),pass);
		 if(sharedfolderResponse.getGetSharedFoldersByPath().getFolderListResult()!=null){
	 			List<Folder> sharedfolderList = sharedfolderResponse.getGetSharedFoldersByPath().getFolderListResult().getFolderList();
			for ( Folder fEntry : sharedfolderList) {
			if(res2.equals(""))
			{
				res2=fEntry.getFolderName()	;
			}
			else
			{
				res2+=","+fEntry.getFolderName();
			}
		         
		    }}
		*/
		return res1+"<$nirbhay$>"+res2;
	}
	
	
	
	
	@RequestMapping(value = "/getconPopupList",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getconPopupList(ModelMap map, Principal principal,
			HttpServletRequest request) {
		//System.out.println("!!!!!!!!!!!!!!!!!contacts");
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String proid=(String)hs.getAttribute("caldevProId");
		String res="<select id='conPopupList' onchange='getAllPopupContacts(this.value)'>";
		res+="<option value='"+surl+"/caldav.php/"+uid+"/contacts/'>Personal Contacts</option>";
		res+="<option value='"+surl+"/caldav.php/"+uid+"/collected/'>Collected Contacts</option>";
		res+="<option value='Directory'>Directory</option>";
		
		
		
		
         CardDav carddav=new CardDav();
         List<CardDavCollection> cards=carddav.getAllContactFolder(proid,surl, uid, pass);
         for(CardDavCollection crd : cards)
			{
				
				String cpath=crd.getPath();
				if(cpath!=null && !cpath.startsWith(surl))
				{
					cpath=surl+cpath;
				}
				if(!((crd.getDisplayName().equals("Personal Contacts") && cpath.endsWith("/contacts/")) || (crd.getDisplayName().equals("Collected Contacts") && cpath.endsWith("/collected/")))) 
				{
					res+="<option value='"+cpath+"'>"+crd.getDisplayName()+"</option>";
				}
			}
		

        /* path="/"+uid+"/sharedContacts";
 		
         GetSharedFoldersByPathResponse sharedfolderResponse = folderClient.getSharedFoldersByPathRequest(uid, pass, path);//   getSharedFoldersRequest(path.substring(1),pass);
 		
 			
		 if(sharedfolderResponse.getGetSharedFoldersByPath().getFolderListResult()!=null){
	 			List<Folder> sharedfolderList = sharedfolderResponse.getGetSharedFoldersByPath().getFolderListResult().getFolderList();
			for ( Folder fEntry : sharedfolderList) {
			 	
				res+="<option value='/sharedContacts/"+fEntry.getFolderName()+"'>"+fEntry.getFolderName()+"("+fEntry.getCreatedBy()+")</option>";
		         
		    }}*/
		
    
			res+="</select>";
		return res;
		
	}
	
	
	
	@RequestMapping(value = "/getWbmailContactFolder",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getWbmailContactFolder(ModelMap map, Principal principal,
			HttpServletRequest request) {
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String proid=(String)hs.getAttribute("caldevProId");
		String res="<ul>";
		res+=" <li style='cursor: pointer;' onclick='getSelContactUserList(this.id)' id='"+surl+"/caldav.php/"+uid+"/contacts/' ><img src='images/blank_man.png' class='icon_con' />";
		res+=" <span>Personal Contacts</span> <div class='clear'></div></li>";
         res+=" <li style='cursor: pointer;'  onclick='getSelContactUserList(this.id)' id='"+surl+"/caldav.php/"+uid+"/collected/' >    <img src='images/colt_con.png' class='collect_con col_con' />";
         res+=" <span>Collected Contacts</span>   <div class='clear'></div>    </li>";
         res+=" <li style='cursor: pointer;'  onclick='getSelContactUserList(this.id)' id='Directory' onclick='getDirectoryUserList()'> <img src='images/contact_dir.png' class='icon_con col_con' />";
         res+="<span>Directory</span>    <div class='clear'></div>  </li>";
		
		
         CardDav carddav=new CardDav();
         List<CardDavCollection> cards=carddav.getAllContactFolder(proid,surl, uid, pass);
         for(CardDavCollection crd : cards)
			{
				//System.out.println("coll : " + crd.getDisplayName()+"id:"+crd.getId());
				String cpath=crd.getPath();
				if(cpath!=null && !cpath.startsWith(surl))
				{
					cpath=surl+cpath;
				}
				if(!((crd.getDisplayName().equals("Personal Contacts") && cpath.endsWith("/contacts/")) || (crd.getDisplayName().equals("Collected Contacts") && cpath.endsWith("/collected/")))) 
				{
					res+="<li style='cursor: pointer;'  onclick='getSelContactUserList(this.id)' id='"+cpath+"'> <img src='images/group_con.png' class='icon_con col_con group_img'> <span>"+crd.getDisplayName()+"</span><div class='clear'></div></li>";
				}
			}
      /*   String path="/"+uid+"/Contacts";
  		GetFolderResponse folderResponse = folderClient.getFolderRequest(path,uid,pass);
  		List<Folder> folderList = folderResponse.getGetFoldersByParentFolder().getFolderListResult().getFolderList();
  		
  		
 		for ( Folder fEntry : folderList) {
 	        	// System.out.println(fileEntry.getName());
 	        	 if(!(fEntry.getFolderName().equalsIgnoreCase("Personal Contacts") || fEntry.getFolderName().equalsIgnoreCase("Collected Contacts") || fEntry.getFolderName().equalsIgnoreCase("Directory")))
 	        	 {
 	        		 res+="<li style='cursor: pointer;'  onclick='getSelContactUserList(this.id)' id='/Contacts/"+fEntry.getFolderName()+"'> <img src='images/group_con.png' class='icon_con col_con group_img'> <span>"+fEntry.getFolderName()+"</span><img id='hidcon_/Contacts/"+fEntry.getFolderName()+"' onclick='delConFldr(this.id)' src='images/tool.png' class='delete_contact_row'><div class='clear'></div></li>";
 	        		 res+="<li style='cursor: pointer;'  onclick='getSelContactUserList(this.id)' id='/Contacts/"+fEntry.getFolderName()+"'> <img src='images/group_con.png' class='icon_con col_con group_img'> <span>"+fEntry.getFolderName()+"</span><div class='clear'></div></li>";
 	        	 }
 	         
 	    }*/
 		
 		

         //path="/"+uid+"/sharedContacts";
 		// folderResponse = folderClient.getFolderRequest(path,uid,pass);
 		// folderList = folderResponse.getGetFoldersByParentFolder().getFolderListResult().getFolderList();
 		
 		
       //  GetSharedFoldersByPathResponse sharedfolderResponse = folderClient.getSharedFoldersByPathRequest(uid, pass, path);//   getSharedFoldersRequest(path.substring(1),pass);
 		
 		res+="</ul>";
		 res+="<$nirbhay$><ul>";
		
		 List<Shared> srdlst=sharedDao.getSharedDetailForLeft(uid, false,true);
			if(srdlst.size()>0)
			{
				 
				CardDavStore store=carddav.connect(proid, surl, uid, pass);
			for(Shared srd: srdlst)
			{
				if(srd!=null)
				{
					
					String caluid=srd.getCalUID();
					String calurl=surl+"/caldav.php/"+srd.getUserfrom()+"/"+caluid+"/";
					CardDavCollection crdcol;
					try {
						crdcol = store.getCollection(calurl);
					
					String disp=crdcol.getDisplayName();
					String woner=srd.getUserfrom();

			String nm="notmanage";
			String per="Read";
				if(srd.getWriteaccess().equalsIgnoreCase("us") )
				{
					 nm="manage";
					 per="Full Access";
				}
				else if(srd.getWriteaccess().equalsIgnoreCase("uw"))
				{
					 nm="halfmanage";
					 per="Read/Write";
				}
		        	String tit="Group Name:"+disp+"\nGroup Owner:"+woner+"\nPermission:"+per;
		        		 res+="<li style='cursor: pointer;' name='"+nm+"'  onclick='getSelContactUserList(this.id)' id='"+calurl+"'> <img src='images/group_con.png' class='icon_con col_con group_img'> <span title='"+tit+"'>"+disp+"("+woner+")</span><div class='clear'></div></li>";
		        	
					} catch (ObjectStoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ObjectNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			}
	
    
			res+="</ul>";
		return res;
		
	}
	
	@RequestMapping(value = "/takeActionConSubs")
	@ResponseBody
	public String takeActionConSubs( ModelMap map, HttpServletResponse response,
			HttpServletRequest request) {
		String st="true";
		String multipath=request.getParameter("conid");
		String calurl=multipath;
		String subs=request.getParameter("subs");
		HttpSession hs = request.getSession();
		String userid = (String) hs.getAttribute("id");
	
					
		if(multipath.endsWith("/"))
		{
			multipath=multipath.substring(0, multipath.length()-1);
		}
		String[] mularr = multipath.split("/");
		String caluid=mularr[mularr.length-1];
		
		Shared srdd=sharedDao.getSharedDetailForWhich(userid, caluid, false);
		if(srdd!=null)
		{
			if(subs.equalsIgnoreCase("subs"))
			{
			srdd.setIsdisplay(true);
			}
			else if(subs.equalsIgnoreCase("unsubs"))
			{
			srdd.setIsdisplay(false);
			}
			int flg=sharedDao.addSharedDetail(srdd);
			if(flg<=0)
			{
				st="false";
			}
			
		}
		else
		{
			st="false";
		}
		
		return st;
	}
	
	@RequestMapping(value = "/getLeftSharedCon", method = RequestMethod.GET)
	@ResponseBody
	public String getLeftSharedCon(ModelMap map, Principal principal,
			HttpServletRequest request) {
		String res="<ul>";
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String proid=(String)hs.getAttribute("caldevProId");
		 List<Shared> srdlst=sharedDao.getSharedDetailForLeft(uid, false,true);
			if(srdlst.size()>0)
			{
				 CardDav carddav=new CardDav();
				CardDavStore store=carddav.connect(proid, surl, uid, pass);
			for(Shared srd: srdlst)
			{
				if(srd!=null)
				{
					
					String caluid=srd.getCalUID();
					String calurl=surl+"/caldav.php/"+srd.getUserfrom()+"/"+caluid+"/";
					CardDavCollection crdcol;
					try {
						crdcol = store.getCollection(calurl);
					
					String disp=crdcol.getDisplayName();
					String woner=srd.getUserfrom();

			String nm="notmanage";
			String per="Read";
				if(srd.getWriteaccess().equalsIgnoreCase("us") )
				{
					 nm="manage";
					 per="Full Access";
				}
				else if(srd.getWriteaccess().equalsIgnoreCase("uw"))
				{
					 nm="halfmanage";
					 per="Read/Write";
				}
		        	String tit="Group Name:"+disp+"\nGroup Owner:"+woner+"\nPermission:"+per;
		        		 res+="<li style='cursor: pointer;' name='"+nm+"'  onclick='getSelContactUserList(this.id)' id='"+calurl+"'> <img src='images/group_con.png' class='icon_con col_con group_img'> <span title='"+tit+"'>"+disp+"("+woner+")</span><div class='clear'></div></li>";
		        	
					} catch (ObjectStoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ObjectNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			}
	
 
			res+="</ul>";
		return res;
	}
	
	/*@RequestMapping(value = "/shareConFldr",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String shareConFldr(ModelMap map, Principal principal,
			HttpServletRequest request) {
		
		String shareid=request.getParameter("shareid") ;
		String confldr=request.getParameter("confldr") ;
		
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		System.out.println("current folder or file value is : "+confldr);
	    AssignSinglePermissionResponse response = folderClient
	            .assignSinglePermission("/"+uid+confldr, uid,pass, shareid+"/sharedContacts", "uw");
	    String resp = response.getAssignSinglePermissionResponse();
		return resp;
		
	}*/
	
	
	@RequestMapping(value = "/delConFldr",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String delConFldr(ModelMap map, Principal principal,
			HttpServletRequest request) {
		String resp="";
		String confldr=request.getParameter("conid") ;
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		 CardDav carddav=new CardDav();
		 boolean st=carddav.delContact(surl, uid, pass, confldr);
		 if(st)
		 {
			 resp=surl +"/caldav.php/"+uid+"/contacts/";
			 hs.setAttribute("active_contact",resp);
		 }
		/*DeleteFileResponse resF=fileClient.deleteFile("/"+uid+confldr, uid,pass);
		
		hs.setAttribute("active_contact", "/Contacts/Personal Contacts");*/
		return resp;
		
	}
	
	
	@RequestMapping(value = "/deleteSelContacts",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String deleteSelContacts(ModelMap map, Principal principal,
			HttpServletRequest request) {
		String resp="true";
		boolean st=false;
		String confile=request.getParameter("con_file") ;
		//System.out.println(confile);
		if(confile!=null && !confile.equals(""))
		{
		String array_del[]=confile.split(",");
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String cfolder=(String)hs.getAttribute("active_contact");
		String gpid=cfolder;
		String surl=(String)hs.getAttribute("caldevUrl");
		if(!cfolder.startsWith(surl))
		{
			gpid=surl+cfolder;
		}
		 CardDav carddav=new CardDav();
		
		
		for(int i=0;i< array_del.length;i++)
		{
		//fileClient.deleteFile(array_del[i], uid, pass);
			boolean sst= carddav.delContactMem(surl, uid, pass, gpid, array_del[i]);
			if(!st)
				st=sst;
		}
		}
		if(!st)
		{
			resp="false";
		}
		return resp;
		
	}
	
	
	
	/*@RequestMapping(value = "/exportVCFFile", produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String exportVCFFile(ModelMap map, Principal principal, HttpServletRequest request) 
	{
		String res="";
		// 'group_name': fldr, 'export_type': selectedVal, 'con_arr' : JSON.stringify(con_arr)
		String exp_type=request.getParameter("export_type");
		String group_name=request.getParameter("group_name");
		//String con_arr[]=request.getParameterValues("con_arr");
		String con_str=request.getParameter("con_arr");
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		if(exp_type.equalsIgnoreCase("contact"))
		{
			if(con_str!=null && con_str.length()>4)
			{
				try
				{
					con_str=con_str.replace("[\"", "");
					con_str=con_str.replace("\"]", "");
					String con_arr[]=con_str.split("\",\"");
					
					 FileOutputStream fileOuputStream = new FileOutputStream("D:\\new3.txt"); 
					
					for(int i=0;i<con_arr.length;i++)
					{
						GetFileByPathResponse fpr= fileClient.getFileByPath(con_arr[i], uid, pass);
						byte[] fl= fpr.getFile().getFileContent();
						fileOuputStream.write(fl);
					}
				
					fileOuputStream.close();
			
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else if(exp_type.equalsIgnoreCase("group"))
		{
			try
			{
			GetVCFFileResponse fileResponse =fileClient.getVCFFileRequest("/"+uid+"/Contacts/"+group_name, uid,pass);
			List<VCFFileAtt> vcffileList=fileResponse.getGetVCFFilesByParentFile().getVCFFileListResult().getVCFFileList();
			FileOutputStream fileOuputStream = new FileOutputStream("D:\\new3.txt"); 
			for(VCFFileAtt vfclst: vcffileList )
			{
				GetFileByPathResponse fpr= fileClient.getFileByPath("/"+uid+"/Contacts/"+group_name+"/"+vfclst.getContactFileName(), uid,pass);
				byte[] fl= fpr.getFile().getFileContent();
				fileOuputStream.write(fl);
			}
			fileOuputStream.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(exp_type.equalsIgnoreCase("all"))
		{
			try
			{
			GetFolderResponse folderResponse = folderClient.getFolderRequest("/"+uid+"/Contacts",uid,pass);
    		List<Folder> folderList = folderResponse.getGetFoldersByParentFolder().getFolderListResult().getFolderList();
    		FileOutputStream fileOuputStream = new FileOutputStream("D:\\new3.txt"); 
    		for(Folder fEntry : folderList)
				{
    			GetVCFFileResponse fileResponse =fileClient.getVCFFileRequest("/"+uid+"/Contacts/"+fEntry.getFolderName(), uid,pass);
    			List<VCFFileAtt> vcffileList=fileResponse.getGetVCFFilesByParentFile().getVCFFileListResult().getVCFFileList();
    			for(VCFFileAtt vfclst: vcffileList )
    			{
    				GetFileByPathResponse fpr= fileClient.getFileByPath("/"+uid+"/Contacts/"+fEntry.getFolderName()+"/"+vfclst.getContactFileName(), uid,pass);
    				byte[] fl= fpr.getFile().getFileContent();
    				fileOuputStream.write(fl);
    			}
				}
    		fileOuputStream.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return res;
	}*/
	
	
	@RequestMapping(value = "/exportVCFDownload", produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	public void exportVCFDownload(ModelMap map, Principal principal, HttpServletRequest request,HttpServletResponse response) 
	{
		String res="";
		// 'group_name': fldr, 'export_type': selectedVal, 'con_arr' : JSON.stringify(con_arr)
		String group_name=request.getParameter("fldr");
		
		HttpSession hs=request.getSession();
		String uid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		
			try
			{
				String str="";	
				CardDav cdav=new CardDav();
				VCard []vcarr=cdav.getAllContactList(surl, uid, pass, group_name);
				for(VCard vc: vcarr)
				{
				   str+=	vc.toString();
				}
				
				//GetVCFFileResponse fileResponse =fileClient.getVCFFileRequest("/"+uid+group_name, uid,pass);
				//List<VCFFileAtt> vcffileList=fileResponse.getGetVCFFilesByParentFile().getVCFFileListResult().getVCFFileList();
				// UUID uuid = UUID.randomUUID();
			   //  String randomUUIDString = uuid.toString();
			     String nm="contacts.vcf";
				//String outPath="C:\\Users\\nirbhay\\AppData\\Local\\Temp\\"+nm;
				//String outPath="/tmp/tomcat7-tomcat7-tmp/"+nm;
			    // String realPath = request.getServletContext().getRealPath("/");
			    // String outPath  = realPath+"WEB-INF/view/jsp/temp/"+nm;
			     
				/*FileOutputStream fileOuputStream = new FileOutputStream(outPath); 
				for(VCFFileAtt vfclst: vcffileList )
				{
					GetFileByPathResponse fpr= fileClient.getFileByPath("/"+uid+group_name+"/"+vfclst.getContactFileName(), uid,pass);
					byte[] fl=org.apache.commons.codec.binary.Base64.decodeBase64(fpr.getFile().getFileContent());
					fileOuputStream.write(fl);
				}
				fileOuputStream.close();*/
			     //byte []btarr=str.getBytes();
			     
				 String headerKey = "Content-Disposition";
	             String headerValue = String.format("attachment; filename=\"%s\"",  nm);
	             response.setHeader(headerKey, headerValue);
	             InputStream input = IOUtils.toInputStream(str);
	             OutputStream output = response.getOutputStream();
	             byte[] buffer = new byte[4096];
	
	             int byteRead;
	
	             while ((byteRead = input.read(buffer)) != -1) {
	                output.write(buffer, 0, byteRead);
	             }
	             input.close();
	             output.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	//	return "exportVCFDownload";
	}
	@Autowired  
	private SharedDao sharedDao; 
	
	
	@RequestMapping(value = "/manageContactSubs")
	@ResponseBody
	public String manageContactSubs( ModelMap map, HttpServletResponse response,
			HttpServletRequest request) {
		String st="<table width='100%'><thead> <tr><th>Group</th><th>Owner</th><th>Permission</th><th>Action</th></thead></tr><tbody>";
		HttpSession hs = request.getSession();
		String id = (String) hs.getAttribute("id");
		String pass = (String) hs.getAttribute("pass");
		String surl=(String)hs.getAttribute("caldevUrl");
		String proid=(String)hs.getAttribute("caldevProId");
		List<Shared> srdlst=sharedDao.getSharedDetailForLeft(id, false);
		if(srdlst.size()>0)
		{
			CardDav cldav=new CardDav();
			CardDavStore store=cldav.connect(proid, surl, id, pass);
		for(Shared srd: srdlst)
		{
			if(srd!=null)
			{
			
				String caluid=srd.getCalUID();
				String calurl=surl+"/caldav.php/"+srd.getUserfrom()+"/"+caluid+"/";
				String per="Read";
				if(srd.getWriteaccess().equalsIgnoreCase("us"))
				{
					per="Full Access";
				}
				else if(srd.getWriteaccess().equalsIgnoreCase("uw"))
				{
					per="Read/Write";
				}
				try {
					CardDavCollection coll=store.getCollection(calurl);
					st+="<tr><td>"+coll.getDisplayName()+"</td>";
					st+="<td>"+srd.getUserfrom()+"</td>";
					st+="<td>"+per+"</td>";
					st+="<td><a name='"+calurl+"' onclick='takeConSubsAction(this.name,\"subs\")' style='cursor:pointer;'>Subscribe</a>"
							+ "/<a name='"+calurl+"' onclick='takeConSubsAction(this.name,\"unsubs\")' style='cursor:pointer;'>Unsubscribe</a></td></tr>";
					
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			
			}
			
		}
		
		}
		else
		{
			st="<table width='100%'> <tbody><tr><td>You don't have any shared contact.</td></tr>";
		}
		
		st+="</tbody></table>";
		return st;
	}
	
@RequestMapping(value = "/sharingPopupCon",  produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
	public String sharingPopupCon(ModelMap map, HttpServletRequest request)
	{
		HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String id=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String act_fldr=request.getParameter("path");
		
		String multipath = act_fldr;
		String calpath=multipath;
		String surl=(String)hs.getAttribute("caldevUrl");
		if(!calpath.startsWith(surl))
			calpath= surl+calpath;
			
		if(multipath.endsWith("/"))
		{
			multipath=multipath.substring(0, multipath.length()-1);
		}
		String[] mularr = multipath.split("/");
		String caluid=mularr[mularr.length-1];
		List<Shared> srdlst= sharedDao.getSharedDetailForPopup(id, caluid,false);
		
		map.addAttribute("sharedlist", srdlst);
	
		return "contactshare";
	}

@RequestMapping("/assignSinglePermission")
@ResponseBody
public String assignSinglePermission(ModelMap map, Principal principal,
        @RequestParam String user, @RequestParam String value,@RequestParam String multipath,
        HttpServletRequest request) {
    try
    {
    	HttpSession hs=request.getSession();
		String host=(String)hs.getAttribute("host");
		String userid=(String)hs.getAttribute("id");
		String pass=(String)hs.getAttribute("pass");
		String resp="";
		if (userid == null) 
		{
			return "ajaxTrue";
		}
		else 
		{
		

			
			String calpath=multipath;
			String surl=(String)hs.getAttribute("caldevUrl");
			if(!calpath.startsWith(surl))
				calpath= surl+calpath;
				
			if(multipath.endsWith("/"))
			{
				multipath=multipath.substring(0, multipath.length()-1);
			}
			String[] mularr = multipath.split("/");
			String caluid=mularr[mularr.length-1];
			
			
			String[] userarr = user.split(",");
			String[] valuearr = value.split(",");
			for (int i = 0; i < userarr.length; i++) 
			{
				String whitchid=userarr[i];
				if(whitchid!=null && whitchid.length()>0)
				{
					String per="ur";
					if(i< valuearr.length)
					{
						per=valuearr[i];
					}
					
					CalDav cd=new CalDav();
					boolean st= cd.shareCollection(calpath, userid, pass, surl, whitchid, per) ;
					if(st)
					{
						int sn=sharedDao.searchSharedDetail(userid, whitchid, caluid,false);
						Shared shrd=new Shared();
						if(sn>0)
						{
							shrd.setSno(sn);
						}
						shrd.setCalUID(caluid);
						shrd.setIscalendar(false);
						shrd.setIsdisplay(true);
						shrd.setUserfrom(userid);
						shrd.setUserwhich(whitchid);
						shrd.setWriteaccess(per);
						int snsrd=sharedDao.addSharedDetail(shrd);
					}
					
				}
			}
			return resp;

		}
   
	}catch(Exception e)
    {
	
	    return "ajaxTrue";
	}
}


@RequestMapping("/removeAssignedPermission")
@ResponseBody
public String removeAssignedPermission( HttpServletRequest request, ModelMap map, Principal principal, @RequestParam String folderPath,
		 @RequestParam String remid, @RequestParam String sno)
{
try
{
	String resp = "";
	HttpSession hs = request.getSession();
	String userid = (String) hs.getAttribute("id");
	String pass = (String) hs.getAttribute("pass");
	String calpath=folderPath;
	String surl=(String)hs.getAttribute("caldevUrl");
	if(!calpath.startsWith(surl))
		calpath= surl+calpath;
		
	if(folderPath.endsWith("/"))
	{
		folderPath=folderPath.substring(0, folderPath.length()-1);
	}
	String[] mularr = folderPath.split("/");
	String caluid=mularr[mularr.length-1];
	CalDav cd=new CalDav();
	boolean st= cd.shareCollection(calpath, userid, pass, surl, remid, "") ;
	if(st)
	{
		int sn=0;
		if(sno!=null && sno.length()>0)
		{
			sn=Integer.parseInt(sno.trim());
		}
		sharedDao.deleteSharedDetail(remid, caluid, sn,false);
	}
	
	return resp;

} catch (Exception e) {
	return "ajaxTrue";
}

}
	
}



