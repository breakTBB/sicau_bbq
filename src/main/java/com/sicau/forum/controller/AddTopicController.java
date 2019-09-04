package com.sicau.forum.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.sicau.forum.dao.AnswerDao;
import com.sicau.forum.dao.MessageDao;
import com.sicau.forum.dao.TopicDao;
import com.sicau.forum.dao.UserDao;
import com.sicau.forum.model.Topic;
import com.sicau.forum.model.User;
import com.sicau.forum.util.HostHolder;


@Controller
public class AddTopicController {
	@Autowired
	private UserDao userDao;

	@Autowired
	private TopicDao topicDao;

	@Autowired
	private HostHolder hostHolder;

	@RequestMapping(path = "/addTopic", method = RequestMethod.GET)
	public String displayMyProfile(Model model) {
		User user = hostHolder.getUser();
		model.addAttribute("user", user);
		return "addTopic";
	}

	@RequestMapping(path = "/addTopic", method = RequestMethod.POST)
	public View addTask(@RequestParam("category") String category, @RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("id_user") String id_user, HttpServletRequest request) {
		Topic topic = new Topic();
		topic.setCategory(category);
		topic.setContent(content);
		topic.setTitle(title);
		topic.setCreatedDate(new Date());
		topic.setIdUser(Integer.parseInt(id_user));
		topic.setUser(userDao.getUserById(Long.parseLong(id_user)));

		topicDao.addTopic(topic);
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/topics/"+category+"/1");
	}
	
}
