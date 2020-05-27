package webmail.bean;

import javax.mail.Store;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;

import com.sun.mail.imap.IMAPFolder;

public class NPSessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		System.out.println( " session:" + event.getSession().getId() );
		try
		{
			SessionLogoutListener sll=(SessionLogoutListener)event.getSession().getAttribute("sll") ;
			
			Store storenp=sll.getStore();
			 IMAPFolder foldernp=sll.getFolder();
			 
			 XMPPConnection xmppChatClass=sll.getXchat();
				if(xmppChatClass!=null){
					try {
						xmppChatClass.disconnect();
					} catch (NotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			 try
				{
				 foldernp.close(true);
				}
				catch(Exception ee)
				{
					//ee.printStackTrace();
				}
				try
				{
					storenp.close();
				}
				catch(Exception ee)
				{
					//ee.printStackTrace();
				}
				event.getSession().invalidate();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
	}

}
