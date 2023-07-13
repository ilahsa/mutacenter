package com.ifeng.ad.mutacenter.initialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 项目描述: 单机Redis加载类
 */
@Configuration
public class JedisSingleProvider {
	private Logger logger = LoggerFactory.getLogger(JedisSingleProvider.class);

	@Bean(name = "spring.redis.single.pool")
	public JedisPoolConfig jedisPoolConfig(@Value("${spring.redis.single.pool.max-total}") int maxTotal,
			@Value("${spring.redis.single.pool.max-idle}") int maxIdle,
			@Value("${spring.redis.single.pool.max-wait-millis}") int maxWaitMillis,
			@Value("${spring.redis.single.pool.redis_test_on_borrow}") boolean redisTestOnBorrow) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setTestOnBorrow(redisTestOnBorrow);
		return config;
	}

	@Bean(name = "jedisPool")
	public JedisPool jedisPool(@Qualifier("spring.redis.single.pool") JedisPoolConfig poolConfig,
			@Value("${spring.redis.single.host}") String host,
			@Value("${spring.redis.single.port}") int port,
			@Value("${spring.redis.single.pwd}") String pwd,
			@Value("${spring.redis.single.connection-timeout}") int timeout) {
		JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout, pwd);
		logger.info("JedisPool连接池创建完成！");
		return jedisPool;
	}

	@Bean(name = "jedisPoolEx")
	public JedisPool jedisPoolEx(@Qualifier("spring.redis.single.pool") JedisPoolConfig poolConfig,
							   @Value("${spring.redis.single.host}") String host,
							   @Value("${spring.redis.single.port}") int port,
							   @Value("${spring.redis.single.pwd}") String pwd,
							   @Value("${spring.redis.single.connection-timeout}") int timeout) {
		JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout, pwd,1);
		logger.info("JedisPool连接池创建完成！");
		return jedisPool;
	}
}
