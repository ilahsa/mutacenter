package com.ifeng.ad.mutacenter.handlers;

import java.util.Iterator;
import java.util.Map;




import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;




import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.ifeng.ad.mutacenter.Constant;
import com.ifeng.ad.mutacenter.service.RedisService;
import com.ifeng.ad.mutacenter.verticles.KafkaVerticle;

/**
 * 权重处理器。<br>
 * 接收权重信息，首先存储到redis中，然后将redis的key发送到Kafka。<br>
 * 权重信息的格式示例：   {"广告位id" : {
 *                                      "网盟1" : ["10", "1", "1"], 
 *                                      "网盟2" : ["20", "2", "2"]
 *                                   },
 *                       ......
 *                     }
 *                      
 *                      
 * 
 * @author
 * @date 2019年2月19日
 */
@Component
public class WeightHandler implements Constant {
    private static final Logger logger = LoggerFactory.getLogger(WeightHandler.class);
    
    @Autowired
    private RedisService redisService;
    @Autowired
    private KafkaVerticle kafkaVerticle;

    public void handlerWeight(RoutingContext rtx) {
        HttpServerResponse response = rtx.response().putHeader("Content-Type", "text/plain");
        String weightMsg = rtx.getBodyAsString(DEFAULT_ENCODING);
        logger.info("received weight msg: {}", weightMsg);
        
        // 解析权重信息
        JsonObject weightJson = null;
        try {
            weightJson = new JsonObject(weightMsg);
        } catch (Exception e) {
            logger.error("parse weight msg error", e);
            response.end("weight msg format error");
            return;
        }
        
        // weightJson: {"18510": {"54": ["90", "399", "0"], "42": ["5", "215", "54"], "41": ["5", "154", "48"]}}
        for (String adPlaceId : weightJson.getMap().keySet()) {
            // weightMap: {"54": ["90", "399", "0"], "42": ["5", "215", "54"], "41": ["5", "154", "48"]}
            JsonObject weightMap = weightJson.getJsonObject(adPlaceId);
            Map<String, String> redisMap = Maps.newHashMapWithExpectedSize(weightMap.size());
            weightMap.getMap().keySet().stream()
                .forEach(allianceId -> {
                    // arr: ["90", "399", "0"]
                    JsonArray arr = weightMap.getJsonArray(allianceId);
                    String value = arr.isEmpty() ? StringUtils.EMPTY : Joiner.on(",").join(arr);
                    redisMap.put(allianceId, value);
                });
            
            String key = REDIS_PREFIX_WEIGHT + adPlaceId;
            logger.info("modify weight key: {}", key);
            // 存储到redis中
            redisService.hmset(key, redisMap);
            
            // 将redis的key发送到Kafka
            kafkaVerticle.write2Kafka(key);
        }
        
        response.end("success");
    }

    public void handlerDmpEcpm(RoutingContext rtx) {
        HttpServerResponse response = rtx.response().putHeader("Content-Type", "text/plain");
        String ecpmMsg = rtx.getBodyAsString(DEFAULT_ENCODING);
        logger.info("received ecpm msg: {}", ecpmMsg);

        // 解析权重信息
        JsonObject ecpmJson = null;
        try {
            ecpmJson = new JsonObject(ecpmMsg);
        } catch (Exception e) {
            logger.error("parse ecpm msg error", e);
            response.end("ecpm msg format error");
            return;
        }

        // ecpmJson: {"18510": {"54": "90", "42": "5", "41": "154"},"18511": {"54": "90", "42": "5", "41": "154"}}
        if(ecpmJson != null && !ecpmJson.isEmpty()) {
            Iterator<Map.Entry<String, Object>> iterator = ecpmJson.getMap().entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                String key = REDIS_PREFIX_ECPM + entry.getKey();
                Map vmap = (Map)entry.getValue();

                logger.info("modify ecpm key: {}", key);

                // 存储到redis中
                redisService.del(key);
                if(vmap != null && vmap.size() > 0) {
                    redisService.hmset(key, vmap);
                }

                // 将redis的key发送到Kafka
                kafkaVerticle.write2Kafka(key);
            }
        }

        response.end("success");
    }
}
