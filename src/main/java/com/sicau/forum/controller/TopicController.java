package com.sicau.forum.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.sicau.forum.dao.AnswerDao;
import com.sicau.forum.dao.MessageDao;
import com.sicau.forum.dao.TopicDao;
import com.sicau.forum.dao.UserDao;
import com.sicau.forum.model.Answer;
import com.sicau.forum.model.Topic;
import com.sicau.forum.model.User;
import com.sicau.forum.service.TopicsService;
import com.sicau.forum.util.HostHolder;

@Controller
public class TopicController {
	@Autowired
	private UserDao userDao;

	@Autowired
	private TopicDao topicDao;

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	HostHolder hostHolder;

	@Autowired
	TopicsService topicsService;

	@RequestMapping(path = "/topic/{id}", method = RequestMethod.GET)
	public String displayTopic(@PathVariable String id, Model model) {
		User user = hostHolder.getUser();
		Long idUser = user.getId();
		Topic topic = topicDao.findTopicById(Long.valueOf(id));
		topic.setVisit(topic.getVisit() + 1);
		topicDao.updateById(topic);
		List<Answer> answers = answerDao.findAnswerByTopic_Id(Long.valueOf(id));

		model.addAttribute("user", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		model.addAttribute("topic", topic);
		model.addAttribute("answers", answers);
		model.addAttribute("idUser", idUser);
		model.addAttribute("userDao", userDao);
		return "topic";
	}

	@RequestMapping(path = "/topic/check", method = RequestMethod.POST)
	public View checkTopic(@RequestParam("id_topic") String id_topic, HttpServletRequest request) {
		String contextPath = request.getContextPath();
		try {
			Topic topic = topicDao.findTopicById(Long.parseLong(id_topic));
			topic.setChecked(true);
			topicDao.updateById(topic);
			return new RedirectView(contextPath + "/topics/all/1");
		} catch (Exception e) {
			return new RedirectView(contextPath + "/topics/all/1");
		}
	}

	@ResponseBody
	@RequestMapping(path = "/topic/upthumb", method = RequestMethod.POST)
	public String  upthumb(@RequestParam("id_topic") String id_topic) {
		try {
			Topic topic = topicDao.findTopicById(Long.parseLong(id_topic));
			topic.setUpthumb(topic.getUpthumb() + 1);
			topicDao.updateById(topic);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(path = "/topic/del/{id}", method = RequestMethod.POST)
	public View deleteTopic(@RequestParam("id_topic") String id_topic, HttpServletRequest request) {
		answerDao.deleteAnswerByTopic_id(Long.parseLong(id_topic));
		topicDao.deleteTopicById(Long.parseLong(id_topic));
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/topics/all/1");
	}

	@RequestMapping(path = "/topic/{id}", method = RequestMethod.POST)
	public View updateAnswer(@RequestParam String id_topic, @RequestParam String action, @RequestParam String id_answer,
			@RequestParam(required = false) String state, HttpServletRequest request) {
		answerDao.deleteAnswerById(Long.valueOf(id_answer));
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/topic/" + id_topic);
	}

	@RequestMapping(path = "/topic", method = RequestMethod.POST)
	public View addAnswer(@RequestParam("content") String content,
			@RequestParam("id_topic") String id_topic, @RequestParam("id_user") String id_user,
			HttpServletRequest request) {
		String code = "";
		topicsService.addAnswer(content, code, id_topic, id_user);
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/topic/" + id_topic);
	}

	@RequestMapping(path = "/reply", method = RequestMethod.POST)
	public View addReply(@RequestParam("content") String content,
						  @RequestParam("id_topic") String id_topic, @RequestParam("id_user") String id_user, @RequestParam("id_answer") String pid,
						  HttpServletRequest request) {
		String code = "";
		topicsService.addReply(content, code, id_topic, id_user, pid);
		String contextPath = request.getContextPath();
		System.out.println("id_topic" + id_topic);
		return new RedirectView(contextPath + "/topic/" + id_topic);
	}

	@RequestMapping(path = "/topics/message", method = RequestMethod.GET)
	public View topicsTransform(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
	
	@RequestMapping(path = "/topic/message", method = RequestMethod.GET)
	public View topicTransform(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
}
