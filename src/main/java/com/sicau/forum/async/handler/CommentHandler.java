package com.sicau.forum.async.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sicau.forum.async.EventHandler;
import com.sicau.forum.async.EventModel;
import com.sicau.forum.async.EventType;
import com.sicau.forum.dao.AnswerDao;
import com.sicau.forum.dao.MessageDao;
import com.sicau.forum.dao.UserDao;
import com.sicau.forum.model.Message;
import com.sicau.forum.model.User;
import com.sicau.forum.util.HostHolder;

/**
 * 评论的处理器
 */
@Component
public class CommentHandler implements EventHandler{

	@Autowired
	UserDao userDao;
	
	@Autowired
	MessageDao messageDao;
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	AnswerDao answerDao;
	
	@Override
	public void doHandle(EventModel model) {
		Message message=new Message();
		message.setFromId(model.getActorId());
		message.setToId(model.getEntityOwnerId());
		User user=userDao.getUserById((long) model.getActorId());
		message.setContent(user.getUsername()+"评论你的话题！");
		message.setCreatedDate(model.getCreatedDate());
		message.setIdTopic(model.getEntityId());
		message.setHasRead(0);
		messageDao.addMessage(message);
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.COMMENT);
	}

}
