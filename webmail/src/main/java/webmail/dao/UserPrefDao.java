package webmail.dao;

import java.util.List;

import webmail.model.UserPref;

public interface UserPrefDao {

	 public long addUserPrefRow(UserPref upref);  
	 public List<UserPref> getUserPrefList(String usernm, String type);
	 public boolean deleteUserPrefRow(long  bwid);
}
