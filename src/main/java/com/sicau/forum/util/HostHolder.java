package com.sicau.forum.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sicau.forum.dao.UserDao;
import com.sicau.forum.model.User;


@Component
public class HostHolder {
	
	private User user;
	
	@Autowired
	UserDao userDao;

	/**
	 * 获取当前登录的用户User
	 */
	public User getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user=userDao.getUserByUsername(((UserDetails) principal).getUsername());
		return user;
	}
}
