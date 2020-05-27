package webmail.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity  
@Table(name="archive") 
public class ChatArchive implements Serializable{  
	  
	 private static final long serialVersionUID = -723583058586873479L;  

	 @Id  
	 @GeneratedValue(strategy=GenerationType.AUTO)  
	 @Column(name = "id")  
	 private Long id; 
	 
	 @Column(name = "username")  
	 private String username; 
	 
	 @Column(name = "timestamp")  
	 private Long timestamp; 
	 
	 @Column(name = "peer")  
	 private String peer; 
	 
	 @Column(name = "bare_peer")  
	 private String bare_peer; 
	 
	 @Column(name = "xml")  
	 private String xml; 
	 
	 @Column(name = "txt")  
	 private String txt; 
	 
	 @Column(name = "kind")  
	 private String 	kind; 
	 
	 @Column(name = "nick")  
	 private String nick; 
	 
	 @Column(name = "created_at")  
	 private Date created_at;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getPeer() {
		return peer;
	}

	public void setPeer(String peer) {
		this.peer = peer;
	}

	public String getBare_peer() {
		return bare_peer;
	}

	public void setBare_peer(String bare_peer) {
		this.bare_peer = bare_peer;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	} 
	 
	 
	 
}
