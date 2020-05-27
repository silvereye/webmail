package com.local;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

import com.example.Connections;
import com.sun.mail.imap.ACL;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.Rights;
import com.sun.mail.imap.Rights.Right;

public class WebmailFolder {

	public boolean isEmptyFolder(String host, String port, String id, String pass, String fldnm)
	{
		boolean isemp = true; 
		
		
			int mcnt=0;
		
        Store store=null;
            try {
            	store=Connections.imapConnectionNP(host, port, id, pass);
                javax.mail.Folder parent = store.getDefaultFolder(); 
                javax.mail.Folder newFolder = parent.getFolder(fldnm); 
                mcnt=newFolder.getMessageCount();
                Folder[] f=store.getFolder(fldnm).list();
                if(f.length>0 || mcnt!=0)
                {
                	isemp = false; 
                }
                
               // store.close();
    } catch (Exception e)   
    {   
      
        e.printStackTrace();   
        isemp = false;   
    }  
            finally
            {
            	try {
					store.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		
		
   //System.out.println("!!!!!!!!!!delete fldr="+isCreated);
		return isemp;
		
	}
	
	public Map getFolderRights(String host, String port, String id, String pass, String fldnm)
	{
		
		Store store=null;
		Map aclval = new HashMap();
        try {
        	store=Connections.imapConnectionNP(host, port, id, pass);
        	IMAPFolder folder = (IMAPFolder) store.getFolder(fldnm);
        	folder.open(Folder.READ_WRITE);
        	ACL[] acl=folder.getACL();
        	for(ACL aacl: acl)
        	{
        		String nm=aacl.getName();
        		if(!nm.equalsIgnoreCase(id))
        		{
        		String rt="ur";
        		Rights rts=aacl.getRights();
        		if (rts.contains(Rights.Right.WRITE) && rts.contains(Rights.Right.READ) && rts.contains(Rights.Right.LOOKUP) && rts.contains(Rights.Right.POST) && rts.contains(Rights.Right.INSERT) && rts.contains(Rights.Right.DELETE) && rts.contains(Rights.Right.KEEP_SEEN) && rts.contains(Rights.Right.CREATE))
        		{
        			rt="us";
        		}
        		else if (rts.contains(Rights.Right.WRITE) && rts.contains(Rights.Right.READ) && rts.contains(Rights.Right.LOOKUP) && rts.contains(Rights.Right.POST) && rts.contains(Rights.Right.INSERT) && rts.contains(Rights.Right.DELETE) && rts.contains(Rights.Right.KEEP_SEEN) )
        		{
        			rt="uw";
        		}
        		aclval.put(nm, rt);
        		}
        	}
        	folder.close(true);
            store.close();
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
		
		return aclval;
	}
	
	public boolean removeFolderACLByID(String host, String port, String id, String pass, String remid, String fldnm)
	{
		boolean st=true;
		Store store=null;
		
        try {
        	store=Connections.imapConnectionNP(host, port, id, pass);
        	IMAPFolder folder = (IMAPFolder) store.getFolder(fldnm);
        	folder.open(Folder.READ_WRITE);
        	folder.removeACL(remid);
        	folder.close(true);
            store.close();
        }
        catch(Exception e)
        {
        	st=false;
        	e.printStackTrace();
        }
        
		return st;
	}
	
	public boolean setFolderACL(String host, String port, String id, String pass,String user,String value,String fldnm)
	{
		boolean res=true;
		
		Store store=null;
	
        try {
        	store=Connections.imapConnectionNP(host, port, id, pass);
        	IMAPFolder folder = (IMAPFolder) store.getFolder(fldnm);
        	folder.open(Folder.READ_WRITE);
        	
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
				
				folder.removeACL(whitchid);
				 Rights rights =new Rights();
			      
			        rights.add(Rights.Right.READ);
			        rights.add(Rights.Right.LOOKUP);
			        rights.add(Rights.Right.KEEP_SEEN);
			        if(per.equalsIgnoreCase("uw") || per.equalsIgnoreCase("us"))
			        {
			        rights.add(Rights.Right.INSERT);
			        rights.add(Rights.Right.WRITE);
			        rights.add(Rights.Right.DELETE);
			        rights.add(Rights.Right.POST);
			        }
			        if(per.equalsIgnoreCase("us"))
			        {
			        	rights.add(Rights.Right.CREATE);
			        }
			        ACL acl=new ACL(whitchid,rights);
			        folder.addACL(acl);
				
				
			}
		}
		folder.close(true);
        store.close();
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        	res=false;
        }
		return res;
	}
}
