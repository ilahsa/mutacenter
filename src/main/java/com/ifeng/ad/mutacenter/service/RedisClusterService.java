package com.ifeng.ad.mutacenter.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类描述:redis连接
 */
public interface RedisClusterService {

	/**
	 * 功能描述: 判断集群是否健康
	 *
	 * @return 是:健康,否:不健康
	 */
	public boolean isHealthy();

	/**
	 * 功能描述: 设置Redis的值
	 *
	 * @param key 设置的key
	 * @param value 设置的value
	 * @return 设置后的返回结果
	 */
	public String set(String key, String value);

	/**
	 * 功能描述: 设置Redis的值并设置秒级超时时间
	 *
	 * @param key 设置的key
	 * @param value 设置的value
	 * @param exptime 超时时间，单位秒
	 * @return 设置后的返回结果
	 */
	public String expSecondSet(String key, String value, int exptime);

	/**
	 * 功能描述: 设置Redis的值并设置毫秒级超时时间
	 *
	 * @param key 设置的key
	 * @param value 设置的value
	 * @param exptime 超时时间，单位毫秒
	 * @return 设置后的返回结果
	 */
	public String expMillisecondSet(String key, String value, long exptime);


	/**
	 * 功能描述: 设置Redis的值并设置到达超时的时间
	 *
	 * @param key 设置的key
	 * @param value 设置的value
	 * @param unixTime 到达超时时间，
	 * @return 设置后的返回结果
	 */
	public Long expAtSet(String key, String value, long unixTime);

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
	public Set<String> maptoredis(Map<String, Map<String, String>> map);
}
