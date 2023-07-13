package com.ifeng.ad.mutacenter.handlers;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.eventbus.AsyncEventBus;
import com.ifeng.ad.mutacenter.Constant;
import com.ifeng.ad.mutacenter.common.geventbus.bean.ReqObj;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

/**
 * 类描述: 引擎处理主Handler
 */
@Component
public class MainHandler implements Constant {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(MainHandler.class);
    private static Logger reqlogger = LoggerFactory.getLogger("asyncReq");

    @Autowired
    protected Vertx vertx;
    @Autowired
    private AsyncEventBus guavaAsyncEventBus;

    private static WorkerExecutor evevtBusListenWorkerExecutor = null;

    public static void init(Vertx vertx) {
        if (evevtBusListenWorkerExecutor == null) {
            evevtBusListenWorkerExecutor = vertx.createSharedWorkerExecutor(
                    Constant.EVENT_BUS_LISTEN_WORKER_POOL_NAME,
                    Constant.EVENT_BUS_LISTEN_WORKER_POOL_SIZE,
                    Constant.EVENT_BUS_LISTEN_WORKER_POOL_MAX_EXEC_TIME,
                    Constant.EVENT_BUS_LISTEN_WORKER_POOL_TIME_UNIT);
        }
    }

    public void adRequest(RoutingContext routingContext) {
        WorkerExecutor executor = vertx.createSharedWorkerExecutor(Constant.EVENT_BUS_LISTEN_WORKER_POOL_NAME);
        HttpServerRequest request = routingContext.request();
        HttpServerResponse response = routingContext.response();
        try {
            executor.executeBlocking(future -> {
                service(request, response);
            }, false, res -> {
                if (res.failed()) {
                    logger.info("Event bus has listened failed, info is: {}", routingContext.request().getParam("p"));
                }
            });
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        } finally {
            executor.close();
        }

        response.putHeader("Access-Control-Allow-Credentials", "true")
                .putHeader("Content-Type", "text/plain;charset=" + DEFAULT_ENCODING)
                .setStatusCode(200)
                .setStatusMessage("OK")
                .end("OK");
    }

    /**
     * 对外提供服务，用于更新数据
     */
    private String service(HttpServerRequest request, HttpServerResponse response) {
        String dev = request.getParam(WORD_DEVID);
        String type = request.getParam(WORD_UPDATETPYE);
        String id = request.getParam(WORD_UPDATEID);
        reqlogger.info("{} {}/{}/{}", LocalDate.now(), Strings.nullToEmpty(dev),Strings.nullToEmpty(type), Strings.nullToEmpty(id));

        if (StringUtils.isEmpty(dev)) {
            logger.error("update dev is NULL");
        }
        if (StringUtils.isEmpty(type)) {
            logger.error("update type is NULL");
        }
        if (StringUtils.isEmpty(id)) {
            logger.error("update id is NULL");
        }

        Set<String> ids = Sets.newHashSet(id.split(","));
        ReqObj reqObj = ReqObj.
                create().
                setDev(dev).
                setType(type).
                setIds(ids);
        guavaAsyncEventBus.post(reqObj);
        return "ok";
    }
}
