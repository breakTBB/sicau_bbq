package com.sicau.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.annotation.WebServlet;

@Controller
public class LoginController {
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
}
