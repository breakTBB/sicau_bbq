package com.sicau.forum.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;
import com.sicau.forum.dao.AnswerDao;
import com.sicau.forum.dao.MessageDao;
import com.sicau.forum.dao.TopicDao;
import com.sicau.forum.dao.UserDao;
import com.sicau.forum.model.Topic;
import com.sicau.forum.model.User;
import com.sicau.forum.service.FollowService;
import com.sicau.forum.util.HostHolder;

@Controller
public class ProfileController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TopicDao topicDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private FollowService followService;

	
	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public String displayMyProfile(Model model) {
		User user = hostHolder.getUser();
		Long points = userDao.getPoints(user.getId());
		Long numberOfTopics = topicDao.countTopicsByUser_Id(user.getId());
		Long numberOfAnswers = answerDao.countAnswersByUser_Id(user.getId());
		Long numberOfHelped = answerDao.countAnswersByUser_IdAndUseful(user.getId(), true);
		model.addAttribute("user", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		model.addAttribute("points", points);
		model.addAttribute("numberOfTopics", numberOfTopics);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("numberOfHelped", numberOfHelped);
		model.addAttribute("switch", true);
		return "profile";
	}

	@RequestMapping(path = "/profile/{id}", method = RequestMethod.GET)
	public String displayProfileById(@PathVariable Long id, Model model) {
		User user = userDao.getUserById(id);
		Long points = userDao.getPoints(user.getId());
		Long numberOfTopics = topicDao.countTopicsByUser_Id(id);
		Long numberOfAnswers = answerDao.countAnswersByUser_Id(id);
		Long numberOfHelped = answerDao.countAnswersByUser_IdAndUseful(id, true);
		User otherUser=hostHolder.getUser();
		boolean isFollowed = followService.isFollowed(otherUser.getId(), id);
		
		model.addAttribute("user", otherUser);
		model.addAttribute("otherUser", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(hostHolder.getUser().getId()));
		model.addAttribute("points", points);
		model.addAttribute("numberOfTopics", numberOfTopics);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("numberOfHelped", numberOfHelped);
		model.addAttribute("switch", user.getId().equals(otherUser.getId()));
		model.addAttribute("followNums", followService.getFollowNum(user.getUsername(), user.getId()));
		model.addAttribute("isFollowed", isFollowed);
		return "profile";
	}

	@RequestMapping(path="/follow/{userId}_{otherUserId}",method=RequestMethod.POST)
	@ResponseBody
	public String addFollow(@PathVariable Long userId, @PathVariable Long otherUserId) {
		User user = userDao.getUserById(userId);
		User otherUser = userDao.getUserById(otherUserId);
		followService.addFollow(otherUser.getUsername(), userId, otherUserId);
		return "follow success!";
	}
	
	@RequestMapping(path="/fans/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public String displayFans(@PathVariable Long userId){
		Set<User> followFans = followService.getFollowUser(userId);
		Map<String, Long> followFansMap = new HashMap<String, Long>();
		for (User fan : followFans) {
			followFansMap.put(fan.getUsername(), fan.getId());
		}
		return JSON.toJSONString(followFansMap);
	}
	
	@RequestMapping(path="/commonfans/{userId}_{otherUserId}",method=RequestMethod.GET)
	@ResponseBody
	public String displayCommonFans(@PathVariable Long userId, @PathVariable Long otherUserId) {
		Set<User> commonFans = followService.getCommonFans(userId, otherUserId);
		Map<String, Long> commonFansMap = new HashMap<>();
		for (User commonFan : commonFans) {
			commonFansMap.put(commonFan.getUsername(), commonFan.getId());
		}
		return JSON.toJSONString(commonFansMap);
	}
	
	@RequestMapping(path = "/profile/message", method = RequestMethod.GET)
	public View topicsTransform(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}

}
