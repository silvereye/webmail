package webmail.idle;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.servlet.http.HttpSession;

import com.example.Connections;
import com.sun.mail.imap.MessageVanishedEvent;

public class MailVanished {

	public static void inboxVanished(String host,String port,String id,String pass, String uids) {
		Store store = Connections.imapConnectionNP(host, port, id, pass);
		String arr[]=uids.split("-");
		long uidarr[]=new long[arr.length];
		for(int i=0;i< arr.length;i++)
		{
			uidarr[i]=Long.parseLong(arr[i].trim());
		}
		try {
			Folder folder= store.getFolder("Inbox");
			MessageVanishedEvent mve=new MessageVanishedEvent(folder, uidarr);
			long larr[]= mve.getUIDs();
			System.out.println("MessageVanishedEvent-------"+larr.length);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
