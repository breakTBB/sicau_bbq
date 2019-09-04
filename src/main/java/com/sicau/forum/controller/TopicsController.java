package com.sicau.forum.controller;

import com.sicau.forum.dao.AnswerDao;
import com.sicau.forum.dao.MessageDao;
import com.sicau.forum.dao.TopicDao;
import com.sicau.forum.dao.UserDao;
import com.sicau.forum.model.PageBean;
import com.sicau.forum.model.Topic;
import com.sicau.forum.model.User;
import com.sicau.forum.service.PageService;
import com.sicau.forum.service.TopicsService;
import com.sicau.forum.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TopicsController {
	@Autowired
	private UserDao userDao;

	@Autowired
	private TopicDao topicDao;

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private HostHolder localHost;
	
	@Autowired
	private TopicsService topicsService;
	
	@Autowired
	private PageService pageService;
	
	/**
	 * 分页处理
	 */
	@RequestMapping(path="/topics/{category}/{currentPage}", method=RequestMethod.GET)
	public String displayTopicPage(@PathVariable String category, @PathVariable int currentPage, Model model) {
		PageBean<Topic> pageTopic=pageService.findItemByPage(category, currentPage, 10);
		List<Topic> pageList;
		String header = setHeader(category);
		int topicsTotalNum=topicsService.getTopicsByCategory(category).size();
		
		User user=localHost.getUser();
		model.addAttribute("user", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		switch (category) {
			case "mostup":
				pageList = topicDao.topUpthumb();
				break;
			case "recommend":
				pageList = topicDao.recommend();
				break;
			default:
				pageList = pageTopic.getItems();
		}

		Long idUser = user.getId();
		model.addAttribute("idUser", idUser);
		model.addAttribute("topicsTotalNum", topicsTotalNum);
		model.addAttribute("header", header);
		model.addAttribute("answerDao", answerDao);
		model.addAttribute("userDao", userDao);
		model.addAttribute("topics", pageList);
		model.addAttribute("currentPage", pageTopic.getCurrentPage());
		model.addAttribute("totalPage", pageTopic.getTotalPage());
		model.addAttribute("hasNext", pageTopic.getIsMore());
		model.addAttribute("isUserTopicPage", false);
		return "topics";
	}
	
	@RequestMapping(path = "/topics/user/{id}_{currentPage}", method = RequestMethod.GET)
	public String displayTopicsByUser(@PathVariable String id, @PathVariable int currentPage, Model model) {
		PageBean<Topic> pageTopic = pageService.findItemByUser(id, currentPage, 10);
		List<Topic> topics = pageTopic.getItems();
		int topicsTotalNum = topicsService.getTopicsByUser(id).size();
		String header = setHeader("user");


		User user=localHost.getUser();
		Long idUser = user.getId();
		model.addAttribute("idUser", idUser);
		model.addAttribute("user", user);
		model.addAttribute("newMessage", messageDao.countMessageByToId(user.getId()));
		model.addAttribute("topics", topics);
		model.addAttribute("header", header);
		model.addAttribute("answerDao", answerDao);
		model.addAttribute("userDao", userDao);
		model.addAttribute("currentPage", pageTopic.getCurrentPage());
		model.addAttribute("totalPage", pageTopic.getTotalPage());
		model.addAttribute("hasNext", pageTopic.getIsMore());
		model.addAttribute("topicsTotalNum", topicsTotalNum);
		model.addAttribute("isUserTopicPage", true);
		return "topics";
	}

	private String setHeader(String category) {
		switch (category) {
			case "bb":
				return "寻找有趣的灵魂";
			case "dt":
				return "发布有趣的见闻";
			case "xr":
				return "擦肩而过的TA";
			case "tj":
				return "川农er值得一看";
			case "mostup":
				return "点赞数前三的帖子";
			case "recommend":
				return "点赞和访问前五的帖子";
			case "all":
				return "川农学子聚集地";
			default:
				return "发布的话题";
		}
	}
	
	/**
	 * 没在首页点击通知的话 url 会和预设的不一样
	 * 三个函数都在处理这个 bug
	 * 其他地方相同的代码也是处理这个逻辑
	 */
	@RequestMapping(path = "/topics/user/message", method = RequestMethod.GET)
	public View topicTransformUser(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
	
	@RequestMapping(path = "/topics/other/message", method = RequestMethod.GET)
	public View topicTransformOther(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
	
	@RequestMapping(path = "/topics/web/message", method = RequestMethod.GET)
	public View topicTransformWeb(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return new RedirectView(contextPath + "/message");
	}
}
