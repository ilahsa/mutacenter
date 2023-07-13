package com.ifeng.ad.mutacenter.service.impl;

import com.ifeng.ad.mutacenter.common.utils.JsonUtils;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import com.ifeng.ad.mutacenter.service.RedisClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by baibq on 2020-09-15.
 */
@Component
public class RedisCloudClusterServiceImpl implements RedisClusterService {

    public static final Logger logger = LoggerFactory.getLogger(RedisCloudClusterServiceImpl.class);
    @Autowired
    @Qualifier("cloudClusterJedisPool")
    private JedisPool jedisPool ;//= (JedisPool) SpringUtils.getBean("cloudClusterJedisPool");

    private void returnResource(Jedis redis) {
        if (redis != null) {
            redis.close();
        }
    }

    @Override
    public boolean isHealthy() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.info("memory");
        } catch (JedisConnectionException jce) {
            logger.error("redis cloud server error:" + jce.getMessage());
            return false;
        } finally {
            returnResource(jedis);
        }

        return true;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String str = jedis.set(key, value);
        returnResource(jedis);
        return str;
    }

    @Override
    public String expSecondSet(String key, String value, int exptime) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.set(key,value,"EX",exptime);
        }catch (Exception ex){
            logger.error("expSecondSet error",ex);
            return null;
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public String expMillisecondSet(String key, String value, long exptime) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.set(key,value,"PX",exptime);
        }catch (Exception ex){
            logger.error("expMillisecondSet error",ex);
            return null;
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public Long expAtSet(String key, String value, long unixTime) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set(key,value);
            return jedis.expireAt(key,unixTime);
        }catch (Exception ex){
            logger.error("expAtSet error",ex);
            return null;
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String str = jedis.get(key);
        returnResource(jedis);
        return str;
    }

    @Override
    public <T> T get(String key, Class<T> cls) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        returnResource(jedis);
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        return JsonUtils.convert2Obj(result, cls);
    }


    @Override
    public Set<String> smembers(String key) {
        Jedis jedis = jedisPool.getResource();
        Set<String> set = jedis.smembers(key);
        returnResource(jedis);
        return set;
    }

    @Override
    public Long sadd(String key, String... values) {
        Jedis jedis = jedisPool.getResource();
        Long l = jedis.sadd(key, values);
        returnResource(jedis);
        return l;
    }

    @Override
    public Double incrByFloat(String key, double value) {
        Jedis jedis = jedisPool.getResource();
        Double d = jedis.incrByFloat(key, value);
        returnResource(jedis);
        return d;
    }

    @Override
    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long l = jedis.del(key);
        returnResource(jedis);
        return l;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> map = jedis.hgetAll(key);
        returnResource(jedis);
        return map;
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = jedisPool.getResource();
        List<String> list = jedis.hmget(key, fields);
        returnResource(jedis);
        return list;
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = jedisPool.getResource();
        String str = jedis.hmset(key, hash);
        returnResource(jedis);
        return str;
    }

    @Override
    public Set<String> maptoredis(Map<String, Map<String, String>> map) {
//        if(map == null || map.isEmpty() || map.size()<=0 ) {
//            return null;
//        }
//        else {
//            Set<String> keyset =
//                    map.entrySet().stream().
//                            filter(e -> (e!=null && StringUtils.isNotEmpty(e.getKey()))). //滤空
//                            filter(e -> "ok".equalsIgnoreCase(hmset(e.getKey(), e.getValue()))). //存储,滤存成功的
//                            map(e -> e.getKey()). //获取成功的key
//                            collect(Collectors.toSet());
//
//            if(keyset == null) {
//                return null;
//            }
//
//            sadd(mutaindex, keyset.toArray(new String[keyset.size()]));
//            return keyset;
//        }
        throw new NotImplementedException();
    }
}

