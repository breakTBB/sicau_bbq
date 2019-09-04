package com.sicau.forum.util;

public class RedisKeyUtil {
	private static String BIZ_EVENT = "EVENT";

	public static String getEventQueueKey() {
		return BIZ_EVENT;
	}
}
