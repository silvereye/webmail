package webmail.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name="Passwordlog") 
public class Passwordlog {

	@Id  
	 @GeneratedValue(strategy=GenerationType.AUTO)  
	 @Column(name = "sno")  
	 private Integer sno;  
	   
	 @Column(name="userid")  
	 private String userid;  
	   
	 @Column(name="ip")  
	 private String ip;  
	 
	 @Column(name="changedate")  
	 private Date changedate;
	 
	 
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

	public Date getChangedate() {
		return changedate;
	}

	public void setChangedate(Date changedate) {
		this.changedate = changedate;
	}

	
}
