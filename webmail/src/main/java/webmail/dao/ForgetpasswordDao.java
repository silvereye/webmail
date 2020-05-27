package webmail.dao;

import webmail.model.Forgetpassword;

public interface ForgetpasswordDao {

	 public int addForgetPwd(Forgetpassword fpwd);  
	 public Forgetpassword getForgetpassword(String email1, String email2, String token, int sn);
	 
}
