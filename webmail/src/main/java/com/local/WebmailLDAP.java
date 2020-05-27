package com.local;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import webmail.wsdl.*;

public class WebmailLDAP {

	
	public  DirContext getConnection(String url,String uid,String password, String base)
	{
		    DirContext ctx=null;
		    try
		    {
		    	String username="";
		    	/*String arr[]=uid.split("@");
		    	String dom="";
		    	if(arr!=null && arr.length==2)
		    	{
		    		dom=arr[1];
		    	}*/
		   // String base="ou=Users,domainName="+dom+",o=domains,"+mbase;
		    username="mail="+uid+","+base;
		 //   System.out.println("@@@@@@@@@@@@@@@@@@ ldap user="+username);
		    Hashtable env = new Hashtable();
		    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		    env.put(Context.PROVIDER_URL, url); // LDAP host and base
		    env.put("java.naming.ldap.attributes.binary", "jpegPhoto");
		    // LDAP authentication options
		    env.put(Context.SECURITY_AUTHENTICATION, "simple");
		    env.put(Context.SECURITY_PRINCIPAL, username);
		    env.put(Context.SECURITY_CREDENTIALS, password);

		     ctx = new InitialDirContext(env);
		
		    }
		    catch(Exception e)
		    {
		    	System.out.print(e.toString());
		    	e.printStackTrace();
		    	
		    }
		    return ctx;
	}
	
	public void closeConn(DirContext ctx)
	{
		try
	    {
	   if(ctx!=null)
	   {
	     ctx.close();
	   }
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	//System.out.print(e.toString());
	    	
	    }
	}
	
	
	
	public VCFLdapDirListReturn getVCFLdapDirAtt(String ldapurl, String uid, String pass, String base, String searchbase)
	{
		
		VCFLdapDirListReturn FileList1 =new VCFLdapDirListReturn();
		ArrayOfVCFLdapDir Files =new ArrayOfVCFLdapDir();
		
		boolean status=true;
		DirContext ctx=null;
		 try	
		 {
		ctx=getConnection(ldapurl,uid,pass,base);
		
		SearchControls constraints = new SearchControls(); 
		constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		String attrList[] = {"cn","mail","mobile","homePhone","telephoneNumber","postalAddress","postalCode","jpegPhoto","department","enabledService","accountStatus"}; 
		constraints.setReturningAttributes(attrList);
		NamingEnumeration results = null;
		
			String searchptr="(|(mail="+searchbase+")(accountStatus=active))";
			results =ctx.search(base,"mail="+searchbase, constraints);
	
		
		int f=0;
		int x=0;
		
		while (results.hasMore()) {
			f=1;
			VCFLdapDirAtt vcfldapatt= new VCFLdapDirAtt();
			int book=0;
			String acc="";
			String mail="";
			String photo="";
			String fn="";
			String mob="";
			String hmob="";
			String tel="";
			String dept="";
			String addr="";
			String addrpcode="";
			byte []jpegBytes1=null;
			SearchResult si =(SearchResult)results.next();
			String ck=si.getName();
			//System.out.println("<br><br><br>"+ck);
			Attributes attrs = si.getAttributes();
			String arr[]=ck.split(",");
			int l=arr.length;

			    if (attrs == null) {
			       System.out.println("   No attributes");
			        continue;
			    }
			    
			    
			    
			    	NamingEnumeration ae = attrs.getAll(); 
			    	while (ae.hasMoreElements()) {
			        Attribute attr =(Attribute)ae.next();
			        String id = attr.getID();
			        Enumeration vals = attr.getAll();
			        while (vals.hasMoreElements())
			        {
			        //String str= vals.nextElement().toString();
			       
			        if(id.equalsIgnoreCase("cn"))
			        {
			        fn=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("mail"))
			        {
			        mail=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("jpegPhoto"))
			        {
			        	jpegBytes1 = (byte[]) vals.nextElement();
			        	 if(jpegBytes1!=null)
			        	 {
			        		 photo= new sun.misc.BASE64Encoder().encode(jpegBytes1);
			        	 }
			        }
			        else if(id.equalsIgnoreCase("mobile"))
			        {
			        mob=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("homePhone"))
			        {
			        hmob=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("telephoneNumber"))
			        {
			        tel=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("postalAddress"))
			        {
			        addr=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("postalCode"))
			        {
			        	addrpcode=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("department"))
			        {
			        dept=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("accountStatus"))
			        {
			        acc=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("enabledService"))
			        {
			        	String str=vals.nextElement().toString();
			        	if(str.equalsIgnoreCase("displayedInGlobalAddressBook"))
			        	{
			        	book=1;
			        	}
			        }
			        
			        
			     }
			    }

			if(mail.indexOf("@")>0 && acc.equalsIgnoreCase("active") && book==1)
			{
				String adr=addr;
				vcfldapatt.setContactName(fn);
				vcfldapatt.setContactEmail(mail);
				if(!addrpcode.equals(""))
				{
					adr+=", "+addrpcode;
				}
				 
				String mb="";
				int chk=0;
				if(mob != null && !(mob.equalsIgnoreCase("")))
				{
				mb=mob;	
				chk++;
				}
				if(hmob != null && !(mob.equalsIgnoreCase("")))
				{
					if(mb == null || (mb.equalsIgnoreCase("")))
					{	
						mb=hmob;	
					}
					chk++;
				}
				if(tel != null && !(tel.equalsIgnoreCase("")))
				{
					if(mb == null || (mb.equalsIgnoreCase("")))
					{	
						mb=tel;	
					}
					chk++;
				}
				
				if(chk>1)
				{
					chk--;
					mb=mb+"(+"+chk+")";
				}
				
				vcfldapatt.setContactAddress(adr);
				vcfldapatt.setContactPhoto(photo);
				vcfldapatt.setContactDept(dept);
				vcfldapatt.setContactPhone(mb);
				Files.getVCFLdapDirList().add(vcfldapatt);
			}
			
			}
			/*if(f==0)
			{
			//out.print("Search not found");
			
			}*/
		
			
			}
			catch(Exception e)
			{
				System.out.print(e);
			}
		finally
		{
			if(ctx!=null)
			{
				closeConn(ctx);
			}
				
		}
		
		 FileList1.setVCFLdapDirListResult(Files);	
		 FileList1.setVCFLdapDirSuccess(status);	
		
			return FileList1;	
			
	
	}
	
	
	public VCFLdapDirListReturn getLdapListAtt(String ldapurl, String uid, String pass, String base,String listbase, String searchbase)
	{
		
		VCFLdapDirListReturn FileList1 =new VCFLdapDirListReturn();
		ArrayOfVCFLdapDir Files =new ArrayOfVCFLdapDir();
		
		boolean status=true;
		DirContext ctx=null;
		 try	
		 {
		ctx=getConnection(ldapurl,uid,pass,base);
		
		SearchControls constraints = new SearchControls(); 
		constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		String attrList[] = {"cn","mail","accountStatus"}; 
		constraints.setReturningAttributes(attrList);
		NamingEnumeration results = null;
		
			String searchptr="(|(mail="+searchbase+")(accountStatus=active))";
			results =ctx.search(listbase,"mail="+searchbase, constraints);
	
		
		int f=0;
		int x=0;
		
		while (results.hasMore()) {
			f=1;
			VCFLdapDirAtt vcfldapatt= new VCFLdapDirAtt();
			String acc="";
			String mail="";
			String fn="";
			
			
			SearchResult si =(SearchResult)results.next();
			String ck=si.getName();
			//System.out.println("<br><br><br>"+ck);
			Attributes attrs = si.getAttributes();
			String arr[]=ck.split(",");
			int l=arr.length;

			    if (attrs == null) {
			       System.out.println("   No attributes");
			        continue;
			    }
			    
			    
			    
			    	NamingEnumeration ae = attrs.getAll(); 
			    	while (ae.hasMoreElements()) {
			        Attribute attr =(Attribute)ae.next();
			        String id = attr.getID();
			        Enumeration vals = attr.getAll();
			        while (vals.hasMoreElements())
			        {
			        //String str= vals.nextElement().toString();
			       
			        if(id.equalsIgnoreCase("cn"))
			        {
			        fn=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("mail"))
			        {
			        mail=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("accountStatus"))
			        {
			        acc=vals.nextElement().toString();
			        }
			        
			        
			        
			     }
			    }

			if(mail.indexOf("@")>0 && acc.equalsIgnoreCase("active") )
			{
				
				vcfldapatt.setContactName(fn);
				vcfldapatt.setContactEmail(mail);
				
				
				vcfldapatt.setContactAddress("");
				vcfldapatt.setContactPhoto("");
				vcfldapatt.setContactDept("");
				vcfldapatt.setContactPhone("");
				Files.getVCFLdapDirList().add(vcfldapatt);
			}
			
			}
			/*if(f==0)
			{
			//out.print("Search not found");
			
			}*/
		
			
			}
			catch(Exception e)
			{
				System.out.print(e);
			}
		finally
		{
			if(ctx!=null)
			{
				closeConn(ctx);
			}
				
		}
		
		 FileList1.setVCFLdapDirListResult(Files);	
		 FileList1.setVCFLdapDirSuccess(status);	
		
			return FileList1;	
			
	
	}
}
