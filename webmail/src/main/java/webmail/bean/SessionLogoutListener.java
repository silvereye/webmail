package webmail.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.mail.Store;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;

import com.sun.mail.imap.IMAPFolder;

public class SessionLogoutListener implements  Serializable,HttpSessionBindingListener{

	private Store store=null;
	private IMAPFolder folder=null;
	private XMPPConnection xchat=null;
	

	public XMPPConnection getXchat() {
		return xchat;
	}

	public void setXchat(XMPPConnection xchat) {
		this.xchat = xchat;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public IMAPFolder getFolder() {
		return folder;
	}

	public void setFolder(IMAPFolder folder) {
		this.folder = folder;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		System.out.println("valueBound:" + event.getName() + " session:" + event.getSession().getId() );
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		System.out.println("valueUnBound:" + event.getName() + " session:" + event.getSession().getId() );
		SessionLogoutListener sll=(SessionLogoutListener)event.getValue();
		try
		{
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
			
		//System.out.println("id:" + event.getSession().getAttribute("id") + " session:" + event.getSession().getId() );
         // add you unlock code here:
  //clearLocksForSession( event.getSession().getId() );
	}

/*public void registerSession() {
		 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( "sessionBindingListener", this  );
		 System.out.println( "registered sessionBindingListener"  );
	    }*/

	/*@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("valueUnBound:" + event.getSession().getAttribute("id") + " session:" + event.getSession().getId() );
	}
*/
}
