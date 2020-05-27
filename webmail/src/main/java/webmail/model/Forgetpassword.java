package webmail.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity  
@Table(name="forgetpassword")  
public class Forgetpassword implements Serializable{  
	  
	 private static final long serialVersionUID = -723583058586873479L;  
	 
	 @Id  
	 @GeneratedValue(strategy=GenerationType.AUTO)  
	 @Column(name = "sno")  
	 private Integer sno;  
	   
	 @Column(name="passtoken")  
	 private String passtoken;  
	   
	 @Column(name="email")  
	 private String email;  
	 
	 @Column(name="recoveryemail")  
	 private String recoveryemail; 
	 	   
	 @Column(name="expdate")  
	 private Date expdate;

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public String getPasstoken() {
		return passtoken;
	}

	public void setPasstoken(String passtoken) {
		this.passtoken = passtoken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRecoveryemail() {
		return recoveryemail;
	}

	public void setRecoveryemail(String recoveryemail) {
		this.recoveryemail = recoveryemail;
	}

	public Date getExpdate() {
		return expdate;
	}

	public void setExpdate(Date expdate) {
		this.expdate = expdate;
	}  
	 
}
