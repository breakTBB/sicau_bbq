package com.sicau.forum.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicau.forum.dao.UserDao;
import com.sicau.forum.model.User;
import com.sicau.forum.util.JedisAdapter;

@Service
public class FollowService {

	@Autowired
	private JedisAdapter jedisAdapter;
	
	@Autowired
	private UserDao userDao;
	
	public void addFollow(String userName, Long userId, Long otherUserId) {
		// key:当前用户username_id，value:关注该用户的用户id
		String key = userName + "_"+ otherUserId;
		jedisAdapter.sadd(key, String.valueOf(userId));
	}

	public long getFollowNum(String userName, Long userId) {
		String key = userName + "_"+ userId;
		long followNum = jedisAdapter.scard(key);
		return followNum;
	}

	public Set<User> getFollowUser(Long userId) {
		User user = userDao.getUserById(userId);
		String followKey = user.getUsername() + "_" + userId;
		Set<String> fansSet = jedisAdapter.smember(followKey);
		Set<User> fansUser = new HashSet<>();
		for (String fans : fansSet) {
			User fan = userDao.getUserById(Long.parseLong(fans));
			fansUser.add(fan);
		}
		return fansUser;
	}

	public Set<User> getCommonFans(Long userId1, Long userId2) {
		User user1 = userDao.getUserById(userId1);
		User user2 = userDao.getUserById(userId2);
		String followKey1 = user1.getUsername() + "_" + userId1;
		String followKey2 = user2.getUsername() + "_" + userId2;
		Set<String> commonFans = jedisAdapter.sinter(followKey1, followKey2);
		Set<User> fansUser = new HashSet<>();
		for (String fans : commonFans) {
			User fan = userDao.getUserById(Long.parseLong(fans));
			fansUser.add(fan);
		}
		return fansUser;
	}

	public boolean isFollowed(Long currentUserId, Long targetUserId) {
		User targetUser = userDao.getUserById(targetUserId);
		String key = targetUser.getUsername() + "_" + targetUserId;
		boolean isFollowed = jedisAdapter.sismember(key, String.valueOf(currentUserId));
		return isFollowed;
	}
}
