package com.ifeng.ad.mutacenter.handlers;

import com.google.common.collect.Sets;
import com.google.common.eventbus.AsyncEventBus;
import com.ifeng.ad.mutacenter.common.geventbus.bean.ReqObj;

import io.vertx.core.AsyncResult;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by wangting3 on 2019/1/29.
 */
@Component
public class KafkaHandler {
    private static Logger logger = LoggerFactory.getLogger(KafkaHandler.class);
    private static Logger kafkalogger = LoggerFactory.getLogger("asyncKafka");

    @Autowired
    private AsyncEventBus guavaAsyncEventBus;

    public void kafkaSubscribe(AsyncResult<Void> res) {
        logger.info("读topic连接" + res.succeeded());
    }

    public void kafkaRead(KafkaConsumerRecord<String, String> kafkaConsumerRecord) {
        String msg = null;
        try {
            msg = kafkaConsumerRecord.value();
            kafkalogger.info(msg);
            if (StringUtils.isEmpty(msg)) {
                return;
            }

            String[] msgs = msg.split("\t");
            if (msgs.length != 4) {
                return;
            }
            Set<String> codeid;
            if (msgs[3].contains(",")) {
                codeid = Sets.newHashSet(msgs[3].split(",")).stream().
                        filter(v -> !"-1".equals(v)).
                        collect(Collectors.toSet());
            } else {
                codeid = Sets.newHashSet(msgs[3]);
            }

            ReqObj reqObj = ReqObj.
                    create().
                    setDev(msgs[1]).
                    setType(msgs[2]).
                    setIds(codeid);
            guavaAsyncEventBus.post(reqObj);
        } catch (Exception e) {
            logger.error(MessageFormatter.arrayFormat("kafka消息[{}]读取错误", new Object[]{msg}).getMessage(), e);
        }
    }

}
