package com.sicau.forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.sicau.forum.dao.TopicDao;
import com.sicau.forum.model.PageBean;
import com.sicau.forum.model.Topic;

@Service
public class PageService {
	
	@Autowired
	private TopicDao topicDao;
	
	public PageBean<Topic> findItemByPage(String category, int currentPage, int pageSize) {
		int countNums = 0;
		if(category.equals("all")) {
			countNums=topicDao.findAll().size();
		}else{
			countNums = topicDao.findTopicsByCategoryOrderByCreatedDateDesc(category).size();
		}
		PageHelper.startPage(currentPage, pageSize);
		List<Topic> allTopic=null;
		if(category.equals("all")) {
			allTopic=topicDao.findAll();
		}else{
			allTopic = topicDao.findTopicsByCategoryOrderByCreatedDateDesc(category);
		}
		PageBean<Topic> pageData = new PageBean<>(currentPage, pageSize, countNums);
		pageData.setItems(allTopic);
		return pageData;
	}
	
	public PageBean<Topic> findItemByUser(String userId, int currentPage, int pageSize) {
		int countNums = topicDao.findTopicsByUser_IdOrderByCreatedDateDesc(Long.valueOf(userId)).size();
		PageHelper.startPage(currentPage, pageSize);
		List<Topic> allTopic=topicDao.findTopicsByUser_IdOrderByCreatedDateDesc(Long.valueOf(userId));
		PageBean<Topic> pageData = new PageBean<>(currentPage, pageSize, countNums);
		pageData.setItems(allTopic);
		return pageData;
	}
}
