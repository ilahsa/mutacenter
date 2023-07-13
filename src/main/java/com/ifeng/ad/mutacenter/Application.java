package com.ifeng.ad.mutacenter;

import javax.annotation.PostConstruct;

import com.ifeng.ad.mutacenter.verticles.EventBusVerticle;
import com.ifeng.ad.mutacenter.verticles.KafkaVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ifeng.ad.mutacenter.common.factory.SpringVerticleFactory;
import com.ifeng.ad.mutacenter.verticles.ShortConnRouterVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 类描述: 系统启动类
 */
@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
@PropertySource(value = { "classpath:application.yml" })
@EnableScheduling
public class Application {

    @Autowired
    SpringVerticleFactory springVerticleFactory;

    @Autowired
    Environment environment;

    @Autowired
    Vertx vertx;

    public static void main(String[] args) {
        // 设置Log4j2为全异步日志
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        // 设置Vertx的Log系统为Log4j2
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4j2LogDelegateFactory");
        // 禁用缓存目录
        System.setProperty("vertx.disableFileCPResolving", "true");

        // Spring Boot启
        SpringApplication.run(Application.class, args);
    }

    /**
     * 功能描述: 服务启动时,部署Verticle
     */
    @PostConstruct
    public void deployVerticles() {
        // Verticle工厂是手动注册的，因为它是由Spring容器创建的
        vertx.registerVerticleFactory(springVerticleFactory);

        // Verticle部署设置
        DeploymentOptions options = new DeploymentOptions()
                .setWorker(true) // 设置Verticle为Worker Verticle
                .setWorkerPoolName("ifeng-mutacenter-worker-thread") // 设置Worker线程池的名称
                .setInstances(environment.getProperty("vertx.verticle.instance", Integer.class)) // 实例数
                .setWorkerPoolSize(environment.getProperty("vertx.verticle.work-pool-size", Integer.class)); // Worker线程池数几个

        // 部署Verticles
        vertx.deployVerticle(springVerticleFactory.prefix() + ":" + ShortConnRouterVerticle.class.getName(), options);
        vertx.deployVerticle(springVerticleFactory.prefix() + ":" + KafkaVerticle.class.getName());
        vertx.deployVerticle(springVerticleFactory.prefix() + ":" + EventBusVerticle.class.getName());
    }
}
