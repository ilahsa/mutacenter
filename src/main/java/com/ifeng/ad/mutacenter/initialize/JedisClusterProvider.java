package com.ifeng.ad.mutacenter.initialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 项目描述: Redis集群加载类
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class JedisClusterProvider {
	private Logger logger = LoggerFactory.getLogger(JedisClusterProvider.class);

	@Bean(name = "spring.redis.pool")
	public JedisPoolConfig jedisPoolConfig(@Value("${spring.redis.jedis.pool.max-total}") int maxTotal,
			@Value("${spring.redis.jedis.pool.max-idle}") int maxIdle,
			@Value("${spring.redis.jedis.pool.max-wait-millis}") int maxWaitMillis,
			@Value("${spring.redis.jedis.pool.redis_test_on_borrow}") boolean redisTestOnBorrow) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setTestOnBorrow(redisTestOnBorrow);
		return config;
	}

	@Bean
	public JedisCluster jedisCluster(@Qualifier("spring.redis.pool") JedisPoolConfig poolConfig,
			@Value("${spring.redis.cluster.nodes}") String nodes,
			@Value("${spring.redis.cluster.connection-timeout}") int connectionTimeout,
			@Value("${spring.redis.cluster.socket-timeout}") int socketTimeout,
			@Value("${spring.redis.cluster.max-redirects}") int maxRedirects,
			@Value("${spring.redis.cluster.pwd}") String pwd) {

		Set<HostAndPort> nodeSet = Arrays.stream(nodes.split(",")).map(val -> {
			String[] arr = val.split(":");
			return new HostAndPort(arr[0], Integer.valueOf(arr[1]));
		}).collect(Collectors.toSet());

		JedisCluster jedisCluster = new JedisCluster(nodeSet, connectionTimeout, socketTimeout, maxRedirects, pwd, poolConfig);
		logger.info("RedisCluster连接池创建完成！");
		return jedisCluster;
	}
}
