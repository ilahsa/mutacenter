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
 * 项目描述: 腾讯云Redis集群加载类
 */
@Configuration
public class JedisCloudClusterProvider {
    private Logger logger = LoggerFactory.getLogger(JedisCloudClusterProvider.class);

    @Bean(name = "spring.redis.cloud-cluster.pool")
    public JedisPoolConfig jedisCloudClusterPoolConfig(@Value("${spring.redis.cloud-cluster.pool.max-total}") int maxTotal,
                                                       @Value("${spring.redis.cloud-cluster.pool.max-idle}") int maxIdle,
                                                       @Value("${spring.redis.cloud-cluster.pool.max-wait-millis}") int maxWaitMillis,
                                                       @Value("${spring.redis.cloud-cluster.pool.redis_test_on_borrow}") boolean redisTestOnBorrow) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(redisTestOnBorrow);
        return config;
    }


    @Bean(name = "cloudClusterJedisPool")
    public JedisPool jedisCloudClusterPool(@Qualifier("spring.redis.cloud-cluster.pool") JedisPoolConfig poolConfig,
                                           @Value("${spring.redis.cloud-cluster.host}") String host,
                                           @Value("${spring.redis.cloud-cluster.port}") int port,
                                           @Value("${spring.redis.cloud-cluster.pwd}") String pwd,
                                           @Value("${spring.redis.cloud-cluster.connection-timeout}") int timeout) {
        JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout, pwd);
        logger.info("cloudClusterJedisPool连接池创建完成！");
        return jedisPool;
    }
}
