package com.ifeng.ad.mutacenter.service.impl;

import com.ifeng.ad.mutacenter.common.utils.JsonUtils;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import com.ifeng.ad.mutacenter.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisServiceImpl implements RedisService {

    public static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private JedisPool jedisPool;

    public RedisServiceImpl(JedisPool jedisPool){
        this.jedisPool = jedisPool;
    }
    @Override
    public void returnResource(Jedis redis) {
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
            logger.error("redis server error:" + jce.getMessage());
            return false;
        } finally {
            returnResource(jedis);
        }

        return true;
    }

    @Override
    public void expire(String key,int seconds){
        Jedis jedis = jedisPool.getResource();
        try {
           jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
    }
    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String str = null;
        try {
            str = jedis.set(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return str;
    }

    @Override
    public String set(String key, String value, long exptime) {
        Jedis jedis = jedisPool.getResource();
        String str = null;
        try {
            str = jedis.psetex(key, exptime, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return str;
    }
    @Override
    public long setnx(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        long ret = 0;
        try {
            ret = jedis.setnx(key,value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return ret;
    }
    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String str = null;
        try {
            str = jedis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return str;
    }

    @Override
    public <T> T get(String key, Class<T> cls) {
        Jedis jedis = jedisPool.getResource();
        String result = null;
        try {
            result = jedis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        return JsonUtils.convert2Obj(result, cls);
    }


    @Override
    public Set<String> smembers(String key) {
        Jedis jedis = jedisPool.getResource();
        Set<String> set = null;
        try {
            set = jedis.smembers(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return set;
    }

    @Override
    public Long sadd(String key, String... values) {
        Jedis jedis = jedisPool.getResource();
        Long l = null;
        try {
            l = jedis.sadd(key, values);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return l;
    }

    @Override
    public Double incrByFloat(String key, double value) {
        Jedis jedis = jedisPool.getResource();
        Double d = null;
        try {
            d = jedis.incrByFloat(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return d;
    }

    @Override
    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long l = null;
        try {
            l = jedis.del(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return l;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> map = null;
        try {
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return map;
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = jedisPool.getResource();
        List<String> list = null;
        try {
            list = jedis.hmget(key, fields);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return list;
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = jedisPool.getResource();
        String str = null;
        try {
            str = jedis.hmset(key, hash);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return str;
    }

    @Override
    public Set<String> maptoredis(Map<String, Map<String, String>> map, boolean clean, Set<String> cleanStartKeys) {
        if (map == null || map.isEmpty() || map.size() <= 0) {
            return null;
        } else {
            Jedis jedis = jedisPool.getResource();
            Set<String> keyset = null;
            try {
                keyset =
                        map.entrySet().stream().
                                filter(e -> (e != null && StringUtils.isNotEmpty(e.getKey()))). //滤空
                                filter(e -> updateredis(e, jedis)). //存储,滤存成功的
                                map(e -> e.getKey()). //获取成功的key
                                collect(Collectors.toSet());

                if (keyset == null) {
                    returnResource(jedis);
                    return null;
                }

                //清除陈旧无用的key
                if (clean && cleanStartKeys != null && !cleanStartKeys.isEmpty()) {
                    //获取所有keys,不适用keys，是因为keys存在性能问题
                    String scanRet = "0";
                    do {
                        ScanResult<String> ret = jedis.scan(scanRet);
                        scanRet = ret.getStringCursor();
                        List<String> keys = ret.getResult();

                        for (String key : keys) {
                            if (cleanStartKeys.stream().filter(key::startsWith).count() > 0 &&
                                    keyset.stream().filter(key::equals).count() == 0) {
                                jedis.del(key);
                            }
                        }
                    } while (!scanRet.equals("0"));
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                returnResource(jedis);
            }
            return keyset;
        }
    }

    private boolean updateredis(Map.Entry<String, Map<String, String>> e, Jedis jedis) {
        String key = e.getKey();
        jedis.del(key);

        Map<String, String> value = e.getValue();

        //过滤异常数据,map-value为空的数据无法存入redis
        value = value.entrySet().stream().filter(entry -> StringUtils.isNotEmpty(entry.getValue())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (value != null && value.size() > 0) {
            if (!value.containsKey("onOff")) { //不包含开关的情况
                if (value.containsKey("status") && !"0".equals(value.get("status"))) {
                    //状态为0是正常状态，非0时需要通知muta清除对应的对象
                    return true;
                } else {
                    //去掉无用的key值，经过if中逻辑以后，所有包含status的值都应是0
                    value.remove("status");
                    return "ok".equalsIgnoreCase(jedis.hmset(key, value));
                }
            } else {//有开关字段
                String onOff = value.get("onOff");
                if ("0".equals(onOff) && !"0".equals(value.get("status"))) {
                    //如果开关是启用，但是状态值为不可用，直接删掉
                    return true;
                }

                value.remove("status");
                return "ok".equalsIgnoreCase(jedis.hmset(key, value));
            }
        } else {
            return true;
        }
    }

    @Override
    public Set<String> maptoredis(Map<String, Map<String, String>> map) {
        return maptoredis(map, false, null);
    }

    @Override
    public Set<String> map4updateredis(Map<String, Map<String, String>> map, boolean checkMustHas, List<String> ignoreKeyWord) {
        if (map == null || map.isEmpty() || map.size() <= 0) {
            return null;
        } else {
            Jedis jedis = jedisPool.getResource();
            Set<String> keyset = null;
            try {
                keyset = map.entrySet().stream().
                        filter(e -> (e != null && StringUtils.isNotEmpty(e.getKey()))). //滤空
                        filter(e -> {
                    String key = e.getKey();

                    if (checkMustHas) {
                        //需要忽略key的list，1、如果为空，则全部检查，2、如果不是空，则判断不包含当前key，就进行检查
                        if (ignoreKeyWord == null || !ignoreKeyWord.contains(key)) {
                            //先检查是不是存在，存在才可以走后面的流程，不存在，直接返回
                            if (!jedis.exists(key)) {
                                return false;
                            }
                        }
                    }

                    Map<String, String> value = e.getValue();

                    if (value != null && value.size() > 0) {
                        //获取更改的数量
                        Long updatesize = value.entrySet().stream().filter(mapvalue -> {
                            //获取要操作的key
                            String mapk = mapvalue.getKey();
                            //获取要操作的值
                            String mapv = mapvalue.getValue();
                            //获取旧值
                            String oldmapv = jedis.hget(key, mapk);
                            //旧值不为空，并且和相等，不做更改操作
                            if (StringUtils.isNotEmpty(oldmapv) && oldmapv.equals(mapv)) {
                                return false;
                            }
                            //否则跟新redis缓存的数据，并记录一次更新
                            else {
                                jedis.hset(key, mapk, mapv);
                                return true;
                            }
                        }).count();

                        //如果有更新，则返回成功，标记此次更新操作执行了
                        if (updatesize > 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }). //存储,滤存成功的
                        map(e -> e.getKey()). //获取执行操作了的key
                        collect(Collectors.toSet());

                if (keyset == null) {
                    return null;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                returnResource(jedis);
            }
            return keyset;
        }
    }

    @Override
    public List<String> allkeys(String pattern) {
        Jedis jedis = null;
        try{
            List<String> retList = new ArrayList<String>();
            jedis = jedisPool.getResource();
            String scanRet = "0";
            ScanParams scanParams = new ScanParams();
            scanParams.match(pattern);
            do {
                ScanResult ret = jedis.scan(scanRet,scanParams);
                scanRet = ret.getStringCursor();
                retList.addAll(ret.getResult());
            } while (!scanRet.equals("0"));
            return retList;
        }catch (Exception ex){
            logger.error(ex.getMessage());
            throw ex;
        }finally {
            returnResource(jedis);
        }
    }
}
