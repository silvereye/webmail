package webmail.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import webmail.model.ChatArchive;

@Repository("chatArchiveDao")
@Transactional
public class ChatArchiveDaoImpl implements ChatArchiveDao{

	@Autowired  
	 private SessionFactory sessionFactory1;  
	
	 @SuppressWarnings("unchecked")
	public List<ChatArchive> getOldChatList(String email)
	{
		 
		 Session session = null;
		 session = sessionFactory1.openSession();
   		 List<ChatArchive> list = session.createCriteria(ChatArchive.class)
				 .add(Restrictions.eq("bare_peer", email))
				 .list();
		session.close();
		return list;
	}
	 
	 public List<ChatArchive> getChatHistoryByIDFirst(String email,String userid)
	 {
		 Session session = null;
		 session = sessionFactory1.openSession();
		  Criteria criteria = session.createCriteria(ChatArchive.class)
					 .add(Restrictions.eq("bare_peer", email))
					 .add(Restrictions.eq("username", userid));
		  	criteria.addOrder(Order.desc("timestamp"));
		    criteria.setMaxResults(10);
   		 List<ChatArchive> list = criteria.list();
		session.close();
		return list;
	 }
}
