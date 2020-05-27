package webmail.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity  
@Table(name="shared")  
public class Shared implements Serializable{  
	  
	 private static final long serialVersionUID = -723583058586873479L;  
	 
	 @Id  
	 @GeneratedValue(strategy=GenerationType.AUTO)  
	 @Column(name = "sno")  
	 private Integer sno;  
	   
	 @Column(name="user_from")  
	 private String userfrom;  
	   
	 @Column(name="user_which")  
	 private String userwhich;  
	 
	 @Column(name="caluid")  
	 private String calUID; 
	 	   
	 @Column(name="iscalendar")  
	 private boolean iscalendar;
	 
	 @Column(name="isdisplay")  
	 private boolean isdisplay;
	 
	 public boolean isIsdisplay() {
		return isdisplay;
	}

	public void setIsdisplay(boolean isdisplay) {
		this.isdisplay = isdisplay;
	}

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public String getUserfrom() {
		return userfrom;
	}

	public void setUserfrom(String userfrom) {
		this.userfrom = userfrom;
	}

	public String getUserwhich() {
		return userwhich;
	}

	public void setUserwhich(String userwhich) {
		this.userwhich = userwhich;
	}

	public String getCalUID() {
		return calUID;
	}

	public void setCalUID(String calUID) {
		this.calUID = calUID;
	}

	public boolean isIscalendar() {
		return iscalendar;
	}

	public void setIscalendar(boolean iscalendar) {
		this.iscalendar = iscalendar;
	}

	public String getWriteaccess() {
		return writeaccess;
	}

	public void setWriteaccess(String writeaccess) {
		this.writeaccess = writeaccess;
	}

	@Column(name="write_access") 
	 private String writeaccess;

	
	 
}
