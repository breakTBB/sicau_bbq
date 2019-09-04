package com.sicau.forum.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.sicau.forum.model.Topic;

@Mapper
public interface TopicDao {
	String TABLE_NAME = "topic";
	String INSERT_FIELDS = "title,content,category,created_date,code,id_user";
	String SELECT_FIELDS = "id,title,content,category,created_date,code,id_user";

	List<Topic> topUpthumb();
	List<Topic> recommend();
	int addTopic(Topic topic);
	void deleteTopicById(@Param("id") Long id);
	Long countTopicsByUser_Id(@Param("userId") Long userId);
	Topic findTopicById(@Param("id") Long id);
	void updateById(Topic topic);
	List<Topic> findTopicsByCategoryOrderByCreatedDateDesc(@Param("category") String category);
	List<Topic> findTopicsByUser_IdOrderByCreatedDateDesc(@Param("id") Long id);
	List<Topic> findAll();
	int getId_userById(@Param("id") Long id);
}
