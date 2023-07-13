package com.ifeng.ad.mutacenter.initialize;

import com.ifeng.ad.mutacenter.handlers.MainHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.client.WebClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * 类描述: 配置类
 */
@Configuration
public class AppConfiguration {
	
	@Autowired
	Environment environment;

	@Autowired
	ApplicationContext applicationContext;

	/**
	 * 功能描述: 加载Vertx并通过Spring托管
	 */
	@Bean
	public Vertx getVertx() {
		return Vertx.vertx();
	}

	/**
	 * 功能描述: 创建WebClient对象并交给Spring托管
	 */
	@Bean
	public WebClient getWebClient() {
		WebClientOptions options = new WebClientOptions().setKeepAlive(true)
				.setMaxPoolSize(environment.getProperty("vertx.http.client.pool-size", Integer.class))
				.setMaxWaitQueueSize(environment.getProperty("vertx.http.client.queue-size", Integer.class))
				.setIdleTimeout(environment.getProperty("vertx.http.client.idle-timeout", Integer.class))
				.setTcpNoDelay(true).setTcpKeepAlive(true);

		// 通过Vertx和option创建WebClient对象
		return WebClient.create(Vertx.vertx(), options);
	}

	@PostConstruct
	private void initUtils() {
		MainHandler.init(getVertx());
	}
}
