package com.sicau.forum.service;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sicau.forum.dao.UserDao;
import com.sicau.forum.model.User;

@Service
public class RegisterService {
	@Autowired
	UserDao userDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	public String reg(String username, String password, String introduction, String admin) {
		User user = new User();
		if (userDao.getUserByUsername(username) == null) {
			user.setUsername(username);
			user.setPassword(password);
			user.setPassword(passwordEncoder.encode(password));
			if (Objects.equals(introduction, "")) {
				user.setIntroduction(null);
			} else {
				user.setIntroduction(introduction);
			}
			user.setCreatedDate(new Date());
			if ("admin".equals(admin)) {
				user.setAdmin(true);
			}
			userDao.addUser(user);
			return "/login";
		} else {
			return "/register";
		}
	}
}
