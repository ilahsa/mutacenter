package com.ifeng.ad.mutacenter.verticles;

import com.google.common.base.Strings;
import com.ifeng.ad.mutacenter.Constant;
import com.ifeng.ad.mutacenter.common.exception.BusinessException;
import com.ifeng.ad.mutacenter.common.exception.Errors;
import com.ifeng.ad.mutacenter.common.utils.ThrowableUtils;
import com.ifeng.ad.mutacenter.handlers.MainHandler;
import com.ifeng.ad.mutacenter.handlers.WeightHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * 项目描述: 路由Verticle
 */
public abstract class RouterVerticle extends AbstractVerticle implements Constant {

    private static final Logger log = LoggerFactory.getLogger(RouterVerticle.class);

    @Autowired
    protected MainHandler mainHandler;
    @Autowired
    protected WeightHandler weightHandler;

    @Autowired
    protected Environment environment;

    protected HttpServerOptions httpServerOptions;
    protected Integer port;

    public abstract void init();

    @Override
    public void start(Future<Void> startFuture) {
        init();
        // 获得Router
        Router router = Router.router(vertx);

        // 收集整个请求体并将其设置在RoutingContext上。
        router.route()
                .handler(BodyHandler.create())
                .handler(CookieHandler.create())
                .failureHandler(this::errHandle);

        router.route("/")
                .method(HttpMethod.GET)
                .method(HttpMethod.HEAD)
                .handler(this::indexRequest);
        
        router.route("/weight")
                .method(HttpMethod.POST)
                .handler(weightHandler::handlerWeight);

        router.route("/ecpm")
                .method(HttpMethod.POST)
                .handler(weightHandler::handlerDmpEcpm);

        /** 广告位修改通知接口 **/
        router.route("/:" + WORD_DEVID + "/:" + WORD_UPDATETPYE + "/:" + WORD_UPDATEID)
                .method(HttpMethod.GET)
                .method(HttpMethod.POST)
                .handler(mainHandler::adRequest);

        // 通过Route创建HttpServer
        vertx.createHttpServer(httpServerOptions)
                .requestHandler(router)
                .listen(port);

        // 路由Verticle加载完毕
        startFuture.complete();
    }

    /**
     * 功能描述: 系统Index
     */
    private void indexRequest(RoutingContext routingContext) {
        int status = 200;
        String hostname = "";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }
        StringBuilder results = new StringBuilder("hello, MutaCenter: ")
                .append(hostname)
                .append(" ")
                .append(new Date().getTime());
        routingContext.response()
                .setStatusCode(status)
                .putHeader("content-type", "text/plain")
                .end(results.toString());
    }


    /**
     * 功能描述: 异常处理
     */
    public void errHandle(RoutingContext routingContext) {
        if (routingContext.failed()) {
            HttpServerRequest request = routingContext.request();
            StringBuilder paramBuilder = new StringBuilder();
            if (request.method() == HttpMethod.POST && request.params().size() > 0) {
                request.params().names().stream().forEach(paramName -> {
                    paramBuilder.append('&').append(paramName).append('=').append(request.params().get(paramName));
                });
                paramBuilder.deleteCharAt(0);
            }
            String msg = StringUtils.join("Request[", request.uri(), "] from [", Strings.nullToEmpty(request.remoteAddress().host()), 
                    "] with params[", paramBuilder.toString(), "] failed[", routingContext.statusCode(), "]");
            
            /*
             * 如果Handler处理过程中出现异常，routingContext.failure()表示出现的异常。
             * 如果在Handler中调用RoutingContext.fail(int statusCode)，routingContext.failure()为null
             */
            Throwable t = routingContext.failure();
            int statusCode = 500;
            if (t != null) {
                if (t instanceof BusinessException) {
                    log.error(msg + " because [" + t.getMessage() + "]");
                    statusCode = Errors.getHttpCode(((BusinessException) t).getErrorCode());
                } else {
                    log.error(msg, ThrowableUtils.filterStackTraceElement(t, Constant.BASE_PACKAGE));
                }
            } else {
                log.error(msg);
            }
            routingContext.response()
                    .setStatusCode(statusCode)
//                    .setStatusMessage("Internal Server Error")
                    .end();
        }
    }
}