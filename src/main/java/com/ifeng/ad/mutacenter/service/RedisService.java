package com.ifeng.ad.mutacenter.service;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类描述:redis连接
 */
public interface RedisService {

	/**
	 * 功能描述: 返还连接
	 */
	public void returnResource(Jedis redis);

	/**
	 * 功能描述: 判断集群是否健康
	 *
	 * @return 是:健康,否:不健康
	 */
	public boolean isHealthy();

	public void expire(String key,int seconds);

	/**
	 * 功能描述: 设置Redis的值
	 *   
	 * @param key 设置的key
	 * @param value 设置的value
	 * @return 设置后的返回结果
	 */
	public String set(String key, String value);

	/**
	 * 功能描述: 设置Redis的值并设置超时时间
	 *   
	 * @param key 设置的key
	 * @param value 设置的value
	 * @param exptime 超时时间，单位毫秒
	 * @return 设置后的返回结果
	 */
	public String set(String key, String value, long exptime);

	/**
	 * ET if not exists(如果不存在，则 SET)
	 * @param key
	 * @param value
	 * @return
	 */
	public long setnx(String key, String value);

	/**
	 * 功能描述: 通过Key获得对应的值
	 *   
	 * @param key Redis Key
	 * @return 对应的值
	 */
	public String get(String key);

	/**
	 * 功能描述: 通过Key和类型获得对应的对象
	 *   
	 * @param key Redis Key
	 * @param clazz 返回对应的类
	 * @return null：未查询到结果，否则返回Json反序列化的值
	 */
	public <T> T get(String key, Class<T> clazz) ;

	/**
	 * 功能描述: 通过Key获得Set类型的集合
	 *
	 * @param key Redis Key
	 * @return key集合所有的元素
	 */
	public Set<String> smembers(String key);

	/**
	 * 功能描述: 添加一个或多个指定的member元素到集合的 key中
	 *
	 * @param key Redis Key
	 * @param values 一个或多个指定的member元素
	 * @return 返回新成功添加到集合里元素的数量，不包括已经存在于集合中的元素.
	 */
	public Long sadd(String key, String... values);
	
	/**
	 * 功能描述: 对一个Key进行原子自增一个double值
	 *   
	 * @param key 原子自增Key
	 * @param value 原子自增值
	 * @return   自增后的结果
	 */
	public Double incrByFloat(String key, double value);
	
	/**
	 * 功能描述: 删除某个key
	 *   
	 * @param key 需要删除的key
	 * @return   删除数量
	 */
	public Long del(String key);

	/**
	 * 功能描述: 获取指定hash的内容
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key);

	/**
	 * 功能描述: 获取指定hash指定字段的内容
	 * @param key
	 * @param fields
	 * @return
	 */
	public List<String> hmget(String key, String... fields);

	/**
	 * 功能描述: 添加一个hashmap数据
	 * @param key Redis Key
	 * @param hash Map
	 * @return
	 */
	public String hmset(String key, Map<String, String> hash);

	/**
	 * 将一组数据放入redis，并存入redis成功的key
	 * @param map
	 * @return
	 */
	public Set<String> maptoredis(Map<String,Map<String, String>> map);

	/**
	 * 将一组数据放入redis，并存入redis成功的key
	 * @param map
	 * @param clean 是否清除以前的数据
	 * @param cleanStartKeys 清除的key开始字段
	 * @return
	 */
	public Set<String> maptoredis(Map<String,Map<String, String>> map, boolean clean, Set<String> cleanStartKeys);

	/**
	 * 将一组数据更新到redis中，并存入redis成功的key
	 * @param map
	 * @param checkMustHas 检查是否存在, 如果为true，在先检查redis中有值才会插入
	 * @return
	 */
	public Set<String> map4updateredis(Map<String,Map<String, String>> map, boolean checkMustHas, List<String> ignoreKeyWord);

	/**
	 * 获取匹配 pattern 的 keys
	 * @param pattern
	 * @return
	 */
	public List<String> allkeys(String pattern);
}
