package com.ifeng.ad.mutacenter.initialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangting3 on 2019/1/24.
 */
@Configuration
public class ThreadPoolExcutorProvider {
    private Logger logger = LoggerFactory.getLogger(ThreadPoolExcutorProvider.class);

    @Value("${mutacenter.corePoolSize}")
    public int corePoolSize;

    @Value("${mutacenter.maximumPoolSize}")
    public int maximumPoolSize;

    @Value("${mutacenter.keepAliveTime}")
    public int keepAliveTime;

    @Value("${mutacenter.blockingQueueSize}")
    public int blockingQueueSize;

    @Bean(name = "threadPoolExecutor")
    public ThreadPoolExecutor initPool() {
        //构造一个线程池
        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(corePoolSize,
                        maximumPoolSize,
                        keepAliveTime,
                        TimeUnit.MILLISECONDS,
                        new ArrayBlockingQueue<>(blockingQueueSize),
                        new ThreadPoolExecutor.DiscardOldestPolicy());
        logger.info("自定义线程池建立完成!");
        return threadPool;
    }
}
