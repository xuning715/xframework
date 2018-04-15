package com.x.framework.redis;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class XRedisTemplate {
	private static final Logger logger = LogManager.getLogger(XRedisTemplate.class);

    private final static String star = "*";
    private final static String colon = ":";
	private final static String blank = "";
	private final static String SERVICE_KEY = "SERVICE_KEY";

    @Autowired
	private RedisTemplate redisTemplate;

	public XRedisTemplate() {
		logger.info("====================XRedisTemplate has been inited=======================");
	}

	public String get(String key) {
		try {
			key = key.replaceAll(colon, blank);
			ValueOperations<String, String> keyValue = redisTemplate.opsForValue();
			String json = keyValue.get(key);
			return json;
		} catch (Exception e) {
			logger.error("AopRedis get exception : ", e);
			return null;
		}
	}

	public String get(String key, int expireSeconds) {
		try {
			key = key.replaceAll(colon, blank);
			ValueOperations<String, String> keyValue = redisTemplate.opsForValue();
			String json = keyValue.get(key);
			if (json != null && json.length() > 0) {
				redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
			}
			return json;
		} catch (Exception e) {
			logger.error("AopRedis get exception : ", e);
			return null;
		}
	}

	public Object getAopObject(String key, int expireSeconds) {
		Object value = null;
		try {
			key = key.replaceAll(colon, blank);
			HashOperations keyValue = redisTemplate.opsForHash();
			value = keyValue.get(key, key);
			if (value != null) {
				redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			logger.error("AopRedis get exception : ", e);
		}
		return value;
	}

	public String set(String key, Object value) {
		String json = null;
		try {
            key = key.replaceAll(colon, blank);
            if (value != null) {
                json = JSON.toJSONString(value);
            }
			logger.info("json==================" + json);
			if (json != null && json.length() > 0) {
                ValueOperations<String, String> keyValue = redisTemplate.opsForValue();
				keyValue.set(key, json);
			}
		} catch (Exception e) {
			json = null;
			logger.error("AopRedis save exception : ", e);
		} finally {
			return json;
		}
	}

	public String set(String key, Object value, int expireSeconds) {
		String json = null;
		try {
			key = key.replaceAll(colon, blank);
			if (value != null) {
				json = JSON.toJSONString(value);
			}
			logger.info("json==================" + json);
			if (json != null && json.length() > 0) {
				ValueOperations<String, String> keyValue = redisTemplate.opsForValue();
				keyValue.set(key, json, expireSeconds, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			json = null;
			logger.error("AopRedis setex exception : ", e);
		} finally {
			return json;
		}
	}

	public void setAopObject(String key, Object value, int expireSeconds) {
		try {
			key = key.replaceAll(colon, blank);
			if (value != null) {
				HashOperations keyValue = redisTemplate.opsForHash();
				keyValue.put(key, key, value);
				redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			logger.error("AopRedis setex exception : ", e);
		}
	}

	public void delete(String key) {
		try {
			redisTemplate.delete(key);
		} catch (Exception e) {
			logger.error("AopRedis remove exception : ", e);
		}
	}

	public void deleteAopObject(String key) {
		try {
			redisTemplate.delete(redisTemplate.keys(key + star));
		} catch (Exception e) {
			logger.error("AopRedis remove exception : ", e);
		}
	}

	public void lpush(String key, Object value) {
		try {
            key = key.replaceAll(colon, blank);
            String json = JSON.toJSONString(value);
            logger.info("json==================" + json);
            ListOperations<String, String> list = redisTemplate.opsForList();
			if (json != null && json.length() > 0) {
                list.leftPush(key, json);
			}
		} catch (Exception e) {
			logger.error("AopRedis lpush exception : ", e);
		}
	}

	public <T> T rpop(String key, final Class<T> clazz) {
		T value = null;
		try {
            key = key.replaceAll(colon, blank);
            ListOperations<String, String> list = redisTemplate.opsForList();
            String json = list.rightPop(key);
			if (json != null && json.length() > 0) {
				value = JSON.parseObject(json, clazz);
			}
		} catch (Exception e) {
			logger.error("AopRedis rpop exception : ", e);
		} finally {
			return value;
		}
	}

}
