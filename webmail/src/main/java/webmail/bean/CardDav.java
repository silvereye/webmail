package webmail.bean;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.webdav.DavServletResponse;
import org.apache.jackrabbit.webdav.client.methods.DeleteMethod;
import org.apache.jackrabbit.webdav.property.DavProperty;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertyNameSet;
import org.apache.jackrabbit.webdav.property.DavPropertySet;
import org.apache.jackrabbit.webdav.property.DefaultDavProperty;
import org.apache.jackrabbit.webdav.property.PropEntry;

import ezvcard.Ezvcard;
import ezvcard.io.text.VCardReader;
import ezvcard.property.FormattedName;
import ezvcard.property.Organization;
import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.connector.dav.CalDavCalendarStore;
import net.fortuna.ical4j.connector.dav.CalDavConstants;
import net.fortuna.ical4j.connector.dav.CardDavCollection;
import net.fortuna.ical4j.connector.dav.CardDavStore;
import net.fortuna.ical4j.connector.dav.DavClient;
import net.fortuna.ical4j.connector.dav.PathResolver;
import net.fortuna.ical4j.connector.dav.method.GetMethod;
import net.fortuna.ical4j.connector.dav.property.CalDavPropertyName;
import net.fortuna.ical4j.connector.dav.property.CardDavPropertyName;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.Standard;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.TzOffsetFrom;
import net.fortuna.ical4j.model.property.TzOffsetTo;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.Property.Id;
import net.fortuna.ical4j.vcard.VCard;
import net.fortuna.ical4j.vcard.VCardBuilder;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.property.Email;
import net.fortuna.ical4j.vcard.property.Fn;
import net.fortuna.ical4j.vcard.property.Name;
import net.fortuna.ical4j.vcard.property.Revision;
import net.fortuna.ical4j.vcard.property.Telephone;
import net.fortuna.ical4j.vcard.property.Version;
import webmail.wsdl.DisplayContact;
import webmail.wsdl.GetFullContactDetailResponse;

public class CardDav implements Serializable
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
	
	
	private HttpClient getHttpClient(CardDavStore store) {

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
	
	public CardDavStore connect(String proid,String surl, String id ,String pass){
		CardDavStore store=null;
		try
		{
		URL url = new URL(surl);
	    store = new CardDavStore("", url, PathResolver.DAVICAL) ;
		store.connect(id, pass.toCharArray());
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
	
	
	public List<CardDavCollection> getAllContactFolder(String proid,String surl, String id ,String pass){
		List<CardDavCollection> cards=null;
		
		CardDavStore store=connect(proid,surl, id, pass);
		 try {
			cards=store.getCollections();
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
		
		return cards;
	}
	
	
	public String  createContactGp(String proid,String surl, String id ,String pass,String grpnm)
	{
		String res="";
		String uidnm=UUID.randomUUID().toString();
		String url=surl+"/caldav.php/"+id+"/"+uidnm;
		
		boolean st=true;
		String body="";
	
		body+="<?xml version='1.0' encoding='utf-8' ?> <D:mkcol xmlns:D='DAV:'  xmlns:C='urn:ietf:params:xml:ns:carddav'>";
		body+="<D:set>   <D:prop>   <D:resourcetype>      <D:collection/> <C:addressbook/>         </D:resourcetype>";
		body+="<D:displayname>"+grpnm+"</D:displayname> <C:addressbook-description xml:lang='en'   >My address book.</C:addressbook-description>";
		body+="      </D:prop>     </D:set>   </D:mkcol>";
		
		
		try
		{
		String str=id+":"+pass;
		byte[]   bytesEncoded = org.apache.commons.codec.binary.Base64.encodeBase64( str .getBytes());
		String auth=new String(bytesEncoded );
		URL obj = new URL(url);
		res=url+"/";
		if(url.startsWith("https://"))
		{
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestProperty("X-HTTP-Method-Override", "MKCOL");
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
		con.setRequestProperty("X-HTTP-Method-Override", "MKCOL");
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
		if(responseCode>=400)
		{
			res="";
		}
		con.disconnect();
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			res="";
		}
	
		
		
		return res;
	}
	
	public String  addContactval(String surl, String id ,String pass,String grpnm,String note,String fullname,String company,String job,String email,String web_page,String phone_work,String phone_home, String phone_mob, String phone_fax,String addr_work,String addr_home,String pre,String fnm,String mnm,String lnm,String suf, byte[] con_img, String cuid)
	{
		String st="true";
		//add vcard
				CardDavStore store=connect("", surl, id, pass);
				try {
					CardDavCollection crd=store.getCollection(grpnm);
					
					VCard vcard = new VCard();
					vcard.getProperties().add(Version.VERSION_4_0);
					vcard.getProperties().add(new Revision(new DateTime()));
					if(cuid==null || cuid.length()<=0)
					{
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Uid(new URI(UUID.randomUUID().toString())));
					}
					else
					{
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Uid(new URI(cuid)));
					}
					
					
					if(note!=null && note.length()>0)
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Note(note));
					
					if(fullname!=null && fullname.length()>0)
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Name(fullname));
					
					if(company!=null && company.length()>0)
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Org(company));
					
					if(job!=null && job.length()>0)
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Title(job));
					
					if(email!=null && email.length()>0)
					{
						List<Parameter> emailtype =  new ArrayList<Parameter>();
						emailtype.add(Type.WORK);
						vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Email(emailtype,email));
					}
					
					if(web_page!=null && web_page.length()>0)
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Url(new URI(web_page)));
					
					Type tp=new Type("WORK,HOME,CELL,FAX");
					
			
					if(phone_work!=null && phone_work.length()>0)
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Telephone(phone_work,tp.FACTORY.createParameter("WORK")));
					
					if(phone_home!=null && phone_home.length()>0)
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Telephone(phone_home,tp.FACTORY.createParameter("HOME")));
					
					if(phone_mob!=null && phone_mob.length()>0)
						vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Telephone(phone_mob,tp.FACTORY.createParameter("CELL")));	
					
					if(phone_fax!=null && phone_fax.length()>0)
						vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Telephone(phone_fax,tp.FACTORY.createParameter("FAX")));
						
					if(addr_work!=null && addr_work.length()>0)
					{
						/*String addl="";
						if(addr_work.contains(" "))
						{
							int ln=addr_work.lastIndexOf(" ");
							addl=addr_work.substring(ln);
							addr_work=addr_work.substring(0, ln);
						}
						else
						{
							addl=addr_work;
							addr_work="";
						}*/
						vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Address(null,null,addr_work,null,null,null," ",Type.WORK));
					}
					
					if(addr_home!=null && addr_home.length()>0)
					{
					/*String addl="";
						if(addr_home.contains(" "))
						{
							int ln=addr_home.lastIndexOf(" ");
							addl=addr_home.substring(ln);
							addr_home=addr_home.substring(0, ln);
						}
						else
						{
							addl=addr_home;
							addr_home="";
						}*/
							
						vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Address(null,null,addr_home,null,null,null," ",Type.HOME));
					}
					
					if(con_img!=null)
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Photo(con_img));
				
					
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.N(lnm,fnm,null,null,null));
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Fn(fullname));
					
					
						crd.addCard(vcard);
					
					
					
				} catch (ObjectStoreException e) {
					// TODO Auto-generated catch block
					st="false";
					e.printStackTrace();
				} catch (ObjectNotFoundException e) {
					// TODO Auto-generated catch block
					st="false";
					e.printStackTrace();
				}
				 catch (Exception e) {
						// TODO Auto-generated catch block
					 st="false";
						e.printStackTrace();
					}
				try{
					//store.disconnect();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
		
		return st;
	}
	
	
	public String  addAtuoContactval(String surl, String id ,String pass,String fullname,String email, String conid)
	{
		String st="true";
		//add vcard
				CardDavStore store=connect("", surl, id, pass);
				try {
					CardDavCollection crd=store.getCollection(conid);
					
					VCard vcard = new VCard();
					vcard.getProperties().add(Version.VERSION_4_0);
					vcard.getProperties().add(new Revision(new DateTime()));
					
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Uid(new URI(UUID.randomUUID().toString())));
					
					
					
					if(fullname!=null && fullname.length()>0)
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Name(fullname));
					
					
					
					if(email!=null && email.length()>0)
					{
						List<Parameter> emailtype =  new ArrayList<Parameter>();
						emailtype.add(Type.WORK);
						vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Email(emailtype,email));
					}
					
					
					vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Fn(fullname));
					
					
						crd.addCard(vcard);
					
					
					
				} catch (ObjectStoreException e) {
					// TODO Auto-generated catch block
					st="false";
					e.printStackTrace();
				} catch (ObjectNotFoundException e) {
					// TODO Auto-generated catch block
					st="false";
					e.printStackTrace();
				}
				 catch (Exception e) {
						// TODO Auto-generated catch block
					 st="false";
						e.printStackTrace();
					}
				try{
					//store.disconnect();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
		return st;
	}
	
	public VCard [] getAllContactList(String surl, String id ,String pass,String grpid)
	{
		VCard []vc=null;
		CardDavStore store=connect("",surl, id, pass);
		try {
			CardDavCollection crd=store.getCollection(grpid);
			
		 	vc=crd.getComponents();
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
		return vc;
	}
	
	
	public boolean delContact(String surl, String id ,String pass,String grpid)
	{
		boolean st=true;
		CardDavStore store=connect("",surl, id, pass);
		try {
			CardDavCollection crd=store.getCollection(grpid);
			crd.delete();
		 	
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			st=false;
			e.printStackTrace();
		}
		
		try{
			//store.disconnect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return st;
	}
	
	public boolean delContactMem(String surl, String id ,String pass,String grpid, String cuid)
	{
		boolean st=true;
		CardDavStore store=connect("",surl, id, pass);
		try {
			//CardDavCollection crd=store.getCollection(grpid);
			DeleteMethod deleteMethod = new DeleteMethod( grpid+ cuid + ".vcf");
	
				 store.getClient().execute(deleteMethod);
		        } 
		catch (IOException e) {
			e.printStackTrace();
			st=false;
        }
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			st=false;
		}
		try{
			//store.disconnect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return st;
	}
	
	public String importContacts(InputStream inputstream,String proid,String surl, String id ,String pass,String grpnm)
	{
	String res="true";
	List<VCard> cards = new ArrayList<VCard>();
		try {
			
			MyVCardBuilder builder = new MyVCardBuilder(inputstream);
	    	
			while (true) {
	    	    final VCard card = builder.build(false);
	    	    if (card == null) {
	    	        break;
	    	    } else {
	    	        cards.add(card);
	    	    }
	    	}
	    	
	  	}
		catch(Exception ee)
		{
			ee.printStackTrace();
			res="Error: this contact (vCard) is not compliant with RFC 2426!";
		}
		
		try{
			
			if(cards!=null && res.equalsIgnoreCase("true"))
			{
				CardDavStore store=connect("", surl, id, pass);
				CardDavCollection crd=store.getCollection(grpnm);
				for(VCard vc: cards)
				{
		    		 net.fortuna.ical4j.vcard.property.Uid uid = (net.fortuna.ical4j.vcard.property.Uid)vc.getProperty(Id.UID);
		    		 if(uid==null)
		    		 {
		    			 vc.getProperties().add(new net.fortuna.ical4j.vcard.property.Uid(new URI(UUID.randomUUID().toString())));
		    		 }
					 crd.addCard(vc);		 
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			res="false";
		}
		try
		{
			inputstream.close();
		}
		catch(Exception e)
		{}
		return res;
	}
	
	public List<VCard> vparse(InputStream inputstream)
	{
	String res="true";
	List<VCard> cards = new ArrayList<VCard>();
		try {
			
			MyVCardBuilder builder = new MyVCardBuilder(inputstream);
	    	
			while (true) {
	    	    final VCard card = builder.build(false);
	    	    if (card == null) {
	    	        break;
	    	    } else {
	    	        cards.add(card);
	    	    }
	    	}
	    	
	  	}
		catch(Exception ee)
		{
			ee.printStackTrace();
			res="Error: this contact (vCard) is not compliant with RFC 2426!";
		}
		
		try {
			inputstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cards;
	}
	
	public GetFullContactDetailResponse getAllValueContact(String surl, String id ,String pass,String grpid, String vcid)
	{
		String res="";
		GetFullContactDetailResponse response = new GetFullContactDetailResponse();
		DisplayContact dc=new DisplayContact();
		CardDavStore store=connect("",surl, id, pass);
		try {
			
			String url=grpid+ vcid + ".vcf";
			String str=id+":"+pass;
			byte[]   bytesEncoded = org.apache.commons.codec.binary.Base64.encodeBase64( str .getBytes());
			String auth=new String(bytesEncoded );
			URL obj = new URL(url);
			///URLConnection conn = obj.openConnection();
			
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestProperty("Authorization", "Basic "+auth);
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			
			String etag = conn.getHeaderField("ETag");
		
			
			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			/*BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine+"\n");
			}
			in.close();*/
			res=IOUtils.toString(conn.getInputStream());
			conn.disconnect();
			
			if(res!=null && res.length()>0)
			{
 				DisplayEZVcard dezvc=new DisplayEZVcard();
				dc=dezvc.dispContactDetails(res);
			}
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		response.setGetFullContactDetail(dc);
			return response;
			//print result
		//	System.out.println(response.toString());
			//MyVCardBuilder builder = new MyVCardBuilder(IOUtils.toInputStream(response.toString()));
			//VCard card = builder.build(true);
			
			
			
			//String res="true";
			//List<VCard> cards = vparse(conn.getInputStream());
			
	/*	InputStream is= IOUtils.toInputStream(response.toString());
			
			 VCardReader vcardReader = new VCardReader(is);
			 ezvcard.VCard vcard = Ezvcard.parse(is).first();
			 System.out.println(vcard);
			 
			 
			 
			 FormattedName fn=vcard.getFormattedName();
			 String fnn=fn.getValue();*/
			 
			
			
			
			 
			 
			 
			 
			 
			/*	try {
					
					MyVCardBuilder builder = new MyVCardBuilder(conn.getInputStream());
			    	
					while (true) {
			    	    final VCard card = builder.build(false);
			    	    if (card == null) {
			    	        break;
			    	    } else {
			    	        cards.add(card);
			    	    }
			    	}
			    	
			  	}
				catch(Exception ee)
				{
					ee.printStackTrace();
					///res="Error: this contact (vCard) is not compliant with RFC 2426!";
				}*/
				
			
	
		       /* String path =store. getPath();
		        if (!path.endsWith("/")) {
		            path = path.concat("/");
		        }
		        GetMethod method = new GetMethod(grpid+ vcid + ".vcf");
		        try {
		        	store.getClient().execute(method);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        if (method.getStatusCode() == DavServletResponse.SC_OK) {
		            try {
		              Calendar cd=  method.getCalendar();
		               System.out.println(cd);
		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } catch (ParserException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		        } else if (method.getStatusCode() == DavServletResponse.SC_NOT_FOUND) {
		           
		        }
		      */
		   
			/*CardDavCollection crd=store.getCollection(grpid);
		
		 	VCard []vcarr=crd.getComponents();
		 	for(VCard c: vcarr) {
		 		String vcuid="";
		 		if(c.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID)!=null)
		 			vcuid= c.getProperty(net.fortuna.ical4j.vcard.Property.Id.UID).getValue();
		 	    if(vcuid.equals(vcid)) 
		 	    {
		 	       vc = c;
		 	     
		 	        break;
		 	    }
		 	}
		 	
		} catch (ObjectStoreException e) {
	// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return vc;*/
	}
	
	public void connect(){
		
		try 
		{
			CardDavStore store = new CardDavStore("", url, PathResolver.DAVICAL);
			store.connect("piyush@silvereye.in", "yahoo@2009".toCharArray());
			
			List<CardDavCollection> colls =  store.getCollections();
			
			for(CardDavCollection coll : colls)
			{
				System.out.println("coll : " + coll.getDisplayName());
				for(VCard  card : coll.getComponents())
				{
					System.out.println("properties" + card.getProperties());
				}

				
				VCard vcard = new VCard();
				vcard.getProperties().add(Version.VERSION_4_0);
				
				List<Parameter> emailtype =  new ArrayList<Parameter>();
				emailtype.add(Type.WORK);
//				emailtype.add(Type.FACTORY.);
//				vcard.getProperties().add(new Email(emailtype ,"amar@silvereye.in"));
				vcard.getProperties().add(new Fn("Amar "));
				vcard.getProperties().add(new Name("Amar lal bharti"));
				vcard.getProperties().add(new Telephone("998530184", Type.HOME));
				vcard.getProperties().add(new Revision(new DateTime()));
				vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Uid(new URI(UUID.randomUUID().toString())));
				
				coll.addCard(vcard);

			}
			
			/*CardDavCollection contacts = store.getCollection("http://caldav.silvereye.in:81/caldav.php/piyush@silvereye.in/contacts");
			if(contacts != null)
			{
				VCard vcard = new VCard();
				vcard.getProperties().add(Version.VERSION_4_0);
				
				List<Parameter> emailtype =  new ArrayList<Parameter>();
				emailtype.add(Type.WORK);
//				emailtype.add(Type.FACTORY.);
//				vcard.getProperties().add(new Email(emailtype ,"amar@silvereye.in"));
				vcard.getProperties().add(new Fn("Amar "));
				vcard.getProperties().add(new Name("Amar lal bharti"));
				vcard.getProperties().add(new Telephone("998530184", Type.HOME));
				vcard.getProperties().add(new Revision(new DateTime()));
				vcard.getProperties().add(new net.fortuna.ical4j.vcard.property.Uid(new URI(UUID.randomUUID().toString())));
				
				contacts.addCard(vcard);
			}*/
			
			DavPropertySet properties = new DavPropertySet();

		  
			store.addCollection("http://caldav.silvereye.in:81/caldav.php/piyush@silvereye.in/contacts");
			
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
}
