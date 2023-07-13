package com.ifeng.ad.mutacenter.verticles;

import com.google.common.eventbus.AsyncEventBus;
import com.ifeng.ad.mutacenter.Constant;
import com.ifeng.ad.mutacenter.common.geventbus.DealMysql2Redis;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventBusVerticle extends AbstractVerticle implements Constant {

    @Autowired
    private AsyncEventBus guavaAsyncEventBus;

    @Autowired
    private DealMysql2Redis dealMysql2Redis;

    @Override
    public void start(Future<Void> startFuture) {
        // 注册消费者
        guavaAsyncEventBus.register(dealMysql2Redis);
    }
}