package com.sicau.forum.util;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * jedis工具类
 */

@Service
public class JedisAdapter implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
	private JedisPool pool = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		pool = new JedisPool("localhost", 6379);
	}

	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long sadd(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sadd(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long srem(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.srem(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean sismember(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sismember(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long scard(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.scard(key);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Set<String> smember(String key)
	{
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.smembers(key);
		} catch (Exception e) {
			logger.error("exception,e:{}",e);
			return null;
		} finally {
			if (jedis != null){
				jedis.close();
			}
		}
		
	}

	public Set<String> sinter(String key1, String key2) {
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sinter(key1, key2);
		} catch (Exception e) {
			logger.error("exception,e:{}",e);
			return null;
		} finally {
			if (jedis != null){
				jedis.close();
			}
		}
	}

	public long lpush(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lpush(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public List<String> brpop(int timeout, String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.brpop(timeout, key);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void setObject(String key, Object obj) {
		// 保存序列化为JSON串
		set(key, JSON.toJSONString(obj));
	}

	public <T> T getObject(String key, Class<T> clazz) {
		String value = get(key);
		if (value != null) {
			return JSON.parseObject(value, clazz);
		}
		return null;
	}

	public long zadd(String key, double score, String username) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			// 返回多少个值
			return jedis.zadd(key, score, username);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Set<String> zrevrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrange(key, start, end);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
