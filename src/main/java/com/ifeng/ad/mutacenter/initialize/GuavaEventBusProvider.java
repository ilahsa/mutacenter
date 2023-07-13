package com.ifeng.ad.mutacenter.initialize;

import com.google.common.eventbus.AsyncEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;

/**
 * 可以当生产-消费-存储模式使用，存储队列使用LinkedBlockingQueue
 * Created by  on 2019/1/23.
 */
@Configuration
public class GuavaEventBusProvider {
    private Logger logger = LoggerFactory.getLogger(GuavaEventBusProvider.class);

    private AsyncEventBus asyncEventBus;

    @PostConstruct
    public void initProvider() {
        asyncEventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());
        logger.info("guava AsyncEventBus建立完成!");
    }

    @Bean("guavaAsyncEventBus")
    public AsyncEventBus guavaEventBus() {
        return asyncEventBus;
    }
}
