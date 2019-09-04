package com.sicau.forum.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import com.sicau.forum.service.RegisterService;

@Controller
public class RegisterController {

	@Autowired
	RegisterService registerService;

	@RequestMapping(path = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("admin", "user");
		return "register";
	}

	@RequestMapping(path = "/register/admin", method = RequestMethod.GET)
	public String regToAdmin(Model model) {
		model.addAttribute("admin", "admin");
		return "register";
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public View register(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("introduction") String introduction, @RequestParam("admin") String admin, HttpServletRequest request) {
		String contextPath = request.getContextPath();
		System.out.println("尝试注册一个 " + admin + "类型的账号");
		String action = registerService.reg(username, password, introduction, admin);
		return new RedirectView(contextPath + action);
	}


}
