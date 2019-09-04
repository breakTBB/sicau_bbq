package com.sicau.forum.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.sicau.forum.dao.AnswerDao;
import com.sicau.forum.dao.MessageDao;
import com.sicau.forum.dao.TopicDao;
import com.sicau.forum.dao.UserDao;
import com.sicau.forum.model.Answer;
import com.sicau.forum.model.Message;
import com.sicau.forum.model.Topic;
import com.sicau.forum.model.User;
import com.sicau.forum.service.MessageService;
import com.sicau.forum.util.HostHolder;

/**
 * 当有人给你评论时触发异步队列，发送通知
 */

@Controller
public class MessageController {
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	MessageService messageService;
	
	@RequestMapping(path="/message",method= RequestMethod.GET)
	public String message(Model model) {
		User user=hostHolder.getUser();
		Long toId=user.getId();
		
		List<Message> messages=messageService.getReadMessageById(toId);
		List<Message> unReadMessages=messageService.getUnreadMessageById(toId);
		
		model.addAttribute("user",user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		model.addAttribute("messages", messages);
		model.addAttribute("unReadMessages", unReadMessages);
		model.addAttribute("userDao", userDao);
		model.addAttribute("messageDao", messageDao);
		return "message";
	}
}
