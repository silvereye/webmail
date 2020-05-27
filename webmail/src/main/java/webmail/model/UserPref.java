package webmail.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity  
@Table(name="userpref") 
public class UserPref implements Serializable{  
	  
	 private static final long serialVersionUID = -723583058586873479L;  

	 @Id  
	 @GeneratedValue(strategy=GenerationType.AUTO)  
	 @Column(name = "prefid")  
	 private long prefid; 
	 
	 @Column(name = "username")  
	 private String username; 
	 
	 @Column(name = "preference")  
	 private String preference; 
	 
	 @Column(name = "value")  
	 private String value;

	public long getPrefid() {
		return prefid;
	}

	public void setPrefid(long prefid) {
		this.prefid = prefid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	} 
	 
	
}
