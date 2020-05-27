package webmail.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import webmail.model.Shared;
import webmail.model.UserPref;

@Repository("userPrefDao")
@Transactional
public class UserPrefDaoImpl implements UserPrefDao{

	 @Autowired  
	 private SessionFactory sessionFactory2;  
	 public long addUserPrefRow(UserPref upref)
	 {
		System.out.println(sessionFactory2);
		Session session = null;
		 session = sessionFactory2.openSession();
		 try
		 {
			 session.saveOrUpdate(upref);  
			 session.close();
		// sessionFactory2.getCurrentSession().flush();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 return 0;
		 }
		 return upref.getPrefid();
	 }
	 
	

	
	 public List<UserPref> getUserPrefList(String usernm, String type)
	 {
		 List<UserPref> list=null;
		 try
		 {
			 Session session = null;
			 session = sessionFactory2.openSession();
			 list = session.createCriteria(UserPref.class)
				 .add(Restrictions.eq("username", usernm))
				 .add(Restrictions.eq("preference", type))
				 .list();
			 session.close();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return list;
	 }
	 
	 public boolean deleteUserPrefRow(long  bwid)
	 {
		 boolean st=true;
		 try
		 {
			 Session session = null;
			 session = sessionFactory2.openSession();
			 UserPref upref = (UserPref)session.createCriteria(UserPref.class)
				 .add(Restrictions.eq("prefid", bwid))
				.uniqueResult();
			 session.delete(upref);
			 session.flush();
			 session.clear();
			 session.close();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 st=false;
		 }
		 return st;
	 }
	 
}
