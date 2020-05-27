package webmail.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import webmail.model.Forgetpassword;

@Repository("forgetpasswordDao")
@Transactional
public class ForgetpasswordDaoImpl implements ForgetpasswordDao{

	 @Autowired  
	 private SessionFactory sessionFactory;  
	
	 public int addForgetPwd(Forgetpassword fpwd)
	 {
		 try
		 {
		 System.out.println("sessssssssssssssssss="+sessionFactory);
		 sessionFactory.getCurrentSession().saveOrUpdate(fpwd);  
		 sessionFactory.getCurrentSession().flush();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return fpwd.getSno();
	 }
	
	 @SuppressWarnings("unchecked")
	 public Forgetpassword getForgetpassword(String email1, String email2, String token, int sn)
	 {
		List<Forgetpassword> list = this.sessionFactory.getCurrentSession().createCriteria(Forgetpassword.class)
				 .add(Restrictions.eq("email", email1))
				 .add(Restrictions.eq("recoveryemail", email2))
				 .add(Restrictions.eq("passtoken", token))
				 .add(Restrictions.eq("sno", sn))
				 .list();
		 
		 if(list != null && !list.isEmpty())
		 {
			 return list.get(0);
		 }
		 return null;
	 }
}
