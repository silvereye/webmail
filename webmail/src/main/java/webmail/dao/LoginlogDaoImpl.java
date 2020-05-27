package webmail.dao;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import webmail.model.Loginlog;
@Repository("loginlogDao")
@Transactional
public class LoginlogDaoImpl implements LoginlogDao {

	@Autowired  
	 private SessionFactory sessionFactory;  
	
	@Override
	public int addUpdateRow(int sn, Loginlog ll) {
		try
		{
		if(sn==0)
		{
			 sessionFactory.getCurrentSession().saveOrUpdate(ll);  
			 sessionFactory.getCurrentSession().flush();
		}
		else
		{
			ll=(Loginlog)this.sessionFactory.getCurrentSession().createCriteria(Loginlog.class)
			 .add(Restrictions.eq("sno", sn)).uniqueResult();
			ll.setLogoutdate(new Date());
			 sessionFactory.getCurrentSession().saveOrUpdate(ll);  
			 sessionFactory.getCurrentSession().flush();
		}
		}
		catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return ll.getSno();
	}

}
