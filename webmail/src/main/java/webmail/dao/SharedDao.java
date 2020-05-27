package webmail.dao;

import java.util.List;

import webmail.model.Shared;

public interface SharedDao {

	 public int addSharedDetail(Shared shrd);  
	 public int updateSharedDetail(Shared shrd);  
	 public int searchSharedDetail(String userfrom, String userwhich, String caluid, boolean iscal);
	 public List<Shared> getSharedDetailForPopup(String userfrom, String caluid, boolean iscal);
	 public Shared getSharedDetailForWhich(String userwhich, String caluid, boolean iscal);
	 public List<Shared> getSharedDetailForLeft(String userwhich, boolean iscal);
	 public List<Shared> getSharedDetailForLeft(String userwhich,boolean iscal, boolean isdisp);
	 public boolean deleteSharedDetail(String userwhich, String caluid,int sno, boolean iscal);
}
