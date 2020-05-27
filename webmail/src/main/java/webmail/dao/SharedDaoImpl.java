package webmail.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import webmail.model.Forgetpassword;
import webmail.model.Shared;

@Repository("sharedDao")
@Transactional
public class SharedDaoImpl implements SharedDao{

	 @Autowired  
	 private SessionFactory sessionFactory;  
	 public int addSharedDetail(Shared shrd)
	 {
		 try
		 {
		 System.out.println("sessssssssssssssssss="+sessionFactory);
		 sessionFactory.getCurrentSession().saveOrUpdate(shrd);  
		 sessionFactory.getCurrentSession().flush();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return shrd.getSno();
	 }
	 
	 public int updateSharedDetail(Shared shrd)
	 {
		 return 0;
	 }
	 
	 public int searchSharedDetail(String userfrom, String userwhich, String caluid,boolean iscal)
	 {
		try
		{
		 List<Shared> list = this.sessionFactory.getCurrentSession().createCriteria(Shared.class)
				 .add(Restrictions.eq("userfrom", userfrom))
				 .add(Restrictions.eq("userwhich", userwhich))
				 .add(Restrictions.eq("calUID", caluid))
				 .add(Restrictions.eq("iscalendar", iscal))
				 .list();
		 
		 if(list != null && !list.isEmpty())
		 {
			 return list.get(0).getSno();
		 }
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		 return 0;
	 }

	 public Shared getSharedDetailForWhich(String userwhich, String caluid,boolean iscal)
	 {
		Shared srd=null;
		 try
		 {
		  srd = (Shared)this.sessionFactory.getCurrentSession().createCriteria(Shared.class)
				 .add(Restrictions.eq("userwhich", userwhich))
				 .add(Restrictions.eq("calUID", caluid))
				 .add(Restrictions.eq("iscalendar", iscal))
				 .uniqueResult();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return srd;
	 }
	 public List<Shared> getSharedDetailForPopup(String userfrom, String caluid,boolean iscal)
	 {
		 List<Shared> list=null;
		 try
		 {
		  list = this.sessionFactory.getCurrentSession().createCriteria(Shared.class)
				 .add(Restrictions.eq("userfrom", userfrom))
				 .add(Restrictions.eq("calUID", caluid))
				 .add(Restrictions.eq("iscalendar", iscal))
				 .list();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return list;
	 }
	 
	 public List<Shared> getSharedDetailForLeft(String userwhich,boolean iscal)
	 {
		 List<Shared> list=null;
		 try
		 {
		 list = this.sessionFactory.getCurrentSession().createCriteria(Shared.class)
				 .add(Restrictions.eq("userwhich", userwhich))
				 .add(Restrictions.eq("iscalendar", iscal))
				 .list();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return list;
	 }
	 
	 public List<Shared> getSharedDetailForLeft(String userwhich,boolean iscal, boolean isdisp)
	 {
		 List<Shared> list=null;
		 try
		 {
		 list = this.sessionFactory.getCurrentSession().createCriteria(Shared.class)
				 .add(Restrictions.eq("userwhich", userwhich))
				 .add(Restrictions.eq("iscalendar", iscal))
				 .add(Restrictions.eq("isdisplay", isdisp))
				 .list();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return list;
	 }
	 
	 public boolean deleteSharedDetail(String userwhich, String caluid,int sno,boolean iscal)
	 {
		 boolean st=true;
		 try
		 {
		 Shared srd = (Shared)this.sessionFactory.getCurrentSession().createCriteria(Shared.class)
				 .add(Restrictions.eq("userwhich", userwhich))
				 .add(Restrictions.eq("calUID", caluid))
				 .add(Restrictions.eq("sno", sno))
				 .add(Restrictions.eq("iscalendar", iscal))
				 .uniqueResult();
		 this.sessionFactory.getCurrentSession().delete(srd);
		 this.sessionFactory.getCurrentSession().flush();
		 this.sessionFactory.getCurrentSession().clear();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 st=false;
		 }
		 return st;
	 }

	
	 
}
