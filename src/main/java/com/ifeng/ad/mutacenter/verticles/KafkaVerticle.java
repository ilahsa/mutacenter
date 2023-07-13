package com.ifeng.ad.mutacenter.verticles;

import com.ifeng.ad.mutacenter.Constant;
import com.ifeng.ad.mutacenter.handlers.KafkaHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Map;
import java.util.Set;

/**

 */
@Component
public class KafkaVerticle extends AbstractVerticle implements Constant {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(KafkaVerticle.class);
    private static Logger kafkalogger = LoggerFactory.getLogger("asyncKafkaWrite");
    private static Logger sdkkafkalogger = LoggerFactory.getLogger("asyncSdkKafkaWrite");

    @Resource(name="consumerConfig")
    private Map<String, String> consumerConfig;

    @Resource(name="producerConfig")
    private Map<String, String> producerConfig;

    @Value("${kafka.readtopic}")
    private String readtopic;

    @Value("${kafka.writetopic}")
    private String writetopic;

    @Value("${kafka.sdkwritetopic}")
    private String sdkWriteTopic;

    private KafkaProducer<String, String> producer;
    private KafkaConsumer<String, String> consumer;

    @Autowired
    private KafkaHandler kafkaHandler;

    @Override
    public void start(Future<Void> startFuture) {
        init();
        kafkaConsumer();

        startFuture.complete();
    }

    private void init() {
        producer = KafkaProducer.create(vertx, producerConfig, String.class, String.class);
        consumer = KafkaConsumer.create(vertx, consumerConfig);
    }

    /**
     * 停顿一下在发到kafka，给一点时间让redis主从同步
     *
     * @param msgSet
     */
    public void writeSet2Kafk(Set<String> msgSet) {
        if (CollectionUtils.isNotEmpty(msgSet)) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                msgSet.stream().forEach(this::write2KafkaNoSleep);
            }
        }
    }

    /**
     * 停顿一下在发到kafka，给一点时间让redis主从同步
     *
     * @param msg
     */
    public void write2Kafka(String msg) {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            write2KafkaNoSleep(msg);
        }
    }

    private void write2KafkaNoSleep(String msg) {
        KafkaProducerRecord<String, String> record = KafkaProducerRecord.create(writetopic, msg);
        producer.write(record, done -> {
            if (done.succeeded()) {
                kafkalogger.info(msg + "\tok");
            } else {
                kafkalogger.info(msg + "\terror");
            }
        });
    }

    public void write2SdkKafkaNosleep(String msg) {
        KafkaProducerRecord<String, String> record = KafkaProducerRecord.create(sdkWriteTopic, msg);
        producer.write(record, done -> {
            if (done.succeeded()) {
                sdkkafkalogger.info(msg + "\tok");
            } else {
                sdkkafkalogger.info(msg + "\terror");
            }
        });
    }

    public void kafkaConsumer() {
        consumer.subscribe(readtopic, kafkaHandler::kafkaSubscribe)
                .handler(kafkaHandler::kafkaRead);
    }

}