package com.ifeng.ad.mutacenter.verticles;

import io.vertx.core.http.HttpServerOptions;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 项目描述: 短链接服务Verticle
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShortConnRouterVerticle extends RouterVerticle {

    public void init() {
        httpServerOptions = new HttpServerOptions().setTcpKeepAlive(false).setTcpNoDelay(true);
        port = environment.getProperty("local.server.short-port", Integer.class);
    }
}