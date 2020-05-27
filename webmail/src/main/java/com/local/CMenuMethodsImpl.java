package com.local;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;

import com.example.Connections;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class CMenuMethodsImpl {

	public static String emptyWebmailFolder(String host,String port,String id, String pass,String fldr)
	{
		String res="true";
		try
		{
			IMAPFolder folder = null;
	        Store store = null;
	        store=Connections.imapConnectionNP(host, port, id, pass);
	        folder = (IMAPFolder) store.getFolder(fldr); //This works for both email account
	        folder.open(Folder.READ_WRITE);
	        Message msg[]= folder.getMessages();
	        Flags deleted = new Flags(Flags.Flag.DELETED);
	        folder.setFlags(msg, deleted, true);
	        folder.close(true);
	        store.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			res="false";
		}
		return res;
		
	}
	
}
