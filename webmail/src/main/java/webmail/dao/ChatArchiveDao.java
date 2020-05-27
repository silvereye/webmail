package webmail.dao;

import java.util.List;

import webmail.model.ChatArchive;

public interface ChatArchiveDao {

	public List<ChatArchive> getOldChatList(String email);
	public List<ChatArchive> getChatHistoryByIDFirst(String email,String userid);
}
