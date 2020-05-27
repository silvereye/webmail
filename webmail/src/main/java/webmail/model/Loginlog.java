package webmail.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity  
@Table(name="Loginlog") 
public class Loginlog {

	 @Id  
	 @GeneratedValue(strategy=GenerationType.AUTO)  
	 @Column(name = "sno")  
	 private Integer sno;  
	   
	 @Column(name="userid")  
	 private String userid;  
	   
	 @Column(name="ip")  
	 private String ip;  
	 
	 @Column(name="logindate")  
	 private Date logindate;
	 
	 @Column(name="logoutdate")  
	 private Date logoutdate;

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}

	public Date getLogoutdate() {
		return logoutdate;
	}

	public void setLogoutdate(Date logoutdate) {
		this.logoutdate = logoutdate;
	}
	
}
