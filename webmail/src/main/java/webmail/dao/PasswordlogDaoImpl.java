package webmail.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import webmail.model.Passwordlog;
@Repository("passwordlogDao")
@Transactional
public class PasswordlogDaoImpl implements PasswordlogDao {

	@Autowired  
	 private SessionFactory sessionFactory;  
	
	@Override
	public int addRow(Passwordlog pl) {
		 try
		 {
		 sessionFactory.getCurrentSession().saveOrUpdate(pl);  
		 sessionFactory.getCurrentSession().flush();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		return pl.getSno();
	}

}
