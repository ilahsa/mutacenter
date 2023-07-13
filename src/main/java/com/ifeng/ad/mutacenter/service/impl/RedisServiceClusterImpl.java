package com.ifeng.ad.mutacenter.service.impl;

import com.ifeng.ad.mutacenter.Constant;
import com.ifeng.ad.mutacenter.common.utils.JsonUtils;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import com.ifeng.ad.mutacenter.service.RedisClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


//@Component
public class RedisServiceClusterImpl implements RedisClusterService {

	public static final Logger logger = LoggerFactory.getLogger(RedisServiceClusterImpl.class);
	//准备做索引，如果要在muta初始化加载所有数据的话，一定要提前做一个索引
	public static final String mutaindex = Constant.WORD_MUTA + Constant.WORD_SPLIT_COLON + Constant.WORD_CONFIGKEY;

	//@Autowired
	private JedisCluster jc;

	public RedisServiceClusterImpl(JedisCluster jedisCluster){
		this.jc = jedisCluster;
	}
	@Override
	public boolean isHealthy() {
		for (Map.Entry<String, JedisPool> node : jc.getClusterNodes().entrySet()) {
			try {
				Jedis jedis = node.getValue().getResource();
				jedis.info("memory");
			} catch (JedisConnectionException jce) {
				logger.error("redis server error:" + jce.getMessage());
				return false;
			}
		}
		return true;
	}

	@Override
	public String set(String key, String value) {
		return jc.set(key, value);
	}

	@Override
	public String expSecondSet(String key, String value, int exptime) {
		return jc.setex(key, exptime, value);
	}

	@Override
	public String expMillisecondSet(String key, String value, long exptime) {
		return jc.psetex(key, exptime, value);
	}

	@Override
	public Long expAtSet(String key, String value, long unixTime){
		jc.set(key,value);
		return jc.expireAt(key,unixTime);
	}

	@Override
	public String get(String key) {
		return jc.get(key);
	}

	@Override
	public <T> T get(String key, Class<T> cls) {
		String result = jc.get(key);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		return JsonUtils.convert2Obj(result, cls);
	}


	@Override
	public Set<String> smembers(String key) {
		return jc.smembers(key);
	}

    @Override
    public Long sadd(String key, String... values) {
	    return jc.sadd(key, values);
    }
    
	@Override
	public Double incrByFloat(String key, double value) {
		return jc.incrByFloat(key, value);
	}

	@Override
	public Long del(String key) {
		return jc.del(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return jc.hgetAll(key);
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return jc.hmget(key, fields);
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return jc.hmset(key, hash);
	}

	@Override
	public Set<String> maptoredis(Map<String, Map<String, String>> map) {
		if(map == null || map.isEmpty() || map.size()<=0 ) {
			return null;
		}
		else {
			Set<String> keyset =
			map.entrySet().stream().
					filter(e -> (e!=null && StringUtils.isNotEmpty(e.getKey()))). //滤空
					filter(e -> "ok".equalsIgnoreCase(hmset(e.getKey(), e.getValue()))). //存储,滤存成功的
					map(e -> e.getKey()). //获取成功的key
					collect(Collectors.toSet());

			if(keyset == null) {
				return null;
			}

			sadd(mutaindex, keyset.toArray(new String[keyset.size()]));
			return keyset;
		}
	}


}
