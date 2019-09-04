package com.sicau.forum.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sicau.forum.model.Message;

@Mapper
public interface MessageDao {
	String TABLE_NAME = "message";
	String INSERT_FIELDS = "from_id,to_id,content,created_date,id_topic,has_read";
	String SELECT_FIELDS = "id,from_id,to_id,content,created_date,id_topic,has_read";

	int addMessage(Message message);
	List<Message> getMessageByToId(@Param("toId") Long toId);
	void deleteMessageByTopicId(@Param("topic_id") Long topic_id);
	int countMessageByToId(@Param("toId") Long toId);
	List<Message> getUnReadMessageByToId(@Param("toId") Long toId);
	void changeReadStatement(@Param("id") Long id);
}
