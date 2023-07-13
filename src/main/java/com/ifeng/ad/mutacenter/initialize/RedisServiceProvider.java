package com.ifeng.ad.mutacenter.initialize;

import com.ifeng.ad.mutacenter.service.RedisService;
import com.ifeng.ad.mutacenter.service.impl.RedisServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author baibq
 * @version 1.0
 * @date 2022-10-12 15:12
 */
@Configuration
public class RedisServiceProvider {
    @Resource(name="jedisPool")
    private JedisPool jedisPool;

    @Resource(name="jedisPoolEx")
    private JedisPool jedisPoolEx;

    @Bean(name = "redisService")
    public RedisService redisService(){
        return new RedisServiceImpl(jedisPool);
    }
    @Bean(name = "redisServiceEx")
    public RedisService redisServiceEx(){
        return new RedisServiceImpl(jedisPoolEx);
    }
}