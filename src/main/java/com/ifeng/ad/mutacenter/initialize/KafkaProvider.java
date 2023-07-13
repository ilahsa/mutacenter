package com.ifeng.ad.mutacenter.initialize;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  on 2019/1/27.
 */
@Configuration
public class KafkaProvider {
    private Logger logger = LoggerFactory.getLogger(KafkaProvider.class);

    @Bean
    public Map<String, String> consumerConfig(@Value("${kafka.servers}") String servers,
                                              @Value("${kafka.key-deserializer}") String keyDeserializer,
                                              @Value("${kafka.value-deserializer}") String valueDeserializer,
                                              @Value("${kafka.group-id}") String groupId,
                                              @Value("${kafka.auto-offset-reset}") String autoOffsetReset,
                                              @Value("${kafka.enable-auto-commit}") String enableAutoCommit) {
        Map<String,String> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        logger.info("kafka消费配置文件加载完成");
        return config;
    }

    @Bean
    public Map<String, String> producerConfig(@Value("${kafka.servers}") String servers,
                                     @Value("${kafka.key-deserializer}") String keyDeserializer,
                                     @Value("${kafka.value-deserializer}") String valueDeserializer,
                                     @Value("${kafka.acks}") String acks) {
        Map<String,String> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keyDeserializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueDeserializer);
        config.put(ProducerConfig.ACKS_CONFIG, acks);
        logger.info("kafka生产配置文件加载完成");
        return config;
    }
}
